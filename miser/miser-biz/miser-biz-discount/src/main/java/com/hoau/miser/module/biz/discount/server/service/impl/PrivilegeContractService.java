package com.hoau.miser.module.biz.discount.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.server.web.message.MessageBundle;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.discount.api.server.service.IPrivilegeContractService;
import com.hoau.miser.module.biz.discount.api.shared.define.CouponCheckStateEnum;
import com.hoau.miser.module.biz.discount.api.shared.domain.CouponAcceptorEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.CouponStateEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractDetailEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeCouponEntity;
import com.hoau.miser.module.biz.discount.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.discount.api.shared.vo.CouponAcceptorVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeContractDetailVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeContractVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeCouponCheckVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeStateQueryVo;
import com.hoau.miser.module.biz.discount.server.dao.PrivilegeContractDao;
import com.hoau.miser.module.biz.discount.server.dao.PrivilegeCouponStateDao;
import com.hoau.miser.module.common.server.service.ISerialNumberService;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.common.shared.define.SerialNumberRuleEnum;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.DateUtils;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 越发越惠客户合同 ClassName: PrivilegeContractService
 * 
 * @author 286330付于令
 * @date 2016年1月13日
 * @version V1.0
 */
@Service
public class PrivilegeContractService implements IPrivilegeContractService {

	@Resource
	private PrivilegeContractDao privilegeContractDao;
	@Resource
	private MessageBundle messageBundle;
	@Resource
	private ISerialNumberService serialNumberService;
	@Resource
	private PrivilegeCouponStateDao privilegeCouponStateDao;

	/**
	 * 根据参数查询合同列表（如果参数中带客户编号，则以客户编号为唯一参数进行查询）
	 */
	public List<PrivilegeContractEntity> queryListByParam(PrivilegeContractVo contractVo, int limit,
			int start) {
		PrivilegeContractEntity paramEntity = contractVo.getPrivilegeContractEntity();
		PrivilegeContractEntity entity = new PrivilegeContractEntity();
		if(!StringUtils.isEmpty(paramEntity.getCustomerCode())) {
//			entity.setCustomerCode(paramEntity.getCustomerCode());
			entity = paramEntity;
		} else {
			entity = paramEntity;
		}
		RowBounds rowBounds = new RowBounds(start, limit);
		return privilegeContractDao.queryContractList(entity, rowBounds);
	}

	public Long queryCountByParam(PrivilegeContractVo contractVo) {
		return privilegeContractDao.queryContractCount(contractVo.getPrivilegeContractEntity());
	}

	/**
	 * 新增合同
	 */
	public String addPrivilegeContract(PrivilegeContractEntity contractEntity, String privilegeContractDetailList) {
		List<PrivilegeContractDetailEntity> jsonArray = com.alibaba.fastjson.JSON.parseArray(
				privilegeContractDetailList, PrivilegeContractDetailEntity.class);
		// 校验数据
		String result = checkPrivilegeContract(contractEntity);
		if (result != null) {
			return result;
		}
		result = checkPrivilegeContractDetail(jsonArray);
		if (result != null) {
			return result;
		}
		beforeAddPrivilegeContractDispose(contractEntity);
		// 校验重复数据 -- 根据客户CODE，合同开始结束时间
		List<PrivilegeContractEntity> existContractList = privilegeContractDao
				.findPrivilegeContract(contractEntity);
		if(existContractList != null && existContractList.size() > 0) {
			// 已经存在一份合同与当前合同的生效时间有重叠
			return messageBundle.getMessage("discount.privilegeContract.contract.exist");
		}
		// 新增数据
		String contractCode = serialNumberService.generateSerialNumber(
				SerialNumberRuleEnum.PRIVILEGE_CONTRACT_CODE);
		contractEntity.setContractCode(contractCode);
		privilegeContractDao.addPrivilegeContract(contractEntity);
		for (PrivilegeContractDetailEntity detailEntity : jsonArray) {
			beforAddPrivilegeContractDetailDispose(detailEntity, contractEntity);
			beforAddPrivilegeContractDetailDispose(detailEntity);
			detailEntity.setCustomerContractId(contractEntity.getId());
			privilegeContractDao.addPrivilegeContractDetail(detailEntity);
		}
		return null;
	}

	/**
	 * 修改合同
	 */
	public String updatePrivilegeContract(PrivilegeContractEntity contractEntity,
			String privilegeContractDetailList) {
		List<PrivilegeContractDetailEntity> jsonArray = com.alibaba.fastjson.JSON.parseArray(
				privilegeContractDetailList, PrivilegeContractDetailEntity.class);
		// 校验新数据
		String result = checkPrivilegeContract(contractEntity);
		if (result != null) {
			return result;
		}
		result = checkPrivilegeContractDetail(jsonArray);
		if (result != null) {
			return result;
		}
		String oldContractId = contractEntity.getId();
		// 失效合同数据
		PrivilegeContractEntity oldContractParam = new PrivilegeContractEntity();
		oldContractParam.setId(oldContractId);
		RowBounds rowBounds = new RowBounds(0, 1);
		List<PrivilegeContractEntity> contractList = privilegeContractDao
				.queryContractList(oldContractParam, rowBounds);
		if(contractList == null) {
			return messageBundle.getMessage("discount.privilegeContract.oldData.empty");
		}
		PrivilegeContractEntity oldContract = contractList.get(0);
		oldContract.setModifyDate(new Date());
		oldContract.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		oldContract.setActive(MiserConstants.NO);
		privilegeContractDao.delPrivilegeContract(oldContract);
		// 失效合同明细数据
		PrivilegeContractDetailEntity oldDetailParam = new PrivilegeContractDetailEntity();
		oldDetailParam.setCustomerContractId(oldContractId);
		rowBounds = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
		List<PrivilegeContractDetailEntity> oldDetailList = privilegeContractDao
				.queryContractDetailList(oldDetailParam, rowBounds);
		for (PrivilegeContractDetailEntity oldDetail : oldDetailList) {
			oldDetail.setModifyDate(new Date());
			oldDetail.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			oldDetail.setActive(MiserConstants.NO);
			privilegeContractDao.delPrivilegeContractDetail(oldDetail);
		}
		// 新增新数据
		beforeAddPrivilegeContractDispose(contractEntity);
		contractEntity.setContractCode(oldContract.getContractCode());
		privilegeContractDao.addPrivilegeContract(contractEntity);
		for (PrivilegeContractDetailEntity entity : jsonArray) {
			beforAddPrivilegeContractDetailDispose(entity, contractEntity);
			beforAddPrivilegeContractDetailDispose(entity);
			entity.setCustomerContractId(contractEntity.getId());
			privilegeContractDao.addPrivilegeContractDetail(entity);
		}
		return null;
	}

	/**
	 * 删除合同
	 */
	public void delPrivilegeContract(PrivilegeContractEntity contractEntity) {
		contractEntity.setModifyDate(new Date());
		contractEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		contractEntity.setActive(MiserConstants.NO);
		privilegeContractDao.delPrivilegeContract(contractEntity);

		String contractId = contractEntity.getId();
		RowBounds rowBounds = new RowBounds(0, 1);
		PrivilegeContractDetailVo detailVo = new PrivilegeContractDetailVo();
		PrivilegeContractDetailEntity detailEntity = new PrivilegeContractDetailEntity();
		detailEntity.setCustomerContractId(contractId);
		detailVo.setPrivilegeContractDetailEntity(detailEntity);
		rowBounds = new RowBounds(0, 20);
		List<PrivilegeContractDetailEntity> contractDetailList = privilegeContractDao
				.queryContractDetailList(detailVo.getPrivilegeContractDetailEntity(), rowBounds);
		for (PrivilegeContractDetailEntity entity : contractDetailList) {
			entity.setActive(MiserConstants.NO);
			entity.setModifyDate(new Date());
			entity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			privilegeContractDao.delPrivilegeContractDetail(entity);
		}
	}

	public PrivilegeContractEntity queryPrivilegeContractById(String id) {
		PrivilegeContractVo contractVo = new PrivilegeContractVo();
		PrivilegeContractEntity contractEntity = new PrivilegeContractEntity();
		contractEntity.setId(id);
		contractVo.setPrivilegeContractEntity(contractEntity);
		List<PrivilegeContractEntity> list = this.queryListByParam(contractVo, 1, 0);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 查询合同明细
	 */
	public List<PrivilegeContractDetailEntity> queryListByParamSub(PrivilegeContractDetailVo detailVo,
			int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return privilegeContractDao.queryContractDetailList(detailVo.getPrivilegeContractDetailEntity(),
				rowBounds);
	}
	/**
	 * 查询合同明细count
	 */
	public Long queryCountByParam(PrivilegeContractDetailVo detailVo) {
		return privilegeContractDao.queryContractDetailCount(detailVo.getPrivilegeContractDetailEntity());
	}

	public void addPrivilegeContractDetail(PrivilegeContractDetailEntity detailEntity) {
		beforAddPrivilegeContractDetailDispose(detailEntity);
		privilegeContractDao.addPrivilegeContractDetail(detailEntity);
	}

	public void updatePrivilegeContractDetail(PrivilegeContractDetailEntity detailEntity) {
		detailEntity.setActive(MiserConstants.YES);
		detailEntity.setModifyDate(new Date());
		detailEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		privilegeContractDao.updatePrivilegeContractDetail(detailEntity);
	}

	public void delPrivilegeContractDetail(PrivilegeContractDetailEntity detailEntity) {
		detailEntity.setModifyDate(new Date());
		detailEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		detailEntity.setActive(MiserConstants.NO);
		privilegeContractDao.delPrivilegeContractDetail(detailEntity);
	}

	public PrivilegeContractDetailEntity queryPrivilegeContractDetailById(String id) {
		PrivilegeContractDetailVo detailVo = new PrivilegeContractDetailVo();
		PrivilegeContractDetailEntity detailEntity = new PrivilegeContractDetailEntity();
		detailEntity.setId(id);
		detailVo.setPrivilegeContractDetailEntity(detailEntity);
		List<PrivilegeContractDetailEntity> list = this.queryListByParamSub(detailVo, 1, 0);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * @Description: 新增越发越惠客户合同 赋初始值
	 * @param contractEntity
	 * @return void
	 * @throws
	 * @author 付于令
	 * @date 2016年01月13日
	 */
	public void beforeAddPrivilegeContractDispose(PrivilegeContractEntity contractEntity) {
		Date dt = new Date();
		contractEntity.setId(UUIDUtil.getUUID());
		contractEntity.setCreateDate(dt);
		contractEntity.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		contractEntity.setModifyDate(dt);
		contractEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		contractEntity.setActive(MiserConstants.YES);
	}

	/**
	 * @Description: 越发越惠客户合同明细 新增赋值
	 * @param detailEntity
	 * @return void
	 * @author 付于令
	 * @date 2016年01月13日
	 */
	public void beforAddPrivilegeContractDetailDispose(PrivilegeContractDetailEntity detailEntity) {
		Date dt = new Date();
		detailEntity.setId(UUIDUtil.getUUID());
		detailEntity.setCreateDate(dt);
		detailEntity.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		detailEntity.setModifyDate(dt);
		detailEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		detailEntity.setActive(MiserConstants.YES);
		// 数据来源设置，默认是PMS系统新增
		if (StringUtil.isEmpty(detailEntity.getDataOrign())) {
			detailEntity.setDataOrign(DataOrignEnum.PMS.getCode());
		}
	}

	public List<PrivilegeStateQueryVo> queryListByParamForStateQuery(PrivilegeStateQueryVo stateQueryVo,
			int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		return privilegeContractDao.queryListByParamForStateQuery(stateQueryVo, rowBounds);
	}

	public String updateCouponAcceptor(CouponAcceptorVo couponAcceptorVo) {
		CouponAcceptorEntity couponAcceptorEntity = couponAcceptorVo.getCouponAcceptorEntity();
		privilegeContractDao.updateCouponAcceptor(couponAcceptorEntity);
		return MessageType.UPDATE_SUCCESS;
	};

	public String addCouponAcceptor(CouponAcceptorVo couponAcceptorVo) {
		CouponAcceptorEntity couponAcceptorEntity = couponAcceptorVo.getCouponAcceptorEntity();
		PrivilegeCouponEntity couponEntity = new PrivilegeCouponEntity();
		couponEntity.setContractCode(couponAcceptorEntity.getContractCode());
		Date recordMonth = couponAcceptorEntity.getRecordMonth();
		recordMonth = DateUtils.strToDate(DateUtils.convert(recordMonth).substring(0, 8) + "01 00:00:00");
		couponEntity.setRecordMonth(recordMonth);
		RowBounds rowBounds = new RowBounds(0, 1);
		List<PrivilegeCouponEntity> pceList = privilegeContractDao
				.queryPrivilegeCouponList(couponEntity, rowBounds);
		// 获得返券ID查询是否有收券人ID
		if (null != pceList && 0 < pceList.size()) {
			couponEntity = pceList.get(0);
			String acceptorId = couponEntity.getCouponAcceptorId();
			// 如果有返券ID则根据返券ID查询返券人信息
			if (!StringUtils.isEmptyWithBlock(acceptorId)) {
				couponAcceptorEntity.setId(acceptorId);
				couponAcceptorEntity.setActive(MiserConstants.NO);
				couponAcceptorEntity.setModifyDate(new Date());
				couponAcceptorEntity.setModifyUser(MiserUserContext.getCurrentUser()
						.getEmpNameAndUserName());
				privilegeContractDao.delCouponAcceptor(couponAcceptorEntity);
				beforAddCouponAcceptor(couponAcceptorEntity);
				privilegeContractDao.addCouponAcceptor(couponAcceptorEntity);
				//  重置ID前删除老数据
				delCouponEntity(couponEntity.getId());
				couponEntity.setId(UUIDUtil.getUUID());
				couponEntity.setCouponAcceptorId(couponAcceptorEntity.getId());
				couponEntity.setCouponState("PENDING_CHECK");
				privilegeContractDao.addPrivilegeCoupon(couponEntity);
			} else {
				beforAddCouponAcceptor(couponAcceptorEntity);
				privilegeContractDao.addCouponAcceptor(couponAcceptorEntity);
				couponEntity.setCouponAcceptorId(couponAcceptorEntity.getId());
				couponEntity.setCouponState("PENDING_CHECK");
				privilegeContractDao.updatePrivilegeCoupon(couponEntity);
			}
		    //  保存返券状态记录
			CouponStateEntity couponStateEntity = new CouponStateEntity();
			couponStateEntity.setContractCode(couponEntity.getContractCode());
			couponStateEntity.setRecordMonth(recordMonth);
			couponStateEntity.setExecuteState("PENDING_CHECK");
			couponStateEntity.setExecuteTime(new Date());
			couponStateEntity.setExecuteUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			initCouponStateEntity(couponStateEntity);
			privilegeCouponStateDao.saveCouponStateEntity(couponStateEntity);
		} else {
			return "申请的返券数据不存在";
		}
		return null;
	};
	
	private void delCouponEntity(String id) {
		PrivilegeCouponEntity delCouponEntity = new PrivilegeCouponEntity();
		delCouponEntity.setId(id);
		delCouponEntity.setModifyDate(new Date());
		delCouponEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		delCouponEntity.setActive(MiserConstants.NO);
		privilegeContractDao.delPrivilegeCoupon(delCouponEntity);
	}

	public void beforAddCouponAcceptor(CouponAcceptorEntity acceptorEntity) {
		Date dt = new Date();
		acceptorEntity.setId(UUIDUtil.getUUID());
		acceptorEntity.setCreateDate(dt);
		acceptorEntity.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		acceptorEntity.setModifyDate(dt);
		acceptorEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		acceptorEntity.setActive(MiserConstants.YES);
	}

	/**
	 * @Description: 越发越惠客户合同明细新增赋值
	 * @author 付于令
	 * @date 2016年01月14日
	 */
	private void beforAddPrivilegeContractDetailDispose(PrivilegeContractDetailEntity detailEntity,
			PrivilegeContractEntity contractEntity) {
		String hasCoupon = contractEntity.getHasCoupon();
		if (StringUtil.isNotEmpty(hasCoupon) && "Y".equals(hasCoupon)) {
			detailEntity.setDdMinFreightDiscount(contractEntity.getDdMinFreightDiscount());
			detailEntity.setDuMinFreightDiscount(contractEntity.getDuMinFreightDiscount());
		}
	}
	
	/**
	 * @Description: 更新返券状态
	 * @author 付于令
	 * @date 2016年01月14日
	 */
	@Override
	public String updateCouponState(PrivilegeCouponCheckVo couCheckVo) {
		if (couCheckVo == null || StringUtil.isEmpty(couCheckVo.getCheckState())) {
			// 参数错误，缺少参数
			return "discount.privilegeCouponCheck.paramError";
		}
		PrivilegeCouponEntity paramEntity = new PrivilegeCouponEntity();
		Date recordMonth = couCheckVo.getRecordMonth();
		recordMonth = DateUtils.strToDate(DateUtils.convert(recordMonth).substring(0, 8) + "01 00:00:00");
		paramEntity.setRecordMonth(recordMonth);
		paramEntity.setContractCode(couCheckVo.getContractCode());
		List<PrivilegeCouponEntity> couponList = privilegeContractDao
				.queryPrivilegeCouponList(paramEntity, new RowBounds(0, 1));
		if (couponList == null || couponList.size() == 0) {
			// 无效的返券数据
			return "discount.privilegeCouponCheck.invalidCouponData";
		}
		PrivilegeCouponEntity couponEntity = couponList.get(0);
		String oldState = couponEntity.getCouponState();
		CouponCheckStateEnum newStateEnum = null;
		try {
			newStateEnum = CouponCheckStateEnum.valueOf(couCheckVo.getCheckState());
		} catch (Exception e) {
			// 无效的返券状态
			return "discount.privilegeCouponCheck.invalidCouponState";
		}
		if (StringUtil.isNotEmpty(oldState)) {
			CouponCheckStateEnum oldStateEnum = CouponCheckStateEnum.valueOf(oldState);
			if (newStateEnum == CouponCheckStateEnum.CHECK_SUCCESS
					|| newStateEnum == CouponCheckStateEnum.CHECK_REJECT) {
				// 审核
				if (oldStateEnum != CouponCheckStateEnum.PENDING_CHECK) {
					return messageBundle.getMessage(oldStateEnum.getMessage());
				}
				couponEntity.setCheckTime(new Date());
				couponEntity.setCheckRemark(couCheckVo.getCheckRemark());
			} else if (newStateEnum == CouponCheckStateEnum.COUPON_FAIL) {
				// 返券失败登记
				if (oldStateEnum != CouponCheckStateEnum.CHECK_SUCCESS) {
					return messageBundle.getMessage(oldStateEnum.getMessage());
				}
				couponEntity.setCouponTime(new Date());
				couponEntity.setCouponRemark(couCheckVo.getCheckRemark());
			}
		} else {
			couponEntity.setCheckTime(new Date());
			couponEntity.setCheckRemark(couCheckVo.getCheckRemark());
		}
		couponEntity.setCouponState(newStateEnum.getValue());
		couponEntity.setModifyDate(new Date());
		couponEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		privilegeContractDao.updatePrivilegeCoupon(couponEntity);
		//  保存返券状态记录
		CouponStateEntity couponStateEntity = new CouponStateEntity();
		couponStateEntity.setContractCode(couponEntity.getContractCode());
		couponStateEntity.setRecordMonth(recordMonth);
		couponStateEntity.setExecuteState(newStateEnum.getValue());
		couponStateEntity.setExecuteTime(new Date());
		couponStateEntity.setExecuteRemark(couCheckVo.getCheckRemark());
		couponStateEntity.setExecuteUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		initCouponStateEntity(couponStateEntity);
		privilegeCouponStateDao.saveCouponStateEntity(couponStateEntity);
		
		return null;
	}
	
	private void initCouponStateEntity(CouponStateEntity couponStateEntity) {
		Date dt = new Date();
		couponStateEntity.setId(UUIDUtil.getUUID());
		couponStateEntity.setCreateDate(dt);
		couponStateEntity.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		couponStateEntity.setModifyDate(dt);
		couponStateEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		couponStateEntity.setActive(MiserConstants.YES);
		String state = couponStateEntity.getExecuteState();
		if("PENDING_CHECK".equals(state)) {
			couponStateEntity.setExecuteStateDes("申请返券");
		} else if("CHECK_SUCCESS".equals(state)) {
			couponStateEntity.setExecuteStateDes("审核成功");
		} else if("CHECK_REJECT".equals(state)) {
			couponStateEntity.setExecuteStateDes("审核失败");
		} else if("COUPON_FAIL".equals(state)) {
			couponStateEntity.setExecuteStateDes("返券失败");
		}
	}

	public List<CouponAcceptorEntity> queryListByParamForCouponAcceptorEntity(CouponAcceptorVo acceptorVo,
			int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return privilegeContractDao.queryListByParamForCouponAcceptor(
				acceptorVo.getCouponAcceptorEntity(), rowBounds);
	}

	public CouponAcceptorEntity queryCouponAcceptorVoById(CouponAcceptorEntity entity) {
		Date recordMonth = entity.getRecordMonth();
		recordMonth = DateUtils.strToDate(DateUtils.convert(recordMonth).substring(0, 8) + "01 00:00:00");
		entity.setRecordMonth(recordMonth);
		PrivilegeCouponEntity couponEntity = new PrivilegeCouponEntity();
		couponEntity.setContractCode(entity.getContractCode());
		couponEntity.setRecordMonth(entity.getRecordMonth());
		RowBounds rowBounds = new RowBounds(0, 1);
		List<PrivilegeCouponEntity> couponEntityList = privilegeContractDao
				.queryPrivilegeCouponList(couponEntity, rowBounds);
		// 获得返券ID查询是否有收券人ID
		if (null != couponEntityList && 0 < couponEntityList.size()) {
			couponEntity = couponEntityList.get(0);
			CouponAcceptorVo acceptorVo = new CouponAcceptorVo();
			CouponAcceptorEntity acceptorEntity = new CouponAcceptorEntity();
			if (null != couponEntity && !StringUtils.isEmptyWithBlock(couponEntity.getCouponAcceptorId())) {
				acceptorEntity.setId(couponEntity.getCouponAcceptorId());
				acceptorVo.setCouponAcceptorEntity(acceptorEntity);
				List<CouponAcceptorEntity> list = this.queryListByParamForCouponAcceptorEntity(acceptorVo,
						1, 0);
				if(list != null && list.size() > 0) {
					CouponStateEntity couponStateEntity = new CouponStateEntity();
					couponStateEntity.setContractCode(entity.getContractCode());
					couponStateEntity.setRecordMonth(recordMonth);
					List<CouponStateEntity> couponStateList = privilegeCouponStateDao.findCouponStateEntity(couponStateEntity);
					list.get(0).setConponStateList(couponStateList);
					list.get(0).setContractCode(entity.getContractCode());
					list.get(0).setRecordMonth(entity.getRecordMonth());
					return list.get(0);
				}
			}
		}
		return null;
	}

	@Override
	public List<PrivilegeCouponCheckVo> queryListByParamForCheck(
			PrivilegeCouponCheckVo privilegeCouponCheckVo, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return privilegeContractDao.queryListByParamForCheck(privilegeCouponCheckVo, rowBounds);
	}

	@Override
	public Long queryCountByParamForCheck(PrivilegeCouponCheckVo privilegeCouponCheckVo, int limit,
			int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return privilegeContractDao.queryCountByParamForCheck(privilegeCouponCheckVo, rowBounds);
	}

	/**
	 * @Description: 检查合同数据
	 * @param contract
	 * @return String
	 * @author 286330付于令
	 * @date 2016年1月27日
	 */
	private String checkPrivilegeContract(PrivilegeContractEntity contract) {
		if (contract == null)
			return messageBundle.getMessage("discount.privilegeContract.contract.empty");
		if (StringUtils.isEmptyWithBlock(contract.getCustomerCode()))
//			return "客户编号为空";
			return messageBundle.getMessage("discount.privilegeContract.customerCode.empty");
		if (contract.getContractStartTime() == null)
//			return "合同开始时间为空";
			return messageBundle.getMessage("discount.privilegeContract.contractStartTime.empty");
		if (contract.getContractEndTime() == null)
//			return "合同结束时间为空";
			return messageBundle.getMessage("discount.privilegeContract.contractEndTime.empty");
		if (contract.getContractStartTime().after(contract.getContractEndTime()))
//			return "合同开始结束时间错误";
			return messageBundle.getMessage("discount.privilegeContract.contractStartAndEndTime.error");
		if (contract.getPrivilegeStartTime() == null)
//			return "优惠开始时间为空";
			return messageBundle.getMessage("discount.privilegeContract.privilegeStartTime.empty");
		if (contract.getPrivilegeEndTime() == null)
//			return "优惠结束时间为空";
			return messageBundle.getMessage("discount.privilegeContract.privilegeEndTime.empty");
		if (contract.getPrivilegeStartTime().after(contract.getPrivilegeEndTime()))
//			return "优惠开始结束时间错误";
			return messageBundle.getMessage("discount.privilegeContract.privilegeStartAndEndTime.error");
		if (StringUtils.isEmptyWithBlock(contract.getHasCoupon()))
//			return "是否返券为空";
			return messageBundle.getMessage("discount.privilegeContract.hasCoupon.empty");
		return null;
	}

	/**
	 * @Description: 检查合同数据明细
	 * @param contractDetailList
	 * @return String
	 * @author 286330付于令
	 * @date 2016年1月27日
	 */
	private String checkPrivilegeContractDetail(
			List<PrivilegeContractDetailEntity> contractDetailList) {
		if (contractDetailList == null || contractDetailList.size() == 0)
//			return "合同明细数据为空";
			return messageBundle.getMessage("discount.privilegeContract.contractDetail.empty");
		// 根据发货金额段起 排序
		Collections.sort(contractDetailList, new Comparator<PrivilegeContractDetailEntity>() {
			@Override
			public int compare(PrivilegeContractDetailEntity o1, PrivilegeContractDetailEntity o2) {
				if (o1.getStartAmount() > o2.getStartAmount()) {
					return 1;
				} else if (o1.getStartAmount() < o2.getStartAmount()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		for (int i = 0; i < contractDetailList.size(); i++) {
			PrivilegeContractDetailEntity contractDetail = contractDetailList.get(i);
			if (contractDetail.getStartAmount() == null)
//				return "明细-发货金额段起为空";
				return messageBundle.getMessage("discount.privilegeContract.startAmount.empty");
			if (contractDetail.getEndAmount() == null)
//				return "明细-发货金额段止为空";
				return messageBundle.getMessage("discount.privilegeContract.endAmount.empty");
			if (contractDetail.getStartAmount() > contractDetail.getEndAmount()) {
//				return "明细-发货金额段起段止数据错误";
				return messageBundle.getMessage("discount.privilegeContract.startAndEndAmount.rangeError");
			}
			if (i > 0
					&& contractDetail.getStartAmount() < contractDetailList.get(i - 1)
							.getEndAmount()) {
//				return "明细-发货金额段起段止数据错误";
				return messageBundle.getMessage("discount.privilegeContract.startAndEndAmount.rangeError");
			}
			if (contractDetail.getDdMinFreightDiscount() == null
					|| contractDetail.getDdMinFreightDiscount() < 0) {
//				return "明细-定日达纯运费最低折扣数据错误，取值必须大于0";
				return messageBundle.getMessage("discount.privilegeContract.ddMinFreightDiscount.rangeError");
			}
			if (contractDetail.getDuMinFreightDiscount() == null
					|| contractDetail.getDuMinFreightDiscount() < 0) {
//				return "明细-经济快运纯运费最低折扣数据错误，取值必须大于0";
				return messageBundle.getMessage("discount.privilegeContract.duMinFreightDiscount.rangeError");
			}
			if (contractDetail.getMaxCouponScale() == null
					|| contractDetail.getMaxCouponScale() < 0 || contractDetail.getMaxCouponScale() > 1) {
//				return "明细-最高返券比例数据错误，取值范围0-1";
				return messageBundle.getMessage("discount.privilegeContract.maxCouponScale.rangeError");
			}
		}
		return null;
	}

	@Override
	public String createExcel(List<PrivilegeStateQueryVo> list) {
		String filePath = null;
		List<String> titleNames = new ArrayList<String>();
		titleNames.add("客户编号");
		titleNames.add("客户名称");
		titleNames.add("是否返券");
		titleNames.add("合同开始时间");
		titleNames.add("合同结束时间");
		titleNames.add("越发越惠开始时间");
		titleNames.add("越发越惠结束时间");
		titleNames.add("执行年月");
		titleNames.add("上上月产值");
		titleNames.add("上月产值");
		titleNames.add("前两月平均产值");
		titleNames.add("当月产值");
		titleNames.add("当月定日达折扣");
		titleNames.add("当月经济快运折扣");
		titleNames.add("返券比例");
		titleNames.add("返券值");
		titleNames.add("应收账款合计");
		titleNames.add("返券状态");
		List<String> paramNames = new ArrayList<String>();
		paramNames.add("customerCode");
		paramNames.add("customerName");
		paramNames.add("hasCoupon");
		paramNames.add("contractStartTime");
		paramNames.add("contractEndTime");
		paramNames.add("privilegeStartTime");
		paramNames.add("privilegeEndTime");
		paramNames.add("recordMonth");
		paramNames.add("lastLastMonthAmount");
		paramNames.add("lastMonthAmount");
		paramNames.add("lastTwoMonthsAverageAmount");
		paramNames.add("currentMonthAmount");
		paramNames.add("currentDdDiscount");
		paramNames.add("currentDuDiscount");
		paramNames.add("couponScale");
		paramNames.add("couponAmount");
		paramNames.add("shouldPayAmountSum");
		paramNames.add("couponState");
		try {
			filePath = new ExcelUtil<PrivilegeStateQueryVo>().exportExcel(
					titleNames, paramNames, list);
		} catch (Exception e) {
			filePath = null;
		}
		return filePath;
	}

	@Override
	public Long queryCountByParamForStateQuery(PrivilegeStateQueryVo psqv) {
		return privilegeContractDao.queryCountByParamForStateQuery(psqv);
	}
}

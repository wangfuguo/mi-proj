package com.hoau.miser.module.api.facade.server.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.facade.server.dao.PrivilegeContractDao;
import com.hoau.miser.module.api.facade.server.service.IPrivilegeContractService;
import com.hoau.miser.module.api.facade.shared.domain.PrivilegeContractDetailEntity;
import com.hoau.miser.module.api.facade.shared.domain.PrivilegeContractEntity;
import com.hoau.miser.module.api.facade.shared.vo.PrivilegeContractDetailVo;
import com.hoau.miser.module.api.facade.shared.vo.PrivilegeContractVo;
import com.hoau.miser.module.common.server.service.ISerialNumberService;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.common.shared.define.SerialNumberRuleEnum;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 越发越惠客户合同 
 * ClassName: PrivilegeContractService
 * @author 286330付于令
 * @date 2016年1月13日
 * @version V1.0
 */
@Service
public class PrivilegeContractService implements IPrivilegeContractService {
	
	@Resource
	private PrivilegeContractDao privilegeContractDao;
	@Resource
	private ISerialNumberService serialNumberService;

	/**
	 * @Description: 添加越发越惠客户合同
	 * @param contractEntity
	 * @return void
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	@Override
	public String addPrivilegeContract(PrivilegeContractVo contractVo) {
		// 校验合同数据
		String result = checkContractVo(contractVo);
		if(result != null) return result;
		PrivilegeContractEntity contractEntity = convertVo2Entity(contractVo);
		result = checkPrivilegeContract(contractEntity);
		if(result != null) return result;
		// 删除老数据 -- 根据客户CODE
		List<PrivilegeContractEntity> existContractList = privilegeContractDao
				.findPrivilegeContract(contractEntity);
		if(existContractList != null && existContractList.size() > 0) {
			PrivilegeContractDetailEntity delDetailEntity = new PrivilegeContractDetailEntity();
			delDetailEntity.setModifyDate(new Date());
			delDetailEntity.setModifyUser(contractEntity.getCreateUser());
			for(PrivilegeContractEntity existContract: existContractList) {
				// 先删除明细
				delDetailEntity.setCustomerContractId(existContract.getId());
				privilegeContractDao.delPrivilegeContractDetail(delDetailEntity);
				// 再删除合同
				existContract.setModifyDate(new Date());
				existContract.setModifyUser(contractEntity.getCreateUser());
				privilegeContractDao.delPrivilegeContract(existContract);
			}
		}
		// 新增数据
		List<PrivilegeContractDetailEntity> contractDetailEntityList = contractEntity.getDetail();
		beforeAddPrivilegeContractDispose(contractEntity);
		String contractCode = serialNumberService.generateSerialNumber(
				SerialNumberRuleEnum.PRIVILEGE_CONTRACT_CODE);
		contractEntity.setContractCode(contractCode);
		privilegeContractDao.addPrivilegeContract(contractEntity);
		for (PrivilegeContractDetailEntity contractDetailEntity : contractDetailEntityList) {
			beforAddPrivilegeContractDetailDispose(contractEntity, contractDetailEntity);
			contractDetailEntity.setCustomerContractId(contractEntity.getId());
			privilegeContractDao.addPrivilegeContractDetail(contractDetailEntity);
		}
		return null;
	}
	/**
	 * @Description: 实体转换
	 * @param contractVo
	 * @return PrivilegeContractEntity 
	 * @author 286330付于令
	 * @date 2016年3月30日
	 */
	private PrivilegeContractEntity convertVo2Entity(PrivilegeContractVo contractVo) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			PrivilegeContractEntity entity = new PrivilegeContractEntity();
			entity.setCustomerCode(contractVo.getCustomerNum());
			entity.setContractStartTime(format.parse(contractVo.getContractStartDate()));
			entity.setContractEndTime(format.parse(contractVo.getContractEndDate()));
			entity.setPrivilegeStartTime(format.parse(contractVo.getYhksdate()));
			entity.setPrivilegeEndTime(format.parse(contractVo.getYhjsdate()));
			String hasCoupon = contractVo.getWhetherCreditCustomers();
			if("2".equals(hasCoupon)) { // 2: 返券，0: 折扣
				entity.setHasCoupon(MiserConstants.YES);
			} else {
				entity.setHasCoupon(MiserConstants.NO);
			}
			entity.setCreateUser(contractVo.getCreateUser());
			String commitmentProduct = contractVo.getCommitmentProduct();
			if(!StringUtils.isEmptyWithBlock(commitmentProduct)) {
				entity.setCommitmentProduct(Double.valueOf(commitmentProduct));				
			}
			List<PrivilegeContractDetailEntity> entityList = new ArrayList<PrivilegeContractDetailEntity>();
			PrivilegeContractDetailEntity detailEntity = null;
			
			List<PrivilegeContractDetailVo> detailVoList = contractVo.getDetail();
			for(PrivilegeContractDetailVo detailVo: detailVoList) {
				detailEntity = new PrivilegeContractDetailEntity();
				detailEntity.setStartAmount(Double.valueOf(detailVo.getFhjedq()));
				detailEntity.setEndAmount(Double.valueOf(detailVo.getFhjedz()));
				detailEntity.setDdMinFreightDiscount(Double.valueOf(detailVo.getDrdyfzdzk()));
				detailEntity.setDuMinFreightDiscount(Double.valueOf(detailVo.getJjkyyfzdzk()));
				if(detailVo.getZgfqbl() != null) {
					detailEntity.setMaxCouponScale(Double.valueOf(detailVo.getZgfqbl()));					
				} else {
					detailEntity.setMaxCouponScale(0.0);
				}
				detailEntity.setRemark(detailVo.getBz());
				entityList.add(detailEntity);
			}
			entity.setDetail(entityList);
			return entity;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * @Description: 验证参数
	 * @param contractVo
	 * @return String 
	 * @author 286330付于令
	 * @date 2016年3月30日
	 */
	private String checkContractVo(PrivilegeContractVo contractVo) {
		if(contractVo == null) return "合同数据为空";
		if(StringUtils.isEmptyWithBlock(contractVo.getCustomerNum())) return "客户编号为空";
		if(StringUtils.isEmptyWithBlock(contractVo.getContractStartDate())) return "合同开始时间为空";
		if(StringUtils.isEmptyWithBlock(contractVo.getContractEndDate())) return "合同结束时间为空";
		if(StringUtils.isEmptyWithBlock(contractVo.getYhksdate())) return "优惠开始时间为空";
		if(StringUtils.isEmptyWithBlock(contractVo.getYhjsdate())) return "优惠结束时间为空";
		if(StringUtils.isEmptyWithBlock(contractVo.getWhetherCreditCustomers())) return "是否返券为空";
		if(StringUtils.isEmptyWithBlock(contractVo.getCreateUser())) return "创建人为空";
		List<PrivilegeContractDetailVo> contractVoDetail = contractVo.getDetail();
		return checkContractDetailVo(contractVoDetail);
	}
	/**
	 * @Description: 验证参数
	 * @param contractVo
	 * @return String 
	 * @author 286330付于令
	 * @date 2016年3月30日
	 */
	private String checkContractDetailVo(List<PrivilegeContractDetailVo> contractVoDetail) {
		for(int i=0; i<contractVoDetail.size(); i++) {
			PrivilegeContractDetailVo contractDetail = contractVoDetail.get(i);
			if(contractDetail == null) return "合同明细数据为空";
			if(StringUtils.isEmptyWithBlock(contractDetail.getFhjedq())) return "明细-发货金额段起为空";
			if(StringUtils.isEmptyWithBlock(contractDetail.getFhjedz())) return "明细-发货金额段止为空";
			if(StringUtils.isEmptyWithBlock(contractDetail.getDrdyfzdzk())) return "明细-定日达纯运费最低折扣为空";
			if(StringUtils.isEmptyWithBlock(contractDetail.getJjkyyfzdzk())) return "明细-经济快运纯运费最低折扣为空";
		}
		return null;
	}

	/**
	 * @Description: 检查合同数据
	 * @param contractEntity
	 * @return String 
	 * @author 286330付于令
	 * @date 2016年1月27日
	 */
	private String checkPrivilegeContract(PrivilegeContractEntity contract) {
		if(contract == null) return "合同数据为空";
		if(contract.getContractStartTime().after(contract.getContractEndTime())) return "合同开始结束时间错误";
		if(contract.getPrivilegeStartTime().after(contract.getPrivilegeEndTime())) return "优惠开始结束时间错误";
		List<PrivilegeContractDetailEntity> contractDetailList = contract.getDetail();
		return checkPrivilegeContractDetail(contractDetailList);
	}
	/**
	 * @Description: 检查合同数据明细
	 * @param contractDetailList
	 * @return String 
	 * @author 286330付于令
	 * @date 2016年1月27日
	 */
	private String checkPrivilegeContractDetail(List<PrivilegeContractDetailEntity> contractDetailList) {
		if(contractDetailList == null || contractDetailList.size() == 0) return "合同明细数据为空";
		// 根据发货金额段起 排序
		Collections.sort(contractDetailList, new Comparator<PrivilegeContractDetailEntity>() {
			@Override
			public int compare(PrivilegeContractDetailEntity o1, PrivilegeContractDetailEntity o2) {
				if(o1.getStartAmount() > o2.getStartAmount()) {
					return 1;
				} else if(o1.getStartAmount() < o2.getStartAmount()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		for(int i=0; i<contractDetailList.size(); i++) {
			PrivilegeContractDetailEntity contractDetail = contractDetailList.get(i);
			if(contractDetail.getStartAmount() > contractDetail.getEndAmount()) {
				return "明细-发货金额段起段止数据错误";
			}
			if(i > 0 && contractDetail.getStartAmount() < contractDetailList.get(i-1).getEndAmount()) {
				return "明细-发货金额段起段止数据错误";
			}
			if(contractDetail.getDdMinFreightDiscount() < 0) {
				return "明细-定日达纯运费最低折扣数据错误，取值必须大于0";
			}
			if(contractDetail.getDuMinFreightDiscount() < 0) { 
				return "明细-经济快运纯运费最低折扣数据错误，取值必须大于0";
			}
			if(contractDetail.getMaxCouponScale() != null && (contractDetail.getMaxCouponScale() > 1
					|| contractDetail.getMaxCouponScale() < 0)) {
				return "明细-最高返券比例数据错误，取值范围0-1";
			}
		}
		return null;
	}
	
	/**
	 * @Description: 新增越发越惠客户合同赋初始值
	 * @param pse
	 * @return void
	 * @author 付于令
	 * @date 2016年01月13日
	 */
	private void beforeAddPrivilegeContractDispose(PrivilegeContractEntity contract) {
		Date dt = new Date();
		contract.setId(UUIDUtil.getUUID());
		contract.setCreateDate(dt);
		contract.setModifyDate(dt);
		if(StringUtils.isEmptyWithBlock(contract.getModifyUser())) {
			contract.setModifyUser(contract.getCreateUser());
		}
		contract.setActive(MiserConstants.YES);
	}
	
	/**
	 * @Description: 越发越惠客户合同明细新增赋值
	 * @param pse
	 * @return void
	 * @author 付于令
	 * @date 2016年01月13日
	 */
	private void beforAddPrivilegeContractDetailDispose(PrivilegeContractEntity contract, 
			PrivilegeContractDetailEntity detail) {
		Date dt = new Date();
		detail.setId(UUIDUtil.getUUID());
		detail.setCreateDate(dt);
		detail.setModifyDate(dt);
		detail.setActive(MiserConstants.YES);
		if(StringUtils.isEmptyWithBlock(detail.getCreateUser())) {
			detail.setCreateUser(contract.getCreateUser());
		}
		if(StringUtils.isEmptyWithBlock(detail.getModifyUser())) {
			detail.setModifyUser(detail.getCreateUser());
		}
		// 数据来源设置，默认是OA系统新增
		if(StringUtil.isEmpty(detail.getDataOrign())) {
			detail.setDataOrign(DataOrignEnum.OA.getCode());
		}
	}
	
}

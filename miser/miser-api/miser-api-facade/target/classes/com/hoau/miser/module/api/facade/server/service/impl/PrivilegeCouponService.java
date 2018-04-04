package com.hoau.miser.module.api.facade.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.miser.module.api.facade.server.dao.PrivilegeCouponDao;
import com.hoau.miser.module.api.facade.server.dao.PrivilegeCouponStateDao;
import com.hoau.miser.module.api.facade.server.service.IPrivilegeCouponService;
import com.hoau.miser.module.api.facade.shared.domain.CouponAcceptorEntity;
import com.hoau.miser.module.api.facade.shared.domain.CouponStateEntity;
import com.hoau.miser.module.api.facade.shared.domain.PrivilegeCouponEntity;
import com.hoau.miser.module.api.facade.shared.vo.CouponAcceptorVo;
import com.hoau.miser.module.api.facade.shared.vo.CouponApplyVo;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.DateUtils;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 越发越惠返券Service ClassName: IPrivilegeCouponService
 * 
 * @author 286330付于令
 * @date 2016年4月8日
 * @version V1.0
 */
@Service
public class PrivilegeCouponService implements IPrivilegeCouponService {
	@Resource
	private PrivilegeCouponDao privilegeCouponDao;
	@Resource
	private PrivilegeCouponStateDao privilegeCouponStateDao;

	/**
	 * @Description: 返券申请
	 * @param couponApplyVo
	 * @return boolean
	 * @author 286330付于令
	 * @date 2016年4月8日
	 */
	@Override
	public void apply(CouponApplyVo couponApplyVo) {
		// 获得返券数据
		RowBounds rowBounds = new RowBounds(0, 1);
		List<PrivilegeCouponEntity> couponList = privilegeCouponDao.queryCouponList(couponApplyVo,
				rowBounds);
		if (null != couponList && 0 < couponList.size()) {
			PrivilegeCouponEntity couponEntity = couponList.get(0);
			// 收券人: true 存在，false 不存在
			if (!StringUtils.isEmptyWithBlock(couponEntity.getCouponAcceptorId())) {
				CouponAcceptorEntity delAcceptor = new CouponAcceptorEntity();
				delAcceptor.setId(couponEntity.getCouponAcceptorId());
				delAcceptor.setActive(MiserConstants.NO);
				delAcceptor.setModifyDate(new Date());
				delAcceptor.setModifyUser("SYSTEM");
				privilegeCouponDao.delCouponAcceptor(delAcceptor);
			}
			// 保存返券人
			CouponAcceptorEntity addAcceptor = getCouponAcceptor(couponApplyVo.getAcceptor());
			privilegeCouponDao.addCouponAcceptor(addAcceptor);
			// 删除返券 旧数据
			PrivilegeCouponEntity delCoupon = new PrivilegeCouponEntity();
			delCoupon.setId(couponEntity.getId());
			delCoupon.setActive(MiserConstants.NO);
			delCoupon.setModifyDate(new Date());
			delCoupon.setModifyUser("SYSTEM");
			privilegeCouponDao.delPrivilegeCoupon(delCoupon);
			// 新增返券 数据
			couponEntity.setId(UUIDUtil.getUUID());
			couponEntity.setCouponAcceptorId(addAcceptor.getId());
			couponEntity.setCouponState("PENDING_CHECK");
			couponEntity.setCouponRemark("返券申请");
			couponEntity.setCouponTime(new Date());
			privilegeCouponDao.addPrivilegeCoupon(couponEntity);
			//  保存返券状态记录
			CouponStateEntity couponStateEntity = new CouponStateEntity();
			couponStateEntity.setContractCode(couponEntity.getContractCode());
			Date recordMonth = DateUtils.convert(couponApplyVo.getRecordMonth());
			couponStateEntity.setRecordMonth(recordMonth);
			couponStateEntity.setExecuteState("PENDING_CHECK");
			couponStateEntity.setExecuteTime(new Date());
			couponStateEntity.setExecuteUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			initCouponStateEntity(couponStateEntity);
			privilegeCouponStateDao.saveCouponStateEntity(couponStateEntity);
		} else {
			throw new BusinessException("返券数据不存在");
		}
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

	/**
	 * @Description: 复制收券人的VO实体属性到Entity
	 * @param acceptor
	 * @return CouponAcceptorEntity 
	 * @author 286330付于令
	 * @date 2016年4月8日
	 */
	private CouponAcceptorEntity getCouponAcceptor(CouponAcceptorVo acceptor) {
		CouponAcceptorEntity acceptorEntity = new CouponAcceptorEntity();
		Date dt = new Date();
		acceptorEntity.setId(UUIDUtil.getUUID());
		acceptorEntity.setCreateDate(dt);
		acceptorEntity.setCreateUser("SYSTEM");
		acceptorEntity.setModifyDate(dt);
		acceptorEntity.setModifyUser("SYSTEM");
		acceptorEntity.setActive(MiserConstants.YES);
		
		acceptorEntity.setAcceptorName(acceptor.getAcceptorName());
		acceptorEntity.setAcceptorPhone(acceptor.getAcceptorPhone());
		acceptorEntity.setBankName(acceptor.getBankName());
		acceptorEntity.setCreditCardNo(acceptor.getCreditCardNo());
		acceptorEntity.setIdentityCardNo(acceptor.getIdentityCardNo());
		acceptorEntity.setAlipayAccount(acceptor.getAlipayAccount());
		acceptorEntity.setRelationshipWithCustomer(acceptor.getRelationshipWithCustomer());
		return acceptorEntity;
	}

}

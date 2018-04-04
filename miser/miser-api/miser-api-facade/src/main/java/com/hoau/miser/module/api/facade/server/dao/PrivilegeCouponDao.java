package com.hoau.miser.module.api.facade.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.facade.shared.domain.CouponAcceptorEntity;
import com.hoau.miser.module.api.facade.shared.domain.PrivilegeCouponEntity;
import com.hoau.miser.module.api.facade.shared.vo.CouponApplyVo;

@Repository
public interface PrivilegeCouponDao {

	List<PrivilegeCouponEntity> queryCouponList(CouponApplyVo couponApplyVo, RowBounds rowBounds);

	void delCouponAcceptor(CouponAcceptorEntity delAcceptor);

	void addCouponAcceptor(CouponAcceptorEntity addAcceptor);

	void addPrivilegeCoupon(PrivilegeCouponEntity couponEntity);

	void delPrivilegeCoupon(PrivilegeCouponEntity delCoupon);

}

package com.hoau.miser.module.api.facade.server.service;

import com.hoau.miser.module.api.facade.shared.vo.CouponApplyVo;

/**
 * 越发越惠返券Service
 * ClassName: IPrivilegeCouponService 
 * @author 286330付于令
 * @date 2016年4月8日
 * @version V1.0
 */
public interface IPrivilegeCouponService {

	/**
	 * @Description: 返券申请
	 * @param couponApplyVo
	 * @return boolean 
	 * @author 286330付于令
	 * @date 2016年4月8日
	 */
	void apply(CouponApplyVo couponApplyVo);

}

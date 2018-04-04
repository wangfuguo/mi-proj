package com.hoau.miser.module.api.facade.server.service;

import com.hoau.miser.module.api.facade.shared.vo.DiscountPrivilegeVo;

/**
 * 越发越惠Service
 * ClassName: IDiscountPrivilegeService 
 * @author 286330付于令
 * @date 2016年1月25日
 * @version V1.0
 */
public interface IDiscountPrivilegeService {
	/**
	 * @Description: 查询越发越惠基础数据List
	 * @param discountPrivilegeVo
	 * @return List<List<DiscountPrivilegeVo>> 
	 * @author 286330付于令
	 * @date 2016年1月25日
	 */
	public Object queryListByParam(DiscountPrivilegeVo discountPrivilegeVo);
}

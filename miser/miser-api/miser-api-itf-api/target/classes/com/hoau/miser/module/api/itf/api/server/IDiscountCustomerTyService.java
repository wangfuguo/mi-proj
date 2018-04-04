/**
* @Title: IDiscountCustomerService.java
* @Package com.hoau.miser.module.biz.discount.server.service
* @Description: TODO(用一句话描述该文件做什么)
* @author 陈启勇
* @date 2016年1月5日 下午3:25:35
* @version V1.0
*/
package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCustomerTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

/**
 * 客户折扣管理service接口
 * ClassName: IDiscountCustomerService
 * @author 廖文强
 * @date 2016年06月12日
 * @version V1.0
 */
public interface IDiscountCustomerTyService {

	/**
	 * 客户折扣管理集合信息(客户编号，运输类型。。。)
	 * @param baseTyParam
	 * @return DiscountCustomerEntity
	 * @Author 廖文强
	 * @Time 2016年06月12日
	 */
	public DiscountCustomerTyEntity queryDiscountCustomerByParam(PriceQueryParam baseTyParam);



}

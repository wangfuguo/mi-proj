/**
* @Title: IDiscountCorpService.java
* @Package com.hoau.miser.module.biz.discount.server.service
* @Description: TODO(用一句话描述该文件做什么)
* @author 陈启勇
* @date 2016年1月5日 下午3:25:00
* @version V1.0
*/
package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCorpTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

import java.util.List;


/**
 * 网点折扣管理service接口
 * ClassName: IDiscountCorpService
 * @author 廖文强
 * @date 2016年06月12日
 * @version V1.0
 */
public interface IDiscountCorpTyService {

	/**
	 * 网点折扣管理集合信息（门店组织编号，运输类型）
	 * @param baseTyParam
	 * @return DiscountCorpTyEntity
	 * @Author 廖文强
	 * @Time 2015年1月6日
	 */
	public DiscountCorpTyEntity queryDisCorpByParam(PriceQueryParam baseTyParam);

}

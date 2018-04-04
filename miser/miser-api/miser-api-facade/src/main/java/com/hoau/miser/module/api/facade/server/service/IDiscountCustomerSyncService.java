/**   
* @Title: IDiscountCustomerService.java 
* @Package com.hoau.miser.module.biz.discount.server.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 陈启勇 
* @date 2016年1月5日 下午3:25:35 
* @version V1.0   
*/
package com.hoau.miser.module.api.facade.server.service;

import java.util.List;

import com.hoau.miser.module.api.facade.shared.domain.DiscountCustomerEntity;
import com.hoau.miser.module.api.facade.shared.vo.BaseResponseVo;
import com.hoau.miser.module.api.facade.shared.vo.DiscountCustomerVo;


/**
 * 客户折扣管理service接口
 * ClassName: IDiscountCustomerService 
 * @author 陈启勇
 * @date 2016年1月13日
 * @version V1.0   
 */
public interface IDiscountCustomerSyncService {
	
	public Integer addDiscountCustomer(DiscountCustomerEntity beanv);

	List<DiscountCustomerEntity> queryListByParam(DiscountCustomerVo beanv,int limit, int start);
	
	public BaseResponseVo<Object> DiscountCustomerSync(String jsonString);
	
}

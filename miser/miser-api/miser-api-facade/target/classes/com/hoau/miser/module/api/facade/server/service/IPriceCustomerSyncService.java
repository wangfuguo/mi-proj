/**   
 * @Title: IPriceCustomerService.java 
 * @Package com.hoau.miser.module.biz.pricecard.server.service 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月5日 下午1:41:34 
 * @version V1.0   
 */
package com.hoau.miser.module.api.facade.server.service;

import java.util.List;

import com.hoau.miser.module.api.facade.shared.domain.SyncCustomerPrice;

public interface IPriceCustomerSyncService {

	public String syncCustomerPrice(List<SyncCustomerPrice> scpBeanList);
}

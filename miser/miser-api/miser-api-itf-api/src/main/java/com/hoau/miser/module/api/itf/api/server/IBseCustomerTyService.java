/**
 * @Title: IBseCustomerService.java
 * @Package com.hoau.miser.module.biz.pricecard.server.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author dengyin
 * @date 2016年1月6日 下午5:53:41
 * @version V1.0
 */
package com.hoau.miser.module.api.itf.api.server;



import com.hoau.miser.module.api.itf.api.shared.domain.BseCustomerTyEntity;

import java.util.List;

/**
 * ClassName: IBseCustomerService
 *
 * @author dengyin
 * @date 2016年1月6日
 * @version V1.0
 */
public interface IBseCustomerTyService {

	BseCustomerTyEntity queryBseCustomerByCustNo(String custNo);
}

/**   
 * @Title: IBseCustomerService.java 
 * @Package com.hoau.miser.module.biz.pricecard.server.service 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月6日 下午5:53:41 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.api.server.service;

import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.BseCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.BseCustomerVo;

/**
 * ClassName: IBseCustomerService
 * 
 * @author dengyin
 * @date 2016年1月6日
 * @version V1.0
 */
public interface IBseCustomerService {
	BseCustomerEntity selectByPrimaryKey(String id);

	List<BseCustomerEntity> selectCustomerAllList(BseCustomerVo bseCustomerVo,int limit, int start);
	
	List<BseCustomerEntity> queryBseCustomerByParam(BseCustomerVo bseCustomerVo,int limit,int start);
	Long queryCountByParam(BseCustomerVo bseCustomerVo);
	
	
	void updateFromOaPmsInterface(BseCustomerEntity bseCustomerEntity);
}

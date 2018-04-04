package com.hoau.miser.module.biz.job.server.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DiscountCustomerEntity;

/**
 * ClassName: CustomerDisCountAndSurchargeDao 
 * @Description: 客户附加费和折扣DAO
 * @author 275636
 * @date 2016年1月25日
 * @version V1.0   
 */
@Repository
public interface CustomerDisCountAndSurchargeDao {

	/**
	 * @Description: 获取客户折扣附加费集合
	 * @author 275636
	 * @date 2016年1月25日
	 */
	List<DiscountCustomerEntity> queryNeedDiscountCustomerData(Map<String, Object> newMapParam);
	/**
	 * @Description: 获取客户折扣运费集合
	 * @author 275636
	 * @date 2016年1月25日
	 */
	List<DiscountCustomerEntity> queryNeedDiscountCustomerFreightData(Map<String, Object> newMapParam);


}

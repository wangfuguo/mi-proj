package com.hoau.miser.module.biz.job.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.CustomerPriceSendEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;


/**
 * 客户价格
 * ClassName: CustomerPriceSendDao 
 * @author 廖文强
 * @date 2016年1月26日
 * @version V1.0
 */
@Repository
public interface CustomerPriceSendDao {
	
	
	//新的方法
	
	/**
	 * 
	 * @Description: 查询客户价格的数据
	 * @param @param param
	 * @param @return   
	 * @return List<Map<String,Object>> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月8日
	 */
	public List<Map<String, Object>> queryCusomerPriceData(Map<String,Object> param);
	
	/**
	 * 
	 * @Description: 客户价格失败信息去重
	 * @param @param queryEnity   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月8日
	 */
	public void failureInfoUniqByCustPrice(DataSendJobFailureEntity queryEnity);
	
	/**
	 * 
	 * @Description: 拿到失败客户价格信息
	 * @param @param param
	 * @param @return   
	 * @return List<Map<String,Object>> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月8日
	 */
	public List<Map<String, Object>> queryFailCustPriceData(Map<String,Object> param);
	
	
	
	/**
	 * 
	 * @Description: 失败折扣信息去重
	 * @param @param queryEnity   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月8日
	 */
	public void failureInfoUniqByCustDiscount(DataSendJobFailureEntity queryEnity);
	/**
	 * 
	 * @Description: 查询失败客户折扣信息
	 * @param @param param
	 * @param @return   
	 * @return List<Map<String,Object>> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月8日
	 */
	public List<Map<String, Object>> queryFailCustDiscountData(Map<String,Object> param);
	
	/**
	 * 客户价格城市映射
	 * @Description: TODO描述该方法是做什么的
	 * @param @param param
	 * @param @return   
	 * @return List<Map<String,Object>> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月13日
	 */
	public List<Map<String,Object>> queryCustomerPriceCityCustBycustomerNos(Map<String,Object> param);
	/**
	 * 标准价格城市映射
	 * @Description: TODO描述该方法是做什么的
	 * @param @param param
	 * @param @return   
	 * @return List<Map<String,Object>> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月13日
	 */
	public List<Map<String,Object>> queryCustomerPriceCitysSandardBycustomerNos(Map<String,Object> param);
	/**
	 * 获得本次符合条件的客户折扣信息
	 * @Description: TODO描述该方法是做什么的
	 * @param @param param
	 * @param @return   
	 * @return List<Map<String,Object>> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年3月1日
	 */
	public List<Map<String, Object>> queryNeedDiscountCustomerData(Map<String,Object> param);
	/**
	 * 
	 * @Description: 查询出要发送的价格信息
	 * @param @param param
	 * @param @return   
	 * @return List<CustomerPriceSendEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年2月23日
	 */
	public ArrayList<CustomerPriceSendEntity> querySendDataByParam(Map<String,Object> param);
	
	
}

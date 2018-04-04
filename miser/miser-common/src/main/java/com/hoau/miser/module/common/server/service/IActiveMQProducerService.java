package com.hoau.miser.module.common.server.service;

import java.io.Serializable;
import java.util.ArrayList;

import javax.jms.Destination;

/**
 * ClassName: IActiveMQProducerService
 * @Description:  ActiveMq发送服务
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月15日
 * @version V1.0
 */
public interface IActiveMQProducerService {

	/**
	 * @Description: 发送字符串消息
	 * @param destination
	 *            发送消息队列目标
	 * @param message
	 *            需要发送的消息
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月15日
	 */
	public void postStringToQueue(Destination destination, final String message);

	/**
	 * @Description: 发送序列化对象
	 * @param destination
	 *            发送的目标
	 * @param object
	 *            需要发送的对象
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月15日
	 */
	public void postObjectToQueue(Destination destination,
			final Serializable object);

	/**
	 * @Description: 发送基础数据
	 * @param basicInfo
	 *            基础数据
	 * @return void
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月24日
	 */
	public void postBasicInfoDataToQueue(final ArrayList basicInfo);

	/**
	 * @Description: 发送优惠分段数据
	 * @param discountSectionData
	 *            优惠分段数据
	 * @return void
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月24日
	 */
	public void postDiscountSectionDataToQueue(final ArrayList discountSectionData);

	/**
	 * @Description: 发送折扣数据
	 * @param discountData
	 *            折扣数据
	 * @return void
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月24日
	 */
	public void postDiscountDataToQueue(final ArrayList discountData);

	/**
	 * @Description: 发送附加费数据
	 * @param extraData
	 *            附加费数据
	 * @return void
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月24日
	 */
	public void postExtrtaDataToQueue(final ArrayList extraData);

	/**
	 * @Description: 发送价格数据
	 * @param priceCardData
	 *            价格数据
	 * @return void
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月24日
	 */
	public void postPriceCardDataToQueue(final ArrayList priceCardData);

	/**
	 * 
	 * @Description: TODO发送包装费
	 * @param @param pricePackgeFeeData
	 * @return void
	 * @throws
	 * @author 李玮琰
	 * @date 2016年2月29日
	 */
	public void postPricePackageFeeDataToQueue(final ArrayList pricePackgeFeeData);

	/**
	 * 
	 * @Description: TODO发送价格包装费
	 * @param @param pricePackgeFeePcData
	 * @return void
	 * @throws
	 * @author 李玮琰
	 * @date 2016年2月29日
	 */
	public void postPricePackageFeePcDataToQueue(final ArrayList pricePackgeFeePcData);

	/**
	 * 
	 * @Description: 发送偏线系数配置
	 * @param @param outerBranchPriceData
	 * @return void
	 * @throws
	 * @author 赵金义
	 * @date 2016年3月18日
	 */
	public void postOuterBranchPriceDataToQueue(final ArrayList outerBranchPriceData);

	/**
	 * 发送价格城市映射关系数据
	 * 
	 * @Description: TODO描述该方法是做什么的
	 * @param @param priceCityMappingData
	 * @return void
	 * @throws
	 * @author 廖文强
	 * @date 2016年2月28日
	 */
	public void postPriceCityMappingToQueue(final ArrayList priceCityMappingData);

	/**
	 * @Description: send PriceDeliveryFee data
	 * @throws Exception
	 * @return void
	 * @author zouyu
	 * @date 2016年2月26日
	 */
	public void postPriceDeliveryFeeDataToQueue(final ArrayList data);

	/**
	 * @Description: send PricePickUpFee data
	 * @throws Exception
	 * @return void
	 * @author zouyu
	 * @date 2016年2月26日
	 */
	public void postPricePickUpFeeDataToQueue(final ArrayList data);

	/**
	 * @Description: send ExtrafeeAddValueFee data
	 * @throws Exception
	 * @return void
	 * @author zouyu
	 * @date 2016年2月26日
	 */
	public void postExtrafeeAddValueFeeDataToQueue(final ArrayList data);

	/**
	 * @Description: send ExtrafeeAddValueFeeItems data
	 * @throws Exception
	 * @return void
	 * @author zouyu
	 * @date 2016年2月26日
	 */
	public void postExtrafeeAddValueFeeItemsDataToQueue(final ArrayList data);

	/**
	 * @Description: send priceEvent data
	 * @throws Exception
	 * @return void
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月26日
     */
	public void postPriceEventDataToQueue(final ArrayList data);

	/**
	 * @Description: 发送客户价格城市映射关系
	 * @throws Exception
	 * @return void
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月27日
     */
	public void postCustomerPriceCityMappingDataToQueue(final ArrayList data);

	/**
	 * @Description: 发送送货城市类型映射关系
	 * @throws Exception
	 * @return void
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月27日
	 */
	public void postDeliveryCityLevelMappingDataToQueue(final ArrayList data);

}

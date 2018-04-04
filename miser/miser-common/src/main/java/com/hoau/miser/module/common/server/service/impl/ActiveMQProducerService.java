package com.hoau.miser.module.common.server.service.impl;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.hoau.miser.module.common.server.service.IActiveMQProducerService;

/**
 * ClassName: ActiveMQProducerService 
 * @Description: 发送mq消息服务 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月15日
 * @version V1.0   
 */
@Component
public class ActiveMQProducerService implements IActiveMQProducerService {

	@Resource
	private JmsTemplate jmsTemplate;

	@Resource
	private Destination basicInfoTopicDestination;

	@Resource
	private Destination discountSectionTopicDestination;

	@Resource
	private Destination discountTopicDestination;

	@Resource
	private Destination extraTopicDestination;

	@Resource
	private Destination pricecardTopicDestination;
	
	@Resource
	private Destination priceCityMappingTopicDestination;
	
	@Resource
	private Destination priceDeliveryFeeTopicDestination;
	
	@Resource
	private Destination pricePickUpFeeTopicDestination;
	
	@Resource
	private Destination extrafeeAddValueFeeTopicDestination;
	
	@Resource
	private Destination pricePackageFeeTopicDestination;
	
	@Resource
	private Destination pricePackageFeePcTopicDestination;
	
	@Resource
	private Destination extrafeeAddValueFeeItemsTopicDestination;

	@Resource
	private Destination outerBranchPriceTopicDestination;

	@Resource
	private Destination priceEventTopicDestination;
	
	@Resource
	private Destination custPriceCityMappingDestination;

	@Resource
	private Destination deliveryCityLevelMappingDestination;

	@Override
	public void postStringToQueue(Destination destination, final String message) {
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}

	@Override
	public void postObjectToQueue(Destination destination, final Serializable object) {
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(object);
			}
		});
	}

	@Override
	public void postBasicInfoDataToQueue(ArrayList basicInfoData) {
		postObjectToQueue(basicInfoTopicDestination, basicInfoData);
	}

	@Override
	public void postDiscountSectionDataToQueue(ArrayList discountSectionData) {
		postObjectToQueue(discountSectionTopicDestination, discountSectionData);
	}

	@Override
	public void postDiscountDataToQueue(ArrayList discountData) {
		postObjectToQueue(discountTopicDestination, discountData);
	}

	@Override
	public void postExtrtaDataToQueue(ArrayList extraData) {
		postObjectToQueue(extraTopicDestination, extraData);
	}

	@Override
	public void postPriceCardDataToQueue(ArrayList priceCardData) {
		postObjectToQueue(pricecardTopicDestination, priceCardData);
	}

	public void postPriceCityMappingToQueue(ArrayList priceCityMappingData) {
		postObjectToQueue(priceCityMappingTopicDestination,priceCityMappingData);
	}
	
	@Override
	public void postPriceDeliveryFeeDataToQueue(ArrayList data) {
		postObjectToQueue(priceDeliveryFeeTopicDestination, data);
	}
	
	@Override
	public void postPricePickUpFeeDataToQueue(ArrayList data) {
		postObjectToQueue(pricePickUpFeeTopicDestination, data);
	}
	
	@Override
	public void postPricePackageFeeDataToQueue(ArrayList pricePackgeFeeData) {
		postObjectToQueue(pricePackageFeeTopicDestination, pricePackgeFeeData);
	}
	
	@Override
	public void postPricePackageFeePcDataToQueue(ArrayList pricePackgeFeePcData) {
		postObjectToQueue(pricePackageFeePcTopicDestination, pricePackgeFeePcData);
	}
	
	@Override
	public void postOuterBranchPriceDataToQueue(ArrayList outerBranchPriceData) {
		postObjectToQueue(outerBranchPriceTopicDestination, outerBranchPriceData);
	}

	@Override
	public void postExtrafeeAddValueFeeDataToQueue(ArrayList data) {
		postObjectToQueue(extrafeeAddValueFeeTopicDestination, data);
	}
	
	@Override
	public void postExtrafeeAddValueFeeItemsDataToQueue(ArrayList data) {
		postObjectToQueue(extrafeeAddValueFeeItemsTopicDestination, data);
	}

	/**
	 * @param data
	 * @return void
	 * @throws Exception
	 * @Description: 发送客户价格城市映射关系
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月27日
	 */
	@Override
	public void postCustomerPriceCityMappingDataToQueue(ArrayList data) {
		postObjectToQueue(custPriceCityMappingDestination, data);
	}

	@Override
	public void postPriceEventDataToQueue(ArrayList data) {
		postObjectToQueue(priceEventTopicDestination, data);
	}

	/**
	 * @param data
	 * @return void
	 * @throws Exception
	 * @Description: 发送送货城市类型映射关系
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月27日
	 */
	@Override
	public void postDeliveryCityLevelMappingDataToQueue(ArrayList data) {
		postObjectToQueue(deliveryCityLevelMappingDestination, data);
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Destination getBasicInfoTopicDestination() {
		return basicInfoTopicDestination;
	}

	public void setBasicInfoTopicDestination(Destination basicInfoTopicDestination) {
		this.basicInfoTopicDestination = basicInfoTopicDestination;
	}

	public Destination getDiscountSectionTopicDestination() {
		return discountSectionTopicDestination;
	}

	public void setDiscountSectionTopicDestination(Destination discountSectionTopicDestination) {
		this.discountSectionTopicDestination = discountSectionTopicDestination;
	}

	public Destination getDiscountTopicDestination() {
		return discountTopicDestination;
	}

	public void setDiscountTopicDestination(Destination discountTopicDestination) {
		this.discountTopicDestination = discountTopicDestination;
	}

	public Destination getExtraTopicDestination() {
		return extraTopicDestination;
	}

	public void setExtraTopicDestination(Destination extraTopicDestination) {
		this.extraTopicDestination = extraTopicDestination;
	}

	public Destination getPricecardTopicDestination() {
		return pricecardTopicDestination;
	}

	public void setPricecardTopicDestination(Destination pricecardTopicDestination) {
		this.pricecardTopicDestination = pricecardTopicDestination;
	}

	public Destination getPriceCityMappingTopicDestination() {
		return priceCityMappingTopicDestination;
	}

	public void setPriceCityMappingTopicDestination(Destination priceCityMappingTopicDestination) {
		this.priceCityMappingTopicDestination = priceCityMappingTopicDestination;
	}

	public Destination getPriceDeliveryFeeTopicDestination() {
		return priceDeliveryFeeTopicDestination;
	}

	public void setPriceDeliveryFeeTopicDestination(Destination priceDeliveryFeeTopicDestination) {
		this.priceDeliveryFeeTopicDestination = priceDeliveryFeeTopicDestination;
	}

	public Destination getPricePickUpFeeTopicDestination() {
		return pricePickUpFeeTopicDestination;
	}

	public void setPricePickUpFeeTopicDestination(Destination pricePickUpFeeTopicDestination) {
		this.pricePickUpFeeTopicDestination = pricePickUpFeeTopicDestination;
	}

	public Destination getExtrafeeAddValueFeeTopicDestination() {
		return extrafeeAddValueFeeTopicDestination;
	}

	public void setExtrafeeAddValueFeeTopicDestination(Destination extrafeeAddValueFeeTopicDestination) {
		this.extrafeeAddValueFeeTopicDestination = extrafeeAddValueFeeTopicDestination;
	}

	public Destination getPricePackageFeeTopicDestination() {
		return pricePackageFeeTopicDestination;
	}

	public void setPricePackageFeeTopicDestination(Destination pricePackageFeeTopicDestination) {
		this.pricePackageFeeTopicDestination = pricePackageFeeTopicDestination;
	}

	public Destination getPricePackageFeePcTopicDestination() {
		return pricePackageFeePcTopicDestination;
	}

	public void setPricePackageFeePcTopicDestination(Destination pricePackageFeePcTopicDestination) {
		this.pricePackageFeePcTopicDestination = pricePackageFeePcTopicDestination;
	}

	public Destination getExtrafeeAddValueFeeItemsTopicDestination() {
		return extrafeeAddValueFeeItemsTopicDestination;
	}

	public void setExtrafeeAddValueFeeItemsTopicDestination(Destination extrafeeAddValueFeeItemsTopicDestination) {
		this.extrafeeAddValueFeeItemsTopicDestination = extrafeeAddValueFeeItemsTopicDestination;
	}

	public Destination getCustPriceCityMappingDestination() {
		return custPriceCityMappingDestination;
	}

	public void setCustPriceCityMappingDestination(
			Destination custPriceCityMappingDestination) {
		this.custPriceCityMappingDestination = custPriceCityMappingDestination;
	}
	
	public Destination getOuterBranchPriceTopicDestination() {
		return outerBranchPriceTopicDestination;
	}

	public void setOuterBranchPriceTopicDestination(Destination outerBranchPriceTopicDestination) {
		this.outerBranchPriceTopicDestination = outerBranchPriceTopicDestination;
	}

	public Destination getPriceEventTopicDestination() {
		return priceEventTopicDestination;
	}

	public void setPriceEventTopicDestination(Destination priceEventTopicDestination) {
		this.priceEventTopicDestination = priceEventTopicDestination;
	}
}

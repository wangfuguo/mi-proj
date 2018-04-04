package com.hoau.hbdp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoau.eai.pc.vo.DiscountDetailInfo;
import com.hoau.eai.pc.vo.DiscountSectionInfo;
import com.hoau.miser.module.biz.job.server.service.IDiscountSectionSendService;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
/**
 * Descript:发送优惠分段测试类
 * ClassName: SendDiscountSectionToMQTest 
 * @author 刘海飞
 * @date 2016年1月27日
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:**/spring.xml"})
public class SendDiscountSectionToMQTest {

	@Resource
	private IDiscountSectionSendService discountSectionSendService;
	
	@Resource
	private IActiveMQProducerService activeMQProducerService;
	
	@Resource
	private Destination discountSectionTopicDestination;
	
	@Test
	public void sendDiscountSectionTest(){
		ArrayList<DiscountSectionInfo> list=new ArrayList<DiscountSectionInfo>();
		
		//1.优惠分段主
		DiscountSectionInfo discountSectionInfo=new DiscountSectionInfo();
		//2.优惠分段明细
		DiscountDetailInfo discountDetailInfo =new DiscountDetailInfo();
		ArrayList<DiscountDetailInfo> discountDetailInfos=new ArrayList<DiscountDetailInfo>();
		
		discountSectionInfo.setId("1992");
		discountSectionInfo.setName("大件上楼1992");
		discountSectionInfo.setRecStatus(Integer.parseInt("0"));
		discountSectionInfo.setRemark("TEST");
		discountSectionInfo.setSuitProduct("D");
		discountSectionInfo.setType("DS");
		discountDetailInfo.setBeginSection(0.02);
		discountDetailInfo.setEndSection(300.00);
		discountDetailInfo.setDiscountAmount(10.00);
		discountDetailInfo.setFuelFees(10.00);
		discountDetailInfo.setLightWeightType("Z");
		discountDetailInfo.setReduceRadix(0.00);
		discountDetailInfo.setSectionDiscount(10.00);
		discountDetailInfo.setSectionUnitPrice(10.00);
		discountDetailInfos.add(discountDetailInfo);
		discountSectionInfo.setDiscountDetailInfos(discountDetailInfos);
		
		list.add(discountSectionInfo);
		activeMQProducerService.postObjectToQueue(discountSectionTopicDestination,list);
	}
}

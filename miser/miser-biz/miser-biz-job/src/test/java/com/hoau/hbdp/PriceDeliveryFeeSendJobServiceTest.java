package com.hoau.hbdp;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoau.miser.module.biz.job.server.service.IPriceDeliveryFeeSendService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:**/spring.xml"})
public class PriceDeliveryFeeSendJobServiceTest {

	@Resource
	IPriceDeliveryFeeSendService priceDeliveryFeeSendService;
	
	@Test
	public void test1()
	{
		priceDeliveryFeeSendService.sendPriceDeliveryFee();
	}


}

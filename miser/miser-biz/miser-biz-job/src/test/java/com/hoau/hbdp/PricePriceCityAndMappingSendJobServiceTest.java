package com.hoau.hbdp;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoau.miser.module.biz.job.server.service.ICustomerPriceSendService;
import com.hoau.miser.module.biz.job.server.service.IPriceCityMappingSendService;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:**/spring.xml"})
public class PricePriceCityAndMappingSendJobServiceTest {

	@Resource
	private IPriceCityMappingSendService priceCityMappingSendService;
	
	@Test
	public void test1()
	{
		priceCityMappingSendService.send();
	}


}

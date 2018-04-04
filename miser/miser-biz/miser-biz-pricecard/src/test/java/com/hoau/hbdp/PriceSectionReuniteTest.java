package com.hoau.hbdp;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:**/spring.xml"})
public class PriceSectionReuniteTest {

	@Resource
	IPriceSectionService priceSectionService;
	
	@Test
	public void test(){
		PriceSectionEntity entity = priceSectionService.reuniteSections("18", "19", "20");
		System.out.println(entity);
	}
}

package com.hoau.hbdp;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoau.eai.pc.vo.BasicInfo;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:**/spring.xml"})
public class MQTest {

	@Resource
	private IActiveMQProducerService activeMQProducerService;
	
	@Resource
	private Destination pricecardCityTopicDestination;
	
	@Test
	public void sendMQTest(){
		BasicInfo info = new BasicInfo();
		info.setBasicType("CT");
		info.setBasicNo("0481");
		info.setBasicNameCn("天长2");
		info.setBasicStatus(0);
		info.setRecStatus(0);
		info.setBasicRemark("测试SpingJMS发送");
		ArrayList<BasicInfo> infos = new ArrayList<BasicInfo>();
		infos.add(info);
		activeMQProducerService.postObjectToQueue(pricecardCityTopicDestination, infos);
		
	}
}

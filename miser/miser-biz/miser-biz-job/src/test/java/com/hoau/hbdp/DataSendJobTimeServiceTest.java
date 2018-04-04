package com.hoau.hbdp;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:**/spring.xml"})
public class DataSendJobTimeServiceTest {

	@Resource
	IDataSendJobTimeService dataSendJobTimeService;
	
	@Test
	public void testSaveLastSendTime()
	{
		DataSendJobTimeEntity entity = new DataSendJobTimeEntity();
		entity.setId(UUIDUtil.getUUID());
		entity.setJobCode("PRICE_CITY");
		entity.setJobName("价格城市");
		entity.setLastSendTime(new Date());
		entity.setActive(MiserConstants.ACTIVE);
		entity.setRemark("测试123");
		entity.setCreateDate(new Date());
		entity.setCreateUser("陈宇霖");
		entity.setModifyDate(new Date());
		entity.setModifyUser("陈宇霖");
		dataSendJobTimeService.saveLastSendTime(entity);
	}

	@Test
	public void testGetLastSendTime()
	{
		DataSendJobTimeEntity entity = new DataSendJobTimeEntity();
		entity.setJobCode("PRICE_CITY");
		System.out.println(dataSendJobTimeService.getLastSendTime(entity));
	}

}

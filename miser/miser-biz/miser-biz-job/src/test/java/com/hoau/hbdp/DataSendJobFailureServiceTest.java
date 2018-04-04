package com.hoau.hbdp;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:**/spring.xml"})
public class DataSendJobFailureServiceTest {

	@Resource
	IDataSendJobFailureService dataSendJobFailureService;
	
	@Test
	public void testSaveFailureData()
	{
		DataSendJobFailureEntity entity = new DataSendJobFailureEntity();
		entity.setId(UUIDUtil.getUUID());
		entity.setBusinessId("7ee9b930-3bbc-404d-b8b4-cf8951a17bb6");
		entity.setType("PRICE_CITY");
		entity.setFailReason("Test123");
		entity.setSendTime(new Date());
		entity.setActive(MiserConstants.ACTIVE);
		entity.setRemark("测试123456");
		entity.setCreateDate(new Date());
		entity.setCreateUser("陈宇霖");
		entity.setModifyDate(new Date());
		entity.setModifyUser("陈宇霖");
		dataSendJobFailureService.saveFailureData(entity);
	}

	@Test
	public void testGetFailureDatas()
	{
		DataSendJobFailureEntity entity = new DataSendJobFailureEntity();
		entity.setType("PRICE_CITY");
		entity.setActive(MiserConstants.ACTIVE);
		System.out.println(dataSendJobFailureService.getFailureDatas(entity));
	}
	
	@Test
	public void testUpdateSendedData()
	{
		DataSendJobFailureEntity entity = new DataSendJobFailureEntity();
		entity.setId("bb01f8b2-f3fb-4579-b2ab-58ca390cd640");
		entity.setActive(MiserConstants.INACTIVE);
		dataSendJobFailureService.updateSendedData(entity);
	}

}

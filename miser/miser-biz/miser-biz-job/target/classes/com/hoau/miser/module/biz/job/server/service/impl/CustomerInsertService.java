package com.hoau.miser.module.biz.job.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.shared.util.JsonUtils;
import com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity;
import com.hoau.miser.module.biz.job.server.dao.CustomerJobMapper;
import com.hoau.miser.module.biz.job.server.service.ICustomerInsertService;
import com.hoau.miser.module.biz.job.shared.domain.CustomerEntity;
import com.hoau.miser.module.biz.job.shared.domain.CustomerExtVo;
import com.hoau.miser.module.util.HttpUtil;


/**
 * ClassName: PricecardCitySyncService 
 * @Description: 价格城市同步服务
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月15日
 * @version V1.0   
 */
@Service
public class CustomerInsertService implements ICustomerInsertService{
	private static Logger logger = LoggerFactory.getLogger(CustomerInsertService.class);
	@Resource
	private CustomerJobMapper customerJobMapper;
	private static PropertiesConfiguration customerConfig;
	
	public CustomerJobMapper getCustomerJobMapper() {
		return customerJobMapper;
	}


	public void setCustomerJobMapper(CustomerJobMapper customerJobMapper) {
		this.customerJobMapper = customerJobMapper;
	}


	static {
		try {
			setCustomerConfig(new PropertiesConfiguration(
					"config.properties"));
		} catch (ConfigurationException cexception) {
			logger.error("read adserver Conf properties file error.",
					cexception);
			throw new RuntimeException(cexception);
		}
	}
	
	@Override
	public void insertCustomerData() {
		CustomerExtVo vo = customerJobMapper.queryMaxCustomerDate();
		String param = JsonUtils.toJson(vo);
		String url = customerConfig.getString("CRM_CUSTOMER_URL");
		String response = new HttpUtil(url).post(param, "");
		ResponseBaseEntity<CustomerExtVo> result = JsonUtils.toObject(response, ResponseBaseEntity.class, CustomerExtVo.class);
		if(result != null && result.getErrorCode().equals("0") && result.getResult() != null && result.getResult().getCustomerEntityList() != null && result.getResult().getCustomerEntityList().size() > 0){
			List<CustomerEntity> customerList = result.getResult().getCustomerEntityList();
			List<Map<String,Object>> customerVOs = customerJobMapper.getCustomerCodes(customerList);
			List<CustomerEntity> customerUpdate = new ArrayList<CustomerEntity>();
			List<CustomerEntity> customerAdd = new ArrayList<CustomerEntity>();
			Map<String,Object> codeMap = new HashMap<String,Object>();
			if(customerVOs != null && customerVOs.size() > 0){
				for (Map<String, Object> map : customerVOs) {
					//取出ID值
					for (Map.Entry<String, Object> entry : map.entrySet()) {
				      if ("CODE".equals(entry.getKey())) {
						  codeMap.put(entry.getValue().toString(),"");
				      } 
				    }
				}
				if(codeMap.size() > 0){
					for(CustomerEntity customerEntity:customerList){
						//如果存在
						if(codeMap.containsKey(customerEntity.getDcAccount().toString()))
							customerUpdate.add(customerEntity);
						else
							customerAdd.add(customerEntity);
					}
				 }
			}else
				customerAdd.addAll(customerList);
			//判断新增
			if(customerAdd.size() > 0)
				customerJobMapper.addCustomerEntity(customerAdd);
			//判断修改
			if(customerUpdate.size() > 0)
				customerJobMapper.updateCustomerEntity(customerUpdate);
		}
	}


	public static PropertiesConfiguration getCustomerConfig() {
		return customerConfig;
	}


	public static void setCustomerConfig(PropertiesConfiguration customerConfig) {
		CustomerInsertService.customerConfig = customerConfig;
	}
}

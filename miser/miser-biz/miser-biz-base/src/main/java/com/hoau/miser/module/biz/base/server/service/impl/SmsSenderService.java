package com.hoau.miser.module.biz.base.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hoau.miser.module.biz.base.api.server.service.ISmsSenderService;
import com.hoau.miser.module.biz.base.api.shared.domain.SmsInfo;
import com.hoau.miser.module.sys.base.api.server.service.IDataDictionaryValueService;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryValueEntity;

@Service
public class SmsSenderService implements ISmsSenderService {

	Logger logger = LoggerFactory.getLogger(SmsSenderService.class);
	
	@Resource
	private IDataDictionaryValueService dataDictionaryValueService;
	
	@Override
	public String send(SmsInfo smsInfo) throws Exception {
		
		String result = null;
		String url = null;
		
		JSONObject jsonObj = (JSONObject)JSONObject.toJSON(smsInfo);
		String json = jsonObj.toJSONString();
		
		if(null == json && json.length() == 0){
			result = "短信发送的内容为空";
			logger.debug(result);
			return result;
		}
		
		//从数据字典中获取配置的用于短信发送的接口地址
		DataDictionaryValueEntity entity = new DataDictionaryValueEntity();
		entity.setTermsCode("SMS_SEND");
		entity.setValueCode("SMS_SEND");
		List<DataDictionaryValueEntity> entityList = dataDictionaryValueService.queryDataDictionaryValueByEntity(entity, 10, 0);
		
		if(null == entityList || entityList.size() == 0){
			result = "根据字典项 SMS_SEND 从字典表中未能找到对应记录";
			logger.debug(result);
			return result;
			
		} else {
			DataDictionaryValueEntity record = entityList.get(0);
			url = record.getValueName();
			
			if(null == url && url.length() == 0){
				result = "短信发送接口地址为空";
				logger.debug(result);
				return result;
			}
		}
		
		//采用 HttpClient 进行 HTTP POST 请求
		PostMethod poster = new PostMethod(url);		
		poster.addRequestHeader("Content-Type","application/json;charset=UTF-8");
		
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		
		poster.setRequestBody(json);
		httpClient.executeMethod(poster);
		
        result = poster.getResponseBodyAsString();
        
        JSONObject obj = (JSONObject)JSONObject.parse(result);
        if("STATUS_SUCCESS".equals(obj.get("errmsg")))
        {
        	result = "success";
        } else {
        	result = (String) obj.get("errmsg");
        }
        
		return result;
	}
	
	
}
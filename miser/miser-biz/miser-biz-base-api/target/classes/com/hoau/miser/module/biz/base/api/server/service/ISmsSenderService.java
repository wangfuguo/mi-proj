package com.hoau.miser.module.biz.base.api.server.service;

import com.hoau.miser.module.biz.base.api.shared.domain.SmsInfo;

public interface ISmsSenderService {
	
	public String send(SmsInfo smsInfo) throws Exception;

}


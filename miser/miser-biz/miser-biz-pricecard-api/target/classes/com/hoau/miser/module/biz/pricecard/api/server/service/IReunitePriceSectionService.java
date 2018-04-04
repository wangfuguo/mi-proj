package com.hoau.miser.module.biz.pricecard.api.server.service;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;


public interface IReunitePriceSectionService {

	/**
	 * @Description: 将多个费用分段重新融合生成一个新的分段
	 * @param sectionCodes	需要进行融合的多个分段的编码
	 * @return PriceSectionEntity 融合后的新的分段对象
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月26日
	 */
	public PriceSectionEntity reuniteSections(String ... sectionCodes);
	
}

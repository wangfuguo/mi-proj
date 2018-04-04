package com.hoau.miser.module.api.facade.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hoau.miser.module.api.facade.server.dao.PriceSectionPms2DcDao;
import com.hoau.miser.module.api.facade.server.service.IPriceSectionPms2DcService;
import com.hoau.miser.module.api.facade.shared.domain.PriceSectionPms2DcEntity;
import com.hoau.miser.module.api.facade.shared.vo.PriceSectionVo;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;

@Service
public class PriceSectionPms2DcImplService implements IPriceSectionPms2DcService{
	
	/**
	 * LOG
	 */
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(PriceSectionPms2DcImplService.class);

	@Resource
	private PriceSectionPms2DcDao priceSectionPmsToDcDao;
	
	@Resource
	private IPriceSectionService priceSectionService;
	/**
	 * 查询易到家客户，网点，标准分段价格,包含价格对应的分段优惠及其明细
	 */
	@Override
	public List<PriceSectionPms2DcEntity> queryPriceSections(PriceSectionVo priceSectionVo) {
		List<PriceSectionPms2DcEntity> list = priceSectionPmsToDcDao.queryPriceSections(priceSectionVo);
		for(PriceSectionPms2DcEntity entity : list){
			if(StringUtils.isNotEmpty(entity.getFreightSectionCode())){
				entity.setPriceSectionEntity(priceSectionService.queryPriceSectionByCode(entity.getFreightSectionCode()));
			}
		}
		return list;
	}

}

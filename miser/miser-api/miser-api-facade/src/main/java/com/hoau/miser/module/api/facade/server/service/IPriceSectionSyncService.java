package com.hoau.miser.module.api.facade.server.service;

import java.util.List;

import com.hoau.miser.module.api.facade.shared.domain.PriceSectionSyncEntity;
import com.hoau.miser.module.api.facade.shared.domain.PriceSectionSubSyncEntity;

public interface IPriceSectionSyncService {

	
	public List<PriceSectionSyncEntity> queryPriceSection(PriceSectionSyncEntity pse);
	
	public void addPriceSection(PriceSectionSyncEntity pse,String priceSectionSubList);
	
	public void addPriceSectionSub(PriceSectionSubSyncEntity pse);
	
}

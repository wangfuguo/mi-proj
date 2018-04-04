package com.hoau.miser.module.api.facade.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.facade.shared.domain.PriceSectionSyncEntity;
import com.hoau.miser.module.api.facade.shared.domain.PriceSectionSubSyncEntity;


@Repository
public interface PriceSectionSyncDao {

	public List<PriceSectionSyncEntity> queryPriceSection(PriceSectionSyncEntity pe);

	public void addPriceSection(PriceSectionSyncEntity pse);
	
	public void addPriceSectionSub(PriceSectionSubSyncEntity pse);
}

package com.hoau.miser.module.biz.pricecard.server.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCorpSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpSectionVo;


public interface PriceCorpSectionDao {
	Date queryCurrentDateTime();
	
	void update(@Param("old") PriceCorpSectionEntity old,@Param("newest") PriceCorpSectionEntity newest);
	
	List<PriceCorpSectionEntity> queryGroup(PriceCorpSectionEntity pcse);
	
	List<PriceCorpSectionEntity> search(PriceCorpSectionEntity pcse);
	
	List<PriceCorpSectionEntity> queryGroup(PriceCorpSectionEntity pcse,RowBounds rowBounds);
	
	Long queryGroupCount(PriceCorpSectionEntity pcse);
	
	void insertBatch(PriceCorpSectionVo vo);
}
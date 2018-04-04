package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCorpSectionEntity;

public class PriceCorpSectionVo {
	
	private PriceCorpSectionEntity priceCorpSection;
	
	private PriceCorpSectionEntity old;
	
	private String filePath;
	
	/**
	 * 结果集信息
	 */
	private List<PriceCorpSectionEntity> list;
	

	public PriceCorpSectionEntity getPriceCorpSection() {
		return priceCorpSection;
	}

	public void setPriceCorpSection(PriceCorpSectionEntity priceCorpSection) {
		this.priceCorpSection = priceCorpSection;
	}

	public List<PriceCorpSectionEntity> getList() {
		return list;
	}

	public void setList(List<PriceCorpSectionEntity> list) {
		this.list = list;
	}

	public PriceCorpSectionEntity getOld() {
		return old;
	}

	public void setOld(PriceCorpSectionEntity old) {
		this.old = old;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
}

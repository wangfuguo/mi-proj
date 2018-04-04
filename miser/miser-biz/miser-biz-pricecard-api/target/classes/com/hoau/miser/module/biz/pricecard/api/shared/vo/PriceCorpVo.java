package com.hoau.miser.module.biz.pricecard.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCorpEntity;

/**
 * 网店专属价格
 * ClassName: PriceCorpVo 
 * @author 李玮琰
 * @date 2016年1月7日
 * @version V1.0
 */
public class PriceCorpVo {
	
	/**
	 * 基本信息
	 */
	private PriceCorpEntity prcEntity;
	
	/**
	 * 结果集信息
	 */
	private List<PriceCorpEntity> prcList;
	
	/**
	 * 查询表单
	 */
	private PriceCorpSearchFormVo searchVo;
	
	/**
	 * 明细VO
	 */
	private PriceCorpGirdVo gridVo;
	
	/**
	 * 明细列表
	 */
	private List<PriceCorpGirdVo> gridVoList;
	
	/**
	 * 是否校验 true 表示生效时间需要验证同时表示新增。false 表示生效时间不需要验证，同时表示修改
	 * 注意类型是字符串
	 * 
	 */
	private String operationType;
	
	
	/**
	 * 选择的对象集合
	 */
	private  String selects;
	
	private PriceCorpGirdVo oldPriceCorp;
	
	private Date currentTime;
	
	public PriceCorpEntity getPrcEntity() {
		return prcEntity;
	}
	public void setPrcEntity(PriceCorpEntity prcEntity) {
		this.prcEntity = prcEntity;
	}
	public List<PriceCorpEntity> getPrcList() {
		return prcList;
	}
	public void setPrcList(List<PriceCorpEntity> prcList) {
		this.prcList = prcList;
	}
	
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getSelects() {
		return selects;
	}
	public void setSelects(String selects) {
		this.selects = selects;
	}
	public PriceCorpSearchFormVo getSearchVo() {
		return searchVo;
	}
	public void setSearchVo(PriceCorpSearchFormVo searchVo) {
		this.searchVo = searchVo;
	}
	public PriceCorpGirdVo getGridVo() {
		return gridVo;
	}
	public void setGridVo(PriceCorpGirdVo gridVo) {
		this.gridVo = gridVo;
	}
	public List<PriceCorpGirdVo> getGridVoList() {
		return gridVoList;
	}
	public void setGridVoList(List<PriceCorpGirdVo> gridVoList) {
		this.gridVoList = gridVoList;
	}
	public PriceCorpGirdVo getOldPriceCorp() {
		return oldPriceCorp;
	}
	public void setOldPriceCorp(PriceCorpGirdVo oldPriceCorp) {
		this.oldPriceCorp = oldPriceCorp;
	}
	public Date getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
}

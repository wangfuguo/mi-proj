/**   
* @Title: DiscountCorpVo.java 
* @Package com.hoau.miser.module.biz.discount.shared.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 陈启勇 
* @date 2016年1月5日 下午3:39:57 
* @version V1.0   
*/
package com.hoau.miser.module.biz.discount.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountCorpEntity;

/**
 * 网点价格Vo
 * ClassName: DiscountCorpVo 
 * @author 陈启勇
 * @date 2016年1月5日
 * @version V1.0   
 */
public class DiscountCorpVo implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -6327445060047215247L;

	private DiscountCorpEntity discountCorpEntity;
	
	private List<DiscountCorpEntity> discountCorpList;
	
	/**
	 * 是否确认
	 */
	private String isConfirm;
	
	private String order;
	
	public String selects;
	
	private String filePath;
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public DiscountCorpEntity getDiscountCorpEntity() {
		return discountCorpEntity;
	}

	public void setDiscountCorpEntity(DiscountCorpEntity discountCorpEntity) {
		this.discountCorpEntity = discountCorpEntity;
	}
	
	public List<DiscountCorpEntity> getDiscountCorpList() {
		return discountCorpList;
	}
	public void setDiscountCorpList(List<DiscountCorpEntity> discountCorpList) {
		this.discountCorpList = discountCorpList;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	/**
	 * @return the selects
	 */
	public String getSelects() {
		return selects;
	}

	/**
	 * @param selects the selects to set
	 */
	public void setSelects(String selects) {
		this.selects = selects;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}

/**   
* @Title: DiscountCustomerVo.java 
* @Package com.hoau.miser.module.biz.discount.shared.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 陈启勇 
* @date 2016年1月5日 下午3:44:41 
* @version V1.0   
*/
package com.hoau.miser.module.biz.discount.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountCustomerEntity;

/**
 * 客户折扣vo
 * ClassName: DiscountCustomerVo 
 * @author 陈启勇
 * @date 2016年1月5日
 * @version V1.0   
 */
public class DiscountCustomerVo implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -6471101763037825786L;

	private DiscountCustomerEntity discountCustomerEntity;
	
	private List<DiscountCustomerEntity> discountCustomerList;
	
	private String isConfirm;
	
	private String order;
	
	public String selects;
	//文件路径
	private String filePath;

	public DiscountCustomerEntity getDiscountCustomerEntity() {
		return discountCustomerEntity;
	}

	public void setDiscountCustomerEntity(
			DiscountCustomerEntity discountCustomerEntity) {
		this.discountCustomerEntity = discountCustomerEntity;
	}

	public List<DiscountCustomerEntity> getDiscountCustomerList() {
		return discountCustomerList;
	}

	public void setDiscountCustomerList(
			List<DiscountCustomerEntity> discountCustomerList) {
		this.discountCustomerList = discountCustomerList;
	}

	/**
	 * @return the isConfirm
	 */
	public String getIsConfirm() {
		return isConfirm;
	}

	/**
	 * @param isConfirm the isConfirm to set
	 */
	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
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

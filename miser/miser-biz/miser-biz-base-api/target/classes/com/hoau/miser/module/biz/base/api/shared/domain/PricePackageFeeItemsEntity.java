package com.hoau.miser.module.biz.base.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;


/**
 * 包装费
 * ClassName: PricePackageFeeItemsEntity 
 * @author 292078
 * @date 2015年12月22日
 * @version V1.0
 */
public class PricePackageFeeItemsEntity extends BaseEntity {
	
	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231350438160812L;

    /**编号 CODE */
    private String code;
    
    /**名称 NAME*/
    private String name;
    
    /**备注 REMARK */
    private String remark;
    
    /**
     * 是否有效
     */
    private String active;
    
    //是否作废(作废时使用)Y已作废 N未作废
    private String invalid;
    
    /**
     * 是否不模糊查询
     */
    private String isNoFuzzy;
	public String getIsNoFuzzy() {
		return isNoFuzzy;
	}

	public void setIsNoFuzzy(String isNoFuzzy) {
		this.isNoFuzzy = isNoFuzzy;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getInvalid() {
		return invalid;
	}

	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}
    
    
    
    
    
}

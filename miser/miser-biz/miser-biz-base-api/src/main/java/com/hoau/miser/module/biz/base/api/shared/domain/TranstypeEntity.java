package com.hoau.miser.module.biz.base.api.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * ClassName: TranstypeEntity 
 * @Description: 运输类型数据类 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月20日
 * @version V1.0   
 */
public class TranstypeEntity extends BaseEntity {

    /**
	 * @Fields serialVersionUID : 序列号
	 */
	private static final long serialVersionUID = -1628278951336224852L;

	/** 编码 */
    private String code;

    /** 名称 */
    private String name;

    /** 别名 */
    private String otherName;

    /** 代号 */
    private String symbol;

    /** 排序号 */
    private int orderNo;

    /** 备注 */
    private String remark;

    /** 是否有效 */
    private String active;
    
    /** 是否作废 */
    private String invalid;
    
    /**
     * @Fields parentCode : 上级产品编码
     */
    private String parentCode;
    
    /**
     * @Fields parentName : 上级产品名称
     */
    private String parentName;
    
    /**
     * @Fields showRoot : 是否显示“全部”
     */
    private String containsRoot;
    
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

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
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

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getContainsRoot() {
		return containsRoot;
	}

	public void setContainsRoot(String containsRoot) {
		this.containsRoot = containsRoot;
	}

}

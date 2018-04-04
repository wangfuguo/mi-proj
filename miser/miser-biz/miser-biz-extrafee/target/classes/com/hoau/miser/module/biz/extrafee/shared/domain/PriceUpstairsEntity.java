package com.hoau.miser.module.biz.extrafee.shared.domain;

import java.util.ArrayList;
import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionSubEntity;

/**
 * ClassName: PriceUpstairsEntity 
 * @Description: 上楼费维护数据类 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月5日
 * @version V1.0   
 */
public class PriceUpstairsEntity extends BaseEntity {
	
    /**
	 * @Fields serialVersionUID : 序列号
	 */
	private static final long serialVersionUID = 6072837466190228599L;

	/**
     * @Fields type : 上楼类型，数据字典
     */
    private String type;
    
    /**
     * @Fields transportType : 运输类型
     */
    private String transportType;
    
    /**
     * @Fields transportTypeName : 运输类型名称
     */
    private String transportTypeName;

    /**
     * @Fields sectionItemCode : 费用分段编码
     */
    private String sectionItemCode;
    
    /**
     * @Fields sectionItemName : 费用分段名称
     */
    private String sectionItemName;

    /**
     * @Fields lockType : 锁定方式
     */
    private String lockType;

    /**
     * @Fields effectiveTime : 生效时间
     */
    private Date effectiveTime;

    /**
     * @Fields invalidTime : 时效时间
     */
    private Date invalidTime;

    /**
     * @Fields remark : 备注
     */
    private String remark;

    /**
     * @Fields active : 是否有效
     */
    private String active;
    
    /**
     * @Fields status : 状态,数据字典	PRICE_SATUS
     */
    private String status;
    
    /**
     * @Fields sectionEntity : 优惠分段信息
     */
    private ArrayList<PriceSectionSubEntity> sectionSubEntities;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getTransportTypeName() {
		return transportTypeName;
	}

	public void setTransportTypeName(String transportTypeName) {
		this.transportTypeName = transportTypeName;
	}

	public String getSectionItemCode() {
        return sectionItemCode;
    }

    public void setSectionItemCode(String sectionItemCode) {
        this.sectionItemCode = sectionItemCode == null ? null : sectionItemCode.trim();
    }

    public String getSectionItemName() {
		return sectionItemName;
	}

	public void setSectionItemName(String sectionItemName) {
		this.sectionItemName = sectionItemName;
	}

	public String getLockType() {
        return lockType;
    }

    public void setLockType(String lockType) {
        this.lockType = lockType == null ? null : lockType.trim();
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<PriceSectionSubEntity> getSectionSubEntities() {
		return sectionSubEntities;
	}

	public void setSectionSubEntities(
			ArrayList<PriceSectionSubEntity> sectionSubEntities) {
		this.sectionSubEntities = sectionSubEntities;
	}

}
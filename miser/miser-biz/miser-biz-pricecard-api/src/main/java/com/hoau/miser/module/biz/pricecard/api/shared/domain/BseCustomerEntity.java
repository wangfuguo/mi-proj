/**   
* @Title: BseCustomerEntity.java 
* @Package com.hoau.miser.module.biz.pricecard.shared.domain 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dengyin
* @date 2016年1月5日 下午2:34:58 
* @version V1.0   
*/
package com.hoau.miser.module.biz.pricecard.api.shared.domain;

import java.util.Date;

/**
 * 客户信息
 * ClassName: BseCustomerEntity 
 * @author dengyin
 * @date 2016年1月5日
 * @version V1.0   
 */
public class BseCustomerEntity {
	 
	//主键	
    private String id;

	//客户编码    
    private String code;

	//客户名称    
    private String name;

	//客户所属公司编码    
    private String orgCode;
    
    // 客户所属路区编码
    private String roadAreaCode;
    
    // 客户所属大区编码
    private String bigRegionCode;
    
    // 客户所属事业部
    private String divisionCode;
    
    // 客户所属门店名称
    private String orgName;
    
    // 客户所属路区名称
    private String roadAreaName;
    
    // 客户所属大区名称
    private String bigRegionName;
    
    // 客户所属事业部名称
    private String divisionName;

	//合同开始时间    
    private Date contractStartTime;

	//合同结束时间    
    private Date contractEndTime;

	//价格开始时间
    private Date pcStartTime;

	//价格结束时间
    private Date pcEndTime;

	//是否启用客户价格
    private String useCustomerPc;

	//是否启用客户折扣    
    private String useCustomerDiscount;

	//备注    
    private String remark;

	//创建时间    
    private Date createTime;

	//创建人    
    private String createUserCode;

	//更新时间    
    private Date modifyTime;

	//更新人    
    private String modifyUserCode;

	//是否可用    
    private String active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

 

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public Date getPcStartTime() {
        return pcStartTime;
    }

    public void setPcStartTime(Date pcStartTime) {
        this.pcStartTime = pcStartTime;
    }

    public Date getPcEndTime() {
        return pcEndTime;
    }

    public void setPcEndTime(Date pcEndTime) {
        this.pcEndTime = pcEndTime;
    }

    public String getUseCustomerPc() {
        return useCustomerPc;
    }

    public void setUseCustomerPc(String useCustomerPc) {
        this.useCustomerPc = useCustomerPc == null ? null : useCustomerPc.trim();
    }

    public String getUseCustomerDiscount() {
        return useCustomerDiscount;
    }

    public void setUseCustomerDiscount(String useCustomerDiscount) {
        this.useCustomerDiscount = useCustomerDiscount == null ? null : useCustomerDiscount.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode == null ? null : createUserCode.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUserCode() {
        return modifyUserCode;
    }

    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode == null ? null : modifyUserCode.trim();
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRoadAreaCode() {
		return roadAreaCode;
	}

	public void setRoadAreaCode(String roadAreaCode) {
		this.roadAreaCode = roadAreaCode;
	}

	public String getBigRegionCode() {
		return bigRegionCode;
	}

	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRoadAreaName() {
		return roadAreaName;
	}

	public void setRoadAreaName(String roadAreaName) {
		this.roadAreaName = roadAreaName;
	}

	public String getBigRegionName() {
		return bigRegionName;
	}

	public void setBigRegionName(String bigRegionName) {
		this.bigRegionName = bigRegionName;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
    
    
}

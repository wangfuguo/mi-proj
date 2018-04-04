/**
* @Title: BseCustomerEntity.java
* @Package com.hoau.miser.module.biz.pricecard.shared.domain
* @Description: TODO(用一句话描述该文件做什么)
* @author dengyin
* @date 2016年1月5日 下午2:34:58
* @version V1.0
*/
package com.hoau.miser.module.api.itf.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息
 * ClassName: BseCustomerEntity
 * @author dengyin
 * @date 2016年1月5日
 * @version V1.0
 */
public class BseCustomerTyEntity implements Serializable {

	//主键
    private String id;

	//客户编码
    private String code;

	//客户名称
    private String name;



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

    //易到家产品是否使用定日达价格
    private String useTingriEasyHome;

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

    private String orgCode;

    public String getUseTingriEasyHome() {
        return useTingriEasyHome;
    }

    public void setUseTingriEasyHome(String useTingriEasyHome) {
        this.useTingriEasyHome = useTingriEasyHome;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

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
        this.useCustomerPc = useCustomerPc;
    }

    public String getUseCustomerDiscount() {
        return useCustomerDiscount;
    }

    public void setUseCustomerDiscount(String useCustomerDiscount) {
        this.useCustomerDiscount = useCustomerDiscount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        this.createUserCode = createUserCode;
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
        this.modifyUserCode = modifyUserCode;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}

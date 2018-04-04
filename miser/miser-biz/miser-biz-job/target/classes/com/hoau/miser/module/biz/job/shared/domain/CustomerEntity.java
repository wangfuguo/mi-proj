package com.hoau.miser.module.biz.job.shared.domain;

import java.util.Date;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 客户信息实体
 * 
 * @author 蒋落琛
 * @date 2016-1-25
 */
public class CustomerEntity extends BaseEntity {

	private static final long serialVersionUID = 8896574465955521540L;

	/**
	 * 客户性质
	 */
	private String accountType;

	/**
	 * CRM客户账号
	 */
	private String accountCode;

	/**
	 * 客户企业全称
	 */
	private String enterpriseName;

	/**
	 * 客户企业缩写
	 */
	private String enterpriseShortName;

	/**
	 * 企业发票抬头
	 */
	private String enterpriseBillHead;

	/**
	 * 所属总公司
	 */
	private String parentCompany;

	/**
	 * 所属总公司名称
	 */
	private String parentCompanyName;

	/**
	 * 企业性质
	 */
	private String enterpriseType;

	/**
	 * 客户等级分类
	 */
	private String accountSub;

	/**
	 * 状态描述
	 */
	private String statusCode;

	/**
	 * 二级物流公司代码
	 */
	private String tierCode;

	/**
	 * 所属行业
	 */
	private String industryCode;

	/**
	 * 客户级别
	 */
	private String accountRatingCode;

	/**
	 * 第一单时间
	 */
	private Date startingTime;
	
	/**
	 * 最后发货时间
	 */
	private Date lastShipmentsTime;

	/**
	 * 详细地址
	 */
	private String detailedAddress;

	/**
	 * 详细地址邮编
	 */
	private String detailedAddressPostalCode;

	/**
	 * 取货详细地址
	 */
	private String deliveryAddress;

	/**
	 * 取货详细地址邮编
	 */
	private String deliveryAddressPostalCode;

	/**
	 * 当前折扣率
	 */
	private Double discountRate;

	/**
	 * 客户备注
	 */
	private String accountRemark;

	/**
	 * 主要货物名称
	 */
	private String mainGoodsName;

	/**
	 * 主要包装方式
	 */
	private String typeOfPackage;

	/**
	 * 迪辰账号
	 */
	private String dcAccount;

	/**
	 * 负责人
	 */
	private String managePerson;

	/**
	 * 负责人工号
	 */
	private String manageEmpCode;

	/**
	 * 事业部
	 */
	private String businessUnitCode;

	/**
	 * 事业部名称
	 */
	private String businessUnitName;

	/**
	 * 大区
	 */
	private String regionsCode;

	/**
	 * 大区名称
	 */
	private String regionsName;

	/**
	 * 所属路区
	 */
	private String roadAreaCode;

	/**
	 * 路区名称
	 */
	private String roadAreaName;

	/**
	 * 客户信用评级
	 */
	private String accountCreditGrade;

	/**
	 * 期望营销活动类型
	 */
	private String marketActiveType;

	/**
	 * 营销活动详述
	 */
	private String marketActiveRemark;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 客户渠道
	 */
	private String accountChannel;

	/**
	 * 客户账期
	 */
	private String accountPeriod;

	/**
	 * 联系人信息
	 */
	private ContactEntity contactEntity;

	/**
	 * 转为意向标识
	 */
	private boolean turnIntentFlag;

	/**
	 * 最后拜访时间
	 */
	private Date lastChatsDate;
	
	/**
	 * 合同开始时间
	 */
	private Date contractStartTime;
	
	/**
	 * 合同结束时间
	 */
	private Date contractEndTime;
	
	/**
	 * 资源客户ID
	 */
	private String customerInfoPoolId;
	
	/**
	 * 发货日志
	 */
	private String waybillLog;
	
	/**
	 * 客户所属组织
	 */
	private String customerOfOrg;
	
	/**
	 *删除原因
	 */
	private String delDesc;
	
	/**
	 * 改签门店
	 */
	private String signTierCode;
	
	/**
	 * 是否创建官网账号
	 */
	private String isCreateObhAccount;
	
	/**
	 * 官网主键
	 */
	private Integer obhUserId;
	
	/**
	 * 网厅账户
	 */
	private String obhAccount;

    /**
     * 近三个月平均产值
     */
    private Double productValueOfThreeMonthAvg;

	public String getSignTierCode() {
		return signTierCode;
	}

	public void setSignTierCode(String signTierCode) {
		this.signTierCode = signTierCode;
	}

	public String getDelDesc() {
		return delDesc;
	}

	public void setDelDesc(String delDesc) {
		this.delDesc = delDesc;
	}

	public CustomerEntity() {

	}

	public CustomerEntity(String id) {
		this.setId(id);
	}

	public String getAccountChannel() {
		return accountChannel;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public String getAccountCreditGrade() {
		return accountCreditGrade;
	}

	public String getAccountPeriod() {
		return accountPeriod;
	}

	public String getAccountRatingCode() {
		return accountRatingCode;
	}

	public String getAccountRemark() {
		return accountRemark;
	}

	public String getAccountSub() {
		return accountSub;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getActive() {
		return active;
	}

	public String getBusinessUnitCode() {
		return businessUnitCode;
	}

	public String getBusinessUnitName() {
		return businessUnitName;
	}

	public ContactEntity getContactEntity() {
		return contactEntity;
	}

	public String getDcAccount() {
		return dcAccount;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public String getDeliveryAddressPostalCode() {
		return deliveryAddressPostalCode;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public String getDetailedAddressPostalCode() {
		return detailedAddressPostalCode;
	}

	public Double getDiscountRate() {
		return discountRate;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public String getEnterpriseShortName() {
		return enterpriseShortName;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public String getMainGoodsName() {
		return mainGoodsName;
	}

	public String getManageEmpCode() {
		return manageEmpCode;
	}

	public String getManagePerson() {
		return managePerson;
	}

	/**
	 * @return the marketActiveRemark
	 */
	public String getMarketActiveRemark() {
		return marketActiveRemark;
	}

	/**
	 * @return the marketActiveType
	 */
	public String getMarketActiveType() {
		return marketActiveType;
	}

	public String getParentCompany() {
		return parentCompany;
	}

	public String getParentCompanyName() {
		return parentCompanyName;
	}

	public String getRegionsCode() {
		return regionsCode;
	}

	public String getRegionsName() {
		return regionsName;
	}

	public String getRoadAreaCode() {
		return roadAreaCode;
	}

	public String getRoadAreaName() {
		return roadAreaName;
	}

	public Date getStartingTime() {
		return startingTime;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getTierCode() {
		return tierCode;
	}

	public String getTypeOfPackage() {
		return typeOfPackage;
	}

	public void setAccountChannel(String accountChannel) {
		this.accountChannel = accountChannel;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public void setAccountCreditGrade(String accountCreditGrade) {
		this.accountCreditGrade = accountCreditGrade;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	public void setAccountRatingCode(String accountRatingCode) {
		this.accountRatingCode = accountRatingCode;
	}

	public void setAccountRemark(String accountRemark) {
		this.accountRemark = accountRemark;
	}

	public void setAccountSub(String accountSub) {
		this.accountSub = accountSub;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setBusinessUnitCode(String businessUnitCode) {
		this.businessUnitCode = businessUnitCode;
	}

	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}

	public void setContactEntity(ContactEntity contactEntity) {
		this.contactEntity = contactEntity;
	}

	public void setDcAccount(String dcAccount) {
		this.dcAccount = dcAccount;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public void setDeliveryAddressPostalCode(String deliveryAddressPostalCode) {
		this.deliveryAddressPostalCode = deliveryAddressPostalCode;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public void setDetailedAddressPostalCode(String detailedAddressPostalCode) {
		this.detailedAddressPostalCode = detailedAddressPostalCode;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public void setEnterpriseShortName(String enterpriseShortName) {
		this.enterpriseShortName = enterpriseShortName;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public void setMainGoodsName(String mainGoodsName) {
		this.mainGoodsName = mainGoodsName;
	}

	public void setManageEmpCode(String manageEmpCode) {
		this.manageEmpCode = manageEmpCode;
	}

	public void setManagePerson(String managePerson) {
		this.managePerson = managePerson;
	}

	/**
	 * @param marketActiveRemark
	 *            the marketActiveRemark to set
	 */
	public void setMarketActiveRemark(String marketActiveRemark) {
		this.marketActiveRemark = marketActiveRemark;
	}

	/**
	 * @param marketActiveType
	 *            the marketActiveType to set
	 */
	public void setMarketActiveType(String marketActiveType) {
		this.marketActiveType = marketActiveType;
	}

	public void setParentCompany(String parentCompany) {
		this.parentCompany = parentCompany;
	}

	public void setParentCompanyName(String parentCompanyName) {
		this.parentCompanyName = parentCompanyName;
	}

	public void setRegionsCode(String regionsCode) {
		this.regionsCode = regionsCode;
	}

	public void setRegionsName(String regionsName) {
		this.regionsName = regionsName;
	}

	public void setRoadAreaCode(String roadAreaCode) {
		this.roadAreaCode = roadAreaCode;
	}

	public void setRoadAreaName(String roadAreaName) {
		this.roadAreaName = roadAreaName;
	}

	public void setStartingTime(Date startingTime) {
		this.startingTime = startingTime;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public void setTierCode(String tierCode) {
		this.tierCode = tierCode;
	}

	public void setTypeOfPackage(String typeOfPackage) {
		this.typeOfPackage = typeOfPackage;
	}

	public String getEnterpriseBillHead() {
		return enterpriseBillHead;
	}

	public void setEnterpriseBillHead(String enterpriseBillHead) {
		this.enterpriseBillHead = enterpriseBillHead;
	}

	public boolean isTurnIntentFlag() {
		return turnIntentFlag;
	}

	public void setTurnIntentFlag(boolean turnIntentFlag) {
		this.turnIntentFlag = turnIntentFlag;
	}

	public Date getLastChatsDate() {
		return lastChatsDate;
	}

	public void setLastChatsDate(Date lastChatsDate) {
		this.lastChatsDate = lastChatsDate;
	}

	public Date getLastShipmentsTime() {
		return lastShipmentsTime;
	}

	public void setLastShipmentsTime(Date lastShipmentsTime) {
		this.lastShipmentsTime = lastShipmentsTime;
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

	public String getCustomerInfoPoolId() {
		return customerInfoPoolId;
	}

	public void setCustomerInfoPoolId(String customerInfoPoolId) {
		this.customerInfoPoolId = customerInfoPoolId;
	}

	public String getWaybillLog() {
		return waybillLog;
	}

	public void setWaybillLog(String waybillLog) {
		this.waybillLog = waybillLog;
	}

	public String getCustomerOfOrg() {
		return customerOfOrg;
	}

	public void setCustomerOfOrg(String customerOfOrg) {
		this.customerOfOrg = customerOfOrg;
	}

	public String getIsCreateObhAccount() {
		return isCreateObhAccount;
	}

	public void setIsCreateObhAccount(String isCreateObhAccount) {
		this.isCreateObhAccount = isCreateObhAccount;
	}

	public Integer getObhUserId() {
		return obhUserId;
	}

	public void setObhUserId(Integer obhUserId) {
		this.obhUserId = obhUserId;
	}

	public String getObhAccount() {
		return obhAccount;
	}

	public void setObhAccount(String obhAccount) {
		this.obhAccount = obhAccount;
	}

    public Double getProductValueOfThreeMonthAvg() {
        return productValueOfThreeMonthAvg;
    }

    public void setProductValueOfThreeMonthAvg(Double productValueOfThreeMonthAvg) {
        this.productValueOfThreeMonthAvg = productValueOfThreeMonthAvg;
    }
}

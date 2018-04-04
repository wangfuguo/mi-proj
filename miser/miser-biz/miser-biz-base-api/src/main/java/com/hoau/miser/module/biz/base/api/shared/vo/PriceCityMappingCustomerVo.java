package com.hoau.miser.module.biz.base.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingCustomerEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.DistrictEntity;


/**
 * 
 * @Description: 价格城市时效管理Vo
 * @Author lhf
 * @Time 2015年12月16日上午10:00:18
 */
public class PriceCityMappingCustomerVo implements Serializable {

	private static final long serialVersionUID = -2719021870354921278L;
	
	/**
	 *价格时效管理对象
	 */
	private PriceCityMappingCustomerEntity priceCityCustomerEntity;
	
	/**
	 *价格时效管理对象
	 */
	/**
     * 增加价格城市code
     */
    private String resourceCodes;
    /**
     * 删除价格城市code
     */
    private String deleteResourceCodes;
    
    
    private String currRoleCode;
	/**
	 * 省市区
	 */
	private DistrictEntity district;
	/**
	 * 省市区集合
	 */
	
	private List<DistrictEntity> districtList;
	
	private List<PriceCityMappingCustomerEntity> priceCityList;
	/**备注*/
    private String bz;
	/**城市类型*/
    private String cityType;
    
    /**价格城市名称*/
    private String cityName;
    private String cityCode;
    private String active;
    
	//文件路径
	private String filePath;
	
	
    
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	public DistrictEntity getDistrict() {
		return district;
	}
	public void setDistrict(DistrictEntity district) {
		this.district = district;
	}
	public List<DistrictEntity> getDistrictList() {
		return districtList;
	}
	public void setDistrictList(List<DistrictEntity> districtList) {
		this.districtList = districtList;
	}
	public String getCurrRoleCode() {
		return currRoleCode;
	}
	public void setCurrRoleCode(String currRoleCode) {
		this.currRoleCode = currRoleCode;
	}
    public String getResourceCodes() {
		return resourceCodes;
	}

	public void setResourceCodes(String resourceCodes) {
		this.resourceCodes = resourceCodes;
	}

	public String getDeleteResourceCodes() {
		return deleteResourceCodes;
	}

	public void setDeleteResourceCodes(String deleteResourceCodes) {
		this.deleteResourceCodes = deleteResourceCodes;
	}

	public PriceCityMappingCustomerEntity getPriceCityCustomerEntity() {
		return priceCityCustomerEntity;
	}

	public void setPriceCityCustomerEntity(PriceCityMappingCustomerEntity priceCityCustomerEntity) {
		this.priceCityCustomerEntity = priceCityCustomerEntity;
	}

	public List<PriceCityMappingCustomerEntity> getPriceCityList() {
		return priceCityList;
	}

	public void setPriceCityList(List<PriceCityMappingCustomerEntity> priceCityList) {
		this.priceCityList = priceCityList;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

}

package com.hoau.miser.module.biz.base.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.CityTypeEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.DistrictEntity;

public class CityTypeVo implements Serializable{

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = -2741081889494565857L;
	 
	private CityTypeEntity cityTypeEntity;
	
	
    
    
	/**
	 * 省市区
	 */
	private DistrictEntity district;
	/**
	 * 省市区集合
	 */
	
	private List<DistrictEntity> districtList;
	
	private List<CityTypeEntity> CityTypeList;
	/**备注*/
    private String bz;
	/**城市类型*/
    private String cityType;
    
    
    private String active;
    
	//文件路径
	private String filePath;

	public CityTypeEntity getCityTypeEntity() {
		return cityTypeEntity;
	}

	public void setCityTypeEntity(CityTypeEntity cityTypeEntity) {
		this.cityTypeEntity = cityTypeEntity;
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

	public List<CityTypeEntity> getCityTypeList() {
		return CityTypeList;
	}

	public void setCityTypeList(List<CityTypeEntity> CityTypeList) {
		this.CityTypeList = CityTypeList;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}

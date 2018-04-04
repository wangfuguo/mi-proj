package com.hoau.miser.module.biz.base.api.server.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.base.api.shared.domain.CityTypeEntity;

public interface ICityTypeService {
	public Long countCityTypeByEntity(CityTypeEntity CityTypeEntity);
	
	public String createCityTypeExcelFile(List<CityTypeEntity> list);
	
	public CityTypeEntity queryCityTypeInUpdate(CityTypeEntity entity);
	
	public List<CityTypeEntity> queryCityTypeByEntity(CityTypeEntity cityTypeEntity,int limit,
			int start);
	public Long updateCityType(CityTypeEntity cityTypeEntity);
	
	public Map<String,Object> cityTypeImport(String path) throws IOException;
	
	public Map<String,String> queryCityTypeCode();
	
	public Map<String,String> queryCityTypeCode2();
}

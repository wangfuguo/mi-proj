/**
 * 
 */
package com.hoau.miser.module.biz.base.api.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.base.api.shared.domain.OutLineEntity;

/**
 * 外发偏线系统管理 2016-3-17 20:09:07 
 * @author dengyin
 *
 */
public interface IOutLineService {

	/*
	 * 偏线主页查询
	 */
	List<OutLineEntity> queryOutLineByEntity(OutLineEntity outLineEntity,int limit,int start);
	
	/**
	 * 统计当前查询条件下的总记录数
	 * @param outLineEntity
	 * @return
	 */
	long countOfOutLineByEntity(OutLineEntity outLineEntity);
	
	List<OutLineEntity> excelQueryOutLineByEntity(OutLineEntity outLineEntity);

	String createExcelFile(List<OutLineEntity> outLineList);

	Map<String, Object> excelImport(String uploadPath);
	
	void updateByEntity(OutLineEntity outLineEntity);
	
	void batchInsertEntityList(List<OutLineEntity> outLineEntities);
	
	void batchUpdateEntityList(List<OutLineEntity> list);

	void destroy(String destoryIdStr);
}

/**
 * 
 */
package com.hoau.miser.module.biz.base.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.base.api.shared.domain.OutLineEntity;

/**
 * @author dengyin
 *
 */
@Repository
public interface OutLineDao {

	List<OutLineEntity> queryOutLineByEntity(OutLineEntity outLineEntity,
			RowBounds rowBounds);
	
	long countOfOutLineByEntity(OutLineEntity outLineEntity);
	
	List<OutLineEntity> excelQueryOutLineByEntity(OutLineEntity outLineEntity);
	
	void updateByEntity(OutLineEntity outLineEntity);
	
	void batchInsertEntityList(List<OutLineEntity> outLineEntities);
	
	void batchUpdateEntityList(List<OutLineEntity> list);

	void destroy(Map param);

}

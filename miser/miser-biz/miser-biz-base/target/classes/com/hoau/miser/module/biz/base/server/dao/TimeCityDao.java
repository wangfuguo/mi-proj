package com.hoau.miser.module.biz.base.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.TimeCityEntity;

@Repository
public interface TimeCityDao {
	
	/**
	 * 查询时效城市列表
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 */
	List<TimeCityEntity> queryByEntity(TimeCityEntity entity,RowBounds rowBounds);
	
	
	/**
	 * 条数统计
	 * @param entity
	 * @return
	 */
	Long countOfTimeCity(TimeCityEntity entity);
	
	/**
	 * 通过ID查询时效城市
	 * @param entity
	 * @return
	 */
	TimeCityEntity queryById(TimeCityEntity entity);
	
	/**
	 * 新增时效城市
	 * @param entity
	 */
	void addTimeCity(TimeCityEntity entity);
	
	/**
	 * 修改时效城市
	 * @param entity
	 */
	void updateTimeCity(TimeCityEntity entity);
	
	/**
	 * 通过名称查询时效城市
	 * @param entity
	 * @return
	 */
	TimeCityEntity queryByName(TimeCityEntity entity);
	
	/**
	 * 作废时效城市
	 * @param entity
	 */
	void invalidTimeCity(TimeCityEntity entity);
	
	/**
	 * 用于导出的查询
	 * @param queryParam
	 * @return
	 */
	public ArrayList<TimeCityEntity> queryForExport(TimeCityEntity entity);
	
    /**
     * 
     * @Description: TODO 批量插入
     * @param @param pceList
     * @param @return   
     * @return int 
     * @throws
     * @author 何羽
     * @date 2016年7月7日
     */
    public Integer insertBatch(@Param("pceList")List<TimeCityEntity> list);

}
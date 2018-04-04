package com.hoau.miser.module.biz.base.api.server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.base.api.shared.domain.TimeCityEntity;

/**
 * 时效城市定义接口
 * @author 何羽
 *
 */
public interface ITimeCityService {

	/**
	 * 查询时效城市列表
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 */
	List<TimeCityEntity> queryByEntity(TimeCityEntity entity,int limit, int start);
	
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
	 * 通过名称查询时效城市
	 * @param entity
	 * @return
	 */
	TimeCityEntity queryTimeCityByName(TimeCityEntity entity);
	
	
	/**
     * @Description 查询所有出发时效城市列表
     * @return
     */
    List<TimeCityEntity> queryAllStartTimeCities();

    /**
     * @Description 查询所有到达时效城市列表
     * @return
     */
    List<TimeCityEntity> queryAllArrivalTimeCities();
    
    /**
     * 更新时效城市
     * @param entity
     */
    void updatetimeCity(TimeCityEntity entity);
    
    /**
     * 作废时效城市
     * @param entity
     */
    void invalidTimeCity(TimeCityEntity entity);
    
    /**
     * 
     * @Description: TODO导出Excel 数据
     * @param @param pcv
     * @param @return   
     * @return List<PriceCityEntity> 
     * @throws
     * @author 李玮琰
     * @date 2016年4月11日
     */
    public ArrayList<TimeCityEntity> excelTimeCity(TimeCityEntity entity);
    
    /**
     * @Description: TODO 生成Excel 
     * @param @param list
     * @param @return
     * @param @throws IOException   
     * @return String 
     * @throws
     * @author 何羽
     * @date 2016年7月7日
     */
    public String createExcelFile(List<TimeCityEntity> list) throws IOException ;
    
    /**
     * 
     * @Description: TODO导入数据
     * @param @param filePath
     * @param @return   
     * @return Map<String,Object> 
     * @throws
     * @author 何羽
     * @date 2016年7月7日
     */
    public Map<String, Object> bathImplTimeCity(String filePath) throws Exception;
}

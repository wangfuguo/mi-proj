package com.hoau.miser.module.biz.base.server.dao;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 价格城市维护
 * ClassName: PriceCityDao 
 * @author Chenyl yulin.chen@hoau.net
 * @date 2016年1月14日
 * @version V1.0
 */
@Repository
public interface PriceCityDao {



	/**
	 * @Description: 查询价格城市 带分页
	 * @param queryParam
	 * @param rowBounds
	 * @return List<PriceCityMappingEntity>
     * @author Chenyl yulin.chen@hoau.net
	 * @date 2016年1月14日
	 */
	ArrayList<PriceCityEntity> queryPriceCities(PriceCityEntity queryParam, RowBounds rowBounds);

	/**
	 * 
	 * @Description: TODO 查询价格城市
	 * @param @param queryParam
	 * @param @return   
	 * @return ArrayList<PriceCityEntity> 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年4月11日
	 */
	public ArrayList<PriceCityEntity> queryPriceCities(PriceCityEntity queryParam);
	
	
	/**
	 * 添加用于导出的查询
	 * @author dengyin
	 * @param queryParam
	 * @return
	 */
	public ArrayList<PriceCityEntity> queryPriceCitiesForExport(PriceCityEntity queryParam); 
	
	/**
	 * @Description: 统计价格城市条数
     * @param queryParam
     * @return List<PriceCityMappingEntity>
     * @author Chenyl yulin.chen@hoau.net
     * @date 2016年1月14日
	 */
	Long queryPriceCityCount(PriceCityEntity queryParam);

    /**
     * @Description: 根据ID查询价格城市
     * @param queryParam
     * @return List<PriceCityMappingEntity>
     * @author Chenyl yulin.chen@hoau.net
     * @date 2016年1月14日
     */
	public PriceCityEntity queryPriceCityById(PriceCityEntity queryParam);

	/**
	 * @description: 增加价格城市
     * @param priceCityEntity
	 * @date 2015年12月22日
     * @author Chenyl yulin.chen@hoau.net
	 * @return void
	 */
	void addPriceCity(PriceCityEntity priceCityEntity);

    /**
     * @description: 修改价格城市
     * @param priceCityEntity
     * @date 2015年12月22日
     * @author Chenyl yulin.chen@hoau.net
     * @return void
     */
    void updatePriceCity(PriceCityEntity priceCityEntity);

    /**
     * @description: 删除价格城市
     * @param priceCityEntity
     * @date 2015年12月22日
     * @author Chenyl yulin.chen@hoau.net
     * @return void
     */
    void invalidPriceCity(PriceCityEntity priceCityEntity);

    /**
     * @description: 删除价格城市
     * @param priceCityEntity
     * @date 2015年12月22日
     * @author Chenyl yulin.chen@hoau.net
     * @return void
     */
    void deletePriceCity(PriceCityEntity priceCityEntity);

    /**
     * @description: 根据编码查询价格城市
     * @param priceCityEntity
     * @date 2015年12月22日
     * @author Chenyl yulin.chen@hoau.net
     * @return void
     */
    public PriceCityEntity queryPriceCityByCode(PriceCityEntity priceCityEntity);

    /**
     * @description: 根据名称查询价格城市
     * @param priceCityEntity
     * @date 2015年12月22日
     * @author Chenyl yulin.chen@hoau.net
     * @return void
     */
    public PriceCityEntity queryPriceCityByName(PriceCityEntity priceCityEntity);
    
    /**
     * 
     * @Description: TODO 批量插入
     * @param @param pceList
     * @param @return   
     * @return int 
     * @throws
     * @author 李玮琰
     * @date 2016年4月11日
     */
    public Integer insertBatch(@Param("pceList")List<PriceCityEntity> pceList);
    
    
}

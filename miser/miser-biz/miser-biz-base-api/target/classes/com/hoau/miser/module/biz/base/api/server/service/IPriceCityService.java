package com.hoau.miser.module.biz.base.api.server.service;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description: 价格城市维护
 * @author: 279716
 * @date: 2016/3/9 17:28
 */
public interface IPriceCityService {

    /**
     * @Description 查询价格城市列表
     * @param priceCityVo
     * @param limit
     * @param start
     * @return
     */
    public ArrayList<PriceCityEntity> queryPriceCities(PriceCityVo priceCityVo, int limit, int start);

    /**
     * @Description 查询所有出发价格城市列表
     * @return
     */
    public List<PriceCityEntity> queryAllStartPriceCities();
    
    /**
     * 查询所有标准范围的出发价格城市.
     */
    public List<PriceCityEntity> queryAllStartStandardPriceCities();

    /**
     * 
     * @Description: 查询所有标准范围的到达价格城市
     * @date 2016年7月26日
     */
    public List<PriceCityEntity> queryAllArrivalStandardPriceCities();
    
    /**
     * @Description 查询所有到达价格城市列表
     * @return
     */
    public List<PriceCityEntity> queryAllArrivalPriceCities();

    /**
     * @Description 查询价格城市数量
     * @param priceCityVo
     * @return
     */
    public Long queryPriceCityCount(PriceCityVo priceCityVo);

    /**
     * @Description 根据ID查询价格城市
     * @param priceCityVo
     * @return
     */
    public PriceCityEntity queryPriceCityById(PriceCityVo priceCityVo);

    /**
     * @Description 根据名称查询有效的价格城市
     * @param priceCityVo
     * @return
     */
    public PriceCityEntity queryPriceCityByName(PriceCityVo priceCityVo);

    /**
     * @Description 新增价格城市
     * @param priceCityVo
     */
    public void addPriceCity(PriceCityVo priceCityVo);

    /**
     * @Description 更新价格城市
     * @param priceCityVo
     */
    public void updatePriceCity(PriceCityVo priceCityVo);

    /**
     * @Description 作废价格城市
     * @param priceCityVo
     */
    public void invalidPriceCity(PriceCityVo priceCityVo);
    
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
    public ArrayList<PriceCityEntity> excelPriceCity(PriceCityVo pcv);
    
    /**
     * @Description: TODO 生成Excel 
     * @param @param list
     * @param @return
     * @param @throws IOException   
     * @return String 
     * @throws
     * @author 李玮琰
     * @date 2016年4月11日
     */
    public String createExcelFile(List<PriceCityEntity> list) throws IOException ;
    
    /**
     * 
     * @Description: TODO导入数据
     * @param @param filePath
     * @param @return   
     * @return Map<String,Object> 
     * @throws
     * @author 李玮琰
     * @date 2016年4月11日
     */
    public Map<String, Object> bathImplPriceCity(String filePath,String piceCityScope)  throws Exception;

}

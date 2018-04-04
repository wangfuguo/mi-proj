package com.hoau.miser.module.biz.base.server.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import com.hoau.miser.module.biz.base.api.server.service.IPriceCityService;
import com.hoau.miser.module.biz.base.api.shared.define.BizBaseConstant;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityVo;
import com.hoau.miser.module.util.StringUtils;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.IPriceCityMappingService;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityMappingVo;
import com.hoau.miser.module.biz.base.server.cache.PriceCityMappingCache;
import com.hoau.miser.module.biz.base.server.dao.PriceCityMappingDao;
import com.hoau.miser.module.common.server.service.ISerialNumberService;
import com.hoau.miser.module.common.shared.define.SerialNumberRuleEnum;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘海飞
 * @create 2015年12月15日 上午8:21:03
 * @description 用户service
 */
@Service
public class PriceCityMappingService implements IPriceCityMappingService {
    private static final Logger logger = LoggerFactory
            .getLogger(PriceCityMappingService.class);
    @Resource
    private PriceCityMappingDao priceCityMappingDao;
    @Resource
    private ISerialNumberService serialNumberService;
    @Resource
    private IPriceCityService priceCityService;

    private static final String PRICECITYSCOPE = "STANDARD";
    /**
     * 页面查询映射关系列表
     * @param pricePriceEntity
     * @param limit
     * @param start
     * @return
     */
    @Override
    public List<PriceCityMappingEntity> queryPriceCityMappingByEntity(
            PriceCityMappingEntity pricePriceEntity, int limit, int start) {
        RowBounds rowBounds =new RowBounds(start,limit);
        return priceCityMappingDao.queryPriceCityByEntity(pricePriceEntity, rowBounds);
    }

    @SuppressWarnings("unchecked")
    private ICache<String, PriceCityMappingEntity> getPriceCityCache(){
        ICache<String, PriceCityMappingEntity> priceCityCache = CacheManager.getInstance().getCache(PriceCityMappingCache.PRICE_CITY_MAPPING_CACHE_UUID);
        return priceCityCache;
    }

    /**
     * 界面查询条件的查询结果总条数
     * @param pricePriceEntity
     * @return
     */
    @Override
    public Long countPriceCityMappingByEntity(PriceCityMappingEntity pricePriceEntity) {

        return priceCityMappingDao.countPriceCityByEntity(pricePriceEntity);
    }

    /**
     * 根据界面提交的数据更新映射关系
     * @param entity
     */
    @Override
    public void updatePriceCityMapping(PriceCityMappingEntity entity) {
        try {
            updatePriceCityMapping(entity, "SEND");
        } catch (Exception e) {
            throw new BusinessException(MessageType.ERROR_ISEXIST,"更新失败");
        }
        try {
            updatePriceCityMapping(entity, "ARRIVAL");
        } catch (Exception e) {
            throw new BusinessException(MessageType.ERROR_ISEXIST,"更新失败");
        }
    }

    /**
     * 更新映射关系数据库实现
     * @param entity
     * @param priceCityType
     * @return
     */
    @Transactional
    public String updatePriceCityMapping(PriceCityMappingEntity entity, String priceCityType) {
        boolean deleteHis = false;
        boolean addNew = false;
        entity.setId(UUIDUtil.getUUID());
        entity.setActive(MiserConstants.ACTIVE);
        entity.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
        entity.setCreateDate(new Date());
        entity.setModifyDate(new Date());
        entity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
        entity.setCityType(priceCityType);
        //查询此省市区县是否存在映射关系
        long count = priceCityMappingDao.queryPriceCityMappingByEntity(entity);
        //如果此省市区县存在价格城市
        if (count > 0) {
            //作废旧的映射关系
            priceCityMappingDao.deletePriceCityMappingByEntity(entity);
            deleteHis = true;
        }
        //将新的映射关系插入到映射关系表
        if ("SEND".equals(priceCityType)) {
            entity.setPriceCityCode(entity.getStartPriceCityCode());
        } else if ("ARRIVAL".equals(priceCityType)){
            entity.setPriceCityCode(entity.getArrivalPriceCityCode());
        } else {
            throw new BusinessException(MessageType.ERROR_ISEXIST,"未知价格城市类型" + priceCityType);
        }
        //如果修改后的映射关系中，价格城市编号为空，不需要插入
        if (!StringUtils.isEmpty(entity.getPriceCityCode())) {
            priceCityMappingDao.addPriceCityMapping(entity);
            addNew = true;
        }
        if (deleteHis) {
            if (addNew) {
                return BizBaseConstant.UPDATE_RECORD_COVER;
            } else {
                return BizBaseConstant.UPDATE_RECORD_DELETE;
            }
        } else {
            if (addNew) {
                return BizBaseConstant.UPDATE_RECORD_NEW_ADD;
            } else {
                return BizBaseConstant.UPDATE_RECORD_NONE;
            }
        }
    }

    /**
     * 映射关系导入
     * @param path
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Map<String,Object> priceCityMappingImport(String path) throws Exception {

        //导入返回结果
        Map<String,Object> resultMap=new HashMap<String, Object>();
        //处理出发价格城市映射的返回结果
        Map<String,Object> resultStartMap;
        //处理到达价格城市映射的返回结果
        Map<String,Object> resultArrivalMap;
        //失败记录列表
        List<PriceCityMappingEntity> errorsList = new ArrayList<PriceCityMappingEntity>();

        //从excel读入的内容
        List<Map<String, String>> list = ExcelUtil.readToListByFile(path, 10, 2);

        //将excel内容转换成对象列表
        List<PriceCityMappingEntity> pseList = new ArrayList<PriceCityMappingEntity>();

        for (Map<String, String> map : list) {
            PriceCityMappingEntity entity = new PriceCityMappingEntity();
            try {
                entity.setActive("Y");
                entity.setProvinceName(StringUtil.trim(map.get(0+"")));
                entity.setCityName(StringUtil.trim(map.get(1+"")));
                entity.setAreaName(StringUtil.trim(map.get(2+"")));
                entity.setStartPriceCity(StringUtil.trim(map.get(3+"")));
                entity.setArrivePriceCity(StringUtil.trim(map.get(4+"")));
                entity.setRemarks(StringUtil.trim(map.get(5+"")));
                pseList.add(entity);
            } catch (Exception e) {
                //记录出发失败
                entity.setCityType("SEND");
                entity.setErrorMsg("excel格式错误");
                errorsList.add(entity);
                //记录到达失败
                PriceCityMappingEntity arrivalError = new PriceCityMappingEntity();
                arrivalError.setProvinceName(entity.getPriceCityName());
                arrivalError.setCityName(entity.getCityName());
                arrivalError.setAreaName(entity.getAreaName());
                arrivalError.setCityType("ARRIVAL");
                arrivalError.setStartPriceCity(entity.getArrivePriceCity());
                arrivalError.setRemarks(entity.getRemarks());
                arrivalError.setErrorMsg("excel格式错误");
                errorsList.add(arrivalError);
                entity = null;// 如果有异常就把pse设为null,这样每条信息都加进去了
                logger.error("批量导入模版数据异常，业务需要仅作提示", e);
            }
        }
        //1.定义一个startPriceCityList集合用于把execel按照起运城市分组
        Map<String, List<PriceCityMappingEntity>> startPriceCityList = new HashMap<String, List<PriceCityMappingEntity>>();

        //2.定义一个arrivePriceCityList集合用于把execel按照起运城市分组
        Map<String, List<PriceCityMappingEntity>> arrivePriceCityList = new HashMap<String, List<PriceCityMappingEntity>>();

        //返回的处理好的集合对象,以chinese为值保存到startPriceCityList中
        for (Iterator it = pseList.iterator(); it.hasNext();){
            //将循环读取的结果放入对象中
            PriceCityMappingEntity priceCityMappingEntity = (PriceCityMappingEntity) it.next();
            //按起运城市分组  如果在这个map中包含有相同的键，这创建一个集合将其存起来
            if (startPriceCityList.containsKey(priceCityMappingEntity.getStartPriceCity())){
                startPriceCityList.get(priceCityMappingEntity.getStartPriceCity()).add(priceCityMappingEntity);
            } else {//如果没有包含相同的键，在创建一个集合保存数据
                List<PriceCityMappingEntity> districts = new ArrayList<PriceCityMappingEntity>();
                districts.add(priceCityMappingEntity);
                startPriceCityList.put(priceCityMappingEntity.getStartPriceCity(), districts);
            }
            //按到达城市分组
            if (arrivePriceCityList.containsKey(priceCityMappingEntity.getArrivePriceCity())){
                arrivePriceCityList.get(priceCityMappingEntity.getArrivePriceCity()).add(priceCityMappingEntity);
            } else {//如果没有包含相同的键，在创建一个集合保存数据
                List<PriceCityMappingEntity> districts = new ArrayList<PriceCityMappingEntity>();
                districts.add(priceCityMappingEntity);
                arrivePriceCityList.put(priceCityMappingEntity.getArrivePriceCity(), districts);
            }
        }

        //更新映射关系
        resultStartMap = addOrUpdatePriceCityMapping(startPriceCityList.entrySet(), "SEND");
        resultArrivalMap = addOrUpdatePriceCityMapping(startPriceCityList.entrySet(), "ARRIVAL");

        //记录更新结果
        resultMap.put("addStartPriceCitySize", resultStartMap.get("addPriceCitySize"));
        resultMap.put("coverStartPriceCitySize", resultStartMap.get("coverPriceCitySize"));
        resultMap.put("addArrivePriceCitySize", resultArrivalMap.get("addPriceCitySize"));
        resultMap.put("coverArrivePriceCitySize", resultArrivalMap.get("coverPriceCitySize"));

        //失败记录生成文件
        errorsList.addAll((List<PriceCityMappingEntity>)resultStartMap.get("errorEpsList"));
        errorsList.addAll((List<PriceCityMappingEntity>)resultArrivalMap.get("errorEpsList"));
        if(errorsList.size()>0){
            List<String> titleNames=new ArrayList<String>();
            titleNames.add("行政省份");
            titleNames.add("行政城市");
            titleNames.add("行政区域");
            titleNames.add("城市类型");
            titleNames.add("价格城市");
            titleNames.add("备注");
            titleNames.add("异常信息");
            List<String> paramNames=new ArrayList<String>();
            paramNames.add("provinceName");
            paramNames.add("cityName");
            paramNames.add("areaName");
            paramNames.add("cityType");
            paramNames.add("startPriceCity");
            paramNames.add("remarks");
            paramNames.add("errorMsg");
            String errorFilePath = new ExcelUtil<PriceCityMappingEntity>().exportExcel(titleNames, paramNames, errorsList);
            resultMap.put("errorFilePath", errorFilePath);
        }
        return resultMap;
    }

    /**
     * 将处理好的数据更新到映射关系表
     * @param set   价格城市对应的省市区县列表
     * @param cityType  价格城市类型
     * @return  处理成功、失败条数及失败记录明细
     * @throws IOException
     */
    @Transactional
    private Map<String, Object> addOrUpdatePriceCityMapping(Set<Entry<String, List<PriceCityMappingEntity>>> set, String cityType) throws IOException {
        Map<String,Object> resultMap=new HashMap<String, Object>();
        String filePath="";
        List<PriceCityMappingEntity> errorEpsList=new ArrayList<PriceCityMappingEntity>();
        long addPriceCitySize = 0; //新增的价格城市映射关系数量
        long coverPriceCitySize = 0;   //修改的价格城市映射关系数量
        //缓存价格城市名称对应编码
        Map<String, String> priceCityNameAndCodes = new HashMap<String, String>();
        //1.1遍历priceCityList，根据价格城市名称插入数据
        for (Map.Entry<String, List<PriceCityMappingEntity>> m : set) {
            String priceCityName = m.getKey();    //价格城市名
            String priceCityCode = null;
            String key = priceCityName + "~!@" + cityType;
            if (priceCityNameAndCodes.containsKey(key)) {
                priceCityCode = priceCityNameAndCodes.get(key);
            } else {
                //查询此价格城市名称是否存在,如果不存在则新插入一条价格城市信息
                PriceCityEntity priceCityEntity = new PriceCityEntity();
                priceCityEntity.setName(priceCityName);
                priceCityEntity.setType(cityType);
                PriceCityVo priceCityVo = new PriceCityVo();
                priceCityEntity.setPriceCityScope(PRICECITYSCOPE);
                priceCityVo.setQueryParam(priceCityEntity);
                PriceCityEntity priceCityEntityExistsResult = priceCityService.queryPriceCityByName(priceCityVo);
                if (priceCityEntityExistsResult != null) {
                    priceCityNameAndCodes.put(key, priceCityEntityExistsResult.getCode());
                    priceCityCode = priceCityEntityExistsResult.getCode();
                } else {
                    priceCityCode = serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_CITY_CODE);
                    priceCityNameAndCodes.put(key, priceCityCode);
                    priceCityService.addPriceCity(priceCityVo);
                }
            }

            List<PriceCityMappingEntity> synList = m.getValue();    //价格城市对应的省市区县列表
            for (PriceCityMappingEntity priceCityMappingEntity : synList) {
                try {
                    //查询省市区对应的code
                    PriceCityMappingEntity price= priceCityMappingDao.queryCodeByEntity(priceCityMappingEntity);
                    if (price != null) {
                        priceCityMappingEntity.setProvinceCode(price.getProvinceCode());
                        priceCityMappingEntity.setCityCode(price.getCityCode());
                        priceCityMappingEntity.setAreaCode(price.getAreaCode());
                    } else {
                        throw new RuntimeException("没有找到对应的省市区");
                    }

                    priceCityMappingEntity.setCityType(cityType);
                    if ("SEND".equals(cityType)) {
                        priceCityMappingEntity.setStartPriceCityCode(priceCityCode);
                    } else if ("ARRIVAL".equals(cityType)) {
                        priceCityMappingEntity.setArrivalPriceCityCode(priceCityCode);
                    }
                    //更新映射关系并返回更新情况
                    String updateResult = updatePriceCityMapping(priceCityMappingEntity, cityType);
                    if (BizBaseConstant.UPDATE_RECORD_NEW_ADD.equals(updateResult)) {
                        addPriceCitySize ++;
                    } else {
                        coverPriceCitySize ++;
                    }
                } catch (Exception e) {
                    logger.error("批量导入模版数据更新操作异常，业务需要仅作提示", e);
                    PriceCityMappingEntity eps=new PriceCityMappingEntity();
                    eps.setProvinceName(priceCityMappingEntity.getProvinceName());
                    eps.setCityName(priceCityMappingEntity.getCityName());
                    eps.setAreaName(priceCityMappingEntity.getAreaName());
                    eps.setCityType(cityType);
                    //出发和到达失败时候记录的价格城市名称全部放到startPriceCity属性中
                    if ("SEND".equals(cityType)) {
                        eps.setStartPriceCity(priceCityMappingEntity.getStartPriceCity());
                    } else if ("ARRIVAL".equals(cityType)) {
                        eps.setStartPriceCity(priceCityMappingEntity.getArrivePriceCity());
                    }
                    eps.setRemarks(priceCityMappingEntity.getRemarks());
                    eps.setErrorMsg(e.getMessage());
                    errorEpsList.add(eps);
                }
            }
        }
        resultMap.put("addPriceCitySize", addPriceCitySize);
        resultMap.put("coverPriceCitySize", coverPriceCitySize);
        resultMap.put("errorEpsList", errorEpsList);
        return resultMap;
    }

    /**
     * 根据查询出来的数据生成excel导出文件
     * @param list  需要导出的数据
     * @return 生成excel文件路径
     */
    @Override
    public String createPriceCityMappingExcelFile(List<PriceCityMappingEntity> list) {
        String filePath=null;
        List<String> titleNames=new ArrayList<String>();
        //titleNames.add("序号");
        titleNames.add("行政省份");
        titleNames.add("行政城市");
        titleNames.add("行政区域");
        titleNames.add("是否有网点");
        titleNames.add("出发价格城市");
        titleNames.add("到达价格城市");
        titleNames.add("备注");
        titleNames.add("修改人");
        titleNames.add("修改时间");
        List<String> paramNames=new ArrayList<String>();
        paramNames.add("provinceName");
        paramNames.add("cityName");
        paramNames.add("areaName");
        paramNames.add("isLocations");
        paramNames.add("startPriceCity");
        paramNames.add("arrivePriceCity");
        paramNames.add("remarks");
        paramNames.add("modifyUserCode");
        paramNames.add("modifyTime");
        try {
            filePath=new ExcelUtil<PriceCityMappingEntity>().exportExcel(titleNames,paramNames,list);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("生成导出数据异常!", e);
            filePath=null;
        }

        return filePath;
    }

    /**
     * 修改时查询数据
     * @param entity
     * @return
     */
    @Override
    public PriceCityMappingEntity queryPriceCityMappingInUpdate(PriceCityMappingEntity entity) {
        entity.setActive(MiserConstants.ACTIVE);
        return priceCityMappingDao.queryPriceCityInUpdate(entity);
    }

}

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
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingCustomerEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
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
import com.hoau.miser.module.biz.base.api.server.service.IPriceCityMappingCustomerService;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.server.cache.PriceCityMappingCustomerCache;
import com.hoau.miser.module.biz.base.server.dao.PriceCityMappingCustomerDao;
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
public class PriceCityMappingCustomerService implements IPriceCityMappingCustomerService {
    private static final Logger logger = LoggerFactory
            .getLogger(PriceCityMappingCustomerService.class);
    @Resource
    private PriceCityMappingCustomerDao priceCityMappingCustomerDao;
    @Resource
    private ISerialNumberService serialNumberService;
    @Resource
    private IPriceCityService priceCityService;

    
    private static final String PRICECITYSCOPE = "CUSTOMER";
    
    /**
     * 页面查询映射关系列表
     * @param pricePriceEntity
     * @param limit
     * @param start
     * @return
     */
    @Override
    public List<PriceCityMappingCustomerEntity> queryPriceCityCustomerByEntity(
            PriceCityMappingCustomerEntity pricePriceEntity, int limit, int start) {
        RowBounds rowBounds =new RowBounds(start,limit);
        return priceCityMappingCustomerDao.queryPriceCityByEntity(pricePriceEntity, rowBounds);
    }

    @SuppressWarnings("unchecked")
    private ICache<String, PriceCityMappingCustomerEntity> getPriceCityCache(){
        ICache<String, PriceCityMappingCustomerEntity> priceCityCache = CacheManager.getInstance().getCache(PriceCityMappingCustomerCache.PRICE_CITY_CUSTOMER_CACHE_UUID);
        return priceCityCache;
    }

    /**
     * 界面查询条件的查询结果总条数
     * @param pricePriceEntity
     * @return
     */
    @Override
    public Long countPriceCityCustomerByEntity(PriceCityMappingCustomerEntity pricePriceEntity) {

        return priceCityMappingCustomerDao.countPriceCityByEntity(pricePriceEntity);
    }

    /**
     * 根据界面提交的数据更新映射关系
     * @param entity
     */
    @Override
    public void updatePriceCityCustomer(PriceCityMappingCustomerEntity entity) {
        try {
            updatePriceCityCustomer(entity, "SEND");
        } catch (Exception e) {
            throw new BusinessException(MessageType.ERROR_ISEXIST,"更新失败");
        }
        try {
            updatePriceCityCustomer(entity, "ARRIVAL");
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
    public String updatePriceCityCustomer(PriceCityMappingCustomerEntity entity, String priceCityType) {
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
        long count = priceCityMappingCustomerDao.queryPriceCityMappingCustomerByEntity(entity);
        //如果此省市区县存在价格城市
        if (count > 0) {
            //作废旧的映射关系
        	priceCityMappingCustomerDao.deletePriceCityMappingCustomerByEntity(entity);
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
        	priceCityMappingCustomerDao.addPriceCityMappingCustomer(entity);
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
    public Map<String,Object> priceCityCustomerImport(String path) throws Exception {

        //导入返回结果
        Map<String,Object> resultMap=new HashMap<String, Object>();
        //处理出发价格城市映射的返回结果
        Map<String,Object> resultStartMap;
        //处理到达价格城市映射的返回结果
        Map<String,Object> resultArrivalMap;
        //失败记录列表
        List<PriceCityMappingCustomerEntity> errorsList = new ArrayList<PriceCityMappingCustomerEntity>();

        //从excel读入的内容
        List<Map<String, String>> list = ExcelUtil.readToListByFile(path, 10, 2);

        //将excel内容转换成对象列表
        List<PriceCityMappingCustomerEntity> pseList = new ArrayList<PriceCityMappingCustomerEntity>();

        for (Map<String, String> map : list) {
            PriceCityMappingCustomerEntity entity = new PriceCityMappingCustomerEntity();
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
                PriceCityMappingCustomerEntity arrivalError = new PriceCityMappingCustomerEntity();
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
        Map<String, List<PriceCityMappingCustomerEntity>> startPriceCityList = new HashMap<String, List<PriceCityMappingCustomerEntity>>();

        //2.定义一个arrivePriceCityList集合用于把execel按照起运城市分组
        Map<String, List<PriceCityMappingCustomerEntity>> arrivePriceCityList = new HashMap<String, List<PriceCityMappingCustomerEntity>>();

        //返回的处理好的集合对象,以chinese为值保存到startPriceCityList中
        for (Iterator it = pseList.iterator(); it.hasNext();){
            //将循环读取的结果放入对象中
            PriceCityMappingCustomerEntity priceCityCustomerEntity = (PriceCityMappingCustomerEntity) it.next();
            //按起运城市分组  如果在这个map中包含有相同的键，这创建一个集合将其存起来
            if (startPriceCityList.containsKey(priceCityCustomerEntity.getStartPriceCity())){
                startPriceCityList.get(priceCityCustomerEntity.getStartPriceCity()).add(priceCityCustomerEntity);
            } else {//如果没有包含相同的键，在创建一个集合保存数据
                List<PriceCityMappingCustomerEntity> districts = new ArrayList<PriceCityMappingCustomerEntity>();
                districts.add(priceCityCustomerEntity);
                startPriceCityList.put(priceCityCustomerEntity.getStartPriceCity(), districts);
            }
            //按到达城市分组
            if (arrivePriceCityList.containsKey(priceCityCustomerEntity.getArrivePriceCity())){
                arrivePriceCityList.get(priceCityCustomerEntity.getArrivePriceCity()).add(priceCityCustomerEntity);
            } else {//如果没有包含相同的键，在创建一个集合保存数据
                List<PriceCityMappingCustomerEntity> districts = new ArrayList<PriceCityMappingCustomerEntity>();
                districts.add(priceCityCustomerEntity);
                arrivePriceCityList.put(priceCityCustomerEntity.getArrivePriceCity(), districts);
            }
        }

        //更新映射关系
        resultStartMap = addOrUpdatePriceCityCustomer(startPriceCityList.entrySet(), "SEND");
        resultArrivalMap = addOrUpdatePriceCityCustomer(startPriceCityList.entrySet(), "ARRIVAL");

        //记录更新结果
        resultMap.put("addStartPriceCitySize", resultStartMap.get("addPriceCitySize"));
        resultMap.put("coverStartPriceCitySize", resultStartMap.get("coverPriceCitySize"));
        resultMap.put("addArrivePriceCitySize", resultArrivalMap.get("addPriceCitySize"));
        resultMap.put("coverArrivePriceCitySize", resultArrivalMap.get("coverPriceCitySize"));

        //失败记录生成文件
        errorsList.addAll((List<PriceCityMappingCustomerEntity>)resultStartMap.get("errorEpsList"));
        errorsList.addAll((List<PriceCityMappingCustomerEntity>)resultArrivalMap.get("errorEpsList"));
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
            String errorFilePath = new ExcelUtil<PriceCityMappingCustomerEntity>().exportExcel(titleNames, paramNames, errorsList);
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
    private Map<String, Object> addOrUpdatePriceCityCustomer(Set<Entry<String, List<PriceCityMappingCustomerEntity>>> set, String cityType) throws IOException {
        Map<String,Object> resultMap=new HashMap<String, Object>();
        String filePath="";
        List<PriceCityMappingCustomerEntity> errorEpsList=new ArrayList<PriceCityMappingCustomerEntity>();
        long addPriceCitySize = 0; //新增的价格城市映射关系数量
        long coverPriceCitySize = 0;   //修改的价格城市映射关系数量
        //缓存价格城市名称对应编码
        Map<String, String> priceCityNameAndCodes = new HashMap<String, String>();
        //1.1遍历priceCityList，根据价格城市名称插入数据
        for (Map.Entry<String, List<PriceCityMappingCustomerEntity>> m : set) {
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
                priceCityVo.setQueryParam(priceCityEntity);
                priceCityEntity.setPriceCityScope(PRICECITYSCOPE);
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

            List<PriceCityMappingCustomerEntity> synList = m.getValue();    //价格城市对应的省市区县列表
            for (PriceCityMappingCustomerEntity priceCityCustomerEntity : synList) {
                try {
                    //查询省市区对应的code
                    PriceCityMappingCustomerEntity price= priceCityMappingCustomerDao.queryCodeByEntity(priceCityCustomerEntity);
                    if (price != null) {
                        priceCityCustomerEntity.setProvinceCode(price.getProvinceCode());
                        priceCityCustomerEntity.setCityCode(price.getCityCode());
                        priceCityCustomerEntity.setAreaCode(price.getAreaCode());
                    } else {
                        throw new RuntimeException("没有找到对应的省市区");
                    }

                    priceCityCustomerEntity.setCityType(cityType);
                    if ("SEND".equals(cityType)) {
                        priceCityCustomerEntity.setStartPriceCityCode(priceCityCode);
                    } else if ("ARRIVAL".equals(cityType)) {
                        priceCityCustomerEntity.setArrivalPriceCityCode(priceCityCode);
                    }
                    //更新映射关系并返回更新情况
                    String updateResult = updatePriceCityCustomer(priceCityCustomerEntity, cityType);
                    if (BizBaseConstant.UPDATE_RECORD_NEW_ADD.equals(updateResult)) {
                        addPriceCitySize ++;
                    } else {
                        coverPriceCitySize ++;
                    }
                } catch (Exception e) {
                    logger.error("批量导入模版数据更新操作异常，业务需要仅作提示", e);
                    PriceCityMappingCustomerEntity eps=new PriceCityMappingCustomerEntity();
                    eps.setProvinceName(priceCityCustomerEntity.getProvinceName());
                    eps.setCityName(priceCityCustomerEntity.getCityName());
                    eps.setAreaName(priceCityCustomerEntity.getAreaName());
                    eps.setCityType(cityType);
                    //出发和到达失败时候记录的价格城市名称全部放到startPriceCity属性中
                    if ("SEND".equals(cityType)) {
                        eps.setStartPriceCity(priceCityCustomerEntity.getStartPriceCity());
                    } else if ("ARRIVAL".equals(cityType)) {
                        eps.setStartPriceCity(priceCityCustomerEntity.getArrivePriceCity());
                    }
                    eps.setRemarks(priceCityCustomerEntity.getRemarks());
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
    public String createPriceCityCustomerExcelFile(List<PriceCityMappingCustomerEntity> list) {
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
            filePath=new ExcelUtil<PriceCityMappingCustomerEntity>().exportExcel(titleNames,paramNames,list);
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
    public PriceCityMappingCustomerEntity queryPriceCityCustomerInUpdate(PriceCityMappingCustomerEntity entity) {
        entity.setActive(MiserConstants.ACTIVE);
        return priceCityMappingCustomerDao.queryPriceCityInUpdate(entity);
    }

}

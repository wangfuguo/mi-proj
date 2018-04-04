package com.hoau.miser.module.biz.base.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.miser.module.biz.base.api.server.service.IPriceCityService;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingCustomerEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.PriceCityException;
import com.hoau.miser.module.biz.base.api.shared.vo.ExcelPriceCityVo;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityVo;
import com.hoau.miser.module.biz.base.server.cache.PriceCityCache;
import com.hoau.miser.module.biz.base.server.cache.PriceCityMappingCache;
import com.hoau.miser.module.biz.base.server.dao.PriceCityDao;
import com.hoau.miser.module.common.server.service.ISerialNumberService;
import com.hoau.miser.module.common.shared.define.SerialNumberRuleEnum;
import com.hoau.miser.module.common.shared.define.TransportType;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PriceCityService
 * @Description: 价格城市维护
 * @author: Chenyl yulin.chen@hoau.net
 * @date: 2016/3/9 18:44
 */
@Service
public class PriceCityService implements IPriceCityService {

    @Resource
    PriceCityDao priceCityDao;
    @Resource
    private ISerialNumberService serialNumberService;

    /**
     * @param priceCityVo
     * @param limit
     * @param start
     * @return
     * @Description 查询价格城市列表
     */
    @Override
    public ArrayList<PriceCityEntity> queryPriceCities(PriceCityVo priceCityVo, int limit, int start) {
        PriceCityEntity queryParam = priceCityVo.getQueryParam();
        RowBounds rowBounds = new RowBounds(start, limit);
        return priceCityDao.queryPriceCities(queryParam, rowBounds);
    }

    /**
     * @Description 查询所有出发价格城市列表
     * @return
     */
    @Override
    public ArrayList<PriceCityEntity> queryAllStartStandardPriceCities() {
        PriceCityEntity queryParam = new PriceCityEntity();
        queryParam.setType("SEND");
        queryParam.setPriceCityScope("STANDARD");
        queryParam.setActive(MiserConstants.ACTIVE);
        return priceCityDao.queryPriceCities(queryParam, new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT));
    }
    
    /**
     * @Description 查询所有出发价格城市列表
     * @return
     */
    @Override
    public ArrayList<PriceCityEntity> queryAllStartPriceCities() {
        PriceCityEntity queryParam = new PriceCityEntity();
        queryParam.setType("SEND");
        queryParam.setActive(MiserConstants.ACTIVE);
        return priceCityDao.queryPriceCities(queryParam, new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT));
    }

    /**
     * @Description 查询所有到达价格城市列表
     * @return
     */
    @Override
    public ArrayList<PriceCityEntity> queryAllArrivalPriceCities() {
        PriceCityEntity queryParam = new PriceCityEntity();
        queryParam.setType("ARRIVAL");
        queryParam.setActive(MiserConstants.ACTIVE);
        return priceCityDao.queryPriceCities(queryParam, new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT));
    }
    
    /**
     * @Description 查询所有到达价格城市列表
     * @return
     */
    @Override
    public ArrayList<PriceCityEntity> queryAllArrivalStandardPriceCities() {
        PriceCityEntity queryParam = new PriceCityEntity();
        queryParam.setType("ARRIVAL");
        queryParam.setPriceCityScope("STANDARD");
        queryParam.setActive(MiserConstants.ACTIVE);
        return priceCityDao.queryPriceCities(queryParam, new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT));
    }

    /**
     * @param priceCityVo
     * @return
     * @Description 查询价格城市数量
     */
    @Override
    public Long queryPriceCityCount(PriceCityVo priceCityVo) {
        PriceCityEntity queryParam = priceCityVo.getQueryParam();
        return priceCityDao.queryPriceCityCount(queryParam);
    }

    /**
     * @param priceCityVo
     * @return
     * @Description 根据ID查询价格城市
     */
    @Override
    public PriceCityEntity queryPriceCityById(PriceCityVo priceCityVo) {
        PriceCityEntity queryParam = priceCityVo.getQueryParam();
        return priceCityDao.queryPriceCityById(queryParam);
    }

    /**
     * @Description 根据名称查询有效的价格城市
     * @param priceCityVo
     * @return
     */
    @Override
    public PriceCityEntity queryPriceCityByName(PriceCityVo priceCityVo) {
        ICache<String, PriceCityEntity> priceCityCache = this.getPriceCityCache();
        PriceCityEntity queryParam = priceCityVo.getQueryParam();
        String name = queryParam.getName();
        String type = queryParam.getType();
        String priceCityScope = queryParam.getPriceCityScope();
        PriceCityEntity entity = priceCityCache.get(name + "~!@" + type + "~!@" + priceCityScope );
        return entity;
    }

    /**
     * 获取价格城市缓存，根据名称和城市类型进行缓存
     * @return
     */
    private ICache<String, PriceCityEntity> getPriceCityCache(){
        ICache<String, PriceCityEntity> priceCityCache = CacheManager.getInstance().getCache(PriceCityCache.PRICECITY_CACHE_UUID);
        return priceCityCache;
    }

    /**
     * @param priceCityVo
     * @Description 新增价格城市
     */
    @Override
    @Transactional
    public void addPriceCity(PriceCityVo priceCityVo) {
        PriceCityEntity queryParam = priceCityVo.getQueryParam();
        PriceCityEntity priceCityEntity = priceCityDao.queryPriceCityByName(queryParam);
        //如果根据名称查询到了，不允许插入
        if (priceCityEntity != null) {
            throw new PriceCityException(PriceCityException.TRANSTYPE_NAME_CONFLICT);
        } else {
            insertPriceCity(queryParam);
        }
    }

    /**
     * @param priceCityVo
     * @Description 更新价格城市
     */
    @Override
    @Transactional
    public void updatePriceCity(PriceCityVo priceCityVo) {
        PriceCityEntity queryParam = priceCityVo.getQueryParam();
        PriceCityEntity priceCityEntity = priceCityDao.queryPriceCityByName(queryParam);
        //如果根据名称查询到了
        if (priceCityEntity != null) {
            //判断查询到的那条的id和本次修改的是否相同，如果相同则作废旧数据,否则不允许修改
            if (priceCityEntity.getId().equals(queryParam.getId())) {
                // 先删除本条
                deletePriceCity(priceCityEntity);
                //再插入新的一条,Code保持不变
                queryParam.setCode(priceCityEntity.getCode());
                insertPriceCity(queryParam);
            } else {
                throw new PriceCityException(PriceCityException.TRANSTYPE_NAME_CONFLICT);
            }
        } else { //名称没有查询到
            //如果有id，说明是新增，不需要删除
            if (!StringUtils.isEmptyWithBlock(queryParam.getId())) {
                // 先删除本条
                deletePriceCity(queryParam);
            } else {
                queryParam.setCode(null);
            }
            //再插入新的一条
            insertPriceCity(queryParam);
        }
    }

    /**
     * @Description 删除价格城市
     * @param priceCityEntity
     */
    @Transactional
    public void deletePriceCity(PriceCityEntity priceCityEntity) {
        priceCityEntity.setModifyDate(new Date());
        priceCityEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
        priceCityEntity.setInvalid(MiserConstants.ACTIVE);
        priceCityEntity.setActive(MiserConstants.INACTIVE);
        priceCityDao.deletePriceCity(priceCityEntity);
    }

    /**
     * @Description 插入价格城市
     * @param priceCityEntity
     */
    @Transactional
    public void insertPriceCity(PriceCityEntity priceCityEntity) {
        Date date = new Date();
        //因为excel批量导入的时候可能需要新增价格城市，调用此service方法的时候会先生成code设置到priceCityEntity中，所以不能重复设置
        if (StringUtils.isEmpty(priceCityEntity.getCode())) {
            priceCityEntity.setCode(serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_CITY_CODE));
        }
        priceCityEntity.setId(UUIDUtil.getUUID());
        priceCityEntity.setInvalid(MiserConstants.INACTIVE);
        priceCityEntity.setActive(MiserConstants.ACTIVE);
        priceCityEntity.setCreateDate(date);
        priceCityEntity.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
        priceCityEntity.setModifyDate(date);
        priceCityEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
        priceCityDao.addPriceCity(priceCityEntity);
    }

    /**
     * @param priceCityVo
     * @Description 作废价格城市
     */
    @Override
    @Transactional
    public void invalidPriceCity(PriceCityVo priceCityVo) {
        PriceCityEntity priceCityEntity = priceCityVo.getQueryParam();
        priceCityEntity.setModifyDate(new Date());
        priceCityEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
        priceCityEntity.setInvalid(MiserConstants.ACTIVE);
        priceCityEntity.setActive(MiserConstants.ACTIVE);
        priceCityDao.invalidPriceCity(priceCityEntity);
    }

	@Override
	public ArrayList<PriceCityEntity> excelPriceCity(PriceCityVo pcv) {
		return priceCityDao.queryPriceCitiesForExport(pcv.getQueryParam());
	}

	@Override
	public String createExcelFile(List<PriceCityEntity> list)
			throws IOException {
		List<String> titleNames = new ArrayList<String>();
		titleNames.add("城市编号");
		titleNames.add("城市名称");
		titleNames.add("城市类型");
		titleNames.add("是否作废");
		titleNames.add("备注");
		
		titleNames.add("创建人");
		titleNames.add("创建时间");
		titleNames.add("修改人");
		titleNames.add("修改时间");
		
		List<String> paramNames = new ArrayList<String>();
		
		paramNames.add("code");
		paramNames.add("name");
		paramNames.add("type");
		paramNames.add("invalid");
		paramNames.add("remark");
		
		paramNames.add("createUser");
		paramNames.add("createDate");
		paramNames.add("modifyUser");
		paramNames.add("modifyDate");
		
		return new ExcelUtil<PriceCityEntity>().exportExcel(titleNames, paramNames, list);
	}

	@Override
	public Map<String, Object> bathImplPriceCity(String filePath,String piceCityScope) throws Exception {
		List<Map<String,String>> list = ExcelUtil.readToListByFile(filePath, 3, 2);
		
		int failCount = 0;
		Integer succCount = 0;
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Long> repeatMap = new HashMap<String, Long>();
		List<PriceCityEntity> pceList = new ArrayList<PriceCityEntity>();
		List<ExcelPriceCityVo> errorList = new ArrayList<ExcelPriceCityVo>();
		int tmp = 1 ;
		for (Map<String, String> map : list) {
			tmp++;
			PriceCityEntity pce = new PriceCityEntity();
			pce.setName(map.get("0"));
			pce.setType(gainTransportType(map.get("1")));
			pce.setRemark(map.get("2"));
			pce.setPriceCityScope(piceCityScope);

			if(StringUtils.isEmptyWithBlock(pce.getName())){
				retMap.put("error", "城市名称不能为空");
				//break;
			}
			if(StringUtils.isEmptyWithBlock(pce.getType())){
				retMap.put("error", "不存在运输类型【"+map.get("1")+ "】");
				//break;
			}
	        PriceCityEntity priceCityEntity = priceCityDao.queryPriceCityByName(pce);
	       
	        String repeatKey = pce.getName() + ":"
					+ pce.getType() + ":" + pce.getPriceCityScope();
			 if(repeatMap.containsKey(repeatKey)){
				 failCount ++;
				 retMap.put("error", "与Excel表中的城市重复");
				 //break;
			 }else{
				if (priceCityEntity != null) {
					failCount ++;
					retMap.put("error","与数据库中的城市重复");
					//break;
			    }
			 }
			 if(retMap.get("error")!=null && !retMap.get("error").equals("")){
					ExcelPriceCityVo ecv = new ExcelPriceCityVo();
					 ecv.setCityName(map.get("0"));
					 ecv.setCityType(map.get("1"));
					 ecv.setRemark(map.get("2"));
					 ecv.setOldSerial(String.valueOf(tmp));
					 ecv.setErrorMsg(retMap.get("error").toString());
					 errorList.add(ecv);
					 continue;
			 }
	        
	    	 Date date = new Date();
	         //因为excel批量导入的时候可能需要新增价格城市，调用此service方法的时候会先生成code设置到priceCityEntity中，所以不能重复设置
	         if (StringUtils.isEmpty(pce.getCode())) {
	        	 pce.setCode(serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_CITY_CODE));
	         }
	         pce.setId(UUIDUtil.getUUID());
	         pce.setInvalid(MiserConstants.INACTIVE);
	         pce.setActive(MiserConstants.ACTIVE);
	         pce.setCreateDate(date);
	         pce.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	         pce.setModifyDate(date);
	         pce.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	         repeatMap.put(repeatKey, 1L);
		     pceList.add(pce);
	        
		}
		retMap.put("error",""); 
		if(StringUtils.isEmptyWithBlock(retMap.get("error")==null ? "":retMap.get("error").toString())){
			if(pceList.size() > 0){
				succCount = priceCityDao.insertBatch(pceList);
				retMap.put("error", "");
			}
		}
		retMap.put("succCount", succCount);
		retMap.put("failCount", failCount);
		retMap.put("fileUrl","");
		if(errorList.size()>0){
			 List<String> titleNames=new ArrayList<String>();
             titleNames.add("城市名称");
             titleNames.add("城市类型");
             titleNames.add("备注");
             titleNames.add("原序列号");
             titleNames.add("异常信息");
             
             List<String> paramNames=new ArrayList<String>();
             paramNames.add("cityName");
             paramNames.add("cityType");
             paramNames.add("remark");
             paramNames.add("oldSerial");
             paramNames.add("errorMsg");
             String errorFilePath = new ExcelUtil<ExcelPriceCityVo>().exportExcel(titleNames, paramNames, errorList);
             retMap.put("fileUrl",errorFilePath);
		}
		
		return retMap;
	}
	
	/**
	 * 
	 * @Description: TODO 到运输类型代码
	 * @param @param name
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年4月11日
	 */
	private String gainTransportType(String name){
		TransportType[] dt = TransportType.values();
		for (TransportType transportType : dt) {
			if(transportType.getName().equals(name)){
				return transportType.getCode();
			}
		}
		return null;
	}
	

}

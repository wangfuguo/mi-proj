package com.hoau.miser.module.biz.base.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.miser.module.biz.base.api.server.service.ITimeCityService;
import com.hoau.miser.module.biz.base.api.shared.domain.TimeCityEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.PriceCityException;
import com.hoau.miser.module.biz.base.api.shared.vo.ExcelPriceCityVo;
import com.hoau.miser.module.biz.base.server.cache.TimeCityCache;
import com.hoau.miser.module.biz.base.server.dao.TimeCityDao;
import com.hoau.miser.module.common.server.service.ISerialNumberService;
import com.hoau.miser.module.common.shared.define.SerialNumberRuleEnum;
import com.hoau.miser.module.common.shared.define.TransportType;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class TimeCityService implements ITimeCityService {
	
    @Resource
    private ISerialNumberService serialNumberService;
	@Resource
	private TimeCityDao timeCityDao;

	@Override
	public List<TimeCityEntity> queryByEntity(TimeCityEntity entity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return timeCityDao.queryByEntity(entity, rowBounds);
	}

	@Override
	public Long countOfTimeCity(TimeCityEntity entity) {
		return timeCityDao.countOfTimeCity(entity);
	}

	@Override
	public TimeCityEntity queryById(TimeCityEntity entity) {
		return timeCityDao.queryById(entity);
	}

	@Override
	public TimeCityEntity queryTimeCityByName(TimeCityEntity entity) {
        ICache<String, TimeCityEntity> timeCityCache = this.getTimeCityCache();
        String name = entity.getName();
        String type = entity.getType();
        TimeCityEntity timeCityEntity = timeCityCache.get(name + "~!@" + type );
        return timeCityEntity;
    }
	
	/**
     * 获取价格城市缓存，根据名称和城市类型进行缓存
     * @return
     */
    private ICache<String, TimeCityEntity> getTimeCityCache(){
        ICache<String, TimeCityEntity> timeCityCache = CacheManager.getInstance().getCache(TimeCityCache.TIMECITY_CACHE_UUID);
        return timeCityCache;
    }
	
	@Override
	public List<TimeCityEntity> queryAllStartTimeCities() {
		TimeCityEntity entity  = new TimeCityEntity();
		entity.setType("SEND");
		entity.setActive(MiserConstants.ACTIVE);
		return timeCityDao.queryByEntity(entity, new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT));
	}

	@Override
	public List<TimeCityEntity> queryAllArrivalTimeCities() {
		TimeCityEntity entity  = new TimeCityEntity();
		entity.setType("ARRIVAL");
		entity.setActive(MiserConstants.ACTIVE);
		return timeCityDao.queryByEntity(entity, new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT));
	}

	@Override
	@Transactional
	public void updatetimeCity(TimeCityEntity entity) {
		TimeCityEntity timeCityEntity = timeCityDao.queryByName(entity);
        //如果根据名称查询到了
        if (timeCityEntity != null) {
            //判断查询到的那条的id和本次修改的是否相同，如果相同则作废旧数据,否则不允许修改
            if (timeCityEntity.getId().equals(entity.getId())) {
                // 先作废本条
            	invalidTimeCity(timeCityEntity);
                //再插入新的一条,Code保持不变
                entity.setCode(timeCityEntity.getCode());
                insertTimeCity(entity);
            } else {
                throw new PriceCityException(PriceCityException.TRANSTYPE_NAME_CONFLICT);
            }
        } else { //名称没有查询到
            //如果有id，说明是新增，不需要作废
            if (!StringUtils.isEmptyWithBlock(entity.getId())) {
                // 先作废本条
            	invalidTimeCity(entity);
            } else {
            	entity.setCode(null);
            }
            //再插入新的一条
            insertTimeCity(entity);
        }
    }
	
	 /**
     * @Description 删除价格城市
     * @param TimeCityEntity
     */
    @Transactional
    public void invalidTimeCity(TimeCityEntity entity) {
    	entity.setModifyTime(new Date());
    	entity.setModifyUserCode(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
        timeCityDao.invalidTimeCity(entity);
    }
    
    
    /**
     * @Description 插入价格城市
     * @param TimeCityEntity
     */
    @Transactional
    public void insertTimeCity(TimeCityEntity entity) {
        Date date = new Date();
        //因为excel批量导入的时候可能需要新增价格城市，调用此service方法的时候会先生成code设置到TimeCityEntity中，所以不能重复设置
        if (StringUtils.isEmpty(entity.getCode())) {
        	entity.setCode(serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_CITY_CODE));
        }
        entity.setId(UUIDUtil.getUUID());
        entity.setInvalid(MiserConstants.INACTIVE);
        entity.setActive(MiserConstants.ACTIVE);
        entity.setCreateTime(date);
        entity.setCreateUserCode(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
        entity.setModifyTime(date);
        entity.setModifyUserCode(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
        timeCityDao.addTimeCity(entity);
    }

	@Override
	public ArrayList<TimeCityEntity> excelTimeCity(TimeCityEntity entity) {
		return timeCityDao.queryForExport(entity);
		}

	@Override
	public String createExcelFile(List<TimeCityEntity> list) throws IOException {
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
		
		paramNames.add("createUserCode");
		paramNames.add("createTime");
		paramNames.add("modifyUserCode");
		paramNames.add("modifyTime");
		
		return new ExcelUtil<TimeCityEntity>().exportExcel(titleNames, paramNames, list);
	}

	@Override
	public Map<String, Object> bathImplTimeCity(String filePath) throws Exception {
		List<Map<String,String>> list = ExcelUtil.readToListByFile(filePath, 3, 2);
		
		int failCount = 0;
		Integer succCount = 0;
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Long> repeatMap = new HashMap<String, Long>();
		List<TimeCityEntity> pceList = new ArrayList<TimeCityEntity>();
		List<ExcelPriceCityVo> errorList = new ArrayList<ExcelPriceCityVo>();
		int tmp = 1 ;
		for (Map<String, String> map : list) {
			tmp++;
			TimeCityEntity pce = new TimeCityEntity();
			pce.setName(map.get("0"));
			pce.setType(gainTransportType(map.get("1")));
			pce.setRemark(map.get("2"));

			if(StringUtils.isEmptyWithBlock(pce.getName())){
				retMap.put("error", "城市名称不能为空");
				//break;
			}
			if(StringUtils.isEmptyWithBlock(pce.getType())){
				retMap.put("error", "不存在运输类型【"+map.get("1")+ "】");
				//break;
			}
	        TimeCityEntity TimeCityEntity = timeCityDao.queryByName(pce);
	       
	        String repeatKey = pce.getName() + ":" + pce.getType();
			 if(repeatMap.containsKey(repeatKey)){
				 failCount ++;
				 retMap.put("error", "与Excel表中的城市重复");
				 //break;
			 }else{
				if (TimeCityEntity != null) {
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
	         //因为excel批量导入的时候可能需要新增价格城市，调用此service方法的时候会先生成code设置到TimeCityEntity中，所以不能重复设置
	         if (StringUtils.isEmpty(pce.getCode())) {
	        	 pce.setCode(serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_CITY_CODE));
	         }
	         pce.setId(UUIDUtil.getUUID());
	         pce.setInvalid(MiserConstants.INACTIVE);
	         pce.setActive(MiserConstants.ACTIVE);
	         pce.setCreateTime(date);
	         pce.setCreateUserCode(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	         pce.setModifyTime(date);
	         pce.setModifyUserCode(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	         repeatMap.put(repeatKey, 1L);
		     pceList.add(pce);
	        
		}
		retMap.put("error",""); 
		if(StringUtils.isEmptyWithBlock(retMap.get("error")==null ? "":retMap.get("error").toString())){
			if(pceList.size() > 0){
				succCount = timeCityDao.insertBatch(pceList);
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
	 * @author 何羽
	 * @date 2016年7月7日
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

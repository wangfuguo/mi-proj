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

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.IAgingCityService;
import com.hoau.miser.module.biz.base.api.shared.domain.AgingCityEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.api.shared.vo.AgingCityVo;
import com.hoau.miser.module.biz.base.server.dao.AgingCityDao;
import com.hoau.miser.module.common.server.service.ISerialNumberService;
import com.hoau.miser.module.common.shared.define.SerialNumberRuleEnum;
import com.hoau.miser.module.common.shared.domain.SerialNumberRuleEntity;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：刘海飞
 * @create：2015年12月15日 上午8:21:03
 * @description：用户service
 */
@Service
public class AgingCityService implements IAgingCityService {
	private static final Logger logger = LoggerFactory
			.getLogger(AgingCityService.class);
	@Resource
	private AgingCityDao agingCityDao;
	
	
	@Resource
	private ISerialNumberService serialNumberService;
	
	@Override
	public List<AgingCityEntity> queryAgingCityByEntity(
			AgingCityEntity priceAgingEntity, int limit, int start) {
		RowBounds rowBounds =new RowBounds(start,limit);
		return agingCityDao.queryAgingCityByEntity(priceAgingEntity, rowBounds);
	}
	@Override
	public Long countAgingCityByEntity(AgingCityEntity priceAgingEntity) {
		
		return agingCityDao.countAgingCityByEntity(priceAgingEntity);
	}
	@Override
	public void updateAgingCityEntity(AgingCityEntity entity) {
			String operUserCode =MiserUserContext.getCurrentUser().getEmpNameAndUserName();
			try {
				this.updateStartAgingCity(entity, operUserCode);
			} catch (Exception e) {
				throw new BusinessException(MessageType.ERROR_ISEXIST,"更新失败");
			}
			try {
				this.updateArriveAgingCity(entity, operUserCode);
			} catch (Exception e) {
				throw new BusinessException(MessageType.ERROR_ISEXIST,"更新失败");
			}
	}
	
	public void updateStartAgingCity(AgingCityEntity entity,String operUserCode){
		String sequence=null;//序列号
		String[] params=null;
		//1.更新出发价格城市
		entity.setId(UUIDUtil.getUUID());
		entity.setActive(MiserConstants.ACTIVE);
		entity.setCreateUser(operUserCode);
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date());
		entity.setModifyUser(operUserCode);
		entity.setAgingCityName(entity.getStartAgingCity());
		entity.setCityType("SEND");
		
		 //查询省市区对应的code
		 AgingCityEntity price= agingCityDao.queryCodeByEntity(entity);
		 entity.setProvinceCode(price.getProvinceCode());
		 entity.setCityCode(price.getCityCode());
		 entity.setAreaCode(price.getAreaCode());
		 long isExit=agingCityDao.queryAgingCityMappingByEntity(entity);//判断这个行政区域已经有了 出发价格城市
		 if("".equals(entity.getStartAgingCity())){
			 if(isExit>=1){
					//这个行政区域有了出发价格城市，则作废
					agingCityDao.deleteAgingCityMappingByEntity(entity);
			 }
		 }else{
			 /**循环价格城市相同的list*/
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("type", "SEND"); //城市类型
			 map.put("startAgingCity", entity.getStartAgingCity());
			 map.put("active",MiserConstants.ACTIVE);
			//1.1.1 判断这个出发价格城市名称是否已使用过,有则取其code值
			String code=agingCityDao.queryAgingCityByStartAgingName(map);
				
				if(code!=null&& !"".equals(code)){//如果这个出发价格城市已经有了
					
					if(isExit>=1){ 
						//这个行政区域有了出发价格城市，则作废
						agingCityDao.deleteAgingCityMappingByEntity(entity);
						//插入一条新记录
						entity.setAgingCityCode(code);
						agingCityDao.addAgingCityMapping(entity);
					}else{
						entity.setAgingCityCode(code);
						agingCityDao.addAgingCityMapping(entity);
					}
					
				}else{//没有这个出发价格城市名称,插入主表
					//获取序列
					sequence=	serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_CITY_CODE, params);
					entity.setAgingCityCode(sequence);
					agingCityDao.addAgingCity(entity);//增加主表
					
					if(isExit>=1){
						//这个行政区域有了出发价格城市，则作废
						agingCityDao.deleteAgingCityMappingByEntity(entity);
						//插入一条新记录
						agingCityDao.addAgingCityMapping(entity);
					}else{
						//插入一条新记录
						agingCityDao.addAgingCityMapping(entity);
					}
				}
		 }
	}
	
	public void updateArriveAgingCity(AgingCityEntity entity,String operUserCode){
		String sequence=null;//序列号
		String[] params=null;
		//1.更新出发价格城市
		entity.setId(UUIDUtil.getUUID());
		entity.setActive(MiserConstants.ACTIVE);
		entity.setCreateUser(operUserCode);
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date());
		entity.setModifyUser(operUserCode);
		entity.setAgingCityName(entity.getArriveAgingCity());
		entity.setCityType("ARRIVAL");
		
		 //查询省市区对应的code
		 AgingCityEntity price= agingCityDao.queryCodeByEntity(entity);
		 entity.setProvinceCode(price.getProvinceCode());
		 entity.setCityCode(price.getCityCode());
		 entity.setAreaCode(price.getAreaCode());
		 long isExit=agingCityDao.queryAgingCityMappingByEntity(entity);//判断这个行政区域已经有了到达价格城市
		if("".equals(entity.getArriveAgingCity())){
			 if(isExit>=1){
					//这个行政区域有了到达价格城市，则作废
					agingCityDao.deleteAgingCityMappingByEntity(entity);
				}
		}else{
			/**循环价格城市相同的list*/
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("type", "ARRIVAL"); //城市类型
			 map.put("startAgingCity", entity.getArriveAgingCity());
			 map.put("active",MiserConstants.ACTIVE);
			//1.1.1 判断这个到达价格城市名称是否已使用过,有则取其code值
			String code=agingCityDao.queryAgingCityByStartAgingName(map);
				if(code!=null&& !"".equals(code)){//如果这个到达价格城市已经有了
					
					if(isExit>=1){ 
						//这个行政区域有了到达价格城市，则作废
						agingCityDao.deleteAgingCityMappingByEntity(entity);
						//插入一条新记录
						entity.setAgingCityCode(code);
						agingCityDao.addAgingCityMapping(entity);
					}else{
						entity.setAgingCityCode(code);
						agingCityDao.addAgingCityMapping(entity);
					}
					
				}else{//没有这个到达价格城市名称,插入主表
					//获取序列
					sequence=	serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_CITY_CODE, params);
					entity.setAgingCityCode(sequence);
					agingCityDao.addAgingCity(entity);//增加主表
					
					if(isExit>=1){
						//这个行政区域有了到达价格城市，则作废
						agingCityDao.deleteAgingCityMappingByEntity(entity);
						//插入一条新记录
						agingCityDao.addAgingCityMapping(entity);
					}else{
						//插入一条新记录
						agingCityDao.addAgingCityMapping(entity);
					}
				}
		}
		
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public Map<String,Object> cityInprot(String path) throws Exception {
		
		Map<String,Object> retuMap=new HashMap<String, Object>();
		
		List<Map<String, String>> list;
		list = ExcelUtil.readToListByFile(path, 10, 2);
		
		Date dt = agingCityDao.searchCurrentDateTime();
		
		//查询所有出发时效城市代码
		Map<String,String> smap = new HashMap<String, String>();//存储原始数据库中的map
		Map<String,String> smapt = new HashMap<String, String>();//临时存储
		Map<String,Object> mapt=new HashMap<String,Object>();
		mapt.put("type", "SEND"); //城市类型
		mapt.put("active",MiserConstants.ACTIVE);
		List<Map<String,String>> slist = agingCityDao.queryAgingCity(mapt);
		for (Map<String,String> map : slist) {
		   smap.put(map.get("NAME"), map.get("CODE"));
		   smapt.put(map.get("NAME"), map.get("CODE"));
		}
		
		//查询所有到达时效城市代码
		Map<String,String> armap = new HashMap<String, String>();//存储原始数据库中的map
		Map<String,String> armaptemp = new HashMap<String, String>();//临时存储
		Map<String,Object> armapt=new HashMap<String,Object>();
		armapt.put("type", "ARRIVAL"); //城市类型
		armapt.put("active",MiserConstants.ACTIVE);
		List<Map<String,String>> alist = agingCityDao.queryAgingCity(armapt);
		for (Map<String,String> map : alist) {
		   armap.put(map.get("NAME"), map.get("CODE"));
		   armaptemp.put(map.get("NAME"), map.get("CODE"));
		}
				 
		 //查询出省
		Map<String,String> pmap = new HashMap<String, String>();
		List<Map<String,String>> plist = agingCityDao.queryCity("PROVINCE");
		for (Map<String, String> map : plist) {
			pmap.put(map.get("NAME"), map.get("CODE"));
		}
		 //市
		Map<String,String> cmap = new HashMap<String, String>();
		List<Map<String,String>> clist = agingCityDao.queryCity("CITY");
		for (Map<String, String> map : clist) {
			cmap.put(map.get("NAME"), map.get("CODE"));
		}
		 //区
		Map<String,String> amap = new HashMap<String, String>();
		List<Map<String,String>> arlist = agingCityDao.queryCity("AREA");
		for (Map<String, String> map : arlist) {
			amap.put(map.get("NAME"), map.get("CODE"));
		}
		
		SerialNumberRuleEntity snre = serialNumberService.generateBeginSerialNumber(SerialNumberRuleEnum.PC_PRICE_CITY_CODE, null);
		
		List<AgingCityEntity> aerrorEpsList = new ArrayList<AgingCityEntity>();
		List<AgingCityEntity> serrorEpsList = new ArrayList<AgingCityEntity>();
		
		List<AgingCityEntity> sendlist = new ArrayList<AgingCityEntity>();
		List<AgingCityEntity> arrlist = new ArrayList<AgingCityEntity>();
		
		
		List<PriceCityMappingEntity> sendMapList = new ArrayList<PriceCityMappingEntity>();
		List<PriceCityMappingEntity> arrMapList = new ArrayList<PriceCityMappingEntity>();
		
		int count = 0;
		long sadd = 0;
		long scover = 0;
		
		long aadd = 0;
		long acover = 0;
		for (Map<String, String> map : list) {
			
			AgingCityEntity entity = new AgingCityEntity();
			
			AgingCityEntity sentity = new AgingCityEntity();
			AgingCityEntity aentity = new AgingCityEntity();
			
			PriceCityMappingEntity priceCity = new PriceCityMappingEntity();
			
			PriceCityMappingEntity spriceCity = new PriceCityMappingEntity();
			PriceCityMappingEntity apriceCity = new PriceCityMappingEntity();
			
			try {
				entity.setActive(MiserConstants.ACTIVE);
				entity.setRemarks(StringUtil.trim(map.get(5+"")));
				
				entity.setCreateUser(MiserUserContext.getCurrentDeptCode());
				entity.setCreateDate(dt);
				entity.setModifyUser(MiserUserContext.getCurrentDeptCode());
				entity.setModifyDate(dt);
				
				entity.setProvinceName(StringUtil.trim(map.get(0+"")));
				entity.setCityName(StringUtil.trim(map.get(1+"")));
				entity.setAreaName(StringUtil.trim(map.get(2+"")));
				entity.setAgingCityName(map.get(3+""));
				
				
				priceCity.setActive(MiserConstants.ACTIVE);
				priceCity.setCreateDate(dt);
				priceCity.setCreateUser(MiserUserContext.getCurrentDeptCode());
				priceCity.setModifyDate(dt);
				priceCity.setModifyUser(MiserUserContext.getCurrentDeptCode());
				priceCity.setRemarks(entity.getRemarks());
				String province = pmap.get(entity.getProvinceName());
				String city = cmap.get(entity.getCityName());
				String area = amap.get(entity.getAreaName());
				if(!StringUtils.isEmptyWithBlock(province)){
					priceCity.setPriceCityCode(province);
					priceCity.setCityCode(city);
					priceCity.setAreaCode(area);
				}
				
				try{
					spriceCity =  priceCity.copy();
					
					sentity = entity.copy();
					sentity.setCityType("SEND");
					if(!StringUtils.isEmptyWithBlock(entity.getAgingCityName())){
						String code  = smapt.get(entity.getAgingCityName());
						//如果源记录中存在则只添加映射,并把以前 的映射废除
						if(!StringUtils.isEmptyWithBlock(code)){
							sentity.setAgingCityCode(code);
						}else{
							sadd++;
							sentity.setId(UUIDUtil.getUUID());
							sentity.setAgingCityCode(serialNumberService.generateSerialNumber(snre, null));
							smapt.put(sentity.getAgingCityName(), sentity.getAgingCityCode());
							sendlist.add(sentity);
						}
						if(StringUtils.isEmptyWithBlock(province)){
							throw new Exception("出发城市省份不能为空");
						}
						
						spriceCity.setCode(sentity.getAgingCityCode());
						spriceCity.setId(UUIDUtil.getUUID());
						
						//可能映射没有被删除
						/*if(!StringUtils.isEmptyWithBlock(smap.get(entity.getAgingCityName()))){
							scover++;
							agingCityDao.deleteMapping(spriceCity);
						}*/
						if(agingCityDao.deleteMapping(spriceCity) > 0){
							scover++;
							acover++;
						}
						sendMapList.add(spriceCity);
					}
				}catch(Exception ex){
					logger.error("批量导入模版数据更新操作异常，业务需要仅作提示", ex);
					AgingCityEntity eps=new AgingCityEntity();
					eps.setProvinceName(StringUtil.trim(map.get(0+"")));
					eps.setCityName(StringUtil.trim(map.get(1+"")));
					eps.setAreaName(StringUtil.trim(map.get(1+"")));
					eps.setStartAgingCity(entity.getStartAgingCity());
					eps.setRemarks(entity.getRemarks());
					eps.setErrorMsg(ex.getMessage());
					eps.setOldSerial(count+"");
					serrorEpsList.add(eps);
				}
				
				
				try{
					apriceCity =  priceCity.copy();
					
					aentity = entity.copy();
					aentity.setCityType("ARRIVAL");
					if(!StringUtils.isEmptyWithBlock(entity.getAgingCityName())){
						String code = armaptemp.get(entity.getAgingCityName());
						if(!StringUtils.isEmptyWithBlock(code)){
							aentity.setAgingCityCode(code);
						}else{
							aadd++;
							aentity.setId(UUIDUtil.getUUID());
							aentity.setAgingCityCode(serialNumberService.generateSerialNumber(snre, null));
							armaptemp.put(aentity.getAgingCityName(), aentity.getAgingCityCode());
							arrlist.add(aentity);
						}
						if(StringUtils.isEmptyWithBlock(province)){
							throw new Exception("到达城市省份不能为空");
						}
						apriceCity.setCode(aentity.getAgingCityCode());
						apriceCity.setId(UUIDUtil.getUUID());
						
						if(agingCityDao.deleteMapping(apriceCity) > 0){
							acover++;
							scover++;
						}
						//可能出现映射没有被删除
						/*if(!StringUtils.isEmptyWithBlock(armap.get(entity.getAgingCityName()))){
							acover++;
							agingCityDao.deleteMapping(apriceCity);
						}*/
						arrMapList.add(apriceCity);
					}
				}catch(Exception ex){
					logger.error("批量导入模版数据更新操作异常，业务需要仅作提示", ex);
					AgingCityEntity eps=new AgingCityEntity();
					eps.setProvinceName(StringUtil.trim(map.get(0+"")));
					eps.setCityName(StringUtil.trim(map.get(1+"")));
					eps.setAreaName(StringUtil.trim(map.get(1+"")));
					eps.setArriveAgingCity(entity.getArriveAgingCity());
					eps.setRemarks(entity.getRemarks());
					eps.setErrorMsg(ex.getMessage());
					eps.setOldSerial(count+"");
					aerrorEpsList.add(eps);
				}
				
			} catch (Exception e) {
				logger.error("批量导入模版数据异常，业务需要仅作提示", e);
			}
		}
		
		serialNumberService.generateEndSerialNumber(snre);
		if(sendlist.size() >0){
			agingCityDao.batchInsertTimeCity(sendlist);
		}
		if(arrlist.size() > 0){
			agingCityDao.batchInsertTimeCity(arrlist);
		}
		if(sendMapList.size() > 0){
			agingCityDao.batchInsertTimeMapping(sendMapList);
		}
		if(arrMapList.size() > 0){
			agingCityDao.batchInsertTimeMapping(arrMapList);
		}
		
		String sfilePath = "";
		if(serrorEpsList.size() > 0){
			List<String> titleNames=new ArrayList<String>();
			titleNames.add("行政省份");
			titleNames.add("行政城市");
			titleNames.add("行政区域");
			titleNames.add("出发时效城市");
			titleNames.add("备注");
			titleNames.add("旧序列号");
			titleNames.add("异常信息");
			List<String> paramNames=new ArrayList<String>();
			paramNames.add("provinceName");
			paramNames.add("cityName");
			paramNames.add("areaName");
			paramNames.add("startAgingCity");
			paramNames.add("remarks");
			paramNames.add("oldSerial");
			paramNames.add("errorMsg");
			sfilePath=new ExcelUtil<AgingCityEntity>().exportExcel(titleNames,paramNames,serrorEpsList);
		}
		String afilePath = "";
		if(aerrorEpsList.size() > 0){
			List<String> titleNames=new ArrayList<String>();
			titleNames.add("行政省份");
			titleNames.add("行政城市");
			titleNames.add("行政区域");
			titleNames.add("到达时效城市");
			titleNames.add("备注");
			titleNames.add("旧序列号");
			titleNames.add("异常信息");
			List<String> paramNames=new ArrayList<String>();
			paramNames.add("provinceName");
			paramNames.add("cityName");
			paramNames.add("areaName");
			paramNames.add("arriveAgingCity");
			paramNames.add("remarks");
			paramNames.add("oldSerial");
			paramNames.add("errorMsg");
			afilePath=new ExcelUtil<AgingCityEntity>().exportExcel(titleNames,paramNames,aerrorEpsList);
		}
		
		retuMap.put("addStartAgingCitySize", sadd);
		retuMap.put("coverStartAgingCitySize", scover);
		retuMap.put("fileStartPath", sfilePath);
		
		retuMap.put("addArriveAgingCitySize", aadd);
		retuMap.put("coverArriveAgingCitySize", acover);
		retuMap.put("fileArrivePath", afilePath);
		
		return retuMap;  
	}
	
	//在原基础上优化过渡版本
	public Map<String,Object> cityInprotNew(String path) throws Exception {
		
		Map<String,Object> retuMap=new HashMap<String, Object>();
		Map<String,Object> retuStartMap;
		Map<String,Object> retuArriveMap;
		
		List<Map<String, String>> list;
		List<AgingCityEntity> pseList = new ArrayList<AgingCityEntity>();
		list = ExcelUtil.readToListByFile(path, 10, 2);
		
		//查询所有出发时效城市代码
		Map<String,String> smap = new HashMap<String, String>();
		Map<String,Object> mapt=new HashMap<String,Object>();
		mapt.put("type", "SEND"); //城市类型
		mapt.put("active",MiserConstants.ACTIVE);
		List<Map<String,String>> slist = agingCityDao.queryAgingCity(mapt);
		for (Map<String,String> map : slist) {
		   smap.put(map.get("NAME"), map.get("CODE"));
		}
		
		//查询所有到达时效城市代码
		Map<String,String> armap = new HashMap<String, String>();
		Map<String,Object> armapt=new HashMap<String,Object>();
		armapt.put("type", "ARRIVAL"); //城市类型
		armapt.put("active",MiserConstants.ACTIVE);
		List<Map<String,String>> alist = agingCityDao.queryAgingCity(armapt);
		for (Map<String,String> map : alist) {
		   armap.put(map.get("NAME"), map.get("CODE"));
		}
				 
		 //查询出省
		Map<String,String> pmap = new HashMap<String, String>();
		List<Map<String,String>> plist = agingCityDao.queryCity("PROVINCE");
		for (Map<String, String> map : plist) {
			pmap.put(map.get("NAME"), map.get("CODE"));
		}
		 //市
		Map<String,String> cmap = new HashMap<String, String>();
		List<Map<String,String>> clist = agingCityDao.queryCity("CITY");
		for (Map<String, String> map : clist) {
			cmap.put(map.get("NAME"), map.get("CODE"));
		}
		 //区
		Map<String,String> amap = new HashMap<String, String>();
		List<Map<String,String>> arlist = agingCityDao.queryCity("AREA");
		for (Map<String, String> map : arlist) {
			amap.put(map.get("NAME"), map.get("CODE"));
		}
		
		for (Map<String, String> map : list) {
			AgingCityEntity entity = new AgingCityEntity();
			try {
				entity.setActive("Y");
				entity.setProvinceName(StringUtil.trim(map.get(0+"")));
				entity.setCityName(StringUtil.trim(map.get(1+"")));
				entity.setAreaName(StringUtil.trim(map.get(2+"")));
				entity.setStartAgingCity(StringUtil.trim(map.get(3+"")));
				entity.setArriveAgingCity(StringUtil.trim(map.get(4+"")));
				entity.setRemarks(StringUtil.trim(map.get(5+"")));
			} catch (Exception e) {
				entity = null;// 如果有异常就把pse设为null,这样每条信息都加进去了
				logger.error("批量导入模版数据异常，业务需要仅作提示", e);
			} finally {
				pseList.add(entity);
			}
		}
		Map<String, Long> countMap = new HashMap<String, Long>();
		countMap.put("addStartAgingCitySize", new Long(0));// 增加出发时效城市条数
		countMap.put("coverStartAgingCitySize", new Long(0));// 覆盖出发时效城市条数
		countMap.put("addArriveAgingCitySize", new Long(0));// 增加到达时效城市条数
		countMap.put("coverArriveAgingCitySize", new Long(0));// 覆盖到达时效城市条数
		
		  //1.定义一个startAgingCityList集合用于把execel按照起运城市分组
		  Map<String, List<AgingCityEntity>> startAgingCityList = new HashMap<String, List<AgingCityEntity>>();
		  
		  //2.定义一个arriveAgingCityList集合用于把execel按照起运城市分组
		  Map<String, List<AgingCityEntity>> arriveAgingCityList = new HashMap<String, List<AgingCityEntity>>();
		  
		  //返回的处理好的集合对象,以chinese为值保存到startAgingCityList中
		  for (Iterator it = pseList.iterator(); it.hasNext();){
		   //将循环读取的结果放入对象中
		  AgingCityEntity agingCityEntity = (AgingCityEntity) it.next();
			  if(agingCityEntity==null){
				  continue;
			  }
		   //按起运城市分组  如果在这个map中包含有相同的键，这创建一个集合将其存起来 
		   if (startAgingCityList.containsKey(agingCityEntity.getStartAgingCity())){
		    List<AgingCityEntity> syn = startAgingCityList.get(agingCityEntity.getStartAgingCity());
		    syn.add(agingCityEntity);
		    //如果没有包含相同的键，在创建一个集合保存数据
		   } else {  
		    List<AgingCityEntity> syns = new ArrayList<AgingCityEntity>();
		    syns.add(agingCityEntity);
		    startAgingCityList.put(agingCityEntity.getStartAgingCity(), syns);
		   }
		   //按到达城市分组
		   if (arriveAgingCityList.containsKey(agingCityEntity.getArriveAgingCity())){
			    List<AgingCityEntity> syn = arriveAgingCityList.get(agingCityEntity.getArriveAgingCity());
			    syn.add(agingCityEntity);
			    //如果没有包含相同的键，在创建一个集合保存数据
			   } else {  
			    List<AgingCityEntity> syns = new ArrayList<AgingCityEntity>();
			    syns.add(agingCityEntity);
			    arriveAgingCityList.put(agingCityEntity.getArriveAgingCity(), syns);
			   }
	   }
		retuStartMap= this.addOrUpdateAgingCity(smap,pmap,cmap,amap,startAgingCityList.entrySet(),countMap,"SEND");
		retuArriveMap= this.addOrUpdateAgingCity(armap,pmap,cmap,amap,arriveAgingCityList.entrySet(), countMap,"ARRIVAL");
		  
		retuMap.put("addStartAgingCitySize", retuStartMap.get("addStartAgingCitySize"));
		retuMap.put("coverStartAgingCitySize", retuStartMap.get("coverStartAgingCitySize"));
		retuMap.put("fileStartPath", retuStartMap.get("fileStartPath"));
		
		retuMap.put("addArriveAgingCitySize", retuArriveMap.get("addArriveAgingCitySize"));
		retuMap.put("coverArriveAgingCitySize", retuArriveMap.get("coverArriveAgingCitySize"));
		retuMap.put("fileArrivePath", retuArriveMap.get("fileArrivePath"));
		return retuMap;  
	}
	
	private Map<String,Object> addOrUpdateAgingCity(Map<String,String> mapAginCity,
			Map<String,String> pmap,
			Map<String,String> cmap,
			Map<String,String> amap,
			Set<Entry<String, List<AgingCityEntity>>> set,
			Map<String,Long> countMap,
			String type) throws IOException {
		Map<String,Object> retuMap=new HashMap<String, Object>();
		String fileStartPath="";
		String operUserCode =MiserUserContext.getCurrentUser().getEmpNameAndUserName();
		List<AgingCityEntity> errorEpsList=new ArrayList<AgingCityEntity>();
		
		long add = 0L;
		long cover = 0L;
		
	    for (Map.Entry<String, List<AgingCityEntity>> m : set){
			
		  //获取所有的值
		  String startCityName=m.getKey();//出发时效城市名
		  List<AgingCityEntity> synList = m.getValue();//时效城市
		  String sequence=null;//序列号
		  String[] params=null;
		  for (AgingCityEntity agingCityEntity : synList) {
			  agingCityEntity.setId(UUIDUtil.getUUID());
			  agingCityEntity.setActive(MiserConstants.ACTIVE);
			  agingCityEntity.setCreateUser(operUserCode);
			  agingCityEntity.setCreateDate(new Date());
			  agingCityEntity.setModifyDate(new Date());
			  agingCityEntity.setModifyUser(operUserCode);
			  agingCityEntity.setAgingCityName(startCityName);
			  agingCityEntity.setCityType(type);
			  String code=mapAginCity.get(startCityName);
			  try {
				agingCityEntity.setProvinceCode(pmap.get(agingCityEntity.getProvinceName()));
				agingCityEntity.setCityCode(cmap.get(agingCityEntity.getCityName()));
				agingCityEntity.setAreaCode(amap.get(agingCityEntity.getAreaName()));
				if(code!=null&& !"".equals(code)){//如果这个出发时效城市已经有了
					long isExit=agingCityDao.queryAgingCityMappingByEntity(agingCityEntity);//判断这个行政区域已经有了 出发时效城市
					if(isExit>=1){ 
						cover++;//覆盖条数
						//这个行政区域有了出发时效城市，则作废
						agingCityDao.deleteAgingCityMappingByEntity(agingCityEntity);
						//插入一条新记录
						agingCityEntity.setAgingCityCode(code);
						agingCityDao.addAgingCityMapping(agingCityEntity);
					}else{
						add++;//增加条数
						agingCityEntity.setAgingCityCode(code);
						agingCityDao.addAgingCityMapping(agingCityEntity);
					}
					
				}else{//没有这个出发时效城市名称,插入主表
					//获取序列
					sequence=	serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_CITY_CODE, params);
					agingCityEntity.setAgingCityCode(sequence);
					agingCityDao.addAgingCity(agingCityEntity);//增加主表
					mapAginCity.put(agingCityEntity.getAgingCityName(), agingCityEntity.getAgingCityCode());
					//如果这个行政区域已经有了 出发时效城市，则更新出发时效城市名称
					long isExit=agingCityDao.queryAgingCityMappingByEntity(agingCityEntity);
					if(isExit>=1){
						cover++;//覆盖条数
						//这个行政区域有了出发时效城市，则作废
						agingCityDao.deleteAgingCityMappingByEntity(agingCityEntity);
						//插入一条新记录
						agingCityDao.addAgingCityMapping(agingCityEntity);
					}else{
						add++;//增加条数
						//插入一条新记录
						agingCityDao.addAgingCityMapping(agingCityEntity);
					}
				}
			} catch (Exception e) {
				logger.error("批量导入模版数据更新操作异常，业务需要仅作提示", e);
				AgingCityEntity eps=new AgingCityEntity();
				eps.setProvinceName(agingCityEntity.getProvinceName());
				eps.setCityName(agingCityEntity.getCityName());
				eps.setAreaName(agingCityEntity.getAreaName());
				eps.setStartAgingCity(agingCityEntity.getStartAgingCity());
				eps.setArriveAgingCity(agingCityEntity.getArriveAgingCity());
				eps.setRemarks(agingCityEntity.getRemarks());
				eps.setErrorMsg(e.getMessage());
				errorEpsList.add(eps);
			}
			
		}
		  
	  }
	  if(errorEpsList.size()>0){
			List<String> titleNames=new ArrayList<String>();
			titleNames.add("行政省份");
			titleNames.add("行政城市");
			titleNames.add("行政区域");
			titleNames.add("出发时效城市");
			titleNames.add("备注");
			titleNames.add("旧序列号");
			titleNames.add("异常信息");
			List<String> paramNames=new ArrayList<String>();
			paramNames.add("provinceName");
			paramNames.add("cityName");
			paramNames.add("areaName");
			paramNames.add("startAgingCity");
			paramNames.add("remarks");
			paramNames.add("oldSerial");
			paramNames.add("errorMsg");
			fileStartPath=new ExcelUtil<AgingCityEntity>().exportExcel(titleNames,paramNames,errorEpsList);
	   }
	   if(type.equals("SEND")){
			retuMap.put("addStartAgingCitySize", countMap.get("addStartAgingCitySize") + add);
			retuMap.put("coverStartAgingCitySize", countMap.get("coverStartAgingCitySize") + cover);
		}else if(type.equals("ARRIVAL")) {
			retuMap.put("addArriveAgingCitySize", countMap.get("addArriveAgingCitySize") + add);
			retuMap.put("coverArriveAgingCitySize", countMap.get("coverArriveAgingCitySize") + cover);
		}
		retuMap.put("fileStartPath", fileStartPath);
		return retuMap;
	}
	
    private Map<String,Object> addOrUpdateStartAgingCity( Set<Entry<String, List<AgingCityEntity>>> set,Map<String,Long> countMap) throws IOException {
		
		Map<String,Object> retuMap=new HashMap<String, Object>();
		String fileStartPath="";
		String operUserCode =MiserUserContext.getCurrentUser().getEmpNameAndUserName();
		List<AgingCityEntity> errorEpsList=new ArrayList<AgingCityEntity>();
		//1.1遍历startAgingCityList，根据时效城市名称插入数据
		  for (Map.Entry<String, List<AgingCityEntity>> m : set){
				long addStartAgingCitySize= countMap.get("addStartAgingCitySize").longValue();
				long coverStartAgingCitySize= countMap.get("coverStartAgingCitySize").longValue();
			//获取所有的值
			   String startCityName=m.getKey();//出发时效城市名
			   List<AgingCityEntity> synList = m.getValue();//时效城市
			   String sequence=null;//序列号
			   String[] params=null;
			   for (AgingCityEntity agingCityEntity : synList) {
				   agingCityEntity.setId(UUIDUtil.getUUID());
				   agingCityEntity.setActive(MiserConstants.ACTIVE);
				   agingCityEntity.setCreateUser(operUserCode);
				   agingCityEntity.setCreateDate(new Date());
				   agingCityEntity.setModifyDate(new Date());
				   agingCityEntity.setModifyUser(operUserCode);
				   agingCityEntity.setAgingCityName(startCityName);
				   agingCityEntity.setCityType("SEND");
				/**循环时效城市相同的list*/
				 Map<String,Object> map=new HashMap<String,Object>();
				 map.put("type", "SEND"); //城市类型
				 map.put("startAgingCity", startCityName);
				 map.put("active",MiserConstants.ACTIVE);
				//1.1.1 判断这个出发时效城市名称是否已使用过,有则取其code值
				String code=agingCityDao.queryAgingCityByStartAgingName(map);
				try {
					 //查询省市区对应的code
					 AgingCityEntity aging= agingCityDao.queryCodeByEntity(agingCityEntity);
					 agingCityEntity.setProvinceCode(aging.getProvinceCode());
					 agingCityEntity.setCityCode(aging.getCityCode());
					 agingCityEntity.setAreaCode(aging.getAreaCode());
					if(code!=null&& !"".equals(code)){//如果这个出发时效城市已经有了
						long isExit=agingCityDao.queryAgingCityMappingByEntity(agingCityEntity);//判断这个行政区域已经有了 出发时效城市
						if(isExit>=1){ 
							coverStartAgingCitySize++;//覆盖条数
							//这个行政区域有了出发时效城市，则作废
							agingCityDao.deleteAgingCityMappingByEntity(agingCityEntity);
							//插入一条新记录
							agingCityEntity.setAgingCityCode(code);
							agingCityDao.addAgingCityMapping(agingCityEntity);
						}else{
							addStartAgingCitySize++;//增加条数
							agingCityEntity.setAgingCityCode(code);
							agingCityDao.addAgingCityMapping(agingCityEntity);
						}
						
					}else{//没有这个出发时效城市名称,插入主表
						//获取序列
						sequence=	serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_CITY_CODE, params);
						agingCityEntity.setAgingCityCode(sequence);
						agingCityDao.addAgingCity(agingCityEntity);//增加主表
						//如果这个行政区域已经有了 出发时效城市，则更新出发时效城市名称
						long isExit=agingCityDao.queryAgingCityMappingByEntity(agingCityEntity);
						if(isExit>=1){
							coverStartAgingCitySize++;//覆盖条数
							//这个行政区域有了出发时效城市，则作废
							agingCityDao.deleteAgingCityMappingByEntity(agingCityEntity);
							//插入一条新记录
							agingCityDao.addAgingCityMapping(agingCityEntity);
						}else{
							addStartAgingCitySize++;//增加条数
							//插入一条新记录
							agingCityDao.addAgingCityMapping(agingCityEntity);
						}
					}
				} catch (Exception e) {
					logger.error("批量导入模版数据更新操作异常，业务需要仅作提示", e);
					AgingCityEntity eps=new AgingCityEntity();
					eps.setProvinceName(agingCityEntity.getProvinceName());
					eps.setCityName(agingCityEntity.getCityName());
					eps.setAreaName(agingCityEntity.getAreaName());
					eps.setStartAgingCity(agingCityEntity.getStartAgingCity());
					eps.setArriveAgingCity(agingCityEntity.getArriveAgingCity());
					eps.setRemarks(agingCityEntity.getRemarks());
					eps.setErrorMsg(e.getMessage());
					errorEpsList.add(eps);
				}
				
			}
			   retuMap.put("sumStartSize", synList.size());
			   countMap.put("addStartAgingCitySize", addStartAgingCitySize);
			   countMap.put("coverStartAgingCitySize", coverStartAgingCitySize);
			   if(errorEpsList.size()>0){
					 List<String> titleNames=new ArrayList<String>();
					 titleNames.add("行政省份");
					 titleNames.add("行政城市");
					 titleNames.add("行政区域");
					 titleNames.add("出发时效城市");
					 titleNames.add("备注");
					 titleNames.add("旧序列号");
					 titleNames.add("异常信息");
					List<String> paramNames=new ArrayList<String>();
					paramNames.add("provinceName");
					paramNames.add("cityName");
					paramNames.add("areaName");
					paramNames.add("startAgingCity");
					paramNames.add("remarks");
					paramNames.add("oldSerial");
					paramNames.add("errorMsg");
					fileStartPath=new ExcelUtil<AgingCityEntity>().exportExcel(titleNames,paramNames,errorEpsList);
			   }   
		  }
		  	retuMap.put("addStartAgingCitySize", countMap.get("addStartAgingCitySize"));
			retuMap.put("coverStartAgingCitySize", countMap.get("coverStartAgingCitySize"));
			retuMap.put("fileStartPath", fileStartPath);
			return retuMap;
	}
	private Map<String,Object> addOrUpdateArriveAgingCity( Set<Entry<String, List<AgingCityEntity>>> set,Map<String,Long> countMap) throws IOException {
		String fileArrivePath="";
		Map<String,Object> retuMap=new HashMap<String, Object>();
		List<AgingCityEntity> errorArriveList=new ArrayList<AgingCityEntity>();
		String operUserCode =MiserUserContext.getCurrentUser().getEmpNameAndUserName();
		//2.1遍历arriveAgingCityList，根据时效城市名称插入数据
		  for (Map.Entry<String, List<AgingCityEntity>> m : set){
			  	long addArriveAgingCitySize= countMap.get("addArriveAgingCitySize").longValue();
				long coverArriveAgingCitySize= countMap.get("coverArriveAgingCitySize").longValue();
			  //获取所有的值
			   String arriveCityName=m.getKey();//出发时效城市名
			   List<AgingCityEntity> synList = m.getValue();//时效城市
			   String sequence=null;//序列号
			   String[] params=null;
			   for (AgingCityEntity agingCityEntity : synList) {
				   agingCityEntity.setId(UUIDUtil.getUUID());
				   agingCityEntity.setActive(MiserConstants.ACTIVE);
				   agingCityEntity.setCreateUser(operUserCode);
				   agingCityEntity.setCreateDate(new Date());
				   agingCityEntity.setModifyDate(new Date());
				   agingCityEntity.setModifyUser(operUserCode);
				   agingCityEntity.setAgingCityName(arriveCityName);
				   agingCityEntity.setCityType("ARRIVAL");
				   
				
				/**循环时效城市相同的list*/
				 Map<String,Object> map=new HashMap<String,Object>();
				 map.put("type", "ARRIVAL"); //城市类型
				 map.put("startAgingCity", arriveCityName);
				 map.put("active",MiserConstants.ACTIVE);
				//1.1.1 判断这个出发时效城市名称是否已使用过,有则取其code值
				String code=agingCityDao.queryAgingCityByStartAgingName(map);
				try {
					//查询省市区对应的code
					AgingCityEntity aging= agingCityDao.queryCodeByEntity(agingCityEntity);
					agingCityEntity.setProvinceCode(aging.getProvinceCode());
					agingCityEntity.setCityCode(aging.getCityCode());
					agingCityEntity.setAreaCode(aging.getAreaCode());
					if(code!=null&& !"".equals(code)){//如果这个到达时效城市已经有了
						long isExit=agingCityDao.queryAgingCityMappingByEntity(agingCityEntity);//判断这个行政区域已经有了 到达时效城市
						if(isExit>=1){ 
							coverArriveAgingCitySize++;
							//这个行政区域有了到达时效城市，则作废
							agingCityDao.deleteAgingCityMappingByEntity(agingCityEntity);
							//插入一条新记录
							agingCityEntity.setAgingCityCode(code);
							agingCityDao.addAgingCityMapping(agingCityEntity);
						}else{
							addArriveAgingCitySize++;
							agingCityEntity.setAgingCityCode(code);
							agingCityDao.addAgingCityMapping(agingCityEntity);
						}
						
					}else{//没有这个出发时效城市名称,插入主表
						//获取序列
						sequence=	serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_PRICE_CITY_CODE, params);
						agingCityEntity.setAgingCityCode(sequence);
						agingCityDao.addAgingCity(agingCityEntity);//增加主表
						
						//如果这个行政区域已经有了 出发时效城市，则更新出发时效城市名称
						long isExit=agingCityDao.queryAgingCityMappingByEntity(agingCityEntity);
						if(isExit>=1){
							coverArriveAgingCitySize++;
							//这个行政区域有了出发时效城市，则作废
							agingCityDao.deleteAgingCityMappingByEntity(agingCityEntity);
							//插入一条新记录
							agingCityDao.addAgingCityMapping(agingCityEntity);
						}else{
							addArriveAgingCitySize++;
							//插入一条新记录
							agingCityDao.addAgingCityMapping(agingCityEntity);
							}
						}
				} catch (Exception e) {
					logger.error("批量导入模版数据更新操作异常，业务需要仅作提示", e);
					AgingCityEntity eps=new AgingCityEntity();
					eps.setProvinceName(agingCityEntity.getProvinceName());
					eps.setCityName(agingCityEntity.getCityName());
					eps.setAreaName(agingCityEntity.getAreaName());
					eps.setStartAgingCity(agingCityEntity.getStartAgingCity());
					eps.setArriveAgingCity(agingCityEntity.getArriveAgingCity());
					eps.setRemarks(agingCityEntity.getRemarks());
					eps.setErrorMsg(e.getMessage());
					errorArriveList.add(eps);
					}
			   }
			   retuMap.put("arriveSumSize", synList.size());
			   countMap.put("addArriveAgingCitySize", addArriveAgingCitySize);
			   countMap.put("coverArriveAgingCitySize", coverArriveAgingCitySize);
			   if(errorArriveList.size()>0){
					 List<String> titleNames=new ArrayList<String>();
					 titleNames.add("行政省份");
					 titleNames.add("行政城市");
					 titleNames.add("行政区域");
					 titleNames.add("到达时效城市");
					 titleNames.add("备注");
					 titleNames.add("旧序列号");
					 titleNames.add("异常信息");
					List<String> paramNames=new ArrayList<String>();
					paramNames.add("provinceName");
					paramNames.add("cityName");
					paramNames.add("areaName");
					paramNames.add("arriveAgingCity");
					paramNames.add("remarks");
					paramNames.add("oldSerial");
					paramNames.add("errorMsg");
					fileArrivePath=new ExcelUtil<AgingCityEntity>().exportExcel(titleNames,paramNames,errorArriveList);
			   }   
		  }
		  retuMap.put("addArriveAgingCitySize", countMap.get("addArriveAgingCitySize"));
		  retuMap.put("coverArriveAgingCitySize", countMap.get("coverArriveAgingCitySize"));
		  retuMap.put("fileArrivePath", fileArrivePath);
		  return retuMap;
	}
	public List<AgingCityEntity> excelQueryListByParam(
			AgingCityVo psv) {
		return agingCityDao.excelQueryListByParam(psv.getAgingCityEntity());
	}

	public String createExcelFile(List<AgingCityEntity> list) {
		String filePath=null;
		List<String> titleNames=new ArrayList<String>();
		 //titleNames.add("序号");
		titleNames.add("行政省份");
		 titleNames.add("行政城市");
		 titleNames.add("行政区域");
		 titleNames.add("是否有网点");
		 titleNames.add("出发时效城市");
		 titleNames.add("到达时效城市");
		 titleNames.add("备注");
		 titleNames.add("修改人");
		 titleNames.add("修改时间");
		List<String> paramNames=new ArrayList<String>();
		paramNames.add("provinceName");
		paramNames.add("cityName");
		paramNames.add("areaName");
		paramNames.add("isLocations");
		paramNames.add("startAgingCity");
		paramNames.add("arriveAgingCity");
		paramNames.add("remarks");
		paramNames.add("modifyUserCode");
		paramNames.add("modifyTime");
		try {
			filePath=new ExcelUtil<AgingCityEntity>().exportExcel(titleNames,paramNames,list);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("生成导出数据异常!", e);
			filePath=null;
		}
		
		return filePath;
	}
	@Override
	public AgingCityEntity queryAgingCityInUpdate(AgingCityEntity entity) {
		entity.setActive(MiserConstants.ACTIVE);
		return agingCityDao.queryAgingCityInUpdate(entity);
	}
	@Override
	public List<AgingCityEntity> queryStartAgingCityList(
			AgingCityEntity userEntity, int limit, int start) {
		RowBounds rowBounds =new RowBounds(start,limit);
		return agingCityDao.queryStartAgingCityList(userEntity, rowBounds);
	}
	@Override
	public Long queryCountByParam(AgingCityEntity userEntity) {
		return agingCityDao.queryCountByParam(userEntity);
	}
	@Override
	public List<AgingCityEntity> queryArriveAgingCityList(
			AgingCityEntity userEntity, int limit, int start) {
		RowBounds rowBounds =new RowBounds(start,limit);
		return agingCityDao.queryArriveAgingCityList(userEntity, rowBounds);
	}
	@Override
	public Long queryCountByParamArrive(AgingCityEntity userEntity) {
		return agingCityDao.queryCountByParamArrive(userEntity);
	}
	
	
}

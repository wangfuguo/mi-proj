package com.hoau.miser.module.biz.base.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.ICityTypeService;
import com.hoau.miser.module.biz.base.api.shared.domain.AgingCityEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.CityTypeEntity;
import com.hoau.miser.module.biz.base.server.dao.CityTypeDao;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryValueEntity;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;
@Service
public class CityTypeService implements ICityTypeService{
	private static final Logger logger = LoggerFactory.getLogger(CityTypeService.class);
	@Resource
	private CityTypeDao cityTypeDao;
//	@Resource
//	private ISerialNumberService serialNumberService;
	
	/**
	 * 查询列表数据
	 */
	public List<CityTypeEntity> queryCityTypeByEntity(CityTypeEntity cityTypeEntity,int limit,int start){
		RowBounds rowBounds = new RowBounds(start, limit);
		return cityTypeDao.queryCityTypeByEntity(cityTypeEntity,rowBounds);
	}
	/**
	 * 界面查询条件的查询结果总条数
	 */
	@Override
	public Long countCityTypeByEntity(CityTypeEntity CityTypeEntity) {
		return cityTypeDao.countCityTypeByEntity(CityTypeEntity);
	}
	


	@Override
	public String createCityTypeExcelFile(List<CityTypeEntity> list) {
		String filePath=null;
		List<String> titleNames = new ArrayList<String>();
		titleNames.add("行政省份");
		titleNames.add("行政城市");
		titleNames.add("行政区域");
		titleNames.add("城市类型");
		titleNames.add("备注");
		titleNames.add("创建人");
		titleNames.add("创建时间");
		titleNames.add("修改人");
		titleNames.add("修改时间");
		List<String> paramNames = new ArrayList<String>();
		paramNames.add("provinceName");
		paramNames.add("cityName");
		paramNames.add("areaName");
		paramNames.add("cityType");
		paramNames.add("remark");
		paramNames.add("createUserCode");
		paramNames.add("createTime");
		paramNames.add("modifyUserCode");
		paramNames.add("modifyTime");
		
		Map<String, String> codeName=queryCityTypeCode2();
		for(CityTypeEntity ctList:list){
			ctList.setCityType(codeName.get(ctList.getCityType()));
		}
		 try {
	            filePath=new ExcelUtil<CityTypeEntity>().exportExcel(titleNames,paramNames,list);
	        } catch (Exception e) {
	            // TODO: handle exception
	            logger.error("生成导出数据异常!", e);
	            filePath=null;
	        }
		return filePath;
	}
	/**
	 * 
	 */
	@Override
	public CityTypeEntity queryCityTypeInUpdate(CityTypeEntity entity) {
		entity.setActive(MiserConstants.ACTIVE);
		CityTypeEntity c=cityTypeDao.queryCityTypeInUpdate(entity);
		return c;
	}
//	修改城市类别
	@Override
	@Transactional
	public Long updateCityType(CityTypeEntity cityTypeEntity) {
//		当前用户
		String operUserCode =MiserUserContext.getCurrentUser().getEmpNameAndUserName();
		Date date=new Date();
		cityTypeEntity.setCreateTime(date);
		cityTypeEntity.setCreateUserCode(operUserCode);
		cityTypeEntity.setModifyTime(date);
		cityTypeEntity.setModifyUserCode(operUserCode);
		cityTypeEntity.setActive("Y");
		cityTypeEntity.setId(UUIDUtil.getUUID());
		long coverCityTypeSize= 0;
		
			if(cityTypeDao.updateActiveBySSQ(cityTypeEntity)>0){
				coverCityTypeSize++;
			}
			
		
		cityTypeDao.insertCityType(cityTypeEntity);
		return coverCityTypeSize;
	}
	
	
//	导入
	@Override
	@Transactional
	public Map<String, Object> cityTypeImport(String path) throws IOException {
		
		Map<String,Object> retuMap=new HashMap<String, Object>();
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<CityTypeEntity> pseList = new ArrayList<CityTypeEntity>();//存入excel中的数据
		
			try {
				list = ExcelUtil.readToListByFile(path, 10, 2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Map<String, String> codeName=queryCityTypeCode();	
			List<CityTypeEntity> errorEpsList=new ArrayList<CityTypeEntity>();
		for (Map<String, String> map : list) {
			CityTypeEntity entity = new CityTypeEntity();
			try {
				entity.setActive("Y");
				entity.setProvinceName(StringUtil.trim(map.get(0+"")));
				entity.setCityName(StringUtil.trim(map.get(1+"")));
				entity.setAreaName(StringUtil.trim(map.get(2+"")));
				entity.setRemark(StringUtil.trim(map.get(4+"")));
				String cityCode=codeName.get(StringUtil.trim(map.get(3+"")));
				if(cityCode==null){
					throw new RuntimeException("不支持的城市类型");
				}
				entity.setCityType(cityCode);
				pseList.add(entity);
			} catch (Exception e) {
				CityTypeEntity eps=new CityTypeEntity();
				eps.setProvinceName(entity.getProvinceName());
				eps.setCityName(entity.getCityName());
				eps.setAreaName(entity.getAreaName());
				eps.setCityType(entity.getCityType());
				eps.setRemark(entity.getRemark());
				errorEpsList.add(eps);
				logger.error("批量导入模版数据异常，业务需要仅作提示", e);
			} 
		}
		
		long sumCityTypeSize=list.size();//总条数
		long addCityTypeSize=0;//导入条数
		long coverCityTypeSize=0;//覆盖条数
		long errorCityTypeSize=errorEpsList.size();//失败条数
		String errorFilePath="";
		for(CityTypeEntity ct:pseList){//循环excel中出来的数据
			CityTypeEntity oldEntity= cityTypeDao.queryBySSQ(ct);//按excel中省市区name查出城市类别信息
			if(oldEntity!=null){//覆盖
					try {
						ct.setProvinceCode(oldEntity.getProvinceCode());
						ct.setCityCode(oldEntity.getCityCode());
						ct.setAreaCode(oldEntity.getAreaCode());
						if(!"".equals(oldEntity.getId())&&oldEntity.getId()!=null){
							//覆盖
							coverCityTypeSize=this.updateCityType(ct)+coverCityTypeSize;
						}else{
//							当前用户
							String operUserCode =MiserUserContext.getCurrentUser().getEmpNameAndUserName();
							Date date=new Date();
							ct.setCreateTime(date);
							ct.setCreateUserCode(operUserCode);
							ct.setModifyTime(date);
							ct.setModifyUserCode(operUserCode);
							ct.setActive("Y");
							ct.setId(UUIDUtil.getUUID());
							cityTypeDao.insertCityType(ct);//直接
							addCityTypeSize++;
						}
						
					} catch (Exception e) {
						logger.error("批量导入模版数据更新操作异常，业务需要仅作提示", e);
						CityTypeEntity eps=new CityTypeEntity();
						eps.setProvinceName(ct.getProvinceName());
						eps.setCityName(ct.getCityName());
						eps.setAreaName(ct.getAreaName());
						eps.setCityType(ct.getCityType());
						eps.setRemark(ct.getRemark());
						errorEpsList.add(eps);
					}
					
				
			}else{
				CityTypeEntity eps=new CityTypeEntity();
				eps.setProvinceName(ct.getProvinceName());
				eps.setCityName(ct.getCityName());
				eps.setAreaName(ct.getAreaName());
				eps.setCityType(ct.getCityType());
				eps.setRemark(ct.getRemark());
				errorEpsList.add(eps);
			}
		}
		if(errorEpsList.size()>0){
			 List<String> titleNames=new ArrayList<String>();
			 titleNames.add("行政省份");
			 titleNames.add("行政城市");
			 titleNames.add("行政区域");
			 titleNames.add("城市类型");
			 titleNames.add("备注");
			 titleNames.add("创建人");
			 titleNames.add("创建时间");
			 titleNames.add("修改人");
			 titleNames.add("修改时间");
			List<String> paramNames=new ArrayList<String>();
			paramNames.add("provinceName");
			paramNames.add("cityName");
			paramNames.add("areaName");
			paramNames.add("cityType");
			paramNames.add("remark");
			paramNames.add("createUserCode");
			paramNames.add("createTime");
			paramNames.add("modifyUserCode");
			paramNames.add("modifyTime");
			errorFilePath=new ExcelUtil<CityTypeEntity>().exportExcel(titleNames,paramNames,errorEpsList);
			errorCityTypeSize+=errorEpsList.size();
		}   
		retuMap.put("sumCityTypeSize",sumCityTypeSize);
		retuMap.put("addCityTypeSize",addCityTypeSize);
		retuMap.put("coverCityTypeSize",coverCityTypeSize);
		retuMap.put("errorCityTypeSize",errorCityTypeSize);
		retuMap.put("errorFilePath", errorFilePath);
		 return retuMap;  
	}
	
	
	
	
	@Override
	public Map<String, String> queryCityTypeCode() {
//		查出所有城市级别对应的数据字典数据放入map
	Map<String,String> cityCodeName=new HashMap<String, String>();
	List<DataDictionaryValueEntity> codename=cityTypeDao.queryCityTypeCode();
	for(DataDictionaryValueEntity m:codename){
		cityCodeName.put(m.getValueName(),m.getValueCode());
	}
		return cityCodeName;
	}
	
	@Override
	public Map<String, String> queryCityTypeCode2() {
//		查出所有城市级别对应的数据字典数据放入map
	Map<String,String> cityCodeName=new HashMap<String, String>();
	List<DataDictionaryValueEntity> codename=cityTypeDao.queryCityTypeCode();
	for(DataDictionaryValueEntity m:codename){
		cityCodeName.put(m.getValueCode(),m.getValueName());
	}
		return cityCodeName;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

/**
 * 
 */
package com.hoau.miser.module.biz.base.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.IOutLineService;
import com.hoau.miser.module.biz.base.api.shared.domain.OutLineEntity;
import com.hoau.miser.module.biz.base.server.dao.OutLineDao;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.base.api.server.service.IDistrictService;
import com.hoau.miser.module.sys.base.api.shared.domain.DistrictEntity;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 外发偏线系统管理 2016-3-17 20:09:07 
 * @author dengyin
 *
 */
@Service
public class OutLineServiceImpl  implements IOutLineService{

	private static final Logger logger = LoggerFactory.getLogger(OutLineServiceImpl.class);
	
	@Resource
	private OutLineDao outLineDao;
	
	@Resource
	private IDistrictService districtService;
	
	@Override
	public List<OutLineEntity> queryOutLineByEntity(
			OutLineEntity outLineEntity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		
		return outLineDao.queryOutLineByEntity(outLineEntity,rowBounds);
	}
	
	
	/**
	 * 统计当前查询条件下的总记录数
	 * @param outLineEntity
	 * @return
	 */
	public long countOfOutLineByEntity(OutLineEntity outLineEntity){
		return outLineDao.countOfOutLineByEntity(outLineEntity);
	}
	
	public List<OutLineEntity> excelQueryOutLineByEntity(OutLineEntity outLineEntity){
		return outLineDao.excelQueryOutLineByEntity(outLineEntity);
	}
	
	
	public void updateByEntity(OutLineEntity outLineEntity){
		String curUserCode = MiserUserContext.getCurrentUser().getUserName();
		outLineEntity.setModifyUserCode(curUserCode);
		outLineDao.updateByEntity(outLineEntity);
	}
	
	//批量新增
	public void batchInsertEntityList(List<OutLineEntity> outLineEntities){
		if(outLineEntities != null && outLineEntities.size() > 0){
			List<OutLineEntity> tmpOutLineEntityList = new ArrayList<OutLineEntity>();
			
			for(int i=0;i<outLineEntities.size(); i++){
				OutLineEntity curOutLineEntity = outLineEntities.get(i);
				beforeOperDeal(curOutLineEntity);
				
				tmpOutLineEntityList.add(curOutLineEntity);
				int tmpSize = tmpOutLineEntityList.size();
				
				if(tmpSize % 500 == 0){
					outLineDao.batchInsertEntityList(tmpOutLineEntityList);
					tmpOutLineEntityList.clear();
				}
			}
			
			// 若上面不能整除 余下的记录在这里做一个批量提交
			if(tmpOutLineEntityList.size() > 0){
				outLineDao.batchInsertEntityList(tmpOutLineEntityList);
			}
		}
	}
	
	public void batchUpdateEntityList(List<OutLineEntity> list){
		if(list != null && list.size() > 0){
			List<OutLineEntity> tmpOutLineEntityList = new ArrayList<OutLineEntity>();
			
			String curUserCode = MiserUserContext.getCurrentUser().getUserName();
			
			for(int i=0;i<list.size(); i++){
				OutLineEntity curOutLineEntity = list.get(i);
				
				curOutLineEntity.setModifyUserCode(curUserCode);
				
				tmpOutLineEntityList.add(curOutLineEntity);
				int tmpSize = tmpOutLineEntityList.size();
				
				if(tmpSize % 500 == 0){
					outLineDao.batchUpdateEntityList(tmpOutLineEntityList);
					tmpOutLineEntityList.clear();
				}
			}
			
			// 若上面不能整除 余下的记录在这里做一个批量提交
			if(tmpOutLineEntityList.size() > 0){
				outLineDao.batchUpdateEntityList(tmpOutLineEntityList);
			}
		}
	}
	
	
	private void beforeOperDeal(OutLineEntity curOutLineEntity) {
		
		String id = UUIDUtil.getUUID();
		Date dt = new Date();
		String curUserCode = MiserUserContext.getCurrentUser().getUserName();
		curOutLineEntity.setId(id);
		curOutLineEntity.setActive(MiserConstants.YES);
		curOutLineEntity.setCreateTime(dt);
		curOutLineEntity.setModifyTime(dt);
		curOutLineEntity.setCreateUserCode(curUserCode);
		curOutLineEntity.setModifyUserCode(curUserCode);
		
	}


	public String createExcelFile(List<OutLineEntity> outLineList){
		String filePath = null;
		List<String> titleNames = new ArrayList<String>();
		titleNames.add("省");
		 titleNames.add("市");
		 titleNames.add("区县");
		 titleNames.add("外发系数最低收费");
		 titleNames.add("重量外发系数");
		 titleNames.add("体积外发系数");
		 titleNames.add("备注");
		 titleNames.add("创建人");
		 titleNames.add("创建时间");		 
		 titleNames.add("修改人");
		 titleNames.add("修改时间");
		 
		 List<String> paramNames=new ArrayList<String>();
			paramNames.add("provinceName");
			paramNames.add("cityName");
			paramNames.add("areaName");
			paramNames.add("lowestFee");
			paramNames.add("weightRate");
			paramNames.add("volumeRate");
			paramNames.add("remark");
			paramNames.add("createUserName");
			paramNames.add("createTime");
			paramNames.add("modifyUserName");
			paramNames.add("modifyTime");
			
			try {
				filePath = new ExcelUtil<OutLineEntity>().exportExcel(titleNames, paramNames, outLineList);
			} catch (Exception e) {
				logger.error("生成导出数据文件异常.....");
				e.printStackTrace();
			}
			return filePath;
	}
	
	public Map<String, Object> excelImport(String uploadPath){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, String>> list;
		
		//需要批量新增到数据的记录集合
		List<OutLineEntity> outLineEntityAddList = new ArrayList<OutLineEntity>();
		
		//需要批量更新到数据的记录集合
		List<OutLineEntity> outLineEntityUpdateList = new ArrayList<OutLineEntity>();
		
		HashMap<String, String> repeatMap = new HashMap<String, String>();
		
		//省
		Map<String,String> provinceMap = new HashMap<String,String>();
		
		//市
		Map<String,String> cityMap = new HashMap<String,String>();
		
		//区县
		Map<String,String> areaMap = new HashMap<String,String>();
	 
		//导入文件本身数据不合法的数据量:本身记录逻辑主键重复 字段校验
		int importFileErrorCount = 0;
		
		//成功新增的量
		int addCount = 0;
		
		//覆盖的数据量
		int replaceCount = 0;
		
		try {
			
			list = ExcelUtil.readToListByFile(uploadPath, 7, 2);
			if(list != null && list.size() > 0){
				for (Map<String, String> map : list) {
					
					OutLineEntity outLineEntity = new OutLineEntity();
					
					String provinceName = StringUtil.trim(map.get(0 + ""));
					String cityName = StringUtil.trim(map.get(1 + ""));
					String areaName = StringUtil.trim(map.get(2+ ""));
					String lowestFee = StringUtil.trim(map.get(3+ ""));
					String weightRate = StringUtil.trim(map.get(4+ ""));
					String volumeRate = StringUtil.trim(map.get(5+ ""));
					String remark = StringUtil.trim(map.get(6+ ""));
					
					String subKey = "省:"+provinceName+" 市:"+cityName+" 区县:"+areaName;
					
					//省 是否为空
					if(null == provinceName || "".equals(provinceName)){
						resultMap.put("error","省不能为空");
						break;
					}
					
					//市 是否为空
					if(null == cityName || "".equals(cityName)){
						resultMap.put("error","市不能为空");
						break;
					}

					//区县 是否为空
					if(null == areaName || "".equals(areaName)){
						resultMap.put("error","区县不能为空");
						break;
					}
					
					//外发系数最低收费 是否为空
					if(null == lowestFee || "".equals(lowestFee)){
						resultMap.put("error","外发系数最低收费不能为空");
						break;
					}					
					
					//重量外发系数 是否为空
					if(null == weightRate || "".equals(weightRate)){
						resultMap.put("error","重量外发系数不能为空");
						break;
					}		
					
					//体积外发系数 是否为空
					if(null == volumeRate || "".equals(volumeRate)){
						resultMap.put("error","体积外发系数不能为空");
						break;
					}
					
					//根据省份名称获取对应的省份编码
					if(provinceMap.containsKey(provinceName)){
						outLineEntity.setProvinceCode(provinceMap.get(provinceName));
					} else {
						
						//北京市 天津市 重庆市 特殊地市要加类型
						DistrictEntity districtEntityVo = new DistrictEntity();
						districtEntityVo.setDistrictName(provinceName);
						districtEntityVo.setDistrictType("PROVINCE");
						
						List<DistrictEntity> provinceList = districtService.queryDistrictByEntity(districtEntityVo);
						if(provinceList == null || provinceList.size() == 0){
							resultMap.put("error","不存在该名称的省份:"+provinceName);
							break;
						} else {
							DistrictEntity provinceObj = provinceList.get(0);
							provinceMap.put(provinceName, provinceObj.getDistrictCode());
							outLineEntity.setProvinceCode(provinceObj.getDistrictCode());
						}
					}
					
					//根据城市名称获取对应的城市编码
					if(cityMap.containsKey(cityName)){
						outLineEntity.setCityCode(cityMap.get(cityName));
					} else {
						DistrictEntity districtEntityVo = new DistrictEntity();
						districtEntityVo.setDistrictName(cityName);
						districtEntityVo.setParentDistrictCode(provinceMap.get(provinceName));
						districtEntityVo.setDistrictType("CITY");
						List<DistrictEntity> cityList = districtService.queryDistrictByEntity(districtEntityVo);
						if(cityList == null || cityList.size() == 0){
							resultMap.put("error","不存在该名称的城市:"+cityName);
							break;
						} else {
							DistrictEntity cityObj = cityList.get(0);
							cityMap.put(cityName, cityObj.getDistrictCode());
							outLineEntity.setCityCode(cityObj.getDistrictCode());
						}
					}
					
					//根据区县名称获取对应的区县编码
					if(areaMap.containsKey(areaName)){
						outLineEntity.setAreaCode(areaMap.get(areaName));
					} else {
						DistrictEntity districtEntityVo = new DistrictEntity();
						districtEntityVo.setDistrictName(areaName);
						districtEntityVo.setParentDistrictCode(cityMap.get(cityName));
						districtEntityVo.setDistrictType("AREA");
						List<DistrictEntity> areaList = districtService.queryDistrictByEntity(districtEntityVo);
						if(areaList == null || areaList.size() == 0 ){
							resultMap.put("error","不存在该名称的区县:"+areaName);
							break;
						} else {
							DistrictEntity areaObj = areaList.get(0);
							areaMap.put(areaName, areaObj.getDistrictCode());
							outLineEntity.setAreaCode(areaObj.getDistrictCode());
						}
					}
					
					
					outLineEntity.setLowestFee(new BigDecimal(lowestFee));
					outLineEntity.setWeightRate(new BigDecimal(weightRate));
					outLineEntity.setVolumeRate(new BigDecimal(volumeRate));
					outLineEntity.setRemark(remark);
					 
					
					if (repeatMap.containsKey(subKey)) {
						resultMap.put("error", "数据重复：" + subKey);
						break;
					}
					repeatMap.put(subKey, subKey);
					
					//检查当前逻辑主键在数据库中是否已经存在 若不存在 则新增 否则更新
					long count = countOfOutLineByEntity(outLineEntity);
					if(count > 0){ //数据库中已经存在
						outLineEntityUpdateList.add(outLineEntity);
					} else {
						outLineEntityAddList.add(outLineEntity);
					}
				}
				
				
				if(resultMap.containsKey("error")){ //若存在错误
					resultMap.put("error", resultMap.get("error"));
					return resultMap;
				}
				
				if(outLineEntityUpdateList.size() > 0){ //批量更新
					batchUpdateEntityList(outLineEntityUpdateList);
				}
				
				if(outLineEntityAddList.size() > 0 ){ //批量新增
					batchInsertEntityList(outLineEntityAddList);
				}
				
				String msg = "本次导入共："+list.size() +" 条记录，其中新增记录:"
				                     +outLineEntityAddList.size()
				                     +" 条，更新记录:"+outLineEntityUpdateList.size();
				
				resultMap.put("success", msg);
				
			}
			
		} catch (Exception e) {
			logger.error("OutLineServiceImpl.excelImport 从导入文件中读取列表异常:"+e.toString());
		} 
		
		return resultMap;
	}


	@Override
	public void destroy(String destoryIdStr) {
		Map<String,String> param = new HashMap<String, String>();
		String destoryCondtion = " AND T.ID IN ("+destoryIdStr+")";
		param.put("destoryIdStr", destoryCondtion);
		param.put("modifyUserCode", MiserUserContext.getCurrentUser().getUserName());
		outLineDao.destroy(param);
	}

}

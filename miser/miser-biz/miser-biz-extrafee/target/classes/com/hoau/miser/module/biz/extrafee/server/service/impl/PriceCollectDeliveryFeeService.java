/**
 * 
 */
package com.hoau.miser.module.biz.extrafee.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.IPriceCityService;
import com.hoau.miser.module.biz.base.api.server.service.ITranstypeService;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityVo;
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;
import com.hoau.miser.module.biz.extrafee.server.dao.PriceCollectDeliveryFeeDao;
import com.hoau.miser.module.biz.extrafee.server.service.IPriceCollectDeliveryFeeService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceCollectDeliveryFeeEntity;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.base.api.server.service.IDataDictionaryValueService;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryValueEntity;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author dengyin
 */

@Service
public class PriceCollectDeliveryFeeService implements
		IPriceCollectDeliveryFeeService {

	private static final Logger logger = LoggerFactory.getLogger(PriceCollectDeliveryFeeService.class);
	
	@Resource
	private PriceCollectDeliveryFeeDao priceCollectDeliveryFeeDao;
	
	@Resource
	private ITranstypeService transtypeService;
	
	@Resource
	private IPriceCityService priceCityService;	
	
	@Resource
	private IDataDictionaryValueService dataDictionaryValueService;
	
	@Override
	public List<PriceCollectDeliveryFeeEntity> queryListByEntity(
			PriceCollectDeliveryFeeEntity entity,int start,int limit) {
		
		RowBounds rowBounds = new RowBounds(start,limit);
		return priceCollectDeliveryFeeDao.queryListByEntity(entity, rowBounds);
	}

	@Override
	public List<PriceCollectDeliveryFeeEntity> queryListByEntity2(
			PriceCollectDeliveryFeeEntity entity,int start,int limit) {
		
		RowBounds rowBounds = new RowBounds(start,limit);
		return priceCollectDeliveryFeeDao.queryListByEntity2(entity, rowBounds);
	}
	
	
	@Override
	public Long queryCountByEntity(PriceCollectDeliveryFeeEntity entity) {
		return priceCollectDeliveryFeeDao.queryCountByEntity(entity);
	}
	
	public PriceCollectDeliveryFeeEntity queryEntityById(String id) {
		if(StringUtil.isEmpty(id)){
			return null;
		}
		PriceCollectDeliveryFeeEntity entity=new PriceCollectDeliveryFeeEntity();
		entity.setId(id);
		List<PriceCollectDeliveryFeeEntity> list=this.queryListByEntity(entity, 0, 1);
		return list!=null&&list.size()>0?list.get(0):null;
	}	

	@Override
	public Integer add(PriceCollectDeliveryFeeEntity entity,boolean isConfirmAdd) {
		entity.setActive(MiserConstants.YES);
		return this.addOrUpdate(entity, isConfirmAdd);
	}

	@Override
	public Integer update(PriceCollectDeliveryFeeEntity entity,boolean isConfirmAdd) {
		return this.addOrUpdate(entity, isConfirmAdd);
	}

	@Override
	public void delete(String selectedIdStr) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("modifyUserCode", MiserUserContext.getCurrentUser().getUserName());
		paramMap.put("selectedIdStr", selectedIdStr);
		priceCollectDeliveryFeeDao.delete(paramMap);
	}
	
	private Integer addOrUpdate(PriceCollectDeliveryFeeEntity entity,Boolean isConfirmAdd) {
		//默认成功
		Integer result=1;
		
		//查询当前信息状态,如果状态不符合修改条件，给用户以提示
		PriceCollectDeliveryFeeEntity selfEntity = this.queryEntityById(entity.getId());

		if(null != selfEntity){
			if("是".equals(selfEntity.getActive())){
				selfEntity.setActive("Y");
			} else if("否".equals(selfEntity.getActive())){
				selfEntity.setActive("N");
			}
		}
		
		if(selfEntity!=null){
			if (selfEntity.getActive().equals(MiserConstants.NO)
					|| !("EFFECTIVE".equals(selfEntity.getStatus()) || "WAIT"
							.equals(selfEntity.getStatus()))) {
				result = 2;
				return result;
			}
		}
		//查询历史有效信息
		PriceCollectDeliveryFeeEntity queryEntity=new PriceCollectDeliveryFeeEntity();
		queryEntity.setActive(MiserConstants.YES);
		queryEntity.setBeginPriceCityCode(entity.getBeginPriceCityCode());
		queryEntity.setTransTypeCode(entity.getTransTypeCode());
		queryEntity.setStatus("EFFECTIVE");
		queryEntity.setCollectDeliveryType(entity.getCollectDeliveryType());
		
		
		//查询生效中的信息
		List<PriceCollectDeliveryFeeEntity> state2List= this.queryListByEntity(queryEntity, 0, 1);
		
		//查询待生效的信息
		queryEntity.setStatus("WAIT");
		List<PriceCollectDeliveryFeeEntity> state3List= this.queryListByEntity(queryEntity, 0, 1);
		
		if(state3List!=null&&state3List.size()>0){//存在待生效信息
			
			if(isConfirmAdd){//确认新增
			
				//将旧的待生效信息作废
				PriceCollectDeliveryFeeEntity state3Entity=state3List.get(0);
				state3Entity.setActive(MiserConstants.NO);
				beforOperDeal(state3Entity);
				this.priceCollectDeliveryFeeDao.update(state3Entity);
				
			}else{//表示需要确认是否新增
				
				result=0;
				return result;
			}
		}
		
		//新增待生效信息
		entity.setId(null);
		beforOperDeal(entity);
		priceCollectDeliveryFeeDao.add(entity);
		
		// 将生效中的信息的失效时间赋值为待生效的生效时间
		if(state2List!=null&&state2List.size()>0){
			PriceCollectDeliveryFeeEntity state2Entity=state2List.get(0);
			beforOperDeal(state2Entity);
			state2Entity.setInvalidTime(entity.getEffectiveTime());
			priceCollectDeliveryFeeDao.update(state2Entity);
		}
		result=1;
		return result;
		
	}
	
	public void beforOperDeal(PriceCollectDeliveryFeeEntity entity){
		Date dt = new Date();
		if(StringUtil.isBlank(entity.getId())){//新增的话
			entity.setId(UUIDUtil.getUUID());
			entity.setActive(MiserConstants.YES);
			entity.setCreateTime(dt);
			entity.setCreateUserCode(MiserUserContext.getCurrentUser().getUserName());
		}
		
		if("是".equals(entity.getActive())){
			entity.setActive("Y");
		} else if("否".equals(entity.getActive())){
			entity.setActive("N");
		}
		
		entity.setModifyTime(dt);
		entity.setModifyUserCode(MiserUserContext.getCurrentUser().getUserName());
	}

	@Override
	public String createExcelFile(List<PriceCollectDeliveryFeeEntity> entityList) {
		
		String filePath = null;
		List<String> titleNames = new ArrayList<String>();
		 titleNames.add("出发价格城市");
		 titleNames.add("运输类型");
		 titleNames.add("代收类型");
		 titleNames.add("代收手续费率");
		 titleNames.add("费率锁定方式");
		 titleNames.add("最低化收手续费");
		 titleNames.add("最高化收手续费");
		 titleNames.add("生效时间");
		 titleNames.add("失效时间");
		 titleNames.add("备注");
		 titleNames.add("是否可用");
		 titleNames.add("状态");
		 titleNames.add("创建人");
		 titleNames.add("创建时间");		 
		 titleNames.add("修改人");
		 titleNames.add("修改时间");
		 
		List<String> paramNames=new ArrayList<String>();
		paramNames.add("beginPriceCityName");
		paramNames.add("transTypeName");
		paramNames.add("collectDeliveryName");
		paramNames.add("collectDeliveryRate");
		paramNames.add("rateLockName");
		paramNames.add("lowestCollectDeliveryFee");
		paramNames.add("highestCollectDeliveryFee");
		paramNames.add("effectiveTimeStr");
		paramNames.add("invalidTimeStr");
		paramNames.add("remark");
		paramNames.add("active");
		paramNames.add("status");
		paramNames.add("createUserCode");
		paramNames.add("createTime");
		paramNames.add("modifyUserCode");
		paramNames.add("modifyTime");
			
		try {
			filePath = new ExcelUtil<PriceCollectDeliveryFeeEntity>().exportExcel(titleNames, paramNames, entityList);
		} catch (Exception e) {
			logger.error("生成导出数据文件异常.....");
			e.printStackTrace();
		}
		return filePath;
	}

	@Transactional
	public Map<String, Object> excelImport(String uploadPath) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String, String>> list;
			
			Map<String, Object> retMap = new HashMap<String, Object>();
			List<PriceCollectDeliveryFeeEntity> entityList = new ArrayList<PriceCollectDeliveryFeeEntity>();
			HashMap<String, String> repeatMap = new HashMap<String, String>();
	 
			
			//出发城市缓存
	        Map<String, String> beginPriceCityNameAndCodeMap = new HashMap<String, String>();
	        
			// 运输类型
			Map<String, String> transTypeCodeMap = new HashMap<String, String>();

			TranstypeVo transtypeVo = new TranstypeVo();
			TranstypeEntity transtypeEntity = new TranstypeEntity();
			transtypeEntity.setActive(MiserConstants.YES);
			transtypeVo.setTranstypeEntity(transtypeEntity);
			List<TranstypeEntity> transtypeEntityList = transtypeService
					.queryTranstypes(transtypeVo);
			for (TranstypeEntity value : transtypeEntityList) {
				transTypeCodeMap.put(value.getName(), value.getCode());
			}
			
			//代收类型
			List<DataDictionaryValueEntity> collectDeliverTypeList = dataDictionaryValueService.queryParamByTermsCode("COLLECTDELIVERYTYPE");
	        Map<String, String> collectDeliverTypeNameCodeMap = new HashMap<String, String>();
			if(null != collectDeliverTypeList && collectDeliverTypeList.size() > 0){
				for(DataDictionaryValueEntity curEntity : collectDeliverTypeList){
					String valueCode = curEntity.getValueCode();
					String valueName = curEntity.getValueName();
					
					collectDeliverTypeNameCodeMap.put(valueName, valueCode);
				}
			}
			
			//费率锁定方式
			List<DataDictionaryValueEntity> rateLockTypeList = dataDictionaryValueService.queryParamByTermsCode("LOCK_TYPE");
	        Map<String, String> rateLockTypeNameCodeMap = new HashMap<String, String>();
			if(null != rateLockTypeList && rateLockTypeList.size() > 0){
				for(DataDictionaryValueEntity curEntity : rateLockTypeList){
					String valueCode = curEntity.getValueCode();
					String valueName = curEntity.getValueName();
					
					rateLockTypeNameCodeMap.put(valueName, valueCode);
				}
			}	        
	        
	        
			
			try {
				
				list = ExcelUtil.readToListByFile(uploadPath, 10, 2);
				if(list != null && list.size() > 0){
					for (Map<String, String> map : list) {
						
						PriceCollectDeliveryFeeEntity entity = new PriceCollectDeliveryFeeEntity();
						
						//出发价格城市
						String beginPriceCityName = StringUtil.trim(map.get(0 + ""));
						
						//运输类型
						String transTypeName = StringUtil.trim(map.get(1 + ""));
						
						//代收类型
						String collectDeliveryTypeName = StringUtil.trim(map.get(2+ ""));
						
						//代收手续费率
						String collectDeliveryRate = StringUtil.trim(map.get(3+ ""));
						
						//最低代收手续费
						String lowestCollectDeliveryFee = StringUtil.trim(map.get(4+ ""));
						
						//最高代收手续费
						String hightestCollectDeliveryFee = StringUtil.trim(map.get(5+ ""));						
						
						//费率锁定方式
						String rateLockTypeName = StringUtil.trim(map.get(6+ ""));
						
						//生效时间
						String effectiveTime = StringUtil.trim(map.get(7+ ""));
						
						//失效时间
						String invalidTime = StringUtil.trim(map.get(8+ ""));
						
						//备注
						String remark = StringUtil.trim(map.get(9+ ""));
						 
						
						String subKey = "出发价格城市:"+beginPriceCityName+" 运输类型:"+transTypeName+" 代收类型:"+collectDeliveryTypeName;
						
						if(null == beginPriceCityName || "".equals(beginPriceCityName)){
							retMap.put("error","出发价格城市不能为空");
							break;
						}
						
						if(null == transTypeName|| "".equals(transTypeName)){
							retMap.put("error","运输类型不能为空");
							break;
						}

						if(null == collectDeliveryTypeName || "".equals(collectDeliveryTypeName)){
							retMap.put("error","代收类型不能为空");
							break;
						}
						
						if(null == effectiveTime || "".equals(effectiveTime)){
							retMap.put("error","生效时间不能为空");
							break;
						}					
						  
						//校验 出发价格城市的名称是否存在
						//根据名称从接口获取出发价格城市编码
		                if (beginPriceCityNameAndCodeMap.containsKey(beginPriceCityName)) {
		                    entity.setBeginPriceCityCode(beginPriceCityNameAndCodeMap.get(beginPriceCityName));
		                } else {
		                    PriceCityEntity startPriceCityEntity = new PriceCityEntity();
		                    startPriceCityEntity.setName(StringUtil.trim(map.get(0 + "")));
		                    startPriceCityEntity.setType("SEND");
		                    startPriceCityEntity.setPriceCityScope("STANDARD");
		                    
		                    PriceCityVo startPriceCityVo = new PriceCityVo();
		                    startPriceCityVo.setQueryParam(startPriceCityEntity);
		                    PriceCityEntity startPriceCity = priceCityService.queryPriceCityByName(startPriceCityVo);
		                    if (startPriceCity != null) {
		                    	beginPriceCityNameAndCodeMap.put(beginPriceCityName, startPriceCity.getCode());
		                        entity.setBeginPriceCityCode(startPriceCity.getCode());
		                    } else {
		                        retMap.put("error","不存在的出发城市:" + StringUtil.trim(map.get(0 + "")));
		                        break;
		                    }
		                }						
						
						//校验  运输类型名称是否存在
						if (!transTypeCodeMap.containsKey(StringUtil.trim(map.get(1 + "")))) {
							retMap.put("error","不存在的运输类型:" + StringUtil.trim(map.get(1 + "")));
							break;
						}
		                
						//校验 代收类型是否存在
						if (!collectDeliverTypeNameCodeMap.containsKey(collectDeliveryTypeName)) {
							retMap.put("error","不存在的代收类型:" + collectDeliveryTypeName);
							break;
						}
						
						
						//校验 费率锁定方式是否存在
						if (!rateLockTypeNameCodeMap.containsKey(rateLockTypeName)) {
							retMap.put("error","不存在的费率锁定方式:" + rateLockTypeName);
							break;
						}						
						
						if (repeatMap.containsKey(subKey)) {
							retMap.put("error", "数据重复：" + subKey);
							break;
						}
						repeatMap.put(subKey, subKey);
				 
						
						//运输类型
						entity.setTransTypeCode(transTypeCodeMap.get(transTypeName));
						
						//代收类型
						entity.setCollectDeliveryType(Integer.valueOf(collectDeliverTypeNameCodeMap.get(collectDeliveryTypeName)));
						
						//代收手续费率
						if(null != collectDeliveryRate && !"".equals(collectDeliveryRate)){
							entity.setCollectDeliveryRate(new BigDecimal(collectDeliveryRate));
						}
						
						//最低代收手续费
						if(null != lowestCollectDeliveryFee && !"".equals(lowestCollectDeliveryFee)){
							entity.setLowestCollectDeliveryFee(new BigDecimal(lowestCollectDeliveryFee));
						}						
						
						//最高代收手续费
						if(null != hightestCollectDeliveryFee && !"".equals(hightestCollectDeliveryFee)){
							entity.setHighestCollectDeliveryFee(new BigDecimal(hightestCollectDeliveryFee));
						}								
						
						//费率锁定方式
						entity.setRateLockType(Integer.valueOf(rateLockTypeNameCodeMap.get(rateLockTypeName)));
						
						//生效时间
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						entity.setEffectiveTime(format.parse(effectiveTime));
						
						//失效时间
						if(null != invalidTime){
							entity.setInvalidTime(format.parse(invalidTime));
						}
						
						//备注
						entity.setRemark(remark);
						
						entityList.add(entity);
					}
					
					
					if(retMap.containsKey("error")){ //若存在错误
						resultMap.put("error", retMap.get("error"));
						return resultMap;
					}
					
					for(PriceCollectDeliveryFeeEntity curEntity : entityList){
						this.addOrUpdate(curEntity, true);
					}
					
					String msg = "本次导入共："+list.size() +" 条记录，处理成功:"+entityList.size();
					resultMap.put("success", msg);
				}
				
			} catch (Exception e) {
				logger.error("PriceCollectDeliveryFeeService.excelImport 从导入文件中读取列表异常:"+e.toString());
			} 
			
			return resultMap;
	}
}

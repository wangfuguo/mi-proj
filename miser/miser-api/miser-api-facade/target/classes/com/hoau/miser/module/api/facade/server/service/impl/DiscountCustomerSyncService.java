/**   
* @Title: DiscountCustomerService.java 
* @Package com.hoau.miser.module.biz.discount.server.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 陈启勇 
* @date 2016年1月5日 下午4:05:58 
* @version V1.0   
*/
package com.hoau.miser.module.api.facade.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.facade.server.dao.DiscountCustomerSyncDao;
import com.hoau.miser.module.api.facade.server.service.IDiscountCustomerSyncService;
import com.hoau.miser.module.api.facade.server.service.IPriceCustomerSyncService;
import com.hoau.miser.module.api.facade.server.service.IPriceSectionSyncService;
import com.hoau.miser.module.api.facade.shared.define.BaseResponseEnum;
import com.hoau.miser.module.api.facade.shared.domain.DiscountCustomerEntity;
import com.hoau.miser.module.api.facade.shared.domain.DiscountCustomerSyncEntity;
import com.hoau.miser.module.api.facade.shared.domain.PriceSectionSyncEntity;
import com.hoau.miser.module.api.facade.shared.domain.SyncCustomerPrice;
import com.hoau.miser.module.api.facade.shared.vo.BaseResponseVo;
import com.hoau.miser.module.api.facade.shared.vo.DiscountCustomerVo;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * ClassName: DiscountCustomerService 
 * @author 陈启勇
 * @date 2016年1月13日
 * @version V1.0   
 */
@Service
public class DiscountCustomerSyncService implements IDiscountCustomerSyncService {

    private static final Logger log = LoggerFactory.getLogger(DiscountCustomerSyncService.class);
    
	@Resource
	private DiscountCustomerSyncDao discountCustomerDao;
	@Resource
	private IPriceSectionSyncService priceSectionSyncService;
	@Resource
	private IPriceCustomerSyncService priceCustomerSyncService;
	@Resource
	private IPriceSectionService priceSectionService;
	
	@Override
	public List<DiscountCustomerEntity> queryListByParam(DiscountCustomerVo beanv,int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return discountCustomerDao.queryListByParam(beanv, rowBounds);
	}
	@Override
	public Integer addDiscountCustomer(DiscountCustomerEntity bean) {
		Integer result=1;
		Date effectiveTime = bean.getEffectiveTime();
		Date invalidTime = bean.getInvalidTime();
		if(effectiveTime != null && invalidTime != null){
			long currentTime = System.currentTimeMillis();
			Date date = new Date(currentTime);
			if(effectiveTime.compareTo(date)<0){
				bean.setEffectiveTime(date);
			}
			if(invalidTime.compareTo(effectiveTime) <=0 && invalidTime.compareTo(date)<=0){
				return 3;
			}
		}
		result = addOrUpdateDiscountCustomer(bean,"ADD");
		return result;
	}
	
	private Integer addOrUpdateDiscountCustomer(DiscountCustomerEntity bean,String flag) {
		//默认成功
		Integer result=1;
		//查询历史有效信息
		DiscountCustomerEntity queryEntity=new DiscountCustomerEntity();
		queryEntity.setActive(MiserConstants.YES);
		queryEntity.setCustomerCode(bean.getCustomerCode());
		queryEntity.setTransTypeCode(bean.getTransTypeCode());
		queryEntity.setState(DiscountCustomerEntity.STATE_2);
		DiscountCustomerVo queryVo1=new DiscountCustomerVo();
		queryVo1.setDiscountCustomerEntity(queryEntity);
		//查询生效中的信息
		List<DiscountCustomerEntity> state2List= this.queryListByParam(queryVo1, 1, 0);
		//查询待生效的信息
		queryEntity.setState(DiscountCustomerEntity.STATE_3);
		List<DiscountCustomerEntity> state3List= this.queryListByParam(queryVo1, 1, 0);
		if(state2List != null && state2List.size()>0){
			DiscountCustomerEntity state2Entity=state2List.get(0);
			state2Entity.setInvalidTime(bean.getEffectiveTime());
			beforAdd(state2Entity);
			discountCustomerDao.updateDiscountCustomer(state2Entity);
		}
		if(state3List!=null&&state3List.size()>0){//存在待生效信息
			//将旧的待生效信息作废
			DiscountCustomerEntity state3Entity=state3List.get(0);
			state3Entity.setActive(MiserConstants.NO);
			beforAdd(state3Entity);
			discountCustomerDao.updateDiscountCustomer(state3Entity);
		}
		//新增待生效信息
		bean.setId(null);
		beforAdd(bean);
		discountCustomerDao.insert(bean);
		
		return result;
	}
	
	/**
	 * 
	 * @Description: 客户折扣初始值
	 * @param @param bean   
	 * @return void 
	 * @throws
	 * @author 陈启勇
	 * @date 2016年1月6日
	 */
	public DiscountCustomerEntity beforAdd(DiscountCustomerEntity bean){
		Date dt = new Date();
		if(StringUtil.isBlank(bean.getId())){//新增的话
			bean.setId(UUIDUtil.getUUID());
			bean.setActive(MiserConstants.ACTIVE);
			bean.setCreateDate(dt);
		}
		bean.setDataOrign(DataOrignEnum.OA.getName());
		bean.setModifyDate(dt);
		bean.setModifyUser(bean.getCreateUser());
		return bean;
	}

	@Override
	@Transactional
	public BaseResponseVo<Object> DiscountCustomerSync(String jsonString) {
		/* out参数 */
		BaseResponseVo<Object> response = new BaseResponseVo<Object>();
		try{
			List<DiscountCustomerSyncEntity> listBean = new ArrayList<DiscountCustomerSyncEntity>();
			if(StringUtil.isEmpty(jsonString)){
				response.setStatus(BaseResponseEnum.DATA_ERROR.getStatus());
				response.setMessage(BaseResponseEnum.DATA_ERROR.getMessage());
				
				log.info("DiscountCustomerSyncService DiscountCustomerSync jsonString 为空直接返回");
				return response;
			}else{
				try{
					listBean = JSONObject.parseArray(jsonString, DiscountCustomerSyncEntity.class);
					List<SyncCustomerPrice> scpBeanList = new ArrayList<SyncCustomerPrice>();
					SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
					for(DiscountCustomerSyncEntity syncBean:listBean){
						log.info("DiscountCustomerSyncService DiscountCustomerSync customerCode:"+syncBean.getCustomerCode()+" 开始");
						
						BaseResponseVo<Object> responsePrice = verificationCustomerPrice(syncBean);
						
						log.info("DiscountCustomerSyncService DiscountCustomerSync customerCode"+syncBean.getCustomerCode()+" responsePrice:"+responsePrice.getMessage());
						
						if(!(BaseResponseEnum.SUCCESS.getStatus().equals(responsePrice.getStatus()))){
							return responsePrice;
						}
						
						//组装数据同步至客户价格
						SyncCustomerPrice scpBean = new SyncCustomerPrice();
						scpBean.setCustomerCode(syncBean.getCustomerCode());//客户编号
						scpBean.setTransTypeCode(syncBean.getTransTypeCode());//运输类型编号
						scpBean.setHeavyFloatPrice(syncBean.getHeavyFloatPrice());//重货浮动浮动金额
						scpBean.setLightFloatPrice(syncBean.getLightFloatPrice());//轻货浮动浮动金额
						scpBean.setFloatPercentage(syncBean.getFloatPercentage());//浮动百分比
						scpBean.setEffectiveTime(simpledateformat.parse(syncBean.getEffectiveTime()));//生效时间
						scpBean.setInvalidTime(simpledateformat.parse(syncBean.getInvalidTime()));
						scpBean.setUserCode(syncBean.getCreateUserName());
						scpBean.setFreightSectionPrice(syncBean.getFreightSectionPrice());
						
						scpBeanList.add(scpBean);
					}
					
					//add by dengyin 2016-5-22 14:31:56 需要添加  为空的判断 
					String flag = "success";
					if(scpBeanList.size() >0){
						flag = priceCustomerSyncService.syncCustomerPrice(scpBeanList);
					}
					
					log.info("DiscountCustomerSyncService DiscountCustomerSync 调用 syncCustomerPrice flag:"+flag);
					//end by dengyin 2016-5-22 14:31:56 需要添加  为空的判断 
					
					if("success".equals(flag)){
						response.setStatus(BaseResponseEnum.SUCCESS.getStatus());
						response.setMessage(BaseResponseEnum.SUCCESS.getMessage());
						return response;
					}else{
						response.setStatus(BaseResponseEnum.CUSTOMER_PRICE_ERROR.getStatus());
						response.setMessage(BaseResponseEnum.CUSTOMER_PRICE_ERROR.getMessage());
						return response;
					}
				}catch(Exception e){
					log.info("DiscountCustomerSyncService DiscountCustomerSync try catch 1 err:"+e.toString());
					response.setStatus(BaseResponseEnum.JSON_ERROR.getStatus());
					response.setMessage(BaseResponseEnum.JSON_ERROR.getMessage());
					return response;
				}
			}
		}catch(Exception e){
			log.info("DiscountCustomerSyncService DiscountCustomerSync try catch 2 err:"+e.toString());
			response.setStatus(BaseResponseEnum.SYSTEM_ERROR.getStatus());
			response.setMessage(BaseResponseEnum.SYSTEM_ERROR.getMessage());
			return response;
		}
	}
	
	public BaseResponseVo<Object> verificationCustomerPrice(DiscountCustomerSyncEntity syncBean){
		/* out参数 */
		BaseResponseVo<Object> response = new BaseResponseVo<Object>();
		DiscountCustomerEntity bean = new DiscountCustomerEntity();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		try{
			if(null == syncBean){
				response.setStatus(BaseResponseEnum.JSON_ERROR.getStatus());
				response.setMessage(BaseResponseEnum.JSON_ERROR.getMessage());
				return response;
			}
			//判断客户编码是否为空
			String customerCode = syncBean.getCustomerCode();
			if(StringUtil.isEmpty(customerCode)){
				response.setStatus(BaseResponseEnum.CUSTOMER_CODE.getStatus());
				response.setMessage(BaseResponseEnum.CUSTOMER_CODE.getMessage());
				return response;
			}else{
				bean.setCustomerCode(customerCode);
			}
			//判断运输类型是否为空
			String transTypeCode = syncBean.getTransTypeCode();
			if(StringUtil.isEmpty(transTypeCode)){
				bean.setTransTypeCode("00000000000000000000");
			}else{
				bean.setTransTypeCode(transTypeCode);
			}
			String freightSectionPrice = syncBean.getFreightSectionPrice();
			
			//add by dengyin 2016-5-22 14:57:11 运费优惠分段与（浮动比例/浮动金额）必须传其中一个
			boolean freightSectionPriceIsNull = false;
			boolean heightDiscountIsNull = false;
			boolean lightDiscountIsNull = false;
			boolean floatPercentIsNull = false;
			//end by dengyin 2016-5-22 14:57:11 运费优惠分段与（浮动比例/浮动金额）必须传其中一个
			
			//判断运费是否为空，不为空并且运输类型不为空
			if(StringUtil.isNotEmpty(freightSectionPrice)){
				if(!isNumeric(freightSectionPrice)){
					response.setStatus(BaseResponseEnum.PRICE_IS_NUM.getStatus());
					response.setMessage(BaseResponseEnum.PRICE_IS_NUM.getMessage());
					return response;
				}
				BaseResponseVo<Object> transTypeCodeResponse = verificationPrice(bean,freightSectionPrice,"FREIGHT");
				if(null != transTypeCodeResponse){
					return transTypeCodeResponse;
				}
			} else { 
				
				freightSectionPriceIsNull = true;
				
				 /**
				  *  add by dengyin 2016-5-16 11:03:48 运费分段折扣 为空时 
				  *  【需要复制最近一条非作废状态的记录的运费优惠分段到新运费优惠分段中】
				  */
				DiscountCustomerEntity curDiscountCusEntity = discountCustomerDao.selectByCusTransCodeForLatest(bean);
				if(null != curDiscountCusEntity){
					bean.setFreightSectionCode(curDiscountCusEntity.getFreightSectionCode());
				}
			}
			
			/**
			 *  end by dengyin 2016-5-16 11:03:48 运费分段折扣 为空时 
			 *  【需要复制最近一条非作废状态的记录的运费优惠分段到新运费优惠分段中】
			 */
		 
			String discountAccodingPcTime = syncBean.getDiscountAccodingPcTime();
			if(StringUtil.isBlank(discountAccodingPcTime)){
				response.setStatus(BaseResponseEnum.DISCOUNT_ACCODINGPC_TIME.getStatus());
				response.setMessage(BaseResponseEnum.DISCOUNT_ACCODINGPC_TIME.getMessage());
				return response;
			}else{
				bean.setDiscountAccodingPcTime(simpledateformat.parse(discountAccodingPcTime));
			}
			
			BigDecimal heavyFloatPrice = syncBean.getHeavyFloatPrice();
			if("".equals(heavyFloatPrice) || heavyFloatPrice == null){
				heightDiscountIsNull = true;
				bean.setHeavyFloatPrice(null);
			}else{
				bean.setHeavyFloatPrice(heavyFloatPrice);
			}
			
			BigDecimal lightFloatPrice = syncBean.getLightFloatPrice();
			if("".equals(lightFloatPrice) || lightFloatPrice == null){
				lightDiscountIsNull = true;
				bean.setLightFloatPrice(null);
			}else{
				bean.setLightFloatPrice(lightFloatPrice);
			}
			
			BigDecimal floatPercentage = syncBean.getFloatPercentage();
			if("".equals(floatPercentage) || floatPercentage == null){
				floatPercentIsNull = true;
				bean.setFloatPercentage(null);
			}else{
				bean.setFloatPercentage(floatPercentage);
			}
			
			//add by dengyin 2016-5-22 15:01:49 运费优惠分段与（浮动比例/浮动金额）必须传其中一个 都为空需要返回异常
			if(freightSectionPriceIsNull && heightDiscountIsNull && lightDiscountIsNull && floatPercentIsNull){
				response.setStatus(BaseResponseEnum.fregihtSectionHeavyLightFloatBothNull.getStatus());
				response.setMessage(BaseResponseEnum.fregihtSectionHeavyLightFloatBothNull.getMessage());
				return response;
			}
			//end by dengyin 2016-5-22 15:01:49 运费优惠分段与（浮动比例/浮动金额）必须传其中一个
			
			
			String discountPriorityType = syncBean.getDiscountPriorityType();
			if(StringUtil.isEmpty(discountPriorityType)){
				response.setStatus(BaseResponseEnum.DISCOUNT_PRIORITY_TYPE.getStatus());
				response.setMessage(BaseResponseEnum.DISCOUNT_PRIORITY_TYPE.getMessage());
				return response;
			}else{
				bean.setDiscountPriorityType(discountPriorityType);
			}
			//验证价格
			BaseResponseVo<Object> responsePrice = verificationCustomerPrice(bean, syncBean);
			if(null != responsePrice.getStatus()){
				return responsePrice;
			}
			//折扣生效日期
			String effectiveTime = syncBean.getEffectiveTime();
			if(effectiveTime == null){
				response.setStatus(BaseResponseEnum.EFFECTIVE_TIME.getStatus());
				response.setMessage(BaseResponseEnum.EFFECTIVE_TIME.getMessage());
				return response;
			}else{
				bean.setEffectiveTime(simpledateformat.parse(effectiveTime));
			}
			//折扣失效日期
			String invalidTime = syncBean.getInvalidTime();
			if(invalidTime == null){
				response.setStatus(BaseResponseEnum.INVALID_TIME.getStatus());
				response.setMessage(BaseResponseEnum.INVALID_TIME.getMessage());
				return response;
			}else{
				bean.setInvalidTime(simpledateformat.parse(invalidTime));
			}
			//备注
			String remark = syncBean.getRemark();
			if(StringUtil.isNotEmpty(remark)){
				bean.setRemark(remark);
			}
			//创建人
			String createUserName = syncBean.getCreateUserName();
			if(StringUtil.isEmpty(createUserName)){
				response.setStatus(BaseResponseEnum.CREATE_USER_NAME.getStatus());
				response.setMessage(BaseResponseEnum.CREATE_USER_NAME.getMessage());
				return response;
			}else{
				bean.setCreateUser(createUserName);
			}
			Integer flag = addDiscountCustomer(bean);
			if(1 == flag){
				response.setStatus(BaseResponseEnum.SUCCESS.getStatus());
				response.setMessage(BaseResponseEnum.SUCCESS.getMessage());
				return response;
			}else{
				response.setStatus(BaseResponseEnum.BUSINESS_EXCEPTION.getStatus());
				response.setMessage(BaseResponseEnum.BUSINESS_EXCEPTION.getMessage());
				return response;
			}
		}catch(Exception e){

			log.info("DiscountCustomerSyncService verificationCustomerPrice try catch err:"+e.toString());
			
			response.setStatus(BaseResponseEnum.SYSTEM_ERROR.getStatus());
			response.setMessage(BaseResponseEnum.SYSTEM_ERROR.getMessage());
			return response;
		}
	}
	
	
	public BaseResponseVo<Object> verificationCustomerPrice(DiscountCustomerEntity bean,DiscountCustomerSyncEntity syncBean){
		//提货费分段折扣
		String pickupSectionPrice = syncBean.getPickupSectionPrice();
		if(StringUtil.isNotEmpty(pickupSectionPrice)){
			BaseResponseVo<Object> response = verificationPrice(bean,pickupSectionPrice,"PICKUP");
			if(null != response){
				return response;
			}	
		}
		//送货费分段折扣
		String deliverySectionPrice= syncBean.getDeliverySectionPrice();
		if(StringUtil.isNotEmpty(deliverySectionPrice)){
			BaseResponseVo<Object> response = verificationPrice(bean,deliverySectionPrice,"DELIVERY");
			if(null != response){
				return response;
			}
		}
		//保价率分段折扣
		String insuranceRateSectionPrice = syncBean.getInsuranceRateSectionPrice();
		if(StringUtil.isNotEmpty(insuranceRateSectionPrice)){
			BaseResponseVo<Object> response = verificationPrice(bean,insuranceRateSectionPrice,"INSURANCE_RATE");
			if(null != response){
				return response;
			}
		}
		//保价费分段折扣
		String insuranceSectionPrice = syncBean.getInsuranceSectionPrice();
		if(StringUtil.isNotEmpty(insuranceSectionPrice)){
			BaseResponseVo<Object> response = verificationPrice(bean,insuranceSectionPrice,"INSURANCE");
			if(null != response){
				return response;
			}	
		}
		//工本费分段折扣
		String paperSectionPrice = syncBean.getPaperSectionPrice();
		if(StringUtil.isNotEmpty(paperSectionPrice)){
			BaseResponseVo<Object> response = verificationPrice(bean,paperSectionPrice,"PAPER");
			if(null != response){
				return response;
			}
		}
		//信息费分段折扣
		String smsSectionPrice = syncBean.getSmsSectionPrice();
		if(StringUtil.isNotEmpty(smsSectionPrice)){
			BaseResponseVo<Object> response = verificationPrice(bean,smsSectionPrice,"SMS");
			if(null != response){
				return response;
			}
		}
		//燃油费分段折扣
		String fuelSectionPrice = syncBean.getFuelSectionPrice();
		if(StringUtil.isNotEmpty(fuelSectionPrice)){
			BaseResponseVo<Object> response = verificationPrice(bean,fuelSectionPrice,"FUEL");
			if(null != response){
				return response;
			}
		}
		//附加费分段折扣
		String addSectionPrice = syncBean.getAddSectionPrice();
		if(StringUtil.isNotEmpty(addSectionPrice)){
			BaseResponseVo<Object> response = verificationPrice(bean,addSectionPrice,"EXTRA_FEE");
			if(null != response){
				return response;
			}
		}
		//代收手续费率分段折扣
		String collectionRateSectionPrice = syncBean.getCollectionRateSectionPrice();
		if(StringUtil.isNotEmpty(collectionRateSectionPrice)){
			BaseResponseVo<Object> response = verificationPrice(bean,collectionRateSectionPrice,"COLLECTION_RATE");
			if(null != response){
				return response;
			}
		}
		//代收手续费分段折扣
		String collectionSectionPrice = syncBean.getCollectionSectionPrice();
		if(StringUtil.isNotEmpty(collectionSectionPrice)){
			BaseResponseVo<Object> response = verificationPrice(bean,collectionSectionPrice,"COLLECTION");
			if(null != response){
				return response;
			}	
		}
		//上楼费分段折扣
		String upstairsSectionPrice = syncBean.getUpstairsSectionPrice();
		if(StringUtil.isNotEmpty(upstairsSectionPrice)){
			BaseResponseVo<Object> response = verificationPrice(bean,upstairsSectionPrice,"UPSTAIRS");
			if(null != response){
				return response;
			}
		}
		return new BaseResponseVo<Object>();
	}
	
	public BaseResponseVo<Object> verificationPrice(DiscountCustomerEntity bean,String price,String flag){
		BaseResponseVo<Object> response = new BaseResponseVo<Object>();
		if(StringUtil.isNotEmpty(price)){
			if(!isNumeric(price)){
				response.setStatus(BaseResponseEnum.PRICE_IS_NUM.getStatus());
				response.setMessage(BaseResponseEnum.PRICE_IS_NUM.getMessage());
				return response;
			}
			//运费分段折扣	
			if("FREIGHT".equals(flag)){
				PriceSectionSyncEntity psBean = new PriceSectionSyncEntity();
				psBean.setSectionedItem("FREIGHT");
				psBean.setPrice(price);
				//psBean.setPriceType("MONEY");
				//psBean.setSectionAccodingItem("WEIGHT");
				psBean.setSuitoa("Y");
				psBean.setSuitProduct(bean.getTransTypeCode());
				List<PriceSectionSyncEntity> list = priceSectionSyncService.queryPriceSection(psBean);
				if(list.size()>0){
					bean.setFreightSectionCode(list.get(0).getCode());
					return null;
				}else{
					return null;
				}
//				else{
//					//新增
//					String suitProduct = null;
//					PriceSectionEntity pseBean = new PriceSectionEntity(); 
//					pseBean.setSectionedItem(flag);
//					pseBean.setCreateUser(bean.getCreateUser());
//					pseBean.setSuitProduct(bean.getTransTypeCode());
//					//组装明细
//					JSONArray jsonArray = new JSONArray();
//					JSONObject jsonObject = new JSONObject();
//					jsonObject.put("sectionAccodingItem", "WEIGHT");
//					jsonObject.put("priceType", "MONEY");
//					jsonObject.put("price", price);
//					jsonObject.put("minPrice", 0);
//					jsonObject.put("maxPrice", MiserConstants.DEFAULT_MAX_VALUE);
//					jsonObject.put("startValue", 0);
//					jsonObject.put("endValue", MiserConstants.DEFAULT_MAX_VALUE);
//					jsonObject.put("calcAlone", "Y");
//					jsonArray.add(jsonObject);
//					suitProduct = priceSectionService.addSyncPriceSection(pseBean, jsonArray.toString());
//					bean.setFreightSectionCode(suitProduct);
//					return null;
//				}
			}
			
			else{ 
				PriceSectionSyncEntity psBean = new PriceSectionSyncEntity();
				psBean.setSectionedItem(flag);
				psBean.setPrice(price);
//				psBean.setPriceType("MONEY");
//				psBean.setSectionAccodingItem("WEIGHT");
				psBean.setSuitoa("Y");
				psBean.setSuitProduct(bean.getTransTypeCode());
				List<PriceSectionSyncEntity> list = priceSectionSyncService.queryPriceSection(psBean);
				if(list.size()>0){
					String suitProduct = list.get(0).getCode();
					//提货费分段折扣	
					if("PICKUP".equals(flag)){
						bean.setPickupSectionCode(suitProduct);
						return null;
					}
					//送货费分段折扣	
					if("DELIVERY".equals(flag)){
						bean.setDeliverySectionCode(suitProduct);
						return null;
					}
					//送货费分段折扣	
					if("DELIVERY".equals(flag)){
						bean.setDeliverySectionCode(suitProduct);
						return null;
					}
					//送货费分段折扣	
					if("INSURANCE_RATE".equals(flag)){
						bean.setInsuranceRateSectionCode(suitProduct);
						return null;
					}
					
					//保价费分段折扣	
					if("INSURANCE".equals(flag)){
						bean.setInsuranceSectionCode(suitProduct);
						return null;
					}
					//工本费分段折扣	
					if("PAPER".equals(flag)){
						bean.setPaperSectionCode(suitProduct);
						return null;
					}
					//信息费分段折扣	
					if("SMS".equals(flag)){
						bean.setSmsSectionCode(suitProduct);
						return null;
					}
					//燃油费分段折扣	
					if("FUEL".equals(flag)){
						bean.setFuelSectionCode(suitProduct);
						return null;
					}
					//附加费分段折扣	
					if("EXTRA_FEE".equals(flag)){
						bean.setAddSectionCode(suitProduct);
						return null;
					}
					//代收手续费率分段折扣	
					if("COLLECTION_RATE".equals(flag)){
						bean.setCollectionRateSectionCode(suitProduct);
						return null;
					}
					//代收手续费分段折扣	
					if("COLLECTION".equals(flag)){
						bean.setCollectionSectionCode(suitProduct);
						return null;
					}
					//上楼费分段折扣	
					if("UPSTAIRS".equals(flag)){
						bean.setUpstairsSectionCode(suitProduct);
						return null;
					}
				}
				else{
					return null;
				}
//					//新增
//					String suitProduct = null;
//					PriceSectionEntity pseBean = new PriceSectionEntity(); 
//					pseBean.setSectionedItem(flag);
//					pseBean.setCreateUser(bean.getCreateUser());
//					pseBean.setSuitProduct(bean.getTransTypeCode());
//					//组装明细
//					JSONArray jsonArray = new JSONArray();
//					JSONObject jsonObject = new JSONObject();
//					jsonObject.put("sectionAccodingItem", "WEIGHT");
//					jsonObject.put("priceType", "MONEY");
//					jsonObject.put("price", price);
//					jsonObject.put("startValue", 0);
//					jsonObject.put("endValue", MiserConstants.DEFAULT_MAX_VALUE);
//					jsonObject.put("calcAlone", "Y");
//					jsonObject.put("minPrice", 0);
//					jsonObject.put("maxPrice", MiserConstants.DEFAULT_MAX_VALUE);
//					jsonObject.put("createUser", bean.getCreateUser());
//					jsonArray.add(jsonObject);
//					suitProduct = priceSectionService.addSyncPriceSection(pseBean, jsonArray.toString());
//					//提货费分段折扣	
//					if("PICKUP".equals(flag)){
//						bean.setPickupSectionCode(suitProduct);
//						return null;
//					}
//					//送货费分段折扣	
//					if("DELIVERY".equals(flag)){
//						bean.setDeliverySectionCode(suitProduct);
//						return null;
//					}
//					//送货费分段折扣	
//					if("INSURANCE_RATE".equals(flag)){
//						bean.setInsuranceRateSectionCode(suitProduct);
//						return null;
//					}
//					//保价费分段折扣	
//					if("INSURANCE".equals(flag)){
//						bean.setInsuranceSectionCode(suitProduct);
//						return null;
//					}
//					//工本费分段折扣	
//					if("PAPER".equals(flag)){
//						bean.setPaperSectionCode(suitProduct);
//						return null;
//					}
//					//信息费分段折扣	
//					if("SMS".equals(flag)){
//						bean.setSmsSectionCode(suitProduct);
//						return null;
//					}
//					//燃油费分段折扣	
//					if("FUEL".equals(flag)){
//						bean.setFuelSectionCode(suitProduct);
//						return null;
//					}
//					//附加费分段折扣	
//					if("EXTRA_FEE".equals(flag)){
//						bean.setAddSectionCode(suitProduct);
//						return null;
//					}
//					//代收手续费率分段折扣	
//					if("COLLECTION_RATE".equals(flag)){
//						bean.setCollectionRateSectionCode(suitProduct);
//						return null;
//					}
//					//代收手续费分段折扣	
//					if("COLLECTION".equals(flag)){
//						bean.setCollectionSectionCode(suitProduct);
//						return null;
//					}
//					//上楼费分段折扣	
//					if("UPSTAIRS".equals(flag)){
//						bean.setUpstairsSectionCode(suitProduct);
//						return null;
//					}
//				}
				}
			}
		return response;
	}

	public boolean isNumeric(String str){ 
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");   
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false; 
		} 
			return true; 
	}
}

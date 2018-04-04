package com.hoau.miser.module.biz.job.server.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.eai.pc.vo.PriceCardInfo;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.miser.module.biz.job.server.dao.CustomerPriceSendDao;
import com.hoau.miser.module.biz.job.server.service.ICustomerPriceSendService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobFailureService;
import com.hoau.miser.module.biz.job.server.service.IDataSendJobTimeService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.CustomerPriceSendEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobTimeEntity;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.common.server.service.IActiveMQProducerService;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;


/**
 * 客户价格Service
 * ClassName: CustomerPriceSendService 
 * @author 廖文强
 * @date 2016年1月26日
 * @version V1.0
 */
@Service
public class CustomerPriceSendService implements ICustomerPriceSendService{

	
	private static final Logger log = LoggerFactory.getLogger(CustomerPriceSendService.class);
	@Resource
	private CustomerPriceSendDao customerPriceSendDao;
	
	@Resource
	private IDataSendJobTimeService dataSendJobTimeService;
	
	@Resource
	private IDataSendJobFailureService dataSendJobFailureService;
	
	@Resource
	private IActiveMQProducerService activeMQProducerService;
	
	@Resource
	private IPriceSectionService priceSectionService;
	
	/**
	 * @Fields MAX_COUNT : 每次提交MQ的条数
	 */
	private static int MAX_COUNT = 100;
	
	static{
		try {
			Properties properties = ConfigFileLoadUtil.getPropertiesForClasspath("config.properties");
			String maxCountStr = properties.getProperty("SENDMQ.PRICECARD.MAXCOUNT", "100");
			MAX_COUNT = StringUtils.toInt(maxCountStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void sendPrice() {
		// 记录本次发送时间
		Date thisTime = new Date();
		
		// 查询出上次发送时间
		DataSendJobTimeEntity resultEntity = dataSendJobTimeService
				.getLastSendTime(DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD);
		// 本次组装查询条件
		Map<String, Object> newMapParam = new HashMap<String, Object>();
		newMapParam.put("endTime", thisTime);
		if (resultEntity != null) {
			newMapParam.put("startTime", resultEntity.getLastSendTime());
		}
		
		//发送的数据
		ArrayList<CustomerPriceSendEntity> sendDatas=new ArrayList<CustomerPriceSendEntity>();
				
		//失败数据与成功数据的去重操作
		DataSendJobFailureEntity queryFailureFilterEntity = new DataSendJobFailureEntity();
		if(resultEntity != null){
			queryFailureFilterEntity.setLastSendTime(resultEntity.getLastSendTime());
		}
		queryFailureFilterEntity.setSendTime(thisTime);
		queryFailureFilterEntity.setActive(MiserConstants.ACTIVE);
		queryFailureFilterEntity.setType(DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD);
		
		this.customerPriceSendDao.failureInfoUniqByCustPrice(queryFailureFilterEntity);
		//然后发送失败数据
		Map<String,Object> failCustPriceParam=new HashMap<String, Object>();
		failCustPriceParam.put("type", DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD);
		List<Map<String,Object>> failCustPrices=this.customerPriceSendDao.queryFailCustPriceData(failCustPriceParam);
		sendDatas=convertCustPriceEntityList(failCustPrices);
		
		//发送数据
		this.sendData(sendDatas, true, DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD);
		
		//查询本次要发送的客户价格数据
		List<Map<String,Object>> thisDatas=this.customerPriceSendDao.queryCusomerPriceData(newMapParam);
		sendDatas=convertCustPriceEntityList(thisDatas);
		//发送数据
		this.sendData(sendDatas, false, DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD);
		
		//------------客户价格同步完毕
		//设置为客户价格（无价格）折扣JOB编码
		queryFailureFilterEntity.setType(DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD_DISCOUNT);
		//客户折扣去重
		this.customerPriceSendDao.failureInfoUniqByCustDiscount(queryFailureFilterEntity);
		
		//发送客户折扣失败的信息
		Map<String,Object> failCustDiscountParam=new HashMap<String, Object>();
		failCustDiscountParam.put("type", DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD_DISCOUNT);
		List<Map<String,Object>> failCustDiscounts=this.customerPriceSendDao.queryFailCustDiscountData(failCustDiscountParam);
		sendDatas=convertCustDiscountEntityList(failCustDiscounts);
		this.sendData(sendDatas, true,DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD_DISCOUNT);
		
		//查询本次的客户折扣信息
		List<Map<String,Object>> newDiscountCustomerDatas=customerPriceSendDao.queryNeedDiscountCustomerData(newMapParam);
		
		//客户折扣转换为客户价格信息
		sendDatas=convertCustDiscountEntityList(newDiscountCustomerDatas);
		// 发送数据
		this.sendData(sendDatas, false,DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD_DISCOUNT);	
		
		// 更新上次发送时间
		DataSendJobTimeEntity saveEntity = new DataSendJobTimeEntity();
		saveEntity.setId(UUIDUtil.getUUID());
		saveEntity.setJobCode(DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD);
		saveEntity.setJobName(DataSendJobConstant.getNameByCode(DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD));
		saveEntity.setLastSendTime(thisTime);
		saveEntity.setActive(MiserConstants.ACTIVE);
		saveEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
		saveEntity.setCreateDate(new Date());
		saveEntity.setModifyDate(new Date());
		saveEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
		dataSendJobTimeService.saveLastSendTime(saveEntity);
	}
	
	/**
	 * 客户价格Map使用
	 * @Description: List Map集合转为List 集合队形
	 * @param @param List<Map<String,Object>> list
	 * @param @return   
	 * @return List<CustomerPriceSendEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月8日
	 */
	private ArrayList<CustomerPriceSendEntity> convertCustPriceEntityList(List<Map<String,Object>> list){
		ArrayList<CustomerPriceSendEntity> lists=new ArrayList<CustomerPriceSendEntity>();
		
		
		
		for(Map<String,Object> map:list){
			CustomerPriceSendEntity entity=new CustomerPriceSendEntity();
			entity.setArriveCityName((String)map.get("ARRIVECITYNAME"));
			entity.setDiscountPriorityType((String)map.get("DISCOUNTPRIORITYTYPE"));
			entity.setEffectiveDate((Date)map.get("EFFECTIVEDATE"));
			entity.setExtraChargePrice(bigDecimalToDouble(map.get("EXTRACHARGEPRICE")));
			entity.setFuelSubchargeId((String)map.get("FUELSUBCHARGEID"));
			entity.setHeavyDiscount(bigDecimalToDouble(map.get("HEAVYDISCOUNT")));
			entity.setId((String)map.get("ID"));
			entity.setInvalidDate((Date)map.get("INVALIDDATE"));
			entity.setLightDiscount(bigDecimalToDouble(map.get("LIGHTDISCOUNT")));
			entity.setActive((String)map.get("ACTIVE"));
			entity.setSectionId((String)map.get("SECTIONID"));
			entity.setSendCity((String)map.get("SENDCITY"));
			entity.setStandardId((String)map.get("STANDARDID"));
			entity.setStartPrice(bigDecimalToDouble(map.get("STARTPRICE")));
			entity.setTransportType((String)map.get("TRANSPORTTYPE"));
			entity.setVolumePrice(bigDecimalToDouble(map.get("VOLUMEPRICE")));
			entity.setWeightPrice(bigDecimalToDouble(map.get("WEIGHTPRICE")));
			entity.setRemark((String)map.get("REMARK"));
			entity.setCustomertype((String)map.get("CUSTOMERTYPE"));
			//价格的是否有效
			Integer status=this.getRecStatus(entity.getInvalidDate(),entity.getEffectiveDate(),entity.getActive());
			entity.setRecStatus(status);
			lists.add(entity);
		}
		
		return lists;
	}
	
	/**
	 * 客户折扣Map使用
	 * @Description: List Map集合转为List 集合队形
	 * @param @param list
	 * @param @return   
	 * @return ArrayList<CustomerPriceSendEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月8日
	 */
	private ArrayList<CustomerPriceSendEntity> convertCustDiscountEntityList(List<Map<String,Object>> list){
		
		ArrayList<CustomerPriceSendEntity> sendDatas=new ArrayList<CustomerPriceSendEntity>();
		for (Map<String, Object> map : list) {
			//折扣id
			String id=(String)map.get("id");
			String customerCode=(String)map.get("CUSTOMER_CODE");
			//得到最终状态
			String active=(String)map.get("ACTIVE");
			Date effectiveTime=(Date)map.get("EFFECTIVE_TIME");
			Date invalidTime=(Date)map.get("INVALID_TIME");
			Date pcCustStartTime=(Date)map.get("PC_START_TIME");
			Date pcCustEndTime=(Date)map.get("PC_END_TIME");
			Integer recStatus=this.getRecStatus(invalidTime, effectiveTime, active);
			//查询条件
			String sendPriceCity=(String)map.get("PRICE_CITY");
			String transTypeCode=(String)map.get("TRANS_TYPE_CODE");
			String remark=(String)map.get("REMARK");
			Date pcTime=(Date)map.get("DISCOUNT_ACCODING_PC_TIME");
			Map<String,Object> sendDateMap=new HashMap<String, Object>();
			sendDateMap.put("sendPriceCity", sendPriceCity);
			sendDateMap.put("transTypeCode", transTypeCode);
			sendDateMap.put("pcTime", pcTime);
			if(StringUtils.isEmpty(sendPriceCity)|| pcTime!=null){
				continue;
			}
			Double heavy_float_price=bigDecimalToDouble(map.get("HEAVY_FLOAT_PRICE"));
			Double light_float_price=bigDecimalToDouble(map.get("LIGHT_FLOAT_PRICE"));
			Double float_percentage=bigDecimalToDouble(map.get("FLOAT_PERCENTAGE"));
			//当前客户折扣转换的价格信息：没有客户编号 fuelSubchargeId:燃油（只有燃油费）
			ArrayList<CustomerPriceSendEntity> curCustomerPrices=customerPriceSendDao.querySendDataByParam(sendDateMap);
			for(CustomerPriceSendEntity entity: curCustomerPrices){
				entity.setRecStatus(recStatus);
				entity.setId(id);
				entity.setEffectiveDate(pcCustStartTime);
				entity.setInvalidDate(pcCustEndTime);
				Double volPrice= entity.getVolumePrice();
				Double weiPrice=entity.getWeightPrice();
				if(float_percentage>0){//百分比处理
					volPrice=volPrice*(1+float_percentage/100);
					weiPrice=weiPrice*(1+float_percentage/100);
				}else{//浮动金额处理
					weiPrice=nullDoubleZero(weiPrice)+nullDoubleZero(heavy_float_price);
					volPrice=nullDoubleZero(volPrice)+nullDoubleZero(light_float_price);
				}
				entity.setVolumePrice(volPrice);
				entity.setWeightPrice(weiPrice);
				entity.setStandardId(customerCode);
				entity.setRemark(remark);
				sendDatas.add(entity);
			}
		}
		return sendDatas;
	}
	
	
	
	

	@Transactional
	public void sendData(ArrayList<CustomerPriceSendEntity> datas, boolean isFailureData,String type){
		
		ArrayList<PriceCardInfo> priceCardData = new ArrayList<PriceCardInfo>(MAX_COUNT);
		//这里主要是去掉客户价格出发价格城市不一致的信息
		ArrayList<CustomerPriceSendEntity> copyDatas=new ArrayList<CustomerPriceSendEntity>();
		
		if(DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD.equals(type)){
			
			
			Set<String> customerNos=new HashSet<String>();
			for(int i = 0; i < datas.size(); i++){
				CustomerPriceSendEntity entity = datas.get(i);
				String customerNo=entity.getStandardId();
				customerNos.add(customerNo);
			}
			//标准价卡城市映射
			Map<String,String> standarCustomerMCityMap=new HashMap<String, String>();
			//客户价卡城市映射
			Map<String,String> custCustomerMCityMap=new HashMap<String, String>();
			
			Map<String,Object> param=new HashMap<String, Object>();
			if(customerNos.size()<1){
				return;
			}
			param.put("customerNos", customerNos);
			List<Map<String,Object>> standarList=this.customerPriceSendDao.queryCustomerPriceCitysSandardBycustomerNos(param);
			List<Map<String,Object>> custList=this.customerPriceSendDao.queryCustomerPriceCityCustBycustomerNos(param);
			
			for(Map<String,Object> map:standarList){
				String customerNo=(String)map.get("CODE");
				String priceCity=(String)map.get("PRICE_CITY");
				standarCustomerMCityMap.put(customerNo, priceCity);
			}
			for(Map<String,Object> map:custList){
				String customerNo=(String)map.get("CODE");
				String priceCity=(String)map.get("PRICE_CITY");
				custCustomerMCityMap.put(customerNo, priceCity);
			}
			for(int i = 0; i < datas.size(); i++){
				CustomerPriceSendEntity entity=datas.get(i);
				String sendPriceCity=entity.getSendCity();
				if(StringUtils.isEmpty(sendPriceCity)){
					continue;
				}
				if(MiserConstants.ACTIVE.equals(entity.getCustomertype())){
					//标准映射
					if(sendPriceCity.equals(standarCustomerMCityMap.get(entity.getStandardId()))){
						copyDatas.add(entity);
					}
				}else if(MiserConstants.INACTIVE.equals(entity.getCustomertype())){
					//客户映射
					if(sendPriceCity.equals(custCustomerMCityMap.get(entity.getStandardId()))){
						copyDatas.add(entity);
					}
				}else{
					continue;
				}
				
				
				
			}
			datas.clear();
			datas=copyDatas;
		}
		
		/**
		 * 如果是折扣新插数据失败时，就把数据放到这个集合里，用于去重处理
		 */
		Map<String,DataSendJobFailureEntity> failureDataMap=new HashMap<String, DataSendJobFailureEntity>();
		
		for (int i = 0; i < datas.size(); i++) {
			//将查询出来的价格城市转换成需要发送到MQ队列的对象
			CustomerPriceSendEntity entity = datas.get(i);
			
			PriceCardInfo info = new PriceCardInfo();
			String arriveCityName=entity.getArriveCityName();
			if(!StringUtils.isEmpty(arriveCityName) ){
				int lepLeng=4-arriveCityName.length();
				for(int j=0;j<lepLeng;j++){
					arriveCityName="0"+arriveCityName;
				}
			}
			info.setRecStatus(entity.getRecStatus());
			info.setArriveCityName(arriveCityName);//a)对照类型为C时为目的城市编号
			info.setType("C");
			info.setTransportType(entity.getTransportType()); 
			info.setStandardId(entity.getStandardId());//客户编号
			info.setCityMapType("C");//对照类型：1）当C为价格城市类型；
			info.setStartPrice(entity.getStartPrice()==null ? 0:entity.getStartPrice());//最低收费
			info.setWeightPrice(entity.getWeightPrice()==null ? 0:entity.getWeightPrice());//重量单价
			info.setVolumePrice(entity.getVolumePrice()==null ? 0:entity.getVolumePrice());//体积单价
			info.setExtraChargePrice(entity.getExtraChargePrice()==null ? 0:entity.getExtraChargePrice());//附加费
			info.setLightDiscount(entity.getLightDiscount()==null ? 0:entity.getLightDiscount());//轻货折扣
			info.setHeavyDiscount(entity.getHeavyDiscount()==null ? 0:entity.getHeavyDiscount());//重货折扣
			//transportTime 运输时间（小时）
			info.setEffectiveDate(entity.getEffectiveDate());//生效日期
			info.setInvalidDate(entity.getInvalidDate());//失效日期
			//fuelSubchargeId 燃油分段 暂为null
			info.setFuelSubchargeId(entity.getFuelSubchargeId());
			//sectionId 优惠分段 运费
			info.setSectionId(entity.getSectionId());
			info.setRemark(entity.getRemark());
			priceCardData.add(info);
			if ((i + 1) % MAX_COUNT == 0 || i == datas.size()-1) {
				try {
					activeMQProducerService.postPriceCardDataToQueue(priceCardData);
					
					//如果是失败数据发送成功了更新失败表状态为已发送
					if (isFailureData) {
						for (int j = 0; j < priceCardData.size(); j++) {
							CustomerPriceSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
							DataSendJobFailureEntity saveSuccessEntity = new DataSendJobFailureEntity();
							saveSuccessEntity.setId(failureEntity.getId());
							saveSuccessEntity.setActive(MiserConstants.INACTIVE);
							dataSendJobFailureService.updateSendedData(saveSuccessEntity);
						}
					}
				} catch (Exception e) {
					if (!isFailureData) {
						//如果是新数据发送失败了，插入到失败表
						for (int j = 0; j < priceCardData.size(); j++) {
							CustomerPriceSendEntity failureEntity = datas.get((i / MAX_COUNT) * MAX_COUNT + j); //获取此次发送的数据
							DataSendJobFailureEntity saveFailureEntity = new DataSendJobFailureEntity();
							saveFailureEntity.setId(UUIDUtil.getUUID());
							saveFailureEntity.setBusinessId(failureEntity.getId());
							saveFailureEntity.setActive(MiserConstants.ACTIVE);
							saveFailureEntity.setCreateDate(new Date());
							saveFailureEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
							saveFailureEntity.setFailReason(e.getMessage());
							saveFailureEntity.setModifyDate(new Date());
							saveFailureEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
							saveFailureEntity.setSendTime(new Date());
							saveFailureEntity.setType(type);
							if(DataSendJobConstant.JOB_CODE_CUSTOMER_PRICE_CARD_DISCOUNT.equals(type)){
								failureDataMap.put(failureEntity.getId(), saveFailureEntity);
							}else{
								dataSendJobFailureService.saveFailureData(saveFailureEntity);
							}
							
						}
					}
				}
				priceCardData.clear();
			}
			
			//最后再插入失败信息
			for(DataSendJobFailureEntity saveFailureEntity: failureDataMap.values()){
				dataSendJobFailureService.saveFailureData(saveFailureEntity);
			}
		}
	}
	
	
	
	
	/**
	 * 
	 * @Description: 判断数据是否有效
	 * @param @param invalidDate 失效时间
	 * @param @param effectiveDate 生效时间
	 * @param @param scrActive 原是否有效
	 * @param @return
	 * @return Integer 0:有效，1：失效
	 * @throws
	 * @author 廖文强
	 * @date 2016年3月2日
	 */
	public Integer getRecStatus(Date invalidDate, Date effectiveDate,
			String scrActive) {
		Integer result = null;
		if (MiserConstants.ACTIVE.equals(scrActive)) {// active='Y'
			if (effectiveDate.getTime() <= new Date().getTime()) {// 过了生效期
				if (invalidDate != null) {// 有失效时间
					if (new Date().getTime() < invalidDate.getTime()) {// 没到失效期
						result = 0;
					} else {
						result = 1;
					}
				} else {// 没失效时间
					result = 0;
				}
			} else {// 没到生效期
				result = 1;
			}
		} else {
			result = 1;
		}
		return result ;
	}
	
	private Double bigDecimalToDouble(Object bd){
		if(bd==null) return null;
		BigDecimal bd2=(BigDecimal)bd;
		return new Double(bd2.doubleValue());
	}
	private Double nullDoubleZero(Double number){
		if(number==null){
			return 0.0;
		}else{
			return number;
		}
	}
}

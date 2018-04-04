package com.hoau.miser.module.biz.base.server.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.server.components.security.SecurityNonCheckRequired;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.IOrgService;
import com.hoau.miser.module.biz.base.api.server.service.IPriceTimeService;
import com.hoau.miser.module.biz.base.api.server.service.ISmsSenderService;
import com.hoau.miser.module.biz.base.api.shared.domain.OrgInfoEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceTimeEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.SmsCnt;
import com.hoau.miser.module.biz.base.api.shared.domain.SmsInfo;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceTimeVo;
import com.hoau.miser.module.biz.discount.api.server.service.IPriceEventService;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventDiscountSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventRouteDiscountEntity;
import com.hoau.miser.module.biz.extrafee.server.service.IPriceCollectDeliveryFeeService;
import com.hoau.miser.module.biz.extrafee.server.service.IPriceExtrafeeStandardService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceCollectDeliveryFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceExtrafeeStandardEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceExtrafeeStandardVo;
import com.hoau.miser.module.common.server.interceptor.CookieNonCheckRequired;
import com.hoau.miser.module.sys.base.api.server.service.IDistrictService;

@Controller
@Scope("prototype")
public class PriceTimeAction extends AbstractAction {

	private static final long serialVersionUID = 3547927741097542982L;

	private static final Logger log = LoggerFactory
			.getLogger(PriceTimeAction.class);

	@Resource
	private IPriceTimeService priceTimeService;

	@Resource
	private IPriceEventService priceEventService;

	@Resource
	private IPriceExtrafeeStandardService priceExtrafeeStandardService;

	@Resource
	private IDistrictService districtService;

	@Resource
	private IOrgService orgService;

	@Resource
	private ISmsSenderService smsSenderService;
	
	@Resource
	private IPriceCollectDeliveryFeeService priceCollectDeliveryFeeService;

	private PriceTimeVo priceTimeVo;

	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String index() {
		return "index";
	}

	/**
	 * 用于处理表单的参数及返回结果集
	 * @param priceTimeVo 表单参数
	 * @param flag 用于区分是查询 还是用于 导出的查询
	 * @return
	 */
	public List<PriceTimeEntity> initQuery(PriceTimeVo priceTimeVo,String flag) {

		if (priceTimeVo == null) {
			priceTimeVo = new PriceTimeVo();
			priceTimeVo.setPriceTimeEntity(new PriceTimeEntity());
			priceTimeVo
					.setPriceTimeEntityList(new ArrayList<PriceTimeEntity>());
		}

		// 用于减少查询次数 将对应数据活动数据缓存
		Map<String, List<PriceEventRouteDiscountEntity>> eventRouteDiscountEntityMap = 
				new HashMap<String, List<PriceEventRouteDiscountEntity>>();

		// 出发门店
		OrgInfoEntity orgInfoEntityVo = new OrgInfoEntity();
		OrgInfoEntity orgInfoEntity = null;
		String sendSaleDepartment = priceTimeVo.getSendSaleDepartment();
		if (null != sendSaleDepartment && !sendSaleDepartment.equals("")) {
			orgInfoEntityVo.setIsSalesDepartment("Y");
			orgInfoEntityVo.setCode(sendSaleDepartment);
			orgInfoEntity = orgService.queryOrgaInfoByEntity(orgInfoEntityVo);

			priceTimeVo.getPriceTimeEntity().setSendProvinceCode(
					orgInfoEntity.getProvinceCode());
			priceTimeVo.getPriceTimeEntity().setSendCityCode(
					orgInfoEntity.getCityCode());

			// 因为 area_code 值不正确 修改为 county_code
			priceTimeVo.getPriceTimeEntity().setSendAreaCode(
					orgInfoEntity.getCountyCode());
		}

		// 到达门店
		String arriveSaleDepartment = priceTimeVo.getArriveSaleDepartment();
		if (null != arriveSaleDepartment && !arriveSaleDepartment.equals("")) {

			orgInfoEntityVo.setCode(arriveSaleDepartment);
			orgInfoEntity = orgService.queryOrgaInfoByEntity(orgInfoEntityVo);
			priceTimeVo.getPriceTimeEntity().setArriveProvinceCode(
					orgInfoEntity.getProvinceCode());
			priceTimeVo.getPriceTimeEntity().setArriveCityCode(
					orgInfoEntity.getCityCode());

			// 因为 area_code 值不正确 修改为 county_code
			priceTimeVo.getPriceTimeEntity().setArriveAreaCode(
					orgInfoEntity.getCountyCode());
		}

		List<PriceTimeEntity> priceTimeEntityList = priceTimeService
				.queryPriceTimeEntityListByEntity(getPriceTimeVo()
						.getPriceTimeEntity(), limit, start);

		if (priceTimeEntityList != null) {

			for (PriceTimeEntity curTimeEntity : priceTimeEntityList) {

				PriceEventRouteDiscountEntity entityVo = new PriceEventRouteDiscountEntity();
				entityVo.setSendPriceCityCode(curTimeEntity
						.getStandardSendPriceCity());
				entityVo.setArrivalPriceCityCode(curTimeEntity
						.getStandardArrivePriceCity());

				List<PriceEventRouteDiscountEntity> eventRouteDiscountList = null;
				String key = curTimeEntity.getStandardSendPriceCity() + "@"
						+ curTimeEntity.getStandardArrivePriceCity();

				// 若 map 里已经存在过 就不再请求数据
				if (eventRouteDiscountEntityMap.containsKey(key)) {
					eventRouteDiscountList = eventRouteDiscountEntityMap
							.get(key);
				} else {
					eventRouteDiscountList = priceEventService
							.queryEventRouteDiscount(entityVo);
					eventRouteDiscountEntityMap
							.put(key, eventRouteDiscountList);
				}

				if (null != eventRouteDiscountList
						&& eventRouteDiscountList.size() > 0) {

					String eventIdStr = "";
					String eventCodeStr = "";
					String eventNameStr = "";

					for (PriceEventRouteDiscountEntity tmpEntity : eventRouteDiscountList) {

						// 前面查询时没有传递 运输类型 这里需要根据运输类型来设置每一条记录
						if (curTimeEntity.getStandardTransTypeCode().equals(
								tmpEntity.getTransTypeCode())) {
							String eventId = tmpEntity.getEventId();
							eventIdStr += eventId + ",";

							String eventCode = tmpEntity.getEventCode();
							eventCodeStr += eventCode + ",";

							String eventName = tmpEntity.getEventName();
							eventNameStr += eventName + ",";
						}
					}

					if (eventIdStr.endsWith(",")) {
						eventIdStr = eventIdStr.substring(0,
								eventIdStr.length() - 1);
						curTimeEntity.setEventIdStr(eventIdStr);
					}

					if (eventCodeStr.endsWith(",")) {
						eventCodeStr = eventCodeStr.substring(0,
								eventCodeStr.length() - 1);
						curTimeEntity.setEventCodeStr(eventCodeStr);
					}

					if (eventNameStr.endsWith(",")) {
						eventNameStr = eventNameStr.substring(0,
								eventNameStr.length() - 1);
						curTimeEntity.setEventNameStr(eventNameStr);
					}
				}
				
				//针对于导出 要在这里拼接部分字段 如 单价 运行时长 
				
				if("export".equals(flag)){
					//单价
					StringBuffer buf = new StringBuffer();
					if(StringUtil.isNotEmpty(curTimeEntity.getStandardWeightPrice())){
						buf.append("重货:").append(curTimeEntity.getStandardWeightPrice()).append("元/公斤");
					}
					
					if(StringUtil.isNotEmpty(curTimeEntity.getStandardVolumePrice())){
						if(StringUtil.isNotEmpty(curTimeEntity.getStandardWeightPrice())){
							buf.append("\r\n");
						}
						buf.append("轻货:").append(curTimeEntity.getStandardVolumePrice()).append("元/立方米");
					}
					
					curTimeEntity.setStandardWeightPrice(buf.toString());
					
					//运行时长
					StringBuffer pickupBuf = new StringBuffer();
					if(StringUtil.isNotEmpty(curTimeEntity.getPickupPromDay())){
						pickupBuf.append("预计客户自提时间:第").append(curTimeEntity.getPickupPromDay()).append("天");
					}
					
					if(StringUtil.isNotEmpty(curTimeEntity.getDeliveryPromDay())){
						if(StringUtil.isNotEmpty(curTimeEntity.getPickupPromDay())){
							pickupBuf.append("\r\n");
						}						
						pickupBuf.append("预计送货上门时间:第 ").append(curTimeEntity.getDeliveryPromDay()).append("天");
					}
					curTimeEntity.setPickupPromDay(pickupBuf.toString());
				}
			}
		}


		return priceTimeEntityList;
	}

	@JSON
	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String query() {
		try {

			List<PriceTimeEntity> priceTimeEntityList = initQuery(priceTimeVo,"query");
			priceTimeVo.setPriceTimeEntityList(priceTimeEntityList);
			setTotalCount(priceTimeService
					.countOfPriceTimeEntityListByEntity(priceTimeVo
							.getPriceTimeEntity()));

		} catch (Exception e) {
			log.error("method:query,msg:" + e.toString());
			return returnError(e.toString());
		}
		return returnSuccess();
	}

	@JSON
	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String excelExport() {

		PrintWriter out = null;
		try {
			
			start = 0;
			limit = 1000000000;
		 
			List<PriceTimeEntity> priceTimeEntityList = initQuery(priceTimeVo,"export");
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			String filePath = priceTimeService.createExcelFile(priceTimeEntityList);
			JSONObject json = new JSONObject();
			json.put("filePath", filePath);
			json.put("count", priceTimeEntityList != null ? priceTimeEntityList.size() : 0);
			out.println(json.toString());
			out.flush();
		} catch (Exception e) {
			log.error("excelExport,msg:" + e.toString());
			return returnError(e.toString());
		} finally{
			if(out != null ){
				out.close();
			}
		}
		return returnSuccess();
	}

	@JSON
	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String eventViewById() {
		try {
			String eventCode = priceTimeVo.getEventCode();
			String transTypeCode = priceTimeVo.getPriceTimeEntity()
					.getStandardTransTypeCode();

			PriceEventDiscountSubEntity entity = new PriceEventDiscountSubEntity();
			entity.setEventCode(eventCode);
			entity.setTransTypeCode(transTypeCode);
			List<PriceEventDiscountSubEntity> eventDiscountSubList = priceEventService
					.queryEventDiscountSubListByParam(entity);

			if (null != eventDiscountSubList && eventDiscountSubList.size() > 0) {
				PriceEventDiscountSubEntity subEntity = eventDiscountSubList
						.get(0);
				List<PriceSectionEntity> sectionEntityList = getSectionSubByEventDiscountSub(subEntity);

				if (null != sectionEntityList && sectionEntityList.size() > 0) {
					priceTimeVo.setPriceSectionEntityList(sectionEntityList);
				}
			}

		} catch (Exception e) {
			log.error("method:eventViewById,msg:" + e.toString());
			return returnError(e.toString());
		}
		return returnSuccess();
	}

	@JSON
	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String queryFreightAndExtraFee() {
		try {

			PriceExtrafeeStandardVo psv = new PriceExtrafeeStandardVo();
			PriceExtrafeeStandardEntity extraEntity = new PriceExtrafeeStandardEntity();
			String transTypeCode = priceTimeVo.getPriceTimeEntity()
					.getStandardTransTypeCode();
			extraEntity.setTransTypeCode(transTypeCode);
			extraEntity.setState("EFFECTIVE");

			psv.setPriceExtrafeeStandardEntity(extraEntity);

			// 获取 指定运输类型 当前正在生效的 标准附加费
			List<PriceExtrafeeStandardEntity> extraFeeEntityList = priceExtrafeeStandardService
					.queryListByParam(psv, 1000000, 0);
			if (null != extraFeeEntityList && extraFeeEntityList.size() > 0) {

				List<com.hoau.miser.module.biz.base.api.shared.domain.PriceExtrafeeStandardEntity> list = new ArrayList<com.hoau.miser.module.biz.base.api.shared.domain.PriceExtrafeeStandardEntity>();

				for (PriceExtrafeeStandardEntity curEntity : extraFeeEntityList) {
					com.hoau.miser.module.biz.base.api.shared.domain.PriceExtrafeeStandardEntity targetEntity = parseExtrafeeStandardEntity(curEntity);
					
					//代收货款手续费率 单独从其他表获取
					if(!"COLLECTION_RATE".equals(targetEntity.getType())){
						list.add(targetEntity);
					}
					
				}
				
				// begin 获取 代收货款手续费率
				
				PriceCollectDeliveryFeeEntity tmpQryEntity = new PriceCollectDeliveryFeeEntity();
				
				String standardSendPriceCityCode = priceTimeVo.getPriceTimeEntity().getStandardSendPriceCity();
				String collectDeliveryType = priceTimeVo.getCollectDeliveryType();
				
				
				tmpQryEntity.setBeginPriceCityCode(standardSendPriceCityCode);
				tmpQryEntity.setTransTypeCode(transTypeCode);
				tmpQryEntity.setCollectDeliveryType(
						(null == collectDeliveryType || "".equals(collectDeliveryType)) ? 0 : Integer.valueOf(collectDeliveryType)
						);
				tmpQryEntity.setStatus("EFFECTIVE");
				
				List<PriceCollectDeliveryFeeEntity> collectDeliverList = priceCollectDeliveryFeeService.queryListByEntity(tmpQryEntity, 0, 1);
				
				if(null != collectDeliverList && collectDeliverList.size() > 0){
					PriceCollectDeliveryFeeEntity deliverFeeEntity = collectDeliverList.get(0);
					
					com.hoau.miser.module.biz.base.api.shared.domain.PriceExtrafeeStandardEntity extraFeeEntity = 
							new com.hoau.miser.module.biz.base.api.shared.domain.PriceExtrafeeStandardEntity();
					
					extraFeeEntity.setType("COLLECTION_RATE");
					
					//代收货款手续费率
					extraFeeEntity.setMoney(deliverFeeEntity.getCollectDeliveryRate().doubleValue());
					
					//最低费用
					extraFeeEntity.setLowestFee(deliverFeeEntity.getLowestCollectDeliveryFee().doubleValue());
					
					//最高费用
					extraFeeEntity.setHeightestFee(deliverFeeEntity.getHighestCollectDeliveryFee().doubleValue());
					
					list.add(extraFeeEntity);
				}
				// end 获取 代收货款手续费率
				

				priceTimeVo.setPriceExtrafeeStandardEntityList(list);
			}

			List<BigDecimal> freightFeeList = new ArrayList<BigDecimal>();

			// 获取对应涉及 活动的详情
			String eventCodeStr = priceTimeVo.getPriceTimeEntity()
					.getEventCodeStr();

			if (null != eventCodeStr && eventCodeStr.length() != 0) {
				String[] eventCodeArr = eventCodeStr.split(",");

				for (String curEventCode : eventCodeArr) {

					PriceEventDiscountSubEntity discountSubEntity = new PriceEventDiscountSubEntity();
					discountSubEntity.setEventCode(curEventCode);
					discountSubEntity.setTransTypeCode(transTypeCode);

					// 这里获取到的时活动折扣详情 都是一样 分段编码
					List<PriceEventDiscountSubEntity> eventDiscountSubList = priceEventService
							.queryEventDiscountSubListByParam(discountSubEntity);

					// 获取具体详情分段 对应的明细 即优惠分段
					if (null != eventDiscountSubList
							&& eventDiscountSubList.size() > 0) {
						PriceEventDiscountSubEntity subEntity = eventDiscountSubList
								.get(0);
						List<PriceSectionEntity> sectionEntityList = getSectionSubByEventDiscountSub(subEntity);

						// 计算出 当前活动对应的 纯运费
						if (null != sectionEntityList
								&& sectionEntityList.size() > 0) {
							BigDecimal curFreightFee = calculateFreightFee(
									priceTimeVo, sectionEntityList);
							freightFeeList.add(curFreightFee);

						} else { // 当存在活动 但没配置明细时 仍要计算费用
							BigDecimal curFreightFee = calculateFreightFeeWithoutEvent(priceTimeVo);
							freightFeeList.add(curFreightFee);
						}
					}
				}
			} else { // 当不存在活动时 仍要计算费用
				BigDecimal curFreightFee = calculateFreightFeeWithoutEvent(priceTimeVo);
				freightFeeList.add(curFreightFee);
			}

			// 如果有多个活动，优先取最低的（因为这样才能引发客户的兴趣前来发货，但是最后解释需要问营业网点，哈哈）。
			if (freightFeeList.size() > 0) {
				BigDecimal freightFee = Collections.min(freightFeeList);

				// 微信同 邱国兴沟通 纯运费 最后凡是有小数的 都取整加1 没有小数的 如 20.0 还是20.0不加1
				BigDecimal newFreightFee = freightFee.setScale(0,
						BigDecimal.ROUND_FLOOR);

				if (freightFee.compareTo(newFreightFee) == 0) { // 例如 20.00 不用加1
					priceTimeVo.setFreightFee(freightFee);
				} else { // 只要带了小数的都取整 +1
					newFreightFee = newFreightFee.add(new BigDecimal(1));
					priceTimeVo.setFreightFee(newFreightFee);
				}
			}
		} catch (Exception e) {
			log.error("method:queryFreightAndExtraFee,msg:" + e.toString());
			return returnError(e.toString());
		}
		return returnSuccess();
	}

	public BigDecimal calculateFreightFeeWithoutEvent(PriceTimeVo priceTimeVo) {
		// 界面填写的 总重量
		double totalWeight = Double.valueOf(priceTimeVo.getTotalWeight());

		// 界面填写的 总体积
		double totalVolume = Double.valueOf(priceTimeVo.getTotalVolume());

		// 标准重量单价
		BigDecimal standardWeightPrice = new BigDecimal(priceTimeVo
				.getPriceTimeEntity().getStandardWeightPrice());

		// 标准体积单价
		BigDecimal standardVolumePrice = new BigDecimal(priceTimeVo
				.getPriceTimeEntity().getStandardVolumePrice());

		BigDecimal freightFeeForWeight = standardWeightPrice
				.multiply(new BigDecimal(totalWeight));
		BigDecimal freightFeeForVolume = standardVolumePrice
				.multiply(new BigDecimal(totalVolume));
		BigDecimal maxFreightFee = freightFeeForWeight.max(freightFeeForVolume);

		return maxFreightFee;

	}

	private com.hoau.miser.module.biz.base.api.shared.domain.PriceExtrafeeStandardEntity parseExtrafeeStandardEntity(
			com.hoau.miser.module.biz.extrafee.shared.domain.PriceExtrafeeStandardEntity curEntity) {

		com.hoau.miser.module.biz.base.api.shared.domain.PriceExtrafeeStandardEntity targetEntity = new com.hoau.miser.module.biz.base.api.shared.domain.PriceExtrafeeStandardEntity();

		targetEntity.setTransTypeCode(curEntity.getTransTypeCode());
		targetEntity.setTransTypeName(curEntity.getTransTypeName());
		targetEntity.setType(curEntity.getType());
		targetEntity.setMoney(curEntity.getMoney());
		targetEntity.setLockType(curEntity.getLockType());
		targetEntity.setEffectiveTime(curEntity.getEffectiveTime());
		targetEntity.setInvalidTime(curEntity.getInvalidTime());
		targetEntity.setRemark(curEntity.getRemark());
		targetEntity.setActive(curEntity.getActive());
		targetEntity.setState(curEntity.getState());
		targetEntity.setIsAlert(curEntity.getIsAlert());

		return targetEntity;
	}

	/**
	 * 计算出纯运费 一、纯运费=MAX(重量单价 * 重量折扣比例 , 体积单价 * 体积折扣比例) 1、重量折扣比例 、体积折扣比例
	 * ：根据点击计算所在行的“活动详情”里取值【对应存在多个活动时，取哪一个活动？】
	 * 1）、根据界面填写的“总重量”在对应的“运费分段明细”根据“分段依据”为“重量”及其“段起”、“段止”找到其符合的“重量折扣比例值”；
	 * 2）、根据界面填写的“总体积”在对应的“运费分段明细”根据“分段依据”为“体积”及其“段起”、“段止”找到其符合的“体积折扣比例值”；
	 * 2、重量单价：点击所处行对应“单价”中的“重货单价” 3、体积单价：点击所处行对应“单价”中的“轻货单价”
	 * 
	 * 如果有多个活动，优先取最低的（因为这样才能引发客户的兴趣前来发货，但是最后解释需要问营业网点，哈哈）。
	 * 
	 * @param sectionEntityList
	 * @return
	 */
	public BigDecimal calculateFreightFee(PriceTimeVo priceTimeVo,
			List<PriceSectionEntity> sectionEntityList) {

		// 标准重量单价
		BigDecimal standardWeightPrice = new BigDecimal(priceTimeVo
				.getPriceTimeEntity().getStandardWeightPrice());

		// 标准体积单价
		BigDecimal standardVolumePrice = new BigDecimal(priceTimeVo
				.getPriceTimeEntity().getStandardVolumePrice());

		// 界面填写的 总重量
		double totalWeight = Double.valueOf(priceTimeVo.getTotalWeight());

		// 界面填写的 总体积
		double totalVolume = Double.valueOf(priceTimeVo.getTotalVolume());

		// 按重量匹配的折扣值
		double weightDiscountVal = 1d;

		// 按体积匹配的折扣值
		double volumeDiscountVal = 1d;

		// sectionEntityList 包含了优惠活动中配置过的各项费用 这里只需要 运费项目
		for (PriceSectionEntity curSectionEntity : sectionEntityList) {

			if ("FREIGHT".equals(curSectionEntity.getSectionedItem())) {

				// 得到 运费分段明细
				List<PriceSectionSubEntity> curSectionSubEntityList = curSectionEntity
						.getSubEntities();

				// 分别找出其 按重量 按体积 对应的折扣比例值
				if (null != curSectionSubEntityList
						&& curSectionSubEntityList.size() > 0) {

					for (PriceSectionSubEntity curSectionSubEntity : curSectionSubEntityList) {

						// 分段依据
						String accodingItem = curSectionSubEntity
								.getSectionAccodingItem();

						// 段起
						Double startValue = curSectionSubEntity.getStartValue();

						// 段止
						Double endValue = curSectionSubEntity.getEndValue();

						// 按重量
						if ("WEIGHT".equals(accodingItem)
								&& (totalWeight >= startValue && totalWeight < endValue)) {
							weightDiscountVal = curSectionSubEntity.getPrice();
						}

						// 按体积
						if ("VOLUMN".equals(accodingItem)
								&& (totalVolume >= startValue && totalVolume < endValue)) {
							volumeDiscountVal = curSectionSubEntity.getPrice();
						}
					}
				}
				break;
			}
		}

		// 判断是否获得 重量折扣比例 体积折扣比例

		// 根据重量计算出来的运费
		BigDecimal freightFeeForWeight = new BigDecimal(0d);

		freightFeeForWeight = standardWeightPrice.multiply(
				new BigDecimal(totalWeight)).multiply(
				new BigDecimal(weightDiscountVal));

		BigDecimal freightFeeForVolume = new BigDecimal(0d);
		freightFeeForVolume = standardVolumePrice.multiply(
				new BigDecimal(totalVolume)).multiply(
				new BigDecimal(volumeDiscountVal));

		// MAX(重量单价 * 重量折扣比例 , 体积单价 * 体积折扣比例)
		BigDecimal maxFreightFee = freightFeeForWeight.max(freightFeeForVolume);

		return maxFreightFee;
	}

	public List<PriceSectionEntity> getSectionSubByEventDiscountSub(
			PriceEventDiscountSubEntity subEntity) {

		List<PriceSectionEntity> sectionSubList = null;

		StringBuffer sectionCodeBuffer = new StringBuffer("");

		// 运费分段折扣编码
		String freightSectionCode = subEntity.getFreightSectionCode();
		if (null != freightSectionCode && !"".equals(freightSectionCode)) {
			sectionCodeBuffer.append("'").append(freightSectionCode)
					.append("'");
		}

		// 附加费分段折扣编码
		String addSectionCode = subEntity.getAddSectionCode();

		if (null != addSectionCode && !"".equals(addSectionCode)) {
			sectionCodeBuffer.append(",");
			sectionCodeBuffer.append("'").append(addSectionCode).append("'");
		}

		// 燃油费分段折扣编码
		String fuelSectionCode = subEntity.getFuelSectionCode();

		if (null != fuelSectionCode && !"".equals(fuelSectionCode)) {
			sectionCodeBuffer.append(",");
			sectionCodeBuffer.append("'").append(fuelSectionCode).append("'");
		}

		// 提货费分段折扣编码
		String pickupSectionCode = subEntity.getPickupSectionCode();

		if (null != pickupSectionCode && !"".equals(pickupSectionCode)) {
			sectionCodeBuffer.append(",");
			sectionCodeBuffer.append("'").append(pickupSectionCode).append("'");
		}

		// 送货费分段折扣编码
		String deliverySectionCode = subEntity.getDeliverySectionCode();

		if (null != deliverySectionCode && !"".equals(deliverySectionCode)) {
			sectionCodeBuffer.append(",");
			sectionCodeBuffer.append("'").append(deliverySectionCode)
					.append("'");
		}

		// 上楼费分段折扣编码
		String upstairSectionCode = subEntity.getUpstairSectionCode();

		if (null != upstairSectionCode && !"".equals(upstairSectionCode)) {
			sectionCodeBuffer.append(",");
			sectionCodeBuffer.append("'").append(upstairSectionCode)
					.append("'");
		}

		// 保价率分段折扣编码
		String insuranceRateSectionCode = subEntity
				.getInsuranceRateSectionCode();

		if (null != insuranceRateSectionCode
				&& !"".equals(insuranceRateSectionCode)) {
			sectionCodeBuffer.append(",");
			sectionCodeBuffer.append("'").append(insuranceRateSectionCode)
					.append("'");
		}

		// 保价费分段折扣编码
		String insuranceSectionCode = subEntity.getInsuranceSectionCode();

		if (null != insuranceSectionCode && !"".equals(insuranceSectionCode)) {
			sectionCodeBuffer.append(",");
			sectionCodeBuffer.append("'").append(insuranceSectionCode)
					.append("'");
		}

		// 工本费分段折扣编码
		String paperSectionCode = subEntity.getPaperSectionCode();

		if (null != paperSectionCode && !"".equals(paperSectionCode)) {
			sectionCodeBuffer.append(",");
			sectionCodeBuffer.append("'").append(paperSectionCode).append("'");
		}

		// 信息费分段折扣编码
		String smsSectionCode = subEntity.getSmsSectionCode();

		if (null != smsSectionCode && !"".equals(smsSectionCode)) {
			sectionCodeBuffer.append(",");
			sectionCodeBuffer.append("'").append(smsSectionCode).append("'");
		}

		// 代收手续费率分段折扣编码
		String collectionRateSectionCode = subEntity
				.getCollectionRateSectionCode();

		if (null != collectionRateSectionCode
				&& !"".equals(collectionRateSectionCode)) {
			sectionCodeBuffer.append(",");
			sectionCodeBuffer.append("'").append(collectionRateSectionCode)
					.append("'");
		}

		// 代收手续费分段折扣编码
		String collectionSectionCode = subEntity.getCollectionSectionCode();

		if (null != collectionSectionCode && !"".equals(collectionSectionCode)) {
			sectionCodeBuffer.append(",");
			sectionCodeBuffer.append("'").append(collectionSectionCode)
					.append("'");
		}

		String sectionCodeStr = sectionCodeBuffer.toString();
		if (!sectionCodeStr.equals("")) {
			String con = " AND PSE.CODE IN (" + sectionCodeStr + ")";
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("sectionCodeStr", con);
			sectionSubList = priceTimeService
					.querySectionSubListBySectionCodeStr(paramMap);
		}

		return sectionSubList;
	}

	@JSON
	@SecurityNonCheckRequired
	@CookieNonCheckRequired
	public String calculateSmsSend() {
		try {

			String smsTpl = priceTimeVo.getSmsTpl();

			String arriveProvinceCode = priceTimeVo.getPriceTimeEntity()
					.getArriveProvinceCode();
			String arriveProviceName = districtService.queryDistrictInfoByCode(
					arriveProvinceCode).getDistrictName();
			smsTpl = smsTpl.replace("@到达省份@", arriveProviceName);

			String arriveCityCode = priceTimeVo.getPriceTimeEntity()
					.getArriveCityCode();
			String arriveCityName = districtService.queryDistrictInfoByCode(
					arriveCityCode).getDistrictName();
			smsTpl = smsTpl.replace("@到达城市@", arriveCityName);

			String arriveAreaCode = priceTimeVo.getPriceTimeEntity()
					.getArriveAreaCode();
			String arriveAreaName = districtService.queryDistrictInfoByCode(
					arriveAreaCode).getDistrictName();
			smsTpl = smsTpl.replace("@到达区县@", arriveAreaName);

			String sendProvinceCode = priceTimeVo.getPriceTimeEntity()
					.getSendProvinceCode();
			String sendProvinceName = districtService.queryDistrictInfoByCode(
					sendProvinceCode).getDistrictName();
			smsTpl = smsTpl.replace("@出发省份@", sendProvinceName);

			String sendCityCode = priceTimeVo.getPriceTimeEntity()
					.getSendCityCode();
			String sendCityName = districtService.queryDistrictInfoByCode(
					sendCityCode).getDistrictName();
			smsTpl = smsTpl.replace("@出发城市@", sendCityName);

			String sendAreaCode = priceTimeVo.getPriceTimeEntity()
					.getSendAreaCode();
			String sendAreaName = districtService.queryDistrictInfoByCode(
					sendAreaCode).getDistrictName();
			smsTpl = smsTpl.replace("@出发区县@", sendAreaName);

			// 接收短信的手机号
			String telephone = priceTimeVo.getTelephone();

			SmsInfo smsInfo = new SmsInfo();
			smsInfo.setSystemName("OHTER");
			smsInfo.setBusinessType("00001");

			SmsCnt smsCnt = new SmsCnt();
			smsCnt.setMobile(telephone);
			smsCnt.setContent(smsTpl);
			smsCnt.setSmsId("");

			List<SmsCnt> smsCntList = new ArrayList<SmsCnt>();
			smsCntList.add(smsCnt);

			smsInfo.setRequestContentList(smsCntList);

			String result = smsSenderService.send(smsInfo);
			return returnSuccess(result);

		} catch (Exception e) {
			log.error("method:calculateSmsSend,msg:" + e.toString());
			return returnError(e.toString());
		}
	}

	public PriceTimeVo getPriceTimeVo() {
		return priceTimeVo;
	}

	public void setPriceTimeVo(PriceTimeVo priceTimeVo) {
		this.priceTimeVo = priceTimeVo;
	}

}

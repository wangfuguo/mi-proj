package com.hoau.miser.module.api.itf.server.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.shared.define.ProdTypeConstant;
import com.hoau.miser.module.api.itf.api.shared.domain.BseCustomerTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceEventTyQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.constants.CplbConstant;
import com.hoau.miser.module.api.itf.server.constants.LockTypeConstants;
import com.hoau.miser.module.api.itf.server.constants.PkpConstants;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.util.define.MiserConstants;
import org.springframework.beans.BeanUtils;

/**
 * PMS工具类
 * 
 * @author 蒋落琛
 * @date 2016-6-8下午2:35:46
 */
public class PmsUtils {

	/**
	 * 判断是否为天猫渠道
	 * 
	 * @param orderOrign
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-8下午2:37:09
	 * @update
	 */
	public static boolean isTmall(String orderOrign) {
		return "TM".equals(orderOrign);
	}
	
	/**
	 * 当前是否为整车
	 * @return
	 */
	public static boolean isZC(String productType)
	{
		return CplbConstant.TYPE_ZC.equals(productType)
				|| CplbConstant.TYPE_JJPC.equals(productType);
	}
	
	/**
	 * 判断当前日期是否在指定的开始/结束日期范围之内
	 * 
	 * @param htksrq
	 * @param htjsrq
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-2下午3:07:28
	 * @update
	 */
	public static boolean isBetween(Date htksrq, Date htjsrq) {
		String now = new java.text.SimpleDateFormat("yyyy/MM/dd")
				.format(new Date());
		boolean ok = true;
		// 开始日期和结束日期不能同时为空
		if (htksrq == null && htjsrq == null) {
			ok = false;
		}
		if (htksrq != null) {
			if (new java.text.SimpleDateFormat("yyyy/MM/dd")
			.format(htksrq).compareTo(now) > 0) {
				ok = false;
			}
		}
		if (htjsrq != null) {
			if (new java.text.SimpleDateFormat("yyyy/MM/dd")
			.format(htjsrq).compareTo(now) < 0) {
				ok = false;
			}
		}
		return ok;
	}
	
	/**
	   * 判断当前日期是否在指定的开始/结束日期范围之内
	   * 如果不在合同范围之内，需要判断价卡结束日期是否超过了
	   * @param htksrq 合同开始日期
	   * @param htjsrq 合同结束日期
	   * @param jkksrq 价卡开始日期
	   * @param jkjsrq 价卡结束日期
	   * @return 是否在此范围之内
	   */
	  public static boolean isBetween(Date htksrq, Date htjsrq, Date jkksrq, Date jkjsrq)
	  {
		  String now = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		  boolean ok = true;
		  //开始日期和结束日期不能同时为空
		  if((htksrq == null)
				  //此处判断出错了，导致如果都是空没有正确判断
				  && (htjsrq == null))
		  {
			  ok = false;
		  }

		  if(htksrq != null)
		  {
			  if(new java.text.SimpleDateFormat("yyyy/MM/dd")
				.format(htksrq).compareTo(now) > 0)
			  {
				  ok = false;
			  }
		  }
		  if(htjsrq != null)
		  {
			  if(new java.text.SimpleDateFormat("yyyy/MM/dd")
				.format(htjsrq).compareTo(now) < 0)
			  {
				  ok = false;
			  }
		  }
		  //如果不在合同范围之内，需要判断价卡结束日期是否超过了
		  if(!ok)
		  {
			  if(jkjsrq != null)
			  {
				  if(new java.text.SimpleDateFormat("yyyy/MM/dd")
					.format(jkjsrq).compareTo(now) >= 0)
				  {
					  ok = true;
				  }
			  }
		  }
		  return ok;
	  }
	
	/** 
	* @Title: calculateSlf 
	* @Description: 根据体积、重量和此种上楼方式计对应的优惠分段定义算出上楼费，体积和重量分别计算，取大者 
	* @param @param slfdys	此数组已经按照轻重货排序、且各自按照段起值倒序排列
	* @param @param zl
	* @param @param tj
	* @param @return    设定文件 
	* @return double    返回类型 
	* @throws 
	*/
	public static BigDecimal calculateSlf(List<PriceSectionSubEntity> priceSectionSubEntityList, BigDecimal zl, BigDecimal tj)
	{
		BigDecimal slf = BigDecimal.valueOf(0);
		BigDecimal slf_zl = BigDecimal.valueOf(0);
		BigDecimal slf_tj = BigDecimal.valueOf(0);
		boolean continue_zl = false;
		boolean continue_tj = false;
		boolean breakflag_zl = false;
		boolean breakflag_tj = false;
		for (int j=0; j<priceSectionSubEntityList.size(); j++){
			PriceSectionSubEntity priceSectionSubEntity = priceSectionSubEntityList.get(j);
			if (priceSectionSubEntity.getSectionAccodingItem().equals(PmsConstants.SECTION_ACCODING_ITEM_WEIGHT)) //重货
			{
				if (!breakflag_zl && check(priceSectionSubEntity, zl, continue_zl)) 
				{
					slf_zl = slf_zl.add(calculateZlTjSlf(priceSectionSubEntity, zl, continue_zl));
					breakflag_zl = "N".equals(priceSectionSubEntity.getCalcAlone());//单独计算的话不再向下循环重量计算
					continue_zl = "Y".equals(priceSectionSubEntity.getCalcAlone());//不是单独计算的需要继续向下取折扣
				}
			}
			else //轻货
			{
				if (!breakflag_tj && check(priceSectionSubEntity, tj, continue_tj)) 
				{
					slf_tj = slf_tj.add(calculateZlTjSlf(priceSectionSubEntity, tj, continue_tj));
					breakflag_tj = "N".equals(priceSectionSubEntity.getCalcAlone());//单独计算的话不再向下循环重量计算
					continue_tj = "Y".equals(priceSectionSubEntity.getCalcAlone());
				}
			}
		}
		slf = slf_zl.max(slf_tj);
		return slf;
	}
	
	/** 
	* @Title: calculateSlf 
	* @Description: 上楼费计算
	* @param @param slf		分段定义
	* @param @param zl		重量
	* @param @return    设定文件 
	* @return double    返回类型 
	* @throws 
	*/
	public static BigDecimal calculateZlTjSlf(PriceSectionSubEntity slfdy, BigDecimal value, boolean ignore)
	{
		String jjfs = slfdy.getSectionAccodingItem();
		if (ignore) //忽略直接取最大值
		{
			if (PmsConstants.SECTION_ACCODING_ITEM_PACKAGE.equals(jjfs)) //按票的，直接返回单价
			{
				return slfdy.getPrice();
			}
			else 
			{
				if ("N".equals(slfdy.getCalcAlone())) //单独计算的，返回(dz-0.01)*单价
				{
					if(PmsConstants.PRICE_TYPE_MONEY.equals(slfdy.getPriceType())){
						return slfdy.getPrice();
					} else {
						return slfdy.getPrice().multiply(slfdy.getEndValue().subtract(new BigDecimal(0.01)));
					}
				}
				else //不是单独计算的，需要将(dz-dq)，然后再乘以单价
				{
					return slfdy.getPrice().multiply(
							(slfdy.getEndValue().compareTo(slfdy.getStartValue()) == 0 ? new BigDecimal(0.01) : slfdy.getStartValue()));
				}
			}
		}
		if (PmsConstants.SECTION_ACCODING_ITEM_PACKAGE.equals(jjfs)) //按票的，直接返回单价
		{
			return slfdy.getPrice();
		}
		else 
		{
			if ("N".equals(slfdy.getCalcAlone())) //单独计算的，返回value*单价
			{
				if(PmsConstants.PRICE_TYPE_MONEY.equals(slfdy.getPriceType())){
					return slfdy.getPrice();
				} else {
					return slfdy.getPrice().multiply(value);
				}
			}
			else //不是单独计算的，需要将value减去段起值，然后再乘以单价
			{
				return slfdy.getPrice().multiply(value.subtract(slfdy.getStartValue().compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0) : (slfdy.getStartValue().subtract(new BigDecimal(0.01)))));
			}
		}
	}
	
	/** 
	* @Title: check 
	* @Description:  检查是否在优惠分段折扣内
	* @param @param d
	* @param @param value
	* @param @param ignore	对于不是单独计算的，需要将下面的折扣都加上来，这时候需要忽略范围判断
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	public static boolean check(PriceSectionSubEntity d, BigDecimal value,
			boolean ignore) {
		if (d == null) {
			return false;
		}
		if (ignore) {
			return true;
		}
		if (d.getStartValue().compareTo(value) <= 0 && d.getEndValue().compareTo(value) > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 把计算得出的double只保留小数点后指定位数
	 * @param value			原始值
	 * @param scale			保留的位数
	 * @param roundingMode	采用的进位方式
	 * @return
	 * */
	public static double toMoneyFormat(double value, int scale, int roundingMode)
	{
		//判断是否超过了小数点后scale位
		String valueStr = "" + value;
		if(valueStr.indexOf(".") != -1 && valueStr.substring(valueStr.indexOf(".") + 1).length() > scale)
		{
			if (roundingMode == BigDecimal.ROUND_CEILING) 
			{
				//为防止计算机不能表示的小数，在N位之后不为零(1.230000000000512)导致向上取整多加了1，首先保留scale+1位，进行4舍五入,然后对scale+1位小数进行向上取整
				return Double.parseDouble(getScaledValue(
						getScaledValue(valueStr, scale + 1, true, BigDecimal.ROUND_HALF_UP), 
						scale, true, BigDecimal.ROUND_CEILING));
			}
			else 
			{
				//不是向上取整的直接按照取整模式进行格式化
				return Double.parseDouble(getScaledValue(valueStr, scale, true, roundingMode));
			}
		}
		return value;	
	}
	
	public static String getScaledValue(String original, int scale, boolean cutZero, int roundingMode)
	{
		BigDecimal bd = new BigDecimal(original);
		bd = bd.divide(BigDecimal.valueOf(1), scale >= 0 ? scale : 0, roundingMode);
		String str = bd.toString();
		if(cutZero && scale > 0)
		{
			for(StringBuffer sb = new StringBuffer(str); sb.length() > 0;)
			{
				char c = sb.charAt(sb.length() - 1);
				if(c == '0' || c == '.')
				{
					sb.deleteCharAt(sb.length() - 1);
					if(c == '.')
						return sb.toString();
				} else
				{
					return sb.toString();
				}
			}

		}
		return str;
	}
	
	/**
	 * 此运单是否MRO采购商城订单
	 * 
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-4下午4:42:20
	 * @update
	 */
	public static boolean isMROOrder(String omsBizType)
	{
		String MRO_TYPE_STR = PkpConstants.MRO_TYPE_STR;
		MRO_TYPE_STR = StringUtil.isEmpty(MRO_TYPE_STR) ? "MRO" : MRO_TYPE_STR;
		return MRO_TYPE_STR.equals(omsBizType);
	}
	
	/**
	 * 把计算得出的double只保留小数点后指定位数
	 * 
	 * @param value
	 * @param scale
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-4下午4:48:10
	 * @update
	 */
	public static double toMoneyFormat(double value, int scale)
	{
		//判断是否超过了小数点后scale位
		String valueStr = "" + value;
		if(valueStr.indexOf(".") != -1 && valueStr.substring(valueStr.indexOf(".") + 1).length() > scale)
		{
			//首先保留scale+1位，进行4舍五入
			return Double.parseDouble(PmsUtils.getScaledValue(
					PmsUtils.getScaledValue(valueStr, scale + 1, true, BigDecimal.ROUND_HALF_UP), 
					scale, true, BigDecimal.ROUND_CEILING));
		}
		return value;	
	}
	
	/**
	 * 计算分段折扣
	 * @param zks 折扣信息定义
	 * @param weight 重量
	 * @param volume 体积
	 * @return 分段折扣
	 */
	public static BigDecimal calculateFdzk(List<PriceSectionSubEntity> zks,
			BigDecimal weight, BigDecimal volume) {
		// 最小金额
		BigDecimal finalMinValue = null;
		BigDecimal weightMinValue = null;
		BigDecimal volumnMinValue = null;
		// 根据轻重类型来获取区间
		for (int i = 0; i < zks.size(); i++) {
			PriceSectionSubEntity zk = zks.get(i);
			if (zk != null) {
				if (zk.getSectionAccodingItem().equals(
						PmsConstants.SECTION_ACCODING_ITEM_WEIGHT)) // 重货
				{
					if (PmsUtils.check(zk, weight, false)) {
						weightMinValue = zk.getPrice();
					}
				} else // 轻货
				{
					if (PmsUtils.check(zk, volume, false)) {
						volumnMinValue = zk.getPrice();
					}
				}
			}
		}
		if (weightMinValue != null && volumnMinValue != null) 
		{
			finalMinValue = weightMinValue.min(volumnMinValue);
		}
		else if (weightMinValue != null) 
		{
			finalMinValue = weightMinValue;
		}
		else if (volumnMinValue != null) 
		{
			finalMinValue = volumnMinValue;
		}
		return finalMinValue;
	}
	
	/**
	 * 获取优惠分段中的最小值
	 * 
	 * @param sections
	 * @param weight
	 * @param volumn
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-8下午7:42:56
	 * @update
	 */
	public static BigDecimal getMinInSections(List<List<PriceSectionSubEntity>> sections, BigDecimal weight, BigDecimal volumn)
	{
		BigDecimal minValue = null;
		if(sections != null && sections.size() > 0){
			for (int i = 0; i < sections.size(); i++) 
			{
				if (sections.get(i) != null) {
					BigDecimal price = PmsUtils.calculateFdzk(sections.get(i), weight, volumn);
					if(price != null){
						if(minValue == null){
							minValue = price;
						} else {
							minValue = minValue.min(price);
						}
					}
				}
			}
		}
		return minValue;
	}
	
	/**
	 * BIGDECIMAL为空时自动赋值
	 * 
	 * @param num
	 * @return
	 * @author 蒋落琛
	 * @date 2016-6-29下午1:48:59
	 * @update
	 */
	public static BigDecimal procBigDecimalNullUtil(BigDecimal num){
		return num == null ? BigDecimal.ZERO : num;
	}
	
	/**
	 * 各费用计算接口返回值处理
	 * 
	 * @param dto
	 * @param calculateType
	 * @param isInternalBelt
	 * @param deliveryMethod
	 * @param insuredRate
	 * @param insuredMoney
	 * @param productType
	 * @param isMROOrder
	 * @param isSmsService
	 * @author 蒋落琛
	 * @date 2016-6-27下午4:01:03
	 * @update
	 */
	public static void procCalculateResult(SurchargeDto dto,
			String calculateType, boolean isInternalBelt,
			String deliveryMethod, BigDecimal insuredRate,
			BigDecimal insuredMoney, String productType, Boolean isMROOrder,
			Boolean isSmsService) {
		BigDecimal min = BigDecimal.ZERO;
		BigDecimal max = BigDecimal.valueOf(999999999);
		BigDecimal value = BigDecimal.ZERO;
		boolean enabled = false;
		//免限价，都为0，并且不得修改
		if (!isInternalBelt) {
			enabled = true;
			if (dto != null && dto.getAmount() != null) {
				value = dto.getAmount();
				if(dto.getLockType() != null){
					switch (dto.getLockType()) {
						case LockTypeConstants.LOCK_TYPE_NO:
							break;
						case LockTypeConstants.LOCK_TYPE_UP:
							max = dto.getAmount();
							break;
						case LockTypeConstants.LOCK_TYPE_DOWN:
							min = dto.getAmount();
							break;
						case LockTypeConstants.LOCK_TYPE_ALL:
							min = dto.getAmount();
							enabled = false;
							break;
					}
				}
				// 如果有自定义的最小值和最大值，则以其为准
				if (dto.getMaxAmount() != null) {
					max = dto.getMaxAmount();
				}
				if (dto.getMinAmount() != null) {
					min = dto.getMinAmount();
				}
			} else {
				// 不能把所有费用都设为0，工本费=2，保价率=4，是默认值
				if (PmsConstants.ADDITIONAL_TYPE_INSURANCE
						.equals(calculateType)) {
					value = BigDecimal.valueOf(4); // hsw at
													// 2012-03-22,郑哥确认由3改为4
				} else if (PmsConstants.ADDITIONAL_TYPE_PAPER
						.equals(calculateType)) {
					value = BigDecimal.valueOf(2);
				}
			}

			// 打包费
			if (enabled && "PACKING".equals(calculateType)) // 打包费，可编辑的情况，默认为0的
			{
				value = BigDecimal.ZERO;
			}
			
			// 保价费
			if (PmsConstants.ADDITIONAL_TYPE_INSURANCE
					.equals(calculateType)){ // 保价费，还是不能修改
				if (dto != null && dto.getLockType() != null && insuredRate != null && insuredMoney != null) {
					min = BigDecimal.ZERO;
					max = BigDecimal.valueOf(999999999);
					BigDecimal je = insuredRate.multiply(insuredMoney)
							.multiply(BigDecimal.valueOf(0.001));
					// 整车强制不锁定
					if (PmsUtils.isZC(productType)
							|| dto.getLockType() == LockTypeConstants.LOCK_TYPE_NO) {
						value = je;
					} else if (dto.getLockType() == LockTypeConstants.LOCK_TYPE_UP) {
						value = value.min(je);
					} else if (dto.getLockType() == LockTypeConstants.LOCK_TYPE_DOWN) {
						value = value.max(je);
					}
					// 非MRO订单，需要向整数位四舍五入
					if (isMROOrder != null && !isMROOrder) {
						value = BigDecimal.valueOf(PmsUtils.toMoneyFormat(
								value.doubleValue(), 0,
								BigDecimal.ROUND_HALF_UP));
					}
					// 完全锁定
					dto.setLockType(LockTypeConstants.LOCK_TYPE_ALL);
				}
			}
			
			// 信息费，特殊考虑定日达和非选择短信服务的情况
			if (PmsConstants.ADDITIONAL_TYPE_SMS.equals(calculateType)) {
				min = BigDecimal.ZERO;
				max = BigDecimal.valueOf(9999999);
				if (isSmsService != null && isSmsService) {
					// 相比DC去除了判断是因为不管怎样，都不是修改客户，原来有个条件永远不成立
					value = BigDecimal.valueOf(1);
				} else {
					if (CplbUtils.isDRD(productType)
							|| CplbUtils.isLD(productType)
							// 偏线即使不发送短信也要收钱
							|| CplbUtils.isPX(productType)) {
						// 客户附加费为0时，允许为0，否则即使选择了不发短信，还是要收取
						value = dto != null
								&& value.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO
								: BigDecimal.valueOf(1);
					} else {
						value = BigDecimal.ZERO;
					}
				}
				// 受送货上楼影响，如果是送货上楼，信息费为零
				/*if (isTmAndNotContractedCustomer) {
					value = BigDecimal.ZERO;
				}*/
				// MRO订单信息费为零，且锁定不允许修改
				if (isMROOrder != null && isMROOrder) {
					value = BigDecimal.ZERO;
				}
			}
		}
		
		//下面进行设置或者控制
		if(dto == null){
			dto = new SurchargeDto();
		}
		dto.setMinAmount(min);
		dto.setMaxAmount(max);
		dto.setAmount(value);
		
		// 送货费
		//经济拼车/整车开单时，到货服务选择“送货上门”(如果没选默认是送货上门),送货费默认为0，并可以是可编辑状态，最低为0，最高为9999元。
		if(PmsConstants.ADDITIONAL_TYPE_DELIVERY.equals(calculateType) && 
				(PkpConstants.DELIVERY_HOME.equals(deliveryMethod)) && 
				(PmsUtils.isZC(productType) || CplbUtils.isJJPC(productType))){
			dto.setMinAmount(BigDecimal.ZERO);
			dto.setMaxAmount(BigDecimal.valueOf(9999));
			dto.setAmount(BigDecimal.ZERO);
		}
	}

	/**
	 * 是否启用客户 价格或折扣
	 * 然后结合是否启用的字段判断
	 *
	 * @Param [bseCustomer, type]
	 * @Return boolean
	 * @Throws
	 * @Author 廖文强
	 * @Date 2016/6/12 10:20
	 * @Version v1
	 */
	public static boolean isEnablePcOrDiscount(BseCustomerTyEntity bseCustomer, String type) {
		boolean isEnable = true;
		BseCustomerTyEntity baseBak = new BseCustomerTyEntity();
		if(bseCustomer != null){
			BeanUtils.copyProperties(bseCustomer, baseBak);
		} else {
			return false;
		}
		if (bseCustomer != null && MiserConstants.ACTIVE.equals(bseCustomer.getActive())) {//有效
			Date currDate = new Date();
			//先判断合同条件是否符合
			isEnable = isTimeCheck(baseBak.getContractStartTime(), baseBak.getContractEndTime(), currDate);

			if (!isEnable) {//合同不在有效期,再判断价格有效期
				isEnable = isTimeCheck(baseBak.getPcStartTime(), baseBak.getPcEndTime(), currDate);
			}

			if (isEnable) {//4. 然后结合是否启用的字段判断
				if ("PC".equals(type)) {
					if (MiserConstants.ACTIVE.equals(baseBak.getUseCustomerPc())) {//价格启用
						isEnable = true;
					} else {
						isEnable = false;
					}
				} else if ("DISCOUNT".equals(type)) {
					if (MiserConstants.ACTIVE.equals(baseBak.getUseCustomerDiscount())) {//折扣启用
						isEnable = true;
					} else {
						isEnable = false;
					}
				}
			}

		}
		return isEnable;
	}


	/**
	 * 时间判断
	 * @param startDate
	 * @param endDate
	 * @param currDate
	 * @return
	 */
	public static boolean isTimeCheck(Date startDate,Date endDate,Date currDate){
		boolean isEnable=false;
		if(endDate==null){//设置一秒后
			endDate=new Date(System.currentTimeMillis()+1000);
		}
		if(startDate!=null&&(startDate.compareTo(currDate)<=0&&endDate.compareTo(currDate)>0)){
			//开始时间不为空，且当前时间在开始和结束时间区间内
			isEnable=true;
		}
		return isEnable;
	}

	/**
	 * 将价格查询条件转换成活动需要的查询条件
	 * @param priceParam
	 * @return
	 * @author 陈宇霖
	 * @date 数据
	 */
	public static PriceEventTyQueryParam convertPriceQueryParamToEventQueryParam(PriceQueryParam priceParam) {
		if (priceParam == null) {
			return null;
		}

		PriceEventTyQueryParam eventParam = new PriceEventTyQueryParam();
		eventParam.setTransportType(priceParam.getTransTypeCode());
		eventParam.setCustomerCode(priceParam.getCustNo());
		eventParam.setArrivalOrgCode(priceParam.getDestCode());
		eventParam.setSendOrgCode(priceParam.getOriginCode());
		eventParam.setHistory(priceParam.isHistory());
		eventParam.setQueryTime(priceParam.getBillTime());
		eventParam.setOrderChannel(priceParam.getOrderChannel());
		eventParam.setOriginPositionInfo(priceParam.getOriginPositionInfo());
		eventParam.setDestPositionInfo(priceParam.getDestPositionInfo());
		//偏线使用经济快运的活动
		if (ProdTypeConstant.TYPE_PX.equals(eventParam.getTransportType())) {
			eventParam.setTransportType(ProdTypeConstant.TYPE_LD);
		}
		return eventParam;
	}
}

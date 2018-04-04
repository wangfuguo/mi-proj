package com.hoau.miser.module.biz.pricecard.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.IPriceCityService;
import com.hoau.miser.module.biz.base.api.server.service.ITranstypeService;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityVo;
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCorpSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCorpSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpSectionVo;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceCorpSectionDao;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceSectionDao;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class PriceCorpSectionServiceImpl implements IPriceCorpSectionService{

	private static final Logger log = LoggerFactory
			.getLogger(PriceCorpSectionServiceImpl.class);
	
	@Resource
	private PriceCorpSectionDao priceCorpSectionDao;
	
	@Resource
	private PriceSectionDao priceSectionDao;
	
	@Resource
	private ITranstypeService transtypeService;
	
	@Resource
	private IPriceCityService priceCityService;
	
	
	private static final String PRICECITYSCOPE = "STANDARD";
	
	@Override
	public List<PriceCorpSectionEntity> queryGroup(PriceCorpSectionEntity pcse,int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return priceCorpSectionDao.queryGroup(pcse, rowBounds);
	}
	
	@Override
	public Long queryGroupCount(PriceCorpSectionEntity pcse) {
		return priceCorpSectionDao.queryGroupCount(pcse);
	}
	
	@Transactional
	@Override
	public void insertBatch(PriceCorpSectionVo vo) {
		
		//贯穿整过程的时间
		Date currentTime = queryCurrentDateTime();
		
		//设置非变量字段
		vo.getPriceCorpSection().setCreateUserCode(MiserUserContext.getCurrentUser().getUserName());
		vo.getPriceCorpSection().setCreateTime(currentTime);
		vo.getPriceCorpSection().setModifyUserCode(MiserUserContext.getCurrentUser().getUserName());
		vo.getPriceCorpSection().setModifyTime(currentTime);
		vo.getPriceCorpSection().setActive(MiserConstants.ACTIVE);
		vo.getPriceCorpSection().setDataorign(DataOrignEnum.PMS.getCode());
		
		List<PriceCorpSectionEntity> list = new ArrayList<PriceCorpSectionEntity>();
		for (PriceCorpSectionEntity pcs : vo.getList()) {
			pcs.setId(UUID.randomUUID().toString());
			list.add(pcs);
		}
		vo.setList(list);
		
		//old不为空则表示是修改
		if(vo.getOld() != null){
			vo.getOld().setDataorign(DataOrignEnum.PMS.getCode());
			PriceCorpSectionEntity newest = new PriceCorpSectionEntity();  
			//PriceCorpSectionEntity newpcs = vo.getOld();
			//如果是对生效中的数据进行更改则需要把生效时间设置为当前时间,
			if( vo.getOld().getState().equals(PriceCorpSectionEntity.STATE_2)){
				newest.setInvalidTime(vo.getPriceCorpSection().getEffectiveTime());
				newest.setModifyUserCode(MiserUserContext.getCurrentUser().getUserName());
				newest.setModifyTime(currentTime);
				priceCorpSectionDao.update(vo.getOld(), newest);
			}else{
				//对非生效中的数据直接作废
				newest.setActive(MiserConstants.INACTIVE);
				priceCorpSectionDao.update(vo.getOld(), newest);
			}
		}
		
		//检查重复数据
		
		PriceCorpSectionEntity basePcse = new PriceCorpSectionEntity();
		basePcse.setState(PriceCorpSectionEntity.STATE_2);
		basePcse.setCorpCode(vo.getPriceCorpSection().getCorpCode());
		List<PriceCorpSectionEntity> base = priceCorpSectionDao.queryGroup(basePcse);
		for (PriceCorpSectionEntity pg : base) {
			if (!(compareRange(pg.getEffectiveTime(), pg.getInvalidTime(), 
					vo.getPriceCorpSection().getEffectiveTime(), vo.getPriceCorpSection().getInvalidTime()))) {
				throw new BusinessException("该网点已有正在生效的，且有效时间重合，不能提交！");
			}
		}
		priceCorpSectionDao.insertBatch(vo);
		
	}
	
	@Override
	public List<PriceCorpSectionEntity> search(PriceCorpSectionEntity pcse) {
		return priceCorpSectionDao.search(pcse);
	}

	
	@Transactional
	@Override
	public void revoke(List<PriceCorpSectionEntity> list) {
		
		PriceCorpSectionEntity pcse = new PriceCorpSectionEntity();
		pcse.setActive(MiserConstants.INACTIVE);
		pcse.setModifyUserCode(MiserUserContext.getCurrentUser().getUserName());
		pcse.setModifyTime(this.queryCurrentDateTime());
		for (PriceCorpSectionEntity priceCorpSectionEntity : list) {
			priceCorpSectionDao.update(priceCorpSectionEntity, pcse);
		}
		
	}
	
	@Override
	public String exportExcel(List<PriceCorpSectionEntity> list) {
		String filePath = null;
		List<String> titleNames = new ArrayList<String>();
		
		titleNames.add("序号");
		titleNames.add("事业部");
		titleNames.add("大区");
		titleNames.add("路区");
		titleNames.add("门店");
		titleNames.add("物流代码");
		
		titleNames.add("运输类型");
		titleNames.add("到达价格城市");
		titleNames.add("首重(1段)");
		titleNames.add("首重(1段)单价");
		titleNames.add("续重(1段)单价");
		titleNames.add("首重(2段)");
		titleNames.add("首重(2段)单价");
		titleNames.add("续重(2段)单价");
		titleNames.add("首重(3段)");
		titleNames.add("首重(3段)单价");
		titleNames.add("续重(3段)单价");
		titleNames.add("总运费优惠分段");
		
		titleNames.add("生效时间");
		titleNames.add("失效时间");
		titleNames.add("当前状态");
		titleNames.add("备注");
		titleNames.add("创建人");
		titleNames.add("创建时间");
		titleNames.add("最后修改人");
		titleNames.add("最后修改时间");
		// 属性名，反射取属性
		List<String> paramNames = new ArrayList<String>();
		paramNames.add("index");
		paramNames.add("divisionName");
		paramNames.add("bigRegionName");
		paramNames.add("roadAreaName");
		paramNames.add("corpName");
		paramNames.add("logisticName");
		
		paramNames.add("transTypeName");
		paramNames.add("arrivePriceCityName");
		paramNames.add("firstWeight");
		paramNames.add("firstWeightPrice");
		paramNames.add("firstAddWeightPrice");
		paramNames.add("secondWeight");
		paramNames.add("secondWeightPrice");
		paramNames.add("secondAddWeightPrice");
		paramNames.add("thirdWeight");
		paramNames.add("thirdWeightPrice");
		paramNames.add("thirdAddWeightPrice");
		paramNames.add("freightSectionName");
		
		paramNames.add("effectiveTime");
		paramNames.add("invalidTime");
		paramNames.add("stateName");
		paramNames.add("remark");
		paramNames.add("createUserName");
		paramNames.add("createTime");
		paramNames.add("modifyUserName");
		paramNames.add("modifyTime");
		try {
			filePath = new ExcelUtil<PriceCorpSectionEntity>().exportExcel(titleNames,
					paramNames, list);
		} catch (Exception e) {
			log.error("生成导出数据异常!", e);
			filePath = null;
		}

		return filePath;
	}
	
	@Override
	public Map<String, Object> impl(String filePath) throws Exception {
		// 返回信息串
		Map<String, Object> retMap = new HashMap<String, Object>();
		// excel中读出的网点详细信息
		List<Map<String, String>> excelList = ExcelUtil.readToListByFile(
				filePath, 12, 2);
		// excel信息用于填充前台表单
		List<PriceCorpSectionEntity> priceCorpSectionList = new ArrayList<PriceCorpSectionEntity>();

		// 优惠分段--燃油附加费
		Map<String, String> priceSectionCodeMap = new HashMap<String, String>();
		PriceSectionEntity pse = new PriceSectionEntity();
		pse.setActive(MiserConstants.ACTIVE);
		pse.setSectionedItem("FREIGHT");
		List<PriceSectionEntity> priceSectionList = priceSectionDao.queryPriceSection(pse);
		for (PriceSectionEntity value : priceSectionList) {
			priceSectionCodeMap.put(value.getName(), value.getCode());
		}

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

		
		// 缓存出发到达价格城市名称对应编码
		Map<String, String> arrivalPriceCityNameAndCodes = new HashMap<String, String>();

		// 批量导入后，提示：“本次导入***条数据，新增待生效数据***条，覆盖原待生效数据***条。”
		int succCount = 0;
		int failCount = 0;
		// 如果excel文件有重复数据提示并放弃导入
		String repeat = "";
		String verify = "";
		Map<String, Long> repeatMap = new HashMap<String, Long>();
		// 6、明细列表——批量导入入，包含以下字段：出发价格城市 ^,到达价格城市 ^,运输类型 ^,重量单价 ^v,体积单价 ^v,附件费
		// ^v,最低收费 ^v,燃油服务费 ^v
		for (Map<String, String> map : excelList) {
			PriceCorpSectionEntity pcsv = new PriceCorpSectionEntity();
			try {
				// 出发价格城市
				// 到达价格城市,根据名称从接口查询
				String arrivalPriceCityName = StringUtils.trim(map.get("1"));
				if (arrivalPriceCityNameAndCodes.containsKey(arrivalPriceCityName)) {
					pcsv.setArrivePriceCity(arrivalPriceCityNameAndCodes.get(arrivalPriceCityName));
				} else {
					PriceCityEntity arrivalPriceCityEntity = new PriceCityEntity();
					arrivalPriceCityEntity.setName(arrivalPriceCityName);
					arrivalPriceCityEntity.setType("ARRIVAL");
					arrivalPriceCityEntity.setPriceCityScope(PRICECITYSCOPE);
					PriceCityVo arrivalPriceCityVo = new PriceCityVo();
					arrivalPriceCityVo.setQueryParam(arrivalPriceCityEntity);
					PriceCityEntity arrivalPriceCity = priceCityService.queryPriceCityByName(arrivalPriceCityVo);
					if (arrivalPriceCity != null) {
						arrivalPriceCityNameAndCodes.put(arrivalPriceCityName,
								arrivalPriceCity.getCode());
						pcsv.setArrivePriceCity(arrivalPriceCity.getCode());
					} else {
						throw new RuntimeException("到达价格城市["
								+ arrivalPriceCityEntity.getName() + "]不存在");
					}
				}
				pcsv.setArrivePriceCityName(arrivalPriceCityName);
				// 运输类型
				pcsv.setTransTypeCode(transTypeCodeMap.get(map.get("0")));
				
				pcsv.setTransTypeName(StringUtils.trim(map.get("0")));
				//首重(1段)
				String firstWeight = map.get("2");
				BigDecimal db = null;
				try {
					Double temp = Double.valueOf(firstWeight);
					if (temp < 0) {
						verify = "首重(1段)不能小于0";
						break;
					}
					;
					db = BigDecimal.valueOf(temp);
				} catch (Exception ex) {
					verify = "首重(1段)只能是数字";
					break;
				}
				pcsv.setFirstWeight(db);
				// 首重(1段)单价
				String firstWeightPrice = map.get("3");
				db = null;
				try {
					Double temp = Double.valueOf(firstWeightPrice);
					if (temp < 0) {
						verify = "首重(1段)单价不能小于0";
						break;
					}
					;
					db = BigDecimal.valueOf(temp);
				} catch (Exception ex) {
					verify = "首重(1段)单价只能是数字";
					break;
				}
				pcsv.setFirstWeightPrice(db);
				
				// 续重(1段)单价
				String firstAddWeightPrice = map.get("4");
				db = null;
				try {
					Double temp = Double.valueOf(firstAddWeightPrice);
					if (temp < 0) {
						verify = "续重(1段)单价不能小于0";
						break;
					}
					;
					db = BigDecimal.valueOf(temp);
				} catch (Exception ex) {
					verify = "续重(1段)单价只能是数字";
					break;
				}
				pcsv.setFirstAddWeightPrice(db);
				
				//2段
				String secondWeight = map.get("5");
				db = null;
				try {
					if(!StringUtil.isBlank(secondWeight)){
						Double temp = Double.valueOf(secondWeight);
						db = BigDecimal.valueOf(temp);
					}
				} catch (Exception ex) {
					verify = "首重(2段)只能是数字";
					break;
				}
				pcsv.setSecondWeight(db);
				
				//2段
				String secondWeightPrice = map.get("6");
				db = null;
				try {
					if(!StringUtil.isBlank(secondWeightPrice)){
						Double temp = Double.valueOf(secondWeightPrice);
						db = BigDecimal.valueOf(temp);
					}
				} catch (Exception ex) {
					verify = "首重(2段)单价只能是数字";
					break;
				}
				pcsv.setSecondWeightPrice(db);
				
				//2段
				String secondAddWeightPrice = map.get("7");
				db = null;
				try {
					if(!StringUtil.isBlank(secondAddWeightPrice)){
						Double temp = Double.valueOf(secondAddWeightPrice);
						db = BigDecimal.valueOf(temp);
					}
				} catch (Exception ex) {
					verify = "续重(2段)单价只能是数字";
					break;
				}
				pcsv.setSecondAddWeightPrice(db);
				
				//3段
				String thirdWeight = map.get("8");
				db = null;
				try {
					if(!StringUtil.isBlank(thirdWeight)){
						Double temp = Double.valueOf(thirdWeight);
						db = BigDecimal.valueOf(temp);
					}
				} catch (Exception ex) {
					verify = "首重(3段)只能是数字";
					break;
				}
				pcsv.setThirdWeight(db);
				
				String thirdWeightPrice = map.get("9");
				db = null;
				try {
					if(!StringUtil.isBlank(thirdWeightPrice)){
						Double temp = Double.valueOf(thirdWeightPrice);
						db = BigDecimal.valueOf(temp);
					}
				} catch (Exception ex) {
					verify = "首重(3段)单价只能是数字";
					break;
				}
				pcsv.setThirdWeightPrice(db);
				
				String thirdAddWeightPrice = map.get("10");
				db = null;
				try {
					if(!StringUtil.isBlank(thirdAddWeightPrice)){
						Double temp = Double.valueOf(thirdAddWeightPrice);
						db = BigDecimal.valueOf(temp);
					}
				} catch (Exception ex) {
					verify = "续重(3段)单价只能是数字";
					break;
				}
				pcsv.setThirdAddWeightPrice(db);
				
				//校验1段
				if(pcsv.getFirstWeight().doubleValue() == 0){
					verify="首重(1段)不能为空且不能小于0";
					break;
				}
				if(pcsv.getFirstWeightPrice() == null || pcsv.getFirstWeightPrice().doubleValue() <= 0){
					verify="首重(1段)单价不能为空且不能小于0";
					break;
				}
				if(pcsv.getFirstAddWeightPrice() == null || pcsv.getFirstAddWeightPrice().doubleValue() <= 0){
					verify="续重(1段)单价不能为空且不能小于0";
					break;
				}
				if(pcsv.getSecondWeight()!=null){
					if(!(pcsv.getSecondWeight().doubleValue() > pcsv.getFirstWeight().doubleValue())){
						verify="需满足：首重(2段)>首重(1段)";
						break;
					}
					if(pcsv.getSecondWeightPrice() ==null || pcsv.getSecondWeightPrice().doubleValue() <=0){
						verify="首重2段不为0时，首重2段单价不能为空且不小于0";
						break;
					}
					if(pcsv.getSecondAddWeightPrice() == null || pcsv.getSecondAddWeightPrice().doubleValue() <= 0){
						verify="首重2段不为0时，续重2段单价不能为空且不能小于0";
						break;
					}
				}
				if(pcsv.getThirdWeight()!=null){
					if(pcsv.getSecondWeight() == null){
						verify="需满足：首重(3段)>首重(2段)";
						break;
					}
					if(!(pcsv.getThirdWeight().doubleValue() > pcsv.getSecondWeight().doubleValue())){
						verify="需满足：首重(3段)>首重(2段)";
						break;
					}
					if(pcsv.getThirdWeightPrice() ==null || pcsv.getThirdWeightPrice().doubleValue() <=0){
						verify="首重3段不为0时，首重3段单价不能为空且不小于0";
						break;
					}
					if(pcsv.getThirdAddWeightPrice() == null || pcsv.getThirdAddWeightPrice().doubleValue() <= 0){
						verify="首重3段不为0时，续重3段单价不能为空且不能小于0";
						break;
					}
				}
				
				
				if(pcsv.getThirdWeight()!=null){
					if(pcsv.getSecondWeight()!=null){
						if(!(pcsv.getThirdWeight().doubleValue() > pcsv.getSecondWeight().doubleValue() 
								&& pcsv.getSecondWeight().doubleValue() > pcsv.getFirstWeight().doubleValue())){
							verify = "需满足：首重(3段)>首重(2段)>首重(1段)";
							break;
						}
					}else{
						verify = "首重3段不为空时，首重2段不能为空";
						break;
					}
				}
				// 运费服务费 --优惠分段
				pcsv.setFreightSectionCode(priceSectionCodeMap.get(map.get("11")));
				
				pcsv.setFreightSectionName(map.get("11"));

				// 累计重复:到达城市+运输类型相同则为重复
				String repeatKey = pcsv.getArrivePriceCityName() + ":"
						+ pcsv.getTransTypeName();
				if (repeatMap.containsKey(repeatKey)) {
					repeatMap.put(repeatKey, repeatMap.get(repeatKey) + 1);
					// 有重复随意取一提示即可
					repeat = repeatKey;
					// 如果重复放弃导入
					break;
				} else {
					repeatMap.put(repeatKey, 1L);
				}
				if (StringUtils.isEmpty(pcsv.getArrivePriceCity())) {
					verify = "系统没有到达价格城市："
							+ pcsv.getArrivePriceCityName();
				} else if (StringUtils.isEmpty(pcsv.getTransTypeCode())) {
					verify = "系统没有运输类型：" + pcsv.getTransTypeName();
				} else if (StringUtils.isEmpty(pcsv
						.getFreightSectionCode()) && !StringUtils.isEmpty(pcsv.getFreightSectionName())) {
					verify = "系统没有运费优惠分段："
							+ pcsv.getFreightSectionName();
				}
				if (StringUtils.isNotEmpty(verify)) {
					// 如果校验失败放弃导入
					break;
				}
				priceCorpSectionList.add(pcsv);
				succCount++;
			} catch (Exception e) {
				failCount++;
				log.error("批量导入模版数据异常，业务需要仅作提示", e);
				verify = e.getMessage();
				// 如果失败放弃导入
				break;
			} finally {

				// priceCorpList.add(priceCorpVo.copy());
			}

		}
		retMap.put("succCount", succCount);
		retMap.put("failCount", failCount);
		if (StringUtils.isNotEmpty(verify)) {
			retMap.put(
					"repeatTip",
					"第"
							+ (priceCorpSectionList.size() - succCount + 1)
							+ "行->"
							+ (StringUtils.isEmpty(repeat) ? (StringUtils
									.isEmpty(verify) ? "" : verify) : repeat + "重复"
									+ repeatMap.get(repeat) + "条"));
		}else{
			retMap.put("repeatTip","");
		}
		
		retMap.put("list", priceCorpSectionList);
		return retMap;
	}

	@Override
	public List<PriceCorpSectionEntity> reform(PriceCorpSectionVo vo) {
		List<PriceCorpSectionEntity> list =  new ArrayList<PriceCorpSectionEntity>();
		PriceCorpSectionEntity old = vo.getOld();
		if(old != null){
			Long index  = 0L;
			for (PriceCorpSectionEntity priceCorpSectionEntity : vo.getList()) {
				priceCorpSectionEntity.setCreateUserName(old.getCreateUserName().split(" ")[2]);
				priceCorpSectionEntity.setCreateUserCode(old.getCreateUserCode());
				priceCorpSectionEntity.setModifyUserName(old.getModifyUserName().split(" ")[2]);
				priceCorpSectionEntity.setModifyUserCode(old.getModifyUserCode());
				priceCorpSectionEntity.setCreateTime(old.getCreateTime());
				priceCorpSectionEntity.setModifyTime(old.getModifyTime());
				priceCorpSectionEntity.setIndex(++index);
				list.add(priceCorpSectionEntity);
			}
		}else{
			Long index  = 0L;
			for (PriceCorpSectionEntity priceCorpSectionEntity : vo.getList()) {
				priceCorpSectionEntity.setCreateUserName(MiserUserContext.getCurrentUser().getEmpName());
				priceCorpSectionEntity.setCreateUserCode(MiserUserContext.getCurrentUser().getUserName());
				priceCorpSectionEntity.setModifyUserName(MiserUserContext.getCurrentUser().getEmpName());
				priceCorpSectionEntity.setModifyUserCode(MiserUserContext.getCurrentUser().getUserName());
				priceCorpSectionEntity.setCreateTime(new Date());
				priceCorpSectionEntity.setModifyTime(new Date());
				priceCorpSectionEntity.setIndex(++index);
				list.add(priceCorpSectionEntity);
			}
		}
		
		return list;
	}
	
	@Override
	public Date queryCurrentDateTime(){
		return priceCorpSectionDao.queryCurrentDateTime();
	}

	
	/**
	 * 
	 * @Description: TODO 比较两个时间断，把数据库时间做为基准看前端数据是否合法
	 * @param @param dbeffectiveTime 数据库中生效时间
	 * @param @param dbinvalidTime 数据库失效时间
	 * @param @param pageeffectiveTime 页面生效时间
	 * @param @param pageinvalidTime 页面失效时间
	 * @param @return
	 * @return boolean 返回true 表示数据库不存在时间已经包含页面时间
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月28日
	 */
	private boolean compareRange(Date dbeffectiveTime, Date dbinvalidTime,
			Date pageeffectiveTime, Date pageinvalidTime) {
		Long dbinvalidTimeT = Long.MAX_VALUE;
		Long pageinvalidTimeT = Long.MAX_VALUE;
		if (dbinvalidTime != null) {
			dbinvalidTimeT = dbinvalidTime.getTime();
		}
		if (pageinvalidTime != null) {
			pageinvalidTimeT = pageinvalidTime.getTime();
		}
		return compareRange(dbeffectiveTime.getTime(), dbinvalidTimeT,
				pageeffectiveTime.getTime(), pageinvalidTimeT);
	}

	/**
	 * 
	 * @Description: TODO比较两个区间把区间一作为基准
	 * @param @param start1 区间一开始
	 * @param @param end1 区间一结束
	 * @param @param start2区间二开始
	 * @param @param end2区间二结束
	 * @param @return
	 * @return boolean true 表示 区间一不包含区间二，false表示区间一包含区间二
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月28日
	 */
	private boolean compareRange(Long start1, Long end1, Long start2, Long end2) {
		// 1作为参考数基准
		if (end2 <= start1)
			return true;
		if (end1 <= start2)
			return true;
		return false;
	}



}

package com.hoau.miser.module.biz.pricecard.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerSectionService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerSectionSubService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerSectionSubVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionVo;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceCustomerSectionSubDao;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 客户专属价格接口实现 ClassName: PriceCustomerService
 * 
 * @author 何羽
 * @date 2016年5月5日
 * @version V2.0
 */
@Service
public class PriceCustomerSectionSubService implements IPriceCustomerSectionSubService {
	
	@Resource
	private PriceCustomerSectionSubDao priceCustomerSectionSubDao;
	@Resource
	private IPriceCustomerSectionService priceCustomerSectionService;
	@Resource
	private ITranstypeService transtypeService;
	@Resource
	private IPriceSectionService priceSectionService;
	@Resource
	private IPriceCityService priceCityService;

	
	private static final Logger log = LoggerFactory
			.getLogger(PriceCustomerSectionSubService.class);


	@Transactional
	public String insertSelective(PriceCustomerSectionSubEntity priceCustomerSectionSubEntity) {
		String id = beforOperDeal(priceCustomerSectionSubEntity);
		priceCustomerSectionSubDao.insertSelective(priceCustomerSectionSubEntity);
		return id;
	}

	@Override
	public List<PriceCustomerSectionSubEntity> selectCustomerSubListByParam(
			PriceCustomerSectionSubEntity priceCustomerSectionSubEntity, int limit, int start) {
		
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		//add by dengyin 2016-4-11 11:38:16 解决查询明细时缓慢 增加分区
		//根据parentId查询出主表记录的创建时间
//		PriceCustomerSectionEntity tmpObj = new PriceCustomerSectionEntity();
//		tmpObj.setId(priceCustomerSectionSubEntity.getParentId());
		
//		String partitionName = "PRICE_CUS_SUBZ_PART";
//		List<PriceCustomerSectionEntity> priceCustomerEntityList = priceCustomerSectionService.queryPriceCustomerMixed(tmpObj, 1, 0);
//		PriceCustomerSectionEntity targetObj = priceCustomerEntityList.get(0);
	 
//		Date curCreateTime = targetObj.getCreateTime();
		
//		String curCreateTimeStr = format.format(curCreateTime);

//		int year = Integer.valueOf(curCreateTimeStr.split("-")[0].substring(2, 4));
//		int month = Integer.valueOf(curCreateTimeStr.split("-")[1]);
		
//		partitionName += year;
//		
//		if(month > 6){
//			partitionName = partitionName + "12";
//		}
//		else {
//			partitionName = partitionName + "06";
//		}
		
//		priceCustomerSubEntity.setPartitionName(partitionName);
		
		
		List<PriceCustomerSectionSubEntity> resultList = priceCustomerSectionSubDao.listCustomerSubListByParam(priceCustomerSectionSubEntity);
		
		return resultList;
	}

	@Override
	public int updateByPrimaryKeySelective(PriceCustomerSectionSubEntity priceCustomerSectionSubEntity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int batchUpdateActiveByIdStr(PriceCustomerSectionSubVo priceCustomerSectionSubVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void batchInsertCustomerSub(List<PriceCustomerSectionSubEntity> priceCustomerSectionSubEntityList) {


		if (priceCustomerSectionSubEntityList != null
				&& priceCustomerSectionSubEntityList.size() > 0) {

			List<PriceCustomerSectionSubEntity> tmpPriceCustomerSubEntityList = new ArrayList<PriceCustomerSectionSubEntity>();

			for (int i = 0; i < priceCustomerSectionSubEntityList.size(); i++) {
				PriceCustomerSectionSubEntity curSubEntity = priceCustomerSectionSubEntityList
						.get(i);
				beforOperDeal(curSubEntity);

				tmpPriceCustomerSubEntityList.add(curSubEntity);
				int tmpSize = tmpPriceCustomerSubEntityList.size();

				if (tmpSize % 500 == 0) {
					priceCustomerSectionSubDao
							.batchInsertCustomerSub(tmpPriceCustomerSubEntityList);
					tmpPriceCustomerSubEntityList.clear();
				}
			}

			// 若上面不能整除 余下的记录在这里做一个批量提交
			if (tmpPriceCustomerSubEntityList.size() > 0) {
				priceCustomerSectionSubDao
						.batchInsertCustomerSub(tmpPriceCustomerSubEntityList);
				tmpPriceCustomerSubEntityList.clear();
			}
		}
	}

	@Override
	public void batchUpdateForInvalidByParentId(Map<String, String> paramMap) {
		priceCustomerSectionSubDao.batchUpdateForInvalidByParentId(paramMap);

	}

	@Override
	public void destoryPriceCustomerSubByParentIdStr(Map<String, Object> paramMap) {
		priceCustomerSectionSubDao.destoryPriceCustomerSubByParentIdStr(paramMap);
	}

	@Override
	public Map<String, Object> bathImplPriceCustomer(String path) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, String>> list;
		List<PriceCustomerSectionSubEntity> priceCustomerSubList = new ArrayList<PriceCustomerSectionSubEntity>();
		list = ExcelUtil.readToListByFile(path, 13, 2);
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

		// 优惠分段
		PriceSectionVo psv = new PriceSectionVo();
		PriceSectionEntity sEntity = new PriceSectionEntity();
		sEntity.setActive(MiserConstants.YES);
		psv.setPriceSectionEntity(sEntity);
		List<PriceSectionEntity> sList = priceSectionService
				.queryPriceSection(psv);
		Map<String, String> sectionMap = new HashMap<String, String>();
		for (PriceSectionEntity entity : sList) {
			sectionMap.put(entity.getName(), entity.getCode());
		}

		int succCount = 0;
		int failCount = 0;
		HashMap<String, String> repeatMap = new HashMap<String, String>();

        //缓存出发到达价格城市名称对应编码
        Map<String, String> sendPriceCityNameAndCodes = new HashMap<String, String>();
        Map<String, String> arrivalPriceCityNameAndCodes = new HashMap<String, String>();

		for (Map<String, String> map : list) {
			PriceCustomerSectionSubEntity subEntity = new PriceCustomerSectionSubEntity();
			String subKey = "出发城市：" + StringUtil.trim(map.get(0 + ""))
					+ ";到达城市：" + StringUtil.trim(map.get(1 + "")) + ";运输类型："
					+ StringUtil.trim(map.get(2 + ""));
			try {
				
				if(null == map.get(0 + "") || "".equals(map.get(0 + ""))){
					retMap.put("error","出发城市不能为空");
					break;
				}
				
				if(null == map.get(1 + "") || "".equals(map.get(1 + ""))){
					retMap.put("error","到达城市不能为空");
					break;
				}				
				
				if(null == map.get(2 + "") || "".equals(map.get(2 + ""))){
					retMap.put("error","运输类型不能为空");
					break;
				}
				
				if(null == map.get(3 + "") || "".equals(map.get(3 + ""))){
					retMap.put("error","一段首重不能为空");
					break;
				}				
				
				if(null == map.get(4 + "") || "".equals(map.get(4 + ""))){
					retMap.put("error","一段首重单价不能为空");
					break;
				}
				if(null == map.get(5 + "") || "".equals(map.get(5 + ""))){
					retMap.put("error","一段续重单价不能为空");
					break;
				}
				if(null == map.get(6 + "") || "".equals(map.get(6 + ""))){
					retMap.put("error","二段首重不能为空");
					break;
				}
				if(null == map.get(7 + "") || "".equals(map.get(7 + ""))){
					retMap.put("error","二段首重单价不能为空");
					break;
				}
				if(null == map.get(8 + "") || "".equals(map.get(8 + ""))){
					retMap.put("error","二段续重单价不能为空");
					break;
				}
				if(null == map.get(9 + "") || "".equals(map.get(9 + ""))){
					retMap.put("error","三段首重不能为空");
					break;
				}
				if(null == map.get(10 + "") || "".equals(map.get(10 + ""))){
					retMap.put("error","三段首重单价不能为空");
					break;
				}
				if(null == map.get(11 + "") || "".equals(map.get(11 + ""))){
					retMap.put("error","三段续重单价不能为空");
					break;
				}

				//根据名称从接口获取出发价格城市编码
                String startPriceCityName = StringUtil.trim(map.get(0 + ""));
                if (sendPriceCityNameAndCodes.containsKey(startPriceCityName)) {
                    subEntity.setSendPriceCity(sendPriceCityNameAndCodes.get(startPriceCityName));
                } else {
                    PriceCityEntity startPriceCityEntity = new PriceCityEntity();
                    startPriceCityEntity.setName(StringUtil.trim(map.get(0 + "")));
                    startPriceCityEntity.setType("SEND");
                    startPriceCityEntity.setPriceCityScope("STANDARD");
                    PriceCityVo startPriceCityVo = new PriceCityVo();
                    startPriceCityVo.setQueryParam(startPriceCityEntity);
                    PriceCityEntity startPriceCity = priceCityService.queryPriceCityByName(startPriceCityVo);
                    if (startPriceCity != null) {
                        sendPriceCityNameAndCodes.put(startPriceCityName, startPriceCity.getCode());
                        subEntity.setSendPriceCity(startPriceCity.getCode());
                    } else {
                        retMap.put("error","不存在的出发城市:" + StringUtil.trim(map.get(0 + "")));
                        break;
                    }
                }
                
                
				//根据名称从接口获取到达价格城市编码
                String arrivalPriceCityName = StringUtil.trim(map.get(1 + ""));
                if (arrivalPriceCityNameAndCodes.containsKey(arrivalPriceCityName)) {
                    subEntity.setArrivePriceCity(arrivalPriceCityNameAndCodes.get(arrivalPriceCityName));
                } else {
                    PriceCityEntity arrivalPriceCityEntity = new PriceCityEntity();
                    arrivalPriceCityEntity.setName(StringUtil.trim(map.get(1 + "")));
                    arrivalPriceCityEntity.setType("ARRIVAL");
                    arrivalPriceCityEntity.setPriceCityScope("STANDARD");
                    PriceCityVo arrivalPriceCityVo = new PriceCityVo();
                    arrivalPriceCityVo.setQueryParam(arrivalPriceCityEntity);
                    PriceCityEntity arrivalPriceCity = priceCityService.queryPriceCityByName(arrivalPriceCityVo);
                    if (arrivalPriceCity != null) {
                        subEntity.setArrivePriceCity(arrivalPriceCity.getCode());
                    } else {
                        retMap.put("error","不存在的到达城市:" + StringUtil.trim(map.get(1 + "")));
                        break;
                    }
                }

				if (!transTypeCodeMap.containsKey(StringUtil.trim(map.get(2 + "")))) {
					retMap.put("error","不存在的运输类型:" + StringUtil.trim(map.get(2 + "")));
					break;
				}
				
				if(null != map.get(12 + "") && ! "".equals(map.get(12 + ""))){
					if (!sectionMap.containsKey(StringUtil.trim(map.get(12 + "")))) {
						retMap.put("error","不存在的运费优惠分段:" + StringUtil.trim(map.get(12 + "")));
						break;
					}
				}

				// 出发城市名称
				subEntity.setSendPriceCityName(StringUtil.trim(map.get(0 + "")));
				
				// 到达城市名称
				subEntity.setArrivePriceCityName(StringUtil.trim(map.get(1 + "")));
				
				// 运输类型编码
				subEntity.setTransTypeCode(transTypeCodeMap.get(StringUtil.trim(map.get(2 + ""))));
				
				// 运输类型名称
				subEntity.setTransTypeName(StringUtil.trim(map.get(2 + "")));
				
				// 一段首重
				subEntity.setFirstWeight(new BigDecimal(StringUtil.trim(map.get(3 + ""))));
				
				// 一段首重单价
				subEntity.setFirstWeightPrice(new BigDecimal(StringUtil.trim(map.get(4 + ""))));
				
				// 一段续重单价
				subEntity.setFirstAddWeightPrice(new BigDecimal(StringUtil.trim(map.get(5 + ""))));
				
				// 二段首重
				subEntity.setSecondWeight(new BigDecimal(StringUtil.trim(map.get(6 + ""))));

				// 二段首重单价
				subEntity.setSecondWeightPrice(new BigDecimal(StringUtil.trim(map.get(7 + ""))));
				
				// 二段续重单价
				subEntity.setSecondAddWeightPrice(new BigDecimal(StringUtil.trim(map.get(8 + ""))));
				
				// 三段首重
				subEntity.setThirdWeight(new BigDecimal(StringUtil.trim(map.get(9 + ""))));
				
				// 三段首重单价
				subEntity.setThirdWeightPrice(new BigDecimal(StringUtil.trim(map.get(10 + ""))));
				
				// 三段续重单价
				subEntity.setThirdAddWeightPrice(new BigDecimal(StringUtil.trim(map.get(11 + ""))));
				
				// 运费优惠分段
				subEntity.setFreightSectionCode(sectionMap.get(StringUtil.trim(map.get(12 + ""))));
				
				if (repeatMap.containsKey(subKey)) {
					retMap.put("error", "数据重复：" + subKey);
					break;
				}
				repeatMap.put(subKey, subKey);
				succCount++;
			} catch (Exception e) {
				subEntity = null;
				failCount++;
				log.error("批量导入客户价格明细异常，业务需要仅作提示", e);
				retMap.put("error", "数据：" + subKey + ";导入出现异常");
			} finally {
				if (retMap.get("error") != null) {
					break;
				}
				priceCustomerSubList.add(subEntity);
			}
			retMap.put("succCount", succCount);
			retMap.put("failCount", failCount);
			retMap.put("list", priceCustomerSubList);
		}
		return retMap;
	}

	@Override
	public void batchUpdateForEffectiveByParentId(Map<String, Object> paramMap) {
		priceCustomerSectionSubDao.batchUpdateForEffectiveByParentId(paramMap);

	}

	@Override
	public List<PriceCustomerSectionSubEntity> selectCustomerSubListByParam(PriceCustomerSectionSubEntity tmpSubObj) {
		return priceCustomerSectionSubDao.listCustomerSubListByParam(tmpSubObj);
	}

	/**
	 * 客户专属价格明细之前的处理工作： 增加id，时间，active
	 * 
	 * @param
	 * @Author dengyin
	 * @Time 2015年12月17日上午10:22:19
	 */
	public String beforOperDeal(PriceCustomerSectionSubEntity priceCustomerSectionSubEntity) {

		String id = null;
		Date dt = new Date();
		id = UUIDUtil.getUUID();
		priceCustomerSectionSubEntity.setId(id);
		priceCustomerSectionSubEntity.setActive(MiserConstants.YES);
		priceCustomerSectionSubEntity.setCreateTime(dt);
		
		//当从 销售合同对接的 PMS 接口调用过来时 是没有会话的 因此不能那样取值
		if(priceCustomerSectionSubEntity.getCreateUserCode() == null || "".equals(priceCustomerSectionSubEntity.getCreateUserCode())){
			priceCustomerSectionSubEntity.setCreateUserCode((MiserUserContext.getCurrentUser().getUserName()));	
		}
		

		priceCustomerSectionSubEntity.setModifyTime(dt);
		
		if(priceCustomerSectionSubEntity.getModifyUserCode() == null || "".equals(priceCustomerSectionSubEntity.getModifyUserCode())){
			priceCustomerSectionSubEntity.setModifyUserCode(MiserUserContext.getCurrentUser().getUserName());
		}
		
		
		priceCustomerSectionSubEntity.setDataOrign(DataOrignEnum.PMS.getCode());

		return id;
	}
	
}

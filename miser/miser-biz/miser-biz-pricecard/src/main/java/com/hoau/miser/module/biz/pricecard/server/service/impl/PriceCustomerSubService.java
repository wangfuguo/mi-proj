/**   
 * @Title: PriceCustomerService.java 
 * @Package com.hoau.miser.module.biz.pricecard.api.server.service.impl 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月5日 下午1:42:29 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.server.service.impl;

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
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerSubService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerSubVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionVo;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceCustomerSubDao;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 客户专属价格接口实现 ClassName: PriceCustomerService
 * 
 * @author dengyin
 * @date 2016年1月5日
 * @version V1.0
 */
@Service
public class PriceCustomerSubService implements IPriceCustomerSubService {

	private static final Logger log = LoggerFactory
			.getLogger(PriceCustomerSubService.class);

	@Resource
	private PriceCustomerSubDao priceCustomerSubDao;
	@Resource
	private IPriceSectionService priceSectionService;
	@Resource
	private IPriceCityService priceCityService;
	@Resource
	private ITranstypeService transtypeService;
	
	@Resource
	private IPriceCustomerService priceCustomerService;

	/**
	 * 新增 客户专属价格明细
	 */
	@Transactional
	public String insertSelective(PriceCustomerSubEntity priceCustomerSubEntity) {
		String id = beforOperDeal(priceCustomerSubEntity);
		priceCustomerSubDao.insertSelective(priceCustomerSubEntity);
		return id;
	}

	/**
	 * 客户专属价格明细之前的处理工作： 增加id，时间，active
	 * 
	 * @param
	 * @Author dengyin
	 * @Time 2015年12月17日上午10:22:19
	 */
	public String beforOperDeal(PriceCustomerSubEntity priceCustomerSubEntity) {

		String id = null;
		Date dt = new Date();
		id = UUIDUtil.getUUID();
		priceCustomerSubEntity.setId(id);
		priceCustomerSubEntity.setActive(MiserConstants.YES);
		priceCustomerSubEntity.setCreateTime(dt);
		
		//当从 销售合同对接的 PMS 接口调用过来时 是没有会话的 因此不能那样取值
		if(priceCustomerSubEntity.getCreateUserCode() == null || "".equals(priceCustomerSubEntity.getCreateUserCode())){
			priceCustomerSubEntity.setCreateUserCode((MiserUserContext.getCurrentUser().getUserName()));	
		}
		

		priceCustomerSubEntity.setModifyTime(dt);
		
		if(priceCustomerSubEntity.getModifyUserCode() == null || "".equals(priceCustomerSubEntity.getModifyUserCode())){
			priceCustomerSubEntity.setModifyUserCode(MiserUserContext.getCurrentUser().getUserName());
		}
		
		
		priceCustomerSubEntity.setDataOrign(DataOrignEnum.PMS.getCode());

		return id;
	}

	@Override
	public List<PriceCustomerSubEntity> selectCustomerSubListByParam(
			PriceCustomerSubEntity priceCustomerSubEntity, int limit, int start) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		//add by dengyin 2016-4-11 11:38:16 解决查询明细时缓慢 增加分区
		//根据parentId查询出主表记录的创建时间
		PriceCustomerEntity tmpObj = new PriceCustomerEntity();
		tmpObj.setId(priceCustomerSubEntity.getParentId());
		
		String partitionName = "PRICE_CUS_SUBZ_PART";
		List<PriceCustomerEntity> priceCustomerEntityList = priceCustomerService.queryPriceCustomerMixed(tmpObj, 1, 0);
		PriceCustomerEntity targetObj = priceCustomerEntityList.get(0);
	 
		Date curCreateTime = targetObj.getCreateTime();
		
		String curCreateTimeStr = format.format(curCreateTime);

		int year = Integer.valueOf(curCreateTimeStr.split("-")[0].substring(2, 4));
		int month = Integer.valueOf(curCreateTimeStr.split("-")[1]);
		
		partitionName += year;
		
		if(month > 6){
			partitionName = partitionName + "12";
		}
		else {
			partitionName = partitionName + "06";
		}
		
		priceCustomerSubEntity.setPartitionName(partitionName);
		
		//end by dengyin 2016-4-11 11:38:16 解决查询明细时缓慢 增加分区
		
		List<PriceCustomerSubEntity> resultList = priceCustomerSubDao.listCustomerSubListByParam(priceCustomerSubEntity);
		
		return resultList;
	}

	@Transactional
	public int updateByPrimaryKeySelective(
			PriceCustomerSubEntity priceCustomerSubEntity) {
		return priceCustomerSubDao
				.updateByPrimaryKeySelective(priceCustomerSubEntity);
	}

	@Transactional
	public int batchUpdateActiveByIdStr(PriceCustomerSubVo priceCustomerSubVo) {
		return priceCustomerSubDao.batchUpdateActiveByIdStr(priceCustomerSubVo);
	}

	public void batchInsertCustomerSub(
			List<PriceCustomerSubEntity> priceCustomerSubEntityList) {

		if (priceCustomerSubEntityList != null
				&& priceCustomerSubEntityList.size() > 0) {

			List<PriceCustomerSubEntity> tmpPriceCustomerSubEntityList = new ArrayList<PriceCustomerSubEntity>();

			for (int i = 0; i < priceCustomerSubEntityList.size(); i++) {
				PriceCustomerSubEntity curSubEntity = priceCustomerSubEntityList
						.get(i);
				beforOperDeal(curSubEntity);

				tmpPriceCustomerSubEntityList.add(curSubEntity);
				int tmpSize = tmpPriceCustomerSubEntityList.size();

				if (tmpSize % 500 == 0) {
					priceCustomerSubDao
							.batchInsertCustomerSub(tmpPriceCustomerSubEntityList);
					tmpPriceCustomerSubEntityList.clear();
				}
			}

			// 若上面不能整除 余下的记录在这里做一个批量提交
			if (tmpPriceCustomerSubEntityList.size() > 0) {
				priceCustomerSubDao
						.batchInsertCustomerSub(tmpPriceCustomerSubEntityList);
				tmpPriceCustomerSubEntityList.clear();
			}
		}
	}

	@Override
	public void batchUpdateForInvalidByParentId(Map<String, String> paramMap) {
		priceCustomerSubDao.batchUpdateForInvalidByParentId(paramMap);
	}

	/**
	 * @Description: 根据勾选的 客户专属记录的 id 拼接串 更新明细表的 active=N
	 *               modifyUserCode=currentUser modifyTime=sysdate
	 * @param @param paramMap[modifyUserCode=currentUser
	 *        destoryIdStr=destoryIdStr]
	 * @return void
	 * @throws
	 * @author dengyin
	 * @date 2016年1月14日
	 */
	public void destoryPriceCustomerSubByParentIdStr(
			Map<String, Object> paramMap) {
		priceCustomerSubDao.destoryPriceCustomerSubByParentIdStr(paramMap);
	}

	public Map<String, Object> bathImplPriceCustomer(String path,String customerType)
			throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, String>> list;
		List<PriceCustomerSubEntity> priceCustomerSubList = new ArrayList<PriceCustomerSubEntity>();
		list = ExcelUtil.readToListByFile(path, 11, 2);
		// 运输类型
		Map<String, String> transTypeCodeMap = new HashMap<String, String>();
		Map<String, String> transTypeCodeMap2 = new HashMap<String, String>();

		TranstypeVo transtypeVo = new TranstypeVo();
		TranstypeEntity transtypeEntity = new TranstypeEntity();
		transtypeEntity.setActive(MiserConstants.YES);
		transtypeVo.setTranstypeEntity(transtypeEntity);
		List<TranstypeEntity> transtypeEntityList = transtypeService
				.queryTranstypes(transtypeVo);
		for (TranstypeEntity value : transtypeEntityList) {
			transTypeCodeMap.put(value.getName(), value.getCode());
			transTypeCodeMap2.put(value.getCode(), value.getName());
		}

		// 优惠分段
		PriceSectionVo psv = new PriceSectionVo();
		PriceSectionEntity sEntity = new PriceSectionEntity();
		sEntity.setActive(MiserConstants.YES);
		psv.setPriceSectionEntity(sEntity);
		List<PriceSectionEntity> sList = priceSectionService
				.queryPriceSection(psv);
		Map<String, String> sectionMap = new HashMap<String, String>();
		Map<String, String> sectionMap2 = new HashMap<String, String>();
		for (PriceSectionEntity entity : sList) {
			sectionMap.put(entity.getName(), entity.getCode());
			sectionMap2.put(entity.getCode(), entity.getName());
		}

		int succCount = 0;
		int failCount = 0;
		HashMap<String, String> repeatMap = new HashMap<String, String>();

        //缓存出发到达价格城市名称对应编码
        Map<String, String> sendPriceCityNameAndCodes = new HashMap<String, String>();
        Map<String, String> arrivalPriceCityNameAndCodes = new HashMap<String, String>();

		for (Map<String, String> map : list) {
			PriceCustomerSubEntity subEntity = new PriceCustomerSubEntity();
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
					retMap.put("error","重量单价不能为空");
					break;
				}				
				
				if(null == map.get(4 + "") || "".equals(map.get(4 + ""))){
					retMap.put("error","体积单价不能为空");
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
                    
                    
                    if ("Y".equals(customerType)) { //若为 新客户
                    	startPriceCityEntity.setPriceCityScope("STANDARD");
					} else if("N".equals(customerType)){  //若为老客户
						startPriceCityEntity.setPriceCityScope("CUSTOMER");
					}
                    
                    
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
                    
                    if ("Y".equals(customerType)) { //若为 新客户
                    	arrivalPriceCityEntity.setPriceCityScope("STANDARD");
					} else if("N".equals(customerType)){  //若为老客户
						arrivalPriceCityEntity.setPriceCityScope("CUSTOMER");
					}
                    
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
				
				if(null != map.get(7 + "") && !"".equals(map.get(7 + ""))){
					if (!sectionMap.containsKey(StringUtil.trim(map.get(7 + "")))) {
						retMap.put("error","不存在的燃油费分段:" + StringUtil.trim(map.get(7 + "")));
						break;
					}
				}
				
				if(null != map.get(8 + "") && ! "".equals(map.get(8 + ""))){
					if (!sectionMap.containsKey(StringUtil.trim(map.get(8 + "")))) {
						retMap.put("error","不存在的运费分段:" + StringUtil.trim(map.get(8 + "")));
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
				
				// 重量单价
				subEntity.setWeightPrice(new BigDecimal(StringUtil.trim(map.get(3 + ""))));
				
				// 体积单价
				subEntity.setVolumePrice(new BigDecimal(StringUtil.trim(map.get(4 + ""))));
				
				// 附加费 可以为空
				if(null != map.get(5 + "") && !"".equals(map.get(5 + "") )){
					subEntity.setAddFee(new BigDecimal(StringUtil.trim(map.get(5 + ""))));
				}
				
				// 最低收费 可以为空
				if(null != map.get(6 + "") && !"".equals(map.get(6 + "") )){
					subEntity.setLowestFee(new BigDecimal(StringUtil.trim(map.get(6 + ""))));
				}
				
				// 燃油费分段 可以为空
				if(null != map.get(7 + "") || !"".equals(map.get(7 + ""))){
					subEntity.setFuelFeeSection(sectionMap.get(StringUtil.trim(map.get(7 + ""))));
					subEntity.setFuelFeeSectionName(StringUtil.trim(map.get(7 + "")));
				}
				
				// 运费分段 可以为空
				if( null != map.get(8 + "") || !"".equals(map.get(8 + ""))){
					subEntity.setFreightFeeSection(sectionMap.get(StringUtil.trim(map.get(8 + ""))));
					subEntity.setFreightFeeSectionName(StringUtil.trim(map.get(8 + "")));
				}
				
				// 重量折扣 可以为空
				if(null != map.get(9 + "") && !"".equals(map.get(9 + ""))){
					subEntity.setWeightDiscount(new BigDecimal(StringUtil.trim(map.get(9 + ""))));
				}
				
				// 体积折扣 可以为空
				if(null != map.get(10 + "") && ! "".equals(map.get(10 + ""))){
					subEntity.setVolumeDiscount(new BigDecimal(StringUtil.trim(map.get(10 + ""))));
				}
				
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
	
	/**
	 * 针对于 生效中 的副表记录进行修改操作
	 * @param paramMap
	 */
	public void batchUpdateForEffectiveByParentId(Map<String,Object> paramMap){
		priceCustomerSubDao.batchUpdateForEffectiveByParentId(paramMap);
	}
	
	@Override
	public List<PriceCustomerSubEntity> selectCustomerSubListByParam(
			PriceCustomerSubEntity tmpSubObj) {
		return priceCustomerSubDao.listCustomerSubListByParam(tmpSubObj);
	}
}

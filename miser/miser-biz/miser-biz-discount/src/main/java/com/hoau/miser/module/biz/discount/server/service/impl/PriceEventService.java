package com.hoau.miser.module.biz.discount.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.entity.BaseEntity;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.miser.module.biz.base.api.server.service.IPriceCityService;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityVo;
import com.hoau.miser.module.biz.discount.api.shared.exception.MessageType;

import org.apache.ibatis.session.RowBounds;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.ITranstypeService;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;
import com.hoau.miser.module.biz.discount.api.server.service.IPriceEventService;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventCorpSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventCustomerSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventDiscountSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventOrderchannelSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventOrgEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventRouteDiscountEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventRouteSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEvnetEntity;
import com.hoau.miser.module.biz.discount.server.cache.PriceEventOrgCache;
import com.hoau.miser.module.biz.discount.server.dao.PriceEventCorpSubDao;
import com.hoau.miser.module.biz.discount.server.dao.PriceEventCustomerSubDao;
import com.hoau.miser.module.biz.discount.server.dao.PriceEventDiscountSubDao;
import com.hoau.miser.module.biz.discount.server.dao.PriceEventOrderchannelSubDao;
import com.hoau.miser.module.biz.discount.server.dao.PriceEventRouteSubDao;
import com.hoau.miser.module.biz.discount.server.dao.PriceEvnetDao;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionVo;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.base.api.server.service.IDataDictionaryValueService;
import com.hoau.miser.module.sys.base.api.server.service.IOrgAdministrativeInfoService;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryValueEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.OrgAdministrativeInfoEntity;
import com.hoau.miser.module.sys.base.api.shared.vo.OrgTreeNode;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.DateUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 优惠折扣Service ClassName: PriceEventService
 * 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
@Service
public class PriceEventService implements IPriceEventService {

	@Resource
	private PriceEvnetDao priceEvnetDao;
	@Resource
	private PriceEventCorpSubDao priceEventCorpSubDao;
	@Resource
	private PriceEventDiscountSubDao priceEventDiscountSubDao;
	@Resource
	private PriceEventOrderchannelSubDao priceEventOrderchannelSubDao;
	@Resource
	private PriceEventRouteSubDao priceEventRouteSubDao;
	@Resource
	private PriceEventCustomerSubDao priceEventCustomerSubDao;
	@Resource
	private IPriceCityService priceCityService;
	@Resource
	private IPriceSectionService priceSectionService;
	@Resource
	private IDataDictionaryValueService dataDictionaryValueService;
	@Resource
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	@Resource
	private ITranstypeService transtypeService;
	private static final String PRICECITYSCOPE = "STANDARD";
	/**
	 * 批量导入大小
	 */
	private final static int BATCH_SIZE = 100;

	public List<PriceEvnetEntity> queryListByParam(PriceEvnetEntity psv,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return priceEvnetDao.queryListByParam(psv, rowBounds);
	}

	@Transactional
	public void addPriceEvnet(PriceEvnetEntity psv) {
		Long minuteDiff = DateUtils.getMinuteDiff(psv.getEffectiveTime(), new Date());
		if (minuteDiff.longValue() > PriceEvnetEntity.minuteDiff) {
			//生效时间小于当前时间的话，直接设置为当前时间
			psv.setEffectiveTime(new Date());
		}
		psv.setEventCode(System.currentTimeMillis() + "");
		beforOperDeal(psv);
		insertChild(psv);
		this.priceEvnetDao.insertPriceEvnet(psv);
	}

	@Transactional
	public void updatePriceEvnet(PriceEvnetEntity psv) {
		Long minuteDiff = DateUtils.getMinuteDiff(psv.getEffectiveTime(), new Date());
		//生效时间小于当前时间时，修改生效时间为当前是时间
		if (minuteDiff.longValue() > PriceEvnetEntity.minuteDiff) {
			psv.setEffectiveTime(new Date());
		}
		// 只有生效中和待生效的数据才可以修改
		PriceEvnetEntity oldEvent = this.queryPriceEvnetById(psv.getId());
		if (oldEvent.getActive().equals(MiserConstants.NO)
				|| !(PriceEvnetEntity.STATE_2.equals(oldEvent.getState()) || PriceEvnetEntity.STATE_3
						.equals(oldEvent.getState()))) {
			// 状态发生变化
			throw new BusinessException(MessageType.PRICEEVENT_STATUS_CHANGED);
		}
		//待生效的数据直接作废
		if (PriceEvnetEntity.STATE_3.equals(oldEvent.getState())) {
			oldEvent.setActive(MiserConstants.NO);
		} else {
			//Chenyl @20160321 修改生效中信息的时候不需要将主表进行作废操作,只讲失效时间设置为修改后记录的开始时间
			oldEvent.setInvalidTime(psv.getEffectiveTime());
		}
		oldEvent.setIsForceColse(MiserConstants.NO);
		oldEvent.setModifyDate(new Date());
		oldEvent.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		this.priceEvnetDao.updatePriceEvnet(oldEvent);
		// 新增
		psv.setEventCode(System.currentTimeMillis() + "");
		// 修改历史id全部重新获取
		beforOperDeal(psv);
		insertChild(psv);
		this.priceEvnetDao.insertPriceEvnet(psv);
	}

	/**
	 * 
	 * @Description: 增加各子表
	 * @param @param pse
	 * @return void
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月7日
	 */
	private void beforOperDeal(PriceEvnetEntity pse) {
		Date dt = new Date();
		for (PriceEventCorpSubEntity childEntity : pse.getPriceEventCorpSubs()) {
			unionSetBaseData(childEntity, dt);
			childEntity.setActive(MiserConstants.YES);
			childEntity.setEventCode(pse.getEventCode());
		}
		for (PriceEventDiscountSubEntity childEntity : pse.getPriceEventDiscountSubs()) {
			unionSetBaseData(childEntity, dt);
			childEntity.setActive(MiserConstants.YES);
			childEntity.setEventCode(pse.getEventCode());
		}
		for (PriceEventOrderchannelSubEntity childEntity : pse.getPriceEventOrderchannelSubs()) {
			unionSetBaseData(childEntity, dt);
			childEntity.setActive(MiserConstants.YES);
			childEntity.setEventCode(pse.getEventCode());
		}
		for (PriceEventRouteSubEntity childEntity : pse.getPriceEventRouteSubs()) {
			unionSetBaseData(childEntity, dt);
			childEntity.setActive(MiserConstants.YES);
			childEntity.setEventCode(pse.getEventCode());
		}
		for (PriceEventCustomerSubEntity childEntity : pse.getPriceEventCustomerSubs()) {
			unionSetBaseData(childEntity, dt);
			childEntity.setActive(MiserConstants.YES);
			childEntity.setEventCode(pse.getEventCode());
		}

		unionSetBaseData(pse, dt);
		pse.setActive(MiserConstants.YES);
	}

	/**
	 * 统一设置默认值
	 * @param baseEntity
	 * @param oprDate
     */
	public void unionSetBaseData(BaseEntity baseEntity, Date oprDate) {
		baseEntity.setId(UUIDUtil.getUUID());
		baseEntity.setCreateDate(oprDate);
		baseEntity.setCreateUser(MiserUserContext.getCurrentUser().getUserName());
		baseEntity.setModifyDate(oprDate);
		baseEntity.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
	}

	public Long queryCountByParam(PriceEvnetEntity psv) {
		return priceEvnetDao.queryCountByParam(psv);
	}

	@Transactional
	public void delPriceEvnet(PriceEvnetEntity pse) {
		// 查询当前信息状态,如果状态不符合修改条件，给用户以提示
		PriceEvnetEntity selfEntity = this.queryPriceEvnetById(pse.getId());
		if (selfEntity.getActive().equals(MiserConstants.NO)
				|| !(PriceEvnetEntity.STATE_3.equals(selfEntity.getState()))) {
			throw new BusinessException(MessageType.PRICEEVENT_STATUS_CHANGED);
		}
		//待生效数据作废，不需要更新失效时间
		selfEntity.setActive(MiserConstants.NO);
		selfEntity.setIsForceColse(MiserConstants.NO);
		selfEntity.setModifyDate(new Date());
		selfEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		this.priceEvnetDao.updatePriceEvnet(selfEntity);
	}

	@Transactional
	public void stopPriceEvent(PriceEvnetEntity pse) {
		// 查询当前信息状态,如果状态不符合修改条件，给用户以提示
		PriceEvnetEntity selfEntity = this.queryPriceEvnetById(pse.getId());
		if (selfEntity.getActive().equals(MiserConstants.NO)
				|| !(PriceEvnetEntity.STATE_2.equals(selfEntity.getState()))) {
			throw new BusinessException(MessageType.PRICEEVENT_STATUS_CHANGED);
		}
		selfEntity.setActive(MiserConstants.YES);
		// 强制终止，生效中数据的作废，只需要更新失效时间，活动仍然有效，只是过期了
		selfEntity.setIsForceColse(MiserConstants.YES);
		selfEntity.setModifyDate(new Date());
		selfEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		selfEntity.setInvalidTime(new Date());
		this.priceEvnetDao.updatePriceEvnet(selfEntity);
	}

	/**
	 * 
	 * @Description: 增加各子表
	 * @param @param pse
	 * @return void
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月7日
	 */
	private void insertChild(PriceEvnetEntity pse) {
		// 增加各子表
		for (PriceEventCorpSubEntity childEntity : pse.getPriceEventCorpSubs()) {
			this.priceEventCorpSubDao.insertPriceEventCorpSub(childEntity);
		}
		for (PriceEventDiscountSubEntity childEntity : pse
				.getPriceEventDiscountSubs()) {
			this.priceEventDiscountSubDao
					.insertPriceEventDiscountSub(childEntity);
		}
		for (PriceEventOrderchannelSubEntity childEntity : pse
				.getPriceEventOrderchannelSubs()) {
			this.priceEventOrderchannelSubDao
					.insertPriceEventOrderchannelSub(childEntity);
		}
		for (PriceEventRouteSubEntity childEntity : pse
				.getPriceEventRouteSubs()) {
			this.priceEventRouteSubDao.insertPriceEventRouteSub(childEntity);
		}
		for (PriceEventCustomerSubEntity childEntity : pse
				.getPriceEventCustomerSubs()) {
			this.priceEventCustomerSubDao
					.insertPriceEventCustomerSub(childEntity);
		}
	}

	public PriceEvnetEntity queryPriceEvnetById(String id) {
		PriceEvnetEntity entity = new PriceEvnetEntity();
		entity.setId(id);
		List<PriceEvnetEntity> list = priceEvnetDao.queryPriceEvnetById(entity);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	public List<OrgTreeNode<PriceEventOrgEntity>> queryOrgByParentRes(
			PriceEventOrgEntity orgEntity) {
		ICache<String, List<OrgTreeNode<PriceEventOrgEntity>>> OrgCache = this.getOrgCache();
		List<OrgTreeNode<PriceEventOrgEntity>> entity = OrgCache.get(JSON.toJSONString(orgEntity));
		return entity;
	}
	@SuppressWarnings("unchecked")
	private ICache<String, List<OrgTreeNode<PriceEventOrgEntity>>> getOrgCache(){
		ICache<String, List<OrgTreeNode<PriceEventOrgEntity>>> roleCache = CacheManager.getInstance().getCache(PriceEventOrgCache.PRICE_EVENT_ORG_CACHE_UUID);
		return roleCache;
	}
	public Map<String, Object> impleAreaScope(Map<String, Object> param)
			throws Exception {

		if (param == null)
			return null;
		String path = (String) param.get("path");
		// 字典表-区域范围
		List<DataDictionaryValueEntity> corpTypeList = dataDictionaryValueService
				.queryParamByTermsCode("AREA_SCOPE");
		Map<String, String> corptypeMap = new HashMap<String, String>();
		for (DataDictionaryValueEntity value : corpTypeList) {
			corptypeMap.put(value.getValueName(), value.getValueCode());
		}
		// 行政组织
		List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoService
				.queryALLOrgAdministrativeInfo();
		Map<String, String> value_key_Map = new HashMap<String, String>();
		for (OrgAdministrativeInfoEntity entity : orgList) {
			value_key_Map.put(entity.getName(), entity.getCode());
		}

		List<Map<String, String>> list = ExcelUtil.readToListByFile(path, 2, 2);
		Map<String, Object> mapresult = new HashMap<String, Object>();
		List<PriceEventCorpSubEntity> lists = new ArrayList<PriceEventCorpSubEntity>();
		int sizeCount = 0;
		if (list.size() > 0) {
			// 选删除临时表
			priceEvnetDao.deleteCorpImportTemp();
			int batch = list.size() % BATCH_SIZE == 0 ? list.size()
					/ BATCH_SIZE : list.size() / BATCH_SIZE + 1;
			Map<String, Object> mapInsert = new HashMap<String, Object>();
			for (int i = 0; i < batch; i++) {
				List<List<Object>> batchValues = new ArrayList<List<Object>>();
				for (int j = BATCH_SIZE * i; j < BATCH_SIZE * (i + 1)
						&& j < list.size(); j++) {
					List<Object> values = new ArrayList<Object>();
					Map<String, String> map = list.get(j);
					if (!corptypeMap.containsKey(StringUtil.trim(map
							.get(0 + "")))
							|| !value_key_Map.containsKey(StringUtil.trim(map
									.get(1 + ""))))
						continue;
					sizeCount++;
					values.add(UUIDUtil.getUUID());
					values.add(corptypeMap.get(StringUtil.trim(map.get(0 + ""))));
					values.add(value_key_Map.get(StringUtil.trim(map
							.get(1 + ""))));
					batchValues.add(values);

					// 设置返回数据
					PriceEventCorpSubEntity entity = new PriceEventCorpSubEntity();
					String orgCode = value_key_Map.get(StringUtil.trim(map
							.get(1 + "")));
					entity.setActive(MiserConstants.YES);
					entity.setCorpType(corptypeMap.get(StringUtil.trim(map
							.get(0 + ""))));
					entity.setOrgCode(orgCode);
					lists.add(entity);
				}
				if (batchValues.size() > 0) {
					mapInsert.put("columnValues", batchValues);
					priceEvnetDao.tableImport(mapInsert);
				}
			}
		}
		mapresult.put("sizeCount", sizeCount);
		mapresult.put("sumSize", list.size());
		mapresult.put("lists", lists);
		// 重复数据清理
		priceEvnetDao.clearRepeatData();
		return mapresult;
	}

	public List<PriceEventRouteSubEntity> impleRouteSub(
			Map<String, Object> param) throws Exception {
		if (param == null)
			return null;
		String eventCode = (String) param.get("eventCode");
		String path = (String) param.get("path");
		List<Map<String, String>> list = ExcelUtil.readToListByFile(path, 2, 2);
		List<PriceEventRouteSubEntity> lists = new ArrayList<PriceEventRouteSubEntity>();

		// 缓存出发到达价格城市名称对应编码
		Map<String, String> sendPriceCityNameAndCodes = new HashMap<String, String>();
		Map<String, String> arrivalPriceCityNameAndCodes = new HashMap<String, String>();

		for (Map<String, String> map : list) {
			PriceEventRouteSubEntity entity = new PriceEventRouteSubEntity();
			entity.setActive(MiserConstants.YES);
			entity.setEventCode(eventCode);
			String sendPriceCityName = StringUtil.trim(map.get(0 + ""));
			if (sendPriceCityNameAndCodes.containsKey(sendPriceCityName)) {
				entity.setSendPriceCity(sendPriceCityNameAndCodes
						.get(sendPriceCityName));
				entity.setSendPriceCityName(sendPriceCityName);
			} else {
				// 根据名称从接口获取出发价格城市编码
				PriceCityEntity startPriceCityEntity = new PriceCityEntity();
				startPriceCityEntity.setName(sendPriceCityName);
				startPriceCityEntity.setType("SEND");
				startPriceCityEntity.setPriceCityScope(PRICECITYSCOPE);
				PriceCityVo startPriceCityVo = new PriceCityVo();
				startPriceCityVo.setQueryParam(startPriceCityEntity);
				PriceCityEntity startPriceCity = priceCityService
						.queryPriceCityByName(startPriceCityVo);
				if (startPriceCity != null) {
					sendPriceCityNameAndCodes.put(sendPriceCityName,
							startPriceCity.getCode());
					entity.setSendPriceCity(startPriceCity.getCode());
					entity.setSendPriceCityName(sendPriceCityName);
				}
			}
			String arrivalPriceCityName = map.get(1 + "");
			if (arrivalPriceCityNameAndCodes.containsKey(arrivalPriceCityName)) {
				entity.setArrivalPriceCity(arrivalPriceCityNameAndCodes
						.get(arrivalPriceCityName));
				entity.setArrivalPriceCityName(arrivalPriceCityName);
			} else {
				// 根据名称从接口获取到达价格城市编码
				PriceCityEntity arrivalPriceCityEntity = new PriceCityEntity();
				arrivalPriceCityEntity.setName(arrivalPriceCityName);
				arrivalPriceCityEntity.setType("ARRIVAL");
				arrivalPriceCityEntity.setPriceCityScope(PRICECITYSCOPE);
				PriceCityVo arrivalPriceCityVo = new PriceCityVo();
				arrivalPriceCityVo.setQueryParam(arrivalPriceCityEntity);
				PriceCityEntity arrivalPriceCity = priceCityService
						.queryPriceCityByName(arrivalPriceCityVo);
				if (arrivalPriceCity != null) {
					arrivalPriceCityNameAndCodes.put(arrivalPriceCityName,
							arrivalPriceCity.getCode());
					entity.setArrivalPriceCity(arrivalPriceCity.getCode());
					entity.setArrivalPriceCityName(arrivalPriceCityName);
				}
			}
			lists.add(entity);
		}
		return lists;
	}

	public List<PriceEventDiscountSubEntity> impleDiscountSub(
			Map<String, Object> param) throws Exception {
		if (param == null)
			return null;
		String eventCode = (String) param.get("eventCode");
		String path = (String) param.get("path");
		List<Map<String, String>> list = ExcelUtil
				.readToListByFile(path, 13, 2);
		List<PriceEventDiscountSubEntity> lists = new ArrayList<PriceEventDiscountSubEntity>();
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
		for (Map<String, String> map : list) {
			PriceEventDiscountSubEntity entity = new PriceEventDiscountSubEntity();
			entity.setActive(MiserConstants.YES);
			entity.setEventCode(eventCode);
			// 运输类型
			entity.setTransTypeCode(transTypeCodeMap.get(map.get(0 + "")));
			// 运费分段折扣编码 FREIGHT_SECTION_CODE
			if (sectionMap.get(map.get(1 + "")) != null
					&& !"".equals(sectionMap.get(map.get(1 + "")))) {
				entity.setFreightSectionCode(sectionMap.get(map.get(1 + "")));
				entity.setFreightSectionName(map.get(1 + ""));
			}
			// 附加费分段折扣编码 ADD_SECTION_CODE
			if (sectionMap.get(map.get(2 + "")) != null
					&& !"".equals(sectionMap.get(map.get(2 + "")))) {
				entity.setAddSectionCode(sectionMap.get(map.get(2 + "")));
				entity.setAddSectionName(map.get(2 + ""));
			}
			// 燃油费分段折扣编码 FUEL_SECTION_CODE
			if (sectionMap.get(map.get(3 + "")) != null
					&& !"".equals(sectionMap.get(map.get(3 + "")))) {
				entity.setFuelSectionCode(sectionMap.get(map.get(3 + "")));
				entity.setFuelSectionName(map.get(3 + ""));
			}
			// 提货费分段折扣编码 PICKUP_SECTION_CODE
			if (sectionMap.get(map.get(4 + "")) != null
					&& !"".equals(sectionMap.get(map.get(4 + "")))) {
				entity.setPickupSectionCode(sectionMap.get(map.get(4 + "")));
				entity.setPickupSectionName(map.get(4 + ""));
			}
			// 送货费分段折扣编码 DELIVERY_SECTION_CODE
			if (sectionMap.get(map.get(5 + "")) != null
					&& !"".equals(sectionMap.get(map.get(5 + "")))) {
				entity.setDeliverySectionCode(sectionMap.get(map.get(5 + "")));
				entity.setDeliverySectionName(map.get(5 + ""));
			}
			// 上楼费分段折扣编码 UPSTAIR_SECTION_CODE
			if (sectionMap.get(map.get(6 + "")) != null
					&& !"".equals(sectionMap.get(map.get(6 + "")))) {
				entity.setUpstairSectionCode(sectionMap.get(map.get(6 + "")));
				entity.setUpstairSectionName(map.get(6 + ""));
			}
			// 保价率分段折扣编码 INSURANCE_RATE_SECTION_CODE
			if (sectionMap.get(map.get(7 + "")) != null
					&& !"".equals(sectionMap.get(map.get(7 + "")))) {
				entity.setInsuranceRateSectionCode(sectionMap.get(map
						.get(7 + "")));
				entity.setInsuranceRateSectionName(map.get(7 + ""));
			}
			// 保价费分段折扣编码 INSURANCE_SECTION_CODE
			if (sectionMap.get(map.get(8 + "")) != null
					&& !"".equals(sectionMap.get(map.get(8 + "")))) {
				entity.setInsuranceSectionCode(sectionMap.get(map.get(8 + "")));
				entity.setInsuranceSectionName(map.get(8 + ""));
			}
			// 工本费分段折扣编码 PAPER_SECTION_CODE
			if (sectionMap.get(map.get(9 + "")) != null
					&& !"".equals(sectionMap.get(map.get(9 + "")))) {
				entity.setPaperSectionCode(sectionMap.get(map.get(9 + "")));
				entity.setPaperSectionName(map.get(9 + ""));
			}
			// 信息费分段折扣编码 SMS_SECTION_CODE
			if (sectionMap.get(map.get(10 + "")) != null
					&& !"".equals(sectionMap.get(map.get(10 + "")))) {
				entity.setSmsSectionCode(sectionMap.get(map.get(10 + "")));
				entity.setSmsSectionName(map.get(10 + ""));
			}
			// 代收手续费率分段折扣编码 COLLECTION_RATE_SECTION_CODE
			if (sectionMap.get(map.get(11 + "")) != null
					&& !"".equals(sectionMap.get(map.get(11 + "")))) {
				entity.setCollectionRateSectionCode(sectionMap.get(map
						.get(11 + "")));
				entity.setCollectionRateSectionName(map.get(11 + ""));
			}
			// 代收手续费分段折扣编码 COLLECTION_SECTION_CODE
			if (sectionMap.get(map.get(12 + "")) != null
					&& !"".equals(sectionMap.get(map.get(12 + "")))) {
				entity.setCollectionSectionCode(sectionMap.get(map.get(12 + "")));
				entity.setCollectionSectionName(map.get(12 + ""));
			}
			// 运输类型
			entity.setTransTypeName(map.get(0 + ""));
			lists.add(entity);
		}
		return lists;
	}

	public void deleteCorpImportTemp() {
		priceEvnetDao.deleteCorpImportTemp();
	}

	public List<PriceEventDiscountSubEntity> queryPDESubListByParam(
			String parentId, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		PriceEventDiscountSubEntity priceEventDiscountSubEntity = new PriceEventDiscountSubEntity();
		priceEventDiscountSubEntity.setEventCode(parentId);
		return priceEventDiscountSubDao.selectPEDSubListByParam(
				priceEventDiscountSubEntity, rowBounds);
	}

	public List<PriceEventRouteSubEntity> queryLineSubListByParam(
			String parentId, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		PriceEventRouteSubEntity eventRouteSubEntity = new PriceEventRouteSubEntity();
		eventRouteSubEntity.setEventCode(parentId);
		return priceEventRouteSubDao.selectLineSubListByParam(
				eventRouteSubEntity, rowBounds);
	}
	
	/**
	 * @Description:根据实体查询优惠内容
	 * @throws
	 * @author dengyin
	 * @date 2016-4-26 11:00:49
	 */
	public List<PriceEventDiscountSubEntity> queryEventDiscountSubListByParam(PriceEventDiscountSubEntity entity){
		RowBounds rowBounds = new RowBounds(0, 100);
		return priceEventDiscountSubDao.selectPEDSubListByParam(entity, rowBounds);
	}
	
	/**
	 * add by dengyin 2016-5-2 17：01：23  价格时效查询
	 * @param entity
	 * @return
	 */
	public List<PriceEventRouteDiscountEntity> queryEventRouteDiscount(PriceEventRouteDiscountEntity entity){
		return priceEventDiscountSubDao.queryEventRouteDiscount(entity);
	}
}

package com.hoau.miser.module.biz.pricecard.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

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
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceStandardSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceStandardSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardSectionVo;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceStandardDao;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceStandardSectionDao;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.DateUtils;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @ClassName: PriceStandardSectionService
 * @Description: 易入户标准价格管理Service
 * @author zhangqingfu
 * @date 2016年5月4日 下午4:05:49
 *
 */
@Service
public class PriceStandardSectionService implements IPriceStandardSectionService {
	
	private static final Logger log = LoggerFactory.getLogger(PriceStandardSectionService.class);

	@Resource
	PriceStandardSectionDao priceStandardSectionDao;
	@Resource
	private IPriceCityService priceCityService;
	@Resource
	private ITranstypeService transtypeService;
	@Resource
	private PriceStandardDao priceStandardDao;

	/*
	 * <p>Title: queryPageByCondition</p>
	 * <p>Description: </p>
	 * @param vo
	 * @param limit
	 * @param start
	 * @return
	 * @see com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardSectionService#queryPageByCondition(com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardSectionVo, int, int)
	 */
	@Override
	public List<PriceStandardSectionEntity> queryPageByCondition(PriceStandardSectionVo vo, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return priceStandardSectionDao.queryPageByCondition(vo, rowBounds);
	}

	/**
	 * @Title: addOrUpdatePriceStandardSection
	 * @Description: 新增或修改易入户标准价格 1、id存在则修改 2、不存在则新增
	 * @return Integer 操作状态
	 * @param entity
	 *            易入户标准价格
	 */
	public Integer addOrUpdatePriceStandardSection(PriceStandardSectionEntity entity,String user,Date dt) {
		Integer i = 0;
		if (null != entity) {
			entity.setModifyDate(dt);
			entity.setModifyUser(user);
			if (StringUtil.isEmpty(entity.getId())) {// 新增
				entity.setId(UUIDUtil.getUUID());
				entity.setActive(MiserConstants.YES);
				entity.setCreateDate(dt);
				entity.setCreateUser(user);
				i = priceStandardSectionDao.addPriceStandardSection(entity);
			} else {// 修改
				i = priceStandardSectionDao.updatePriceStandardSection(entity);
			}
		}
		return i;
	}

	/**
	 * @Title: add
	 * @Description: 新增一条记录
	 * @return int
	 * @param entity
	 */
	Integer add(PriceStandardSectionEntity entity,String user,Date dt) {
		return this.addOrUpdatePriceStandardSection(entity,user,dt);
	}

	/**
	 * @Title: updateAndAdd
	 * @Description: 修改已生效的记录，新增一条新记录 1、新增时，如果存在有效的相同记录（运输类型，出发价格城市，到达价格城市一致）
	 *               且该记录状态为生效中 则延续该记录：修改当前记录的失效时间为新增记录的生效时间
	 *               2、新增时，如果存在有效的相同记录（运输类型，出发价格城市，到达价格城市一致） 且该记录状态为待生效 则作废该记录
	 *               3、修改时，如果该记录状态为生效中，则延续该记录 4、修改时，如果该记录状态为待生效，则作废该记录
	 *               5、修改时，如果同时存在一条待生效记录，作废待生效记录，并延续当前记录
	 * @return int
	 * @param list
	 */
	Integer updateAndAdd(List<PriceStandardSectionEntity> list,String user,Date dt) {
		Integer i = 0;
		Date temp = null;
		for (PriceStandardSectionEntity entity : list) {
			if (StringUtil.isEmpty(entity.getId())) {// 待新增的记录
				temp = entity.getEffectiveTime();
			}
			if ("WAIT".equals(entity.getState())) {// 待生效的记录
				entity.setActive(MiserConstants.NO);// 作废
			}
			if ("EFFECTIVE".equals(entity.getState())) {// 已生效的记录
				entity.setInvalidTime(temp);// 延续下一条记录
			}
			i += this.addOrUpdatePriceStandardSection(entity,user,dt);
		}
		return i;
	}

	/*
	 * <p>Title: update</p>
	 * <p>Description: </p>
	 * @param vo
	 * @param user
	 * @param dt
	 * @return
	 * @see com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardSectionService#update(com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardSectionVo, java.lang.String, java.util.Date)
	 */
	@Transactional
	@Override
	public Integer update(PriceStandardSectionVo vo,String user,Date dt) {
		int i = 0;
		List<PriceStandardSectionEntity> list = vo.getPriceStandardSectionList();
		if (null != list && list.size() > 1) {
			i = this.updateAndAdd(list,user,dt);
		} else {
			i = this.add(vo.getPriceStandardSectionEntity(),user,dt);
		}
		return i;
	}

	/*
	 * <p>Title: delete</p>
	 * <p>Description: </p>
	 * @param vo
	 * @param user
	 * @param dt
	 * @return
	 * @see com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardSectionService#delete(com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardSectionVo, java.lang.String, java.util.Date)
	 */
	@Transactional
	@Override
	public Integer delete(PriceStandardSectionVo vo,String user,Date dt) {
		Integer i = 0;
		List<PriceStandardSectionEntity> list = vo.getPriceStandardSectionList();
		if (null != list && list.size() > 0) {
			for (PriceStandardSectionEntity entity : list) {
				entity.setActive(MiserConstants.NO); // 是否有效
				i += this.addOrUpdatePriceStandardSection(entity,user,dt);
			}
		}
		return i;
	}

	/*
	 * <p>Title: queryById</p>
	 * <p>Description: </p>
	 * @param vo
	 * @return
	 * @see com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardSectionService#queryById(com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardSectionVo)
	 */
	@Override
	public PriceStandardSectionEntity queryById(PriceStandardSectionVo vo) {
		PriceStandardSectionEntity entity = vo.getPriceStandardSectionEntity();
		return null != entity ? this.priceStandardSectionDao.queryById(entity.getId()) : null;
	}

	/*
	 * <p>Title: checkRecord</p>
	 * <p>Description: </p>
	 * @param vo
	 * @return
	 * @see com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardSectionService#checkRecord(com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardSectionVo)
	 */
	@Override
	public List<PriceStandardSectionEntity> checkRecord(PriceStandardSectionVo vo) {
		PriceStandardSectionEntity entity = vo.getPriceStandardSectionEntity();
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != entity) {
			map.put("transTypeCode", entity.getTransTypeCode());
			map.put("sendPriceCity", entity.getSendPriceCity().getCode());
			map.put("arrivePriceCity", entity.getArrivePriceCity().getCode());
			map.put("active", MiserConstants.YES);
		}
		return this.priceStandardSectionDao.queryActive(map);
	}

	/*
	 * <p>Title: queryCountByCondition</p>
	 * <p>Description: </p>
	 * @param vo
	 * @return
	 * @see com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardSectionService#queryCountByCondition(com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardSectionVo)
	 */
	@Override
	public long queryCountByCondition(PriceStandardSectionVo vo) {
		return priceStandardSectionDao.queryCountByCondition(vo);
	}

	/*
	 * <p>Title: export</p>
	 * <p>Description: </p>
	 * @param vo
	 * @return
	 * @throws IOException
	 * @see com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardSectionService#export(com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardSectionVo)
	 */
	@Override
	public ExcelExportResultEntity export(PriceStandardSectionVo vo) throws IOException {
		if (vo == null || vo.getPriceStandardSectionEntity() == null) {
			return null;
		}
		List<String> titleNames = Arrays.asList("出发价格城市", "到达价格城市", "运输类型", "一段首重", "一段首重单价", "一段续重单价", "二段首重",
				"二段首重单价", "二段续重单价", "三段首重", "三段首重单价", "三段续重单价", "生效时间", "失效时间", "当前状态", "备注", "创建人", "创建时间", "最后修改人",
				"最后修改时间");
		//查询参数
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" SENDCITY.NAME AS sendPriceCityName, ");
		sql.append(" ARRIVECITY.NAME AS arrivePriceCityName,");
		sql.append(" pss.TRANS_TYPE_NAME as transTypeName,");
		sql.append(" pss.FIRST_WEIGHT as firstWeight,");
		sql.append(" pss.FIRST_WEIGHT_PRICE as firstWeightPrice,");
		sql.append(" pss.FIRST_ADD_WEIGHT_PRICE as firstAddWeightPrice,");
		sql.append(" pss.SECOND_WEIGHT as secondWeight,");
		sql.append(" pss.SECOND_WEIGHT_PRICE as secondWeightPrice,");
		sql.append(" pss.SECOND_ADD_WEIGHT_PRICE as secondAddWeightPrice,");
		sql.append(" pss.THIRD_WEIGHT as thirdWeight,");
		sql.append(" pss.THIRD_WEIGHT_PRICE as thirdWeightPrice,");
		sql.append(" pss.THIRD_ADD_WEIGHT_PRICE as thirdAddWeightPrice,");
		sql.append(" pss.EFFECTIVE_TIME as effectiveTime,");
		sql.append(" pss.INVALID_TIME as invalidTime,");
		sql.append(" CASE WHEN pss.ACTIVE='N' THEN '已废弃' ");
		sql.append(" WHEN pss.INVALID_TIME IS NOT NULL AND pss.INVALID_TIME < SYSDATE THEN '已过期'  ");
		sql.append(" WHEN pss.EFFECTIVE_TIME > SYSDATE THEN '待生效' ");
		sql.append(" ELSE '生效中' END AS stateName, ");
		sql.append(" pss.remark,");
		sql.append(" pss.CREATE_USER_CODE as createUser,");
		sql.append(" pss.CREATE_TIME as createDate,");
		sql.append(" pss.MODIFY_USER_CODE as modifyUser,");
		sql.append(" pss.MODIFY_TIME as modifyDate");
		sql.append(" FROM");
		sql.append(" T_PRICE_STANDARD_SECTION pss");
		sql.append(" LEFT JOIN");
		sql.append(" T_PRICE_CITY sendcity ON");
		sql.append(" pss.SEND_PRICE_CITY = sendcity.CODE");
		sql.append(" and sendcity.ACTIVE = 'Y'");
		sql.append(" LEFT JOIN");
		sql.append(" T_PRICE_CITY arrivecity ON");
		sql.append(" pss.ARRIVE_PRICE_CITY = arrivecity.CODE");
		sql.append(" and arrivecity.ACTIVE = 'Y'");
		sql.append(" WHERE");
		StringBuffer filter = new StringBuffer();
		// 出发价格城市查询条件
		if (vo.getPriceStandardSectionEntity().getSendPriceCity() != null
				&& StringUtil.isNotEmpty(vo.getPriceStandardSectionEntity().getSendPriceCity().getCode())) {
			filter.append(" AND PSS.SEND_PRICE_CITY = ?");
			params.add(vo.getPriceStandardSectionEntity().getSendPriceCity().getCode());
		}
		// 出发省市区查询条件
		if (StringUtil.isNotEmpty(vo.getSendProvinceCode()) || StringUtil.isNotEmpty(vo.getSendCityCode())
				|| StringUtil.isNotEmpty(vo.getSendAreaCode())) {
			filter.append(" AND PSS.SEND_PRICE_CITY in (select PRICE_CITY from T_PRICE_CITY_MAPPING_DISTRICT WHERE ACTIVE='Y'");
			if(StringUtil.isNotEmpty(vo.getSendProvinceCode())){
				filter.append(" AND PROVINCE_CODE = ? ");
				params.add(vo.getSendProvinceCode());
			}
			if(StringUtil.isNotEmpty(vo.getSendCityCode())){
				filter.append(" AND CITY_CODE = ? ");
				params.add(vo.getSendCityCode());
			}
			if(StringUtil.isNotEmpty(vo.getSendAreaCode())){
				filter.append(" AND AREA_CODE = ? ");
				params.add(vo.getSendAreaCode());
			}
			filter.append(" GROUP BY PRICE_CITY) ");
		}
		// 到达价格城市查询 条件
		if (vo.getPriceStandardSectionEntity().getArrivePriceCity() != null
				&& StringUtil.isNotEmpty(vo.getPriceStandardSectionEntity().getArrivePriceCity().getCode())) {
			filter.append(" AND PSS.ARRIVE_PRICE_CITY = ?");
			params.add(vo.getPriceStandardSectionEntity().getArrivePriceCity().getCode());
		}
		// 到达省市区查询条件
		if (StringUtil.isNotEmpty(vo.getArriveProvinceCode()) || StringUtil.isNotEmpty(vo.getArriveCityCode())
				|| StringUtil.isNotEmpty(vo.getArriveAreaCode())) {
			filter.append(" AND PSS.ARRIVE_PRICE_CITY in (select PRICE_CITY from T_PRICE_CITY_MAPPING_DISTRICT WHERE ACTIVE='Y'");
			if(StringUtil.isNotEmpty(vo.getArriveProvinceCode())){
				filter.append(" AND PROVINCE_CODE = ? ");
				params.add(vo.getArriveProvinceCode());
			}
			if(StringUtil.isNotEmpty(vo.getArriveCityCode())){
				filter.append(" AND CITY_CODE = ? ");
				params.add(vo.getArriveCityCode());
			}
			if(StringUtil.isNotEmpty(vo.getArriveAreaCode())){
				filter.append(" AND AREA_CODE = ? ");
				params.add(vo.getArriveAreaCode());
			}
			filter.append(" GROUP BY PRICE_CITY) ");
		}
		// 状态查询条件
		if(StringUtil.isEmpty(vo.getPriceStandardSectionEntity().getState())){
			filter.append(" AND PSS.active='Y'");
		}else if("PASSED".equals(vo.getPriceStandardSectionEntity().getState())){
			filter.append(" AND SYSDATE>PSS.INVALID_TIME AND PSS.ACTIVE='Y' ");
		}else if("EFFECTIVE".equals(vo.getPriceStandardSectionEntity().getState())){
			filter.append(" AND (SYSDATE>=PSS.EFFECTIVE_TIME AND NVL(PSS.INVALID_TIME,TO_DATE('2999-12-31 23:59:59', 'yyyy-mm-dd hh24:mi:ss'))>=SYSDATE ");
			filter.append(" AND PSS.ACTIVE='Y' )");
		}else if("WAIT".equals(vo.getPriceStandardSectionEntity().getState())){
			filter.append(" AND SYSDATE<=PSS.EFFECTIVE_TIME AND PSS.ACTIVE='Y' ");
		}else {
			filter.append(" AND PSS.active='N' ");
		}
		// 产品类别查询条件
		if(StringUtil.isNotEmpty(vo.getPriceStandardSectionEntity().getTransTypeCode())){
			filter.append(" AND PSS.trans_type_code = ? ");
			params.add(vo.getPriceStandardSectionEntity().getTransTypeCode());
		}
		// 生效时间查询条件
		if(StringUtil.isNotEmpty(vo.getEffectiveTime())){
			filter.append(" AND PSS.EFFECTIVE_TIME <= TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') ");
			params.add(vo.getEffectiveTime());
			filter.append(" AND NVL(PSS.INVALID_TIME,TO_DATE('2999-12-31 23:59:59', 'yyyy-mm-dd hh24:mi:ss'))>=TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') ");
			params.add(vo.getEffectiveTime());
		}
		//处理filter开头的AND
		if (filter.toString().startsWith(" AND")) {
			sql.append(filter.substring(4));
		} else {
			sql.append(filter);
		}
		ExcelExportResultEntity resultEntity = new ExcelUtil<PriceStandardSectionEntity>().sqlTofile(titleNames, sql.toString(), params);
		return resultEntity;
	}
	
	/*
	 * <p>Title: importExcel</p>
	 * <p>Description: </p>
	 * @param path
	 * @return
	 * @throws Exception
	 * @see com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardSectionService#importExcel(java.lang.String)
	 */
	@Override
	@Transactional
	public Map<String, Object> importExcel(String path) throws Exception {
		Map<String,Object> retuMap=new HashMap<String, Object>();
		List<Map<String, String>> list;
		Date nowDate =  searchCurrentDateTime();
		List<PriceStandardSectionEntity> pseList = new ArrayList<PriceStandardSectionEntity>();
		List<ExcelPriceStandardSectionEntity> errorList = new ArrayList<ExcelPriceStandardSectionEntity>();
		list = ExcelUtil.readToListByFile(path, 15, 2);
		//运输类型
		Map<String,String> transTypeCodeMap=new HashMap<String, String>();
		TranstypeVo transtypeVo=new TranstypeVo();
		TranstypeEntity transtypeEntity=new TranstypeEntity();
		transtypeEntity.setActive(MiserConstants.YES);
		transtypeVo.setTranstypeEntity(transtypeEntity);
		List<TranstypeEntity> transtypeEntityList=transtypeService.queryTranstypes(transtypeVo);
		for(TranstypeEntity value:transtypeEntityList){
			transTypeCodeMap.put(value.getName(), value.getCode());
		}
		
		//缓存出发到达价格城市名称对应编码
		Map<String, String> sendPriceCityNameAndCodes = new HashMap<String, String>();
		for(PriceCityEntity  pce : priceCityService.queryAllStartStandardPriceCities()){
			sendPriceCityNameAndCodes.put( pce.getName(), pce.getCode());
		}
		Map<String, String> arrivalPriceCityNameAndCodes = new HashMap<String, String>();
		for(PriceCityEntity  pce : priceCityService.queryAllArrivalStandardPriceCities()){
			arrivalPriceCityNameAndCodes.put( pce.getName(), pce.getCode());
		}
		
		Hashtable<String, Long> countMap = new Hashtable<String, Long>();
		countMap.put("addSize", new Long(0));// 增加条数
		countMap.put("coverSize", new Long(0));// 覆盖条数
		countMap.put("errorSize",new Long(0));//错误的条数
		
		int temp = 1;
		for (Map<String, String> map : list) {
			PriceStandardSectionEntity pse = new PriceStandardSectionEntity();
			try {
				pse.setActive("Y");
				//根据名称从接口获取出发价格城市编码
                PriceCityEntity sendPriceCity = new PriceCityEntity();
                String sendPriceCityName = map.get("0") == null ? "" : map.get("0").trim();
                if (sendPriceCityNameAndCodes.containsKey(sendPriceCityName)) {
                    sendPriceCity.setCode(sendPriceCityNameAndCodes.get(sendPriceCityName));
                    sendPriceCity.setName(sendPriceCityName);
                } else {
                    throw new Exception("出发价格城市["+ sendPriceCityName +"]不存在");
                }
				
              //根据名称从接口获取到达价格城市编码
				PriceCityEntity arrivePriceCity = new PriceCityEntity();
                String arrivalPriceCityName = map.get("1") == null ? "" : map.get("1").trim();
                if (arrivalPriceCityNameAndCodes.containsKey(arrivalPriceCityName)) {
                    arrivePriceCity.setCode(arrivalPriceCityNameAndCodes.get(arrivalPriceCityName));
                    arrivePriceCity.setName(arrivalPriceCityName);
                } else {
                    throw new Exception("到达价格城市["+ arrivalPriceCityName +"]不存在");
                }
                
                String transTypeName = map.get(2+"")== null? "": map.get(2+"").trim();
                if(transTypeCodeMap.containsKey(transTypeName)){
                	pse.setTransTypeCode(transTypeCodeMap.get(transTypeName));
    				pse.setTransTypeName(transTypeName);
                }else{
                	throw new Exception("运输类型["+ transTypeName +"]不存在");
                }
                pse.setSendPriceCity(sendPriceCity);
				pse.setArrivePriceCity(arrivePriceCity);
                if(StringUtils.isEmpty(map.get(3+"")) || "0".equals(map.get(3+""))){
					throw new Exception("一段首重["+ map.get(3+"") +"]格式不正确,只能是数字类型且不为0");
				}
				try{
					pse.setFirstWeight(Double.valueOf(StringUtil.trim(map.get(3+""))));
				}catch(Exception ex){
					throw new Exception("一段首重["+ map.get(3+"") +"]格式不正确,只能是数字类型");
				}
				if(StringUtils.isEmpty(map.get(4+"")) || "0".equals(map.get(4+""))){
					throw new Exception("一段首重单价["+ map.get(4+"") +"]格式不正确,只能是数字类型且不为0");
				}
				try{
					pse.setFirstWeightPrice(Double.valueOf(StringUtil.trim(map.get(4+""))));
				}catch(Exception ex){
					throw new Exception("一段首重单价["+ map.get(4+"") +"]格式不正确,只能是数字类型");
				}
				if(StringUtils.isEmpty(map.get(5+"")) || "0".equals(map.get(5+""))){
					throw new Exception("一段续重单价["+ map.get(5+"") +"]格式不正确,只能是数字类型且不为0");
				}
				try{
					pse.setFirstAddWeightPrice(Double.valueOf(StringUtil.trim(map.get(5+""))));
				}catch(Exception ex){
					throw new Exception("一段续重单价["+ map.get(5+"") +"]格式不正确,只能是数字类型");
				}
				try{
					if(StringUtils.isEmpty(map.get(6+""))){
						pse.setSecondWeight(null);
					}else{
						pse.setSecondWeight(Double.valueOf(StringUtil.trim(map.get(6+""))));
					}
				}catch(Exception ex){
					throw new Exception("二段首重["+ map.get(6+"") +"]格式不正确,只能是数字类型");
				}
				try{
					if(StringUtils.isEmpty(map.get(7+""))){
						pse.setSecondWeightPrice(null);
					}else{
						pse.setSecondWeightPrice(Double.valueOf(StringUtil.trim(map.get(7+""))));
					}
				}catch(Exception ex){
					throw new Exception("二段首重单价["+ map.get(7+"") +"]格式不正确,只能是数字类型");
				}
				try{
					if(StringUtils.isEmpty(map.get(8+""))){
						pse.setSecondAddWeightPrice(null);
					}else{
						pse.setSecondAddWeightPrice(Double.valueOf(StringUtil.trim(map.get(8+""))));
					}
				}catch(Exception ex){
					throw new Exception("二段续重单价["+ map.get(8+"") +"]格式不正确,只能是数字类型");
				}
				if(!checkWeightAndPrice(pse.getSecondWeight(), pse.getSecondWeightPrice(), pse.getSecondWeightPrice())){
					throw new Exception("二段首重不为0,二段首重单价、二段续重单价也不能为0");
				}
				try{
					if(StringUtils.isEmpty(map.get(9+""))){
						pse.setThirdWeight(null);
					}else{
						pse.setThirdWeight(Double.valueOf(StringUtil.trim(map.get(9+""))));
					}
				}catch(Exception ex){
					throw new Exception("三段首重["+ map.get(9+"") +"]格式不正确,只能是数字类型");
				}
				try{
					if(StringUtils.isEmpty(map.get(10+""))){
						pse.setThirdWeightPrice(null);
					}else{
						pse.setThirdWeightPrice(Double.valueOf(StringUtil.trim(map.get(10+""))));
					}
				}catch(Exception ex){
					throw new Exception("三段首重单价["+ map.get(10+"") +"]格式不正确,只能是数字类型");
				}
				try{
					if(StringUtils.isEmpty(map.get(10+""))){
						pse.setThirdAddWeightPrice(null);
					}else{
						pse.setThirdAddWeightPrice(Double.valueOf(StringUtil.trim(map.get(11+""))));
					}
				}catch(Exception ex){
					throw new Exception("三段续重单价["+ map.get(11+"") +"]格式不正确,只能是数字类型");
				}
				if(!checkWeightAndPrice(pse.getThirdWeight(), pse.getThirdWeightPrice(), pse.getThirdAddWeightPrice())){
					throw new Exception("三段首重不为0,三段首重单价、三段续重单价也不能为0");
				}
				if(!checkWeight(pse.getThirdWeight(), pse.getSecondWeight(), pse.getFirstWeight())){
					throw new Exception("首重（3段）＞首重（2段）＞首重（1段）");
				}
				pse.setEffectiveTime(DateUtils.convert(map.get(12+"")));
				if(pse.getEffectiveTime()==null){
					throw new Exception("生效时间["+ map.get(12+"") +"]不能被转为日期");
				}
				if(DateUtils.getMinuteDiff(pse.getEffectiveTime(),nowDate)>0){
					pse.setEffectiveTime(nowDate);
				};
				pse.setRemark(map.get(13+""));
				temp ++;
				pseList.add(pse);
			} catch (Exception e) {
				e.printStackTrace();
				ExcelPriceStandardSectionEntity eps = new ExcelPriceStandardSectionEntity();
				eps.setSendPriceCityName(map.get(0+""));
				eps.setArrivePriceCityName(map.get(1+""));
				eps.setTransTypeCode(map.get(2+""));
				eps.setFirstWeight(map.get(3+""));
				eps.setFirstWeightPrice(map.get(4+""));
				eps.setFirstAddWeightPrice(map.get(5+""));
				eps.setSecondWeight(map.get(6+""));
				eps.setSecondWeightPrice(map.get(7+""));
				eps.setSecondAddWeightPrice(map.get(8+""));
				eps.setThirdWeight(map.get(9+""));
				eps.setThirdWeightPrice(map.get(10+""));
				eps.setThirdAddWeightPrice(map.get(11+""));
				eps.setEffectiveTime(map.get(12+""));
				eps.setRemark(map.get(13+""));
				eps.setOldSerial(temp+"");
				eps.setErrorMsg(e.getMessage());
				errorList.add(eps);
			}
		}
		//错误的信息
		Vector<ExcelPriceStandardSectionEntity> errorEpsList=new Vector<ExcelPriceStandardSectionEntity>();
		
		createThread(pseList,countMap,list,errorEpsList,nowDate);
		
		log.info("***************************标准价格导入完成");
		
		errorEpsList.addAll(errorList);
		
		String filePath="";
		
		retuMap.put("addSize", countMap.get("addSize"));
		retuMap.put("coverSize", countMap.get("coverSize"));
		retuMap.put("sumSize",  list.size());
		retuMap.put("errorSize", errorEpsList.size());
		
		if(errorEpsList.size()>0){
			
			 List<String> titleNames=new ArrayList<String>();
			 titleNames.add("出发价格城市");
			 titleNames.add("到达价格城市");
			 titleNames.add("运输类型");
			 titleNames.add("一段首重");
			 titleNames.add("一段首重单价");
			 titleNames.add("一段续重单价");
			 titleNames.add("二段首重");
			 titleNames.add("二段首重单价");
			 titleNames.add("二段续重单价");
			 titleNames.add("三段首重");
			 titleNames.add("三段首重单价");
			 titleNames.add("三段续重单价");
			 titleNames.add("生效时间");
			 titleNames.add("当前状态");
			 titleNames.add("备注");
			 titleNames.add("旧序列号");
			 titleNames.add("异常信息");
			List<String> paramNames=new ArrayList<String>();
			paramNames.add("sendPriceCityName");
			paramNames.add("arrivePriceCityName");
			paramNames.add("transTypeCode");
			paramNames.add("firstWeight");
			paramNames.add("firstWeightPrice");
			paramNames.add("firstAddWeightPrice");
			paramNames.add("secondWeight");
			paramNames.add("secondWeightPrice");
			paramNames.add("secondAddWeightPrice");
			paramNames.add("thirdWeight");
			paramNames.add("thirdWeightPrice");
			paramNames.add("thirdAddWeightPrice");
			paramNames.add("effectiveTime");
			paramNames.add("state");
			paramNames.add("remark");
			paramNames.add("oldSerial");
			paramNames.add("errorMsg");
			filePath=new ExcelUtil<ExcelPriceStandardSectionEntity>().exportExcel(titleNames,paramNames,errorEpsList);
		}
		retuMap.put("filePath", filePath);
		
		return retuMap;
	}
	
	private void createThread(List<PriceStandardSectionEntity> pseList,Map<String,Long> countMap,
			List<Map<String, String>> excelmap,List<ExcelPriceStandardSectionEntity> errorEpsList,Date dt) throws InterruptedException{
		List<PriceStandardSectionEntity> thread0 = new ArrayList<PriceStandardSectionEntity>();
		List<PriceStandardSectionEntity> thread1 = new ArrayList<PriceStandardSectionEntity>();
		List<PriceStandardSectionEntity> thread2 = new ArrayList<PriceStandardSectionEntity>();
		List<PriceStandardSectionEntity> thread3 = new ArrayList<PriceStandardSectionEntity>();
		List<PriceStandardSectionEntity> thread4 = new ArrayList<PriceStandardSectionEntity>();
		List<PriceStandardSectionEntity> thread5 = new ArrayList<PriceStandardSectionEntity>();
		List<PriceStandardSectionEntity> thread6 = new ArrayList<PriceStandardSectionEntity>();
		List<PriceStandardSectionEntity> thread7 = new ArrayList<PriceStandardSectionEntity>();
		List<PriceStandardSectionEntity> thread8 = new ArrayList<PriceStandardSectionEntity>();
		List<PriceStandardSectionEntity> thread9 = new ArrayList<PriceStandardSectionEntity>();
		
		int count = 0;
		
		for(int i = 0; i < pseList.size(); i++){
			switch (i%10) {
			case 0:
				thread0.add(pseList.get(i));count = count >1 ? count : 1 ;break;
			case 1:
				thread1.add(pseList.get(i));count = count >2 ? count : 2 ;break;
			case 2:
				thread2.add(pseList.get(i));count = count >3 ? count : 3 ;break;
			case 3:
				thread3.add(pseList.get(i));count = count >4 ? count : 4 ;break;
			case 4:
				thread4.add(pseList.get(i));count = count >5 ? count : 5 ;break;
			case 5:
				thread5.add(pseList.get(i));count = count >6 ? count : 6 ;break;
			case 6:
				thread6.add(pseList.get(i));count = count >7 ? count : 7 ;break;
			case 7:
				thread7.add(pseList.get(i));count = count >8 ? count : 8 ;break;
			case 8:
				thread8.add(pseList.get(i));count = count >9 ? count : 9 ;break;
			case 9:
				thread9.add(pseList.get(i));count = count >10 ? count : 10; break;
			default:
				break;
			}
		}
		
		CountDownLatch  latch = new CountDownLatch(count);
		
		if(thread0.size()>0){
			ImplThread it = new ImplThread(thread0,0,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread1.size()>0){
			ImplThread it = new ImplThread(thread1,1,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread2.size()>0){
			ImplThread it = new ImplThread(thread2,2,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread3.size()>0){
			ImplThread it = new ImplThread(thread3,3,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread4.size()>0){
			ImplThread it = new ImplThread(thread4,4,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread5.size()>0){
			ImplThread it = new ImplThread(thread5,5,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread6.size()>0){
			ImplThread it = new ImplThread(thread6,6,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread7.size()>0){
			ImplThread it = new ImplThread(thread7,7,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread8.size()>0){
			ImplThread it = new ImplThread(thread8,8,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread9.size()>0){
			ImplThread it = new ImplThread(thread9,9,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		
		latch.await();
	}
	
	private class ImplThread extends Thread{
		private CountDownLatch latch;
		private List<PriceStandardSectionEntity> list;
		//当前序列号
		private int current = 0;
		//错误List
		private List<ExcelPriceStandardSectionEntity> errorList;
		// exlce
		private List<Map<String, String>> excelmapl;
		//统计 map
		private Map<String,Long> countMap;
		//执行Service
		private PriceStandardSectionService priceStandardSectionService;
		private Date dt;
		
		private String userCode;
		public ImplThread(List<PriceStandardSectionEntity> list,int current,List<ExcelPriceStandardSectionEntity> errorList,
				List<Map<String, String>> excelmapl,
				Map<String,Long> countMap,PriceStandardSectionService priceStandardSectionService,String userCode,
				Date dt,
				CountDownLatch latch){
			this.latch = latch;
			this.list = list;
			this.current = current;
			this.errorList = errorList;
			this.excelmapl = excelmapl;
			this.countMap = countMap;
			this.priceStandardSectionService = priceStandardSectionService;
			this.userCode = userCode;
			this.dt = dt;
		}
		@Override
		public void run() {
			Map<String,Long> countMapt = new Hashtable<String, Long>();
			countMapt.put("addSize", new Long(0));// 增加条数
			countMapt.put("coverSize", new Long(0));// 覆盖条数
			countMapt.put("errorSize",new Long(0));//错误的条数
			List<ExcelPriceStandardSectionEntity> excelList = new ArrayList<ExcelPriceStandardSectionEntity>();
			for(int i = 0; i< list.size(); i++){
				PriceStandardSectionEntity pse = list.get(i);
				Map<String, String> excelmap = excelmapl.get((10*i) + current);
				if(pse == null){
					countMapt.put("errorSize",countMapt.get("errorSize")+1);
					continue;
				}
				try {
					priceStandardSectionService.addOrUpdatePriceStandardForThread(pse, countMapt, userCode, dt);
				} catch (Exception e) {
					countMapt.put("errorSize",countMapt.get("errorSize")+1);
					log.error("批量导入模版数据更新操作异常，业务需要仅作提示", e);
					
					ExcelPriceStandardSectionEntity eps = new ExcelPriceStandardSectionEntity();
					eps.setSendPriceCityName(excelmap.get(0+""));
					eps.setArrivePriceCityName(excelmap.get(1+""));
					eps.setTransTypeCode(excelmap.get(2+""));
					eps.setFirstWeight(StringUtil.trim(excelmap.get(3+"")));
					eps.setFirstWeightPrice(StringUtil.trim(excelmap.get(4+"")));
					eps.setFirstAddWeightPrice(StringUtil.trim(excelmap.get(5+"")));
					eps.setSecondWeight(StringUtil.trim(excelmap.get(6+"")));
					eps.setSecondWeightPrice(StringUtil.trim(excelmap.get(7+"")));
					eps.setSecondAddWeightPrice(StringUtil.trim(excelmap.get(8+"")));
					eps.setThirdWeight(StringUtil.trim(excelmap.get(9+"")));
					eps.setThirdWeightPrice(StringUtil.trim(excelmap.get(10+"")));
					eps.setThirdAddWeightPrice(StringUtil.trim(excelmap.get(11+"")));
					eps.setEffectiveTime(excelmap.get(12+""));
					eps.setRemark(excelmap.get(13+""));
					eps.setOldSerial((10*i) + current+"");
					eps.setErrorMsg(e.getMessage());
					excelList.add(eps);
				}
			}
			synchronized(countMap){
				countMap.put("addSize", countMap.get("addSize") + countMapt.get("addSize"));
				countMap.put("coverSize", countMap.get("coverSize") + countMapt.get("coverSize"));
				countMap.put("errorSize", countMap.get("errorSize") + countMapt.get("errorSize"));
			}
			synchronized (errorList) {
				errorList.addAll(excelList);
			}
			latch.countDown();
			
		}
		
	}
	
	//优化后的导入
	private void addOrUpdatePriceStandardForThread(PriceStandardSectionEntity pse,Map<String,Long> countMap,String userName,Date dt) throws Exception {
		long addSize= countMap.get("addSize").longValue();
		long coverSize= countMap.get("coverSize").longValue();
		
		PriceStandardSectionEntity queryEntity = new PriceStandardSectionEntity();
		queryEntity.setTransTypeCode(pse.getTransTypeCode());
		queryEntity.setSendPriceCity(pse.getSendPriceCity());
		queryEntity.setArrivePriceCity(pse.getArrivePriceCity());
		
		PriceStandardSectionVo queryVo1 = new PriceStandardSectionVo();
		queryVo1.setPriceStandardSectionEntity(queryEntity);
		List<PriceStandardSectionEntity> list = this.priceStandardSectionDao.queryAllByCondition(queryVo1);
		if(null != list && list.size() > 0){
			for (PriceStandardSectionEntity priceStandardSectionEntity : list) {
				if("EFFECTIVE".equals(priceStandardSectionEntity.getState())){
					priceStandardSectionEntity.setInvalidTime(pse.getEffectiveTime());
					this.addOrUpdatePriceStandardSection(priceStandardSectionEntity,userName,dt);
					addSize++;//增加 当前生效的只能是一条
				}else{
					//待生效
					priceStandardSectionEntity.setActive(MiserConstants.NO);
					this.addOrUpdatePriceStandardSection(priceStandardSectionEntity,userName,dt);
					coverSize++;//替换
				}
			}
		}else{
			addSize++;//增加
		}
		this.addOrUpdatePriceStandardSection(pse,userName,dt);
		countMap.put("addSize", addSize);
		countMap.put("coverSize", coverSize);
	}
	
	
	private synchronized Date searchCurrentDateTime(){
		return priceStandardDao.searchCurrentDateTime();
	}

	/**
	 * @Title: checkWeight
	 * @Description: 校验：首重（3段）＞首重（2段）＞首重（1段）
	 * @return boolean true 通过，false 不通过
	 * @param third 首重3段
	 * @param second 首重2段
	 * @param first 首重1段
	 */
	public boolean checkWeight(Double third,Double second,Double first) {
		boolean flag = false;
		if(isZero(third) && isZero(second)){
			flag = true;
		}else if(!isZero(third) && !isZero(second)){
			flag = third > second && second > first;
		}else{
			if(isZero(third)){
				flag = second > first;
			}
			if(isZero(second)){
				flag = third > first;
			}
		}
		return flag;
	}

	/**
	 * @Title: checkWeightAndPrice
	 * @Description: 校验：任何一段的重量不为0时，首重单价、续重单价也不能为0
	 * @return boolean true 通过，false 不通过
	 * @param weight 首重
	 * @param price 首重单价
	 * @param addPrice 续重单价
	 */
	public boolean checkWeightAndPrice(Double weight, Double price, Double addPrice) {
		boolean flag = false;
		if(!isZero(weight)){
			flag = !isZero(price) && !isZero(addPrice);
		}else{
			flag = true;
		}
		return flag;
	}
	
	/**
	 * @Title: isZero
	 * @Description: 校验：是否为0
	 * @return boolean true 为0，false 不为0
	 * @param d
	 */
	public boolean isZero(Double d){
		return d == null || d == 0;
	}
}

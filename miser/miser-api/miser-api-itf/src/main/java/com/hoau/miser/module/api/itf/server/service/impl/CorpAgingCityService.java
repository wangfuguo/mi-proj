package com.hoau.miser.module.api.itf.server.service.impl;


import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.ICorpAgingCityService;
import com.hoau.miser.module.api.itf.api.server.IOrgPositionInfoTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.*;
import com.hoau.miser.module.api.itf.api.shared.vo.TimeQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.TimeQueryResult;
import com.hoau.miser.module.api.itf.server.cache.CorpAgingCityCache;
import com.hoau.miser.module.api.itf.server.cache.DistrictAgingCityCache;
import com.hoau.miser.module.api.itf.server.cache.OrgBasicInfoTyCache;
import com.hoau.miser.module.api.itf.server.constants.PmsConstants;
import com.hoau.miser.module.api.itf.server.dao.CorpAgingCityDao;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.api.itf.server.util.CollectionUtils;
import com.hoau.miser.module.api.itf.server.util.CplbUtils;
import com.hoau.miser.module.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author：廖文强
 * @create：2016年06月02日
 * @description：时效城市
 */
@Service
public class CorpAgingCityService implements ICorpAgingCityService {

	@Resource
	CorpAgingCityDao corpAgingCityDao;

	@Resource
	IOrgPositionInfoTyService orgPositionInfoTyService;

	/**
	 * @param orgCode 组织编码
	 * @return LogticsAgingPCMappDto
	 * @throws
	 * @Description: 查询物流时效价格城市映射
	 * @author 廖文强
	 * @date 2016年06月02日
	 */
	@Override
	public CorpAgingCityEntity queryAgingCityByOrgCode(String orgCode) {
		//根据组织查询到省市区县
		OrgPositionInfoTyEntity district = orgPositionInfoTyService.queryDistrictByOrgCode(orgCode);
		if (district == null) {
			throw new BusinessException("组织["+ orgCode +"]未定义行政区域", "组织["+ orgCode +"]未定义行政区域");
		}
		CorpAgingCityEntity result = new CorpAgingCityEntity();
		result.setOrgCode(district.getOrgCode());
		result.setOrgName(district.getOrgName());
		result.setLogisticCode(district.getLogisticCode());
		//根据组织查询出发时效城市
		ICache<String, CorpAgingCityEntity> cache = CacheManager.getInstance().getCache(DistrictAgingCityCache.UUID);
		String key = new CacheKey(district.getProvinceCode(), district.getCityCode(), district.getCountyCode(), PmsConstants.PRICE_CITY_SEND).generateKey();
		CorpAgingCityEntity sendTimeCity = cache.get(key);
		if (sendTimeCity == null) {
			throw new BusinessException(district.getProvinceName() + district.getCityName() + district.getCountyName() + "未定义出发时效城市",
					district.getProvinceName() + district.getCityName() + district.getCountyName() + "未定义出发时效城市");
		} else {
			result.setSendAgingCityCode(sendTimeCity.getSendAgingCityCode());
			result.setSendAgingCityName(sendTimeCity.getSendAgingCityName());
		}
		//根据组织查询到达时效城市
		cache = CacheManager.getInstance().getCache(DistrictAgingCityCache.UUID);
		key = new CacheKey(district.getProvinceCode(), district.getCityCode(), district.getCountyCode(), PmsConstants.PRICE_CITY_ARRIVAL).generateKey();
		CorpAgingCityEntity arrivalTimeCity = cache.get(key);
		if (arrivalTimeCity == null) {
			throw new BusinessException(district.getProvinceName() + district.getCityName() + district.getCountyName() + "未定义到达时效城市",
					district.getProvinceName() + district.getCityName() + district.getCountyName() + "未定义到达时效城市");
		} else {
			result.setArrivalAgingCityCode(arrivalTimeCity.getArrivalAgingCityCode());
			result.setArrivalAgingCityName(arrivalTimeCity.getArrivalAgingCityName());
		}
		return result;
	}

	/**
	 * 根据省市区县查询出发/到达时效城市
	 *
	 * @param queryParam
	 * @return
	 * @author 陈宇霖
	 * @date 2016年07月14日17:08:45
	 */
	@Override
	public CorpAgingCityEntity queryAgingCityByDistrict(DistrictAgingCityRequestEntity queryParam) {
		ICache<String, CorpAgingCityEntity> cache = CacheManager.getInstance().getCache(DistrictAgingCityCache.UUID);
		String key = new CacheKey(queryParam.getProvinceCode(), queryParam.getCityCode(), queryParam.getAreaCode(), queryParam.getAgingCityType()).generateKey();
		return cache.get(key);
	}

	/**
	 * 根据条件查询时效
	 *
	 * @param param
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月22日10:46:40
	 */
	@Override
	public List<TimeQueryResult> queryTime(TimeQueryParam param) {
		if (param.getQueryType() == null) {
			throw new ChargeException("查询类型为空", "查询类型为空");
		}
		switch (param.getQueryType()) {
			case 0:
				return queryTimeByOrgCode(param);
			case 1:
				return queryTimeByDistrictCode(param);
			case 2:
				return queryTimeByTimeCity(param);
			default:
				throw new ChargeException("查询类型["+ param.getQueryType() +"]无效", "查询类型["+ param.getQueryType() +"]无效");
		}
	}

	/**
	 * 计算预计到达时间
	 *
	 * @param param
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月22日14:23:55
	 */
	@Override
	public TimeQueryResult calculateExpectArrivalTime(TimeQueryParam param) {
		if (param.isHistory() && param.getSendTime() == null) {
			throw new ChargeException("查询历史时效必须提供出发时间", "查询历史时效必须提供出发时间");
		}
		String transportType = param.getTransportType();
		if (StringUtil.isEmpty(param.getTransportType())) {
			throw new ChargeException("运输类型不得为空", "运输类型不得为空");
		}
		//1.把运输类型去掉,只按照线路查询是否可以走货
		param.setTransportType(null);
		List<TimeQueryResult> results = queryTime(param);
		if (results == null) {
			throw new ChargeException("此线路未开通", "此线路未开通");
		}
		param.setTransportType(transportType);
		results = queryTime(param);
		if (!CollectionUtils.isEmpty(results)) {
			TimeQueryResult result = results.get(0);
			int transportTypeDelayDays = CplbUtils.isYAZ(param.getTransportType()) ? 3 : 0; //易安装,在时效上加三天
			if (param.getDeliveryType() != null && param.getDeliveryType() == 0) {
				result.setMinExpectArrivalTime(DateUtils.addDayToDate(param.isHistory() ? param.getSendTime() : new Date(), result.getMinDay() + transportTypeDelayDays));
				if (result.getMaxDay() != null && result.getMinDay().intValue() != result.getMaxDay().intValue()) {
					result.setMaxExpectArrivalTime(DateUtils.addDayToDate(param.isHistory() ? param.getSendTime() : new Date(), result.getMaxDay() + transportTypeDelayDays));
				} else {
					result.setMaxExpectArrivalTime(null);
				}
			} else {
				result.setMinExpectArrivalTime(DateUtils.addDayToDate(param.isHistory() ? param.getSendTime() : new Date(), result.getDeliveryDay() + transportTypeDelayDays));
				result.setMaxExpectArrivalTime(null);
			}
			return result;
		} else {
			throw new ChargeException("此线路不提供" + CplbUtils.toCplbmc(param.getTransportType()) + "服务",
					"此线路不提供" + CplbUtils.toCplbmc(param.getTransportType()) + "服务");
		}
	}

	/**
	 * 根据时效城市查询时效
	 * @param param
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月22日13:44:08
	 */
	private List<TimeQueryResult> queryTimeByTimeCity(TimeQueryParam param) {
		if (StringUtil.isEmpty(param.getSendTimeCityCode()) || StringUtil.isEmpty(param.getArrivalTimeCityCode())) {
			throw new ChargeException("出发和到达时效城市不可为空", "出发和到达时效城市不可为空");
		}
		return corpAgingCityDao.queryTime(param);
	}

	/**
	 * 根据组织查询时效
	 * @param param
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月22日13:47:18
	 */
	private List<TimeQueryResult> queryTimeByOrgCode(TimeQueryParam param) {
		if (StringUtil.isEmpty(param.getSendOrgCode()) || StringUtil.isEmpty(param.getArrivalOrgCode())) {
			throw new ChargeException("出发和到达组织不可为空", "出发和到达组织不可为空");
		}
		//根据组织查询到时效城市
		CorpAgingCityEntity sendTimeCity = queryAgingCityByOrgCode(param.getSendOrgCode());
		if (sendTimeCity == null || StringUtil.isEmpty(sendTimeCity.getSendAgingCityCode())) {
			throw new ChargeException("出发区域未定义出发时效城市", "出发区域未定义出发时效城市");
		}
		CorpAgingCityEntity arrivalTimeCity = queryAgingCityByOrgCode(param.getArrivalOrgCode());
		if (arrivalTimeCity == null || StringUtil.isEmpty(arrivalTimeCity.getArrivalAgingCityCode())) {
			throw new ChargeException("到达区域未定义到达时效城市", "到达区域未定义到达时效城市");
		}
		//根据时效城市查询时效
		param.setSendTimeCityCode(sendTimeCity.getSendAgingCityCode());
		param.setArrivalTimeCityCode(arrivalTimeCity.getArrivalAgingCityCode());
		return corpAgingCityDao.queryTime(param);
	}

	/**
	 * 根据组织编码查询时效
	 * @param param
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月22日14:10:36
	 */
	private List<TimeQueryResult> queryTimeByDistrictCode(TimeQueryParam param) {
		if (StringUtil.isEmpty(param.getSendDistrictCode()) || StringUtil.isEmpty(param.getArrivalDistrictCode())) {
			throw new ChargeException("出发和到达区域不可为空", "出发和到达区域不可为空");
		}
		//根据组织获取时效城市
		String sendTimeCity = corpAgingCityDao.queryTimeCityByDistrict(param.getSendDistrictCode(), "SEND");
		if (StringUtil.isEmpty(sendTimeCity)) {
			throw new ChargeException("出发区域未定义时效城市", "出发区域未定义时效城市");
		}
		String arrivalTimeCity = corpAgingCityDao.queryTimeCityByDistrict(param.getArrivalDistrictCode(), "ARRIVAL");
		if (StringUtil.isEmpty(arrivalTimeCity)) {
			throw new ChargeException("到达区域未定义时效城市", "到达区域未定义时效城市");
		}
		//根据时效城市查询时效
		param.setSendTimeCityCode(sendTimeCity);
		param.setArrivalTimeCityCode(arrivalTimeCity);
		return corpAgingCityDao.queryTime(param);
	}


}

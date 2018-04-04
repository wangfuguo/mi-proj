package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.DistrictAgingCityRequestEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.TimeQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.TimeQueryResult;

import javax.ws.rs.PathParam;
import java.util.List;

/**
 * @author 289612
 * @date 2015年12月15日
 *
 */
public interface ICorpAgingCityService {

	/**
	 * @param orgCode 组织编码
	 * @return LogticsAgingPCMappDto
	 * @throws
	 * @Description: 查询物流时效价格城市映射
	 * @author 廖文强
	 * @date 2016年06月02日
	 */
	public CorpAgingCityEntity queryAgingCityByOrgCode(String orgCode);

	/**
	 * 根据省市区县查询出发/到达时效城市
	 * @param queryParam
	 * @return
	 * @author 陈宇霖
	 * @date 2016年07月14日17:08:45
	 */
	public CorpAgingCityEntity queryAgingCityByDistrict(DistrictAgingCityRequestEntity queryParam);

	/**
	 * 根据条件查询时效
	 * @param param
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月22日10:46:40
	 */
	public List<TimeQueryResult> queryTime(TimeQueryParam param);


	/**
	 * 计算预计到达时间
	 * @param param
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月22日14:23:55
	 */
	public TimeQueryResult calculateExpectArrivalTime (TimeQueryParam param);

}



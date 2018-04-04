package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.DistrictAgingCityRequestEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.TimeQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.TimeQueryResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 292078
 * @date 2016年06月01日
 */
@Repository
public interface CorpAgingCityDao {

	/**
	 * @param @param  userEntity
	 * @param @return
	 * @return List<CorpAgingCityQueryResult>
	 * @throws
	 * @Description: 出发、到达时效城市列表
	 * @author 廖文强
	 * @date 2016年06月01日
	 */
	CorpAgingCityEntity queryAgingCityByOrgCode(String orgCode);

	/**
	 * 根据省市区县查询时效城市
	 * @param queryParam
	 * @return
	 * @author 陈宇霖
	 * @date 2016年07月14日14:37:47
	 */
	CorpAgingCityEntity queryAgingCityByDistrict(DistrictAgingCityRequestEntity queryParam);

	/**
	 * 查询时效
	 * @param param
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月22日10:50:10
	 */
	List<TimeQueryResult> queryTime(TimeQueryParam param);

	/**
	 * 根据行政组织获取价卡城市定义
	 * @param districtCode
	 * @param type
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月22日13:58:00
	 */
	String queryTimeCityByDistrict(@Param("districtCode") String districtCode,
								   @Param("type") String type);

}

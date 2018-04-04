package com.hoau.miser.module.api.itf.server.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;

/**
 * @Description: 价格城市维护
 * ClassName: CorpPriceCityDao
 * @author Chenyl yulin.chen@hoau.net
 * @date 2016年1月14日
 * @version V1.0
 */
@Repository
public interface CorpPriceCityDao {

	/**
	 * @Description: 查询价格城市
	 * @param orgCode
	 * @return CorpPriceCityEntity
     * @author 廖文强
	 * @date 2016年1月14日
	 */
	CorpPriceCityEntity queryPriceCityByOrgCode(Map<String, Object> map);
}

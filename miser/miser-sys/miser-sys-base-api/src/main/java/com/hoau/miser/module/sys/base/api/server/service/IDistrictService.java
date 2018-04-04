package com.hoau.miser.module.sys.base.api.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.sys.base.api.shared.domain.DistrictEntity;


/**
 * @author：高佳
 * @create：2015年6月3日 下午2:47:30
 * @description：省市区service接口
 */
public interface IDistrictService {
	
	/**
	 * 查询所有省份
	 * @return
	 * @author 高佳
	 * @date 2015年6月3日
	 * @update 
	 */
	List<DistrictEntity> queryAllProvince();
	
	/**
	 * 根据实体查行政区域
	 * @param district
	 * @return
	 * @author 高佳
	 * @date 2015年6月5日
	 * @update 
	 */
	List<DistrictEntity> queryDistrictByEntity(DistrictEntity district);
	
	/**
	 * 根据实体查行政区域
	 * @param district
	 * @param limit 分页参数
	 * @param start 分页参数
	 * @return
	 * @author 高佳
	 * @date 2015年7月1日
	 * @update 
	 */
	List<DistrictEntity> queryDistrictByEntity(DistrictEntity district,int limit,int start);

	/**
	 * 查询所有国家
	 * @param limit
	 * @param start
	 * @return
	 * @author 高佳
	 * @date 2015年7月9日
	 * @update 
	 */
	List<DistrictEntity> queryAllNation(int limit, int start);
	/**
	 * 根据部门编码查询组织
	 * @param currentDeptCode
	 * @return
	 * @author刘海飞
	 * @date 2015年12月10日
	 * @update 
	 */
	List<DistrictEntity> queryDistrictInfoByName(String districtName);
	/**
	 * 根据上级查询
	 * @param parentCode
	 * @return
	 * @author 刘海飞
	 * @date 2015年12月09日
	 * @update 
	 */
	List<DistrictEntity> queryDistrictInfoEntityInfoByParentCode(String parentCode);
	
	/**
	 * 根据行政组织编码查询节点
	 * @param distictCode
	 * @return
	 * @author 刘海飞
	 * @date 2015年12月09日
	 * @update 
	 */
	DistrictEntity queryDistrictInfoByCode (String distictCode);
	
	Long queryDistrictByCode(String code);
}

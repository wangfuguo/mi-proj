package com.hoau.miser.module.sys.job.server.dao;

import com.hoau.miser.module.sys.job.shared.domain.DistrictEntity;

/**
 * @author：何巍
 * @create：2015年6月3日 下午2:09:28
 * @description：
 */
public interface DistrictSyncDao {
	/**
	 * 删除所有行政区域
	 * @author 高佳
	 * @date 2015年6月4日
	 * @update 
	 */
	void deleteAllDistrictMDM();

	/**
	 * 新增行政区域
	 * @param district
	 * @author 高佳
	 * @date 2015年6月4日
	 * @update 
	 */
	void addDistrictMDM(DistrictEntity district);

	/**
	 * 获取最大版本号
	 * @return
	 * @author 高佳
	 * @date 2015年6月4日
	 * @update 
	 */
	Long getLastVersionNo();
	
	/**
	 * 根据id判断区域是否存在
	 * @param id
	 * @return
	 */
	Long IsExistDistrictnMDMById(String id);

	/**
	 * 更新行政区域
	 * @param district
	 */
	void updateDistrictMDM(DistrictEntity district);
	
	/**
	 * 
	 * @Description: 更新mdm的信息到District
	 * @param    
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月24日
	 */
	void updateDistrictMDM2District();
}

package com.hoau.miser.module.sys.base.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.sys.base.api.shared.domain.DistrictEntity;
/**
 * @description:行政区域Dao
 * @author 刘海飞
 *@date 2015年12月9日
 */
@Repository
public interface DistrictDao {

	
	/**
	 * 根据组织编码查询组织
	 * @param map
	 * @return
	 * @author 刘海飞
	 * @date 2015年12月10日
	 * @update 
	 */
	List<DistrictEntity> queryDistrictInfoByName(Map<String, Object> map);
	/**
	 * 根据上级部门查询下属部门
	 * @param map
	 * @return
	 * @author 刘海飞
	 * @date 2015年12月10日
	 * @update 
	 */
	List<DistrictEntity> queryDistrictInfoByParentCode(Map<String, Object> map);

	/**
	 * 根据行政组织编码查询节点信息
	 * @param map
	 * @return
	 * @author 刘海飞
	 * @date 2015年12月10日
	 * @update 
	 */
	DistrictEntity queryDistrictInfoByCode(Map<String, Object> map);
	
	/**
	 * 查询最后更新时间
	 * @return
	 * @author 高佳
	 * @date 2015年6月3日
	 * @update 
	 */
	Long getLastUpdateTime();

	/**
	 * 查询热门城市
	 * @return
	 * @author 高佳
	 * @date 2015年6月3日
	 * @update 
	 */
	List<DistrictEntity> queryHotCitys();

	/**
	 * 根据条件查询行政区域
	 * @return
	 * @author 高佳
	 * @date 2015年6月3日
	 * @update 
	 */
	List<DistrictEntity> queryDistrictByEntity(DistrictEntity district);
	
	
	/**
	 * 根据条件查询行政区域
	 * @param district 查询条件
	 * @param rowBounds 分页参数
	 * @return
	 * @author 高佳
	 * @date 2015年7月1日
	 * @update 
	 */
	List<DistrictEntity> queryDistrictByEntity(DistrictEntity district,RowBounds rowBounds);
	

	Long queryDistrictByCode(Map<String, Object> map);
}

package com.hoau.miser.module.sys.base.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.sys.base.api.shared.domain.CommonOrgEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.OrgSearchConditionEntity;

/**
 * @author：高佳
 * @create：2015年6月30日 下午2:27:32
 * @description：公共选择器组织dao
 */
@Repository
public interface CommonOrgDao {

	/**
	 * 根据条件查询组织信息
	 * @param orgSearchConditionEntity 查询条件
	 * @param rowBounds 分页参数
	 * @return
	 * @author 高佳
	 * @date 2015年6月30日
	 * @update 
	 */
	List<CommonOrgEntity> queryOrgByParam(
			OrgSearchConditionEntity orgSearchConditionEntity,
			RowBounds rowBounds);

	/**
	 * 根据条件统计查询组织数量
	 * @param orgSearchConditionEntity
	 * @return
	 * @author 高佳
	 * @date 2015年7月15日
	 * @update 
	 */
	Long countOrgByParam(OrgSearchConditionEntity orgSearchConditionEntity);
	
	/**
	 * 根据传递的 组织编码 递归的查询其父层的信息
	 * @param code 
	 * @return
	 * @author dengyin
	 * @date 2015年7月15日
	 * @update 
	 */
	List<CommonOrgEntity> queryCommonOrgListFetchByCode(String code);
	
	/**
	 * 根据公司名字查找公司编码
	 * @param code 
	 * @return
	 * @author 陈启勇
	 * @date 2016年3月1日
	 * @update 
	 */
	CommonOrgEntity queryByName(String name);

}

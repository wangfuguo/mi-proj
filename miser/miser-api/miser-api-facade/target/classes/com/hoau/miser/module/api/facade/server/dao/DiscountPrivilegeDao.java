package com.hoau.miser.module.api.facade.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.facade.shared.domain.DiscountPrivilegeEntity;

/**
 * 越发越惠DAO
 * ClassName: DiscountPrivilegeDao 
 * @author 286330付于令
 * @date 2016年1月25日
 * @version V1.0
 */
@Repository
public interface DiscountPrivilegeDao {
	/**
	 * @Description: 查询越发越惠折扣数据List
	 * @param entity
	 * @param rowBounds
	 * @return List<DiscountPrivilegeEntity> 
	 * @author 286330付于令
	 * @date 2016年1月25日
	 */
	public List<DiscountPrivilegeEntity> queryDiscountListByParam(DiscountPrivilegeEntity entity, RowBounds rowBounds);
	/**
	 * @Description: 查询越发越惠返券数据List
	 * @param entity
	 * @param rowBounds
	 * @return List<DiscountPrivilegeEntity> 
	 * @author 286330付于令
	 * @date 2016年1月25日
	 */
	public List<DiscountPrivilegeEntity> queryCouponListByParam(DiscountPrivilegeEntity entity, RowBounds rowBounds);
}

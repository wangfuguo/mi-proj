package com.hoau.miser.module.api.facade.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.facade.shared.domain.CouponStateEntity;

/**
 * 返券状态DAO
 * ClassName: PrivilegeCouponStateDao 
 * @author 286330付于令
 * @date 2016年4月14日
 * @version V1.0
 */
@Repository
public interface PrivilegeCouponStateDao {
	/**
	 * @Description: 保存越发越惠返券状态
	 * @param entity   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年4月14日
	 */
	void saveCouponStateEntity(CouponStateEntity entity);
	/**
	 * @Description: 查询越发越惠返券状态
	 * @param entity
	 * @return List<CouponStateEntity> 
	 * @author 286330付于令
	 * @date 2016年4月14日
	 */
	List<CouponStateEntity> findCouponStateEntity(CouponStateEntity entity);
}

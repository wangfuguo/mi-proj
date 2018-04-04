package com.hoau.miser.module.biz.job.server.dao;

import java.util.List;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.PrivilegeContractEntity;
import com.hoau.miser.module.biz.job.shared.domain.PrivilegeCouponEntity;
import com.hoau.miser.module.biz.job.shared.domain.PrivilegeDiscountEntity;
import com.hoau.miser.module.biz.job.shared.domain.PrivilegeEntity;

/**
 * 越发越惠数据同步DAO
 * ClassName: DiscountPrivilegeSendDao 
 * @author 286330付于令
 * @date 2016年2月23日
 * @version V1.0
 */
public interface DiscountPrivilegeSendDao {
	/**
	 * @Description: 查询上次发送失败的越发越惠定义数据
	 * @param entity
	 * @return List<PrivilegeEntity> 
	 * @author 286330付于令
	 * @date 2016年2月23日
	 */
	List<PrivilegeEntity> queryFailurePrivilege(DataSendJobFailureEntity entity);
	/**
	 * @Description: 查询越发越惠定义数据
	 * @param entity
	 * @return List<PrivilegeEntity> 
	 * @author 286330付于令
	 * @date 2016年2月23日
	 */
	List<PrivilegeEntity> queryPrivilege(DataSendJobQueryFilterEntity entity);
	/**
	 * @Description: 查询上次发送失败的越发越惠合同数据
	 * @param entity
	 * @return List<PrivilegeContractEntity> 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	List<PrivilegeContractEntity> queryFailurePrivilegeContract(DataSendJobFailureEntity entity);
	/**
	 * @Description: 查询越发越惠合同数据
	 * @param entity
	 * @return List<PrivilegeContractEntity> 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	List<PrivilegeContractEntity> queryPrivilegeContract(DataSendJobQueryFilterEntity entity);
	/**
	 * @Description: 查询上次发送失败的越发越惠折扣数据
	 * @param entity
	 * @return List<PrivilegeDiscountEntity> 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	List<PrivilegeDiscountEntity> queryFailurePrivilegeDiscount(DataSendJobFailureEntity entity);
	/**
	 * @Description: 查询越发越惠折扣数据
	 * @param entity
	 * @return List<PrivilegeDiscountEntity> 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	List<PrivilegeDiscountEntity> queryPrivilegeDiscount(DataSendJobQueryFilterEntity entity);
	/**
	 * @Description: 查询上次发送失败的越发越惠返券数据
	 * @param entity
	 * @return List<PrivilegeCouponEntity> 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	List<PrivilegeCouponEntity> queryFailurePrivilegeCoupon(DataSendJobFailureEntity entity);
	/**
	 * @Description: 查询越发越惠返券数据
	 * @param entity
	 * @return List<PrivilegeCouponEntity> 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	List<PrivilegeCouponEntity> queryPrivilegeCoupon(DataSendJobQueryFilterEntity entity);
}

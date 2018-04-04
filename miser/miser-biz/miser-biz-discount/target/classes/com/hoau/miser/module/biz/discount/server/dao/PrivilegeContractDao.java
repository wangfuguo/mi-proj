package com.hoau.miser.module.biz.discount.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.discount.api.shared.domain.CouponAcceptorEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractDetailEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeCouponEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeCouponCheckVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeStateQueryVo;

/**
 * 越发越惠客户合同 
 * ClassName: PrivilegeContractDao
 * @author 付于令
 * @date 2016年01月12日
 * @version V1.0
 */
@Repository
public interface PrivilegeContractDao {

	/**
	 * @Description: 查询越发越惠客户合同列表
	 * @param contract
	 * @param rowBounds
	 * @return List<PrivilegeContractEntity>
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public List<PrivilegeContractEntity> queryContractList(PrivilegeContractEntity contract,
			RowBounds rowBounds);
	/**
	 * @Description: 统计越发越惠客户合同数量
	 * @param contract
	 * @return Long
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public Long queryContractCount(PrivilegeContractEntity contract);
	/**
	 * @Description: 新增越发越惠客户合同
	 * @param contract
	 * @return void
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public void addPrivilegeContract(PrivilegeContractEntity contract);
	/**
	 * @Description: 删除越发越惠客户合同
	 * @param contract
	 * @return void
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public void delPrivilegeContract(PrivilegeContractEntity contract);
	/**
	 * @Description: 查询越发越惠客户合同明细列表
	 * @param detail
	 * @param rowBounds
	 * @return List<PrivilegeContractDetailEntity>
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public List<PrivilegeContractDetailEntity> queryContractDetailList(
			PrivilegeContractDetailEntity detail, RowBounds rowBounds);
	/**
	 * @Description: 统计越发越惠客户合同明细数量
	 * @param pe
	 * @return Long
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public Long queryContractDetailCount(PrivilegeContractDetailEntity pe);
	/**
	 * @Description: 新增越发越惠客户合同明细
	 * @param pse
	 * @return void
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public void addPrivilegeContractDetail(PrivilegeContractDetailEntity pse);
	/**
	 * @Description: 修改越发越惠客户合同明细
	 * @param pse
	 * @return void
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public void updatePrivilegeContractDetail(PrivilegeContractDetailEntity pse);
	/**
	 * @Description: 删除越发越惠客户合同明细
	 * @param pse
	 * @return void
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public void delPrivilegeContractDetail(PrivilegeContractDetailEntity pse);
	/**
	 * @Description: 越发越惠状态查询
	 * @param psv
	 * @param rowBounds
	 * @return List<PrivilegeStateQueryVo>
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月15日
	 */
	public List<PrivilegeStateQueryVo> queryListByParamForStateQuery(PrivilegeStateQueryVo psv,
			RowBounds rowBounds);
	public Long queryCountByParamForStateQuery(PrivilegeStateQueryVo psqv);
	/**
	 * @Description: 越发越惠返券记录
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return
	 * @return List<PrivilegeCouponEntity>
	 * @throws
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月15日
	 */
	public List<PrivilegeCouponEntity> queryPrivilegeCouponList(
			PrivilegeCouponEntity pce, RowBounds rowBounds);
	/**
	 * @Description: 设置返券信息
	 * @param @param pce
	 * @return void
	 * @throws
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月19日
	 */
	public void updatePrivilegeCoupon(PrivilegeCouponEntity pce);
	public void addPrivilegeCoupon(PrivilegeCouponEntity couponAcceptorEntity);
	/**
	 * @Description: 越发越惠收券人
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return
	 * @return List<CouponAcceptorEntity>
	 * @throws
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月15日
	 */
	public List<CouponAcceptorEntity> queryListByParamForCouponAcceptor(CouponAcceptorEntity pde,
			RowBounds rowBounds);
	/**
	 * @Description: TODO新增收券人信息
	 * @param @param couponAcceptorVo
	 * @param @return
	 * @return String
	 * @throws
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月15日
	 */
	public void addCouponAcceptor(CouponAcceptorEntity couponAcceptorEntity);
	public void delCouponAcceptor(CouponAcceptorEntity couponAcceptorEntity);
	public void updateCouponAcceptor(CouponAcceptorEntity couponAcceptorEntity);
	/**
	 * @Description: 查询审核列表
	 * @param privilegeCouponCheckVo
	 * @param rowBounds
	 * @return List<PrivilegeCouponCheckVo>
	 * @author 286330付于令
	 * @date 2016年1月20日
	 */
	public List<PrivilegeCouponCheckVo> queryListByParamForCheck(
			PrivilegeCouponCheckVo privilegeCouponCheckVo, RowBounds rowBounds);
	public Long queryCountByParamForCheck(PrivilegeCouponCheckVo privilegeCouponCheckVo,
			RowBounds rowBounds);
	/**
	 * @Description: 根据客户CODE查询合同，合同开始结束时间
	 * @param entity
	 * @return void 
	 * @author 付于令
	 * @date 2016年4月1日
	 */
	public List<PrivilegeContractEntity> findPrivilegeContract(PrivilegeContractEntity entity);
	/**
	 * @Description: 删除返券数据
	 * @param delCouponEntity   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年4月11日
	 */
	public void delPrivilegeCoupon(PrivilegeCouponEntity delCouponEntity);
}

package com.hoau.miser.module.biz.discount.api.server.service;

import java.util.List;

import com.hoau.miser.module.biz.discount.api.shared.domain.CouponAcceptorEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractDetailEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.CouponAcceptorVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeContractDetailVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeContractVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeCouponCheckVo;
import com.hoau.miser.module.biz.discount.api.shared.vo.PrivilegeStateQueryVo;

/**
 * 越发越惠客户合同
 * ClassName: IPrivilegeContractService 
 * @author 286330付于令
 * @date 2016年01月12日
 * @version V1.0
 */
public interface IPrivilegeContractService {
	/**
	 * @Description: 查询越发越惠客户合同集合信息
	 * @param psv
	 * @param limit
	 * @param start
	 * @return List<PrivilegeContractEntity>  
	 * @Author 286330付于令
	 * @date 2016年01月12日
	 */
	public List<PrivilegeContractEntity> queryListByParam(PrivilegeContractVo psv, int limit, int start);
	/**
	 * @Description: 统计越发越惠客户合同数量
	 * @param psv
	 * @return Long  
	 * @Author 286330
	 * @date 2016年01月12日
	 */
	public Long queryCountByParam(PrivilegeContractVo psv);
	/**
	 * @Description: 添加越发越惠客户合同
	 * @param pse   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年01月12日
	 */
	public String addPrivilegeContract(PrivilegeContractEntity pse,String PrivilegeContractDetailList);
	/**
	 * 
	 * @Description: 修改越发越惠客户合同
	 * @param pse   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年01月12日
	 */
	public String updatePrivilegeContract(PrivilegeContractEntity pse,String PrivilegeContractDetailList);
	/**
	 * @Description: 删除越发越惠客户合同
	 * @param pse   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年01月12日
	 */
	public void delPrivilegeContract(PrivilegeContractEntity pse);
	
	/**
	 * @Description: 查询越发越惠客户合同明细
	 * @param id
	 * @return PrivilegeContractEntity 
	 * @author 286330付于令
	 * @date 2016年01月12日
	 */
	public PrivilegeContractEntity queryPrivilegeContractById(String id);
	/**
	 * @Description: 查询越发越惠客户合同明细列表
	 * @param psv
	 * @param limit
	 * @param start
	 * @return List<PrivilegeContractDetailEntity> 
	 * @author 286330付于令
	 * @date 2016年01月12日
	 */
	public List<PrivilegeContractDetailEntity> queryListByParamSub(PrivilegeContractDetailVo psv, int limit, int start);
	/**
	 * @Description: 查询越发越惠客户合同明细条数
	 * @param psv
	 * @return Long 
	 * @author 286330付于令
	 * @date 2016年01月12日
	 */
	public Long queryCountByParam(PrivilegeContractDetailVo psv);
	/**
	 * @Description: 添加越发越惠客户合同明细
	 * @param pse   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年01月12日
	 */
	public void addPrivilegeContractDetail(PrivilegeContractDetailEntity pse);
	/**
	 * @Description: 修改越发越惠客户合同明细
	 * @param pse   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年01月12日
	 */
	public void updatePrivilegeContractDetail(PrivilegeContractDetailEntity pse);
	/**
	 * @Description: 删除越发越惠客户合同明细
	 * @param pse   
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年01月12日
	 */
	public void delPrivilegeContractDetail(PrivilegeContractDetailEntity pse);
	/**
	 * @Description: 根据ID获取越发越惠客户合同明细
	 * @param id
	 * @return PrivilegeContractDetailEntity 
	 * @author 286330付于令
	 * @date 2016年01月12日
	 */
	public PrivilegeContractDetailEntity queryPrivilegeContractDetailById(String id);
	
	/**
	 * 
	 * @Description: 越发越惠状态查询
	 * @param @param psqv
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PrivilegeStateQueryVo> 
	 * @throws
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月15日
	 */
	public List<PrivilegeStateQueryVo> queryListByParamForStateQuery(PrivilegeStateQueryVo psqv, int limit, int start);
	/**
	 * 
	 * @Description: 越发越惠状态查询
	 * @param @param psqv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月15日
	 */
	public Long queryCountByParamForStateQuery(PrivilegeStateQueryVo psqv);
	
	
	/**
	 * 
	 * @Description: 新增返券记录
	 * @param @param couponAcceptorVo
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月15日
	 */
	public String  addCouponAcceptor(CouponAcceptorVo couponAcceptorVo);
	
	/**
	 * 
	 * @Description: 修改返券记录
	 * @param @param couponAcceptorVo
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年1月15日
	 */
	public String  updateCouponAcceptor(CouponAcceptorVo couponAcceptorVo);
//	/**
//	 * 
//	 * @Description: 删除返券记录
//	 * @param @param couponAcceptorVo
//	 * @param @return   
//	 * @return String 
//	 * @throws
//	 * @author mao.wang@newHoau.com.cn
//	 * @date 2016年1月15日
//	 */
//	public String  delCouponAcceptor(CouponAcceptorVo couponAcceptorVo);

	/**
	 * @param couCheckVo   
	 * @return String 错误信息 
	 * @author 286330付于令
	 * @date 2016年1月19日
	 */
	public String updateCouponState(PrivilegeCouponCheckVo couCheckVo);
	
	public CouponAcceptorEntity queryCouponAcceptorVoById(CouponAcceptorEntity couponAcceptorEntity);
	/**
	 * @Description: 查询审核列表
	 * @param privilegeCouponCheckVo
	 * @param limit
	 * @param start
	 * @return List<PrivilegeCouponCheckVo> 
	 * @author 286330付于令
	 * @date 2016年1月20日
	 */
	public List<PrivilegeCouponCheckVo> queryListByParamForCheck(
			PrivilegeCouponCheckVo privilegeCouponCheckVo, int limit, int start);
	public Long queryCountByParamForCheck(
			PrivilegeCouponCheckVo privilegeCouponCheckVo, int limit, int start);
	public String createExcel(List<PrivilegeStateQueryVo> list);
}

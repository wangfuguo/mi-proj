package com.hoau.miser.module.biz.discount.api.server.service;

import java.util.List;

import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountPrivilegeEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountPrivilegeVo;

/**
 * 
 * @Description: 越发越惠action
 * @Author 292078
 * @Date 2015年12月15日
 */
public interface IDiscountPrivilegeService {

	/**
	 * 
	 * @Description: 查询越发越惠列表
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<DiscountPrivilegeEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public List<DiscountPrivilegeEntity> queryListByParam(DiscountPrivilegeVo psv, int limit, int start);
	/**
	 * 
	 * @Description: 统计越发越惠数量
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public Long queryCountByParam(DiscountPrivilegeVo psv);
	/**
	 * 
	 * @Description: 新增越发越惠
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String addDiscountPrivilege(DiscountPrivilegeEntity pse);
	/**
	 * 
	 * @Description: 修改越发越惠
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String updateDiscountPrivilege(DiscountPrivilegeEntity pse);
	/**
	 * 
	 * @Description: 删除越发越惠
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String delDiscountPrivilege(DiscountPrivilegeEntity pse);
	/**
	 * 
	 * @Description: 查询越发越惠ById
	 * @param @param id
	 * @param @return   
	 * @return DiscountPrivilegeEntity 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public DiscountPrivilegeEntity queryDiscountPrivilegeById(String id);
}

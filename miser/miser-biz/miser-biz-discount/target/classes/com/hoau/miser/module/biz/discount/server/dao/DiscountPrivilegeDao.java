package com.hoau.miser.module.biz.discount.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountPrivilegeEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountPrivilegeVo;
/**
 * 
 * @Description: 越发越惠Dao
 * @Author 292078
 * @Date 2015年12月15日
 */
@Repository
public interface DiscountPrivilegeDao {

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
	public List<DiscountPrivilegeEntity> queryListByParam(DiscountPrivilegeVo psv, RowBounds rowBounds);
	
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
	public void addDiscountPrivilege(DiscountPrivilegeEntity pse);
	/**
	 * 
	 * @Description: 修改越发越惠
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public void updateDiscountPrivilege(DiscountPrivilegeEntity pse);
	/**
	 * 
	 * @Description: 删除越发越惠
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public void delDiscountPrivilege(DiscountPrivilegeEntity pse);

	public Long findPrivilege(DiscountPrivilegeEntity privilege);

	
}

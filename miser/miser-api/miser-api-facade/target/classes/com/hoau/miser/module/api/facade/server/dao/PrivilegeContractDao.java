package com.hoau.miser.module.api.facade.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.facade.shared.domain.PrivilegeContractDetailEntity;
import com.hoau.miser.module.api.facade.shared.domain.PrivilegeContractEntity;
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
	 * @Description: 新增越发越惠客户合同
	 * @param entity   
	 * @return void 
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public void addPrivilegeContract(PrivilegeContractEntity entity);
	/**
	 * @Description: 新增越发越惠客户合同明细
	 * @param entity   
	 * @return void 
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public void addPrivilegeContractDetail(PrivilegeContractDetailEntity entity);
	/**
	 * @Description: 根据客户CODE查询合同
	 * @param entity
	 * @return void 
	 * @author 付于令
	 * @date 2016年4月1日
	 */
	public List<PrivilegeContractEntity> findPrivilegeContract(PrivilegeContractEntity entity);
	/**
	 * @Description: 删除越发越惠客户合同
	 * @param entity
	 * @return void 
	 * @author 付于令
	 * @date 2016年4月1日
	 */
	public void delPrivilegeContract(PrivilegeContractEntity entity);
	/**
	 * @Description: 删除越发越惠客户合同明细
	 * @param entity
	 * @return void 
	 * @author 付于令
	 * @date 2016年4月1日
	 */
	public void delPrivilegeContractDetail(PrivilegeContractDetailEntity entity);
}

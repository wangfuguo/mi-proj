package com.hoau.miser.module.api.facade.server.service;

import com.hoau.miser.module.api.facade.shared.vo.PrivilegeContractVo;

/**
 * 越发越惠客户合同
 * ClassName: IPrivilegeContractService 
 * @author 付于令
 * @date 2016年01月12日
 * @version V1.0
 */
public interface IPrivilegeContractService {
	/**
	 * @Description: 添加越发越惠客户合同
	 * @param pse
	 * @return void
	 * @author 付于令
	 * @date 2016年01月12日
	 */
	public String addPrivilegeContract(PrivilegeContractVo entity);
}

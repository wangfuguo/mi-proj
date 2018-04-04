package com.hoau.miser.module.sys.base.api.server.service;

import java.util.List;

import com.hoau.miser.module.sys.base.api.shared.domain.OrgAdministrativeInfoEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.PlatformEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.SalesDepartmentEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.TransferCenterEntity;

/**
 * @author：高佳
 * @create：2015年6月9日 下午2:38:11
 * @description：
 */
public interface IOrgAdministrativeInfoService {

	/**
	 * 根据部门编码查询组织
	 * @param currentDeptCode
	 * @return
	 * @author 高佳
	 * @date 2015年6月11日
	 * @update 
	 */
	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(
			String currentDeptCode);

	/**
	 * 
	 * @Description: 查询全部组织
	 * @param @return   
	 * @return List<OrgAdministrativeInfoEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月12日
	 */
	List<OrgAdministrativeInfoEntity> queryALLOrgAdministrativeInfo();
	/**
	 * 通过部门编码批量查询部门信息
	 * @param orgIds
	 * @return
	 * @author 高佳
	 * @date 2015年6月12日
	 * @update 
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoBatchByCode(
			String[] orgIds);
	
	/**
	 * 根据上级查询
	 * @param parentCode
	 * @return
	 * @author 高佳
	 * @date 2015年6月11日
	 * @update 
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByParentCode(String parentCode);

	/**
	 * 根据物流编码查询
	 * @param logistCode
	 * @return
	 * @author 张贞献
	 * @date 2015年8月3日
	 * @update 
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByLogistCode(String logistCode);
	
	/**
	 * 根据部门实体查询部门信息
	 * @param orgAdministrativeInfoEntity
	 * @return
	 * @author 张贞献
	 * @date 2015-7-20
	 * @update 
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfo(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity);
	
	/**
	 * 根据部门编码查询平台
	 * @param currentDeptCode
	 * @return
	 * @author 张贞献
	 * @date 2015-7-24
	 * @update 
	 */
	PlatformEntity queryPlatformEntityByCode(String currentDeptCode);
	
	/**
	 * 根据部门编码查询门店
	 * @param currentDeptCode
	 * @return
	 * @author 张贞献
	 * @date 2015-7-24
	 * @update 
	 */
	SalesDepartmentEntity querySalesDepartmentEntityByCode(String currentDeptCode);
	
	/**
	 * 根据部门编码查询场站
	 * @param currentDeptCode
	 * @return
	 * @author 张贞献
	 * @date 2015-7-24
	 * @update 
	 */
	TransferCenterEntity queryTransferCenterEntityByCode(String currentDeptCode);

}

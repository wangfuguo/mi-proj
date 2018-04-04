package com.hoau.miser.module.sys.base.server.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.sys.base.api.shared.domain.OrgAdministrativeInfoEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.PlatformEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.SalesDepartmentEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.TransferCenterEntity;

/**
 * @author：高佳
 * @create：2015年6月10日 上午8:56:21
 * @description：组织信息dao
 */
@Repository
public interface OrgAdministrativeInfoDao {

	/**
	 * 根据组织编码查询组织
	 * @param map
	 * @return
	 * @author 张贞献
	 * @date 2015-7-24
	 * @update 
	 */
	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(
			Map<String, Object> map);

	/**
	 * 根据物流代码集合查询组织
	 * @param map
	 * @return
	 * @author 张贞献
	 * @date 2015年8月3日
	 * @update 
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoBylogistCode(
			Map<String, Object> map);
	
	/**
	 * 根据上级部门查询下属部门
	 * @param map
	 * @return
	 * @author 高佳
	 * @date 2015年6月11日
	 * @update 
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByParentCode(
			Map<String, Object> map);
	
	/**
	 * 
	 * @Description: 拿到全部的组织机构
	 * @param @param orgAdministrativeInfoEntity
	 * @param @return   
	 * @return List<OrgAdministrativeInfoEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月11日
	 */
	List<OrgAdministrativeInfoEntity> queryAllOrgAdministrativeInfo(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity);

	/**
	 * 
	 * @Description: 查询最后更新版本
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月11日
	 */
	public Long queryLastVersionNo();
	/**
	 * 根据部门信息查出部门
	 * @param orgAdministrativeInfoEntity
	 * @return
	 * @author 张贞献
	 * @date 2015-7-20
	 * @update 
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfo(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity);

	/**
	 * 根据平台编码查询平台
	 * @param map
	 * @return
	 * @author 张贞献
	 * @date 2015-7-24
	 * @update 
	 */
	PlatformEntity queryPlatformEntityByCode(Map<String, Object> map);
	
	/**
	 * 根据门店编码查询门店
	 * @param map
	 * @return
	 * @author 张贞献
	 * @date 2015-7-24
	 * @update 
	 */
	SalesDepartmentEntity querySalesDepartmentEntityByCode(Map<String, Object> map);
	
	/**
	 * 根据场站编码查询场站
	 * @param map
	 * @return
	 * @author 张贞献
	 * @date 2015-7-24
	 * @update 
	 */
	TransferCenterEntity queryTransferCenterEntityByCode(Map<String, Object> map);
	
}

package com.hoau.miser.module.sys.job.server.dao;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 *  
 * @Author 292078
 * @Date 2015年12月16日
 */
public interface OrgSyncDao {

	/**
	 * 更新合并组织表数据
	 * @param versionNo
	 * @param date void  
	 * @Author 292078
	 * @Time 2015年12月16日上午11:55:50
	 */
	void updateMergeOrg(@Param("versionNo") long versionNo,@Param("date") Date date);

	/**
	 * 更新组织网点信息->针对于存在网点的
	 * @Description: TODO描述该方法是做什么的
	 * @param @param param   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年3月3日
	 */
	void updateBseDistrictCorp();
	
	

	/**
	 * 更新组织网点信息->针对于不存在网点的
	 */
	void updateBseDistrictWithOutCorp();
	
	
	
	/**
	 * 更新合并场站表
	 * @param versionNo
	 * @param date
	 * @author 廖文强
	 * @date 2016年03月31日
	 * @update 
	 */
	void updateMergeTranferCenter(@Param("versionNo") long versionNo,@Param("date") Date date);

	/**
	 * 删除无效场站数据
	 * @param versionNo
	 * @param date
	 * @author 廖文强
	 * @date 2016年03月31日
	 * @update 
	 */
	void deleteInvalidTranferCenter(@Param("versionNo") long versionNo,@Param("date") Date date);

	/**
	 * 更新合并平台表
	 * @param versionNo
	 * @param date
	 * @author 廖文强
	 * @date 2016年03月31日
	 * @update 
	 */
	void updateMergePlatform(@Param("versionNo") long versionNo,@Param("date") Date date);

	/**
	 * 删除平台表无效数据
	 * @param versionNo
	 * @param date
	 * @author 廖文强
	 * @date 2016年03月31日
	 * @update 
	 */
	void deleteInvalidPlatform(@Param("versionNo") long versionNo,@Param("date") Date date);

	/**
	 * 更新合并门店表
	 * @param versionNo
	 * @param date
	 * @author 廖文强
	 * @date 2016年03月31日
	 * @update 
	 */
	void updateMergeSalesDepartment(@Param("versionNo") long versionNo,@Param("date") Date date);

	/**
	 * 删除门店表无效数据
	 * @param versionNo
	 * @param date
	 * @author 廖文强
	 * @date 2016年03月31日
	 * @update 
	 */
	void deleteInvalidSalesDepartment(@Param("versionNo") long versionNo,@Param("date") Date date);

}

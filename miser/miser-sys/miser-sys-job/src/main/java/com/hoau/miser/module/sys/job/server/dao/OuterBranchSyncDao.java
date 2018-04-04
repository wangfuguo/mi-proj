package com.hoau.miser.module.sys.job.server.dao;



import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.sys.job.shared.domain.OutBranchEntity;

/**
 * 
 *
 * @author 涂婷婷
 * @date 2015年9月10日
 */
@Repository
public interface OuterBranchSyncDao {
	
	/**
	 * 同步外部代理网点信息临时表
	 * 
	 * @param outBranchEntity
	 * @author 涂婷婷
	 * @date 2015年9月10日
	 * @update
	 */
	void addOuterBranchMdm(OutBranchEntity outBranchEntity);
	/**
	 * 获取上词更新的最大时间截
	 * 
	 * @return
	 * @author 涂婷婷
	 * @date 2015年9月10日
	 * @update
	 */
	Long getLastUpdateTime();
	
	/**
	 * 根据版本号统计临时表记录数
	 * 
	 * @return
	 * @author 涂婷婷
	 * @date 2015年9月10日
	 * @update
	 */
	Long queryMdmCountByCode(String code);
	
	/**
	 * 修改外部代理网点信息临时表
	 * 
	 * @param outBranchEntity
	 * @author 涂婷婷
	 * @date 2015年9月10日
	 * @update
	 */
	void updateOuterBranchMdm(OutBranchEntity outBranchEntity);
	/**
	 * 更新合并偏线数据
	 * @param version
	 * @param date
	 * @author 高佳
	 * @date 2015年9月24日
	 * @update 
	 */
	void updateOuterBranch(@Param("versionNo") long versionNo, @Param("date") Date date);
}

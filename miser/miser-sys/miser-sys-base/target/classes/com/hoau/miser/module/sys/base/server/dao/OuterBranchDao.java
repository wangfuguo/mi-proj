package com.hoau.miser.module.sys.base.server.dao;



import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.sys.base.api.shared.domain.OuterBranchEntity;

/**
 * 
 * @Description: 偏线公司
 * @Author 292078
 * @Time 2015年12月11日上午10:17:20
 */
@Repository
public interface OuterBranchDao {
	/**
	 * 
	 * @Description: 查询偏线公司信息
	 * @param outerBranchEntity
	 * @param rowBounds
	 * @return List<OuterBranchEntity>  
	 * @Author 292078
	 * @Time 2015年12月11日上午10:18:17
	 */
	List<OuterBranchEntity> queryOuterBranchList(OuterBranchEntity outerBranchEntity, RowBounds rowBounds);
	/**
	 * 
	 * @Description: 查询偏线公司数量
	 * @param outerBranchEntity
	 * @return Long  
	 * @Author 292078
	 * @Time 2015年12月11日上午10:19:42
	 */
	Long queryOuterBranchCount(OuterBranchEntity outerBranchEntity);
}

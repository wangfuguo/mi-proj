package com.hoau.miser.module.biz.extrafee.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceUpstairsEntity;

/**
 * ClassName: PriceUpstairsEntityMapper 
 * @Description: 上楼费dao 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月5日
 * @version V1.0   
 */
@Repository
public interface PriceUpstairsDao {
	
	/**
	 * @Description: 上楼费界面查询
	 * @param entity
	 * @param rowBounds
	 * @return List<PriceUpstairsEntity> 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月25日
	 */
	public List<PriceUpstairsEntity> queryUpstairsPrices(PriceUpstairsEntity entity, RowBounds rowBounds);

	/**
	 * @Description: 上楼费界面查询总条数
	 * @param entity
	 * @return List<PriceUpstairsEntity> 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月25日
	 */
	public Long queryUpstairsPricesCount(PriceUpstairsEntity entity);
	
	/**
	 * @Description: 新增上楼费
	 * @param entity   
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月25日
	 */
	public void addUpstairsPrice(PriceUpstairsEntity entity);
	
	/**
	 * @Description: 根据id查询上楼费信息
	 * @param id
	 * @return PriceUpstairsEntity 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月25日
	 */
	public PriceUpstairsEntity queryUpstairsEntityById(String id);

	/**
	 * @Description: 检查操作数据是否待生效
	 * @param entity
	 * @return PriceUpstairsEntity 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月25日
	 */
	public PriceUpstairsEntity checkExistsWaitUpstairsPrice(PriceUpstairsEntity entity);
	
	/**
	 * @Description: 检查操作数据是否生效中
	 * @param entity
	 * @return PriceUpstairsEntity 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月25日
	 */
	public PriceUpstairsEntity checkExistsEffectiveUpstairsPrice(PriceUpstairsEntity entity);
	
	/**
	 * @Description: 检查操作数据是否已过期
	 * @param entity
	 * @return PriceUpstairsEntity 已过期返回过期数据，不过期返回空
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月25日
	 */
	public PriceUpstairsEntity checkExistsPassedUpstairsPrice(PriceUpstairsEntity entity);
	
	/**
	 * @Description: 删除上楼费
	 * @param entity   
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月25日
	 */
	public void deleteUpstairsPrice(PriceUpstairsEntity entity);
	
	/**
	 * @Description: 更新失效时间
	 * @param entity   
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月25日
	 */
	public void updateInvalidTime(PriceUpstairsEntity entity);

	/**
	 * @Description: 根据id查询到分段明细的上楼费
	 * @param id	上楼费id
	 * @return PriceUpstairsEntity 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月25日
	 */
	public PriceUpstairsEntity queryUpstairsEntityWithSectionDetailsById(String id);
}
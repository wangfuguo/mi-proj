package com.hoau.miser.module.api.facade.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.facade.shared.domain.DiscountCustomerEntity;
import com.hoau.miser.module.api.facade.shared.vo.DiscountCustomerVo;

@Repository
public interface DiscountCustomerSyncDao {
	int deleteByPrimaryKey(String id);

    int insert(DiscountCustomerEntity record);

    int insertSelective(DiscountCustomerEntity record);

    DiscountCustomerEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DiscountCustomerEntity record);

    int updateByPrimaryKey(DiscountCustomerEntity record);
    
    List<DiscountCustomerEntity> queryListByParam(DiscountCustomerEntity record);
	/**
	 * 
	 * @Description: 查询客户折扣列表
	 * @param @param beanv
	 * @param @param rowBounds 
	 * @return List<DiscountCustomerEntity> 
	 * @author 陈启勇
	 * @date 2016年1月7日
	 */
	public List<DiscountCustomerEntity> queryListByParam(DiscountCustomerVo beanv, RowBounds rowBounds);
    
    /**
	 * 
	 * @Description: updateDiscountCustomer
	  * @param @param beanv
	 * @param @param rowBounds 
	 * @return List<DiscountCustomerEntity> 
	 * @author 陈启勇
	 * @date 2016年1月7日
	 */
	public void updateDiscountCustomer(DiscountCustomerEntity beanv);
	
	
	/**
	 * add by dengyin 2016-5-16 11:03:48 运费分段折扣 为空时 
	 * 【需要复制最近一条非作废状态的记录的运费优惠分段到新运费优惠分段中】
	 * @param entity
	 * @return
	 */
	public DiscountCustomerEntity selectByCusTransCodeForLatest(DiscountCustomerEntity entity);	
}
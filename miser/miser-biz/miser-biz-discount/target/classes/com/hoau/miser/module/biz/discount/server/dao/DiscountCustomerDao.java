package com.hoau.miser.module.biz.discount.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountCustomerEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountCustomerVo;



public interface DiscountCustomerDao {
	int deleteByPrimaryKey(String id);

    int insert(DiscountCustomerEntity record);

    int insertSelective(DiscountCustomerEntity record);

    DiscountCustomerEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DiscountCustomerEntity record);

    int updateByPrimaryKey(DiscountCustomerEntity record);
    
    /**
	 * 
	 * @Description: 统计客户折扣数量
	 * @param beanv
	 * @return Long  
	 * @Author 陈启勇
	 * @Time 2016年1月6日下午
	 */
	public Long queryCountByParam(DiscountCustomerVo beanv);
	
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
	 * @param @param beanv
	 * @param @return   
	 * @return List<DiscountCorpEntity> 
	 * @throws
	 * @author 陈启勇
	 * @date 2016年1月18日
	 */
	public List<DiscountCustomerEntity> excelQueryListByParam(DiscountCustomerVo beanv);
	
}
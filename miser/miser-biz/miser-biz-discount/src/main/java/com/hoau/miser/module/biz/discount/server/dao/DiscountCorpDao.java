package com.hoau.miser.module.biz.discount.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountCorpEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountCorpVo;



public interface DiscountCorpDao {
    int deleteByPrimaryKey(String id);

    int insert(DiscountCorpEntity record);

    int insertSelective(DiscountCorpEntity record);

    DiscountCorpEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DiscountCorpEntity record);

    int updateByPrimaryKey(DiscountCorpEntity record);
    
    /**
	 * 
	 * @Description: 统计网点折扣数量
	 * @param param
	 * @return Long  
	 * @Author 陈启勇
	 * @Time 2016年1月6日下午
	 */
	public Long queryCountByParam(DiscountCorpVo beanv);
	
	/**
	 * 
	 * @Description: 查询网点折扣列表
	 * @param @param beanv
	 * @param @param rowBounds 
	 * @return List<DiscountCorpEntity> 
	 * @author 陈启勇
	 * @date 2016年1月7日
	 */
	public List<DiscountCorpEntity> queryListByParam(DiscountCorpVo beanv, RowBounds rowBounds);
	
	/**
	 * 
	 * @Description: updateDiscountCorp
	  * @param @param beanv
	 * @param @param rowBounds 
	 * @return List<DiscountCorpEntity> 
	 * @author 陈启勇
	 * @date 2016年1月7日
	 */
	public void updateDiscountCorp(DiscountCorpEntity beanv);
	
	/**
	 * @param @param beanv
	 * @param @return   
	 * @return List<DiscountCorpEntity> 
	 * @throws
	 * @author 陈启勇
	 * @date 2016年1月18日
	 */
	public List<DiscountCorpEntity> excelQueryListByParam(DiscountCorpVo beanv);
	
}
package com.hoau.miser.module.biz.pricecard.server.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardVo;
/**
 * 
 * @Description: 标准价格Dao
 * @Author 廖文强
 * @Date 2015年12月15日
 */
@Repository
public interface PriceStandardDao {

	/**
	 * 
	 * @Description: 查询标准价格列表
	 * @param param
	 * @param rowBounds
	 * @return List<PriceStandardEntity>  
	 * @Author 廖文强
	 * @Time 2015年12月15日下午1:33:23
	 */
	public List<PriceStandardEntity> queryListByParam(PriceStandardVo psv, RowBounds rowBounds);
	
	
	/**
	 * 
	 * @Description: 查询标准价格列表(Excel)
	 * @param @param psv
	 * @param @return   
	 * @return List<ExcelPriceStandardEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	public List<ExcelPriceStandardEntity> excelQueryListByParam(PriceStandardVo psv);
	
	/**
	 * 
	 * @Description: 统计标准价格数量
	 * @param param
	 * @return Long  
	 * @Author 廖文强
	 * @Time 2015年12月15日下午4:37:17
	 */
	public Long queryCountByParam(PriceStandardVo psv);
	/**
	 * 
	 * @Description: addPriceStandard
	 * @param pse void  
	 * @Author 廖文强
	 * @Time 2015年12月15日下午5:26:22
	 */
	public void addPriceStandard(PriceStandardEntity pse);
	/**
	 * 
	 * @Description: updatePriceStandard
	 * @param pse void  
	 * @Author 廖文强
	 * @Time 2015年12月15日下午5:26:27
	 */
	public void updatePriceStandard(PriceStandardEntity pse);

	public Date searchCurrentDateTime();
	
	List<PriceStandardEntity> searchEffectTimeData(PriceStandardVo psv);
}

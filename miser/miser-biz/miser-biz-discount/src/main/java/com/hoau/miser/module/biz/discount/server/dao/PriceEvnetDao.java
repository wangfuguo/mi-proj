package com.hoau.miser.module.biz.discount.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventOrgEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEvnetEntity;

/**
 * 折扣dao
 * ClassName: PriceEvnetDao 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
@Repository
public interface PriceEvnetDao {

	/**
	 * 
	 * @Description: 根据条件查询优惠折扣集合
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceEvnetEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public List<PriceEvnetEntity> queryListByParam(PriceEvnetEntity psv, RowBounds rowBounds);
	
	
	
	/**
	 * 
	 * @Description: 插入优惠折扣
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void insertPriceEvnet(PriceEvnetEntity psv);
	
	/**
	 * 
	 * @Description: 修改优惠折扣
	 * @param @param psv   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void updatePriceEvnet(PriceEvnetEntity psv);
	
	/**
	 * 
	 * @Description: 根据条件统计优惠折扣集合
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public Long queryCountByParam(PriceEvnetEntity psv);
	/**
	 * eventCode 必须传
	 * @Description: 根据条件查询组织折扣集合
	 * @param @param psv
	 * @param @return   
	 * @return List<PriceEvnetEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public List<PriceEventOrgEntity> queryOrgListByParam(PriceEventOrgEntity psv);
	
	/**
	 * eventCode 必须传
	 * @Description: 根据条件统计组织信息集合
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public Long queryOrgCountByParam(PriceEvnetEntity psv);
	
	/**
	 * 
	 * @Description: 查询所有子节点组织code集合
	 * @param @return   
	 * @return List<Map<String,Object>> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月8日
	 */
	public List<Map<String, Object>> getLeafOrgs();
	
	/**
	 * 
	 * @Description: 根据条件查询优惠折扣详细
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceEvnetEntity> 
	 * @throws
	 * @author 275636
	 * @date 2016年1月12日
	 */
	public List<PriceEvnetEntity> queryPriceEvnetById(PriceEvnetEntity psv);


	/**
	 * 
	 * @Description: 批量导入区域范围数据到临时表
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceEvnetEntity> 
	 * @throws
	 * @author 275636
	 * @date 2016年1月16日
	 */
	public void tableImport(Map<String, Object> mapInsert);


	/**
	 * 
	 * @Description: 清理区域范围临时表里面重复数据
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceEvnetEntity> 
	 * @throws
	 * @author 275636
	 * @date 2016年1月16日
	 */
	public void clearRepeatData();


	/**
	 * 
	 * @Description: 清空区域范围临时表数据
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceEvnetEntity> 
	 * @throws
	 * @author 275636
	 * @date 2016年1月16日
	 */
	public void deleteCorpImportTemp();
	
}

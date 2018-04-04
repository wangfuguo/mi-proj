/**   
 * @Title: PriceCustomerDao.java 
 * @Package com.hoau.miser.module.biz.pricecard.server.dao 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月5日 下午1:37:19 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerEntity;

/**
 * 客户专属价格数据接入 ClassName: PriceCustomerDao
 * 
 * @author dengyin
 * @date 2016年1月5日
 * @version V1.0
 */
@Repository
public interface PriceCustomerDao {

	int deleteByPrimaryKey(String id);

	int insert(PriceCustomerEntity priceCustomerEntity);

	int insertSelective(PriceCustomerEntity priceCustomerEntity);

	PriceCustomerEntity selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(PriceCustomerEntity priceCustomerEntity);

	int updateByPrimaryKey(PriceCustomerEntity priceCustomerEntity);

	// 客户专属界面实际查询 SQL
	List<PriceCustomerEntity> queryPriceCustomerMixed(
			PriceCustomerEntity priceCustomerEntity, RowBounds rowBounds);
	
	List<PriceCustomerEntity> queryPriceCustomerMixed(PriceCustomerEntity priceCustomerEntity);
	
	Long countOfPriceCustomerMixed(PriceCustomerEntity priceCustomerEntity);

	// 根据客户编号查询客户对应的部分组织信息
	PriceCustomerEntity queryPriceCustomerInfoByCode(String customerCode);

	// 更新对应记录为 失效
	public int updateForInvalidById(PriceCustomerEntity priceCustomerEntity);

	/**
	 * @author dengyin
	 * @date 2016年1月14日
	 */
	void destoryPriceCustomerForWaitByIdStr(Map<String, Object> paramMap);
	
	void destoryPriceCustomerForEffectiveByIdStr(Map<String, Object> paramMap);
	

	/**
	 * @Description: 校验即将新增的 客户专属价格生效 失效时间段 是否存在重复的时间段
	 *               每个客户在生效中的专属价格只能有一个。
	 *               所以提交时需验证该客户在同一部门是否存在有效时间重合，且当前状态为生效中的数据
	 * @param @param priceCustomerEntity
	 * @param @return
	 * @return int
	 * @throws
	 * @author dengyin
	 * @date 2016年1月18日
	 */
	public int checkCustomerPriceDiscountExistRepeatTimezone(
			PriceCustomerEntity priceCustomerEntity);

	/**
	 * 
	 * @Description: 查看界面导出客户价格时读取源数据使用
	 * @param @param vo
	 * @param @return
	 * @return List<ExcelPriceCustomerEntity>
	 * @throws
	 * @author 赵金义
	 * @date 2016年1月20日
	 */
	public List<ExcelPriceCustomerEntity> excelQueryListByParam(
			PriceCustomerEntity entity);
	
	public List<ExcelPriceCustomerEntity> excelQueryListByParamForStandard(
			PriceCustomerEntity entity);	
	
	/**
	 * @Description:根据 id 串返回包含 状态 的记录
	 * @param  paramMap
	 * @return List<PriceCustomerEntity>
	 * @throws
	 * @author dengyin
	 * @date 2016年1月22日
	 */
	public List<PriceCustomerEntity> selectPriceCustomerEntityIncludeStatusIdStr(Map<String,Object> paramMap);
	
	/**
	 * 针对于 生效中 的主表数据进行修改操作
	 * @param priceCustomerEntity
	 */
	public void updateForEffectiveById(PriceCustomerEntity priceCustomerEntity);
	
	
	public PriceCustomerEntity selectPriceCustomerListForLastByCode(String customerCode);
}

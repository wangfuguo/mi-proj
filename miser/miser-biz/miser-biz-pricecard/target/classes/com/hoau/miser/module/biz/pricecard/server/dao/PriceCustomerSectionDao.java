package com.hoau.miser.module.biz.pricecard.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceCustomerSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionEntity;

/**
 * 易入户客户价格数据接入 ClassName: PriceCustomerSectionDao
 * 
 * @author 何羽
 * @date 2016年5月3日
 * @version V2.0
 */
@Repository
public interface PriceCustomerSectionDao {

	int deleteByPrimaryKey(String id);
	
	int insert(PriceCustomerSectionEntity priceCustomerSectionEntity);
	
	int insertSelective(PriceCustomerSectionEntity priceCustomerSectionEntity);
	
	PriceCustomerSectionEntity selectByPrimaryKey(String id);
	
	int updateByPrimaryKeySelective(PriceCustomerSectionEntity priceCustomerSectionEntity);

	int updateByPrimaryKey(PriceCustomerSectionEntity priceCustomerSectionEntity);
	
	// 客户专属界面实际查询 SQL
	List<PriceCustomerSectionEntity> queryPriceCustomerSectionMixed(
			PriceCustomerSectionEntity priceCustomerSectionEntity, RowBounds rowBounds);
	
	List<PriceCustomerSectionEntity> queryPriceCustomerSectionMixed(PriceCustomerSectionEntity priceCustomerSectionEntity);
	
	Long countOfPriceCustomerMixed(PriceCustomerSectionEntity priceCustomerSectionEntity);
	
	/**
	 * @Description: 校验即将新增的 客户专属价格生效 失效时间段 是否存在重复的时间段
	 *               每个客户在生效中的专属价格只能有一个。
	 *               所以提交时需验证该客户在同一部门是否存在有效时间重合，且当前状态为生效中的数据
	 * @param @param priceCustomerSectionEntity
	 * @param @return
	 * @return int
	 * @throws
	 * @author 何羽
	 * @date 2016年5月5日
	 */
	public int checkCustomerPriceDiscountExistRepeatTimezone(
			PriceCustomerSectionEntity priceCustomerSectionEntity);
	
	/**
	 * 针对于 生效中 的主表数据进行修改操作
	 * @param priceCustomerEntity
	 */
	public void updateForEffectiveById(PriceCustomerSectionEntity priceCustomerEntity);
	
	// 更新对应记录为 失效
	public int updateForInvalidById(PriceCustomerSectionEntity priceCustomerEntity);
	
	/**
	 * @Description:根据 id 串返回包含 状态 的记录
	 * @param  paramMap
	 * @return List<PriceCustomerEntity>
	 * @throws
	 * @author 何羽
	 * @date 2016年5月6日
	 */
	public List<PriceCustomerSectionEntity> selectPriceCustomerEntityIncludeStatusIdStr(Map<String,Object> paramMap);
	
	/**
	 * @author 何羽
	 * @date 2016年5月6日
	 */
	void destoryPriceCustomerForWaitByIdStr(Map<String, Object> paramMap);
	
	void destoryPriceCustomerForEffectiveByIdStr(Map<String, Object> paramMap);
	
	/**
	 * 
	 * @Description: 查看界面导出客户价格时读取源数据使用
	 * @param @param vo
	 * @param @return
	 * @return List<ExcelPriceCustomerEntity>
	 * @throws
	 * @author 何羽
	 * @date 2016年5月6日
	 */
	public List<ExcelPriceCustomerSectionEntity> excelQueryListByParam(
			PriceCustomerSectionEntity entity);
	
	public List<ExcelPriceCustomerSectionEntity> excelQueryListByParamForStandard(
			PriceCustomerSectionEntity entity);	

}

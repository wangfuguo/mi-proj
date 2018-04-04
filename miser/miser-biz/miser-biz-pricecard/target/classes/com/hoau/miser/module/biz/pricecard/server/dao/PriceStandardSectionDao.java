package com.hoau.miser.module.biz.pricecard.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceStandardSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardSectionVo;

/**
 * @ClassName: PriceStandardSectionDao
 * @Description: 易入户标准价格管理Dao
 * @author zhangqingfu
 * @date 2016年5月4日 下午2:02:20
 *
 */
@Repository
public interface PriceStandardSectionDao {

	/**
	 * @Title: queryPageByCondition
	 * @Description: 根据条件查询分页结果集
	 * @return List<PriceStandardSectionEntity> 易入户标准价格结果集
	 * @param vo
	 *            易入户标准价格查询条件
	 * @param rowBounds
	 *            分页条件
	 */
	List<PriceStandardSectionEntity> queryPageByCondition(PriceStandardSectionVo vo, RowBounds rowBounds);

	/**
	 * @Title: queryAllByCondition
	 * @Description: 根据条件查询所有结果
	 * @return List<PriceStandardSectionEntity> 易入户标准价格结果集
	 * @param vo
	 *            易入户标准价格查询条件
	 */
	List<PriceStandardSectionEntity> queryAllByCondition(PriceStandardSectionVo vo);

	/**
	 * @Title: queryCountByCondition
	 * @Description: 根据查询条件查询记录数
	 * @return Long 符合条件记录数
	 * @param vo
	 *            易入户标准价格查询条件
	 */
	Long queryCountByCondition(PriceStandardSectionVo vo);

	/**
	 * @Title: queryById
	 * @Description: 根据ID查询易入户标准价格结果
	 * @return PriceStandardSectionEntity 易入户标准价格实体
	 * @param id
	 *            主键id
	 */
	PriceStandardSectionEntity queryById(String id);

	/**
	 * @Title: queryActive
	 * @Description: 根据条件查询易入户标准价格结果
	 * @return List<PriceStandardSectionEntity>
	 * @param map
	 *            1、transTypeCode 运输类型 2、sendPriceCity 出发价格城市 3、arrivePriceCity
	 *            到达价格城市 4、active 是否有效
	 */
	List<PriceStandardSectionEntity> queryActive(Map<String, Object> map);

	/**
	 * @Title: addPriceStandardSection
	 * @Description: 新增易入户标准价格
	 * @return int
	 * @param entity
	 *            易入户标准价格实体
	 */
	Integer addPriceStandardSection(PriceStandardSectionEntity entity);

	/**
	 * @Title: updatePriceStandardSection
	 * @Description: 修改易入户标准价格
	 * @return int
	 * @param entity
	 *            易入户标准价格实体
	 */
	Integer updatePriceStandardSection(PriceStandardSectionEntity entity);

}

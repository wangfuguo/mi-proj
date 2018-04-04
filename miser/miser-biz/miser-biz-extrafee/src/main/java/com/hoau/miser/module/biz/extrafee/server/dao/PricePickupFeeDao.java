package com.hoau.miser.module.biz.extrafee.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.extrafee.shared.domain.PricePickupFeeEntity;

/**
 * ClassName: PricePickupFeeDao 
 * @author 刘海飞
 * @date 2016年1月4日
 * @version V1.0
 */
@Repository
public interface PricePickupFeeDao {
	
/**
 * 
 * @Description: TODO查询提货费list
 * @param @param entity
 * @param @param rowBounds
 * @param @return   
 * @return List<PricePackageFeeItemsEntity> 
 * @throws
 * @author 刘海飞
 * @date 2016年1月4日
 */
List<PricePickupFeeEntity> queryPricePickupFeeByParam(PricePickupFeeEntity entity, RowBounds rowBounds);

/**
 * @Description: TODO统计条数
 * @param @param entity
 * @param @return   
 * @return Long 
 * @throws
 * @author 刘海飞
 * @date 2016年1月4日
 */
Long queryCountByParam(PricePickupFeeEntity entity);

/**
 * @Description: TODO修改提货费
 * @param @param entity   
 * @return void 
 * @throws
 * @author 刘海飞
 * @date 2016年1月4日
 */
void updatePricePickupFeeByEntity(PricePickupFeeEntity entity);

/**
 * @Description: TODO删除提货费
 * @param @param entity   
 * @return void 
 * @throws
 * @author 刘海飞
 * @date 2016年1月4日
 */
void deletePricePickupFeeByEntity(PricePickupFeeEntity entity);

/**
 * @Description: TODO增加提货费
 * @param @param entity   
 * @return void 
 * @throws
 * @author 刘海飞
 * @date 2016年1月4日
 */
void addPricePickupFeeByEntity(PricePickupFeeEntity entity);

/**
 * @Description: TODO 查询
 * @param @param id
 * @param @return   
 * @return PricePickupFeeEntity 
 * @throws
 * @author 刘海飞
 * @date 2016年1月4日
 */
PricePickupFeeEntity queryPricePickupFeeByEntity(PricePickupFeeEntity entity);
/**
 * @Description: TODO根据价格城市code和运输类型判断 唯一
 * @param @param map
 * @param @return   
 * @return Long 
 * @throws
 * @author 刘海飞
 * @date 2016年1月4日
 */
PricePickupFeeEntity queryPricePickupFeeByStats(PricePickupFeeEntity entity);

/**
 * @Description: TODO作废待生效的 
 * @param @param entity   
 * @return void 
 * @throws
 * @author 刘海飞
 * @date 2016年1月7日
 */
void updatePricePickupFeeByState3(PricePickupFeeEntity entity);

/**
 * @Description: TODO修改生效中的失效时间
 * @param @param entity   
 * @return void 
 * @throws
 * @author 刘海飞
 * @date 2016年1月7日
 */
void updatePricePickupFeeByState2(PricePickupFeeEntity entity);
/**
 * 
 * @Description: 导出功能查询
 * @param @param entity
 * @param @return   
 * @return List<PricePickupFeeEntity> 
 * @throws
 * @author 刘海飞
 * @date 2016年4月11日
 */
List<PricePickupFeeEntity> queryPricePickupFeeByParamExport(PricePickupFeeEntity entity);
}

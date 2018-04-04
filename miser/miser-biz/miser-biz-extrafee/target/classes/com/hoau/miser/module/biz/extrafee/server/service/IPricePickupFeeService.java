package com.hoau.miser.module.biz.extrafee.server.service;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PricePickupFeeEntity;



/**
 * ClassName: IPricePickupFeeService 
 * @author 刘海飞
 * @date 2016年1月4日
 * @version V1.0
 */
public interface IPricePickupFeeService {

/**
 * @Description: TODO查询提货费列表
 * @param @param entity
 * @param @param limit
 * @param @param start
 * @param @return   
 * @return List<PricePickupFeeEntity> 
 * @throws
 * @author 刘海飞
 * @date 2016年1月4日
 */
List<PricePickupFeeEntity> queryPricePickupFeeByParam(PricePickupFeeEntity entity,int limit,
			int start);
/**
 * @Description: TODO查询条数
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
Integer updatePricePickupFeeByEntity(PricePickupFeeEntity entity,boolean isConver);

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
Integer addPricePickupFeeByEntity(PricePickupFeeEntity entity,boolean isConver);

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
 * @Description: 导出：创建Excel
 * @param list
 * @return String 
 * @throws
 * @author 286330付于令
 * @date 2016年4月5日
 */
String createExcel(List<PricePickupFeeEntity> list);

/**
 * @Description: 导出功能查询
 * @param @param entity
 * @param @return   
 * @return List<PricePickupFeeEntity> 
 * @throws
 * @author 刘海飞
 * @date 2016年4月11日
 */
List<PricePickupFeeEntity> queryPricePickupFeeByParam(PricePickupFeeEntity entity);
}

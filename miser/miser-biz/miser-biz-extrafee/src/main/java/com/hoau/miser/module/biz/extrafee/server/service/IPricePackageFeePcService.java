package com.hoau.miser.module.biz.extrafee.server.service;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PricePackageFeePcEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PricePackageFeePcVo;

/**
 * 价格城市包装费Service
 * ClassName: IPricePackageFeePcService 
 * @author 廖文强
 * @date 2016年1月19日
 * @version V1.0
 */
public interface IPricePackageFeePcService {

	/**
	 * 
	 * @Description: 根据参数查询信息集合
	 * @param @param ppFeePcVO
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PricePackageFeePcEntity> 
	 * @throws
	 * @author 292078
	 * @date 2016年1月19日
	 */
	List<PricePackageFeePcEntity> queryListByParam(PricePackageFeePcVo ppFeePcVO, int limit, int start);

	/**
	 * 
	 * @Description: 根据参数查询信息数量
	 * @param @param ppFeePcVO
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 292078
	 * @date 2016年1月19日
	 */
	Long queryCountByParam(PricePackageFeePcVo ppFeePcVO);

	/**
	 * 
	 * @Description: 根据ip获取对象
	 * @param @param id
	 * @param @return   
	 * @return PricePackageFeePcEntity 
	 * @throws
	 * @author 292078
	 * @date 2016年1月19日
	 */
	PricePackageFeePcEntity queryPpFeePcById(String id);

	/**
	 * 
	 * @Description: 增加包装费
	 * @param @param ppFeePcEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2016年1月19日
	 */
	void addPpFeePc(PricePackageFeePcEntity ppFeePcEntity);

	/**
	 * 
	 * @Description: 修改包装费
	 * @param @param ppFeePcEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2016年1月19日
	 */
	void updatePpFeePc(PricePackageFeePcEntity ppFeePcEntity);

	/**
	 * 
	 * @Description: 删除包装费
	 * @param @param ppFeePcEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2016年1月19日
	 */
	void delFeePc(PricePackageFeePcEntity ppFeePcEntity);

}

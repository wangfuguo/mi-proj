package com.hoau.miser.module.biz.extrafee.server.service;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PricePackageFeeStandardEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PricePackageFeeStandardVO;

/**
 * 包装费标准service
 * ClassName: IPricePackageFeeStandardService 
 * @author 292078
 * @date 2015年12月28日
 * @version V1.0
 */
public interface IPricePackageFeeStandardService {

	/**
	 * 
	 * @Description: 根据参数查询信息集合
	 * @param @param ppfeeStandardVO
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PricePackageFeeStandardEntity> 
	 * @throws
	 * @author 292078
	 * @date 2015年12月28日
	 */
	List<PricePackageFeeStandardEntity> queryListByParam(PricePackageFeeStandardVO ppfeeStandardVO, int limit, int start);

	/**
	 * 
	 * @Description: 根据参数查询信息数量
	 * @param @param ppfeeStandardVO
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 292078
	 * @date 2015年12月28日
	 */
	Long queryCountByParam(PricePackageFeeStandardVO ppfeeStandardVO);

	/**
	 * 
	 * @Description: 根据ip获取对象
	 * @param @param id
	 * @param @return   
	 * @return PricePackageFeeStandardEntity 
	 * @throws
	 * @author 292078
	 * @date 2015年12月28日
	 */
	PricePackageFeeStandardEntity queryPpFeeStandardById(String id);

	/**
	 * 
	 * @Description: 增加包装费
	 * @param @param ppFeeStandardEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月28日
	 */
	void addPpFeeStandard(PricePackageFeeStandardEntity ppFeeStandardEntity);

	/**
	 * 
	 * @Description: 修改包装费
	 * @param @param ppFeeStandardEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月28日
	 */
	void updatePpFeeStandard(PricePackageFeeStandardEntity ppFeeStandardEntity);

	/**
	 * 
	 * @Description: 删除包装费
	 * @param @param ppFeeStandardEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月28日
	 */
	void delFeeStandard(PricePackageFeeStandardEntity ppFeeStandardEntity);

}

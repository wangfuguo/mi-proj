package com.hoau.miser.module.biz.extrafee.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.extrafee.shared.domain.PricePackageFeeStandardEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PricePackageFeeStandardVO;

/**
 * 标准包装费dao
 * ClassName: PricePackageFeeStandardDao 
 * @author 292078
 * @date 2015年12月28日
 * @version V1.0
 */
@Repository
public interface PricePackageFeeStandardDao {

	/**
	 * 
	 * @Description: 查询标准包装费集合
	 * @param @param ppfeeStandardVO
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PricePackageFeeStandardEntity> 
	 * @throws
	 * @author 292078
	 * @date 2015年12月28日
	 */
	List<PricePackageFeeStandardEntity> queryListByParam(PricePackageFeeStandardVO ppfeeStandardVO, RowBounds rowBounds);

	/**
	 * 
	 * @Description: 统计包装费信息数
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
	 * @Description: 增加一条信息
	 * @param @param ppFeeStandardEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月28日
	 */
	void addPpFeeStandard(PricePackageFeeStandardEntity ppFeeStandardEntity);

	/**
	 * 
	 * @Description: 删除信息
	 * @param @param ppFeeStandardEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2015年12月28日
	 */
	void delFeeStandard(PricePackageFeeStandardEntity ppFeeStandardEntity);

}

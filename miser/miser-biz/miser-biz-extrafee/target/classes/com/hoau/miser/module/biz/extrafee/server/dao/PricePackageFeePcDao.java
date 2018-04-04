package com.hoau.miser.module.biz.extrafee.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.extrafee.shared.domain.PricePackageFeePcEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PricePackageFeePcVo;

/**
 * 标准价格城市包装费dao
 * ClassName: PricePackageFeePcDao 
 * @author 292078
 * @date 2016年1月19日
 * @version V1.0
 */
@Repository
public interface PricePackageFeePcDao {

	/**
	 * 
	 * @Description: 查询标准包装费集合
	 * @param @param ppFeePcVO
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PricePackageFeePcEntity> 
	 * @throws
	 * @author 292078
	 * @date 2016年1月19日
	 */
	List<PricePackageFeePcEntity> queryListByParam(PricePackageFeePcVo ppFeePcVO, RowBounds rowBounds);

	/**
	 * 
	 * @Description: 统计包装费信息数
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
	 * @Description: 增加一条信息
	 * @param @param ppFeePcEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2016年1月19日
	 */
	void addPpFeePc(PricePackageFeePcEntity ppFeePcEntity);

	/**
	 * 
	 * @Description: 删除信息
	 * @param @param ppFeePcEntity   
	 * @return void 
	 * @throws
	 * @author 292078
	 * @date 2016年1月19日
	 */
	void delPpFeePc(PricePackageFeePcEntity ppFeePcEntity);

}

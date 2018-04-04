package com.hoau.miser.module.biz.extrafee.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceExtrafeeStandardEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.ExtrafeeAddValueFeeVo;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceExtrafeeStandardVo;
/**
 * 
 * @Description: 标准附加费Dao
 * @Author 292078
 * @Date 2015年12月15日
 */
@Repository
public interface PriceExtrafeeStandardDao {

	/**
	 * 
	 * @Description: 查询标准附加费列表
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceExtrafeeStandardEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public List<PriceExtrafeeStandardEntity> queryListByParam(PriceExtrafeeStandardVo psv, RowBounds rowBounds);
	
	/**
	 * 
	 * @Description: 统计标准附加费数量
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public Long queryCountByParam(PriceExtrafeeStandardVo psv);
	/**
	 * 
	 * @Description: 新增标准附加费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public void addPriceExtrafeeStandard(PriceExtrafeeStandardEntity pse);
	/**
	 * 
	 * @Description: 修改标准附加费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public void updatePriceExtrafeeStandard(PriceExtrafeeStandardEntity pse);
	/**
	 * 
	 * @Description: 删除标准附加费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public void delPriceExtrafeeStandard(PriceExtrafeeStandardEntity pse);

	/**
	 * 没分页
	 * @Description: TODO描述该方法是做什么的
	 * @param @param psv
	 * @param @return   
	 * @return List<PriceExtrafeeStandardEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月5日
	 */
	public List<PriceExtrafeeStandardEntity> queryListByParam(PriceExtrafeeStandardVo psv);

	
}

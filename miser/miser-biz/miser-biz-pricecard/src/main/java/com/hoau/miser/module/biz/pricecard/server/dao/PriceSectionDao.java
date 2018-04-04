package com.hoau.miser.module.biz.pricecard.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionSubVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionVo;
/**
 * 优惠分段
 * ClassName: PriceSectionDao 
 * @author 王茂
 * @date 2015年12月22日
 * @version V1.0
 */
@Repository
public interface PriceSectionDao {

	/**
	 * 
	 * @Description: 查询优惠分段列表
	 * @param @return   
	 * @return List<PriceSectionEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2016年1月7日
	 */
	public List<PriceSectionEntity> queryPriceSection(PriceSectionEntity pe);
	
	public PriceSectionEntity queryPriceSectionOne(PriceSectionEntity pe);
	/**
	 * 
	 * @Description: 查询优惠分段列表
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceSectionEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public List<PriceSectionEntity> queryListByParam(PriceSectionVo psv, RowBounds rowBounds);
	
	/**
	 * 
	 * @Description: 统计优惠分段数量
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public Long queryCountByParam(PriceSectionVo psv);
	/**
	 * 
	 * @Description: 新增优惠分段
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public void addPriceSection(PriceSectionEntity pse);
	/**
	 * 
	 * @Description: 修改
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public void updatePriceSection(PriceSectionEntity pse);
	/**
	 * 
	 * @Description: 删除优惠分段
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public void delPriceSection(PriceSectionEntity pse);

	/**
	 * 
	 * @Description: 查询优惠分段明细列表
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceSectionSubEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public List<PriceSectionSubEntity> queryListByParamSub(PriceSectionSubVo psv, RowBounds rowBounds);
	
	/**
	 * 
	 * @Description: 统计优惠分段明细数量
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public Long queryCountByParamSub(PriceSectionSubVo psv);
	/**
	 * 
	 * @Description: 新增优惠分段明细
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public void addPriceSectionSub(PriceSectionSubEntity pse);
	/**
	 * 
	 * @Description: 修改优惠分段明细
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public void updatePriceSectionSub(PriceSectionSubEntity pse);
	/**
	 * 
	 * @Description: 删除优惠分段明细
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public void delPriceSectionSub(PriceSectionSubEntity pse);
	
	/**
	 * @Description: 根据费用分段编号查询费用分段，带分段明细数据
	 * @param code   费用分段编号
	 * @return PriceSectionEntity	费用分段数据 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月27日
	 */
	public PriceSectionEntity queryPriceSectionByCode(String code);
	
	/**
	 * @Description: 根据融合分段来源查询分段
	 * @param pse	融合分段来源
	 * @return PriceSectionEntity 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月19日
	 */
	public ArrayList<PriceSectionEntity> queryPriceSectionByReuniteSource(PriceSectionEntity pse);
	
	/**
	 * @Description: 导出excel查询
	 * @param @param entity
	 * @param @return   
	 * @return List<PriceSectionEntity> 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年4月6日
	 */
	public List<PriceSectionEntity> excelQueryListByParam(PriceSectionEntity entity);
}

package com.hoau.miser.module.biz.pricecard.api.server.service;

import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionSubVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionVo;

/**
 * 优惠分段
 * ClassName: IPriceSectionService 
 * @author 王茂
 * @date 2015年12月22日
 * @version V1.0
 */
public interface IPriceSectionService {

	
	/**
	 * 
	 * @Description: 公共选择器查询优惠分段
	 * @param @param psv
	 * @param @return   
	 * @return List<PriceSectionEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2016年1月7日
	 */
	public List<PriceSectionEntity> queryPriceSection(PriceSectionVo psv);
	/**
	 * 
	 * @Description: 查询优惠分段集合信息
	 * @param param
	 * @param limit
	 * @param start
	 * @return List<PriceSectionEntity>  
	 * @Author 292078
	 * @Time 2015年12月15日下午1:28:34
	 */
	public List<PriceSectionEntity> queryListByParam(PriceSectionVo psv, int limit, int start);
	/**
	 * 
	 * @Description: 统计优惠分段数量
	 * @param param
	 * @return Long  
	 * @Author 292078
	 * @Time 2015年12月15日下午4:37:17
	 */
	public Long queryCountByParam(PriceSectionVo psv);
	/**
	 * 
	 * @Description: 添加优惠分段
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public void addPriceSection(PriceSectionEntity pse,String priceSectionSubList);
	/**
	 * 
	 * @Description: 修改优惠分段
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public void updatePriceSection(PriceSectionEntity pse,String priceSectionSubList);
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
	 * @Description: 查询优惠分段明细
	 * @param @param id
	 * @param @return   
	 * @return PriceSectionEntity 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public PriceSectionEntity queryPriceSectionById(String id);
	/**
	 * 
	 * @Description: 查询优惠分段明细列表
	 * @param @param psv
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PriceSectionSubEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public List<PriceSectionSubEntity> queryListByParamSub(PriceSectionSubVo psv, int limit, int start);
	/**
	 * 
	 * @Description: 查询优惠分段明细条数
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public Long queryCountByParam(PriceSectionSubVo psv);
	/**
	 * 
	 * @Description: 添加优惠分段明细
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
	 * 
	 * @Description: 根据ID获取优惠分段明细
	 * @param @param id
	 * @param @return   
	 * @return PriceSectionSubEntity 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public PriceSectionSubEntity queryPriceSectionSubById(String id);
	
	/**
	 * @Description: 将多个费用分段重新融合生成一个新的分段
	 * @param sectionCodes	需要进行融合的多个分段的编码
	 * @return PriceSectionEntity 融合后的新的分段对象
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月26日
	 */
	public PriceSectionEntity reuniteSections(String ... sectionCodes);
	
	/**
	 * @Description: 根据费用分段的编码获取费用分段及分段明细
	 * @param code	费用分段编码
	 * @return PriceSectionEntity 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月24日
	 */
	public PriceSectionEntity queryPriceSectionByCode(String code);

	public PriceSectionEntity reuniteSectionsList(List<String> str);
	
	/**
	 * 
	 * @Description: 添加同步优惠分段
	 * @param @param pse   
	 * @return String 
	 * @throws
	 * @author 陈启勇
	 * @date 2016年3月7日
	 */
	public String addSyncPriceSection(PriceSectionEntity pse,String priceSectionSubList);

	/**
	 * @Description: 导出
	 * @param @param psv
	 * @param @return   
	 * @return List<PriceSectionEntity> 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年4月5日
	 */
	public List<PriceSectionEntity> excelQueryListByParam(PriceSectionVo psv);
	/**
	 * @Description: TODO创建导出文件
	 * @param @param list
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年4月6日
	 */
	public String createExcelFile(List<PriceSectionEntity> list);
}

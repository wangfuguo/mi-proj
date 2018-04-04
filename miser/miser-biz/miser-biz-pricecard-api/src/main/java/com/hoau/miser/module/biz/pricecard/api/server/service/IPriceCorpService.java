package com.hoau.miser.module.biz.pricecard.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpGirdVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpVo;

public interface IPriceCorpService {
	
	/**
	 * 
	 * @Description: TODO根据参数查询
	 * @param @param priceCorpVo
	 * @param @param limt
	 * @param @param start
	 * @param @return   
	 * @return List<PriceCorpGirdVo> 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月7日
	 */
	public List<PriceCorpGirdVo> queryListByParam(PriceCorpVo priceCorpVo,int limit,int start);
	
	/**
	 * 
	 * @Description: TODO 统计总条数
	 * @param @param priceCorpVo
	 * @param @return   
	 * @return long 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年3月14日
	 */
	public long queryListByParamCount(PriceCorpVo priceCorpVo);
	
	/**
	 * 
	 * @Description: TODO作废信息
	 * @param @param pcgv
	 * @param @return   
	 * @return Integer 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月14日
	 */
	public Integer cancelByPrimaryKey(List<PriceCorpGirdVo> pcgv);
	
	/**
	 * 
	 * @Description: TODO 根据明细查询
	 * @param @param priceCorpVo
	 * @param @return   
	 * @return PriceCorpGirdVo 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月14日
	 */
	public List<PriceCorpGirdVo> findByPriceCorpGirdVo(PriceCorpGirdVo priceCorpVo);
	
	/**
	 * 
	 * @Description: TODO 获取数据库时间
	 * @param @return   
	 * @return Date 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月20日
	 */
	public Date queryCurrentDateTime();
	
	/**
	 * 
	 * @Description: TODO 批量新增
	 * @param @param pcgv
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月21日
	 */
	public void insertBatch(PriceCorpVo pcgv);
	
	/**
	 * 
	 * @Description: 批量导入网点专属价格明细，不插入数据库，只填充页面表单
	 * @param path
	 * @return Map<String,Object> 成功数量，失败数量，返回action的Entity，错误记录.
	 * @throws Exception
	 * @author 赵金义
	 * @date 2016年1月14日
	 */
	public Map<String, Object> bathImplPriceCorps(PriceCorpGirdVo priceCorpVo) throws Exception;
	
	/**
	 * @Description: 生成Excel导出文件，导出某个网店详细列表信息
	 * @param priceCorpVo 根据前台显示的网店查出详细再导出excel
	 * @return String 
	 * @throws Exception
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	public String createExcelFile(List<PriceCorpGirdVo> details);
	
}

package com.hoau.miser.module.biz.pricecard.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCorpSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpSectionVo;

public interface IPriceCorpSectionService {
	
	/**
	 * 
	 * @Description: TODO 批量新增
	 * @param @param vo   
	 * @return void 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月7日
	 */
	void insertBatch(PriceCorpSectionVo vo);
	
	/**
	 * 
	 * @Description: TODO 查询当前时间
	 * @param @return   
	 * @return Date 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月7日
	 */
	Date queryCurrentDateTime();
	
	/**
	 * 
	 * @Description: TODO 分组查询主表内容
	 * @param @param pcse
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PriceCorpSectionEntity> 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月7日
	 */
	List<PriceCorpSectionEntity> queryGroup(PriceCorpSectionEntity pcse,int limit, int start);
	
	/**
	 * 
	 * @Description: TODO 统计分组内容
	 * @param @param pcse
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月7日
	 */
	Long queryGroupCount(PriceCorpSectionEntity pcse);
	
	/**
	 * 
	 * @Description: TODO 作废
	 * @param @param list   
	 * @return void 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月7日
	 */
	void revoke(List<PriceCorpSectionEntity> list);
	
	/**
	 * 
	 * @Description: TODO 查询明细信息
	 * @param @param pcse
	 * @param @return   
	 * @return List<PriceCorpSectionEntity> 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月7日
	 */
	List<PriceCorpSectionEntity> search(PriceCorpSectionEntity pcse);
	
	/**
	 * 
	 * @Description: TODO 生成导出Excel
	 * @param @param list
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月8日
	 */
	String exportExcel(List<PriceCorpSectionEntity> list);
	
	/**
	 * 
	 * @Description: TODO 批量导入
	 * @param @param filePath
	 * @param @return   
	 * @return Map<String,Object> 
	 * @throws
	 * @author Liwy
	 * @date 2016年5月9日
	 */
	Map<String, Object> impl(String filePath) throws Exception;
	
	/**
	 * 
	 * @Description: TODO 重组表格数据主要 针对数据缺失
	 * @param @param vo
	 * @param @return   
	 * @return List<PriceCorpSectionEntity> 
	 * @throws
	 * @author Liwy
	 * @date 2016年7月1日
	 */
	List<PriceCorpSectionEntity> reform(PriceCorpSectionVo vo);
	
}

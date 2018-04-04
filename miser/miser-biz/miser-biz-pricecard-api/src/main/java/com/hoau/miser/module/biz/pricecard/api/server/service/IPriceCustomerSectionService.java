package com.hoau.miser.module.biz.pricecard.api.server.service;

import java.io.IOException;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceCustomerSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerSectionVo;

/**
 * 易入户客户价格接口定义 ClassName: IPriceCustomerSectionService
 * 
 * @author 何羽
 * @date 2016年5月3日
 * @version V2.0
 */
public interface IPriceCustomerSectionService {

	/**
	 * @param start
	 * @param limit
	 * @Description: 易入户客户价格界面实际查询 SQL
	 * @param @param priceCustomerSectionEntity
	 * @param @return
	 * @return List<PriceCustomerSectionEntity>
	 * @throws
	 * @author 何羽
	 * @date 2016年5月3日
	 */
	public List<PriceCustomerSectionEntity> queryPriceCustomerMixed(
			PriceCustomerSectionEntity priceCustomerSectionEntity, int limit, int start);
	
	public Long countOfPriceCustomerMixed(PriceCustomerSectionEntity priceCustomerEntity);
	
	/**
	 * @Description: 保存易入户客户价格 及其明细
	 * @param @param PriceCustomerSectionVo
	 * @return void
	 * @throws
	 * @author 何羽
	 * @date 2016年5月5日
	 */
	public String addCustomerAndSubItemList(PriceCustomerSectionVo priceCustomerSectionVo);
	
	
	/**
	 * @Description: 修改易入户客户专属价格 及其明细
	 * @param @param PriceCustomerSectionVo
	 * @return void
	 * @throws
	 * @author 何羽
	 * @date 2016年5月5日
	 */
	public String updateCustomerAndSubItemList(PriceCustomerSectionVo priceCustomerSectionVo);
	
	/**
	 * @Description: 对选中的 客户专属记录 及其副表的明细记录 统一作废实现
	 * @param destoryIdStr
	 *            ： 选中的客户专属记录的 id 拼接串 '1','2'
	 * @return String
	 * @throws
	 * @author 何羽
	 * @date 2016年5月6日
	 */
	public String destoryPriceCustomerAndSubList(String destoryIdStr);
	
	/**
	 * 
	 * @Description: TODO描述该方法是做什么的
	 * @param @param priceCustomerEntity
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 李玮琰
	 * @date 2016年4月7日
	 */
	public List<PriceCustomerSectionEntity> excelPriceCustomer(PriceCustomerSectionEntity priceCustomerEntity);
	
	public String createExcelFileForCustomer(List<PriceCustomerSectionEntity> list) throws IOException ;
	
	/**
	 * 
	 * @Description: 查询导出客户价格明细的数据
	 * @param @param priceCustomerVo
	 * @param @return
	 * @return List<ExcelPriceCustomerSectionEntity>
	 * @throws
	 * @author 何羽
	 * @date 2016年5月6日
	 */
	public List<ExcelPriceCustomerSectionEntity> queryExcelListByParam(
			PriceCustomerSectionVo priceCustomerVo);
	
	public List<ExcelPriceCustomerSectionEntity> excelQueryListByParamForStandard(
			PriceCustomerSectionVo priceCustomerVo);	

	/**
	 * 
	 * @Description: 根据查询导出客户价格明细的数据生成excel文件返回地址
	 * @param @param list
	 * @param @return
	 * @return String
	 * @throws IOException 
	 * @throws
	 * @author 赵金义
	 * @date 2016年1月20日
	 */
	public String createExcelFile(List<ExcelPriceCustomerSectionEntity> list,String exportType) throws IOException;

	
}

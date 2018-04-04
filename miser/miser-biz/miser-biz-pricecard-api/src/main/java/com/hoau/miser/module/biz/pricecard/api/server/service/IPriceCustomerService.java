/**   
 * @Title: IPriceCustomerService.java 
 * @Package com.hoau.miser.module.biz.pricecard.server.service 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月5日 下午1:41:34 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.api.server.service;

import java.io.IOException;
import java.util.List;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerVo;

/**
 * 客户专属价格接口定义 ClassName: IPriceCustomerService
 * 
 * @author dengyin
 * @date 2016年1月5日
 * @version V1.0
 */
public interface IPriceCustomerService {

	/**
	 * @Description: 保存 客户价格 及其明细
	 * @param @param priceCustomerVo
	 * @return void
	 * @throws
	 * @author dengyin
	 * @date 2016年1月11日
	 */
	public String addCustomerAndSubItemList(PriceCustomerVo priceCustomerVo);

	/**
	 * @param start
	 * @param limit
	 * @Description: 客户专属界面实际查询 SQL
	 * @param @param priceCustomerEntity
	 * @param @return
	 * @return List<PriceCustomerEntity>
	 * @throws
	 * @author dengyin
	 * @date 2016年1月12日
	 */
	public List<PriceCustomerEntity> queryPriceCustomerMixed(
			PriceCustomerEntity priceCustomerEntity, int limit, int start);
	
	
	Long countOfPriceCustomerMixed(PriceCustomerEntity priceCustomerEntity);

	/**
	 * @Description: 根据 组织编码 返回其父层的记录 统一设定到 PriceCustomerEntity 这里只会涉及 组织的信息
	 * @param @param code
	 * @param @return
	 * @return PriceCustomerEntity
	 * @throws
	 * @author dengyin
	 * @date 2016年1月13日
	 */
	public PriceCustomerEntity queryPriceCustomerOrgInfo(String code);

	/**
	 * @Description: 修改客户专属价格 及其明细
	 * @param @param priceCustomerVo
	 * @return void
	 * @throws
	 * @author dengyin
	 * @date 2016年1月13日
	 */
	public String updateCustomerAndSubItemList(PriceCustomerVo priceCustomerVo);

	/**
	 * @Description: 对选中的 客户专属记录 及其副表的明细记录 统一作废实现
	 * @param destoryIdStr
	 *            ： 选中的客户专属记录的 id 拼接串 '1','2'
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月14日
	 */
	public String destoryPriceCustomerAndSubList(String destoryIdStr);

	/**
	 * 
	 * @Description: 查询导出客户价格明细的数据
	 * @param @param priceCustomerVo
	 * @param @return
	 * @return List<ExcelPriceCustomerEntity>
	 * @throws
	 * @author 赵金义
	 * @date 2016年1月20日
	 */
	public List<ExcelPriceCustomerEntity> queryExcelListByParam(
			PriceCustomerVo priceCustomerVo);
	
	public List<ExcelPriceCustomerEntity> excelQueryListByParamForStandard(
			PriceCustomerVo priceCustomerVo);	

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
	public String createExcelFile(List<ExcelPriceCustomerEntity> list,String exportType) throws IOException;

	public int checkCustomerPriceDiscountExistRepeatTimezone(
			PriceCustomerEntity checkEntity);

	public PriceCustomerEntity selectPriceCustomerListForLastByCode(
			String customerCode);

	public void insertSelective(PriceCustomerEntity newPriceCustomerEntity);

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
	public List<PriceCustomerEntity> excelPriceCustomer(PriceCustomerEntity priceCustomerEntity);
	
	public String createExcelFileForCustomer(List<PriceCustomerEntity> list) throws IOException ;
	
	/**
	 * 针对于 生效中 的主表数据进行修改操作
	 * @param priceCustomerEntity
	 */
	public void updateForEffectiveById(PriceCustomerEntity priceCustomerEntity);
 
}

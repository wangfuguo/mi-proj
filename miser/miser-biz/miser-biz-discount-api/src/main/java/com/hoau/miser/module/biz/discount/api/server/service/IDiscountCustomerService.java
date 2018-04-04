/**   
* @Title: IDiscountCustomerService.java 
* @Package com.hoau.miser.module.biz.discount.server.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 陈启勇 
* @date 2016年1月5日 下午3:25:35 
* @version V1.0   
*/
package com.hoau.miser.module.biz.discount.api.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountCustomerEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountCustomerVo;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;

/**
 * 客户折扣管理service接口
 * ClassName: IDiscountCustomerService 
 * @author 陈启勇
 * @date 2016年1月13日
 * @version V1.0   
 */
public interface IDiscountCustomerService {

	/**
	 * 客户折扣管理集合信息
	 * @param param
	 * @param limit
	 * @param start
	 * @return List<DiscountCustomerEntity>  
	 * @Author 陈启勇
	 * @Time 2015年1月13日
	 */
	public List<DiscountCustomerEntity> queryListByParam(DiscountCustomerVo psv, int limit, int start);

	/**
	 * 客户折扣数量
	 * @param param
	 * @return Long  
	 * @Author 陈启勇
	 * @Time 2015年1月13日
	 */
	public Long queryCountByParam(DiscountCustomerVo beanv);
	
	/**
	 * 增加客户折扣信息
	 * @param @param beanv
	 * @param @param isConfirmAdd
	 * @param @return   
	 * @return Integer 
	 * @throws
	 * @author 陈启勇
	 * @date 2015年1月13日
	 */
	public Integer addDiscountCustomer(DiscountCustomerEntity beanv,Boolean isConfirmAdd);
	
	/**
	 * 修改客户折扣信息
	 * @param @param beanv
	 * @param @param isConfirm 是否确认
	 * @param @return   
	 * @return Integer 
	 * @throws
	 * @author 陈启勇
	 * @date 2015年1月13日
	 */
	public Integer updateDiscountCustomer(DiscountCustomerEntity beanv,Boolean isConfirm);
	
	/**
	 * 删除
	 * @param pse void
	 * @Author 陈启勇
	 * @Time 2015年1月13日
	 */
	public void delDiscountCustomer(DiscountCustomerEntity beanv);
	
	/**
	 *批量删除
	 * @param @param entitys   
	 * @return void 
	 * @throws
	 * @author 陈启勇
	 * @date 2015年1月13日
	 */
	public void bathDelDiscountCustomer(List<DiscountCustomerEntity> entitys);
	
	/**
	 * 根据id得到客户折扣
	 * @param id
	 * @return DiscountCustomerEntity  
	 * @Author 陈启勇
	 * @Time 2015年1月13日
	 */
	public DiscountCustomerEntity queryById(String id);
	
	public List<DiscountCustomerEntity> excelQueryListByParam(DiscountCustomerVo beanv);
	/**
	 * 
	 * @Description: 生成Excel导出文件
	 * @param @param list
	 * @param @return   
	 * @return String 
	 * @throws
	 * @Author 陈启勇
	 * @Time 2015年1月18日
	 */
	public ExcelExportResultEntity createExcelFileToSql(DiscountCustomerEntity entity);
	public String createExcelFile();
	
	/**
	 * 该方法会将错误的信息收集起来，返回给调用者
	 * @Description: 处理从Excel里批量导入数据的操作
	 * @param @param path
	 * @param @return
	 * @param @throws Exception   
	 * @return Map<String,Object> addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
	 * @throws
	 * @Author 陈启勇
	 * @Time 2015年1月18日
	 */
	public  Map<String,Object>  bathImplDiscountCustomer(String path) throws Exception;

	
	
}

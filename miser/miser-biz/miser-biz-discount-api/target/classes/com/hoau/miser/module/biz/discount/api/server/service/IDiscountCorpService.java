/**   
* @Title: IDiscountCorpService.java 
* @Package com.hoau.miser.module.biz.discount.server.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 陈启勇 
* @date 2016年1月5日 下午3:25:00 
* @version V1.0   
*/
package com.hoau.miser.module.biz.discount.api.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountCorpEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountCorpVo;


/**
 * 网点折扣管理service接口
 * ClassName: IDiscountCorpService 
 * @author 陈启勇
 * @date 2016年1月5日
 * @version V1.0   
 */
public interface IDiscountCorpService {

	/**
	 * 网点折扣管理集合信息
	 * @param param
	 * @param limit
	 * @param start
	 * @return List<DiscountCorpEntity>  
	 * @Author 陈启勇
	 * @Time 2015年1月6日
	 */
	public List<DiscountCorpEntity> queryListByParam(DiscountCorpVo psv, int limit, int start);
	

	/**
	 * 网点折扣数量
	 * @param param
	 * @return Long  
	 * @Author 陈启勇
	 * @Time 2015年1月6日
	 */
	public Long queryCountByParam(DiscountCorpVo psv);
	
	/**
	 * 增加网点折扣信息
	 * @param @param pse
	 * @param @param isConfirmAdd
	 * @param @return   
	 * @return Integer 
	 * @throws
	 * @author 陈启勇
	 * @date 2015年1月6日
	 */
	public Integer addDiscountCorp(DiscountCorpEntity pse,Boolean isConfirmAdd);
	
	/**
	 * 修改网点折扣信息
	 * @param @param pse
	 * @param @param isConfirm 是否确认
	 * @param @return   
	 * @return Integer 
	 * @throws
	 * @author 陈启勇
	 * @date 2015年1月6日
	 */
	public Integer updateDiscountCorp(DiscountCorpEntity pse,Boolean isConfirm);
	
	/**
	 * 删除
	 * @param pse void
	 * @Author 陈启勇
	 * @Time 2015年1月6日
	 */
	public void delDiscountCorp(DiscountCorpEntity pse);
	
	/**
	 *批量删除
	 * @param @param entitys   
	 * @return void 
	 * @throws
	 * @author 陈启勇
	 * @date 2015年1月6日
	 */
	public void bathDelDiscountCorp(List<DiscountCorpEntity> entitys);
	
	/**
	 * 根据id得到网点折扣
	 * @param id
	 * @return DiscountCorpEntity  
	 * @Author 陈启勇
	 * @Time 2015年1月6日
	 */
	public DiscountCorpEntity queryById(String id);
	
	
	public List<DiscountCorpEntity> excelQueryListByParam(DiscountCorpVo psv);
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
	public String createExcelFile(List<DiscountCorpEntity> list);
	
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
	 * @Time 2015年1月19日
	 */
	public  Map<String,Object>  bathImplDiscountCorp(String path) throws Exception;
}

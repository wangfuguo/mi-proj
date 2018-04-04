/**   
 * @Title: IPriceCustomerService.java 
 * @Package com.hoau.miser.module.biz.pricecard.server.service 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月5日 下午1:41:34 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.api.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerSubVo;

/**
 * 客户专属价格接口定义 ClassName: IPriceCustomerService
 * 
 * @author dengyin
 * @date 2016年1月5日
 * @version V1.0
 */
public interface IPriceCustomerSubService {

	/**
	 * @Description: 新增客户专属价格明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月8日
	 */
	String insertSelective(PriceCustomerSubEntity priceCustomerSubEntity);

	/**
	 * 
	 * @Description: 根据 parentId 查询对应客户专属价格明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月8日
	 */
	List<PriceCustomerSubEntity> selectCustomerSubListByParam(
			PriceCustomerSubEntity priceCustomerSubEntity, int limit, int start);

	/**
	 * @Description: 修改客户专属价格明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月8日
	 */
	int updateByPrimaryKeySelective(
			PriceCustomerSubEntity priceCustomerSubEntity);

	/**
	 * @Description: 批量作废选中的记录
	 * @param @param batchDeleteIdStr
	 * @param @return
	 * @return int
	 * @throws
	 * @author dengyin
	 * @date 2016年1月8日
	 */
	int batchUpdateActiveByIdStr(PriceCustomerSubVo priceCustomerSubVo);

	/**
	 * 
	 * @Description: 批量添加 客户专属明细
	 * @param @param priceCustomerSubEntityList
	 * @return void
	 * @throws
	 * @author dengyin
	 * @date 2016年1月11日
	 */
	public void batchInsertCustomerSub(
			List<PriceCustomerSubEntity> priceCustomerSubEntityList);

	public void batchUpdateForInvalidByParentId(Map<String, String> paramMap);

	/**
	 * @Description: 根据勾选的 客户专属记录的 id 拼接串 更新明细表的 active=N
	 *               modifyUserCode=currentUser modifyTime=sysdate
	 * @param @param paramMap[modifyUserCode=currentUser
	 *        destoryIdStr=destoryIdStr]
	 * @return void
	 * @throws
	 * @author dengyin
	 * @date 2016年1月14日
	 */
	void destoryPriceCustomerSubByParentIdStr(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description: 导入价格明细
	 * @param @param path
	 * @param customerType 是否为新客户
	 * @param @return
	 * @return Map<String,Object> 成功数量，失败数量，返回action的Entity，错误记录.
	 * @throws Exception
	 * @throws
	 * @author 赵金义
	 * @date 2016年1月14日
	 */
	public Map<String, Object> bathImplPriceCustomer(String path,String customerType)
			throws Exception;
	
	/**
	 * 针对于 生效中 的副表记录进行修改操作
	 * @param paramMap
	 */
	public void batchUpdateForEffectiveByParentId(Map<String,Object> paramMap);

	List<PriceCustomerSubEntity> selectCustomerSubListByParam(
			PriceCustomerSubEntity tmpSubObj);
}

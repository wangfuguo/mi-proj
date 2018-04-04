package com.hoau.miser.module.biz.pricecard.api.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerSectionSubVo;

/**
 * 易入户客户专属价格接口定义 ClassName: IPriceCustomerService
 * 
 * @author 何羽
 * @date 2016年5月5日
 * @version V2.0
 */
public interface IPriceCustomerSectionSubService {

	/**
	 * @Description: 新增易入户客户专属价格明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author 何羽
	 * @date 2016年5月5日
	 */
	String insertSelective(PriceCustomerSectionSubEntity priceCustomerSectionSubEntity);

	/**
	 * 
	 * @Description: 根据 parentId 查询对应易入户客户专属价格明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author 何羽
	 * @date 2016年5月5日
	 */
	List<PriceCustomerSectionSubEntity> selectCustomerSubListByParam(
			PriceCustomerSectionSubEntity priceCustomerSectionSubEntity, int limit, int start);

	/**
	 * @Description: 修改客户专属价格明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author 何羽
	 * @date 2016年5月5日
	 */
	int updateByPrimaryKeySelective(
			PriceCustomerSectionSubEntity priceCustomerSectionSubEntity);

	/**
	 * @Description: 批量作废选中的记录
	 * @param @param batchDeleteIdStr
	 * @param @return
	 * @return int
	 * @throws
	 * @author 何羽
	 * @date 2016年5月5日
	 */
	int batchUpdateActiveByIdStr(PriceCustomerSectionSubVo priceCustomerSectionSubVo);

	/**
	 * 
	 * @Description: 批量添加 客户专属明细
	 * @param @param priceCustomerSubEntityList
	 * @return void
	 * @throws
	 * @author 何羽
	 * @date 2016年5月5日
	 */
	public void batchInsertCustomerSub(
			List<PriceCustomerSectionSubEntity> priceCustomerSectionSubEntity);

	public void batchUpdateForInvalidByParentId(Map<String, String> paramMap);

	/**
	 * @Description: 根据勾选的 客户专属记录的 id 拼接串 更新明细表的 active=N
	 *               modifyUserCode=currentUser modifyTime=sysdate
	 * @param @param paramMap[modifyUserCode=currentUser
	 *        destoryIdStr=destoryIdStr]
	 * @return void
	 * @throws
	 * @author 何羽
	 * @date 2016年5月5日
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
	 * @author 何羽
	 * @date 2016年5月5日
	 */
	public Map<String, Object> bathImplPriceCustomer(String path)
			throws Exception;
	
	/**
	 * 针对于 生效中 的副表记录进行修改操作
	 * @param paramMap
	 */
	public void batchUpdateForEffectiveByParentId(Map<String,Object> paramMap);

	List<PriceCustomerSectionSubEntity> selectCustomerSubListByParam(
			PriceCustomerSectionSubEntity tmpSubObj);
}

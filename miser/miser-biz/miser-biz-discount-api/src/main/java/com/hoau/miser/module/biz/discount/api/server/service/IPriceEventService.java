package com.hoau.miser.module.biz.discount.api.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventDiscountSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventOrgEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventRouteDiscountEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventRouteSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEvnetEntity;
import com.hoau.miser.module.sys.base.api.shared.vo.OrgTreeNode;

/**
 * 优惠折扣Service
 * ClassName: IPriceEventService 
 * @author 廖文强
 * @date 2016年1月6日
 * @version V1.0
 */
public interface IPriceEventService {

	/**
	 * 
	 * @Description: 根据条件查询优惠折扣集合
	 * @param @param psv
	 * @param @param limit
	 * @param @param start
	 * @param @return   
	 * @return List<PriceEvnetEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public List<PriceEvnetEntity> queryListByParam(PriceEvnetEntity psv, int limit, int start);
	
	/**
	 * 直接新增，不需要校验重复性
	 * @Description: 插入优惠折扣
	 * @param @param psv   
	 * @return  void
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void addPriceEvnet(PriceEvnetEntity psv);
	
	/**
	 * 作废旧的，新增新的
	 * @Description: 修改优惠折扣
	 * @param @param psv
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public void updatePriceEvnet(PriceEvnetEntity psv);
	
	/**
	 * 
	 * @Description: 根据条件统计优惠折扣集合
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月6日
	 */
	public Long queryCountByParam(PriceEvnetEntity psv);
	
	/**
	 * 活动未生效前可以作废，活动开始后作废按钮灰色禁用。
	 * @Description: delPriceEvnet
	 * @param pse
	 * @return void
	 * @Author 廖文强
	 * @Time 2015年12月15日下午5:26:32
	 */
	public void delPriceEvnet(PriceEvnetEntity pse);
	
	/**
	 * 
	 * @Description:强制终止
	 * @param @param pse
	 * @param @return   
	 * @return void
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月7日
	 */
	public void stopPriceEvent(PriceEvnetEntity pse);
	
	
	/**
	 * 根据id得到价格表
	 * @param id
	 * @return PriceEvnetEntity  
	 * @Author 廖文强
	 * @Time 2015年12月17日下午7:12:24
	 */
	public PriceEvnetEntity queryPriceEvnetById(String id);
	
	/**
	 * 
	 * @Description: 拿到下级组织
	 * @param @param orgEntity {eventCode 必须传，parentCode=='DP010000'（如果为空，则是全部）}
	 * @param @return   
	 * @return List<OrgTreeNode<PriceEventOrgEntity>> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月8日
	 */
	public List<OrgTreeNode<PriceEventOrgEntity>> queryOrgByParentRes(PriceEventOrgEntity orgEntity);
	/**
	 * 
	 * @Description:区域范围Excel导入
	 * @param @param param path:路径,eventCode：活动code
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PriceEventCorpSubEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月11日
	 */
	public Map<String,Object> impleAreaScope(Map<String,Object> param ) throws Exception;
	/**
	 * 
	 * @Description: 线路Excel导入
	 * @param @param param path:路径,eventCode：活动code
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PriceEventRouteSubEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月11日
	 */
	public List<PriceEventRouteSubEntity> impleRouteSub(Map<String,Object> param ) throws Exception;
	/**
	 * 
	 * @Description: 折扣明细Excel导入
	 * @param @param param
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PriceEventDiscountSubEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月11日
	 */
	public List<PriceEventDiscountSubEntity> impleDiscountSub(Map<String,Object> param ) throws Exception;

	/**
	 * 
	 * @Description:用于关闭活动或者取消活动界面时清空临时表
	 * @throws
	 * @author 275636
	 * @date 2016年1月16日
	 */
	public void deleteCorpImportTemp();

	/**
	 * 
	 * @Description:根据父id查询优惠内容
	 * @throws
	 * @author 275636
	 * @date 2016年2月26日
	 */
	public List<PriceEventDiscountSubEntity> queryPDESubListByParam(String parentId, int limit,int start);

	/**
	 * 
	 * @Description:根据父id查询线路内容
	 * @throws
	 * @author 275636
	 * @date 2016年2月26日
	 */
	public List<PriceEventRouteSubEntity> queryLineSubListByParam(
			String parentId, int limit, int start);
	
	
	/**
	 * @Description:根据实体查询优惠内容
	 * @throws
	 * @author dengyin
	 * @date 2016-4-26 11:00:49
	 */
	public List<PriceEventDiscountSubEntity> queryEventDiscountSubListByParam(PriceEventDiscountSubEntity entity);
	

	/**
	 * add by dengyin 2016-5-2 17：01：23  价格时效查询
	 * @param entity
	 * @return
	 */
	public List<PriceEventRouteDiscountEntity> queryEventRouteDiscount(PriceEventRouteDiscountEntity entity);
	
}

/**   
* @Title: DiscountCorpService.java 
* @Package com.hoau.miser.module.biz.discount.server.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 陈启勇 
* @date 2016年1月5日 下午4:05:04 
* @version V1.0   
*/
package com.hoau.miser.module.biz.discount.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.ITranstypeService;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;
import com.hoau.miser.module.biz.discount.api.server.service.IDiscountCorpService;
import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountCorpEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountCorpVo;
import com.hoau.miser.module.biz.discount.server.dao.DiscountCorpDao;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionVo;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.base.api.server.service.ICommonOrgService;
import com.hoau.miser.module.sys.base.api.shared.domain.CommonOrgEntity;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.DateUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * ClassName: DiscountCorpService 
 * @author 陈启勇
 * @date 2016年1月5日
 * @version V1.0   
 */
@Service
public class DiscountCorpService implements IDiscountCorpService {
	
	private static final Logger log = LoggerFactory.getLogger(DiscountCorpEntity.class);
	@Resource
	private DiscountCorpDao discountCorpDao;
	@Resource
	private IPriceSectionService priceSectionService;
	@Resource
	private ITranstypeService transtypeService;
	@Resource
	private ICommonOrgService commonOrgService;
	
	@Override
	public List<DiscountCorpEntity> queryListByParam(DiscountCorpVo beanv,int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return discountCorpDao.queryListByParam(beanv, rowBounds);
	}

	
	@Override
	public Long queryCountByParam(DiscountCorpVo beanv) {
		return (long) discountCorpDao.excelQueryListByParam(beanv).size();
	}

	/* (non-Javadoc)
	 * @see com.hoau.miser.module.biz.discount.server.service.IDiscountCorpService#addDiscountCorp(com.hoau.miser.module.biz.discount.shared.domain.DiscountCorpEntity, java.lang.Boolean)
	 */
	@Override
	@Transactional
	public Integer addDiscountCorp(DiscountCorpEntity bean, Boolean isConfirmAdd) {
		Integer result=1;
		Date effectiveTime = bean.getEffectiveTime();
		Date invalidTime = bean.getInvalidTime();
		if(effectiveTime != null && invalidTime != null){
			long currentTime = System.currentTimeMillis();
			Date date = new Date(currentTime);
			if(effectiveTime.compareTo(date)<0){
				bean.setEffectiveTime(date);
			}
			if(invalidTime.compareTo(effectiveTime) <=0 && invalidTime.compareTo(date)<=0){
				return 3;
			}
		}
		result = addOrUpdateDiscountCorp(bean,isConfirmAdd,"ADD");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hoau.miser.module.biz.discount.server.service.IDiscountCorpService#updateDiscountCorp(com.hoau.miser.module.biz.discount.shared.domain.DiscountCorpEntity, java.lang.Boolean)
	 */
	@Override
	@Transactional
	public Integer updateDiscountCorp(DiscountCorpEntity bean, Boolean isConfirm) {
		Integer result = 1;
		Date effectiveTime = bean.getEffectiveTime();
		Date invalidTime = bean.getInvalidTime();
		if(effectiveTime !=null && invalidTime != null){
			long currentTime = System.currentTimeMillis();
			Date date = new Date(currentTime);
			if(invalidTime.compareTo(effectiveTime) <=0 && invalidTime.compareTo(date)<=0){
				return 3;
			}
		}
		result = addOrUpdateDiscountCorp(bean,isConfirm,"UPDATE");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hoau.miser.module.biz.discount.server.service.IDiscountCorpService#bathDelDiscountCorp(java.util.List)
	 */
	@Override
	public void bathDelDiscountCorp(List<DiscountCorpEntity> entitys) {
		for(DiscountCorpEntity bean:entitys){
			delDiscountCorp(bean);
		}

	}

	@Transactional
	public void delDiscountCorp(DiscountCorpEntity bean) {
		bean.setRemark(MiserUserContext.getCurrentUser().getUserName()+" 于 "+DateUtils.convert(new Date())+" 手动删除");
		beforAdd(bean);
		bean.setActive(MiserConstants.NO);
		bean.setInvalidTime(new Date());
		discountCorpDao.updateDiscountCorp(bean);
	}
	/* (non-Javadoc)
	 * @see com.hoau.miser.module.biz.discount.server.service.IDiscountCorpService#queryDiscountCorpById(java.lang.String)
	 */
	@Override
	public DiscountCorpEntity queryById(String id) {
		return discountCorpDao.selectByPrimaryKey(id);
	}
	
	private Integer addOrUpdateDiscountCorp(DiscountCorpEntity bean,Boolean isConfirmAdd,String flag) {
		//默认成功
		Integer result=1;
		//查询历史有效信息
		DiscountCorpEntity queryEntity=new DiscountCorpEntity();
		queryEntity.setActive(MiserConstants.YES);
		queryEntity.setOrgCode(bean.getOrgCode());
		queryEntity.setTransTypeCode(bean.getTransTypeCode());
		queryEntity.setState(DiscountCorpEntity.STATE_2);
		DiscountCorpVo queryVo1=new DiscountCorpVo();
		queryVo1.setDiscountCorpEntity(queryEntity);
		//查询生效中的信息
		List<DiscountCorpEntity> state2List= this.queryListByParam(queryVo1, 1, 0);
		//查询待生效的信息
		queryEntity.setState(DiscountCorpEntity.STATE_3);
		List<DiscountCorpEntity> state3List= this.queryListByParam(queryVo1, 1, 0);
		
		if(state2List!=null&&state2List.size()>0){
			DiscountCorpEntity state2Entity=state2List.get(0);
			if("UPDATE".equals(flag)){
				Date nowDate = new Date();
				state2Entity.setActive(MiserConstants.YES);
				state2Entity.setInvalidTime(nowDate);
				bean.setEffectiveTime(nowDate);
				beforAdd(state2Entity);
				discountCorpDao.updateDiscountCorp(state2Entity);
			}else{
				if(bean.getEffectiveTime().getTime()>=state2Entity.getEffectiveTime().getTime() 
					&& bean.getEffectiveTime().getTime()<=state2Entity.getInvalidTime().getTime()){
					return 2;
				}
			}
			
		}
		if(state3List!=null&&state3List.size()>0){//存在待生效信息
			
			if(isConfirmAdd){//确认新增
				//将旧的待生效信息作废
				DiscountCorpEntity state3Entity=state3List.get(0);
				state3Entity.setActive(MiserConstants.NO);
				beforAdd(state3Entity);
				discountCorpDao.updateDiscountCorp(state3Entity);
			}else{//表示需要确认是否新增
				result=0;
				return result;
			}
		}
		//新增待生效信息
		bean.setId(null);
		beforAdd(bean);
		discountCorpDao.insert(bean);
		return result;
	}
	/**
	 * 
	 * @Description: 网点折扣初始值
	 * @param @param bean   
	 * @return void 
	 * @throws
	 * @author 陈启勇
	 * @date 2016年1月6日
	 */
	public DiscountCorpEntity beforAdd(DiscountCorpEntity bean){
		Date dt = new Date();
		if(StringUtil.isBlank(bean.getId())){//新增的话
			bean.setId(UUIDUtil.getUUID());
			bean.setActive(MiserConstants.ACTIVE);
			bean.setCreateDate(dt);
			bean.setCreateUser(MiserUserContext.getCurrentUser().getUserName());
		}
		bean.setDataOrign(DataOrignEnum.PMS.getName());
		bean.setModifyDate(dt);
		bean.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
		return bean;
	}


	/* (non-Javadoc)
	 * @see com.hoau.miser.module.biz.discount.server.service.IDiscountCorpService#excelQueryListByParam(com.hoau.miser.module.biz.discount.shared.vo.DiscountCorpVo)
	 */
	@Override
	public List<DiscountCorpEntity> excelQueryListByParam(DiscountCorpVo beanv) {
		return discountCorpDao.excelQueryListByParam(beanv);
	}


	/* (non-Javadoc)
	 * @see com.hoau.miser.module.biz.discount.server.service.IDiscountCorpService#createExcelFile(java.util.List)
	 */
	public String createExcelFile() {
		// 优惠分段
		String filePath=null;
		List<String> titleNames=new ArrayList<String>();
		titleNames.add("序号");
		titleNames.add("所在事业部");
		titleNames.add("所在大区");
		titleNames.add("所在路区");
		titleNames.add("所在门店");
		titleNames.add("运输类型");
		titleNames.add("纯运费优惠分段");
		titleNames.add("附加费优惠分段");
		titleNames.add("燃油费优惠分段");
		titleNames.add("提货费优惠分段");
		titleNames.add("送货费优惠分段");
		titleNames.add("上楼费优惠分段");
		titleNames.add("保价率优惠分段");
		titleNames.add("保价最低收费优惠分段");
		titleNames.add("工本费优惠分段");
		titleNames.add("信息费优惠分段");
		titleNames.add("代收手续费率优惠分段");
		titleNames.add("代收手续费优惠分段");
		titleNames.add("备注");
		titleNames.add("生效日期");
		titleNames.add("失效日期");
		
		try {
			filePath=new ExcelUtil<DiscountCorpEntity>().exportExcel(titleNames);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("生成导出数据异常!", e);
			filePath=null;
		}
		
		return filePath;
	}
	
	/* (non-Javadoc)
	 * @see com.hoau.miser.module.biz.discount.server.service.IDiscountCorpService#createExcelFile(java.util.List)
	 */
	public String createExcelFile(List<DiscountCorpEntity> list) {
		// 优惠分段
		PriceSectionVo beanv=new PriceSectionVo();
		PriceSectionEntity sEntity = new PriceSectionEntity();
		sEntity.setActive(MiserConstants.YES);
		beanv.setPriceSectionEntity(sEntity);
		List<PriceSectionEntity> sList = priceSectionService.queryPriceSection(beanv);
		Map<String, String> sectionMap2 = new HashMap<String, String>();
		for (PriceSectionEntity entity : sList) {
			sectionMap2.put(entity.getCode(), entity.getName());
		}

		
		for(DiscountCorpEntity entity:list){
			entity.setFreightSectionName(sectionMap2.get(entity.getFreightSectionCode()));
		    entity.setPickupSectionName(sectionMap2.get(entity.getPickupSectionCode()));
		    entity.setDeliverySectionName(sectionMap2.get(entity.getDeliverySectionCode()));
		    entity.setInsuranceRateSectionName(sectionMap2.get(entity.getInsuranceRateSectionCode()));
		    entity.setInsuranceSectionName(sectionMap2.get(entity.getInsuranceSectionCode()));
		    entity.setPaperSectionName(sectionMap2.get(entity.getPaperSectionCode()));
		    entity.setSmsSectionName(sectionMap2.get(entity.getSmsSectionCode()));
		    entity.setFuelSectionName(sectionMap2.get(entity.getFuelSectionCode()));
		    entity.setCollectionRateSectionName(sectionMap2.get(entity.getCollectionRateSectionCode()));
		    entity.setCollectionSectionName(sectionMap2.get(entity.getCollectionSectionCode()));
		    entity.setAddSectionName(sectionMap2.get(entity.getAddSectionCode()));
		    entity.setUpstairsSectionName(sectionMap2.get(entity.getUpstairsSectionCode()));
		}
		String filePath=null;
		List<String> titleNames=new ArrayList<String>();
		titleNames.add("序号");
		titleNames.add("所在事业部");
		titleNames.add("所在大区");
		titleNames.add("所在路区");
		titleNames.add("所在门店");
		titleNames.add("运输类型");
		titleNames.add("纯运费优惠分段");
		titleNames.add("附加费优惠分段");
		titleNames.add("燃油费优惠分段");
		titleNames.add("提货费优惠分段");
		titleNames.add("送货费优惠分段");
		titleNames.add("上楼费优惠分段");
		titleNames.add("保价率优惠分段");
		titleNames.add("保价最低收费优惠分段");
		titleNames.add("工本费优惠分段");
		titleNames.add("信息费优惠分段");
		titleNames.add("代收手续费率优惠分段");
		titleNames.add("代收手续费优惠分段");
		titleNames.add("备注");
		titleNames.add("生效日期");
		titleNames.add("失效日期");
		List<String> paramNames=new ArrayList<String>();
		paramNames.add("id");
		paramNames.add("division");
		paramNames.add("bigRegion");
		paramNames.add("roadArea");
		paramNames.add("orgCodeName");
		paramNames.add("transTypeName");
		paramNames.add("freightSectionName");
		paramNames.add("addSectionName");
		paramNames.add("fuelSectionName");
		paramNames.add("pickupSectionName");
		paramNames.add("deliverySectionName");
		paramNames.add("upstairsSectionName");
		paramNames.add("insuranceRateSectionName");
		paramNames.add("insuranceSectionName");
		paramNames.add("paperSectionName");
		paramNames.add("smsSectionName");
		paramNames.add("collectionRateSectionName");
		paramNames.add("collectionSectionName");
		paramNames.add("remark");
		try {
			filePath=new ExcelUtil<DiscountCorpEntity>().exportExcel(titleNames,paramNames,list);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("生成导出数据异常!", e);
			filePath=null;
		}
		
		return filePath;
	}


	/* (non-Javadoc)
	 * @see com.hoau.miser.module.biz.discount.server.service.IDiscountCorpService#bathImplDiscountCorp(java.lang.String)
	 */
	@Override
	public Map<String, Object> bathImplDiscountCorp(String path)
			throws Exception {
		Map<String,Object> retuMap=new HashMap<String, Object>();
		List<Map<String, String>> list;
		List<DiscountCorpEntity> dcList = new ArrayList<DiscountCorpEntity>();
		list = ExcelUtil.readToListByFile(path, 27, 2);
		//运输类型
		Map<String,String> transTypeCodeMap=new HashMap<String, String>();
		Map<String,String> transTypeCodeMap2=new HashMap<String, String>();
		
		TranstypeVo transtypeVo=new TranstypeVo();
		TranstypeEntity transtypeEntity=new TranstypeEntity();
		transtypeEntity.setActive(MiserConstants.YES);
		transtypeVo.setTranstypeEntity(transtypeEntity);
		List<TranstypeEntity> transtypeEntityList=transtypeService.queryTranstypes(transtypeVo);
		for(TranstypeEntity value:transtypeEntityList){
			transTypeCodeMap.put(value.getName(), value.getCode());
			transTypeCodeMap2.put(value.getCode(),value.getName());
		}
		
		//优惠分段
		PriceSectionVo psv=new PriceSectionVo();
		PriceSectionEntity sEntity = new PriceSectionEntity();
		sEntity.setActive(MiserConstants.YES);
		psv.setPriceSectionEntity(sEntity);
		List<PriceSectionEntity> sList = priceSectionService.queryPriceSection(psv);
		Map<String,String> sectionMap=new HashMap<String, String>();
		Map<String,String> sectionMap2=new HashMap<String, String>();
		for(PriceSectionEntity entity:sList){
			sectionMap.put(entity.getName(), entity.getCode());
			sectionMap2.put(entity.getCode(),entity.getName());
		}
		for (Map<String, String> map : list) {
			DiscountCorpEntity pcbean = new DiscountCorpEntity();
			try {
				pcbean.setActive("Y");
				CommonOrgEntity coeBean = commonOrgService.queryByName(StringUtil.trim(map.get(4+"")));
				if(null != coeBean){
					pcbean.setOrgCode(coeBean.getCode());
					pcbean.setTransTypeCode(transTypeCodeMap.get(StringUtil.trim(map.get(5+""))));
				    pcbean.setFreightSectionCode(sectionMap.get(StringUtil.trim(map.get(6+""))));
				    pcbean.setAddSectionCode(sectionMap.get(StringUtil.trim(map.get(7+""))));
				    pcbean.setFuelSectionCode(sectionMap.get(StringUtil.trim(map.get(8+""))));
				    pcbean.setPickupSectionCode(sectionMap.get(StringUtil.trim(map.get(9+""))));
				    pcbean.setDeliverySectionCode(sectionMap.get(StringUtil.trim(map.get(10+""))));
				    pcbean.setUpstairsSectionCode(sectionMap.get(StringUtil.trim(map.get(11+""))));
				    pcbean.setInsuranceRateSectionCode(sectionMap.get(StringUtil.trim(map.get(12+""))));
				    pcbean.setInsuranceSectionCode(sectionMap.get(StringUtil.trim(map.get(13+""))));
				    pcbean.setPaperSectionCode(sectionMap.get(StringUtil.trim(map.get(14+""))));
				    pcbean.setSmsSectionCode(sectionMap.get(StringUtil.trim(map.get(15+""))));
				    pcbean.setCollectionRateSectionCode(sectionMap.get(StringUtil.trim(map.get(16+""))));
				    pcbean.setCollectionSectionCode(sectionMap.get(StringUtil.trim(map.get(17+""))));
				    pcbean.setRemark(StringUtil.trim(map.get(18+"")));
				    pcbean.setEffectiveTime(DateUtils.strToDate(map.get(19+"")));
				    pcbean.setInvalidTime(DateUtils.strToDate(map.get(20+"")));
				}
			} catch (Exception e) {
				pcbean = null;// 如果有异常就把pcbean设为null,这样每条信息都加进去了
				log.error("批量导入模版数据异常，业务需要仅作提示", e);
				
			} finally {
				dcList.add(pcbean);
			}

		}
		Map<String, Long> countMap = new HashMap<String, Long>();
		countMap.put("addSize", new Long(0));// 增加条数
		countMap.put("coverSize", new Long(0));// 覆盖条数
		Long beforAddSize=null;
		Long beforCoverSize=null;
		
		for (int i = 0; i < dcList.size(); i++) {
			DiscountCorpEntity pcbean = dcList.get(i);
			try {
				if (pcbean == null) {// 有异常
					continue;
				} else {
					 beforAddSize=countMap.get("addSize");
					 beforCoverSize=countMap.get("coverSize");
					this.addOrUpdateDiscountCorp(pcbean, countMap);
				}
			} catch (Exception e) {
				log.error("批量导入模版数据更新操作异常，业务需要仅作提示", e);
				countMap.put("addSize", beforAddSize);
				countMap.put("coverSize", beforCoverSize);
			}
		}

		retuMap.put("addSize", countMap.get("addSize"));
		retuMap.put("coverSize", countMap.get("coverSize"));
		retuMap.put("sumSize", dcList.size());
		return retuMap;
	}
	
	/* (non-Javadoc)
	 * @see com.hoau.miser.module.biz.discount.server.service.IDiscountCustomerService#bathImplDiscountCustomer(java.lang.String)
	 */
	private void addOrUpdateDiscountCorp(DiscountCorpEntity pcbean,Map<String,Long> countMap) throws Exception {
		
		long addSize= countMap.get("addSize").longValue();
		long coverSize= countMap.get("coverSize").longValue();
			
		//查询历史有效信息
		DiscountCorpEntity queryEntity=new DiscountCorpEntity();
		queryEntity.setActive(MiserConstants.YES);
		queryEntity.setTransTypeCode(pcbean.getTransTypeCode());
		queryEntity.setState(DiscountCorpEntity.STATE_2);
		queryEntity.setOrgCode(pcbean.getOrgCode());
		DiscountCorpVo queryVo1=new DiscountCorpVo();
		if(pcbean.getTransTypeCode()==null){
			throw new Exception("运输类型数据错误！");	
		}
		queryVo1.setDiscountCorpEntity(queryEntity);
		//查询生效中的信息
		List<DiscountCorpEntity> state2List= this.queryListByParam(queryVo1, 1, 0);
		//查询待生效的信息
		queryEntity.setState(DiscountCorpEntity.STATE_3);
		List<DiscountCorpEntity> state3List= this.queryListByParam(queryVo1, 1, 0);
		if(state3List!=null&&state3List.size()>0){//存在待生效信息
			//将旧的待生效信息作废
			DiscountCorpEntity state3Entity=state3List.get(0);
			state3Entity.setActive(MiserConstants.NO);
			beforAdd(state3Entity);
			discountCorpDao.updateDiscountCorp(state3Entity);
			coverSize++;//替换
		}else{
			addSize++;//增加
		}
			
		//新增待生效信息
		Long minuteDiff2= DateUtils.getMinuteDiff(pcbean.getEffectiveTime(),new Date());
		if(minuteDiff2>0)pcbean.setEffectiveTime(new Date());
		pcbean.setId(null);
		beforAdd(pcbean);
		discountCorpDao.insert(pcbean);
			
		// 将生效中的信息的失效时间赋值为待生效的生效时间
		if(state2List!=null&&state2List.size()>0){
			DiscountCorpEntity state2Entity=state2List.get(0);
			beforAdd(state2Entity);
			state2Entity.setActive(MiserConstants.NO);
			discountCorpDao.updateDiscountCorp(state2Entity);
		}
		countMap.put("addSize", addSize);
		countMap.put("coverSize", coverSize);
	}

}

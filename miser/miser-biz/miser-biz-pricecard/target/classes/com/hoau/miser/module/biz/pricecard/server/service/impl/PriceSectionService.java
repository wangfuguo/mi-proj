package com.hoau.miser.module.biz.pricecard.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.define.ReunitePriceSectionConstant;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionSubVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionVo;
import com.hoau.miser.module.biz.pricecard.server.cache.PriceSectionCache;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceSectionDao;
import com.hoau.miser.module.common.server.service.ISerialNumberService;
import com.hoau.miser.module.common.shared.define.SerialNumberRuleEnum;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;
@Service
public class PriceSectionService implements IPriceSectionService {

	@Resource
	private PriceSectionDao priceSectionDao;
	@Resource
	private ISerialNumberService serialNumberService;
	
	public List<PriceSectionEntity> queryPriceSection(PriceSectionVo psv) {
		List<PriceSectionEntity> listPiceSection=null;
		PriceSectionEntity entity = psv.getPriceSectionEntity();
		if(null!=entity&&!StringUtils.isEmptyWithBlock(entity.getCode())){
			listPiceSection=new ArrayList<PriceSectionEntity>();
			//如果有code则直接查缓存
			PriceSectionEntity priceSectionEn=queryPriceSectionByCode(entity.getCode());
			listPiceSection.add(priceSectionEn);
		}else{
			//查询参数没有code值,直接查询数据库
			listPiceSection=priceSectionDao.queryPriceSection(entity);
		}
	   return listPiceSection;
	}
	public List<PriceSectionEntity> queryListByParam(PriceSectionVo psv, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return priceSectionDao.queryListByParam(psv,rowBounds);
	}

	public Long queryCountByParam(PriceSectionVo psv) {
		return priceSectionDao.queryCountByParam(psv);
	}

	public void addPriceSection(PriceSectionEntity pse,String priceSectionSubList) {
		
		List<PriceSectionSubEntity> jsonArray = com.alibaba.fastjson.JSON.parseArray(priceSectionSubList,PriceSectionSubEntity.class);
		beforAddPriceSectionDispose(pse);
		String code=serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_SECTION_CODE);
		pse.setCode(code);
		priceSectionDao.addPriceSection(pse);
		for(PriceSectionSubEntity npse:jsonArray){
			beforAddPriceSectionSubDispose(npse);
			npse.setSectionId(pse.getId());
			priceSectionDao.addPriceSectionSub(npse);
		}
	}
	
	public void updatePriceSection(PriceSectionEntity pse,String priceSectionSubList) {
		
		//失效旧数据
		String oldSectionId=pse.getId();
		PriceSectionVo psv=new PriceSectionVo();
		PriceSectionEntity newPriceSectionEntity=new PriceSectionEntity();
		newPriceSectionEntity.setId(oldSectionId);
		psv.setPriceSectionEntity(newPriceSectionEntity);
		RowBounds rowBounds = new RowBounds(0,1);
		List<PriceSectionEntity> listPriceSection=priceSectionDao.queryListByParam(psv,rowBounds);
		for(PriceSectionEntity pte:listPriceSection){
			pte.setModifyDate(new Date());
			pte.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			pte.setActive(MiserConstants.NO);
			priceSectionDao.updatePriceSection(pte);
		}
		PriceSectionSubVo pSubVo=new PriceSectionSubVo();
		PriceSectionSubEntity newPriceSectionSubEntity=new PriceSectionSubEntity();
		newPriceSectionSubEntity.setSectionId(oldSectionId);
		pSubVo.setPriceSectionSubEntity(newPriceSectionSubEntity);
		rowBounds = new RowBounds(0,20);
		List<PriceSectionSubEntity> listPriceSectionSub=priceSectionDao.queryListByParamSub(pSubVo,rowBounds);
		for(PriceSectionSubEntity oldpsse:listPriceSectionSub){
			oldpsse.setModifyDate(new Date());
			oldpsse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			oldpsse.setActive(MiserConstants.NO);
			priceSectionDao.updatePriceSectionSub(oldpsse);
		}
		//新增新数据
		List<PriceSectionSubEntity> jsonArray = com.alibaba.fastjson.JSON.parseArray(priceSectionSubList,PriceSectionSubEntity.class);
		beforAddPriceSectionDispose(pse);
		priceSectionDao.addPriceSection(pse);
		for(PriceSectionSubEntity npse:jsonArray){
			beforAddPriceSectionSubDispose(npse);
			npse.setSectionId(pse.getId());
			priceSectionDao.addPriceSectionSub(npse);
		}
	}

	public void delPriceSection(PriceSectionEntity pse) {
		PriceSectionEntity dbEnity=queryPriceSectionById(pse.getId());
		if(MiserConstants.NO.equals(dbEnity.getActive())){
			throw new BusinessException(MessageType.STATE_CHANGE,"state");
		}
		//删明细
		PriceSectionSubEntity newPriceSectionSubEntity=new PriceSectionSubEntity();
		newPriceSectionSubEntity.setSectionId(pse.getId());
		newPriceSectionSubEntity.setActive(MiserConstants.NO);
		newPriceSectionSubEntity.setModifyDate(new Date());
		newPriceSectionSubEntity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		priceSectionDao.delPriceSectionSub(newPriceSectionSubEntity);
		//删主表
		pse.setModifyDate(new Date());
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setActive(MiserConstants.NO);
		priceSectionDao.delPriceSection(pse);
		
	}

	public PriceSectionEntity queryPriceSectionById(String id) {
		PriceSectionVo psv=new PriceSectionVo();
		PriceSectionEntity pce=new PriceSectionEntity();
		pce.setId(id);
		psv.setPriceSectionEntity(pce);
		List<PriceSectionEntity> list=this.queryListByParam(psv,1,0);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	
	public List<PriceSectionSubEntity> queryListByParamSub(PriceSectionSubVo psv, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return priceSectionDao.queryListByParamSub(psv,rowBounds);
	}

	public Long queryCountByParam(PriceSectionSubVo psv) {
		return priceSectionDao.queryCountByParamSub(psv);
	}

	public void addPriceSectionSub(PriceSectionSubEntity pse) {
		beforAddPriceSectionSubDispose(pse);
		priceSectionDao.addPriceSectionSub(pse);
	}
	public void updatePriceSectionSub(PriceSectionSubEntity pse) {
		pse.setActive(MiserConstants.YES);
		pse.setModifyDate(new Date());
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		priceSectionDao.updatePriceSectionSub(pse);
	}
	public void delPriceSectionSub(PriceSectionSubEntity pse) {
		pse.setModifyDate(new Date());
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setActive(MiserConstants.NO);
		priceSectionDao.delPriceSectionSub(pse);
	}
	public PriceSectionSubEntity queryPriceSectionSubById(String id) {
		PriceSectionSubVo psv=new PriceSectionSubVo();
		PriceSectionSubEntity pce=new PriceSectionSubEntity();
		pce.setId(id);
		psv.setPriceSectionSubEntity(pce);
		List<PriceSectionSubEntity> list=this.queryListByParamSub(psv,1,0);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	/**
	 * 
	 * @Description: 新增优惠分段赋初始值
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public void beforAddPriceSectionDispose(PriceSectionEntity pse){
		Date dt = new Date();
		pse.setId(UUIDUtil.getUUID());
		pse.setCreateDate(dt);
		pse.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setModifyDate(dt);
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setActive(MiserConstants.YES);
	}
	
	/**
	 * 
	 * @Description: 新增同步优惠分段赋初始值
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 陈启勇
	 * @date 2016年3月7日
	 */
	public void beforAddPriceSectionSync(PriceSectionEntity pse){
		Date dt = new Date();
		pse.setId(UUIDUtil.getUUID());
		pse.setCreateDate(dt);
		pse.setCreateUser(pse.getCreateUser());
		pse.setModifyDate(dt);
		pse.setModifyUser(pse.getCreateUser());
		pse.setActive(MiserConstants.YES);
	}
	
	/**
	 * 
	 * @Description: 优惠分段缓存
	 * @param @param code
	 * @param @return   
	 * @return PriceSectionEntity 
	 * @throws
	 * @author 王茂
	 * @date 2016年1月7日
	 */
	@Override
    public PriceSectionEntity queryPriceSectionByCode(String code) {
		ICache<String, PriceSectionEntity> transtypeCache = this.getPriceSectionCache();
		PriceSectionEntity entity = transtypeCache.get(code);
		return entity;		
    }

	/**
	 * 
	 * @Description: 获得优惠分段缓存
	 * @param @return   
	 * @return ICache<String,TranstypeEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2016年1月7日
	 */
	@SuppressWarnings("unchecked")
	private ICache<String, PriceSectionEntity> getPriceSectionCache(){
		ICache<String, PriceSectionEntity> roleCache = CacheManager.getInstance().getCache(PriceSectionCache.PRICE_SECTION_CACHE_UUID);
		return roleCache;
	}
	/**
	 * 
	 * @Description: 优惠分段明细新增赋值
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月23日
	 */
	public void beforAddPriceSectionSubDispose(PriceSectionSubEntity pse){
		Date dt = new Date();
		pse.setId(UUIDUtil.getUUID());
		pse.setCreateDate(dt);
		pse.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setModifyDate(dt);
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setActive(MiserConstants.YES);
	}


	/**
	 * @Description: 新增优惠分段
	 * @param entity   需要新增的数据，带明细数据
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月19日
	 */
	public void addPriceSection(PriceSectionEntity entity) {
		Date dt = new Date();
		entity.setId(UUIDUtil.getUUID());
		entity.setCreateDate(dt);
		entity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
		entity.setModifyDate(dt);
		entity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
		entity.setActive(MiserConstants.YES);
		entity.setCode(serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_SECTION_CODE));
		priceSectionDao.addPriceSection(entity);
		List<PriceSectionSubEntity> subEntities = entity.getSubEntities();
		for(PriceSectionSubEntity subEntity : subEntities){
			subEntity.setSectionId(entity.getId());
			subEntity.setId(UUIDUtil.getUUID());
			subEntity.setCreateDate(dt);
			subEntity.setCreateUser(MiserConstants.MQ_UPDATE_DATE_USER);
			subEntity.setModifyDate(dt);
			subEntity.setModifyUser(MiserConstants.MQ_UPDATE_DATE_USER);
			subEntity.setActive(MiserConstants.YES);
			priceSectionDao.addPriceSectionSub(subEntity);
		}
	}
	
	@Override
	@Transactional
	public PriceSectionEntity reuniteSections(String... sectionCodes) {
		return reuniteSectionsList(Arrays.asList(sectionCodes));
	}
	
	@Override
	@Transactional
	public PriceSectionEntity reuniteSectionsList(List<String> sectionCodes) {
		//没有入参，返回空
		if (sectionCodes == null) {
			return new PriceSectionEntity();
		}
		for (int i = 0; i < sectionCodes.size(); i++) {
			if (StringUtils.isEmptyWithBlock(sectionCodes.get(i))) {
				sectionCodes.remove(i);
				i--;
			}
		}
		if (sectionCodes.size() == 0) {
			return new PriceSectionEntity();
		}
		//如果只有一个分段，不需要融合，直接返回查询结果
		if (sectionCodes.size() == 1) {
			return queryPriceSectionByCode(sectionCodes.get(0));
		}
		//多个分段，查询出所有分段
		ArrayList<PriceSectionEntity> entities = new ArrayList<PriceSectionEntity>(sectionCodes.size());
		//去重
		Set<String> deleteRepeatSet = new HashSet<String>(sectionCodes);
		for (String code : deleteRepeatSet) {
			entities.add(queryPriceSectionByCode(code));
		}
		//如果只有一个，则直接返回
		if (entities.size() == 1) {
			return entities.get(0);
		}
		//大于一个，进行融合
		else if (entities.size() > 1) {
			//融合后的备注
			StringBuffer reuniteRemark = new StringBuffer();
			//融合后的适用产品
			String reuniteSuitProduct = null;
			String reuniteSuitProductName = null;
			//融合后的分段项目
			String reuniteSectionItem = null;
			//融合后的费用分段
			PriceSectionEntity reuniteEntity = new PriceSectionEntity();
			//融合后的费用分段明细
			List<PriceSectionSubEntity> reuniteSubEntities = new ArrayList<PriceSectionSubEntity>();
			//融合的费用分段来源
			StringBuffer reuniteSource = new StringBuffer();
			
			for (int i = 0; i < entities.size(); i++) {
				PriceSectionEntity entity = entities.get(i);
				//融合后的适用产品、分段项目区第一个
				if (i == 0) {
					reuniteSuitProduct = entity.getSuitProduct();
					reuniteSuitProductName = entity.getSuitProductName();
					reuniteSectionItem = entity.getSectionedItem();
				}
				reuniteRemark.append(entity.getName()).append(i == entities.size() - 1 ? "" : "-");
				List<PriceSectionSubEntity> subEntities = entity.getSubEntities();
				for (int j = 0; j < subEntities.size(); j++) {
					PriceSectionSubEntity subEntity = subEntities.get(j);
					if (reuniteSubEntities.size() == 0) {
						reuniteSubEntities.add(subEntity);
						continue;
					} else {
						boolean reunited = false;
						for (int k = 0; k < reuniteSubEntities.size(); k++) {
							PriceSectionSubEntity retuniteSubEntity = reuniteSubEntities.get(k);
							List reuniteResult = reuinteSubSection(retuniteSubEntity, subEntity);
							String reuniteType = (String)reuniteResult.get(0);
							List<PriceSectionSubEntity> reuniteResultSubEntities = (ArrayList<PriceSectionSubEntity>)reuniteResult.get(1);
							//如果返回结果记录条数大于零，说明进行了融合，则需要新增分段，否则继续向下查找是否需要进行融合
							if (ReunitePriceSectionConstant.REUNITE_TYPE_PERFECT_REUNITED.equals(reuniteType) 
									&& reuniteResultSubEntities.size() > 0) {
								for (int l = 0; l < reuniteResultSubEntities.size(); l++) {
									//将融合前的分段替换掉
									if (l == 0) {
										reuniteSubEntities.set(k, reuniteResultSubEntities.get(l));
										continue;
									}
									//将融合后多出的分段添加进去
									if (k + l > reuniteSubEntities.size()) {
										reuniteSubEntities.add(reuniteResultSubEntities.get(l));
									} else {
										reuniteSubEntities.add(k + l, reuniteResultSubEntities.get(l));
									}
								}
								//由于上面想列表中插入了元素，需要重置当前游标位置
								k += reuniteResultSubEntities.size() - 1;
								//完美融合的，退后循环
								reunited = true;
								break;
							}
							//前置导致的未融合直接在当前分段位置插入后退出循环
							if (ReunitePriceSectionConstant.REUNITE_TYPE_UNREUNITED_BEFORE.equals(reuniteType)) {
								reuniteSubEntities.add(k, subEntity);
								reunited = true;
								break;
							}
							//非完美融合的,重置开始时间后继续向后融合
							if (ReunitePriceSectionConstant.REUNITE_TYPE_IMPERFECT_REUNITED.equals(reuniteType)) {
								//将未完美融合的部分替换进来
								for (int l = 0; l < reuniteResultSubEntities.size(); l++) {
									//将融合前的分段替换掉
									if (l == 0) {
										reuniteSubEntities.set(k, reuniteResultSubEntities.get(l));
										continue;
									}
									//将融合后多出的分段添加进去
									if (k + l > reuniteSubEntities.size()) {
										reuniteSubEntities.add(reuniteResultSubEntities.get(l));
									} else {
										reuniteSubEntities.add(k + l, reuniteResultSubEntities.get(l));
									}
								}
								//由于上面想列表中插入了元素，需要重置当前游标位置
								k += reuniteResultSubEntities.size() - 1;
								//重新设置待融合分段的段起，用于和后续分段继续融合
								subEntity.setStartValue(retuniteSubEntity.getEndValue());
								//如果是非完美融合的，则需要重置为未融合，否则后续的分段如果没有融合进去就会被舍弃掉了
								reunited = false;
							}
						}
						//如果没有被融合进去，说明是新类型的分段，或者是没有重叠的分段，直接添加进去
						if (!reunited) {
							reuniteSubEntities.add(subEntity);
						}
					}
				}
			}
			String[] sourceCodes = new String[entities.size()];
			for (int i = 0; i < entities.size(); i++) {
				sourceCodes[i] = entities.get(i).getCode();
			}
			Arrays.sort(sourceCodes);
			for (int i = 0; i < sourceCodes.length; i++) {
				reuniteSource.append(sourceCodes[i]).append(i == entities.size() - 1 ? "" : "-");
			}
			reuniteEntity.setRemark(reuniteRemark.toString());
			reuniteEntity.setSectionedItem(reuniteSectionItem);
			reuniteEntity.setShortCode("");
			reuniteEntity.setSuitProduct(reuniteSuitProduct);
			reuniteEntity.setSuitProductName(reuniteSuitProductName);
			reuniteEntity.setSubEntities(reuniteSubEntities);
			reuniteEntity.setReuniteSource(reuniteSource.toString());
			//判读融合后的分段在数据库中是否存在
			PriceSectionEntity queryEntity = new PriceSectionEntity();
			queryEntity.setActive(MiserConstants.ACTIVE);
			queryEntity.setReuniteSource(reuniteSource.toString());
			ArrayList<PriceSectionEntity> oldPriceSectionEntities = priceSectionDao.queryPriceSectionByReuniteSource(queryEntity);
			//数据库中已经存在的分段
			PriceSectionEntity existsOldPriceSectionEntity = null;
			for (int i = 0; i < oldPriceSectionEntities.size(); i++) {
				List<PriceSectionSubEntity> subEntities = oldPriceSectionEntities.get(i).getSubEntities();
				//如果明细个数不同不用比较了
				if (subEntities != null && subEntities.size() != reuniteSubEntities.size()) {
					continue;
				} else {
					boolean existsOld = false;
					for (int j = 0; j < reuniteSubEntities.size(); j++) {
						//有一个不包含则查找下一个分段
						if (!subEntities.contains(reuniteSubEntities.get(j))) {
							break;
						}
						//到这里说明所有的分段都包含了，则设置已存在的分段
						if (j == reuniteSubEntities.size() - 1) {
							existsOld = true;
							existsOldPriceSectionEntity = oldPriceSectionEntities.get(i);
						}
					}
					//如果存在了，不继续比较了
					if (existsOld) {
						break;
					}
				}
			}
			//如果存在，则直接返回数据库中已存在的分段数据
			if (existsOldPriceSectionEntity != null) {
				return existsOldPriceSectionEntity;
			} else {
				//如果不存在，将融合后的分段插入到数据库中，然后返回
				String code = serialNumberService.generateSerialNumber(SerialNumberRuleEnum.REUNITE_PC_SECTION_CODE);
				reuniteEntity.setName("融合分段" + code);
				reuniteEntity.setShortCode("REUNITE" + code);
//				addPriceSection(reuniteEntity);
				return reuniteEntity;
			}
			
		}
		//说明所有传递进来的分段编码都没有查询到对应的分段
		return new PriceSectionEntity();
	}
	/**
	 * @Description: 将两个分段明细进行融合
	 * @param reuniteSubSection	融合前的分段
	 * @param newSubSection		需要进行融合的分段
	 * @return List		包含两个数据，	第一个是融合的类型，如果是完美融合，则直接结束后续的融合，否则需要重新设置待融合分段的段起为融合前分段的段止，
	 * 								第二个是融合后的分段，如果没有产生融合则返回空列表(长度为0)
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月28日
	 */
	public List reuinteSubSection(PriceSectionSubEntity reuniteSubSection, PriceSectionSubEntity newSubSection) {
		//返回的结果集
		List<Object> returnValue = new ArrayList<Object>();
		//融合的类型，默认未融合
		String reuniteType = ReunitePriceSectionConstant.REUNITE_TYPE_UNREUNITED_CANNOT;
		//融合的结果
		List<PriceSectionSubEntity> reunitedSubSections = new ArrayList<PriceSectionSubEntity>(3);

		String accodingItem = newSubSection.getSectionAccodingItem();
		double startValue = newSubSection.getStartValue();
		double endValue = newSubSection.getEndValue();
		double price = newSubSection.getPrice();
		double minPrice = newSubSection.getMinPrice();
		double maxPrice = newSubSection.getMaxPrice();

		String reunitedAccodingItem = reuniteSubSection.getSectionAccodingItem();
		//分段依据相同的才进行融合，不相同的什么都不返回
		if (reunitedAccodingItem.equals(accodingItem)) {
			double reuniteStartValue = reuniteSubSection.getStartValue();
			double reuniteEndValue = reuniteSubSection.getEndValue();
			double reunitePrice = reuniteSubSection.getPrice();
			double reuniteMinPrice = reuniteSubSection.getMinPrice();
			double reuniteMaxPrice = reuniteSubSection.getMaxPrice();
			//判断段起
			if(startValue < reuniteStartValue) {//新分段段起小于待融合分段的段起
				//如果新分段段止在待融合分段内的
				if (endValue > reuniteStartValue && endValue <= reuniteEndValue) {
					//完美融合
					reuniteType = ReunitePriceSectionConstant.REUNITE_TYPE_PERFECT_REUNITED;
					//如果费用、最低、最高有一个不相同，则产生三个分段
					if (price != reunitePrice || minPrice != reuniteMinPrice || maxPrice != reuniteMaxPrice) {
						//第一个分段
						PriceSectionSubEntity firstSubSection = new PriceSectionSubEntity();
						firstSubSection.setActive(MiserConstants.ACTIVE);
						firstSubSection.setCalcAlone(newSubSection.getCalcAlone());
						firstSubSection.setCreateDate(new Date());
						firstSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						firstSubSection.setStartValue(startValue);
						firstSubSection.setEndValue(reuniteStartValue);
						firstSubSection.setPrice(price);
						firstSubSection.setMinPrice(minPrice);
						firstSubSection.setMaxPrice(maxPrice);
						firstSubSection.setModifyDate(new Date());
						firstSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						firstSubSection.setPriceType(newSubSection.getPriceType());
						firstSubSection.setRemark(newSubSection.getRemark());
						firstSubSection.setSectionAccodingItem(accodingItem);
						reunitedSubSections.add(firstSubSection);
						
						//第二个分段
						PriceSectionSubEntity secondSubSection = new PriceSectionSubEntity();
						secondSubSection.setActive(MiserConstants.ACTIVE);
						secondSubSection.setCalcAlone(newSubSection.getCalcAlone());
						secondSubSection.setCreateDate(new Date());
						secondSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						secondSubSection.setStartValue(reuniteStartValue);
						secondSubSection.setEndValue(endValue);
						secondSubSection.setPrice(Math.min(price, reunitePrice));
						secondSubSection.setMinPrice(Math.min(minPrice, reuniteMinPrice));
						secondSubSection.setMaxPrice(Math.max(maxPrice, reuniteMaxPrice));
						secondSubSection.setModifyDate(new Date());
						secondSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						secondSubSection.setPriceType(newSubSection.getPriceType());
						secondSubSection.setRemark(newSubSection.getRemark());
						secondSubSection.setSectionAccodingItem(accodingItem);
						reunitedSubSections.add(secondSubSection);
						
						if (endValue != reuniteEndValue) {
							//第三个分段
							PriceSectionSubEntity thirdSubSection = new PriceSectionSubEntity();
							thirdSubSection.setActive(MiserConstants.ACTIVE);
							thirdSubSection.setCalcAlone(newSubSection.getCalcAlone());
							thirdSubSection.setCreateDate(new Date());
							thirdSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
							thirdSubSection.setStartValue(endValue);
							thirdSubSection.setEndValue(reuniteEndValue);
							thirdSubSection.setPrice(reunitePrice);
							thirdSubSection.setMinPrice(reuniteMinPrice);
							thirdSubSection.setMaxPrice(reuniteMaxPrice);
							thirdSubSection.setModifyDate(new Date());
							thirdSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
							thirdSubSection.setPriceType(newSubSection.getPriceType());
							thirdSubSection.setRemark(newSubSection.getRemark());
							thirdSubSection.setSectionAccodingItem(accodingItem);
							reunitedSubSections.add(thirdSubSection);
						}
						
					} else { //如果三项都相同，则直接扩大分段的范围
						PriceSectionSubEntity firstSubSection = new PriceSectionSubEntity();
						firstSubSection.setActive(MiserConstants.ACTIVE);
						firstSubSection.setCalcAlone(reuniteSubSection.getCalcAlone());
						firstSubSection.setCreateDate(new Date());
						firstSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						firstSubSection.setStartValue(startValue);
						firstSubSection.setEndValue(reuniteEndValue);
						firstSubSection.setPrice(price);
						firstSubSection.setMinPrice(minPrice);
						firstSubSection.setMaxPrice(maxPrice);
						firstSubSection.setModifyDate(new Date());
						firstSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						firstSubSection.setPriceType(reuniteSubSection.getPriceType());
						firstSubSection.setRemark(reuniteSubSection.getRemark());
						firstSubSection.setSectionAccodingItem(accodingItem);
						reunitedSubSections.add(firstSubSection);
					}
				//新分段段止在待融合分段外的
				} else if (endValue > reuniteEndValue) {
					//非完美融合，只融合到融合前分段的结尾即可，融合前分段结尾-待融合分段结尾和后续分段进行融合
					reuniteType = ReunitePriceSectionConstant.REUNITE_TYPE_IMPERFECT_REUNITED;
					//如果费用、最低、最高有一个不相同，则产生两个分段
					if (price != reunitePrice || minPrice != reuniteMinPrice || maxPrice != reuniteMaxPrice) {
						//第一个分段
						PriceSectionSubEntity firstSubSection = new PriceSectionSubEntity();
						firstSubSection.setActive(MiserConstants.ACTIVE);
						firstSubSection.setCalcAlone(reuniteSubSection.getCalcAlone());
						firstSubSection.setCreateDate(new Date());
						firstSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						firstSubSection.setStartValue(startValue);
						firstSubSection.setEndValue(reuniteStartValue);
						firstSubSection.setPrice(price);
						firstSubSection.setMinPrice(minPrice);
						firstSubSection.setMaxPrice(maxPrice);
						firstSubSection.setModifyDate(new Date());
						firstSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						firstSubSection.setPriceType(reuniteSubSection.getPriceType());
						firstSubSection.setRemark(reuniteSubSection.getRemark());
						firstSubSection.setSectionAccodingItem(accodingItem);
						reunitedSubSections.add(firstSubSection);

						//第二个分段
						PriceSectionSubEntity secondSubSection = new PriceSectionSubEntity();
						secondSubSection.setActive(MiserConstants.ACTIVE);
						secondSubSection.setCalcAlone(newSubSection.getCalcAlone());
						secondSubSection.setCreateDate(new Date());
						secondSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						secondSubSection.setStartValue(reuniteStartValue);
						secondSubSection.setEndValue(reuniteEndValue);
						secondSubSection.setPrice(Math.min(price, reunitePrice));
						secondSubSection.setMinPrice(Math.min(minPrice, reuniteMinPrice));
						secondSubSection.setMaxPrice(Math.max(maxPrice, reuniteMaxPrice));
						secondSubSection.setModifyDate(new Date());
						secondSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						secondSubSection.setPriceType(newSubSection.getPriceType());
						secondSubSection.setRemark(newSubSection.getRemark());
						secondSubSection.setSectionAccodingItem(accodingItem);
						reunitedSubSections.add(secondSubSection);
					} else {//否则只是扩大分段范围
						PriceSectionSubEntity firstSubSection = new PriceSectionSubEntity();
						firstSubSection.setActive(MiserConstants.ACTIVE);
						firstSubSection.setCalcAlone(reuniteSubSection.getCalcAlone());
						firstSubSection.setCreateDate(new Date());
						firstSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						firstSubSection.setStartValue(startValue);
						firstSubSection.setEndValue(reuniteEndValue);
						firstSubSection.setPrice(price);
						firstSubSection.setMinPrice(minPrice);
						firstSubSection.setMaxPrice(maxPrice);
						firstSubSection.setModifyDate(new Date());
						firstSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						firstSubSection.setPriceType(reuniteSubSection.getPriceType());
						firstSubSection.setRemark(reuniteSubSection.getRemark());
						firstSubSection.setSectionAccodingItem(accodingItem);
						reunitedSubSections.add(firstSubSection);
					}
				} else {//待融合的整个分段比融合前分段小
					reuniteType = ReunitePriceSectionConstant.REUNITE_TYPE_UNREUNITED_BEFORE;
				}
			} else if (startValue >= reuniteStartValue && startValue < reuniteEndValue) {
				//完全被包在融合前的分段内
				if (endValue > reuniteStartValue && endValue <= reuniteEndValue) {
					//完美融合
					reuniteType = ReunitePriceSectionConstant.REUNITE_TYPE_PERFECT_REUNITED;
					//如果费用、最低、最高有一个不相同，则产生两个分段
					if (price != reunitePrice || minPrice != reuniteMinPrice || maxPrice != reuniteMaxPrice) {
						//第一个分段
						if (startValue != reuniteStartValue) {
							PriceSectionSubEntity firstSubSection = new PriceSectionSubEntity();
							firstSubSection.setActive(MiserConstants.ACTIVE);
							firstSubSection.setCalcAlone(newSubSection.getCalcAlone());
							firstSubSection.setCreateDate(new Date());
							firstSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
							firstSubSection.setStartValue(reuniteStartValue);
							firstSubSection.setEndValue(startValue);
							firstSubSection.setPrice(reunitePrice);
							firstSubSection.setMinPrice(reuniteMinPrice);
							firstSubSection.setMaxPrice(reuniteMaxPrice);
							firstSubSection.setModifyDate(new Date());
							firstSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
							firstSubSection.setPriceType(newSubSection.getPriceType());
							firstSubSection.setRemark(newSubSection.getRemark());
							firstSubSection.setSectionAccodingItem(accodingItem);
							reunitedSubSections.add(firstSubSection);
						}
						
						//第二个分段
						PriceSectionSubEntity secondSubSection = new PriceSectionSubEntity();
						secondSubSection.setActive(MiserConstants.ACTIVE);
						secondSubSection.setCalcAlone(newSubSection.getCalcAlone());
						secondSubSection.setCreateDate(new Date());
						secondSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						secondSubSection.setStartValue(startValue);
						secondSubSection.setEndValue(endValue);
						secondSubSection.setPrice(Math.min(price, reunitePrice));
						secondSubSection.setMinPrice(Math.min(minPrice, reuniteMinPrice));
						secondSubSection.setMaxPrice(Math.max(maxPrice, reuniteMaxPrice));
						secondSubSection.setModifyDate(new Date());
						secondSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						secondSubSection.setPriceType(newSubSection.getPriceType());
						secondSubSection.setRemark(newSubSection.getRemark());
						secondSubSection.setSectionAccodingItem(accodingItem);
						reunitedSubSections.add(secondSubSection);
						
						if (endValue != reuniteEndValue) {
							//第三个分段
							PriceSectionSubEntity thirdSubSection = new PriceSectionSubEntity();
							thirdSubSection.setActive(MiserConstants.ACTIVE);
							thirdSubSection.setCalcAlone(newSubSection.getCalcAlone());
							thirdSubSection.setCreateDate(new Date());
							thirdSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
							thirdSubSection.setStartValue(endValue);
							thirdSubSection.setEndValue(reuniteEndValue);
							thirdSubSection.setPrice(reunitePrice);
							thirdSubSection.setMinPrice(reuniteMinPrice);
							thirdSubSection.setMaxPrice(reuniteMaxPrice);
							thirdSubSection.setModifyDate(new Date());
							thirdSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
							thirdSubSection.setPriceType(newSubSection.getPriceType());
							thirdSubSection.setRemark(newSubSection.getRemark());
							thirdSubSection.setSectionAccodingItem(accodingItem);
							reunitedSubSections.add(thirdSubSection);
						}
					} else {
						reunitedSubSections.add(reuniteSubSection);
					}
				} else {//待融合分段结尾超过了融合前分段结尾
					//非完美融合，只融合到融合前分段的结尾即可，融合前分段结尾-待融合分段结尾和后续分段进行融合
					reuniteType = ReunitePriceSectionConstant.REUNITE_TYPE_IMPERFECT_REUNITED;
					//如果费用、最低、最高有一个不相同，则产生两个分段
					if (price != reunitePrice || minPrice != reuniteMinPrice || maxPrice != reuniteMaxPrice) {
						//第一个分段
						if (reuniteStartValue != startValue) {
							PriceSectionSubEntity firstSubSection = new PriceSectionSubEntity();
							firstSubSection.setActive(MiserConstants.ACTIVE);
							firstSubSection.setCalcAlone(reuniteSubSection.getCalcAlone());
							firstSubSection.setCreateDate(new Date());
							firstSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
							firstSubSection.setStartValue(reuniteStartValue);
							firstSubSection.setEndValue(startValue);
							firstSubSection.setPrice(reunitePrice);
							firstSubSection.setMinPrice(reuniteMinPrice);
							firstSubSection.setMaxPrice(reuniteMaxPrice);
							firstSubSection.setModifyDate(new Date());
							firstSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
							firstSubSection.setPriceType(reuniteSubSection.getPriceType());
							firstSubSection.setRemark(reuniteSubSection.getRemark());
							firstSubSection.setSectionAccodingItem(accodingItem);
							reunitedSubSections.add(firstSubSection);
						}

						//第二个分段
						PriceSectionSubEntity secondSubSection = new PriceSectionSubEntity();
						secondSubSection.setActive(MiserConstants.ACTIVE);
						secondSubSection.setCalcAlone(newSubSection.getCalcAlone());
						secondSubSection.setCreateDate(new Date());
						secondSubSection.setCreateUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						secondSubSection.setStartValue(startValue);
						secondSubSection.setEndValue(reuniteEndValue);
						secondSubSection.setPrice(Math.min(price, reunitePrice));
						secondSubSection.setMinPrice(Math.min(minPrice, reuniteMinPrice));
						secondSubSection.setMaxPrice(Math.max(maxPrice, reuniteMaxPrice));
						secondSubSection.setModifyDate(new Date());
						secondSubSection.setModifyUser(ReunitePriceSectionConstant.REUNITE_SYSTEM_USER);
						secondSubSection.setPriceType(newSubSection.getPriceType());
						secondSubSection.setRemark(newSubSection.getRemark());
						secondSubSection.setSectionAccodingItem(accodingItem);
						reunitedSubSections.add(secondSubSection);
					} else {//使用融合前分段，然后和后续的分段继续进行融合
						reunitedSubSections.add(reuniteSubSection);
					}
				}
			} else {
				reuniteType = ReunitePriceSectionConstant.REUNITE_TYPE_UNREUNITED_AFTER;
			}
		}
		returnValue.add(reuniteType);
		returnValue.add(reunitedSubSections);
		return returnValue;
	}
	@Override
	public String addSyncPriceSection(PriceSectionEntity pse,String priceSectionSubList) {
		List<PriceSectionSubEntity> jsonArray = com.alibaba.fastjson.JSON.parseArray(priceSectionSubList,PriceSectionSubEntity.class);
		beforAddPriceSectionSync(pse);
		String code=serialNumberService.generateSerialNumber(SerialNumberRuleEnum.PC_SECTION_CODE);
		pse.setCode(code);
		//纯运费分段折扣	
		if("FREIGHT".equals(pse.getSectionedItem())){
			pse.setName("纯运费"+code);
			pse.setShortCode("CYF"+code);
		}
		//提货费分段折扣	
		if("PICKUP".equals(pse.getSectionedItem())){
			pse.setName("提货费"+code);
			pse.setShortCode("THF"+code);
		}
		//送货费分段折扣	
		if("DELIVERY".equals(pse.getSectionedItem())){
			pse.setName("送货费"+code);
			pse.setShortCode("SHF"+code);
		}
		//保价费率分段折扣	
		if("INSURANCE_RATE".equals(pse.getSectionedItem())){
			pse.setName("保价费率"+code);
			pse.setShortCode("BJFL"+code);
		}
		//保价费分段折扣	
		if("INSURANCE".equals(pse.getSectionedItem())){
			pse.setName("保价费"+code);
			pse.setShortCode("BJF"+code);
		}
		//工本费分段折扣	
		if("PAPER".equals(pse.getSectionedItem())){
			pse.setName("工本费"+code);
			pse.setShortCode("GBF"+code);
		}
		//信息费分段折扣	
		if("SMS".equals(pse.getSectionedItem())){
			pse.setName("信息费"+code);
			pse.setShortCode("XXF"+code);
		}
		//燃油费分段折扣	
		if("FUEL".equals(pse.getSectionedItem())){
			pse.setName("燃油费"+code);
			pse.setShortCode("RYF"+code);
		}
		//附加费分段折扣	
		if("EXTRA_FEE".equals(pse.getSectionedItem())){
			pse.setName("附加费"+code);
			pse.setShortCode("FJF"+code);
		}
		//代收手续费率分段折扣	
		if("COLLECT_RATE".equals(pse.getSectionedItem())){
			pse.setName("代收手续费率"+code);
			pse.setShortCode("DSJXFL"+code);
		}
		//代收手续费分段折扣	
		if("COLLECTION_RATE".equals(pse.getSectionedItem())){
			pse.setName("代收手续费"+code);
			pse.setShortCode("DSJXF"+code);
		}
		//上楼费分段折扣	
		if("UPSTAIRS".equals(pse.getSectionedItem())){
			pse.setName("上楼费"+code);
			pse.setShortCode("SLF"+code);
		}
		
		priceSectionDao.addPriceSection(pse);
		for(PriceSectionSubEntity npse:jsonArray){
			beforAddPriceSectionSubSync(npse);
			npse.setSectionId(pse.getId());
			priceSectionDao.addPriceSectionSub(npse);
		}
		return code;
	}
	
	/**
	 * 
	 * @Description: 优惠分段明细新增赋值
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月23日
	 */
	public void beforAddPriceSectionSubSync(PriceSectionSubEntity pse){
		Date dt = new Date();
		pse.setId(UUIDUtil.getUUID());
		pse.setCreateDate(dt);
		pse.setCreateUser(pse.getCreateUser());
		pse.setModifyDate(dt);
		pse.setModifyUser(pse.getCreateUser());
		pse.setActive(MiserConstants.YES);
	}
	@Override
	public List<PriceSectionEntity> excelQueryListByParam(PriceSectionVo psv) {
		return priceSectionDao.excelQueryListByParam(psv.getPriceSectionEntity());
	}
	@Override
	public String createExcelFile(List<PriceSectionEntity> list) {
		String filePath=null;
		List<String> titleNames=new ArrayList<String>();
		 titleNames.add("优惠分段名称");
		 titleNames.add("优惠分段简码");
		 titleNames.add("运输类型");
		 titleNames.add("使用费用类型");
		 titleNames.add("主表备注");
		 titleNames.add("是否有效");
		 titleNames.add("修改用户");
		 titleNames.add("修改时间");
		 titleNames.add("分段依据");
		 titleNames.add("段起");
		 titleNames.add("段止");
		 titleNames.add("是否阶梯计算");
		 titleNames.add("费用类型");
		 titleNames.add("费用");
		 titleNames.add("最低收费");
		 titleNames.add("最高收费");
		 titleNames.add("明细表备注");
		
		List<String> paramNames=new ArrayList<String>();
		paramNames.add("name");
		paramNames.add("shortCode");
		paramNames.add("suitProductName");
		paramNames.add("sectionedItem");
		paramNames.add("remark");
		paramNames.add("active");
		paramNames.add("modifyUser");
		paramNames.add("modifyDate");
		paramNames.add("sectionAccodingItem");
		paramNames.add("startValue");
		paramNames.add("endValue");
		paramNames.add("calcAlone");
		paramNames.add("priceType");
		paramNames.add("price");
		paramNames.add("minPrice");
		paramNames.add("maxPrice");
		paramNames.add("sub_remark");
		try {
			filePath=new ExcelUtil<PriceSectionEntity>().exportExcel(titleNames,paramNames,list);
		} catch (Exception e) {
			// TODO: handle exception
			filePath=null;
		}
		
		return filePath;
	}
	
}

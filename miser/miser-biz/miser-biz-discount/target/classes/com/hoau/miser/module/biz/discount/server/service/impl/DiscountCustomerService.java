/**   
 * @Title: DiscountCustomerService.java 
 * @Package com.hoau.miser.module.biz.discount.server.service.impl 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 陈启勇 
 * @date 2016年1月5日 下午4:05:58 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.discount.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;



import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.ITranstypeService;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;
import com.hoau.miser.module.biz.discount.api.server.service.IDiscountCustomerService;
import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountCorpEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountCustomerEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountCustomerVo;
import com.hoau.miser.module.biz.discount.server.dao.DiscountCustomerDao;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionVo;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.base.api.server.service.IDataDictionaryValueService;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryValueEntity;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.DateUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;


/**
 * ClassName: DiscountCustomerService
 * 
 * @author 陈启勇
 * @date 2016年1月13日
 * @version V1.0
 */
@Service
public class DiscountCustomerService implements IDiscountCustomerService {
	private static final Logger log = LoggerFactory
			.getLogger(DiscountCustomerEntity.class);
	@Resource
	private DiscountCustomerDao discountCustomerDao;
	@Resource
	private IPriceSectionService priceSectionService;
	@Resource
	private ITranstypeService transtypeService;

	@Resource
	private IDataDictionaryValueService dictionaryValue;
	
	@Override
	public List<DiscountCustomerEntity> queryListByParam(
			DiscountCustomerVo beanv, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return discountCustomerDao.queryListByParam(beanv, rowBounds);
	}

	@Override
	public Long queryCountByParam(DiscountCustomerVo beanv) {
		return (long) discountCustomerDao.queryCountByParam(beanv);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hoau.miser.module.biz.discount.server.service.IDiscountCustomerService
	 * #addDiscountCustomer(com.hoau.miser.module.biz.discount.shared.domain.
	 * DiscountCustomerEntity, java.lang.Boolean)
	 */
	@Override
	@Transactional
	public Integer addDiscountCustomer(DiscountCustomerEntity bean,
			Boolean isConfirmAdd) {
		Integer result = 1;
		Date effectiveTime = bean.getEffectiveTime();
		Date invalidTime = bean.getInvalidTime();
		if (effectiveTime != null && invalidTime != null) {
			long currentTime = System.currentTimeMillis();
			Date date = new Date(currentTime);
			if (effectiveTime.compareTo(date) < 0) {
				bean.setEffectiveTime(date);
			}
			if (invalidTime.compareTo(effectiveTime) <= 0
					&& invalidTime.compareTo(date) <= 0) {
				return 3;
			}
		}
		result = addOrUpdateDiscountCustomer(bean, isConfirmAdd, "ADD");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hoau.miser.module.biz.discount.server.service.IDiscountCustomerService
	 * #updateDiscountCustomer(com.hoau.miser.module.biz.discount.shared.domain.
	 * DiscountCustomerEntity, java.lang.Boolean)
	 */
	@Override
	@Transactional
	public Integer updateDiscountCustomer(DiscountCustomerEntity bean,
			Boolean isConfirm) {
		Integer result = 1;
		Date effectiveTime = bean.getEffectiveTime();
		Date invalidTime = bean.getInvalidTime();
		if (effectiveTime != null && invalidTime != null) {  //生效时间不能小于等于失效时间
			long currentTime = System.currentTimeMillis();
			Date date = new Date(currentTime);
			if (invalidTime.compareTo(effectiveTime) <= 0
					&& invalidTime.compareTo(date) <= 0) {
				return 3;
			}
		}
		
        //add by dengyin 2016-5-16 15:26:29 生效时间小于当前时间时 需要置为当前时间
        Date curDateTime = new Date();
        if(effectiveTime.compareTo(curDateTime) < 0 ){
            bean.setEffectiveTime(curDateTime);
        }
        //end by dengyin 2016-5-16 15:26:29 生效时间小于当前时间时 需要置为当前时间
		
		result = addOrUpdateDiscountCustomer(bean, isConfirm, "UPDATE");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hoau.miser.module.biz.discount.server.service.IDiscountCustomerService
	 * #bathDelDiscountCustomer(java.util.List)
	 */
	@Override
	public void bathDelDiscountCustomer(List<DiscountCustomerEntity> entitys) {
		for (DiscountCustomerEntity bean : entitys) {
			delDiscountCustomer(bean);
		}

	}

	@Transactional
	public void delDiscountCustomer(DiscountCustomerEntity bean) {
		bean.setRemark(MiserUserContext.getCurrentUser().getUserName() + " 于 "
				+ DateUtils.convert(new Date()) + " 手动删除");
		beforAdd(bean);
		bean.setActive(MiserConstants.NO);
		bean.setInvalidTime(new Date());
		discountCustomerDao.updateDiscountCustomer(bean);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hoau.miser.module.biz.discount.server.service.IDiscountCustomerService
	 * #queryDiscountCustomerById(java.lang.String)
	 */
	@Override
	public DiscountCustomerEntity queryById(String id) {
		return discountCustomerDao.selectByPrimaryKey(id);
	}

	private Integer addOrUpdateDiscountCustomer(DiscountCustomerEntity bean,
			Boolean isConfirmAdd, String flag) {
		// 默认成功
		Integer result = 1;
		// 查询历史有效信息
		DiscountCustomerEntity queryEntity = new DiscountCustomerEntity();
		queryEntity.setActive(MiserConstants.YES);
		queryEntity.setCustomerCode(bean.getCustomerCode());
		queryEntity.setTransTypeCode(bean.getTransTypeCode());
		queryEntity.setState(DiscountCustomerEntity.STATE_2);
		DiscountCustomerVo queryVo1 = new DiscountCustomerVo();
		queryVo1.setDiscountCustomerEntity(queryEntity);
		// 查询生效中的信息
		List<DiscountCustomerEntity> state2List = this.queryListByParam(
				queryVo1, 1, 0);
		// 查询待生效的信息
		queryEntity.setState(DiscountCustomerEntity.STATE_3);
		List<DiscountCustomerEntity> state3List = this.queryListByParam(
				queryVo1, 1, 0);





		
 

	 
		
		if (state2List != null && state2List.size() > 0) {
			DiscountCustomerEntity state2Entity = state2List.get(0);
			
			//只有取出的数据  不是当前修改的 数据对象时 才需要进行 时间重叠的判断 
			if( ! bean.getId().equals(state2Entity.getId())){
				//无论新增  还是 修改 时间重叠的判断
				if (bean.getEffectiveTime().getTime() >= state2Entity
						.getEffectiveTime().getTime()
						&& bean.getEffectiveTime().getTime() <= state2Entity
								.getInvalidTime().getTime()) {
					return 2;
				}
				
				if (bean.getInvalidTime().getTime() >= state2Entity
						.getEffectiveTime().getTime()
						&& bean.getInvalidTime().getTime() <= state2Entity
								.getInvalidTime().getTime()) {
					return 2;
				}
				
				if(state2Entity.getEffectiveTime().getTime()>=bean.getEffectiveTime().getTime()  
						&& state2Entity .getEffectiveTime().getTime() <= bean.getInvalidTime().getTime()){
					return 2;
				}
			}
			
			if ("UPDATE".equals(flag)) {
				
				String id = bean.getId();
				DiscountCustomerEntity oldBean = new DiscountCustomerEntity();
				if (StringUtil.isNotEmpty(id)) {
					oldBean = discountCustomerDao.selectByPrimaryKey(id);
				}
				
				Date nowDate = new Date();
				if (null == oldBean.getInvalidTime() || nowDate.getTime() <= oldBean.getInvalidTime().getTime() ) {
					state2Entity.setActive(MiserConstants.YES);
					state2Entity.setInvalidTime(nowDate);
					bean.setEffectiveTime(nowDate);
					beforAdd(state2Entity);
				}
				discountCustomerDao.updateDiscountCustomer(state2Entity);
			}
			 
			
			/*
			if ("UPDATE".equals(flag)) {
				Date nowDate = new Date();
				if (nowDate.getTime() <= oldBean.getInvalidTime().getTime()) {
					state2Entity.setActive(MiserConstants.YES);
					state2Entity.setInvalidTime(nowDate);
					bean.setEffectiveTime(nowDate);
					beforAdd(state2Entity);
				}
				discountCustomerDao.updateDiscountCustomer(state2Entity);
			} else {
				if (bean.getEffectiveTime().getTime() >= state2Entity
						.getEffectiveTime().getTime()
						&& bean.getEffectiveTime().getTime() <= state2Entity
								.getInvalidTime().getTime()) {
					return 2;
				}
			}
			*/
			
		}
		if (state3List != null && state3List.size() > 0) {// 存在待生效信息
			if (isConfirmAdd) {// 确认新增
				// 将旧的待生效信息作废
				DiscountCustomerEntity state3Entity = state3List.get(0);
				state3Entity.setActive(MiserConstants.NO);
				beforAdd(state3Entity);
				discountCustomerDao.updateDiscountCustomer(state3Entity);
			} else {// 表示需要确认是否新增
				result = 0;
				return result;
			}
		}
		// 新增待生效信息
		bean.setId(null);
		beforAdd(bean);
		discountCustomerDao.insert(bean);

		return result;
	}

	/**
	 * 
	 * @Description: 客户折扣初始值
	 * @param @param bean
	 * @return void
	 * @throws
	 * @author 陈启勇
	 * @date 2016年1月6日
	 */
	public DiscountCustomerEntity beforAdd(DiscountCustomerEntity bean) {
		Date dt = new Date();
		if (StringUtil.isBlank(bean.getId())) {// 新增的话
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hoau.miser.module.biz.discount.server.service.IDiscountCustomerService
	 * #excelQueryListByParam(com.hoau.miser.module.biz.discount.shared.vo.
	 * DiscountCustomerVo)
	 */
	@Override
	public List<DiscountCustomerEntity> excelQueryListByParam(
			DiscountCustomerVo beanv) {
		return discountCustomerDao.excelQueryListByParam(beanv);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hoau.miser.module.biz.discount.server.service.IDiscountCustomerService
	 * #createExcelFile(java.util.List)
	 */
	@Override
	public ExcelExportResultEntity createExcelFileToSql(
			DiscountCustomerEntity entityx) {
		List<String> titleNames = new ArrayList<String>();
		// titleNames.add("序号");
		titleNames.add("所在事业部");
		titleNames.add("所在大区");
		titleNames.add("所在路区");
		titleNames.add("所在门店");
		titleNames.add("客户编号");
		titleNames.add("客户名称");
		titleNames.add("物流代码");
		titleNames.add("运输类型");
		titleNames.add("折扣基于标准价格时间");
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
		titleNames.add("优先类型");
		titleNames.add("生效日期");
		titleNames.add("失效日期");
		titleNames.add("备注");
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT /*+ORDERED*/ D.NAME AS DIVISION,");
		sql.append("C.NAME AS BIGREGION,");
		sql.append("B.NAME AS ROADAREA,");
		sql.append("A.NAME AS ORGCODENAME,");
		sql.append("CUSTOMER_CODE,");
		sql.append("TBC.NAME AS CUSTOMERNAME,");
		sql.append("A.LOGIST_CODE AS LOGIST_CODE,");
		sql.append("E.NAME AS TRANSTYPENAME,");
		sql.append("DISCOUNT_ACCODING_PC_TIME,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.Freight_Section_Code)as FREIGHT_SECTION_CODE,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.ADD_SECTION_CODE)as ADD_SECTION_CODE,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.FUEL_SECTION_CODE)as FUEL_SECTION_CODE,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.PICKUP_SECTION_CODE)as PICKUP_SECTION_CODE,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.DELIVERY_SECTION_CODE)as DELIVERY_SECTION_CODE,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.UPSTAIRS_SECTION_CODE)as UPSTAIRS_SECTION_CODE,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.INSURANCE_RATE_SECTION_CODE)as INSURANCE_RATE_SECTION_CODE,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.INSURANCE_SECTION_CODE)as INSURANCE_SECTION_CODE,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.PAPER_SECTION_CODE)as PAPER_SECTION_CODE,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.SMS_SECTION_CODE)as SMS_SECTION_CODE,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.COLLECTION_RATE_SECTION_CODE)as COLLECTION_RATE_SECTION_CODE,");
		sql.append("(SELECT name from t_price_section where active='Y' AND code=TDCM.COLLECTION_SECTION_CODE)as COLLECTION_SECTION_CODE,");
		sql.append("DISCOUNT_PRIORITY_TYPE,");
		sql.append("EFFECTIVE_TIME, INVALID_TIME,REMARK,");
		sql.append("CASE WHEN TDCM.ACTIVE = 'N' THEN 'DELETED'" +
		    " WHEN TDCM.INVALID_TIME IS NOT NULL AND TDCM.INVALID_TIME < SYSDATE THEN 'PASSED'" +
		    " WHEN TDCM.EFFECTIVE_TIME > SYSDATE THEN 'WAIT' " +
		    " ELSE 'EFFECTIVE' END AS STATUS");
		sql.append(" FROM T_DISCOUNT_CUSTOMER TDCM LEFT JOIN (SELECT CODE,NAME,ORG_CODE FROM T_BSE_CUSTOMER WHERE  ACTIVE = 'Y') TBC ON TBC.CODE = TDCM.CUSTOMER_CODE LEFT JOIN (SELECT CODE,NAME,PARENT_CODE,LOGIST_CODE FROM T_BSE_ORG WHERE  IS_SALES_DEPARTMENT = 'Y') A ON A.CODE = TBC.ORG_CODE LEFT JOIN (SELECT CODE,NAME,PARENT_CODE FROM T_BSE_ORG WHERE IS_ROAD_AREA = 'Y') B");
		sql.append(" ON A.PARENT_CODE = B.CODE");
		sql.append(" LEFT JOIN (SELECT CODE,NAME,PARENT_CODE FROM T_BSE_ORG WHERE IS_BIG_REGION = 'Y') C ");
		sql.append(" ON B.PARENT_CODE = C.CODE");
		sql.append(" LEFT JOIN (SELECT CODE,NAME,PARENT_CODE FROM T_BSE_ORG WHERE IS_DIVISION = 'Y') D ");
		sql.append(" ON C.PARENT_CODE = D.CODE");
		sql.append(" LEFT JOIN (SELECT CODE,NAME FROM T_BSE_TRANS_TYPE WHERE ACTIVE = 'Y') E ");
		sql.append(" ON TDCM.TRANS_TYPE_CODE = E.CODE");
		sql.append(" LEFT JOIN (SELECT EMP_NAME,USER_NAME FROM T_BSE_USER WHERE ACTIVE = 'Y') F ");
		sql.append(" ON TDCM.CREATE_USER_CODE = F.USER_NAME");
		sql.append(" LEFT JOIN (SELECT EMP_NAME,USER_NAME FROM T_BSE_USER WHERE ACTIVE = 'Y') G ");
		sql.append(" ON TDCM.MODIFY_USER_CODE = G.USER_NAME where 1=1 ");
		if (null != entityx) {
			if (StringUtils.isNotBlank(entityx.getTransTypeCode())) {
				sql.append(" AND TDCM.TRANS_TYPE_CODE=?");
				params.add(entityx.getTransTypeCode());
			}
			if (StringUtils.isNotBlank(entityx.getOrgCode())) {
				sql.append(" AND TDCM.CUSTOMER_CODE in (select TB.CODE from T_BSE_CUSTOMER TB where TB.ORG_CODE=?)");
				params.add(entityx.getOrgCode());
			}
			if (StringUtils.isNotBlank(entityx.getRoadArea())
					&& StringUtils.isBlank(entityx.getOrgCode())) {
				sql.append(" AND TDCM.CUSTOMER_CODE in (select TB.CODE from T_BSE_CUSTOMER TB where TB.ORG_CODE in (SELECT CODE FROM T_BSE_ORG WHERE PARENT_CODE=? AND  IS_SALES_DEPARTMENT = 'Y'))");
				params.add(entityx.getRoadArea());
			}
			if (StringUtils.isNotBlank(entityx.getBigRegion())
					&& StringUtils.isBlank(entityx.getOrgCode())
					&& StringUtils.isBlank(entityx.getRoadArea())) {
				sql.append(" AND TDCM.CUSTOMER_CODE in (select TB.CODE from T_BSE_CUSTOMER TB where TB.ORG_CODE in (SELECT CODE FROM T_BSE_ORG WHERE PARENT_CODE in (SELECT CODE FROM T_BSE_ORG WHERE PARENT_CODE=? AND  IS_ROAD_AREA = 'Y')))");
				params.add(entityx.getBigRegion());
			}
			if (StringUtils.isNotBlank(entityx.getDivision())
					&& StringUtils.isBlank(entityx.getOrgCode())
					&& StringUtils.isBlank(entityx.getRoadArea())
					&& StringUtils.isBlank(entityx.getBigRegion())) {
				sql.append(" AND TDCM.CUSTOMER_CODE in (select TB.CODE from T_BSE_CUSTOMER TB where TB.ORG_CODE in (SELECT CODE FROM T_BSE_ORG WHERE PARENT_CODE in (SELECT CODE FROM T_BSE_ORG WHERE PARENT_CODE in (SELECT CODE FROM T_BSE_ORG WHERE PARENT_CODE=? AND  IS_BIG_REGION = 'Y'))))");
				params.add(entityx.getDivision());
			}
			if (StringUtils.isNotBlank(entityx.getCustomerCode())) {
				sql.append(" AND TDCM.CUSTOMER_CODE=?");
				params.add(entityx.getCustomerCode());
			}
			if (StringUtils.isNotBlank(entityx.getCustomerName())) {
				sql.append(" AND TDCM.CUSTOMER_CODE in (select TBN.CODE from T_BSE_CUSTOMER TBN where TBN.NAME LIKE CONCAT(CONCAT('%', ?),'%'))");
				params.add(entityx.getCustomerName());
			}
			if (null != entityx.getEffectiveTime()) {
				sql.append(" AND TDCM.EFFECTIVE_TIME<=? AND (TDCM.INVALID_TIME>=? or TDCM.INVALID_TIME is null)");
				params.add(entityx.getEffectiveTime());
				params.add(entityx.getEffectiveTime());
			}
			sql.append(") M");
			if (StringUtils.isNotBlank(entityx.getState())) {
				sql.append(" where 1 = 1");
				if ("1".equals(entityx.getState())) {
					//sql.append(" AND (sysdate>TDCM.INVALID_TIME and TDCM.ACTIVE='Y')");
					sql.append(" AND M.STATUS = 'PASSED'");
				} else if ("2".equals(entityx.getState())) {
					//sql.append(" AND ((sysdate>=TDCM.EFFECTIVE_TIME and TDCM.INVALID_TIME is null and TDCM.ACTIVE='Y')or (sysdate>=TDCM.EFFECTIVE_TIME  and TDCM.INVALID_TIME is not null and sysdate<TDCM.INVALID_TIME and TDCM.ACTIVE='Y'))");
					sql.append(" AND M.STATUS = 'EFFECTIVE'");
				} else if ("3".equals(entityx.getState())) {
					//sql.append(" AND (sysdate<=TDCM.EFFECTIVE_TIME and TDCM.ACTIVE='Y')");
					sql.append(" AND M.STATUS = 'WAIT'");
				} else if ("4".equals(entityx.getState())) {
					//sql.append(" AND TDCM.ACTIVE='N'");
					sql.append(" AND M.STATUS = 'DELETED'");
				} else if ("5".equals(entityx.getState())) {
					//sql.append(" AND TDCM.ACTIVE='Y'");
				}
			}
		}
		ExcelExportResultEntity resultEntity  = null;
		try {
			// filePath=new
			// ExcelUtil<DiscountCustomerEntity>().exportExcel(titleNames,paramNames,list);
			resultEntity = new ExcelUtil<Object>()
					.sqlTofile(titleNames, sql.toString(), params);
		} catch (Exception e) {
			log.error("生成导出数据异常!", e);
			resultEntity = null;
		}

		return resultEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hoau.miser.module.biz.discount.server.service.IDiscountCustomerService
	 * #bathImplDiscountCustomer(java.lang.String)
	 */
	@Override
	public Map<String, Object> bathImplDiscountCustomer(String path)
			throws Exception {
		Map<String, Object> retuMap = new HashMap<String, Object>();
		List<Map<String, String>> list;
		List<DiscountCustomerEntity> dcList = new ArrayList<DiscountCustomerEntity>();
		list = ExcelUtil.readToListByFile(path, 28, 2);
		// 运输类型
		Map<String, String> transTypeCodeMap = new HashMap<String, String>();
		Map<String, String> transTypeCodeMap2 = new HashMap<String, String>();

		TranstypeVo transtypeVo = new TranstypeVo();
		TranstypeEntity transtypeEntity = new TranstypeEntity();
		transtypeEntity.setActive(MiserConstants.YES);
		transtypeVo.setTranstypeEntity(transtypeEntity);
		List<TranstypeEntity> transtypeEntityList = transtypeService
				.queryTranstypes(transtypeVo);
		for (TranstypeEntity value : transtypeEntityList) {
			transTypeCodeMap.put(value.getName(), value.getCode());
			transTypeCodeMap2.put(value.getCode(), value.getName());
		}
		
		//折扣类型
		Map<String,String> mapdve = new HashMap<String, String>();
		List<DataDictionaryValueEntity> dve = dictionaryValue.queryParamByTermsCode("CUSTOMER_DISCOUNT_PRIORITY_LEVEL");
		for (DataDictionaryValueEntity dataDictionaryValueEntity : dve) {
			mapdve.put(dataDictionaryValueEntity.getValueName(), dataDictionaryValueEntity.getValueCode());
		}

		// 优惠分段
		PriceSectionVo psv = new PriceSectionVo();
		PriceSectionEntity sEntity = new PriceSectionEntity();
		sEntity.setActive(MiserConstants.YES);
		psv.setPriceSectionEntity(sEntity);
		List<PriceSectionEntity> sList = priceSectionService
				.queryPriceSection(psv);
		Map<String, String> sectionMap = new HashMap<String, String>();
		Map<String, String> sectionMap2 = new HashMap<String, String>();
		for (PriceSectionEntity entity : sList) {
			sectionMap.put(entity.getName(), entity.getCode());
			sectionMap2.put(entity.getCode(), entity.getName());
		}
		for (Map<String, String> map : list) {
			// 添加参数检查
			if (null == map.get(0 + "") || "".equals(map.get(0 + ""))) {
				retuMap.put("error", "客户编码不能为空");
				break;
			}
			if (null == map.get(3 + "") || "".equals(map.get(3 + ""))) {
				retuMap.put("error", "运输类型不能为空");
				break;
			}
			if (null == map.get(18 + "") || "".equals(map.get(18 + ""))) {
				retuMap.put("error", "生效时间不能为空");
				break;
			}
			if (null == map.get(19 + "") || "".equals(map.get(19 + ""))) {
				retuMap.put("error", "失效时间不能为空");
				break;
			}
			if (!transTypeCodeMap.containsKey(StringUtil.trim(map.get(3 + "")))) {
				retuMap.put("error",
						"不存在的运输类型:" + StringUtil.trim(map.get(3 + "")));
				break;
			}
			for (int dataIndex = 5; dataIndex <= 16; dataIndex++) {
				if (!CheckPriceSection(sectionMap, map, dataIndex)) {
					retuMap.put(
							"error",
							"不存在的优惠分段:"
									+ StringUtil.trim(map.get(dataIndex + "")));
					break;
				}
			}
			if (null != retuMap.get("error")) {
				break;
			}
			if (null == DateUtils.convert(map.get(18 + ""))) {
				retuMap.put("error", "生效时间格式不正确，请输入yyyy-MM-dd HH:mm:ss格式的时间");
				break;
			}
			if (null == DateUtils.convert(map.get(19 + ""))) {
				retuMap.put("error", "失效时间格式不正确，请输入yyyy-MM-dd HH:mm:ss格式的时间");
				break;
			}
			//
			DiscountCustomerEntity pcbean = new DiscountCustomerEntity();
			try {
				pcbean.setActive("Y");
				pcbean.setCustomerCode(StringUtil.trim(map.get(0 + "")));
				pcbean.setTransTypeCode(transTypeCodeMap.get(StringUtil
						.trim(map.get(3 + ""))));
				pcbean.setDiscountAccodingPcTime(DateUtils.convert(map
						.get(4 + "")));
				pcbean.setFreightSectionCode(sectionMap.get(StringUtil.trim(map
						.get(5 + ""))));
				pcbean.setAddSectionCode(sectionMap.get(StringUtil.trim(map
						.get(6 + ""))));
				pcbean.setFuelSectionCode(sectionMap.get(StringUtil.trim(map
						.get(7 + ""))));
				pcbean.setPickupSectionCode(sectionMap.get(StringUtil.trim(map
						.get(8 + ""))));
				pcbean.setDeliverySectionCode(sectionMap.get(StringUtil
						.trim(map.get(9 + ""))));
				pcbean.setUpstairsSectionCode(sectionMap.get(StringUtil
						.trim(map.get(10 + ""))));
				pcbean.setInsuranceRateSectionCode(sectionMap.get(StringUtil
						.trim(map.get(11 + ""))));
				pcbean.setInsuranceSectionCode(sectionMap.get(StringUtil
						.trim(map.get(12 + ""))));
				pcbean.setPaperSectionCode(sectionMap.get(StringUtil.trim(map
						.get(13 + ""))));
				pcbean.setSmsSectionCode(sectionMap.get(StringUtil.trim(map
						.get(14 + ""))));
				pcbean.setCollectionRateSectionCode(sectionMap.get(StringUtil
						.trim(map.get(15 + ""))));
				pcbean.setCollectionSectionCode(sectionMap.get(StringUtil
						.trim(map.get(16 + ""))));
				String pType = StringUtil.trim(map.get(17 + ""));
				if(mapdve.containsKey(pType)&& StringUtils.isNotBlank(mapdve.get(pType))){
					pcbean.setDiscountPriorityType(mapdve.get(pType));
				}else{
					retuMap.put("error", "优先类型：" + pType + "不存在");
					break;
				}
				pcbean.setEffectiveTime(DateUtils.strToDate(map.get(18 + "")));
				pcbean.setInvalidTime(DateUtils.strToDate(map.get(19 + "")));
				pcbean.setRemark(StringUtil.trim(map.get(20 + "")));
				dcList.add(pcbean);
			} catch (Exception e) {
				pcbean = null;// 如果有异常就把pcbean设为null,这样每条信息都加进去了
				log.error("批量导入模版数据异常，业务需要仅作提示", e);

			} finally {
			}

		}
		Map<String, Long> countMap = new HashMap<String, Long>();
		countMap.put("addSize", new Long(0));// 增加条数
		countMap.put("coverSize", new Long(0));// 覆盖条数
		Long beforAddSize = null;
		Long beforCoverSize = null;

		for (int i = 0; i < dcList.size(); i++) {
			DiscountCustomerEntity pcbean = dcList.get(i);
			try {
				if (pcbean == null) {// 有异常
					continue;
				} else {
					beforAddSize = countMap.get("addSize");
					beforCoverSize = countMap.get("coverSize");
					this.addOrUpdateDiscountCustomer(pcbean, countMap);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hoau.miser.module.biz.discount.server.service.IDiscountCustomerService
	 * #bathImplDiscountCustomer(java.lang.String)
	 */
	private void addOrUpdateDiscountCustomer(DiscountCustomerEntity pcbean,
			Map<String, Long> countMap) throws Exception {
		long addSize = countMap.get("addSize").longValue();
		long coverSize = countMap.get("coverSize").longValue();

		// 查询历史有效信息
		DiscountCustomerEntity queryEntity = new DiscountCustomerEntity();
		queryEntity.setActive(MiserConstants.YES);
		queryEntity.setTransTypeCode(pcbean.getTransTypeCode());
		queryEntity.setState(DiscountCustomerEntity.STATE_2);
		queryEntity.setCustomerCode(pcbean.getCustomerCode());
		DiscountCustomerVo queryVo1 = new DiscountCustomerVo();
		if (pcbean.getTransTypeCode() == null) {
			throw new Exception("运输类型数据错误！");
		}
		queryVo1.setDiscountCustomerEntity(queryEntity);
		// 查询生效中的信息
		List<DiscountCustomerEntity> state2List = this.queryListByParam(
				queryVo1, 1, 0);
		// 查询待生效的信息
		queryEntity.setState(DiscountCustomerEntity.STATE_3);
		List<DiscountCustomerEntity> state3List = this.queryListByParam(
				queryVo1, 1, 0);
		if (state3List != null && state3List.size() > 0) {// 存在待生效信息
			// 将旧的待生效信息作废
			DiscountCustomerEntity state3Entity = state3List.get(0);
			state3Entity.setActive(MiserConstants.NO);
			beforAdd(state3Entity);
			discountCustomerDao.updateDiscountCustomer(state3Entity);
			coverSize++;// 替换
		} else {
			addSize++;// 增加
		}

		// 新增待生效信息
		Long minuteDiff2 = DateUtils.getMinuteDiff(pcbean.getEffectiveTime(),
				new Date());
		if (minuteDiff2 > 0)
			pcbean.setEffectiveTime(new Date());
		pcbean.setId(null);
		beforAdd(pcbean);
		discountCustomerDao.insert(pcbean);

		if (state2List != null && state2List.size() > 0) {
			DiscountCustomerEntity state2Entity = state2List.get(0);
			beforAdd(state2Entity);
			state2Entity.setActive(MiserConstants.NO);
			discountCustomerDao.updateDiscountCustomer(state2Entity);
		}
		countMap.put("addSize", addSize);
		countMap.put("coverSize", coverSize);
	}

	@Override
	public String createExcelFile() {
		// 优惠分段
		String filePath = null;
		List<String> titleNames = new ArrayList<String>();
		titleNames.add("序号");
		titleNames.add("所在事业部");
		titleNames.add("所在大区");
		titleNames.add("所在路区");
		titleNames.add("所在门店");
		titleNames.add("客户编号");
		titleNames.add("客户名称");
		titleNames.add("运输类型");
		titleNames.add("折扣基于标准价格时间");
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
		titleNames.add("优先类型");
		titleNames.add("生效日期");
		titleNames.add("失效日期");
		titleNames.add("备注");
		try {
			filePath = new ExcelUtil<DiscountCorpEntity>()
					.exportExcel(titleNames);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("生成导出数据异常!", e);
			filePath = null;
		}
		return filePath;
	}

	/**
	 * 
	 * @Description: 检查优惠分段是否存在
	 * @param @param sectionMap
	 * @param @param datamap
	 * @param @param dataIndex
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author 赵金义
	 * @date 2016年4月18日
	 */
	private boolean CheckPriceSection(Map<String, String> sectionMap,
			Map<String, String> datamap, int dataIndex) {
		boolean res = true;
		if (null != datamap.get(dataIndex + "")
				&& !"".equals(datamap.get(dataIndex + ""))) {
			if (!sectionMap.containsKey(StringUtil.trim(datamap.get(dataIndex
					+ "")))) {
				res = false;
			}
		}
		return res;
	}
}

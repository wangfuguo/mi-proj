/**   
 * @Title: PriceCustomerService.java 
 * @Package com.hoau.miser.module.biz.pricecard.api.server.service.impl 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月5日 下午1:42:29 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.server.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.miser.module.biz.pricecard.api.server.service.IBseCustomerService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerSubService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.BseCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.BseCustomerVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerVo;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceCustomerDao;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.base.api.server.service.ICommonOrgService;
import com.hoau.miser.module.sys.base.api.shared.domain.CommonOrgEntity;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 客户专属价格接口实现 ClassName: PriceCustomerService
 * 
 * @author dengyin
 * @date 2016年1月5日
 * @version V1.0
 */
@Service
public class PriceCustomerService implements IPriceCustomerService {

	@Resource
	private PriceCustomerDao priceCustomerDao;

	@Resource
	private IPriceCustomerSubService priceCustomerSubService;

	@Resource
	private ICommonOrgService commonOrgService;
	
	@Resource
	private IBseCustomerService bseCustomerService;

	/**
	 * @Description: 提交客户专属价格及其明细用于保存前的一些后台业务规则校验
	 * @param @param priceCustomerVo
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月18日
	 */
	public String beforeAddCustomerAndSubItemCheck(
			PriceCustomerVo priceCustomerVo) {

		String result = "success";
		Date effectiveTime = priceCustomerVo.getPriceCustomerEntity().getEffectiveTime();
		
		//判断 填写的 生效时间 小于 当前时间
		Date curDate = new Date();
		if(effectiveTime.before(curDate))
		{
			priceCustomerVo.getPriceCustomerEntity().setEffectiveTime(curDate);
		}
		
		/**
		 * 每个客户在生效中的专属价格 只能有一个。 所以提交时需验证该客户在同一部门是否存在有效时间重合，且当前状态为生效中的数据
		 */
		int countOfRows = priceCustomerDao
				.checkCustomerPriceDiscountExistRepeatTimezone(priceCustomerVo
						.getPriceCustomerEntity());
		if (countOfRows > 0) {
			return "priceCustomer.priceDiscountExistRepeatTimezone";
		}
		

		
		//add by dengyin 2016-4-9 11:18:16 对于前台提交的明细记录 必填项字段进行校验
		List<PriceCustomerSubEntity> priceCustomerSubEntityList = priceCustomerVo.getPriceCustomerSubEntityList();
		if(priceCustomerSubEntityList != null && priceCustomerSubEntityList.size() > 0){
			for(PriceCustomerSubEntity curSubEntity : priceCustomerSubEntityList){
				
				/*
				 * modify by dengyin 2016-4-12 15:48:46 屏蔽掉对于 出发价格城市 必填的校验
				if(curSubEntity.getSendPriceCityName() == null || "".equals(curSubEntity.getSendPriceCityName())){
					result =  "priceCustomer.subDetailSendPriceCityNameCannotNull";
					break;
				}
				
				
				if(curSubEntity.getSendPriceCity() == null || "".equals(curSubEntity.getSendPriceCity())){
					result =  "priceCustomer.subDetailSendPriceCityCodeCannotNull";
					break;
				}
				*/
				
				if(curSubEntity.getArrivePriceCityName() == null || "".equals(curSubEntity.getArrivePriceCityName())){
					result =  "priceCustomer.subDetailArrivePriceCityNameCannotNull";
					break;
				}
				
				if(curSubEntity.getArrivePriceCity()== null || "".equals(curSubEntity.getArrivePriceCity())){
					result =  "priceCustomer.subDetailArrivePriceCityCodeCannotNull";
					break;
				}				
				
				if(curSubEntity.getTransTypeName() == null || "".equals(curSubEntity.getTransTypeName())){
					result =  "priceCustomer.subDetailTransTypeNameCannotNull";
					break;					
				}
				
				if(curSubEntity.getTransTypeCode() == null || "".equals(curSubEntity.getTransTypeCode())){
					result =  "priceCustomer.subDetailTransTypeCodeCannotNull";
					break;		
				}
				
				if(curSubEntity.getWeightPrice() == null ){
					result =  "priceCustomer.subDetailWeightPriceCannotNull";
					break;		
				}
				
				if(curSubEntity.getVolumePrice() == null){
					result =  "priceCustomer.subDetailVolumePriceCannotNull";
					break;		
				}
			}
		}
		//end by dengyin 2016-4-9 11:18:16 对于前台提交的明细记录 必填项字段进行校验
		
		return result;
	}

	@Transactional
	public String addCustomerAndSubItemList(PriceCustomerVo priceCustomerVo) {

		//默认从前台传递的 id = '' 导致后台判断是否存在重复时间段时错误
		priceCustomerVo.getPriceCustomerEntity().setId(null);
		
		//从 修改操作 调用此方法时 因为前面已经调用过 如下的 check 操作这里通过判断 以减少调用
		String checkResult = "success";
		if(!"PASSED".equals(priceCustomerVo.getPriceCustomerEntity().getStatus())){
			checkResult = beforeAddCustomerAndSubItemCheck(priceCustomerVo);
		}

		if (checkResult.equals("success")) {
			PriceCustomerEntity priceCustomerEntity = beforOperDeal(priceCustomerVo
					.getPriceCustomerEntity());

			List<PriceCustomerSubEntity> priceCustomerSubEntityList = priceCustomerVo
					.getPriceCustomerSubEntityList();

			// 为 客户专属价格明细 设置主表的键值
			if (priceCustomerSubEntityList != null
					&& priceCustomerSubEntityList.size() > 0) {
				for (PriceCustomerSubEntity curSubEntity : priceCustomerSubEntityList) {
					curSubEntity.setParentId(priceCustomerEntity.getId());
				}
			}

			priceCustomerDao.insertSelective(priceCustomerEntity);
			priceCustomerSubService.batchInsertCustomerSub(priceCustomerVo
					.getPriceCustomerSubEntityList());

		}

		return checkResult;
	}

	@Transactional
	public String updateCustomerAndSubItemList(PriceCustomerVo priceCustomerVo) {
		
		//先进行相关检查
		String checkResult = beforeAddCustomerAndSubItemCheck(priceCustomerVo);
		
		if("success".equals(checkResult))
		{
			
			PriceCustomerEntity tmpObj = new PriceCustomerEntity();
			tmpObj.setId(priceCustomerVo.getPriceCustomerEntity().getId());
			
			List<PriceCustomerEntity>  tmpObjList = queryPriceCustomerMixed(tmpObj,1,0);
			if(tmpObjList != null && tmpObjList.size() > 0){
				tmpObj = tmpObjList.get(0);
				
				//针对于 待生效 的记录进行修改操作
				if("WAIT".equals(tmpObj.getStatus())){
					updateCustomerAndSubItemListForWait(priceCustomerVo);
				}
				
				//针对于 生效中 的记录进行修改操作
				if("EFFECTIVE".equals(tmpObj.getStatus())){
					updateCustomerAndSubItemListForEffective(priceCustomerVo);
				}
				
				//针对于 已过期 的记录进行修改操作 对原记录不进行处理 实际作为新增操作
				//变相的 复制 新增
				if("PASSED".equals(tmpObj.getStatus())){
					
					//add by dengyin 2016-5-23 17:10:06 针对于 已过期的数据 需要添加 失效时间是否大于当前时间
					Date curDate = new Date();
					
					//失效时间 小于 当前时间 则返回提示 
					if(priceCustomerVo.getPriceCustomerEntity().getInvalidTime().before(curDate)){
						return "priceCustomer.passedInvalidTimeMustThanCurTime";
					}
					else {
						priceCustomerVo.getPriceCustomerEntity().setStatus("PASSED");
						addCustomerAndSubItemList(priceCustomerVo);
					}
					//end by dengyin 2016-5-23 17:10:06 针对于 已过期的数据 需要添加 失效时间是否大于当前时间

				}
			}
		}
		
		return checkResult;

	}
	
	/**
	 * 针对于 生效中 的记录进行 更新操作
	 * @param priceCustomerVo
	 */
	public void updateCustomerAndSubItemListForEffective(PriceCustomerVo priceCustomerVo){
		
		/***************************对老数据的处理*****************************/
		//对主表的记录进行操作
		Date curTime = new Date();
		String curUserCode = MiserUserContext.getCurrentUser().getUserName();
		PriceCustomerEntity priceCustomerEntityTmp = new PriceCustomerEntity();
		
		String id = priceCustomerVo.getPriceCustomerEntity().getId();
		priceCustomerEntityTmp.setId(id);
		priceCustomerEntityTmp.setInvalidTime(curTime);
		priceCustomerEntityTmp.setModifyTime(curTime);
		priceCustomerEntityTmp.setModifyUserCode(curUserCode);
		priceCustomerDao.updateForEffectiveById(priceCustomerEntityTmp);
		 
		//对副表记录进行操作
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("modifyUserCode", curUserCode);
		paramMap.put("modifyTime", curTime);
		paramMap.put("parentId", id);
		priceCustomerSubService.batchUpdateForEffectiveByParentId(paramMap);
		
		
		/***************************对新数据的处理*****************************/
		//对主表的记录进行操作
		PriceCustomerEntity realPriceCustomerEntity = priceCustomerVo.getPriceCustomerEntity();
		//realPriceCustomerEntity.setEffectiveTime(curTime);
		realPriceCustomerEntity = beforOperDeal(realPriceCustomerEntity);
		priceCustomerDao.insertSelective(realPriceCustomerEntity);
		
		//对副表的记录进行操作
		
		List<PriceCustomerSubEntity> priceCustomerSubEntityList = priceCustomerVo.getPriceCustomerSubEntityList();
		
		// 为 客户专属价格明细 设置主表的键值
		if (priceCustomerSubEntityList != null
				&& priceCustomerSubEntityList.size() > 0) {
			for (PriceCustomerSubEntity curSubEntity : priceCustomerSubEntityList) {
				curSubEntity.setParentId(realPriceCustomerEntity.getId());
			}
		}
		
		priceCustomerSubService.batchInsertCustomerSub(priceCustomerSubEntityList);
	}
	
	/**
	 * 针对于 待生效 的记录的更新逻辑
	 * @param priceCustomerVo
	 */
	public void updateCustomerAndSubItemListForWait(PriceCustomerVo priceCustomerVo){
		String modifyUserCode = MiserUserContext.getCurrentUser().getUserName();
		
		/**
		 * 下面的数据 尽管是从当前表单获取的最新对象 但更新原有数据时 只是根据 id 更新 active = N (sql 中直接设值)
		 * modifyUserCode=当前用户 modifyTime=sysdate (sql 中直接设值) 
		 */
		PriceCustomerEntity oldPriceCustomerEntity = priceCustomerVo.getPriceCustomerEntity();
		oldPriceCustomerEntity.setModifyUserCode(modifyUserCode);


		// 更新老版本的 明细记录 根据客户专属价格老数据的 主键 作为其 parentId 来统一更新
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("modifyUserCode", modifyUserCode);
		paramMap.put("parentId", oldPriceCustomerEntity.getId());

		priceCustomerDao.updateForInvalidById(oldPriceCustomerEntity);
		priceCustomerSubService.batchUpdateForInvalidByParentId(paramMap);

		// 再将新的数据插入数据库
		PriceCustomerEntity priceCustomerEntity = beforOperDeal(priceCustomerVo
				.getPriceCustomerEntity());
		List<PriceCustomerSubEntity> priceCustomerSubEntityList = priceCustomerVo
				.getPriceCustomerSubEntityList();

		// 为 客户专属价格明细 设置主表的键值
		if (priceCustomerSubEntityList != null
				&& priceCustomerSubEntityList.size() > 0) {
			for (PriceCustomerSubEntity curSubEntity : priceCustomerSubEntityList) {
				curSubEntity.setParentId(priceCustomerEntity.getId());
			}
		}

		priceCustomerDao.insertSelective(priceCustomerEntity);
		priceCustomerSubService
				.batchInsertCustomerSub(priceCustomerSubEntityList);
	}

	/**
	 * @Description: 对选中的 客户专属记录 及其副表的明细记录 统一作废实现
	 * @param destoryIdStr
	 *            ： 选中的客户专属记录的 id 拼接串 '1','2'
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月14日
	 */
	@Transactional
	public String destoryPriceCustomerAndSubList(String destoryIdStr) {

		Map<String, Object> paramMap = new HashMap<String, Object>();

		String destroyIdStrA = " AND T.ID in (" + destoryIdStr + ")";
		paramMap.put("destoryIdStr", destroyIdStrA);
		
		//A 根据 id 串判断后台实际的状态仍是否为可用于作废 即 待生效 已生效 若不是 则需要返回 状态已发生变化 不能执行该操作
		List<PriceCustomerEntity> priceCustomerEntityList = priceCustomerDao.selectPriceCustomerEntityIncludeStatusIdStr(paramMap);
		
		//是否存在记录
		boolean isExistedRecord = false;
		
		//判断是否存在不能用于作废的记录
		boolean isExistedInvalidStatus = false;
		
		if(priceCustomerEntityList != null && priceCustomerEntityList.size() > 0)
		{
			isExistedRecord = true;
			
			//判断是否都为 待生效 或 已生效
			for(PriceCustomerEntity curEntity : priceCustomerEntityList)
			{
				//只有 待生效和已生效的数据，在客户专属价格管理界面选中某客户或在客户详情中，作废按钮才能可用，否则灰色禁用
				if( !("EFFECTIVE".equals(curEntity.getStatus()) || "WAIT".equals(curEntity.getStatus()))  )
				{
					isExistedInvalidStatus = true;
					break;
				}
		 
			}
		}
		
		if(isExistedInvalidStatus)
		{
			return "miser_priceCustomer_StatusChange";
		}
		else
		{
			//这时记录的状态应是一致的 
			if(isExistedRecord)
			{
				String modifyUserCode = MiserUserContext.getCurrentUser().getUserName();
				paramMap.put("modifyUserCode", modifyUserCode);
 
				//区分为 待生效 还是 生效中 
				String status = priceCustomerEntityList.get(0).getStatus();
				
				if(status.equals("WAIT")){
					destoryPriceCustomerForWati(paramMap,destoryIdStr);
				}
				
				if(status.equals("EFFECTIVE")){
					destoryPriceCustomerForEffective(paramMap,destoryIdStr);
				}
			}
			
		}
		
		return "miser_priceCustomer_destorySuccess";
	}
	
	/*
	 * 对于 生效中的数据进行作废  
	 * 不更新 active 
	 *  invalidTime=sysdate
	 *  modifyTime=sysdate
	 *  modifyUserCode=curUser
	 */
	public void destoryPriceCustomerForEffective(Map<String, Object> paramMap,String destoryIdStr){
		priceCustomerDao.destoryPriceCustomerForEffectiveByIdStr(paramMap);
	}
	
	
	/**
	 * //对于 待生效 的数据进行作废 
	 * ACTIVE=N 
	 * modifyUserCode=curUser
	 * modifyTime=sysdate
	 * @param paramMap
	 * @param destoryIdStr
	 */
	public void destoryPriceCustomerForWati(Map<String, Object> paramMap,String destoryIdStr){
		
		// 先更新主表
		priceCustomerDao.destoryPriceCustomerForWaitByIdStr(paramMap);

		String destroyIdStrB = "  AND T.PARENT_ID in (" + destoryIdStr + ")";
		paramMap.put("destoryIdStr", destroyIdStrB);

		// 再更新副表
		priceCustomerSubService.destoryPriceCustomerSubByParentIdStr(paramMap);
	}
	
	
	

	public PriceCustomerEntity beforOperDeal(
			PriceCustomerEntity priceCustomerEntity) {

		String id = null;
		Date dt = new Date();
		id = UUIDUtil.getUUID();
		priceCustomerEntity.setId(id);
		priceCustomerEntity.setActive(MiserConstants.YES);
		priceCustomerEntity.setCreateTime(dt);
		priceCustomerEntity.setCreateUserCode((MiserUserContext
				.getCurrentUser().getUserName()));

		priceCustomerEntity.setModifyTime(dt);
		priceCustomerEntity.setModifyUserCode(MiserUserContext.getCurrentUser()
				.getUserName());
		
		priceCustomerEntity.setDataOrign(DataOrignEnum.PMS.getCode());

		return priceCustomerEntity;
	}

	/**
	 * @Description: 客户专属界面实际查询 SQL
	 * @param @param priceCustomerEntity
	 * @param @return
	 * @return List<PriceCustomerEntity>
	 * @throws
	 * @author dengyin
	 * @date 2016年1月12日
	 */
	public List<PriceCustomerEntity> queryPriceCustomerMixed(
			PriceCustomerEntity priceCustomerEntity, int limit, int start) {

		RowBounds rowBounds = new RowBounds(start, limit);
		List<PriceCustomerEntity> priceCustomerEntityList = priceCustomerDao
				.queryPriceCustomerMixed(priceCustomerEntity, rowBounds);
		return priceCustomerEntityList;
	}
	
	public Long countOfPriceCustomerMixed(PriceCustomerEntity priceCustomerEntity){
		return priceCustomerDao.countOfPriceCustomerMixed(priceCustomerEntity);
	}

	/**
	 * @Description: 根据 组织编码 返回其父层的记录 统一设定到 PriceCustomerEntity 这里只会涉及 组织的信息
	 * @param @param code
	 * @param @return
	 * @return PriceCustomerEntity
	 * @throws
	 * @author dengyin
	 * @date 2016年1月13日
	 */
	public PriceCustomerEntity queryPriceCustomerOrgInfo(String code) {
		PriceCustomerEntity priceCustomerEntity = null;

		if (code == null) {
			return null;
		}

		List<CommonOrgEntity> commonOrgEntityList = commonOrgService
				.queryCommonOrgListFetchByCode(code);
		if (commonOrgEntityList != null && commonOrgEntityList.size() > 0) {
			priceCustomerEntity = new PriceCustomerEntity();
			for (CommonOrgEntity curEntity : commonOrgEntityList) {
				// 是否为事业部
				if ("Y".equals(curEntity.getIsDivision())) {
					priceCustomerEntity.setDivisionCode(curEntity.getCode());
					priceCustomerEntity.setDivisionName(curEntity.getName());
				}

				// 是否为大区
				if ("Y".equals(curEntity.getIsBigRegion())) {
					priceCustomerEntity.setBigRegionCode(curEntity.getCode());
					priceCustomerEntity.setBigRegionName(curEntity.getName());
				}

				// 是否为路区
				if ("Y".equals(curEntity.getIsRoadArea())) {
					priceCustomerEntity.setRoadAreaCode(curEntity.getCode());
					priceCustomerEntity.setRoadAreaName(curEntity.getName());
				}

				// 是否为门店
				if ("Y".equals(curEntity.getIsSalesDepartment())) {
					priceCustomerEntity.setSaleDepartCode(curEntity.getCode());
					priceCustomerEntity.setSaleDepartName(curEntity.getName());
				}

			}
		}
		return priceCustomerEntity;
	}

	public List<ExcelPriceCustomerEntity> queryExcelListByParam(
			PriceCustomerVo priceCustomerVo) {
		return priceCustomerDao.excelQueryListByParam(priceCustomerVo
				.getPriceCustomerEntity());
	}
	
	public List<ExcelPriceCustomerEntity> excelQueryListByParamForStandard(
			PriceCustomerVo priceCustomerVo){
		return priceCustomerDao.excelQueryListByParamForStandard(priceCustomerVo
				.getPriceCustomerEntity());
	}

	public String createExcelFile(List<ExcelPriceCustomerEntity> list,String exportType)
			throws IOException {
		String filePath = "";
		List<String> titleNames = new ArrayList<String>();
		if("view".equals(exportType)){
			titleNames.add("所属事业部");
			titleNames.add("所属大区");
			titleNames.add("所属路区");
			titleNames.add("所属门店");
			titleNames.add("物流代码");
			titleNames.add("客户编号");
			titleNames.add("客户名称");
		}

		titleNames.add("出发价格城市");
		titleNames.add("到达价格城市");
		titleNames.add("运输类型");
		titleNames.add("重量单价");
		titleNames.add("体积单价");
		titleNames.add("附加费");
		titleNames.add("最低收费");
		titleNames.add("燃油费分段");
		
		if("view".equals(exportType)){		
			titleNames.add("生效时间");
			titleNames.add("失效时间");
			titleNames.add("备注");
			titleNames.add("状态");
		}
		
		titleNames.add("运费分段");
		titleNames.add("重量折扣");
		titleNames.add("体积折扣");
		
		if("view".equals(exportType)){
			titleNames.add("创建人");
			titleNames.add("创建时间");
			titleNames.add("修改人");
			titleNames.add("修改时间");
		}
		
		List<String> paramNames = new ArrayList<String>();
		
		if("view".equals(exportType)){
			paramNames.add("divisionName");
			paramNames.add("bigRegionName");
			paramNames.add("roadAreaName");
			paramNames.add("orgName");
			paramNames.add("logistCode");
			paramNames.add("customerCode");
			paramNames.add("customerName");
		}
		paramNames.add("sendCityName");
		paramNames.add("arriveCityName");
		paramNames.add("transTypeName");
		paramNames.add("weightPrice");
		paramNames.add("volumePrice");
		paramNames.add("addFee");
		paramNames.add("lowestFee");
		paramNames.add("fuelFeeSectionName");
		
		if("view".equals(exportType)){
			paramNames.add("effectiveTime");
			paramNames.add("invalidTime");
			paramNames.add("remark");
			paramNames.add("state");
		}
		
		paramNames.add("freightFeeSectionName");
		paramNames.add("weightDiscount");
		paramNames.add("volumeDiscount");
		
		if("view".equals(exportType)){
			paramNames.add("createUser");
			paramNames.add("createDate");
			paramNames.add("modifyUser");
			paramNames.add("modifyDate");
		}
		filePath = new ExcelUtil<ExcelPriceCustomerEntity>().exportExcel(titleNames, paramNames, list);
		return filePath;
	}
	
	@Override
	public int checkCustomerPriceDiscountExistRepeatTimezone(
			PriceCustomerEntity checkEntity) {
		return priceCustomerDao.checkCustomerPriceDiscountExistRepeatTimezone(checkEntity);
	}
	
	@Override
	public PriceCustomerEntity selectPriceCustomerListForLastByCode(
			String customerCode) {
		return priceCustomerDao.selectPriceCustomerListForLastByCode(customerCode);
	}
	
	@Override
	public void insertSelective(PriceCustomerEntity newPriceCustomerEntity) {
		priceCustomerDao.insert(newPriceCustomerEntity);
	}

	@Override
	public List<PriceCustomerEntity> excelPriceCustomer(
			PriceCustomerEntity priceCustomerEntity) {
		return priceCustomerDao.queryPriceCustomerMixed(priceCustomerEntity);
		
	}

	@Override
	public String createExcelFileForCustomer(List<PriceCustomerEntity> list) throws IOException {
		List<String> titleNames = new ArrayList<String>();
		titleNames.add("所属事业部");
		titleNames.add("所属大区");
		titleNames.add("所属路区");
		titleNames.add("所属门店");
		titleNames.add("物流代码");
		titleNames.add("客户编号");
		titleNames.add("客户名称");
		
		titleNames.add("生效时间");
		titleNames.add("失效时间");
		titleNames.add("备注");
		titleNames.add("状态");
		
		titleNames.add("创建人");
		titleNames.add("创建时间");
		titleNames.add("修改人");
		titleNames.add("修改时间");
		
		List<String> paramNames = new ArrayList<String>();
		
		paramNames.add("divisionName");
		paramNames.add("bigRegionName");
		paramNames.add("roadAreaName");
		paramNames.add("saleDepartName");
		paramNames.add("logistCode");
		paramNames.add("customerCode");
		paramNames.add("customerName");
		
		paramNames.add("effectiveTime");
		paramNames.add("invalidTime");
		paramNames.add("remark");
		paramNames.add("statusName");
		
		paramNames.add("createUserName");
		paramNames.add("createTime");
		paramNames.add("modifyUserName");
		paramNames.add("modifyTime");
		
		return new ExcelUtil<PriceCustomerEntity>().exportExcel(titleNames, paramNames, list);
	}
	
	/**
	 * 针对于 生效中 的主表数据进行修改操作
	 * @param priceCustomerEntity
	 */
	public void updateForEffectiveById(PriceCustomerEntity priceCustomerEntity){
		priceCustomerDao.updateForEffectiveById(priceCustomerEntity);
	}
 
}

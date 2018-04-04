package com.hoau.miser.module.biz.pricecard.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerSectionService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerSectionSubService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceCustomerSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerSectionVo;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceCustomerSectionDao;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class PriceCustomerSectionService implements IPriceCustomerSectionService {
	
	@Resource
	private PriceCustomerSectionDao priceCustomerSectiondao;
	
	@Resource
	private IPriceCustomerSectionSubService priceCustomerSectionSubService;

	@Override
	public List<PriceCustomerSectionEntity> queryPriceCustomerMixed(PriceCustomerSectionEntity priceCustomerSectionEntity,
			int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		List<PriceCustomerSectionEntity> priceCustomerSectionEntityList = priceCustomerSectiondao
				.queryPriceCustomerSectionMixed(priceCustomerSectionEntity, rowBounds);
		return priceCustomerSectionEntityList;
	}

	@Override
	public Long countOfPriceCustomerMixed(PriceCustomerSectionEntity priceCustomerEntity){
		return priceCustomerSectiondao.countOfPriceCustomerMixed(priceCustomerEntity);
	}

	@Override
	public String addCustomerAndSubItemList(PriceCustomerSectionVo priceCustomerSectionVo) {

		//默认从前台传递的 id = '' 导致后台判断是否存在重复时间段时错误
		priceCustomerSectionVo.getPriceCustomerSectionEntity().setId(null);
		
		//从 修改操作 调用此方法时 因为前面已经调用过 如下的 check 操作这里通过判断 以减少调用
		String checkResult = "success";
		if(!"PASSED".equals(priceCustomerSectionVo.getPriceCustomerSectionEntity().getStatus())){
			checkResult = beforeAddCustomerAndSubItemCheck(priceCustomerSectionVo);
		}

		if (checkResult.equals("success")) {
			PriceCustomerSectionEntity priceCustomerEntity = beforOperDeal(priceCustomerSectionVo
					.getPriceCustomerSectionEntity());

			List<PriceCustomerSectionSubEntity> priceCustomerSectionSubEntity = priceCustomerSectionVo
					.getPriceCustomerSectionSubEntityList();

			// 为 客户专属价格明细 设置主表的键值
			if (priceCustomerSectionSubEntity != null
					&& priceCustomerSectionSubEntity.size() > 0) {
				for (PriceCustomerSectionSubEntity curSubEntity : priceCustomerSectionSubEntity) {
					curSubEntity.setParentId(priceCustomerEntity.getId());
				}
			}

			priceCustomerSectiondao.insertSelective(priceCustomerEntity);
			priceCustomerSectionSubService.batchInsertCustomerSub(priceCustomerSectionVo
					.getPriceCustomerSectionSubEntityList());

		}

		return checkResult;
	}

	@Override
	public String updateCustomerAndSubItemList(PriceCustomerSectionVo priceCustomerSectionVo) {
		
		//先进行相关检查
		String checkResult = beforeAddCustomerAndSubItemCheck(priceCustomerSectionVo);
		
		if("success".equals(checkResult))
		{
			
			PriceCustomerSectionEntity tmpObj = new PriceCustomerSectionEntity();
			tmpObj.setId(priceCustomerSectionVo.getPriceCustomerSectionEntity().getId());
			
			List<PriceCustomerSectionEntity>  tmpObjList = queryPriceCustomerMixed(tmpObj,1,0);
			if(tmpObjList != null && tmpObjList.size() > 0){
				tmpObj = tmpObjList.get(0);
				
				//针对于 待生效 的记录进行修改操作
				if("WAIT".equals(tmpObj.getStatus())){
					updateCustomerAndSubItemListForWait(priceCustomerSectionVo);
				}
				
				//针对于 生效中 的记录进行修改操作
				if("EFFECTIVE".equals(tmpObj.getStatus())){
					updateCustomerAndSubItemListForEffective(priceCustomerSectionVo);
				}
				
				//针对于 已过期 的记录进行修改操作 对原记录不进行处理 实际作为新增操作
				//变相的 复制 新增
				if("PASSED".equals(tmpObj.getStatus())){
					
					// 针对于 已过期的数据 需要添加 失效时间是否大于当前时间
					Date curDate = new Date();
					
					//失效时间 小于 当前时间 则返回提示 
					if(priceCustomerSectionVo.getPriceCustomerSectionEntity().getInvalidTime().before(curDate)){
						return "priceCustomer.passedInvalidTimeMustThanCurTime";
					}
					else {
						priceCustomerSectionVo.getPriceCustomerSectionEntity().setStatus("PASSED");
						addCustomerAndSubItemList(priceCustomerSectionVo);
					}
					// end 针对于 已过期的数据 需要添加 失效时间是否大于当前时间
//					priceCustomerSectionVo.getPriceCustomerSectionEntity().setStatus("PASSED");
//					addCustomerAndSubItemList(priceCustomerSectionVo);
				}
			}
		}
		
		return checkResult;

	}
	
	
	/**
	 * @Description: 提交客户专属价格及其明细用于保存前的一些后台业务规则校验
	 * @param @param PriceCustomerSectionVo
	 * @param @return
	 * @return String
	 * @throws
	 * @author 何羽
	 * @date 2016年5月3日
	 */
	public String beforeAddCustomerAndSubItemCheck(
			PriceCustomerSectionVo priceCustomerSectionVo) {

		String result = "success";
		Date effectiveTime = priceCustomerSectionVo.getPriceCustomerSectionEntity().getEffectiveTime();

		//判断 填写的 生效时间 小于 当前时间
		Date curDate = new Date();
		if(effectiveTime.before(curDate))
		{
			priceCustomerSectionVo.getPriceCustomerSectionEntity().setEffectiveTime(curDate);
		}
		
		/**
		 * 每个客户在生效中的专属价格 只能有一个。 所以提交时需验证该客户在同一部门是否存在有效时间重合，且当前状态为生效中的数据
		 */
		int countOfRows = priceCustomerSectiondao
				.checkCustomerPriceDiscountExistRepeatTimezone(priceCustomerSectionVo
						.getPriceCustomerSectionEntity());
		if (countOfRows > 0) {
			return "priceCustomer.priceDiscountExistRepeatTimezone";
		}
		

		
		//add by dengyin 2016-4-9 11:18:16 对于前台提交的明细记录 必填项字段进行校验
		List<PriceCustomerSectionSubEntity> priceCustomerSubEntityList = priceCustomerSectionVo.getPriceCustomerSectionSubEntityList();
		if(priceCustomerSubEntityList != null && priceCustomerSubEntityList.size() > 0){
			for(PriceCustomerSectionSubEntity curSubEntity : priceCustomerSubEntityList){
				
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
				
				if(curSubEntity.getFirstWeight() == null ){
					result =  "priceCustomer.subDetailWeightPriceCannotNull";
					break;		
				}
				
				if(curSubEntity.getFirstWeightPrice() == null){
					result =  "priceCustomer.subDetailVolumePriceCannotNull";
					break;		
				}
				if(curSubEntity.getFirstAddWeightPrice() == null){
					result =  "priceCustomer.subDetailVolumePriceCannotNull";
					break;		
				}
			}
		}
		//end by dengyin 2016-4-9 11:18:16 对于前台提交的明细记录 必填项字段进行校验
		
		return result;
	}
	
	public PriceCustomerSectionEntity beforOperDeal(
			PriceCustomerSectionEntity priceCustomerSectionEntity) {

		String id = null;
		Date dt = new Date();
		id = UUIDUtil.getUUID();
		priceCustomerSectionEntity.setId(id);
		priceCustomerSectionEntity.setActive(MiserConstants.YES);
		priceCustomerSectionEntity.setCreateTime(dt);
		priceCustomerSectionEntity.setCreateUserCode((MiserUserContext
				.getCurrentUser().getUserName()));

		priceCustomerSectionEntity.setModifyTime(dt);
		priceCustomerSectionEntity.setModifyUserCode(MiserUserContext.getCurrentUser()
				.getUserName());
		
		priceCustomerSectionEntity.setDataOrign(DataOrignEnum.PMS.getCode());

		return priceCustomerSectionEntity;
	}
	
	/**
	 * 针对于 生效中 的记录进行 更新操作
	 * @param priceCustomerVo
	 */
	public void updateCustomerAndSubItemListForEffective(PriceCustomerSectionVo priceCustomerVo){
		
		/***************************对老数据的处理*****************************/
		//对主表的记录进行操作
		Date curTime = new Date();
		String curUserCode = MiserUserContext.getCurrentUser().getUserName();
		PriceCustomerSectionEntity priceCustomerEntityTmp = new PriceCustomerSectionEntity();
		
		String id = priceCustomerVo.getPriceCustomerSectionEntity().getId();
		priceCustomerEntityTmp.setId(id);
		priceCustomerEntityTmp.setInvalidTime(curTime);
		priceCustomerEntityTmp.setModifyTime(curTime);
		priceCustomerEntityTmp.setModifyUserCode(curUserCode);
		priceCustomerSectiondao.updateForEffectiveById(priceCustomerEntityTmp);
		 
		//对副表记录进行操作
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("modifyUserCode", curUserCode);
		paramMap.put("modifyTime", curTime);
		paramMap.put("parentId", id);
		priceCustomerSectionSubService.batchUpdateForEffectiveByParentId(paramMap);
		
		
		/***************************对新数据的处理*****************************/
		//对主表的记录进行操作
		PriceCustomerSectionEntity realPriceCustomerEntity = priceCustomerVo.getPriceCustomerSectionEntity();
		realPriceCustomerEntity.setEffectiveTime(curTime);
		realPriceCustomerEntity = beforOperDeal(realPriceCustomerEntity);
		priceCustomerSectiondao.insertSelective(realPriceCustomerEntity);
		
		//对副表的记录进行操作
		
		List<PriceCustomerSectionSubEntity> priceCustomerSubEntityList = priceCustomerVo.getPriceCustomerSectionSubEntityList();
		
		// 为 客户专属价格明细 设置主表的键值
		if (priceCustomerSubEntityList != null
				&& priceCustomerSubEntityList.size() > 0) {
			for (PriceCustomerSectionSubEntity curSubEntity : priceCustomerSubEntityList) {
				curSubEntity.setParentId(realPriceCustomerEntity.getId());
			}
		}
		
		priceCustomerSectionSubService.batchInsertCustomerSub(priceCustomerSubEntityList);
	}
	
	/**
	 * 针对于 待生效 的记录的更新逻辑
	 * @param priceCustomerVo
	 */
	public void updateCustomerAndSubItemListForWait(PriceCustomerSectionVo priceCustomerVo){
		String modifyUserCode = MiserUserContext.getCurrentUser().getUserName();
		
		/**
		 * 下面的数据 尽管是从当前表单获取的最新对象 但更新原有数据时 只是根据 id 更新 active = N (sql 中直接设值)
		 * modifyUserCode=当前用户 modifyTime=sysdate (sql 中直接设值) 
		 */
		PriceCustomerSectionEntity oldPriceCustomerEntity = priceCustomerVo.getPriceCustomerSectionEntity();
		oldPriceCustomerEntity.setModifyUserCode(modifyUserCode);


		// 更新老版本的 明细记录 根据客户专属价格老数据的 主键 作为其 parentId 来统一更新
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("modifyUserCode", modifyUserCode);
		paramMap.put("parentId", oldPriceCustomerEntity.getId());

		priceCustomerSectiondao.updateForInvalidById(oldPriceCustomerEntity);
		priceCustomerSectionSubService.batchUpdateForInvalidByParentId(paramMap);

		// 再将新的数据插入数据库
		PriceCustomerSectionEntity priceCustomerEntity = beforOperDeal(priceCustomerVo
				.getPriceCustomerSectionEntity());
		List<PriceCustomerSectionSubEntity> priceCustomerSubEntityList = priceCustomerVo
				.getPriceCustomerSectionSubEntityList();

		// 为 客户专属价格明细 设置主表的键值
		if (priceCustomerSubEntityList != null
				&& priceCustomerSubEntityList.size() > 0) {
			for (PriceCustomerSectionSubEntity curSubEntity : priceCustomerSubEntityList) {
				curSubEntity.setParentId(priceCustomerEntity.getId());
			}
		}

		priceCustomerSectiondao.insertSelective(priceCustomerEntity);
		priceCustomerSectionSubService
				.batchInsertCustomerSub(priceCustomerSubEntityList);
	}

	@Override
	public String destoryPriceCustomerAndSubList(String destoryIdStr) {

		Map<String, Object> paramMap = new HashMap<String, Object>();

		String destroyIdStrA = " AND T.ID in (" + destoryIdStr + ")";
		paramMap.put("destoryIdStr", destroyIdStrA);
		
		//A 根据 id 串判断后台实际的状态仍是否为可用于作废 即 待生效 已生效 若不是 则需要返回 状态已发生变化 不能执行该操作
		List<PriceCustomerSectionEntity> priceCustomerEntityList = priceCustomerSectiondao.selectPriceCustomerEntityIncludeStatusIdStr(paramMap);
		
		//是否存在记录
		boolean isExistedRecord = false;
		
		//判断是否存在不能用于作废的记录
		boolean isExistedInvalidStatus = false;
		
		if(priceCustomerEntityList != null && priceCustomerEntityList.size() > 0)
		{
			isExistedRecord = true;
			
			//判断是否都为 待生效 或 已生效
			for(PriceCustomerSectionEntity curEntity : priceCustomerEntityList)
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
		priceCustomerSectiondao.destoryPriceCustomerForEffectiveByIdStr(paramMap);
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
		priceCustomerSectiondao.destoryPriceCustomerForWaitByIdStr(paramMap);

		String destroyIdStrB = "  AND T.PARENT_ID in (" + destoryIdStr + ")";
		paramMap.put("destoryIdStr", destroyIdStrB);

		// 再更新副表
		priceCustomerSectionSubService.destoryPriceCustomerSubByParentIdStr(paramMap);
	}

	@Override
	public List<PriceCustomerSectionEntity> excelPriceCustomer(PriceCustomerSectionEntity priceCustomerEntity) {
		return priceCustomerSectiondao.queryPriceCustomerSectionMixed(priceCustomerEntity);
	}

	@Override
	public String createExcelFileForCustomer(List<PriceCustomerSectionEntity> list) throws IOException {
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
		
		return new ExcelUtil<PriceCustomerSectionEntity>().exportExcel(titleNames, paramNames, list);
	}

	@Override
	public List<ExcelPriceCustomerSectionEntity> queryExcelListByParam(PriceCustomerSectionVo priceCustomerVo) {
		return priceCustomerSectiondao.excelQueryListByParam(priceCustomerVo
				.getPriceCustomerSectionEntity());
	}

	@Override
	public List<ExcelPriceCustomerSectionEntity> excelQueryListByParamForStandard(PriceCustomerSectionVo priceCustomerVo) {
		return priceCustomerSectiondao.excelQueryListByParamForStandard(priceCustomerVo
				.getPriceCustomerSectionEntity());
	}

	@Override
	public String createExcelFile(List<ExcelPriceCustomerSectionEntity> list, String exportType) throws IOException {
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
		titleNames.add("一段首重");
		titleNames.add("一段首重单价");
		titleNames.add("一段续重单价");
		titleNames.add("二段首重");
		titleNames.add("二段首重单价");
		titleNames.add("二段续重单价");
		titleNames.add("三段首重");
		titleNames.add("三段首重单价");
		titleNames.add("三段续重单价");
		
		if("view".equals(exportType)){		
			titleNames.add("生效时间");
			titleNames.add("失效时间");
			titleNames.add("备注");
			titleNames.add("状态");
		}
		
		titleNames.add("运费优惠分段");
		
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
		paramNames.add("firstWeight");
		paramNames.add("firstWeightPrice");
		paramNames.add("firstAddWeightPrice");
		paramNames.add("secondWeight");
		paramNames.add("secondWeightPrice");
		paramNames.add("secondAddWeightPrice");
		paramNames.add("thirdWeight");
		paramNames.add("thirdWeightPrice");
		paramNames.add("thirdAddWeightPrice");
		
		if("view".equals(exportType)){
			paramNames.add("effectiveTime");
			paramNames.add("invalidTime");
			paramNames.add("remark");
			paramNames.add("state");
		}
		
		paramNames.add("freightSectionCodeName");
		
		if("view".equals(exportType)){
			paramNames.add("createUser");
			paramNames.add("createDate");
			paramNames.add("modifyUser");
			paramNames.add("modifyDate");
		}
		filePath = new ExcelUtil<ExcelPriceCustomerSectionEntity>().exportExcel(titleNames, paramNames, list);
		return filePath;
	}

}

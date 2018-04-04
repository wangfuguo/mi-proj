package com.hoau.miser.module.biz.extrafee.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.miser.module.biz.extrafee.server.dao.PricePickupFeeDao;
import com.hoau.miser.module.biz.extrafee.server.service.IPricePickupFeeService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PricePickupFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.exception.PricePickUpFeeException;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceStandardEntity;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class PricePickupFeeService  implements IPricePickupFeeService {

	@Resource
	private PricePickupFeeDao deliveryChargesDao;
	
	@Override
	public Integer updatePricePickupFeeByEntity(PricePickupFeeEntity entity,boolean isConver) {
		
	return	this.addPricePickupFeeByEntity(entity,isConver);
	}

	@Override
	public void deletePricePickupFeeByEntity(PricePickupFeeEntity entity) {
		//无法修改失效或已过期数据
		if(PricePickupFeeEntity.STATE_1.equals(entity.getState())||PricePickupFeeEntity.STATE_4.equals(entity.getState())){
			throw new PricePickUpFeeException("不能删除失效或作废数据");
		}
		entity.setActive(MiserConstants.INACTIVE);
		entity.setModifyDate(new Date());
		entity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		deliveryChargesDao.deletePricePickupFeeByEntity(entity);
	}

	@Override
	public Integer addPricePickupFeeByEntity(PricePickupFeeEntity entity,boolean isConver) {
		//默认成功
		Integer result=1;
		//根据出发价格城市和运输类型判断是否已存在数据
		String operUserCode =MiserUserContext.getCurrentUser().getEmpNameAndUserName();
		entity.setId(UUIDUtil.getUUID());
		entity.setActive(MiserConstants.ACTIVE);
		entity.setCreateUser(operUserCode);
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date());
		entity.setModifyUser(operUserCode);
		
		//判断生效日期是否大于当前日期
		if(entity.getEffectiveTime().getTime()<new Date().getTime()){
			return 3;
		}
		if(entity.getEffectiveTime().getTime()>entity.getInvalidTime().getTime()){
			return 3;
		}
		//判断是否 是已失效数据
//		entity.setState(PricePickupFeeEntity.STATE_1);
		PricePickupFeeEntity entity1=deliveryChargesDao.queryPricePickupFeeByStats(entity);
		if(entity1!=null && PricePickupFeeEntity.STATE_1.equals(entity1.getState())){
			result=2;
			return result;
		}
		
		//查询待生效数据
		entity.setState(PricePickupFeeEntity.STATE_3);
		PricePickupFeeEntity entity3=deliveryChargesDao.queryPricePickupFeeByStats(entity);
		if(entity3!=null){//有待生效的
			//1.作废条待生效的数据
			if(isConver){//确认覆盖则覆盖
				deliveryChargesDao.updatePricePickupFeeByState3(entity);
			}else{
				result=0;
				return result;
			}
		}
		//查询生效中的数据
				entity.setState(PricePickupFeeEntity.STATE_2);//生效中
				PricePickupFeeEntity entity2=deliveryChargesDao.queryPricePickupFeeByStats(entity);
				if(entity2!=null){
					//有生效中的数据，就把该生效中的数据的失效时间 设置为待生效数据的生效时间
					deliveryChargesDao.updatePricePickupFeeByState2(entity);
				}
		//插入一条新的待生效的数据
		deliveryChargesDao.addPricePickupFeeByEntity(entity);
		
		return result;
	}

	public void updateOrAddPricePickupFee(){
		
	}
	@Override
	public PricePickupFeeEntity queryPricePickupFeeByEntity(PricePickupFeeEntity entity) {
		entity.setActive(MiserConstants.ACTIVE);
		if(PricePickupFeeEntity.STATE_2.equals(entity.getState())||PricePickupFeeEntity.STATE_3.equals(entity.getState())){
			return deliveryChargesDao.queryPricePickupFeeByEntity(entity);
		}else{
			throw new PricePickUpFeeException("不能修改失效或作废数据");
		}
		
	}

	@Override
	public List<PricePickupFeeEntity> queryPricePickupFeeByParam(
			PricePickupFeeEntity entity, int limit, int start) {
		RowBounds rowBounds=new RowBounds(start, limit);
		return deliveryChargesDao.queryPricePickupFeeByParam(entity,rowBounds);
	}

	@Override
	public Long queryCountByParam(PricePickupFeeEntity entity) {
		return deliveryChargesDao.queryCountByParam(entity);
	}

	@Override
	public String createExcel(List<PricePickupFeeEntity> list) {
		String filePath = null;
		List<String> titleNames = new ArrayList<String>();
		titleNames.add("出发价格城市");
		titleNames.add("运输类型");
		titleNames.add("重货单价");
		titleNames.add("轻货单价");
		titleNames.add("最低收费");
		titleNames.add("状态");
		titleNames.add("生效时间");
		titleNames.add("失效时间");
		titleNames.add("备注");
		titleNames.add("创建人");
		titleNames.add("创建时间");
		titleNames.add("最后修改人");
		titleNames.add("最后修改时间");
		List<String> paramNames = new ArrayList<String>();
		paramNames.add("priceCityName");
		paramNames.add("transportTypeName");
		paramNames.add("weightPrice");
		paramNames.add("volumnPrice");
		paramNames.add("minMoney");
		paramNames.add("state");
		paramNames.add("effectiveTime");
		paramNames.add("invalidTime");
		paramNames.add("remark");
		paramNames.add("createUser");
		paramNames.add("createDate");
		paramNames.add("modifyUser");
		paramNames.add("modifyDate");
		try {
			filePath = new ExcelUtil<PricePickupFeeEntity>().exportExcel(
					titleNames, paramNames, list);
		} catch (Exception e) {
			filePath = null;
		}
		return filePath;
	}

	@Override
	public List<PricePickupFeeEntity> queryPricePickupFeeByParam(
			PricePickupFeeEntity entity) {
		return deliveryChargesDao.queryPricePickupFeeByParamExport(entity);
	}
	
}

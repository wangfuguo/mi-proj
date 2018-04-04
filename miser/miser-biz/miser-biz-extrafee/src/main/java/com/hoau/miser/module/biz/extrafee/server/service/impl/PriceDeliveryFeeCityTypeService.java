package com.hoau.miser.module.biz.extrafee.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.extrafee.server.dao.PriceDeliveryFeeCityTypeDao;
import com.hoau.miser.module.biz.extrafee.server.service.IPriceDeliveryFeeCityTypeService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceDeliveryFeeCityTypeEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceDeliveryFeeCityTypeVo;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.DateUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 送货费
 * ClassName: PriceDeliveryFeeCityTypeService 
 * @author 廖文强
 * @date 2016年1月4日
 * @version V1.0
 */
@Service
public class PriceDeliveryFeeCityTypeService implements
		IPriceDeliveryFeeCityTypeService {

	@Resource
	private PriceDeliveryFeeCityTypeDao priceDeliveryFeeCityTypeDao;
	public List<PriceDeliveryFeeCityTypeEntity> queryListByParam(
			PriceDeliveryFeeCityTypeVo priceDeliveryFeeCityTypeVo, int limit,
			int start) {
		
		RowBounds rowBounds = new RowBounds(start,limit);
		return priceDeliveryFeeCityTypeDao.queryListByParam(priceDeliveryFeeCityTypeVo,rowBounds);
	}

	public Long queryCountByParam(
			PriceDeliveryFeeCityTypeVo priceDeliveryFeeCityTypeVo) {
		return priceDeliveryFeeCityTypeDao.queryCountByParam(priceDeliveryFeeCityTypeVo);
	}

	
	public PriceDeliveryFeeCityTypeEntity queryPriceDeliveryFeeCityTypeById(
			String id) {
		if(StringUtil.isEmpty(id)){
			return null;
		}
		PriceDeliveryFeeCityTypeVo vo=new PriceDeliveryFeeCityTypeVo();
		PriceDeliveryFeeCityTypeEntity entity=new PriceDeliveryFeeCityTypeEntity();
		entity.setId(id);
		vo.setPdfctEntity(entity);
		List<PriceDeliveryFeeCityTypeEntity> list=this.queryListByParam(vo, 1, 0);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	@Transactional
	public Integer addPriceDeliveryFeeCityType(
			PriceDeliveryFeeCityTypeEntity pdfctEntity, boolean IsConfirm) {
		pdfctEntity.setActive(MiserConstants.YES);
		return this.addOrUpdatePriceStandard(pdfctEntity, IsConfirm);
	}

	@Transactional
	public Integer updatePriceDeliveryFeeCityType(
			PriceDeliveryFeeCityTypeEntity pdfctEntity, boolean IsConfirm) {
		
		return this.addOrUpdatePriceStandard(pdfctEntity, IsConfirm);
	}
	
	/**
	 * 
	 * @Description: 删除
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	@Transactional
	public int delPriceDeliveryFeeCityType(PriceDeliveryFeeCityTypeEntity pse) {
		int result=0;
		//pse.setRemark(MiserUserContext.getCurrentUser().getEmpNameAndUserName()+" 于 "+DateUtils.convert(new Date())+" 手动删除");
		// 查询当前信息状态,如果状态不符合修改条件，给用户以提示
		if(StringUtil.isNotEmpty(pse.getId())){
			PriceDeliveryFeeCityTypeEntity selfEntity = this.queryPriceDeliveryFeeCityTypeById(pse.getId());
			if (selfEntity.getActive().equals(MiserConstants.NO)
					|| !(PriceDeliveryFeeCityTypeEntity.STATE_2.equals(selfEntity.getState()) || PriceDeliveryFeeCityTypeEntity.STATE_3
							.equals(selfEntity.getState()))) {
				result = 2;
				return result;
			}
		}
		if(PriceDeliveryFeeCityTypeEntity.STATE_3.equals(pse.getState())){//待生效数据
			beforOperDeal(pse);
			pse.setActive(MiserConstants.NO);
			priceDeliveryFeeCityTypeDao.updatePriceDeliveryFeeCityType(pse);
			//生效信息中的失效时间设为null
			PriceDeliveryFeeCityTypeEntity queryEntity=new PriceDeliveryFeeCityTypeEntity();
			queryEntity.setActive(MiserConstants.YES);
			queryEntity.setCityType(pse.getCityType());
			queryEntity.setState(PriceDeliveryFeeCityTypeEntity.STATE_2);
			PriceDeliveryFeeCityTypeVo queryVo1=new PriceDeliveryFeeCityTypeVo();
			queryVo1.setPdfctEntity(queryEntity);
			//查询生效中的信息
			List<PriceDeliveryFeeCityTypeEntity> state2List= this.queryListByParam(queryVo1, 1, 0);

			// 将生效中的信息的失效时间赋值为待生效的生效时间
			if(state2List!=null&&state2List.size()>0){
				PriceDeliveryFeeCityTypeEntity state2Entity=state2List.get(0);
				beforOperDeal(state2Entity);
				state2Entity.setState("setNullInvalidTime");
				state2Entity.setInvalidTime(null);
				priceDeliveryFeeCityTypeDao.updatePriceDeliveryFeeCityType(state2Entity);
			}
		}else if(PriceDeliveryFeeCityTypeEntity.STATE_2.equals(pse.getState())){//生效中
			beforOperDeal(pse);
			pse.setInvalidTime(new Date());
			priceDeliveryFeeCityTypeDao.updatePriceDeliveryFeeCityType(pse);
			
		}
		result=1;
		return result;
		
	}

	/**
	 * A、新增单条价格时同此规则，如果存在待生效数据提示“该送货费存在相同的待生效数据，是否覆盖？”确认，则直接覆盖。
	 * 如果无则直接新增。
	 * B、修改，如果修改待生效数据，则更新原待生效数据。
	 * 如果修改生效中数据，则产生一条待生效数据，此时如果存在另一条待生效数据提示“该送货费存在相同的待生效数据，是否覆盖？” 确认，则直接覆盖。
	 * 如果无则直接新增待生效数据，已失效数据不能修改。
	 * @Description: 增加和修改数据的逻辑处理
	 * @param @param pse
	 * @param @param isConfirmAdd
	 * @param @return   
	 * @return Integer  0：需确认，1：成功，2：状态发生变化
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	private Integer addOrUpdatePriceStandard(PriceDeliveryFeeCityTypeEntity pse,Boolean isConfirmAdd) {
		//默认成功
		Integer result=1;
		//查询当前信息状态,如果状态不符合修改条件，给用户以提示
		PriceDeliveryFeeCityTypeEntity selfEntity = this.queryPriceDeliveryFeeCityTypeById(pse.getId());
		if(selfEntity!=null){
			if (selfEntity.getActive().equals(MiserConstants.NO)
					|| !(PriceDeliveryFeeCityTypeEntity.STATE_2.equals(selfEntity.getState()) || PriceDeliveryFeeCityTypeEntity.STATE_3
							.equals(selfEntity.getState()))) {
				result = 2;
				return result;
			}
		}
		//查询历史有效信息
		PriceDeliveryFeeCityTypeEntity queryEntity=new PriceDeliveryFeeCityTypeEntity();
		queryEntity.setActive(MiserConstants.YES);
		queryEntity.setCityType(pse.getCityType());
		queryEntity.setTransTypeCode(pse.getTransTypeCode());
		queryEntity.setState(PriceDeliveryFeeCityTypeEntity.STATE_2);
		PriceDeliveryFeeCityTypeVo queryVo1=new PriceDeliveryFeeCityTypeVo();
		queryVo1.setPdfctEntity(queryEntity);
		//查询生效中的信息
		List<PriceDeliveryFeeCityTypeEntity> state2List= this.queryListByParam(queryVo1, 1, 0);
		//查询待生效的信息
		queryEntity.setState(PriceDeliveryFeeCityTypeEntity.STATE_3);
		List<PriceDeliveryFeeCityTypeEntity> state3List= this.queryListByParam(queryVo1, 1, 0);
		if(state3List!=null&&state3List.size()>0){//存在待生效信息
			
			if(isConfirmAdd){//确认新增
				//将旧的待生效信息作废
				PriceDeliveryFeeCityTypeEntity state3Entity=state3List.get(0);
				state3Entity.setActive(MiserConstants.NO);
				beforOperDeal(state3Entity);
				this.priceDeliveryFeeCityTypeDao.updatePriceDeliveryFeeCityType(state3Entity);
				
			}else{//表示需要确认是否新增
				
				result=0;
				return result;
			}
		}
		
		//新增待生效信息
		pse.setId(null);
		beforOperDeal(pse);
		priceDeliveryFeeCityTypeDao.addPriceDeliveryFeeCityType(pse);
		
		// 将生效中的信息的失效时间赋值为待生效的生效时间
		if(state2List!=null&&state2List.size()>0){
			PriceDeliveryFeeCityTypeEntity state2Entity=state2List.get(0);
			beforOperDeal(state2Entity);
			state2Entity.setInvalidTime(pse.getEffectiveTime());
			priceDeliveryFeeCityTypeDao.updatePriceDeliveryFeeCityType(state2Entity);
		}
		result=1;
		return result;
		
	}
	
	/**
	 * 变更送货费之前的处理工作：
	 * @Description: TODO描述该方法是做什么的
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	public void beforOperDeal(PriceDeliveryFeeCityTypeEntity pse){
		Date dt = new Date();
		if(StringUtil.isBlank(pse.getId())){//新增的话
			pse.setId(UUIDUtil.getUUID());
			pse.setActive(MiserConstants.YES);
			pse.setCreateDate(dt);
			pse.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		}
		pse.setModifyDate(dt);
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	}
	
	@Transactional
	public int bathDelPriceDeliveryFeeCityType(
			List<PriceDeliveryFeeCityTypeEntity> list) {
		int result=0;
		for(PriceDeliveryFeeCityTypeEntity entity:list){
			result=this.delPriceDeliveryFeeCityType(entity);
			if(result==2){//状态发生变化
				return result;
			}
		}
		return result;
	}

}

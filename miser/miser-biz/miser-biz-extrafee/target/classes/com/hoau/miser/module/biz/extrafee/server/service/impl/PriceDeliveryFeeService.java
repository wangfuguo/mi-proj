/**
 * 
 */
package com.hoau.miser.module.biz.extrafee.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.extrafee.server.dao.PriceDeliveryFeeDao;
import com.hoau.miser.module.biz.extrafee.server.service.IPriceDeliveryFeeService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceDeliveryFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceDeliveryFeeVo;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 配送费管理
 * @author dengyin
 *
 */
@Service
public class PriceDeliveryFeeService implements IPriceDeliveryFeeService {
	
	@Resource
	private PriceDeliveryFeeDao priceDeliveryFeeDao;

	@Override
	public List<PriceDeliveryFeeEntity> queryListByParam(
			PriceDeliveryFeeVo priceDeliveryVo, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return priceDeliveryFeeDao.queryListByParam(priceDeliveryVo,rowBounds);
	}

	@Override
	public Long queryCountByParam(PriceDeliveryFeeVo priceDeliveryVo) {
		return priceDeliveryFeeDao.queryCountByParam(priceDeliveryVo);
	}

	@Override
	public PriceDeliveryFeeEntity queryPriceDeliveryFeeById(String id) {
		
		PriceDeliveryFeeVo priceDeliveryFeeVo = new PriceDeliveryFeeVo();
		PriceDeliveryFeeEntity entity = new PriceDeliveryFeeEntity();
		entity.setId(id);
		priceDeliveryFeeVo.setPriceDeliveryFeeEntity(entity);
		RowBounds rowBounds = new RowBounds(0, 1);
		List<PriceDeliveryFeeEntity> list = priceDeliveryFeeDao.queryListByParam(priceDeliveryFeeVo , rowBounds);
		
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Transactional
	public Integer addPriceDeliveryFee(PriceDeliveryFeeEntity entity,boolean isConfirm) {
		entity.setActive(MiserConstants.YES);
		return this.addOrUpdatePriceDelivery(entity,isConfirm);
	}

	@Transactional
	public Integer updatePriceDeliveryFee(PriceDeliveryFeeEntity entity,boolean isConfirm) {
		return this.addOrUpdatePriceDelivery(entity,isConfirm);
	}
	
	@Transactional
	public int delPriceDeliveryFee(PriceDeliveryFeeEntity pse) {
		int result=0;
		
		// 查询当前信息状态,如果状态不符合修改条件，给用户以提示
		if(StringUtil.isNotEmpty(pse.getId())){
			PriceDeliveryFeeEntity selfEntity = this.queryPriceDeliveryFeeById(pse.getId());
			if (selfEntity.getActive().equals(MiserConstants.NO)
					|| !("EFFECTIVE".equals(selfEntity.getState()) || "WAIT"
							.equals(selfEntity.getState()))) {
				result = 2;
				return result;
			}
		}
		if("WAIT".equals(pse.getState())){//待生效数据
			beforOperDeal(pse);
			pse.setActive(MiserConstants.NO);
			priceDeliveryFeeDao.updatePriceDeliveryFee(pse);
			
			//生效信息中的失效时间设为null
			PriceDeliveryFeeEntity queryEntity=new PriceDeliveryFeeEntity();
			queryEntity.setActive(MiserConstants.YES);
			queryEntity.setCityType(pse.getCityType());
			queryEntity.setState("EFFECTIVE");
			PriceDeliveryFeeVo queryVo1=new PriceDeliveryFeeVo();
			queryVo1.setPriceDeliveryFeeEntity(queryEntity);
			
			//查询生效中的信息
			List<PriceDeliveryFeeEntity> state2List= this.queryListByParam(queryVo1, 1, 0);

			// 将生效中的信息的失效时间赋值为待生效的生效时间
			if(state2List!=null&&state2List.size()>0){
				PriceDeliveryFeeEntity state2Entity=state2List.get(0);
				beforOperDeal(state2Entity);
				state2Entity.setState("setNullInvalidTime");
				state2Entity.setInvalidTime(null);
				priceDeliveryFeeDao.updatePriceDeliveryFee(state2Entity);
			}
		}else if("EFFECTIVE".equals(pse.getState())){//生效中
			beforOperDeal(pse);
			pse.setInvalidTime(new Date());
			priceDeliveryFeeDao.updatePriceDeliveryFee(pse);
			
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
	 * @author dengyin
	 * @date2016-7-7 16:13:34
	 */
	private Integer addOrUpdatePriceDelivery(PriceDeliveryFeeEntity pse,Boolean isConfirmAdd) {
		//默认成功
		Integer result=1;
		
		PriceDeliveryFeeEntity selfEntity = null;
		
		//新增过来时 id 为 ""
		if(!"".equals(pse.getId())){
			selfEntity = this.queryPriceDeliveryFeeById(pse.getId());
		}
		
		//查询当前信息状态,如果状态不符合修改条件，给用户以提示1
		if(selfEntity != null){
			if (selfEntity.getActive().equals(MiserConstants.NO)
					|| !("EFFECTIVE".equals(selfEntity.getState()) || "WAIT"
							.equals(selfEntity.getState()))) {
				result = 2;
				return result;
			}
		}
		
		//查询历史有效信息
		PriceDeliveryFeeEntity queryEntity=new PriceDeliveryFeeEntity();
		queryEntity.setActive(MiserConstants.YES);
		queryEntity.setCityType(pse.getCityType());
		queryEntity.setTransTypeCode(pse.getTransTypeCode());
		//queryEntity.setSectionItemCode(pse.getSectionItemCode());
		queryEntity.setState("EFFECTIVE");
		
		PriceDeliveryFeeVo queryVo1=new PriceDeliveryFeeVo();
		queryVo1.setPriceDeliveryFeeEntity(queryEntity);
		
		//查询生效中的信息
		List<PriceDeliveryFeeEntity> state2List= this.queryListByParam(queryVo1, 1, 0);
		
		
		//查询待生效的信息
		queryEntity.setState("WAIT");
		List<PriceDeliveryFeeEntity> state3List= this.queryListByParam(queryVo1, 1, 0);
		if(state3List!=null&&state3List.size()>0){//存在待生效信息
			if(isConfirmAdd){//确认新增
				
				//将旧的待生效信息作废
				PriceDeliveryFeeEntity state3Entity=state3List.get(0);
				state3Entity.setActive(MiserConstants.NO);
				beforOperDeal(state3Entity);
				
				this.priceDeliveryFeeDao.updatePriceDeliveryFee(state3Entity);
				
			}else{//表示需要确认是否新增
				
				result=0;
				return result;
			}
		}
		
		//新增待生效信息
		pse.setId(null);
		beforOperDeal(pse);
		priceDeliveryFeeDao.addPriceDeliveryFee(pse);
		
		// 将生效中的信息的失效时间赋值为待生效的生效时间
		if(state2List!=null&&state2List.size()>0){
			PriceDeliveryFeeEntity state2Entity=state2List.get(0);
			beforOperDeal(state2Entity);
			state2Entity.setInvalidTime(pse.getEffectiveTime());
			priceDeliveryFeeDao.updatePriceDeliveryFee(state2Entity);
		}
		result=1;
		return result;
		
	}
	
	public void beforeOper(PriceDeliveryFeeEntity entity){
		String curUserName = MiserUserContext.getCurrentUser().getUserName();
		entity.setId(UUIDUtil.getUUID());
		entity.setCreateUserCode(curUserName);
		entity.setModifyUserCode(curUserName);
		entity.setActive(MiserConstants.YES);
	}
	
	@Override
	public int batchDelPriceDeliveryFee(List<PriceDeliveryFeeEntity> entityList) {
		int result=0;
		for(PriceDeliveryFeeEntity entity:entityList){
			result=this.delPriceDeliveryFee(entity);
			if(result==2){//状态发生变化
				return result;
			}
		}
		return result;
	}
	

	
	
	

	
	/**
	 * 变更送货费之前的处理工作：
	 * @Description: TODO描述该方法是做什么的
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author dengyin
	 * @date2016-7-7 16:13:34
	 */
	public void beforOperDeal(PriceDeliveryFeeEntity pse){
		Date dt = new Date();
		if(StringUtil.isBlank(pse.getId())){//新增的话
			pse.setId(UUIDUtil.getUUID());
			pse.setActive(MiserConstants.YES);
			pse.setCreateTime(dt);
			pse.setCreateUserCode(MiserUserContext.getCurrentUser().getUserName());
		}
		pse.setModifyTime(dt);
		pse.setModifyUserCode(MiserUserContext.getCurrentUser().getUserName());
	}
	

 
}

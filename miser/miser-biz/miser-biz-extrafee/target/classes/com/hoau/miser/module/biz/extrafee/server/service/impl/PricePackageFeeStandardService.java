package com.hoau.miser.module.biz.extrafee.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.shared.domain.PricePackageFeeItemsEntity;
import com.hoau.miser.module.biz.extrafee.server.dao.PricePackageFeeStandardDao;
import com.hoau.miser.module.biz.extrafee.server.service.IPricePackageFeeStandardService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PricePackageFeeStandardEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.vo.PricePackageFeeStandardVO;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class PricePackageFeeStandardService implements
		IPricePackageFeeStandardService {

	@Resource
	private PricePackageFeeStandardDao pricePackageFeeStandardDao;
	
	public List<PricePackageFeeStandardEntity> queryListByParam(
			PricePackageFeeStandardVO ppfeeStandardVO, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return pricePackageFeeStandardDao.queryListByParam(ppfeeStandardVO,rowBounds);
	}

	
	public Long queryCountByParam(PricePackageFeeStandardVO ppfeeStandardVO) {
		
		return pricePackageFeeStandardDao.queryCountByParam(ppfeeStandardVO);
	}

	
	public PricePackageFeeStandardEntity queryPpFeeStandardById(String id) {
		PricePackageFeeStandardEntity entity=new PricePackageFeeStandardEntity();
		entity.setId(id);
		PricePackageFeeStandardVO vo=new PricePackageFeeStandardVO();
		vo.setPpFeeStandardEntity(entity);
		List<PricePackageFeeStandardEntity> list=this.queryListByParam(vo, 1, 0);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	@Transactional
	public void addPpFeeStandard(PricePackageFeeStandardEntity ppFeeStandardEntity) {
		if(isExist(ppFeeStandardEntity)){
			throw new BusinessException(MessageType.ERROR_ISEXIST,"code");
		}
		ppFeeStandardEntity.setId(null);
		beforOperDeal(ppFeeStandardEntity);
		pricePackageFeeStandardDao.addPpFeeStandard(ppFeeStandardEntity);

	}

	@Transactional
	public void updatePpFeeStandard(
			PricePackageFeeStandardEntity ppFeeStandardEntity) {
		//先删除旧的信息
		PricePackageFeeStandardEntity dbEntity=this.queryPpFeeStandardById(ppFeeStandardEntity.getId());
		if(dbEntity==null)return;
		this.delFeeStandard(dbEntity);
		PricePackageFeeStandardEntity dbEntity2=this.queryPpFeeStandardById(ppFeeStandardEntity.getId());
		//ppFeeStandardEntity.setActive(MiserConstants.NO);
		//再增加新的信息
		this.addPpFeeStandard(ppFeeStandardEntity);

	}
	
	@Transactional
	public void delFeeStandard(PricePackageFeeStandardEntity ppFeeStandardEntity) {
		ppFeeStandardEntity.setActive(MiserConstants.NO);
		beforOperDeal(ppFeeStandardEntity);
		pricePackageFeeStandardDao.delFeeStandard(ppFeeStandardEntity);
	}

	/**
	 * 
	 * @Description: 执行操作之前的逻辑处理
	 * @param @param entity   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2015年12月28日
	 */
	private void beforOperDeal(PricePackageFeeStandardEntity entity){
		Date dt = new Date();
		if(StringUtil.isBlank(entity.getId())){
			entity.setId(UUIDUtil.getUUID());
			entity.setActive(MiserConstants.YES);
			entity.setCreateDate(dt);
			entity.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		}
		entity.setModifyDate(dt);
		entity.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	}
	/**
	 * 
	 * @Description: 是否存在
	 * @param @param ppFeeStandardEntity
	 * @param @return   
	 * @return boolean 
	 * @throws
	 * @author 廖文强
	 * @date 2015年12月29日
	 */
	private boolean isExist(PricePackageFeeStandardEntity ppFeeStandardEntity){
		PricePackageFeeStandardEntity temp=new PricePackageFeeStandardEntity();
		temp.setActive(MiserConstants.YES);
		temp.setTransTypeCode(ppFeeStandardEntity.getTransTypeCode());
		PricePackageFeeItemsEntity tempFeeItems=new PricePackageFeeItemsEntity();
		tempFeeItems.setCode(ppFeeStandardEntity.getPpFeeItems().getCode());
		temp.setPpFeeItems(tempFeeItems);
		PricePackageFeeStandardVO vo=new PricePackageFeeStandardVO();
		vo.setPpFeeStandardEntity(temp);
		List<PricePackageFeeStandardEntity> list=this.queryListByParam(vo, 1, 0);
		return list!=null&&list.size()>0?true:false;
	}
}

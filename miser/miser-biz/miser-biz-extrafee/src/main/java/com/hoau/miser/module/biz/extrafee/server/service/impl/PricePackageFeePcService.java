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
import com.hoau.miser.module.biz.extrafee.server.dao.PricePackageFeePcDao;
import com.hoau.miser.module.biz.extrafee.server.service.IPricePackageFeePcService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PricePackageFeePcEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.vo.PricePackageFeePcVo;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class PricePackageFeePcService implements
		IPricePackageFeePcService {

	@Resource
	private PricePackageFeePcDao pricePackageFeePcDao;
	
	public List<PricePackageFeePcEntity> queryListByParam(
			PricePackageFeePcVo ppFeePcVO, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return pricePackageFeePcDao.queryListByParam(ppFeePcVO,rowBounds);
	}

	
	public Long queryCountByParam(PricePackageFeePcVo ppFeePcVO) {
		
		return pricePackageFeePcDao.queryCountByParam(ppFeePcVO);
	}

	
	public PricePackageFeePcEntity queryPpFeePcById(String id) {
		PricePackageFeePcEntity entity=new PricePackageFeePcEntity();
		entity.setId(id);
		PricePackageFeePcVo vo=new PricePackageFeePcVo();
		vo.setPpFeePcEntity(entity);
		List<PricePackageFeePcEntity> list=this.queryListByParam(vo, 1, 0);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	@Transactional
	public void addPpFeePc(PricePackageFeePcEntity ppFeePcEntity) {
		if(isExist(ppFeePcEntity)){
			throw new BusinessException(MessageType.ERROR_ISEXIST,"code");
		}
		ppFeePcEntity.setId(null);
		beforOperDeal(ppFeePcEntity);
		pricePackageFeePcDao.addPpFeePc(ppFeePcEntity);

	}

	@Transactional
	public void updatePpFeePc(
			PricePackageFeePcEntity ppFeePcEntity) {
		//先删除旧的信息
		PricePackageFeePcEntity dbEntity=this.queryPpFeePcById(ppFeePcEntity.getId());
		if(dbEntity==null)return;
		this.delFeePc(dbEntity);
		//再增加新的信息
		this.addPpFeePc(ppFeePcEntity);

	}
	@Transactional
	public void delFeePc(PricePackageFeePcEntity ppFeePcEntity) {
		ppFeePcEntity.setActive(MiserConstants.NO);
		beforOperDeal(ppFeePcEntity);
		pricePackageFeePcDao.delPpFeePc(ppFeePcEntity);
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
	private void beforOperDeal(PricePackageFeePcEntity entity){
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
	 * @param @param ppFeePcEntity
	 * @param @return   
	 * @return boolean 
	 * @throws
	 * @author 廖文强
	 * @date 2015年12月29日
	 */
	private boolean isExist(PricePackageFeePcEntity ppFeePcEntity){
		PricePackageFeePcEntity temp=new PricePackageFeePcEntity();
		temp.setActive(MiserConstants.YES);
		temp.setPriceCity(ppFeePcEntity.getPriceCity());
		temp.setTransTypeCode(ppFeePcEntity.getTransTypeCode());
		PricePackageFeeItemsEntity tempFeeItems=new PricePackageFeeItemsEntity();
		tempFeeItems.setCode(ppFeePcEntity.getPpFeeItems().getCode());
		temp.setPpFeeItems(tempFeeItems);
		PricePackageFeePcVo vo=new PricePackageFeePcVo();
		vo.setPpFeePcEntity(temp);
		List<PricePackageFeePcEntity> list=this.queryListByParam(vo, 1, 0);
		return list!=null&&list.size()>0?true:false;
	}
}

package com.hoau.miser.module.api.facade.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.miser.module.api.facade.server.dao.DiscountPrivilegeDao;
import com.hoau.miser.module.api.facade.server.service.IDiscountPrivilegeService;
import com.hoau.miser.module.api.facade.shared.domain.DiscountPrivilegeEntity;
import com.hoau.miser.module.api.facade.shared.vo.DiscountPrivilegeVo;
import com.hoau.miser.module.util.StringUtils;

/**
 * 越发越惠Service实现
 * ClassName: DiscountPrivilegeService 
 * @author 286330付于令
 * @date 2016年1月25日
 * @version V1.0
 */
@Service
public class DiscountPrivilegeService implements IDiscountPrivilegeService {

	@Resource
	private DiscountPrivilegeDao discountPrivilegeDao;
	/**
	 * @Description: 查询越发越惠基础数据List
	 * @param paramEntity
	 * @return List<List<DiscountPrivilegeVo>>
	 * @author 286330付于令
	 * @date 2016年1月25日
	 */
	@Override
	public Object queryListByParam(DiscountPrivilegeVo discountPrivilegeVo) {
		RowBounds rowBounds = new RowBounds();
		DiscountPrivilegeEntity param = getParam(discountPrivilegeVo);
		List<DiscountPrivilegeEntity> discountEntityList = discountPrivilegeDao.queryDiscountListByParam(param, rowBounds);
		List<DiscountPrivilegeEntity> couponEntityList = discountPrivilegeDao.queryCouponListByParam(param, rowBounds);
		return convertEntity2Vo(discountEntityList, couponEntityList);
	}
	private List<Object> convertEntity2Vo(List<DiscountPrivilegeEntity> discountEntityList, 
			List<DiscountPrivilegeEntity> couponEntityList) {
		List<Object> voList = new ArrayList<Object>();
		List<DiscountPrivilegeVo> discountVoList = new ArrayList<DiscountPrivilegeVo>();
		DiscountPrivilegeVo vo = null;
		int xh = 1;
		for(DiscountPrivilegeEntity entity: discountEntityList) {
			vo = new DiscountPrivilegeVo();
			if(entity.getStartAmount() != null) {
				vo.setFhjedq(entity.getStartAmount().toString());
			}
			if(entity.getEndAmount() != null) {
				vo.setFhjedz(entity.getEndAmount().toString());
			}
			if(entity.getDdMinFreightDiscount() != null) {
				vo.setDrdyfzdzk(entity.getDdMinFreightDiscount().toString());
			}
			if(entity.getDuMinFreightDiscount() != null) {
				vo.setJjkyyfzdzk(entity.getDuMinFreightDiscount().toString());
			}
			if(entity.getMaxCouponScale() != null) {
				vo.setZgfqbl(entity.getMaxCouponScale().toString());
			}
			vo.setXh(xh + "");
			xh++;
			discountVoList.add(vo);
		}
		voList.add(discountVoList);
		Collections.sort(couponEntityList, new Comparator<DiscountPrivilegeEntity>() {
			@Override
			public int compare(DiscountPrivilegeEntity o1, DiscountPrivilegeEntity o2) {
				// 根据DD折扣排序，再根据DU折扣排序
				if(o1.getDdMinFreightDiscount() > o2.getDdMinFreightDiscount()) {
					return 1;
				} else if(o1.getDdMinFreightDiscount() < o2.getDdMinFreightDiscount()) {
					return -1;
				} else if(o1.getDuMinFreightDiscount() > o2.getDuMinFreightDiscount()) {
					return 1;
				} else if(o1.getDuMinFreightDiscount() < o2.getDuMinFreightDiscount()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		List<List<DiscountPrivilegeVo>> couponVoList = new ArrayList<List<DiscountPrivilegeVo>>();
		List<DiscountPrivilegeVo> couponList = new ArrayList<DiscountPrivilegeVo>();
		int size = couponEntityList.size();
		xh = 1;
		for(int i=0; i<size; i++) {
			DiscountPrivilegeEntity entity = couponEntityList.get(i);
			vo = new DiscountPrivilegeVo();
			if(entity.getStartAmount() != null) {
				vo.setFhjedq(entity.getStartAmount().toString());
			}
			if(entity.getEndAmount() != null) {
				vo.setFhjedz(entity.getEndAmount().toString());				
			}
			if(entity.getDdMinFreightDiscount() != null) {
				vo.setDrdyfzdzk(entity.getDdMinFreightDiscount().toString());
			}
			if(entity.getDuMinFreightDiscount() != null) {
				vo.setJjkyyfzdzk(entity.getDuMinFreightDiscount().toString());
			}
			if(entity.getMaxCouponScale() != null) {
				vo.setZgfqbl(entity.getMaxCouponScale().toString());
			}
			vo.setXh(xh + "");
			xh++;
			couponList.add(vo);
			/*
			 * 条件顺序不能变（根据上一步的排序顺序来得）：
			 * 必须先判断DD折扣是否发生变化，变化了就是新分段
			 * 没有变化，再判断DU折扣是否发生变化，变化了就是新分段
			 * 否则，还是老分段
			 */
			if(i == size-1 || !entity.getDdMinFreightDiscount().equals(couponEntityList.get(i+1).getDdMinFreightDiscount()) || 
					!entity.getDuMinFreightDiscount().equals(couponEntityList.get(i+1).getDuMinFreightDiscount())) {
				couponVoList.add(couponList);
				xh = 1;
				// 新分段：新List（最后一个元素，就没有必要再新建List了）
				if(i < size-1) couponList = new ArrayList<DiscountPrivilegeVo>();
			}
		}
		voList.add(couponVoList);
		return voList;
	}
	
	private DiscountPrivilegeEntity getParam(DiscountPrivilegeVo discountPrivilegeVo) {
		DiscountPrivilegeEntity paramEntity = new DiscountPrivilegeEntity();
		if(discountPrivilegeVo != null) {
			if(!StringUtils.isEmptyWithBlock(discountPrivilegeVo.getDrdyfzdzk())) {
				paramEntity.setDdMinFreightDiscount(Double.valueOf(discountPrivilegeVo.getDrdyfzdzk()));
			}
			if(!StringUtils.isEmptyWithBlock(discountPrivilegeVo.getJjkyyfzdzk())) {
				paramEntity.setDuMinFreightDiscount(Double.valueOf(discountPrivilegeVo.getJjkyyfzdzk()));
			}
		}
		return paramEntity;
	}
}

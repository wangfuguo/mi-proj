package com.hoau.miser.module.biz.discount.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.miser.module.biz.discount.api.server.service.IDiscountPrivilegeService;
import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountPrivilegeEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PrivilegeContractDetailEntity;
import com.hoau.miser.module.biz.discount.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountPrivilegeVo;
import com.hoau.miser.module.biz.discount.server.dao.DiscountPrivilegeDao;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;
@Service
public class DiscountPrivilegeService implements IDiscountPrivilegeService {

	@Resource
	private DiscountPrivilegeDao DiscountPrivilegeDao;
	
	/**
	 * 
	 * @Description: 查询越发越惠列表
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<DiscountPrivilegeEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public List<DiscountPrivilegeEntity> queryListByParam(DiscountPrivilegeVo psv, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return DiscountPrivilegeDao.queryListByParam(psv,rowBounds);
	}
	
	/**
	 * 
	 * @Description: 统计越发越惠数量
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public Long queryCountByParam(DiscountPrivilegeVo psv) {
		return DiscountPrivilegeDao.queryCountByParam(psv);
	}
	/**
	 * 
	 * @Description: 新增越发越惠
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String addDiscountPrivilege(DiscountPrivilegeEntity pse) {
		checkParam(pse);
		DiscountPrivilegeEntity pe = new DiscountPrivilegeEntity();
		pe.setStartAmount(pse.getStartAmount());
		pe.setEndAmount(pse.getEndAmount());
		pe.setDdMinFreightDiscount(pse.getDdMinFreightDiscount());
		pe.setDuMinFreightDiscount(pse.getDuMinFreightDiscount());
		pe.setHasCoupon(pse.getHasCoupon());
		pe.setActive(MiserConstants.YES);
		pe.setState("WAIT");
		DiscountPrivilegeEntity pfe = queryDiscountPrivilegeByEvery(pe);
		boolean flag = true;
		// 如果有待生效数据
		if (null != pfe && StringUtils.isNotEmpty(pfe.getId()) && !pse.getId().equals(pfe.getId())) {
			// 0为没有弹出提示框
			if ("0".equals(pse.getIsAlert())) {
				return MessageType.ERROR_ISALERT;
			}else if ("1".equals(pse.getIsAlert())) {
				// 作废原待生效数据
				pfe.setActive(MiserConstants.NO);
				pfe.setInvalidTime(pse.getEffectiveTime());
				pfe.setModifyDate(new Date());
				pfe.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
				DiscountPrivilegeDao.updateDiscountPrivilege(pfe);
			}
			flag = false;
		}
		pe.setState("EFFECTIVE");
		pfe=queryDiscountPrivilegeByEvery(pe);
		// 如果有已生效数据
		if(null!=pfe&&StringUtils.isNotEmpty(pfe.getId())){
			pfe.setInvalidTime(pse.getEffectiveTime());
			pfe.setModifyDate(new Date());
			pfe.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			DiscountPrivilegeDao.updateDiscountPrivilege(pfe);
			flag = false;
		}
		if(flag) {
			checkPrivilege(pse);
		}
		beforAddDiscountPrivilegeDispose(pse);
		DiscountPrivilegeDao.addDiscountPrivilege(pse);
		return MessageType.ADD_SUCCESS;
	}
	/**
	 * 
	 * @Description: 修改越发越惠
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String updateDiscountPrivilege(DiscountPrivilegeEntity pse) {
		checkParam(pse);
		DiscountPrivilegeEntity newPse=queryDiscountPrivilegeById(pse.getId());
		String state=newPse.getState();
		//无法修改失效或已过期数据
		if("PASSED".equals(state)||"DELETED".equals(state)){
			return MessageType.ERROR_INVALID;
		}
		// 查询数据唯一性
		DiscountPrivilegeEntity pe = new DiscountPrivilegeEntity();
		pe.setStartAmount(pse.getStartAmount());
		pe.setEndAmount(pse.getEndAmount());
		pe.setDdMinFreightDiscount(pse.getDdMinFreightDiscount());
		pe.setDuMinFreightDiscount(pse.getDuMinFreightDiscount());
		pe.setHasCoupon(pse.getHasCoupon());
		pe.setActive(MiserConstants.YES);
		pe.setState("WAIT");
		DiscountPrivilegeEntity pfe = queryDiscountPrivilegeByEvery(pe);
		boolean flag = true;
		if("EFFECTIVE".equals(state)){
			// 如果有待生效数据
			if (null != pfe && StringUtils.isNotEmpty(pfe.getId()) && !pse.getId().equals(pfe.getId())) {
				// 0为没有弹出提示框
				if ("0".equals(pse.getIsAlert())) {
					return MessageType.ERROR_ISALERT;
				} else if ("1".equals(pse.getIsAlert())) {
					// 作废原待生效数据
					pfe.setActive(MiserConstants.NO);
					pfe.setInvalidTime(pse.getEffectiveTime());
					pfe.setModifyDate(new Date());
					pfe.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
					DiscountPrivilegeDao.updateDiscountPrivilege(pfe);
				}
				flag = false;
			}
		}else if("WAIT".equals(state)){
			if (null != pfe && StringUtils.isNotEmpty(pfe.getId())) {
				pfe.setActive(MiserConstants.NO);
				pfe.setInvalidTime(pse.getEffectiveTime());
				pfe.setModifyDate(new Date());
				pfe.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
				DiscountPrivilegeDao.updateDiscountPrivilege(pfe);
				flag = false;
			}
		}
		//设置生效中的数据的失效时间
		pe.setState("EFFECTIVE");
		pfe = queryDiscountPrivilegeByEvery(pe);
		if(null!=pfe){
			pfe.setInvalidTime(pse.getEffectiveTime());
			pfe.setModifyDate(new Date());
			pfe.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			DiscountPrivilegeDao.delDiscountPrivilege(pfe);
			flag = false;
		}
		if(flag) {
			checkPrivilege(pse);
		}
		// 新增新待生效数据
		beforAddDiscountPrivilegeDispose(pse);
		pse.setInvalidTime(null);
		DiscountPrivilegeDao.addDiscountPrivilege(pse);
		return MessageType.UPDATE_SUCCESS;
	}
	private void checkParam(DiscountPrivilegeEntity entity) {
		if(entity.getStartAmount() >= entity.getEndAmount()) {
			throw new BusinessException("段起必须小于段止！");
		}
	}

	/**
	 * 
	 * @Description: 删除越发越惠
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String delDiscountPrivilege(DiscountPrivilegeEntity pse) {
		DiscountPrivilegeEntity newPse=queryDiscountPrivilegeById(pse.getId());
		String state=newPse.getState();
		//无法修改失效或已过期数据
		if("PASSED".equals(state)||"DELETED".equals(state)){
			return MessageType.ERROR_INVALID;
		}
		pse.setModifyDate(new Date());
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setActive(MiserConstants.NO);
		pse.setInvalidTime(new Date());
		DiscountPrivilegeDao.delDiscountPrivilege(pse);
		return MessageType.DELETE_SUCCESS;
	}
	public DiscountPrivilegeEntity queryDiscountPrivilegeByEvery(DiscountPrivilegeEntity DiscountPrivilegeEntity) {
		DiscountPrivilegeVo psv=new DiscountPrivilegeVo();
		psv.setDiscountPrivilegeEntity(DiscountPrivilegeEntity);
		List<DiscountPrivilegeEntity> list=this.queryListByParam(psv,1,0);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	public DiscountPrivilegeEntity queryDiscountPrivilegeById(String id) {
		DiscountPrivilegeVo psv=new DiscountPrivilegeVo();
		DiscountPrivilegeEntity pce=new DiscountPrivilegeEntity();
		pce.setId(id);
		psv.setDiscountPrivilegeEntity(pce);
		List<DiscountPrivilegeEntity> list=this.queryListByParam(psv,1,0);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	/**
	 * 增加越发越惠之前的处理工作：
	 * 增加id，时间，active
	 * @param pse void  
	 * @Author 292078
	 * @Time 2015年12月17日上午10:22:19
	 */
	public void beforAddDiscountPrivilegeDispose(DiscountPrivilegeEntity pse){
		Date dt = new Date();
		pse.setId(UUIDUtil.getUUID());
		pse.setActive(MiserConstants.YES);
		pse.setCreateDate(dt);
		pse.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setModifyDate(dt);
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		if(pse.getEffectiveTime().before(dt)) {
			pse.setEffectiveTime(dt);
		}
	}
	
	private void checkPrivilege(DiscountPrivilegeEntity privilege) {
		// 校验重复数据
		Long existCount = DiscountPrivilegeDao.findPrivilege(privilege);
		if(existCount != null && existCount > 0) {
			// 已经存在一个分段与当前分段有重叠
			throw new BusinessException("discount.pricePrivilege.selectionError");
		}
	}
	
}

package com.hoau.miser.module.biz.extrafee.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.hoau.hbdp.framework.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.extrafee.server.dao.PriceExtrafeeStandardDao;
import com.hoau.miser.module.biz.extrafee.server.service.IPriceExtrafeeStandardService;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceExtrafeeStandardEntity;
import com.hoau.miser.module.biz.extrafee.shared.domain.PriceExtrafeeStandardEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceExtrafeeStandardVo;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceExtrafeeStandardVo;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.base.api.server.service.IDataDictionaryValueService;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryValueEntity;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PriceExtrafeeStandardService implements IPriceExtrafeeStandardService {

	private static final Logger log = LoggerFactory.getLogger(ExtrafeeAddValueFeeService.class);
	@Resource
	private PriceExtrafeeStandardDao PriceExtrafeeStandardDao;
	@Resource
	private IDataDictionaryValueService dataDictionaryValueService;
	
	/**
	 * 
	 * @Description: 查询标准附加费列表
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceExtrafeeStandardEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public List<PriceExtrafeeStandardEntity> queryListByParam(PriceExtrafeeStandardVo psv, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return PriceExtrafeeStandardDao.queryListByParam(psv,rowBounds);
	}
	
	/**
	 * 
	 * @Description: 统计标准附加费数量
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public Long queryCountByParam(PriceExtrafeeStandardVo psv) {
		return PriceExtrafeeStandardDao.queryCountByParam(psv);
	}
	/**
	 * 
	 * @Description: 新增标准附加费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@Transactional
	public String addPriceExtrafeeStandard(PriceExtrafeeStandardEntity pse) {
		PriceExtrafeeStandardEntity pe=new PriceExtrafeeStandardEntity();
		pe.setType(pse.getType());
		pe.setTransTypeCode(pse.getTransTypeCode());
		pe.setActive(MiserConstants.YES);
		//首先判断是否有生效中的数据，存在生效中的直接返回，不允许新增
		pe.setState("EFFECTIVE");
		PriceExtrafeeStandardEntity pfe=queryPriceExtrafeeStandardByEvery(pe);
		if(null!=pfe&&StringUtils.isNotEmpty(pfe.getId())){
			throw new BusinessException("已存在生效中数据，不可新增！");
		}
		//再判断是否有生效中的数据，有的话提示覆盖
		pe.setState("WAIT");
		pfe = queryPriceExtrafeeStandardByEvery(pe);
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
				PriceExtrafeeStandardDao.updatePriceExtrafeeStandard(pfe);
			}
		}
		beforAddPriceExtrafeeStandardDispose(pse);
		PriceExtrafeeStandardDao.addPriceExtrafeeStandard(pse);
		return MessageType.ADD_SUCCESS;
	}
	/**
	 * 
	 * @Description: 修改标准附加费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@Transactional
	public String updatePriceExtrafeeStandard(PriceExtrafeeStandardEntity pse) {
		// TODO Auto-generated method stub
		PriceExtrafeeStandardEntity newpse=queryPriceExtrafeeStandardById(pse.getId());
		
		String state=newpse.getState();
		//无法修改失效或已过期数据
		if("PASSED".equals(state)||"DELETED".equals(state)){
			return MessageType.ERROR_INVALID;
		}
		// 查询数据唯一性
		PriceExtrafeeStandardEntity pe=new PriceExtrafeeStandardEntity();
		pe.setType(pse.getType());
		pe.setTransTypeCode(pse.getTransTypeCode());
		pe.setActive(MiserConstants.YES);
		pe.setState("WAIT");
		PriceExtrafeeStandardEntity pfe = queryPriceExtrafeeStandardByEvery(pe);
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
					PriceExtrafeeStandardDao.updatePriceExtrafeeStandard(pfe);
				}
			}
		}else if("WAIT".equals(state)){
			if (null != pfe && StringUtils.isNotEmpty(pfe.getId())) {
			pfe.setActive(MiserConstants.NO);
			pfe.setInvalidTime(pse.getEffectiveTime());
			pfe.setModifyDate(new Date());
			pfe.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			PriceExtrafeeStandardDao.updatePriceExtrafeeStandard(pfe);
			}
		}
		//设置生效中的数据的失效时间
		pe.setState("EFFECTIVE");
		pfe = queryPriceExtrafeeStandardByEvery(pe);
		if(null!=pfe){
		pfe.setInvalidTime(pse.getEffectiveTime());
		pfe.setModifyDate(new Date());
		pfe.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		PriceExtrafeeStandardDao.delPriceExtrafeeStandard(pfe);
		}
		//新增新数据
		beforAddPriceExtrafeeStandardDispose(pse);
		pse.setInvalidTime(null);
		PriceExtrafeeStandardDao.addPriceExtrafeeStandard(pse);
		return MessageType.UPDATE_SUCCESS;
	}
	/**
	 * 
	 * @Description: 删除标准附加费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	@Transactional
	public String delPriceExtrafeeStandard(PriceExtrafeeStandardEntity pse) {
		// TODO Auto-generated method stub
		PriceExtrafeeStandardEntity newpse=queryPriceExtrafeeStandardById(pse.getId());
		String state=newpse.getState();
		//无法修改失效或已过期数据
		if("PASSED".equals(state)||"DELETED".equals(state)){
			return MessageType.ERROR_INVALID;
		}
		pse.setModifyDate(new Date());
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setActive(MiserConstants.NO);
		pse.setInvalidTime(new Date());
		PriceExtrafeeStandardDao.delPriceExtrafeeStandard(pse);
		return MessageType.DELETE_SUCCESS;
	}
	public PriceExtrafeeStandardEntity queryPriceExtrafeeStandardByEvery(PriceExtrafeeStandardEntity priceExtrafeeStandardEntity) {
		PriceExtrafeeStandardVo psv=new PriceExtrafeeStandardVo();
		psv.setPriceExtrafeeStandardEntity(priceExtrafeeStandardEntity);
		// TODO Auto-generated method stub
		List<PriceExtrafeeStandardEntity> list=this.queryListByParam(psv,1,0);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	public PriceExtrafeeStandardEntity queryPriceExtrafeeStandardById(String id) {
		// TODO Auto-generated method stub
		PriceExtrafeeStandardVo psv=new PriceExtrafeeStandardVo();
		PriceExtrafeeStandardEntity pce=new PriceExtrafeeStandardEntity();
		pce.setId(id);
		psv.setPriceExtrafeeStandardEntity(pce);
		List<PriceExtrafeeStandardEntity> list=this.queryListByParam(psv,1,0);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	/**
	 * 增加标准附加费之前的处理工作：
	 * 增加id，时间，active
	 * @param pse void  
	 * @Author 292078
	 * @Time 2015年12月17日上午10:22:19
	 */
	public void beforAddPriceExtrafeeStandardDispose(PriceExtrafeeStandardEntity pse){
		Date dt = new Date();
		pse.setId(UUIDUtil.getUUID());
		pse.setActive(MiserConstants.YES);
		pse.setCreateDate(dt);
		pse.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setModifyDate(dt);
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	}
	public List<PriceExtrafeeStandardEntity> excelListByParam(PriceExtrafeeStandardVo psv){
		return PriceExtrafeeStandardDao.queryListByParam(psv);
	}
	
	public String createExcelFile(List<PriceExtrafeeStandardEntity> list) {
		// 优惠分段
		
		String lockTypeD = "LOCK_TYPE";
		String stateD="PRICE_SATUS";
		String typeD= "ADDITIONAL_TYPE";
		
		
		for(PriceExtrafeeStandardEntity entity:list){
			entity.setLockTypeName(this.queryValueNameByTermsCodeAndValueCode(lockTypeD, String.valueOf(entity.getLockType().intValue())));
			entity.setState(this.queryValueNameByTermsCodeAndValueCode(stateD, entity.getState()));
			entity.setType(this.queryValueNameByTermsCodeAndValueCode(typeD, entity.getType()));
		}
		String filePath=null;
		List<String> titleNames=new ArrayList<String>();
		 //titleNames.add("序号");
		 titleNames.add("增值费类别");
		 titleNames.add("运输类型");
		 titleNames.add("金额/费率");
		 titleNames.add("锁定方式");
		 
		 titleNames.add("生效时间");
		 titleNames.add("失效时间");
		 titleNames.add("当前状态");
		 titleNames.add("备注");
		 titleNames.add("创建人");
		 titleNames.add("创建时间");
		 titleNames.add("最后修改人");
		 titleNames.add("最后修改时间");
		List<String> paramNames=new ArrayList<String>();
		paramNames.add("type");
		paramNames.add("transTypeName");
		paramNames.add("money");
		paramNames.add("lockTypeName");
		
		paramNames.add("effectiveTime");
		paramNames.add("invalidTime");
		paramNames.add("state");
		paramNames.add("remark");
		paramNames.add("createUser");
		paramNames.add("createDate");
		paramNames.add("modifyUser");
		paramNames.add("modifyDate");
		try {
			filePath=new ExcelUtil<PriceExtrafeeStandardEntity>().exportExcel(titleNames,paramNames, list);
		} catch (Exception e) {
			log.error("生成导出数据异常!", e);
			filePath=null;
		}
		
		return filePath;
	}
	
	private String queryValueNameByTermsCodeAndValueCode(String termsCode, String valueCode){
		String str="";
		try {
			if(!(StringUtil.isEmpty(termsCode) || StringUtil.isEmpty(valueCode))){
				str=dataDictionaryValueService.queryValueNameByTermsCodeAndValueCode(termsCode, valueCode);
			}
		} catch (Exception e) {
			DataDictionaryValueEntity ddvEntity=dataDictionaryValueService.queryByTermscodeAndValueCode(termsCode, valueCode);
			if(ddvEntity!=null){
				str=ddvEntity.getValueName();
			}
		}
		return str;
	}
}

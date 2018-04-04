package com.hoau.miser.module.biz.extrafee.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.extrafee.server.dao.ExtrafeeAddValueFeeDao;
import com.hoau.miser.module.biz.extrafee.server.service.IExtrafeeAddValueFeeService;
import com.hoau.miser.module.biz.extrafee.shared.domain.ExtrafeeAddValueFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.exception.MessageType;
import com.hoau.miser.module.biz.extrafee.shared.vo.ExtrafeeAddValueFeeVo;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.base.api.server.service.IDataDictionaryValueService;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryValueEntity;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;
@Service
public class ExtrafeeAddValueFeeService implements IExtrafeeAddValueFeeService {

	private static final Logger log = LoggerFactory.getLogger(ExtrafeeAddValueFeeService.class);
	
	@Resource
	private ExtrafeeAddValueFeeDao ExtrafeeAddValueFeeDao;
	
	@Resource
	private IDataDictionaryValueService dataDictionaryValueService;
	
	
	
	
	/**
	 * 
	 * @Description: 查询增值费列表
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<ExtrafeeAddValueFeeEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public List<ExtrafeeAddValueFeeEntity> queryListByParam(ExtrafeeAddValueFeeVo psv, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return ExtrafeeAddValueFeeDao.queryListByParam(psv,rowBounds);
	}
	
	
	/**
	 * 
	 * @Description: 统计增值费数量
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public Long queryCountByParam(ExtrafeeAddValueFeeVo psv) {
		return ExtrafeeAddValueFeeDao.queryCountByParam(psv);
	}
	/**
	 * 
	 * @Description: 新增增值费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String addExtrafeeAddValueFee(ExtrafeeAddValueFeeEntity pse) {
		// TODO Auto-generated method stub
		ExtrafeeAddValueFeeEntity pe = new ExtrafeeAddValueFeeEntity();
		pe.setCode(pse.getCode());
		pe.setTransTypeCode(pse.getTransTypeCode());
		pe.setActive(MiserConstants.YES);
		pe.setState("WAIT");
		ExtrafeeAddValueFeeEntity pfe = queryExtrafeeAddValueFeeByEvery(pe);
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
				ExtrafeeAddValueFeeDao.updateExtrafeeAddValueFee(pfe);
			}
		}
		pe.setState("EFFECTIVE");
		pfe=queryExtrafeeAddValueFeeByEvery(pe);
		if(null!=pfe&&StringUtils.isNotEmpty(pfe.getId())){
			pfe.setInvalidTime(pse.getEffectiveTime());
			pfe.setModifyDate(new Date());
			pfe.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			ExtrafeeAddValueFeeDao.updateExtrafeeAddValueFee(pfe);
		}
		beforAddExtrafeeAddValueFeeDispose(pse);
		ExtrafeeAddValueFeeDao.addExtrafeeAddValueFee(pse);
		return MessageType.ADD_SUCCESS;
	}
	/**
	 * 
	 * @Description: 修改增值费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String updateExtrafeeAddValueFee(ExtrafeeAddValueFeeEntity pse) {
		ExtrafeeAddValueFeeEntity newPse=queryExtrafeeAddValueFeeById(pse.getId());
		String state=newPse.getState();
		//无法修改失效或已过期数据
		if("PASSED".equals(state)||"DELETED".equals(state)){
			return MessageType.ERROR_INVALID;
		}
		// 查询数据唯一性
		ExtrafeeAddValueFeeEntity pe = new ExtrafeeAddValueFeeEntity();
		pe.setCode(pse.getCode());
		pe.setTransTypeCode(pse.getTransTypeCode());
		pe.setActive(MiserConstants.YES);
		pe.setState("WAIT");
		ExtrafeeAddValueFeeEntity pfe = queryExtrafeeAddValueFeeByEvery(pe);
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
					ExtrafeeAddValueFeeDao.updateExtrafeeAddValueFee(pfe);
				}
			}
		}else if("WAIT".equals(state)){
			if (null != pfe && StringUtils.isNotEmpty(pfe.getId())) {
			pfe.setActive(MiserConstants.NO);
			pfe.setInvalidTime(pse.getEffectiveTime());
			pfe.setModifyDate(new Date());
			pfe.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			ExtrafeeAddValueFeeDao.updateExtrafeeAddValueFee(pfe);
			}
		}
		//设置生效中的数据的失效时间
		pe.setState("EFFECTIVE");
		pfe = queryExtrafeeAddValueFeeByEvery(pe);
		if(null!=pfe){
			pfe.setInvalidTime(pse.getEffectiveTime());
			pfe.setModifyDate(new Date());
			pfe.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
			ExtrafeeAddValueFeeDao.delExtrafeeAddValueFee(pfe);
		}
		// 新增新待生效数据
		beforAddExtrafeeAddValueFeeDispose(pse);
		pse.setInvalidTime(null);
		ExtrafeeAddValueFeeDao.addExtrafeeAddValueFee(pse);
		return MessageType.UPDATE_SUCCESS;

	}
	/**
	 * 
	 * @Description: 删除增值费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String delExtrafeeAddValueFee(ExtrafeeAddValueFeeEntity pse) {
		// TODO Auto-generated method stub
		ExtrafeeAddValueFeeEntity newPse=queryExtrafeeAddValueFeeById(pse.getId());
		String state=newPse.getState();
		//无法修改失效或已过期数据
		if("PASSED".equals(state)||"DELETED".equals(state)){
			return MessageType.ERROR_INVALID;
		}
		pse.setModifyDate(new Date());
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setActive(MiserConstants.NO);
		pse.setInvalidTime(new Date());
		ExtrafeeAddValueFeeDao.delExtrafeeAddValueFee(pse);
		return MessageType.DELETE_SUCCESS;
	}
	public ExtrafeeAddValueFeeEntity queryExtrafeeAddValueFeeByEvery(ExtrafeeAddValueFeeEntity ExtrafeeAddValueFeeEntity) {
		ExtrafeeAddValueFeeVo psv=new ExtrafeeAddValueFeeVo();
		psv.setExtrafeeAddValueFeeEntity(ExtrafeeAddValueFeeEntity);
		// TODO Auto-generated method stub
		List<ExtrafeeAddValueFeeEntity> list=this.queryListByParam(psv,1,0);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	public ExtrafeeAddValueFeeEntity queryExtrafeeAddValueFeeById(String id) {
		// TODO Auto-generated method stub
		ExtrafeeAddValueFeeVo psv=new ExtrafeeAddValueFeeVo();
		ExtrafeeAddValueFeeEntity pce=new ExtrafeeAddValueFeeEntity();
		pce.setId(id);
		psv.setExtrafeeAddValueFeeEntity(pce);
		List<ExtrafeeAddValueFeeEntity> list=this.queryListByParam(psv,1,0);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	/**
	 * 增加增值费之前的处理工作：
	 * 增加id，时间，active
	 * @param pse void  
	 * @Author 292078
	 * @Time 2015年12月17日上午10:22:19
	 */
	public void beforAddExtrafeeAddValueFeeDispose(ExtrafeeAddValueFeeEntity pse){
		Date dt = new Date();
		pse.setId(UUIDUtil.getUUID());
		pse.setActive(MiserConstants.YES);
		pse.setCreateDate(dt);
		pse.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		pse.setModifyDate(dt);
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	}

	public List<ExtrafeeAddValueFeeEntity> excelListByParam(ExtrafeeAddValueFeeVo psv){
		return ExtrafeeAddValueFeeDao.queryListByParam(psv);
	}
	
	public String createExcelFile(List<ExtrafeeAddValueFeeEntity> list) {
		// 优惠分段
		
		String calculationTypeD = "AddValueFeecalculation";
		String lockTypeD = "LOCK_TYPE";
		String stateD="PRICE_SATUS";
		
		
		for(ExtrafeeAddValueFeeEntity entity:list){
			entity.setCalculationType(this.queryValueNameByTermsCodeAndValueCode(calculationTypeD, entity.getCalculationType()));
			entity.setLockTypeName(this.queryValueNameByTermsCodeAndValueCode(lockTypeD, String.valueOf(entity.getLockType().intValue())));
			entity.setState(this.queryValueNameByTermsCodeAndValueCode(stateD, entity.getState()));
		}
		String filePath=null;
		List<String> titleNames=new ArrayList<String>();
		 //titleNames.add("序号");
		 titleNames.add("增值费科目");
		 titleNames.add("运输类型");
		 titleNames.add("重量单价");
		 titleNames.add("体积单价");
		 titleNames.add("最低收费");
		 titleNames.add("锁定状态");
		 titleNames.add("计算方式");
		 titleNames.add("生效时间");
		 titleNames.add("失效时间");
		 titleNames.add("当前状态");
		 titleNames.add("备注");
		 titleNames.add("创建人");
		 titleNames.add("创建时间");
		 titleNames.add("最后修改人");
		 titleNames.add("最后修改时间");
		List<String> paramNames=new ArrayList<String>();
		paramNames.add("codeName");
		paramNames.add("transTypeName");
		paramNames.add("weightPrice");
		paramNames.add("lightPrice");
		paramNames.add("lowestMoney");
		paramNames.add("lockTypeName");
		paramNames.add("calculationType");
		paramNames.add("effectiveTime");
		paramNames.add("invalidTime");
		paramNames.add("state");
		paramNames.add("remark");
		paramNames.add("createUser");
		paramNames.add("createDate");
		paramNames.add("modifyUser");
		paramNames.add("modifyDate");
		try {
			filePath=new ExcelUtil<ExtrafeeAddValueFeeEntity>().exportExcel(titleNames,paramNames, list);
		} catch (Exception e) {
			// TODO: handle exception
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

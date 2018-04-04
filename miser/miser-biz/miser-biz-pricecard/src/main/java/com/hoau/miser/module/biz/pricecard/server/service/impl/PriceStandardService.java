package com.hoau.miser.module.biz.pricecard.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.IPriceCityService;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;
import com.hoau.miser.module.util.StringUtils;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.miser.module.biz.base.api.server.service.ITranstypeService;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceStandardService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardVo;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceStandardDao;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.DateUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class PriceStandardService implements IPriceStandardService {

	private static final Logger log = LoggerFactory.getLogger(PriceStandardService.class);
	@Resource
	private PriceStandardDao priceStandardDao;
	@Resource
	private IPriceSectionService priceSectionService;
	@Resource
	private IPriceCityService priceCityService;
	@Resource
	private ITranstypeService transtypeService;
	
	public List<PriceStandardEntity> queryListByParam(PriceStandardVo psv, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return priceStandardDao.queryListByParam(psv,rowBounds);
	}

	public Long queryCountByParam(PriceStandardVo psv) {
		return priceStandardDao.queryCountByParam(psv);
	}
	@Transactional
	public Integer addPriceStandard(PriceStandardEntity pse,Boolean isConfirmAdd) {
		pse.setActive(MiserConstants.YES);
		return this.addOrUpdatePriceStandard(pse,isConfirmAdd);
	}
	@Transactional
	public Integer updatePriceStandard(PriceStandardEntity pse,Boolean isConfirmAdd) {
		return this.addOrUpdatePriceStandard(pse,isConfirmAdd);
	}
	
	/**
	 * A、新增单条价格时同此规则，如果存在待生效数据提示“该线路存在相同的待生效数据，是否覆盖？”确认，则直接覆盖。
	 * 如果无则直接新增。
	 * B、修改，如果修改待生效数据，则更新原待生效数据。
	 * 如果修改生效中数据，则产生一条待生效数据，此时如果存在另一条待生效数据提示“该线路存在相同的待生效数据，是否覆盖？” 确认，则直接覆盖。
	 * 如果无则直接新增待生效数据，已失效数据不能修改。 
	 * @Description:增加和修改数据的逻辑处理
	 * @param @param pse
	 * @param @param isConfirmAdd
	 * @param @return   
	 * @return Integer -1:时间不满足,0：需确认，1：成功，2：状态发生变化
	 * @throws
	 * @author 廖文强
	 * @date 2015年12月30日
	 */
	private Integer addOrUpdatePriceStandard(PriceStandardEntity pse,Boolean isConfirmAdd) {
		//默认成功
		Integer result=1;
		Long minuteDiff2= DateUtils.getMinuteDiff(pse.getEffectiveTime(),new Date());
		if(minuteDiff2>0){
			result=-1;
			//起始时间要大于当前时间
			return result;
		}
		//查询当前信息状态,如果状态不符合修改条件，给用户以提示
		if(!StringUtils.isEmpty(pse.getId())){
			PriceStandardEntity selfEntity=this.queryPriceStandardById(pse.getId());
			if(selfEntity.getActive().equals(MiserConstants.NO)||!(PriceStandardEntity.STATE_2.equals(selfEntity.getState())||PriceStandardEntity.STATE_3.equals(selfEntity.getState()))){
				result=2;
				return result;
			}
		}
		
		//查询历史有效信息
		PriceStandardEntity queryEntity=new PriceStandardEntity();
		queryEntity.setActive(MiserConstants.YES);
		queryEntity.setTransTypeCode(pse.getTransTypeCode());
		queryEntity.setSendPriceCity(pse.getSendPriceCity());
		queryEntity.setArrivePriceCity(pse.getArrivePriceCity());
		queryEntity.setState(PriceStandardEntity.STATE_2);
		PriceStandardVo queryVo1=new PriceStandardVo();
		queryVo1.setPriceStandardEntity(queryEntity);
		//查询生效中的信息
		List<PriceStandardEntity> state2List= this.queryListByParam(queryVo1, 1, 0);
		//查询待生效的信息
		queryEntity.setState(PriceStandardEntity.STATE_3);
		List<PriceStandardEntity> state3List= this.queryListByParam(queryVo1, 1, 0);
		if(state3List!=null&&state3List.size()>0){//存在待生效信息
			
			if(isConfirmAdd){//确认新增
				//将旧的待生效信息作废
				PriceStandardEntity state3Entity=state3List.get(0);
				state3Entity.setActive(MiserConstants.NO);
				beforOperDeal(state3Entity);
				priceStandardDao.updatePriceStandard(state3Entity);
				
			}else{//表示需要确认是否新增
				
				result=0;
				return result;
			}
		}
		
		//新增待生效信息
		pse.setId(null);
		beforOperDeal(pse);
		priceStandardDao.addPriceStandard(pse);
		
		// 将生效中的信息的失效时间赋值为待生效的生效时间
		if(state2List!=null&&state2List.size()>0){
			PriceStandardEntity state2Entity=state2List.get(0);
			beforOperDeal(state2Entity);
			state2Entity.setInvalidTime(pse.getEffectiveTime());
			priceStandardDao.updatePriceStandard(state2Entity);
		}
		
		return result;
		
	}
	
	/**
	 * 变更标准价格之前的处理工作：
	 * 增加id，时间，active
	 * @param pse void  
	 * @Author 廖文强
	 * @Time 2015年12月17日上午10:22:19
	 */
	public void beforOperDeal(PriceStandardEntity pse){
		Date dt = new Date();
		if(StringUtil.isEmpty(pse.getId())){//新增的话
			pse.setId(UUIDUtil.getUUID());
			pse.setActive(MiserConstants.YES);
			pse.setCreateDate(dt);
			pse.setCreateUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		}
		pse.setModifyDate(dt);
		pse.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	}
	
	//统一时间
	private void beforOperDealForThreadNew(PriceStandardEntity pse,String userName,Date dt){
		if(StringUtil.isEmpty(pse.getId())){//新增的话
			pse.setId(UUIDUtil.getUUID());
			pse.setActive(MiserConstants.YES);
			pse.setCreateDate(dt);
			pse.setCreateUser(userName);
		}
		pse.setModifyDate(dt);
		pse.setModifyUser(userName);
	}

	@Transactional
	public int delPriceStandard(PriceStandardEntity pse) {
		int result=0;
		//pse.setRemark(MiserUserContext.getCurrentUser().getEmpNameAndUserName()+" 于 "+DateUtils.convert(new Date())+" 手动删除");
		//查询当前信息状态,如果状态不符合修改条件，给用户以提示
		PriceStandardEntity selfEntity = this.queryPriceStandardById(pse.getId());
		if (selfEntity.getActive().equals(MiserConstants.NO)
				|| !(PriceStandardEntity.STATE_2.equals(selfEntity.getState()) || PriceStandardEntity.STATE_3
						.equals(selfEntity.getState()))) {
			result = 2;
			return result;
		}
		if(PriceStandardEntity.STATE_3.equals(pse.getState())){//待生效数据
			beforOperDeal(pse);
			pse.setActive(MiserConstants.NO);
			priceStandardDao.updatePriceStandard(pse);
			//生效信息中的失效时间设为null
			PriceStandardEntity queryEntity=new PriceStandardEntity();
			queryEntity.setActive(MiserConstants.YES);
			queryEntity.setTransTypeCode(pse.getTransTypeCode());
			queryEntity.setSendPriceCity(pse.getSendPriceCity());
			queryEntity.setArrivePriceCity(pse.getArrivePriceCity());
			queryEntity.setState(PriceStandardEntity.STATE_2);
			PriceStandardVo queryVo1=new PriceStandardVo();
			queryVo1.setPriceStandardEntity(queryEntity);
			//查询生效中的信息
			List<PriceStandardEntity> state2List= this.queryListByParam(queryVo1, 1, 0);

			// 将生效中的信息的失效时间赋值为待生效的生效时间
			if(state2List!=null&&state2List.size()>0){
				PriceStandardEntity state2Entity=state2List.get(0);
				beforOperDeal(state2Entity);
				state2Entity.setState("setNullInvalidTime");
				state2Entity.setInvalidTime(null);
				priceStandardDao.updatePriceStandard(state2Entity);
			}
		}else if(PriceStandardEntity.STATE_2.equals(pse.getState())){//生效中
			beforOperDeal(pse);
			pse.setInvalidTime(new Date());
			priceStandardDao.updatePriceStandard(pse);
			
		}
		result=1;
		return result;
		
	}

	public PriceStandardEntity queryPriceStandardById(String id) {
		// TODO Auto-generated method stub
		PriceStandardVo psv=new PriceStandardVo();
		PriceStandardEntity pce=new PriceStandardEntity();
		pce.setId(id);
		psv.setPriceStandardEntity(pce);
		List<PriceStandardEntity> list=this.queryListByParam(psv,1,0);
		return list!=null&&list.size()>0?list.get(0):null;
	}
	@Transactional
	public int bathDelPriceStandard(List<PriceStandardEntity> entitys) {
		int result=-1;
		for(PriceStandardEntity pse:entitys){
			result=this.delPriceStandard(pse);
			if(result==2){//状态发生变化
				return result;
			}
		}
		return result;
		
		
	}
	/**
	 * A、新增单条价格时同此规则，如果存在待生效数据提示“该线路存在相同的待生效数据，是否覆盖？”确认，则直接覆盖。
	 * 如果无则直接新增。
	 * B、修改，如果修改待生效数据，则更新原待生效数据。
	 * 如果修改生效中数据，则产生一条待生效数据，此时如果存在另一条待生效数据提示“该线路存在相同的待生效数据，是否覆盖？” 确认，则直接覆盖。
	 * 如果无则直接新增待生效数据，已失效数据不能修改。 
	 * @Description: 批量导入数据时调用这个方法（大体逻辑应该跟@addOrUpdatePriceStandard相同,只是做了统计工作)
	 * @param @param pse
	 * @param @param addSize
	 * @param @param coverSize   
	 * @return void 
	 * @throws Exception 
	 * @throws
	 * @author 廖文强
	 * @date 2015年12月31日
	 */
	 /*private void addOrUpdatePriceStandard(PriceStandardEntity pse,Map<String,Long> countMap) throws Exception {
		
		long addSize= countMap.get("addSize").longValue();
		long coverSize= countMap.get("coverSize").longValue();
		//查询历史有效信息
		PriceStandardEntity queryEntity=new PriceStandardEntity();
		queryEntity.setActive(MiserConstants.YES);
		queryEntity.setTransTypeCode(pse.getTransTypeCode());
		queryEntity.setSendPriceCity(pse.getSendPriceCity());
		queryEntity.setArrivePriceCity(pse.getArrivePriceCity());
		queryEntity.setState(PriceStandardEntity.STATE_2);
		PriceStandardVo queryVo1=new PriceStandardVo();
		if(pse.getTransTypeCode()==null||pse.getSendPriceCity().getCode()==null||pse.getArrivePriceCity().getCode()==null){
			throw new Exception("运输类型、出发城市、或到达城市数据错误！");	
		}
		queryVo1.setPriceStandardEntity(queryEntity);
		//查询生效中的信息
		List<PriceStandardEntity> state2List= this.queryListByParam(queryVo1, 1, 0);
		//查询待生效的信息
		queryEntity.setState(PriceStandardEntity.STATE_3);
		List<PriceStandardEntity> state3List= this.queryListByParam(queryVo1, 1, 0);
		if(state3List!=null&&state3List.size()>0){//存在待生效信息
				//将旧的待生效信息作废
				PriceStandardEntity state3Entity=state3List.get(0);
				state3Entity.setActive(MiserConstants.NO);
				beforOperDeal(state3Entity);
				priceStandardDao.updatePriceStandard(state3Entity);
				coverSize++;
				
		}else{
			addSize++;
		}
		
		//新增待生效信息
		Long minuteDiff2= DateUtils.getMinuteDiff(pse.getEffectiveTime(),new Date());
		if(minuteDiff2>0)pse.setEffectiveTime(new Date());
		pse.setId(null);
		beforOperDeal(pse);
		priceStandardDao.addPriceStandard(pse);
		
		// 将生效中的信息的失效时间赋值为待生效的生效时间
		if(state2List!=null&&state2List.size()>0){
			PriceStandardEntity state2Entity=state2List.get(0);
			beforOperDeal(state2Entity);
			state2Entity.setInvalidTime(pse.getEffectiveTime());
			priceStandardDao.updatePriceStandard(state2Entity);
		}
		countMap.put("addSize", addSize);
		countMap.put("coverSize", coverSize);
		
	}*/

	//优化后的导入
	private void addOrUpdatePriceStandardForThread(PriceStandardEntity pse,Map<String,Long> countMap,String userName,Date dt) throws Exception {
		
		long addSize= countMap.get("addSize").longValue();
		long coverSize= countMap.get("coverSize").longValue();
		
		PriceStandardEntity queryEntity=new PriceStandardEntity();
		queryEntity.setTransTypeCode(pse.getTransTypeCode());
		queryEntity.setSendPriceCity(pse.getSendPriceCity());
		queryEntity.setArrivePriceCity(pse.getArrivePriceCity());
		
		PriceStandardVo queryVo1=new PriceStandardVo();
		queryVo1.setPriceStandardEntity(queryEntity);
		List<PriceStandardEntity> list = priceStandardDao.searchEffectTimeData(queryVo1);
		if(list.size() > 0){
			for (PriceStandardEntity priceStandardEntity : list) {
				if(priceStandardEntity.getState().equals(PriceStandardEntity.STATE_2)){
					//生效
					beforOperDealForThreadNew(priceStandardEntity,userName,dt);
					priceStandardEntity.setInvalidTime(pse.getEffectiveTime());
					priceStandardDao.updatePriceStandard(priceStandardEntity);
					addSize++;//增加 当前生效的只能是一条
				}else if(priceStandardEntity.getState().equals(PriceStandardEntity.STATE_3)){
					//待生效
					priceStandardEntity.setActive(MiserConstants.NO);
					beforOperDealForThreadNew(priceStandardEntity,userName,dt);
					priceStandardDao.updatePriceStandard(priceStandardEntity);
					coverSize++;//替换
				}
			}
		}else{
			addSize++;//增加
		}
		beforOperDealForThreadNew(pse,userName,dt);
		priceStandardDao.addPriceStandard(pse);
		countMap.put("addSize", addSize);
		countMap.put("coverSize", coverSize);
			
	}
	
	
	
	//优化后的批量导入
	@Transactional
	public  Map<String,Object>  bathImplPriceStandards(String path) throws Exception {
		Map<String,Object> retuMap=new HashMap<String, Object>();
		List<Map<String, String>> list;
		Date nowDate =  searchCurrentDateTime();
		List<PriceStandardEntity> pseList = new ArrayList<PriceStandardEntity>();
		List<ExcelPriceStandardEntity> errorList = new ArrayList<ExcelPriceStandardEntity>();
		list = ExcelUtil.readToListByFile(path, 15, 2);
		//运输类型
		Map<String,String> transTypeCodeMap=new HashMap<String, String>();
		
		TranstypeVo transtypeVo=new TranstypeVo();
		TranstypeEntity transtypeEntity=new TranstypeEntity();
		transtypeEntity.setActive(MiserConstants.YES);
		transtypeVo.setTranstypeEntity(transtypeEntity);
		List<TranstypeEntity> transtypeEntityList=transtypeService.queryTranstypes(transtypeVo);
		for(TranstypeEntity value:transtypeEntityList){
			transTypeCodeMap.put(value.getName(), value.getCode());
		}
		
		//优惠分段
		PriceSectionVo psv=new PriceSectionVo();
		PriceSectionEntity sEntity = new PriceSectionEntity();
		sEntity.setActive(MiserConstants.YES);
		psv.setPriceSectionEntity(sEntity);
		List<PriceSectionEntity> sList = priceSectionService
				.queryPriceSection(psv);
		Map<String,String> sectionMap=new HashMap<String, String>();
		for(PriceSectionEntity entity:sList){
			sectionMap.put(entity.getName(), entity.getCode());
		}
        //缓存出发到达价格城市名称对应编码
		Map<String, String> sendPriceCityNameAndCodes = new HashMap<String, String>();
		for(PriceCityEntity  pce : priceCityService.queryAllStartStandardPriceCities()){
			sendPriceCityNameAndCodes.put( pce.getName(), pce.getCode());
		}
		Map<String, String> arrivalPriceCityNameAndCodes = new HashMap<String, String>();
		for(PriceCityEntity  pce : priceCityService.queryAllArrivalStandardPriceCities()){
			arrivalPriceCityNameAndCodes.put( pce.getName(), pce.getCode());
		}
		
		Hashtable<String, Long> countMap = new Hashtable<String, Long>();
		countMap.put("addSize", new Long(0));// 增加条数
		countMap.put("coverSize", new Long(0));// 覆盖条数
		countMap.put("errorSize",new Long(0));//错误的条数
		
		int temp = 1;
		for (Map<String, String> map : list) {
			temp ++;
			PriceStandardEntity pse = new PriceStandardEntity();
			try {
				pse.setActive("Y");
                //根据名称从接口获取出发价格城市编码
                PriceCityEntity sendPriceCity = new PriceCityEntity();
                String sendPriceCityName = map.get("0") == null ? "" : map.get("0").trim();
                if (sendPriceCityNameAndCodes.containsKey(sendPriceCityName)) {
                    sendPriceCity.setCode(sendPriceCityNameAndCodes.get(sendPriceCityName));
                    sendPriceCity.setName(sendPriceCityName);
                } else {
                    throw new Exception("出发价格城市["+ sendPriceCityName +"]不存在");
                }
                //根据名称从接口获取到达价格城市编码
				PriceCityEntity arrivePriceCity = new PriceCityEntity();
                String arrivalPriceCityName = map.get("1") == null ? "" : map.get("1").trim();
                if (arrivalPriceCityNameAndCodes.containsKey(arrivalPriceCityName)) {
                    arrivePriceCity.setCode(arrivalPriceCityNameAndCodes.get(arrivalPriceCityName));
                    arrivePriceCity.setName(arrivalPriceCityName);
                } else {
                    throw new Exception("到达价格城市["+ arrivalPriceCityName +"]不存在");
                }
                
                String transTypeName = map.get(2+"")== null? "": map.get(2+"").trim();
                if(transTypeCodeMap.containsKey(transTypeName)){
                	pse.setTransTypeCode(transTypeCodeMap.get(transTypeName));
    				pse.setTransTypeName(transTypeName);
                }else{
                	throw new Exception("运输类型["+ transTypeName +"]不存在");
                }
				
				try{
					pse.setWeightPrice(Double.valueOf(StringUtil.trim(map.get(3+""))));
				}catch(Exception ex){
					throw new Exception("重量单价["+ map.get(3+"") +"]格式不正确,只能是数字类型");
				}
				try{
					pse.setVolumePrice(Double.valueOf(StringUtil.trim(map.get(4+""))));
				}catch(Exception ex){
					throw new Exception("体积单价["+ map.get(4+"") +"]格式不正确,只能是数字类型");
				}
				try{
					pse.setAddFee(Double.valueOf(StringUtil.trim(map.get(5+""))));
				}catch(Exception ex){
					throw new Exception("附加费["+ map.get(5+"") +"]格式不正确,只能是数字类型");
				}
				try{
					pse.setLowestFee(Double.valueOf(StringUtil.trim(map.get(6+""))));
				}catch(Exception ex){
					throw new Exception("最低收费["+ map.get(6+"") +"]格式不正确,只能是数字类型");
				}
				pse.setFuelFeeSection(sectionMap.get(StringUtil.trim(map.get(7+""))));
				pse.setEffectiveTime(DateUtils.convert(map.get(8+"")));
				if(pse.getEffectiveTime()==null){
					throw new Exception("生效时间["+ map.get(8+"") +"]不能被转为日期");
				}
				if(DateUtils.getMinuteDiff(pse.getEffectiveTime(),nowDate)>0){
					pse.setEffectiveTime(nowDate);
				};
				pse.setRemark(map.get(9+""));
				pse.setSendPriceCity(sendPriceCity);
				pse.setArrivePriceCity(arrivePriceCity);
			} catch (Exception e) {
				ExcelPriceStandardEntity eps=new ExcelPriceStandardEntity();
				eps.setSendPriceCityName(map.get(0+""));
				eps.setArrivePriceCityName(map.get(1+""));
				eps.setTransTypeCode(map.get(2+""));
				eps.setWeightPrice(StringUtil.trim(map.get(3+"")));
				eps.setVolumePrice(StringUtil.trim(map.get(4+"")));
				eps.setAddFee(StringUtil.trim(map.get(5+"")));
				eps.setLowestFee(StringUtil.trim(map.get(6+"")));
				eps.setEffectiveTime(map.get(8+""));
				eps.setFuelFeeSection(map.get(7+""));
				eps.setRemark(map.get(10+""));
				eps.setOldSerial(temp+"");
				eps.setErrorMsg(e.getMessage());
				errorList.add(eps);
				
				pse = null;
				log.error("批量导入模版数据异常，业务需要仅作提示", e);
				
			}finally{
				pseList.add(pse);
			}

		}
		//错误的信息
		Vector<ExcelPriceStandardEntity> errorEpsList=new Vector<ExcelPriceStandardEntity>();
				
		createThread(pseList,countMap,list,errorEpsList,nowDate);
		
		log.info("***************************标准价格导入完成");
		
		errorEpsList.addAll(errorList);
		
		String filePath="";
		
		retuMap.put("addSize", countMap.get("addSize"));
		retuMap.put("coverSize", countMap.get("coverSize"));
		retuMap.put("sumSize",  list.size());
		retuMap.put("errorSize", errorEpsList.size());
		if(errorEpsList.size()>0){
			
			 List<String> titleNames=new ArrayList<String>();
			 titleNames.add("出发价格城市");
			 titleNames.add("到达价格城市");
			 titleNames.add("运输类型");
			 titleNames.add("重量单价");
			 titleNames.add("体积单价");
			 titleNames.add("附加费");
			 titleNames.add("最低收费");
			 titleNames.add("燃油服务费");
			 titleNames.add("生效时间");
			 titleNames.add("当前状态");
			 titleNames.add("备注");
			 titleNames.add("旧序列号");
			 titleNames.add("异常信息");
			List<String> paramNames=new ArrayList<String>();
			paramNames.add("sendPriceCityName");
			paramNames.add("arrivePriceCityName");
			paramNames.add("transTypeCode");
			paramNames.add("weightPrice");
			paramNames.add("volumePrice");
			paramNames.add("addFee");
			paramNames.add("lowestFee");
			paramNames.add("fuelFeeSection");
			paramNames.add("effectiveTime");
			paramNames.add("state");
			paramNames.add("remark");
			paramNames.add("oldSerial");
			paramNames.add("errorMsg");
			filePath=new ExcelUtil<ExcelPriceStandardEntity>().exportExcel(titleNames,paramNames,errorEpsList);
		}
		retuMap.put("filePath", filePath);
		
		return retuMap;
	}
	
	
	
	
	private void createThread(List<PriceStandardEntity> pseList,
			Map<String,Long> countMap,
			List<Map<String, String>> excelmap,
			List<ExcelPriceStandardEntity> errorEpsList,Date dt) throws InterruptedException{
		
		List<PriceStandardEntity> thread0 = new ArrayList<PriceStandardEntity>();
		List<PriceStandardEntity> thread1 = new ArrayList<PriceStandardEntity>();
		List<PriceStandardEntity> thread2 = new ArrayList<PriceStandardEntity>();
		List<PriceStandardEntity> thread3 = new ArrayList<PriceStandardEntity>();
		List<PriceStandardEntity> thread4 = new ArrayList<PriceStandardEntity>();
		List<PriceStandardEntity> thread5 = new ArrayList<PriceStandardEntity>();
		List<PriceStandardEntity> thread6 = new ArrayList<PriceStandardEntity>();
		List<PriceStandardEntity> thread7 = new ArrayList<PriceStandardEntity>();
		List<PriceStandardEntity> thread8 = new ArrayList<PriceStandardEntity>();
		List<PriceStandardEntity> thread9 = new ArrayList<PriceStandardEntity>();
		
		int count = 0;
		
		
		for(int i = 0; i < pseList.size(); i++){
			switch (i%10) {
			case 0:
				thread0.add(pseList.get(i));count = count >1 ? count : 1 ;break;
			case 1:
				thread1.add(pseList.get(i));count = count >2 ? count : 2 ;break;
			case 2:
				thread2.add(pseList.get(i));count = count >3 ? count : 3 ;break;
			case 3:
				thread3.add(pseList.get(i));count = count >4 ? count : 4 ;break;
			case 4:
				thread4.add(pseList.get(i));count = count >5 ? count : 5 ;break;
			case 5:
				thread5.add(pseList.get(i));count = count >6 ? count : 6 ;break;
			case 6:
				thread6.add(pseList.get(i));count = count >7 ? count : 7 ;break;
			case 7:
				thread7.add(pseList.get(i));count = count >8 ? count : 8 ;break;
			case 8:
				thread8.add(pseList.get(i));count = count >9 ? count : 9 ;break;
			case 9:
				thread9.add(pseList.get(i));count = count >10 ? count : 10; break;
			default:
				break;
			}
		}
		
		CountDownLatch  latch = new CountDownLatch(count);
		
		if(thread0.size()>0){
			ImplThread it = new ImplThread(thread0,0,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread1.size()>0){
			ImplThread it = new ImplThread(thread1,1,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread2.size()>0){
			ImplThread it = new ImplThread(thread2,2,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread3.size()>0){
			ImplThread it = new ImplThread(thread3,3,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread4.size()>0){
			ImplThread it = new ImplThread(thread4,4,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread5.size()>0){
			ImplThread it = new ImplThread(thread5,5,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread6.size()>0){
			ImplThread it = new ImplThread(thread6,6,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread7.size()>0){
			ImplThread it = new ImplThread(thread7,7,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread8.size()>0){
			ImplThread it = new ImplThread(thread8,8,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		if(thread9.size()>0){
			ImplThread it = new ImplThread(thread9,9,errorEpsList,excelmap,countMap,this,MiserUserContext.getCurrentUser().getEmpNameAndUserName(),dt,latch);
			it.start();
		}
		
		latch.await();
		
	}
	
	private class ImplThread extends Thread{
		public ImplThread(List<PriceStandardEntity> list,
				int current,
				List<ExcelPriceStandardEntity> errorList,
				List<Map<String, String>> excelmapl,
				Map<String,Long> countMap,
				PriceStandardService priceStandardServices,
				String userCode,
				Date dt,
				CountDownLatch latch){
			this.dt = dt;
			this.latch = latch;
			this.list = list;
			this.current = current;
			this.errorList = errorList;
			this.excelmapl = excelmapl;
			this.countMap = countMap;
			this.priceStandardServices = priceStandardServices;
			this.userCode = userCode;
		}
		
		private Date dt;
		private CountDownLatch latch;
		private List<PriceStandardEntity> list;
		//当前序列号
		private int current = 0;
		//错误List
		private List<ExcelPriceStandardEntity> errorList;
		// exlce
		private List<Map<String, String>> excelmapl;
		//统计 map
		private Map<String,Long> countMap;
		//执行Service
		private PriceStandardService priceStandardServices;
		
		private String userCode;

		public void run() {
			Map<String,Long> countMapt = new Hashtable<String, Long>();
			countMapt.put("addSize", new Long(0));// 增加条数
			countMapt.put("coverSize", new Long(0));// 覆盖条数
			countMapt.put("errorSize",new Long(0));//错误的条数
			List<ExcelPriceStandardEntity> excelList = new ArrayList<ExcelPriceStandardEntity>();
			for(int i = 0; i< list.size(); i++){
				PriceStandardEntity pse = list.get(i);
				Map<String, String> excelmap = excelmapl.get((10*i) + current);
				if(pse == null){
					countMapt.put("errorSize",countMapt.get("errorSize")+1);
					continue;
				}
				try {
					priceStandardServices.addOrUpdatePriceStandardForThread(pse, countMapt,userCode,dt);
				} catch (Exception e) {
					countMapt.put("errorSize",countMapt.get("errorSize")+1);
					log.error("批量导入模版数据更新操作异常，业务需要仅作提示", e);
					
					/**
					 * 把错误信息收集起来，将来导给用户看
					 */
					ExcelPriceStandardEntity eps=new ExcelPriceStandardEntity();
					eps.setSendPriceCityName(excelmap.get(0+""));
					eps.setArrivePriceCityName(excelmap.get(1+""));
					eps.setTransTypeCode(excelmap.get(2+""));
					eps.setWeightPrice(StringUtil.trim(excelmap.get(3+"")));
					eps.setVolumePrice(StringUtil.trim(excelmap.get(4+"")));
					eps.setAddFee(StringUtil.trim(excelmap.get(5+"")));
					eps.setLowestFee(StringUtil.trim(excelmap.get(6+"")));
					eps.setFuelFeeSection(excelmap.get(7+""));
					
					eps.setEffectiveTime(excelmap.get(8+""));
					eps.setRemark(excelmap.get(10+""));
					eps.setOldSerial((10*i) + current+"");
					eps.setErrorMsg(e.getMessage());
					excelList.add(eps);
					
				}
			}
			synchronized(countMap){
				countMap.put("addSize", countMap.get("addSize") + countMapt.get("addSize"));
				countMap.put("coverSize", countMap.get("coverSize") + countMapt.get("coverSize"));
				countMap.put("errorSize", countMap.get("errorSize") + countMapt.get("errorSize"));
			}
			synchronized (errorList) {
				errorList.addAll(excelList);
			}
			latch.countDown();
		}
	}
	
	public List<ExcelPriceStandardEntity> excelQueryListByParam(
			PriceStandardVo psv) {
		return priceStandardDao.excelQueryListByParam(psv);
	}

	public ExcelExportResultEntity createExcelFile(PriceStandardVo entity) {
		if (entity == null || entity.getPriceStandardEntity() == null) {
			return null;
		}
		//表头
		List<String> titleNames = new ArrayList<String>();
		//导出前几列与导入模板相同
		titleNames.add("出发价格城市");
		titleNames.add("到达价格城市");
		titleNames.add("运输类型");
		titleNames.add("重量单价");
		titleNames.add("体积单价");
		titleNames.add("附加费");
		titleNames.add("最低收费");
		titleNames.add("燃油服务费");
		titleNames.add("生效时间");
		titleNames.add("备注");

		titleNames.add("当前状态");
		titleNames.add("失效时间");
		titleNames.add("创建人");
		titleNames.add("创建时间");
		titleNames.add("最后修改时间");
		titleNames.add("最后修改人");
		//查询参数
		List<Object> params = new ArrayList<Object>();
		//查询语句
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		//
		sql.append("SPCE.NAME AS SPCE_NAME, ");
		sql.append("APCE.NAME AS APCE_NAME, ");
		sql.append("PSE.TRANS_TYPE_NAME AS TRANS_TYPE_NAME, ");
		sql.append("PSE.WEIGHT_PRICE AS WEIGHT_PRICE, ");
		sql.append("PSE.VOLUME_PRICE AS VOLUME_PRICE, ");
		sql.append("PSE.ADD_FEE AS ADD_FEE, ");
		sql.append("PSE.LOWEST_FEE AS LOWEST_FEE, ");
		sql.append("SECTION.NAME AS FUEL_FEE_SECTION_NAME, ");
		sql.append("PSE.EFFECTIVE_TIME AS EFFECTIVE_TIME, ");
		sql.append("PSE.REMARK AS REMARK, ");
		//状态
		sql.append("CASE WHEN PSE.ACTIVE='N' THEN '已废弃' ");
		sql.append("WHEN PSE.INVALID_TIME IS NOT NULL AND PSE.INVALID_TIME < SYSDATE THEN '已过期'  ");
		sql.append("WHEN PSE.EFFECTIVE_TIME > SYSDATE THEN '待生效' ");
		sql.append("ELSE '生效中' END AS STATE, ");

		sql.append("PSE.INVALID_TIME AS INVALID_TIME, ");
		sql.append("PSE.CREATE_USER_CODE AS CREATE_USER_CODE, ");
		sql.append("PSE.CREATE_TIME AS CREATE_TIME, ");
		sql.append("PSE.MODIFY_TIME AS MODIFY_TIME, ");
		sql.append("PSE.MODIFY_USER_CODE AS MODIFY_USER_CODE ");
		sql.append("FROM T_PRICE_STANDARD PSE ");
		sql.append("LEFT JOIN T_PRICE_CITY SPCE ON PSE.SEND_PRICE_CITY=SPCE.CODE ");
		sql.append("LEFT JOIN T_PRICE_CITY APCE ON PSE.ARRIVE_PRICE_CITY=APCE.CODE ");
		sql.append("LEFT JOIN T_PRICE_SECTION SECTION ON PSE.FUEL_FEE_SECTION=SECTION.CODE AND SECTION.ACTIVE='Y' ");
		sql.append("WHERE ");
		//出发价格城市选择项
		StringBuffer filter = new StringBuffer();
		if (entity.getPriceStandardEntity().getSendPriceCity() != null
				&& !StringUtils.isEmpty(entity.getPriceStandardEntity().getSendPriceCity().getCode())) {
			filter.append("AND PSE.SEND_PRICE_CITY = ? ");
			params.add(entity.getPriceStandardEntity().getSendPriceCity().getCode());
		}
		//出发省市区县选择项目
		if (!StringUtils.isEmpty(entity.getSendProvinceCode())
				|| !StringUtils.isEmpty(entity.getSendCityCode())
				|| !StringUtils.isEmpty(entity.getSendAreaCode())) {
			filter.append("AND PSE.SEND_PRICE_CITY IN (SELECT PRICE_CITY FROM T_PRICE_CITY_MAPPING_DISTRICT WHERE ACTIVE='Y' ");
			if (!StringUtils.isEmpty(entity.getSendProvinceCode())) {
				filter.append("AND PROVINCE_CODE = ? ");
				params.add(entity.getSendProvinceCode());
			}
			if (!StringUtils.isEmpty(entity.getSendCityCode())) {
				filter.append("AND CITY_CODE = ? ");
				params.add(entity.getSendCityCode());
			}
			if (!StringUtils.isEmpty(entity.getSendAreaCode())) {
				filter.append("AND AREA_CODE = ? ");
				params.add(entity.getSendAreaCode());
			}
			filter.append("GROUP BY PRICE_CITY) ");
		}
		//到达价格城市项目
		if (entity.getPriceStandardEntity().getArrivePriceCity() != null
				&& !StringUtils.isEmpty(entity.getPriceStandardEntity().getArrivePriceCity().getCode())) {
			filter.append("AND PSE.ARRIVE_PRICE_CITY = ? ");
			params.add(entity.getPriceStandardEntity().getArrivePriceCity().getCode());
		}
		//到达省市区县项目
		if (!StringUtils.isEmpty(entity.getArriveProvinceCode())
				|| !StringUtils.isEmpty(entity.getArriveCityCode())
				|| !StringUtils.isEmpty(entity.getArriveAreaCode())) {
			filter.append("AND PSE.ARRIVE_PRICE_CITY IN (select PRICE_CITY from T_PRICE_CITY_MAPPING_DISTRICT WHERE ACTIVE='Y' ");
			if (!StringUtils.isEmpty(entity.getArriveProvinceCode())) {
				filter.append("AND PROVINCE_CODE = ? ");
				params.add(entity.getArriveProvinceCode());
			}
			if (!StringUtils.isEmpty(entity.getArriveCityCode())) {
				filter.append("AND CITY_CODE = ? ");
				params.add(entity.getArriveCityCode());
			}
			if (!StringUtils.isEmpty(entity.getArriveAreaCode())) {
				filter.append("AND AREA_CODE = ? ");
				params.add(entity.getArriveAreaCode());
			}
			filter.append("GROUP BY PRICE_CITY) ");
		}
		//状态
		if (StringUtils.isEmpty(entity.getPriceStandardEntity().getState())) {
			filter.append("AND PSE.ACTIVE = 'Y' ");
		} else if ("PASSED".equals(entity.getPriceStandardEntity().getState())) {
			filter.append("AND SYSDATE>PSE.INVALID_TIME AND PSE.ACTIVE='Y' ");
		} else if ("EFFECTIVE".equals(entity.getPriceStandardEntity().getState())) {
			filter.append("AND (SYSDATE>=PSE.EFFECTIVE_TIME AND NVL(PSE.INVALID_TIME,TO_DATE('2999-12-31 23:59:59', 'yyyy-mm-dd hh24:mi:ss'))>=SYSDATE ");
			filter.append("AND PSE.ACTIVE='Y' )");
		} else if ("WAIT".equals(entity.getPriceStandardEntity().getState())) {
			filter.append("AND SYSDATE<=PSE.EFFECTIVE_TIME AND PSE.ACTIVE='Y' ");
		} else {
			filter.append("AND PSE.active='N' ");
		}
		//产品类别
		if (!StringUtils.isEmpty(entity.getPriceStandardEntity().getTransTypeCode())) {
			filter.append("AND PSE.TRANS_TYPE_CODE = ? ");
			params.add(entity.getPriceStandardEntity().getTransTypeCode());
		}
		//生效时间点
		if (!StringUtils.isEmpty(entity.getEffectiveTime())) {
			filter.append("AND PSE.EFFECTIVE_TIME <= TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') ");
			params.add(entity.getEffectiveTime());
			filter.append("AND NVL(PSE.INVALID_TIME,TO_DATE('2999-12-31 23:59:59', 'yyyy-mm-dd hh24:mi:ss'))>=TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') ");
			params.add(entity.getEffectiveTime());
		}
		//处理filter开头的AND
		if (filter.toString().startsWith("AND")) {
			sql.append(filter.substring(3));
		} else {
			sql.append(filter);
		}
		ExcelExportResultEntity resultEntity = new ExcelUtil<ExcelPriceStandardEntity>().sqlTofile(titleNames, sql.toString(), params);
		return resultEntity;
	}
	
	private Date searchCurrentDateTime(){
		return priceStandardDao.searchCurrentDateTime();
	}

}

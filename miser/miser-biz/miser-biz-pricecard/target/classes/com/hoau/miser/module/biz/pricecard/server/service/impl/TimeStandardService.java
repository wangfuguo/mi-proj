package com.hoau.miser.module.biz.pricecard.server.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.base.api.server.service.ITranstypeService;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;
import com.hoau.miser.module.biz.pricecard.api.server.service.ITimeStandartService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.TimeStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.ExcelTimeStandVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.TimeStandardVo;
import com.hoau.miser.module.biz.pricecard.server.dao.TimeStandardDao;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.DateUtils;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

@Service
public class TimeStandardService implements ITimeStandartService {

	private static final Logger log = LoggerFactory.getLogger(TimeStandardService.class);
	
	@Resource
	private ITranstypeService transtypeService;
	
	@Resource
	private TimeStandardDao timeStandardDao;
	
	
	@Override
	public List<TimeStandardEntity> queryListByParam(TimeStandardVo psv,
			int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return timeStandardDao.queryListByParam(psv,rowBounds);
	}

	@Override
	public Long queryCountByParam(TimeStandardVo psv) {
		return timeStandardDao.queryCountByParam(psv);
	}

	@Override
	public Integer addTimeStandard(TimeStandardEntity tse,boolean isConfirm) {
		tse.setActive(MiserConstants.YES);
		return this.addOrUpdateTimeStandard(tse,isConfirm);
	}

	@Override
	@Transactional
	public Integer addOrUpdateTimeStandard(TimeStandardEntity tse,
			boolean isConfirm) {
		//默认成功
			Integer result=1;
			Long minuteDiff2= DateUtils.getMinuteDiff(tse.getEffectiveTime(),new Date());
			if(minuteDiff2>0){
				result=-1;
				//起始时间要大于当前时间
				return result;
			}
			//查询当前信息状态,如果状态不符合修改条件，给用户以提示
			if(!StringUtils.isEmpty(tse.getId())){
				TimeStandardEntity selfEntity=this.queryTimeStandardById(tse.getId());
						
				if(selfEntity.getActive().equals(MiserConstants.NO)||!(TimeStandardEntity.STATE_2.equals(selfEntity.getState())||TimeStandardEntity.STATE_3.equals(selfEntity.getState()))){
					result=2;
					return result;
				}
			}
			
			//查询历史有效信息
			TimeStandardEntity queryEntity=new TimeStandardEntity();
			queryEntity.setActive(MiserConstants.YES);
			queryEntity.setTransTypeCode(tse.getTransTypeCode());
			queryEntity.setSendTimeCity(tse.getSendTimeCity());
			queryEntity.setArriveTimeCity(tse.getArriveTimeCity());
			queryEntity.setState(TimeStandardEntity.STATE_2);
			TimeStandardVo queryVo1=new TimeStandardVo();
			queryVo1.setTimeStandardEntity(queryEntity);
			//查询生效中的信息
			List<TimeStandardEntity> state2List= this.queryListByParam(queryVo1, 1, 0);
			//查询待生效的信息
			queryEntity.setState(TimeStandardEntity.STATE_3);
			List<TimeStandardEntity> state3List= this.queryListByParam(queryVo1, 1, 0);
			if(state3List!=null&&state3List.size()>0){//存在待生效信息
				
				if(isConfirm){//确认新增
					//将旧的待生效信息作废
					TimeStandardEntity state3Entity=state3List.get(0);
					state3Entity.setActive(MiserConstants.NO);
					beforOperDeal(state3Entity);
					timeStandardDao.updateByPrimaryKeySelective(state3Entity);
					
				}else{//表示需要确认是否新增
					
					result=0;
					return result;
				}
			}
			
			//新增待生效信息
			tse.setId(null);
			beforOperDeal(tse);
			timeStandardDao.insertSelective(tse);
			
			// 将生效中的信息的失效时间赋值为待生效的生效时间
			if(state2List!=null&&state2List.size()>0){
				TimeStandardEntity state2Entity=state2List.get(0);
				state2Entity.setId(state2Entity.getId());
				beforOperDeal(state2Entity);
				state2Entity.setInvalidTime(tse.getEffectiveTime());
				timeStandardDao.updateByPrimaryKeySelective(state2Entity);
			}
			
			return result;
				
	}

	@Override
	public TimeStandardEntity queryTimeStandardById(String id) {
		TimeStandardVo psv=new TimeStandardVo();
		TimeStandardEntity pce=new TimeStandardEntity();
		pce.setId(id);
		psv.setTimeStandardEntity(pce);
		List<TimeStandardEntity> list=this.queryListByParam(psv,1,0);
		return list!=null&&list.size()>0?list.get(0):null;
	}

	private void beforOperDealForThread(TimeStandardEntity tse,String userName,Date dt) {
		if(StringUtil.isEmpty(tse.getId())){//新增的话
			tse.setId(UUIDUtil.getUUID());
			tse.setActive(MiserConstants.YES);
			tse.setCreateTime(dt);
			tse.setCreateUserCode(userName);
		}
		tse.setModifyTime(dt);
		tse.setModifyUserCode(userName);
	}
	
	private void beforOperDeal(TimeStandardEntity tse) {
		Date dt = new Date();
		if(StringUtil.isEmpty(tse.getId())){//新增的话
			tse.setId(UUIDUtil.getUUID());
			tse.setActive(MiserConstants.YES);
			tse.setCreateTime(dt);
			tse.setCreateUserCode(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		}
		tse.setModifyTime(dt);
		tse.setModifyUserCode(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
	}

	@Override
	public Integer updateTimeStandard(TimeStandardEntity tse, boolean isConfirm) {
		return this.addOrUpdateTimeStandard(tse,isConfirm);
	}

	@Override
	public Integer bathDelTimeStandard(List<TimeStandardEntity> timeStandardList) {
		int result=-1;
		for(TimeStandardEntity pse:timeStandardList){
			result=this.delTimeStandard(pse);
			if(result==2){//状态发生变化
				return result;
			}
		}
		return result;
	}

	@Transactional
	@Override
	public Integer delTimeStandard(TimeStandardEntity tse) {
		int result=0;
		//pse.setRemark(MiserUserContext.getCurrentUser().getEmpNameAndUserName()+" 于 "+DateUtils.convert(new Date())+" 手动删除");
		//查询当前信息状态,如果状态不符合修改条件，给用户以提示
		TimeStandardEntity selfEntity  =  this.queryTimeStandardById(tse.getId());
		if (selfEntity.getActive().equals(MiserConstants.NO)
				|| !(TimeStandardEntity.STATE_2.equals(selfEntity.getState()) || TimeStandardEntity.STATE_3
						.equals(selfEntity.getState()))) {
			result = 2;
			return result;
		}
		if(TimeStandardEntity.STATE_3.equals(tse.getState())){//待生效数据
			beforOperDeal(tse);
			tse.setActive(MiserConstants.NO);
			timeStandardDao.updateByPrimaryKeySelective(tse);
			//生效信息中的失效时间设为null
			TimeStandardEntity queryEntity=new TimeStandardEntity();
			queryEntity.setActive(MiserConstants.YES);
			queryEntity.setTransTypeCode(tse.getTransTypeCode());
			queryEntity.setSendTimeCity(tse.getSendTimeCity());
			queryEntity.setArriveTimeCity(tse.getArriveTimeCity());
			queryEntity.setState(TimeStandardEntity.STATE_2);
			TimeStandardVo queryVo1=new TimeStandardVo();
			queryVo1.setTimeStandardEntity(queryEntity);
			//查询生效中的信息
			List<TimeStandardEntity> state2List= this.queryListByParam(queryVo1, 1, 0);

			// 将生效中的信息的失效时间赋值为待生效的生效时间
			if(state2List!=null&&state2List.size()>0){
				TimeStandardEntity state2Entity=state2List.get(0);
				beforOperDeal(state2Entity);
				state2Entity.setState("setNullInvalidTime");
				state2Entity.setInvalidTime(null);
				timeStandardDao.updateByPrimaryKeySelective(state2Entity);
			}
		}else if(TimeStandardEntity.STATE_2.equals(tse.getState())){//生效中
			beforOperDeal(tse);
			tse.setInvalidTime(new Date());
			timeStandardDao.updateByPrimaryKeySelective(tse);
			
		}
		result=1;
		return result;
		
	}

	@Override
	public ExcelExportResultEntity createExcelFile(TimeStandardVo timeStandardVo) throws IOException {
		
		if (timeStandardVo == null || timeStandardVo.getTimeStandardEntity() == null) {
			return null;
		}
		//表头
		List<String> titleNames = new ArrayList<String>();
		//导出前几列与导入模板相同
		titleNames.add("运输类型");
		titleNames.add("出发时效城市");
		titleNames.add("达到时效城市");
		titleNames.add("最短通知天数");
		titleNames.add("最长通知天数");
		titleNames.add("自提承若天数");
		titleNames.add("送货承若天数");
		titleNames.add("生效时间");
		titleNames.add("失效时间");
		titleNames.add("备注");

		titleNames.add("当前状态");
		titleNames.add("是否有效");
		titleNames.add("创建人");
		titleNames.add("创建时间");
		titleNames.add("修改人");
		titleNames.add("修改时间");
		
		List<String> paramNames = new ArrayList<String>();
		paramNames.add("transTypeName");
		paramNames.add("sendTimeCityName");
		paramNames.add("arriveTimeCityName");
		paramNames.add("minNotifyDay");
		paramNames.add("maxNotifyDay");
		paramNames.add("pickupPromDay");
		paramNames.add("deliveryPromDay");
		paramNames.add("effectiveTime");
		paramNames.add("invalidTime");
		paramNames.add("remark");
		
		paramNames.add("stateName");
		paramNames.add("activeName");
		paramNames.add("createUserCode");
		paramNames.add("createTime");
		paramNames.add("modifyUserCode");
		paramNames.add("modifyTime");
		
		List<TimeStandardEntity> list = timeStandardDao.queryListByParam(timeStandardVo);
		
        ExcelExportResultEntity resultEntity = new ExcelExportResultEntity(); 
        resultEntity.setFilePath(new ExcelUtil<TimeStandardEntity>().exportExcel(titleNames,paramNames,list));
        resultEntity.setRecordCount(list.size());
		return resultEntity;
	}

	@Transactional
	@Override
	public Map<String, Object> bathImplTimeStandards(String uploadPath) throws Exception {
		Map<String,Object> retuMap=new HashMap<String, Object>();
		List<Map<String, String>> list;
		Date nowDate =  timeStandardDao.searchCurrentDateTime();
		List<TimeStandardEntity> pseList = new ArrayList<TimeStandardEntity>();
		list = ExcelUtil.readToListByFile(uploadPath, 10, 2);
		//错误的信息
		List<ExcelTimeStandVo> errorEpsList=new ArrayList<ExcelTimeStandVo>();
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
		
        //缓存出发到达价格城市名称对应编码
		Map<String,Object> maps = new HashMap<String, Object>();
		Map<String,Object> smaps = new HashMap<String, Object>();
		maps.put("type", TimeStandardEntity.SEND);
		List<Map<String, Object>> sendPriceCityNameAndCodes = timeStandardDao.queryTimeCity(maps);
		for (Map<String, Object> map : sendPriceCityNameAndCodes) {
			smaps.put(map.get("NAME").toString(),  map.get("CODE"));
		}
		
		
		Map<String,Object> mapa= new HashMap<String, Object>();
		Map<String,Object> amapa= new HashMap<String, Object>();
		mapa.put("type", TimeStandardEntity.ARRIVAL);
		List<Map<String, Object>> arrivalPriceCityNameAndCodes = timeStandardDao.queryTimeCity(mapa);
		for (Map<String, Object> map : arrivalPriceCityNameAndCodes) {
			amapa.put(map.get("NAME").toString(),  map.get("CODE"));
		}
		
		int temp = 1;
		for (Map<String, String> map : list) {
			temp ++;
			TimeStandardEntity tse = new TimeStandardEntity();
			try {
				
                //根据名称从接口获取出发价格城市编码
                String sendTimeCityName = map.get("1") == null ? "" : map.get("1").trim();
                if(smaps.containsKey(sendTimeCityName)){
                	 tse.setSendTimeCity(smaps.get(sendTimeCityName).toString());
                     tse.setSendTimeCityName(sendTimeCityName);
                }else{
                	 throw new Exception("出发时效城市["+ sendTimeCityName +"]不存在");
                }
                
                //根据名称从接口获取到达价格城市编码
                String arrivalTimeCityName = map.get("2") == null ? "" : map.get("2").trim();
                if (amapa.containsKey(arrivalTimeCityName)) {
                	  tse.setArriveTimeCity(amapa.get(arrivalTimeCityName).toString());
                      tse.setArriveTimeCityName(arrivalTimeCityName);
				}else {
                    throw new Exception("到达价格城市["+ arrivalTimeCityName +"]不存在");
                }
                
                String transTypeName = map.get(0+"")== null? "": map.get(0+"").trim();
                if(transTypeCodeMap.containsKey(transTypeName)){
                	  tse.setTransTypeCode(transTypeCodeMap.get(transTypeName));
                	  tse.setTransTypeName(transTypeName);
                }else{
                	throw new Exception("运输类型["+ transTypeName +"]不存在");
                }
               
                try{
                	tse.setMinNotifyDay(new BigDecimal(map.get(3+"")));
                }catch(Exception e){
                	throw new Exception("最短通知天数["+ map.get(3+"") +"]格式不正确,只能是数字类型");
                }
                try{
                	tse.setMaxNotifyDay(new BigDecimal(map.get(4+"")));
                }catch(Exception e){
                	throw new Exception("最长通知天数["+ map.get(4+"") +"]格式不正确,只能是数字类型");
                }
				tse.setPickupPromDay(StringUtil.trim(map.get(5+"")));
				try{
					tse.setDeliveryPromDay(new BigDecimal(map.get(6+"")));
				}catch(Exception e){
					throw new Exception("送货承诺天数["+ map.get(6+"") +"]格式不正确,只能是数字类型");
				}
				//导入时可能 会出现没有生效时间则把生效时间赋值为当前时间
				if(StringUtils.isEmptyWithBlock(map.get(7+""))){
					tse.setEffectiveTime(nowDate);
				}else{
					tse.setEffectiveTime(DateUtils.convert(StringUtil.trim(map.get(7+""))));
					if(tse.getEffectiveTime() == null){
						throw new Exception("生效时间["+ map.get(7+"") +"]不能被转为日期类型");
					}
				}
				if(!StringUtils.isEmptyWithBlock(map.get(8+""))){
					tse.setInvalidTime(DateUtils.convert(StringUtil.trim(map.get(8+""))));
					if(tse.getInvalidTime()==null){
						throw new Exception("失效时间["+ map.get(8+"") +"]不能被转为日期类型");
					}
				}
				tse.setRemark(map.get(9+""));
				tse.setCreateTime(nowDate);
				tse.setCreateUserCode(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
				tse.setModifyTime(nowDate);
				tse.setModifyUserCode(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
				tse.setActive("Y");
				} catch (Exception e) {
					
					ExcelTimeStandVo eps=new ExcelTimeStandVo();
					eps.setTransTypeName(map.get("0"));
					eps.setSendTimeCityName(map.get("1"));
					eps.setArriveTimeCityName(map.get("2"));
					eps.setMinNotifyDay(map.get("3"));
					eps.setMaxNotifyDay(map.get("4"));
					eps.setPickupPromDay(map.get("5"));
					eps.setDeliveryPromDay(map.get("6"));
					eps.setEffectiveTime(map.get("7"));
					eps.setInvalidTime(map.get("8"));
					eps.setRemark(map.get("9"));
					eps.setOldSerial(temp+"");
					eps.setErrorMess(e.getMessage());
					errorEpsList.add(eps);
					
					tse = null;// 如果有异常就把pse设为null,这样每条信息都加进去了
					log.error("批量导入模版数据异常，业务需要仅作提示", e);
			} finally {
				pseList.add(tse);
			}
		}
		
		
		Hashtable<String, Long> countMap = new Hashtable<String, Long>();
		countMap.put("addSize", new Long(0));// 增加条数
		countMap.put("coverSize", new Long(0));// 覆盖条数
		countMap.put("errorSize",new Long(0));//错误的条数
		
		//错误的信息
		Vector<ExcelTimeStandVo> errorList=new Vector<ExcelTimeStandVo>();
		createThread(pseList,countMap,list,errorList,nowDate);
		log.info("***************************时效价格导入完成");
		
		String filePath="";
		errorEpsList.addAll(errorList);
		
		retuMap.put("addSize", countMap.get("addSize"));
		retuMap.put("coverSize", countMap.get("coverSize"));
		retuMap.put("sumSize", list.size());
		retuMap.put("errorSize", errorEpsList.size());
		if(errorEpsList.size()>0){
			
			 List<String> titleNames=new ArrayList<String>();
			 titleNames.add("运输类型");
			 titleNames.add("出发时效城市");
			 titleNames.add("到达时效城市");
			 titleNames.add("最短通知天数");
			 titleNames.add("最长通知天数");
			 titleNames.add("自提货承若天数");
			 titleNames.add("送货承若天数");
			 titleNames.add("生效时间");
			 titleNames.add("失效时间");
			 titleNames.add("备注");
			 titleNames.add("旧序列号");
			 titleNames.add("异常信息");
			List<String> paramNames=new ArrayList<String>();
			paramNames.add("transTypeName");
			paramNames.add("sendTimeCityName");
			paramNames.add("arriveTimeCityName");
			paramNames.add("minNotifyDay");
			paramNames.add("maxNotifyDay");
			paramNames.add("pickupPromDay");
			paramNames.add("deliveryPromDay");
			paramNames.add("effectiveTime");
			paramNames.add("invalidTime");
			paramNames.add("remark");
			paramNames.add("oldSerial");
			paramNames.add("errorMess");
			filePath=new ExcelUtil<ExcelTimeStandVo>().exportExcel(titleNames,paramNames,errorEpsList);
		}
		retuMap.put("filePath", filePath);
		return retuMap;
	}

	private void createThread(List<TimeStandardEntity> pseList,
			Map<String,Long> countMap,
			List<Map<String, String>> excelmap,
			List<ExcelTimeStandVo> errorEpsList,Date dt) throws InterruptedException{
		
		List<TimeStandardEntity> thread0 = new ArrayList<TimeStandardEntity>();
		List<TimeStandardEntity> thread1 = new ArrayList<TimeStandardEntity>();
		List<TimeStandardEntity> thread2 = new ArrayList<TimeStandardEntity>();
		List<TimeStandardEntity> thread3 = new ArrayList<TimeStandardEntity>();
		List<TimeStandardEntity> thread4 = new ArrayList<TimeStandardEntity>();
		List<TimeStandardEntity> thread5 = new ArrayList<TimeStandardEntity>();
		List<TimeStandardEntity> thread6 = new ArrayList<TimeStandardEntity>();
		List<TimeStandardEntity> thread7 = new ArrayList<TimeStandardEntity>();
		List<TimeStandardEntity> thread8 = new ArrayList<TimeStandardEntity>();
		List<TimeStandardEntity> thread9 = new ArrayList<TimeStandardEntity>();
		
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
		public ImplThread(List<TimeStandardEntity> list,
				int current,
				List<ExcelTimeStandVo> errorList,
				List<Map<String, String>> excelmapl,
				Map<String,Long> countMap,
				TimeStandardService timeStandardService,
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
			this.timeStandardService = timeStandardService;
			this.userCode = userCode;
		}
		
		private Date dt;
		private CountDownLatch latch;
		private List<TimeStandardEntity> list;
		//当前序列号
		private int current = 0;
		//错误List
		private List<ExcelTimeStandVo> errorList;
		// exlce
		private List<Map<String, String>> excelmapl;
		//统计 map
		private Map<String,Long> countMap;
		//执行Service
		private TimeStandardService timeStandardService;
		
		private String userCode;

		public void run() {
			Map<String,Long> countMapt = new Hashtable<String, Long>();
			countMapt.put("addSize", new Long(0));// 增加条数
			countMapt.put("coverSize", new Long(0));// 覆盖条数
			countMapt.put("errorSize",new Long(0));//错误的条数
			List<ExcelTimeStandVo> excelList = new ArrayList<ExcelTimeStandVo>();
			for(int i = 0; i< list.size(); i++){
				TimeStandardEntity pse = list.get(i);
				Map<String, String> impleMap = excelmapl.get((10*i) + current);
				if(pse == null){
					countMapt.put("errorSize",countMapt.get("errorSize")+1);
					continue;
				}
				try {
					timeStandardService.addOrUpdateTimeStandardForThread(pse, countMapt,userCode,dt);
				} catch (Exception e) {
					countMapt.put("errorSize",countMapt.get("errorSize")+1);
					log.error("批量导入模版数据更新操作异常，业务需要仅作提示", e);
					
					/**
					 * 把错误信息收集起来，将来导给用户看
					 */
					ExcelTimeStandVo eps=new ExcelTimeStandVo();
					eps.setTransTypeName(impleMap.get("0"));
					eps.setSendTimeCityName(impleMap.get("1"));
					eps.setArriveTimeCityName(impleMap.get("2"));
					eps.setMinNotifyDay(impleMap.get("3"));
					eps.setMaxNotifyDay(impleMap.get("4"));
					eps.setPickupPromDay(impleMap.get("5"));
					eps.setDeliveryPromDay(impleMap.get("6"));
					eps.setEffectiveTime(impleMap.get("7"));
					eps.setInvalidTime(impleMap.get("8"));
					eps.setRemark(impleMap.get("9"));
					eps.setOldSerial((10*i) + current+"");
					eps.setErrorMess(e.getMessage());
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
	
	private void addOrUpdateTimeStandardForThread(TimeStandardEntity tse,Map<String,Long> countMap,String userName,Date dt) throws Exception{
		
		long addSize= countMap.get("addSize").longValue();
		long coverSize= countMap.get("coverSize").longValue();
		//查询历史有效信息
		TimeStandardEntity queryEntity=new TimeStandardEntity();
		queryEntity.setTransTypeCode(tse.getTransTypeCode());
		queryEntity.setSendTimeCity(tse.getSendTimeCity());
		queryEntity.setArriveTimeCity(tse.getArriveTimeCity());
		
		TimeStandardVo queryVo1=new TimeStandardVo();
		queryVo1.setTimeStandardEntity(queryEntity);
		List<TimeStandardEntity> list = queryListByParam(queryVo1,1,0);
		if(list.size()>0){
			for (TimeStandardEntity timeStandardEntity : list) {
				if(timeStandardEntity.getState().equals(TimeStandardEntity.STATE_2)){
					beforOperDealForThread(tse, userName, dt);
					timeStandardEntity.setInvalidTime(tse.getEffectiveTime());
					timeStandardDao.updateByPrimaryKeySelective(timeStandardEntity);
					addSize++;//增加 当前生效的只能是一条
				}else if(timeStandardEntity.getState().equals(TimeStandardEntity.STATE_3)){
					timeStandardEntity.setActive(MiserConstants.NO);
					beforOperDealForThread(tse, userName, dt);
					timeStandardDao.updateByPrimaryKeySelective(timeStandardEntity);
					coverSize++;//替换
				}
			}
		}else{
			addSize++;//增加 当前生效的只能是一条
		}
		beforOperDealForThread(tse, userName, dt);
		timeStandardDao.insertSelective(tse);
		countMap.put("addSize", addSize);
		countMap.put("coverSize", coverSize);
	}
	
	
}

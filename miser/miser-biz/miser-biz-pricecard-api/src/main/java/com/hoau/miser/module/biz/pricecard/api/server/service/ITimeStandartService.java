package com.hoau.miser.module.biz.pricecard.api.server.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.TimeStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.TimeStandardVo;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;

public interface ITimeStandartService {
	
	public List<TimeStandardEntity> queryListByParam(TimeStandardVo psv, int limit, int start);
	
	public Long queryCountByParam(TimeStandardVo psv);
	
	public Integer addTimeStandard(TimeStandardEntity tse,boolean isConfirm);
	
	public Integer addOrUpdateTimeStandard(TimeStandardEntity tse,boolean isConfirm);
	
	public TimeStandardEntity queryTimeStandardById(String id);
	
	
	public Integer updateTimeStandard(TimeStandardEntity tse,boolean isConfirm);
	
	public Integer bathDelTimeStandard(List<TimeStandardEntity> timeStandardList);
	
	public Integer delTimeStandard(TimeStandardEntity tse);
	
	public ExcelExportResultEntity createExcelFile(TimeStandardVo timeStandardVo)  throws IOException;
	
	public Map<String,Object> bathImplTimeStandards(String uploadPath) throws Exception;
}

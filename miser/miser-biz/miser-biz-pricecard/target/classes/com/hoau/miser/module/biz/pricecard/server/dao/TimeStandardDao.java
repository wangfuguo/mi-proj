package com.hoau.miser.module.biz.pricecard.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;



import org.apache.ibatis.session.RowBounds;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.TimeStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.TimeStandardVo;


public interface TimeStandardDao {
	
	List<TimeStandardEntity> queryListByParam(TimeStandardVo psv, RowBounds rowBounds);
	
	List<TimeStandardEntity> queryListByParam(TimeStandardVo psv);
	
	public Long queryCountByParam(TimeStandardVo psv);
	
    int deleteByPrimaryKey(String id);

    int insert(TimeStandardEntity record);

    int insertSelective(TimeStandardEntity record);

    TimeStandardEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TimeStandardEntity record);

    int updateByPrimaryKey(TimeStandardEntity record);
    
    List<Map<String,Object>> queryTimeCity(Map<String,Object> map);
    
    Date searchCurrentDateTime();
    
}
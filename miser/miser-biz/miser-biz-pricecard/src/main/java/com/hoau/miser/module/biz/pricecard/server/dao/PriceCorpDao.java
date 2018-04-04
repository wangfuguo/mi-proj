package com.hoau.miser.module.biz.pricecard.server.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCorpEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpGirdVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpSearchFormVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpVo;

@Repository
public interface PriceCorpDao {
    int deleteByPrimaryKey(String id);
    
    int cancelByPrimaryKey(PriceCorpGirdVo pcgv);
    
    int cancelByLJPrimary(PriceCorpGirdVo pcgv);
    
    int updateByLJPrimaryForInsert(PriceCorpGirdVo pcgv);
    
    int insert(PriceCorpEntity record);

    int insertSelective(PriceCorpEntity record);

    int insertBatch(PriceCorpVo pcv);
    
    PriceCorpEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PriceCorpEntity record);

    int updateByPrimaryKey(PriceCorpEntity record);
    
    Date queryCurrentDateTime();
    
    List<PriceCorpGirdVo> queryBySearch(PriceCorpSearchFormVo pcsfv,RowBounds rowBounds);
    
    long queryBySearchCount(PriceCorpSearchFormVo pcsfv);
    
    List<PriceCorpGirdVo> findByCorpDetail(PriceCorpGirdVo vo);
    
    Integer checkIsCanSave(PriceCorpGirdVo pcgv);
    
    List<PriceCorpGirdVo> queryListCorp(PriceCorpGirdVo pcgv);
}
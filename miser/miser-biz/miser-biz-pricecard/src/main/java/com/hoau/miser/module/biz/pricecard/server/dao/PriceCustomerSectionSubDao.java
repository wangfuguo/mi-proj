package com.hoau.miser.module.biz.pricecard.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerSectionSubVo;


/**
 * 易入户客户专属价格数据接入
 * ClassName: PriceCustomerSectionSubDao 
 * @author 何羽
 * @date 2016年5月5日
 * @version V2.0   
 */
@Repository
public interface PriceCustomerSectionSubDao {

    int insert(PriceCustomerSectionSubEntity priceCustomerSectionSubEntity);

    int insertSelective(PriceCustomerSectionSubEntity priceCustomerSubEntity);

    PriceCustomerSectionSubEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PriceCustomerSectionSubEntity priceCustomerSubEntity);

    int updateByPrimaryKey(PriceCustomerSectionSubEntity priceCustomerSubEntity);

	List<PriceCustomerSectionSubEntity> selectCustomerSubListByParam(PriceCustomerSectionSubEntity priceCustomerSubEntity, RowBounds rowBounds);
	
	int batchUpdateActiveByIdStr(PriceCustomerSectionSubVo priceCustomerSectionSubVo);
	
	int batchInsertCustomerSub(List<PriceCustomerSectionSubEntity> priceCustomerSectionSubEntityList);

	void batchUpdateForInvalidByParentId(Map<String, String> paramMap);

    /**
     * @Description: 根据勾选的 客户专属记录的 id 拼接串 
     * 更新明细表的 active=N modifyUserCode=currentUser modifyTime=sysdate
     * @param @param paramMap[modifyUserCode=currentUser destoryIdStr=destoryIdStr]   
     * @return void 
     * @throws
     * @author dengyin
     * @date 2016年1月14日
     */
	void destoryPriceCustomerSubByParentIdStr(Map<String, Object> paramMap);
	
	void batchUpdateForEffectiveByParentId(Map<String,Object> paramMap);
	
	List<PriceCustomerSectionSubEntity> listCustomerSubListByParam(PriceCustomerSectionSubEntity priceCustomerSectionSubEntity);


}

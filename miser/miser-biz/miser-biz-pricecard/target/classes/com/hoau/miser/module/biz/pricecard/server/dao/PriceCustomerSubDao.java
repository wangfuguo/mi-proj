/**   
* @Title: PriceCustomerDao.java 
* @Package com.hoau.miser.module.biz.pricecard.server.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dengyin
* @date 2016年1月5日 下午1:37:19 
* @version V1.0   
*/
package com.hoau.miser.module.biz.pricecard.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerSubVo;

/**
 * 客户专属价格数据接入
 * ClassName: PriceCustomerDao 
 * @author dengyin
 * @date 2016年1月5日
 * @version V1.0   
 */
@Repository
public interface PriceCustomerSubDao {

    int insert(PriceCustomerSubEntity priceCustomerSubEntity);

    int insertSelective(PriceCustomerSubEntity priceCustomerSubEntity);

    PriceCustomerSubEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PriceCustomerSubEntity priceCustomerSubEntity);

    int updateByPrimaryKey(PriceCustomerSubEntity priceCustomerSubEntity);

	List<PriceCustomerSubEntity> selectCustomerSubListByParam(PriceCustomerSubEntity priceCustomerSubEntity, RowBounds rowBounds);
	
	int batchUpdateActiveByIdStr(PriceCustomerSubVo priceCustomerSubVo);
	
	int batchInsertCustomerSub(List<PriceCustomerSubEntity> priceCustomerSubEntityList);

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
	
	List<PriceCustomerSubEntity> listCustomerSubListByParam(PriceCustomerSubEntity priceCustomerSubEntity);
}

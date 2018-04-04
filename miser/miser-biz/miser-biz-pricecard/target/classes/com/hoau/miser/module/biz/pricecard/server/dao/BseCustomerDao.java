/**   
* @Title: BseCustomerDao.java 
* @Package com.hoau.miser.module.biz.pricecard.server.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dengyin
* @date 2016年1月5日 下午2:38:28 
* @version V1.0   
*/
package com.hoau.miser.module.biz.pricecard.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.BseCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.BseCustomerVo;

/**
 * 客户信息数据库层接入
 * ClassName: BseCustomerDao 
 * @author dengyin
 * @date 2016年1月5日
 * @version V1.0   
 */
@Repository
public interface BseCustomerDao {

    BseCustomerEntity selectByPrimaryKey(String id);
    
    List<BseCustomerEntity> selectCustomerAllList(BseCustomerVo bseCustomerVo,RowBounds rowBounds);
    
    List<BseCustomerEntity> queryBseCustomerByParam(BseCustomerVo bseCustomerVo, RowBounds rowBounds);

	Long queryCountByParam(BseCustomerVo bseCustomerVo);
	
	void updateFromOaPmsInterface(BseCustomerEntity bseCustomerEntity);
 
}

/**   
 * @Title: BseCustomerService.java 
 * @Package com.hoau.miser.module.biz.pricecard.api.server.service.impl 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月7日 上午9:21:52 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.miser.module.biz.pricecard.api.server.service.IBseCustomerService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.BseCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.BseCustomerVo;
import com.hoau.miser.module.biz.pricecard.server.dao.BseCustomerDao;

/**
 * ClassName: BseCustomerService
 * 
 * @author dengyin
 * @date 2016年1月7日
 * @version V1.0
 */
@Service
public class BseCustomerService implements IBseCustomerService {

	@Resource
	private BseCustomerDao bseCustomerDao;

	public BseCustomerEntity selectByPrimaryKey(String id) {
		return bseCustomerDao.selectByPrimaryKey(id);
	}

	public List<BseCustomerEntity> selectCustomerAllList(
			BseCustomerVo bseCustomerVo, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return bseCustomerDao.selectCustomerAllList(bseCustomerVo, rowBounds);
	}

	@Override
	public List<BseCustomerEntity> queryBseCustomerByParam(BseCustomerVo bseCustomerVo, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return bseCustomerDao.queryBseCustomerByParam(bseCustomerVo, rowBounds);
	}

	@Override
	public Long queryCountByParam(BseCustomerVo bseCustomerVo) {
		return bseCustomerDao.queryCountByParam(bseCustomerVo);
	}
	
	@Override
	public void updateFromOaPmsInterface(BseCustomerEntity bseCustomerEntity) {
		bseCustomerDao.updateFromOaPmsInterface(bseCustomerEntity);
	}

}

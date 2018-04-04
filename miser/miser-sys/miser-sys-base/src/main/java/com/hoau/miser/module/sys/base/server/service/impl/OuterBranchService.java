package com.hoau.miser.module.sys.base.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hoau.miser.module.common.shared.util.RestTemplateClient;
import com.hoau.miser.module.sys.base.api.server.service.IOuterBranchService;
import com.hoau.miser.module.sys.base.api.shared.domain.OuterBranchEntity;
import com.hoau.miser.module.sys.base.server.dao.OuterBranchDao;

/**
 * @author：涂婷婷
 * @create：2015年9月10日 下午8:31:29
 * @description：
 */
@Service
public class OuterBranchService implements IOuterBranchService {
	private static final Logger log = LoggerFactory.getLogger(OuterBranchService.class);
	@Resource
	private OuterBranchDao outerBranchDao;

	public List<OuterBranchEntity> queryOuterBranchList(OuterBranchEntity outerBranchEntity, int limit, int start) {
		RowBounds rowBounds =new RowBounds(start,limit);
		return outerBranchDao.queryOuterBranchList(outerBranchEntity,rowBounds);
	}
	
	public Long queryOuterBranchCount(OuterBranchEntity outerBranchEntity) {
		return outerBranchDao.queryOuterBranchCount(outerBranchEntity);
	}
	

}

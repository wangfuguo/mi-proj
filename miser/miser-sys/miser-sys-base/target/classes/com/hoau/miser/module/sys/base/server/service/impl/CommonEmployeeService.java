package com.hoau.miser.module.sys.base.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.miser.module.sys.base.api.server.service.ICommonEmployeeService;
import com.hoau.miser.module.sys.base.api.shared.domain.CommonEmployeeEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.EmployeeSearchConditionEntity;
import com.hoau.miser.module.sys.base.server.dao.CommonEmployeeDao;

/**
 * @author：李旭锋
 * @create：2015年7月22日 上午11:32:32
 * @description：
 */
@Service
public class CommonEmployeeService implements ICommonEmployeeService {

	@Resource
	private CommonEmployeeDao commonEmployeeDao;

	@Override
	public List<CommonEmployeeEntity> queryEmployeeByParam(
			EmployeeSearchConditionEntity employeeSearchConditionEntity) {
		RowBounds rowBounds = new RowBounds(
				employeeSearchConditionEntity.getStart(),
				employeeSearchConditionEntity.getLimit());
		return commonEmployeeDao.queryEmployeeByParam(
				employeeSearchConditionEntity, rowBounds);
	}

	@Override
	public Long queryCountEmployeeByParam(
			EmployeeSearchConditionEntity employeeSearchConditionEntity) {
		return commonEmployeeDao.queryCountEmployeeByParam(employeeSearchConditionEntity);
	}

}

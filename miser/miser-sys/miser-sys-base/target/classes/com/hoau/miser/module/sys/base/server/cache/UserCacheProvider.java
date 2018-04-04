package com.hoau.miser.module.sys.base.server.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.hbdp.framework.entity.IUser;
import com.hoau.miser.module.sys.base.api.shared.domain.EmployeeEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.UserEntity;
import com.hoau.miser.module.sys.base.api.shared.exception.UserException;
import com.hoau.miser.module.sys.base.server.dao.EmployeeDao;
import com.hoau.miser.module.sys.base.server.dao.UserDao;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：高佳
 * @create：2015年6月6日 下午3:48:47
 * @description：用户缓存数据提供者
 */
@Component
public class UserCacheProvider implements ITTLCacheProvider<IUser> {
	@Resource
	private UserDao userDao;
	@Resource
	private EmployeeDao employeeDao;
	@Override
	public IUser get(String key) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", key);
		map.put("userActive",MiserConstants.ACTIVE);
		map.put("empActive", MiserConstants.ACTIVE);
		map.put("orgActive", MiserConstants.ACTIVE);
		map.put("userRoleOrgActive", MiserConstants.ACTIVE);
		map.put("roleResActive", MiserConstants.ACTIVE);
		UserEntity user = userDao.queryUserWithRoleIdAndOrgIdByUserName(map);
		if(user != null){
			map = new HashMap<String, String>();
			map.put("empCode", user.getEmpCode());
			map.put("empActive",MiserConstants.ACTIVE);
			map.put("orgActive",MiserConstants.ACTIVE);
			EmployeeEntity emp = employeeDao.queryEmployeeInfoByCode(map);
			user.setEmployee(emp);
		}else{
			throw new UserException(UserException.CURRENT_USER_NO_ROLE);
		}
		return user;
	}
	
}

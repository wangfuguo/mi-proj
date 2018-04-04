package com.hoau.miser.module.sys.base.server.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.miser.module.sys.base.api.shared.domain.UserOrgResCodeUrisEntity;
import com.hoau.miser.module.sys.base.server.dao.UserDao;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：高佳
 * @create：2015年6月9日 下午7:52:48
 * @description：用户部门角色权限缓存数据提供者
 */
@Component
public class UserOrgRoleResCacheProvider implements ITTLCacheProvider<List<Set<String>>>{
	@Resource
	private UserDao userDao;

	@Override
	public List<Set<String>> get(String key) {
		String[] strs = key.split("#");
		String userCode = strs[0];
		String deptCode  = strs[1];
		List<Set<String>> listInfo = queryOrgResCodeUrisByCode(userCode, deptCode);
		return listInfo;
	}
	
	private List<Set<String>> queryOrgResCodeUrisByCode(String userCode,String deptCode){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Set<String>> result = new ArrayList<Set<String>>();
		Set<String> uriSet = new HashSet<String>();
		Set<String> codeSet = new HashSet<String>();
		map.put("userCode", userCode);
		map.put("deptCode", deptCode);
		map.put("orgRoleActive", MiserConstants.ACTIVE);
		map.put("roleResActive", MiserConstants.ACTIVE);
		map.put("resActive", MiserConstants.ACTIVE);
		List<UserOrgResCodeUrisEntity> orgCodeAndRoleCodes = userDao.queryOrgResCodeUrisByCode(map);
		for(UserOrgResCodeUrisEntity entity : orgCodeAndRoleCodes){
			uriSet.add(entity.getResUri());
			codeSet.add(entity.getResCode());
		}
		result.add(codeSet);
		result.add(uriSet);
		return result;
	}
}

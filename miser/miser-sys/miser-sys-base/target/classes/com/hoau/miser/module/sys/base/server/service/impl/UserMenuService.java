package com.hoau.miser.module.sys.base.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hoau.miser.module.sys.base.api.server.service.IUserMenuService;
import com.hoau.miser.module.sys.base.api.shared.domain.UserMenuEntity;

/**
 * @author：高佳
 * @create：2015年6月9日 上午8:51:30
 * @description：
 */
@Service
public class UserMenuService implements IUserMenuService{

	@Override
	public List<UserMenuEntity> queryUserMenuByUserCode(String empCode) {
		return null;
	}

}

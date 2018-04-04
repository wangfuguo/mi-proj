package com.hoau.miser.module.sys.base.api.server.service;

import com.hoau.miser.module.sys.frameworkimpl.shared.domain.CurrentInfo;

/**
 * @author：高佳
 * @create：2015年6月17日 下午7:41:34
 * @description：资源监控service
 */
public interface IResourceMonitorService {

	/**
	 * 用户上线监控
	 * @param info
	 * @param userName
	 * @author 高佳
	 * @date 2015年6月17日
	 * @update 
	 */
	void online(CurrentInfo info, String userName);
	
	/**
	 * 用户注销
	 * @param info
	 * @author 高佳
	 * @date 2015年6月17日
	 * @update 
	 */
	void offline(CurrentInfo info);

	/**
	 * 菜单统计计数器
	 * @param functionCode 菜单编码
	 * @param currentInfo 操作信息
	 * @author 高佳
	 * @date 2015年9月8日
	 * @update 
	 */
	void counterResource(String functionCode, CurrentInfo currentInfo);

}

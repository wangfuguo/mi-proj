package com.hoau.miser.module.util;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.util.pojo.AttachmentConfig;

/**
 * 系统路径管理
 * 
 * @author 275636
 * @date 2016-3-1
 */
public class AttachmentRootPath {

	/**
	 * linux server path
	 */
	private static String linuxServerpath;

	/**
	 * 获取路径
	 * 
	 * @return
	 * @author 蒋落琛
	 * @date 2015-5-27
	 * @update
	 */
	public static String getAttachRootPath() {
		// 判断该linuxpath是否存在，如果不存在则执行初始化配置
		if (StringUtil.isEmpty(linuxServerpath)) {
			// 初始化配置信息
			initConfig();
		}
		// 获取本系统的os名称
		String osname = System.getProperty("os.name");
		// 获取root的路径
		String rootPath = "";
		// 如果这个系统是windows的，则获取本class所在的路径
		if (osname.contains("Windows")) {
			// 获取root路径信息
			rootPath = AttachmentRootPath.class.getClassLoader()
					.getResource("").getPath();
			// 取WEB-INF上层目录路径
			rootPath = rootPath.substring(0, rootPath.lastIndexOf("WEB-INF") - 1);
		} else {
			// 如果不是则去linuxserverpath路径
			rootPath = linuxServerpath;
		}
		return rootPath;
	}

	/**
	 * 初始化配置信息
	 * 
	 * @author 蒋落琛
	 * @date 2015-5-27
	 * @update
	 */
	public static void initConfig() {
		// 附件配置实体类
		AttachmentConfig config = (AttachmentConfig) SpringContextUtil
				.getBean("attachmentConfig");
		// 获取linux server path 的路径信息
		linuxServerpath = config.getLinuxServerPath();
	}

}

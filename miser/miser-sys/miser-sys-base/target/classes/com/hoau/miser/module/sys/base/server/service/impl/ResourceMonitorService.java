package com.hoau.miser.module.sys.base.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.hoau.hbdp.framework.cache.redis.RedisClient;
import com.hoau.miser.module.common.shared.exception.BusinessMonitorException;
import com.hoau.miser.module.sys.base.api.server.service.IResourceMonitorService;
import com.hoau.miser.module.sys.frameworkimpl.shared.domain.CurrentInfo;

/**
 * @author：高佳
 * @create：2015年6月17日 下午7:41:55
 * @description：
 */
@Service
public class ResourceMonitorService implements IResourceMonitorService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ResourceMonitorService.class);
	/**
	 * Jedis客户端
	 */
	@Autowired(required = false)
	private RedisClient client;
	/**
	 * 监控分钟跨度：30分钟
	 */
	public static final int MONITOR_MINUTES = 30;

	/**
	 * 监控日期格式
	 */
	public static final String MONITOR_DATE_FORMAT = "yyyyMMdd";

	/**
	 * 监控时间格式
	 */
	public static final String MONITOR_TIME_FORMAT = "HH:mm";

	/**
	 * 监控时间分隔符
	 */
	public static final String MONITOR_TIME_SEPARATOR = "-";

	/**
	 * HASH KEY分隔符
	 */
	public static final String MONITOR_SEPARATOR = "#";

	/**
	 * 资源前缀
	 */
	public static final String RESOURCE_COUNTER_PREFIX = "RESOURCE";

	/**
	 * 在线部门
	 */
	public static final String ONLINE_COUNTER_PREFIX = "ONLINE";

	/**
	 * 在线分钟
	 */
	public static final int ONLINE_MINUTES = 24 * 60;

	@Override
	public void online(CurrentInfo currentInfo, String userName) {
		LOGGER.debug("monitor online: " + currentInfo);

		// 输入参数校验
		if (currentInfo == null
				|| StringUtils.isEmpty(currentInfo.getUserName())
				|| StringUtils.isEmpty(currentInfo.getCurrentDeptCode())) {
			throw new BusinessMonitorException("输入参数错误");
		}

		// 获取key名称
		String key = ONLINE_COUNTER_PREFIX + MONITOR_SEPARATOR
				+ currentInfo.getCurrentDeptCode() + MONITOR_SEPARATOR
				+ currentInfo.getUserName();

		Jedis jedis = null;

		try {

			jedis = client.getResource();

			jedis.setex(key, ONLINE_MINUTES * 60, userName);

		} catch (JedisConnectionException je) {
			LOGGER.error(je.getMessage(), je);
			client.returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				client.returnResource(jedis);
			}
		}
	}

	@Override
	public void offline(CurrentInfo currentInfo) {
		LOGGER.debug("monitor online: " + currentInfo);

		// 输入参数校验
		if (currentInfo == null
				|| StringUtils.isEmpty(currentInfo.getUserName())
				|| StringUtils.isEmpty(currentInfo.getCurrentDeptCode())) {
			throw new BusinessMonitorException("输入参数错误");
		}

		// 获取key名称
		String key = ONLINE_COUNTER_PREFIX + MONITOR_SEPARATOR
				+ currentInfo.getCurrentDeptCode() + MONITOR_SEPARATOR
				+ currentInfo.getUserName();

		Jedis jedis = null;

		try {

			jedis = client.getResource();

			jedis.del(key);

		} catch (JedisConnectionException je) {
			LOGGER.error(je.getMessage(), je);
			client.returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				client.returnResource(jedis);
			}
		}
	}

	@Override
	public void counterResource(String resourceCode, CurrentInfo currentInfo) {
		LOGGER.debug("monitor resource: " + resourceCode);
		// 输入参数校验
		if (resourceCode == null || currentInfo == null
				|| StringUtils.isEmpty(currentInfo.getCurrentDeptCode())) {
			throw new BusinessMonitorException("输入参数错误");
		}
		// 获取hash名称
		String hash = RESOURCE_COUNTER_PREFIX + MONITOR_SEPARATOR
				+ buildHash(currentInfo);
		Jedis jedis = null;
		try {
			jedis = client.getResource();
			Long i = jedis.hincrBy(hash, resourceCode, 1);
			if (i <= 0) {
				LOGGER.error("error monitor indicator: " + resourceCode);
			}
		} catch (JedisConnectionException je) {
			LOGGER.error(je.getMessage(), je);
			client.returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				client.returnResource(jedis);
			}
		}
	}

	private String buildHash(CurrentInfo currentInfo) {
		// 设置日期
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(MONITOR_DATE_FORMAT);
		StringBuffer hash = new StringBuffer();
		hash.append(dateFormat.format(calendar.getTime()));
		// 设置时间，动态计算分钟间隔
		long result = DateUtils.truncate(calendar, Calendar.DATE)
				.getTimeInMillis();
		result += calendar.get(Calendar.HOUR_OF_DAY)
				* DateUtils.MILLIS_PER_HOUR;
		result += (calendar.get(Calendar.MINUTE) / MONITOR_MINUTES)
				* MONITOR_MINUTES * DateUtils.MILLIS_PER_MINUTE;
		Date startTime = new Date(result);
		Date endTime = DateUtils.addMinutes(startTime, MONITOR_MINUTES);
		SimpleDateFormat timeFormat = new SimpleDateFormat(MONITOR_TIME_FORMAT);
		hash.append(MONITOR_SEPARATOR);
		hash.append(timeFormat.format(startTime))
				.append(MONITOR_TIME_SEPARATOR)
				.append(timeFormat.format(endTime));
		// 设置部门
		hash.append(MONITOR_SEPARATOR);
		hash.append(currentInfo.getCurrentDeptCode());
		return hash.toString();
	}
}

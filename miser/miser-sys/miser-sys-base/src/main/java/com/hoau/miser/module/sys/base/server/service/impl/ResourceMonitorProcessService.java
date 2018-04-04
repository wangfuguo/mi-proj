package com.hoau.miser.module.sys.base.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.hoau.hbdp.framework.cache.redis.RedisClient;
import com.hoau.miser.module.sys.base.api.server.service.IResourceMonitorProcessService;
import com.hoau.miser.module.util.CollectionUtils;
import com.hoau.miser.module.util.DateUtils;
import com.hoau.miser.module.util.define.NumberConstants;

/**
 * @author：高佳
 * @create：2015年9月11日 下午4:25:54
 * @description：菜单监控处理service实现
 */
@Service
public class ResourceMonitorProcessService implements IResourceMonitorProcessService{
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceMonitorProcessService.class);
	/**
	 * Jedis客户端
	 */
	@Resource
	private RedisClient client;

	@Override
	public void processResourceMonitor() {
		Jedis jedis = null;

		// 以当前时间前五分钟为时间节点
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, NumberConstants.NEGATIVE_NUMERAL_FIVE);
		
		// 动态计算分钟间隔，获取监控截止时间
		long result = DateUtils.truncate(calendar, Calendar.DATE).getTimeInMillis();
		result += calendar.get(Calendar.HOUR_OF_DAY) * DateUtils.MILLIS_PER_HOUR;
		result += ( calendar.get(Calendar.MINUTE) / ResourceMonitorService.MONITOR_MINUTES ) * ResourceMonitorService.MONITOR_MINUTES * DateUtils.MILLIS_PER_MINUTE;
		Date closeTime = new Date(result);
		
		// 获取需要遍历的日期
		SimpleDateFormat dateFormat = new SimpleDateFormat(ResourceMonitorService.MONITOR_DATE_FORMAT);
		String today = dateFormat.format(calendar.getTime());
		String yesterday = dateFormat.format(DateUtils.addDays(calendar.getTime(), -1));
		List<String> dates = Arrays.asList(yesterday, today);
		
		try {
			LOGGER.info("持久化两日内菜单监控数据");
			jedis = client.getResource();
			// 遍历两日内应用监控数据
			for (String date : dates) {
				Set<String> keys = jedis.keys(ResourceMonitorService.RESOURCE_COUNTER_PREFIX
								+ ResourceMonitorService.MONITOR_SEPARATOR + date + "*");
				if (CollectionUtils.isEmpty(keys)) {
					continue;
				}
				// 遍历HASH，数据持久化
				for (String key : keys) {
					Map<String, String> map = jedis.hgetAll(key);
					if (map == null || map.isEmpty()) {
						continue;
					}
					
					String key2 = key.substring(ResourceMonitorService.RESOURCE_COUNTER_PREFIX.length()
							+ ResourceMonitorService.MONITOR_SEPARATOR.length());
					Date monitorStartTime = this.persistBusinessMonitor(key2, map, false);
					// 截止时间前的监控数据不再变化，需要删除
					if (closeTime.after(monitorStartTime)) {
						jedis.expire(key, 1);
					}
				}
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
	
	@Transactional
	private Date persistBusinessMonitor(String key, Map<String, String> map, boolean businessIndicator) throws ParseException{
		
		LOGGER.info("KEY: " + key);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			LOGGER.info(entry.getKey() + ": " + entry.getValue());
		}
		
		// 通过KEY键，获取维度信息
		// KEY键规则：日期#时间段#部门
		String[] strs = key.split(ResourceMonitorService.MONITOR_SEPARATOR);
		
		Date monitorDate = DateUtils.parseDate(strs[0],
				new String[] { ResourceMonitorService.MONITOR_DATE_FORMAT });
		
		String monitorTimeRange = strs[1];
		String orgCode = strs[2];

		/*Date now = new Date();
		OrganizationLayerEntity layer = orgLayerService.getOrgLayerEntityByCache(orgCode, now);
		
		String[] monitorTimes = monitorTimeRange.split(ResourceMonitorService.MONITOR_TIME_SEPARATOR);
		Date monitorStartTime = DateUtils.parseDate(strs[0] + monitorTimes[0],
				new String[] { ResourceMonitorService.MONITOR_DATE_FORMAT
						+ ResourceMonitorService.MONITOR_TIME_FORMAT });
		Date monitorEndTime = DateUtils.parseDate(strs[0] + monitorTimes[1],
				new String[] { ResourceMonitorService.MONITOR_DATE_FORMAT
				+ ResourceMonitorService.MONITOR_TIME_FORMAT });
		
		// 初始化数据
		List<MonitorDataEntity> list = new ArrayList<MonitorDataEntity>(map.size());
		List<String> indicatorCodes = new ArrayList<String>();
		
		// 构造监控数据
		for (Map.Entry<String, String> entry : map.entrySet()) {
			
			String code = entry.getKey();
			Long indicatorValue = Long.valueOf(entry.getValue());
			
			MonitorDataEntity monitorData = new MonitorDataEntity();
			monitorData.setId(UUIDUtil.getUUID());
			monitorData.setOrgCode(orgCode);
			if (layer != null) {
				monitorData.setLevel2OrgCode(layer.getLevel4());	// 事业部是四级部门
				monitorData.setLevel3OrgCode(layer.getLevel5());	// 大区是五级部门
			}
			monitorData.setMonitorDate(monitorDate);
			monitorData.setMonitorTimeRange(monitorTimeRange);
			monitorData.setIndicatorValue(indicatorValue);
			monitorData.setCreateTime(now);
			monitorData.setMonitorStartTime(monitorStartTime);
			monitorData.setMonitorEndTime(monitorEndTime);
			
			if (businessIndicator) {
				monitorData.setIndicatorCode(code);
				indicatorCodes.add(code);
			} else {
				monitorData.setIndicatorCode(BusinessMonitorIndicator.SYSTEM_RESOURCE_COUNT.getCode());
				indicatorCodes.add(BusinessMonitorIndicator.SYSTEM_RESOURCE_COUNT.getCode());
				monitorData.setIndicatorSubCode(code);
			}
			
			list.add(monitorData);
		}

		// 删除历史数据
		monitorDataService.batchDeleteMonitorData(monitorStartTime, monitorTimeRange, orgCode, indicatorCodes);
		// 批量插入最新数据
		monitorDataService.batchAddMonitorData(list);*/
    	
		//return monitorStartTime;
		return null;
	}

}

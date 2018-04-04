package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.IPriceSectionTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.api.itf.server.cache.PriceSectionTyCache;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: PriceSectionTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: 优惠分段查询接口实现
 * @date 16/6/8 15:09
 */
@Service
public class PriceSectionTyService implements IPriceSectionTyService {
	/**
	 * 根据优惠分段编码查询优惠分段明细
	 *
	 * @param sectionCode
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日15:08:12
	 */
	@Override
	public List<PriceSectionSubEntity> querySectionDetailByCode(String sectionCode) {
		if(StringUtil.isEmpty(sectionCode)) return null;
		ICache<String, List<PriceSectionSubEntity>> cache = CacheManager.getInstance().getCache(PriceSectionTyCache.UUID);
		return cache.get(sectionCode);
	}
}

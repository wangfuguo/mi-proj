package com.hoau.miser.module.api.itf.server.cache;

import com.hoau.hbdp.framework.cache.provider.ITTLCacheProvider;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.shared.domain.BseCustomerTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity;
import com.hoau.miser.module.api.itf.server.dao.BseCustomerTyDao;
import com.hoau.miser.module.api.itf.server.dao.CorpAgingCityDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description 基础客户
 * ClassName: BseCustomerTyCacheProvider
 * @author 廖文强
 * @date 2016年06月08日
 * @version V1.0
 */
@Component
public class BseCustomerTyCacheProvider implements ITTLCacheProvider<BseCustomerTyEntity> {

	@Resource
	BseCustomerTyDao bseCustomerTyDao;

	public BseCustomerTyEntity get(String custNo) {
		if (StringUtil.isEmpty(custNo)) {
			return null;
		}
		List<BseCustomerTyEntity> list=bseCustomerTyDao.queryBseCustomerTyByCustNo(custNo);
		return list!=null&&list.size()>0?list.get(0):null;
	}

}

/**
 *
 */
package com.hoau.miser.module.api.itf.api.server;


import com.hoau.miser.module.api.itf.api.shared.domain.OutLineTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

import java.util.List;
import java.util.Map;

/**
 * 外发偏线系统管理 2016-3-17 20:09:07
 * @author dengyin
 *
 */
public interface IOutLineTyService {

	/**
	 * 偏线价格查询
	 * @Param  [baseTyParam]
	 * @Return com.hoau.miser.module.api.itf.api.shared.domain.OutLineTyEntity
	 * @Throws
	 * @Author 廖文强
	 * @Date 2016/6/13 14:43
	 * @Version v1
	 */
	OutLineTyEntity queryOutLineByEntity(String destOrgCode);

}

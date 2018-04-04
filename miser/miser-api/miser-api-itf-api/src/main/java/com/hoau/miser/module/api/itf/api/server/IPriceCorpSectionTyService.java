package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceCorpSectionTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: IPriceCorpSectionTyService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 分段网点价格
 * @date 2016/07/05
 */
public interface IPriceCorpSectionTyService {
    /**
     *
     * @param baseTyParam
     * @return
     * @Description: 查询网点价格
     * @author 廖文强
     * @date 2016年06月06日
     */
    public PriceCorpSectionTyEntity queryPriceSectionQueryParam(PriceQueryParam baseTyParam);
}

package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCorpTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: DiscountCorpTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: 网点折扣
 * @date 2016/6/12 17:28
 */
@Repository
public interface DiscountCorpTyDao {
    /**
     *
     * @Param  [baseTyParam]
     * @Return java.util.List<com.hoau.miser.module.api.itf.api.shared.domain.DiscountCorpTyEntity>
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/12 17:29
     * @Version v1
     */
    List<DiscountCorpTyEntity> queryListByParam(PriceQueryParam baseTyParam);
}

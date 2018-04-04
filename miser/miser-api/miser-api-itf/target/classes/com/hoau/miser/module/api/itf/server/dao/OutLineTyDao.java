package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.OutLineTyEntity;
import org.springframework.stereotype.Repository;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: OutLineTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/13 15:05
 */
@Repository
public interface OutLineTyDao {
    /**
     * 查询外发偏线根据目的地组织编码
     * @Param  [destCode]
     * @Return com.hoau.miser.module.api.itf.api.shared.domain.OutLineTyEntity
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/13 15:05
     * @Version v1
     */
    OutLineTyEntity queryOutLineByDestCode(String destOrgCode);
}

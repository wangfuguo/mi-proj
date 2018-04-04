package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.BseCustomerTyEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: BseCustomerTyDao
 * @Package com.hoau.miser.module.api.itf.server.dao
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/8 17:19
 */
@Repository
public interface BseCustomerTyDao {

    List<BseCustomerTyEntity> queryBseCustomerTyByCustNo(String custNo);
}

package com.hoau.miser.module.api.itf.server.dao;

import com.hoau.miser.module.api.itf.api.shared.domain.TransportTypeQueryEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.AvailableTransportTypeQueryResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sai on 15/12/15.
 */
@Repository
public interface AvailableTransportTypeDao {

	/**
     * 查询时效允许的运输类型
     * @param transportTypeQueryEntity
     * @return
     * @author 陈宇霖
     * @date 2016年06月07日20:36:32
     */
    public List<AvailableTransportTypeQueryResult> queryTimeAvailableTransportTypes(TransportTypeQueryEntity transportTypeQueryEntity);

    /**
     * 查询价格允许的运输类型集合
     * @param transportTypeQueryEntity
     * @return List<AvailableTransportTypeQueryResult>
     * @author 廖文强
     * @date 2016年1月14日
     */
    public  List<AvailableTransportTypeQueryResult> queryPriceAvailableTransportTypes(TransportTypeQueryEntity transportTypeQueryEntity);
}

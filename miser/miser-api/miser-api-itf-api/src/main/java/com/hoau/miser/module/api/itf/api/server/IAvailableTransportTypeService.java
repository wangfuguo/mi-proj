package com.hoau.miser.module.api.itf.api.server;


import com.hoau.miser.module.api.itf.api.shared.vo.AvailableTransportTypeQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.AvailableTransportTypeQueryResult;

import java.util.List;

/**
 * ClassName: ITranstypeService
 * @Description: 运输类型操作服务
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年3月1日
 * @version V1.0
 */
public interface IAvailableTransportTypeService {

    /**
     * @Description: 运输类型集合交集(标准和时效)
     * @param availableTransportTypeQueryParam :出发，到达。。。
     * @return List<TransTypeFyDto>
     * @author 廖文强
     * @date 2016年06月02日
     */
    public List<AvailableTransportTypeQueryResult> queryAvailableTransTypes(AvailableTransportTypeQueryParam availableTransportTypeQueryParam);
}

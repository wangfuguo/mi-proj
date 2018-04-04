package com.hoau.miser.module.api.itf.server.facade;

import com.hoau.hbdp.webservice.components.rest.define.HttpContentTypeConstants;
import com.hoau.hbdp.webservice.components.rest.define.RestErrorCodeConstants;
import com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity;
import com.hoau.miser.module.api.itf.api.server.IPriceUpstairsTyService;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceUpstairsEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.*;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: DataDefineQueryFacade
 * @Package com.hoau.miser.module.api.itf.server.facade
 * @Description: 数据定义查询接口
 * @date 2016年06月20日09:25:49
 */
@Path("/itf/dataDefineQuery")
@Consumes(HttpContentTypeConstants.JSON_UTF8)
@Produces(HttpContentTypeConstants.JSON_UTF8)
@Service
public class DataDefineQueryFacade {

	@Resource
	IPriceUpstairsTyService priceUpstairsTyService;

	/**
	 * 查询上楼定义
	 * @param entity
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月14日14:34:02
	 */
    @POST
    @Path("/v1/queryUpstairsDefine")
    public ResponseBaseEntity<PriceUpstairsEntity> queryUpstairsDefine(PriceUpstairsEntity entity){
        ResponseBaseEntity<PriceUpstairsEntity> response=new ResponseBaseEntity<PriceUpstairsEntity>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(priceUpstairsTyService.queryUpstairsByParam(entity));
        return response;
    }

}

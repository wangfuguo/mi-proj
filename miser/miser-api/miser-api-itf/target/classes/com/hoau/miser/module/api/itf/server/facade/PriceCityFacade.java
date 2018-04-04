package com.hoau.miser.module.api.itf.server.facade;

import com.hoau.hbdp.webservice.components.rest.define.HttpContentTypeConstants;
import com.hoau.hbdp.webservice.components.rest.define.RestErrorCodeConstants;
import com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity;
import com.hoau.miser.module.api.itf.api.server.ICorpPriceCityService;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpPriceCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.*;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: TimeCityFacade
 * @Package com.hoau.miser.module.api.itf.server.facade
 * @Description: 时效相关接口
 * @date 2016年06月14日14:06:19
 */
@Path("/itf/priceCity")
@Consumes(HttpContentTypeConstants.JSON_UTF8)
@Produces(HttpContentTypeConstants.JSON_UTF8)
@Service
public class PriceCityFacade {

    @Resource
    private ICorpPriceCityService corpPriceCityService;

	/**
	 * 根据组织编码查询价卡城市
	 * @param param		参数,省、市、区必须
	 * @param cityType	查询的类型:SEND 出发 ARRIVAL 到达
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月14日14:34:02
	 */
    @POST
    @Path("/v1/queryPriceCity/{cityType}")
    public ResponseBaseEntity<CorpPriceCityEntity> queryPriceCity(OrgPositionInfoTyEntity param, @PathParam("cityType") String cityType){
        ResponseBaseEntity<CorpPriceCityEntity> response=new ResponseBaseEntity<CorpPriceCityEntity>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(corpPriceCityService.queryPriceCity(param, cityType));
        return response;
    }

}

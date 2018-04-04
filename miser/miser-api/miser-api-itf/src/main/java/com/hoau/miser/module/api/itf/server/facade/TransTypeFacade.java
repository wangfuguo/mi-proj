package com.hoau.miser.module.api.itf.server.facade;

import com.hoau.hbdp.webservice.components.rest.define.HttpContentTypeConstants;
import com.hoau.hbdp.webservice.components.rest.define.RestErrorCodeConstants;
import com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity;
import com.hoau.miser.module.api.itf.api.server.IAvailableTransportTypeService;
import com.hoau.miser.module.api.itf.api.shared.vo.AvailableTransportTypeQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.AvailableTransportTypeQueryResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: ITransTypeFacade 产品类型接口
 * @Package com.hoau.miser.module.api.itf.server.facade
 * @Description: 查询可用产品类别
 * @date 2016/6/1 10:46
 */
@Path("/itf/transType")
@Consumes(HttpContentTypeConstants.JSON_UTF8)
@Produces(HttpContentTypeConstants.JSON_UTF8)
@Service
public class TransTypeFacade {

    @Resource
    private IAvailableTransportTypeService transTypeService;

    @POST
    @Path("/v1/queryAvailableTransTypes")
    public ResponseBaseEntity<List<AvailableTransportTypeQueryResult>> queryAvailableTransTypes(AvailableTransportTypeQueryParam availableTransportTypeQueryParam){
        ResponseBaseEntity<List<AvailableTransportTypeQueryResult>> response=new ResponseBaseEntity<List<AvailableTransportTypeQueryResult>>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(transTypeService.queryAvailableTransTypes(availableTransportTypeQueryParam));
        return  response;
    }

}

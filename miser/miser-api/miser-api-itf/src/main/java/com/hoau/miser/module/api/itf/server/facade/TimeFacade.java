package com.hoau.miser.module.api.itf.server.facade;

import com.hoau.hbdp.webservice.components.rest.define.HttpContentTypeConstants;
import com.hoau.hbdp.webservice.components.rest.define.RestErrorCodeConstants;
import com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity;
import com.hoau.miser.module.api.itf.api.server.ICorpAgingCityService;
import com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.DistrictAgingCityRequestEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.TimeQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.TimeQueryResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.*;
import java.util.List;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: TimeCityFacade
 * @Package com.hoau.miser.module.api.itf.server.facade
 * @Description: 时效相关接口
 * @date 2016年06月14日14:06:19
 */
@Path("/itf/time")
@Consumes(HttpContentTypeConstants.JSON_UTF8)
@Produces(HttpContentTypeConstants.JSON_UTF8)
@Service
public class TimeFacade {

    @Resource
    private ICorpAgingCityService corpAgingCityService;

	/**
	 * 根据组织编码查询出发、到达时效城市
	 * @param orgCode
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月14日14:34:02
	 */
    @GET
    @Path("/v1/queryAgingCityByOrgCode/{orgCode}")
    public ResponseBaseEntity<CorpAgingCityEntity> queryAgingCityByOrgCode(@PathParam("orgCode") String orgCode){
        ResponseBaseEntity<CorpAgingCityEntity> response=new ResponseBaseEntity<CorpAgingCityEntity>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(corpAgingCityService.queryAgingCityByOrgCode(orgCode));
        return response;
    }

	@POST
	@Path("/v1/queryAgingCityByDistrict")
	/**   
	* @Description:
	* @params [queryParam]
	* @return com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity<com.hoau.miser.module.api.itf.api.shared.domain.CorpAgingCityEntity> 
	* @author 陈宇霖 
	* @date 16/7/14 17:02
	* @version V1.0   
	*/
	public ResponseBaseEntity<CorpAgingCityEntity> queryAgingCityByDistrict(DistrictAgingCityRequestEntity queryParam) {
		ResponseBaseEntity<CorpAgingCityEntity> response=new ResponseBaseEntity<CorpAgingCityEntity>();
		response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
		response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
		response.setResult(corpAgingCityService.queryAgingCityByDistrict(queryParam));
		return response;
	}

	/**
	 * 查询时效定义信息
	 * @param param
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月22日14:18:23
	 */
	@POST
	@Path("/v1/queryTime")
	public ResponseBaseEntity<List<TimeQueryResult>> queryTime(TimeQueryParam param) {
		ResponseBaseEntity<List<TimeQueryResult>> response=new ResponseBaseEntity<List<TimeQueryResult>>();
		response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
		response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
		response.setResult(corpAgingCityService.queryTime(param));
		return response;
	}

	/**
	 * 计算预计到达时间
	 * @param param
	 * @return 如果线路没有开线,抛出异常,如果开线了但是提供的运输类型不存在,则返回null,否则返回时效
	 * @author 陈宇霖
	 * @date 2016年06月22日14:23:55
	 */
	@POST
	@Path("/v1/calculateExpectArrivalTime")
	public ResponseBaseEntity<TimeQueryResult> calculateExpectArrivalTime (TimeQueryParam param) {
		ResponseBaseEntity<TimeQueryResult> response=new ResponseBaseEntity<TimeQueryResult>();
		response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
		response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
		response.setResult(corpAgingCityService.calculateExpectArrivalTime(param));
		return response;
	}

}

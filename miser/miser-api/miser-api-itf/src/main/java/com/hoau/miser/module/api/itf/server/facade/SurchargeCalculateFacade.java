package com.hoau.miser.module.api.itf.server.facade;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.server.constants.CplbConstant;
import org.springframework.stereotype.Service;

import com.hoau.hbdp.webservice.components.rest.define.HttpContentTypeConstants;
import com.hoau.hbdp.webservice.components.rest.define.RestErrorCodeConstants;
import com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity;
import com.hoau.miser.module.api.itf.api.server.ICalculateBaseService;
import com.hoau.miser.module.api.itf.api.server.ISpecialServerPriceService;
import com.hoau.miser.module.api.itf.api.server.ISurchargePriceService;
import com.hoau.miser.module.api.itf.api.shared.dto.PackChargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.QueryPackChargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.QuerySurchargeDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SpecialServerCalculateResultDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SpecialServerPriceConditionDto;
import com.hoau.miser.module.api.itf.api.shared.dto.SurchargeDto;

/**
 * 附加费对外接口
 *
 * @author 蒋落琛
 * @date 2016-6-7下午4:35:51
 */
@Path("/itf/surchargeCalculate")
@Consumes(HttpContentTypeConstants.JSON_UTF8)
@Produces(HttpContentTypeConstants.JSON_UTF8)
@Service
public class SurchargeCalculateFacade {

    /**
     * 送货费
     */
    @Resource
    private ICalculateBaseService<QuerySurchargeDto, SurchargeDto> calcWaybillDeliveryFeeService;
    /**
     * 上楼费
     */
    @Resource
    private ICalculateBaseService<QuerySurchargeDto, SurchargeDto> calcWaybillUpstairSFeeService;
    /**
     * 信息费
     */
    @Resource
    private ICalculateBaseService<QuerySurchargeDto, SurchargeDto> calcWaybillInformationFeeService;
    /**
     * 工本费
     */
    @Resource
    private ICalculateBaseService<QuerySurchargeDto, SurchargeDto> calcWaybillProductionFeeService;
    /**
     * 保价费率
     */
    @Resource
    private ICalculateBaseService<QuerySurchargeDto, SurchargeDto> calcWaybillInsuredRateService;
    /**
     * 保价费
     */
    @Resource
    private ICalculateBaseService<QuerySurchargeDto, SurchargeDto> calcWaybillInsuredFeeService;
    /**
     * 提货费
     */
    @Resource
    private ICalculateBaseService<QuerySurchargeDto, SurchargeDto> calcWaybillPickupFeeService;
    /**
     * 代收货款费率
     */
    @Resource
    private ICalculateBaseService<QuerySurchargeDto, SurchargeDto> calcWaybillCollectionPayRateService;
    /**
     * 代收货款手续费
     */
    @Resource
    private ICalculateBaseService<QuerySurchargeDto, SurchargeDto> calcWaybillCollectionPayFeeService;
    /**
     * 燃油费
     */
    @Resource
    private ICalculateBaseService<QuerySurchargeDto, SurchargeDto> calcWaybillFuelFeeService;
    /**
     * 所有特服费列表
     */
    @Resource
    private ISpecialServerPriceService specialServerPriceService;
    /**
     * 特服费单项费用
     */
    @Resource
    private ICalculateBaseService<SpecialServerPriceConditionDto, SpecialServerCalculateResultDto> calcWaybillExtraFeeService;
    /**
     * 初始加载包装费
     */
    @Resource
    private ISurchargePriceService surchargePriceService;
    /**
     * 包装费
     */
    @Resource
    private ICalculateBaseService<QueryPackChargeDto, SurchargeDto> calcWaybillPackingFeeService;

    /**
     * 计算送货费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-8下午4:40:53
     * @update
     */
    @POST
    @Path("/calculateDeliveryCharge")
    public ResponseBaseEntity<SurchargeDto> calculateDeliveryCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillDeliveryFeeService, "PRICE"));
        return response;
    }

    /**
     * 计算标准送货费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-8下午4:40:53
     * @update
     */
    @POST
    @Path("/calculateStandardDeliveryCharge")
    public ResponseBaseEntity<SurchargeDto> calculateStandardDeliveryCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SurchargeDto result = calculationPrice(params, calcWaybillDeliveryFeeService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 计算上楼费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-8下午4:41:12
     * @update
     */
    @POST
    @Path("/calculateDeliveryUpstairsCharge")
    public ResponseBaseEntity<SurchargeDto> calculateDeliveryUpstairsCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillUpstairSFeeService, "PRICE"));
        return response;
    }

    /**
     * 计算标准上楼费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-8下午4:41:12
     * @update
     */
    @POST
    @Path("/calculateStandardDeliveryUpstairsCharge")
    public ResponseBaseEntity<SurchargeDto> calculateStandardDeliveryUpstairsCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SurchargeDto result = calculationPrice(params, calcWaybillUpstairSFeeService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 计算信息费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-8下午4:41:12
     * @update
     */
    @POST
    @Path("/calculateInformationCharge")
    public ResponseBaseEntity<SurchargeDto> calculateInformationCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillInformationFeeService, "PRICE"));
        return response;
    }

    /**
     * 计算标准信息费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-8下午4:41:12
     * @update
     */
    @POST
    @Path("/calculateStandardInformationCharge")
    public ResponseBaseEntity<SurchargeDto> calculateStandardInformationCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SurchargeDto result = calculationPrice(params, calcWaybillInformationFeeService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 计算工本费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-8下午4:41:12
     * @update
     */
    @POST
    @Path("/calculateIssuingCharge")
    public ResponseBaseEntity<SurchargeDto> calculateIssuingCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillProductionFeeService, "PRICE"));
        return response;
    }

    /**
     * 计算标准工本费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-8下午4:41:12
     * @update
     */
    @POST
    @Path("/calculateStandardIssuingCharge")
    public ResponseBaseEntity<SurchargeDto> calculateStandardIssuingCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SurchargeDto result = calculationPrice(params, calcWaybillProductionFeeService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 计算保价费率
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-13下午6:42:43
     * @update
     */
    @POST
    @Path("/calculateInsuredRate")
    public ResponseBaseEntity<SurchargeDto> calculateInsuredRate(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillInsuredRateService, "PRICE"));
        return response;
    }

    /**
     * 计算标准保价费率
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-13下午6:42:43
     * @update
     */
    @POST
    @Path("/calculateStandardInsuredRate")
    public ResponseBaseEntity<SurchargeDto> calculateStandardInsuredRate(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SurchargeDto result = calculationPrice(params, calcWaybillInsuredRateService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 计算保价费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-13下午6:46:50
     * @update
     */
    @POST
    @Path("/calculateInsuredPrice")
    public ResponseBaseEntity<SurchargeDto> calculateInsuredPrice(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillInsuredFeeService, "PRICE"));
        return response;
    }

    /**
     * 计算标准保价费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-13下午6:46:50
     * @update
     */
    @POST
    @Path("/calculateStandardInsuredPrice")
    public ResponseBaseEntity<SurchargeDto> calculateStandardInsuredPrice(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SurchargeDto result = calculationPrice(params, calcWaybillInsuredFeeService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 计算提货费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-13下午6:46:50
     * @update
     */
    @POST
    @Path("/calculatePickupPrice")
    public ResponseBaseEntity<SurchargeDto> calculatePickupPrice(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillPickupFeeService, "PRICE"));
        return response;
    }

    /**
     * 计算标准提货费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-13下午6:46:50
     * @update
     */
    @POST
    @Path("/calculateStandardPickupPrice")
    public ResponseBaseEntity<SurchargeDto> calculateStandardPickupPrice(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SurchargeDto result = calculationPrice(params, calcWaybillPickupFeeService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 计算代收货款费率
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-14下午2:11:22
     * @update
     */
    @POST
    @Path("/calculateCollectionPayRate")
    public ResponseBaseEntity<SurchargeDto> calculateCollectionPayRate(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillCollectionPayRateService, "PRICE"));
        return response;
    }

    /**
     * 计算标准代收货款费率
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-14下午2:11:22
     * @update
     */
    @POST
    @Path("/calculateStandardCollectionPayRate")
    public ResponseBaseEntity<SurchargeDto> calculateStandardCollectionPayRate(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SurchargeDto result = calculationPrice(params, calcWaybillCollectionPayRateService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 计算代收货款手续费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-14下午2:49:36
     * @update
     */
    @POST
    @Path("/calculateCollectionPayCharge")
    public ResponseBaseEntity<SurchargeDto> calculateCollectionPayCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillCollectionPayFeeService, "PRICE"));
        return response;
    }

    /**
     * 计算标准代收货款手续费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-14下午2:49:36
     * @update
     */
    @POST
    @Path("/calculateStandardCollectionPayCharge")
    public ResponseBaseEntity<SurchargeDto> calculateStandardCollectionPayCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SurchargeDto result = calculationPrice(params, calcWaybillCollectionPayFeeService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 计算燃油费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-15上午11:44:46
     * @update
     */
    @POST
    @Path("/calculateFuelCharge")
    public ResponseBaseEntity<SurchargeDto> calculateFuelCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillFuelFeeService, "PRICE"));
        return response;
    }

    /**
     * 计算标准燃油费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-15上午11:44:46
     * @update
     */
    @POST
    @Path("/calculateStandardFuelCharge")
    public ResponseBaseEntity<SurchargeDto> calculateStandardFuelCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SurchargeDto result = calculationPrice(params, calcWaybillFuelFeeService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 获取所有特服费列表
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-15下午4:24:11
     * @update
     */
    @POST
    @Path("/calculateListSpecialPrice")
    public ResponseBaseEntity<List<SpecialServerCalculateResultDto>> calculateListSpecialPrice(
            SpecialServerPriceConditionDto params) {
        ResponseBaseEntity<List<SpecialServerCalculateResultDto>> response = new ResponseBaseEntity<List<SpecialServerCalculateResultDto>>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        // 父运输类型
        String parProductType = params.getProductType();
        // 判断是否是偏线，并设置当前产品类型
        boolean isPx = isPx(params);
        List<SpecialServerCalculateResultDto> result = specialServerPriceService
                .calculateListSpecialPrice(params);
        // 如果是偏线，并且没有配置项
        if (isPx && (result == null || result.size() == 0)) {
            params.setProductType(parProductType);
            result = specialServerPriceService
                    .calculateListSpecialPrice(params);
        }
        response.setResult(result);
        return response;
    }

    /**
     * 计算特服费单项费用
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-15下午4:24:11
     * @update
     */
    @POST
    @Path("/calculateUnitermSpecialPrice")
    public ResponseBaseEntity<SpecialServerCalculateResultDto> calculateUnitermSpecialPrice(
            SpecialServerPriceConditionDto params) {
        ResponseBaseEntity<SpecialServerCalculateResultDto> response = new ResponseBaseEntity<SpecialServerCalculateResultDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillExtraFeeService, "PRICE"));
        return response;
    }

    /**
     * 计算标准特服费单项费用
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-15下午4:24:11
     * @update
     */
    @POST
    @Path("/calculateStandardUnitermSpecialPrice")
    public ResponseBaseEntity<SpecialServerCalculateResultDto> calculateStandardUnitermSpecialPrice(
            SpecialServerPriceConditionDto params) {
        ResponseBaseEntity<SpecialServerCalculateResultDto> response = new ResponseBaseEntity<SpecialServerCalculateResultDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SpecialServerCalculateResultDto result = calculationPrice(params, calcWaybillExtraFeeService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 初始加载包装费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-15下午4:24:11
     * @update
     */
    @POST
    @Path("/initialLoadPackCharge")
    public ResponseBaseEntity<List<PackChargeDto>> initialLoadPackCharge(
            QuerySurchargeDto params) {
        ResponseBaseEntity<List<PackChargeDto>> response = new ResponseBaseEntity<List<PackChargeDto>>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        // 父运输类型
        String parProductType = params.getProductType();
        // 判断是否是偏线，并设置当前产品类型
        boolean isPx = isPx(params);
        List<PackChargeDto> result = surchargePriceService.initialLoadPackCharge(params);
        // 如果是偏线，并且没有配置项
        if (isPx && (result == null || result.size() == 0)) {
            params.setProductType(parProductType);
            result = surchargePriceService.initialLoadPackCharge(params);
        }
        response.setResult(result);
        return response;
    }

    /**
     * 计算包装费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-16上午8:20:24
     * @update
     */
    @POST
    @Path("/calculatePackPrice")
    public ResponseBaseEntity<SurchargeDto> calculatePackPrice(
            QueryPackChargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(calculationPrice(params, calcWaybillPackingFeeService, "PRICE"));
        return response;
    }

    /**
     * 计算标准包装费
     *
     * @param params
     * @return
     * @author 蒋落琛
     * @date 2016-6-16上午8:20:24
     * @update
     */
    @POST
    @Path("/calculateStandardPackPrice")
    public ResponseBaseEntity<SurchargeDto> calculateStandardPackPrice(
            QueryPackChargeDto params) {
        ResponseBaseEntity<SurchargeDto> response = new ResponseBaseEntity<SurchargeDto>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        SurchargeDto result = calculationPrice(params, calcWaybillPackingFeeService, "STANDARD");
        result = (result != null && result.getStandardAmount() == null) ? null : result;
        response.setResult(result);
        return response;
    }

    /**
     * 费用计算
     *
     * @param params
     * @return
     */
    private SurchargeDto calculationPrice(QuerySurchargeDto params, ICalculateBaseService<QuerySurchargeDto, SurchargeDto> service, String operType) {
        // 费用信息
        SurchargeDto result = null;
        // 父运输类型
        String parProductType = params.getProductType();
        // 是否偏线
        boolean isPx = isPx(params);
        // 判断费用类型，PRICE:实际费用计算  STANDARD:标准费用计算
        if (operType.equals("PRICE")) {
            result = service.calculate(params);
        } else {
            result = service.calculateStandard(params);
        }
        // 如果是偏线，并且没有配置项
        if (isPx && (result == null || !result.isHaveConfig())) {
            params.setProductType(parProductType);
            // 判断费用类型，PRICE:实际费用计算  STANDARD:标准费用计算
            if (operType.equals("PRICE")) {
                result = service.calculate(params);
            } else {
                result = service.calculateStandard(params);
            }
        }
        return result;
    }

    /**
     * 包装费计算
     *
     * @param params
     * @return
     */
    private SurchargeDto calculationPrice(QueryPackChargeDto params, ICalculateBaseService<QueryPackChargeDto, SurchargeDto> service, String operType) {
        // 费用信息
        SurchargeDto result = null;
        // 父运输类型
        String parProductType = params.getProductType();
        // 是否偏线
        boolean isPx = isPx(params);
        // 判断费用类型，PRICE:实际费用计算  STANDARD:标准费用计算
        if (operType.equals("PRICE")) {
            result = service.calculate(params);
        } else {
            result = service.calculateStandard(params);
        }
        // 如果是偏线，并且没有配置项
        if (isPx && (result == null || !result.isHaveConfig())) {
            params.setProductType(parProductType);
            // 判断费用类型，PRICE:实际费用计算  STANDARD:标准费用计算
            if (operType.equals("PRICE")) {
                result = service.calculate(params);
            } else {
                result = service.calculateStandard(params);
            }
        }
        return result;
    }

    /**
     * 特服费计算
     *
     * @param params
     * @return
     */
    private SpecialServerCalculateResultDto calculationPrice(SpecialServerPriceConditionDto params, ICalculateBaseService<SpecialServerPriceConditionDto, SpecialServerCalculateResultDto> service, String operType) {
        // 费用信息
        SpecialServerCalculateResultDto result = null;
        // 父运输类型
        String parProductType = params.getProductType();
        // 是否偏线
        boolean isPx = isPx(params);
        // 判断费用类型，PRICE:实际费用计算  STANDARD:标准费用计算
        if (operType.equals("PRICE")) {
            result = service.calculate(params);
        } else {
            result = service.calculateStandard(params);
        }
        // 如果是偏线，并且没有配置项
        if (isPx && (result == null || !result.isHaveConfig())) {
            params.setProductType(parProductType);
            // 判断费用类型，PRICE:实际费用计算  STANDARD:标准费用计算
            if (operType.equals("PRICE")) {
                result = service.calculate(params);
            } else {
                result = service.calculateStandard(params);
            }
        }
        return result;
    }

    /**
     * 当前运输类型赋值，并判断是否为偏线
     *
     * @param params
     * @return
     */
    private boolean isPx(QuerySurchargeDto params) {
        boolean isPx = false;
        // 子产品类型如果不为空
        if (StringUtil.isNotEmpty(params.getSubProductType())) {
            // 如果是偏线
            if (CplbConstant.TYPE_PX.equals(params.getSubProductType())) {
                isPx = true;
                params.setProductType(CplbConstant.TYPE_PX);
            }/* else {
                params.setProductType(params.getSubProductType());
			}*/
        }
        return isPx;
    }

    /**
     * 当前运输类型赋值，并判断是否为偏线
     *
     * @param params
     * @return
     */
    private boolean isPx(QueryPackChargeDto params) {
        boolean isPx = false;
        // 子产品类型如果不为空
        if (StringUtil.isNotEmpty(params.getSubProductType())) {
            // 如果是偏线
            if (CplbConstant.TYPE_PX.equals(params.getSubProductType())) {
                isPx = true;
                params.setProductType(CplbConstant.TYPE_PX);
            }/* else {
                params.setProductType(params.getSubProductType());
			}*/
        }
        return isPx;
    }

    /**
     * 当前运输类型赋值，并判断是否为偏线
     *
     * @param params
     * @return
     */
    private boolean isPx(SpecialServerPriceConditionDto params) {
        boolean isPx = false;
        // 子产品类型如果不为空
        if (StringUtil.isNotEmpty(params.getSubProductType())) {
            // 如果是偏线
            if (CplbConstant.TYPE_PX.equals(params.getSubProductType())) {
                isPx = true;
                params.setProductType(CplbConstant.TYPE_PX);
            }/* else {
                params.setProductType(params.getSubProductType());
			}*/
        }
        return isPx;
    }
}

package com.hoau.miser.module.api.itf.server.facade;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.hbdp.webservice.components.rest.define.HttpContentTypeConstants;
import com.hoau.hbdp.webservice.components.rest.define.RestErrorCodeConstants;
import com.hoau.hbdp.webservice.components.rest.entity.ResponseBaseEntity;
import com.hoau.miser.module.api.itf.api.server.IBseCustomerTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceEasyHomeTyService;
import com.hoau.miser.module.api.itf.api.server.IPriceTyService;
import com.hoau.miser.module.api.itf.api.shared.define.PCTyConstans;
import com.hoau.miser.module.api.itf.api.shared.define.ProdTypeConstant;
import com.hoau.miser.module.api.itf.api.shared.domain.BseCustomerTyEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceEasyHomeQueryResult;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryResult;
import com.hoau.miser.module.api.itf.server.constants.CplbConstant;
import com.hoau.miser.module.api.itf.server.constants.PriceConstants;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import com.hoau.miser.module.util.define.MiserConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceFacade 价格
 * @Package com.hoau.miser.module.api.itf.server.facade
 * @Description: 查询价格
 * @date 2016/6/22
 */
@Path("/itf/price")
@Consumes(HttpContentTypeConstants.JSON_UTF8)
@Produces(HttpContentTypeConstants.JSON_UTF8)
@Service
public class PriceFacade {

    @Resource
    private IPriceTyService priceTyService;
    @Resource
    private IPriceEasyHomeTyService priceEasyHomeTyService;
    @Resource
    private IBseCustomerTyService bseCustomerTyService;

    @POST
    @Path("/v1/getPriceQueryResult")
    public ResponseBaseEntity<PriceQueryResult> getPriceQueryResult(PriceQueryParam baseTyParam){

        //入参校验
        if (baseTyParam == null || StringUtil.isEmpty(baseTyParam.getOriginCode()) || StringUtil.isEmpty(baseTyParam.getDestCode())
                || (StringUtil.isEmpty(baseTyParam.getParTransTypeCode()) && StringUtil.isEmpty(baseTyParam.getSubTransTypeCode()))) {
            return null;
        }

        //查询历史价格必须提供时间
        if (baseTyParam.isHistory() && baseTyParam.getBillTime() == null) {
            throw new BusinessException("查询历史价格必须提供开单时间", "查询历史价格必须提供开单时间");
        }

        PriceQueryResult result = null;
        boolean isUseEihPrice = false;//是否使用易到家价格模式
        if(CplbConstant.TYPE_YRH.equals(baseTyParam.getParTransTypeCode())
                || CplbConstant.TYPE_YAZ.equals(baseTyParam.getParTransTypeCode())
                || CplbConstant.TYPE_YBG.equals(baseTyParam.getParTransTypeCode())){
            //易到家默认使用易到家模式
            isUseEihPrice = true;
            // 客户编号不为空
            if(!StringUtil.isEmpty(baseTyParam.getCustNo())){
                // 判断是否使用“定日达”价格
                BseCustomerTyEntity bseCustomer = bseCustomerTyService.queryBseCustomerByCustNo(baseTyParam.getCustNo());
                // 如果使用“定时达”价格
                if(bseCustomer != null && MiserConstants.ACTIVE.equals(bseCustomer.getUseTingriEasyHome())){
                    isUseEihPrice=true;
                }
            }
        }

        //使用易到家查询价格
        if (isUseEihPrice) {
            PriceEasyHomeQueryResult eihPrice = queryEasyInHousePrice(baseTyParam);// 如果查到了“易到家”的价格，否则再查询“定日达”的价格
            if(eihPrice != null && !(eihPrice.getStandardWeight() == null
                    && eihPrice.getCorpWeight() == null && eihPrice.getCustomerWeight() == null)){
                result = new PriceQueryResult();
                result.setUsePriceType(PriceConstants.USE_PRICE_EASYHOME);
                result.setPriceEasyHomeQueryResult(eihPrice);
            } else { //没有易到家的价格,设置产品为定日达
                baseTyParam.setParTransTypeCode(ProdTypeConstant.TYPE_DRD);
            }
        }

        //如果result还为null,说明不是使用易到家价格或没有定义易到家价格,使用传统价格查询
        if (result == null) {
            PriceQueryResult traditionalPrice=this.queryTraditionalPrice(baseTyParam);
            if(traditionalPrice != null){
                traditionalPrice.setUsePriceType(PriceConstants.USE_PRICE_TYPE_DRD);
                result = traditionalPrice;
            }
        }

        ResponseBaseEntity<PriceQueryResult> response=new ResponseBaseEntity<PriceQueryResult>();
        response.setErrorCode(RestErrorCodeConstants.STATUS_SUCCESS);
        response.setErrorMessage(RestErrorCodeConstants.STATUS_SUCCESS_INFO);
        response.setResult(result);
        return  response;
    }

	/**
     * 查询传统的运费定义
     * @param baseTyParam
     * @return
     * @author 陈宇霖
     * @date 2016年07月27日23:22:16
     */
    public PriceQueryResult queryTraditionalPrice(PriceQueryParam baseTyParam){

        //根据策略重置产品类型
        restTransportTypeByStrategy(baseTyParam);

        //重置之后如果产品为空,说明策略有问题,直接返回空
        if (StringUtil.isEmpty(baseTyParam.getTransTypeCode())) {
            return null;
        }

        PriceQueryResult result = priceTyService.getPriceQueryResultByParam(baseTyParam);

        //如果策略是先子后父,子没有查询到就修改产品后再查询
        if(result == null && baseTyParam.getStgyTransType().intValue() == PCTyConstans.STGY_TRANSTYPE_2
                && !baseTyParam.getTransTypeCode().equals(baseTyParam.getParTransTypeCode())  //前面查询的子产品和父产品不相等的时候才查询
                && !StringUtil.isEmpty(baseTyParam.getParTransTypeCode())){
            baseTyParam.setTransTypeCode(baseTyParam.getParTransTypeCode());
            result = priceTyService.getPriceQueryResultByParam(baseTyParam);
        }

        return result;
    }

	/**
     * 查询易到家快递模式运费定义
     * @param baseTyParam
     * @return
     * @author 陈宇霖
     * @date 2016年07月27日23:23:06
     */
    public PriceEasyHomeQueryResult queryEasyInHousePrice(PriceQueryParam baseTyParam){

        //根据策略重置产品类型
        restTransportTypeByStrategy(baseTyParam);

        //重置之后如果产品为空,说明策略有问题,直接返回空
        if (StringUtil.isEmpty(baseTyParam.getTransTypeCode())) {
            return null;
        }

        PriceEasyHomeQueryResult result = priceEasyHomeTyService.getPriceSectionQueryResultByParam(baseTyParam);

        if(result == null && baseTyParam.getStgyTransType().intValue() == PCTyConstans.STGY_TRANSTYPE_2
                && !baseTyParam.getTransTypeCode().equals(baseTyParam.getParTransTypeCode()) //前面查询的子产品和父产品不相等的时候才查询
                && !StringUtil.isEmpty(baseTyParam.getParTransTypeCode())){
            baseTyParam.setTransTypeCode(baseTyParam.getParTransTypeCode());
            result = priceEasyHomeTyService.getPriceSectionQueryResultByParam(baseTyParam);
        }
        return result;
    }

	/**
     * 根据查询策略重置运输类型
     * @param param
     * @author 陈宇霖
     * @date 2016年07月27日23:19:43
     */
    private void restTransportTypeByStrategy (PriceQueryParam param) {
        //如果没有设置策略,默认先查子,再查父
        if(param.getStgyTransType() == null){
            param.setStgyTransType(PCTyConstans.STGY_TRANSTYPE_2);
        }
        param.setTransTypeCode(null);
        switch (param.getStgyTransType().intValue()) {
            case PCTyConstans.STGY_TRANSTYPE_0: //按父产品查询
                param.setTransTypeCode(param.getParTransTypeCode());
                break;
            case PCTyConstans.STGY_TRANSTYPE_1: //按子产品查询
                param.setTransTypeCode(param.getSubTransTypeCode());
                break;
            case PCTyConstans.STGY_TRANSTYPE_2: //先子后父
                if (!StringUtil.isEmpty(param.getSubTransTypeCode())) {
                    param.setTransTypeCode(param.getSubTransTypeCode());
                } else {
                    param.setTransTypeCode(param.getParTransTypeCode());
                }
                break;
        }
    }

}

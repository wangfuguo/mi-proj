package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.*;
import com.hoau.miser.module.api.itf.api.shared.define.PCTyConstans;
import com.hoau.miser.module.api.itf.api.shared.domain.*;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceEasyHomeQueryResult;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.util.BigDecimalUtil;
import com.hoau.miser.module.api.itf.server.util.PmsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceEasyHomeTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: 易到家价格
 * @date 2016/7/5 16:22
 */
@Service
public class PriceEasyHomeTyService implements IPriceEasyHomeTyService {

    @Resource
    private IPriceStandardSectionTyService priceStandardSectionTyService;
    @Resource
    private IPriceCorpSectionTyService priceCorpSectionTyService;
    @Resource
    private IPriceCustSectionTyService priceCustSectionTyService;
    @Resource
    private IBseCustomerTyService bseCustomerTyService;
    @Resource
    private IPriceEventTyService priceEventTyService;

    @Resource
    private IDiscountTyService discountTyService;


    @Override
    public PriceEasyHomeQueryResult getPriceSectionQueryResultByParam(PriceQueryParam baseTyParam) {
        PriceQueryParam baseBak = new PriceQueryParam();
        BeanUtils.copyProperties(baseTyParam, baseBak);

        //体积折算重量，与实际重量对比，取大。折算公式为 体积（立方厘米）/4500
        baseBak.setWeightNo(baseBak.getWeightNo().max(BigDecimalUtil.setDefaultVale(baseBak.getVolumeNo()).multiply(new BigDecimal(1000000)).divide(new BigDecimal(4500), 0, BigDecimal.ROUND_CEILING)));
        PriceEasyHomeQueryResult result=new PriceEasyHomeQueryResult();

        //分段标准价格
        PriceStandardSectionTyEntity priceStandardSectionTyEntity=this.getPriceStandardSectionByParam(baseBak);
        if(priceStandardSectionTyEntity==null) return null;
        //分段客户价格
        PriceCustSectionConfTyEntity priceCustSectionConfTyEntity=null;
        //分段网点价格
        PriceCorpSectionTyEntity priceCorpSectionTyEntity=this.getPriceCorpSectionByParam(baseBak);
        if(StringUtil.isNotEmpty(baseBak.getCustNo())){
            priceCustSectionConfTyEntity=this.getPriceCustSectionConfByParam(baseBak);
        }
        //设置标准价格
        Map<String,BigDecimal> standardMap= getCurrSection(baseBak.getWeightNo(),priceStandardSectionTyEntity);
        result.setStandardWeight(standardMap.get("weight"));
        result.setStandardWeightPrice(standardMap.get("weightPrice"));
        result.setStandardAddPrice(standardMap.get("addWeightPrice"));
        //设置网点
        if(priceCorpSectionTyEntity!=null) {
            Map<String, BigDecimal> priceCorpMap = getCurrSection(baseBak.getWeightNo(), priceCorpSectionTyEntity);
            result.setCorpWeight(priceCorpMap.get("weight"));
            result.setCorpWeightPrice(priceCorpMap.get("weightPrice"));
            result.setCorpAddPrice(priceCorpMap.get("addWeightPrice"));
        }
        //设置客户
        if(priceCustSectionConfTyEntity!=null&&priceCustSectionConfTyEntity.getCustSectionTyEntity()!=null){
            Map<String, BigDecimal> priceMap = getCurrSection(baseBak.getWeightNo(), priceCustSectionConfTyEntity.getCustSectionTyEntity().getPriceCustSubSectionTyEntity());
            result.setCustomerWeight(priceMap.get("weight"));
            result.setCustomerWeightPrice(priceMap.get("weightPrice"));
            result.setCustomerAddPrice(priceMap.get("addWeightPrice"));
        }

        BigDecimal weightDiscountMin=BigDecimal.valueOf(1);
        //拿到活动折扣
        List<List<PriceSectionSubEntity>> eventFreightSections = this.getEventFreightSection(baseBak);
        BigDecimal eventWeightDiscount = this.getMinInSections(eventFreightSections, PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo());

        weightDiscountMin=BigDecimalUtil.min(weightDiscountMin,eventWeightDiscount,1);

        //设置分段价格
        BigDecimal minWeightPrice=null;
        BigDecimal minAddPrice=null;
        BigDecimal weight=null;

        if(priceCustSectionConfTyEntity!=null&&priceCustSectionConfTyEntity.getCustSectionTyEntity()!=null){
            weightDiscountMin = BigDecimalUtil.min(weightDiscountMin, discountTyService.getMinDiscount(priceCustSectionConfTyEntity.getCustSectionTyEntity().getPriceCustSubSectionTyEntity().getFreightSectionCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo()), 1);
            minWeightPrice=result.getCustomerWeightPrice().multiply(weightDiscountMin);
            minAddPrice=result.getCustomerAddPrice().multiply(weightDiscountMin);
            weight=result.getCustomerWeight();
        }else if(priceCorpSectionTyEntity!=null){
            weightDiscountMin = BigDecimalUtil.min(weightDiscountMin, discountTyService.getMinDiscount(priceCorpSectionTyEntity.getFreightSectionCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo()), 1);
            minWeightPrice=result.getCorpWeightPrice().multiply(weightDiscountMin);
            minAddPrice=result.getCorpAddPrice().multiply(weightDiscountMin);
            weight=result.getCorpWeight();
        }else{
            minWeightPrice=(result.getStandardWeightPrice() == null ? BigDecimal.valueOf(0) : result.getStandardWeightPrice()).multiply(weightDiscountMin);
            minAddPrice=(result.getStandardAddPrice() == null ? BigDecimal.valueOf(0) : result.getStandardAddPrice()).multiply(weightDiscountMin);
            weight=result.getStandardWeight();
        }
        //如果外部折扣不为空,使用标准价格进行打折
        if (baseBak.getOmsFreightDiscount() != null && baseBak.getOmsFreightDiscount().doubleValue() > 0) {
            BigDecimal omsMinWeightPrice = result.getStandardWeightPrice() == null ? new BigDecimal(0) : result.getStandardWeightPrice().multiply(baseBak.getOmsFreightDiscount());
            BigDecimal omsMinAddPrice = result.getStandardAddPrice() == null ? new BigDecimal(0) : result.getStandardAddPrice().multiply(baseBak.getOmsFreightDiscount());
            minWeightPrice = minWeightPrice.min(omsMinWeightPrice);
            minAddPrice = minAddPrice.min(omsMinAddPrice);
        }
        result.setMinWeightPrice(minWeightPrice);
        result.setMinAddPrice(minAddPrice);
        result.setWeight(weight);
        return result;
    }

    @Override
    public PriceStandardSectionTyEntity getPriceStandardSectionByParam(PriceQueryParam baseTyParam) {
        return priceStandardSectionTyService.queryByPriceSectionQueryParam(baseTyParam);
    }

    @Override
    public PriceCorpSectionTyEntity getPriceCorpSectionByParam(PriceQueryParam baseTyParam) {
        return priceCorpSectionTyService.queryPriceSectionQueryParam(baseTyParam);
    }

    /**
     * 得到当前分段信息（首重，首重单价，续重单价）
     * @param weightNo
     * @param baseTyEntity
     * @return
     */
    private Map<String,BigDecimal> getCurrSection(BigDecimal weightNo,PriceSectionBaseTyEntity baseTyEntity ){
        Map<String,BigDecimal> map=new HashMap<String, BigDecimal>();
        BigDecimal firstWeight=BigDecimalUtil.setDefaultVale(baseTyEntity.getFirstWeight());
        BigDecimal secondWeight= BigDecimalUtil.setDefaultVale(baseTyEntity.getSecondWeight()) ;
        BigDecimal thirdWeight=BigDecimalUtil.setDefaultVale(baseTyEntity.getThirdWeight());

        if(weightNo.compareTo(thirdWeight)>0 && thirdWeight.compareTo(BigDecimal.ZERO) > 0){//第一分段
            map.put("weight",thirdWeight);
            map.put("weightPrice",baseTyEntity.getThirdWeightPrice());
            map.put("addWeightPrice",baseTyEntity.getThirdAddWeightPrice());
        }else if(weightNo.compareTo(secondWeight)>0 && secondWeight.compareTo(BigDecimal.ZERO) > 0){//第二分段
            map.put("weight",secondWeight);
            map.put("weightPrice",baseTyEntity.getSecondWeightPrice());
            map.put("addWeightPrice",baseTyEntity.getSecondAddWeightPrice());
        }else{
            map.put("weight",firstWeight);
            map.put("weightPrice",baseTyEntity.getFirstWeightPrice());
            map.put("addWeightPrice",baseTyEntity.getFirstAddWeightPrice());
        }
        return map;
    }

    @Override
    public PriceCustSectionConfTyEntity getPriceCustSectionConfByParam(PriceQueryParam baseTyParam) {
        if (StringUtil.isEmpty(baseTyParam.getCustNo())) {
            return null;
        }

        PriceQueryParam baseBak = new PriceQueryParam();
        BeanUtils.copyProperties(baseTyParam, baseBak);

        //设置是否启用客户价格
        BseCustomerTyEntity bseCustomer = bseCustomerTyService.queryBseCustomerByCustNo(baseBak.getCustNo());
        //是否启用客户价格
        boolean isUseCustomerPc = PmsUtils.isEnablePcOrDiscount(bseCustomer, "PC");
        //是否启用客户折扣
        boolean isUseCustomerDiscount = PmsUtils.isEnablePcOrDiscount(bseCustomer, "DISCOUNT");
        PriceCustSectionConfTyEntity confTyEntity=new PriceCustSectionConfTyEntity();
        confTyEntity.setUseCustomerDiscount(isUseCustomerDiscount);
        confTyEntity.setUseCustomerPc(isUseCustomerPc);
        if(isUseCustomerPc){
            PriceCustSectionTyEntity priceCustSectionTyEntity= priceCustSectionTyService.queryPriceCustSectionTyByQueryParam(baseBak);
            confTyEntity.setCustSectionTyEntity(priceCustSectionTyEntity);
        }

        return confTyEntity;
    }

    /**
     * 拿到活动中的运费优惠分段集合
     *
     * @Param [queryParam]
     * @Return java.util.List<java.util.List<com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity>>
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/16 17:24
     * @Version v1
     */
    private List<List<PriceSectionSubEntity>> getEventFreightSection(PriceQueryParam baseTyParam) {
        return priceEventTyService.getEventSection(PmsUtils.convertPriceQueryParamToEventQueryParam(baseTyParam), PriceEventDiscountSubEntity.FEE_TYPE_FREIGHT);
    }
    /**
     * 得倒分段集合中最小的值
     *
     * @Param [sections, accodingItem, accodingValue]
     * @Return java.math.BigDecimal
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/22 9:51
     * @Version v1
     */
    private BigDecimal getMinInSections(List<List<PriceSectionSubEntity>> sections, String accodingItem, BigDecimal accodingValue) {
        if (sections == null) {
            return new BigDecimal(1);
        }
        BigDecimal minValues = new BigDecimal(1);
        for (List<PriceSectionSubEntity> list : sections) {

            for (PriceSectionSubEntity subEntity : list) {
                if (subEntity != null && subEntity.check(accodingItem, accodingValue)) {
                    minValues = minValues.min(subEntity.getPrice());
                }
            }
        }
        return minValues;
    }
}

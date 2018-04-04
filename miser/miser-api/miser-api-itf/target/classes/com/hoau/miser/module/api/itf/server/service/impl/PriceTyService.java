package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.*;
import com.hoau.miser.module.api.itf.api.shared.define.PCTyConstans;
import com.hoau.miser.module.api.itf.api.shared.define.ProdTypeConstant;
import com.hoau.miser.module.api.itf.api.shared.domain.*;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryResult;
import com.hoau.miser.module.api.itf.server.util.BigDecimalUtil;
import com.hoau.miser.module.api.itf.server.util.PmsUtils;
import com.hoau.miser.module.util.define.MiserConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: PriceTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/7 10:20
 */
@Service
public class PriceTyService implements IPriceTyService {
    @Resource
    private IPriceStandardTyService priceStandardTyService;
    @Resource
    private IPriceCorpTyService priceCorpTyService;
    @Resource
    private IPriceCustTyService priceCustTyService;
    @Resource
    private IBseCustomerTyService bseCustomerTyService;
    @Resource
    private IPricePickupFeeTyService pricePickupFeeTyService;
    @Resource
    private IOutLineTyService outLineTyService;
    @Resource
    private IPriceSectionTyService priceSectionTyService;
    @Resource
    private IPriceEventTyService priceEventTyService;
    @Resource
    private IDiscountCustomerTyService discountCustomerTyService;

    @Resource
    private IDiscountTyService discountTyService;

	/**
     * 获取标准价卡
     * @param baseTyParam
     * @return
     */
    public PriceStandardTyEntity getPriceStandardByParam(PriceQueryParam baseTyParam) {
        //保留原查询条件
        PriceQueryParam baseBak = new PriceQueryParam();
        BeanUtils.copyProperties(baseTyParam, baseBak);

        //【偏线】时取【经济快运】的数据。
        if (ProdTypeConstant.TYPE_PX.equals(baseBak.getTransTypeCode())) {
            baseBak.setTransTypeCode(ProdTypeConstant.TYPE_LD);
        }

        //获取标准价格
        PriceStandardTyEntity psEntity = this.priceStandardTyService.queryPriceStandardTyByQueryParam(baseBak);

        //偏线需要在经济快运的原价格上添加外发价格
        if (psEntity != null && ProdTypeConstant.TYPE_PX.equals(baseTyParam.getTransTypeCode())) {
            /**
             如果产品类型是【偏线】，且根据目的公司拿到外发偏线价格相关信息（重量，体积，最低收费）。
             将其值跟标准价格里相关信息相加后，赋值给标准价格（如：重量（新的重量单价）=重量（偏线）+重量）。
             **/
            OutLineTyEntity outLineTyEntity = this.outLineTyService.queryOutLineByEntity(baseTyParam.getDestCode());
            if (outLineTyEntity != null) {
                psEntity.setWeightPrice(psEntity.getWeightPrice().add(outLineTyEntity.getWeightRate()));
                psEntity.setVolumePrice(psEntity.getVolumePrice().add(outLineTyEntity.getVolumeRate()));
                psEntity.setLowestFee(psEntity.getLowestFee().add(outLineTyEntity.getLowestFee()));
            }
        }
        return psEntity;
    }

	/**
     * 获取网点价卡
     * @param baseTyParam
     * @return
     */
    public PriceCorpTyEntity getPriceCorpByParam(PriceQueryParam baseTyParam) {
        // 这里有生效的网点价格就算是启用网点价格
        //保留原查询条件
        PriceQueryParam baseBak = new PriceQueryParam();
        BeanUtils.copyProperties(baseTyParam, baseBak);

        //【偏线】时取【经济快运】的数据。
        if (ProdTypeConstant.TYPE_PX.equals(baseBak.getTransTypeCode())) {
            baseBak.setTransTypeCode(ProdTypeConstant.TYPE_LD);

        }
        PriceCorpTyEntity priceEntity = this.priceCorpTyService.queryPriceCorpTyByQueryParam(baseBak);
        if (priceEntity != null && ProdTypeConstant.TYPE_PX.equals(baseTyParam.getTransTypeCode())) {
            /**
             如果产品类型是【偏线】，且根据目的公司拿到外发偏线价格相关信息（重量，体积，最低收费）。
             将其值跟网点价格里相关信息相加后，赋值给网点价格（如：重量（新的重量单价）=重量（偏线）+重量）。
             **/
            OutLineTyEntity outLineTyEntity = this.outLineTyService.queryOutLineByEntity(baseTyParam.getDestCode());
            if (outLineTyEntity != null) {
                priceEntity.setWeightPrice(priceEntity.getWeightPrice().add(outLineTyEntity.getWeightRate()));
                priceEntity.setVolumePrice(priceEntity.getVolumePrice().add(outLineTyEntity.getVolumeRate()));
                priceEntity.setLowestFee(priceEntity.getLowestFee().add(outLineTyEntity.getLowestFee()));
            }
        }
        if (priceEntity != null) {
            String ownDiscountSectCode = discountTyService.getCorpFreightOwnDiscountSectCode(baseBak);
            //专属折扣优惠分段
            priceEntity.setFreightOwnDiscountSectCode(ownDiscountSectCode);
        }

        return priceEntity;
    }

	/**
     * 获取客户价卡
     * @param baseTyParam
     * @return
     */
    public PriceCustConfTyEntity getPriceCustConfByParam(PriceQueryParam baseTyParam) {
        PriceQueryParam baseBak = new PriceQueryParam();
        BeanUtils.copyProperties(baseTyParam, baseBak);
        if (StringUtil.isEmpty(baseTyParam.getCustNo())) {
            return null;
        }

        //设置是否启用客户价格
        BseCustomerTyEntity bseCustomer = bseCustomerTyService.queryBseCustomerByCustNo(baseBak.getCustNo());
        //是否启用客户价格
        boolean isUseCustomerPc = PmsUtils.isEnablePcOrDiscount(bseCustomer, "PC");
        //是否启用客户折扣
        boolean isUseCustomerDiscount = PmsUtils.isEnablePcOrDiscount(bseCustomer, "DISCOUNT");

        PriceCustTyEntity priceEntity = null;
        PriceCustConfTyEntity custConfTyEntity = new PriceCustConfTyEntity();
        if (isUseCustomerPc) {
            boolean isPxPrice = true;//是否查到偏线价格
            priceEntity = this.priceCustTyService.queryPriceCustTyByQueryParam(baseBak);
            if (priceEntity == null && ProdTypeConstant.TYPE_PX.equals(baseBak.getTransTypeCode())) {
                isPxPrice = false;
                //如果未查到，且当前是偏线产品，就查经济快运的价格
                baseBak.setTransTypeCode(ProdTypeConstant.TYPE_LD);
                priceEntity = this.priceCustTyService.queryPriceCustTyByQueryParam(baseBak);
            }
            if (priceEntity != null && !isPxPrice) {
                /**
                 如果产品类型是【偏线】，且根据目的公司拿到外发偏线价格相关信息（重量，体积，最低收费）。
                 将其值跟价格里相关信息相加后，赋值给网点价格（如：重量（新的重量单价）=重量（偏线）+重量）。
                 **/
                OutLineTyEntity outLineTyEntity = this.outLineTyService.queryOutLineByEntity(baseTyParam.getDestCode());
                if (outLineTyEntity != null) {
                    priceEntity.getPriceCustSubTyEntity().setWeightPrice(priceEntity.getPriceCustSubTyEntity().getWeightPrice().add(outLineTyEntity.getWeightRate()));
                    priceEntity.getPriceCustSubTyEntity().setVolumePrice(priceEntity.getPriceCustSubTyEntity().getVolumePrice().add(outLineTyEntity.getVolumeRate()));
                    priceEntity.getPriceCustSubTyEntity().setLowestFee(priceEntity.getPriceCustSubTyEntity().getLowestFee().add(outLineTyEntity.getLowestFee()));
                }
            }

        }
        boolean defaultShowDiscountValue=false; //默认不显示折扣后价格
        String discountPriorityType = "CHEAPEST"; //默认客户折扣类型为最低折扣
        DiscountCustomerTyEntity dcEntity = discountCustomerTyService.queryDiscountCustomerByParam(baseBak);
        if(dcEntity!=null) {
            discountPriorityType = dcEntity.getDiscountPriorityType();
            defaultShowDiscountValue=dcEntity.getDefaultShowDiscountValue().equals(MiserConstants.ACTIVE);
        }
        custConfTyEntity.setDiscountPriorityType(discountPriorityType);
        custConfTyEntity.setShowDiscountPrice(defaultShowDiscountValue);

        if (isUseCustomerDiscount) {//如果使用客户折扣，将对应折扣放入价格实体，加是否显示折扣价格的标志。
            custConfTyEntity.setFreightOwnDiscountSectCode(discountTyService.getCustFreightOwnDiscountSectCode(baseBak));
            custConfTyEntity.setAddFeeOwnDiscountSectCode(discountTyService.getCustAddFeeOwnDiscountSectCode(baseBak));
        }

        custConfTyEntity.setUseCustomerDiscount(isUseCustomerDiscount);
        custConfTyEntity.setUseCustomerPc(isUseCustomerPc);
        if (priceEntity != null) {
            custConfTyEntity.setCustTyEntity(priceEntity);
        }
        return custConfTyEntity;
    }

	/**
     * 根据查询条件获取价卡数据入口
     * @param baseTyParam
     * @return
     * @author 廖文强
     * @date 2016年07月28日10:39:17
     */
    public PriceQueryResult getPriceQueryResultByParam(PriceQueryParam baseTyParam) {

        PriceQueryParam baseBak = new PriceQueryParam();
        BeanUtils.copyProperties(baseTyParam, baseBak);

        PriceQueryResult result = new PriceQueryResult();

        //获取价卡数据:标准、网点、客户、当前使用
        Object[] priceCards = this.getCurrPrice(baseBak);
        if (priceCards == null || priceCards.length != 4) {
            return null;
        }

        //标准价卡
        PriceStandardTyEntity priceStandard = (PriceStandardTyEntity) priceCards[0];
        //没有获取到标准价卡的,直接返回
        if (priceStandard == null) {
            return null;
        }

        //网点价卡
        PriceCorpTyEntity priceCorp = (PriceCorpTyEntity)priceCards[1];

        //客户价卡
        PriceCustConfTyEntity priceCust = (PriceCustConfTyEntity)priceCards[2];

        //当前使用的价卡
        PriceTyEntity currPrice = (PriceTyEntity) priceCards[3];

        BigDecimal weightPrice = currPrice.getWeightPrice();//重量单价
        BigDecimal volumePrice = currPrice.getVolumePrice();//体积单价

        //提货费的计算
        BigDecimal[] ppFee = this.getPricePickupFee(baseBak, bseCustomerTyService.queryBseCustomerByCustNo(baseBak.getCustNo()));
        weightPrice = weightPrice.add(ppFee[0]);//重量单价(+提货费)
        volumePrice = volumePrice.add(ppFee[1]);//体积单价(+提货费)

        //越发越惠最低折扣
        BigDecimal discountPriValueMin = null;
        if (StringUtil.isNotEmpty(baseBak.getCustNo())) {
            //越发越惠折扣(经济快运，定日达)
            discountPriValueMin = discountTyService.getDiscountPriValueMin(baseBak.getCustNo(), baseBak.getBillTime());
        }

        /** 重量最低单价 **/
        BigDecimal weightPriceMin = new BigDecimal(0);
        /** 重量最低单价 **/
        BigDecimal volumePriceMin = new BigDecimal(0);
        /** 重量折扣最低 **/
        BigDecimal weightDiscountMin = new BigDecimal(1);
        /** 体积折扣最低 **/
        BigDecimal volumeDiscountMin = new BigDecimal(1);

        //获取活动优惠分段,并计算出最小的活动折扣
        List<List<PriceSectionSubEntity>> eventFreightSections = this.getEventFreightSection(baseTyParam);
        BigDecimal eventWeightDiscount = this.getMinInSections(eventFreightSections, PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo());
        BigDecimal eventVolumnDiscount = this.getMinInSections(eventFreightSections, PCTyConstans.CALCULATION_TYPE_VOLUMN, baseBak.getVolumeNo());

        result.setShowWeightPrice(weightPrice);
        result.setShowVolumePrice(volumePrice);

        //有客户折扣，且启用,且显示折扣,则在原显示单价上乘以客户折扣或越发越惠折扣
        if (priceCust != null && priceCust.isUseCustomerPc() && priceCust.isShowDiscountPrice() && !StringUtil.isEmpty(priceCust.getFreightOwnDiscountSectCode())) {
            //取折扣最小值：越发越惠不存在时，才取运费优惠折扣最小值
            weightPriceMin = weightPrice.multiply(discountPriValueMin != null ? discountPriValueMin
                    : discountTyService.getMinDiscount(priceCust.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo()));
            volumePriceMin = volumePrice.multiply(discountPriValueMin != null ? discountPriValueMin
                    : discountTyService.getMinDiscount(priceCust.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_VOLUMN, baseBak.getVolumeNo()));
            result.setShowWeightPrice(weightPriceMin);
            result.setShowVolumePrice(volumePriceMin);
        }
        if (discountPriValueMin == null) {//越发越惠不存在
            if (priceCust != null && priceCust.isUseCustomerPc()) {
                /*
                折扣优先类型=原来dc的价卡状态(
                CHEAPEST:最低折扣
                EVENT:活动折扣
                CUSTOMER:客户折扣
                0:完全锁定（默认）
                )
                */
                if ("CUSTOMER".equals(priceCust.getDiscountPriorityType()) || "0".equals(priceCust.getDiscountPriorityType())) {//客户折扣
                    //折扣与运费分段折扣比较取最小值
                    weightDiscountMin = BigDecimalUtil.min(currPrice.getWeightDiscount(),
                            discountTyService.getMinDiscount(currPrice.getFreightDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo()), 1);
                    volumeDiscountMin = BigDecimalUtil.min(currPrice.getVolumeDiscount(),
                            discountTyService.getMinDiscount(currPrice.getFreightDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_VOLUMN, baseBak.getVolumeNo()), 1);
                    if (StringUtil.isNotEmpty(priceCust.getFreightOwnDiscountSectCode())) {
                        //如果使用的客户价卡,则需要和客户价卡中的折扣比最小值
                        if (PCTyConstans.PC_TYPE_CUST == currPrice.getType()) {
                            weightDiscountMin = BigDecimalUtil.min(weightDiscountMin,
                                    discountTyService.getMinDiscount(priceCust.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo()), 1);
                            volumeDiscountMin = BigDecimalUtil.min(volumeDiscountMin,
                                    discountTyService.getMinDiscount(priceCust.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_VOLUMN, baseBak.getVolumeNo()), 1);
                        } else { //当前价卡不是客户价卡的,直接取客户折扣中的配置
                            weightDiscountMin = discountTyService.getMinDiscount(priceCust.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo());
                            volumeDiscountMin = discountTyService.getMinDiscount(priceCust.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_VOLUMN, baseBak.getVolumeNo());
                        }
                    }
                    weightPriceMin = weightPrice.multiply(weightDiscountMin);
                    volumePriceMin = volumePrice.multiply(volumeDiscountMin);
                } else if ("CHEAPEST".equals(priceCust.getDiscountPriorityType())) {//最低折扣
                    //活动，货物折扣，运费分段折扣取最小值
                    weightDiscountMin = BigDecimalUtil.min(BigDecimalUtil.min(currPrice.getWeightDiscount(),
                            discountTyService.getMinDiscount(currPrice.getFreightDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo()), 1),
                            eventWeightDiscount, 1);
                    volumeDiscountMin = BigDecimalUtil.min(BigDecimalUtil.min(currPrice.getVolumeDiscount(),
                            discountTyService.getMinDiscount(currPrice.getFreightDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_VOLUMN, baseBak.getVolumeNo()), 1),
                            eventVolumnDiscount, 1);

                    weightPriceMin = weightPrice.multiply(weightDiscountMin);
                    volumePriceMin = volumePrice.multiply(volumeDiscountMin);
                    if (priceCust.getCustTyEntity() != null) {
                        //客户的重量体积折扣与客户运费分段折扣取最小值
                        BigDecimal custWeightDiscount = BigDecimalUtil.min(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getWeightDiscount(),
                                discountTyService.getMinDiscount(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getFreightSectionCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo())
                                , 1);
                        BigDecimal custVolumeDiscount = BigDecimalUtil.min(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getVolumeDiscount(),
                                discountTyService.getMinDiscount(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getFreightSectionCode(), PCTyConstans.CALCULATION_TYPE_VOLUMN, baseBak.getVolumeNo())
                                , 1);
                        //加上提货费
                        weightPriceMin = weightPriceMin.min(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getWeightPrice().add(ppFee[0]).multiply(custWeightDiscount));
                        volumePriceMin = volumePriceMin.min(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getVolumePrice().add(ppFee[1]).multiply(custVolumeDiscount));
                    }
                    //标准价格下的网点折扣
                    if (priceCorp != null) {
                        weightPriceMin = weightPriceMin.min(
                                priceStandard.getWeightPrice().add(ppFee[0]).multiply(
                                        discountTyService.getMinDiscount(priceCorp.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo())
                                )
                        );
                        volumePriceMin = volumePriceMin.min(
                                priceStandard.getVolumePrice().add(ppFee[1]).multiply(
                                        discountTyService.getMinDiscount(priceCorp.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_VOLUMN, baseBak.getVolumeNo())
                                )
                        );
                    }

                    //标准价格下的客户专属折扣折扣
                    BigDecimal custFreightOwnWeightDiscount = discountTyService.getMinDiscount(priceCust.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo());
                    BigDecimal custFreightOwnVolumeDiscount = discountTyService.getMinDiscount(priceCust.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_VOLUMN, baseBak.getVolumeNo());
                    weightPriceMin = weightPriceMin.min(
                            priceStandard.getWeightPrice().add(ppFee[0]).multiply(
                                    custFreightOwnWeightDiscount
                            )
                    );
                    volumePriceMin = volumePriceMin.min(
                            priceStandard.getVolumePrice().add(ppFee[1]).multiply(
                                    custFreightOwnVolumeDiscount
                            )
                    );

                }
            } else {//没有客户价格的

                //折扣与运费分段折扣比较取最小值
                weightDiscountMin = BigDecimalUtil.min(currPrice.getWeightDiscount(),
                        discountTyService.getMinDiscount(currPrice.getFreightDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo()), 1);
                volumeDiscountMin = BigDecimalUtil.min(currPrice.getVolumeDiscount(),
                        discountTyService.getMinDiscount(currPrice.getFreightDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_VOLUMN, baseBak.getVolumeNo()), 1);
                // 如果有网点折扣，还需判断网点的折扣(网点专属价卡不再享受网点折扣了)
                if (priceCorp != null && PCTyConstans.PC_TYPE_CORP != currPrice.getType()) {
                    if (StringUtil.isNotEmpty(priceCorp.getFreightOwnDiscountSectCode())) {
                        weightDiscountMin = BigDecimalUtil.min(weightDiscountMin,
                                discountTyService.getMinDiscount(priceCorp.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_WEIGHT, baseBak.getWeightNo()), 1);
                        volumeDiscountMin = BigDecimalUtil.min(volumeDiscountMin,
                                discountTyService.getMinDiscount(priceCorp.getFreightOwnDiscountSectCode(), PCTyConstans.CALCULATION_TYPE_VOLUMN, baseBak.getVolumeNo()), 1);
                    }
                }
                weightDiscountMin = weightDiscountMin.min(eventWeightDiscount);
                volumeDiscountMin = volumeDiscountMin.min(eventVolumnDiscount);
                weightPriceMin = weightPrice.multiply(weightDiscountMin);
                volumePriceMin = weightPrice.add(ppFee[1]).multiply(volumeDiscountMin);
            }
        } else { //越发越惠的直接使用越发越惠的折扣设置最小值
            weightPriceMin = weightPrice.multiply(discountPriValueMin);
            volumePriceMin = volumePrice.multiply(discountPriValueMin);
        }

        //价格赋值
        result.setStandardWeightPrice(priceStandard.getWeightPrice().add(ppFee[0]).setScale(2, BigDecimal.ROUND_HALF_UP));
        result.setStandardVolumePrice(priceStandard.getVolumePrice().add(ppFee[1]).setScale(0, BigDecimal.ROUND_HALF_UP));

        if (priceCorp != null) {
            result.setCorpWeightPrice(priceCorp.getWeightPrice().add(ppFee[0]).setScale(2, BigDecimal.ROUND_HALF_UP));
            result.setCorpVolumePrice(priceCorp.getVolumePrice().add(ppFee[1]).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (priceCust != null && priceCust.getCustTyEntity() != null) {
            result.setCustomerWeightPrice(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getWeightPrice().add(ppFee[0]).setScale(2, BigDecimal.ROUND_HALF_UP));
            result.setCustomerVolumePrice(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getVolumePrice().add(ppFee[1]).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        //如果有OMS折扣的,需要使用标准价格与网点价格进行处理
        if (baseBak.getOmsFreightDiscount() != null && baseBak.getOmsFreightDiscount().doubleValue() > 0) {
            BigDecimal omsMinWeightPrice = priceStandard.getWeightPrice().add(ppFee[0]).multiply(baseBak.getOmsFreightDiscount());
            BigDecimal omsMinVolumePrice = priceStandard.getVolumePrice().add(ppFee[1]).multiply(baseBak.getOmsFreightDiscount());
            weightPriceMin = weightPriceMin.min(omsMinWeightPrice);
            volumePriceMin = volumePriceMin.min(omsMinVolumePrice);
        }

        result.setMinWeightPrice(weightPriceMin.setScale(2, BigDecimal.ROUND_HALF_UP)); //重量单价四舍五入保留两位小数
        result.setMinVolumePrice(volumePriceMin.setScale(0, BigDecimal.ROUND_HALF_UP)); //体积单价四舍五入保留整数
        result.setMinFreightCharge(BigDecimalUtil.setDefaultVale(currPrice.getLowestFee()).add(BigDecimalUtil.setDefaultVale(ppFee[2])).setScale(0, BigDecimal.ROUND_CEILING)); //运费向上取整
        return result;
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
     * 获取提货上门费用
     *
     * @Param [baseTyParam, bseCustomer]
     * @Return java.math.BigDecimal[]
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/12 11:17
     * @Version v1
     */
    public BigDecimal[] getPricePickupFee(PriceQueryParam baseTyParam, BseCustomerTyEntity bseCustomer) {
        PricePickupFeeTyEntity pickupFee = null;
        BigDecimal[] result = new BigDecimal[3];
        boolean isUseFee = true;//是否用提货费
        if (PCTyConstans.TAKE_DELIVERY_TYPE_1.equals(baseTyParam.getTakeDelyType())) {
            //上门提货
            if (bseCustomer != null && MiserConstants.ACTIVE.equals(bseCustomer.getActive())) {//有效
                Date currDate = new Date();
                if (PmsUtils.isTimeCheck(bseCustomer.getPcStartTime(), bseCustomer.getPcEndTime(), currDate)) {//价格时间判断
                    isUseFee = false;//在价格有效期内，不用
                }
            }
        } else {
            isUseFee = false;//客户自送，不用
        }
        if (isUseFee) pickupFee = pricePickupFeeTyService.queryPricePickupFeeByQueryParam(baseTyParam);
        if (pickupFee != null) {
            result[0] = new BigDecimal(pickupFee.getWeightPrice());
            result[1] = new BigDecimal(pickupFee.getVolumnPrice());
            result[2] = new BigDecimal(pickupFee.getMinMoney());
        } else {
            result[0] = new BigDecimal(0);
            result[1] = new BigDecimal(0);
            result[2] = new BigDecimal(0);
        }
        return result;
    }

	/**
     * 获取价卡信息
     * @param baseTyParam
     * @return 标准价卡、网点价卡、客户价卡、当前使用的价卡
     * @author 陈宇霖
     * @date 2016年07月28日08:52:01
     */
    public Object[] getCurrPrice(PriceQueryParam baseTyParam) {

        PriceStandardTyEntity priceStandard = null; //标准价卡
        PriceCorpTyEntity priceCorp = null; //网点价卡
        PriceCustConfTyEntity priceCust = null; //客户价卡
        PriceTyEntity currentPrice = null; //当前使用的价卡

        //保留原始参数不变
        PriceQueryParam baseBak = new PriceQueryParam();
        BeanUtils.copyProperties(baseTyParam, baseBak);

        priceStandard = this.getPriceStandardByParam(baseBak);//标准价格
        //没有获取到标准价卡的,直接返回
        if (priceStandard == null) {
            return new Object[4];
        }

        priceCorp = this.getPriceCorpByParam(baseBak);//网点价格

        //查询客户价卡
        BseCustomerTyEntity bseCustomer = null; //当前客户
        if (!StringUtil.isEmpty(baseBak.getCustNo())) {
            priceCust = this.getPriceCustConfByParam(baseBak);
            bseCustomer = bseCustomerTyService.queryBseCustomerByCustNo(baseBak.getCustNo());
        }

        if (priceCust != null && priceCust.isUseCustomerPc() && priceCust.getCustTyEntity() != null) {
            currentPrice = new PriceTyEntity();
            currentPrice.setOriginCode(baseBak.getOriginCode());
            currentPrice.setDestCode(baseBak.getDestCode());
            currentPrice.setTransType(baseBak.getTransTypeCode());
            currentPrice.setType(PCTyConstans.PC_TYPE_CUST);
            currentPrice.setWeightPrice(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getWeightPrice());
            currentPrice.setVolumePrice(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getVolumePrice());
            currentPrice.setAddFee(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getAddFee());
            currentPrice.setLowestFee(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getLowestFee());
            currentPrice.setWeightDiscount(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getWeightDiscount());
            currentPrice.setVolumeDiscount(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getVolumeDiscount());
            //运费优惠分段
            currentPrice.setFreightDiscountSectCode(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getFreightSectionCode());
            //专属折扣优惠分段
            if (priceCust.isUseCustomerDiscount() && !StringUtil.isEmpty(priceCust.getFreightOwnDiscountSectCode())) {
                currentPrice.setFreightOwnDiscountSectCode(priceCust.getFreightOwnDiscountSectCode());
            }
        } else if (priceCorp != null) {
            currentPrice = new PriceTyEntity();
            currentPrice.setOriginCode(baseBak.getOriginCode());
            currentPrice.setDestCode(baseBak.getDestCode());
            currentPrice.setTransType(baseBak.getTransTypeCode());
            currentPrice.setType(PCTyConstans.PC_TYPE_CORP);
            currentPrice.setWeightPrice(priceCorp.getWeightPrice());
            currentPrice.setVolumePrice(priceCorp.getVolumePrice());
            currentPrice.setAddFee(priceCorp.getAddFee());
            currentPrice.setLowestFee(priceCust.getCustTyEntity().getPriceCustSubTyEntity().getLowestFee());
            String ownDiscountSectCode = discountTyService.getCorpFreightOwnDiscountSectCode(baseBak);
            //专属折扣优惠分段
            currentPrice.setFreightOwnDiscountSectCode(ownDiscountSectCode);

        } else {
            currentPrice = new PriceTyEntity();
            currentPrice.setOriginCode(baseBak.getOriginCode());
            currentPrice.setDestCode(baseBak.getDestCode());
            currentPrice.setTransType(baseBak.getTransTypeCode());
            currentPrice.setType(PCTyConstans.PC_TYPE_STAND);
            currentPrice.setWeightPrice(priceStandard.getWeightPrice());
            currentPrice.setVolumePrice(priceStandard.getVolumePrice());
            currentPrice.setAddFee(priceStandard.getAddFee());
            currentPrice.setLowestFee(priceStandard.getLowestFee());
        }
        Object[] result = new Object[]{priceStandard, priceCorp, priceCust, currentPrice};
        return result;
    }

}

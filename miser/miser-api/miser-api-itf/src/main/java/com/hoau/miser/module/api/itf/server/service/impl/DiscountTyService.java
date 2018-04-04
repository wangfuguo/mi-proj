package com.hoau.miser.module.api.itf.server.service.impl;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.api.itf.api.server.*;
import com.hoau.miser.module.api.itf.api.shared.define.ProdTypeConstant;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCorpTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountCustomerTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.DiscountPrivilegeTyEntity;
import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity;
import com.hoau.miser.module.api.itf.api.shared.vo.PriceQueryParam;
import com.hoau.miser.module.api.itf.server.exception.ChargeException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: DiscountTyService
 * @Package com.hoau.miser.module.api.itf.server.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/17 14:49
 */
@Service
public class DiscountTyService implements IDiscountTyService {
    @Resource
    private IDiscountCustomerTyService discountCustomerTyService;
    @Resource
    private IDiscountCorpTyService discountCorpTyService;
    @Resource
    private IDiscountPrivilegeTyService discountPrivilegeTyService;

    @Resource
    private IPriceSectionTyService priceSectionTyService;

    /**
     * 得到客户的专属折扣运费优惠分段编号
     * @Param  [baseTyParam]
     * @Return java.lang.String
     * @Throws
     * @Author 廖文强
     * @Date 2016/6/17 15:38
     * @Version v1
     */
    public String getCustFreightOwnDiscountSectCode(PriceQueryParam baseTyParam){

        String freightOwnDiscountSectCode=null;
        PriceQueryParam baseBak=new PriceQueryParam() ;
        if(baseTyParam != null){
            BeanUtils.copyProperties(baseTyParam,baseBak);
        } else {
            baseBak = null;
        }
        DiscountCustomerTyEntity entity=discountCustomerTyService.queryDiscountCustomerByParam(baseBak);
        if(ProdTypeConstant.TYPE_PX.equals( baseBak.getTransTypeCode())&&entity==null){
            //偏线折扣不存在的时候再使用经济快运折扣
            baseBak.setTransTypeCode(ProdTypeConstant.TYPE_LD);
            entity=discountCustomerTyService.queryDiscountCustomerByParam(baseBak);
        }
        if(entity!=null){
            freightOwnDiscountSectCode=entity.getFreightSectionCode();
        }
        return freightOwnDiscountSectCode;
    }

    @Override
    public String getCustAddFeeOwnDiscountSectCode(PriceQueryParam baseTyParam) {
        String addFeeOwnDiscountSectCode=null;
        PriceQueryParam baseBak=new PriceQueryParam() ;
        if(baseTyParam != null){
            BeanUtils.copyProperties(baseTyParam,baseBak);
        } else {
            baseBak = null;
        }
        DiscountCustomerTyEntity entity=discountCustomerTyService.queryDiscountCustomerByParam(baseBak);
        if(ProdTypeConstant.TYPE_PX.equals( baseBak.getTransTypeCode())&&entity==null){
            //偏线折扣不存在的时候再使用经济快运折扣
            baseBak.setTransTypeCode(ProdTypeConstant.TYPE_LD);
            entity=discountCustomerTyService.queryDiscountCustomerByParam(baseBak);
        }
        if(entity!=null){
            addFeeOwnDiscountSectCode=entity.getAddSectionCode();
        }
        return addFeeOwnDiscountSectCode;
    }

    public String getCorpFreightOwnDiscountSectCode(PriceQueryParam baseTyParam) {
        String freightOwnDiscountSectCode=null;
        PriceQueryParam baseBak=new PriceQueryParam() ;
        if(baseTyParam != null){
            BeanUtils.copyProperties(baseTyParam,baseBak);
        } else {
            baseBak = null;
        }
        DiscountCorpTyEntity corpTyEntity=discountCorpTyService.queryDisCorpByParam(baseTyParam);
        if(corpTyEntity!=null){
            freightOwnDiscountSectCode=corpTyEntity.getFreightSectionCode();
        }
        return freightOwnDiscountSectCode;
    }

    public BigDecimal getDiscountPriValueMin(String custNo, Date billTime) {
        if(StringUtil.isEmpty(custNo)){
            return null;
        }
        if(billTime==null)billTime=new Date();
        //越发越惠最低折扣
        BigDecimal discountPriValue=null;
        if(StringUtil.isNotEmpty(custNo)){
            //越发越惠折扣(经济快运，定日达)
            BigDecimal currentDuDiscount=new BigDecimal(0), currentDdDiscount=new BigDecimal(0);
            DiscountPrivilegeTyEntity discountPriEntity=discountPrivilegeTyService.queryDiscountCustomerByCustNoAndrecordMonth(custNo,billTime);
            if (discountPriEntity!=null){
                currentDuDiscount=discountPriEntity.getCurrentDuDiscount();
                currentDdDiscount=discountPriEntity.getCurrentDdDiscount();
                if(currentDuDiscount==null)currentDuDiscount=BigDecimal.valueOf(1);
                if(currentDdDiscount==null)currentDdDiscount=BigDecimal.valueOf(1);
                //取两者最小值
                discountPriValue= currentDuDiscount.compareTo(currentDdDiscount)<=0?currentDuDiscount:currentDdDiscount;
            }
        }
        return discountPriValue;
    }


    /**
     * 得到最小的折扣值
     *
     * @param selectCode    优惠分段编号
     * @param accodingItem  分段依据
     * @param accodingValue 依据值
     * @return
     * @author  陈宇霖
     * @date 2016年07月28日10:33:08
     */
    @Override
    public BigDecimal getMinDiscount(String selectCode, String accodingItem, BigDecimal accodingValue) {
        if (StringUtil.isEmpty(selectCode)) {
            return new BigDecimal(1);
        }
        BigDecimal minValues = BigDecimal.valueOf(1);
        List<PriceSectionSubEntity> psSubs = priceSectionTyService.querySectionDetailByCode(selectCode);
        for (PriceSectionSubEntity subEntity : psSubs) {
            if (subEntity != null && subEntity.check(accodingItem, accodingValue)) {//这里因为已经做好排序了，所以找到就跳出
                minValues = minValues.min(subEntity.getPrice());
                break;
            }
        }
        return minValues;
    }
}

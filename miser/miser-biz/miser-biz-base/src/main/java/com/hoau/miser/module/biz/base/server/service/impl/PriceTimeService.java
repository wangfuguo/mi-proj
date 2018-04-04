/**
 * 
 */
package com.hoau.miser.module.biz.base.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hoau.miser.module.biz.base.api.server.service.IPriceTimeService;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceTimeEntity;
import com.hoau.miser.module.biz.base.server.dao.PriceTimeDao;
import com.hoau.miser.module.common.shared.util.ExcelUtil;

/**
 * @author dengyin
 *
 */
@Service
public class PriceTimeService implements IPriceTimeService {
	
	private static final Logger log = LoggerFactory.getLogger(PriceTimeService.class);

	@Resource
	private PriceTimeDao priceTimeDao;
	
	@Override
	public List<PriceTimeEntity> queryPriceTimeEntityListByEntity(
			PriceTimeEntity priceTimeEntity, int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start,limit);
		return priceTimeDao.queryPriceTimeEntityListByEntity(priceTimeEntity, rowBounds);
	}

	@Override
	public long countOfPriceTimeEntityListByEntity(
			PriceTimeEntity priceTimeEntity) {
		return priceTimeDao.countOfPriceTimeEntityListByEntity(priceTimeEntity);
	}
	
	
	/**
	 * @Description: 价格时效需求涉及优惠分段明细查询
	 * @param @param entity
	 * @param @return   
	 * @return List<PriceSectionEntity> 
	 * @throws
	 * @author dengyin
	 * @date 2016-4-26 13:48:44
	 */
	public List<PriceSectionEntity> querySectionSubListBySectionCodeStr(Map<String, String> paramMap){
		return priceTimeDao.querySectionSubListBySectionCodeStr(paramMap);
	}
	
	
	public String createExcelFile(List<PriceTimeEntity> priceTimeEntityList){
		String filePath = null;
		List<String> titleNames = new ArrayList<String>();
	    titleNames.add("出发省市区");
	    titleNames.add("到达省市区");
	    titleNames.add("出发城市");
	    titleNames.add("到达城市");
	    titleNames.add("运输类型");
	    titleNames.add("单价");
	    titleNames.add("最低收费");
	    titleNames.add("运行时长(天)");
	    titleNames.add("优惠活动");		 
 
		 
		List<String> paramNames=new ArrayList<String>();
		paramNames.add("sendPathName");
		paramNames.add("arrivePathName");
		paramNames.add("standardSendPriceCityName");
		paramNames.add("standardArrivePriceCityName");
		paramNames.add("standardTransTypeName");
		paramNames.add("standardWeightPrice");
		paramNames.add("standardLowestFee");
		paramNames.add("pickupPromDay");
		paramNames.add("eventNameStr");
 
		try {
			filePath = new ExcelUtil<PriceTimeEntity>().exportExcel(titleNames, paramNames, priceTimeEntityList);
		} catch (Exception e) {
			log.error("生成导出数据文件异常.....");
			e.printStackTrace();
		}
		return filePath;
	}
	

}

package com.hoau.miser.module.api.facade.server.service;

import java.util.List;

import com.hoau.miser.module.api.facade.shared.domain.PriceSectionPms2DcEntity;
import com.hoau.miser.module.api.facade.shared.vo.PriceSectionVo;

/**
 * 易到家分段价格服务接口
 * @author ZOUYU
 */
public interface IPriceSectionPms2DcService {

	/**
	 * @Description: 查询易到家客户，网点，标准分段价格
	 * @throws Exception   
	 * @return String 
	 * @author zouyu
	 * @date 2016年7月5日
	 */
	List<PriceSectionPms2DcEntity> queryPriceSections(PriceSectionVo priceSectionVo);
	
}

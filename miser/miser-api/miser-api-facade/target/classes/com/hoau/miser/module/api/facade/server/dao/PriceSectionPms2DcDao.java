package com.hoau.miser.module.api.facade.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.api.facade.shared.domain.PriceSectionPms2DcEntity;
import com.hoau.miser.module.api.facade.shared.vo.PriceSectionVo;

/**
 * 迪辰易到家开单查询pms分段价格服务接口
 * @author zouyu
 */
@Repository
public interface PriceSectionPms2DcDao {

	/**
	 * @Description: 查询易到家客户，网点，标准分段价格
	 * @throws Exception   
	 * @return String 
	 * @author zouyu
	 * @date 2016年7月6日
	 */
	public List<PriceSectionPms2DcEntity> queryPriceSections(PriceSectionVo priceSectionVo);

}

package com.hoau.miser.module.api.itf.api.server;

import com.hoau.miser.module.api.itf.api.shared.domain.PriceSectionSubEntity;

import java.util.List;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: IPriceSectionTyService
 * @Package com.hoau.miser.module.api.itf.api.server
 * @Description: 优惠分段查询接口
 * @date 16/6/8 14:58
 */
public interface IPriceSectionTyService {

	/**
	 * 根据优惠分段编码查询优惠分段明细
	 * @param sectionCode
	 * @return
	 * @author 陈宇霖
	 * @date 2016年06月08日15:08:12
	 */
	public List<PriceSectionSubEntity> querySectionDetailByCode(String sectionCode);

}

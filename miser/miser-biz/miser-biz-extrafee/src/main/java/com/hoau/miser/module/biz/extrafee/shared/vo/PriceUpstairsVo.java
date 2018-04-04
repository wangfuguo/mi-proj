package com.hoau.miser.module.biz.extrafee.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceUpstairsEntity;

/**
 * ClassName: PriceUpstairsVo 
 * @Description: 上楼费前后台交互类 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年1月5日
 * @version V1.0   
 */
public class PriceUpstairsVo implements Serializable {

	/**
	 * @Fields serialVersionUID : 序列号
	 */
	private static final long serialVersionUID = 5512543511749519600L;

	/**
	 * @Fields entity : 单条上楼费数据
	 */
	private PriceUpstairsEntity priceUpstairsEntity;
	
	/**
	 * @Fields entities : 多条上楼费数据
	 */
	private List<PriceUpstairsEntity> priceUpstairsEntities;

	public PriceUpstairsEntity getPriceUpstairsEntity() {
		return priceUpstairsEntity;
	}

	public void setPriceUpstairsEntity(PriceUpstairsEntity priceUpstairsEntity) {
		this.priceUpstairsEntity = priceUpstairsEntity;
	}

	public List<PriceUpstairsEntity> getPriceUpstairsEntities() {
		return priceUpstairsEntities;
	}

	public void setPriceUpstairsEntities(
			List<PriceUpstairsEntity> priceUpstairsEntities) {
		this.priceUpstairsEntities = priceUpstairsEntities;
	}

	
	
}

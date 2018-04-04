package com.hoau.miser.module.biz.base.api.shared.vo;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @ClassName:
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author: 279716
 * @date: 2016/3/9 17:23
 */
public class PriceCityVo  implements Serializable{

    /**
     * 查询条件
     */
    private PriceCityEntity queryParam;

    /**
     * 返回的单个实例
     */
    private PriceCityEntity priceCityEntity;

    /**
     * 返回的列表信息
     */
    private ArrayList<PriceCityEntity> priceCityEntities;

    /**
     * 导入文件路径
     */
    private String filePath;
    
    public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public PriceCityEntity getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(PriceCityEntity queryParam) {
        this.queryParam = queryParam;
    }

    public PriceCityEntity getPriceCityEntity() {
        return priceCityEntity;
    }

    public void setPriceCityEntity(PriceCityEntity priceCityEntity) {
        this.priceCityEntity = priceCityEntity;
    }

    public ArrayList<PriceCityEntity> getPriceCityEntities() {
        return priceCityEntities;
    }

    public void setPriceCityEntities(ArrayList<PriceCityEntity> priceCityEntities) {
        this.priceCityEntities = priceCityEntities;
    }
}

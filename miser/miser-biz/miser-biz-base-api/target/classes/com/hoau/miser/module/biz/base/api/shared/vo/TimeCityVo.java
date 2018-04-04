package com.hoau.miser.module.biz.base.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.TimeCityEntity;

/**
 * 时效城市定义Vo
 * @author 何羽
 *
 */
public class TimeCityVo implements Serializable{

	private static final long serialVersionUID = 9196227085718388301L;
	
	private TimeCityEntity timeCityEntity;
	
	private List<TimeCityEntity> timeCityList;
	
	 /**
     * 导入文件路径
     */
    private String filePath;

	public TimeCityEntity getTimeCityEntity() {
		return timeCityEntity;
	}

	public void setTimeCityEntity(TimeCityEntity timeCityEntity) {
		this.timeCityEntity = timeCityEntity;
	}

	public List<TimeCityEntity> getTimeCityList() {
		return timeCityList;
	}

	public void setTimeCityList(List<TimeCityEntity> timeCityList) {
		this.timeCityList = timeCityList;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}

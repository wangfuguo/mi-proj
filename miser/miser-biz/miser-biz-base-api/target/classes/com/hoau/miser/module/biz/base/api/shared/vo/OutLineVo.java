/**
 * 
 */
package com.hoau.miser.module.biz.base.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.OutLineEntity;

/**
 * @author yifan
 *
 */
public class OutLineVo implements Serializable {

	private static final long serialVersionUID = -718580618493572786L;
	private OutLineEntity outLineEntity;
	private String filePath;
	
	private String destoryIdStr;
	
	private List<OutLineEntity> outLineEntities;
	
	public OutLineEntity getOutLineEntity() {
		return outLineEntity;
	}
	public void setOutLineEntity(OutLineEntity outLineEntity) {
		this.outLineEntity = outLineEntity;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public List<OutLineEntity> getOutLineEntities() {
		return outLineEntities;
	}
	public void setOutLineEntities(List<OutLineEntity> outLineEntities) {
		this.outLineEntities = outLineEntities;
	}
	public String getDestoryIdStr() {
		return destoryIdStr;
	}
	public void setDestoryIdStr(String destoryIdStr) {
		this.destoryIdStr = destoryIdStr;
	}
	
	
	
}

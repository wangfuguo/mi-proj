package com.hoau.miser.module.sys.base.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.sys.base.api.shared.domain.OuterBranchEntity;

/**
 * 
 * @Description: 偏线公司Vo
 * @Author 292078
 * @Time 2015年12月11日上午10:00:18
 */
public class OuterBranchVo implements Serializable {

	private static final long serialVersionUID = -2719021870354921278L;
	
	/**
	 *偏线公司对象
	 */
	private OuterBranchEntity outerBranchEntity;
	
	/**
	 *偏线公司对象集合
	 */
	private List<OuterBranchEntity> outerBranchList;

	public OuterBranchEntity getOuterBranchEntity() {
		return outerBranchEntity;
	}

	public void setOuterBranchEntity(OuterBranchEntity outerBranchEntity) {
		this.outerBranchEntity = outerBranchEntity;
	}

	public List<OuterBranchEntity> getOuterBranchList() {
		return outerBranchList;
	}

	public void setOuterBranchList(List<OuterBranchEntity> outerBranchList) {
		this.outerBranchList = outerBranchList;
	}


	

}

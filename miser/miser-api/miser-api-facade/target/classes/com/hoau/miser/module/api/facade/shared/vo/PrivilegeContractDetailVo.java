package com.hoau.miser.module.api.facade.shared.vo;

public class PrivilegeContractDetailVo {

	private String fhjedq; // 发货金额段起
	private String fhjedz; // 发货金额段止
	private String drdyfzdzk; // 定日达纯运费最低折扣
	private String jjkyyfzdzk; // 经济快运纯运费最低折扣
	private String zgfqbl; // 最高返券比例
	private String bz; // 备注
	
	public String getFhjedq() {
		return fhjedq;
	}
	public void setFhjedq(String fhjedq) {
		this.fhjedq = fhjedq;
	}
	public String getFhjedz() {
		return fhjedz;
	}
	public void setFhjedz(String fhjedz) {
		this.fhjedz = fhjedz;
	}
	public String getDrdyfzdzk() {
		return drdyfzdzk;
	}
	public void setDrdyfzdzk(String drdyfzdzk) {
		this.drdyfzdzk = drdyfzdzk;
	}
	public String getJjkyyfzdzk() {
		return jjkyyfzdzk;
	}
	public void setJjkyyfzdzk(String jjkyyfzdzk) {
		this.jjkyyfzdzk = jjkyyfzdzk;
	}
	public String getZgfqbl() {
		return zgfqbl;
	}
	public void setZgfqbl(String zgfqbl) {
		this.zgfqbl = zgfqbl;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
}

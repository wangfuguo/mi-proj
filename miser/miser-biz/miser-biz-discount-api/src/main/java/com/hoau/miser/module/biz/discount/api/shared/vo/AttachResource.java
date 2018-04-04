package com.hoau.miser.module.biz.discount.api.shared.vo;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * 附件实体
 * 
 * @author 275636
 * @date 2016-1-15
 */
public class AttachResource extends BaseEntity {

	private static final long serialVersionUID = 3411925407523667150L;

	// 附件关联外键id
	private String attachguid;

	// 附件的名称
	private String attachname;

	// 附件的类型
	private String attachtype;

	// 附件的路径
	private String attachpath;

	// 附件的大小
	private String attachsize;

	// 无参的构造函数
	public AttachResource() {
		super();
	}

	/**
	 * 有餐的构造函数，其中包括附件关联id，附件名称，附件类型 附件的路径和附件的大小
	 * 
	 * @param attachguid
	 * @param attachname
	 * @param attachtype
	 * @param attachpath
	 * @param attachsize
	 */
	public AttachResource(String attachguid, String attachname,
			String attachtype, String attachpath, String attachsize) {
		super();
		this.attachguid = attachguid;
		this.attachname = attachname;
		this.attachtype = attachtype;
		this.attachpath = attachpath;
		this.attachsize = attachsize;
	}

	/***
	 * @return the attachguid
	 */
	public String getAttachguid() {
		return attachguid;
	}

	/***
	 * @param attachguid
	 *            the attachguid to set
	 */
	public void setAttachguid(String attachguid) {
		this.attachguid = attachguid;
	}

	/***
	 * @return the attachname
	 */
	public String getAttachname() {
		return attachname;
	}

	/***
	 * @param attachname
	 *            the attachname to set
	 */
	public void setAttachname(String attachname) {
		this.attachname = attachname;
	}

	/***
	 * @return the attachtype
	 */
	public String getAttachtype() {
		return attachtype;
	}

	/***
	 * @param attachtype
	 *            the attachtype to set
	 */
	public void setAttachtype(String attachtype) {
		this.attachtype = attachtype;
	}

	/***
	 * @return the attachpath
	 */
	public String getAttachpath() {
		return attachpath;
	}

	/***
	 * @param attachpath
	 *            the attachpath to set
	 */
	public void setAttachpath(String attachpath) {
		this.attachpath = attachpath;
	}

	/***
	 * @return the attachsize
	 */
	public String getAttachsize() {
		return attachsize;
	}

	/***
	 * @param attachsize
	 *            the attachsize to set
	 */
	public void setAttachsize(String attachsize) {
		this.attachsize = attachsize;
	}
}
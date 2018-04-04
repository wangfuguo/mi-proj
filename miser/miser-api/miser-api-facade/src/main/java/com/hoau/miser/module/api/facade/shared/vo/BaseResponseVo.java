package com.hoau.miser.module.api.facade.shared.vo;

/**
 * 响应基础VO
 * ClassName: BaseResponseVo 
 * @author 286330付于令
 * @date 2016年1月25日
 * @version V1.0
 */
public class BaseResponseVo<T> {
	private String status;
	private String message;
	private T content;
	public BaseResponseVo() {
		this.message = "";
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}
}

package com.hoau.miser.module.sys.job.shared.domain;

import com.hoau.hbdp.framework.entity.BaseEntity;

/**
 * @author：高佳
 * @create：2015年6月1日 下午6:45:02
 * @description：
 */
public class EmployeeEntity extends BaseEntity{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 员工号
	 */
	private String employeeCode;
	
	/**
	 * 员工姓名
	 */
	private String employeeName;
	
	/**
	 * 性别
	 */
	private String gender;
	
	/**
	 * 拼音名
	 */
	private String pinYinName;
	
	/**
	 * 拼音简称
	 */
	private String pinYinShortName;
	
	
	/**
	 * 账户
	 */
	private String account;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 部门名称
	 */
	private String deptName;
	
	/**
	 * 岗位名称
	 */
	private String jobName;
	
	/**
	 * 部门编码
	 */
	private String deptCode;
	
	/**
	 * 岗位编码
	 */
	private String jobCode;
	
	/**
	 * 直接上级编码
	 */
	private String managerCode;
	
	/**
	 * 手机
	 */
	private String mobile;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 座机
	 */
	private String telePhone;
	
	/**
	 * 员工状态
	 * 0 试用 
	 * 1 正式
	 * 2 实习
	 * 5离职
	 */
	private String status;
	
	/**
	 * 
	 */
	private String active;
	/**
	 * 版本号
	 */
	private long versionNo;
	
	

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPinYinName() {
		return pinYinName;
	}

	public void setPinYinName(String pinYinName) {
		this.pinYinName = pinYinName;
	}

	public String getPinYinShortName() {
		return pinYinShortName;
	}

	public void setPinYinShortName(String pinYinShortName) {
		this.pinYinShortName = pinYinShortName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelePhone() {
		return telePhone;
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
}

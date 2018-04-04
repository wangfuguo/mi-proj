package com.hoau.eai.pc.vo;

import java.io.Serializable;

/**
 * 基础信息
 * @author Leslie
 *
 */
public class BasicInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 基础数据类型：CT-价格城市；CO网点信息
	 */
	private String basicType;
	/**
	 * 1）当类型为价格城市：价格城市编号； 2）当类型为网点信息：网点编号
	 */
	private String basicNo;
	/**
	 * 1）当类型为价格城市：价格城市中文名称； 2）当类型为网点信息：网点中文名称
	 */
	private String basicNameCn;
	/**
	 * 备注
	 */
	private String basicRemark;
	/**
	 * 是否有效（0表示可用；1表示不可用）
	 */
	private Integer basicStatus;
	/**
	 *  预留字段1
	 *	1）当类型为价格城市：为空；
	 *  2）当类型为网点信息：上级网点编码（总部填-1）
	 */
	private String basicString1;
	/**
	 *  预留字段2
	 *	1）当类型为价格城市：为空；
	 *	2）当类型为网点信息：所属城市
	 */
	private String basicString2;
	/**
	 *  预留字段3
	 *	1）当类型为价格城市：为空；
	 *	2）当类型为网点信息：公司地址
	 */
	private String basicString3;
	/**
	 *  预留字段4
 	 *	1）当类型为价格城市：为空；
     *  2）当类型为网点信息：传真
	 */
	private String basicString4;
	/**
	 * 预留字段5
	 * 1）当类型为价格城市：为空；
	 * 2）当类型为网点信息：电话号码
	 */
	private String basicString5;
	/**
	 * 预留字段6
	 * 1）当类型为价格城市：为空；
	 * 2）当类型为网点信息：邮编
	 */
	private String basicString6;
	/**
	 * 预留字段7
	 * 1)档类型为价格城市：城市类型:SEND出发 ARRIVAL到达
	 */
	private String basicString7;
	/**
	 * 预留字段8
	 */
	private String basicString8;
	/**
	 * 预留字段9
	 */
	private String basicString9;
	/**
	 * 预留字段10
	 */
	private String basicString10;
	/**
	 * 删除状态0正常
	 */
	private Integer recStatus;
	/**
	 * @return the basicType
	 */
	public String getBasicType() {
		return basicType;
	}
	/**
	 * @param basicType the basicType to set
	 */
	public void setBasicType(String basicType) {
		this.basicType = basicType;
	}
	/**
	 * @return the basicNo
	 */
	public String getBasicNo() {
		return basicNo;
	}
	/**
	 * @param basicNo the basicNo to set
	 */
	public void setBasicNo(String basicNo) {
		this.basicNo = basicNo;
	}
	/**
	 * @return the basicNameCn
	 */
	public String getBasicNameCn() {
		return basicNameCn;
	}
	/**
	 * @param basicNameCn the basicNameCn to set
	 */
	public void setBasicNameCn(String basicNameCn) {
		this.basicNameCn = basicNameCn;
	}
	/**
	 * @return the basicRemark
	 */
	public String getBasicRemark() {
		return basicRemark;
	}
	/**
	 * @param basicRemark the basicRemark to set
	 */
	public void setBasicRemark(String basicRemark) {
		this.basicRemark = basicRemark;
	}
	/**
	 * @return the basicStatus
	 */
	public Integer getBasicStatus() {
		return basicStatus;
	}
	/**
	 * @param basicStatus the basicStatus to set
	 */
	public void setBasicStatus(Integer basicStatus) {
		this.basicStatus = basicStatus;
	}
	/**
	 * @return the basicString1
	 */
	public String getBasicString1() {
		return basicString1;
	}
	/**
	 * @param basicString1 the basicString1 to set
	 */
	public void setBasicString1(String basicString1) {
		this.basicString1 = basicString1;
	}
	/**
	 * @return the basicString2
	 */
	public String getBasicString2() {
		return basicString2;
	}
	/**
	 * @param basicString2 the basicString2 to set
	 */
	public void setBasicString2(String basicString2) {
		this.basicString2 = basicString2;
	}
	/**
	 * @return the basicString3
	 */
	public String getBasicString3() {
		return basicString3;
	}
	/**
	 * @param basicString3 the basicString3 to set
	 */
	public void setBasicString3(String basicString3) {
		this.basicString3 = basicString3;
	}
	/**
	 * @return the basicString4
	 */
	public String getBasicString4() {
		return basicString4;
	}
	/**
	 * @param basicString4 the basicString4 to set
	 */
	public void setBasicString4(String basicString4) {
		this.basicString4 = basicString4;
	}
	/**
	 * @return the basicString5
	 */
	public String getBasicString5() {
		return basicString5;
	}
	/**
	 * @param basicString5 the basicString5 to set
	 */
	public void setBasicString5(String basicString5) {
		this.basicString5 = basicString5;
	}
	/**
	 * @return the basicString6
	 */
	public String getBasicString6() {
		return basicString6;
	}
	/**
	 * @param basicString6 the basicString6 to set
	 */
	public void setBasicString6(String basicString6) {
		this.basicString6 = basicString6;
	}
	/**
	 * @return the basicString7
	 */
	public String getBasicString7() {
		return basicString7;
	}
	/**
	 * @param basicString7 the basicString7 to set
	 */
	public void setBasicString7(String basicString7) {
		this.basicString7 = basicString7;
	}
	/**
	 * @return the basicString8
	 */
	public String getBasicString8() {
		return basicString8;
	}
	/**
	 * @param basicString8 the basicString8 to set
	 */
	public void setBasicString8(String basicString8) {
		this.basicString8 = basicString8;
	}
	/**
	 * @return the basicString9
	 */
	public String getBasicString9() {
		return basicString9;
	}
	/**
	 * @param basicString9 the basicString9 to set
	 */
	public void setBasicString9(String basicString9) {
		this.basicString9 = basicString9;
	}
	/**
	 * @return the basicString10
	 */
	public String getBasicString10() {
		return basicString10;
	}
	/**
	 * @param basicString10 the basicString10 to set
	 */
	public void setBasicString10(String basicString10) {
		this.basicString10 = basicString10;
	}
	public Integer getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(Integer recStatus) {
		this.recStatus = recStatus;
	}
	@Override
	public String toString() {
		return "BasicInfo [basicType=" + basicType + ", basicNo=" + basicNo
				+ ", basicNameCn=" + basicNameCn + ", basicRemark="
				+ basicRemark + ", basicStatus=" + basicStatus + ", recStatus="
				+ recStatus + "]";
	}
	
}

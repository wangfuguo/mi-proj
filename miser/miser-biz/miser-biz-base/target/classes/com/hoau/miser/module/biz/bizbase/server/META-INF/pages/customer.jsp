<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<!DOCTYPE HTML>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<%@include file="common.jsp"%>
<ext:module groups="customer" subModule="customer"/>

<!--
<script type='text/javascript'> 
bizbase.customer.i18n = function(key, args) {
msg = {
	'miser.common.update' : '修改',
	'miser.common.queryCondition' : '查询条件',
	'miser.common.queryNoResult' : '查询结果为空',
	'miser.biz.base.customer.code' : '客户编号',
	'miser.biz.base.customer.name' : '客户名称',
	'miser.biz.base.customer.active' : '是否有效',
	'miser.biz.base.customer.rownumberer' : '序号',
	'miser.biz.base.customer.orgCode' : '组织',
	'miser.biz.base.customer.orgName' : '组织',
	'miser.biz.base.customer.logisticName' : '物流代码',
	'miser.biz.base.customer.contractStartTime' : '合同开始时间',
	'miser.biz.base.customer.contractEndTime' : '合同结束时间',
	'miser.biz.base.customer.pcStartTime' : '价格开始时间',
	'miser.biz.base.customer.pcEndTime' : '价格结束时间',
	'miser.biz.base.customer.useCustomerPc' : '启用客户价格',
	'miser.biz.base.customer.useCustomerDiscount' : '启用客户折扣',
	'miser.biz.base.customer.showDiscountPrice' : '显示折扣后价格',
	'miser.biz.base.customer.remark' : '备注'
};
var message = msg[ key] ; 
if(args != null){  
for ( var i = 0; i < args.length; i++) { 
var reg ='{'+i+'}'; 
message = message.toString().replace(reg, args[i]); 
} 
} 
return message; 
}
</script> 
<script type="text/javascript" src="${scripts}/customer.js"></script>
 -->
</head>
<body>
</body>
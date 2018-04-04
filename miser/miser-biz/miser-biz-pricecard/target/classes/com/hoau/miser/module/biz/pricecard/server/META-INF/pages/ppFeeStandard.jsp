<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<!DOCTYPE HTML>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<%@include file="common.jsp"%>
<ext:module groups="ppFeeStandard" subModule="ppFeeStandard"/>
<script type='text/javascript'> 
pricecard.ppFeeStandard.i18n = function(key, args) { 
msg = {'pricecard.ppFeeStandard.modifyDate':'\u4FEE\u6539\u65F6\u95F4','pricecard.ppFeeStandard.money':'\u5355\u4EF7','bizbase.base.button.deleteentry':'\u5220\u9664','pricecard.ppFeeStandard.active':'pricecard.ppFeeStandard.active','pricecard.ppFeeStandard.effectiveTime':'\u542F\u7528\u65F6\u95F4','pricecard.ppFeeStandard.createDate':'\u521B\u5EFA\u65F6\u95F4','bizbase.base.button.updateentry':'\u4FEE\u6539','pricecard.ppFeeStandard.id':'pricecard.ppFeeStandard.id','pricecard.ppFeeStandard.rate':'\u7CFB\u6570','bizbase.base.button.addentry':'\u65B0\u589E','pricecard.ppFeeStandard.packageCode':'\u9879\u76EE\u7F16\u7801','miser.common.save':'\u4fdd\u5b58','miser.pricecard.timeout':'\u8D85\u65F6','pricecard.ppFeeStandard.maxMoney2':'\u6700\u9AD8\u6536\u8D39','pricecard.ppFeeStandard.lockType':'\u9501\u5B9A\u65B9\u5F0F','pricecard.ppFeeStandard.calculationType':'\u8BA1\u7B97\u65B9\u5F0F','miser.common.queryCondition':'\u67e5\u8be2\u6761\u4ef6','miser.common.query':'\u67e5\u8be2','pricecard.ppFeeStandard.minMoney':'\u6700\u4F4E\u6536\u8D39','pricecard.ppFeeStandard.remark':'\u5907\u6CE8','bizbase.base.oneselected':'\u8BF7\u9009\u62E9\u4E00\u6761\u4FE1\u606F','miser.common.cancel':'\u53d6\u6d88','miser.common.reset':'\u91cd\u7f6e','pricecard.ppFeeStandard.modifyUser':'\u4FEE\u6539\u4EBA','pricecard.ppFeeStandard.createUser':'\u521B\u5EFA\u4EBA'};
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
<script type="text/javascript" 
src="${scripts}/ppFeeStandard.js"></script>

</script>
</head>
<body>
</body>
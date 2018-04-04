<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<!DOCTYPE HTML>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<%@include file="common.jsp"%>
<ext:module groups="priceAddValueFee" subModule="priceAddValueFee"/>
<script type='text/javascript'> 
pricecard.priceAddValueFee.i18n = function(key, args) {
msg = {'pricecard.priceAddValueFee.transTypeCode':'\u8FD0\u8F93\u7C7B\u578B','pricecard.priceAddValueFee.calculationType':'\u8BA1\u7B97\u7C7B\u578B','pricecard.priceAddValueFee.weightPrice':'\u8F7B\u8D27\u5355\u4EF7','pricecard.priceAddValueFee.lightPrice':'\u91CD\u8D27\u5355\u4EF7','pricecard.priceAddValueFee.lowestMoney':'\u6700\u4F4E\u6536\u8D39','pricecard.priceAddValueFee.lockType':'\u662F\u5426\u9501\u5B9A','pricecard.priceAddValueFee.effectiveTime':'\u751F\u6548\u65F6\u95F4','pricecard.priceAddValueFee.invalidTime':'\u5931\u6548\u65F6\u95F4','pricecard.priceAddValueFee.code':'\u9879\u76EE\u7F16\u7801','pricecard.priceAddValueFee.oneselected':'\u8BF7\u9009\u62E9\u4E00\u6761\u6570\u636E','pricecard.priceAddValueFee.remark':'\u5907\u6CE8'};
var message = msg[ key] ; 
if(args != null){
for ( var i = 0; i < args.length; i++) { 
var reg ='{'+i+'}'; 
message = message.toString().replace(reg, args[i]); 
} 
} 
return message; 
}
pricecard.priceAddValueFee.isPermission = function (url){
	permissions = {'dataDictionary/dataDictionaryAddButton':true}; 
	return permissions[url]; 
}; 
</script>
<script type="text/javascript" src="${scripts}/priceAddValueFee.js"></script>

</head>
<body>
</body>


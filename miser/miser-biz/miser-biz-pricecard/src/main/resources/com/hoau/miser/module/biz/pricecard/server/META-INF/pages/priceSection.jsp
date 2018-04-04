<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<!DOCTYPE HTML>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<%@include file="common.jsp"%>
<ext:module groups="priceSection" subModule="priceSection"/>
<!-- <script type='text/javascript'> 
pricecard.priceSection.i18n = function(key, args) {
msg = {'pricecard.priceSection.name':'\u4F18\u60E0\u5206\u6BB5\u540D\u79F0','pricecard.priceSection.shortCode':'\u4F18\u60E0\u5206\u6BB5\u7B80\u7801','pricecard.priceSection.suitProduct':'\u4F7F\u7528\u8FD0\u8F93\u7C7B\u578B','pricecard.priceSection.sectionedItem':'\u4F7F\u7528\u8D39\u7528\u7C7B\u578B','pricecard.priceSection.sendPriceCity.name':'\u51FA\u53D1\u4EF7\u5361\u57CE\u5E02','pricecard.priceSection.sendLable':'\u51FA\u53D1','pricecard.priceStandard.remark':'\u5907\u6CE8','pricecard.priceStandard.lowestFee':'\u6700\u4F4E\u6536\u8D39','pricecard.priceStandard.timeout':'pricecard.priceStandard.timeout','pricecard.priceStandard.weightPrice':'\u91CD\u91CF\u5355\u4EF7','pricecard.priceStandard.modifyDate':'\u4FEE\u6539\u65F6\u95F4','pricecard.priceStandard.updateentry':'\u4FEE\u6539','pricecard.priceStandard.volumePrice':'\u4F53\u79EF\u5355\u4EF7','miser.common.delete':'\u5220\u9664','pricecard.priceStandard.arrivePriceCityName':'\u5230\u8FBE\u4EF7\u5361\u57CE\u5E02','pricecard.priceStandard.oneselected':'pricecard.priceStandard.oneselected','miser.common.queryCondition':'\u67e5\u8be2\u6761\u4ef6','miser.common.reset':'\u91cd\u7f6e','pricecard.priceStandard.transTypeName':'\u8FD0\u8F93\u7C7B\u578B','pricecard.priceStandard.effectiveTime':'\u751F\u6548\u65F6\u95F4','pricecard.priceStandard.sendPriceCityName':'\u51FA\u53D1\u4EF7\u5361\u57CE\u5E02','pricecard.priceStandard.isdelete':'pricecard.priceStandard.isdelete','pricecard.priceStandard.modifyUser':'\u4FEE\u6539\u7528\u6237','pricecard.priceStandard.addFee':'\u9644\u52A0\u8D39','pricecard.priceStandard.state':'\u72B6\u6001','pricecard.priceStandard.arrivePriceCity.name':'\u5230\u8FBE\u4EF7\u5361\u57CE\u5E02','pricecard.priceStandard.fuelFeeSection':'\u71C3\u6CB9\u5206\u6BB5','pricecard.priceStandard.active':'\u662F\u5426\u6709\u6548','pricecard.priceStandard.addentry':'\u65B0\u589E','miser.common.query':'\u67e5\u8be2','pricecard.priceStandard.invalidTime':'\u5931\u6548\u65F6\u95F4','pricecard.priceStandard.arriveLable':'\u5230\u8FBE','pricecard.priceSection.remark':'\u5907\u6CE8','pricecard.priceSection.add':'\u65B0\u589E','pricecard.priceSection.update':'\u4FEE\u6539','pricecard.priceSection.name':'\u4F18\u60E0\u5206\u6BB5\u540D\u79F0','pricecard.priceSection.shortCode':'\u4F18\u60E0\u5206\u6BB5\u7B80\u7801','pricecard.priceSection.suitProduct':'\u4F7F\u7528\u8FD0\u8F93\u7C7B\u578B','pricecard.priceSection.sectionedItem':'\u4F7F\u7528\u8D39\u7528\u7C7B\u578B','pricecard.priceSection.remark':'\u5907\u6CE8','pricecard.priceSection.active':'\u662F\u5426\u6709\u6548','pricecard.priceSection.modifyUser':'\u4FEE\u6539\u7528\u6237','pricecard.priceSection.modifyDate':'\u4FEE\u6539\u65F6\u95F4','pricecard.priceSection.detailBtn':'\u660E\u7EC6\u67E5\u8BE2','pricecard.priceSection.oneselected':'\u8BF7\u9009\u62E9\u4E00\u6761\u6570\u636E','pricecard.priceSection.timeout':'\u8BF7\u6C42\u8D85\u65F6','pricecard.priceSection.goodsType':'\u8D27\u7269\u8F7B\u91CD\u8D27\u7C7B\u578B','pricecard.priceSection.startValue':'\u6bb5\u8d77\uFF08\u2265\uFF09','pricecard.priceSection.endValue':'\u6bb5\u81f3\uFF08\uFF1C\uFF09','pricecard.priceSection.sectionAccodingItem':'\u5206\u6BB5\u4F9D\u636E','pricecard.priceSection.priceType':'\u8D39\u7528\u7C7B\u578B','pricecard.priceSection.price':'\u8D39\u7528','pricecard.priceSection.calcAlone':'\u662F\u5426\u9636\u68AF\u7ED3\u7B97','pricecard.priceSection.minPrice':'\u6700\u4F4E\u6536\u8D39','pricecard.priceSection.maxPrice':'\u6700\u9AD8\u6536\u8D39','pricecard.priceSection.active':'\u662F\u5426\u751F\u6548'};
var message = msg[ key] ; 
if(args != null){  
for ( var i = 0; i < args.length; i++) { 
var reg ='{'+i+'}'; 
message = message.toString().replace(reg, args[i]); 
} 
} 
return message; 
}
pricecard.priceSection.isPermission = function (url){
	permissions = {'dataDictionary/dataDictionaryAddButton':true}; 
	return permissions[url]; 
	}; 
</script> 
<script type="text/javascript" src="${scripts}/priceSection.js"></script>
-->
</head>
<body>
</body>


<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<!DOCTYPE HTML>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<%@include file="common.jsp"%>
<ext:module groups="privilegeContract" subModule="privilegeContract"/>
<%-- <script type='text/javascript'> 
discount.privilegeContract.i18n = function(key, args) {
msg = {'discount.privilegeContract.add':'\u65b0\u589e',
		'discount.privilegeContract.update':'\u4fee\u6539',
		'discount.privilegeContract.customerCode':'\u5BA2\u6237\u7F16\u53F7',
		'discount.privilegeContract.customerName':'\u5BA2\u6237\u540D\u79F0',
		'discount.privilegeContract.contractStartTime':'\u5408\u540C\u5F00\u59CB\u65F6\u95F4',
		'discount.privilegeContract.contractEndTime':'\u5408\u540C\u7ED3\u675F\u65F6\u95F4',
		'discount.privilegeContract.privilegeStartTime':'\u8D8A\u53D1\u8D8A\u60E0\u5F00\u59CB\u65F6\u95F4',
		'discount.privilegeContract.privilegeEndTime':'\u8D8A\u53D1\u8D8A\u60E0\u7ED3\u675F\u65F6\u95F4',
		'discount.privilegeContract.state':'\u8D8A\u53D1\u8D8A\u60E0\u5F53\u524D\u72B6\u6001',
		'discount.privilegeContract.hasCoupon':'\u662F\u5426\u8FD4\u5238',
		'discount.privilegeContract.remark':'\u5907\u6ce8',
		'discount.privilegeContract.active':'\u662f\u5426\u6709\u6548',
		'discount.privilegeContract.modifyUser':'最后修改人',
		'discount.privilegeContract.modifyDate':'最后修改时间',
		'discount.privilegeContract.createUser':'\u521b\u5efa\u4eba',
		'discount.privilegeContract.createDate':'\u521b\u5efa\u65f6\u95f4',
		'discount.privilegeContract.customerContractId':'\u8D8A\u53D1\u8D8A\u60E0\u5BA2\u6237\u5408\u540CID',
		'discount.privilegeContract.startAmount':'\u53D1\u8D27\u91D1\u989D\u6BB5\u8D77',
		'discount.privilegeContract.endAmount':'\u53D1\u8D27\u91D1\u989D\u6BB5\u6B62',
		'discount.privilegeContract.ddMinFreightDiscount':'定日达纯运费实际折扣',
		'discount.privilegeContract.duMinFreightDiscount':'经济快运纯运费实际折扣',
		'discount.privilegeContract.maxCouponScale':'实际返券比例',
		'discount.privilegeContract.effectiveTime':'\u751F\u6548\u65F6\u95F4',
		'discount.privilegeContract.invalidTime':'\u5931\u6548\u65F6\u95F4',
		'discount.privilegeContract.dataOrign':'\u6570\u636E\u6765\u6E90',
		'discount.privilegeContract.state':'\u5F53\u524D\u72B6\u6001',
		'discount.privilegeContract.contractStartAndEndTime':'\u6709\u6548\u65F6\u95F4',
		'discount.privilegeContract.startAndEndAmount':'\u53D1\u8D27\u91D1\u989D\u5206\u6BB5',
		'discount.privilegeContract.detailBtn': '查询合同明细',
		'discount.privilegeContract.contractDetail' : '合同明细',
		
		'miser.common.pickUpOne':'\u8BF7\u9009\u62E9\u4E00\u6761\u8BB0\u5F55\u8FDB\u884C\u64CD\u4F5C',
		'miser.common.query': '查询', 
		'miser.common.reset': '重置', 
		'miser.common.delete': '删除', 
		'miser.common.update' : '修改', 
		'miser.common.add' : '新增',
		'miser.common.save':'保存', 
		'miser.common.cancel': '取消', 
		'miser.common.division' : '事业部', 
		'miser.common.bigregion' : '大区', 
		'miser.common.roadarea' : '路区', 
		'miser.common.salesdepartment' : '门店',
		'miser.common.dataNotIntact' : '数据不完整',
		'miser.common.timeout' : '连接超时', 
		'miser.common.emptyText' : '查询结果为空', 
		'miser.common.invalideData' : '该数据已作废,不可修改',
		'miser.common.serial' : '序号', 
		'miser.common.effective' : '生效中', 
		'miser.common.passed' : '已过期', 
		'miser.common.wait' : '待生效', 
		'miser.common.deleted' : '已废弃', 
		'miser.common.detailList' : '明细列表',
		'miser.common.queryCondition' : '\u67E5\u8BE2\u6761\u4EF6'};
var message = msg[ key] ; 
if(args != null){  
for ( var i = 0; i < args.length; i++) { 
var reg ='{'+i+'}'; 
message = message.toString().replace(reg, args[i]); 
} 
} 
return message; 
}
discount.privilegeContract.isPermission = function (url){
	permissions = {'dataDictionary/dataDictionaryAddButton':true}; 
	return permissions[url]; 
	}; 
</script> 
<script type="text/javascript" src="${scripts}/privilegeContract.js"></script> --%>

</head>
<body>
</body>


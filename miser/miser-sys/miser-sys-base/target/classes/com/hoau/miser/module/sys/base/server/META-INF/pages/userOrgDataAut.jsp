<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<!DOCTYPE HTML>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<%@include file="common.jsp"%>
<ext:module groups="userOrgDataAut" subModule="userOrgDataAut"/>
<!-- 调试开始 
<script type="text/javascript" src="http://localhost:8080/miser-sys-web/scripts/common/ext-hoau.js"></script>
<script type="text/javascript" src="http://localhost:8080/miser-sys-web/scripts/common/miser-util.js"></script>
<script type="text/javascript" src="http://localhost:8080/miser-sys-web/scripts/common/commonSelector.js"></script>
<script type="text/javascript" src="http://localhost:8080/miser-sys-web/scripts/common/common.js"></script>
<script type='text/javascript'>
if(typeof base == 'undefined'){
base={};
}
base.realPath = function (path) { 
return '/miser-sys-web/base/' + path;
};

base.userOrgDataAut={}
</script>
-->
<!-- 调试开始
<script type='text/javascript'> 
base.userOrgDataAut.i18n = function(key, args) { 
msg = {'miser.common.query':'\u67e5\u8be2','bse.userOrgDataAut.userName':'\u7528\u6237\u540d','miser.common.delete':'\u5220\u9664','miser.common.save':'\u4fdd\u5b58','miser.common.resultisNull':'\u67e5\u8be2\u7ed3\u679c\u4e3a\u7a7a','miser.common.queryCondition':'\u67e5\u8be2\u6761\u4ef6','miser.common.add':'\u65b0\u589e','bse.userOrgDataAut.modifyDate':'\u4fee\u6539\u65e5\u671f','bse.userOrgDataAut.orgName':'\u90e8\u95e8\u540d\u79f0','miser.common.cancel':'\u53d6\u6d88','bse.userOrgDataAut.includeSubOrg':'\u662f\u5426\u5305\u542b\u5b50\u90e8\u95e8','miser.common.reset':'\u91cd\u7f6e'};
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
 
<script type="text/javascript" src="${scripts}/userOrgDataAut.js"></script>
-->
</head>
<body>
</body>
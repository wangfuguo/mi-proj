<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<!DOCTYPE HTML>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<%@include file="common.jsp"%>
<ext:module groups="employee" subModule="employee"/>
<%-- <script type='text/javascript'> 
base.employee.i18n = function(key, args) { 
msg = {'bse.employee.jobName':'\u5c97\u4f4d\u540d\u79f0','bse.employee.pinyinShort':'\u62fc\u97f3\u7b80\u79f0','bse.employee.managerCode':'\u76f4\u63a5\u4e0a\u7ea7','bse.employee.mobilePhone':'\u624b\u673a','bse.employee.empCode':'\u5458\u5de5\u5de5\u53f7','bse.employee.account':'\u8d26\u53f7','miser.common.queryCondition':'\u67e5\u8be2\u6761\u4ef6','miser.common.resultisNull':'\u67e5\u8be2\u7ed3\u679c\u4e3a\u7a7a','bse.employee.password':'\u5bc6\u7801','bse.employee.modifyDate':'\u4fee\u6539\u65f6\u95f4','miser.common.query':'\u67e5\u8be2','bse.employee.deptName':'\u90e8\u95e8\u540d\u79f0','bse.employee.empName':'\u5458\u5de5\u59d3\u540d','miser.common.reset':'\u91cd\u7f6e'};
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
<script type="text/javascript" src="${scripts}/employee.js"></script> --%>
</head>
<body>
</body>
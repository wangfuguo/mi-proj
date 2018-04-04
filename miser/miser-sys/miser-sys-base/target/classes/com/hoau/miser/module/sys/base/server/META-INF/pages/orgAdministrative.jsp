<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<%-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>  --%>
<!DOCTYPE HTML>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<%@include file="common.jsp"%>

<ext:module groups="orgAdministrative" subModule="orgAdministrative"/>
<%-- <script type='text/javascript'> 
base.orgAdministrative.i18n = function(key, args) { 
msg = {'bse.orgAdministrative.PlatForm':'\u5e73\u53f0\u4fe1\u606f','bse.orgAdministrative.OrgMainInfo':'\u7ec4\u7ec7\u5c5e\u6027\u4fe1\u606f','bse.orgAdministrative.TransferCenter':'\u573a\u7ad9\u4fe1\u606f','bse.orgAdministrative.OrgBusinessInfo':'\u884c\u653f\u7ec4\u7ec7\u4e1a\u52a1\u5c5e\u6027','bse.orgAdministrative.OrgAuxiliaryInfo':'\u7ec4\u7ec7\u67b6\u6784\u57fa\u672c\u4fe1\u606f','bse.orgAdministrative.SaleDepartment':'\u95e8\u5e97\u4fe1\u606f'};
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
<script type="text/javascript" src="${scripts}/orgAdministrative.js"></script> --%>
<%-- <spring:eval expression="@applicationProperties['gis.js.map']" var="gishost"/> 
<script src="${gishost}" type="text/javascript"> </script> --%>


</head>
<body>
</body>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/ext" prefix="ext" %>
<!DOCTYPE HTML>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<%@include file="common.jsp"%>
<style>
	.x-panel-header-default .x-tool-img{
	background-image:url(../images/common/tool-sprites-dark.png);}
		/* but边框颜色 */
	.x-btn-default-small{
		background-color: #FFF;
		border-style:solid;
		border-width:1px;
		border-color:#c3c3c3;
	}
</style>
<style type="text/css">
    .myCls{
        color: #AAAAAA;
    }
</style>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar c1 = Calendar.getInstance();  
	c1.setTime(new Date());  
	c1.set(Calendar.DATE, c1.get(Calendar.DATE)); 
	String startDate = sdf.format(c1.getTime());
	Calendar c2 = Calendar.getInstance();  
	c2.setTime(new Date());  
	c2.set(Calendar.DATE, c2.get(Calendar.DATE)+7); 
	String endDate = sdf.format(c2.getTime());
	String cId = (request.getParameter("cId") == null) ? "" : request.getParameter("cId");
	String lockView = (request.getParameter("lock") == null) ? "" : request.getParameter("lock");
	String eventCode = (request.getParameter("eventCode") == null) ? "" : request.getParameter("eventCode");
%>
<script type="text/javascript">
	var startDate = '<%= startDate %>';
	var endDate = '<%= endDate %>';
	var cId = '<%= cId%>';
	var lockView = '<%= lockView%>';
	var eventCode = '<%= eventCode%>';
	</script>
<ext:module groups="priceEvnetAdd" subModule="priceEvnetAdd"/>
<script type="text/javascript" src="${scripts}/PriceEvnetAdd/PriceEvnetAdd.js"></script>
</head>
<body>
</body>
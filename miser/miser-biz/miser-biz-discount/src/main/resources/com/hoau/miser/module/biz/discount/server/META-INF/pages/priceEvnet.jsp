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
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//Calendar c1 = Calendar.getInstance();  
	//c1.setTime(new Date());  
	//c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 31); 
	//String startDate = sdf.format(c1.getTime());
	Calendar c2 = Calendar.getInstance();  
	c2.setTime(new Date());  
	c2.set(Calendar.DATE, c2.get(Calendar.DATE)); 
	String startDate = sdf.format(c2.getTime());
%>
<script type="text/javascript">
	var startDate = '<%= startDate %>';
</script>
<ext:module groups="priceEvnet" subModule="priceEvnet"/>
<%-- <script type="text/javascript" src="${scripts}/PriceEvnetList/PriceEvnetList.js"></script> --%>
</head>
<body>
</body>
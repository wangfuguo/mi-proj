<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.hoau.hbdp.framework.server.context.UserContext" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.io.InputStream" %>


<%@taglib uri="/ext" prefix="ext" %>
<!doctype html>
<html>

<%

	ClassLoader loader = Thread.currentThread().getContextClassLoader();
	Properties props = new Properties();
	InputStream resourceStream = loader.getResourceAsStream("config.properties");
	props.load(resourceStream);

	String env = props.getProperty("auth.widget.moduleswitcher.config.env");
	String renderToId = props.getProperty("auth.widget.moduleswitcher.config.renderToId");
	// 是否已接入统一权限系统
	String authEnabled = props.getProperty("auth.widget.moduleswitcher.config.authEnabled");
	// 统一权限系统模块ID，未接入的需提供异于当前已接入的所有moduleId的唯一ID
	String moduleId = props.getProperty("auth.widget.moduleswitcher.config.moduleId");
	String authBaseUrl = props.getProperty("auth.remote.app.base.url");
	String authBasePath = props.getProperty("auth.remote.app.base.path");
	String authEntryPoint = props.getProperty("auth.remote.app.entry.point");
	// 统一登出地址
	String ssoLogoutService = props.getProperty("sso.logout.service");
	String ssoLogoutUrl = props.getProperty("sso.logout.url");

%>


<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<%@include file="common.jsp"%>
<ext:module groups="main" subModule="main"/>
<link href="${styles}/left_tree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${scripts}/main.js"></script>
<script type="text/javascript" src="${scripts}/view/MainView.js"></script>

<!-- add by dengyin 2016-7-4 10:19:16  auth 接入 -->
        <%--切换插件所需js及css--%>
        <link href="${scripts}/plugin/moduleswitcher/css/auth-widget-moduleswitcher-main.css" rel="stylesheet" type="text/css" />
        <script src="${scripts}/plugin/moduleswitcher/js/auth-widget-moduleswitcher-config.js" type="text/javascript" ></script>
        

        <%-- 注意设置当前登录用户 --%>
        <script>

	        AuthWidgetModuleSwitcherConfig.env = "<%=env%>";
	        AuthWidgetModuleSwitcherConfig.renderToId = "<%=renderToId%>";
	        AuthWidgetModuleSwitcherConfig.authEnabled = <%=authEnabled%>;
	        AuthWidgetModuleSwitcherConfig.moduleId = "<%=moduleId%>";
	        AuthWidgetModuleSwitcherConfig.remoteBaseUrl = "<%=authBaseUrl%>";
	        AuthWidgetModuleSwitcherConfig.remoteBasePath = "<%=authBaseUrl + authBasePath%>";
	        AuthWidgetModuleSwitcherConfig.remoteEntryPoint = "<%=authBaseUrl + authEntryPoint%>";
	        AuthWidgetModuleSwitcherConfig.ssoLogoutUrl = "<%=ssoLogoutService + ssoLogoutUrl%>";
        
            AuthWidgetModuleSwitcherConfig.currentUser = "<%=UserContext.getCurrentUser().getUserName()%>";
        </script>
        <%--切换插件所需js及css--%>
        
        <script src="${scripts}/plugin/lib/require.js" data-main="${scripts}/plugin/moduleswitcher/js/auth-widget-moduleswitcher-plugin"></script>
        
                
 <!-- end by dengyin 2016-7-4 10:19:16  auth 接入 -->       
        
<style type="text/css">
{
	font-family:"微软雅黑";
}
</style>
</head>
<script type="text/javascript">
function logout(){
	var successFun = function(json) {
		
		//modify by dengyin 2016-7-4 14:49:51 sso auth 接入
		//window.location = '../login/index.action';
		
		if(Ext.isEmpty(json)){
			window.location = '../login/index.action';
		} else {
			var pmsLogInType = json.pmsLogInType;
			
			if(pmsLogInType == 'normal'){
				window.location = '../login/index.action';
			} else if(pmsLogInType == 'sso'){
				window.location = AuthWidgetModuleSwitcherConfig.ssoLogoutUrl;
			} else {
				window.location = '../login/index.action';
			}
		}
		//end by dengyin 2016-7-4 14:49:51 sso auth 接入
	};
	var failureFun = function(json) {
		if (Ext.isEmpty(json)) {
			//Miser.showErrorMes('连接超时'); // 请求超时
			document.getElementById("msg").style.display = "block";
			document.getElementById("error").innerText = "连接超时!";
		} else {
			var message = json.message;
			//Miser.showErrorMes(message); // 提示失败原因
			document.getElementById("msg").style.display = "block";
			document.getElementById("error").innerText = message+"!";
		}
	};
	miser.requestJsonAjax('logoutAction!logout.action', null, successFun,
			failureFun);
}
</script>
<body>

        <%--切换插件显示面板--%>
        <div id="auth-moduleswitcher" class="auth_moduleswitcher_dropdown_container">
            <span class="left_arrow"></span>
        </div>
        <%--切换插件显示面板--%>
	<div id="logoImageDiv" style="width: 200px;"><img  src="${images}/jiageguanli.png"></div>
	<div id="loginoutDiv" title="退出系统" style="cursor:pointer" onclick="logout();"><img  src="${images}/quit.jpg"></div>
</body>
</html>

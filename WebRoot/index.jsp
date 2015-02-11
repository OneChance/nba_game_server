<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>  

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class="loaded">
	<!--<![endif]-->
	<head>
		<meta charset="utf-8">

		<title>NBA MANAGER</title>

		<!-- <meta name="description" content=""> -->
		<!-- 让IE浏览器用最高级内核渲染页面 还有用 Chrome 框架的页面用webkit 内核
		================================================== -->
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<!-- IOS6全屏 Chrome高版本全屏
		================================================== -->
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="mobile-web-app-capable" content="yes">
		<!-- 让360双核浏览器用webkit内核渲染页面
		================================================== -->
		<meta name="renderer" content="webkit">
		<!-- Mobile Specific Metas
		================================================== -->
		<!-- !!!注意 minimal-ui 是IOS7.1的新属性，最小化浏览器UI -->
		<meta name="viewport"
			content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<meta name="format-detection" content="telephone=no">
		<!-- CSS
		================================================== -->	
		<link href="resources/css/reset.css" rel="stylesheet" type="text/css">
		<link href="resources/css/magicwall.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/style.css" rel="stylesheet" type="text/css">
		<link href="resources/css/style_1280.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/style_1024.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/style_768.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/style_320.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/style_retina.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/nbagame.css" rel="stylesheet"
			type="text/css">	

		<!--[if lt IE 9]>
			<script src="http://cdn.bootcss.com/html5shiv/r29/html5.js"></script>
		<![endif]-->

	</head>
	<body>

		<div class="container">
			<header class="ai-header">
			<div class="ai-nav-wrap">
				<div class="ai-nav">
					<div class="ai-logo"><spring:message code="title"/></div>

					<div class="sidebtn"></div>

					<div class="ai-nav-list">
						<div class="ai-nav-redLine">
							
						</div>
						<a href="#" class="ai-nav-listitem cur" id="team"><spring:message code="menu_team"/></a>
						<a href="#" class="ai-nav-listitem" id="market""><spring:message code="menu_market"/></a>
						<a href="#" class="ai-nav-listitem" id="rank"><spring:message code="menu_rank"/></a>
						<a href="#" class="ai-nav-listitem" id="reg"><spring:message code="menu_reg"/></a>
					</div>
				</div>
			</div>
			
			<div class="ai-banner-wrap ai-eachcont" nav_index="0"
				style="overflow: hidden;" id="mainframe">
			
			</div>

			</header>

			<footer class="ai-footer">

			<div class="mid_wrap copyright">
				<div class="mid_block">
					<h2>
						<i>ZHSTART<spring:message code="menu_studio"/></i>
					</h2>
					<h3>
						<i> © 2015-2115 ZHSTART All rights reserved.</i>
					</h3>
				</div>
			</div>
			</footer>
			
		</div>



		<script type="text/javascript" src="resources/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="resources/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="resources/js/jquery.scrollfire.min.js"></script>
		<script type="text/javascript" src="resources/js/jay.plugin.smoothscroll.js"></script>
		<script type="text/javascript" src="resources/js/sly.min.js"></script>
		<script type="text/javascript" src="resources/js/index.js"></script>
		<script src="resources/js/nbagame.js"></script>

	</body>
</html>

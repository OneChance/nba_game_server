<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class="loaded">

	<head>
		<meta charset="utf-8">
		<title>NBA MANAGER</title>

		<meta http-equiv="X-UA-Compatible" content="chrome1,IE=edge">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="mobile-web-app-capable" content="yes">
		<meta name="renderer" content="webkit">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<meta name="format-detection" content="telephone=no">

		<link href="resources/css/reset.css" rel="stylesheet" type="text/css">
		<link href="resources/css/style.css" rel="stylesheet" type="text/css">
		<link href="resources/css/magicwall.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/style_1580.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/style_1280.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/style_1024.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/style_768.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/style_retina.css" rel="stylesheet"
			type="text/css">
		<link href="resources/css/nbagame.css" rel="stylesheet"
			type="text/css">
		<link rel="stylesheet" href="resources/css/font-awesome.min.css">
		<link href="resources/css/animation.css" rel="stylesheet"
			type="text/css">
		<script>
	var browser = navigator.appName
	var b_version = navigator.appVersion
	var version = b_version.split(";");
	var trim_Version = version[1].replace(/[ ]/g, "");

	if ((browser == "Microsoft Internet Explorer" && trim_Version == "MSIE6.0")
			|| (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE7.0")) {
		alert("浏览器版本过低,可能无法正常游戏!");
	}
</script>
	</head>
	<body>
		<div id="loading" class="shade">
			<div class="shadebg"></div>
			<div class="loading">
				<div class="ball"></div>
				<div class="ball1"></div>
			</div>
		</div>

		<div id="chart" class="shade chartshade">
			<div class="shadebg"></div>
			<div class="chartbg" id="chart_container">

			</div>
		</div>

		<div class="container">
			<header class="ai-header">
			<div class="ai-nav-wrap">
				<div class="ai-nav">
					<div class="ai-logo">
						<spring:message code="title" />
					</div>

					<div class="ai-nav-list">
						<div class="ai-nav-redLine"></div>
						<a href="#" class="ai-nav-listitem cur" id="team"><spring:message
								code="menu_team" /> </a>
						<a href="#" class="ai-nav-listitem" id="market""><spring:message
								code="menu_market" /> </a>
						<a href="#" class="ai-nav-listitem" id="reg"><spring:message
								code="menu_reg" /> </a>
					</div>
				</div>
			</div>

			<div class="ai-banner-wrap ai-eachcont mainframe" nav_index="0"
				id="mainframe"></div>

			</header>

			<footer class="ai-footer">
			<div class="mid_wrap copyright">
				<div class="mid_block">
					<h2>
						<i>ZHSTAR&nbsp;<spring:message code="menu_studio" /> </i>
					</h2>
					<h3>
						<i> © 2015-2115 ZHSTAR All rights reserved.</i>
					</h3>
				</div>
			</div>
			</footer>

		</div>

		<script type="text/javascript" src="resources/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="resources/js/index.js"></script>
		<script src="resources/js/nbagame.js"></script>

		<script>
	jQuery("#reg").click(function() {
		$("#loading").show();
		$("#mainframe").load("account/reg/");
	});

	jQuery("#team").click(function() {
		$("#loading").show();
		$("#mainframe").load("game/myteam/");
	});

	jQuery("#market").click(function() {

		if ('${loginu}' == '') {
			alert("<spring:message code='needlogin'/>");
			$("#loading").show();
			jQuery("#team").click();
		} else if ('${team}' == '') {
			alert("<spring:message code='need_team'/>");
			$("#loading").show();
			jQuery("#team").click();
		} else {
			$("#loading").show();
			$("#mainframe").load("game/market/");
		}

	});

	jQuery("#rank").click(function() {
		$("#mainframe").load("game/rank/");
	});

	jQuery(function() {
		$("#loading").hide();
		$("#chart").hide();
		$("#mainframe").load("game/myteam/");
	});

	//设置国际化变量
	var confirm_update_cap = "<spring:message code="update_cap"/>";
	var confirm_update_eq = "<spring:message code="update_eq"/>";
</script>
	</body>
</html>

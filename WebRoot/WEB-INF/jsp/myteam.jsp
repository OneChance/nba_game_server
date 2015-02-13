<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">

	<div class="feedback-index for-login">
		<c:if test="${empty loginu}">
			<form>
				<div class="fb-rows">
					<input id="user_name" class="fb-ipt"
						placeholder="<spring:message code="user_name"/>:" type="text">
				</div>
				<div class="fb-rows">
					<input id="password" class="fb-ipt"
						placeholder="<spring:message code="password"/>:" type="password">
				</div>
				<div class="fb-rows">
					<input class="fb-btn" type="button"
						onclick="login('<spring:message code="need_user_name"/>','<spring:message code="need_password"/>')"
						value="<spring:message code="menu_login"/>">
				</div>
			</form>
		</c:if>
		<c:if test="${not empty loginu}">
			<h2 class="title-big">
				<spring:message code="loginok" />
				,${loginu.user_name}
			</h2>
		</c:if>
	</div>

	<div style="float:left;background-color: blue;">
		<font color=red>xxxx</font>
	</div>
</div>


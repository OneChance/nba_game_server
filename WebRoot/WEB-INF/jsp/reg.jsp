<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div class="container">
	<div class="feedback-index fi-center">
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
					onclick="reg('<spring:message code="need_user_name"/>','<spring:message code="need_password"/>')"
					value="<spring:message code="menu_reg"/>">
			</div>
		</form>
	</div>
</div>

<script>
	$(".loadingbg").hide();
</script>

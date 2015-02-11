<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>  


<div class="container">
	<div class="feedback-index">
		<form>
			<div class="fb-rows">
				<input id="user_name" class="fb-ipt" placeholder="<spring:message code="user_name"/>:" type="text">
			</div>
			<div class="fb-rows">
				<input id="password" class="fb-ipt" placeholder="<spring:message code="password"/>:"
					type="password">
			</div>
			<div class="fb-rows">
				<input class="fb-btn" type="button" onclick="check()" value="<spring:message code="menu_reg"/>">
			</div>
		</form>
	</div>
</div>


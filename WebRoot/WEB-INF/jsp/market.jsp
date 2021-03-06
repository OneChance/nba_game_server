<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", -10);
%>

<script>
	jQuery(function() {
		jQuery("#pos_select").val("${pos_select}");
	});
</script>

<div class="container">

	<div class="feedback-index">
		<div class="domainlook">
			<fieldset>
				<div class="domaintitle">
					<spring:message code="player_position" />
				</div>
				<select id="pos_select" name="extension" class="select"
					value="pos_select">
					<option value=''>
						<spring:message code='pos_all' />
					</option>
					<option value='中锋'>
						<spring:message code='pos_c' />
					</option>
					<option value='前锋'>
						<spring:message code='pos_f' />
					</option>
					<option value='后卫'>
						<spring:message code='pos_g' />
					</option>
				</select>
				<input class="fb-btn" type="button" onclick="search();"
					value="<spring:message code='button_search' />">
			</fieldset>
		</div>
	</div>

	<br>

	<div class="my-team">
		<c:forEach items="${playerList}" var="player" varStatus="status">

			<c:if test="${not empty playerList}">
				<ul class="green pricing jcarousel-list jcarousel-list-horizontal">
					<li
						class="jcarousel-item jcarousel-item-horizontal jcarousel-item-2 jcarousel-item-2-horizontal player-card"
						jcarouselindex="2">
						<ul>
							<li class="title">
								<div class="contain">
									<img src="resources/images/players/${player.img_src}"
										width="115" height="115" alt="testimonial" class="circled">
									<div class="spinner"
										style="opacity: 0; transform: rotate(2160deg) scale(1, 1);"></div>
								</div>
								<h3>
									${player.player_name}
								</h3>
							<li>
								<spring:message code='player_position' />
								:${player.pos}
							</li>
							<li>
								<spring:message code='salary' />
								:${player.sal}
								<i class="fa fa-line-chart fa-cursor-right"
									onclick="to_chart('player_ev','${player.player_id}')"></i>
							</li>
							<li>
								<c:if test="${tradeAble}">
									<a href="#"
										onclick="sign_player('${player.player_id}','<spring:message code="confirm_sign"/>')"><spring:message
											code='sign' /> </a>
								</c:if>
								<c:if test="${! tradeAble}">
									<h1>
										<spring:message code="trade_able_time" />
									</h1>
								</c:if>
							</li>
						</ul>
					</li>
				</ul>
			</c:if>

		</c:forEach>
	</div>
</div>

<script>
	$("#loading").hide();
</script>

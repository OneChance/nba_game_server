<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
	jQuery("#tomarket").click(function() {
		jQuery("#market").click();
	});
</script>

<div class="container">
	<div class="feedback-index fi-left ">

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

			<c:if test="${empty team}">
				<form>
					<div class="fb-rows">
						<input id="team_name" class="fb-ipt"
							placeholder="<spring:message code="team_name"/>:" type="text">
					</div>

					<div class="fb-rows">
						<input class="fb-btn" type="button"
							onclick="create_team('<spring:message code="need_team_name"/>')"
							value="<spring:message code="create_team"/>">
					</div>
				</form>
			</c:if>

			<c:if test="${not empty team}">
				<ul class="green pricing jcarousel-list jcarousel-list-horizontal"
					style="width: 300px; padding-top: 0px;">
					<li
						class="jcarousel-item jcarousel-item-horizontal jcarousel-item-2 jcarousel-item-2-horizontal"
						jcarouselindex="2"
						style="float: left; list-style: none; width: 300px !important; margin-top: 0px !important; margin-right: 0px !important;">
						<ul>
							<li class="title" style="height: 60px;">
								<h3>
									${team.team_name}
								</h3>
							</li>
							<li class="divider"></li>
							<li>
								<spring:message code="team_money" />
								:${team.team_money}
							</li>
							<li>
								<spring:message code="ev" />
								:${team.ev}
							</li>
							<li></li>
						</ul>
					</li>
				</ul>
			</c:if>

		</c:if>
	</div>


	<c:if test="${not empty team}">
		<div style="float: left;">
			<c:if test="${not empty team_players}">
				<c:forEach items="${team_players}" var="player" varStatus="status">
					<ul class="green pricing jcarousel-list jcarousel-list-horizontal"
						style="width: 218px; float: left; margin-left: 15px; margin-top: 10px; height: 583px">
						<li
							class="jcarousel-item jcarousel-item-horizontal jcarousel-item-2 jcarousel-item-2-horizontal"
							jcarouselindex="2"
							style="float: left; list-style: none; width: 218px !important;height: 583px; margin-top: 0px">
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
								<li class="divider"></li>
								<li>
									<spring:message code='player_position' />
									:${player.pos}
								</li>
								<li>
									<spring:message code='salary' />
									:<h8>${player.sal}</h8>
								</li>

								<li>
									<spring:message code='shoot' />
									:<h8>${player.shoot}</h8>&nbsp;&nbsp;
									<spring:message code='free_throw' />
									:<h8>${player.free_throw}</h8>
								</li>
								<li>
									<spring:message code='rebound' />
									:<h8>${player.rebound}</h8>&nbsp;&nbsp;
									<spring:message code='assist' />
									:<h8>${player.assist}</h8>
								</li>
								<li>
									<spring:message code='steal' />
									:<h8>${player.steal}</h8>&nbsp;&nbsp;
									<spring:message code='block' />
									:<h8>${player.block}</h8>
								</li>
								<li>
									<spring:message code='fault' />
									:<h8>${player.fault}</h8>&nbsp;&nbsp;
									<spring:message code='foul' />
									:<h8>${player.foul}</h8>
								</li>
								<li>
									<spring:message code='point' />
									:<h8>${player.point}</h8>
								</li>
								<li>
									<spring:message code='today_ev' />
									:<h8>${player.ev}</h8>
								</li>

								<li>
									<c:if test="${tradeAble}">
										<a href="#"
											onclick="unsign_player('${player.player_id}','<spring:message code="confirm_unsign"/>')"><spring:message
												code='unsign' />
										</a>
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
				</c:forEach>
			</c:if>
			<c:if test="${empty team_players and not empty loginu}">
				<a class="button" href="#"
					style="margin-top: 50px; margin-left: 100px; padding: 20px"
					id="tomarket"><spring:message code="noplayer" />
				</a>
			</c:if>
		</div>
	</c:if>

</div>


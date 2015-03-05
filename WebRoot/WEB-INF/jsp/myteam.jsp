
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", -10);
%>


<script>
	jQuery("#tomarket").click(function() {
		jQuery("#market").click();
	});
</script>

<div class="container">

	<c:if test="${not_enough_player}">
		<a class="game-tip" href="#" id="tomarket"><spring:message
				code="not_enough_player" /> </a>
	</c:if>

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

			<c:if test="${empty team}">
				<form style="margin-top: 50px;">
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
				<ul
					class="green pricing jcarousel-list jcarousel-list-horizontal team_div">
					<li
						class="jcarousel-item jcarousel-item-horizontal jcarousel-item-2 jcarousel-item-2-horizontal team_div"
						jcarouselindex="2"
						style="float: left; list-style: none; margin-right: 0px !important;">
						<ul style="height: 285px;">
							<li class="title" style="height: 60px;">
								<h3>${team.team_name}</h3>
							</li>

							<li class="divider"></li>
							<li><spring:message code="manager" /> :${loginu.user_name}
							</li>
							<li><spring:message code="team_money" /> : <i
								class="fa fa-dollar"></i>${team.team_money}</li>
							<li><spring:message code="ev" /> :${team.ev}</li>
							<li><spring:message code="arena_in" /> : <i
								class="fa fa-dollar"></i>${team.dil.arena_in}</li>
							<li><spring:message code="player_sal_all" /> : <i
								class="fa fa-dollar"></i>${team.dil.pay}</li>
							<li style="padding: 10px 20px 10px 20px; text-align: left;">
								<spring:message code="today_profit" /> :${team.dil.profit}
							</li>
						</ul>
					</li>
				</ul>


				<ul
					class="green pricing jcarousel-list jcarousel-list-horizontal team_div">
					<li
						class="jcarousel-item jcarousel-item-horizontal jcarousel-item-2 jcarousel-item-2-horizontal team_div"
						jcarouselindex="2"
						style="float: left; list-style: none; margin-right: 0px !important;">
						<ul style="height: 210px;">
							<li class="title"
								style="height: 110px; background-color: #FF8800 !important;">
								<h3>
									${team.arena.arena_name}&nbsp;&nbsp; <img
										src="resources/images/arena/${team.arena.arena_img}"
										width="80" height="80" alt="testimonial" class="boxed">
								</h3>


							</li>
							<li class="divider"></li>
							<li><spring:message code="arena_cap" /> :${team.arena.cap}
								&nbsp;&nbsp; <c:if
									test="${tradeAble and team.arena.cap_level<10}">
									<i class="fa fa-wrench fa-cursor-right"
										onclick="update_cap(${team.arena.cap_level})"></i>
								</c:if></li>
							<li style="padding: 10px 20px 10px 20px; text-align: left;">
								<spring:message code="arena_attend" />
								:${team.arena.attendance} &nbsp;&nbsp; <c:if
									test="${tradeAble and team.arena.eq_level<10}">
									<i class="fa fa-subway fa-cursor-right"
										onclick="update_eq(${team.arena.eq_level})"></i>
								</c:if>
							</li>
						</ul>
					</li>
				</ul>

			</c:if>
		</c:if>
	</div>


	<c:if test="${not empty team}">
		<div class="team_player_container" style="float: left;">
			<c:if test="${not empty team_players}">
				<c:forEach items="${team_players}" var="player" varStatus="status">
					<ul
						class="green pricing jcarousel-list jcarousel-list-horizontal team_player_div"
						style="width: 218px; float: left; margin-top: 10px; height: 550px">
						<li
							class="jcarousel-item jcarousel-item-horizontal jcarousel-item-2 jcarousel-item-2-horizontal"
							jcarouselindex="2"
							style="float: left; list-style: none; width: 218px !important; height: 550px; margin-top: 0px">
							<ul>
								<li class="title">
									<div class="contain">
										<img src="resources/images/players/${player.img_src}"
											width="115" height="115" alt="testimonial" class="circled">
										<div class="spinner"
											style="opacity: 0; transform: rotate(2160deg) scale(1, 1);"></div>
									</div>
									<h3>${player.player_name}</h3>
								<li class="divider"></li>
								<li><spring:message code='player_position' />
									:${player.pos}</li>
								<li><spring:message code='salary' /> : <h8>
									${player.sal} </h8></li>

								<li><spring:message code='shoot' /> : <h8>
									${player.shoot} </h8> &nbsp;&nbsp; <spring:message code='free_throw' />
									: <h8> ${player.free_throw} </h8></li>
								<li><spring:message code='rebound' /> : <h8>
									${player.rebound} </h8> &nbsp;&nbsp; <spring:message code='assist' />
									: <h8> ${player.assist} </h8></li>
								<li><spring:message code='steal' /> : <h8>
									${player.steal} </h8> &nbsp;&nbsp; <spring:message code='block' />
									: <h8> ${player.block} </h8></li>
								<li><spring:message code='fault' /> : <h8>
									${player.fault} </h8> &nbsp;&nbsp; <spring:message code='foul' /> :
									<h8> ${player.foul} </h8></li>
								<li><spring:message code='point' /> : <h8>
									${player.point} </h8></li>
								<li><spring:message code='today_ev' /> : <h8>
									${player.ev} </h8></li>

								<li><c:if test="${tradeAble}">
										<a href="#"
											onclick="unsign_player('${player.player_id}','<spring:message code="confirm_unsign"/>')"><spring:message
												code='unsign' /> </a>
									</c:if> <c:if test="${! tradeAble}">
										<h1>
											<spring:message code="trade_able_time" />
										</h1>
									</c:if></li>
							</ul>
						</li>
					</ul>
				</c:forEach>
			</c:if>

		</div>
	</c:if>

</div>

<script>
	$(".loadingbg").hide();
</script>

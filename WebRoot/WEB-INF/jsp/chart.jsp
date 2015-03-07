<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script id="others_zepto_10rc1" type="text/javascript" class="library"
	src="resources/js/zepto.min.js"></script>
<script src="resources/js/d3.v2.min.js"></script>
<script src="resources/js/xcharts.min.js"></script>
<link rel="stylesheet" href="resources/css/xcharts.min.css">

<figure style="height: 300px;" id="chart_table">
	<div>
		<a class="button_small" href="javacript:void(0);"
			onclick="close_chart()"><i class="fa fa-close fa3x"></i> </a>
	</div>
</figure>

<div class="chart-note chart-note-color chart-note-green"></div>
<div class="chart-note chart-note-text">
	<spring:message code="pay" />
</div>
<div class="chart-note chart-note-color chart-note-red"></div>
<div class="chart-note chart-note-text">
	<spring:message code="income" />
</div>
<div class="chart-note chart-note-color chart-note-yellow"></div>
<div class="chart-note chart-note-text">
	<spring:message code="profit" />
</div>
<script>
	function myXFormat(x) {
		var xFormat = d3.time.format('%A');
		if (xFormat(x) == 'Monday') {
			return "<spring:message code="Monday"/>";
		}
		if (xFormat(x) == 'Tuesday') {
			return "<spring:message code="Tuesday"/>";
		}
		if (xFormat(x) == 'Wednesday') {
			return "<spring:message code="Wednesday"/>";
		}
		if (xFormat(x) == 'Thursday') {
			return "<spring:message code="Thursday"/>";
		}
		if (xFormat(x) == 'Friday') {
			return "<spring:message code="Friday"/>";
		}
		if (xFormat(x) == 'Saturday') {
			return "<spring:message code="Saturday"/>";
		}
		if (xFormat(x) == 'Sunday') {
			return "<spring:message code="Sunday"/>";
		}
	}

	jQuery(function() {
		getChart('team_money', 'week');
	});

	function getChart(type, category) {
		jQuery.ajax({
			type : "POST",
			url : "game/get_chart/",
			data : {
				type : type,
				category : category
			},
			cache : false,
			success : function(json) {
				if (json.message != '') {
					alert(json.message);
				} else {
					var data = json.data;

					var order = [ 0, 1, 0, 2 ], i = 0, chart = new xChart(
							'line-dotted', data[order[i]], '#chart_table', {
								axisPaddingTop : 10,
								dataFormatX : function(x) {
									return new Date(x);
								},
								tickFormatX : function(x) {
									return myXFormat(x);
								},
								mouseover : chart_point_mouseover,
								mouseout : chart_point_mouseout,
								timing : 1250
							}), rotateTimer, toggles = d3
							.selectAll('.multi button'), t = 3500;
				}
			}
		});
	}

	var tt = document.createElement('div');
	tt.className = 'ex-tooltip';
	document.body.appendChild(tt);
	var pos_x, pos_y;

	function chart_point_mouseover(d) {

		$(document).mousemove(function(e) {
			pos_x = e.pageX;
			pos_y = e.pageY;
		});
	
		$(tt).css("position", "absolute");
		$(tt).css("left", pos_x + 10);
		$(tt).css("top", pos_y + 10);

		$(tt).text(myXFormat(d.x) + ': ' + d.y).show();
	}

	function chart_point_mouseout() {
		$(tt).hide();
	}
</script>



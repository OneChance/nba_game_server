<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script id="others_zepto_10rc1" type="text/javascript" class="library"
	src="resources/js/zepto.min.js"></script>
<script src="resources/js/d3.v2.min.js"></script>
<script src="resources/js/xcharts.min.js"></script>
<link rel="stylesheet" href="resources/css/xcharts.min.css">

<figure style="height: 300px;" id="chart_table">
<div class="chart-title">
	<h3 id="title_text">
		<spring:message code="player_7" />
	</h3>
</div>
<div>
	<i class="fa fa-close fa-2x fa-cursor-right white-i"
		onclick="close_chart()"></i>
</div>
</figure>

<div class="chart-note chart-note-color chart-note-red chart-note-left"></div>
<div class="chart-note chart-note-text">
	<spring:message code="e_value" />
</div>

<script>

	jQuery(function() {
		getChart('${player_id}');
	});

	function getChart(player_id) {

		jQuery.ajax({
			type : "POST",
			url : "game/get_chart/",
			data : {
				type : 'player_ev',
				player_id : player_id
			},
			cache : false,
			success : function(json) {
				if (json.message != '') {
					alert(json.message);
				} else {
					var data = json.data;

					if (data == 'no-data') {
						jQuery("#title_text").append(
								"[<spring:message code="chart_no_data" />]");
					} else {
						var order = [ 0, 1, 0, 2 ], i = 0, chart = new xChart(
								'line-dotted', data[order[i]], '#chart_table',
								{
									axisPaddingTop : 10,
									
									dataFormatX : function(x) {
										return x;
									},
									mouseover : chart_point_mouseover,
									mouseout : chart_point_mouseout,
									timing : 1250
								}), rotateTimer, toggles = d3
								.selectAll('.multi button'), t = 3500;
					}
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

		$(tt).text(d.x + ': ' + d.y).show();
	}

	function chart_point_mouseout() {
		$(tt).hide();
	}
</script>



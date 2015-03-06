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

<script>

	function myXFormat(x){
			var xFormat = d3.time.format('%A');
			if(xFormat(x)=='Monday'){
				return "<spring:message code="Monday"/>";
			}
			if(xFormat(x)=='Tuesday'){
				return "<spring:message code="Tuesday"/>";
			}
			if(xFormat(x)=='Wednesday'){
				return "<spring:message code="Wednesday"/>";
			}
			if(xFormat(x)=='Thursday'){
				return "<spring:message code="Thursday"/>";
			}
			if(xFormat(x)=='Friday'){
				return "<spring:message code="Friday"/>";
			}
			if(xFormat(x)=='Saturday'){
				return "<spring:message code="Saturday"/>";
			}
			if(xFormat(x)=='Sunday'){
				return "<spring:message code="Sunday"/>";
			}
		}	

	jQuery(function() {
		
		
		jQuery.ajax({
			type : "POST",
			url : "game/get_chart/",
			data : {
				
			},
			cache : false,
			success : function(json) {
				if (json.message != '') {
					alert(json.message);
				}else{
					var data = json.data;
		
					var order = [ 0, 1, 0, 2 ], i = 0, chart = new xChart(
					'line-dotted', data[order[i]], '#chart_table', {
						axisPaddingTop : 5,
						dataFormatX : function(x) {
							return new Date(x);
						},
						tickFormatX : function(x) {
							return myXFormat(x);
						},
						timing : 1250
					}), rotateTimer, toggles = d3.selectAll('.multi button'), t = 3500;
				}
			}
		});
		
		
	});
				
				
</script>



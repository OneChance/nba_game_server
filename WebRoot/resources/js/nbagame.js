jQuery(".ai-nav-listitem").click(function() {

	jQuery(".ai-nav-listitem").each(function() {
		jQuery(this).removeClass("cur");
	});

	jQuery(this).addClass("cur");

});

jQuery(".options a").click(function() {
	jQuery(".options li a").each(function() {
		jQuery(this).removeClass("active");
	});

	jQuery(this).addClass("active");

});

var update_cap = function(current_level) {

	var update_cost = current_level * current_level * 5000
	confirm_update_cap = confirm_update_cap
			.replace(/cost_replace/, update_cost);

	to_confirm(confirm_update_cap, function() {

		$("#loading").show();

		jQuery.ajax({
			type : "POST",
			url : "game/update_cap/",
			data : {

			},
			cache : false,
			success : function(json) {
				if (json.message != '') {
					to_alert(json.message);
				}else{
					parent.location.reload();
				}	
				
				$("#loading").hide();
			}
		});
	});
}

var update_eq = function(current_level) {

	var update_cost = current_level * current_level * 5000;
	confirm_update_eq = confirm_update_eq.replace(/cost_replace/, update_cost);

	to_confirm(confirm_update_eq, function() {

		$("#loading").show();

		jQuery.ajax({
			type : "POST",
			url : "game/update_eq/",
			data : {

			},
			cache : false,
			success : function(json) {
				if (json.message != '') {
					to_alert(json.message);
				}else{
					parent.location.reload();
				}
				
				$("#loading").hide();
			}
		});
	});
}

var to_chart = function(type, player_id) {
	$("#chart").show();
	$("#chart_container").load(
			"game/chart/?type=" + type + "&player_id=" + player_id);
}

var close_chart = function() {
	$("#chart").hide();
}

var close_confirm = function() {
	$("#confirm").hide();
}

var alert_confirm = function() {
	$("#alert").hide();
}

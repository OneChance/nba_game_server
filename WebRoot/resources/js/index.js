function reg(namenull, needpassword) {

	$("#loading").show();

	var user_name = jQuery("#user_name").val();
	var password = jQuery("#password").val();

	if (!user_name) {
		to_alert(namenull);
		jQuery("#user_name").focus();
		$("#loading").hide();
		return false;
	}

	else if (!password) {
		to_alert(needpassword)
		jQuery("#password").focus();
		$("#loading").hide();
		return false;
	}

	jQuery.ajax({
		type : "POST",
		url : "account/register/",
		data : {
			user_name : user_name,
			password : password
		},
		cache : false,
		success : function(json) {
			if (json.message != '') {
				to_alert(json.message);
			} else {
				// 跳转到我的球队去登陆
				jQuery("#team").click();
			}

			$("#loading").hide();
		}
	});
}

function login(namenull, needpassword) {

	$("#loading").show();

	var user_name = jQuery("#user_name").val();
	var password = jQuery("#password").val();

	if (!user_name) {
		to_alert(namenull);
		jQuery("#user_name").focus();
		$("#loading").hide();
		return false;
	}

	else if (!password) {
		to_alert(needpassword)
		jQuery("#password").focus();
		$("#loading").hide();
		return false;
	}

	jQuery.ajax({
		type : "POST",
		url : "account/login/",
		data : {
			user_name : user_name,
			password : password
		},
		cache : false,
		success : function(json) {
			if (json.message != '') {
				to_alert(json.message);
				$("#loading").hide();
			} else {
				parent.location.reload();
			}
		}
	});
}

function create_team(teamnamenull) {

	$("#loading").show();

	var team_name = jQuery("#team_name").val();

	if (!team_name) {
		to_alert(teamnamenull);
		jQuery("#team_name").focus();
		$("#loading").hide();
		return false;
	}

	jQuery.ajax({
		type : "POST",
		url : "game/create_team/",
		data : {
			team_name : team_name
		},
		cache : false,
		success : function(json) {
			if (json.message != '') {
				to_alert(json.message);
			} else {
				jQuery("#team").click();
			}

			$("#loading").hide();
		}

	});
}

function search() {
	var pos_select = jQuery("#pos_select").val();
	$("#mainframe").load("game/market/?pos_select=" + pos_select);
}

var global_yes_func;
var global_param;

function to_confirm(msg, yes_func, params) {
	$("#confirm_msg").html(msg);
	$("#confirm").show();
	global_yes_func = yes_func;
	global_param = params;
}

function to_alert(msg) {
	$("#alert_msg").html(msg);
	$("#alert").show();
}

function confirm_yes() {
	$("#confirm").hide();
	global_yes_func.apply(this, global_param);
}

function sign_player(player_id, confirm_sign) {

	to_confirm(confirm_sign, function(player_id) {

		$("#confirm").hide();
		$("#loading").show();

		var pos_select = jQuery("#pos_select").val();

		jQuery.ajax({
			type : "POST",
			url : "game/sign_player/",
			data : {
				player_id : player_id
			},
			cache : false,
			success : function(json) {
				if (json.message != '') {
					to_alert(json.message);
				} else {
					$("#mainframe").load(
							"game/market/?pos_select=" + pos_select);
				}

				$("#loading").hide();
			}
		});
	}, [ player_id ]);
}

function unsign_player(player_id, confirm_unsign) {

	to_confirm(confirm_unsign, function(player_id) {

		$("#loading").show();

		jQuery.ajax({
			type : "POST",
			url : "game/unsign_player/",
			data : {
				player_id : player_id
			},
			cache : false,
			success : function(json) {
				if (json.message != '') {
					to_alert(json.message);
				} else {
					$("#mainframe").load("game/myteam/");
				}
				$("#loading").hide();
			}
		});
	}, [ player_id ]);
}

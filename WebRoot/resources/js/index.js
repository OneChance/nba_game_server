function reg(namenull, needpassword) {

	$("#loading").show();
	
	var user_name = jQuery("#user_name").val();
	var password = jQuery("#password").val();

	if (!user_name) {
		alert(namenull);
		jQuery("#user_name").focus();
		$("#loading").hide();
		return false;
	}

	else if (!password) {
		alert(needpassword)
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
				alert(json.message);
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
		alert(namenull);
		jQuery("#user_name").focus();
		$("#loading").hide();
		return false;
	}

	else if (!password) {
		alert(needpassword)
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
				alert(json.message);
			}
			parent.location.reload();
		}
	});
}

function create_team(teamnamenull) {

	$("#loading").show();
	
	var team_name = jQuery("#team_name").val();

	if (!team_name) {
		alert(teamnamenull);
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
				alert(json.message);
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

function sign_player(player_id,confirm_sign) {
	if (confirm(confirm_sign)) {
		
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
					alert(json.message);
				} else {
					$("#mainframe").load(
							"game/market/?pos_select=" + pos_select);
				}
				
				$("#loading").hide();
			}
		});
	}
}

function unsign_player(player_id, confirm_unsign) {

	if (confirm(confirm_unsign)) {
		
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
					alert(json.message);
				} else {
					$("#mainframe").load("game/myteam/");
				}
				$("#loading").hide();
			}
		});
	}

}

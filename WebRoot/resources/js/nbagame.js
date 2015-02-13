jQuery(function(){
	$("#mainframe").load("game/myteam/");
});

jQuery(".ai-nav-listitem").click(function() {

	jQuery(".ai-nav-listitem").each(function() {
		jQuery(this).removeClass("cur");
	});

	jQuery(this).addClass("cur");

});

jQuery("#reg").click(function() {
	$("#mainframe").load("account/reg/");
});

jQuery("#team").click(function() {
	$("#mainframe").load("game/myteam/");
});

jQuery("#market").click(function() {
	$("#mainframe").load("game/market/");
});

jQuery("#rank").click(function() {
	$("#mainframe").load("game/rank/");
});
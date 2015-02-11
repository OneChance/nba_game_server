jQuery(".ai-nav-listitem").click(function() {

	jQuery(".ai-nav-listitem").each(function() {
		jQuery(this).removeClass("cur");
	});

	jQuery(this).addClass("cur");

});

jQuery("#reg").click(function() {
	$("#mainframe").load("account/reg/");
});
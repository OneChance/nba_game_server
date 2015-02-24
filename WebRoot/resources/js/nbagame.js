
jQuery(".ai-nav-listitem").click(function() {

	jQuery(".ai-nav-listitem").each(function() {
		jQuery(this).removeClass("cur");
	});

	jQuery(this).addClass("cur");

});


jQuery(".options a").click(function() {
	alert('xxx')
	jQuery(".options li a").each(function() {
		jQuery(this).removeClass("active");
	});

	jQuery(this).addClass("active");

});





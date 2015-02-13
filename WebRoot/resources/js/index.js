
function reg(namenull,needpassword){
	
	var user_name = jQuery("#user_name").val();
    var password = jQuery("#password").val();
    
    if (!user_name) {
        alert(namenull);
        jQuery("#user_name").focus();
        return false;
    }

    else if (!password) {
        alert(needpassword)
        jQuery("#password").focus();
        return false;
    }
	
    jQuery.ajax({
        type: "POST",
        url: "account/register/",
        data: { user_name: user_name, password: password},
        cache: false,
        success: function (json) {
            if(json.message != ''){
            	alert(json.message);       	
            }else{
            	//跳转到我的球队去登陆
            	jQuery("#team").click();
            }
        }
    });
}

function login(namenull,needpassword){
	
	var user_name = jQuery("#user_name").val();
    var password = jQuery("#password").val();
    
    if (!user_name) {
        alert(namenull);
        jQuery("#user_name").focus();
        return false;
    }

    else if (!password) {
        alert(needpassword)
        jQuery("#password").focus();
        return false;
    }
	
    jQuery.ajax({
        type: "POST",
        url: "account/login/",
        data: { user_name: user_name, password: password},
        cache: false,
        success: function (json) {
            if(json.message != ''){
            	alert(json.message);       	
            }else{
            	//刷新我的球队页面
            	jQuery("#team").click();
            }
        }
    });
}

function oncase(nodeid) {
    $.getJSON("/include/casegallery.ashx?cate=" + nodeid).always(function (date) {
        var cacheDate = date;
        var cahceDiv = $("<div>");
        var target_grid = $("#case_grid");
        var target_magic = $(".magicwall");
        $.each(cacheDate, function (key, value) {
            //console.log(key,value["image_url"],value["image_title"],value["image_detal"],value["image_mark"])
            var wrapLink = $("<a>").addClass("hover-content").attr("href", value["image_link"])
				.append(
					$("<div>").addClass("hover-content-inner")
						.append($("<h2>").text(value["image_title"]))
						.append($("<p>").text(value["image_detal"]))
				);
            var items = $("<li>").addClass(value["image_mark"]).attr({
                "data-thumb": value["image_url"]
            });
            var itemsMerge = items.append(wrapLink);
            cahceDiv.append(itemsMerge);
        });

        target_grid.html(cahceDiv.html());
        $.getScript("/js/jquery.magicwall.min.js", function () {
			var options = {
				delay: 1300,
				preloadBeforeSwitch: true,
				animations: " flipX,flipY,slideX,slideY,slideRow,slideColumn,fade,-flipX,-flipY,-slideX,-slideY,-slideRow,-slideColumn,-fade",
				maxItemHeight: 120,
				maxColumnsCount: 5,
				flipXDuration: 1200,
				flipXEasing: "easeInOutBack",
				flipYDuration: 1200,
				flipYEasing: "easeInOutBack",
				slideXDuration: 1500,
				slideXEasing: "easeInOutBack",
				slideYDuration: 1500,
				slideYEasing: "easeInOutBack",
				rowsCount :3,
				pauseOnHover: "all"
			};
            target_magic.magicWall(options);
        });
    });
}

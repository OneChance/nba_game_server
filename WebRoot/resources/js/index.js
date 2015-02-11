
function check() {

    var user_name = jQuery("#user_name").val();
    var password = jQuery("#password").val();
    
    if (!name) {
        alert("请输入账号!");
        jQuery("#user_name").focus();
        return false;
    }

    else if (!password) {
        alert("请输入密码")
        jQuery("#password").focus();
        return false;
    }
  
    else {
        jQuery.ajax({
            type: "POST",
            url: "/interface/contact.aspx",
            data: { txtname: jQuery("#txtname").val(), txtCompany: jQuery("#txtCompany").val(), txtemail: jQuery("#txtemail").val(), txtContent: jQuery("#txtContent").val(), tel: tel, qq: qq },
            cache: false,
            success: function (msg) {
                if (msg == 1) {
                    alert("留言信息已提交成功!");

                    jQuery("#txtname").val("");
                    jQuery("#txtContent").val("");
                    jQuery("#txtemail").val("");
                    jQuery("#txttel").val("");
                }
                else
                    alert(msg);

            }
        })
    }
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

$(function () {    
    $(".ai-banner-1").click(function () { location.href = "/about/us.html"; });
    $(".ai-banner-2").click(function () { location.href = "/service/weixin.html"; });
    $(".ai-banner-3").click(function () { location.href = "/service/mobile.html"; });
    $(".ai-banner-4").click(function () { location.href = "/service/3d.html"; });
    $(".ai-banner-5").click(function () { location.href = "/service/chart.html"; });

    $(".count-wrap").click(function () { location.href = "/about/us.html"; });

    //$(".si-qq").click(function () {
    //    window.open('http://b.qq.com/webc.htm?new=0&sid=4000218655&eid=218808P8z8p8p8R8z8q8p&o=www.adinnet.cn&q=7&ref=' + document.location, '_blank', 'height=544, width=644,toolbar=no,scrollbars=no,menubar=no,status=no');
    //    window.opener = null; window.close();
    //});

    //var useragent = navigator.userAgent;
    //var ipad = /ipad/i.test(useragent);
    //var webkit = /applewebkit/i.test(useragent);
    //var firefox = /firefox/i.test(useragent);
    //var ios = /ipad|ipod|iphone/i.test(useragent);
    //var mao = location.hash.replace("#","");
    //var targetNav = $(".ai-nav-list");
    //var scrollelem = (function () {
    //    if (webkit === true) {
    //        return $("body")
    //    } else if (firefox === true) {
    //        return $("html")
    //    } else {
    //        return $("html")
    //    }
    //})();

    //if ($this.hasClass("cur")) { return false; }
    //var idx = mao;
    //var idx_head_height = targetNav[0].clientHeight;
    //var sct = document.getElementById(idx).offsetTop;
    //alert(sct + "===" + document.getElementById("index_blog").offsetTop);
    //var _easing = "";
    //if (sct >= 800) {
    //    _easing = "easeOutExpo";
    //} else {
    //    _easing = "swing";
    //}
    //scrollelem.animate({
    //    "scrollTop": sct - idx_head_height
    //}, 300);
});

function loadM() {

   // var $this = $("#" + mao + "1");
    alert(1);
    // $this.click();
}
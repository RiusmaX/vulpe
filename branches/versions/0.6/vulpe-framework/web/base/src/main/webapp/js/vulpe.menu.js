var vulpeMenu = {
	arrowImages : {
		down : [ "downArrow", "../themes/" + vulpe.config.theme + "/images/menu/down.gif", 23 ],
		right : [ "rightArrow", "../themes/" + vulpe.config.theme + "/images/menu/right.gif" ]
	},
	transition : {
		overtime : 300,
		outtime : 300
	},
	shadow : {
		enable : true,
		offsetx : 5,
		offsety : 5
	},
	showHideDelay : {
		showdelay : 100,
		hidedelay : 200
	},

	detectWebkit : navigator.userAgent.toLowerCase().indexOf("applewebkit") != -1,
	detectie6 : document.all && !window.XMLHttpRequest,
	css3support : window.msPerformance || (!document.all && document.querySelector),
	ajaxMenu : function($, setting) {
		var menuContainer = $("#" + setting.contentsource[0])
		menuContainer.html("Loading Menu...")
		$.ajax( {
			url : setting.contentsource[1],
			async : true,
			error : function(ajaxrequest) {
				menuContainer.html("Error fetching content. Server Response: " + ajaxrequest.responseText);
			},
			success : function(content) {
				menuContainer.html(content);
				vulpeMenu.buildMenu($, setting);
			}
		})
	},

	buildMenu : function($, setting) {
		var mainMenu = $("#" + setting.mainmenuid + ">ul");
		mainMenu.parent().get(0).className = setting.classname || "vulpeMenu"
		var headers = mainMenu.find("ul").parent()
		headers.hover(function(e) {
				$(this).children("a:eq(0)").addClass("selected")
			}, 
			function(e) {
				$(this).children("a:eq(0)").removeClass("selected")
			}
		);
		headers.each(function(i) {
				var currentObject = $(this).css( {
					zIndex : 100 - i
				});
				var subUL = $(this).find("ul:eq(0)").css( {
					display : "block"
				});
				subUL.data("timers", {});
				this._dimensions = {
					w : this.offsetWidth,
					h : this.offsetHeight,
					subulw : subUL.outerWidth(),
					subulh : subUL.outerHeight()
				}
				this.istopheader = currentObject.parents("ul").length == 1 ? true : false;
				subUL.css( {
					top : this.istopheader && setting.orientation != "v" ? this._dimensions.h + "px" : 0
				});
				if (currentObject.html().indexOf("<img") == -1) {
					currentObject.children("a:eq(0)").css(this.istopheader ? {
							paddingRight : vulpeMenu.arrowImages.down[2]
							} : {}
					).append("<img src=\"" + (this.istopheader && setting.orientation != "v" ? vulpeMenu.arrowImages.down[1] : vulpeMenu.arrowImages.right[1])
							+ "\" class=\""	+ (this.istopheader && setting.orientation != "v" ? vulpeMenu.arrowImages.down[0] : vulpeMenu.arrowImages.right[0])
							+ "\" style=\"border:0;\" />");
				}
				if (vulpeMenu.shadow.enable && !vulpeMenu.css3support) {
					this._shadowoffset = {
						x : (this.istopheader ? subUL.offset().left + vulpeMenu.shadow.offsetx : this._dimensions.w),
						y : (this.istopheader ? subUL.offset().top + vulpeMenu.shadow.offsety	: currentObject.position().top)
					}
					if (this.istopheader) {
						$parentshadow = $(document.body);
					} else {
						var $parentLi = currentObject.parents("li:eq(0)");
						$parentshadow = $parentLi.get(0).$shadow;
					}
					this.$shadow = $("<div class=\"vulpeMenuShadow" + (this.istopheader ? " vulpeMenuTopLevelShadow" : "") + "\"></div>").prependTo($parentshadow).css({
						left : this._shadowoffset.x + "px",
						top : this._shadowoffset.y + "px"
					});
				}
				currentObject.hover(function(e) {
					var targetUL = subUL;
					var header = currentObject.get(0);
					clearTimeout(targetUL.data("timers").hidetimer);
					targetUL.data("timers").showtimer = setTimeout(function() {
											header._offsets = {
												left : currentObject.offset().left,
												top : currentObject.offset().top
											}
											var menuleft = header.istopheader && setting.orientation != "v" ? 0	: header._dimensions.w;
											menuleft = (header._offsets.left + menuleft
													+ header._dimensions.subulw > $(window).width()) ? (header.istopheader
													&& setting.orientation != "v" ? -header._dimensions.subulw
													+ header._dimensions.w
													: -header._dimensions.w)
													: menuleft;
											if (targetUL.queue().length <= 1) {
												targetUL.css( {
													left : menuleft + "px",
													width : header._dimensions.subulw + "px"
												}).animate( {
													height : "show",
													opacity : "show"
												}, vulpeMenu.transition.overtime)
												if (vulpeMenu.shadow.enable
														&& !vulpeMenu.css3support) {
													var shadowleft = header.istopheader ? targetUL
															.offset().left
															+ vulpeMenu.shadow.offsetx
															: menuleft
													var shadowtop = header.istopheader ? targetUL
															.offset().top
															+ vulpeMenu.shadow.offsety
															: header._shadowoffset.y
													if (!header.istopheader
															&& vulpeMenu.detectWebkit) { // in
														header.$shadow.css( {
															opacity : 1
														})
													}
													header.$shadow.css( {
														overflow : "",
														width : header._dimensions.subulw + "px",
														left : shadowleft + "px",
														top : shadowtop + "px"
													}).animate( {
														height : header._dimensions.subulh + "px"
													}, vulpeMenu.transition.overtime)
												}
											}
										}, vulpeMenu.showHideDelay.showdelay)
							}, function(e) {
								var targetUL = subUL;
								var header = currentObject.get(0);
								clearTimeout(targetUL.data("timers").showtimer);
								targetUL.data("timers").hidetimer = setTimeout(function() {
									targetUL.animate( {
										height : "hide",
										opacity : "hide"
									}, vulpeMenu.transition.outtime)
									if (vulpeMenu.shadow.enable && !vulpeMenu.css3support) {
										if (vulpeMenu.detectWebkit) {
											header.$shadow.children("div:eq(0)").css( {
												opacity : 0
											})
										}
										header.$shadow.css({
											overflow : "hidden"
										}).animate({
											height : 0
										}, vulpeMenu.transition.outtime);
									}
								}, vulpeMenu.showHideDelay.hidedelay);
							}
					)
			})
	if (vulpeMenu.shadow.enable && vulpeMenu.css3support) {
		var $toplevelul = $("#" + setting.mainmenuid + " ul li ul");
		var css3shadow = parseInt(vulpeMenu.shadow.offsetx) + "px " + parseInt(vulpeMenu.shadow.offsety) + "px 5px #aaa";
		var shadowprop = [ "boxShadow", "MozBoxShadow", "WebkitBoxShadow", "MsBoxShadow" ];
		for ( var i = 0; i < shadowprop.length; i++) {
			$toplevelul.css(shadowprop[i], css3shadow);
		}
	}
	mainMenu.find("ul").css({
		display : "none",
		visibility : "visible"
	});
},

init : function(setting) {
	if (typeof setting.customtheme == "object" && setting.customtheme.length == 2) {
		var mainmenuid = "#" + setting.mainmenuid;
		var mainselector = (setting.orientation == "v") ? mainmenuid : mainmenuid + ", " + mainmenuid;
		document.write("<style type=\"text/css\">\n" + mainselector + " ul li a {background:" + setting.customtheme[0] + ";}\n" + mainmenuid + " ul li a:hover {background:" + setting.customtheme[1] + ";}\n" + "</style>");
	}
	this.shadow.enable = (document.all && !window.XMLHttpRequest) ? false : this.shadow.enable;
	jQuery(document).ready(function($) {
			if (typeof setting.contentsource == "object") {
				vulpeMenu.ajaxMenu($, setting);
			} else {
				vulpeMenu.buildMenu($, setting);
			}
		}
	)
}

}

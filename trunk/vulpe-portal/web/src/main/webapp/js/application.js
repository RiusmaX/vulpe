/**
 * JavaScript Application Document
 */
var app = {

	arrays : {
		categories : []
	},

	token : {
		otherLanguages : "-otherLanguages",
		showOtherLanguages : "-showOtherLanguages",
		languageIdFlag : "-languageIdFlag",
		languageId : "language_id"
	},

	frontend : {
		querySearch : function() {
			if (vulpe.util.isEmpty($('#querySearch').val())) {
				$('#querySearch').focus();
				return false;
			}
			return true;
		}
	},

	showByCategory : function(id) {
		$("div.type").hide();
		$.each(app.arrays.categories, function(i, obj) {
			if (obj.id == id) {
				if (obj.categoryType == "CONTENT") {
					$("#contentType").show();
					$(".vulpeNotRequired", "#contentType").removeClass("vulpeNotRequired").addClass("vulpeRequired");
					$(".vulpeRequired", "#downloadLinkType").removeClass("vulpeRequired").addClass("vulpeNotRequired");
				} else if (obj.categoryType == "DOWNLOAD" || obj.categoryType == "LINK") {
					$("#downloadLinkType").show();
					$(".vulpeNotRequired", "#downloadLinkType").removeClass("vulpeNotRequired").addClass("vulpeRequired");
					$(".vulpeRequired", "#contentType").removeClass("vulpeRequired").addClass("vulpeNotRequired");
				}
			}
		});
	},

	addRemoveVideo : function(message) {
		var video = vulpe.util.get("video")
		if (vulpe.util.isVisible(video)) {
			var videoURL = vulpe.util.getElementField("videoURL");
			var videoHeight = vulpe.util.getElementField("videoHeight");
			var videoWidth = vulpe.util.getElementField("videoWidth");
			if (videoURL.val().length > 0) {
				var command = function() {
					video.hide();
					videoURL.val("");
					videoHeight.val("");
					videoWidth.val("");
				}
				vulpe.view.warningDialog(message, command);
			} else {
				video.hide();
			}
		} else {
			video.show();
		}
	},

	textUpdate : function(id, textValue) {
		var value = vulpe.util.get(id + app.token.languageIdFlag).val();
		var fields = jQuery("input[id*='" + id + "']");
		for ( var i = 0; i < fields.length; i++) {
			var field = jQuery(fields[i]);
			var fieldType = field.attr("type");
			var fieldId = field.attr("id");
			if (fieldType == "hidden" && fieldId.indexOf(app.token.languageId) != -1 && (field.val() == value || field.val() == "")) {
				field.val(value);
				var fieldText = vulpe.util.getElementField("text", field);
				fieldText.val(textValue);
				break;
			}
		}
	},

	changeLanguage : function(id, value, editor) {
		var text = vulpe.util.getElementField("text", id);
		if (editor) {
			text.contents().find('body').html("");
			text.focus();
		} else {
			text.val("");
			text.focus();
		}
		var fields = jQuery("input[id*='" + id + "']");
		for ( var i = 0; i < fields.length; i++) {
			var field = jQuery(fields[i]);
			var fieldType = field.attr("type");
			var fieldId = field.attr("id");
			if (fieldType == "hidden" && fieldId.indexOf(app.token.languageId) != -1 && field.val() == value) {
				var fieldText = vulpe.util.getElementField("text", field);
				if (editor) {
					text.contents().find('body').html(fieldText.val());
				} else {
					text.val(fieldText.val());
					var otherLanguages = vulpe.util.get(id + app.token.otherLanguages);
					if (otherLanguages.css("display") != "none") {
						otherLanguages.hide();
						var showOtherLanguages = vulpe.util.get(id + app.token.showOtherLanguages);
						if (showOtherLanguages.length == 1) {
							showOtherLanguages.click();
							var src = showOtherLanguages.attr("src");
							showOtherLanguages.attr("src", src.replace("up.png", "down.png"));
						}
					}
				}
				break;
			}
		}
	},

	languageIdFlag : function(id, editor) {
		var languageIdFlag = vulpe.util.get(id + app.token.languageIdFlag);
		languageIdFlag.bind('change', function() {
			app.changeLanguage(id, languageIdFlag.val(), editor);
		});
		languageIdFlag.msDropDown();
	},

	showOtherLanguagesClick : function(id) {
		vulpe.util.get(id + app.token.showOtherLanguages).bind("click", function() {
			languageId = vulpe.util.get(id + app.token.languageIdFlag)
			var value = languageId.val();
			$("span", "#" + id + app.token.otherLanguages).show();
			vulpe.util.get(id + app.token.otherLanguages + value).hide();
			vulpe.view.showHideElement(id + app.token.otherLanguages, true);
			var showOtherLanguages = vulpe.util.get(id + app.token.showOtherLanguages);
			var src = showOtherLanguages.attr("src");
			var othersLanguages = vulpe.util.get(id + app.token.otherLanguages).css("display") != "none";
			showOtherLanguages.attr("src", src.indexOf("down.png") != -1 ? src.replace("down.png", "up.png") : src.replace("up.png", "down.png"));
		});
	},

	textChange : function(id, editor) {
		var text = vulpe.util.getElementField("text", id);
		if (editor) {
			text.rte({
				controls_rte : rte_toolbar,
				controls_html : html_toolbar
			});
			var rte = vulpe.util.getElementField("text", id).contents();
			rte.bind('keyup', function() {
				app.textUpdate(id, rte.find('body').html());
			});
		} else {
			text.bind('blur keyup', function() {
				app.textUpdate(id, text.val());
			});
			app.showOtherLanguagesClick(id);
		}
		app.languageIdFlag(id, editor);
	}

/*
 * <select id="${elementId}_languageId_flag" style="width: 45px;"
 * name="${name}.languageId"> <option value="af"
 * title="${pageContext.request.contextPath}/images/flags/af.png">Afrikaans</option>
 * <option value="sq"
 * title="${pageContext.request.contextPath}/images/flags/sq.png">Albanian</option>
 * <option value="ar"
 * title="${pageContext.request.contextPath}/images/flags/ar.png">Arabic</option>
 * <option value="hy"
 * title="${pageContext.request.contextPath}/images/flags/hy.png">Armenian</option>
 * <option value="az"
 * title="${pageContext.request.contextPath}/images/flags/az.png">Azerbaijani</option>
 * <option value="eu"
 * title="${pageContext.request.contextPath}/images/flags/eu.png">Basque</option>
 * <option value="be"
 * title="${pageContext.request.contextPath}/images/flags/be.png">Belarusian</option>
 * <option value="bg"
 * title="${pageContext.request.contextPath}/images/flags/bg.png">Bulgarian</option>
 * <option value="ca"
 * title="${pageContext.request.contextPath}/images/flags/ca.png">Catalan</option>
 * <option value="zh-CN"
 * title="${pageContext.request.contextPath}/images/flags/zh-CN.png">Chinese</option>
 * <option value="hr"
 * title="${pageContext.request.contextPath}/images/flags/hr.png">Croatian</option>
 * <option value="cs"
 * title="${pageContext.request.contextPath}/images/flags/cs.png">Czech</option>
 * <option value="da"
 * title="${pageContext.request.contextPath}/images/flags/da.png">Danish</option>
 * <option value="nl"
 * title="${pageContext.request.contextPath}/images/flags/nl.png">Dutch</option>
 * <option value="en"
 * title="${pageContext.request.contextPath}/images/flags/en.png">English</option>
 * <option value="et"
 * title="${pageContext.request.contextPath}/images/flags/rt.png">Estonian</option>
 * <option value="tl"
 * title="${pageContext.request.contextPath}/images/flags/tl.png">Filipino</option>
 * <option value="fi"
 * title="${pageContext.request.contextPath}/images/flags/fi.png">Finnish</option>
 * <option value="fr"
 * title="${pageContext.request.contextPath}/images/flags/fr.png">French</option>
 * <option value="gl"
 * title="${pageContext.request.contextPath}/images/flags/gl.png">Galician</option>
 * <option value="ka"
 * title="${pageContext.request.contextPath}/images/flags/ka.png">Georgian</option>
 * <option value="de"
 * title="${pageContext.request.contextPath}/images/flags/de.png">German</option>
 * <option value="el"
 * title="${pageContext.request.contextPath}/images/flags/el.png">Greek</option>
 * <option value="ht"
 * title="${pageContext.request.contextPath}/images/flags/ht.png">Haitian Creole</option>
 * <option value="iw"
 * title="${pageContext.request.contextPath}/images/flags/iw.png">Hebrew</option>
 * <option value="hi"
 * title="${pageContext.request.contextPath}/images/flags/hi.png">Hindi</option>
 * <option value="hu"
 * title="${pageContext.request.contextPath}/images/flags/hu.png">Hungarian</option>
 * <option value="is"
 * title="${pageContext.request.contextPath}/images/flags/is.png">Icelandic</option>
 * <option value="id"
 * title="${pageContext.request.contextPath}/images/flags/id.png">Indonesian</option>
 * <option value="ga"
 * title="${pageContext.request.contextPath}/images/flags/ga.png">Irish</option>
 * <option value="it"
 * title="${pageContext.request.contextPath}/images/flags/it.png">Italian</option>
 * <option value="ja"
 * title="${pageContext.request.contextPath}/images/flags/ja.png">Japanese</option>
 * <option value="ko"
 * title="${pageContext.request.contextPath}/images/flags/ko.png">Korean</option>
 * <option value="lv"
 * title="${pageContext.request.contextPath}/images/flags/lv.png">Latvian</option>
 * <option value="lt"
 * title="${pageContext.request.contextPath}/images/flags/lt.png">Lithuanian</option>
 * <option value="mk"
 * title="${pageContext.request.contextPath}/images/flags/mk.png">Macedonian</option>
 * <option value="ms"
 * title="${pageContext.request.contextPath}/images/flags/ms.png">Malay</option>
 * <option value="mt"
 * title="${pageContext.request.contextPath}/images/flags/mt.png">Maltese</option>
 * <option value="no"
 * title="${pageContext.request.contextPath}/images/flags/no.png">Norwegian</option>
 * <option value="fa"
 * title="${pageContext.request.contextPath}/images/flags/fa.png">Persian</option>
 * <option value="pl"
 * title="${pageContext.request.contextPath}/images/flags/pl.png">Polish</option>
 * <option value="pt"
 * title="${pageContext.request.contextPath}/images/flags/pt.png">Portuguese</option>
 * <option value="ro"
 * title="${pageContext.request.contextPath}/images/flags/ro.png">Romanian</option>
 * <option value="ru"
 * title="${pageContext.request.contextPath}/images/flags/ry.png">Russian</option>
 * <option value="sr"
 * title="${pageContext.request.contextPath}/images/flags/sr.png">Serbian</option>
 * <option value="sk"
 * title="${pageContext.request.contextPath}/images/flags/sk.png">Slovak</option>
 * <option value="sl"
 * title="${pageContext.request.contextPath}/images/flags/sl.png">Slovenian</option>
 * <option value="es"
 * title="${pageContext.request.contextPath}/images/flags/es.png">Spanish</option>
 * <option value="sw"
 * title="${pageContext.request.contextPath}/images/flags/sw.png">Swahili</option>
 * <option value="sv"
 * title="${pageContext.request.contextPath}/images/flags/sv.png">Swedish</option>
 * <option value="th"
 * title="${pageContext.request.contextPath}/images/flags/th.png">Thai</option>
 * <option value="tr"
 * title="${pageContext.request.contextPath}/images/flags/tr.png">Turkish</option>
 * <option value="uk"
 * title="${pageContext.request.contextPath}/images/flags/uk.png">Ukrainian</option>
 * <option value="ur"
 * title="${pageContext.request.contextPath}/images/flags/ur.png">Urdu</option>
 * <option value="vi"
 * title="${pageContext.request.contextPath}/images/flags/vi.png">Vietnamese</option>
 * <option value="cy"
 * title="${pageContext.request.contextPath}/images/flags/cy.png">Welsh</option>
 * <option value="yi"
 * title="${pageContext.request.contextPath}/images/flags/yi.png">Yiddish</option>
 * </select>
 */
}
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>
	<c:if test="${empty textTranslateIndex}">
		<c:set var="textTranslateIndex" value="0"/>
	</c:if>
	<script type="text/javascript">
		var change${property}${currentStatus.count} = function(value) {
			var id = "${elementId}";
			var text = vulpe.util.get(id + ".text");
			text.val("");
			text.focus();
			var fields = jQuery("input[id*='" + id + "']");
			for (var i = 0; i < fields.length; i++) {
				var field = jQuery(fields[i]);
				var fieldType = field.attr("type");
				var fieldId = field.attr("id");
				if (fieldType == "hidden" && fieldId.indexOf("language.id") != -1 && field.val() == value) {
					var textId = vulpe.util.getPrefixIdByElement(field) + "text";
					text.val(vulpe.util.get(textId).val());
					var otherLanguages = vulpe.util.get("${property}_otherLanguages");
					if (otherLanguages.css("display") != "none") {
						otherLanguages.hide();
						vulpe.util.get("${property}_showOtherLanguages").click();
						var showOtherLanguages = vulpe.util.get("${property}_showOtherLanguages");
						var src = showOtherLanguages.attr("src");
						showOtherLanguages.attr("src", src.replace("up.png", "down.png"));
					}
					break;
				}
			}
		}
		var textUpdate${property}${currentStatus.count} = function(textValue) {
			var id = "${elementId}";
			var value = vulpe.util.get("${property}_languageId_flag${currentStatus.count}").val();
			var fields = jQuery("input[id*='" + id + "']");
			for (var i = 0; i < fields.length; i++) {
				var field = jQuery(fields[i]);
				var fieldType = field.attr("type");
				var fieldId = field.attr("id");
				if (fieldType == "hidden" && fieldId.indexOf("language.id") != -1 && (field.val() == value || field.val() == "")) {
					field.val(value);
					var textId = vulpe.util.getPrefixIdByElement(field) + "text";
					vulpe.util.get(textId).val(textValue);
					break;
				}
			}
		}
		$(document).ready(function(){
			var id = "${elementId}";
			var languageIdFlag = vulpe.util.get("${property}_languageId_flag${currentStatus.count}");
			languageIdFlag.bind('change', function(){
				change${property}${currentStatus.count}(languageIdFlag.val());
			});
			languageIdFlag.msDropDown();
			var text = vulpe.util.get(id + ".text");
			text.bind('blur keyup', function(){
				textUpdate${property}${currentStatus.count}(text.val());
			});
			vulpe.util.get("${property}_showOtherLanguages").bind("click", function(){
				var value = languageIdFlag.val();
				$("span", "#${property}_otherLanguages").show();
				vulpe.util.get("${property}_otherLanguages" + value).hide();
				vulpe.view.showHideElement("${property}_otherLanguages", true);
				var showOtherLanguages = vulpe.util.get("${property}_showOtherLanguages");
				var src = showOtherLanguages.attr("src");
				var othersLanguages = vulpe.util.get("${property}_otherLanguages").css("display") != "none";
				showOtherLanguages.attr("src", src.indexOf("down.png") != -1 ? src.replace("down.png", "up.png") : src.replace("up.png", "down.png"));
			});
		});
	</script>
	<c:forEach var="language" items="${cachedClass['Language']}" varStatus="status">
		<v:hidden property="${property}.languages[${status.index}].language.id"/>
		<v:hidden property="${property}.languages[${status.index}].text"/>
	</c:forEach>
	<c:set var="languageIdValueEL" value="${'${'}${name}.languageId${'}'}"/>
	<c:set var="languageIdValue" value="${util:eval(pageContext, languageIdValueEL)}"/>
	<!--
	<select id="${property}_languageId_flag${currentStatus.count}" style="width: 45px;" name="${name}.languageId">
		<option value="af" title="${pageContext.request.contextPath}/images/flags/af.png">Afrikaans</option>
		<option value="sq" title="${pageContext.request.contextPath}/images/flags/sq.png">Albanian</option>
		<option value="ar" title="${pageContext.request.contextPath}/images/flags/ar.png">Arabic</option>
		<option value="hy" title="${pageContext.request.contextPath}/images/flags/hy.png">Armenian</option>
		<option value="az" title="${pageContext.request.contextPath}/images/flags/az.png">Azerbaijani</option>
		<option value="eu" title="${pageContext.request.contextPath}/images/flags/eu.png">Basque</option>
		<option value="be" title="${pageContext.request.contextPath}/images/flags/be.png">Belarusian</option>
		<option value="bg" title="${pageContext.request.contextPath}/images/flags/bg.png">Bulgarian</option>
		<option value="ca" title="${pageContext.request.contextPath}/images/flags/ca.png">Catalan</option>
		<option value="zh-CN" title="${pageContext.request.contextPath}/images/flags/zh-CN.png">Chinese</option>
		<option value="hr" title="${pageContext.request.contextPath}/images/flags/hr.png">Croatian</option>
		<option value="cs" title="${pageContext.request.contextPath}/images/flags/cs.png">Czech</option>
		<option value="da" title="${pageContext.request.contextPath}/images/flags/da.png">Danish</option>
		<option value="nl" title="${pageContext.request.contextPath}/images/flags/nl.png">Dutch</option>
		<option value="en" title="${pageContext.request.contextPath}/images/flags/en.png">English</option>
		<option value="et" title="${pageContext.request.contextPath}/images/flags/rt.png">Estonian</option>
		<option value="tl" title="${pageContext.request.contextPath}/images/flags/tl.png">Filipino</option>
		<option value="fi" title="${pageContext.request.contextPath}/images/flags/fi.png">Finnish</option>
		<option value="fr" title="${pageContext.request.contextPath}/images/flags/fr.png">French</option>
		<option value="gl" title="${pageContext.request.contextPath}/images/flags/gl.png">Galician</option>
		<option value="ka" title="${pageContext.request.contextPath}/images/flags/ka.png">Georgian</option>
		<option value="de" title="${pageContext.request.contextPath}/images/flags/de.png">German</option>
		<option value="el" title="${pageContext.request.contextPath}/images/flags/el.png">Greek</option>
		<option value="ht" title="${pageContext.request.contextPath}/images/flags/ht.png">Haitian Creole</option>
		<option value="iw" title="${pageContext.request.contextPath}/images/flags/iw.png">Hebrew</option>
		<option value="hi" title="${pageContext.request.contextPath}/images/flags/hi.png">Hindi</option>
		<option value="hu" title="${pageContext.request.contextPath}/images/flags/hu.png">Hungarian</option>
		<option value="is" title="${pageContext.request.contextPath}/images/flags/is.png">Icelandic</option>
		<option value="id" title="${pageContext.request.contextPath}/images/flags/id.png">Indonesian</option>
		<option value="ga" title="${pageContext.request.contextPath}/images/flags/ga.png">Irish</option>
		<option value="it" title="${pageContext.request.contextPath}/images/flags/it.png">Italian</option>
		<option value="ja" title="${pageContext.request.contextPath}/images/flags/ja.png">Japanese</option>
		<option value="ko" title="${pageContext.request.contextPath}/images/flags/ko.png">Korean</option>
		<option value="lv" title="${pageContext.request.contextPath}/images/flags/lv.png">Latvian</option>
		<option value="lt" title="${pageContext.request.contextPath}/images/flags/lt.png">Lithuanian</option>
		<option value="mk" title="${pageContext.request.contextPath}/images/flags/mk.png">Macedonian</option>
		<option value="ms" title="${pageContext.request.contextPath}/images/flags/ms.png">Malay</option>
		<option value="mt" title="${pageContext.request.contextPath}/images/flags/mt.png">Maltese</option>
		<option value="no" title="${pageContext.request.contextPath}/images/flags/no.png">Norwegian</option>
		<option value="fa" title="${pageContext.request.contextPath}/images/flags/fa.png">Persian</option>
		<option value="pl" title="${pageContext.request.contextPath}/images/flags/pl.png">Polish</option>
		<option value="pt" title="${pageContext.request.contextPath}/images/flags/pt.png">Portuguese</option>
		<option value="ro" title="${pageContext.request.contextPath}/images/flags/ro.png">Romanian</option>
		<option value="ru" title="${pageContext.request.contextPath}/images/flags/ry.png">Russian</option>
		<option value="sr" title="${pageContext.request.contextPath}/images/flags/sr.png">Serbian</option>
		<option value="sk" title="${pageContext.request.contextPath}/images/flags/sk.png">Slovak</option>
		<option value="sl" title="${pageContext.request.contextPath}/images/flags/sl.png">Slovenian</option>
		<option value="es" title="${pageContext.request.contextPath}/images/flags/es.png">Spanish</option>
		<option value="sw" title="${pageContext.request.contextPath}/images/flags/sw.png">Swahili</option>
		<option value="sv" title="${pageContext.request.contextPath}/images/flags/sv.png">Swedish</option>
		<option value="th" title="${pageContext.request.contextPath}/images/flags/th.png">Thai</option>
		<option value="tr" title="${pageContext.request.contextPath}/images/flags/tr.png">Turkish</option>
		<option value="uk" title="${pageContext.request.contextPath}/images/flags/uk.png">Ukrainian</option>
		<option value="ur" title="${pageContext.request.contextPath}/images/flags/ur.png">Urdu</option>
		<option value="vi" title="${pageContext.request.contextPath}/images/flags/vi.png">Vietnamese</option>
		<option value="cy" title="${pageContext.request.contextPath}/images/flags/cy.png">Welsh</option>
		<option value="yi" title="${pageContext.request.contextPath}/images/flags/yi.png">Yiddish</option>
	</select>
	 -->
	<select id="${property}_languageId_flag${currentStatus.count}" style="width: 45px;" name="${name}.languageId">
	<c:forEach var="language" items="${cachedClass['Language']}" varStatus="status">
		<c:set var="selected" value=""/>
		<c:if test="${language.id == languageIdValue}"><c:set var="selected"> selected="selected"</c:set></c:if>
		<option ${selected} value="${language.id}" title="${pageContext.request.contextPath}/images/flags/${language.localeCode}.png">&nbsp;</option>
	</c:forEach>
	</select>
	<c:choose>
		<c:when test="${empty rows && empty cols}">
			<v:text property="${property}.text" paragraph="false" size="${size}" required="${required}" />
		</c:when>
		<c:otherwise>
			<v:textarea property="${property}.text" paragraph="false" required="${required}" rows="${rows}" cols="${cols}" />
		</c:otherwise>
	</c:choose>
	<img src="${pageContext.request.contextPath}/images/icons/bullet_arrow_up.png" style="cursor: pointer" id="${property}_showOtherLanguages">
	<c:set var="textLanguagesEL" value="${'${'}${name}.languages${'}'}"/>
	<c:set var="textLanguages" value="${util:eval(pageContext, textLanguagesEL)}"/>
	<span id="${property}_otherLanguages" style="position: relative; left: 27px; display: none;">
		<c:forEach var="textLanguage" items="${textLanguages}">
			<span id="${property}_otherLanguages${textLanguage.language.id}"><br><img src="${pageContext.request.contextPath}/images/flags/${textLanguage.language.localeCode}.png">&nbsp;${textLanguage.text}</span>
		</c:forEach>
	</span>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp"%>
</c:if>
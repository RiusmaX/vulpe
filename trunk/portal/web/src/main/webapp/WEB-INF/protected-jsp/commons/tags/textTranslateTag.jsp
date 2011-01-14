<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:if test="${render}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagBegin.jsp"%>
	<c:if test="${empty textTranslateIndex}">
		<c:set var="textTranslateIndex" value="0" />
	</c:if>
	<script type="text/javascript">
		$(document).ready(function(){
			app.textChange("${elementId}", ${not empty editor && editor});
		});
	</script>
	<c:forEach var="language" items="${cachedClasses['Language']}" varStatus="status">
		<v:hidden property="${property}.languages[${status.index}].language.id" />
		<v:hidden property="${property}.languages[${status.index}].text" />
	</c:forEach>
	<c:set var="languageIdValueEL" value="${'${'}${name}.languageId${'}'}" />
	<c:set var="languageIdValue" value="${util:eval(pageContext, languageIdValueEL)}" />
	<select id="${elementId}-languageIdFlag" style="width: 45px;" name="${name}.languageId">
		<c:forEach var="language" items="${cachedClasses['Language']}" varStatus="status">
			<c:set var="selected" value="" />
			<c:if test="${language.id == languageIdValue}">
				<c:set var="selected"> selected="selected"</c:set>
			</c:if>
			<option ${selected} value="${language.id}"
				title="${pageContext.request.contextPath}/images/flags/${language.localeCode}.png">&nbsp;</option>
		</c:forEach>
	</select>
	<c:choose>
		<c:when test="${empty rows && empty cols}">
			<v:text property="${property}.text" paragraph="false" size="${size}" required="${required}" />
		</c:when>
		<c:otherwise>
			<v:textarea property="${property}.text" paragraph="false" required="${required}" rows="${rows}"
				cols="${cols}" />
		</c:otherwise>
	</c:choose>
	<c:set var="textLanguagesEL" value="${'${'}${name}.languages${'}'}" />
	<c:set var="textLanguages" value="${util:eval(pageContext, textLanguagesEL)}" />
	<c:if test="${empty editor && not empty textLanguages && fn:length(textLanguages) > 1}">
		<img src="${pageContext.request.contextPath}/images/icons/bullet_arrow_down.png"
			style="cursor: pointer" id="${elementId}-showOtherLanguages" alt="show languages text"/>
		<span id="${elementId}-otherLanguages" style="position: relative; left: 27px; display: none;">
		<c:forEach var="textLanguage" items="${textLanguages}">
			<span id="${elementId}-otherLanguages${textLanguage.language.id}"><br/>
			<img
				src="${pageContext.request.contextPath}/images/flags/${textLanguage.language.localeCode}.png" alt="${textLanguage.text}"/>&nbsp;${textLanguage.text}</span>
		</c:forEach> </span>
	</c:if>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagEnd.jsp"%>
</c:if>
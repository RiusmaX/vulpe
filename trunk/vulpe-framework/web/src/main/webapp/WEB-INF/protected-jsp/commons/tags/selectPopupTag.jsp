<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>
<c:if test="${empty readonly}"><c:set var="readonly" value="${true}"/></c:if>
<c:if test="${show eq true}">
	<v:hidden property="${property}.${identifier}"/>
	<c:if test="${autoComplete && empty autoCompleteMinLength}">
		<c:set var="autoCompleteMinLength" value="3"/>
	</c:if>
	<c:set var="autoCompleteAction" value="${fn:replace(action, '/select', '/autocomplete/ajax')}" />
	<v:text labelKey="${labelKey}" property="${property}.${description}" readonly="${autoComplete ? false : true}" elementId="${elementId}" size="${size}" showAsText="${showAsText}" autoComplete="${description}" autoCompleteURL="${autoCompleteAction}" autoCompleteSelect="true" autoCompleteMinLength="${autoCompleteMinLength}" required="${required}" targetValue="${targetValue}" targetName="${targetName}">
		<c:if test="${!showAsText}"><v:popup action="${action}" labelKey="label.vulpe.selected" popupId="${popupId}" popupProperties="${popupProperties}" popupWidth="${popupWidth}"/></c:if>
	</v:text>
</c:if>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>
<c:if test="${empty readonly}"><c:set var="readonly" value="${true}"/></c:if>
<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/configAttributesTag.jsp" %>
	<div id="${elementId}_selectPopup">
	<c:remove var="elementId"/>
	<v:hidden property="${property}.${identifier}"/>
	<c:if test="${autocomplete && empty autocompleteMinLength}">
		<c:set var="autocompleteMinLength" value="3"/>
	</c:if>
	<c:set var="autocompleteAction" value="${fn:replace(action, '/select', '/autocomplete/ajax')}" />
	<v:text labelKey="${labelKey}" property="${property}.${description}" readonly="${autocomplete ? false : true}" elementId="${elementId}" size="${size}" showAsText="${showAsText}" autocomplete="${description}" autocompleteURL="${autocompleteAction}" autocompleteSelect="true" autocompleteMinLength="${autocompleteMinLength}" required="${required}" targetValue="${targetValue}" targetName="${targetName}" autocompleteList="${autocompleteList}">
		<c:if test="${!showAsText}"><v:popup action="${action}" labelKey="label.vulpe.selected" popupId="${popupId}" popupProperties="${popupProperties}" popupWidth="${popupWidth}"/></c:if>
	</v:text>
	</div>
</c:if>
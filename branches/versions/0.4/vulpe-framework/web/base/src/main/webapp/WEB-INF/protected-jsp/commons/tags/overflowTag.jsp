<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:choose>
	<c:when test="${not empty limitContent && limitContent > 0 && fn:length(value) > limitContent}">
		<c:set var="fullValue" value="${value}"/>
		<c:if test="${fn:length(value) > limitContent}">
			<c:set var="value" value="${fn:substring(value, 0, limitContent)}..."/>
		</c:if>
		<span id="${elementId}_value">${util:toString(value)}&nbsp;</span><span id="${elementId}_showContent" class="vulpeShowContent"><a href="javascript:void(0);" onclick="vulpe.view.showContent('${elementId}');"><fmt:message key="vulpe.messages.showContent"/></a></span>
		<div id="${elementId}_content" class="vulpeContentOverflow" style="display: none">${fullValue}<div id="${elementId}-closeContent" class="vulpeCloseContentOverflow"><a href="javascript:void(0);" onclick="vulpe.view.hideContent('${elementId}');"><fmt:message key="vulpe.messages.close"/></a></div></div>
	</c:when>
	<c:otherwise>${util:toString(value)}</c:otherwise>
</c:choose>
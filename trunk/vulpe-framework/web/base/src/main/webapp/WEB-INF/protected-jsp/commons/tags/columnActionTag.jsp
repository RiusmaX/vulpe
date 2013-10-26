<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${render}">
	<c:if test="${isHeader}"><th scope="col" width="${width}"><fmt:message key="${labelKey}"/></th></c:if>
	<c:if test="${not empty selectCheckOn}"><c:set var="styleClass" value="${styleClass} unclickable"/></c:if>
	<c:if test="${not empty width}"><c:set var="width">width="${width}"</c:set></c:if>
	<c:if test="${!isHeader}">
		<td ${width} class="${styleClass}">
			<v:action iconClass="${iconClass}" javascript="${javascript}" layerFields="${layerFields}" beforeJs="${beforeJs}" afterJs="${afterJs}" style="${style}" elementId="${elementId}" validate="${validate}" labelKey="${labelKey}" layer="${layer}" queryString="${queryString}" action="${action}" iconBorder="${iconBorder}" iconHeight="${iconHeight}" icon="${icon}" iconWidth="${iconWidth}" noSubmitForm="${noSubmitForm}" showTextOfButton="${!global['application-view-showIconOfButton']}"/>
		</td>
	</c:if>
</c:if>
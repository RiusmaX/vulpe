<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${show}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagBegin.jsp" %>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}"><c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/></c:if>
	<c:if test="${!onlyToSee && !showAsText}"><s:file theme="simple" name="${name}" id="${elementId}" accesskey="${accesskey}" accept="${accept}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" tabindex="${tabindex}" title="${title}"/></c:if>
	<c:if test="${saveInSession}"><c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/></c:if>
	<c:if test="${not empty value}">
		<c:set var="fileNameEL" value="${'${'}${name}FileName${'}'}"/>
		<c:set var="fileName" value="${util:eval(pageContext, fileNameEL)}"/>
		<c:set var="contentTypeEL" value="${'${'}${name}ContentType${'}'}"/>
		<c:set var="contentType" value="${util:eval(pageContext, contentTypeEL)}"/>
		<c:set var="contentDisposition" value="attachment;filename=${fileName}"/>
		<a href="${util:linkProperty(pageContext, name, contentType, contentDisposition)}"><fmt:message key="vulpe.label.download"/> (${fileName})</a>
		<v:hidden property="${property}ContentType"/>
		<v:hidden property="${property}FileName"/>
	</c:if>
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagEnd.jsp" %>
</c:if>
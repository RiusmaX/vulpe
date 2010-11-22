<c:if test="${show}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagBegin.jsp" %>
	<c:if test="${empty size && not empty maxlength}"><c:set var="size" value="${maxlength}"/></c:if>
	<c:if test="${empty size}"><c:set var="size" value="10"/></c:if>
	<c:if test="${empty maxlength}"><c:set var="maxlength" value="10"/></c:if>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}"><c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/></c:if>
	<c:if test="${saveInSession}"><c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/></c:if>
	<c:if test="${onlyToSee}"><c:set var="showAsText" value="${true}"/></c:if>
	<c:choose>
		<c:when test="${showAsText}"><span>${value}</span><v:hidden property="${property}"/></c:when>
		<c:otherwise><s:textfield theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" maxlength="${maxlength}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" onselect="${onselect}" readonly="${readonly}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}"/></c:otherwise>
	</c:choose>
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagEnd.jsp" %>
	<c:if test="${!showAsText}">
	<script type="text/javascript">
		jQuery(function($){
			vulpe.util.get('${elementId}').mask("99/99/9999");
			vulpe.util.get('${elementId}').datepicker({
				showOn: 'button',
				buttonImage: '${pageContext.request.contextPath}/themes/${global['theme']}/images/icons/button-calendar-16x16.png',
				buttonImageOnly: true
			});
			vulpe.util.get('ui-datepicker-div').css('z-index', 3000);
		});
	</script>
	</c:if>
</c:if>
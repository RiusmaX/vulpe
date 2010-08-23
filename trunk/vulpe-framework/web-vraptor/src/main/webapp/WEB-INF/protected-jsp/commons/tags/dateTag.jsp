<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>
	<c:if test="${empty size && not empty maxlength}">
		<c:set var="size" value="${maxlength}"/>
	</c:if>
	<c:if test="${empty size}">
		<c:set var="size" value="10"/>
	</c:if>
	<c:if test="${empty maxlength}">
		<c:set var="maxlength" value="10"/>
	</c:if>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>
	<c:if test="${saveInSession}">
		<c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/>
	</c:if>
	<c:if test="${onlyToSee}">
		<c:set var="showAsText" value="${true}"/>
	</c:if>
	<c:choose>
		<c:when test="${showAsText}">
			<span onclick="${onclick}" ondblclick="${ondblclick}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" style="${style}" class="vulpeSimpleLabel ${styleClass}" id="${elementId}" title="${title}" >${value}</span>
			<v:hidden property="${property}"/>
		</c:when>
		<c:otherwise>
			<input type="text" name="${name}" <c:if test="${not empty accesskey}"> accesskey="${accesskey}"</c:if><c:if test="${disabled}">disabled="${disabled}"</c:if><c:if test="${not empty maxlength && maxlength > size}">maxlength="${maxlength}"</c:if><c:if test="${not empty onblur}"> onblur="${onblur}"</c:if><c:if test="${not empty onchange}"> onchange="${onchange}"</c:if><c:if test="${not empty onclick}"> onclick="${onclick}"</c:if><c:if test="${not empty ondblclick}"> ondblclick="${ondblclick}"</c:if><c:if test="${not empty onfocus}"> onfocus="${onfocus}"</c:if><c:if test="${not empty onkeydown}"> onkeydown="${onkeydown}"</c:if><c:if test="${not empty onkeypress}"> onkeypress="${onkeypress}"</c:if><c:if test="${not empty onkeyup}"> onkeyup="${onkeyup}"</c:if><c:if test="${not empty onmousedown}"> onmousedown="${onmousedown}"</c:if><c:if test="${not empty onmousemove}"> onmousemove="${onmousemove}"</c:if><c:if test="${not empty onmouseout}"> onmouseout="${onmouseout}"</c:if><c:if test="${not empty onmouseover}"> onmouseover="${onmouseover}"</c:if><c:if test="${not empty onmouseup}"> onmouseup="${onmouseup}"</c:if><c:if test="${not empty onselect}"> onselect="${onselect}"</c:if><c:if test="${readonly}"> readonly="${readonly}"</c:if> size="${size}"<c:if test="${not empty style}"> style="${style}"</c:if><c:if test="${not empty styleClass}"> class="${styleClass}"</c:if> id="${elementId}" tabindex="${tabindex}" <c:if test="${not empty title}">title="${title}"</c:if> value="${value}" />
		</c:otherwise>
	</c:choose>
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
	<c:if test="${!showAsText}">
	<script>
		jQuery(function($){
			vulpe.util.get('${elementId}').mask("99/99/9999");
			vulpe.util.get('${elementId}').datepicker();
			vulpe.util.get('ui-datepicker-div').css('z-index', 3000);
		});
	</script>
	</c:if>
</c:if>
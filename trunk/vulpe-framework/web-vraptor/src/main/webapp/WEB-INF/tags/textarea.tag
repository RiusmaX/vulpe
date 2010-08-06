<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributes.jsp" %>
<%@ attribute name="cols" required="false" rtexprvalue="true" %>
<%@ attribute name="property" required="true" rtexprvalue="true" %>
<%@ attribute name="readonly" required="false" rtexprvalue="true" %>
<%@ attribute name="rows" required="false" rtexprvalue="true" %>

<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>

<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>

	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>

	<c:choose>
		<c:when test="${showAsText}">
			<span onclick="${onclick}" ondblclick="${ondblclick}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" style="${style}" class="vulpeSimpleLabel ${styleClass}" id="${elementId}" title="${title}" >${value}</span>
		</c:when>
		<c:otherwise>
			<textarea name="${name}" <c:if test="${not empty accesskey}">accesskey="${accesskey}"</c:if> cols="${cols}" <c:if test="${disabled}">disabled="${disabled}"</c:if><c:if test="${not empty onblur}"> onblur="${onblur}"</c:if><c:if test="${not empty onchange}"> onchange="${onchange}"</c:if> onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" id="${elementId}" readonly="${readonly}" rows="${rows}" style="${style}" class="${styleClass}" tabindex="${tabindex}" title="${title}" value="${value}"/>
		</c:otherwise>
	</c:choose>
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
	<script type="text/javascript">
		jQuery(function($){
			vulpe.util.get('${elementId}').focus(function() {
				$(this).effect("highlight");
			});
		});
	</script>
</c:if>
<c:if test="${show}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagBegin.jsp" %>
	<c:if test="${empty accept}"><c:set var="accept" value="*.jpg;*.gif;*.png"/></c:if>
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>
	<c:if test="${empty imageThumbWidth}"><c:set var="imageThumbWidth" value="70"/></c:if>
	<c:if test="${empty imageThumbHeight}"><c:set var="imageThumbHeight" value="52"/></c:if>
	<c:if test="${empty imageWidth}"><c:set var="imageWidth" value="640"/></c:if>
	<c:if test="${empty imageHeight}"><c:set var="imageHeight" value="480"/></c:if>
	<c:if test="${!onlyToSee}">
		<s:file theme="simple" name="${name}" id="${elementId}" accesskey="${accesskey}" accept="${accept}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" tabindex="${tabindex}" title="${title}"/>
	</c:if>
	<jsp:doBody/>
	<c:if test="${not empty util:getProperty(pageContext, name)}">
		<c:set var="thumbName" value="${name}-thumb"/>
		<a id="${elementId}-image" href="${util:linkImage(pageContext, name, 'image/jpeg', '', imageWidth, imageThumbWidth)}" class="lightbox"><img border="0" src="${util:linkKey(thumbName, 'image/jpeg', '')}" width="${imageThumbWidth}" height="${imageThumbHeight}" class="thumb"/></a>
		<script type="text/javascript">
	    $(function() {
		    <c:choose>
			<c:when test="${showGallery}">
	        $('.lightbox').lightBox();
		    </c:when>
		    <c:otherwise>
		    vulpe.util.get('${elementId}-image').lightBox();
		    </c:otherwise>
		    </c:choose>
	    });
	    </script>
	</c:if>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/tagEnd.jsp" %>
</c:if>
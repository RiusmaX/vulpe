<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>
<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>
	<c:if test="${empty styleClass}"><c:set var="styleClass" value="rte-zone"/></c:if>
	<c:choose>
		<c:when test="${showAsText}">
			<br/><c:out value="${value}" escapeXml="false"/>
		</c:when>
		<c:otherwise>
			<textarea name="${name}" id="${elementId}" class="${styleClass}" style="${style}" cols="${cols}" rows="${rows}">${value}</textarea>
			<script type="text/javascript">
				jQuery(document).ready(function() {
					vulpe.util.get('${elementId}').rte({
						//css: ['default.css'],
						controls_rte: rte_toolbar,
						controls_html: html_toolbar
					});
				});
			</script>
		</c:otherwise>
	</c:choose>
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
</c:if>
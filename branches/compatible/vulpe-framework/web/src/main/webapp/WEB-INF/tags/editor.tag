<%@ attribute name="property" required="true" rtexprvalue="true" %>
<%@ attribute name="style" required="false" rtexprvalue="true" %>
<%@ attribute name="styleClass" required="false" rtexprvalue="true" %>
<%@ attribute name="value" required="false" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="labelKey" required="false" rtexprvalue="true" %>
<%@ attribute name="labelStyle" required="false" rtexprvalue="true" %>
<%@ attribute name="labelClass" required="false" rtexprvalue="true" %>
<%@ attribute name="breakLabel" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="paragraph" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="name" required="false" rtexprvalue="true" %>
<%@ attribute name="elementId" required="false" rtexprvalue="true" %>
<%@ attribute name="targetName" required="false" rtexprvalue="true" %>
<%@ attribute name="targetValue" required="false" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="role" required="false" rtexprvalue="true" %>
<%@ attribute name="logged" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="saveInSession" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="expireInSession" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="cssFrame" required="false" rtexprvalue="true" %>
<%@ attribute name="showAsText" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>

<c:if test="${show eq true}">

	<%@include file="/WEB-INF/protected-jsp/commons/tags/configAttributesTag.jsp" %>

	<c:if test="${empty styleClass}">
		<c:set var="styleClass" value="rte-zone"/>
	</c:if>
	
	<c:choose>
		<c:when test="${showAsText}">
			<br/><c:out value="${value}" escapeXml="false"/>
		</c:when>
		<c:otherwise>
			<textarea name="${name}" id="${elementId}" class="${styleClass}" style="${style}">${value}</textarea>
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
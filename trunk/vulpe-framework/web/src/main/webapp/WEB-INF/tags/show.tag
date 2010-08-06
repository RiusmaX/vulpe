<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ attribute name="type" required="false" rtexprvalue="true"%>
<%@ attribute name="targetValue" required="false" rtexprvalue="true" type="java.lang.Object"%>
<%@ attribute name="property" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="pattern" required="false" rtexprvalue="true" type="java.lang.String"%>
<c:set var="propertyTarget" value="${empty targetValue ? currentItem : targetValue}"/>
<c:set var="valueEL" value="${'${'}propertyTarget.${property}${'}'}"/>
<c:set var="value" value="${util:eval(pageContext, valueEL)}"/>
<c:choose>
	<c:when test="${not empty type}">
		<c:choose>
			<c:when test="${type == 'enum' || type == 'ENUM' || type == 'Enum'}">
				<c:if test="${not empty value}">
				${util:enumInField(propertyTarget, property, value)}
				</c:if>
			</c:when>
			<c:when test="${type == 'date' || type == 'DATE' || type == 'Date'}">
				<c:if test="${not empty value}">
				<fmt:formatDate value="${value}" pattern="${pattern}"/>
				</c:if>
			</c:when>
		</c:choose>
	</c:when>
	<c:otherwise>${value}</c:otherwise>
</c:choose>
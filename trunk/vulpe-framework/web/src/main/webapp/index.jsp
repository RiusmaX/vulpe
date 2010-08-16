<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ajax" value="${empty SPRING_SECURITY_SAVED_REQUEST_KEY ? '/ajax': ''}"/>
<c:choose>
	<c:when test="${not empty vulpeCurrentLayout && vulpeCurrentLayout == 'BACKEND'}">
		<c:redirect url="/backend/Index${ajax}" />
	</c:when>
	<c:otherwise>
		<c:redirect url="/frontend/Index${ajax}" />
	</c:otherwise>
</c:choose>
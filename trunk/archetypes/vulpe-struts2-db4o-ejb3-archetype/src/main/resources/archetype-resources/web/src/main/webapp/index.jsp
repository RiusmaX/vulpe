#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${not empty vulpeCurrentLayout && vulpeCurrentLayout == 'BACKEND'}">
		<c:redirect url="/backend/Index.action" />
	</c:when>
	<c:otherwise>
		<c:redirect url="/frontend/Index.action" />
	</c:otherwise>
</c:choose>
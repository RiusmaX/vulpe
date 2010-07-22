#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${symbol_dollar}{not empty vulpeCurrentLayout && vulpeCurrentLayout == 'BACKEND'}">
		<c:redirect url="/backend/Index" />
	</c:when>
	<c:otherwise>
		<c:redirect url="/frontend/Index" />
	</c:otherwise>
</c:choose>
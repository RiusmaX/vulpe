<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${not empty vulpeCurrentLayout && vulpeCurrentLayout == 'BACKEND'}">
		<c:redirect url="/backend/Index" />
	</c:when>
	<c:otherwise>
		<c:redirect url="/frontend/Index" />
	</c:otherwise>
</c:choose>
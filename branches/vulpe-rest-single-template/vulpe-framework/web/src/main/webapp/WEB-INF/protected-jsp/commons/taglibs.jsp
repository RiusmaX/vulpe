<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<%@ taglib uri="/WEB-INF/util" prefix="util"%>
<%@include file="/WEB-INF/protected-jsp/commons/taglibsExtended.jsp"%>
<fmt:setBundle basename="${vulpeI18nManager}" />
<c:choose>
	<c:when test="${empty TABINDEX}">
		<c:set var="TABINDEX" scope="request" value="0" />
	</c:when>
	<c:otherwise>
		<c:set var="TABINDEX" scope="request" value="${TABINDEX+1}" />
	</c:otherwise>
</c:choose>
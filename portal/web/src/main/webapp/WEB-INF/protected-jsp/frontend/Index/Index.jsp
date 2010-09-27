<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:forEach var="content" items="${contents}">
<div id="${content.id}">
	${content.title}<br>
	${content.miniText}<br>
	<c:out value="${content.fullText}" escapeXml="${content.escapeXml}" />
</div>
</c:forEach>
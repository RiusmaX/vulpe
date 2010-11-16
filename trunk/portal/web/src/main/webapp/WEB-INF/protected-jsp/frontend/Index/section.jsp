<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<div id="contentPortal">
	<c:forEach var="content" items="${now['contents']}">
	<div id="content${content.id}">
		<c:choose>
		<c:when test="${not empty content.miniText.text}">
		<a href="javascript:void(0);" onclick="vulpe.view.request.submitLink('/frontend/Index/content/ajax/${content.id}');"><strong>${content.title}</strong></a> - <v:show property="date" targetValue="${content}" type="DATE" pattern="dd/MM/yyyy hh:mm"/><br>
		${content.miniText}
		</c:when>
		<c:otherwise>
		<strong>${content.title}</strong> - <v:show property="date" targetValue="${content}" type="DATE" pattern="dd/MM/yyyy hh:mm"/><br>
		<c:out value="${content.fullText}" escapeXml="${content.escapeXml}" />
		</c:otherwise>
		</c:choose>
	</div>
	<br>
	</c:forEach>
</div>
<jsp:include page="../bottom.jsp"/>
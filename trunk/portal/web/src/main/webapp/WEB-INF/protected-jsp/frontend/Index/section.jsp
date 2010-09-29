<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<div id="contentPortal">
	<c:forEach var="content" items="${now['contents']}">
	<div id="content${content.id}">
		<a href="javascript:void(0);" onclick="vulpe.view.request.submitLink('/frontend/Index/content/ajax?contentId=${content.id}');"><strong>${content.title}</strong></a> - <v:show property="date" targetValue="${content}" type="DATE" pattern="dd/MM/yyyy hh:mm"/><br>
		${content.miniText}<br>
		<c:out value="${content.fullText}" escapeXml="${content.escapeXml}" />
	</div>
	<br>
	</c:forEach>
</div>
<jsp:include page="../bottom.jsp"/>
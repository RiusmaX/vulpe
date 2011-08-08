<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<div id="portalSearch">
<c:forEach var="content" items="${now['contents']}">
	<div id="content_${content.id}">
		<c:choose>
		<c:when test="${not empty content.miniText.text}"><a href="javascript:void(0);" onclick="vulpe.view.request.submitLink('/frontend/Index/content/ajax/${content.id}');"><strong>${content.title}</strong></a> - <v:show property="date" targetValue="${content}" type="DATE" pattern="dd/MM/yyyy HH:mm"/><br/></c:when>
		<c:otherwise><strong>${content.title}</strong> - <v:show property="date" targetValue="${content}" type="DATE" pattern="dd/MM/yyyy HH:mm"/><br/></c:otherwise>
		</c:choose>
	</div>
	<br/>
	</c:forEach>	
</div>
<jsp:include page="../bottom.jsp"/>
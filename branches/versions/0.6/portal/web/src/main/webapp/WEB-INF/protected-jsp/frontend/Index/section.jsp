<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<div id="portalContent">
	<c:forEach var="content" items="${now['contents']}" varStatus="status">
	<c:if test="${status.count > 1}"><hr/></c:if>
	<div id="content_${content.id}">
		<p>
		<c:choose>
		<c:when test="${not empty content.miniText.text}"><a href="javascript:void(0);" onclick="vulpe.view.request.submitLink('/frontend/Index/content/ajax/${content.id}');"><strong>${content.title}</strong></a> - <v:show property="date" targetValue="${content}" type="DATE" pattern="dd/MM/yyyy HH:mm"/><br/></c:when>
		<c:otherwise><strong>${content.title}</strong> - <v:show property="date" targetValue="${content}" type="DATE" pattern="dd/MM/yyyy HH:mm"/><br/></c:otherwise>
		</c:choose>
		<c:if test="${not empty content.subtitle.text}">${content.subtitle}<br/></c:if>
		<c:if test="${not empty content.videoURL}">
		<div id="content_${content.id}_video" class="portalContentVideo">
		<object width="${content.videoWidth}" height="${content.videoHeight}">
			<param name="movie"	value="${content.videoURL}"></param>
			<param name="allowFullScreen" value="true"></param>
			<param name="allowscriptaccess" value="always"></param>
			<embed src="${content.videoURL}" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="${content.videoWidth}" height="${content.videoHeight}"></embed>
		</object>
		</div>
		</c:if>
		<c:choose>
		<c:when test="${not empty content.miniText.text}">${content.miniText}</c:when>
		<c:otherwise><c:out value="${content.fullText}" escapeXml="${content.escapeXml}" /></c:otherwise>
		</c:choose>
		</p>
	</div>
	</c:forEach>
</div><br>
<jsp:include page="../bottom.jsp"/>
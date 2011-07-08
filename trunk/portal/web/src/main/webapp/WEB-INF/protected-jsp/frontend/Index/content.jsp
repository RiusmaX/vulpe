<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<div id="portalContent">
	<c:set var="content" value="${now['content']}"/>
	<div id="content_${content.id}">
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
		<c:out value="${content.fullText}" escapeXml="${content.escapeXml}" />
		<br/><br/>
		<a href="javascript:void(0);" onclick="vulpe.view.request.submitLink('/frontend/Index/section/ajax/${content.section.id}');"><strong><fmt:message key="label.portal.back"/></strong></a><br/>
	</div>
	<br/>
</div>
<jsp:include page="../bottom.jsp"/>
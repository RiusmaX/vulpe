<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<div id="contentPortal">
	<c:set var="content" value="${now['content']}"/>
	<div id="content_${content.id}">
		<c:out value="${content.fullText}" escapeXml="${content.escapeXml}" />
		<br><br>
		<a href="javascript:void(0);" onclick="vulpe.view.request.submitLink('/frontend/Index/section/ajax?sectionId=${content.section.id}');"><strong><fmt:message key="label.portal.back"/></strong></a><br>
	</div>
	<br>
</div>
<jsp:include page="../bottom.jsp"/>
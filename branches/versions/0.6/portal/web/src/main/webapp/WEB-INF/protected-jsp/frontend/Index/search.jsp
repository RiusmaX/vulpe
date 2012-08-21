<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<div id="portalSearch">
	<c:if test="${not empty now['contents']}">
	<fieldset>
	<legend><fmt:message key='label.portal.Contents' /></legend>
	<c:forEach var="content" items="${now['contents']}">
	<a href="javascript:void(0);" onclick="vulpe.view.request.submitLink('/frontend/Index/content/ajax/${content.id}');">${content.title}</a><br/>
	</c:forEach>
	</fieldset>
	<br/>
	</c:if>
	<c:if test="${not empty now['downloads']}">
	<fieldset>
	<legend><fmt:message key='label.portal.Downloads' /></legend>
	<c:forEach var="download" items="${now['downloads']}">
	<a href="${pageContext.request.contextPath}/frontend/Index/download?id=${download.id}">${download.name}</a><br/>
	</c:forEach>
	</fieldset>
	<br/>
	</c:if>
	<c:if test="${not empty now['links']}">
	<fieldset>
	<legend><fmt:message key='label.portal.Links' /></legend>
	<c:forEach var="link" items="${now['links']}">
	<c:if test="${link.target.value == '_blank'}"><c:set var="onclick">onclick="window.open(this.href); return false;"</c:set></c:if>
	<a href="${pageContext.request.contextPath}/frontend/Index/link?id=${link.id}" ${onclick}>${link.name}</a>
	</c:forEach>
	</fieldset>
	<br/>
	</c:if>
</div>
<jsp:include page="../bottom.jsp"/>
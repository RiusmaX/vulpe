<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<div id="content">
	<c:if test="${now['showContentTitle'] && empty now['bodyTwice']}">
		<c:if test="${not empty now['titleKey']}"><fmt:message var="contentTitle">${now['titleKey']}${now['onlyToSee'] ? '.view' : ''}</fmt:message></c:if>
		<div id="title">
			${not empty now['contentTitle'] ? now['contentTitle'] : contentTitle}
			<c:if test="${now['showContentSubtitle'] && empty now['bodyTwice']}">
				<c:if test="${not empty now['subtitleKey']}"><fmt:message var="contentSubtitle">${now['subtitleKey']}</fmt:message></c:if>
				<div id="subtitle">${not empty now['contentSubtitle'] ? now['contentSubtitle'] : contentSubtitle}</div>
			</c:if>		
		</div>
	</c:if>
	<%@include file="/WEB-INF/protected-jsp/commons/contentPrepend.jsp" %>
	<c:choose>
		<c:when test="${now['bodyTwice']}">
			<c:choose>
				<c:when test="${now['bodyTwiceType'] == 'MAIN'}"><%@include file="/WEB-INF/protected-jsp/commons/main.jsp" %></c:when>
				<c:when test="${now['bodyTwiceType'] == 'SELECT'}"><%@include file="/WEB-INF/protected-jsp/commons/select.jsp" %></c:when>
			</c:choose>
		</c:when>
		<c:when test="${now['controllerType'] == 'MAIN'}"><%@include file="/WEB-INF/protected-jsp/commons/main.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'SELECT'}"><%@include file="/WEB-INF/protected-jsp/commons/select.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'TABULAR'}"><%@include file="/WEB-INF/protected-jsp/commons/tabular.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'REPORT'}"><%@include file="/WEB-INF/protected-jsp/commons/report.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'FRONTEND'}"><%@include file="/WEB-INF/protected-jsp/commons/frontend.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'BACKEND'}"><%@include file="/WEB-INF/protected-jsp/commons/backend.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'NONE'}"><%@include file="/WEB-INF/protected-jsp/commons/none.jsp" %></c:when>
	</c:choose>
	<%@include file="/WEB-INF/protected-jsp/commons/contentAppend.jsp" %>
</div>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<div id="content">
	<%@include file="/WEB-INF/protected-jsp/commons/contentPrepend.jsp" %>
	<c:choose>
		<c:when test="${vulpeBodyTwice}">
			<c:choose>
				<c:when test="${vulpeBodyTwiceType == 'CRUD'}"><%@include file="/WEB-INF/protected-jsp/commons/crud.jsp" %></c:when>
				<c:when test="${vulpeBodyTwiceType == 'SELECT'}"><%@include file="/WEB-INF/protected-jsp/commons/select.jsp" %></c:when>
			</c:choose>
		</c:when>
		<c:when test="${now['controllerType'] == 'CRUD'}"><%@include file="/WEB-INF/protected-jsp/commons/crud.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'SELECT'}"><%@include file="/WEB-INF/protected-jsp/commons/select.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'TABULAR'}"><%@include file="/WEB-INF/protected-jsp/commons/tabular.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'REPORT'}"><%@include file="/WEB-INF/protected-jsp/commons/report.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'FRONTEND'}"><%@include file="/WEB-INF/protected-jsp/commons/frontend.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'BACKEND'}"><%@include file="/WEB-INF/protected-jsp/commons/backend.jsp" %></c:when>
	</c:choose>
	<%@include file="/WEB-INF/protected-jsp/commons/contentAppend.jsp" %>
</div>
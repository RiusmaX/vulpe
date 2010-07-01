<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="vulpeFormName" value="${controllerConfig.formName}" scope="request"/>
<c:if test="${vulpeBodyTwice}">
	<c:choose>
	<c:when test="${vulpeBodyTwiceType == 'CRUD'}">
		<c:set var="vulpeFormName" value="${controllerConfig.CRUDFormName}" scope="request"/>
	</c:when>
	<c:when test="${vulpeBodyTwiceType == 'SELECT'}">
		<c:set var="vulpeFormName" value="${controllerConfig.selectFormName}" scope="request"/>
	</c:when>
	</c:choose>
</c:if>
<v:form id="${vulpeFormName}" name="${vulpeFormName}" theme="simple" validate="true" enctype="multipart/form-data" method="post">
	<c:if test="${!onlyToSee}">
	<input type="hidden" name="operation" value="${operation}" id="${vulpeFormName}_operation"/>
	<input type="hidden" name="paging.page" value="${paging.page}" id="${vulpeFormName}_paging.page"/>
	<input type="hidden" name="id" value="${id}" id="${vulpeFormName}_id"/>
	<input type="hidden" name="selectedTab" value="${selectedTab}" id="${vulpeFormName}_selectedTab"/>
	<input type="hidden" name="executed" value="${executed}" id="${vulpeFormName}_executed"/>
	<input type="hidden" name="entity.orderBy" value="${entity.orderBy}" id="${vulpeFormName}_entity.orderBy"/>
	<input type="hidden" name="popupKey" value="${popupKey}" id="${vulpeFormName}_popupKey"/>
	</c:if>
	<c:if test="${securityContext.authenticated && empty popupKey}">
	<div id="userAuthenticated"><fmt:message key="label.vulpe.security.logged.welcome"><fmt:param value="${securityContext.user.name}"/></fmt:message>&nbsp;<a href="${pageContext.request.contextPath}/j_spring_security_logout"><fmt:message key="label.vulpe.security.logoff"/></a></div>
	</c:if>
	<c:if test="${showTitle && empty vulpeBodyTwice}">
		<div id="contentTitle"><fmt:message>${controllerConfig.titleKey}${onlyToSee ? '.view' : ''}</fmt:message></div>
	</c:if>
	<div id="content">
		<c:choose>
			<c:when test="${vulpeBodyTwice}">
				<c:choose>
				<c:when test="${vulpeBodyTwiceType == 'CRUD'}">
				<%@include file="/WEB-INF/protected-jsp/commons/crud.jsp" %>
				</c:when>
				<c:when test="${vulpeBodyTwiceType == 'SELECT'}">
				<%@include file="/WEB-INF/protected-jsp/commons/select.jsp" %>
				</c:when>
				</c:choose>
			</c:when>
			<c:when test="${controllerConfig.controllerType == 'CRUD'}">
				<%@include file="/WEB-INF/protected-jsp/commons/crud.jsp" %>
			</c:when>
			<c:when test="${controllerConfig.controllerType == 'SELECT'}">
				<%@include file="/WEB-INF/protected-jsp/commons/select.jsp" %>
			</c:when>
			<c:when test="${controllerConfig.controllerType == 'TABULAR'}">
				<%@include file="/WEB-INF/protected-jsp/commons/tabular.jsp" %>
			</c:when>
			<c:when test="${controllerConfig.controllerType == 'REPORT'}">
				<%@include file="/WEB-INF/protected-jsp/commons/report.jsp" %>
			</c:when>
			<c:when test="${controllerConfig.controllerType == 'FRONTEND'}">
				<%@include file="/WEB-INF/protected-jsp/commons/frontend.jsp" %>
			</c:when>
			<c:when test="${controllerConfig.controllerType == 'BACKEND'}">
				<%@include file="/WEB-INF/protected-jsp/commons/backend.jsp" %>
			</c:when>
		</c:choose>
	</div>
</v:form>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<v:form id="${controllerConfig.formName}" name="${controllerConfig.formName}" theme="simple" validate="true" enctype="multipart/form-data" method="post">
	<c:if test="${!onlyToSee}">
	<input type="hidden" name="operation" value="${operation}" id="${controllerConfig.formName}_operation"/>
	<input type="hidden" name="paging.page" value="${paging.page}" id="${controllerConfig.formName}_paging.page"/>
	<input type="hidden" name="id" value="${id}" id="${controllerConfig.formName}_id"/>
	<input type="hidden" name="selectedTab" value="${selectedTab}" id="${controllerConfig.formName}_selectedTab"/>
	<input type="hidden" name="executed" value="${executed}" id="${controllerConfig.formName}_executed"/>
	<input type="hidden" name="entity.orderBy" value="${entity.orderBy}" id="${controllerConfig.formName}_entity.orderBy"/>
	<input type="hidden" name="popupKey" value="${popupKey}" id="${controllerConfig.formName}_popupKey"/>
	</c:if>
	<c:if test="${authenticated && empty popupKey}">
	<div id="userAuthenticated"><fmt:message key="label.vulpe.security.logged.welcome"><fmt:param value="${userName}"/></fmt:message>&nbsp;<a href="${pageContext.request.contextPath}/j_spring_security_logout"><fmt:message key="label.vulpe.security.logoff"/></a></div>
	</c:if>
	<c:if test="${showTitle}">
		<div id="contentTitle"><fmt:message>${controllerConfig.titleKey}${onlyToSee ? '.view' : ''}</fmt:message></div>
	</c:if>
	<div id="content">
		<c:choose>
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
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="vulpeFormName" value="${now['formName']}" scope="request"/>
<c:if test="${now['bodyTwice']}">
	<c:choose>
	<c:when test="${now['bodyTwiceType'] == 'MAIN'}"><c:set var="vulpeFormName" value="${now['controllerConfig'].mainFormName}" scope="request"/></c:when>
	<c:when test="${now['bodyTwiceType'] == 'SELECT'}"><c:set var="vulpeFormName" value="${now['controllerConfig'].selectFormName}" scope="request"/></c:when>
	</c:choose>
</c:if>
<v:form id="${vulpeFormName}" name="${vulpeFormName}" theme="simple" validate="true" enctype="multipart/form-data" method="post">
	<div id="vulpeControlFields">
	<input type="hidden" name="now.controllerType" value="${now['controllerType']}" id="${vulpeFormName}-controllerType"/>
	<input type="hidden" name="now.operation" value="${now['operation']}" id="${vulpeFormName}-operation"/>
	<c:if test="${now['controllerType'] == 'TABULAR'}"><input type="hidden" name="ever.tabularSize" value="${ever['tabularSize']}" id="${vulpeFormName}-tabularSize"/></c:if>
	<input type="hidden" name="now.selectedTab" value="${now.selectedTab}" id="${vulpeFormName}-selectedTab"/>
	<input type="hidden" name="now.executed" value="${now['executed']}" id="${vulpeFormName}-executed"/>
	<c:if test="${now['controllerType'] != 'FRONTEND' && now['controllerType'] != 'BACKEND'}"><input type="hidden" name="entitySelect.orderBy" value="${entity.orderBy}" id="${vulpeFormName}-entitySelect_orderBy"/></c:if>
	<input type="hidden" name="now.popupKey" value="${now['popupKey']}" id="${vulpeFormName}-popupKey"/>
	</div>
	<c:if test="${securityContext.authenticated && empty now['popupKey'] && (empty now['bodyTwice'] || now['bodyTwiceType'] == 'MAIN')}"><%@include file="/WEB-INF/protected-jsp/commons/userAuthenticated.jsp" %></c:if>
	<c:if test="${now['showContentTitle'] && empty now['bodyTwice']}">
	<c:if test="${not empty now['titleKey']}"><fmt:message var="contentTitle">${now['titleKey']}${onlyToSee ? '.view' : ''}</fmt:message></c:if>
	<div id="contentTitle">${not empty now['contentTitle'] ? now['contentTitle'] : contentTitle}</div>
	</c:if>
	<c:if test="${now['showContentSubtitle'] && empty now['bodyTwice']}">
		<c:if test="${not empty now['subtitleKey']}"><fmt:message var="contentSubtitle">${now['subtitleKey']}</fmt:message></c:if>
		<div id="contentSubtitle">${not empty now['contentSubtitle'] ? now['contentSubtitle'] : contentSubtitle}</div>
	</c:if>
	<%@include file="/WEB-INF/protected-jsp/commons/content.jsp" %>
</v:form>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="vulpeFormName" value="${now['formName']}" scope="request"/>
<c:if test="${vulpeBodyTwice}">
	<c:choose>
	<c:when test="${vulpeBodyTwiceType == 'MAIN'}"><c:set var="vulpeFormName" value="${controllerConfig.MainFormName}" scope="request"/></c:when>
	<c:when test="${vulpeBodyTwiceType == 'SELECT'}"><c:set var="vulpeFormName" value="${controllerConfig.selectFormName}" scope="request"/></c:when>
	</c:choose>
</c:if>
<v:form id="${vulpeFormName}" name="${vulpeFormName}" theme="simple" validate="true" enctype="multipart/form-data" method="post">
	<input type="hidden" name="controllerType" value="${controllerType}" id="${vulpeFormName}-controllerType"/>
	<input type="hidden" name="operation" value="${operation}" id="${vulpeFormName}-operation"/>
	<%--<input type="hidden" name="paging.page" value="${paging.page}" id="${vulpeFormName}-paging.page"/>--%>
	<c:if test="${now['controllerType'] == 'TABULAR'}"><input type="hidden" name="tabularSize" value="${tabularSize}" id="${vulpeFormName}-tabularSize"/></c:if>
	<%--<input type="hidden" name="id" value="${id}" id="${vulpeFormName}-id"/>--%>
	<input type="hidden" name="selectedTab" value="${selectedTab}" id="${vulpeFormName}-selectedTab"/>
	<input type="hidden" name="executed" value="${executed}" id="${vulpeFormName}-executed"/>
	<c:if test="${now['controllerType'] != 'FRONTEND' && now['controllerType'] != 'BACKEND'}"><input type="hidden" name="entitySelect.orderBy" value="${entity.orderBy}" id="${vulpeFormName}-entitySelect_orderBy"/></c:if>
	<input type="hidden" name="popupKey" value="${popupKey}" id="${vulpeFormName}-popupKey"/>
	<c:if test="${securityContext.authenticated && empty popupKey && (empty vulpeBodyTwice || vulpeBodyTwiceType == 'MAIN')}"><%@include file="/WEB-INF/protected-jsp/commons/userAuthenticated.jsp" %></c:if>
	<c:if test="${now['showContentTitle'] && empty vulpeBodyTwice}">
	<c:if test="${not empty now['titleKey']}"><fmt:message var="contentTitle">${now['titleKey']}${onlyToSee ? '.view' : ''}</fmt:message></c:if>
	<div id="contentTitle">${not empty now['contentTitle'] ? now['contentTitle'] : contentTitle}</div>
	</c:if>
	<c:if test="${now['showContentSubtitle'] && empty vulpeBodyTwice}">
		<c:if test="${not empty now['subtitleKey']}"><fmt:message var="contentSubtitle">${now['subtitleKey']}</fmt:message></c:if>
		<div id="contentSubtitle">${not empty now['contentSubtitle'] ? now['contentSubtitle'] : contentSubtitle}</div>
	</c:if>
	<%@include file="/WEB-INF/protected-jsp/commons/content.jsp" %>
</v:form>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<div id="bottom">
<ul>
	<li>
	<h1><fmt:message key='label.portal.Learn' /></h1>
	</li>
	<li><a href="javascript:void(0);"
		onclick="vulpe.view.request.submitMenu('/frontend/Learn/ajax');"><fmt:message key='label.portal.bottom.first.steps'/></a></li>
	<li>Tutorial</li>
</ul>
<ul>
	<li>
	<h1><fmt:message key='label.portal.Community' /></h1>
	</li>
	<li><a href="http://twitter.com/vulpeframework" target="_blank">@vulpeframework</a></li>
	<li><a href="http://groups.google.com/group/vulpe-framework"
		target="_blank">vulpe @ Google group</a></li>
</ul>
<ul>
	<li>
	<h1><fmt:message key='label.portal.Code' /></h1>
	</li>
	<li><a href="http://code.google.com/p/vulpe" target="_blank">vulpe
	@ Google code</a></li>
</ul>
<ul class="last">
	<li>
	<h1><fmt:message key='label.portal.Downloads' /></h1>
	</li>
	<c:forEach var="download" items="${vulpePortalDownloads}">
	<li><a href="${pageContext.request.contextPath}/frontend/Index/download?downloadId=${download.id}">${download.name}</a></li>
	</c:forEach>
</ul>
</div>
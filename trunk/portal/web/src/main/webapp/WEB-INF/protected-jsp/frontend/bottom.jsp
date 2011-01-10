<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<div id="bottom">
<ul>
	<li>
	<h1><fmt:message key='label.portal.Links' /></h1>
	</li>
	<c:forEach var="link" items="${ever['vulpePortalLinks']}">
	<c:forEach var="position" items="${link.positions}">
	<c:if test="${position == 'FOOTER'}">
	<li><a href="${pageContext.request.contextPath}/frontend/Index/link?linkId=${link.id}" target="${link.target.value}">${link.name}</a></li>
	</c:if>
	</c:forEach>
	</c:forEach>
</ul>
<ul>
	<li>
	<h1><fmt:message key='label.portal.Social' /></h1>
	</li>
	<c:forEach var="social" items="${ever['vulpePortalSocial']}">
	<c:choose>
	<c:when test="${social.network == 'TWITTER'}">
		<li><a href="http://twitter.com/${social.name}" target="${social.target.value}">@${social.name}</a></li>
	</c:when>
	</c:choose>
	</c:forEach>
</ul>
<ul>
	<li>
	<h1><fmt:message key='label.portal.Community' /></h1>
	</li>
	<c:forEach var="community" items="${ever['vulpePortalCommunities']}">
	<li><a href="${community.url}" target="${community.target.value}">${community.name}</a></li>
	</c:forEach>
</ul>
<ul class="last">
	<li>
	<h1><fmt:message key='label.portal.Downloads' /></h1>
	</li>
	<c:forEach var="download" items="${ever['vulpePortalDownloads']}">
	<li><a href="${pageContext.request.contextPath}/frontend/Index/download?downloadId=${download.id}">${download.name}</a></li>
	</c:forEach>
</ul>
</div>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:choose>
	<c:when test="${ever['vulpeCurrentLayout'] == 'FRONTEND'}">
	<div id="portal">
		<div id="search">
		<input type="text" id="querySearch" name="querySearch" size="40"><v:action labelKey="label.portal.search" action="search" showIconOfButton="false" styleClass="button" elementId="search-button" layerFields="search" beforeJs="portal.querySearch()" />
		</div>
		<div id="portalLogo"></div>
	</div>
	<div id="menuleft"></div>
	<div id="menuright"></div>
	</c:when>
	<c:otherwise>
		<div class="image"></div>
	</c:otherwise>
</c:choose>
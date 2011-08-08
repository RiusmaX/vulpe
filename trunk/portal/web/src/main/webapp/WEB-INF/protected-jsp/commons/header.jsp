<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:choose>
	<c:when test="${ever['vulpeCurrentLayout'] == 'FRONTEND'}">
	<div id="vulpe">
		<div id="search">
		<input type="text" name="querySearch" size="40"><v:action labelKey="label.portal.search" action="search" showIconOfButton="false" styleClass="button" elementId="search-button" />
		</div>
		<div id="vulpeqs"></div>
	</div>
	<div id="menuleft"></div>
	<div id="menuright"></div>
	</c:when>
	<c:otherwise>
		<div class="image"></div>
	</c:otherwise>
</c:choose>
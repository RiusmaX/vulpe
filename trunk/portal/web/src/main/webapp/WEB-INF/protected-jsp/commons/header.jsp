<c:choose>
	<c:when test="${ever['vulpeCurrentLayout'] == 'FRONTEND'}">
	<div id="vulpe">
		<div id="vulpeqs"></div>
	</div>
	<div id="menuleft"></div>
	<div id="menuright"></div>
	</c:when>
	<c:otherwise>
		<div class="image"></div>
	</c:otherwise>
</c:choose>
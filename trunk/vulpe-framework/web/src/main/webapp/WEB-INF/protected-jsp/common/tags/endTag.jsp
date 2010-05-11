<c:if test="${!showAsText || !onlyToSee}">
	<a id="${elementId}_loading" style="display: none;"
		class="loadingField"><img
		src="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/loader-field.gif"
		border="0" /></a>
	<a id="${elementId}_Error" style="display: none;" href="javascript:;"
		onclick="vulpe.util.get('${elementId}').focus();"><img id="${elementId}_ErrorMsg"
		src="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/stop.png"
		border="0" />
	</a>
</c:if>
<c:if test="${paragraph}">
	</p>
</c:if>

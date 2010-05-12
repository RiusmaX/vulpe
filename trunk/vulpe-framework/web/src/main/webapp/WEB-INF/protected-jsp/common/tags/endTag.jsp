<c:if test="${!showAsText || !onlyToSee}">
	<img id="${elementId}_loading" class="imageFieldLoading"
		src="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/loader-field.gif" />
	<img id="${elementId}_ErrorMessage"
		src="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/stop.png"
		onclick="vulpe.util.get('${elementId}').focus();" class="imageErrorMessage" />
</c:if>
<c:if test="${paragraph}">
	</p>
</c:if>
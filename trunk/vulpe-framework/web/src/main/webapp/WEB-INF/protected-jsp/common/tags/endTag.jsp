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
<c:if test="${not empty validateType}">
<script type="text/javascript">
vulpe.config.elements["${elementId}"] = {
	type: '${validateType}'
	<c:if test="${not empty validateRange}">,range: '${validateRange}'</c:if>
	<c:if test="${not empty validateMin}">,min: '${validateMin}'</c:if>
	<c:if test="${not empty validateMax}">,max: '${validateMax}'</c:if>
	<c:if test="${not empty validateMinLength}">,minLength: '${validateMinLength}'</c:if>
	<c:if test="${not empty validateMaxLength}">,maxLength: '${validateMaxLength}'</c:if>
	<c:if test="${not empty validateMask}">,mask: '${validateMask}'</c:if>
	<c:if test="${not empty validateDatePattern}">,datePattern: '${validateDatePattern}'</c:if>
}
</script>
</c:if>
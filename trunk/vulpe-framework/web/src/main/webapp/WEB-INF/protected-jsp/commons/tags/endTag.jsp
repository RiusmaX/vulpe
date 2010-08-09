<c:if test="${!showAsText || !onlyToSee}">
<img id="${elementId}_loading" class="vulpeImageFieldLoading" src="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/ajax/field-loader.gif" />
<img id="${elementId}_ErrorMessage" src="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icons/field-error-stop.png" onclick="vulpe.util.get('${elementId}').focus();" class="vulpeImageErrorMessage" />
</c:if>
<c:if test="${required}"><span class="vulpeRequiredField">*</span></c:if>
<c:if test="${paragraph}"></p></c:if>
<c:if test="${not empty requiredField || not empty validateType}">
<script type="text/javascript">
<c:if test="${not empty requiredField}">
vulpe.util.get('${elementId}').blur(function() {
	var requiredFieldId = "${vulpeFormName}_${fn:replace(prepareName, property, requiredField)}";
	var value = $(this).val();
	var requiredFieldValue = vulpe.util.get(requiredFieldId).val();
	if (value == "" || requiredFieldValue != "") {
		vulpe.util.get(requiredFieldId).removeClass("vulpeRequired");
	} else {
		vulpe.util.get(requiredFieldId).addClass("vulpeRequired");
	}
});
</c:if>
<c:if test="${not empty validateType}">
vulpe.config.elements["${elementId}"] = {
	type: '${validateType}'
	<c:if test="${not empty validateRange}">,range: '${validateRange}'</c:if>
	<c:if test="${not empty validateMin}">,min: '${validateMin}'</c:if>
	<c:if test="${not empty validateMax}">,max: '${validateMax}'</c:if>
	<c:if test="${not empty validateMinLength}">,minlength: '${validateMinLength}'</c:if>
	<c:if test="${not empty validateMaxLength}">,maxlength: '${validateMaxLength}'</c:if>
	<c:if test="${not empty validateMask}">,mask: '${validateMask}'</c:if>
	<c:if test="${not empty validateDatePattern}">,datePattern: '${validateDatePattern}'</c:if>
}
</c:if>
</script>
</c:if>
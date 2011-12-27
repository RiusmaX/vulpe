<c:if test="${!showAsText}">
<c:if test="${showRequiredIcon}">
<img id="${elementId}-loading" class="vulpeImageFieldLoading" src="${pageContext.request.contextPath}/themes/${global['project-theme']}/images/ajax/field-loader.gif" alt="<fmt:message key="label.vulpe.image.loading"/>" style="display: none" />
<img id="${elementId}-iconErrorMessage" class="vulpeImageErrorMessage" src="${pageContext.request.contextPath}/themes/${global['project-theme']}/images/icons/field-error-stop.png" alt="<fmt:message key="label.vulpe.image.field.error"/>" style="display: none" />
<c:if test="${!showAsText && not empty validateMaxLength}"><span id="${elementId}-charcount" class="vulpeCharCount"></span></c:if>
<span id="${elementId}-errorMessage" class="vulpeErrorMessage" style="display: none">&nbsp;</span>
</c:if>
<c:if test="${showAsText && empty value}">&nbsp;</c:if>
<script type="text/javascript">
vulpe.util.get('${elementId}-loading').hide();
vulpe.util.get('${elementId}-iconErrorMessage').hide();
vulpe.util.get('${elementId}-iconErrorMessage').bind('click', function(){
	vulpe.util.get('${elementId}').focus()
});
<c:if test="${not empty requiredFields || not empty validateType}">
<c:if test="${not empty requiredFields}">
vulpe.util.get('${elementId}').blur(function() {
	var value = $(this).val();
	var checkRequired = function(prepareName, property, requiredField) {
		var requiredFieldId = prepareName.replace(property, requiredField).replace(/\./g, "_").replace(/-/g, "_");
		vulpe.view.setRequired(requiredFieldId, (value != ""));
	}
	<c:forTokens var="requiredField" items="${requiredFields}" delims=",">
	checkRequired("${prepareName}", "${property}", "${requiredField}");
	</c:forTokens>
});
</c:if>
<c:if test="${not empty validateType}">
<c:if test="${!showAsText && not empty validateMaxLength}">
jQuery(function($){
	vulpe.util.get("${elementId}").charCounter(${validateMaxLength}, {
		container: "#${elementId}-charcount",
		pulse: !vulpe.config.browser.ie6,
		format: vulpe.config.messages.charCount,
		delay: 50
	});
});
</c:if>
vulpe.config.elements["${elementId}"] = {
	type: '${fn:toUpperCase(validateType)}'
	<c:if test="${not empty validateRange}">,range: '${validateRange}'</c:if>
	<c:if test="${not empty validateMin}">,min: '${validateMin}'</c:if>
	<c:if test="${not empty validateMax}">,max: '${validateMax}'</c:if>
	<c:if test="${not empty validateMinLength}">,minlength: '${validateMinLength}'</c:if>
	<c:if test="${not empty validateMaxLength}">,maxlength: '${validateMaxLength}'</c:if>
	<c:if test="${not empty validateMask}">,mask: '${validateMask}'</c:if>
	<c:if test="${not empty validateDatePattern}">,datePattern: '${validateDatePattern}'</c:if>
}
</c:if>
</c:if>
</script>
</c:if>
</span>
<c:if test="${paragraph}"></p></c:if>
<script src="${pageContext.request.contextPath}/js/bodyoverlay.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>
<c:if test="${(vulpeCurrentLayout == 'FRONTEND' && vulpeFrontendMenuType == 'DROPPY') || (vulpeCurrentLayout == 'BACKEND' && vulpeBackendMenuType == 'DROPPY')}">
<script src="${pageContext.request.contextPath}/js/jquery.droppy.js" type="text/javascript" charset="utf-8"></script>
</c:if>
<script src="${pageContext.request.contextPath}/js/jquery.form.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.hotkeys.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.lightbox.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.maskedinput.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.simplemodal.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.rte.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.tools.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.datepicker.i18n.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.validation.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.browser.detect.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.webtoolkit.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/application.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/themes/${vulpeTheme}/js/frontend/${vulpeTheme}.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript" charset="utf-8">
vulpe.config.contextPath = '${pageContext.request.contextPath}';
vulpe.config.theme = '${vulpeTheme}';
vulpe.config.messages = {
	error: {
		checkfield: '<fmt:message key="vulpe.error.validate.checkfield"/>', 
		checkfields: '<fmt:message key="vulpe.error.validate.checkfields"/>',
		validate: {
			date:'<fmt:message key="vulpe.error.validate.date"/>',
			double:'<fmt:message key="vulpe.error.validate.double"/>',
			float:'<fmt:message key="vulpe.error.validate.float"/>',
			floatRange:'<fmt:message key="vulpe.error.validate.float.range"/>',
			integer:'<fmt:message key="vulpe.error.validate.integer"/>',
			integerRange:'<fmt:message key="vulpe.error.validate.integer.range"/>',
			long:'<fmt:message key="vulpe.error.validate.long"/>',
			maxlength:'<fmt:message key="vulpe.error.validate.maxlength"/>',
			minlength:'<fmt:message key="vulpe.error.validate.minlength"/>',
			required:'<fmt:message key="vulpe.error.validate.required"/>'	
		}
	},
	exclusion: '<fmt:message key="vulpe.msg.confirm.exclusion"/>',
	fieldRequired: '<fmt:message key="vulpe.js.error.required"/>',
	keyRequired: '<fmt:message key="vulpe.js.error.key.required"/>',
	selectedExclusion: '<fmt:message key="vulpe.msg.confirm.selected.exclusion"/>',
	selectRecordsExclusion: '<fmt:message key="vulpe.msg.select.records.exclusion"/>',
	upload: '<fmt:message key="vulpe.error.upload"/>'
}
vulpe.config.lightbox = {
	imageText: '<fmt:message key="vulpe.lightbox.image.text"/>',
	ofText: '<fmt:message key="vulpe.lightbox.of.text"/>',
}
vulpe.config.messageSlideUpTime = '${vulpeMessageSlideUpTime}';
<c:if test="${vulpeShowAsMobile}">
vulpe.config.popup.mobile = true;
</c:if>
vulpe.config.popup.closeTitle = '<fmt:message key="vulpe.js.close.popup.title"/>';

$("#alertDialog").dialog({
	autoOpen: false,
	bgiframe: true,
	modal: true,
	buttons: {
		Ok: function() {
			$(this).dialog('close');
		}
	}
});

$("#confirmationDialog").dialog({
	autoOpen: false,
	bgiframe: true,
	resizable: false,
	height:140,
	modal: true,
	overlay: {
		backgroundColor: '#000',
		opacity: 0.5
	},
	buttons: {
		Ok: function() {
			$(this).dialog('close');
			if (vulpe.command) {
				vulpe.command();
			}
		},
		Cancel: function() {
			$(this).dialog('close');
		}
	}
});
</script>
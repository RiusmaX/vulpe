<script type="text/javascript" charset="utf-8">
_vulpeContextPath = '${pageContext.request.contextPath}';
_vulpeTheme = '${vulpeTheme}';
_vulpeMsgSelectedExclusion = '<fmt:message key="vulpe.msg.confirm.selected.exclusion"/>';
_vulpeMsgExclusion = '<fmt:message key="vulpe.msg.confirm.exclusion"/>';
_vulpeMsgSelectRecordsExclusion = '<fmt:message key="vulpe.msg.select.records.exclusion"/>';
_vulpeMsgUpload = '<fmt:message key="vulpe.error.upload"/>';
_vulpeLightboxImageText = '<fmt:message key="vulpe.lightbox.image.text"/>';
_vulpeLightboxOfText = '<fmt:message key="vulpe.lightbox.of.text"/>';
<c:if test="${vulpeShowAsMobile}">
_vulpePopupMobile = true;
</c:if>
_vulpeMsgFieldRequired = '<fmt:message key="vulpe.js.error.required"/>';
_vulpeMsgKeyRequired = '<fmt:message key="vulpe.js.error.key.required"/>';
_vulpeClosePopupTitle = '<fmt:message key="vulpe.js.close.popup.title"/>';
_vulpeValidateMessages = {validate: "<fmt:message key='vulpe.error.validate'/>", required: "<fmt:message key='vulpe.error.required'/>"}

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
			if (_command) {
				_command();
			}
		},
		Cancel: function() {
			$(this).dialog('close');
		}
	}
});
</script>
<%-- 
<script src="${pageContext.request.contextPath}/themes/${vulpeTheme}/js/${vulpeTheme}.js" type="text/javascript" charset="utf-8"></script>
--%>
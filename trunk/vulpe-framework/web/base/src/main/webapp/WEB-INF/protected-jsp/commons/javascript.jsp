<script src="${pageContext.request.contextPath}/js/bodyoverlay.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.charcounter.js" type="text/javascript" charset="utf-8"></script>
<!--[if IE]><script src="${pageContext.request.contextPath}/js/jquery.bgiframe.js" type="text/javascript" charset="utf-8"></script><![endif]-->
<c:if test="${(vulpeCurrentLayout == 'FRONTEND' && global['frontendMenuType'] == 'DROPPY') || (vulpeCurrentLayout == 'BACKEND' && global['backendMenuType'] == 'DROPPY')}"><script src="${pageContext.request.contextPath}/js/jquery.droppy.js" type="text/javascript" charset="utf-8"></script></c:if>
<c:if test="${(vulpeCurrentLayout == 'FRONTEND' && global['frontendMenuType'] == 'SUPERFISH') || (vulpeCurrentLayout == 'BACKEND' && global['backendMenuType'] == 'SUPERFISH')}">
<script src="${pageContext.request.contextPath}/js/jquery.hover.intent.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.superfish.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.supersubs.js" type="text/javascript" charset="utf-8"></script>
</c:if>
<script src="${pageContext.request.contextPath}/js/jquery.form.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.growl.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.hotkeys.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.lightbox.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.maskedinput.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.simplemodal.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.rte.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.rte.toolbar.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.tools.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.datepicker.i18n.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.validation.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.browser.detect.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.webtoolkit.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/application.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/themes/${global['theme']}/js/frontend/${global['theme']}.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
vulpe.config.contextPath = '${pageContext.request.contextPath}';
vulpe.config.theme = '${global['theme']}';
vulpe.config.messages = {
	error: {
		checkfield: '<fmt:message key="vulpe.error.validate.checkfield"/>',
		checkfields: '<fmt:message key="vulpe.error.validate.checkfields"/>',
		fatal: '<fmt:message key="vulpe.error.fatal"/>',
		validate: {
			date:'<fmt:message key="vulpe.error.validate.date"/>',
			double:'<fmt:message key="vulpe.error.validate.double"/>',
			float:'<fmt:message key="vulpe.error.validate.float"/>',
			floatRange:'<fmt:message key="vulpe.error.validate.float.range"/>',
			integer:'<fmt:message key="vulpe.error.validate.integer"/>',
			integerRange:'<fmt:message key="vulpe.error.validate.integer.range"/>',
			long:'<fmt:message key="vulpe.error.validate.long"/>',
			mask:'<fmt:message key="vulpe.error.validate.mask"/>',
			maxlength:'<fmt:message key="vulpe.error.validate.maxlength"/>',
			minlength:'<fmt:message key="vulpe.error.validate.minlength"/>',
			required:'<fmt:message key="vulpe.error.validate.required"/>',
			requireOneFilter: '<fmt:message key="vulpe.error.validate.require.one.filter"/>',
			repeatedCharacters: '<fmt:message key="vulpe.error.validate.repeated.characters"/>'
		}
	},
	clear: '<fmt:message key="vulpe.message.confirm.clear"/>',
	charCount: '<fmt:message key="vulpe.message.charCount"/>',
	deleteThis: '<fmt:message key="vulpe.message.confirm.delete"/>',
	fieldRequired: '<fmt:message key="vulpe.js.error.required"/>',
	keyRequired: '<fmt:message key="vulpe.js.error.key.required"/>',
	deleteSelected: '<fmt:message key="vulpe.message.confirm.delete.selected"/>',
	selectRecordsToDelete: '<fmt:message key="vulpe.message.select.records.to.delete"/>',
	updatePost: '<fmt:message key="vulpe.message.confirm.updatePost"/>',
	upload: '<fmt:message key="vulpe.error.upload"/>',
	close: '<fmt:message key="vulpe.messages.close"/>'
}
vulpe.config.lightbox = {
	imageText: '<fmt:message key="vulpe.lightbox.image.text"/>',
	ofText: '<fmt:message key="vulpe.lightbox.of.text"/>'
}
vulpe.config.messageSlideUp = '${global['messageSlideUp']}';
vulpe.config.messageSlideUpTime = '${global['messageSlideUpTime']}';
<c:if test="${global['showAsMobile']}">
vulpe.config.popup.mobile = true;
</c:if>
vulpe.config.popup.closeTitle = '<fmt:message key="vulpe.js.close.popup.title"/>';
vulpe.config.accentMap = {
	"á": "a", "â": "a", "ã": "a", "à": "a",	"ä": "a",
	"Á": "A", "Â": "A", "Ã": "A", "À": "A",	"Ä": "A",
	"é": "e", "ê": "e",	"è": "e", "ë": "e",
	"É": "E", "Ê": "E",	"È": "E", "Ë": "E",
	"í": "i", "î": "i", "ì": "i", "ï": "i",
	"Í": "I", "Î": "I", "Ì": "I", "Ï": "I",
	"ó": "o", "ö": "o", "ô": "o", "õ": "o", "ò": "o",
	"Ó": "O", "Ö": "O", "Ô": "O", "Õ": "O", "Ò": "O",
	"ú": "u", "ü": "u", "û": "u", "ù": "u",
	"Ú": "U", "Ü": "U", "Û": "U", "Ù": "U",
	"ç": "c",
	"Ç": "C"
}
</script>
<%@include file="/WEB-INF/protected-jsp/commons/javascriptExtended.jsp"%>
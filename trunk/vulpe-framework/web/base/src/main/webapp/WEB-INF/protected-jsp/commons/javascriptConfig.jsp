<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<script type="text/javascript" charset="utf-8">
vulpe.config.buttons = {
	ok: '<fmt:message key="label.vulpe.button.ok"/>',
	cancel: '<fmt:message key="label.vulpe.button.cancel"/>',
	yes: '<fmt:message key="label.vulpe.button.yes"/>',
	no: '<fmt:message key="label.vulpe.button.no"/>'
}
vulpe.config.contextPath = '${pageContext.request.contextPath}';
vulpe.config.dialogs = {
	alertTitle: "<fmt:message key='vulpe.dialog.alert.title'/>",
	confirmationTitle: "<fmt:message key='vulpe.dialog.confirmation.title'/>",
	warningTitle: "<fmt:message key='vulpe.dialog.warning.title'/>",
	successTitle: "<fmt:message key='vulpe.dialog.success.title'/>"
}
vulpe.config.theme = '${global['application-theme']}';
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
	deleteFile: '<fmt:message key="vulpe.message.confirm.delete.file"/>',
	fieldRequired: '<fmt:message key="vulpe.js.error.required"/>',
	keyRequired: '<fmt:message key="vulpe.js.error.key.required"/>',
	deleteSelected: '<fmt:message key="vulpe.message.confirm.delete.selected"/>',
	deleteSelecteds: '<fmt:message key="vulpe.message.confirm.delete.selecteds"/>',
	loadingBase: '<fmt:message key="vulpe.message.loading"/>',
	loadingCreate: '<fmt:message key="vulpe.message.loading.create"/>',
	loadingCreatePost: '<fmt:message key="vulpe.message.loading.createPost"/>',
	loadingUpdate: '<fmt:message key="vulpe.message.loading.update"/>',
	loadingUpdatePost: '<fmt:message key="vulpe.message.loading.updatePost"/>',
	loadingDelete: '<fmt:message key="vulpe.message.loading.delete"/>',
	loadingRead: '<fmt:message key="vulpe.message.loading.read"/>',
	loadingClear: '<fmt:message key="vulpe.message.loading.clear"/>',
	loadingBack: '<fmt:message key="vulpe.message.loading.back"/>',
	selectRecordsToDelete: '<fmt:message key="vulpe.message.select.records.to.delete"/>',
	updatePost: '<fmt:message key="vulpe.message.confirm.updatePost"/>',
	upload: '<fmt:message key="vulpe.error.upload"/>',
	close: '<fmt:message key="vulpe.messages.close"/>'
}
vulpe.config.lightbox = {
	imageText: '<fmt:message key="vulpe.lightbox.image.text"/>',
	ofText: '<fmt:message key="vulpe.lightbox.of.text"/>'
}
vulpe.config.messageSlideUp = "${global['application-view-messageSlideUp']}";
vulpe.config.messageSlideUpTime = "${global['application-view-messageSlideUpTime']}";
<c:if test="${global['application-mobile-enabled']}">vulpe.config.popup.mobile = true;</c:if>
<c:if test="${global['application-view-showReportInNewWindow']}">vulpe.config.layout.showReportInNewWindow = true;</c:if>
<c:if test="${!global['application-view-showIconErrorMessage']}">vulpe.config.layout.showIconErrorMessage = false;</c:if>
<c:if test="${global['application-view-layout-displaySpecificMessagesWhenLoading']}">vulpe.config.layout.displaySpecificMessagesWhenLoading = true;</c:if>
<c:if test="${global['application-view-layout-showLoadingAsModal']}">vulpe.config.layout.loading.modal = true;</c:if>
vulpe.config.hotKeys = eval("${global['application-hotKeys']}");
vulpe.config.sortType = "${global['application-view-sortType']}";
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
<c:if test="${util:isAuthenticated(pageContext)}">
vulpe.config.session = {
	idleTime: ${global['application-view-session-idleTime']},
	initialSessionTimeoutMessage: "<fmt:message key='vulpe.message.session.initialSessionTimeoutMessage'/>",
	timeoutCountdownId: "#sessionTimeoutCountdown",
	redirectAfter: ${global['application-view-session-redirectAfter']},
	redirectTo: eval("${global['application-view-session-redirectTo']}"),
	keepAliveURL: eval("${global['application-view-session-keepAliveURL']}"),
	expireSessionMessageTitle: "<fmt:message key='vulpe.message.session.expireSessionMessageTitle'/>",
	expiredMessageTitle: "<fmt:message key='vulpe.message.session.expiredMessageTitle'/>",
	expiredMessage: "<fmt:message key='vulpe.message.session.expiredMessage'/>",
	time: ${ever['maxInactiveInterval']}
}
$(document).ready(function() {
	vulpe.view.configureSliderAction();
	vulpe.view.sessionExpirationAlert();
	$.idleTimer(vulpe.config.session.idleTime);
	$(document).bind("idle.idleTimer", function(){
		if ($.data(document, "idleTimer") === "idle" && !vulpe.config.session.running){
			var counter = vulpe.config.session.redirectAfter;
			vulpe.config.session.running = true;
			$(vulpe.config.session.timeoutCountdownId).html(vulpe.config.session.redirectAfter);
			vulpe.view.sessionExpirationAlert("open");
			vulpe.config.session.timer = setInterval(function(){
				counter -= 1;
				if (counter === 0) {
					vulpe.view.sessionExpiredInformation();
				} else {
					$(vulpe.config.session.timeoutCountdownId).html(counter);
				};
			}, 1000);
		};
	});
});
</c:if>
</script>
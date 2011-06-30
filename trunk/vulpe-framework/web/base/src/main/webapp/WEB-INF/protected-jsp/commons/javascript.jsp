<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<script src="${pageContext.request.contextPath}/js/bodyoverlay.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.charcounter.js" type="text/javascript" charset="utf-8"></script>
<!--[if IE]><script src="${pageContext.request.contextPath}/js/jquery.bgiframe.js" type="text/javascript" charset="utf-8"></script><![endif]-->
<c:if test="${(vulpeCurrentLayout == 'FRONTEND' && global['project-view-frontendMenuType'] == 'DROPPY') || (vulpeCurrentLayout == 'BACKEND' && global['project-view-backendMenuType'] == 'DROPPY')}"><script src="${pageContext.request.contextPath}/js/jquery.droppy.js" type="text/javascript" charset="utf-8"></script></c:if>
<c:if test="${(vulpeCurrentLayout == 'FRONTEND' && global['project-view-frontendMenuType'] == 'SUPERFISH') || (vulpeCurrentLayout == 'BACKEND' && global['project-view-backendMenuType'] == 'SUPERFISH')}">
<script src="${pageContext.request.contextPath}/js/jquery.hover.intent.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.superfish.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.supersubs.js" type="text/javascript" charset="utf-8"></script>
</c:if>
<script src="${pageContext.request.contextPath}/js/jquery.form.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.growl.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.hotkeys.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.idle.timer.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.lightbox.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.maskedinput.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.simplemodal.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.rte.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.rte.toolbar.js" type="text/javascript" charset="utf-8"></script>
<%-- <script src="${pageContext.request.contextPath}/js/jquery.tools.js" type="text/javascript" charset="utf-8"></script> --%>
<script src="${pageContext.request.contextPath}/js/jquery.ui.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.datepicker.i18n.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.validation.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.browser.detect.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.webtoolkit.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/application.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/themes/${global['project-theme']}/js/frontend/${global['project-theme']}.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
vulpe.config.buttons = {
	ok: '<fmt:message key="label.vulpe.button.ok"/>',
	cancel: '<fmt:message key="label.vulpe.button.cancel"/>'
}
vulpe.config.contextPath = '${pageContext.request.contextPath}';
vulpe.config.dialogs = {
	alertTitle: "<fmt:message key='vulpe.dialog.warning.title'/>",
	confirmationTitle: "<fmt:message key='vulpe.dialog.confirmation.title'/>",
	successTitle: "<fmt:message key='vulpe.dialog.success.title'/>"
}
vulpe.config.theme = '${global['project-theme']}';
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
	loading: '<fmt:message key="vulpe.message.loading"/>',
	selectRecordsToDelete: '<fmt:message key="vulpe.message.select.records.to.delete"/>',
	updatePost: '<fmt:message key="vulpe.message.confirm.updatePost"/>',
	upload: '<fmt:message key="vulpe.error.upload"/>',
	close: '<fmt:message key="vulpe.messages.close"/>'
}
vulpe.config.lightbox = {
	imageText: '<fmt:message key="vulpe.lightbox.image.text"/>',
	ofText: '<fmt:message key="vulpe.lightbox.of.text"/>'
}
vulpe.config.messageSlideUp = "${global['project-view-messageSlideUp']}";
vulpe.config.messageSlideUpTime = "${global['project-view-messageSlideUpTime']}";
<c:if test="${global['project-mobile-enabled']}">
vulpe.config.popup.mobile = true;
</c:if>
<c:if test="${global['project-view-showReportInNewWindow']}">
vulpe.config.layout.showReportInNewWindow = true;
</c:if>
<c:if test="${!global['project-view-showIconErrorMessage']}">
vulpe.config.layout.showIconErrorMessage = false;
</c:if>
vulpe.config.hotKeys = eval("${global['project-hotKeys']}");
vulpe.config.sortType = "${global['project-view-sortType']}";
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
	idleTime: ${global['project-view-session-idleTime']},
	initialSessionTimeoutMessage: "<fmt:message key='vulpe.message.session.initialSessionTimeoutMessage'/>",
	timeoutCountdownId: "sessionTimeoutCountdown",
	redirectAfter: ${global['project-view-session-redirectAfter']},
	redirectTo: eval("${global['project-view-session-redirectTo']}"),
	keepAliveURL: eval("${global['project-view-session-keepAliveURL']}"),
	expireSessionMessageTitle: "<fmt:message key='vulpe.message.session.expireSessionMessageTitle'/>",
	expiredMessageTitle: "<fmt:message key='vulpe.message.session.expiredMessageTitle'/>",
	expiredMessage: "<fmt:message key='vulpe.message.session.expiredMessage'/>",
	running: false,
	timer: function(){}
}
$(document).ready(function() {
	$(vulpe.config.layers.informationMessage).html(vulpe.config.session.initialSessionTimeoutMessage);
	$(vulpe.config.layers.informationDialog).dialog({
		title: vulpe.config.session.expireSessionMessageTitle,
		autoOpen: false,
		closeOnEscape: false,
		draggable: false,
		width: 460,
		minHeight: 50,
		modal: true,
		beforeclose: function() {
			clearInterval(vulpe.config.session.timer);
			vulpe.config.session.running = false;
			$.ajax({
			  url: vulpe.config.session.keepAliveURL,
			  async: false
			});
		},
		buttons: {
			Ok: function() {
				$(this).dialog("close");
			}
		},
		resizable: false,
		open: function() {
			$("body").css("overflow", "hidden");
		},
		close: function() {
			$("body").css("overflow", "auto");
		}
	});
	$.idleTimer(vulpe.config.session.idleTime);
	$(document).bind("idle.idleTimer", function(){
		if ($.data(document, "idleTimer") === "idle" && !vulpe.config.session.running){
			var counter = vulpe.config.session.redirectAfter;
			vulpe.config.session.running = true;
			$('#' + vulpe.config.session.timeoutCountdownId).html(vulpe.config.session.redirectAfter);
			$(vulpe.config.layers.informationDialog).dialog("open");
			vulpe.config.session.timer = setInterval(function(){
				counter -= 1;
				if (counter === 0) {
					$(vulpe.config.layers.informationMessage).html(vulpe.config.session.expiredMessage);
					//$(vulpe.config.layers.informationDialog).dialog("disable");
					$(vulpe.config.layers.informationDialog).dialog({
						title: vulpe.config.session.expiredMessageTitle,
						autoOpen: false,
						closeOnEscape: false,
						draggable: false,
						width: 460,
						minHeight: 50,
						modal: true,
						beforeclose: function() {
							window.location = vulpe.config.session.redirectTo;
						},
						buttons: {
							Ok: function() {
								window.location = vulpe.config.session.redirectTo;
							}
						},
						resizable: false,
						open: function() {
							$("body").css("overflow", "hidden");
						},
						close: function() {
							$("body").css("overflow", "auto");
						}
					});
					$(vulpe.config.layers.informationDialog).dialog("open");
				} else {
					$('#' + vulpe.config.session.timeoutCountdownId).html(counter);
				};
			}, 1000);
		};
	});
});
</c:if>
</script>
<%@include file="/WEB-INF/protected-jsp/commons/javascriptExtended.jsp"%>
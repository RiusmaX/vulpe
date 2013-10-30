<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="vulpeFormName" value="${now['controllerConfig'].formName}" scope="request" />
<c:if test="${now['bodyTwice']}">
	<c:choose>
		<c:when test="${now['bodyTwiceType'] == 'MAIN'}">
			<c:set var="vulpeFormName" value="${now['controllerConfig'].mainFormName}" scope="request" />
		</c:when>
		<c:when test="${now['bodyTwiceType'] == 'SELECT'}">
			<c:set var="vulpeFormName" value="${now['controllerConfig'].selectFormName}" scope="request" />
		</c:when>
	</c:choose>
</c:if>
<c:if test="${empty targetName}">
	<c:choose>
		<c:when test="${empty targetConfig}">
			<c:set var="prepareName" value="${not empty now['targetName'] ? now['targetName'] : 'entity'}" />
		</c:when>
		<c:otherwise>
			<c:set var="prepareName" value="${targetConfigPropertyName}" />
		</c:otherwise>
	</c:choose>
	<c:set var="prepareName" value="${fn:replace(prepareName, '[', '__')}" />
	<c:set var="prepareName" value="${fn:replace(prepareName, '].', '__')}" />
	<c:set var="prepareName" value="${fn:replace(prepareName, '.', '_')}" />
</c:if>
<c:if test="${not empty vulpeShowMessages || !vulpeShowMessages}">
	<c:set var="vulpeShowMessages" value="true" scope="request" />
	<%@include file="/WEB-INF/protected-jsp/commons/messages/error.jsp"%>
</c:if>
<div id="content">
	<c:if test="${now['showContentTitle'] && empty now['bodyTwice']}">
		<c:if test="${not empty now['titleKey']}"><fmt:message var="contentTitle">${now['titleKey']}${now['onlyToSee'] ? '.view' : ''}</fmt:message></c:if>
		<div id="title">
			${not empty now['contentTitle'] ? now['contentTitle'] : contentTitle}
			<c:if test="${now['showContentSubtitle'] && empty now['bodyTwice']}">
				<c:if test="${not empty now['subtitleKey']}"><fmt:message var="contentSubtitle">${now['subtitleKey']}</fmt:message></c:if>
				<div id="subtitle">${not empty now['contentSubtitle'] ? now['contentSubtitle'] : contentSubtitle}</div>
			</c:if>
		</div>
	</c:if>
	<%@include file="/WEB-INF/protected-jsp/commons/contentPrepend.jsp" %>
	<c:choose>
		<c:when test="${now['bodyTwice']}">
			<c:choose>
				<c:when test="${now['bodyTwiceType'] == 'MAIN'}"><%@include file="/WEB-INF/protected-jsp/commons/main.jsp" %></c:when>
				<c:when test="${now['bodyTwiceType'] == 'SELECT'}"><%@include file="/WEB-INF/protected-jsp/commons/select.jsp" %></c:when>
			</c:choose>
		</c:when>
		<c:when test="${now['controllerType'] == 'MAIN'}"><%@include file="/WEB-INF/protected-jsp/commons/main.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'SELECT'}"><%@include file="/WEB-INF/protected-jsp/commons/select.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'TABULAR'}"><%@include file="/WEB-INF/protected-jsp/commons/tabular.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'REPORT'}"><%@include file="/WEB-INF/protected-jsp/commons/report.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'FRONTEND'}"><%@include file="/WEB-INF/protected-jsp/commons/frontend.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'BACKEND'}"><%@include file="/WEB-INF/protected-jsp/commons/backend.jsp" %></c:when>
		<c:when test="${now['controllerType'] == 'NONE'}"><%@include file="/WEB-INF/protected-jsp/commons/none.jsp" %></c:when>
	</c:choose>
	<%@include file="/WEB-INF/protected-jsp/commons/contentAppend.jsp" %>
</div>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
	vulpe.view.checkTimeToSessionExpire(${ever['maxInactiveInterval']});
	vulpe.config.onlyToSee = ${now['onlyToSee']};
	vulpe.controller.currentName = "${now['controllerConfig'].controllerName}";
	<c:if test="${global['application-view-focusFirst']&& !ajax}">vulpe.util.focusFirst("${now['controllerType'] == 'TABULAR' ? 'entities' : ''}");</c:if>
	<c:if test="${(global['application-view-frontendMenuType'] == 'DROPPY' && (now['controllerType'] == 'FRONTEND' || ever['vulpeCurrentLayout'] == 'FRONTEND')) || (global['application-view-backendMenuType'] == 'DROPPY' && (now['controllerType'] == 'BACKEND' || ever['vulpeCurrentLayout'] == 'BACKEND'))}">$("#nav").droppy();</c:if>
	<c:if test="${(global['application-view-frontendMenuType'] == 'SUPERFISH' && (now['controllerType'] == 'FRONTEND' || ever['vulpeCurrentLayout'] == 'FRONTEND')) || (global['application-view-backendMenuType'] == 'SUPERFISH' && (now['controllerType'] == 'BACKEND' || ever['vulpeCurrentLayout'] == 'BACKEND'))}">if (vulpe.config.browser.ie6 || vulpe.config.browser.ie8) { $("#nav").superfish().find("ul").bgIframe({opacity: false}); } else { $("#nav").superfish(); }</c:if>
	<c:if test="${(global['application-view-frontendMenuType'] == 'VULPE' && (now['controllerType'] == 'FRONTEND' || ever['vulpeCurrentLayout'] == 'FRONTEND')) || (global['application-view-backendMenuType'] == 'VULPE' && (now['controllerType'] == 'BACKEND' || ever['vulpeCurrentLayout'] == 'BACKEND'))}">vulpeMenu.init({ mainmenuid: "menu", orientation: "h", classname: "vulpeMenu",	/*customtheme: ["#1c5a80", "#18374a"],*/ contentsource: "markup" });</c:if>
	<c:if test="${pageContext.request.locale ne 'en_US'}">$.datepicker.setDefaults($.datepicker.regional['${pageContext.request.locale}']);</c:if>
	if (document.forms['${vulpeFormName}']) {
		<c:if test="${empty now['popupKey']}">vulpe.config.formName = "${vulpeFormName}";</c:if>
		vulpe.config.logic.prepareName = "${prepareName}";
		<c:if test="${!now['ajax']}">
		vulpe.util.removeHotKeys($(this));
		vulpe.util.checkHotKeys($(this));
		</c:if>
		var operation = vulpe.util.get("operation");
		operation.val("${now['operation' ]}");
		operation.attr("defaultValue", "${now['operation']}");
		var pagingPage = vulpe.util.get('paging.page');
		pagingPage.val("${paging.page}");
		pagingPage.attr("defaultValue", "${paging.page}");
		var id = vulpe.util.get("id");
		id.val("${id}");
		id.attr("defaultValue", "${id}");
		var executed = vulpe.util.get("executed");
		executed.val("${executed}");
		executed.attr("defaultValue", "${executed}");
		<c:if test="${now['controllerType'] != 'FRONTEND' && now['controllerType'] != 'BACKEND'}">
		var orderBy = vulpe.util.get('entity_orderBy');
		orderBy.val("${entity.orderBy}");
		orderBy.attr("defaultValue", "${entity.orderBy}");
		</c:if>
	}
	$(vulpe.config.layers.alertDialog).dialog({
		title: vulpe.config.dialogs.alertTitle,
		autoOpen: false,
		bgiframe: true,
		minHeight: 50,
		modal: true,
		open: function(event, ui) {
			vulpe.util.removeHotKeys();
		},
		buttons: {
			'<fmt:message key="label.vulpe.button.ok"/>': function() {
				$(this).dialog('close');
				vulpe.util.checkHotKeys();
			}
		}
	});
	$(vulpe.config.layers.confirmationDialog).dialog({
		title: vulpe.config.dialogs.confirmationTitle,
		autoOpen: false,
		bgiframe: true,
		resizable: false,
		minHeight: 50,
		modal: true,
		open: function(event, ui) {
			vulpe.util.removeHotKeys();
		},
		overlay: {
			backgroundColor: '#000',
			opacity: 0.5
		},
		buttons: {
			'<fmt:message key="label.vulpe.button.yes"/>': function() {
				$(this).dialog('close');
				if (vulpe.command) {
					vulpe.command();
					vulpe.util.checkHotKeys();
				}
			},
			'<fmt:message key="label.vulpe.button.no"/>': function() {
				$(this).dialog('close');
				vulpe.util.checkHotKeys();
			}
		}
	});
	$(vulpe.config.layers.warningDialog).dialog({
		title: vulpe.config.dialogs.warningTitle,
		autoOpen: false,
		bgiframe: true,
		resizable: false,
		minHeight: 50,
		modal: true,
		open: function(event, ui) {
			vulpe.util.removeHotKeys();
		},
		overlay: {
			backgroundColor: '#000',
			opacity: 0.5
		},
		buttons: {
			'<fmt:message key="label.vulpe.button.ok"/>': function() {
				$(this).dialog('close');
				if (vulpe.command) {
					vulpe.command();
					vulpe.util.checkHotKeys();
				}
			},
			'<fmt:message key="label.vulpe.button.cancel"/>': function() {
				$(this).dialog('close');
				vulpe.util.checkHotKeys();
			}
		}
	});
	var selectForm = $(vulpe.config.layers.selectForm);
	if (selectForm.length == 1 && vulpe.util.trim(selectForm.html()).length == 0) {
		selectForm.hide();
	}
	$('#dialog_link, ul#icons li').hover(
		function() { $(this).addClass('ui-state-hover'); },
		function() { $(this).removeClass('ui-state-hover'); }
	);
<c:choose>
	<c:when test="${now['requireOneFilter'] && now['controllerType'] == 'SELECT'}">vulpe.config.requireOneFilter = true;</c:when>
	<c:otherwise>vulpe.config.requireOneFilter = false;</c:otherwise>
</c:choose>
	<c:if test="${not empty now['focusToField']}">vulpe.util.getElementField("${now['focusToField']}").focus();</c:if>
	<c:if test="${!now['ajax']}">vulpe.view.checkRequiredFields();</c:if>
	vulpe.view.initTimerToSessionExpire();
	// vulpe map actions - init
	vulpe.config.actions = {
		submit : ${util:getMapJSON(pageContext, 'vulpeActions')},
		menu : ${util:getMapJSON(pageContext, 'vulpeMenuActions')},
		sort : ${util:getMapJSON(pageContext, 'vulpeSortActions')},
		control : ${util:getMapJSON(pageContext, 'vulpeControlActions')}
	}
	vulpe.config.controller.owner = '${now['controllerConfig'].ownerController}';
	vulpe.config.layers.layer = "${now['bodyTwice'] ? 'main' : 'body'}";
	vulpe.view.init();
	// vulpe map actions
});
</script>
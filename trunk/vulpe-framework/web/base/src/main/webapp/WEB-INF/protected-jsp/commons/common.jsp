<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="vulpeFormName" value="${controllerConfig.formName}" scope="request"/>
<c:if test="${vulpeBodyTwice}">
	<c:choose>
		<c:when test="${vulpeBodyTwiceType == 'MAIN'}"><c:set var="vulpeFormName" value="${controllerConfig.MainFormName}" scope="request"/></c:when>
		<c:when test="${vulpeBodyTwiceType == 'SELECT'}"><c:set var="vulpeFormName" value="${controllerConfig.selectFormName}" scope="request"/></c:when>
	</c:choose>
</c:if>
<c:if test="${empty targetName}">
	<c:choose>
	<c:when test="${empty targetConfig}"><c:set var="prepareName" value="${not empty vulpeTargetName ? vulpeTargetName : 'entity'}"/></c:when>
	<c:otherwise><c:set var="prepareName" value="${targetConfigPropertyName}"/></c:otherwise>
	</c:choose>
	<c:set var="prepareName" value="${fn:replace(prepareName, '[', '__')}"/>
	<c:set var="prepareName" value="${fn:replace(prepareName, '].', '__')}"/>
	<c:set var="prepareName" value="${fn:replace(prepareName, '.', '_')}"/>
</c:if>
<c:if test="${empty vulpeShowActions || !vulpeShowActions || vulpeBodySelect}">
<c:set var="vulpeShowActions" value="true" scope="request"/>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
	<c:if test="${onlyToSee}">vulpe.config.onlyToSee = true;</c:if>
	<c:if test="${global['project-view-focusFirst']}">vulpe.util.focusFirst("${now['controllerType'] == 'TABULAR' ? 'entities' : ''}");</c:if>
	<c:if test="${(global['project-view-frontendMenuType'] == 'DROPPY' && (now['controllerType'] == 'FRONTEND' || vulpeCurrentLayout == 'FRONTEND')) || (global['project-view-backendMenuType'] == 'DROPPY' && (now['controllerType'] == 'BACKEND' || vulpeCurrentLayout == 'BACKEND'))}">$("#nav").droppy();</c:if>
	<c:if test="${(global['project-view-frontendMenuType'] == 'SUPERFISH' && (now['controllerType'] == 'FRONTEND' || vulpeCurrentLayout == 'FRONTEND')) || (global['project-view-backendMenuType'] == 'SUPERFISH' && (now['controllerType'] == 'BACKEND' || vulpeCurrentLayout == 'BACKEND'))}">if (vulpe.config.browser.ie) { $("#nav").superfish().find("ul").bgIframe({opacity: false}); } else { $("#nav").superfish(); }</c:if>
	<c:if test="${pageContext.request.locale ne 'en_US'}">$.datepicker.setDefaults($.datepicker.regional['${pageContext.request.locale}']);</c:if>
	if (document.forms['${vulpeFormName}']) {
		<c:if test="${empty popupKey}">vulpe.config.formName = "${vulpeFormName}";</c:if>
		vulpe.config.logic.prepareName = "${prepareName}";
		<c:if test="${!ajax}">
		vulpe.util.removeHotKeys($(this));
		vulpe.util.checkHotKeys($(this));
		</c:if>
		vulpe.util.get('${vulpeFormName}-operation').each(function(){
			$(this).val('${operation}');
			$(this).attr('defaultValue', $(this).val());
		});
		vulpe.util.get('${vulpeFormName}-paging.page').each(function(){
			$(this).val('${paging.page}');
			$(this).attr('defaultValue', $(this).val());
		});
		vulpe.util.get('${vulpeFormName}-id').each(function(){
			$(this).val('${id}');
			$(this).attr('defaultValue', $(this).val());
		});
		vulpe.util.get('${vulpeFormName}-executed').each(function(){
			$(this).val('${executed}');
			$(this).attr('defaultValue', $(this).val());
		});
		<c:if test="${now['controllerType'] != 'FRONTEND' && now['controllerType'] != 'BACKEND'}">
		vulpe.util.get('${vulpeFormName}-entity_orderBy').each(function(){
			$(this).val('${entity.orderBy}');
			$(this).attr('defaultValue', $(this).val());
		});
		</c:if>
	}
	$("#alertDialog").dialog({
		autoOpen: false,
		bgiframe: true,
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
	$("#confirmationDialog").dialog({
		autoOpen: false,
		bgiframe: true,
		resizable: false,
		height: 140,
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
<c:choose>
	<c:when test="${now['requireOneFilter'] && now['controllerType'] == 'SELECT'}">vulpe.config.requireOneFilter = true;</c:when>
	<c:otherwise>vulpe.config.requireOneFilter = false;</c:otherwise>
</c:choose>
	<c:if test="${not empty now['fieldToFocus']}">vulpe.util.getElementField("${now['fieldToFocus']}").focus();</c:if>
	<c:if test="${!ajax}">vulpe.view.checkRequiredFields();</c:if>
});
</script>
</c:if>
<c:if test="${not empty vulpeShowMessages || !vulpeShowMessages}">
<c:set var="vulpeShowMessages" value="true" scope="request"/>
<%@include file="/WEB-INF/protected-jsp/commons/messages.jsp" %>
</c:if>
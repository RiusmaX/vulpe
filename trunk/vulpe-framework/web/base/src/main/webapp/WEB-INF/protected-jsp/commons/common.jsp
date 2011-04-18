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
	<c:if test="${global['focusFirst']}">vulpe.util.focusFirst("${now['controllerType'] == 'TABULAR' ? 'entities' : ''}");</c:if>
	<c:if test="${(global['frontendMenuType'] == 'DROPPY' && (now['controllerType'] == 'FRONTEND' || vulpeCurrentLayout == 'FRONTEND')) || (global['backendMenuType'] == 'DROPPY' && (now['controllerType'] == 'BACKEND' || vulpeCurrentLayout == 'BACKEND'))}">$("#nav").droppy();</c:if>
	<c:if test="${(global['frontendMenuType'] == 'SUPERFISH' && (now['controllerType'] == 'FRONTEND' || vulpeCurrentLayout == 'FRONTEND')) || (global['backendMenuType'] == 'SUPERFISH' && (now['controllerType'] == 'BACKEND' || vulpeCurrentLayout == 'BACKEND'))}">if (vulpe.config.browser.ie) { $("#nav").superfish().find("ul").bgIframe({opacity: false}); } else { $("#nav").superfish(); }</c:if>
	<c:if test="${pageContext.request.locale ne 'en_US'}">$.datepicker.setDefaults($.datepicker.regional['${pageContext.request.locale}']);</c:if>
	if (document.forms['${vulpeFormName}']) {
		<c:if test="${empty popupKey}">vulpe.config.formName = "${vulpeFormName}";</c:if>
		vulpe.config.logic.prepareName = "${prepareName}";
		vulpe.util.removeHotKeys(["return", "Ctrl+f7", "Ctrl+f8", "Ctrl+f9", "Ctrl+f10", "Ctrl+f12", "Ctrl+del", "Alt+f8", "Alt+Shift+del"]);
	<c:if test="${now['buttons']['create'] || now['buttons']['SELECT_create'] || now['buttons']['Main_create']}">
		var buttonCreate = vulpe.util.get("vulpeButtonCreate-${vulpeFormName}");
		if (buttonCreate.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Ctrl+f8",
				command: function() {
					buttonCreate.click();
					return false;
				},
				override: true
			});
		}
	</c:if>
	<c:if test="${now['buttons']['createPost'] || now['buttons']['SELECT_createPost'] || now['buttons']['Main_createPost']}">
		var buttonCreatePost = vulpe.util.get("vulpeButtonCreatePost-${vulpeFormName}");
		if (buttonCreatePost.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Ctrl+f10",
				command: function() {
					buttonCreatePost.click();
					return false;
				},
				putSameOnReturnKey: true,
				returnKeyDontFireInText: true,
				override: true
			});
		}
	</c:if>
	<c:if test="${now['buttons']['updatePost'] || now['buttons']['SELECT_updatePost'] || now['buttons']['Main_updatePost']}">
		var buttonUpdatePost = vulpe.util.get("vulpeButtonUpdatePost-${vulpeFormName}");
		if (buttonUpdatePost.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Ctrl+f10",
				command: function() {
					buttonUpdatePost.click();
					return false;
				},
				putSameOnReturnKey: true,
				returnKeyDontFireInText: true,
				override: true
			});
		}
	</c:if>
	<c:if test="${now['buttons']['tabularPost']}">
		var buttonTabularPost = vulpe.util.get("vulpeButtonTabularPost-${vulpeFormName}");
		if (buttonTabularPost.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Ctrl+f10",
				command: function() {
					buttonTabularPost.click();
					return false;
				},
				override: true
			});
		}
	</c:if>
	<c:if test="${now['buttons']['delete'] || now['buttons']['SELECT_delete'] || now['buttons']['Main_delete']}">
		var buttonDelete = vulpe.util.get("vulpeButtonDelete-${vulpeFormName}");
		if (buttonDelete.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Ctrl+del",
				command: function() {
					buttonDelete.click();
					return false;
				}
			});
		}
	</c:if>
	<c:if test="${now['buttons']['prepare'] || now['buttons']['SELECT_prepare'] || now['buttons']['Main_prepare']}">
		var buttonPrepare = vulpe.util.get("vulpeButtonPrepare-${vulpeFormName}");
		if (buttonPrepare.attr("onclick")) {
			<c:if test="${now['controllerType'] == 'MAIN'}">
			<c:set var="prepare" value="Ctrl+backspace"/>
			</c:if>
			<c:if test="${now['controllerType'] == 'SELECT'}">
			<c:set var="prepare" value="Alt+Shift+del"/>
			</c:if>
			vulpe.util.addHotKey({
				hotKey: "${prepare}",
				command: function() {
					buttonPrepare.click();
					return false;
				},
				override: true
			});
		}
	</c:if>
	<c:if test="${now['buttons']['tabularFilter']}">
		var buttonTabularFilter = vulpe.util.get("vulpeButtonTabularFilter-${vulpeFormName}");
		if (buttonTabularFilter.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Ctrl+f7",
				command: function() {
					buttonTabularFilter.click();
					return false;
				},
				putSameOnReturnKey: true,
				override: true
			});
		}
	</c:if>
	<c:if test="${now['buttons']['tabularReload']}">
		var buttonTabularReload = vulpe.util.get("vulpeButtonTabularReload-${vulpeFormName}");
		if (buttonTabularReload.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Ctrl+f9",
				command: function() {
					buttonTabularReload.click();
					return false;
				},
				override: true
			});
		}
	</c:if>
	<c:if test="${now['buttons']['read'] || now['buttons']['SELECT_read'] || now['buttons']['Main_read']}">
		var buttonRead = vulpe.util.get("vulpeButtonRead-${vulpeFormName}");
		if (buttonRead.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Ctrl+f9",
				command: function() {
					buttonRead.click();
					return false;
				},
				putSameOnReturnKey: true,
				override: true
			});
		}
	</c:if>
	<c:if test="${now['buttons']['report'] || now['buttons']['SELECT_report'] || now['buttons']['Main_report']}">
		var buttonReport = vulpe.util.get("vulpeButtonReport-${vulpeFormName}");
		if (buttonReport.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Ctrl+f12",
				command: function() {
					buttonReport.click();
					return false;
				}
			});
		}
	</c:if>
	<c:if test="${now['buttons']['clear'] || now['buttons']['SELECT_clear'] || now['buttons']['Main_clear']}">
		var buttonClear = vulpe.util.get("vulpeButtonClear-${vulpeFormName}");
		if (buttonClear.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Alt+Shift+del",
				command: function() {
					buttonClear.click();
					return false;
				}
			});
		}
	</c:if>
	<c:set var="buttonCreateTabularEL" value="${'${'}now['buttons']['addDetail${targetConfig.name}']${'}'}"/>
	<c:set var="buttonCreateTabular" value="${util:eval(pageContext, buttonCreateTabularEL)}"/>
	<c:if test="${buttonCreateTabular}">
		var buttonAddTabularLine = vulpe.util.get("vulpeButtonAddDetail-entities-${vulpeFormName}");
		if (buttonAddTabularLine.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Ctrl+f8",
				command: function() {
					buttonAddTabularLine.click();
					return false;
				},
				override: true
			});
		}
	</c:if>
	<c:if test="${!controllerConfig.simple}">
	<c:forEach items="${controllerConfig.details}" var="detail">
		<c:set var="buttonDetailEL" value="${'${'}now['buttons']['addDetail${detail.baseName}']${'}'}"/>
		<c:set var="buttonDetail" value="${util:eval(pageContext, buttonDetailEL)}"/>
		<c:if test="${buttonDetail}">
		var buttonAddDetail_${detail.baseName} = vulpe.util.get("vulpeButtonAddDetail-${detail.baseName}-${vulpeFormName}");
		if (buttonAddDetail_${detail.baseName}.attr("onclick")) {
			vulpe.util.addHotKey({
				hotKey: "Alt+f8",
				command: function() {
					buttonAddDetail_${detail.baseName}.click();
					return false;
				}
			});
		}
		</c:if>
	</c:forEach>
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
		buttons: {
			'<fmt:message key="label.vulpe.button.ok"/>': function() {
				$(this).dialog('close');
			}
		}
	});
	$("#confirmationDialog").dialog({
		autoOpen: false,
		bgiframe: true,
		resizable: false,
		height: 140,
		modal: true,
		overlay: {
			backgroundColor: '#000',
			opacity: 0.5
		},
		buttons: {
			'<fmt:message key="label.vulpe.button.ok"/>': function() {
				$(this).dialog('close');
				if (vulpe.command) {
					vulpe.command();
				}
			},
			'<fmt:message key="label.vulpe.button.cancel"/>': function() {
				$(this).dialog('close');
			}
		}
	});
<c:choose>
	<c:when test="${now['requireOneFilter'] && now['controllerType'] == 'SELECT'}">vulpe.config.requireOneFilter = true;</c:when>
	<c:otherwise>vulpe.config.requireOneFilter = false;</c:otherwise>
</c:choose>
	<c:if test="${not empty now['fieldToFocus']}">vulpe.util.getElementField("${now['fieldToFocus']}").focus();</c:if>
});
</script>
</c:if>
<c:if test="${not empty vulpeShowMessages || !vulpeShowMessages}">
<c:set var="vulpeShowMessages" value="true" scope="request"/>
<%@include file="/WEB-INF/protected-jsp/commons/messages.jsp" %>
</c:if>
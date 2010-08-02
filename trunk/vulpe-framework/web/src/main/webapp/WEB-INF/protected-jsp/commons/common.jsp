<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="vulpeFormName" value="${controllerConfig.formName}" scope="request"/>
<c:if test="${vulpeBodyTwice}">
	<c:choose>
	<c:when test="${vulpeBodyTwiceType == 'CRUD'}">
		<c:set var="vulpeFormName" value="${controllerConfig.CRUDFormName}" scope="request"/>
	</c:when>
	<c:when test="${vulpeBodyTwiceType == 'SELECT'}">
		<c:set var="vulpeFormName" value="${controllerConfig.selectFormName}" scope="request"/>
	</c:when>
	</c:choose>
</c:if>
<c:if test="${empty vulpeShowActions || !vulpeShowActions || vulpeBodySelect}">
<c:set var="vulpeShowActions" value="true" scope="request"/>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
	vulpe.util.focusFirst();
<c:if test="${(vulpeFrontendMenuType == 'DROPPY' && (controllerConfig.controllerType == 'FRONTEND' || vulpeCurrentLayout == 'FRONTEND')) || (vulpeBackendMenuType == 'DROPPY' && (controllerConfig.controllerType == 'BACKEND' || vulpeCurrentLayout == 'BACKEND'))}">
	$("#nav").droppy();
</c:if>
<c:if test="${pageContext.request.locale ne 'en_US'}">
	$.datepicker.setDefaults($.datepicker.regional['${pageContext.request.locale}']);
</c:if>
	if (document.forms['${vulpeFormName}']) {
	<c:if test="${createShow || SELECT_createShow || CRUD_createShow}">
		var buttonCreate = vulpe.util.get("vulpeButtonCreate_${vulpeFormName}");
		if (buttonCreate.attr("onclick")) {
			jQuery(document).bind('keydown', 'Ctrl+f8', function (){buttonCreate.click(); return false;});
		}
	</c:if>
	<c:if test="${createPostShow || SELECT_createPostShow || CRUD_createPostShow}">
		var buttonCreatePost = vulpe.util.get("vulpeButtonCreatePost_${vulpeFormName}");
		if (buttonCreatePost.attr("onclick")) {
			jQuery(document).bind('keydown', 'Ctrl+f10', function (){buttonCreatePost.click();});
		}
	</c:if>
	<c:if test="${updatePostShow || SELECT_updatePostShow || CRUD_updatePostShow}">
		var buttonUpdatePost = vulpe.util.get("vulpeButtonUpdatePost_${vulpeFormName}");
		if (buttonUpdatePost.attr("onclick")) {
			jQuery(document).bind('keydown', 'Ctrl+f10', function (){buttonUpdatePost.click();});
		}
	</c:if>
	<c:if test="${tabularPostShow}">
		var buttonTabularPost = vulpe.util.get("vulpeButtonTabularPost_${vulpeFormName}");
		if (buttonTabularPost.attr("onclick")) {
			jQuery(document).bind('keydown', 'Ctrl+f10', function (){buttonTabularPost.click();});
		}
	</c:if>
	<c:if test="${deleteShow || SELECT_deleteShow || CRUD_deleteShow}">
		var buttonDelete = vulpe.util.get("vulpeButtonDelete_${vulpeFormName}");
		if (buttonDelete.attr("onclick")) {
			jQuery(document).bind('keydown', 'Ctrl+del', function (){buttonDelete.click();});
		}
	</c:if>
	<c:if test="${prepareShow || SELECT_prepareShow || CRUD_prepareShow}">
		var buttonPrepare = vulpe.util.get("vulpeButtonPrepare_${vulpeFormName}");
		if (buttonPrepare.attr("onclick")) {
			<c:if test="${controllerConfig.controllerType == 'CRUD'}">
			<c:set var="prepare" value="Ctrl+backspace"/>
			</c:if>
			<c:if test="${controllerConfig.controllerType == 'SELECT'}">
			<c:set var="prepare" value="Shift+del"/>
			</c:if>
			jQuery(document).bind('keydown', '${prepare}', function (){buttonPrepare.click();});
		}
	</c:if>
	<c:if test="${tabularFilterShow}">
		var buttonTabularFilter = vulpe.util.get("vulpeButtonTabularFilter_${vulpeFormName}");
		if (buttonTabularFilter.attr("onclick")) {
			jQuery(document).bind('keydown', 'Ctrl+f7', function (){buttonTabularFilter.click();});
			jQuery(document).bind('keydown', 'return', function (){buttonTabularFilter.click(); return false;});
		}
	</c:if>
	<c:if test="${tabularReloadShow}">
		var buttonTabularReload = vulpe.util.get("vulpeButtonTabularReload_${vulpeFormName}");
		if (buttonTabularReload.attr("onclick")) {
			jQuery(document).bind('keydown', 'Ctrl+f9', function (){buttonTabularReload.click();});
		}
	</c:if>
	<c:if test="${readShow || SELECT_readShow || CRUD_readShow}">
		var buttonRead = vulpe.util.get("vulpeButtonRead_${vulpeFormName}");
		if (buttonRead.attr("onclick")) {
			jQuery(document).bind('keydown', 'Ctrl+f9', function (){buttonRead.click();});
			jQuery(document).bind('keydown', 'return', function (){buttonRead.click(); return false;});
		}
	</c:if>
	<c:if test="${clearShow || SELECT_clearShow || CRUD_clearShow}">
		var buttonClear = vulpe.util.get("vulpeButtonClear_${vulpeFormName}");
		if (buttonClear.attr("onclick")) {
			jQuery(document).bind('keydown', 'Shift+del', function (){buttonClear.click();});
		}
	</c:if>
	<c:set var="buttonCreate_tabular_EL" value="${'${'}addDetailShow${targetConfig.name}${'}'}"/>
	<c:set var="buttonCreate_tabular" value="${util:eval(pageContext, buttonCreate_tabular_EL)}"/>
	<c:if test="${buttonCreate_tabular}">
		var buttonAddDetail_entities = vulpe.util.get("vulpeButtonAddDetail_${vulpeFormName}_entities");
		if (buttonAddDetail_entities.attr("onclick")) {
			jQuery(document).bind('keydown', 'Ctrl+f8', function (){buttonAddDetail_entities.click();});
		}
	</c:if>
	<c:if test="${!controllerConfig.simple}">
	<c:forEach items="${controllerConfig.details}" var="detail">
		<c:set var="buttonDetailEL" value="${'${'}addDetailShow${detail.baseName}${'}'}"/>
		<c:set var="buttonDetail" value="${util:eval(pageContext, buttonDetailEL)}"/>
		<c:if test="${buttonDetail}">
		var buttonAddDetail_${detail.baseName} = vulpe.util.get("vulpeButtonAddDetail_${vulpeFormName}_${detail.baseName}");
		if (buttonAddDetail_${detail.baseName}.attr("onclick")) {
			jQuery(document).bind('keydown', 'Alt+f8', function (){buttonAddDetail_${detail.baseName}.click();});
		}
		</c:if>
	</c:forEach>
	</c:if>
		vulpe.util.get('${vulpeFormName}_operation').each(function(){
			$(this).val('${operation}');
			$(this).attr('defaultValue', $(this).val());
		});
		vulpe.util.get('${vulpeFormName}_paging.page').each(function(){
			$(this).val('${paging.page}');
			$(this).attr('defaultValue', $(this).val());
		});
		vulpe.util.get('${vulpeFormName}_id').each(function(){
			$(this).val('${id}');
			$(this).attr('defaultValue', $(this).val());
		});
		vulpe.util.get('${vulpeFormName}_executed').each(function(){
			$(this).val('${executed}');
			$(this).attr('defaultValue', $(this).val());
		});
		vulpe.util.get('${vulpeFormName}_entity.orderBy').each(function(){
			$(this).val('${entity.orderBy}');
			$(this).attr('defaultValue', $(this).val());
		});
	}
	$("#alertDialog").dialog({
		autoOpen: false,
		bgiframe: true,
		modal: true,
		buttons: {
			'<fmt:message key="vulpe.label.button.ok"/>': function() {
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
			'<fmt:message key="vulpe.label.button.ok"/>': function() {
				$(this).dialog('close');
				if (vulpe.command) {
					vulpe.command();
				}
			},
			'<fmt:message key="vulpe.label.button.cancel"/>': function() {
				$(this).dialog('close');
			}
		}
	});
	var columns = jQuery("th[id!='']", "#entities");
	for (var i = 0; i < columns.length; i++) {
		var column = columns[i];
		var order = vulpe.config.order[column.id];
		if (order) {
			vulpe.util.get(order.property).val(order.value);
			column.className = order.css;
		}
	}
});
</script>
</c:if>
<c:if test="${not empty vulpeShowMessages || !vulpeShowMessages}">
<c:set var="vulpeShowMessages" value="true" scope="request"/>
<%@include file="/WEB-INF/protected-jsp/commons/messages.jsp" %>
</c:if>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${empty vulpeShowActions || !vulpeShowActions}">
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

	if (document.forms['${controllerConfig.formName}']) {
	<c:if test="${createShow}">
		var buttonCreate = $("#vulpeButtonCreate_${controllerConfig.formName}");
		if (buttonCreate) {
			buttonCreate.${createShow ? 'show' : 'hide'}();
			jQuery(document).bind('keydown', 'Ctrl+f8', function (evt){buttonCreate.click(); return false;});
		}
	</c:if>

	<c:if test="${createPostShow}">
		var buttonCreatePost = $("#vulpeButtonCreatePost_${controllerConfig.formName}");
		if (buttonCreatePost) {
			buttonCreatePost.${createPostShow ? 'show' : 'hide'}();
			jQuery(document).bind('keydown', 'Ctrl+f10', function (){buttonCreatePost.click();});
		}
	</c:if>

	<c:if test="${updatePostShow}">
		var buttonUpdatePost = $("#vulpeButtonUpdatePost_${controllerConfig.formName}");
		if (buttonUpdatePost) {
			buttonUpdatePost.${updatePostShow ? 'show' : 'hide'}();
			jQuery(document).bind('keydown', 'Ctrl+f10', function (){buttonUpdatePost.click();});
		}
	</c:if>

	<c:if test="${tabularPostShow}">
		var buttonTabularPost = $("#vulpeButtonTabularPost_${controllerConfig.formName}");
		if (buttonTabularPost) {
			buttonTabularPost.${tabularPostShow ? 'show' : 'hide'}();
			jQuery(document).bind('keydown', 'Ctrl+f10', function (){buttonTabularPost.click();});
		}
	</c:if>

	<c:if test="${deleteShow}">
		var buttonDelete = $("#vulpeButtonDelete_${controllerConfig.formName}");
		if (buttonDelete) {
			buttonDelete.${deleteShow ? 'show' : 'hide'}();
			jQuery(document).bind('keydown', 'Ctrl+del', function (){buttonDelete.click();});
		}
	</c:if>

	<c:if test="${prepareShow}">
		var buttonPrepare = $("#vulpeButtonPrepare_${controllerConfig.formName}");
		if (buttonPrepare) {
			buttonPrepare.${prepareShow ? 'show' : 'hide'}();
			<c:if test="${controllerConfig.controllerType == 'CRUD'}">
			<c:set var="prepare" value="Ctrl+backspace"/>
			</c:if>
			<c:if test="${controllerConfig.controllerType == 'SELECT'}">
			<c:set var="prepare" value="Shift+del"/>
			</c:if>
			<c:if test="${controllerConfig.controllerType == 'TABULAR'}">
			<c:set var="prepare" value="Ctrl+f9"/>
			</c:if>
			jQuery(document).bind('keydown', '${prepare}', function (){buttonPrepare.click();});
		}
	</c:if>

	<c:if test="${readShow}">
		var buttonRead = $("#vulpeButtonRead_${controllerConfig.formName}");
		if (buttonRead) {
			buttonRead.${readShow ? 'show' : 'hide'}();
			jQuery(document).bind('keydown', 'Ctrl+f9', function (){buttonRead.click();});
		}
	</c:if>

	<c:if test="${clearShow}">
		var buttonClear = $("#vulpeButtonClear_${controllerConfig.formName}");
		if (buttonClear) {
			buttonClear.${clearShow ? 'show' : 'hide'}();
			jQuery(document).bind('keydown', 'Shift+del', function (){buttonClear.click();});
		}
	</c:if>

	<c:set var="buttonCreate_tabular_EL" value="${'${'}addDetailShow${targetConfig.name}${'}'}"/>
	<c:set var="buttonCreate_tabular" value="${util:eval(pageContext, buttonCreate_tabular_EL)}"/>
	<c:if test="${buttonCreate_tabular}">
		var buttonAddDetail_entities = $("#vulpeButtonAddDetail_${controllerConfig.formName}_entities");
		if (buttonAddDetail_entities) {
			buttonAddDetail_entities.${buttonCreate_tabular ? 'show' : 'hide'}();
			jQuery(document).bind('keydown', 'Ctrl+f8', function (){buttonAddDetail_entities.click();});
		}
	</c:if>

	<c:if test="${!controllerConfig.simple}">
	<c:forEach items="${controllerConfig.details}" var="detail">
		<c:set var="buttonDetailEL" value="${'${'}addDetailShow${detail.baseName}${'}'}"/>
		<c:set var="buttonDetail" value="${util:eval(pageContext, buttonDetailEL)}"/>
		<c:if test="${buttonDetail}">
		var buttonAddDetail_${detail.baseName} = $("#vulpeButtonAddDetail_${controllerConfig.formName}_${detail.baseName}");
		if (buttonAddDetail_${detail.baseName}) {
			buttonAddDetail_${detail.baseName}.${buttonDetail ? 'show' : 'hide'}();
			jQuery(document).bind('keydown', 'Alt+f8', function (){buttonAddDetail_${detail.baseName}.click();});
		}
		</c:if>
	</c:forEach>
	</c:if>

		vulpe.util.get('${controllerConfig.formName}_operation').each(function(){
			$(this).val('${operation}');
			$(this).attr('defaultValue', $(this).val());
		});

		vulpe.util.get('${controllerConfig.formName}_paging.page').each(function(){
			$(this).val('${paging.page}');
			$(this).attr('defaultValue', $(this).val());
		});

		vulpe.util.get('${controllerConfig.formName}_id').each(function(){
			$(this).val('${id}');
			$(this).attr('defaultValue', $(this).val());
		});

		vulpe.util.get('${controllerConfig.formName}_executed').each(function(){
			$(this).val('${executed}');
			$(this).attr('defaultValue', $(this).val());
		});

		vulpe.util.get('${controllerConfig.formName}_entity.orderBy').each(function(){
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
<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>
<c:if test="${empty vulpeShowActions || !vulpeShowActions}">
<c:set var="vulpeShowActions" value="true" scope="request"/>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		vulpe.util.focusFirst();
		<c:if test="${(vulpeFrontendMenuType == 'DROPPY' && actionConfig.type == 'FRONTEND') || (vulpeBackendMenuType == 'DROPPY' && actionConfig.type != 'FRONTEND')}">
		$("#nav").droppy();
		</c:if>
		<c:if test="${pageContext.request.locale ne 'en_US'}">
		$.datepicker.setDefaults($.datepicker.regional['${pageContext.request.locale}']);
		</c:if>

		_vulpeValidateMessages = {validate: "<fmt:message key='vulpe.error.validate'/>", required: "<fmt:message key='vulpe.error.required'/>"}

		if (document.forms['${actionConfig.formName}']) {
		<c:if test="${createShow}">
			var button_create = $("#button_create_${actionConfig.formName}");
			if (button_create) {
				button_create.${createShow ? 'show' : 'hide'}();
				jQuery(document).bind('keydown', 'Ctrl+insert', function (evt){button_create.click(); return false;});
			}
		</c:if>

		<c:if test="${createPostShow}">
			var button_create_post = $("#button_create_post_${actionConfig.formName}");
			if (button_create_post) {
				button_create_post.${createPostShow ? 'show' : 'hide'}();
				<c:if test="${createPostShow}">
				jQuery(document).bind('keydown', 'Ctrl+f10', function (){button_create_post.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${updatePostShow}">
			var button_update_post = $("#button_update_post_${actionConfig.formName}");
			if (button_update_post) {
				button_update_post.${updatePostShow ? 'show' : 'hide'}();
				<c:if test="${updatePostShow}">
				jQuery(document).bind('keydown', 'Ctrl+f10', function (){button_update_post.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${tabularPostShow}">
		var button_tabularPost = $("#button_tabularPost_${actionConfig.formName}");
		if (button_tabularPost) {
			button_tabularPost.${tabularPostShow ? 'show' : 'hide'}();
			<c:if test="${tabularPostShow}">
			jQuery(document).bind('keydown', 'Ctrl+f10', function (){button_tabularPost.click();});
			</c:if>
		}
		</c:if>

		<c:if test="${deleteShow}">
			var button_delete = $("#button_delete_${actionConfig.formName}");
			if (button_delete) {
				button_delete.${deleteShow ? 'show' : 'hide'}();
				<c:if test="${deleteShow}">
				jQuery(document).bind('keydown', 'Ctrl+del', function (){button_delete.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${prepareShow}">
			var button_prepare = $("#button_prepare_${actionConfig.formName}");
			if (button_prepare) {
				button_prepare.${prepareShow ? 'show' : 'hide'}();
				<c:if test="${prepareShow}">
				<c:if test="${actionConfig.type == 'CRUD'}">
				<c:set var="prepare" value="Ctrl+backspace"/>
				</c:if>
				<c:if test="${actionConfig.type == 'SELECT'}">
				<c:set var="prepare" value="Shift+del"/>
				</c:if>
				<c:if test="${actionConfig.type == 'TABULAR'}">
				<c:set var="prepare" value="Ctrl+f9"/>
				</c:if>
				jQuery(document).bind('keydown', '${prepare}', function (){button_prepare.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${readShow}">
			var button_read = $("#button_read_${actionConfig.formName}");
			if (button_read) {
				button_read.${readShow ? 'show' : 'hide'}();
				<c:if test="${readShow}">
				jQuery(document).bind('keydown', 'Ctrl+f9', function (){button_read.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${clearShow}">
			var button_clear = $("#button_clear_${actionConfig.formName}");
			if (button_clear) {
				button_clear.${clearShow ? 'show' : 'hide'}();
				<c:if test="${clearShow}">
				jQuery(document).bind('keydown', 'Shift+del', function (){button_clear.click();});
				</c:if>
			}
		</c:if>

		<c:set var="button_create_tabular_EL" value="${'${'}addDetailShow${targetConfig.name}${'}'}"/>
		<c:set var="button_create_tabular" value="${util:eval(pageContext, button_create_tabular_EL)}"/>
		<c:if test="${button_create_tabular}">
			var button_addDetail_entities = $("#button_addDetail_${actionConfig.formName}_entities");
			if (button_addDetail_entities) {
				button_addDetail_entities.${button_create_tabular ? 'show' : 'hide'}();
				<c:if test="${button_create_tabular}">
				jQuery(document).bind('keydown', 'Ctrl+insert', function (){button_addDetail_entities.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${!actionConfig.simple}">
		<c:forEach items="${actionConfig.details}" var="detail">
			<c:set var="btEL" value="${'${'}addDetailShow${detail.baseName}${'}'}"/>
			<c:set var="bt" value="${util:eval(pageContext, btEL)}"/>
			<c:if test="${bt}">
			var button_addDetail_${detail.baseName} = $("#button_addDetail_${actionConfig.formName}_${detail.baseName}");
			if (button_addDetail_${detail.baseName}) {
				button_addDetail_${detail.baseName}.${bt ? 'show' : 'hide'}();
				jQuery(document).bind('keydown', 'Alt+insert', function (){button_addDetail_${detail.baseName}.click();});
			}
			</c:if>
		</c:forEach>
		</c:if>

			vulpe.util.get('${actionConfig.formName}_operation').each(function(){
				$(this).val('${operation}');
				$(this).attr('defaultValue', $(this).val());
			});

			vulpe.util.get('${actionConfig.formName}_paging.page').each(function(){
				$(this).val('${paging.page}');
				$(this).attr('defaultValue', $(this).val());
			});

			vulpe.util.get('${actionConfig.formName}_id').each(function(){
				$(this).val('${id}');
				$(this).attr('defaultValue', $(this).val());
			});

			vulpe.util.get('${actionConfig.formName}_executed').each(function(){
				$(this).val('${executed}');
				$(this).attr('defaultValue', $(this).val());
			});

			vulpe.util.get('${actionConfig.formName}_entity.orderBy').each(function(){
				$(this).val('${entity.orderBy}');
				$(this).attr('defaultValue', $(this).val());
			});

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
		}
	});
</script>
</c:if>

<c:if test="${empty vulpeShowMessages || !vulpeShowMessages}">
	<c:set var="vulpeShowMessages" value="true" scope="request"/>
	<%@include file="/WEB-INF/protected-jsp/common/messages.jsp" %>
</c:if>
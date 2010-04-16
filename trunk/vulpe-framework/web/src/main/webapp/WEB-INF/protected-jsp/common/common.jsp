<%
// sets encoding
if (request.getAttribute("ENCODING_SETED") == null){
	request.setCharacterEncoding("utf-8");
	request.setAttribute("ENCODING_SETED", Boolean.TRUE);
}
%>

<%-- import taglibs --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v" %>
<%@ taglib uri="/WEB-INF/util" prefix="util" %>
<fmt:setBundle basename="${initParam['project.bundle']}"/>

<c:if test="${empty PRINT_BUTTONS || !PRINT_BUTTONS}">
<c:set var="PRINT_BUTTONS" value="true" scope="request"/>
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
		<c:if test="${not empty createShow}">
			var button_create_${actionConfig.formName} = $("#button_create_${actionConfig.formName}");
			if (button_create_${actionConfig.formName}) {
				button_create_${actionConfig.formName}.${createShow ? 'show' : 'hide'}();
				<c:if test="${createShow}">
				jQuery(document).bind('keydown', 'Ctrl+insert', function (){button_create_${actionConfig.formName}.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${not empty createPostShow}">
			var button_create_post_${actionConfig.formName} = $("#button_create_post_${actionConfig.formName}");
			if (button_create_post_${actionConfig.formName}) {
				button_create_post_${actionConfig.formName}.${createPostShow ? 'show' : 'hide'}();
				<c:if test="${createPostShow}">
				jQuery(document).bind('keydown', 'Ctrl+f10', function (){button_create_post_${actionConfig.formName}.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${not empty updatePostShow}">
			var button_update_post_${actionConfig.formName} = $("#button_update_post_${actionConfig.formName}");
			if (button_update_post_${actionConfig.formName}) {
				button_update_post_${actionConfig.formName}.${updatePostShow ? 'show' : 'hide'}();
				<c:if test="${updatePostShow}">
				jQuery(document).bind('keydown', 'Ctrl+f10', function (){button_update_post_${actionConfig.formName}.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${not empty tabularPostShow}">
		var button_tabularPost_${actionConfig.formName} = $("#button_tabularPost_${actionConfig.formName}");
		if (button_tabularPost_${actionConfig.formName}) {
			button_tabularPost_${actionConfig.formName}.${tabularPostShow ? 'show' : 'hide'}();
			<c:if test="${tabularPostShow}">
			jQuery(document).bind('keydown', 'Ctrl+f10', function (){button_tabularPost_${actionConfig.formName}.click();});
			</c:if>
		}
		</c:if>

		<c:if test="${not empty deleteShow}">
			var button_delete_${actionConfig.formName} = $("#button_delete_${actionConfig.formName}");
			if (button_delete_${actionConfig.formName}) {
				button_delete_${actionConfig.formName}.${deleteShow ? 'show' : 'hide'}();
				<c:if test="${deleteShow}">
				jQuery(document).bind('keydown', 'Ctrl+del', function (){button_delete_${actionConfig.formName}.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${not empty prepareShow}">
			var button_prepare_${actionConfig.formName} = $("#button_prepare_${actionConfig.formName}");
			if (button_prepare_${actionConfig.formName}) {
				button_prepare_${actionConfig.formName}.${prepareShow ? 'show' : 'hide'}();
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
				jQuery(document).bind('keydown', '${prepare}', function (){button_prepare_${actionConfig.formName}.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${not empty readShow}">
			var button_read_${actionConfig.formName} = $("#button_read_${actionConfig.formName}");
			if (button_read_${actionConfig.formName}) {
				button_read_${actionConfig.formName}.${readShow ? 'show' : 'hide'}();
				<c:if test="${readShow}">
				jQuery(document).bind('keydown', 'Ctrl+f9', function (){button_read_${actionConfig.formName}.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${not empty clearShow}">
			var button_clear_${actionConfig.formName} = $("#button_clear_${actionConfig.formName}");
			if (button_clear_${actionConfig.formName}) {
				button_clear_${actionConfig.formName}.${clearShow ? 'show' : 'hide'}();
				<c:if test="${clearShow}">
				jQuery(document).bind('keydown', 'Shift+del', function (){button_clear_${actionConfig.formName}.click();});
				</c:if>
			}
		</c:if>

		<c:set var="button_create_tabular_EL" value="${'${'}addDetailShow${targetConfig.name}${'}'}"/>
		<c:set var="button_create_tabular" value="${util:eval(pageContext, button_create_tabular_EL)}"/>
		<c:if test="${button_create_tabular}">
			var button_addDetail_${actionConfig.formName}_entities = $("#button_addDetail_${actionConfig.formName}_entities");
			if (button_addDetail_${actionConfig.formName}_entities) {
				button_addDetail_${actionConfig.formName}_entities.${button_create_tabular ? 'show' : 'hide'}();
				<c:if test="${button_create_tabular}">
				jQuery(document).bind('keydown', 'Ctrl+insert', function (){button_addDetail_${actionConfig.formName}_entities.click();});
				</c:if>
			}
		</c:if>

		<c:if test="${!actionConfig.simple}">
		<c:forEach items="${actionConfig.details}" var="detail">
			<c:set var="btEL" value="${'${'}addDetailShow${detail.baseName}${'}'}"/>
			<c:set var="bt" value="${util:eval(pageContext, btEL)}"/>
			<c:if test="${not empty bt}">
			var button_addDetail_${actionConfig.formName}_${detail.baseName} = $("#button_addDetail_${actionConfig.formName}_${detail.baseName}");
			if (button_addDetail_${actionConfig.formName}_${detail.baseName}) {
				button_addDetail_${actionConfig.formName}_${detail.baseName}.${bt ? 'show' : 'hide'}();
				<c:if test="${bt}">
				jQuery(document).bind('keydown', 'Alt+insert', function (){button_addDetail_${actionConfig.formName}_${detail.baseName}.click();});
				</c:if>
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

<c:if test="${empty PRINT_MSG || !PRINT_MSG}">
	<c:set var="PRINT_MSG" value="true" scope="request"/>
	<%@include file="/WEB-INF/protected-jsp/common/messages.jsp" %>
</c:if>

<c:choose>
<c:when test="${empty TABINDEX}">
	<c:set var="TABINDEX" scope="request" value="0"/>
</c:when>
<c:otherwise>
	<c:set var="TABINDEX" scope="request" value="${TABINDEX+1}"/>
</c:otherwise>
</c:choose>
<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>

<div id="${actionConfig.formName}_select">
	<div id="${actionConfig.formName}_select_actions" class="actions">
		<%@include file="/WEB-INF/protected-jsp/common/selectActions.jsp" %>
	</div>
	<p class="selectForm">
		<div id="${actionConfig.formName}_select_form">
			<jsp:include page="${actionConfig.viewPath}" />
		</div>
	</p>

	<p class="selectTable">
		<div id="${actionConfig.formName}_select_table">
			<jsp:include page="${actionConfig.viewItemsPath}" />
			<c:if test="${actionConfig.type == 'REPORT' && not empty downloadInfo}">
				<v:hidden name="downloadInfo" value="${downloadInfo}" saveInSession="true" expireInSession="true" render="false"/>
				<script type="text/javascript">
					$(document).ready(function() {
						vulpe.view.request.submitReport('${util:linkKey('downloadInfo', '', '')}', 800, 600);
					});
				</script>
			</c:if>
 		</div>
	</p>

	<p class="selectFooter">
		<div id="${actionConfig.formName}_select_footer">
		</div>
	</p>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		_vulpeLogicPrepareName = "entity";
		var formIndex = vulpe.util.getVulpeValidateForms("${actionConfig.formName}");
		var vulpeValidateAttributes = new Array();
		<c:forEach var="validate" items="${entity.attributesToValidateInSelect}" varStatus="status">vulpeValidateAttributes[vulpeValidateAttributes.length] = {name: "${validate.name}", label: "<fmt:message key='${validate.label}'/>", identifier: "${validate.identifier}", description: "${validate.description}", type: "${validate.type}", size:"${validate.size}"};</c:forEach>
		_vulpeValidateForms[formIndex] = {name: "${actionConfig.formName}", attributes: vulpeValidateAttributes};
	});
</script>
<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>

<div id="${actionConfig.formName}_report">
	<div id="${actionConfig.formName}_report_actions" class="actions">
		<%@include file="/WEB-INF/protected-jsp/common/reportActions.jsp" %>
	</div>
	<p class="selectForm">
	<div id="${actionConfig.formName}_report_body">
		<jsp:include page="${actionConfig.viewPath}" />
	</div>
	</p>
	<p class="selectTable">
	<div id="${actionConfig.formName}_report_table">
		<jsp:include page="${actionConfig.viewItemsPath}" />
		<c:if test="${not empty downloadInfo}">
			<v:hidden name="downloadInfo" value="${downloadInfo}" saveInSession="true" expireInSession="true" render="false"/>
			<script type="text/javascript">
				$(document).ready(function() {
					vulpe.view.request.submitReport('${util:linkKey('downloadInfo', '', '')}', 800, 600);
				});
			</script>
		</c:if>
	</div>
	</p>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		var formIndex = vulpe.util.getVulpeValidateForms("${actionConfig.formName}");
		var vulpeValidateAttributes = new Array();
		_vulpeValidateForms[formIndex] = {name: "${actionConfig.formName}", attributes: vulpeValidateAttributes};
	});
</script>
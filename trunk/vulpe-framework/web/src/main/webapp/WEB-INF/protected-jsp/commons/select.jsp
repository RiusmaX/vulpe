<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<div id="${actionConfig.formName}_select">
	<div id="${actionConfig.formName}_select_actions" class="actions">
		<%@include file="/WEB-INF/protected-jsp/commons/selectActions.jsp" %>
	</div>
	<div id="${actionConfig.formName}_select_form">
		<p class="selectForm">
			<jsp:include page="${actionConfig.viewPath}" />
		</p>
	</div>

	<div id="${actionConfig.formName}_select_table">
		<p class="selectTable">
			<jsp:include page="${actionConfig.viewItemsPath}" />
			<c:if test="${actionConfig.type == 'REPORT' && not empty downloadInfo}">
				<v:hidden name="downloadInfo" value="${downloadInfo}" saveInSession="true" expireInSession="true" render="false"/>
				<script type="text/javascript">
					$(document).ready(function() {
						vulpe.view.request.submitReport('${util:linkKey('downloadInfo', '', '')}', 800, 600);
					});
				</script>
			</c:if>
		</p>
	</div>

	<p class="selectFooter">
		<div id="${actionConfig.formName}_select_footer">
		</div>
	</p>
</div>
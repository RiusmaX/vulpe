<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${vulpeBodyTwice}">
<fieldset>
<legend><fmt:message>${fn:replace(controllerConfig.titleKey,'crud_select', 'select')}</fmt:message></legend>
</c:if>
<div id="vulpeSelect">
	<div id="vulpeSelectActions" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/selectActions.jsp" %>
	</div>
	<div id="vulpeSelectForm">
		<p class="vulpeSelectForm">
			<jsp:include page="${controllerConfig.controllerType == 'CRUD_SELECT' ? controllerConfig.viewSelectPath : controllerConfig.viewPath}" />
		</p>
	</div>

	<div id="vulpeSelectTable_${vulpeFormName}">
		<p class="vulpeSelectTable">
			<jsp:include page="${controllerConfig.controllerType == 'CRUD_SELECT' ? controllerConfig.viewSelectItemsPath : controllerConfig.viewItemsPath}" />
			<c:if test="${controllerConfig.controllerType == 'REPORT' && not empty downloadInfo}">
				<v:hidden name="downloadInfo" value="${downloadInfo}" saveInSession="true" expireInSession="true" render="false"/>
				<script type="text/javascript">
					$(document).ready(function() {
						vulpe.view.request.submitReport('${util:linkKey('downloadInfo', '', '')}', 800, 600);
					});
				</script>
			</c:if>
		</p>
	</div>

	<div id="vulpeSelectFooter">
		<p class="vulpeSelectFooter">
		</p>
	</div>
</div>
<c:if test="${vulpeBodyTwice}">
</fieldset>
</c:if>
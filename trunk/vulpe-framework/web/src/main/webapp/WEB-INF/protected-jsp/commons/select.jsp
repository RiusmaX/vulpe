<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="vulpeTargetName" value="entitySelect" scope="request"/>
<c:if test="${vulpeBodyTwice}">
<c:set var="vulpeBodySelect" value="${true}" scope="request"/>
<fieldset>
<legend><fmt:message>${fn:replace(now['titleKey'], '.twice', '.select')}</fmt:message></legend>
</c:if>
<div id="vulpeSelect">
	<div id="vulpeSelectActions" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/selectActions.jsp" %>
	</div>
	<div id="vulpeSelectForm">
		<jsp:include page="${now['controllerType'] == 'TWICE' ? controllerConfig.viewSelectPath : controllerConfig.viewPath}" />
	</div>
	<div id="vulpeSelectTable_${vulpeFormName}">
		<c:remove var="vulpeBodySelect" scope="request"/>
		<jsp:include page="${now['controllerType'] == 'TWICE' ? controllerConfig.viewSelectItemsPath : controllerConfig.viewItemsPath}" />
		<c:if test="${now['controllerType'] == 'REPORT' && not empty downloadInfo}">
			<v:hidden name="downloadInfo" value="${downloadInfo}" saveInSession="true" expireInSession="true" render="false"/>
			<script type="text/javascript">
				$(document).ready(function() {
					vulpe.view.request.submitReport('${util:linkKey('downloadInfo', '', '')}', 800, 600);
				});
			</script>
		</c:if>
	</div>
	<div id="vulpeSelectFooter"></div>
</div>
<c:if test="${vulpeBodyTwice}">
</fieldset>
</c:if>
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<c:if test="${not empty param.reportFormat}"><jsp:include page="${now['controllerConfig'].viewItemsPath}" /></c:if>
<c:if test="${not empty downloadInfo}">
	<v:hidden name="downloadInfo" value="${downloadInfo}" saveInSession="true" expireInSession="true"
		render="false" />
	<div id="report" title="<fmt:message key="${now['reportTitleKey']}" />" style="display:none">
		<iframe id="reportFrame" width="100%" height="98%" border="0"/>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			vulpe.view.request.submitReport('${util:linkKey('downloadInfo', '', '')}', 800, 600);
		});
	</script>
</c:if>
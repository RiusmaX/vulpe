<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%--<jsp:include page="${controllerConfig.viewItemsPath}" />--%>
<c:if test="${not empty downloadInfo}">
	<p><fmt:message key="vulpe.message.report.generated.successfully" /></p>
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
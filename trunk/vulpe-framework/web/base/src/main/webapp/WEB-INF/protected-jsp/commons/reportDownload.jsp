<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<p><fmt:message key="vulpe.msg.report.generated.successfully" /></p>
<%--<jsp:include page="${controllerConfig.viewItemsPath}" />--%>
<c:if test="${not empty downloadInfo}">
	<v:hidden name="downloadInfo" value="${downloadInfo}" saveInSession="true" expireInSession="true"
		render="false" />
	<script type="text/javascript">
		$(document).ready(function() {
			vulpe.view.request.submitReport('${util:linkKey('downloadInfo', '', '')}', 800, 600);
		});
	</script>
</c:if>
<c:if test="${empty downloadInfo}">
	<p><fmt:message key="vulpe.msg.empty.list" /></p>
</c:if>
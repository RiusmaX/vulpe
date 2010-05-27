<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<div id="${actionConfig.formName}_backend">
	<div id="${actionConfig.formName}_backend_actions" class="actions">
		<%--@include file="/WEB-INF/protected-jsp/commons/backendActions.jsp" --%>
	</div>

	<div id="${actionConfig.formName}_backend_body">
		<jsp:include page="${actionConfig.viewPath}" />
	</div>

	<div id="${actionConfig.formName}_backend_footer">
	<c:if test="${not empty backend_footer}">
	</c:if>
	</div>
</div>
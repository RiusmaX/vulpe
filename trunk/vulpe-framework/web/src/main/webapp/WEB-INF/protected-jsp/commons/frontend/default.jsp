<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<div id="${actionConfig.formName}_frontend">
	<div id="${actionConfig.formName}_frontend_actions" class="actions">
		<%@include file="/WEB-INF/protected-jsp/commons/frontend/defaultActions.jsp" %>
	</div>

	<div id="${actionConfig.formName}_frontend_body">
		<jsp:include page="${actionConfig.viewPath}" />
	</div>

	<div id="${actionConfig.formName}_frontend_footer">
	<c:if test="${not empty frontend_footer}">
	</c:if>
	</div>
</div>
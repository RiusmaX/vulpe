<%@include file="/WEB-INF/protected-jsp/common/common.jsp" %>

<div id="${actionConfig.formName}_crud">
	<div id="${actionConfig.formName}_crud_actions" class="actions">
		<%@include file="/WEB-INF/protected-jsp/common/crudActions.jsp" %>
	</div>

	<c:if test="${actionConfig.detailsInTabs eq true && not empty actionConfig.details && fn:length(actionConfig.details) > 0}">
		<div id="${actionConfig.formName}_crud_body_tabs">
		<ul>
			<li><a href="#${actionConfig.formName}_crud_body"><fmt:message key="vulpe.label.main"/></a></li>
			<c:forEach items="${actionConfig.details}" var="detail">
				<c:if test="${empty detail.parentDetailConfig}">
					<li><a href="#${actionConfig.formName}_${detail.baseName}_detail"><fmt:message key="${detail.titleKey}"/></a></li>
				</c:if>
			</c:forEach>
			<c:if test="${not empty crud_footer}">
				<li><a href="#${actionConfig.formName}_crud_footer"><fmt:message key="vulpe.label.completion"/></a></li>
			</c:if>
		</ul>
	</c:if>

	<div id="${actionConfig.formName}_crud_body">
		<jsp:include page="${actionConfig.viewPath}" />
	</div>

	<c:if test="${not empty actionConfig.details && fn:length(actionConfig.details) > 0}">
		<c:forEach items="${actionConfig.details}" var="detail">
			<c:if test="${empty detail.parentDetailConfig}">
				<c:set var="targetConfig" value="${detail}" scope="request"/>
				<c:set var="targetConfigPropertyName" value="${detail.propertyName}" scope="request"/>

				<jsp:include page="/WEB-INF/protected-jsp/common/detail.jsp">
					<jsp:param name="detail_viewPath" value="${detail.viewPath}"/>
				</jsp:include>

				<c:set var="targetConfig" value="${null}" scope="request"/>
				<c:set var="targetConfigPropertyName" value="${null}" scope="request"/>
			</c:if>
		</c:forEach>
	</c:if>

	<div id="${actionConfig.formName}_crud_footer">
	</div>

	<c:if test="${actionConfig.detailsInTabs eq true && not empty actionConfig.details && fn:length(actionConfig.details) > 0}">
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				var tabsName = '#${actionConfig.formName}_crud_body_tabs';
				$(tabsName).tabs({
				    show: function(event, ui) {
				    	var selected = $(tabsName).tabs('option', 'selected');
				        vulpe.util.selectTab("${actionConfig.formName}", selected);
				        return true;
			    	}
				});
				var selectedTab = "${selectedTab}";
				if (selectedTab && selectedTab > 0) {
					$(tabsName).tabs('option', 'selected', selectedTab);
				}
			});
		</script>
	</c:if>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		_vulpeLogicPrepareName = "entity";
		var formIndex = vulpe.util.getVulpeValidateForms("${actionConfig.formName}");
		var vulpeValidateAttributes = new Array();
		<c:forEach var="validate" items="${entity.attributesToValidateInCRUD}" varStatus="status">vulpeValidateAttributes[vulpeValidateAttributes.length] = {name: "${validate.name}", label: "<fmt:message key='${validate.label}'/>", identifier: "${validate.identifier}", description: "${validate.description}", type: "${validate.type}", size:"${validate.size}"};</c:forEach>
		_vulpeValidateForms[formIndex] = {name: "${actionConfig.formName}", attributes: vulpeValidateAttributes};
	});
</script>
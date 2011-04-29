<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${vulpeBodyTwice}">
<fieldset>
<legend><fmt:message>${fn:replace(now['titleKey'], '.twice', '.main')}</fmt:message></legend>
</c:if>
<div id="vulpeMain">
	<div id="vulpeMainActions" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/mainActions.jsp" %>
	</div>
	<c:if test="${controllerConfig.showInTabs && not empty controllerConfig.details && fn:length(controllerConfig.details) > 0}">
		<div id="vulpeMainBodyTabs">
		<fmt:message key="label.vulpe.tabs.navigation" var="tabNavigationTitle"/>
		<ul title="${tabNavigationTitle}">
			<c:set var="tabTitle"><fmt:message key="${now['masterTitleKey']}"/></c:set>
			<c:if test="${not empty tabs || not empty tabs[now['masterTitleKey']]}"><c:set var="tabTitle" value="${tabs[now['masterTitleKey']].title}"/></c:if>
			<li title="${tabTitle}"><a id="vulpeMainBodyTabs0" href="#vulpeMainBody">${tabTitle}</a></li>
			<c:forEach items="${controllerConfig.details}" var="detail" varStatus="status">
				<c:if test="${empty detail.parentDetailConfig && !detail.notControlView}">
					<c:set var="tabTitle"><fmt:message key="${detail.titleKey}"/></c:set>
					<c:if test="${not empty tabs || not empty tabs[detail.titleKey]}"><c:set var="tabTitle" value="${tabs[detail.titleKey].title}"/></c:if>
					<li title="${tabTitle}"><a id="vulpeMainBodyTabs${status.count}" href="#vulpeDetail-${detail.baseName}">${tabTitle}</a></li>
				</c:if>
			</c:forEach>
			<c:if test="${not empty vulpeMainFooter}"><li><a href="#vulpeMainFooter"><fmt:message key="label.vulpe.completion"/></a></li></c:if>
		</ul>
	</c:if>
	<div id="vulpeMainBody">
		<c:remove var="targetConfig" scope="request"/>
		<c:remove var="targetConfigPropertyName" scope="request"/>
		<jsp:include page="${now['controllerType'] == 'TWICE' ? controllerConfig.viewMainPath : controllerConfig.viewPath}" />
	</div>
	<c:if test="${not empty controllerConfig.details && fn:length(controllerConfig.details) > 0}">
		<c:forEach items="${controllerConfig.details}" var="detail">
			<c:if test="${empty detail.parentDetailConfig && !detail.notControlView}">
				<c:set var="targetConfig" value="${detail}" scope="request"/>
				<c:set var="targetConfigPropertyName" value="${detail.propertyName}" scope="request"/>
				<jsp:include page="/WEB-INF/protected-jsp/commons/detail.jsp">
					<jsp:param name="detailViewPath" value="${detail.viewPath}"/>
				</jsp:include>
				<c:remove var="targetConfig" scope="request"/>
				<c:remove var="targetConfigPropertyName" scope="request"/>
			</c:if>
		</c:forEach>
	</c:if>
	<div id="vulpeMainFooter"></div>
	<c:if test="${controllerConfig.showInTabs && not empty controllerConfig.details && fn:length(controllerConfig.details) > 0}">
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				var tabsName = '#vulpeMainBodyTabs';
				$(tabsName).tabs({
				    show: function(event, ui) {
				    	var selected = ui.panel.id;
				        vulpe.util.selectTab("${vulpeFormName}", selected);
				        vulpe.util.focusFirst(selected);
				        return true;
			    	}
				});
				var selectedTab = "${selectedTab}";
				if (selectedTab && selectedTab != "") {
					$(tabsName).tabs("select", "#" + selectedTab);
				}
				vulpe.config.tabsCount = "${fn:length(controllerConfig.details)}";
			});
		</script>
	</c:if>
</div>
<c:if test="${vulpeBodyTwice}">
</fieldset>
</c:if>
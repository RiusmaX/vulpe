<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${vulpeBodyTwice}">
<fieldset>
<legend><fmt:message>${fn:replace(now['titleKey'], '.twice', '.crud')}</fmt:message></legend>
</c:if>
<div id="vulpeCRUD">
	<div id="vulpeCRUDActions" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/crudActions.jsp" %>
	</div>
	<c:if test="${controllerConfig.detailsInTabs && not empty controllerConfig.details && fn:length(controllerConfig.details) > 0}">
		<div id="vulpeCRUDBodyTabs">
		<fmt:message key="label.vulpe.tabs.navigation" var="tabNavigationTitle"/>
		<ul title="${tabNavigationTitle}">
			<c:set var="tabTitle"><fmt:message key="${now['masterTitleKey']}"/></c:set>
			<c:if test="${not empty tabs || not empty tabs[now['masterTitleKey']]}"><c:set var="tabTitle" value="${tabs[now['masterTitleKey']].title}"/></c:if>
			<li title="${tabTitle}"><a id="vulpeCRUDBodyTabs0" href="#vulpeCRUDBody">${tabTitle}</a></li>
			<c:forEach items="${controllerConfig.details}" var="detail" varStatus="status">
				<c:if test="${empty detail.parentDetailConfig}">
					<c:set var="tabTitle"><fmt:message key="${detail.titleKey}"/></c:set>
					<c:if test="${not empty tabs || not empty tabs[detail.titleKey]}"><c:set var="tabTitle" value="${tabs[detail.titleKey].title}"/></c:if>
					<li title="${tabTitle}"><a id="vulpeCRUDBodyTabs${status.count}" href="#vulpeDetail-${detail.baseName}">${tabTitle}</a></li>
				</c:if>
			</c:forEach>
			<c:if test="${not empty vulpeCRUDFooter}"><li><a href="#vulpeCRUDFooter"><fmt:message key="label.vulpe.completion"/></a></li></c:if>
		</ul>
	</c:if>
	<div id="vulpeCRUDBody">
		<c:remove var="targetConfig" scope="request"/>
		<c:remove var="targetConfigPropertyName" scope="request"/>
		<jsp:include page="${now['controllerType'] == 'TWICE' ? controllerConfig.viewCRUDPath : controllerConfig.viewPath}" />
	</div>
	<c:if test="${not empty controllerConfig.details && fn:length(controllerConfig.details) > 0}">
		<c:forEach items="${controllerConfig.details}" var="detail">
			<c:if test="${empty detail.parentDetailConfig}">
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
	<div id="vulpeCRUDFooter"></div>
	<c:if test="${controllerConfig.detailsInTabs && not empty controllerConfig.details && fn:length(controllerConfig.details) > 0}">
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				var tabsName = '#vulpeCRUDBodyTabs';
				$(tabsName).tabs({
				    show: function(event, ui) {
				    	var selected = ui.panel.id;
				        vulpe.util.selectTab("${vulpeFormName}", selected);
				        return true;
			    	}
				});
				var selectedTab = "${selectedTab}";
				if (selectedTab && selectedTab != "") {
					$(tabsName).tabs("select", "#" + selectedTab);
				}
				var tabs = "${fn:length(controllerConfig.details)}";
				var tabIndex = 0;
				var count = 0;
				var tabControl = function(index) {
					if (index == -1) {
						if (tabIndex == 0) {
							tabIndex = tabs;
						} else {
							tabIndex = tabIndex - 1;
						}
					} else {
						if (tabIndex == tabs) {
							tabIndex = 0;
						} else {
							tabIndex = tabIndex + 1;
						}
					}
					var parent = jQuery("#vulpeCRUDBodyTabs" + tabIndex).attr("href");
					jQuery("#vulpeCRUDBodyTabs" + tabIndex).click();
					vulpe.util.focusFirst(parent);
					count = 1;
				}
				vulpe.util.addHotKey({
					hotKey: "left",
					command: function () {
						tabControl(-1);
    	                return false;
					},
					dontFireInText: true
                });
				vulpe.util.addHotKey({
					hotKey: "Alt+Ctrl+left",
					command: function () {
						tabControl(-1);
    	                return false;
					}
                });
				vulpe.util.addHotKey({
					hotKey: "right",
					command: function () {
						tabControl(1);
    	                return false;
					},
					dontFireInText: true
                });
				vulpe.util.addHotKey({
					hotKey: "Alt+Ctrl+right",
					command: function () {
                    	tabControl(1);
                    	return false;
					}
                });
			});
		</script>
	</c:if>
</div>
<c:if test="${vulpeBodyTwice}">
</fieldset>
</c:if>
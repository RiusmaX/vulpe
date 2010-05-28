<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<div id="vulpeCRUD">
	<div id="vulpeCRUDActions" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/crudActions.jsp" %>
	</div>

	<c:if test="${actionConfig.detailsInTabs eq true && not empty actionConfig.details && fn:length(actionConfig.details) > 0}">
		<div id="vulpeCRUDBodyTabs">
		<ul>
			<li><a id="vulpeCRUDBodyTabs0" href="#vulpeCRUDBody"><fmt:message key="vulpe.label.main"/></a></li>
			<c:forEach items="${actionConfig.details}" var="detail" varStatus="status">
				<c:if test="${empty detail.parentDetailConfig}">
					<li><a id="vulpeCRUDBodyTabs${status.count}" href="#vulpeDetail_${detail.baseName}"><fmt:message key="${detail.titleKey}"/></a></li>
				</c:if>
			</c:forEach>
			<c:if test="${not empty vulpeCRUDFooter}">
				<li><a href="#vulpeCRUDFooter"><fmt:message key="vulpe.label.completion"/></a></li>
			</c:if>
		</ul>
	</c:if>

	<div id="vulpeCRUDBody">
		<jsp:include page="${actionConfig.viewPath}" />
	</div>

	<c:if test="${not empty actionConfig.details && fn:length(actionConfig.details) > 0}">
		<c:forEach items="${actionConfig.details}" var="detail">
			<c:if test="${empty detail.parentDetailConfig}">
				<c:set var="targetConfig" value="${detail}" scope="request"/>
				<c:set var="targetConfigPropertyName" value="${detail.propertyName}" scope="request"/>

				<jsp:include page="/WEB-INF/protected-jsp/commons/detail.jsp">
					<jsp:param name="detailViewPath" value="${detail.viewPath}"/>
				</jsp:include>

				<c:set var="targetConfig" value="${null}" scope="request"/>
				<c:set var="targetConfigPropertyName" value="${null}" scope="request"/>
			</c:if>
		</c:forEach>
	</c:if>

	<div id="vulpeCRUDFooter">
	</div>

	<c:if test="${actionConfig.detailsInTabs eq true && not empty actionConfig.details && fn:length(actionConfig.details) > 0}">
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				var tabsName = '#vulpeCRUDBodyTabs';
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
				var tabs = "${fn:length(actionConfig.details)}";
				var tabIndex = 0;
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
				}
                jQuery(document).bind('keydown', {combi: 'left', disableInInput: true}, function (evt){
                    tabControl(-1); 
                    return false; 
                });
				jQuery(document).bind('keydown', 'Ctrl+left', function (evt){
                    tabControl(-1); 
                    return false; 
                });
                jQuery(document).bind('keydown', {combi: 'right', disableInInput: true}, function (evt){
                    tabControl(1); 
                    return false;
                });
				jQuery(document).bind('keydown', 'Ctrl+right', function (evt){
                    tabControl(1); 
                    return false;
                });
			});
		</script>
	</c:if>
</div>
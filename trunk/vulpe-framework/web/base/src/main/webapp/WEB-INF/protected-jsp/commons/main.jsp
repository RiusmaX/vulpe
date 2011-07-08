<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:if test="${now['bodyTwice']}">
<fieldset>
<legend><fmt:message>${fn:replace(now['titleKey'], '.twice', '.main')}</fmt:message></legend>
</c:if>
<div id="vulpeMain">
	<div id="vulpeMainActions" class="vulpeActions">
		<%@include file="/WEB-INF/protected-jsp/commons/mainActions.jsp" %>
	</div>
	<c:if test="${now['controllerConfig'].showInTabs && not empty now['controllerConfig'].details && fn:length(now['controllerConfig'].details) > 0}">
		<div id="vulpeMainBodyTabs">
		<ul>
			<c:set var="tabTitle"><fmt:message key="${now['masterTitleKey']}"/></c:set>
			<c:if test="${not empty tabs || not empty tabs[now['masterTitleKey']]}"><c:set var="tabTitle" value="${tabs[now['masterTitleKey']].title}"/></c:if>
			<li title="${tabTitle}"><a id="vulpeMainBodyTabs0" href="#vulpeMainBody">${tabTitle}</a></li>
			<c:forEach items="${now['controllerConfig'].details}" var="detail" varStatus="status">
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
		<c:remove var="now['targetConfig']" scope="request"/>
		<c:remove var="now['now['targetConfig']PropertyName']" scope="request"/>
		<jsp:include page="${now['controllerType'] == 'TWICE' ? now['controllerConfig'].viewMainPath : now['controllerConfig'].viewPath}" />
	</div>
	<c:if test="${not empty now['controllerConfig'].details && fn:length(now['controllerConfig'].details) > 0}">
		<c:forEach items="${now['controllerConfig'].details}" var="detail">
			<c:if test="${empty detail.parentDetailConfig && !detail.notControlView}">
				<c:set var="now['targetConfig']" value="${detail}" scope="request"/>
				<c:set var="now['now['targetConfig']PropertyName']" value="${detail.propertyName}" scope="request"/>
				<jsp:include page="/WEB-INF/protected-jsp/commons/detail.jsp">
					<jsp:param name="detailViewPath" value="${detail.viewPath}"/>
				</jsp:include>
				<c:remove var="now['targetConfig']" scope="request"/>
				<c:remove var="now['now['targetConfig']PropertyName']" scope="request"/>
			</c:if>
		</c:forEach>
	</c:if>
	<div id="vulpeMainFooter"></div>
	<c:if test="${now['controllerConfig'].showInTabs && not empty now['controllerConfig'].details && fn:length(now['controllerConfig'].details) > 0}">
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				$(vulpe.config.prefix.detailTab).tabs({
				    show: function(event, ui) {
				    	var selected = ui.panel.id;
				        vulpe.util.focusFirst(selected);
				        vulpe.util.checkDetailHotKeys(selected);
				        return true;
			    	},
			    	select: function(event, ui) {
				    	var selected = ui.panel.id;
				        vulpe.util.selectTab(selected);
				        return true;
			    	}
				});
				vulpe.config.tabsCount = "${fn:length(now['controllerConfig'].details)}";
			});
		</script>
	</c:if>
</div>
<c:if test="${now['bodyTwice']}">
</fieldset>
</c:if>
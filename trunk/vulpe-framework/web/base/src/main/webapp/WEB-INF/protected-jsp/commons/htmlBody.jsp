<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<div id="loading" style="display: none;"></div>
<c:if test="${global['project-view-layout-showSliderPanel']}"><%@include file="/WEB-INF/protected-jsp/commons/sliderPanel.jsp" %></c:if>
<div id="container">
	<div id="modalMessages" style="display: none;" class="vulpeMessages"></div>
	<div id="confirmationDialog" style="display: none">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<span id="confirmationMessage"></span>
		</p>
	</div>
	<div id="warningDialog" style="display: none">
		<p>
			<span class="ui-icon ui-icon-notice" style="float:left; margin:0 7px 20px 0;"></span>
			<span id="warningMessage"></span>
		</p>
	</div>
	<div id="alertDialog" style="display: none;">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>
			<span id="alertMessage"></span>
		</p>
	</div>
	<div id="successDialog" style="display: none;">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
			<span id="successMessage"></span>
		</p>
	</div>
	<div id="informationDialog" style="display: none;">
		<p>
			<span class="ui-icon ui-icon-info" style="float:left; margin:0 7px 50px 0;"></span>
			<span id="informationMessage"></span>
		</p>
	</div>
	<c:if test="${(global['project-view-useBackendLayer'] && ever['vulpeCurrentLayout'] == 'BACKEND') || (global['project-view-useFrontendLayer'] && ever['vulpeCurrentLayout'] == 'FRONTEND')}"><div id="${ever['vulpeCurrentLayout'] == 'FRONTEND' ? 'frontend' : 'backend'}"></c:if>
		<div id="header">
			<%@include file="/WEB-INF/protected-jsp/commons/header.jsp" %>
		</div>
		<div id="menu">
			<ul id="nav" class="${((global['project-view-frontendMenuType'] == 'SUPERFISH' && ever['vulpeCurrentLayout'] == 'FRONTEND') || (global['project-view-backendMenuType'] == 'SUPERFISH' && ever['vulpeCurrentLayout'] == 'BACKEND')) ? 'sf-menu' : ''}">
				<%@include file="/WEB-INF/protected-jsp/commons/menu.jsp" %>
				<c:if test="${global['project-audit'] && ever['vulpeCurrentLayout'] == 'BACKEND'}">
					<%@include file="/WEB-INF/protected-jsp/commons/audit/menu.jsp" %>
				</c:if>
				<c:if test="${global['project-security'] && ever['vulpeCurrentLayout'] == 'BACKEND'}">
					<%@include file="/WEB-INF/protected-jsp/commons/security/menu.jsp" %>
				</c:if>
				<li style="display:none"/>
			</ul>
		</div>
		<div id="messages" style="display: none;" class="vulpeMessages"></div>
		<div id="body">
			<decorator:body/>
		</div>
	<c:if test="${(global['project-view-useBackendLayer'] && ever['vulpeCurrentLayout'] == 'BACKEND') || (global['project-view-useFrontendLayer'] && ever['vulpeCurrentLayout'] == 'FRONTEND')}"></div></c:if>
	<div id="footer">
		<%@include file="/WEB-INF/protected-jsp/commons/footer.jsp" %>
	</div>
</div>
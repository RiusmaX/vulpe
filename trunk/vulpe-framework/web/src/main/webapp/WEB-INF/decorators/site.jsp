<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="${initParam['project.bundle']}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate, post-check=0, pre-check=0"/>
		<c:if test="${vulpeShowAsMobile}">
		<meta name="viewport" content="width=${viewportWidth}, height=${viewportHeight}, user-scalable=${viewportUserScalable}, initial-scale=${viewportInitialScale}, maximum-scale=${viewportMaximumScale}, minimum-scale=${viewportMinimumScale}" />
		</c:if>
		<title><fmt:message key="vulpe.title.application"/></title>
		<link type="image/x-icon" href="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icon.png" rel="shortcut icon"/>
		<script src="${pageContext.request.contextPath}/js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.accordion.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>
		<c:if test="${vulpeBackendMenuType == 'DROPPY'}">
		<script src="${pageContext.request.contextPath}/js/jquery.droppy.js" type="text/javascript" charset="utf-8"></script>
		</c:if>
		<script src="${pageContext.request.contextPath}/js/jquery.form.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.hotkeys.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.lightbox.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.maskedinput.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.simplemodal.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.rte.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.ui.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.ui.datepicker.i18n.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.validation.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/bodyoverlay.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/vulpe.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/application.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/themes/${vulpeTheme}/js/${vulpeTheme}.js" type="text/javascript" charset="utf-8"></script>
		<!--[if IE]>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/WCH.js" charset="utf-8"></script>
		<![endif]-->
		<%@include file="/WEB-INF/protected-jsp/common/javascript.jsp" %>
		<script type="text/javascript" charset="utf-8">
			var contextPath = '${pageContext.request.contextPath}';
			var _vulpeTheme = '${vulpeTheme}';
			var _vulpeMsgSelectedExclusion = '<fmt:message key="vulpe.msg.confirm.selected.exclusion"/>';
			var _vulpeMsgExclusion = '<fmt:message key="vulpe.msg.confirm.exclusion"/>';
			var _vulpeMsgSelectRecordsExclusion = '<fmt:message key="vulpe.msg.select.records.exclusion"/>';
			var _vulpeMsgUpload = '<fmt:message key="vulpe.error.upload"/>';
			var lightboxImageText = '<fmt:message key="vulpe.lightbox.image.text"/>';
			var lightboxOfText = '<fmt:message key="vulpe.lightbox.of.text"/>';
			<c:if test="${vulpeShowAsMobile}">
			_vulpePopupMobile = true;
			</c:if>
			var _vulpeMsgFieldRequired = '<fmt:message key="vulpe.js.error.required"/>';
			var _vulpeMsgKeyRequired = '<fmt:message key="vulpe.js.error.key.required"/>';
		</script>
		<style media="all" type="text/css">
			@import "${pageContext.request.contextPath}/css/vulpe.css";
			@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/${vulpeTheme}.css";
			@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/jquery.accordion.css";
			<c:if test="${vulpeBackendMenuType == 'DROPPY'}">
			@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/jquery.droppy.css";
			</c:if>
			@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/jquery.lightbox.css";
			@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/jquery.simplemodal.css";
			@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/jquery.rte.css";
			@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/jquery.ui.css";
			<!--[if lt IE 7]>
			@import "${pageContext.request.contextPath}/themes/${vulpeTheme}/css/jquery.simplemodal_ie.css?media=screen";
			<![endif]-->
		</style>
		<decorator:head/>
		<%@include file="/WEB-INF/protected-jsp/common/common.jsp" %>
	</head>
	<body>
		<c:if test="${vulpeBackendCenteredLayout}">
		<center>
		</c:if>
		<div id="container">
			<div id="loading" style="display: none;"></div>
			<div id="modalMessages" style="display: none;" class="messages"></div>
			<div id="confirmationDialog" title="<fmt:message key='vulpe.dialog.confirmation.title'/>" style="display: none">
				<p>
					<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
					<span id="confirmationMessage"></span>
				</p>
			</div>
			<div id="alertDialog" title="<fmt:message key='vulpe.dialog.warning.title'/>" style="display: none;">
				<p>
					<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>
					<span id="alertMessage"></span>
				</p>
			</div>
			<div id="successDialog" title="<fmt:message key='vulpe.dialog.success.title'/>" style="display: none;">
				<p>
					<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
					<span id="successMessage"></span>
				</p>
			</div>
			<div id="header">
				<%@include file="/WEB-INF/protected-jsp/common/header.jsp" %>
				<div id="menu">
					<ul id="nav">
						<%@include file="/WEB-INF/protected-jsp/common/menu.jsp" %>
						<c:if test="${vulpeAuditEnabled}">
							<%@include file="/WEB-INF/protected-jsp/common/audit/menu.jsp" %>
						</c:if>
						<c:if test="${vulpeSecurityEnabled}">
							<%@include file="/WEB-INF/protected-jsp/common/security/menu.jsp" %>
						</c:if>
					</ul>
				</div>
			</div>
			<div id="messages" style="display: none;" class="messages"></div>
			<div id="body">
				<decorator:body/>
			</div>
			<div id="footer">
				<%@include file="/WEB-INF/protected-jsp/common/footer.jsp" %>
			</div>
		</div>
		<c:if test="${vulpeBackendCenteredLayout}">
		</center>
		</c:if>
	</body>
</html>
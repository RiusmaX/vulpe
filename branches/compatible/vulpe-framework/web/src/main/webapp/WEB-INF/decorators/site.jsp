<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<fmt:setBundle basename="${vulpeI18nManager}"/>
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
		<c:set var="vulpeCurrentLayout" value="BACKEND" scope="session"/>
		<%@include file="/WEB-INF/protected-jsp/commons/javascript.jsp" %>
		<%@include file="/WEB-INF/protected-jsp/commons/css.jsp" %>
		<decorator:head/>
	</head>
	<body>
		<div id="container" align="${vulpeBackendCenteredLayout ? 'center' : ''}">
			<div id="loading" style="display: none;"></div>
			<div id="modalMessages" style="display: none;" class="vulpeMessages"></div>
			<div id="confirmationDialog" title="<fmt:message key='vulpe.dialog.confirmation.title'/>" style="display: none">
				<p>
					<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
					<span id="confirmationMessage"></span>
				</p>
			</div>
			<div id="alertDialog" title="<fmt:message key='vulpe.dialog.warning.title'/>" style="display: none;">
				<p>
					<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>
					<span id="vulpeAlertMessage"></span>
				</p>
			</div>
			<div id="successDialog" title="<fmt:message key='vulpe.dialog.success.title'/>" style="display: none;">
				<p>
					<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
					<span id="successMessage"></span>
				</p>
			</div>
			<div id="header">
				<%@include file="/WEB-INF/protected-jsp/commons/header.jsp" %>
				<div id="menu">
					<ul id="nav">
						<%@include file="/WEB-INF/protected-jsp/commons/menu.jsp" %>
						<c:if test="${vulpeAuditEnabled}">
							<%@include file="/WEB-INF/protected-jsp/commons/audit/menu.jsp" %>
						</c:if>
						<c:if test="${vulpeSecurityEnabled}">
							<%@include file="/WEB-INF/protected-jsp/commons/security/menu.jsp" %>
						</c:if>
					</ul>
				</div>
			</div>
			<div id="messages" style="display: none;" class="vulpeMessages"></div>
			<div id="body">
				<decorator:body/>
			</div>
			<div id="footer">
				<%@include file="/WEB-INF/protected-jsp/commons/footer.jsp" %>
			</div>
		</div>
	</body>
</html>
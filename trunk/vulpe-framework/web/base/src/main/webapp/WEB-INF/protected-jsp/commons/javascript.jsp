<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="noCache" value="?no-cache=${now['no-cache']}"/>
<script src="${pageContext.request.contextPath}/js/bodyoverlay.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.charcounter.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.cookie.js" type="text/javascript" charset="utf-8"></script>
<!--[if IE]><script src="${pageContext.request.contextPath}/js/jquery.bgiframe.js" type="text/javascript" charset="utf-8"></script><![endif]-->
<c:if test="${(ever['vulpeCurrentLayout'] == 'FRONTEND' && global['application-view-frontendMenuType'] == 'DROPPY') || (ever['vulpeCurrentLayout'] == 'BACKEND' && global['application-view-backendMenuType'] == 'DROPPY')}"><script src="${pageContext.request.contextPath}/js/jquery.droppy.js" type="text/javascript" charset="utf-8"></script></c:if>
<c:if test="${(ever['vulpeCurrentLayout'] == 'FRONTEND' && global['application-view-frontendMenuType'] == 'SUPERFISH') || (ever['vulpeCurrentLayout'] == 'BACKEND' && global['application-view-backendMenuType'] == 'SUPERFISH')}">
<script src="${pageContext.request.contextPath}/js/jquery.hover.intent.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.superfish.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.supersubs.js" type="text/javascript" charset="utf-8"></script>
</c:if>
<script src="${pageContext.request.contextPath}/js/jquery.form.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.growl.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.hotkeys.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.idle.timer.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.lightbox.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.maskedinput.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.simplemodal.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.rte.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.rte.toolbar.js" type="text/javascript" charset="utf-8"></script>
<%-- <script src="${pageContext.request.contextPath}/js/jquery.tools.js" type="text/javascript" charset="utf-8"></script> --%>
<script src="${pageContext.request.contextPath}/js/jquery.ui.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.datepicker.i18n.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/jquery.validation.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.browser.detect.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.webtoolkit.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/vulpe.js" type="text/javascript" charset="utf-8"></script>
<c:if test="${(ever['vulpeCurrentLayout'] == 'FRONTEND' && global['application-view-frontendMenuType'] == 'VULPE') || (ever['vulpeCurrentLayout'] == 'BACKEND' && global['application-view-backendMenuType'] == 'VULPE')}"><script src="${pageContext.request.contextPath}/js/vulpe.menu.js" type="text/javascript" charset="utf-8"></script></c:if>
<script src="${pageContext.request.contextPath}/js/application.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/themes/${global['application-theme']}/js/frontend/${global['application-theme']}.js" type="text/javascript" charset="utf-8"></script>
<%@include file="/WEB-INF/protected-jsp/commons/javascriptExtended.jsp"%>
<%@include file="/WEB-INF/protected-jsp/commons/javascriptConfig.jsp"%>
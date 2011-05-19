<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<c:if test="${global['project-view-showCopyright']}">
<div id="copyright">${currentYear} &copy; <fmt:message key="vulpe.copyright" /><br/>
</div>
</c:if>
<c:if test="${global['project-view-showPoweredBy']}">
<div id="poweredby"><fmt:message key="vulpe.poweredby" /></div>
</c:if>
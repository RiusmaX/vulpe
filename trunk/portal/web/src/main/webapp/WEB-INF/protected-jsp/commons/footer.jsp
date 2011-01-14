<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<center>
<c:if test="${global['showCopyright']}">
<fmt:message key="vulpe.copyright" var="copyright" />
<div id="copyright">${currentYear} &copy; ${not empty ever['vulpePortal'] ? ever['vulpePortal'].copyright : copyright}<br/>
</div>
</c:if>
<c:if test="${global['showPoweredBy']}">
<div id="poweredby"><fmt:message key="vulpe.poweredby" /></div>
</c:if>
</center>
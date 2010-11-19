<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<center>
<c:if test="${global['showCopyright']}">
<div id="copyright">${currentYear} &copy; <fmt:message key="vulpe.copyright" /><br>
</div>
</c:if>
<c:if test="${global['showPoweredBy']}">
<div id="poweredby"><fmt:message key="vulpe.poweredby" /></div>
</c:if>
</center>
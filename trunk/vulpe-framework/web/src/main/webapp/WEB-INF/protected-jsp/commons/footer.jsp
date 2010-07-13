<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<center>
<c:if test="${vulpeShowCopyright}">
<div id="copyright">${currentYear} &copy; <fmt:message key="vulpe.copyright" /><br>
</div>
</c:if>
<c:if test="${vulpeShowPoweredBy}">
<div id="poweredby"><fmt:message key="vulpe.poweredby" /></div>
</c:if>
</center>
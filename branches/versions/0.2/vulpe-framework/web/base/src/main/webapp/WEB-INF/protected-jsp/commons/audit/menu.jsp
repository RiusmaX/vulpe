<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:menu elementId="Audit" labelKey="label.vulpe.menu.Audit">
	<v:menu elementId="Occurrence" labelKey="label.vulpe.menu.Audit.Occurrence" action="/audit/Occurrence/select"/>
</v:menu>
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="5"><fmt:message key="label.transfere.core.Agenda.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="5"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>

<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="8"><fmt:message key="label.transfere.core.Sistema.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column
				labelKey="label.transfere.core.Sistema.select.nome"
				property="nome" sort="true"
			/>
			<v:column
				labelKey="label.transfere.core.Sistema.select.sigla"
				property="sigla" sort="true"
			/>
			<v:column
				labelKey="label.transfere.core.Sistema.select.gerencia"
				property="gerencia"
			/>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="8"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>

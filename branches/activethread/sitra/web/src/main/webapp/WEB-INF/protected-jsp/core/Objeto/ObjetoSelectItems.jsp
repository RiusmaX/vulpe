<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="7"><fmt:message key="label.sitra.core.Objeto.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row showUpdateButton="true" updateActionName="${fn:startsWith(currentItem.descricao, 'ATUALIZA') ? '' : 'core/ObjetoPublicacao/update'}">
			<v:column>
				<table cellpadding="0" cellspacing="0" id="objetos" style="width: 100%; margin: 0">
					<tr>
						<td><v:label key="label.sitra.core.Objeto.select.descricao" /></td>
						<td width="180"><v:label key="label.sitra.core.Objeto.select.destinoTransferencia" /></td>
						<td width="100"><v:label key="label.sitra.core.Objeto.select.usuario" /></td>
						<td width="50"><v:label key="label.sitra.core.Objeto.select.data" /></td>
					</tr>
					<tr>
						<td><v:show property="descricao"/></td>
						<td><v:show type="enum" property="origem"/> -> <v:show type="enum" property="destino"/></td>
						<td><v:show property="usuario"/></td>
						<td><v:show type="date" pattern="dd/MM/yyyy" property="data"/></td>
					</tr>
					<c:if test="${not empty currentItem.objetoItens}">
					<tr style="background-color: #C3C5DB">
						<td colspan="4" align="center">
						<c:choose>
						<c:when test="${fn:length(currentItem.objetoItens) > 1}">
						<b><a href="javascript:void(0);"  onclick="vulpe.view.showHideElement('itens${currentStatus.index}');">Objetos</a></b>
						</c:when>
						<c:otherwise>
						<b>Objeto</b>
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr style="background-color: #D8D9F2">
						<td colspan="4">
							<c:set var="style" value="display: none; overflow:auto; width: 100%; height:70px"/>
							<div id="itens${currentStatus.index}" style="${fn:length(currentItem.objetoItens) > 1 ? style : ''}">
							<table cellpadding="0" cellspacing="0" style="width: 99%; margin-left: 5px" id="itens">
								<tr>
									<td width="100"><v:label key="label.sitra.core.Objeto.select.objetoItens.tipoObjeto" /></td>
									<td><v:label key="label.sitra.core.Objeto.select.objetoItens.nomeObjeto" /></td>
									<td width="100"><v:label key="label.sitra.core.Objeto.select.objetoItens.status" /></td>
								</tr>
							<c:choose>
							<c:when test="${fn:length(currentItem.objetoItens) > 1}">
								<c:forEach items="${currentItem.objetoItens}" var="objeto" varStatus="objetoStatus">
								<tr class="${(objetoStatus.index % 2) == 0 ? 'vulpeLineOn' : 'vulpeLineOff'}">
									<td><v:show type="enum" targetValue="${objeto}" property="tipoObjeto"/></td>
									<td><v:show targetValue="${objeto}" property="nomeObjeto"/></td>
									<td><v:show type="enum" targetValue="${objeto}" property="status"/></td>
								</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td><v:show type="enum" targetValue="${currentItem.objetoItens[0]}" property="tipoObjeto"/></td>
									<td><v:show property="objetoItens[0].nomeObjeto"/></td>
									<td><v:show type="enum" targetValue="${currentItem.objetoItens[0]}" property="status"/></td>
								</tr>
							</c:otherwise>
							</c:choose>
							</table>
							</div>
						</td>
					</tr>
					</c:if>
				</table>
			</v:column>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="7"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>

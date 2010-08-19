<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.sitra.core.Objeto.crud.objetoItens.tipoObjeto">
				<c:choose>
				<c:when test="${empty now['publicacao']}">
				<v:select property="tipoObjeto" headerKey="label.sitra.todos"
					showBlank="true" autoLoad="false" onchange="app.all.showExecute(this.value, '${currentStatus.count}');app.all.carregarNomesObjetos(this.value, '${currentStatus.count}', '${vulpeFormName}', '/core/Objeto/objetos/ajax', 'objetos${currentStatus.count}', true)" style="width: 120px;">
					<img id="tipoObjetoExecute${currentStatus.count}" src="${pageContext.request.contextPath}/themes/${global['theme']}/images/icons/button-execute-16x16.png" style="cursor: pointer; ${not empty currentItem.tipoObjeto ? 'display:inline' : 'display:none'}" onclick="app.all.carregarNomesObjetos($('[id$=objetoItens:${currentStatus.index}:tipoObjeto]').val(), '${currentStatus.count}', '${vulpeFormName}', '/core/Objeto/objetos/ajax', 'objetos${currentStatus.count}', false)" title="Executar novamente"/>
				</v:select>
				</c:when>
				<c:otherwise>
				<v:select property="tipoObjeto" showBlank="true" autoLoad="false" style="width: 120px;" />
				</c:otherwise>
				</c:choose>
			</v:column>
			<v:column labelKey="label.sitra.core.Objeto.crud.objetoItens.nomeObjeto" style="${empty now['publicacao'] ? 'width: 270px;' : ''}">
				<c:choose>
				<c:when test="${empty now['publicacao']}">
				<div id="objetos${currentStatus.count}">
				<v:hidden property="nomeObjeto"/>
				<v:show property="nomeObjeto"/>
				<c:if test="${empty currentItem.nomeObjeto}">Selecione um Tipo Objeto.</c:if>
				</div>
				</c:when>
				<c:otherwise>
				<v:text property="nomeObjeto" upperCase="true" size="60" />
				</c:otherwise>
				</c:choose>
			</v:column>
			<c:if test="${empty now['publicacao']}">
			<v:column labelKey="label.sitra.core.Objeto.crud.objetoItens.somenteDesatualizado">
				<v:checkbox
					property="somenteDesatualizado"
					fieldValue="true"
				/>
			</v:column>
			</c:if>
			<v:column labelKey="label.sitra.core.Objeto.crud.objetoItens.status">
				<v:hidden property="status" />
				<v:show type="enum" property="status"/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>

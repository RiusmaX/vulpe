<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.sitra.core.Objeto.crud.objetoItens.tipoObjeto">
				<c:choose>
				<c:when test="${empty now['publicacao']}">
				<v:select property="tipoObjeto" headerKey="label.sitra.todos"
					showBlank="true" autoLoad="false" onchange="app.all.showExecute(this.value, '${currentStatus.index}');app.all.carregarNomesObjetos(this.value, '${currentStatus.index}', '${vulpeFormName}', '/core/Objeto/objetos/ajax', 'objetos${currentStatus.index}', true)" style="width: 120px;">
					<img id="tipoObjetoExecute${currentStatus.index}" src="${pageContext.request.contextPath}/themes/${global['theme']}/images/icons/button-execute-16x16.png" style="cursor: pointer; ${not empty currentItem.tipoObjeto ? 'display:inline' : 'display:none'}" onclick="$('[id$=objetoItens:${currentStatus.index}:tipoObjeto]').change()" title="Executar novamente"/>
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
						<div id="objetos${currentStatus.index}">
						<c:choose>
							<c:when test="${not empty currentItem.nomeObjeto}">
								<v:hidden property="nomeObjeto"/>
								<v:show property="nomeObjeto"/>
							</c:when>
							<c:otherwise>
								<span id="loading${currentStatus.index}">Selecione um Tipo Objeto.</span>
							</c:otherwise>
						</c:choose>
						</div>
					</c:when>
					<c:otherwise>
						<v:text property="nomeObjeto" upperCase="true" size="60" />
					</c:otherwise>
				</c:choose>
			</v:column>
			<v:column labelKey="label.sitra.core.Objeto.crud.objetoItens.somenteDesatualizado" show="${empty now['publicacao']}">
				<v:checkbox
					property="somenteDesatualizado"
					fieldValue="true"
				/>
			</v:column>
			<v:column labelKey="label.sitra.core.Objeto.crud.objetoItens.status">
				<v:hidden property="status" />
				<v:show type="enum" property="status"/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>

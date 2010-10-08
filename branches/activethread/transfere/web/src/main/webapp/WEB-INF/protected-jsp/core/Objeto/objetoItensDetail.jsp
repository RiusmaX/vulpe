<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.transfere.core.Objeto.crud.objetoItens.tipoObjeto">
				<v:select property="tipoObjeto" headerKey="${empty now['publicacao'] ? 'label.transfere.todos' : ''}"
					showBlank="true" autoLoad="false" onchange="app.all.showExecute(this.value, '${currentStatus.index}');app.all.carregarNomesObjetos(this.value, '${currentStatus.index}', '${vulpeFormName}', '/core/Objeto${empty now['publicacao'] ? '' : 'Publicacao'}/objetos/ajax', 'objetos${currentStatus.index}', true)" style="width: 120px;">
					<c:if test="${!onlyToSee}">
					<img id="tipoObjetoExecute${currentStatus.index}" src="${pageContext.request.contextPath}/themes/${global['theme']}/images/icons/button-execute-16x16.png" style="cursor: pointer; ${not empty currentItem.tipoObjeto ? 'display:inline' : 'display:none'}" onclick="$('[id$=objetoItens__${currentStatus.index}__tipoObjeto]').change()" title="Executar novamente"/>
					</c:if>
				</v:select>
			</v:column>
			<v:column labelKey="label.transfere.core.Objeto.crud.objetoItens.nomeObjeto" style="${empty now['publicacao'] ? 'width: 270px;' : ''}">
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
			</v:column>
			<v:column labelKey="label.transfere.core.Objeto.crud.objetoItens.somenteDesatualizado" show="${empty now['publicacao']}">
				<v:checkbox
					property="somenteDesatualizado"
					fieldValue="true"
				/>
			</v:column>
			<v:column labelKey="label.transfere.core.Objeto.crud.objetoItens.status">
				<v:hidden property="status" />
				<v:show type="enum" property="status"/>
			</v:column>
			<v:column labelKey="label.transfere.core.Objeto.crud.objetoItens.acoes">
				<div id="logProcessamento${currentStatus.count}" style="display:none" title="Log de Processamento"><c:out value="${currentItem.textoLogProcessamento}" escapeXml="false"/></div>
				<div id="script${currentStatus.count}" style="display:none" title="Script"><c:out value="${currentItem.textoScript}" escapeXml="false"/></div>
				<div id="scriptAnterior${currentStatus.count}" style="display:none" title="Script Anterior"><c:out value="${currentItem.textoScriptAnterior}" escapeXml="false"/></div>
				<v:action elementId="LogProcessamento${currentStatus.count}" labelKey="label.transfere.core.Objeto.crud.objetoItens.logProcessamento" javascript="$('#logProcessamento${currentStatus.count}').dialog({width:680,height:300,modal:true})" showButtonAsImage="false" show="${not empty currentItem.textoLogProcessamento}"/>
				<v:action elementId="Script${currentStatus.count}" labelKey="label.transfere.core.Objeto.crud.objetoItens.script" javascript="$('#script${currentStatus.count}').dialog({width:680,height:300,modal:true})" showButtonAsImage="false" show="${not empty currentItem.textoScript}"/>
				<v:action elementId="ScriptAnterior${currentStatus.count}" labelKey="label.transfere.core.Objeto.crud.objetoItens.scriptAnterior" javascript="$('#scriptAnterior${currentStatus.count}').dialog({width:680,height:300,modal:true})" showButtonAsImage="false" show="${not empty currentItem.textoScriptAnterior}"/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>
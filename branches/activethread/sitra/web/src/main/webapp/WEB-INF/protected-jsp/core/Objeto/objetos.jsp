<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<c:choose>
	<c:when test="${not empty now['mensagem']}">
		<span id="alertaErro" class="vulpeAlertError">${now['mensagem']}</span>
	</c:when>
	<c:otherwise>
		<v:select elementId="${now['formName']}_entity.objetoItens:${now['index']}:nomeObjeto" name="entity.objetoItens[${now['index']}].nomeObjeto" property="nomeObjeto" headerKey="label.sitra.todos" showBlank="true" autoLoad="false" items="${now['objetos']}" itemKey="name" itemLabel="name" style="width: 250px"/>
	</c:otherwise>
</c:choose>
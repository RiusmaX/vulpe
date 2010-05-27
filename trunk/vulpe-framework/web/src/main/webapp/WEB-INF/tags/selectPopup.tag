<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributes.jsp" %>
<%@ attribute name="maxlength" required="false" rtexprvalue="true" %>
<%@ attribute name="onselect" required="false" rtexprvalue="true" %>
<%@ attribute name="property" required="true" rtexprvalue="true" %>
<%@ attribute name="identifier" required="true" rtexprvalue="true" %>
<%@ attribute name="description" required="true" rtexprvalue="true" %>
<%@ attribute name="readonly" required="false" rtexprvalue="true" %>
<%@ attribute name="size" required="false" rtexprvalue="true" %>
<%@ attribute name="mask" required="false" rtexprvalue="true" %>

<%@ attribute name="icon" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="action" required="false" rtexprvalue="true" %>
<%@ attribute name="popupId" required="false" rtexprvalue="true" %>
<%@ attribute name="beforeJs" required="false" rtexprvalue="true" %>
<%@ attribute name="afterJs" required="false" rtexprvalue="true" %>
<%-- campoDaPaginaQueRecebeValor=campoComValorNaPopup --%>
<%@ attribute name="popupProperties" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="popupExpressions" required="false" rtexprvalue="true" type="java.lang.String" %>
<%-- campoDaPopupDeEntrada=campoDaPaginaComValor --%>
<%@ attribute name="paramProperties" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="paramExpressions" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="paramLayerParent" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="popupLayerParent" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="paramTargetName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="popupTargetName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="queryString" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="requiredParamProperties" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="requiredParamExpressions" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="popupWidth" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="autoComplete" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="autoCompleteMinLength" required="false" rtexprvalue="true" type="java.lang.Integer" %>

<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>
<c:if test="${empty readonly}">
	<c:set var="readonly" value="${true}"/>
</c:if>

<c:if test="${show eq true}">
	<v:hidden property="${property}.${identifier}"/>
	<c:if test="${autoComplete && empty autoCompleteMinLength}">
		<c:set var="autoCompleteMinLength" value="3"/>
	</c:if>

	<c:set var="autoCompleteAction" value="${fn:replace(action, '/prepare', '/autocomplete/ajax')}" />
	<v:text labelKey="${labelKey}" property="${property}.${description}" readonly="${autoComplete ? false : true}" elementId="${elementId}" size="${size}" showAsText="${showAsText}" autoComplete="${description}" autoCompleteURL="${autoCompleteAction}" autoCompleteSelect="true" required="${required}">
		<c:if test="${!showAsText}"><v:popup action="${action}" labelKey="vulpe.label.selected" popupId="${popupId}" popupProperties="${popupProperties}" popupWidth="${popupWidth}"/></c:if>
	</v:text>
</c:if>
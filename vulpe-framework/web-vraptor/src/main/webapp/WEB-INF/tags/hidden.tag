<%@ attribute name="property" required="false" rtexprvalue="true" %>
<%@ attribute name="value" required="false" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="name" required="false" rtexprvalue="true" %>
<%@ attribute name="elementId" required="false" rtexprvalue="true" %>
<%@ attribute name="targetName" required="false" rtexprvalue="true" %>
<%@ attribute name="targetValue" required="false" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="saveInSession" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="expireInSession" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="render" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<%@include file="/WEB-INF/protected-jsp/common/common.jsp" %>

<c:if test="${empty saveInSession}">
	<c:set var="saveInSession" value="${false}"/>
</c:if>
<c:if test="${empty expireInSession}">
	<c:set var="expireInSession" value="${false}"/>
</c:if>
<c:if test="${empty render}">
	<c:set var="render" value="${true}"/>
</c:if>

<c:if test="${empty targetName}">
	<c:if test="${empty targetConfig}">
		<c:set var="targetName" value="entity"/>
	</c:if>
	<c:if test="${not empty targetConfig}">
		<c:set var="targetNameEL" value="${'${'}${targetConfig.baseName}_status.index${'}'}"/>
		<c:set var="targetName" value="${targetConfigPropertyName}[${util:eval(pageContext, targetNameEL)}]"/>
	</c:if>
</c:if>
<c:if test="${empty targetValue}">
	<c:if test="${empty targetConfig}">
		<c:set var="targetValueEL" value="${'${'}${targetName}${'}'}"/>
		<c:set var="targetValue" value="${util:eval(pageContext, targetValueEL)}"/>
	</c:if>
	<c:if test="${not empty targetConfig}">
		<c:set var="targetValueEL" value="${'${'}${targetConfig.baseName}_item${'}'}"/>
		<c:set var="targetValue" value="${util:eval(pageContext, targetValueEL)}"/>
	</c:if>
</c:if>

<c:if test="${not empty property && empty name}">
	<c:set var="name" value="${targetName}.${property}"/>
</c:if>

<c:if test="${empty elementId}">
	<c:set var="prepareName" value="${fn:replace(name, '[', ':')}"/>
	<c:set var="prepareName" value="${fn:replace(prepareName, '].', ':')}"/>
	<c:set var="elementId" value="${actionConfig.formName}_${prepareName}"/>
</c:if>

<c:if test="${not empty property && empty value}">
	<c:set var="valueEL" value="${'${'}targetValue.${property}${'}'}"/>
	<c:set var="value" value="${util:eval(pageContext, valueEL)}"/>
</c:if>

<c:if test="${saveInSession}">
	<c:set var="valueInSession" value="${util:saveInSession(name, value, expireInSession)}"/>
</c:if>

<c:if test="${not empty value}">
	<c:set var="value" value="${util:toString(value)}"/>
</c:if>

<c:if test="${render}">
	<s:hidden theme="simple" name="${name}" id="${elementId}" value="${value}"/>
</c:if>
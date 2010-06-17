<%@ attribute name="icon" required="false" rtexprvalue="true" %>
<%@ attribute name="layer" required="false" rtexprvalue="true" %>
<%@ attribute name="action" required="false" rtexprvalue="true" %>
<%@ attribute name="queryString" required="false" rtexprvalue="true" %>
<%@ attribute name="labelKey" required="true" rtexprvalue="true" %>
<%@ attribute name="widthIcon" required="false" rtexprvalue="true" %>
<%@ attribute name="heightIcon" required="false" rtexprvalue="true" %>
<%@ attribute name="borderIcon" required="false" rtexprvalue="true" %>
<%@ attribute name="style" required="false" rtexprvalue="true" %>
<%@ attribute name="elementId" required="false" rtexprvalue="true" %>
<%@ attribute name="validate" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="beforeJs" required="false" rtexprvalue="true" %>
<%@ attribute name="afterJs" required="false" rtexprvalue="true" %>
<%@ attribute name="javascript" required="false" rtexprvalue="true" %>
<%@ attribute name="layerFields" required="false" rtexprvalue="true" %>
<%@ attribute name="styleClass" required="false" rtexprvalue="true" %>
<%@ attribute name="iconClass" required="false" rtexprvalue="true" %>
<%@ attribute name="noSubmitForm" required="false" rtexprvalue="true" %>
<%@ attribute name="role" required="false" rtexprvalue="true" %>
<%@ attribute name="logged" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="width" required="false" rtexprvalue="true" type="java.lang.String" %>

<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="show" value="${true}"/>
<c:if test="${not empty logged && logged eq true && util:isLogged(pageContext) eq false}">
	<c:set var="show" value="${false}"/>
</c:if>
<c:if test="${not empty role && util:isRole(pageContext, role) eq false}">
	<c:set var="show" value="${false}"/>
</c:if>

<c:if test="${show eq true}">
	<c:if test="${isHeader}">
		<th scope="col" width="${width}"><fmt:message key="${labelKey}"/></th>
	</c:if>
	<c:if test="${!isHeader}">
		<td align="center" width="${width}" onclick="${selectCheckOn}">
			<v:action iconClass="${iconClass}" styleClass="${styleClass}" javascript="${javascript}" layerFields="${layerFields}" beforeJs="${beforeJs}" afterJs="${afterJs}" style="${style}" elementId="${elementId}" validate="${validate}" labelKey="${labelKey}" layer="${layer}" queryString="${queryString}" action="${action}" borderIcon="${borderIcon}" heightIcon="${heightIcon}" icon="${icon}" widthIcon="${widthIcon}" noSubmitForm="${noSubmitForm}"/>
		</td>
	</c:if>
</c:if>
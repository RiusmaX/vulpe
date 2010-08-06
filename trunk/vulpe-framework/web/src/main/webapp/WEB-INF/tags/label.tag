<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ attribute name="elementId" required="false" rtexprvalue="true"%>
<%@ attribute name="forElementId" required="false" rtexprvalue="true"%>
<%@ attribute name="key" required="false" rtexprvalue="true"%>
<%@ attribute name="value" required="false" rtexprvalue="true"%>
<%@ attribute name="style" required="false" rtexprvalue="true"%>
<%@ attribute name="styleClass" required="false" rtexprvalue="true"%>
<fmt:message key="${key}" var="value" />
<label id="${elementId}" for="${forElementId}" style="${style}" class="${styleClass}">${value}</label>
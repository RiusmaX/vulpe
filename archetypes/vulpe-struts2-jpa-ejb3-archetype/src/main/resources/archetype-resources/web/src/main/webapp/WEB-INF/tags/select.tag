<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributes.jsp" %>
<%@ attribute name="property" required="true" rtexprvalue="true" %>
<%@ attribute name="items" required="false" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="itemLabel" required="false" rtexprvalue="true" %>
<%@ attribute name="itemKey" required="false" rtexprvalue="true" %>
<%@ attribute name="headerValue" required="false" rtexprvalue="true" %>
<%@ attribute name="headerKey" required="false" rtexprvalue="true" %>
<%@ attribute name="showBlank" required="false" rtexprvalue="true" %>
<%@ attribute name="autoLoad" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="multiple" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="size" required="false" rtexprvalue="true" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/selectTag.jsp" %>
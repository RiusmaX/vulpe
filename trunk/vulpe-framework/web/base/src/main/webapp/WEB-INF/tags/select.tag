<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributes.jsp" %>
<%@ attribute name="autoLoad" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="headerValue" required="false" rtexprvalue="true" %>
<%@ attribute name="headerKey" required="false" rtexprvalue="true" %>
<%@ attribute name="items" required="false" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="itemLabel" required="false" rtexprvalue="true" %>
<%@ attribute name="itemKey" required="false" rtexprvalue="true" %>
<%@ attribute name="multiple" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="property" required="true" rtexprvalue="true" %>
<%@ attribute name="removeEnumItems" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="size" required="false" rtexprvalue="true" %>
<%@ attribute name="maxlength" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="showBlank" required="false" rtexprvalue="true" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/selectTag.jsp" %>
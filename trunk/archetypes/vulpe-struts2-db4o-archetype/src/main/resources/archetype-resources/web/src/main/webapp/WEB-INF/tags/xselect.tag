<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributes.jsp" %>
<%@ attribute name="list" required="false" rtexprvalue="true" %>
<%@ attribute name="listKey" required="false" rtexprvalue="true" %>
<%@ attribute name="listValue" required="false" rtexprvalue="true" %>
<%@ attribute name="headerKey" required="false" rtexprvalue="true" %>
<%@ attribute name="headerValue" required="false" rtexprvalue="true" %>
<%@ attribute name="onselect" required="false" rtexprvalue="true" %>
<%@ attribute name="property" required="false" rtexprvalue="true" %>
<%@ attribute name="size" required="false" rtexprvalue="true" %>
<%@ attribute name="showAsText" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="emptyOption" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="isEnum" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="enumeration" required="false" rtexprvalue="true" %>
<%@ attribute name="showBlank" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/xselectTag.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributes.jsp" %>
<%@ attribute name="cols" required="false" rtexprvalue="true" %>
<%@ attribute name="limitContent" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="property" required="true" rtexprvalue="true" %>
<%@ attribute name="readonly" required="false" rtexprvalue="true" %>
<%@ attribute name="rows" required="false" rtexprvalue="true" %>
<%@ attribute name="validateMaxLength" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="validateMinLength" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="validateType" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/textareaTag.jsp" %>
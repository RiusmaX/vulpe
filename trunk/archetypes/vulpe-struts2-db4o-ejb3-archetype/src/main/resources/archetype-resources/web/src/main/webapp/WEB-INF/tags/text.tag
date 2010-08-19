<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributes.jsp" %>
<%@ attribute name="property" required="false" rtexprvalue="true" %>
<%@ attribute name="mask" required="false" rtexprvalue="true" %>
<%@ attribute name="lowerCase" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="upperCase" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="maxlength" required="false" rtexprvalue="true" %>
<%@ attribute name="readonly" required="false" rtexprvalue="true" %>
<%@ attribute name="size" required="false" rtexprvalue="true" %>
<%@ attribute name="onselect" required="false" rtexprvalue="true" %>
<%@ attribute name="autoComplete" required="false" rtexprvalue="true" %>
<%@ attribute name="autoCompleteMinLength" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="autoCompleteURL" required="false" rtexprvalue="true" %>
<%@ attribute name="autoCompleteSelect" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="validateType" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="validateMask" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="validateDatePattern" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="validateRange" required="false" rtexprvalue="true" type="java.lang.Integer[]" %>
<%@ attribute name="validateMin" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="validateMax" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="validateMinLength" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="validateMaxLength" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/textTag.jsp" %>
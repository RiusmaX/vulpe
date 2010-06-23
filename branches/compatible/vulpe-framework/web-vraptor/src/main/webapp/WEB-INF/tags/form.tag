<%@ attribute name="theme" required="false" rtexprvalue="true" %>
<%@ attribute name="name" required="false" rtexprvalue="true" %>
<%@ attribute name="id" required="false" rtexprvalue="true" %>
<%@ attribute name="method" required="false" rtexprvalue="true" %>
<%@ attribute name="enctype" required="false" rtexprvalue="true" %>
<%@ attribute name="validate" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:if test="${empty theme}">
	<c:set var="theme" value="simple"/>
</c:if>
<c:if test="${empty validate}">
	<c:set var="validate" value="true"/>
</c:if>
<c:if test="${empty enctype}">
	<c:set var="enctype" value="multipart/form-data"/>
</c:if>
<c:if test="${empty method}">
	<c:set var="method" value="post"/>
</c:if>
<form id="${id}" name="${name}" enctype="${enctype}" method="${method}">
	<jsp:doBody/>
</form>
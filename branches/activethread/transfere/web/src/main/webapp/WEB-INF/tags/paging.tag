<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<%@ attribute name="list" required="false" rtexprvalue="true" type="org.vulpe.commons.beans.Paging"%>
<%@ attribute name="actionName" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="formName" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="layerFields" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="layer" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="beforeJs" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="afterJs" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="showSize" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@include file="/WEB-INF/protected-jsp/commons/tags/pagingTag.jsp"%>
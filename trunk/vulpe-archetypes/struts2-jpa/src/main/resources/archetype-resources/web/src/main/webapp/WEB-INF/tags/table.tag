<%@ attribute name="border" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="cellspacing" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="detailConfig" required="false" rtexprvalue="true" type="org.vulpe.controller.commons.VulpeBaseDetailConfig" %>
<%@ attribute name="elementId" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="emptyKey" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="items" required="false" rtexprvalue="true" type="java.util.Collection" %>
<%@ attribute name="pagingList" required="false" rtexprvalue="true" type="org.vulpe.commons.beans.Paging" %>
<%@ attribute name="pagingActionName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="pagingFormName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="pagingLayerFields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="pagingLayer" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="pagingBeforeJs" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="pagingAfterJs" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="renderId" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="roles" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="show" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="showOnlyIfAuthenticated" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="sortPropertyInfo" required="false" rtexprvalue="true" %>
<%@ attribute name="tableBody" fragment="true" %>
<%@ attribute name="tableFooter" required="false" rtexprvalue="true" %>
<%@ attribute name="tableHeader" required="false" rtexprvalue="true" %>
<%@ attribute name="width" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tableTag.jsp" %>
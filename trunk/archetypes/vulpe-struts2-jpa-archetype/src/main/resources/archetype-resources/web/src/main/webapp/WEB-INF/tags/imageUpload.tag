<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributes.jsp" %>
<%@ attribute name="accept" required="false" rtexprvalue="true" %>
<%@ attribute name="indexed" required="false" rtexprvalue="true" %>
<%@ attribute name="property" required="true" rtexprvalue="true" %>
<%@ attribute name="size" required="false" rtexprvalue="true" %>
<%@ attribute name="imageWidth" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="imageHeight" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="imageThumbWidth" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="imageThumbHeight" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="showGallery" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/imageUploadTag.jsp" %>
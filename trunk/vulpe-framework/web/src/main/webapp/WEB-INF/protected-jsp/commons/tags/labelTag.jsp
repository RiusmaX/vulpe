<%@include file="/WEB-INF/protected-jsp/commons/tags/headerTag.jsp" %>
<fmt:message key="${key}" var="value" />
<label id="${elementId}" for="${forElementId}" style="${style}" class="${styleClass}">${value}</label>
<c:if test="${not empty breakLine && breakLine}"><br></c:if>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<v:text labelKey="label.vulpe.security.SecureResource.main.resourceName" property="resourceName"
	size="100" required="true" />
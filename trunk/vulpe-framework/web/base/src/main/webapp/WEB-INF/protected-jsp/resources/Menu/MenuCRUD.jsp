<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:hidden property="id" />
<v:text labelKey="label.vulpe.commons.Menu.main.name" property="name" required="true"
	validateType="STRING" validateMinLength="3" size="40" maxlength="100" />
<v:text labelKey="label.vulpe.commons.Menu.main.description" property="description" required="true"
	lowerCase="true" validateType="EMAIL" size="60" maxlength="150" />
<v:checkbox labelKey="label.vulpe.commons.Menu.main.backendOnly" property="backendOnly"
	fieldValue="${true}" />
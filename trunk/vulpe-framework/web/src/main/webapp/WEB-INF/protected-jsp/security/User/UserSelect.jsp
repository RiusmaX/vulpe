<%@include file="/WEB-INF/protected-jsp/common/taglibs.jsp" %>

<v:text labelKey="label.vulpe.security.User.select.name" property="name" size="40"/>
<v:text labelKey="label.vulpe.security.User.select.username" property="username" size="40"/>
<v:checkbox labelKey="label.vulpe.security.User.select.active" property="active" fieldValue="${true}"/>

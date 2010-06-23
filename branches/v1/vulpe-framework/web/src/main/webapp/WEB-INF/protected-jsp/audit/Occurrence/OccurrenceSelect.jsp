<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:text labelKey="label.vulpe.audit.Occurrence.select.id" property="id" mask="I"/>
<v:select labelKey="label.vulpe.audit.Occurrence.select.occurrenceType" property="occurrenceType" />
<v:text labelKey="label.vulpe.audit.Occurrence.select.entity" property="entity" size="40"/>
<v:text labelKey="label.vulpe.audit.Occurrence.select.primaryKey" property="primaryKey" size="10"/>
<v:text labelKey="label.vulpe.audit.Occurrence.select.username" property="username" size="40"/>
<v:date labelKey="label.vulpe.audit.Occurrence.select.dateTime" property="dateTime" />

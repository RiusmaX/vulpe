<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:hidden property="id"/>
<v:text
	labelKey="label.gmn.core.Congregacao.crud.nome"
	property="nome"
	size="40"
	maxlength="60"
	validateType="STRING"
	validateMinLength="5"
	validateMaxLength="60"
/>

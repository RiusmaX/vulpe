<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:text
	labelKey="label.gmn.core.Congregacao.select.nome"
	property="nome"
	size="40"
	maxlength="60" required="true"
	validateType="STRING"
	validateMinLength="5"
	validateMaxLength="60"
/>

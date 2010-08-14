<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:text
	labelKey="label.sitra.core.Sistema.select.nome"
	property="nome"
	size="60"
	maxlength="200"
	required="true"
	validateType="STRING"
	validateMinLength="3"
	validateMaxLength="200"
/>
<v:text
	labelKey="label.sitra.core.Sistema.select.sigla"
	property="sigla"
	size="20"
	maxlength="20"
	validateType="STRING"
	validateMinLength="3"
	validateMaxLength="20"
/>

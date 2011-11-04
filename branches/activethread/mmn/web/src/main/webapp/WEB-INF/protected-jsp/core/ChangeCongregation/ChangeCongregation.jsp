<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<p>
<v:select
	labelKey="label.mmn.core.Congregation.main.name"
	property="congregation.id"
	items="${ever['congregationsOfUser']}"
	itemKey="id"
	itemLabel="name"
	showBlank="true" autoLoad="false"
	required="true" paragraph="false"
/>
<br/>
<v:action labelKey="label.mmn.select" helpKey="help.mmn.select" elementId="Select" action="selectValidate" showButtonAsImage="false" showTextOfButton="true" showIconOfButton="false" validate="true" />
</p>
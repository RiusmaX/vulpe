<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.mmn.core.Member.main.phones.number" align="left">
				<v:text property="number" size="20" maxlength="20" mask="(99) 9999-9999" validateType="STRING"
					validateMinLength="5" validateMaxLength="40" />
			</v:column>
			<v:column labelKey="label.mmn.core.Member.main.phones.type" align="left">
				<v:select property="type" autoLoad="false" showBlank="true" />
			</v:column>
			<v:column labelKey="label.mmn.core.Member.main.phones.principal" align="left">
				<v:checkbox property="principal" fieldValue="true" />
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>

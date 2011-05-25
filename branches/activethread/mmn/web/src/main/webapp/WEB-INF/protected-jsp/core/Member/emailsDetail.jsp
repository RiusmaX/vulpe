<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.mmn.core.Member.main.emails.email" align="left">
				<v:text property="email"
					size="40"
					maxlength="100"
					validateType="STRING"
					validateMinLength="5"
					validateMaxLength="40"
				/>
			</v:column>
			<v:column labelKey="label.mmn.core.Member.main.emails.principal" align="left">
				<v:checkbox property="principal" fieldValue="true" />
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>

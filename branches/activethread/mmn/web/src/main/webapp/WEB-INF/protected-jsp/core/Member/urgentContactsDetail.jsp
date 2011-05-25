<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.mmn.core.Member.main.urgentContacts.name" align="left">
				<v:text property="name"
					size="40"
					maxlength="40"
					validateType="STRING"
					validateMinLength="5"
					validateMaxLength="40"
				/>
			</v:column>
			<v:column labelKey="label.mmn.core.Member.main.urgentContacts.phone" align="left">
				<v:text property="phone"
					size="20"
					maxlength="20" mask="(99) 9999-9999"
					validateType="STRING"
					validateMinLength="5"
					validateMaxLength="20"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>

<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column labelKey="label.mmn.core.Congregation.main.groups.name" align="left" width="100%">
				<v:text property="name"
					size="40"
					maxlength="40"
					validateType="STRING"
					validateMinLength="5"
					validateMaxLength="40"
				/>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>

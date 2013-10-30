<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column>
			<table width="100%" cellpadding="0" cellspacing="0" style="font-size: 10px;">
				<tr>
					<td width="90%">
						<v:label key="label.mmn.core.Member.main.addresses.address" />
					</td>
					<td>
						<v:label key="label.mmn.core.Member.main.addresses.number" />
					</td>
					<td>
						<v:label key="label.mmn.core.Member.main.addresses.complement" />
					</td>
				</tr>
				<tr>
					<td>
						<v:text property="address" size="30" maxlength="100" validateType="STRING"
							validateMinLength="5" validateMaxLength="100" />
					</td>
					<td>
						<v:text property="number" size="10" maxlength="10" />
					</td>
					<td>
						<v:text property="complement" size="15" maxlength="40" />
					</td>
				</tr>
				<tr>
					<td>
						<v:label key="label.mmn.core.Member.main.addresses.district" />
					</td>
					<td colspan="2">
						<v:label key="label.mmn.core.Member.main.addresses.postCode" />
					</td>
				</tr>
				<tr>
					<td>
						<v:text property="district" size="20" maxlength="40" />
					</td>
					<td colspan="2">
						<v:text property="postCode" size="10" maxlength="10" mask="99999-999" />
					</td>
				</tr>
			</table>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>

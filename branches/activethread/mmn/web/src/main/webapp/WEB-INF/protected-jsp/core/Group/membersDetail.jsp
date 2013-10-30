<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="v" %>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column>
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="titulo" colspan="2">
						<v:label key="label.mmn.core.Group.main.members.name" />
					</td>
					<td class="titulo">
						<v:label key="label.mmn.core.Group.main.members.gender" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<v:text property="name" size="40" maxlength="60" required="true" />
					</td>
					<td>
						<v:select property="gender" showBlank="true" autoLoad="false" required="true" />
					</td>
				</tr>
				<tr>
					<td class="titulo">
						<v:label key="label.mmn.core.Group.main.members.indicator" />
					</td>
					<td class="titulo">
						<v:label key="label.mmn.core.Group.main.members.reader" />
					</td>
					<td class="titulo">
						<v:label key="label.mmn.core.Group.main.members.microphone" />
					</td>
				</tr>
				<tr>
				<td>
				<v:checkbox property="indicator" fieldValue="true" />
				</td>
				<td>
				<v:checkbox property="reader" fieldValue="true" />
				</td>
				<td>
				<v:checkbox property="microphone" fieldValue="true" />
			</td>
				</tr>
			</table>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>

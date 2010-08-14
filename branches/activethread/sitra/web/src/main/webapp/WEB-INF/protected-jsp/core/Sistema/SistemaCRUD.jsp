<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<table>
	<tr>
		<td>
		<v:text
			labelKey="label.sitra.core.Sistema.crud.nome"
			property="nome"
			size="60"
			maxlength="200"
			required="true"
			validateType="STRING"
			validateMinLength="3"
			validateMaxLength="200"
		/>
		</td>
		<td>
		<v:text
			labelKey="label.sitra.core.Sistema.crud.sigla"
			property="sigla"
			size="20"
			maxlength="20"
			required="true"
			validateType="STRING"
			validateMinLength="3"
			validateMaxLength="20"
		/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<v:text
			labelKey="label.sitra.core.Sistema.crud.gerencia"
			property="gerencia"
			size="60"
			maxlength="200"
			required="true"
		/>
		</td>
	</tr>
	<tr>
		<td>
		<v:text
			labelKey="label.sitra.core.Sistema.crud.analistaResponsavel"
			property="analistaResponsavel"
			size="60"
			maxlength="200"
			required="true"
		/>
		</td>
		<td>
		<v:text
			labelKey="label.sitra.core.Sistema.crud.ramal"
			property="ramal"
			size="20"
			maxlength="20"
			required="true"
		/>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="left">
		<fieldset>
		<legend>&nbsp;Servidores&nbsp;</legend>
		<table>
			<tr>
				<td></td>
				<td><v:label key="label.sitra.core.Sistema.crud.producao"/></td>
				<td><v:label key="label.sitra.core.Sistema.crud.homologacao"/></td>
				<td><v:label key="label.sitra.core.Sistema.crud.desenvolvimento"/></td>
			</tr>
			<tr>
				<td><v:label key="label.sitra.core.Sistema.crud.owner"/></td>
				<td>
				<v:text
					property="ownerProducao"
					size="20"
					maxlength="20"
					required="true" upperCase="true"
				/>
				</td>
				<td colspan="2">
				<v:text
					property="ownerHomologacao"
					size="20"
					maxlength="20"
					required="true" upperCase="true"
				/>
				</td>
			</tr>
			<tr>
				<td><v:label key="label.sitra.core.Sistema.crud.dblink"/></td>
				<td>
				<v:text
					property="dblinkProducao"
					size="40"
					maxlength="100"
					required="true"
				/>
				</td>
				<td>
				<v:text
					property="dblinkHomologacao"
					size="40"
					maxlength="100"
					required="true"
				/>
				</td>
				<td>
				<v:text
					property="dblinkDesenvolvimento"
					size="40"
					maxlength="100"
					required="true"
				/>
				</td>
			</tr>
		</table>
		</fieldset>
		</td>
	</tr>
</table>
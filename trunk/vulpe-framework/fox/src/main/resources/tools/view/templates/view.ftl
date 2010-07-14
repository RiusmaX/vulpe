<#include "macros.ftl"/>
<@forAllValidView ; type, view>
<#list view.types as t>
<#if t == 'SELECT' || t == 'ALL'>
################################################################################
# View Select: ${view.name}
################################################################################
label.${view.projectName}.${view.moduleName}.${view.name}.select=Select of ${view.name?capitalize}
label.${view.projectName}.${view.moduleName}.${view.name}.select.header=List of ${view.name?capitalize}
<#list view.arguments as field>
label.${view.projectName}.${view.moduleName}.${view.name}.select.${field.name}=${field.name?capitalize}
</#list>
<@file name="${view.moduleName}/${view.name}/${view.name}Select.jsp">
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<#list view.arguments as field>
<v:${field.type}
	labelKey="label.${view.projectName}.${view.moduleName}.${view.name}.select.${field.name}"
<#if field.itemKey?has_content>
	property="${field.name}.${field.itemKey}"
<#else>
	property="${field.name}"
</#if>
<#if field.type == 'text' || field.type == 'textarea' || field.type == 'password' || field.type == 'date'>
	<#if field.mask?has_content>
	mask="${field.mask}"
	</#if>
	<#if (field.size > 0)>
	size="${field.size}"
	</#if>
	<#if (field.maxlength > 0)>
	maxlength="${field.maxlength}"
	</#if>
	<#if field.type == 'textarea'>
	cols="${field.cols}" rows="${field.rows}"
	</#if>
</#if>
<#if field.type == 'select'>
	<#if (field.size > 0)>
	size="${field.size}"
	</#if>
	<#if field.items?has_content>
	items="${field.items}"
	</#if>
	<#if field.itemKey?has_content>
	itemKey="${field.itemKey}"
	</#if>
	<#if field.itemLabel?has_content>
	itemLabel="${field.itemLabel}"
	</#if>
	showBlank="${field.showBlank}" autoLoad="${field.autoLoad}"
</#if>
<#if field.type == 'selectPopup'>
	identifier="${field.identifier}" description="${field.description}"
	action="${field.action}" popupId="${field.name}SelectPopup"
	popupProperties="${field.name}.${field.identifier}=${field.identifier},${field.name}.${field.description}=${field.description}"
	size="${field.size}" popupWidth="${field.popupWidth}px"
	autoComplete="${field.autoComplete}"
</#if>
<#if field.type == 'radio'>
	<#if field.list?has_content>
	items="${field.list}"
	</#if>
	<#if field.listKey?has_content>
	itemKey="${field.listKey}"
	</#if>
	<#if field.listValue?has_content>
	itemValue="${field.listValue}"
	</#if>
</#if>
<#if field.type == 'checkbox'>
	fieldValue="${field.fieldValue}"
</#if>
<#if field.type == 'checkboxlist'>
	<#if field.list?has_content>
	list="${field.list}"
	</#if>
	<#if field.listKey?has_content>
	listKey="${field.listKey}"
	</#if>
	<#if field.listValue?has_content>
	listValue="${field.listValue}"
	</#if>
	<#if field.enumeration?has_content>
	enumeration="${field.enumeration}"
	</#if>
</#if>
<#if field.required && (field.validateRequiredScope == "ALL" || field.validateRequiredScope?contains("SELECT"))>
	required="${field.required}"
</#if>
<#if field.validateType?has_content && field.validateScope?has_content && (field.validateScope == "ALL" || field.validateScope?contains("SELECT"))>
	validateType="${field.validateType}"
	<#if field.validateMas?has_content>
	validateMask="${field.validateMask}"
	</#if>
	<#if field.validateType == 'DATE'>
	validateDatePattern="${field.validateDatePattern}"
	</#if>
	<#if field.validateType == 'STRING'>
	<#if field.validateMinLength?has_content>
	validateMinLength="${field.validateMinLength}"
	</#if>
	<#if field.validateMaxLength?has_content>
	validateMaxLength="${field.validateMaxLength}"
	</#if>
	</#if>
	<#if field.validateType == 'INTEGER' || field.validateType == 'LONG' || field.validateType == 'DOUBLE' || field.validateType == 'FLOAT'>
	<#if field.validateRange?has_content>
	validateRange="${field.validateRange}"
	</#if>
	<#if field.validateMin?has_content>
	validateMin="${field.validateMin}"
	</#if>
	<#if field.validateMax?has_content>
	validateMax="${field.validateMax}"
	</#if>
	</#if>
</#if>
/>
</#list>
</@file>
<@file name="${view.moduleName}/${view.name}/${view.name}SelectItems.jsp">
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="${view.columnSpan}"><fmt:message key="label.${view.projectName}.${view.moduleName}.${view.name}.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row<#if view.popupProperties?has_content> popupProperties="${view.popupProperties}"</#if>>
			<#list view.items as field>
			<v:column
				labelKey="label.${view.projectName}.${view.moduleName}.${view.name}.select.${field.name}"
				<#if field.attribute?has_content>
				property="${field.name}.${field.attribute}"
				<#else>
				property="${field.name}"
				</#if>
				<#if field.width?has_content>
				width="${field.width}"
				</#if>
				<#if field.booleanTo?has_content>
				booleanTo="${field.booleanTo}"
				</#if>
				<#if (field.sortable)>
				sort="${field.sortable}"
				</#if>
			/>
			</#list>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="${view.columnSpan}"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
</@file>
</#if>
<#if t == 'CRUD' || t == 'ALL'>
################################################################################
# View CRUD: ${view.name}
################################################################################
label.${view.projectName}.${view.moduleName}.${view.name}.crud=CRUD of ${view.name?capitalize}
<#list view.fields as field>
label.${view.projectName}.${view.moduleName}.${view.name}.crud.${field.name}=${field.name?capitalize}
</#list>
<@file name="${view.moduleName}/${view.name}/${view.name}CRUD.jsp">
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:hidden property="id"/>
<#list view.fields as field>
<v:${field.type}
	labelKey="label.${view.projectName}.${view.moduleName}.${view.name}.crud.${field.name}"
<#if field.itemKey?has_content>
	property="${field.name}.${field.itemKey}"
<#else>
	property="${field.name}"
</#if>
<#if field.type == 'text' || field.type == 'textarea' || field.type == 'password' || field.type == 'date'>
	<#if field.mask?has_content>
	mask="${field.mask}"
	</#if>
	<#if (field.size > 0)>
	size="${field.size}"
	</#if>
	<#if (field.size > 0)>
	maxlength="${field.maxlength}"
	</#if>
	<#if field.type == 'textarea'>
	cols="${field.cols}" rows="${field.rows}"
	</#if>
</#if>
<#if field.type == 'select'>
	<#if (field.size > 0)>
	size="${field.size}"
	</#if>
	<#if field.items?has_content>
	items="${field.items}"
	</#if>
	<#if field.itemKey?has_content>
	itemKey="${field.itemKey}"
	</#if>
	<#if field.itemLabel?has_content>
	itemLabel="${field.itemLabel}"
	</#if>
	showBlank="${field.showBlank}" autoLoad="${field.autoLoad}"
</#if>
<#if field.type == 'selectPopup'>
	identifier="${field.identifier}" description="${field.description}"
	action="${field.action}" popupId="${field.name}SelectPopup"
	popupProperties="${field.name}.${field.identifier}=${field.identifier},${field.name}.${field.description}=${field.description}"
	size="${field.size}" popupWidth="${field.popupWidth}px"
	autoComplete="${field.autoComplete}"
</#if>
<#if field.type == 'radio'>
	<#if field.list?has_content>
	items="${field.list}"
	</#if>
	<#if field.listKey?has_content>
	itemKey="${field.listKey}"
	</#if>
	<#if field.listValue?has_content>
	itemValue="${field.listValue}"
	</#if>
</#if>
<#if field.type == 'checkbox'>
	fieldValue="${field.fieldValue}"
</#if>
<#if field.type == 'checkboxlist'>
	<#if field.list?has_content>
	list="${field.list}"
	</#if>
	<#if field.listKey?has_content>
	listKey="${field.listKey}"
	</#if>
	<#if field.listValue?has_content>
	listValue="${field.listValue}"
	</#if>
	<#if field.enumeration?has_content>
	enumeration="${field.enumeration}"
	</#if>
</#if>
<#if field.required && (field.validateRequiredScope == "ALL" || field.validateRequiredScope?contains("CRUD"))>
	required="${field.required}"
</#if>
<#if field.validateType?has_content && field.validateScope?has_content && (field.validateScope == "ALL" || field.validateScope?contains("CRUD"))>
	validateType="${field.validateType}"
	<#if field.validateMas?has_content>
	validateMask="${field.validateMask}"
	</#if>
	<#if field.validateType == 'DATE'>
	validateDatePattern="${field.validateDatePattern}"
	</#if>
	<#if field.validateType == 'STRING'>
	<#if field.validateMinLength?has_content>
	validateMinLength="${field.validateMinLength}"
	</#if>
	<#if field.validateMaxLength?has_content>
	validateMaxLength="${field.validateMaxLength}"
	</#if>
	</#if>
	<#if field.validateType == 'INTEGER' || field.validateType == 'LONG' || field.validateType == 'DOUBLE' || field.validateType == 'FLOAT'>
	<#if field.validateRange?has_content>
	validateRange="${field.validateRange}"
	</#if>
	<#if field.validateMin?has_content>
	validateMin="${field.validateMin}"
	</#if>
	<#if field.validateMax?has_content>
	validateMax="${field.validateMax}"
	</#if>
	</#if>
</#if>
/>
</#list>
</@file>
<#list view.details as detail>
label.${view.projectName}.${view.moduleName}.${view.name}.crud.master=${view.name}
################################################################################
# View CRUD Detail: ${detail.name}
################################################################################
label.${view.projectName}.${view.moduleName}.${view.name}.crud.${detail.name}=${detail.name?capitalize}
<#list detail.fields as detailField>
label.${view.projectName}.${view.moduleName}.${view.name}.crud.${detail.name}.${detailField.name}=${detailField.name?capitalize}
</#list>
<@file name="${view.moduleName}/${view.name}/${detail.name}Detail.jsp">
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<#list detail.fields as detailField>
			<v:column labelKey="label.${view.projectName}.${view.moduleName}.${view.name}.crud.${detail.name}.${detailField.name}"<#if detailField.align?has_content> align="${detailField.align}"</#if>>
				<v:${detailField.type} property="${detailField.name}"
				<#if detailField.type == 'selectPopup'>
					identifier="${detailField.identifier}" description="${detailField.description}"
					action="${detailField.action}" popupId="${detailField.name}SelectPopup"
					popupProperties="${detailField.name}.${detailField.identifier}=${detailField.identifier},${detailField.name}.${detailField.description}=${detailField.description}"
					size="${detailField.size}" popupWidth="${detailField.popupWidth}px"
					autoComplete="${detailField.autoComplete}"
				</#if>
				<#if detailField.type == 'text' || detailField.type == 'textarea' || detailField.type == 'password' || detailField.type == 'date'>
					<#if detailField.mask?has_content>
					mask="${detailField.mask}"
					</#if>
					<#if (detailField.size > 0)>
					size="${detailField.size}"
					</#if>
					<#if (detailField.size > 0)>
					maxlength="${detailField.maxlength}"
					</#if>
					<#if detailField.type == 'textarea'>
					cols="${detailField.cols}" rows="${detailField.rows}"
					</#if>
				</#if>
				<#if detailField.type == 'select'>
					<#if (detailField.size > 0)>
					size="${detailField.size}"
					</#if>
					<#if detailField.items?has_content>
					items="${detailField.items}"
					</#if>
					<#if detailField.itemKey?has_content>
					itemKey="${detailField.itemKey}"
					</#if>
					<#if detailField.itemLabel?has_content>
					itemLabel="${detailField.itemLabel}"
					</#if>
					showBlank="${detailField.showBlank}" autoLoad="${detailField.autoLoad}"
				</#if>
				<#if detailField.type == 'radio'>
					<#if detailField.list?has_content>
					items="${detailField.list}"
					</#if>
					<#if detailField.listKey?has_content>
					itemKey="${detailField.listKey}"
					</#if>
					<#if detailField.listValue?has_content>
					itemValue="${detailField.listValue}"
					</#if>
				</#if>
				<#if detailField.type == 'checkbox'>
					fieldValue="${detailField.fieldValue}"
				</#if>
				<#if detailField.type == 'checkboxlist'>
					<#if detailField.list?has_content>
					list="${detailField.list}"
					</#if>
					<#if detailField.listKey?has_content>
					listKey="${detailField.listKey}"
					</#if>
					<#if detailField.listValue?has_content>
					listValue="${detailField.listValue}"
					</#if>
					<#if detailField.enumeration?has_content>
					enumeration="${detailField.enumeration}"
					</#if>
				</#if>
				<#if detailField.required && (detailField.validateRequiredScope == "ALL" || detailField.validateRequiredScope?contains("DETAIL"))>
					required="${detailField.required}"
				</#if>
				<#if detailField.validateType?has_content && detailField.validateScope?has_content && (detailField.validateScope == "ALL" || detailField.validateScope?contains("DETAIL"))>
					validateType="${detailField.validateType}"
					<#if detailField.validateMas?has_content>
					validateMask="${detailField.validateMask}"
					</#if>
					<#if detailField.validateType == 'DATE'>
					validateDatePattern="${detailField.validateDatePattern}"
					</#if>
					<#if detailField.validateType == 'STRING'>
					<#if detailField.validateMinLength?has_content>
					validateMinLength="${detailField.validateMinLength}"
					</#if>
					<#if detailField.validateMaxLength?has_content>
					validateMaxLength="${detailField.validateMaxLength}"
					</#if>
					</#if>
					<#if detailField.validateType == 'INTEGER' || detailField.validateType == 'LONG' || detailField.validateType == 'DOUBLE' || detailField.validateType == 'FLOAT'>
					<#if detailField.validateRange?has_content>
					validateRange="${detailField.validateRange}"
					</#if>
					<#if detailField.validateMin?has_content>
					validateMin="${detailField.validateMin}"
					</#if>
					<#if detailField.validateMax?has_content>
					validateMax="${detailField.validateMax}"
					</#if>
					</#if>
				</#if>
				/>
			</v:column>
			</#list>
		</v:row>
	</jsp:attribute>
</v:table>
</@file>
</#list>
</#if>
<#if t == 'TABULAR' || t == 'ALL'>
################################################################################
# View Tabular: ${view.name}
################################################################################
label.${view.projectName}.${view.moduleName}.${view.name}.tabular=${view.name?capitalize}
label.${view.projectName}.${view.moduleName}.${view.name}.tabular.header=${view.name?capitalize}
<#list view.fields as field>
label.${view.projectName}.${view.moduleName}.${view.name}.tabular.${field.name}=${field.name?capitalize}
</#list>
<@file name="${view.moduleName}/${view.name}/${view.name}Tabular.jsp">
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="${view.columnSpan}"><fmt:message key="label.${view.projectName}.${view.moduleName}.${view.name}.tabular.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
		<#list view.fields as field>
			<v:column labelKey="label.${view.projectName}.${view.moduleName}.${view.name}.tabular.${field.name}">
				<v:${field.type}
				<#if field.itemKey?has_content>
					property="${field.name}.${field.itemKey}"
				<#else>
					property="${field.name}"
				</#if>
				<#if field.type == 'text' || field.type == 'textarea' || field.type == 'password' || field.type == 'date'>
					<#if field.mask?has_content>
					mask="${field.mask}"
					</#if>
					<#if (field.size > 0)>
					size="${field.size}"
					</#if>
					<#if (field.size > 0)>
					maxlength="${field.maxlength}"
					</#if>
					<#if field.type == 'textarea'>
					cols="${field.cols}" rows="${field.rows}"
					</#if>
				</#if>
				<#if field.type == 'select'>
					<#if (field.size > 0)>
					size="${field.size}"
					</#if>
					<#if field.items?has_content>
					items="${field.items}"
					</#if>
					<#if field.itemKey?has_content>
					itemKey="${field.itemKey}"
					</#if>
					<#if field.itemLabel?has_content>
					itemLabel="${field.itemLabel}"
					</#if>
					showBlank="${field.showBlank}" autoLoad="${field.autoLoad}"
				</#if>
				<#if field.type == 'selectPopup'>
					identifier="${field.identifier}" description="${field.description}"
					action="${field.action}" popupId="${field.name}SelectPopup"
					popupProperties="${field.name}.${field.identifier}=${field.identifier},${field.name}.${field.description}=${field.description}"
					size="${field.size}" popupWidth="${field.popupWidth}px"
				</#if>
				<#if field.type == 'radio'>
					<#if field.list?has_content>
					items="${field.list}"
					</#if>
					<#if field.listKey?has_content>
					itemKey="${field.listKey}"
					</#if>
					<#if field.listValue?has_content>
					itemValue="${field.listValue}"
					</#if>
				</#if>
				<#if field.type == 'checkbox'>
					fieldValue="${field.fieldValue}"
				</#if>
				<#if field.type == 'checkboxlist'>
					<#if field.list?has_content>
					list="${field.list}"
					</#if>
					<#if field.listKey?has_content>
					listKey="${field.listKey}"
					</#if>
					<#if field.listValue?has_content>
					listValue="${field.listValue}"
					</#if>
					<#if field.enumeration?has_content>
					enumeration="${field.enumeration}"
					</#if>
				</#if>
				<#if field.required && (field.validateRequiredScope == "ALL" || field.validateRequiredScope?contains("TABULAR"))>
					required="${field.required}"
				</#if>
				<#if field.validateType?has_content && field.validateScope?has_content && (field.validateScope == "ALL" || field.validateScope?contains("TABULAR"))>
					validateType="${field.validateType}"
					<#if field.validateMas?has_content>
					validateMask="${field.validateMask}"
					</#if>
					<#if field.validateType == 'DATE'>
					validateDatePattern="${field.validateDatePattern}"
					</#if>
					<#if field.validateType == 'STRING'>
					<#if field.validateMinLength?has_content>
					validateMinLength="${field.validateMinLength}"
					</#if>
					<#if field.validateMaxLength?has_content>
					validateMaxLength="${field.validateMaxLength}"
					</#if>
					</#if>
					<#if field.validateType == 'INTEGER' || field.validateType == 'LONG' || field.validateType == 'DOUBLE' || field.validateType == 'FLOAT'>
					<#if field.validateRange?has_content>
					validateRange="${field.validateRange}"
					</#if>
					<#if field.validateMin?has_content>
					validateMin="${field.validateMin}"
					</#if>
					<#if field.validateMax?has_content>
					validateMax="${field.validateMax}"
					</#if>
					</#if>
				</#if>
				/>
			</v:column>
		</#list>
		</v:row>
	</jsp:attribute>
	<jsp:attribute name="tableFooter">
		<th colspan="${view.columnSpan}"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
</@file>
</#if>
</#list>
</@forAllValidView>
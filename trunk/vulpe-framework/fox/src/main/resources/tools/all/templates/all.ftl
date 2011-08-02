<#include "*/macros.ftl"/>
***********************************************************************************************
Vulpe Fox - Code Generator
***********************************************************************************************
<@forAllValid ; type, all>
<#if all.manager?has_content>
<@source type="manager" override="${all.override}" name="${all.manager.moduleName}.src.main.java.${all.manager.managerPackageName}.${all.manager.name}">
package ${all.manager.managerPackageName};

import org.springframework.stereotype.Service;

import ${all.manager.daoPackageName}.${all.manager.entityName}DAO;
<#if !all.manager.managerSuperclassName??>
import org.vulpe.model.services.manager.impl.VulpeBaseManager;
</#if>
import ${all.manager.packageName}.${all.manager.entityName};

/**
 * Manager implementation of ${all.manager.entityName}
 */
@Service
<#if all.manager.managerSuperclassName??>
<#if all.manager.inheritance>
public class ${all.manager.name} extends ${all.manager.managerSuperclassName}<${all.manager.entityName}, ${all.manager.entityName}DAO<${all.manager.entityName}>> {
<#else>
public class ${all.manager.name} extends ${all.manager.managerSuperclassName}<${all.manager.entityName}> {
</#if>
<#else>
<#if all.manager.inheritance>
public class ${all.manager.name}<ENTITY_CLASS extends ${all.manager.entityName}, ENTITY_DAO extends ${all.manager.entityName}DAO<ENTITY_CLASS>> extends VulpeBaseManager<ENTITY_CLASS, ${all.manager.idType}, ENTITY_DAO> {
<#else>
public class ${all.manager.name} extends VulpeBaseManager<${all.manager.entityName}, ${all.manager.idType}, ${all.manager.entityName}DAO> {
</#if>
</#if>

}
</@source>
</#if>
<#if all.controller?has_content>
<@source type="controller" override="${all.override}" name="controller.src.main.java.${all.controller.controllerPackageName}.${all.controller.name}Controller">
package ${all.controller.controllerPackageName};

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

<#if all.controller.details?has_content>
import org.vulpe.commons.annotations.DetailConfig;
</#if>

import org.vulpe.controller.annotations.Controller;
<#if (all.controller.pageSize > 0)>
import org.vulpe.controller.annotations.Select;
</#if>
<#if all.controller.tabularDespiseFields?has_content>
import org.vulpe.controller.annotations.Tabular;
</#if>

import ${all.controller.packageName}.${all.controller.entityName};
import ${all.controller.servicePackageName}.${all.controller.moduleName?capitalize}Service;
import ${all.controller.projectPackageName}.controller.ApplicationBaseController;


/**
 * Controller implementation of ${all.controller.entityName}
 */
@Component("${all.controller.moduleName}.${all.controller.name}Controller")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = ${all.controller.moduleName?capitalize}Service.class<#if all.controller.details?has_content>, detailsConfig = { <#list all.controller.details as detail>@DetailConfig(<#if detail.name?has_content>name = "${detail.name}"</#if><#if detail.propertyName?has_content>, propertyName = "${detail.propertyName}"</#if><#if detail.despiseFields?has_content>, despiseFields = "${detail.despiseFields}"</#if><#if (detail.startNewDetails > 0)>, startNewDetails = ${detail.startNewDetails}</#if><#if (detail.newDetails > 0)>, newDetails = ${detail.newDetails}</#if><#if detail.parentDetailName?has_content>, parentDetailName = "${detail.parentDetailName}"</#if>)${detail.next}</#list> }</#if><#if (all.controller.pageSize > 0)>, select = @Select(pageSize = ${all.controller.pageSize})</#if><#if all.controller.tabularDespiseFields?has_content>, tabular = @Tabular(despiseFields = { ${all.controller.tabularDespiseFields} }<#if all.controller.tabularName?has_content>, name = "${all.controller.tabularName}"</#if><#if all.controller.tabularPropertyName?has_content>, propertyName = "${all.controller.tabularPropertyName}"</#if><#if (all.controller.tabularStartNewRecords > 0)>, startNewRecords = ${all.controller.tabularStartNewRecords}</#if><#if (all.controller.tabularNewRecords > 0)>, newRecords = ${all.controller.tabularNewRecords}</#if>)</#if>)
public class ${all.controller.name}Controller extends ApplicationBaseController<${all.controller.entityName}, ${all.controller.idType}> {

}
</@source>
</#if>
<#if all.view?has_content>
[ BEGIN - COPY AND PASTE THE FOLLOWING LINES IN THE FILE: ApplicationResources.properties ]

<#list all.view.types as t>
<#if t == 'SELECT' || t == 'ALL'>
################################################################################
# View Select: ${all.view.name}
################################################################################
label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.select=${all.view.prefixLabelOfSelection} ${all.view.label}
label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.select.header=${all.view.prefixLabelOfSelectionList} ${all.view.label}
<#list all.view.labels?keys as label>
label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.select.${label}=${all.view.labels[label]}
</#list>
<@JSP override="${all.override}" name="web/src/main/webapp/WEB-INF/protected-jsp/${all.view.moduleName}/${all.view.name}/${all.view.name}Select.jsp">
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<#list all.view.arguments as field>
<v:${field.type}
	labelKey="label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.select.${field.name}"
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
	autocomplete="${field.autocomplete}"
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
	<#if (field.validateMinLength > 0)>
	validateMinLength="${field.validateMinLength}"
	</#if>
	<#if (field.validateMaxLength > 0)>
	validateMaxLength="${field.validateMaxLength}"
	</#if>
	</#if>
	<#if field.validateType == 'INTEGER' || field.validateType == 'LONG' || field.validateType == 'DOUBLE' || field.validateType == 'FLOAT'>
	<#if field.validateRange?has_content>
	validateRange="${field.validateRange}"
	</#if>
	<#if (field.validateMin > 0)>
	validateMin="${field.validateMin}"
	</#if>
	<#if (field.validateMax > 0)>
	validateMax="${field.validateMax}"
	</#if>
	</#if>
</#if>
/>
</#list>
</@JSP>
<@JSP override="${all.override}" name="web/src/main/webapp/WEB-INF/protected-jsp/${all.view.moduleName}/${all.view.name}/${all.view.name}SelectItems.jsp">
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="${all.view.columnSpan}"><fmt:message key="label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.select.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row<#if all.view.popupProperties?has_content> popupProperties="${all.view.popupProperties}"</#if>>
			<#list all.view.items as field>
			<v:column
				labelKey="label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.select.${field.name}"
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
		<th colspan="${all.view.columnSpan}"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
</@JSP>
</#if>
<#if t == 'MAIN' || t == 'ALL'>
################################################################################
# View MAIN: ${all.view.name}
################################################################################
label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.main=${all.view.prefixLabelOfMaintenance} ${all.view.label}
<#list all.view.fields as field>
label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.main.${field.name}=${field.label}
</#list>
<@JSP override="${all.override}" name="web/src/main/webapp/WEB-INF/protected-jsp/${all.view.moduleName}/${all.view.name}/${all.view.name}Main.jsp">
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:hidden property="id"/>
<#list all.view.fields as field>
<v:${field.type}
	labelKey="label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.main.${field.name}"
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
	autocomplete="${field.autocomplete}"
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
<#if field.required && (field.validateRequiredScope == "ALL" || field.validateRequiredScope?contains("MAIN"))>
	required="${field.required}"
</#if>
<#if field.validateType?has_content && field.validateScope?has_content && (field.validateScope == "ALL" || field.validateScope?contains("MAIN"))>
	validateType="${field.validateType}"
	<#if field.validateMas?has_content>
	validateMask="${field.validateMask}"
	</#if>
	<#if field.validateType == 'DATE'>
	validateDatePattern="${field.validateDatePattern}"
	</#if>
	<#if field.validateType == 'STRING'>
	<#if (field.validateMinLength > 0)>
	validateMinLength="${field.validateMinLength}"
	</#if>
	<#if (field.validateMaxLength > 0)>
	validateMaxLength="${field.validateMaxLength}"
	</#if>
	</#if>
	<#if field.validateType == 'INTEGER' || field.validateType == 'LONG' || field.validateType == 'DOUBLE' || field.validateType == 'FLOAT'>
	<#if field.validateRange?has_content>
	validateRange="${field.validateRange}"
	</#if>
	<#if (field.validateMin > 0)>
	validateMin="${field.validateMin}"
	</#if>
	<#if (field.validateMax > 0)>
	validateMax="${field.validateMax}"
	</#if>
	</#if>
</#if>
/>
</#list>
</@JSP>
<#list all.view.details as detail>
label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.main.master=${all.view.name}
################################################################################
# View Main Detail: ${detail.name}
################################################################################
label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.main.${detail.name}=${detail.label}
<#list detail.fields as detailField>
label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.main.${detail.name}.${detailField.name}=${detailField.label}
</#list>
<@JSP override="${all.override}" name="web/src/main/webapp/WEB-INF/protected-jsp/${all.view.moduleName}/${all.view.name}/${detail.name}Detail.jsp">
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<#list detail.fields as detailField>
			<v:column labelKey="label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.main.${detail.name}.${detailField.name}"<#if detailField.align?has_content> align="${detailField.align}"</#if>>
				<v:${detailField.type} property="${detailField.name}"
				<#if detailField.type == 'selectPopup'>
					identifier="${detailField.identifier}" description="${detailField.description}"
					action="${detailField.action}" popupId="${detailField.name}SelectPopup"
					popupProperties="${detailField.name}.${detailField.identifier}=${detailField.identifier},${detailField.name}.${detailField.description}=${detailField.description}"
					size="${detailField.size}" popupWidth="${detailField.popupWidth}px"
					autocomplete="${detailField.autocomplete}"
				</#if>
				<#if detailField.type == 'text' || detailField.type == 'textarea' || detailField.type == 'password' || detailField.type == 'date'>
					<#if detailField.mask?has_content>
					mask="${detailField.mask}"
					</#if>
					<#if (detailField.size > 0)>
					size="${detailField.size}"
					</#if>
					<#if (detailField.maxlength > 0)>
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
</@JSP>
</#list>
</#if>
<#if t == 'TABULAR' || t == 'ALL'>
################################################################################
# View Tabular: ${all.view.name}
################################################################################
label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.tabular=${all.view.prefixLabelOfTabular} ${all.view.label}
label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.tabular.header=${all.view.label}
<#list all.view.fields as field>
label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.tabular.${field.name}=${field.label}
</#list>
<@JSP override="${all.override}" name="web/src/main/webapp/WEB-INF/protected-jsp/${all.view.moduleName}/${all.view.name}/${all.view.name}Tabular.jsp">
<%@include file="/WEB-INF/protected-jsp/commons/common.jsp" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="v"%>

<v:table>
	<jsp:attribute name="tableHeader">
		<th colspan="${all.view.columnSpan}"><fmt:message key="label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.tabular.header"/></th>
	</jsp:attribute>
	<jsp:attribute name="tableBody">
		<v:row>
		<#list all.view.fields as field>
			<v:column labelKey="label.${all.view.projectName}.${all.view.moduleName}.${all.view.name}.tabular.${field.name}">
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
					<#if (field.validateMinLength > 0)>
					validateMinLength="${field.validateMinLength}"
					</#if>
					<#if (field.validateMaxLength > 0)>
					validateMaxLength="${field.validateMaxLength}"
					</#if>
					</#if>
					<#if field.validateType == 'INTEGER' || field.validateType == 'LONG' || field.validateType == 'DOUBLE' || field.validateType == 'FLOAT'>
					<#if field.validateRange?has_content>
					validateRange="${field.validateRange}"
					</#if>
					<#if (field.validateMin > 0)>
					validateMin="${field.validateMin}"
					</#if>
					<#if (field.validateMax > 0)>
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
		<th colspan="${all.view.columnSpan}"><fmt:message key="vulpe.total.records"/>&nbsp;<v:paging showSize="true"/></th>
	</jsp:attribute>
</v:table>
</@JSP>
</#if>
</#list>

[ END - COPY AND PASTE THE ABOVE LINES IN THE FILE: ApplicationResources.properties ]
***********************************************************************************************
</#if>
</@forAllValid>
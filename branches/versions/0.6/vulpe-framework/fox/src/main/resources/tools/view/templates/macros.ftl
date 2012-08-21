<#--
 -- Checks if type is instance of class
 -->
<#function isInstanceOf type clazz>
	<#local ret = false>
	<#if getSimpleName(type) == clazz>
		<#local ret = true>
	<#elseif type.superinterfaces??>
		<#list type.superinterfaces as i>
			<#local ret = isInstanceOf(i, clazz)>
			<#if ret>
				<#break>
			</#if>
		</#list>
	</#if>

	<#if !ret && type.superclass??>
		<#local ret = isInstanceOf(type.superclass, clazz)>
	</#if>

	<#return ret>
</#function>

<#--
 -- Retrieve simpleName of type
 -->
<#function getSimpleName type>
	<#local ret = type>
	<#if type?contains("<")>
		<#local ret = type?substring(0, type?index_of("<"))>
	</#if>
	<#return ret>
</#function>

<#--
 -- Make loop in the valid classes
 -->
<#macro forAllValidView>
	<@forAllTypes var="type">
		<#if type.isPublic() && type.isClass() && !type.isAbstract() && isInstanceOf(type, "org.vulpe.model.entity.VulpeEntity")>
			<@forAllView var="view" declaration=type>
				<#nested type view>
			</@forAllView>
		</#if>
	</@forAllTypes>
</#macro>

<#macro addField view field>
<v:${field.type}
	labelKey="label.${view.projectName}.${view.moduleName}.${field.name}.select.${field.name}"
	property="${field.name}"
	<#if field.type == 'text' || field.type == 'password' || field.type == 'date'>
	<#if field.mask != ''>
	mask="${field.mask}"
	</#if>
	<#if (field.size > 0)>
	size="${field.size}"
	</#if>
	<#if (field.size > 0)>
	maxlength="${field.maxlength}"
	</#if>
	</#if>
	<#if field.type == 'select'>
	<#if field.items != ''>
	items="${field.items}"
	</#if>
	<#if field.itemKey != ''>
	itemKey="${field.itemKey}"
	</#if>
	<#if field.itemLabel != ''>
	itemLabel="${field.itemLabel}"
	</#if>
	</#if>
/>
</#macro>
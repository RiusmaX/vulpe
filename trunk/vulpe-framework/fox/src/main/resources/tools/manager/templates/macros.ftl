
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
<#macro forAllValidManager>
	<@forAllTypes var="type">
		<#if type.isPublic() && type.isClass() && !type.isAbstract() && isInstanceOf(type, "org.vulpe.model.entity.VulpeEntity")>
			<@forAllManager var="manager" declaration=type>
				<#nested type manager>
			</@forAllManager>
		</#if>
	</@forAllTypes>
</#macro>
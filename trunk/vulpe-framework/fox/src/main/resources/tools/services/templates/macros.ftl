<#assign basePackageName = aptOptions["-Apackage"]>
<#assign baseClassName = aptOptions["-Acomponent"]?cap_first>
<#assign servicesType = aptOptions["-Aservices"]>
<#assign serviceLookup = aptOptions["-AserviceLookup"]>
<#assign applicationServer = aptOptions["-AapplicationServer"]>
<#assign POJO = servicesType?contains('POJO')>
<#assign EJB = servicesType?contains('EJB')>
<#assign springServiceLookup = serviceLookup?contains('spring')>
<#assign JBOSS = applicationServer?contains('jboss')>

<#macro toJavaDoc doc="" ident=""><#if doc!="">${ident}/**
<#list doc?split("\n") as line>
${ident} *${line}
</#list>${ident} */
</#if></#macro>

<#--
 -- Make loop in parameterized types in the super class
 -->
<#macro forAllTypeParameters clazz>
	<#if clazz.declaration.formalTypeParameters??>
		<#list clazz.declaration.formalTypeParameters as typeParameter>
			<#nested typeParameter typeParameter_index typeParameter_has_next>
		</#list>
	</#if>
</#macro>

<#--
 -- Make loop in parameterized types informed in class
 -->
<#macro forAllTypeArguments clazz>
	<#if clazz.actualTypeArguments??>
		<#list clazz.actualTypeArguments as typeArgument>
			<#nested typeArgument typeArgument_index typeArgument_has_next>
		</#list>
	</#if>
</#macro>

<#--
 -- Extract all TypeVariable of type definition
 -->
<#function findTypeVariables type clazz>
	<#local ret = ''>
	<#assign typeVariables = findAllTypeVariable(type, clazz, '')>
	<#local types = typeVariables?split(';')>
	<#list types as type>
		<#if type?? && type != ''>
			<#local typeVariable = type?split('=')>
			<#local variable = ''>
			<#local clazz = ''>
			<#list typeVariable as tv>
				<#if tv_index == 0>
					<#local variable = tv>
				<#else>
					<#local clazz = tv>
				</#if>
			</#list>
			<#local ret = ret + variable + '=' + resolveType(clazz) + ";">
		</#if>
	</#list>
	<#return ret>
</#function>
<#function findAllTypeVariable type clazz types>
	<#local ret = types>
	<@forAllTypeParameters clazz; typeParameter, typeParameter_index, typeParameter_has_next>
		<#if !ret?contains(typeParameter.simpleName)>
			<@forAllTypeArguments clazz ; typeArgument, typeArgument_index, typeArgument_has_next>
				<#if typeArgument_index == typeParameter_index>
					<#if typeParameter.simpleName == typeArgument>
						<#local ret = ret + typeParameter.simpleName + '=' + getFormalTypeVariable(type, typeArgument) + ";">
					<#else>
						<#local ret = ret + typeParameter.simpleName + '=' + typeArgument + ";">
					</#if>
				</#if>
			</@forAllTypeArguments>
		</#if>
	</@forAllTypeParameters>
	<#if clazz.superclass?? && clazz.superclass != 'java.lang.Object'>
		<#return findAllTypeVariable(type, clazz.superclass, ret)>
	</#if>
	<#return ret>
</#function>

<#--
 -- Solve parameterized types
 -- Example:
 -- List<T>, where T = java.lang.String
 -- Result = List<java.lang.String>
 -->
<#function resolveType typeName>
	<#local ret = typeName>
	<#local types = typeVariables?split(';')>
	<#list types as type>
		<#if type?? && type != ''>
			<#local typeVariable = type?split('=')>
			<#local variable = ''>
			<#local clazz = ''>
			<#list typeVariable as tv>
				<#if tv_index == 0>
					<#local variable = tv>
				<#else>
					<#local clazz = tv>
				</#if>
			</#list>
			<#if ret?contains(variable)>
				<#local ret = ret?replace(variable, clazz)>
			</#if>
		</#if>
	</#list>
	<#return ret>
</#function>

<#--
 -- Retrieve parameterized type variable
 -->
<#function getFormalTypeVariable type typeVariable>
	<#local ret = ''>
	<#list type.formalTypeParameters as p>
		<#if typeVariable == p.simpleName>
			<#if p.bounds??>
				<#list p.bounds as b>
					<#local ret = b>
				</#list>
			</#if>
		</#if>
	</#list>
	<#return ret>
</#function>

<#--
 -- Make method signature
 -->
<#function getSignatureMethod type method>
	<#local isSuffix=false>
	<#list method.annotations?keys as entry>
		<#if entry == "org.vulpe.model.annotations.GenerateSuffix">
		     <#local isSuffix=true>
	    </#if>
	</#list>
	<#local ret = resolveType(method.returnType) + " " + method.simpleName>
	<#if isSuffix==true>
		<#local ret = ret + type.simpleName?replace("Manager", "")>
	</#if>
	<#local ret = ret + "(">
	<#list method.parameters as p>
		<#if p_index &gt; 0>
			<#local ret = ret + ", ">
		</#if>
		<#local ret = ret + "final " + resolveType(p.type) + " " + p.simpleName>
	</#list>
	<#local ret = ret + ")">
	<@forAllThrownTypes var="throwType" indexVar="throwType_index" declaration=method>
		<#if throwType_index == 0>
			<#local ret = ret + " throws ">
		</#if>
		<#if throwType_index &gt; 0>
			<#local ret = ret + ", ">
		</#if>
		<#local ret = ret + throwType>
	</@forAllThrownTypes>
	<#return ret>
</#function>

<#function getMethodName type method>
	<#local isSuffix=false>
	<#list method.annotations?keys as entry>
		<#if entry == "org.vulpe.model.annotations.GenerateSuffix">
		     <#local isSuffix=true>
	    </#if>
	</#list>
	<#if isSuffix==true>
		<#return method.simpleName + type.simpleName?replace("Manager", "")>
	<#else>
		<#return method.simpleName>
	</#if>
</#function>

<#function getSignatureClass type>
	<#local ret=type.qualifiedName>
	<#if type.formalTypeParameters??>
		<#list type.formalTypeParameters as p>
			<#if p_index == 0>
				<#local ret = ret + "<">
			</#if>
			<#local ret = ret + resolveType(p.simpleName)>
			<#if p_has_next>
				<#local ret = ret + ",">
			<#else>
				<#local ret = ret + ">">
			</#if>
		</#list>
	</#if>
	<#return ret>
</#function>

<#assign methods = ''>
<#assign typeVariables = ''>

<#--
 -- Checks if method exists
 -->
<#function isMethodExists type method>
	<#local sign = getSignatureMethod(type, method)>
	<#local exists = false>
	<#if methods?contains(sign + ";")>
		<#local exists = true>
	</#if>
	<#assign methods = sign + ";" + methods>
	<#return exists>
</#function>

<#--
 -- Checks if type is instance of class
 -->
<#function isInstanceOf type clazz>
	<#local ret = false>
	<#if type == clazz || type?index_of(clazz) != -1>
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

<#function hasIgnoreManager type>
	<#local ignore=false>
	<#list type.annotations?keys as entry>
		<#if entry == "org.vulpe.model.annotations.IgnoreManager">
		     <#local ignore=true>
	    </#if>
	</#list>
	<#return ignore>
</#function>

<#--
 -- Make loop in valid classes
 -->
<#macro forAllValidClasses>
	<@forAllTypes var="type">
		<#if type?last_index_of("Manager") != -1>
			<#if type.isPublic() && type.isClass() && !type.isAbstract() && isInstanceOf(type, "org.vulpe.model.services.manager.impl.VulpeBaseManager") && !hasIgnoreManager(type)>
				<#assign typeVariables = findTypeVariables(type, type.superclass)>
				<#local signatureClass = getSignatureClass(type)>
				<#nested type signatureClass>
			</#if>
		</#if>
	</@forAllTypes>
</#macro>

<#--
 -- Make loop in valid methods
 -->
<#macro forAllValidMethods>
	<@forAllValidClasses ; type, signatureClass>
		<#local classTransaction = 'false'>
		<@ifHasAnnotation declaration=type var="transaction" annotation="org.vulpe.model.annotations.TransactionType">
			<#local classTransaction = transaction.value>
		</@ifHasAnnotation>
		<@forAllMethods var="method" declaration=type includeSuperclasses=true>
			<#local isPublic=false>
			<#foreach mod in method.getModifiers()>
				<#if mod.toString()=="public">
				     <#local isPublic=true>
			    </#if>
			</#foreach>
			<#if !method.static && method.isGetter()==false && method.isSetter()==false && isPublic==true && method.declaringType != 'java.lang.Object'>
				<#local isDeprecated=false>
				<#list method.annotations?keys as entry>
				    <#if entry == "javax.persistence.Transient" || entry == "java.lang.Deprecated">
					     <#local isDeprecated=true>
				    </#if>
				</#list>
				<#if isDeprecated==false && !isMethodExists(type, method)>
					<#local methodTransaction = classTransaction>
					<@ifHasAnnotation declaration=method var="transaction" annotation="org.vulpe.model.annotations.TransactionType">
						<#local methodTransaction = transaction.value>
					</@ifHasAnnotation>
					<#nested type method methodTransaction getMethodName(type, method) signatureClass>
				</#if>
			</#if>
		</@forAllMethods>
	</@forAllValidClasses>
</#macro>
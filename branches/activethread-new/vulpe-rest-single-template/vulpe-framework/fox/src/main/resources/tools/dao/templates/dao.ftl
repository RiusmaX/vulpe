<#include "macros.ftl"/>
<@forAllValidDAO ; type, dao>
Generating DAO: ${dao.daoPackageName}.${dao.daoName}
<@javaSource name="${dao.daoPackageName}.${dao.daoName}">
package ${dao.daoPackageName};

import ${dao.packageName}.${dao.name};

/**
 * DAO Interface of ${dao.name}
 */
<#if dao.daoSuperclassName??>
<#if dao.inheritance>
public interface ${dao.daoName}<ENTITY_CLASS extends ${dao.name}> extends ${dao.daoSuperclassName}<ENTITY_CLASS> {
<#else>
public interface ${dao.daoName} extends ${dao.daoSuperclassName}<${dao.name}> {
</#if>
<#else>
<#if dao.inheritance>
public interface ${dao.daoName}<ENTITY_CLASS extends ${dao.name}> extends org.vulpe.model.dao.VulpeBaseCRUDDAO<ENTITY_CLASS, ${dao.idType}> {
<#else>
public interface ${dao.daoName} extends org.vulpe.model.dao.VulpeBaseCRUDDAO<${dao.name}, ${dao.idType}> {
</#if>
</#if>
	<#list dao.methods as method>
	${method.returnType} ${method.name}(
		<#list method.parameters as parameter>
		final ${parameter.type} ${parameter.name}<#if parameter_has_next>,</#if>
		</#list>) throws org.vulpe.exception.VulpeApplicationException;
	</#list>
}</@javaSource></@forAllValidDAO>
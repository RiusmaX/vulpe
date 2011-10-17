<#include "macros.ftl"/>
<@forAllValidDAO ; type, dao>
<@source type="dao-interface" override="${dao.override}" name="${dao.daoPackageName}.${dao.daoName}">
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
public interface ${dao.daoName}<ENTITY_CLASS extends ${dao.name}> extends org.vulpe.model.dao.VulpeDAO<ENTITY_CLASS, ${dao.idType}> {
<#else>
public interface ${dao.daoName} extends org.vulpe.model.dao.VulpeDAO<${dao.name}, ${dao.idType}> {
</#if>
</#if>
	<#list dao.methods as method>
	${method.returnType} ${method.name}(<#list method.parameters as parameter><#if parameter.name != "limit">final ${parameter.type} ${parameter.name}<#if parameter_has_next>, </#if></#if></#list>) throws org.vulpe.exception.VulpeApplicationException;
	</#list>
}</@source></@forAllValidDAO>
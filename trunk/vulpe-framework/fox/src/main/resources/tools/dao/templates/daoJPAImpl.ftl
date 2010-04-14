<#include "macros.ftl"/>
<@forAllValidDAO ; type, dao>
Generating DAOJPAImpl: ${dao.daoPackageName}.impl.jpa.${dao.daoName}JPAImpl
<@javaSource name="${dao.daoPackageName}.impl.jpa.${dao.daoName}JPAImpl">
package ${dao.daoPackageName}.impl.jpa;

import ${dao.packageName}.${dao.name};
import ${dao.daoPackageName}.${dao.daoName};

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO JPA implementation of ${dao.name}
 */
@Repository("${dao.daoName}")
@Transactional
<#if dao.daoSuperclassName??>
<#if dao.inheritance>
public class ${dao.daoName}JPAImpl<ENTITY_CLASS extends ${dao.name}> extends ${dao.daoPackageName}.impl.jpa.${dao.daoSuperclassSimpleName}JPAImpl<ENTITY_CLASS> implements ${dao.daoName}<ENTITY_CLASS> {
<#else>
public class ${dao.daoName}JPAImpl extends ${dao.daoPackageName}.impl.jpa.${dao.daoSuperclassSimpleName}JPAImpl<${dao.name}> implements ${dao.daoName} {
</#if>
<#else>
<#if dao.inheritance>
public class ${dao.daoName}JPAImpl<ENTITY_CLASS extends ${dao.name}> extends org.vulpe.model.dao.impl.jpa.VulpeBaseCRUDDAOJPAImpl<ENTITY_CLASS, ${dao.idType}> implements ${dao.daoName}<ENTITY_CLASS> {
<#else>
public class ${dao.daoName}JPAImpl extends org.vulpe.model.dao.impl.jpa.VulpeBaseCRUDDAOJPAImpl<${dao.name}, ${dao.idType}> implements ${dao.daoName} {
</#if>
</#if>
	<#list dao.methods as method>
	@SuppressWarnings("unchecked")
	public ${method.returnType} ${method.name}(
		<#list method.parameters as parameter>
		final ${parameter.type} ${parameter.name}<#if parameter_has_next>,</#if>
		</#list>) throws org.vulpe.exception.VulpeApplicationException {
		<#if method.parameters?has_content>
		final Map<String, Object> map = new HashMap();
		<#list method.parameters as parameter>
		map.put("${parameter.name}", ${parameter.name});
		</#list>
		</#if>
		<#if method.returnType == dao.name>
		<#if method.parameters?has_content>
		final List<${method.returnType}> list = (List<${method.returnType}>) getJpaTemplate().findByNamedQueryAndNamedParams("${dao.name}.${method.name}", map);
		<#else>
		final List<${method.returnType}> list = (List<${method.returnType}>) getJpaTemplate().findByNamedQuery("${dao.name}.${method.name}");
		</#if>
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
		<#else>
		<#if method.parameters?has_content>
		return (${method.returnType}) getJpaTemplate().findByNamedQueryAndNamedParams("${dao.name}.${method.name}", map);
		<#else>
		return (${method.returnType}) getJpaTemplate().findByNamedQuery("${dao.name}.${method.name}");
		</#if>
		</#if>
	}
	</#list>
}</@javaSource></@forAllValidDAO>
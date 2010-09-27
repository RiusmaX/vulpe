<#include "macros.ftl"/>
<@forAllValidManager ; type, manager>
Generating Manager: ${manager.managerPackageName}.${manager.name}
<@javaSource name="${manager.moduleName}.src.main.java.${manager.managerPackageName}.${manager.name}">
package ${manager.managerPackageName};

import org.springframework.stereotype.Service;

<#if !manager.managerSuperclassName??>
import ${manager.daoPackageName}.${manager.entityName}DAO;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;
</#if>
import ${manager.packageName}.${manager.entityName};

/**
 * Manager implementation of ${manager.entityName}
 */
@Service
<#if manager.managerSuperclassName??>
<#if manager.inheritance>
public class ${manager.name}<ENTITY_CLASS extends ${manager.entityName}> extends ${manager.managerSuperclassName}<ENTITY_CLASS> {
<#else>
public class ${manager.name} extends ${manager.managerSuperclassName}<${manager.entityName}> {
</#if>
<#else>
<#if manager.inheritance>
public class ${manager.name}<ENTITY_CLASS extends ${manager.entityName}> extends VulpeBaseManager<ENTITY_CLASS, ${manager.idType}, ${manager.entityName}DAO<ENTITY_CLASS>> {
<#else>
public class ${manager.name} extends VulpeBaseManager<${manager.entityName}, ${manager.idType}, ${manager.entityName}DAO> {
</#if>
</#if>

}
</@javaSource>
</@forAllValidManager>
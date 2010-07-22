<#include "macros.ftl"/>
<@forAllValidManager ; type, manager>
Generating Manager: ${manager.managerPackageName}.${manager.name}
<@javaSource name="${manager.moduleName}.src.main.java.${manager.managerPackageName}.${manager.name}">
package ${manager.managerPackageName};

import org.springframework.stereotype.Service;

import ${manager.daoPackageName}.${manager.entityName}DAO;
import ${manager.packageName}.${manager.entityName};
import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;

/**
 * Manager implementation of ${manager.entityName}
 */
@Service
public class ${manager.name} extends VulpeBaseCRUDManagerImpl<${manager.entityName}, ${manager.idType}, ${manager.entityName}DAO> {

}

</@javaSource>
</@forAllValidManager>
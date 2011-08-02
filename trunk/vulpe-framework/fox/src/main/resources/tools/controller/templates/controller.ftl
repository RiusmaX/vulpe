<#include "macros.ftl"/>
<@forAllValidController ; type, controller>
<@source type="controller" override="${controller.override}" name="controller.src.main.java.${controller.controllerPackageName}.${controller.name}Controller">
package ${controller.controllerPackageName};

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

<#if controller.details?has_content>
import org.vulpe.commons.annotations.DetailConfig;
</#if>

import org.vulpe.controller.annotations.Controller;
<#if (controller.pageSize > 0)>
import org.vulpe.controller.annotations.Select;
</#if>
<#if controller.tabularDespiseFields?has_content>
import org.vulpe.controller.annotations.Tabular;
</#if>

import ${controller.packageName}.${controller.entityName};
import ${controller.servicePackageName}.${controller.moduleName?capitalize}Service;
import ${controller.projectPackageName}.controller.ApplicationBaseController;


/**
 * Controller implementation of ${controller.entityName}
 */
@Component("${controller.moduleName}.${controller.name}Controller")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = ${controller.moduleName?capitalize}Service.class<#if controller.details?has_content>, detailsConfig = { <#list controller.details as detail>@DetailConfig(<#if detail.name?has_content>name = "${detail.name}"</#if><#if detail.propertyName?has_content>, propertyName = "${detail.propertyName}"</#if><#if detail.despiseFields?has_content>, despiseFields = "${detail.despiseFields}"</#if><#if (detail.startNewDetails > 0)>, startNewDetails = ${detail.startNewDetails}</#if><#if (detail.newDetails > 0)>, newDetails = ${detail.newDetails}</#if><#if detail.parentDetailName?has_content>, parentDetailName = "${detail.parentDetailName}"</#if>)${detail.next}</#list> }</#if><#if (controller.pageSize > 0)>, select = @Select(pageSize = ${controller.pageSize})</#if><#if controller.tabularDespiseFields?has_content>, tabular = @Tabular(despiseFields = { ${controller.tabularDespiseFields} }<#if controller.tabularName?has_content>, name = "${controller.tabularName}"</#if><#if controller.tabularPropertyName?has_content>, propertyName = "${controller.tabularPropertyName}"</#if><#if (controller.tabularStartNewRecords > 0)>, startNewRecords = ${controller.tabularStartNewRecords}</#if><#if (controller.tabularNewRecords > 0)>, newRecords = ${controller.tabularNewRecords}</#if>)</#if>)
public class ${controller.name}Controller extends ApplicationBaseController<${controller.entityName}, ${controller.idType}> {

}
</@source>
</@forAllValidController>
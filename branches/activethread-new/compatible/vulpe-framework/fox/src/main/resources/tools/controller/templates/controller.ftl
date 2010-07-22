<#include "macros.ftl"/>
<@forAllValidController ; type, controller>
<#list controller.types as t>
<#if t == 'SELECT'>
Generating Controller: ${controller.controllerPackageName}.${controller.name}SelectController
<@javaSource name="${controller.moduleName}.src.main.java.${controller.controllerPackageName}.${controller.name}SelectController">
package ${controller.controllerPackageName};

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import ${controller.packageName}.${controller.name};
import ${controller.servicePackageName}.${controller.moduleName?capitalize}Services;
import ${controller.projectPackageName}.controller.ApplicationBaseController;


/**
 * Controller implementation of ${controller.name}
 */
@Component("${controller.moduleName}.${controller.name}Select")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(controllerType = ControllerType.SELECT, serviceClass = ${controller.moduleName?capitalize}Services.class<#if (controller.pageSize > 0)>, pageSize = ${controller.pageSize}</#if>)
public class ${controller.name}SelectController extends ApplicationBaseController<${controller.name}, ${controller.idType}> {

}
</@javaSource>
<#elseif t == 'CRUD'>
Generating Controller: ${controller.controllerPackageName}.${controller.name}CRUDController
<@javaSource name="${controller.moduleName}.src.main.java.${controller.controllerPackageName}.${controller.name}CRUDController">
package ${controller.controllerPackageName};

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
<#if controller.details?has_content>
import org.vulpe.commons.annotations.DetailConfig;
</#if>
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import ${controller.packageName}.${controller.name};
import ${controller.servicePackageName}.${controller.moduleName?capitalize}Services;
import ${controller.projectPackageName}.controller.ApplicationBaseController;


/**
 * Controller implementation of ${controller.name}
 */
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("${controller.moduleName}.${controller.name}CRUD")
@Controller(controllerType = ControllerType.CRUD, serviceClass = ${controller.moduleName?capitalize}Services.class<#if controller.details?has_content>, detailsConfig = { <#list controller.details as detail>@DetailConfig(<#if detail.name?has_content>name = "${detail.name}"</#if><#if detail.propertyName?has_content>, propertyName = "${detail.propertyName}"</#if><#if detail.despiseFields?has_content>, despiseFields = "${detail.despiseFields}"</#if><#if (detail.startNewDetails > 0)>, startNewDetails = ${detail.startNewDetails}</#if><#if (detail.newDetails > 0)>, newDetails = ${detail.newDetails}</#if><#if detail.parentDetailName?has_content>, parentDetailName = "${detail.parentDetailName}"</#if>)${detail.next}</#list> }</#if>)
public class ${controller.name}CRUDController extends ApplicationBaseController<${controller.name}, ${controller.idType}> {

}
</@javaSource>
<#elseif t == 'TABULAR'>
Generating Controller: ${controller.controllerPackageName}.${controller.name}TabularController
<@javaSource name="${controller.moduleName}.src.main.java.${controller.controllerPackageName}.${controller.name}TabularController">
package ${controller.controllerPackageName};

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import ${controller.packageName}.${controller.name};
import ${controller.servicePackageName}.${controller.moduleName?capitalize}Services;
import ${controller.projectPackageName}.controller.ApplicationBaseController;


/**
 * Controller implementation of ${controller.name}
 */
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("${controller.moduleName}.${controller.name}Tabular")
@Controller(controllerType = ControllerType.TABULAR, serviceClass = ${controller.moduleName?capitalize}Services.class<#if controller.tabularName?has_content>, tabularName = "${controller.tabularName}"</#if><#if controller.tabularPropertyName?has_content>, tabularPropertyName = "${controller.tabularPropertyName}"</#if><#if (controller.tabularStartNewDetails > 0)>, tabularStartNewDetails = ${controller.tabularStartNewDetails}</#if><#if (controller.tabularNewDetails > 0)>, tabularNewDetails = ${controller.tabularNewDetails}</#if><#if controller.tabularDespiseFields?has_content>, tabularDespiseFields = { ${controller.tabularDespiseFields} }</#if>)
public class ${controller.name}TabularController extends ApplicationBaseController<${controller.name}, ${controller.idType}> {

}
</@javaSource>
</#if>
</#list>
</@forAllValidController>
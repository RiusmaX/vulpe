<#include "macros.ftl"/>
<@forAllValidController ; type, controller>
<#list controller.types as t>
<#if t == 'SELECT'>
Generating Controller: ${controller.controllerPackageName}.${controller.name}SelectAction
<@javaSource name="${controller.controllerPackageName}.${controller.name}SelectAction">
package ${controller.controllerPackageName};

import ${controller.packageName}.${controller.name};
import ${controller.servicePackageName}.${controller.moduleName?capitalize}Services;
import org.vulpe.controller.struts.action.VulpeBaseAction;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;


/**
 * Controller implementation of ${controller.name}
 */
@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.SELECT, serviceClass = ${controller.moduleName?capitalize}Services.class<#if (controller.pageSize > 0)>, pageSize = ${controller.pageSize}</#if>)
public class ${controller.name}SelectAction extends VulpeBaseAction<${controller.name}, ${controller.idType}> {

}
</@javaSource>
<#elseif t == 'CRUD'>
Generating Controller: ${controller.controllerPackageName}.${controller.name}CRUDAction
<@javaSource name="${controller.controllerPackageName}.${controller.name}CRUDAction">
package ${controller.controllerPackageName};

import ${controller.packageName}.${controller.name};
import ${controller.servicePackageName}.${controller.moduleName?capitalize}Services;
import org.vulpe.controller.struts.action.VulpeBaseAction;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;


/**
 * Controller implementation of ${controller.name}
 */
@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.CRUD, serviceClass = ${controller.moduleName?capitalize}Services.class<#if controller.details?has_content>, detailsConfig = { <#list controller.details as detail>@DetailConfig(<#if detail.name?has_content>name = "${detail.name}"</#if><#if detail.propertyName?has_content>, propertyName = "${detail.propertyName}"</#if><#if detail.despiseFields?has_content>, despiseFields = "${detail.despiseFields}"</#if><#if (detail.detailNews > 0)>, detailNews = ${detail.detailNews}</#if><#if detail.parentDetailName?has_content>, parentDetailName = "${detail.parentDetailName}"</#if>)${detail.next}</#list> }</#if>)
public class ${controller.name}CRUDAction extends VulpeBaseAction<${controller.name}, ${controller.idType}> {

}
</@javaSource>
<#elseif t == 'TABULAR'>
Generating Controller: ${controller.controllerPackageName}.${controller.name}TabularAction
<@javaSource name="${controller.controllerPackageName}.${controller.name}TabularAction">
package ${controller.controllerPackageName};

import ${controller.packageName}.${controller.name};
import ${controller.servicePackageName}.${controller.moduleName?capitalize}Services;
import org.vulpe.controller.struts.action.VulpeBaseAction;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;


/**
 * Controller implementation of ${controller.name}
 */
@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.TABULAR, serviceClass = ${controller.moduleName?capitalize}Services.class<#if controller.tabularName?has_content>, tabularName = "${controller.tabularName}"</#if><#if controller.tabularPropertyName?has_content>, tabularPropertyName = "${controller.tabularPropertyName}"</#if><#if (controller.tabularDetailNews > 0)>, tabularDetailNews = ${controller.tabularDetailNews}</#if><#if controller.tabularDespiseFields?has_content>, tabularDespiseFields = { ${controller.tabularDespiseFields} }</#if>)
public class ${controller.name}TabularAction extends VulpeBaseAction<${controller.name}, ${controller.idType}> {

}
</@javaSource>
</#if>
</#list>
</@forAllValidController>
<#include "macros.ftl"/>
Generating ServicePOJO: ${basePackageName}.services.impl.pojo.${baseClassName}ServicePOJO
<@javaSource name="${basePackageName}.services.impl.pojo.${baseClassName}ServicePOJO">
package ${basePackageName}.services.impl.pojo;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import ${basePackageName}.services.${baseClassName}Service;

/**
 * Service implementation of component "${baseClassName}".
 *
 * @author Vulpe Framework
 */
@Service("${baseClassName}Service")
@Transactional
public class ${baseClassName}ServicePOJO implements ${baseClassName}Service {
	/** Logger */
	private static final Logger LOG = Logger.getLogger(${baseClassName}ServicePOJO.class.getName());

<@forAllValidClasses ; type, signatureClass>
	@Qualifier("${type.simpleName?uncap_first}")
	@Autowired
	private ${signatureClass} ${type.simpleName?uncap_first};

</@forAllValidClasses>

<@forAllValidMethods ; type, method, methodTransaction, methodName, signatureClass>
	<#if methodTransaction?? && methodTransaction != '' && methodTransaction != 'false'>
	@Transactional(<#if methodTransaction == 'NOT_SUPPORTED'>readOnly = true<#else>propagation = Propagation.${methodTransaction}</#if>)
	</#if>
	public ${getSignatureMethod(type, method)} {
		long milliseconds = 0;
		if (LOG.isDebugEnabled()) {
			milliseconds = System.currentTimeMillis();
			LOG.debug("Method ${methodName} - Start");
		}

		<#if method.returnType != 'void'>final ${resolveType(method.returnType)} result = </#if>${type.simpleName?uncap_first}.${method.simpleName}(<#list method.parameters as p><#if p_index &gt; 0>, </#if>${p.simpleName}</#list>);

		if (LOG.isDebugEnabled()) {
			LOG.debug("Method ${methodName} - End");
			LOG.debug("Operation executed in "  + (System.currentTimeMillis() - milliseconds) + "ms");
		}
		<#if method.returnType != 'void'>
		return result;
		</#if>
	}

</@forAllValidMethods>
}</@javaSource>
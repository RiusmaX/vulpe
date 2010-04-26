<#include "macros.ftl"/>
Generating ServicesPOJOImpl: ${basePackageName}.services.impl.pojo.${baseClassName}ServicesPOJOImpl
<@javaSource name="${basePackageName}.services.impl.pojo.${baseClassName}ServicesPOJOImpl">
package ${basePackageName}.services.impl.pojo;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import ${basePackageName}.services.${baseClassName}Services;

/**
 * Services implementation of component "${baseClassName}".
 *
 * @author Active Thread Framework
 */
@Service("${baseClassName}Services")
@Transactional
public class ${baseClassName}ServicesPOJOImpl implements ${baseClassName}Services {
	/** Logger */
	private static final Logger LOG = Logger.getLogger(${baseClassName}ServicesPOJOImpl.class.getName());

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
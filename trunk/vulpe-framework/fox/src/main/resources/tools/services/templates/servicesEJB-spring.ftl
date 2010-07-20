<#include "macros.ftl"/>
Generating ServiceEJB: ${basePackageName}.services.impl.ejb.${baseClassName}ServiceEJB
<@javaSource name="${basePackageName}.services.impl.ejb.${baseClassName}ServiceEJB">
package ${basePackageName}.services.impl.ejb;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.interceptor.Interceptors;

<#if JBOSS == true>
import org.jboss.ejb3.annotation.RemoteBinding;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.apache.log4j.Logger;

import ${basePackageName}.services.${baseClassName}Service;

/**
 * Service implementation of component "${baseClassName}".
 *
 * @author Vulpe Framework
 */
@Stateless(name = "${baseClassName}Service")
<#if JBOSS == true>
@RemoteBinding(jndiBinding = "${baseClassName}Service")
</#if>
@Remote(${baseClassName}Service.class)
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class ${baseClassName}ServiceEJB implements ${baseClassName}Service {
	/** Logger */
	private static final Logger LOG = Logger.getLogger(${baseClassName}ServiceEJB.class.getName() );

<@forAllValidClasses ; type, signatureClass>
	@Qualifier("${type.simpleName?uncap_first}")
	@Autowired
	private ${signatureClass} ${type.simpleName?uncap_first};

</@forAllValidClasses>
<@forAllValidMethods ; type, method, methodTransaction, methodName, signatureClass>
	<#if methodTransaction?? && methodTransaction != '' && methodTransaction != 'false'>
	@TransactionAttribute(TransactionAttributeType.${methodTransaction})
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
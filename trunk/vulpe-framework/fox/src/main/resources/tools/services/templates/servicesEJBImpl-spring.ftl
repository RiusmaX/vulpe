<#include "macros.ftl"/>
Generating ServicesEJBImpl: ${basePackageName}.services.impl.ejb.${baseClassName}ServicesEJBImpl
<@javaSource name="${basePackageName}.services.impl.ejb.${baseClassName}ServicesEJBImpl">
package ${basePackageName}.services.impl.ejb;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.interceptor.Interceptors;

<#if JBOSS == true>
import org.jboss.annotation.ejb.RemoteBinding;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.apache.log4j.Logger;

import ${basePackageName}.services.${baseClassName}Services;

/**
 * Services implementation of component "${baseClassName}".
 *
 * @author Active Thread Framework
 */
@Stateless(name = "${baseClassName}Services")
<#if JBOSS == true>
@RemoteBinding(jndiBinding = "${baseClassName}Services")
</#if>
@Remote(${baseClassName}Services.class)
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class ${baseClassName}ServicesEJBImpl implements ${baseClassName}Services {
	/** Logger */
	private static final Logger LOG = Logger.getLogger(${baseClassName}ServicesEJBImpl.class.getName() );

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
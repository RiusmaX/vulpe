<#include "macros.ftl"/>
Generating Interface: ${basePackageName}.services.${baseClassName}Services
<@javaSource name="${basePackageName}.services.${baseClassName}Services">
package ${basePackageName}.services;

import javax.ejb.Remote;

import org.vulpe.model.services.Services;

/**
 * Service Interface of ${baseClassName}Services.
 * 
 * @author Active Thread Framework
 */
@Remote
public interface ${baseClassName}Services extends Services {
<@forAllValidMethods ; type, method, methodTransaction, methodName, signatureClass>
<@toJavaDoc doc=method.docComment ident="	"/>
	${getSignatureMethod(type, method)};
</@forAllValidMethods>
}</@javaSource>
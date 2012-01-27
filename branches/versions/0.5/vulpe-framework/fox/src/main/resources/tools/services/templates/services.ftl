<#include "macros.ftl"/>
<@source type="service-interface" override="true" name="${basePackageName}.services.${baseClassName}Service">
package ${basePackageName}.services;

import javax.ejb.Remote;

import org.vulpe.model.services.VulpeService;

/**
 * Service Interface of ${baseClassName}.
 *
 * @author Vulpe Framework
 */
@Remote
public interface ${baseClassName}Service extends VulpeService {
<@forAllValidMethods ; type, method, methodTransaction, methodName, signatureClass>
<@toJavaDoc doc=method.docComment ident="	"/>
	${getSignatureMethod(type, method)};
</@forAllValidMethods>
}</@source>
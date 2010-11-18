<#include "macros.ftl"/>
Generating ServiceFactory: ${basePackageName}.services.${baseClassName}ServiceFactory
<@javaSource name="${basePackageName}.services.${baseClassName}ServiceFactory">
package ${basePackageName}.services;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import org.vulpe.commons.factory.Factory;
import org.vulpe.commons.factory.VulpeFactoryLocator;
import org.vulpe.commons.beans.AbstractVulpeBeanFactory;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.services.LookupType;

public class ${baseClassName}ServiceFactory implements Factory<${baseClassName}Service> {
	private static final Logger LOG = Logger.getLogger( ${baseClassName}ServiceFactory.class.getName() );

	public static ${baseClassName}ServiceFactory getInstance(){
		if (LOG.isDebugEnabled()) {
			LOG.debug("Getting ${baseClassName}ServiceFactory");
		}
		return FactoryLocator.getInstance().getFactory(${baseClassName}ServiceFactory.class);
	}

	private java.util.Properties props = null;
	private ${baseClassName}Service services = null;

	public ${baseClassName}ServiceFactory() {
		try {
			props = org.vulpe.commons.file.FileUtil.getInstance().getResourceProperties("${baseClassName?uncap_first}.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ${baseClassName}Service instance() {
		LookupType lt = LookupType.valueOf(props.getProperty("services.factory.lookup.type"));
		if (LOG.isDebugEnabled()) {
			LOG.debug("Getting ${baseClassName}");
		}
		try {
			if (LookupType.EJB.equals(lt)) {
				if (services == null) {
					Context ctx = new InitialContext(props);
					services = (${baseClassName}Service) ctx.lookup("${baseClassName}ServiceRemote");
				}
				if (LOG.isDebugEnabled()) {
					LOG.debug("Retrieve ${baseClassName} EJB");
				}
				return services;
			} else if (LookupType.POJO.equals(lt)) {
				try{
					return BeanFactory.getInstance().getBean("${baseClassName}ServicePOJO");
				} finally {
					if (LOG.isDebugEnabled()) {
						LOG.debug("Retrieve ${baseClassName} POJO");
					}
				}
			} else if (LookupType.WS.equals(lt)) {
				try{
					return BeanFactory.getInstance().getBean("${baseClassName}ServiceWSDelegate");
				} finally {
					if (LOG.isDebugEnabled()) {
						LOG.debug("Retrieve ${baseClassName} WS");
					}
				}
			}
			return null;
		} catch(Exception e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Not retrieve ${baseClassName} ".concat(lt.toString()));
			}
			if (e instanceof SystemException) {
				throw (SystemException)e;
			} else {
				throw new SystemException(e);
			}
		}
	}
}</@javaSource>
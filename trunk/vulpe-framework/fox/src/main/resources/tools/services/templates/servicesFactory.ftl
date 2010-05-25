<#include "macros.ftl"/>
Generating ServicesFactory: ${basePackageName}.services.${baseClassName}ServicesFactory
<@javaSource name="${basePackageName}.services.${baseClassName}ServicesFactory">
package ${basePackageName}.services;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import org.vulpe.commons.factory.Factory;
import org.vulpe.commons.factory.VulpeFactoryLocator;
import org.vulpe.commons.beans.AbstractVulpeBeanFactory;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.services.LookupType;

public class ${baseClassName}ServicesFactory implements Factory<${baseClassName}Services> {
	private static final Logger LOG = Logger.getLogger( ${baseClassName}ServicesFactory.class.getName() );

	public static ${baseClassName}ServicesFactory getInstance(){
		if (LOG.isDebugEnabled()) {
			LOG.debug("Getting ${baseClassName}ServicesFactory");
		}
		return FactoryLocator.getInstance().getFactory(${baseClassName}ServicesFactory.class);
	}

	private java.util.Properties props = null;
	private ${baseClassName}Services services = null;

	public ${baseClassName}ServicesFactory() {
		try {
			props = org.vulpe.commons.file.FileUtil.getInstance().getResourceProperties("${baseClassName?uncap_first}.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ${baseClassName}Services instance() {
		LookupType lt = LookupType.valueOf(props.getProperty("services.factory.lookup.type"));
		if (LOG.isDebugEnabled()) {
			LOG.debug("Getting ${baseClassName}");
		}
		try {
			if (LookupType.EJB.equals(lt)) {
				if (services == null) {
					Context ctx = new InitialContext(props);
					services = (${baseClassName}Services) ctx.lookup("${baseClassName}ServicesRemote");
				}
				if (LOG.isDebugEnabled()) {
					LOG.debug("Retrieve ${baseClassName} EJB");
				}
				return services;
			} else if (LookupType.POJO.equals(lt)) {
				try{
					return BeanFactory.getInstance().getBean("${baseClassName}ServicesPOJOImpl");
				} finally {
					if (LOG.isDebugEnabled()) {
						LOG.debug("Retrieve ${baseClassName} POJO");
					}
				}
			} else if (LookupType.WS.equals(lt)) {
				try{
					return BeanFactory.getInstance().getBean("${baseClassName}ServicesWSDelegate");
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
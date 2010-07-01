<#include "macros.ftl"/>
<@forAllValidDAO ; type, dao>
Generating DAOFactory: ${dao.daoPackageName}.${dao.daoName}Factory
<@javaSource name="${dao.daoPackageName}.${dao.daoName}Factory">
package ${dao.daoPackageName};

import org.apache.log4j.Logger;
import org.vulpe.commons.factory.Factory;
import org.vulpe.commons.factory.VulpeFactoryLocator;
import org.vulpe.commons.beans.AbstractVulpeBeanFactory;

<#if dao.inheritance>
public class ${dao.daoName}Factory implements Factory<${dao.daoName}<${dao.packageName}.${dao.name}>>{
<#else>
public class ${dao.daoName}Factory implements Factory<${dao.daoName}>{
</#if>
	private static final Logger LOG = Logger.getLogger( ${dao.daoName}Factory.class.getName() );

	public static ${dao.daoName}Factory getInstance(){
		if (LOG.isDebugEnabled()) {
			LOG.debug("Obtendo ${dao.daoName}Factory");
		}
		return FactoryLocator.getInstance().getFactory(${dao.daoName}Factory.class);
	}

	public ${dao.daoName}Factory(){

	}

	<#if dao.inheritance>
	public ${dao.daoName}<${dao.packageName}.${dao.name}> instance() {
	<#else>
	public ${dao.daoName} instance() {
	</#if>
		if (LOG.isDebugEnabled()) {
			LOG.debug("Obtendo ${dao.daoName}");
		}
		return BeanFactory.getInstance().getBean("${dao.daoName}");
	}
}</@javaSource></@forAllValidDAO>
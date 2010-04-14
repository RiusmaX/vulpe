<#include "macros.ftl"/>
<#include "services.ftl"/>
<#if EJB == true>
	<#if springServiceLookup == true>
		<#include "servicesEJBImpl-spring.ftl"/>
	<#else>
		<#include "servicesEJBImpl.ftl"/>
	</#if>
</#if>
<#if POJO == true>
	<#include "servicesPOJOImpl.ftl"/>
</#if>
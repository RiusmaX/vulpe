<#include "macros.ftl"/>
<#include "services.ftl"/>
<#if EJB == true>
	<#if springServiceLookup == true>
		<#include "servicesEJB-spring.ftl"/>
	<#else>
		<#include "servicesEJB.ftl"/>
	</#if>
</#if>
<#if POJO == true>
	<#include "servicesPOJO.ftl"/>
</#if>
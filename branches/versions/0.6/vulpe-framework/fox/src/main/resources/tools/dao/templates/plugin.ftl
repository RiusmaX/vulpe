<#include "macros.ftl"/>
<#include "dao.ftl"/>
<#if DB4O == true>
	<#include "daoDB4O.ftl"/>
<#else>
	<#include "daoJPA.ftl"/>
</#if>
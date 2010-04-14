<#include "macros.ftl"/>
<#include "dao.ftl"/>
<#if DB4O == true>
	<#include "daoDB4OImpl.ftl"/>
<#else>
	<#include "daoJPAImpl.ftl"/>
</#if>
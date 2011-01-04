<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagBegin.jsp" %>
<c:if test="${not empty property && empty name}"><c:set var="name" value="${targetName}.${property}"/></c:if>
<c:if test="${not empty property && empty value}">
	<c:set var="valueEL" value="${'${'}targetValue.${property}${'}'}"/>
	<c:set var="value" value="${util:eval(pageContext, valueEL)}"/>
</c:if>
<c:if test="${not empty mask}">
<c:choose>
	<c:when test="${mask eq 'M' || mask eq 'MONEY' || mask eq 'C' || mask eq 'CURRENCY'}">
	<script type="text/javascript">
		jQuery(function($){
			vulpe.util.get('${elementId}').maskMoney({symbol:"R$",decimal:",",thousands:"."});
		});
	</script>
		<c:set var="style" value="text-align:right; ${style}"/>
	</c:when>
	<c:when test="${mask eq 'N' || mask eq 'NUMBER' || mask eq 'I' || mask eq 'INTEGER'}">
	<script type="text/javascript">
		jQuery(function($){
			vulpe.util.get('${elementId}').numeric();
		});
	</script>
		<c:set var="style" value="text-align:right; ${style}"/>
	</c:when>
	<c:otherwise>
	<script type="text/javascript">
		jQuery(function($){
			vulpe.util.get('${elementId}').mask("${mask}");
		});
	</script>
	<c:if test="${empty size}">
		<c:set var="size" value="${fn:length(mask)}"/>
	</c:if>
	<c:if test="${empty maxlength}">
		<c:set var="maxlength" value="${size}"/>
	</c:if>
	</c:otherwise>
</c:choose>
</c:if>
<c:if test="${empty autocompleteMinLength}">
	<c:set var="autocompleteMinLength" value="3"/>
</c:if>
<c:if test="${empty size && not empty maxlength}">
	<c:set var="size" value="${maxlength}"/>
</c:if>
<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
	<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
</c:if>
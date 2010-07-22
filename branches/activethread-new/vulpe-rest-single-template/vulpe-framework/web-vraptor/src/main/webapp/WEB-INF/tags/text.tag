<%@include file="/WEB-INF/protected-jsp/commons/tags/tagAttributes.jsp" %>
<%@ attribute name="property" required="false" rtexprvalue="true" %>
<%@ attribute name="mask" required="false" rtexprvalue="true" %>
<%@ attribute name="upperCase" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="maxlength" required="false" rtexprvalue="true" %>
<%@ attribute name="readonly" required="false" rtexprvalue="true" %>
<%@ attribute name="size" required="false" rtexprvalue="true" %>
<%@ attribute name="onselect" required="false" rtexprvalue="true" %>

<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="show" value="${true}"/>
<c:if test="${not empty logged && logged eq true && util:isLogged(pageContext) eq false}">
	<c:set var="show" value="${false}"/>
</c:if>
<c:if test="${not empty role && util:isRole(pageContext, role) eq false}">
	<c:set var="show" value="${false}"/>
</c:if>

<c:if test="${show eq true}">
	<%@include file="/WEB-INF/protected-jsp/commons/tags/beginTag.jsp" %>
	
	<c:if test="${not empty property && empty name}">
		<c:set var="name" value="${targetName}.${property}"/>
	</c:if>
	
	<c:if test="${not empty property && empty value}">
		<c:set var="valueEL" value="${'${'}targetValue.${property}${'}'}"/>
		<c:set var="value" value="${util:eval(pageContext, valueEL)}"/>
	</c:if>
	
	<c:if test="${not empty mask}">
	<c:choose>
		<c:when test="${mask eq 'N'}">
		<script type="text/javascript">
			jQuery(function($){
				vulpe.util.get('${elementId}').maskMoney({symbol:"R$",decimal:",",thousands:"."});
			});
		</script>
			<c:set var="style" value="text-align:right; ${style}"/>
		</c:when>
		<c:when test="${mask eq 'I'}">
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
	
	<c:if test="${empty size && not empty maxlength}">
		<c:set var="size" value="${maxlength}"/>
	</c:if>
	
	<c:if test="${not empty property && util:isFieldInValidator(targetValue, property)}">
		<c:set var="onblur" value="validate${fn:toUpperCase(fn:substring(property, 0, 1))}${fn:substring(property, 1, -1)}(); ${onblur}"/>
	</c:if>
	
	<c:if test="${onlyToSee}">
		<c:set var="showAsText" value="${true}"/>
	</c:if>
	<c:choose>
		<c:when test="${showAsText}">
			<s:label theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" cssStyle="${style}" cssClass="vulpeSimpleLabel ${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}"/>
		</c:when>
		<c:otherwise>
			<s:textfield theme="simple" name="${name}" accesskey="${accesskey}" disabled="${disabled}" maxlength="${maxlength}" onblur="${onblur}" onchange="${onchange}" onclick="${onclick}" ondblclick="${ondblclick}" onfocus="${onfocus}" onkeydown="${onkeydown}" onkeypress="${onkeypress}" onkeyup="${onkeyup}" onmousedown="${onmousedown}" onmousemove="${onmousemove}" onmouseout="${onmouseout}" onmouseover="${onmouseover}" onmouseup="${onmouseup}" onselect="${onselect}" readonly="${readonly}" size="${size}" cssStyle="${style}" cssClass="${styleClass}" id="${elementId}" tabindex="${tabindex}" title="${title}" value="${value}" />
		</c:otherwise>
	</c:choose>
	<jsp:doBody/>
	<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
	<script type="text/javascript">
		jQuery(function($){
			<c:if test="${upperCase && empty mask}">
			vulpe.util.get('${elementId}').bestupper();
			</c:if>
			vulpe.util.get('${elementId}').focus(function() {
				$(this).effect("highlight");
			});
		});
	</script>
</c:if>
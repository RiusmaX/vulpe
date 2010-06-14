<%@ attribute name="labelKey" required="false" rtexprvalue="true" %>
<%@ attribute name="value" required="false" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="property" required="false" rtexprvalue="true" %>
<%@ attribute name="align" required="false" rtexprvalue="true" %>
<%@ attribute name="labelAlign" required="false" rtexprvalue="true" %>
<%@ attribute name="style" required="false" rtexprvalue="true" %>
<%@ attribute name="labelStyle" required="false" rtexprvalue="true" %>
<%@ attribute name="width" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="role" required="false" rtexprvalue="true" %>
<%@ attribute name="logged" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="sort" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="alias" required="false" rtexprvalue="true" %>
<%@ attribute name="colspan" required="false" rtexprvalue="true" %>
<%@ attribute name="showBodyInHeader" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="isImage" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="imageWidth" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="imageHeight" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="onmouseover" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="onmouseout" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="onclick" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="listName" required="false" rtexprvalue="true" %>
<%@ attribute name="booleanTo" required="false" rtexprvalue="true" type="java.lang.String" %>

<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="exibe" value="${true}"/>
<c:if test="${not empty logged && logged eq true && util:isLogged(pageContext) eq false}">
	<c:set var="exibe" value="${false}"/>
</c:if>
<c:if test="${not empty role && util:isRole(pageContext, role) eq false}">
	<c:set var="exibe" value="${false}"/>
</c:if>

<c:if test="${empty targetName}">
	<c:if test="${empty targetConfig}">
		<c:set var="targetName" value="entity"/>
	</c:if>
	<c:if test="${not empty targetConfig}">
		<c:set var="targetNameEL" value="${'${'}${targetConfig.baseName}_status.index${'}'}"/>
		<c:set var="targetName" value="${targetConfigPropertyName}[${util:eval(pageContext, targetNameEL)}]"/>
	</c:if>
</c:if>

<c:if test="${empty targetValue}">
	<c:if test="${empty targetConfig}">
		<c:set var="targetValueEL" value="${'${'}${targetName}${'}'}"/>
		<c:set var="targetValue" value="${util:eval(pageContext, targetValueEL)}"/>
	</c:if>
	<c:if test="${not empty targetConfig}">
		<c:set var="targetValueEL" value="${'${'}${targetConfig.baseName}_item${'}'}"/>
		<c:set var="targetValue" value="${util:eval(pageContext, targetValueEL)}"/>
	</c:if>
</c:if>

<c:if test="${exibe eq true}">
	<c:if test="${empty isHeader_table_tag || isHeader_table_tag}">
		<c:if test="${not empty labelKey}">
			<fmt:message key="${labelKey}" var="label"/>
		</c:if>
		<c:if test="${not empty width}">
			<c:set var="width">width="${width}" </c:set>
		</c:if>
		<c:if test="${not empty labelAlign}">
			<c:set var="labelStyle" value="text-align: ${labelAlign};${labelStyle}"/>
		</c:if>
		<c:if test="${sort eq true && not empty sortPropertyInfo_table_tag && not empty property}">
			<c:if test="${empty alias}">
				<c:set var="alias" value="obj"/>
			</c:if>
			<c:choose>
			<c:when test="${vulpeUseDB4O}">
				<c:set var="idTh">id="${sortPropertyInfo_table_tag}_${property}" </c:set>
				<c:set var="label"><a href="javascript:;" onclick="javascript:vulpe.view.sortTable('${controllerConfig.formName}', '${sortPropertyInfo_table_tag}', '${property}');">${label}</a></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="idTh">id="${sortPropertyInfo_table_tag}_${alias}.${property}" </c:set>
				<c:set var="label"><a href="javascript:;" onclick="javascript:vulpe.view.sortTable('${controllerConfig.formName}', '${sortPropertyInfo_table_tag}', '${alias}.${property}');">${label}</a></c:set>
			</c:otherwise>
			</c:choose>
		</c:if>
		<th onclick="${onclick}" onmouseover="${onmouseover}" onmouseout="${onmouseout}" colspan="${colspan}" ${idTh}${width}scope="col" style="${labelStyle}">
			${label}
			<c:if test="${showBodyInHeader}">
				<jsp:doBody/>
			</c:if>
		</th>
	</c:if>
	<c:if test="${empty listName}">
		<c:set var="listName" value="entities"/>
	</c:if>
	<c:if test="${!isHeader_table_tag}">
		<c:if test="${not empty align}">
			<c:set var="style" value="text-align: ${align};${style}"/>
		</c:if>
		<c:if test="${empty value && not empty property && not empty item_table_tag}">
			<c:set var="valueEL" value="${'${'}${item_table_tag}.${property}${'}'}"/>
			<c:set var="value" value="${util:eval(pageContext, valueEL)}"/>
			<c:set var="propertyValue" value="${util:enumInField(targetValue, property, value)}"/>
			<c:if test="${not empty propertyValue}">
				<c:set var="value" value="${propertyValue}"/>
			</c:if>
		</c:if>
		<td onclick="${selectCheckOff}${onclick}" onmouseover="${onmouseover}" onmouseout="${onmouseout}" colspan="${colspan}" style="${style}">
			<c:if test="${not empty value}">
				<c:choose>
				<c:when test="${!isImage}">
					<c:choose>
					<c:when test="${not empty booleanTo}">
					${util:booleanTo(value, booleanTo)}
					</c:when>
					<c:otherwise>
					${util:toString(value)}
					</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:if test="${empty imageWidth}">
						<c:set var="imageWidth" value="50"/>
					</c:if>
					<c:if test="${empty imageHeight}">
						<c:set var="imageHeight" value="38"/>
					</c:if>
					<c:set var="statusEL" value="${'${'}${status_table_tag}${'.count}'}"/>
					<c:set var="key" value="${listName}[${util:eval(pageContext, statusEL) -1}].${property}"/>
					<center>
						<img border="0" src="${util:linkImage(pageContext, key, 'image/jpeg', '', imageWidth, null)}" width="${imageWidth}" height="${imageHeight}" class="vulpeThumb"/>
					</center>
				</c:otherwise>
				</c:choose>
			</c:if>
			<jsp:doBody/>
		</td>
	</c:if>
</c:if>
<%@ attribute name="elementId" required="false" rtexprvalue="true" %>
<%@ attribute name="onclick" required="false" rtexprvalue="true" %>
<%@ attribute name="onmouseover" required="false" rtexprvalue="true" %>
<%@ attribute name="onmouseout" required="false" rtexprvalue="true" %>
<%@ attribute name="styleClass" required="false" rtexprvalue="true" %>
<%@ attribute name="style" required="false" rtexprvalue="true" %>
<%@ attribute name="rowspan" required="false" rtexprvalue="true" %>
<%@ attribute name="view" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<%@ attribute name="deleteValue" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="deleteName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="deleteLabelKey" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="deleteActionName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="deleteFormName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="deleteLayerFields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="deleteLayer" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="deleteBeforeJs" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="deleteAfterJs" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="deleteRole" required="false" rtexprvalue="true" %>
<%@ attribute name="deleteLogged" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<%@ attribute name="updateValue" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="updateLabelKey" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="updateActionName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="updateFormName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="updateLayerFields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="updateLayer" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="updateBeforeJs" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="updateAfterJs" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="updateRole" required="false" rtexprvalue="true" %>
<%@ attribute name="updateLogged" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<%@ attribute name="popupProperties" required="false" rtexprvalue="true" type="java.lang.String" %>

<%@ attribute name="role" required="false" rtexprvalue="true" %>
<%@ attribute name="logged" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<%@ attribute name="showLine" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>

<c:set var="exibe" value="${true}"/>
<c:if test="${not empty logged && logged eq true && util:isLogged(pageContext) eq false}">
	<c:set var="exibe" value="${false}"/>
</c:if>
<c:if test="${not empty role && util:isRole(pageContext, role) eq false}">
	<c:set var="exibe" value="${false}"/>
</c:if>

<c:if test="${exibe eq true}">
	<c:if test="${empty showLine}">
		<c:set var="showLine" value="${true}"/>
	</c:if>

	<c:set var="status_row_tag" value="${null}"/>
	<c:if test="${not empty status_table_tag}">
		<c:set var="status_row_tagEL" value="${'${'}${status_table_tag}${'}'}"/>
		<c:set var="status_row_tag" value="${util:eval(pageContext, status_row_tagEL)}"/>
	</c:if>

	<c:if test="${empty styleClass && not empty status_row_tag}">
		<c:choose>
			<c:when test="${(status_row_tag.index % 2) == 0}">
				<c:set var="styleClass" value="vulpeLineOn"/>
			</c:when>
			<c:otherwise>
				<c:set var="styleClass" value="vulpeLineOff"/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="${empty updateValue && isSelect_table_tag && updateShow}">
		<c:set var="updateValue" value="id"/>
	</c:if>
	<c:if test="${not empty updateValue && updateValue ne 'false'}">
		<c:if test="${empty updateLabelKey}">
			<c:set var="updateLabelKey" value="vulpe.label.update"/>
		</c:if>
		<c:choose>
			<c:when test="${not empty item_table_tag}">
				<c:set var="updateValue" value="${'${'}${item_table_tag}.${updateValue}${'}'}"/>
			</c:when>
			<c:otherwise>
				<c:set var="updateValue" value=""/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:set var="deleteType" value=""/>
	<c:if test="${empty deleteValue}">
		<c:choose>
			<c:when test="${isSelect_table_tag && deleteShow}">
				<c:set var="deleteValue" value="id"/>
				<c:set var="deleteType" value="select"/>
			</c:when>
			<c:when test="${not empty targetConfig}">
				<c:set var="deleteShowEL" value="${'${'}deleteShow${targetConfig.baseName}${'}'}"/>
				<c:if test="${util:eval(pageContext, deleteShowEL)}">
					<c:set var="deleteValue" value="selected"/>
					<c:set var="deleteType" value="detail"/>
				</c:if>
			</c:when>
		</c:choose>
	</c:if>
	<c:if test="${not empty deleteValue && deleteValue ne 'false'}">
		<c:if test="${empty deleteName}">
			<c:set var="deleteName" value="selected"/>
		</c:if>
		<c:if test="${empty deleteLabelKey}">
			<c:set var="deleteLabelKey" value="vulpe.label.delete"/>
		</c:if>
		<c:set var="deleteValue" value="${'${'}${item_table_tag}.${deleteValue}${'}'}"/>
	</c:if>

	<c:if test="${not empty updateValue && updateValue ne 'false'}">
		<c:if test="${empty updateActionName}">
			<c:set var="updateActionName" value="${controllerConfig.primitiveControllerName}/update"/>
		</c:if>
		<c:if test="${empty updateFormName}">
			<c:set var="updateFormName" value="${controllerConfig.formName}"/>
		</c:if>
		<c:if test="${empty updateLayerFields}">
			<c:set var="updateLayerFields" value="${updateFormName}"/>
		</c:if>
		<c:if test="${empty updateLayer}">
			<c:set var="updateLayer" value="body"/>
		</c:if>
		<c:if test="${not empty updateValue}">
			<c:set var="elementId" value="${util:urlEncode(util:evalString(pageContext, updateValue))}"/>
			<c:choose>
				<c:when test="${empty onclick}">
					<c:choose>
						<c:when test="${view}">
							<c:set var="onclick" value="vulpe.view.request.submitView('${elementId}', '${updateActionName}/ajax', '${updateFormName}', '${updateLayerFields}', '${updateLayer}', '${updateBeforeJs}', '${updateAfterJs}')"/>
						</c:when>
						<c:otherwise>
							<c:set var="onclick" value="vulpe.view.request.submitUpdate('${elementId}', '${updateActionName}/ajax', '${updateFormName}', '${updateLayerFields}', '${updateLayer}', '${updateBeforeJs}', '${updateAfterJs}')"/>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${view}">
							<c:set var="onclick" value="${onclick}; vulpe.view.request.submitView('${elementId}', '${updateActionName}/ajax', '${updateFormName}', '${updateLayerFields}', '${updateLayer}', '${updateBeforeJs}', '${updateAfterJs}');"/>
						</c:when>
						<c:otherwise>
							<c:set var="onclick" value="${onclick}; vulpe.view.request.submitUpdate('${elementId}', '${updateActionName}/ajax', '${updateFormName}', '${updateLayerFields}', '${updateLayer}', '${updateBeforeJs}', '${updateAfterJs}');"/>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:if>

	<c:if test="${not empty deleteValue && deleteValue ne 'false'}">
		<c:if test="${empty deleteActionName}">
			<c:set var="deleteActionName" value="${controllerConfig.primitiveControllerName}/${deleteType == 'detail' ? 'deleteDetail' : 'delete'}"/>
		</c:if>
		<c:if test="${empty deleteFormName}">
			<c:set var="deleteFormName" value="${controllerConfig.formName}"/>
		</c:if>
		<c:if test="${empty deleteLayerFields}">
			<c:set var="deleteLayerFields" value="${deleteFormName}"/>
		</c:if>
		<c:if test="${empty deleteLayer}">
			<c:choose>
				<c:when test="${deleteType == 'detail'}">
					<c:choose>
						<c:when test="${targetConfig.baseName == 'entities'}">
							<c:set var="deleteLayer" value="body"/>
						</c:when>
						<c:otherwise>
							<c:set var="deleteLayer" value="${deleteFormName}_${targetConfig.baseName}_detail_body"/>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:set var="deleteLayer" value="${deleteFormName}_select_table"/>
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${empty deleteBeforeJs}">
			<c:set var="deleteBeforeJs" value=""/>
		</c:if>
	</c:if>

	<c:if test="${popup || not empty updateValue}">
		<c:if test="${empty onmouseover}">
			<c:set var="onmouseover" value="vulpe.view.onmouseoverRow(this);"/>
		</c:if>
		<c:if test="${empty onmouseout}">
			<c:set var="onmouseout" value="vulpe.view.onmouseoutRow(this);"/>
		</c:if>
	</c:if>

	<c:if test="${popup && !isHeader_table_tag && isSelect_table_tag && not empty popupProperties}">
		<c:set var="popupProperties" value="${fn:trim(popupProperties)}"/>
		<c:set var="valueSelectRow" value=""/>
		<c:forEach items="${fn:split(popupProperties, ',')}" var="prop" varStatus="sProp">
			<c:set var="propName" value="${prop}"/>
			<c:set var="propField" value="${prop}"/>
			<c:forEach items="${fn:split(prop, '=')}" var="propCfg" varStatus="sPropCfg">
				<c:if test="${sPropCfg.first}">
					<c:set var="propName" value="${propCfg}"/>
				</c:if>
				<c:if test="${sPropCfg.last}">
					<c:set var="propField" value="${propCfg}"/>
				</c:if>
			</c:forEach>
			<c:set var="valueRowEL" value="${'${'}${item_table_tag}.${propField}${'}'}"/>
			<c:set var="valueRow" value="${util:urlEncode(util:evalString(pageContext, valueRowEL))}"/>
			<c:choose>
				<c:when test="${sProp.first}">
					<c:set var="valueSelectRow" value="${propName}=${valueRow}"/>
				</c:when>
				<c:otherwise>
					<c:set var="valueSelectRow" value="${valueSelectRow},${propName}=${valueRow}"/>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:choose>
			<c:when test="${empty onclick}">
				<c:set var="onclick" value="vulpe.view.selectRow(this, '${valueSelectRow}');"/>
			</c:when>
			<c:otherwise>
				<c:set var="onclick" value="${onclick}; vulpe.view.selectRow('${valueSelectRow}');"/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<tr id="${elementId}" onclick="${onclick}" onmouseover="${onmouseover}" onmouseout="${onmouseout}" class="${styleClass}" style="${style}" rowspan="${rowspan}">
		<c:if test="${showLine}">
			<v:column labelKey="vulpe.label.line" width="1%">
				<c:if test="${!isHeader_table_tag}">
					${status_row_tag.index + 1}.
				</c:if>
			</v:column>
		</c:if>

		<c:if test="${!onlyToSee && not empty deleteValue && deleteValue ne 'false' && deleteType eq 'select'}">
		<c:choose>
			<c:when test="${!isHeader_table_tag}">
				<td onclick="${selectCheckOn}" align="center">
					<input type="checkbox" name="selected" value="${elementId}" tabindex="100000">
				</td>
			</c:when>
			<c:otherwise>
				<th width="10px">
					<input type="checkbox" name="selectAll" onclick="vulpe.view.markUnmarkAll(this);" tabindex="100000">
				</th>
			</c:otherwise>
		</c:choose>
		</c:if>

		<c:if test="${!onlyToSee && not empty deleteValue && deleteValue ne 'false' && deleteType eq 'detail'}">
			<c:if test="${empty isHeader_table_tag || isHeader_table_tag}">
				<%--<v:column role="${deleteRole}" logged="${deleteLogged}" labelKey="${deleteLabelKey}" width="1%"/>--%>
				<th width="10px">
					<input type="checkbox" name="selectAll" onclick="vulpe.view.markUnmarkAll(this);" tabindex="100000">
				</th>
			</c:if>
			<c:if test="${!isHeader_table_tag}">
				<v:column role="${deleteRole}" logged="${deleteLogged}" labelKey="${deleteLabelKey}" width="1%">
					<v:checkbox name="${targetConfigPropertyName}[${status_row_tag.index}].${deleteName}" fieldValue="true" paragraph="false" tabindex="100000"/>
				</v:column>
			</c:if>
		</c:if>

		<jsp:doBody/>
		<%--
		<c:if test="${not empty updateValue && updateValue ne 'false'}">
			<c:if test="${empty isHeader_table_tag || isHeader_table_tag}">
				<v:column role="${updateRole}" logged="${updateLogged}" labelKey="${updateLabelKey}" width="1%"/>
			</c:if>
			<c:if test="${!isHeader_table_tag}">
				<v:columnAction role="${updateRole}" logged="${updateLogged}" icon="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icons/button-row-edit.png" labelKey="${updateLabelKey}" javascript="vulpe.view.request.submitUpdate('${util:urlEncode(util:evalString(pageContext, updateValue))}', '${updateActionName}', '${updateFormName}', '${updateLayerFields}', '${updateLayer}', '${updateBeforeJs}', '${updateAfterJs}')" width="1%"/>
			</c:if>
		</c:if>
		--%>
		<c:if test="${not empty deleteValue && deleteValue ne 'false' && (deleteType eq 'select' || deleteType eq 'detail')}">
			<c:if test="${empty isHeader_table_tag || isHeader_table_tag}">
				<%--
				<v:column role="${deleteRole}" logged="${deleteLogged}" labelKey="${deleteLabelKey}" width="1%" showBodyInHeader="true">
					<v:action javascript="vulpe.view.request.submitDeleteSelected('${deleteActionName}', '${deleteFormName}', '${deleteLayerFields}', '${deleteLayer}', '${deleteBeforeJs}', '${deleteAfterJs}')" labelKey="vulpe.label.delete.selected" icon="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icons/button-delete-all.png"/>
				</v:column>
				 --%>
				<v:column role="${deleteRole}" logged="${deleteLogged}" width="1%" showBodyInHeader="true">
					<c:choose>
						<c:when test="${deleteType eq 'detail'}">
							<v:action javascript="vulpe.view.request.submitDeleteDetailSelected('${targetConfig.baseName}', '${deleteActionName}/ajax', '${deleteFormName}', '${deleteLayerFields}', '${deleteLayer}', '${deleteBeforeJs}', '${deleteAfterJs}')" labelKey="vulpe.label.delete.selected" icon="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icons/button-delete-all.png"/>
						</c:when>
						<c:otherwise>
							<v:action javascript="vulpe.view.request.submitDeleteSelected('${deleteActionName}/ajax', '${deleteFormName}', '${deleteLayerFields}', '${deleteLayer}', '${deleteBeforeJs}', '${deleteAfterJs}')" labelKey="vulpe.label.delete.selected" icon="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icons/button-delete-all.png"/>
						</c:otherwise>
					</c:choose>
				</v:column>
			</c:if>
			<c:if test="${!isHeader_table_tag}">
				<c:choose>
					<c:when test="${deleteType eq 'detail'}">
						<v:columnAction role="${deleteRole}" logged="${deleteLogged}" icon="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icons/button-row-delete.png" labelKey="${deleteLabelKey}" javascript="vulpe.view.confirmExclusion(function() {vulpe.view.request.submitDeleteDetail('${targetConfig.baseName}', ${status_row_tag.index}, '${deleteActionName}/ajax', '${deleteFormName}', '${deleteLayerFields}', '${deleteLayer}', '${deleteBeforeJs}', '${deleteAfterJs}');});" width="1%"/>
					</c:when>
					<c:otherwise>
						<v:columnAction role="${deleteRole}" logged="${deleteLogged}" icon="${pageContext.request.contextPath}/themes/${vulpeTheme}/images/icons/button-row-delete.png" labelKey="${deleteLabelKey}" javascript="vulpe.view.confirmExclusion(function() {vulpe.view.request.submitDelete('${util:urlEncode(util:evalString(pageContext, deleteValue))}', '${deleteActionName}/ajax', '${deleteFormName}', '${deleteLayerFields}', '${deleteLayer}', '${deleteBeforeJs}', '${deleteAfterJs}');});" width="1%"/>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:if>
	</tr>
</c:if>
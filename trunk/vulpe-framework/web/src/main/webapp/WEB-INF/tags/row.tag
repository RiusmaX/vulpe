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
<%@ attribute name="showUpdateButton" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<c:set var="show" value="${true}"/>
<c:if test="${not empty logged && logged eq true && util:isLogged(pageContext) eq false}">
	<c:set var="show" value="${false}"/>
</c:if>
<c:if test="${not empty role && util:isRole(pageContext, role) eq false}">
	<c:set var="show" value="${false}"/>
</c:if>
<c:if test="${show eq true}">
	<c:if test="${empty showLine}">
		<c:set var="showLine" value="${true}"/>
	</c:if>
	<c:if test="${empty styleClass && not empty currentStatus}">
		<c:choose>
			<c:when test="${(currentStatus.index % 2) == 0}">
				<c:set var="styleClass" value="vulpeLineOn"/>
			</c:when>
			<c:otherwise>
				<c:set var="styleClass" value="vulpeLineOff"/>
			</c:otherwise>
		</c:choose>
	</c:if>
	<c:if test="${isHeaderTableTag}">
		<c:set var="styleClass" value="vulpeColumnHeader"/>
	</c:if>
	<c:if test="${empty updateValue && isSelectTableTag && (updateShow || SELECT_updateShow)}">
		<c:set var="updateValue" value="id"/>
	</c:if>
	<c:if test="${not empty updateValue && updateValue ne 'false'}">
		<c:if test="${empty updateLabelKey}">
			<c:set var="updateLabelKey" value="label.vulpe.update"/>
		</c:if>
		<c:choose>
			<c:when test="${not empty currentItem}">
				<c:set var="updateValue" value="${'${'}currentItem.${updateValue}${'}'}"/>
			</c:when>
			<c:otherwise>
				<c:set var="updateValue" value=""/>
			</c:otherwise>
		</c:choose>
	</c:if>
	<c:set var="deleteType" value=""/>
	<c:if test="${empty deleteValue}">
		<c:choose>
			<c:when test="${isSelectTableTag && (deleteShow || SELECT_deleteShow)}">
				<c:set var="deleteValue" value="id"/>
				<c:set var="deleteType" value="${controllerConfig.controllerType == 'TABULAR' ? 'detail' : 'select'}"/>
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
			<c:set var="deleteLabelKey" value="label.vulpe.delete"/>
		</c:if>
		<c:set var="deleteValue" value="${'${'}currentItem.${deleteValue}${'}'}"/>
	</c:if>
	<c:if test="${not empty updateValue && updateValue ne 'false'}">
		<c:if test="${empty updateActionName}">
			<c:set var="updateActionName" value="${controllerConfig.controllerName}/update"/>
		</c:if>
		<c:if test="${empty updateFormName}">
			<c:set var="updateFormName" value="${vulpeFormName}"/>
		</c:if>
		<c:if test="${empty updateLayerFields}">
			<c:set var="updateLayerFields" value="${updateFormName}"/>
		</c:if>
		<c:if test="${empty updateLayer}">
			<c:set var="updateLayer" value="${vulpeBodyTwice ? 'crud' : 'body'}"/>
		</c:if>
		<c:if test="${not empty updateValue && !isHeaderTableTag}">
			<c:set var="elementId" value="${util:urlEncode(util:evalString(pageContext, updateValue))}"/>
			<c:if test="${empty showUpdateButton || !showUpdateButton}">
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
	</c:if>
	<c:if test="${not empty deleteValue && deleteValue ne 'false'}">
		<c:if test="${controllerConfig.controllerType == 'TABULAR'}">
		<c:set var="elementId" value="${currentItem.id}"/>
		</c:if>
		<c:if test="${empty deleteActionName}">
			<c:set var="deleteActionName" value="${controllerConfig.controllerName}/${deleteType == 'detail' ? 'deleteDetail' : 'delete'}"/>
		</c:if>
		<c:if test="${empty deleteFormName}">
			<c:set var="deleteFormName" value="${vulpeFormName}"/>
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
							<c:set var="deleteLayer" value="vulpeDetailBody_${targetConfig.baseName}${currentDetailIndex}"/>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:set var="deleteLayer" value="${controllerConfig.controllerType == 'TABULAR' ? '' : 'vulpeSelectTable_'}${deleteFormName}"/>
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${empty deleteBeforeJs}">
			<c:set var="deleteBeforeJs" value=""/>
		</c:if>
	</c:if>
	<c:if test="${(popup || not empty updateValue) && (empty showUpdateButton || !showUpdateButton)}">
		<c:if test="${empty onmouseover}">
			<c:set var="onmouseover" value="vulpe.view.onmouseoverRow(this);"/>
		</c:if>
		<c:if test="${empty onmouseout}">
			<c:set var="onmouseout" value="vulpe.view.onmouseoutRow(this);"/>
		</c:if>
	</c:if>
	<c:if test="${popup && !isHeaderTableTag && isSelectTableTag && not empty popupProperties}">
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
			<c:set var="valueRowEL" value="${'${'}currentItem.${propField}${'}'}"/>
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
			<v:column labelKey="label.vulpe.line" width="1%" styleClass="vulpeLine">
				<c:if test="${!isHeaderTableTag}">
					${currentStatus.index + 1}.
				</c:if>
			</v:column>
		</c:if>
		<c:if test="${!onlyToSee && not empty deleteValue && deleteValue ne 'false' && deleteType eq 'select'}">
		<c:choose>
			<c:when test="${!isHeaderTableTag}">
				<td onclick="${selectCheckOn}" class="vulpeSelect">
					<input type="checkbox" name="selected" value="${elementId}" tabindex="100000" title="<fmt:message key='help.vulpe.delete.selected'/>">
				</td>
			</c:when>
			<c:otherwise>
				<th id="vulpeSelectAll" width="10px">
					<input type="checkbox" name="selectAll" onclick="vulpe.view.markUnmarkAll(this, '#${deleteLayer}');" tabindex="100000" title="<fmt:message key='help.vulpe.delete.all.selected'/>">
				</th>
			</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${!onlyToSee && not empty deleteValue && deleteValue ne 'false' && deleteType eq 'detail'}">
			<c:if test="${empty isHeaderTableTag || isHeaderTableTag}">
				<th id="vulpeSelectAll" width="10px" style="text-align: center">
					<input type="checkbox" name="selectAll" onclick="vulpe.view.markUnmarkAll(this, '#${deleteLayer}');" tabindex="100000" title="<fmt:message key='help.vulpe.delete.all.selected'/>">
				</th>
			</c:if>
			<c:if test="${!isHeaderTableTag}">
				<v:column role="${deleteRole}" logged="${deleteLogged}" labelKey="${deleteLabelKey}" width="1%" styleClass="vulpeSelect">
					<v:checkbox name="${targetConfigPropertyName}[${currentStatus.index}].${deleteName}" fieldValue="true" paragraph="false" tabindex="100000" titleKey="help.vulpe.delete.selected" />
				</v:column>
			</c:if>
		</c:if>
		<jsp:doBody/>
		<c:if test="${not empty deleteValue && deleteValue ne 'false' && (deleteType eq 'select' || deleteType eq 'detail')}">
			<c:if test="${empty isHeaderTableTag || isHeaderTableTag}">
				<c:if test="${showUpdateButton}">
					<v:column elementId="vulpeUpdate" role="${updateRole}" logged="${updateLogged}" width="1%" showBodyInHeader="true" style="text-align: center">&nbsp;</v:column>
				</c:if>
				<v:column elementId="vulpeDeleteAll" role="${deleteRole}" logged="${deleteLogged}" width="1%" showBodyInHeader="true" style="text-align: center">
					<c:choose>
						<c:when test="${deleteType eq 'detail'}">
							<v:action javascript="vulpe.view.request.submitDeleteDetailSelected('${targetConfigPropertyName}', '${deleteActionName}/ajax', '${deleteFormName}', '${deleteLayerFields}', '${deleteLayer}', '${deleteBeforeJs}', '${deleteAfterJs}')" labelKey="label.vulpe.delete.selected" icon="delete-all" widthIcon="16" heightIcon="16"/>
						</c:when>
						<c:otherwise>
							<v:action javascript="vulpe.view.request.submitDeleteSelected('${deleteActionName}/ajax', '${deleteFormName}', '${deleteLayerFields}', '${deleteLayer}', '${deleteBeforeJs}', '${deleteAfterJs}')" labelKey="label.vulpe.delete.selected" icon="delete-all" widthIcon="16" heightIcon="16"/>
						</c:otherwise>
					</c:choose>
				</v:column>
			</c:if>
			<c:if test="${!isHeaderTableTag}">
				<c:if test="${showUpdateButton}">
					<v:columnAction styleClass="vulpeUpdate" role="${updateRole}" logged="${updateLogged}" icon="row-edit" widthIcon="16" heightIcon="16" labelKey="${updateLabelKey}" javascript="vulpe.view.request.submitUpdate('${elementId}', '${updateActionName}/ajax', '${updateFormName}', '${updateLayerFields}', '${updateLayer}', '${updateBeforeJs}', '${updateAfterJs}')" width="1%" />
				</c:if>
				<c:choose>
					<c:when test="${deleteType eq 'detail'}">
						<v:columnAction styleClass="vulpeDelete" role="${deleteRole}" logged="${deleteLogged}" icon="row-delete" widthIcon="16" heightIcon="16" labelKey="${deleteLabelKey}" javascript="vulpe.view.confirmExclusion(function() {vulpe.view.request.submitDeleteDetail('${targetConfig.baseName}', ${currentStatus.index}, '${deleteActionName}/ajax', '${deleteFormName}', '${deleteLayerFields}', '${deleteLayer}', '${deleteBeforeJs}', '${deleteAfterJs}');});" width="1%" />
					</c:when>
					<c:otherwise>
						<v:columnAction styleClass="vulpeDelete" role="${deleteRole}" logged="${deleteLogged}" icon="row-delete" widthIcon="16" heightIcon="16" labelKey="${deleteLabelKey}" javascript="vulpe.view.confirmExclusion(function() {vulpe.view.request.submitDelete('${util:urlEncode(util:evalString(pageContext, deleteValue))}', '${deleteActionName}/ajax', '${deleteFormName}', '${deleteLayerFields}', '${deleteLayer}', '${deleteBeforeJs}', '${deleteAfterJs}');});" width="1%"/>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:if>
	</tr>
</c:if>
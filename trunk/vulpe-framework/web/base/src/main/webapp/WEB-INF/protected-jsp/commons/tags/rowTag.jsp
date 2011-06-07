<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${render}">
	<c:if test="${empty show}"><c:set var="show" value="${true}"/></c:if>
	<c:if test="${!show}"><c:set var="style" value="display:none;${style}"/></c:if>
	<c:set var="recordId" value="${currentItem.id}"/>
	<c:if test="${empty showLine}"><c:set var="showLine" value="${true}"/></c:if>
	<c:if test="${empty showButtonsDelete}"><c:set var="showButtonsDelete" value="${global['project-view-showButtonsDelete']}"/></c:if>
	<c:if test="${empty showButtonDeleteThis}"><c:set var="showButtonDeleteThis" value="${global['project-view-showButtonDeleteThis']}"/></c:if>
	<c:if test="${empty showButtonUpdate}"><c:set var="showButtonUpdate" value="${global['project-view-showButtonUpdate']}"/></c:if>
	<c:if test="${empty disableDelete}"><c:set var="disableDelete" value="${false}"/></c:if>
	<c:if test="${empty styleClass && not empty currentStatus}">
		<c:choose>
			<c:when test="${(currentStatus.index % 2) == 0}"><c:set var="styleClass" value="vulpeLineOn"/><c:set var="xstyleClass" value="vulpeLineOn" scope="request"/></c:when>
			<c:otherwise><c:set var="styleClass" value="vulpeLineOff"/><c:set var="xstyleClass" value="vulpeLineOff" scope="request"/></c:otherwise>
		</c:choose>
	</c:if>
	<c:if test="${isHeaderTableTag}"><c:set var="styleClass" value="vulpeRowHeader"/></c:if>
	<c:if test="${empty updateValue && isSelectTableTag && (util:isButtonRender('update', 'SELECT'))}">
		<c:set var="updateValue" value="id"/>
	</c:if>
	<c:if test="${not empty updateValue && updateValue ne 'false'}">
		<c:if test="${empty updateLabelKey}"><c:set var="updateLabelKey" value="label.vulpe.update"/></c:if>
		<c:choose>
			<c:when test="${not empty currentItem}"><c:set var="updateValue" value="${'${'}currentItem.${updateValue}${'}'}"/></c:when>
			<c:otherwise><c:set var="updateValue" value=""/></c:otherwise>
		</c:choose>
	</c:if>
	<c:set var="deleteType" value=""/>
	<c:if test="${empty deleteValue}">
		<c:choose>
			<c:when test="${isSelectTableTag && (util:isButtonRender('delete', 'SELECT'))}">
				<c:set var="deleteValue" value="id"/>
				<c:set var="deleteType" value="${now['controllerType'] == 'TABULAR' ? 'detail' : 'select'}"/>
			</c:when>
			<c:when test="${not empty targetConfig}">
				<c:set var="deleteShowName" value="delete${targetConfig.baseName}"/>
				<c:if test="${util:isButtonRender(deleteShowName, '')}">
					<c:set var="deleteValue" value="selected"/>
					<c:set var="deleteType" value="detail"/>
				</c:if>
			</c:when>
		</c:choose>
	</c:if>
	<c:if test="${not empty deleteValue && deleteValue ne 'false'}">
		<c:if test="${empty deleteName}"><c:set var="deleteName" value="selected"/></c:if>
		<c:if test="${empty deleteLabelKey}"><c:set var="deleteLabelKey" value="label.vulpe.delete"/></c:if>
		<c:set var="deleteValue" value="${'${'}currentItem.${deleteValue}${'}'}"/>
	</c:if>
	<c:if test="${not empty updateValue && updateValue ne 'false'}">
		<c:if test="${empty updateActionName}"><c:set var="updateActionName" value="${controllerConfig.ownerController}/update"/></c:if>
		<c:if test="${empty updateFormName}"><c:set var="updateFormName" value="${vulpeFormName}"/></c:if>
		<c:if test="${empty updateLayerFields}"><c:set var="updateLayerFields" value="${updateFormName}"/></c:if>
		<c:if test="${empty updateLayer}"><c:set var="updateLayer" value="${vulpeBodyTwice ? 'main' : 'body'}"/></c:if>
		<c:if test="${not empty updateValue && !isHeaderTableTag}">
			<c:set var="recordId" value="${util:urlEncode(util:evalString(pageContext, updateValue))}"/>
			<c:if test="${empty showButtonUpdate || !showButtonUpdate}">
			<c:choose>
				<c:when test="${not empty updateLayer && updateLayer != 'body'}"><c:set var="updateLayer" value=", layer: '${updateLayer}'"/></c:when>
				<c:otherwise><c:set var="updateLayer" value=""/></c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${not empty updateFormName && updateFormName != updateLayerFields}"><c:set var="updateFormName" value=", formName: '${updateFormName}'"/></c:when>
				<c:otherwise><c:set var="updateFormName" value=""/></c:otherwise>
			</c:choose>
			<c:if test="${not empty updateBeforeJs}"><c:set var="updateBeforeJs" value=", beforeJs: '${updateBeforeJs}'"/></c:if>
			<c:if test="${not empty updateAfterJs}"><c:set var="updateAfterJs" value=", afterJs: '${updateAfterJs}'"/></c:if>
			<c:choose>
				<c:when test="${empty onclick}">
					<c:choose>
						<c:when test="${view}"><c:set var="onclick" value="vulpe.view.request.submitView({url: '${updateActionName}/ajax/${recordId}'${updateFormName}, layerFields: '${updateLayerFields}',${updateLayer}${updateBeforeJs}${updateAfterJs}})"/></c:when>
						<c:otherwise><c:set var="onclick" value="vulpe.view.request.submitUpdate({url: '${updateActionName}/ajax/${recordId}'${updateFormName}, layerFields: '${updateLayerFields}'${updateLayer}${updateBeforeJs}${updateAfterJs}, verify: true})"/></c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${view}"><c:set var="onclick" value="${onclick}; vulpe.view.request.submitView({url: '${updateActionName}/ajax/${recordId}'${updateFormName}, layerFields: '${updateLayerFields}'${updateLayer}${updateBeforeJs}${updateAfterJs}});"/></c:when>
						<c:otherwise><c:set var="onclick" value="${onclick}; vulpe.view.request.submitUpdate({url: '${updateActionName}/ajax/${recordId}'${updateFormName}, layerFields: '${updateLayerFields}'${updateLayer}${updateBeforeJs}${updateAfterJs}, verify: true});"/></c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			</c:if>
		</c:if>
	</c:if>
	<c:if test="${not empty deleteValue && deleteValue ne 'false' && showButtonsDelete}">
		<c:if test="${now['controllerType'] == 'TABULAR'}"><c:set var="recordId" value="${currentItem.id}"/></c:if>
		<c:if test="${empty deleteActionName}"><c:set var="deleteActionName" value="${controllerConfig.ownerController}/${deleteType == 'detail' ? 'deleteDetail' : 'delete'}"/></c:if>
		<c:if test="${empty deleteFormName}"><c:set var="deleteFormName" value="${vulpeFormName}"/></c:if>
		<c:if test="${empty deleteLayerFields}"><c:set var="deleteLayerFields" value="${deleteFormName}"/></c:if>
		<c:if test="${empty deleteLayer}">
			<c:choose>
				<c:when test="${deleteType == 'detail'}">
					<c:choose>
						<c:when test="${targetConfig.baseName == 'entities'}"><c:set var="deleteLayer" value="body"/></c:when>
						<c:when test="${not empty detailLayer}"><c:set var="deleteLayer" value="${detailLayer}"/></c:when>
						<c:otherwise><c:set var="deleteLayer" value="vulpeDetailBody-${targetConfig.baseName}${currentDetailIndex}"/></c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise><c:set var="deleteLayer" value="${now['controllerType'] == 'TABULAR' ? '' : 'vulpeSelectTable-'}${deleteFormName}"/></c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${empty deleteBeforeJs}"><c:set var="deleteBeforeJs" value=""/></c:if>
	</c:if>
	<c:if test="${(popup || not empty updateValue) && (empty showButtonUpdate || !showButtonUpdate)}">
		<c:if test="${not empty onmouseover}"><c:set var="onmouseover"> onmouseover="${onmouseover}"</c:set></c:if>
		<c:if test="${not empty onmouseout}"><c:set var="onmouseout"> onmouseout="${onmouseout}"</c:set></c:if>
	</c:if>
	<c:if test="${popup && !isHeaderTableTag && isSelectTableTag && not empty popupProperties}">
		<c:set var="popupProperties" value="${fn:trim(popupProperties)}"/>
		<c:set var="valueSelectRow" value=""/>
		<c:forEach items="${fn:split(popupProperties, ',')}" var="prop" varStatus="sProp">
			<c:set var="propName" value="${prop}"/>
			<c:set var="propField" value="${prop}"/>
			<c:forEach items="${fn:split(prop, '=')}" var="propCfg" varStatus="sPropCfg">
				<c:if test="${sPropCfg.first}"><c:set var="propName" value="${propCfg}"/></c:if>
				<c:if test="${sPropCfg.last}"><c:set var="propField" value="${propCfg}"/></c:if>
			</c:forEach>
			<c:set var="valueRowEL" value="${'${'}currentItem.${propField}${'}'}"/>
			<c:set var="valueRow" value="${util:urlEncode(util:evalString(pageContext, valueRowEL))}"/>
			<c:choose>
				<c:when test="${sProp.first}"><c:set var="valueSelectRow" value="${propName}=${valueRow}"/></c:when>
				<c:otherwise><c:set var="valueSelectRow" value="${valueSelectRow},${propName}=${valueRow}"/></c:otherwise>
			</c:choose>
		</c:forEach>
		<c:choose>
			<c:when test="${empty onclick}"><c:set var="onclick" value="vulpe.view.selectRow(this, '${valueSelectRow}');"/></c:when>
			<c:otherwise><c:set var="onclick" value="${onclick}; vulpe.view.selectRow('${valueSelectRow}');"/></c:otherwise>
		</c:choose>
	</c:if>
	<c:if test="${not empty onclick}"><c:set var="onclick">onclick="${onclick}"</c:set></c:if>
	<c:if test="${not empty style}"><c:set var="style">style="${style}"</c:set></c:if>
	<c:if test="${not empty styleClass}"><c:set var="styleClass">class="${styleClass}"</c:set></c:if>
	<c:if test="${not empty rowspan}"><c:set var="rowspan">rowspan="${rowspan}"</c:set></c:if>
	<c:set var="elementId" value="${vulpeFormName}-${currentTableElementId}-row-${!isHeaderTableTag ? currentStatus.index : 'header'}"/>
	<tr id="${elementId}" ${onclick} ${onmouseover} ${onmouseout} ${styleClass} ${style} ${rowspan}>
		<c:if test="${!isHeaderTableTag && not empty currentDetailConfig && renderId}"><td style="display: none"><v:hidden property="id"/><v:hidden property="fakeId"/></td></c:if>
		<c:if test="${!onlyToSee && showButtonsDelete && not empty deleteValue && deleteValue ne 'false' && deleteType eq 'select'}">
		<c:choose>
			<c:when test="${!isHeaderTableTag}">
				<td onclick="${selectCheckOn}" class="vulpeSelect ${xstyleClass}">
					<c:if test="${disableDelete}"><c:set var="disableSelect" value="disabled=\"true\""/></c:if>
					<c:set var="checkboxName" value="${!disableDelete ? deleteName : ''}"/>
					<input type="checkbox" name="${checkboxName}" onclick="vulpe.view.controlMarkUnmarkAll(this, '${checkboxName}', '${deleteLayer}');" value="${recordId}" tabindex="100000" title="<fmt:message key='help.vulpe.delete.selected'/>" ${disableSelect}/>
				</td>
			</c:when>
			<c:otherwise>
				<th id="vulpeSelectAll" style="text-align: center;">
					<fmt:message key='label.vulpe.delete'/><br/>
					<input type="checkbox" id="selectAll" name="selectAll" onclick="vulpe.view.markUnmarkAll(this, 'selected', '#${deleteLayer}');" tabindex="100000" title="<fmt:message key='help.vulpe.delete.all.selected'/>"/>
				</th>
			</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${!onlyToSee && showButtonsDelete && not empty deleteValue && deleteValue ne 'false' && deleteType eq 'detail'}">
			<c:if test="${empty isHeaderTableTag || isHeaderTableTag}">
				<th id="vulpeSelectAll" ${styleClass} style="text-align: center;">
					<fmt:message key='label.vulpe.delete'/><br/>
					<input type="checkbox" id="selectAll" name="selectAll" onclick="vulpe.view.markUnmarkAll(this, 'selected', '#${deleteLayer}');" tabindex="100000" title="<fmt:message key='help.vulpe.delete.all.selected'/>"/>
				</th>
			</c:if>
			<c:if test="${!isHeaderTableTag}">
				<v:column roles="${deleteRole}" showOnlyIfAuthenticated="${deleteLogged}" labelKey="${deleteLabelKey}" style="width: 1%" styleClass="vulpeSelect ${xstyleClass}">
					<c:set var="checkboxName" value="${!disableDelete ? deleteName : 'unselected'}"/>
					<v:checkbox name="${targetConfigPropertyName}[${currentStatus.index}].${checkboxName}" onclick="vulpe.view.controlMarkUnmarkAll(this, '${checkboxName}', '${deleteLayer}');" fieldValue="true" paragraph="false" tabindex="100000" titleKey="help.vulpe.delete.selected" disabled="${disableDelete}" focused="false"/>
				</v:column>
			</c:if>
		</c:if>
		<c:if test="${not empty currentDetailConfig && empty targetConfig.parentDetailConfig}">
			<c:set var="detailPagingList" value="${currentDetailConfig.name}_pagingList"/>
			<c:set var="detailPagingListEL" value="${'${'}ever['${detailPagingList}']${'}'}"/>
			<c:set var="detailPagingList" value="${util:eval(pageContext, detailPagingListEL)}"/>
		</c:if>
		<c:if test="${showLine}"><v:column labelKey="label.vulpe.line" style="width: 1%" styleClass="${!isHeaderTableTag ? 'vulpeLine' : 'vulpeRowHeader'} ${xstyleClass}"><c:if test="${!isHeaderTableTag}"><c:choose><c:when test="${not empty detailPagingList}">${((detailPagingList.page - 1) * detailPagingList.pageSize) + currentStatus.count}</c:when><c:otherwise>${currentStatus.count}</c:otherwise></c:choose>.</c:if></v:column></c:if>
		<jsp:doBody/>
		<c:if test="${not empty updateValue && updateValue ne 'false' && showButtonUpdate}">
			<c:choose>
				<c:when test="${not empty updateLayer && updateLayer != 'body'}"><c:set var="updateLayer" value=", layer: '${updateLayer}'"/></c:when>
				<c:otherwise><c:set var="updateLayer" value=""/></c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${not empty updateFormName && updateFormName != updateLayerFields}"><c:set var="updateFormName" value=", formName: '${updateFormName}'"/></c:when>
				<c:otherwise><c:set var="updateFormName" value=""/></c:otherwise>
			</c:choose>
			<c:if test="${not empty updateBeforeJs}"><c:set var="updateBeforeJs" value=", beforeJs: '${updateBeforeJs}'"/></c:if>
			<c:if test="${not empty updateAfterJs}"><c:set var="updateAfterJs" value=", afterJs: '${updateAfterJs}'"/></c:if>
			<c:if test="${empty isHeaderTableTag || isHeaderTableTag}"><v:column elementId="vulpeUpdate" roles="${updateRole}" showOnlyIfAuthenticated="${updateLogged}" showBodyInHeader="true" style="text-align: center; width: 1%">&nbsp;</v:column></c:if>
			<c:if test="${!isHeaderTableTag}">
				<v:columnAction elementId="Update${currentStatus.count}" styleClass="vulpeUpdate ${xstyleClass}" roles="${updateRole}" showOnlyIfAuthenticated="${updateLogged}" icon="row-edit" iconWidth="16" iconHeight="16" labelKey="${updateLabelKey}" javascript="vulpe.view.request.submitUpdate({url: '${updateActionName}/ajax/${recordId}'${updateFormName}, layerFields: '${updateLayerFields}'${updateLayer}${updateBeforeJs}${updateAfterJs}})" style="width: 1%" />
			</c:if>
		</c:if>
		<c:if test="${!onlyToSee && showButtonsDelete && not empty deleteValue && deleteValue ne 'false' && (deleteType eq 'select' || deleteType eq 'detail')}">
			<c:choose>
				<c:when test="${not empty deleteLayer && deleteLayer != 'body'}"><c:set var="deleteLayer" value=", layer: '${deleteLayer}'"/></c:when>
				<c:otherwise><c:set var="deleteLayer" value=""/></c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${not empty deleteFormName && deleteFormName != deleteLayerFields}"><c:set var="deleteFormName" value=", formName: '${deleteFormName}'"/></c:when>
				<c:otherwise><c:set var="deleteFormName" value=""/></c:otherwise>
			</c:choose>
			<c:if test="${not empty deleteAfterJs}"><c:set var="deleteAfterJs" value=", afterJs: '${deleteAfterJs}'"/></c:if>
			<c:if test="${not empty deleteBeforeJs}"><c:set var="deleteBeforeJs" value=", beforeJs: '${deleteBeforeJs}'"/></c:if>
			<c:if test="${empty isHeaderTableTag || isHeaderTableTag}">
				<v:column elementId="vulpeDeleteAll" roles="${deleteRole}" showOnlyIfAuthenticated="${deleteLogged}" showBodyInHeader="true" style="text-align: center; width: 1%">
					<c:choose>
						<c:when test="${deleteType eq 'detail'}"><v:action javascript="vulpe.view.request.submitDeleteDetailSelected({detail: '${targetConfigPropertyName}', url: '${deleteActionName}/ajax'${deleteFormName}, layerFields: '${deleteLayerFields}'${deleteLayer}${deleteBeforeJs}${deleteAfterJs}, queryString: 'detailLayer=${detailLayer}'})" labelKey="label.vulpe.delete.selected" icon="delete-all" iconWidth="16" iconHeight="16" elementId="DeleteAll" show="${util:isButtonShow('delete', 'MAIN')}" disabled="${util:isButtonDisabled('delete', 'MAIN')}" /></c:when>
						<c:otherwise><v:action javascript="vulpe.view.request.submitDeleteSelected({url: '${deleteActionName}/ajax'${deleteFormName}, layerFields: '${deleteLayerFields}'${deleteLayer}${deleteBeforeJs}${deleteAfterJs}})" labelKey="label.vulpe.delete.selected" icon="delete-all" iconWidth="16" iconHeight="16" elementId="DeleteAll" show="${util:isButtonShow('delete', 'SELECT')}" disabled="${util:isButtonDisabled('delete', 'SELECT')}" /></c:otherwise>
					</c:choose>
				</v:column>
			</c:if>
			<c:if test="${!isHeaderTableTag}">
				<c:choose>
				<c:when test="${showButtonDeleteThis}">
				<c:choose>
					<c:when test="${deleteType eq 'detail'}">
						<c:set var="javascript">vulpe.view.confirm('delete', function() {vulpe.view.request.submitDeleteDetail({detail: '${targetConfigPropertyName}', detailIndex: ${currentStatus.index}, url: '${deleteActionName}/ajax'${deleteFormName}, layerFields: '${deleteLayerFields}'${deleteLayer}${deleteBeforeJs}${deleteAfterJs}, queryString: 'detailLayer=${detailLayer}'});});</c:set>
						<c:if test="${disableDelete}"><c:set var="javascript" value="return false;"/></c:if>
						<v:columnAction styleClass="vulpeDelete ${xstyleClass} ${disableDelete ? 'vulpeItemOff' : ''}" roles="${deleteRole}" showOnlyIfAuthenticated="${deleteLogged}" icon="row-delete" iconWidth="16" iconHeight="16" labelKey="${deleteLabelKey}" javascript="${javascript}" style="width: 1%" elementId="Delete${currentStatus.count}" />
					</c:when>
					<c:otherwise>
						<c:set var="javascript">vulpe.view.confirm('delete', function() {vulpe.view.request.submitDelete({url: '${deleteActionName}/ajax/${util:urlEncode(util:evalString(pageContext, deleteValue))}'${deleteFormName}, layerFields: '${deleteLayerFields}'${deleteLayer}${deleteBeforeJs}${deleteAfterJs}});});</c:set>
						<c:if test="${disableDelete}"><c:set var="javascript" value="return false;"/></c:if>
						<v:columnAction styleClass="vulpeDelete ${xstyleClass} ${disableDelete ? 'vulpeItemOff' : ''}" roles="${deleteRole}" showOnlyIfAuthenticated="${deleteLogged}" icon="row-delete" iconWidth="16" iconHeight="16" labelKey="${deleteLabelKey}" javascript="${javascript}" style="width: 1%" elementId="Delete${currentStatus.count}"/>
					</c:otherwise>
				</c:choose>
				</c:when>
				<c:otherwise><td class="${xstyleClass}">&nbsp;</td></c:otherwise>
				</c:choose>
			</c:if>
		</c:if>
	</tr>
</c:if>
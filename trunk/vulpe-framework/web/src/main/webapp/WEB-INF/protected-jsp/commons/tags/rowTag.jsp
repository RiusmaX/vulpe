<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp" %>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagHeader.jsp" %>
<c:if test="${show}">
	<c:if test="${empty showLine}">
		<c:set var="showLine" value="${true}"/>
	</c:if>
	<c:if test="${empty showDeleteButton}">
		<c:set var="showDeleteButton" value="${true}"/>
	</c:if>
	<c:if test="${empty disableDelete}">
		<c:set var="disableDelete" value="${false}"/>
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
	<c:if test="${empty updateValue && isSelectTableTag && (now['buttons']['update'] || now['buttons']['SELECT_update'])}">
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
			<c:when test="${isSelectTableTag && (now['buttons']['delete'] || now['buttons']['SELECT_delete'])}">
				<c:set var="deleteValue" value="id"/>
				<c:set var="deleteType" value="${now['controllerType'] == 'TABULAR' ? 'detail' : 'select'}"/>
			</c:when>
			<c:when test="${not empty targetConfig}">
				<c:set var="deleteShowEL" value="${'${'}now['buttons']['delete${targetConfig.baseName}']${'}'}"/>
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
							<c:set var="onclick" value="vulpe.view.request.submitView({id: '${elementId}', url: '${updateActionName}/ajax', formName: '${updateFormName}', layerFields: '${updateLayerFields}', layer: '${updateLayer}', beforeJs: '${updateBeforeJs}', afterJs: '${updateAfterJs}'})"/>
						</c:when>
						<c:otherwise>
							<c:set var="onclick" value="vulpe.view.request.submitUpdate({id: '${elementId}', url: '${updateActionName}/ajax', formName: '${updateFormName}', layerFields: '${updateLayerFields}', layer: '${updateLayer}', beforeJs: '${updateBeforeJs}', afterJs: '${updateAfterJs}', verify: true})"/>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${view}">
							<c:set var="onclick" value="${onclick}; vulpe.view.request.submitView({id: '${elementId}', url: '${updateActionName}/ajax', formName: '${updateFormName}', layerFields: '${updateLayerFields}', layer: '${updateLayer}', beforeJs: '${updateBeforeJs}', afterJs: '${updateAfterJs}'});"/>
						</c:when>
						<c:otherwise>
							<c:set var="onclick" value="${onclick}; vulpe.view.request.submitUpdate({id: '${elementId}', url: '${updateActionName}/ajax', formName: '${updateFormName}', layerFields: '${updateLayerFields}', layer: '${updateLayer}', beforeJs: '${updateBeforeJs}', afterJs: '${updateAfterJs}', verify: true});"/>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<c:if test="${now['controllerType'] == 'SELECT'}">
				<c:if test="${not empty currentStatus && currentStatus.count <= 10}">
					<script type="text/javascript">
					$(document).ready(function() {
						vulpe.util.addHotKey({
								hotKey: "Ctrl+Shift+${currentStatus.count == 10 ? 0 : currentStatus.count}",
								command: function (){
									vulpe.util.get("${vulpeFormName}-row-${elementId}").click();
									return false;
								}
						});
					});
					</script>
				</c:if>
			</c:if>
			</c:if>
		</c:if>
	</c:if>
	<c:if test="${not empty deleteValue && deleteValue ne 'false' && showDeleteButton}">
		<c:if test="${now['controllerType'] == 'TABULAR'}">
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
							<c:set var="deleteLayer" value="vulpeDetailBody-${targetConfig.baseName}${currentDetailIndex}"/>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:set var="deleteLayer" value="${now['controllerType'] == 'TABULAR' ? '' : 'vulpeSelectTable-'}${deleteFormName}"/>
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${empty deleteBeforeJs}">
			<c:set var="deleteBeforeJs" value=""/>
		</c:if>
	</c:if>
	<c:if test="${(popup || not empty updateValue) && (empty showUpdateButton || !showUpdateButton)}">
		<c:if test="${empty onmouseover}">
			<c:set var="onmouseover"> onmouseover="${onmouseover}"</c:set>
		</c:if>
		<c:if test="${empty onmouseout}">
			<c:set var="onmouseout"> onmouseout="${onmouseout}"</c:set>
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
	<c:if test="${not empty onclick}">
		<c:set var="onclick"> onclick="${onclick}"</c:set>
	</c:if>
	<c:if test="${not empty style}">
		<c:set var="style"> style="${style}"</c:set>
	</c:if>
	<c:if test="${not empty styleClass}">
		<c:set var="styleClass"> class="${styleClass}"</c:set>
	</c:if>
	<c:if test="${not empty rowspan}">
		<c:set var="rowspan"> rowspan="${rowspan}"</c:set>
	</c:if>
	<tr id="${vulpeFormName}-row-${elementId}"${onclick}${onmouseover}${onmouseout}${styleClass}${style}${rowspan}>
		<c:if test="${showLine}">
			<v:column labelKey="label.vulpe.line" width="1%" styleClass="vulpeLine">
				<c:if test="${!isHeaderTableTag}">
					${currentStatus.count}.
				</c:if>
			</v:column>
		</c:if>
		<c:if test="${!onlyToSee && showDeleteButton && not empty deleteValue && deleteValue ne 'false' && deleteType eq 'select'}">
		<c:choose>
			<c:when test="${!isHeaderTableTag}">
				<td onclick="${selectCheckOn}" class="vulpeSelect">
					<c:if test="${disableDelete}"><c:set var="disableSelect" value="disabled=\"true\""/></c:if>
					<input type="checkbox" name="${!disableDelete ? deleteName : ''}" value="${elementId}" tabindex="100000" title="<fmt:message key='help.vulpe.delete.selected'/>"${disableSelect}>
				</td>
			</c:when>
			<c:otherwise>
				<th id="vulpeSelectAll" width="10px">
					<input type="checkbox" name="selectAll" onclick="vulpe.view.markUnmarkAll(this, 'selected', '#${deleteLayer}');" tabindex="100000" title="<fmt:message key='help.vulpe.delete.all.selected'/>">
				</th>
			</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${!onlyToSee && showDeleteButton && not empty deleteValue && deleteValue ne 'false' && deleteType eq 'detail'}">
			<c:if test="${empty isHeaderTableTag || isHeaderTableTag}">
				<th id="vulpeSelectAll" width="10px" style="text-align: center">
					<input type="checkbox" name="selectAll" onclick="vulpe.view.markUnmarkAll(this, 'selected', '#${deleteLayer}');" tabindex="100000" title="<fmt:message key='help.vulpe.delete.all.selected'/>">
				</th>
			</c:if>
			<c:if test="${!isHeaderTableTag}">
				<v:column roles="${deleteRole}" showOnlyIfAuthenticated="${deleteLogged}" labelKey="${deleteLabelKey}" width="1%" styleClass="vulpeSelect">
					<v:checkbox name="${targetConfigPropertyName}[${currentStatus.index}].${!disableDelete ? deleteName : 'unselected'}" fieldValue="true" paragraph="false" tabindex="100000" titleKey="help.vulpe.delete.selected" disabled="${disableDelete}"/>
				</v:column>
			</c:if>
		</c:if>
		<jsp:doBody/>
		<c:if test="${not empty updateValue && updateValue ne 'false' && showUpdateButton}">
			<c:if test="${empty isHeaderTableTag || isHeaderTableTag}">
				<v:column elementId="vulpeUpdate" roles="${updateRole}" showOnlyIfAuthenticated="${updateLogged}" width="1%" showBodyInHeader="true" style="text-align: center">&nbsp;</v:column>
			</c:if>
			<c:if test="${!isHeaderTableTag}">
				<v:columnAction elementId="Update${currentStatus.count}" styleClass="vulpeUpdate" roles="${updateRole}" showOnlyIfAuthenticated="${updateLogged}" icon="row-edit" widthIcon="16" heightIcon="16" labelKey="${updateLabelKey}" javascript="vulpe.view.request.submitUpdate({id: '${elementId}', url: '${updateActionName}/ajax', formName: '${updateFormName}', layerFields: '${updateLayerFields}', layer: '${updateLayer}', beforeJs: '${updateBeforeJs}', afterJs: '${updateAfterJs}'})" width="1%" />
				<c:if test="${now['controllerType'] == 'SELECT'}">
					<c:if test="${not empty currentStatus && currentStatus.count <= 10}">
						<script type="text/javascript">
						$(document).ready(function() {
							vulpe.util.addHotKey({
								hotKey: "Ctrl+Shift+${currentStatus.count == 10 ? 0 : currentStatus.count}",
								command: function (){
									vulpe.util.get("vulpeButtonUpdate${currentStatus.count}-${vulpeFormName}").click();
									return false;
								}
							});
						});
						</script>
					</c:if>
				</c:if>
			</c:if>
		</c:if>
		<c:if test="${!onlyToSee && showDeleteButton && not empty deleteValue && deleteValue ne 'false' && (deleteType eq 'select' || deleteType eq 'detail')}">
			<c:if test="${empty isHeaderTableTag || isHeaderTableTag}">
				<v:column elementId="vulpeDeleteAll" roles="${deleteRole}" showOnlyIfAuthenticated="${deleteLogged}" width="1%" showBodyInHeader="true" style="text-align: center">
					<c:choose>
						<c:when test="${deleteType eq 'detail'}">
							<v:action javascript="vulpe.view.request.submitDeleteDetailSelected({detail: '${targetConfigPropertyName}', url: '${deleteActionName}/ajax', formName: '${deleteFormName}', layerFields: '${deleteLayerFields}', layer: '${deleteLayer}', beforeJs: '${deleteBeforeJs}', afterJs: '${deleteAfterJs}'})" labelKey="label.vulpe.delete.selected" icon="delete-all" widthIcon="16" heightIcon="16" elementId="DeleteAll"/>
						</c:when>
						<c:otherwise>
							<v:action javascript="vulpe.view.request.submitDeleteSelected({url: '${deleteActionName}/ajax', formName: '${deleteFormName}', layerFields: '${deleteLayerFields}', layer: '${deleteLayer}', beforeJs: '${deleteBeforeJs}', afterJs: '${deleteAfterJs}'})" labelKey="label.vulpe.delete.selected" icon="delete-all" widthIcon="16" heightIcon="16" elementId="DeleteAll"/>
						</c:otherwise>
					</c:choose>
				</v:column>
			</c:if>
			<c:if test="${!isHeaderTableTag}">
				<c:choose>
					<c:when test="${deleteType eq 'detail'}">
						<c:set var="javascript">vulpe.view.confirmExclusion(function() {vulpe.view.request.submitDeleteDetail({detail: '${targetConfig.baseName}', detailIndex: ${currentStatus.index}, url: '${deleteActionName}/ajax', fomName: '${deleteFormName}', layerFields: '${deleteLayerFields}', layer: '${deleteLayer}', beforeJs: '${deleteBeforeJs}', afterJs: '${deleteAfterJs}'});});</c:set>
						<c:if test="${disableDelete}">
							<c:set var="javascript" value="return false;"/>
						</c:if>
						<v:columnAction styleClass="vulpeDelete ${disableDelete ? 'vulpeIconOff' : ''}" roles="${deleteRole}" showOnlyIfAuthenticated="${deleteLogged}" icon="row-delete" widthIcon="16" heightIcon="16" labelKey="${deleteLabelKey}" javascript="${javascript}" width="1%" elementId="Delete${currentStatus.count}" />
					</c:when>
					<c:otherwise>
						<c:set var="javascript">vulpe.view.confirmExclusion(function() {vulpe.view.request.submitDelete({id: '${util:urlEncode(util:evalString(pageContext, deleteValue))}', url: '${deleteActionName}/ajax', formName: '${deleteFormName}', layerFields: '${deleteLayerFields}', layer: '${deleteLayer}', beforeJs: '${deleteBeforeJs}', afterJs: '${deleteAfterJs}'});});</c:set>
						<c:if test="${disableDelete}">
							<c:set var="javascript" value="return false;"/>
						</c:if>
						<v:columnAction styleClass="vulpeDelete ${disableDelete ? 'vulpeIconOff' : ''}" roles="${deleteRole}" showOnlyIfAuthenticated="${deleteLogged}" icon="row-delete" widthIcon="16" heightIcon="16" labelKey="${deleteLabelKey}" javascript="${javascript}" width="1%" elementId="Delete${currentStatus.count}"/>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:if>
	</tr>
	<c:if test="${(popup || not empty updateValue) && (empty showUpdateButton || !showUpdateButton)}">
	<script type="text/javascript">
	jQuery(function($){
		$("#${vulpeFormName}-row-${elementId}").bind("mouseenter mouseleave", function(event){
			$(this).toggleClass("vulpeSelectedRow");
		});
	});
	</script>
	</c:if>
</c:if>
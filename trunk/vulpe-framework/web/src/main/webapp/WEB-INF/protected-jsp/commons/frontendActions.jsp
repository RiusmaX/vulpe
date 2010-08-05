<%@include file="/WEB-INF/protected-jsp/commons/actions.jsp" %>
<p>
<%@include file="/WEB-INF/protected-jsp/commons/frontendActionsExtended.jsp" %>
<c:if test="${clearShow}">
<v:action labelKey="clear" elementId="Clear" javascript="document.forms['${vulpeFormName}'].reset();"/>
</c:if>
<c:if test="${createShow}">
<v:action validate="false" labelKey="create" elementId="Create" action="create" beforeJs="vulpe.view.resetFields(%27${vulpeFormName}%27)" helpKey="create" />
</c:if>
<c:if test="${createPostShow}">
<v:action labelKey="createPost" elementId="CreatePost" action="createPost" helpKey="createPost"/>
</c:if>
<c:if test="${deleteShow}">
<v:action beforeJs="vulpe.view.confirmExclusion()" validate="false" labelKey="delete" elementId="Delete" action="delete" helpKey="delete"/>
</c:if>
<c:if test="${updatePostShow}">
<v:action labelKey="updatePost" elementId="UpdatePost" action="updatePost" helpKey="updatePost"/>
</c:if>
<c:if test="${prepareShow}">
<v:action validate="false" labelKey="prepare" elementId="Prepare" action="prepare" helpKey="prepare"/>
</c:if>
</p>
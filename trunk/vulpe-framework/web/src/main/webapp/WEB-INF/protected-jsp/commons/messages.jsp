<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<c:if test="${not empty actionErrors || not empty actionMessages || not empty fieldErrors}">
	<c:if test="${not empty fieldErrors}">
		<script charset="utf-8">
			$(document).ready(function() {
				<c:forEach items="${fieldErrors}" var="entry">
					<c:set var="msgs" value="<em></em>"/>
					<c:forEach items="${entry.value}" var="msg">
						<c:set var="msgs" value="${msgs}${msg}<br/>"/>
					</c:forEach>
					<c:set var="fieldName" value="${entry.key}"/>
					<c:if test="${fn:startsWith(fieldName, vulpeFormName) eq false}">
						<c:set var="fieldName" value="${vulpeFormName}_${fieldName}"/>
					</c:if>
					vulpe.exception.setupError('${fieldName}', '${msgs}');
				</c:forEach>
				vulpe.exception.focusFirstError('${vulpeFormName}');
				$("#messages").removeClass("vulpeMessageError");
				$("#messages").removeClass("vulpeMessageSuccess");
				$("#messages").addClass("vulpeMessageValidation");
				$("#messages").slideDown("slow");
				<c:if test="${vulpeMessageSlideUp}">
				setTimeout(function() {
				  $("#messages").slideUp("slow");
				}, ${vulpeMessageSlideUpTime});
				</c:if>
			});
		</script>
	</c:if>
	<c:choose>
		<c:when test="${IS_EXCEPTION}">
			<c:if test="${empty fieldErrors}">
				<div id="messageTitle" style="display: none"><fmt:message key="vulpe.error.alert" /></div>
			</c:if>
			<!--IS_EXCEPTION-->
			<c:choose>
				<c:when test="${ajax}">
					<!--SUBMIT_AJAX-->
					<ul>
						<c:if test="${not empty fieldErrors}">
							<li class="vulpeAlertError"><fmt:message key="vulpe.error.validate" /></li>
						</c:if>
						<c:if test="${not empty actionErrors}">
							<c:forEach items="${actionErrors}" var="msg">
								<li class="vulpeAlertError">${msg}</li>
							</c:forEach>
						</c:if>
						<c:if test="${not empty actionMessages}">
							<c:forEach items="${actionMessages}" var="msg">
								<li class="vulpeAlertMessage">${msg}</li>
							</c:forEach>
						</c:if>
					</ul>
					<div id="closeMessages"><a href="javascript:void(0);" onclick="$('#messages').slideUp('slow')"><fmt:message key="vulpe.messages.close"/></a></div>
					<script charset="utf-8">
						vulpe.view.onhidemessages = '${util:urlEncode(onHideMessages)}';
					</script>
				</c:when>
				<c:otherwise>
					<div id="errors" class="vulpeMessages">
					<ul>
						<c:if test="${not empty fieldErrors}">
							<li class="vulpeAlertError"><fmt:message key="vulpe.error.validate" /></li>
						</c:if>
						<c:if test="${not empty actionErrors}">
							<c:forEach items="${actionErrors}" var="msg">
								<li class="vulpeAlertError">${msg}</li>
							</c:forEach>
						</c:if>
						<c:if test="${not empty actionMessages}">
							<c:forEach items="${actionMessages}" var="msg">
								<li class="vulpeAlertMessage">${msg}</li>
							</c:forEach>
						</c:if>
					</ul>
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<script charset="utf-8" type="text/javascript">
				$(document).ready(function() {
					var msg = '<ul>';
					$("#messages").removeClass("vulpeMessageError");
					$("#messages").removeClass("vulpeMessageSuccess");
					$("#messages").removeClass("vulpeMessageValidation");
					<c:if test="${not empty actionErrors}">
					$("#messages").addClass("vulpeMessageError");
						<c:forEach items="${actionErrors}" var="msg">
					msg += '<li class="vulpeAlertError">${msg}</li>';
						</c:forEach>
					</c:if>
					<c:if test="${not empty actionMessages}">
					$("#messages").addClass("vulpeMessageSuccess");
						<c:forEach items="${actionMessages}" var="msg">
					msg += '<li class="vulpeAlertMessage">${msg}</li>';
						</c:forEach>
					</c:if>
					msg += '</ul><div id="closeMessages"><a href="javascript:void(0);" onclick="$(\'#messages\').slideUp(\'slow\')"><fmt:message key="vulpe.messages.close"/></a></div>';

					$("#messages").html(msg);
					$("#messages").slideDown("slow");
					<c:if test="${vulpeMessageSlideUp}">
					setTimeout(function() {
			          $("#messages").slideUp("slow");
					}, ${vulpeMessageSlideUpTime});
					</c:if>
				});
			</script>
		</c:otherwise>
	</c:choose>
</c:if>
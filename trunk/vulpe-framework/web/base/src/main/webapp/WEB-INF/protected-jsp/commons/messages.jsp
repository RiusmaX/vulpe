<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<c:if test="${not empty actionErrors || not empty actionMessages || not empty actionInfoMessages || not empty fieldErrors}">
	<c:if test="${not empty fieldErrors}">
		<script charset="utf-8">
			$(document).ready(function() {
				var messages = "#messages";
				if (vulpe.util.existsVulpePopups()) {
					messages = "#messagesPopup";
				}
				<c:forEach items="${fieldErrors}" var="entry">
					<c:set var="msgs" value="<em></em>"/>
					<c:forEach items="${entry.value}" var="msg">
						<c:set var="msgs" value="${msgs}${msg}<br/>"/>
					</c:forEach>
					<c:set var="fieldName" value="${entry.key}"/>
					<c:if test="${fn:startsWith(fieldName, vulpeFormName) eq false}">
						<c:set var="fieldName" value="${vulpeFormName}-${fieldName}"/>
					</c:if>
					vulpe.exception.setupError('${fieldName}', '${msgs}');
				</c:forEach>
				vulpe.exception.focusFirstError('${vulpeFormName}');
				$(messages).removeClass("vulpeMessageError");
				$(messages).removeClass("vulpeMessageSuccess");
				$(messages).removeClass("vulpeMessageInfo");
				$(messages).addClass("vulpeMessageValidation");
				$(messages).slideDown("slow");
				<c:if test="${global['project-view-messageSlideUp']}">
				setTimeout(function() {
				  	$(messages).slideUp("slow");
				}, ${global['project-view-messageSlideUpTime']});
				</c:if>
			});
		</script>
	</c:if>
	<c:choose>
		<c:when test="${IS_EXCEPTION}">
			<c:if test="${empty fieldErrors}"><div id="messageTitle" style="display: none"><fmt:message key="vulpe.error.alert" /></div></c:if>
			<!--IS_EXCEPTION-->
			<c:choose>
				<c:when test="${ajax}">
					<!--SUBMIT_AJAX-->
					<ul>
						<c:if test="${not empty fieldErrors}"><li class="vulpeAlertError"><fmt:message key="vulpe.error.validate" /></li></c:if>
						<c:if test="${not empty actionErrors}"><c:forEach items="${actionErrors}" var="msg"><li class="vulpeAlertError">${msg}</li></c:forEach></c:if>
						<c:if test="${not empty actionMessages}"><c:forEach items="${actionMessages}" var="msg"><li class="vulpeAlertMessage">${msg}</li></c:forEach></c:if>
						<c:if test="${not empty actionInfoMessages}"><c:forEach items="${actionInfoMessages}" var="msg"><li class="vulpeAlertMessage">${msg}</li></c:forEach></c:if>
					</ul>
				</c:when>
				<c:otherwise>
					<div id="errors">
					<ul>
						<c:if test="${not empty fieldErrors}"><li class="vulpeAlertError"><fmt:message key="vulpe.error.validate" /></li></c:if>
						<c:if test="${not empty actionErrors}"><c:forEach items="${actionErrors}" var="msg"><li class="vulpeAlertError">${msg}</li></c:forEach></c:if>
						<c:if test="${not empty actionMessages}"><c:forEach items="${actionMessages}" var="msg"><li class="vulpeAlertMessage">${msg}</li></c:forEach></c:if>
						<c:if test="${not empty actionInfoMessages}"><c:forEach items="${actionInfoMessages}" var="msg"><li class="vulpeAlertMessage">${msg}</li></c:forEach></c:if>
					</ul>
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<script charset="utf-8" type="text/javascript">
				$(document).ready(function() {
					var msg = "";
					var messages = "#messages";
					if (vulpe.util.existsVulpePopups()) {
						messages = "#messagesPopup";
					}
					$(messages).removeClass("vulpeMessageError");
					$(messages).removeClass("vulpeMessageSuccess");
					$(messages).removeClass("vulpeMessageInfo");
					$(messages).removeClass("vulpeMessageValidation");
					var closeButton = function(name) {
						return '<div id="closeMessages"><a href="javascript:void(0);" onclick="$(\'' + name + '\').slideUp(\'slow\')"><fmt:message key="vulpe.messages.close"/></a></div>';
					}
					<c:if test="${not empty actionErrors}">
						msg += "<div id='errorMessages' class='vulpeMessageError'><ul class='vulpeMessageList'>";
						<c:forEach items="${actionErrors}" var="msg">
						msg += '<li class="vulpeAlertError">${msg}</li>';
						</c:forEach>
						msg += "</ul>" + closeButton("#errorMessages") + "</div>";
					</c:if>
					<c:if test="${not empty actionMessages}">
						msg += "<div id='successMessages' class='vulpeMessageSuccess'><ul class='vulpeMessageList'>";
						<c:forEach items="${actionMessages}" var="msg">
							msg += '<li class="vulpeAlertMessage">${msg}</li>';
						</c:forEach>
						msg += "</ul>" + closeButton("#successMessages") + "</div>";
					</c:if>
					<c:if test="${not empty actionInfoMessages}">
						msg += "<div id='infoMessages' class='vulpeMessageInfo'><ul class='vulpeMessageList'>";
						<c:forEach items="${actionInfoMessages}" var="msg">
						msg += '<li class="vulpeAlertMessage">${msg}</li>';
						</c:forEach>
						msg += "</ul>" + closeButton("#infoMessages") + "</div>";
					</c:if>
					jQuery(document).bind("keydown", "Esc", function(evt) {
						$(messages).slideUp('slow');
						return false;
					});
					$(messages).html(msg);
					$(messages).slideDown("slow");
					<c:if test="${global['project-view-messageSlideUp']}">
					setTimeout(function() {
			          	$(messages).slideUp("slow");
					}, ${global['project-view-messageSlideUpTime']});
					</c:if>
				});
			</script>
		</c:otherwise>
	</c:choose>
</c:if>
<%@include file="/WEB-INF/protected-jsp/commons/taglibs.jsp"%>
<c:if test="${not empty actionErrors || not empty actionMessages || not empty actionInfoMessages || not empty fieldErrors}">
	<c:choose>
		<c:when test="${IS_EXCEPTION}">
			<c:choose>
			<c:when test="${empty fieldErrors}"><div id="messageTitle" style="display: none"><fmt:message key="vulpe.error.warning" /></div></c:when>
			<c:otherwise><div id="messageTitle" style="display: none"><fmt:message key="vulpe.error.validate" /></div></c:otherwise>
			</c:choose>
			<!--IS_EXCEPTION-->
			<div id="errors">
			<c:choose>
				<c:when test="${ajax}">
					<!--SUBMIT_AJAX-->
					<ul>
						<c:if test="${not empty actionErrors}"><c:forEach items="${actionErrors}" var="message"><li class="vulpeAlertError">${message}</li></c:forEach></c:if>
						<c:if test="${not empty actionMessages}"><c:forEach items="${actionMessages}" var="message"><li class="vulpeAlertMessage">${message}</li></c:forEach></c:if>
						<c:if test="${not empty actionInfoMessages}"><c:forEach items="${actionInfoMessages}" var="message"><li class="vulpeAlertMessage">${message}</li></c:forEach></c:if>
					</ul>
				</c:when>
				<c:otherwise>
					<ul>
						<c:if test="${not empty actionErrors}"><c:forEach items="${actionErrors}" var="message"><li class="vulpeAlertError">${message}</li></c:forEach></c:if>
						<c:if test="${not empty actionMessages}"><c:forEach items="${actionMessages}" var="message"><li class="vulpeAlertMessage">${message}</li></c:forEach></c:if>
						<c:if test="${not empty actionInfoMessages}"><c:forEach items="${actionInfoMessages}" var="message"><li class="vulpeAlertMessage">${message}</li></c:forEach></c:if>
					</ul>
				</c:otherwise>
			</c:choose>
			</div>
		</c:when>
		<c:otherwise>
			<script charset="utf-8" type="text/javascript">
				$(document).ready(function() {
					var message = "";
					var messages = "#messages";
					var token = "";
					if (vulpe.util.existsVulpePopups()) {
						messages = "#messagesPopup";
					}
					$(messages).removeClass("vulpeMessageError");
					$(messages).removeClass("vulpeMessageSuccess");
					$(messages).removeClass("vulpeMessageInfo");
					$(messages).removeClass("vulpeMessageValidation");
					var closeButton = function(name) {
						return '<div id="closeMessages"><a href="javascript:void(0);" class="vulpeShowHide element[' + name + ']"><fmt:message key="vulpe.messages.close"/></a></div>';
					}
					<c:if test="${not empty actionErrors}">
						message += "<div id='errorMessages' class='vulpeMessageError'><ul class='vulpeMessageList'>";
						<c:forEach items="${actionErrors}" var="message">
						message += '<li class="vulpeAlertError">${message}</li>';
						</c:forEach>
						message += "</ul>" + closeButton("errorMessages") + "</div>";
					</c:if>
					<c:if test="${not empty actionMessages}">
						message += "<div id='successMessages' class='vulpeMessageSuccess'><ul class='vulpeMessageList'>";
						<c:forEach items="${actionMessages}" var="message">
							message += '<li class="vulpeAlertMessage">${message}</li>';
						</c:forEach>
						message += "</ul>" + closeButton("successMessages", messages) + "</div>";
					</c:if>
					<c:if test="${not empty actionInfoMessages}">
						message += "<div id='infoMessages' class='vulpeMessageInfo'><ul class='vulpeMessageList'>";
						<c:forEach items="${actionInfoMessages}" var="message">
						message += '<li class="vulpeAlertMessage">${message}</li>';
						</c:forEach>
						message += "</ul>" + closeButton("infoMessages", messages) + "</div>";
					</c:if>
					jQuery(document).bind("keydown", "Esc", function(evt) {
						$(messages).slideUp('slow', function(){ $(this).html(""); });
						return false;
					});
					$(messages).html(message);
					$(messages).slideDown("slow");
					<c:if test="${global['application-view-messageSlideUp']}">
					setTimeout(function() {
			          	$(messages).slideUp("slow", function(){ $(this).html(""); });
					}, ${global['application-view-messageSlideUpTime']});
					</c:if>
				});
			</script>
		</c:otherwise>
	</c:choose>
</c:if>
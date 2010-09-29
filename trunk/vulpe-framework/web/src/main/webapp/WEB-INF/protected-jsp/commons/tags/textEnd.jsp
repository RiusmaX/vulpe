<jsp:doBody/>
<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
<script type="text/javascript">
	jQuery(function($){
		<c:if test="${empty mask}">
		<c:if test="${upperCase}">
		vulpe.util.get('${elementId}').upperCase();
		</c:if>
		<c:if test="${lowerCase}">
		vulpe.util.get('${elementId}').lowerCase();
		</c:if>
		</c:if>
		<c:if test="${not empty autocomplete}">
		var cache = {};
		vulpe.util.get("${elementId}").autocomplete({
			source: function(request, response) {
				if (cache.term == request.term && cache.content) {
					response(cache.content);
				}
				if (new RegExp(cache.term).test(request.term) && cache.content && cache.content.length < 13) {
					var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
					response($.grep(cache.content, function(value) {
	    				return matcher.test(vulpe.util.normalize(value.${autocomplete}))
					}));
				}
				<c:choose>
				<c:when test="${empty autocompleteValueList}">
				var urlAutoComplete = vulpe.util.getURLComplete("${autocompleteURL}");
				var queryString = "entitySelect.autocomplete=${autocomplete}&entitySelect.${autocomplete}=" + request.term;
				$.ajax({
					type: "POST",
					url: urlAutoComplete,
					dataType: "json",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					data:  queryString,
					success: function(data) {
						cache.term = request.term;
						cache.content = data;
						response(data);
					}
				});
				</c:when>
				<c:otherwise>
				var data = ${autocompleteValueList};
				var matcher = new RegExp( $.ui.autocomplete.escapeRegex( request.term ), "i" );
				response($.grep(data, function(value) {
					value = value.label || value.value || value;
					//return matcher.test( value ) || matcher.test( vulpe.util.normalize( value ) );
					return matcher.test(vulpe.util.normalize(value));
				}));
				</c:otherwise>
				</c:choose>
			},
			open: function(event, ui) {
				var elementWidth = vulpe.util.get("${elementId}").css("width");
				$(".ui-autocomplete").css("width", elementWidth);
			},
			<c:if test="${autocompleteSelect}">
			select: function(event, ui) {
				var idValue = "${elementId}";
				var idProperty = idValue.substring(0, idValue.lastIndexOf(".") + 1) + "id";
				vulpe.util.get(idProperty).val(ui.item.id);
				<c:if test="${not empty autocompleteProperties}">
				var autocompleteProperties = "${autocompleteProperties}".split(",");
				for (var i = 0; i < autocompleteProperties.length; i++) {
					vulpe.util.get("${elementId}".replace("${property}", "") + autocompleteProperties[i]).val(eval("ui.item." + autocompleteProperties[i]));
				}
				</c:if>
				vulpe.util.get("${elementId}").blur();
			},
			</c:if>
			minLength: ${autocompleteMinLength}
		});
		</c:if>
	});
</script>
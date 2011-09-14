<jsp:doBody/>
<%@include file="/WEB-INF/protected-jsp/commons/tags/tagEnd.jsp" %>
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
	    				//return matcher.test(vulpe.util.normalize(value.${fn:substring(autocomplete, 0, fn:indexOf(autocomplete, ','))}))
	    				return matcher.test(vulpe.util.normalize(value));
					}));
				}
				<c:choose>
				<c:when test="${empty autocompleteValueList}">
				var urlAutoComplete = vulpe.util.completeURL("${autocompleteURL}");
				var queryString = "entitySelect.autocomplete=${autocomplete}&entitySelect.autocompleteTerm=" + request.term;
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
				var idProperty = idValue.substring(0, idValue.lastIndexOf("_") + 1) + "id";
				vulpe.util.get(idProperty).val(ui.item.id);
				<c:if test="${not empty autocompleteProperties}">
				var autocompleteProperties = "${autocompleteProperties}".split(",");
				for (var i = 0; i < autocompleteProperties.length; i++) {
					var field = vulpe.util.get("${elementId}".replace("${fn:replace(property, '.', '_')}", "") + autocompleteProperties[i]);
					if (field.length == 1) {
						field.val(eval("ui.item." + autocompleteProperties[i]));
					}
				}
				</c:if>
				vulpe.view.selectPopupCache[idProperty] = [ui.item.id, ui.item.value];
				vulpe.exception.hideFieldError({element: vulpe.util.get(idValue)});
				//vulpe.util.get(idProperty).blur();
				<c:if test="${not empty autocompleteCallback}">
				/*
				var autocompleteCallback = "${autocompleteCallback}";
				if (vulpe.util.isNotEmpty(autocompleteCallback)) {
					try {
						eval(webtoolkit.url.decode(autocompleteCallback));
					} catch(e) {
						// do nothing
					}
				}
				*/
				</c:if>
			},
			</c:if>
			minLength: ${autocompleteMinLength}
		});
		</c:if>
	});
</script>
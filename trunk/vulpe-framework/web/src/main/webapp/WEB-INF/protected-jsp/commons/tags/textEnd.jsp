<jsp:doBody/>
<%@include file="/WEB-INF/protected-jsp/commons/tags/endTag.jsp" %>
<script type="text/javascript">
	jQuery(function($){
		<c:if test="${upperCase && empty mask}">
		vulpe.util.get('${elementId}').bestupper();
		</c:if>
		vulpe.util.get('${elementId}').focus(function() {
			$(this).effect("highlight");
		});
		<c:if test="${not empty autoComplete}">
		var idValue = "${elementId}";
		var idProperty = idValue.substring(0, idValue.lastIndexOf(".") + 1) + "id";
		var cache = {};
		vulpe.util.get("${elementId}").autocomplete({
			source: function(request, response) {
				if (cache.term == request.term && cache.content) {
					response(cache.content);
				}
				if (new RegExp(cache.term).test(request.term) && cache.content && cache.content.length < 13) {
					var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
					response($.grep(cache.content, function(value) {
	    				return matcher.test(value.value)
					}));
				}
				var urlAutoComplete = vulpe.util.getURLComplete("${autoCompleteURL}");
				var queryString = "entitySelect.autoComplete=${autoComplete}&entitySelect.${autoComplete}=" + request.term;
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
			},
			open: function(event, ui) {
				var elementWidth = vulpe.util.get("${elementId}").css("width");
				$(".ui-autocomplete").css("width", elementWidth);
			},
			<c:if test="${autoCompleteSelect}">
			select: function(event, ui) {
				vulpe.util.get(idProperty).val(ui.item.id);
			},
			</c:if>
			minLength: ${autoCompleteMinLength}
		});
		</c:if>
	});
</script>
var portal = {

	querySearch: function() {
		if (vulpe.util.isEmpty($('#querySearch').val())) {
			$('#querySearch').focus();
			return false;
		}
		return true;
	}
}
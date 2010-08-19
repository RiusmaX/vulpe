/**
 * JavaScript Application Document
 */
var app = {

	all: {
		carregarNomesObjetos: function(value, index, formName, uri, id, loading) {
			if (value != "") {
				uri += "?index=" + index + "&tipo=" + value;
				vulpe.view.request.submitAjax(formName, uri, id, '', '', loading);
			} else {
				vulpe.util.get(id).html("Selecione um Tipo Objeto.");
			}
		},

		showExecute: function(value, index) {
			var id = "#tipoObjetoExecute" + index;
			if (value == "") {
				$(id).hide();
			} else {
				$(id).show();
			}
		}
	},

	frontend: {

	}
}
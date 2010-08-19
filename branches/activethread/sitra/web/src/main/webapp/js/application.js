/**
 * JavaScript Application Document
 */
var app = {

	all: {
		carregarNomesObjetos: function(value, index, formName, uri, id) {
			if (value != "") {
				uri += "?index=" + index + "&tipo=" + value;
				vulpe.view.request.submitAjax(formName, uri, id, '', '', true);
			} else {
				vulpe.util.get(id).html("<span id='loading" + index + "'>Selecione um Tipo Objeto.</span>");
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
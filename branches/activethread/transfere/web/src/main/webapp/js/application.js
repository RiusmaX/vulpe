/**
 * JavaScript Application Document
 */
var app = {

	all: {
		carregarNomesObjetos: function(value, index, uri, layer) {
			if (value != "") {
				uri += "?index=" + index + "&tipo=" + value;
				vulpe.view.request.submitAjax({url: uri, layer: layer, individualLoading: true});
			} else {
				vulpe.util.get(layer).html("<span id='loading" + index + "'>Selecione um Tipo Objeto.</span>");
			}
		},

		autorizarAgendamento: function(id) {
			var layer = 'vulpeSelectTable-' + vulpe.config.formName;
			vulpe.view.request.submitAjax({url: "/core/Agenda/autorizar/ajax/" + id, layer: layer});
		},

		cancelarAgendamento: function(id) {
			var layer = 'vulpeSelectTable-' + vulpe.config.formName;
			vulpe.view.request.submitAjax({url: "/core/Agenda/cancelar/ajax/" + id, layer: layer});
		},

		reiniciarAgendamento: function(id) {
			var layer = 'vulpeSelectTable-' + vulpe.config.formName;
			vulpe.view.request.submitAjax({url: "/core/Agenda/reiniciar/ajax/" + id, layer: layer});
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
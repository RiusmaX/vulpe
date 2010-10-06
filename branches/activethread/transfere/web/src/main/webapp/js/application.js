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

		autorizarAgendamento: function(id) {
			vulpe.util.setId(id);
			var layer = 'vulpeSelectTable-' + vulpe.config.formName;
			vulpe.view.request.submitAjax(vulpe.config.formName, "/core/Agenda/autorizar/ajax", layer, '', '');
		},

		cancelarAgendamento: function(id) {
			vulpe.util.setId(id);
			var layer = 'vulpeSelectTable-' + vulpe.config.formName;
			vulpe.view.request.submitAjax(vulpe.config.formName, "/core/Agenda/cancelar/ajax", layer, '', '');
		},

		reiniciarAgendamento: function(id) {
			vulpe.util.setId(id);
			var layer = 'vulpeSelectTable-' + vulpe.config.formName;
			vulpe.view.request.submitAjax(vulpe.config.formName, "/core/Agenda/reiniciar/ajax", layer, '', '');
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
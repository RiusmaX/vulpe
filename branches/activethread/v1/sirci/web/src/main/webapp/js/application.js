/**
 * JavaScript Application Document
 */
var app = {

	frontend: {

	},
	carregarDocumentos: function (formName) {
		vulpe.view.request.submitAjax(formName, '/core/Apontamento/listarDocumentoOrigem/ajax', 'documentos','','',true);
	}
}
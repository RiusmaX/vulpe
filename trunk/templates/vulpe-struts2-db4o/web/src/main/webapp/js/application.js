/**
 * JavaScript Application Document
 */
var app = {

	frontend: {

	},

	preencherQuantidadeEntregue: function (atributo) {
		var inicio = vulpe.util.getPrefixIdByElement(atributo);
		var quantidadeEntregue = vulpe.util.get(inicio + "quantidadeEntregue");
		if (atributo.checked) {
			var valor = vulpe.util.get(inicio + "quantidade").val();
			quantidadeEntregue.val(valor);
			quantidadeEntregue.attr("readOnly", "true");
		} else {
			quantidadeEntregue.val("");
			quantidadeEntregue.removeAttr("readOnly");
		}
	}
}
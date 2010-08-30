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
	},

	privilegios: function(idBatizado, idSexo) {
		var batizado = vulpe.util.get(idBatizado);
		var sexo = vulpe.util.get(idSexo);
		if (eval(batizado.attr("checked"))){
			$('#tipoMinisterio').show();
			if (sexo.val() == 'MASCULINO') {
				$('#privilegio').show();
				$('#privilegioAdicional').show();
			} else {
				$('#privilegio').hide();
				$('#privilegioAdicional').hide();
			}
		} else {
			$('#tipoMinisterio').hide();
			$('#privilegio').hide();
			$('#privilegioAdicional').hide();
		}
	}
}
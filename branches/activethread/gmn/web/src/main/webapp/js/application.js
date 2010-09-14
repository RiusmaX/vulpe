/**
 * JavaScript Application Document
 */
var app = {

	frontend : {

	},

	publicacoes : {
		preencherQuantidadeEntregue : function(entregue) {
			var inicio = vulpe.util.getPrefixIdByElement(entregue);
			var quantidade = vulpe.util.get(inicio + "quantidade");
			var valor = quantidade.val();
			if (valor == "") {
				quantidade.focus();
				return false;
			}
			var quantidadeEntregue = vulpe.util.get(inicio + "quantidadeEntregue");
			if (entregue.checked) {

				quantidadeEntregue.val(valor);
				quantidadeEntregue.attr("readOnly", "true");
			} else {
				quantidadeEntregue.val("");
				quantidadeEntregue.removeAttr("readOnly");
			}
		}
	},

	core : {
		privilegios : function(idBatizado, idSexo) {
			var batizado = vulpe.util.get(idBatizado);
			var sexo = vulpe.util.get(idSexo);
			if (eval(batizado.attr("checked"))) {
				$('#tipoMinisterio').show();
				$('#tipoMinisterioSimples').hide();
				var idTipoMinisterioSimples = vulpe.util.getPrefixIdByElement(batizado)
						+ "tipoMinisterioSimples";
				$('#' + idTipoMinisterioSimples).val("");
				if (sexo.val() == 'MASCULINO') {
					$('#privilegio').show();
					$('#privilegioAdicional').show();
				} else {
					$('#privilegio').hide();
					$('#privilegioAdicional').hide();
				}
			} else {
				$('#tipoMinisterio').hide();
				var idTipoMinisterio = vulpe.util.getPrefixIdByElement(batizado)
						+ "tipoMinisterio";
				$('#' + idTipoMinisterio).val("");
				$('#tipoMinisterioSimples').show();
				$('#privilegio').hide();
				$('#privilegioAdicional').hide();
			}
		},

		privilegiosAdicionais : function(cargo) {
			app.core.mostrarOcultarPrivilegiosAdicionais(4,
					(cargo.value == "ANCIAO" || cargo.value == "SERVO_MINISTERIAL"));
		},

		mostrarOcultarPrivilegiosAdicionais : function(indice, mostrar) {
			var idPrivilegiosAdicionais = "entity.privilegiosAdicionais-" + indice;
			var labels = $("label.checkboxLabel");
			var privilegiosAdicionaisLabel;
			for ( var i = 0; i < labels.length; i++) {
				var label = jQuery(labels[i]);
				if (label.attr("htmlFor") == idPrivilegiosAdicionais) {
					privilegiosAdicionaisLabel = label;
				}
			}
			if (mostrar) {
				vulpe.util.get(idPrivilegiosAdicionais).show();
				privilegiosAdicionaisLabel.show();
			} else {
				vulpe.util.get(idPrivilegiosAdicionais).hide();
				privilegiosAdicionaisLabel.hide();
			}
		}
	}
}
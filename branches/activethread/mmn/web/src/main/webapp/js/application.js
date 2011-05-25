/**
 * JavaScript Application Document
 */
var app = {

	frontend : {

	},

	publications : {
		fillQuantityDelivered : function(delivered) {
			var quantity = vulpe.util.getElementField("quantity", delivered);
			var value = quantity.val();
			if (value == "") {
				quantity.focus();
				return false;
			}
			var quantityDelivered = vulpe.util.getElementField("quantityDelivered", delivered);
			if (delivered.checked) {

				quantityDelivered.val(value);
				quantityDelivered.attr("readOnly", "true");
			} else {
				quantityDelivered.val("");
				quantityDelivered.removeAttr("readOnly");
			}
		}
	},

	core : {
		privileges : function(baptizedId) {
			var baptized = vulpe.util.getElementField(baptizedId);
			var gender = vulpe.util.getElementField("gender");
			if (eval(baptized.attr("checked"))) {
				$('#ministryType').show();
				$('#simpleMinistryType').hide();
				vulpe.util.getElementField("simpleMinistryType").val("");
				if (gender.val() == 'MALE') {
					$('#privilege').show();
					$('#additionalPrivilege').show();
				} else {
					$('#privilege').hide();
					$('#additionalPrivilege').hide();
				}
			} else {
				$('#ministryType').hide();
				vulpe.util.getElementField("ministryType").val("");
				$('#simpleMinistryType').show();
				$('#privilege').hide();
				$('#additionalPrivilege').hide();
			}
		},

		additionalPrivileges : function(responsibility) {
			app.core.showHideAdditionalPrivileges(4, (responsibility.value == "ELDER" || responsibility.value == "MINISTERIAL_SERVANT"));
		},

		showHideAdditionalPrivileges : function(index, show) {
			var additionalPrivilegesId = "entity.additionalPrivileges-" + index;
			var labels = $("label.checkboxLabel");
			var additionalPrivilegesLabel;
			for ( var i = 0; i < labels.length; i++) {
				var label = jQuery(labels[i]);
				if (label.attr("htmlFor") == additionalPrivilegesId) {
					additionalPrivilegesLabel = label;
				}
			}
			if (show) {
				vulpe.util.get(additionalPrivilegesId).show();
				additionalPrivilegesLabel.show();
			} else {
				vulpe.util.get(additionalPrivilegesId).hide();
				additionalPrivilegesLabel.hide();
			}
		}
	}
}
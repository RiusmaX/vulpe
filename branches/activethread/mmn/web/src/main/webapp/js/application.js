/**
 * JavaScript Application Document
 */
vulpe.view.onScroll = function() {
	if ($("#vulpeMain").length == 1 && !vulpe.config.os.iPhone) {
		var scrollTop = $(document).scrollTop();
		var actions = $('#vulpeMainActions');
        if (scrollTop > 154) {
        	actions.addClass('sticky');
        } else {
        	actions.removeClass('sticky');
        }
        var detailActions = $('#vulpeDetailActions-reports');
        if (scrollTop > 190) {
        	detailActions.addClass('stickyDetail');
        	if (vulpe.config.browser.firefox) {
        		//detailActions.css("width", "96.9");
        	}
        } else {
        	detailActions.removeClass('stickyDetail');
        }
    }
}

var app = {

	frontend: {

	},
	
	ministry: {
		changePersonalReportData: function(value) {
			if (vulpe.util.isNotEmpty(value)) {
				vulpe.view.request.submitAjax({url: 'ministry/MemberPersonalReport/update/ajax', layerFields: 'reportData'});
				vulpe.util.get("reportId").val(vulpe.util.get("id").val());
			} else {
				vulpe.util.get("reportId").val("");
			}
		}
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
			var additionalPrivilegesLabel = $("label[for='" + additionalPrivilegesId + "']");
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
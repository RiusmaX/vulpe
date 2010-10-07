jQuery.extend({
	createUploadIframe: function(id) {
		//create frame
		this.frameId = 'jUploadFrame' + id;
		if(window.ActiveXObject) {
			var io = document.createElement('<iframe id="' + this.frameId + '" name="' + this.frameId + '" />');
			io.src = 'javascript:false';
		} else {
			var io = document.createElement('iframe');
			io.id = this.frameId;
			io.name = this.frameId;
		}
		io.style.position = 'absolute';
		io.style.top = '-1000px';
		io.style.left = '-1000px';

		document.body.appendChild(io);

		return jQuery('#'+ this.frameId);
	},

	createUploadForm: function(id, files) {
		//create form
		this.formId = 'jUploadForm' + id;
		var fieldId = '_jUploadField' + id;
		var form = jQuery('<form action="" method="POST" name="' + this.formId + '" id="' + this.formId + '" enctype="multipart/form-data" encoding="multipart/form-data"></form>');
		for(i=0; i<files.length; i++){
			var oldElement = files[i];
			var newElement = jQuery(oldElement).clone();
			jQuery(oldElement).attr('id', files[i].id + fieldId);
			jQuery(oldElement).before(newElement);
			jQuery(oldElement).appendTo(form);
		}
		//set attributes
		jQuery(form).css('position', 'absolute');
		jQuery(form).css('top', '-1200px');
		jQuery(form).css('left', '-1200px');
		jQuery(form).appendTo('body');
		return form;
	},

	uploadFiles: function(s) {
		var id = new Date().getTime();
		var form = jQuery.createUploadForm(id, s.files);
		var frame = jQuery.createUploadIframe(id);
		var frameId = this.frameId;
		var formName = s.formName;
		var layerFields = s.layerFields;
		var queryString = s.queryString;
		var layer = s.layer;
		var validate = s.validate;
		var beforeJs = s.beforeJs;
		var afterJs = s.afterJs;

		var uploadCallback = function() {
			vulpe.view.hideLoading();
			try{
				var io = vulpe.util.getElement(frameId);
				var response = null;
				if(io.contentWindow) {
					response = vulpe.util.trim(io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null);
				}else
				if(io.contentDocument) {
					response = vulpe.util.trim(io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null);
				}

				if (vulpe.util.trim(response) != 'true'){
					jQuery("#messages").html(_vulpeMessageUpload);
					vulpe.view.showMessages();
				}else{
					vulpe.view.request.submitForm({
						formName: formName,
						layerFields: layerFields,
						queryString: queryString,
						layer: layer,
						validate: validate,
						beforeJs: beforeJs,
						afterJs: afterJs,
						isFile: true
					});
				}
			}catch(e){
				jQuery("#messages").html(_vulpeMessageUpload);
				vulpe.view.showMessages();
			}
			form.remove();
			frame.remove();
		}

		if(window.attachEvent){
			vulpe.util.getElement(this.frameId).attachEvent('onload', uploadCallback);
		} else {
			vulpe.util.getElement(this.frameId).addEventListener('load', uploadCallback, false);
		}

		try {
			var form = jQuery('#' + this.formId);
			jQuery(form).attr('action', jQuery.getUploadURL(formName));
			jQuery(form).attr('method', 'POST');
			jQuery(form).attr('target', this.frameId);
			if(form.encoding) {
				form.encoding = 'multipart/form-data';
			} else {
				form.enctype = 'multipart/form-data';
			}
			jQuery(form).submit();
		} catch(e) {
			jQuery("#messages").html(_vulpeMessageUpload);
			vulpe.view.showMessages();
			return;
		}
		vulpe.view.showLoading();
	},

	getUploadURL: function(formName) {
		var formAction = vulpe.util.getElement(formName).action;
		formAction = formAction.replace('.action', '');
		formAction = formAction.substring(0, formAction.lastIndexOf('/'));
		return formAction + '/upload.action?ajax=true';
	}
});
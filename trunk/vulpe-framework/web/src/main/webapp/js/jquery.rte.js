/*
 * jQuery RTE plugin 0.2 - create a rich text form for Mozilla, Opera, and Internet Explorer
 *
 * Copyright (c) 2007 Batiste Bieler
 * Distributed under the GPL (GPL-LICENSE.txt) licenses.
 */

function selectItem(select, value){
	for(var i=0;i<select.options.length;i++) {
		if(value == select.options[i].value){
			select.selectedIndex=i;
			return true;
		}
	}
	return false;
}

// define the rte light plugin
(function(jQuery){
	jQuery.rte = function(elements, css_url){
		var rtes = new Array();
		elements.each(function(i){
			rtes[i] = new jQuery.rte.JRte(this, css_url);
		});
		return jQuery(rtes);
	};
	
	jQuery.fn.rte = function(css_url) {
		return jQuery.rte(this, css_url);
	};
	
	jQuery.rte.JRte = function (ta, css_url) {
		this.textarea = null;
		this.iframe = null;
		this.tb = null;
		
		var element = jQuery(ta);
		if(element.length == 1 && (document.designMode || document.contentEditable)) {
			this.textarea = element;
			this.createIFrame(css_url);
		} else {
			throw 'Erro ao iniciar editor.';
		}
	};
	
	jQuery.rte.JRte.prototype = {
		formatText: function (command, option) {
			this.iframe.contentWindow.focus();
			try{
				this.iframe.contentWindow.document.execCommand(command, false, option);
			}catch(e){
			}
			this.updateTextarea();
			this.iframe.contentWindow.focus();
		},
		
		setDesignMode: function (mode) {
			if (document.contentEditable) {
				if (mode)
					this.iframe.contentWindow.document.designMode = "On";
				else
					this.iframe.contentWindow.document.designMode = "Off";
				return true;
			} else
			if (document.designMode != null) {
				try {
					if (mode)
						this.iframe.contentWindow.document.designMode = "on";
					else
						this.iframe.contentWindow.document.designMode = "off";
					return true;
				} catch (error) {
				}
			}
			return false;
		},
		
		tryEnableDesignMode: function (doc, callback) {
			try {
				this.iframe.contentWindow.document.open();
				this.iframe.contentWindow.document.write(doc);
				this.iframe.contentWindow.document.close();
			} catch(error) {
			}
			
			if (this.setDesignMode(true)) {
				callback();
				return true;
			}
			
			var rte = this;
			setTimeout(function(){
				rte.tryEnableDesignMode(doc, callback);
			}, 250);
			return false;
		},
		
		createIFrame: function (css_url) {
			// need to be created this way
			this.iframe = document.createElement("iframe");
			this.iframe.frameBorder=0;
			this.iframe.frameMargin=0;
			this.iframe.framePadding=0;
			if(this.textarea.attr('class'))
				this.iframe.className = this.textarea.attr('class');
			if(this.textarea.attr('id'))
				this.iframe.id = this.textarea.attr('id');
			if(this.textarea.attr('name'))
				this.iframe.title = this.textarea.attr('name');
			if(this.textarea.attr('style'))
				jQuery(this.iframe).attr('style', this.textarea.attr('style'));
			this.textarea.after(this.iframe);
			var css = "";
			if(css_url)
				var css = "<link type='text/css' rel='stylesheet' href='"+css_url+"' />"
			var content = this.textarea.val();
			// Mozilla need this to display caret
			if(jQuery.trim(content)=='')
				content = '<br>';
			var doc = "<html><head>"+css+"</head><body class='rte-frameBody'>"+content+"</body></html>";
			var rte = this;
			this.tryEnableDesignMode(doc, function() {
				rte.createToolbar();
				jQuery(rte.iframe).before(rte.tb);
				rte.textarea.hide();
				if (rte.isMozilla3())
					rte.forceInit();
			});
		},
		
		forceInit: function(){
			var rte = this;
			if (rte.iframe && rte.iframe.contentWindow && rte.iframe.contentWindow.document) {
				if (rte.iframe.contentWindow.document.designMode.toLowerCase() == 'off') {
					if (rte.setDesignMode(true)) {
						if (rte.iframe.contentWindow.document.designMode.toLowerCase() == 'on') {
							rte.updateIFrame();
							rte.bindEvents();
						}
					}
				}
				setTimeout(function(){
					rte.forceInit();
				}, 250);
			}
		},
		
		isMozilla3: function(){
			if (jQuery.browser.mozilla) {
				var version = parseFloat((jQuery.browser.version.substring(0, jQuery.browser.version.indexOf('.')+2)));
				if (version >= 1.9) {
					return true;
				}
			}
			return false;
		},
		
		enableDesignMode: function () {
			this.updateIFrame();
			this.textarea.hide();
			jQuery('.rte-toolbar-dm', this.tb).show();
			jQuery('.rte-disable', this.tb).attr('title', 'Modo HTML');
			jQuery(this.iframe).show();
			jQuery(this.iframe).before(this.tb);
		},
		
		disableDesignMode: function () {
			this.updateTextarea();
			jQuery(this.iframe).hide();
			jQuery('.rte-toolbar-dm', this.tb).hide();
			jQuery('.rte-disable', this.tb).attr('title', 'Modo Design');
			this.textarea.show();
			this.textarea.before(this.tb);
		},
		
		updateIFrame: function () {
			var content = this.textarea.val();
			this.iframe.contentWindow.document.getElementsByTagName("body")[0].innerHTML = content;
			return content;
		},
		
		updateTextarea: function () {
			var content = this.iframe.contentWindow.document.getElementsByTagName("body")[0].innerHTML;
			this.textarea.val(content);
			return content;
		},
		
		createToolbar: function () {
			this.tb = jQuery("<div class='rte-toolbar'><div class='rte-toolbar-dm'>\
				<p>\
					Estilo: <select class='rte-styles'>\
						<option value=''>Padr&atilde;o</option>\
						<option value='p'>Par&aacute;grafo</option>\
						<option value='h1'>T&iacute;tulo 1</option>\
						<option value='h2'>T&iacute;tulo 2</option>\
						<option value='h3'>T&iacute;tulo 3</option>\
					</select>\
					Fonte: <select class='rte-fonts'>\
						<option value=''>Padr&atilde;o</option>\
						<option value='arial'>Arial</option>\
						<option value='courier'>Courier</option>\
						<option value='tahoma'>Tahoma</option>\
						<option value='times new roman'>Times New Roman</option>\
						<option value='sans-serif'>Sans-Serif</option>\
						<option value='verdana'>Verdana</option>\
					</select>\
					Tamanho: <select class='rte-size'>\
						<option value=''>Padr&atilde;o</option>\
						<option value='1'>1</option>\
						<option value='2'>2</option>\
						<option value='3'>3</option>\
						<option value='4'>4</option>\
						<option value='5'>5</option>\
						<option value='6'>6</option>\
					</select>\
				</p>\
				<p>\
					<a href='#' class='rte-bold' title='Negrito'><img src='"+vulpe.config.contextPath+"/themes/"+vulpe.config.theme+"/images/rte-bold.png' /></a>\
					<a href='#' class='rte-italic' title='It&aacute;lico'><img src='"+vulpe.config.contextPath+"/themes/"+vulpe.config.theme+"/images/rte-italic.png' /></a>\
					<a href='#' class='rte-underline' title='Sublinhado'><img src='"+vulpe.config.contextPath+"/themes/"+vulpe.config.theme+"/images/rte-underline.png' /></a>\
					<a href='#' class='rte-justifyleft' title='&Agrave; esquerda'><img src='"+vulpe.config.contextPath+"/themes/"+vulpe.config.theme+"/images/rte-justify-left.jpg' /></a>\
					<a href='#' class='rte-justifycenter' title='Centralizado'><img src='"+vulpe.config.contextPath+"/themes/"+vulpe.config.theme+"/images/rte-justify-center.jpg' /></a>\
					<a href='#' class='rte-justifyright' title='&Agrave; direita'><img src='"+vulpe.config.contextPath+"/themes/"+vulpe.config.theme+"/images/rte-justify-right.jpg' /></a>\
					<a href='#' class='rte-insertorderedlist' title='Marcador'><img src='"+vulpe.config.contextPath+"/themes/"+vulpe.config.theme+"/images/rte-insert-ordered-list.jpg' /></a>\
					<a href='#' class='rte-insertunorderedlist' title='Lista num&eacute;rica'><img src='"+vulpe.config.contextPath+"/themes/"+vulpe.config.theme+"/images/rte-insert-unordered-list.jpg' /></a>\
					<a href='#' class='rte-link' title='Link'><img src='"+vulpe.config.contextPath+"/themes/"+vulpe.config.theme+"/images/rte-link.png' /></a>\
					<a href='#' class='rte-image' title='Imagem'><img src='"+vulpe.config.contextPath+"/themes/"+vulpe.config.theme+"/images/rte-image.png' /></a>\
				</p>\
				</div>\
				<div class='rte-toolbar-tm'>\
				<p>\
					<a href='#' class='rte-disable' title='Modo HTML'><img src='"+vulpe.config.contextPath+"/themes/"+vulpe.config.theme+"/images/rte-switch.png' /></a>\
				</p></div></div>");
			
			// eventos do toolbar
			var rte = this;
			jQuery('select.rte-styles', this.tb).change(function(){
				var index = this.selectedIndex;
				if( index!=0 ) {
					var selected = this.options[index].value;
					rte.formatText("formatblock", '<'+selected+'>');
				}
			});
			
			jQuery('select.rte-fonts', this.tb).change(function(){
				var index = this.selectedIndex;
				if( index!=0 ) {
					var selected = this.options[index].value;
					rte.formatText("fontname", selected);
				}
			});
			jQuery('select.rte-size', this.tb).change(function(){
				var index = this.selectedIndex;
				if( index!=0 ) {
					var selected = this.options[index].value;
					rte.formatText("fontsize", selected);
				}
			});
			jQuery('.rte-bold', this.tb).click(function(){
				rte.formatText('bold');
				return false;
			});
			jQuery('.rte-italic', this.tb).click(function(){
				rte.formatText('italic');
				return false;
			});
			jQuery('.rte-underline', this.tb).click(function(){
				rte.formatText('underline');
				return false;
			});
			jQuery('.rte-justifyleft', this.tb).click(function(){
				rte.formatText('justifyleft');
				return false;
			});
			jQuery('.rte-justifycenter', this.tb).click(function(){
				rte.formatText('justifycenter');
				return false;
			});
			jQuery('.rte-justifyright', this.tb).click(function(){
				rte.formatText('justifyright');
				return false;
			});
			jQuery('.rte-insertorderedlist', this.tb).click(function(){
				rte.formatText('insertorderedlist');
				return false;
			});
			jQuery('.rte-insertunorderedlist', this.tb).click(function(){
				rte.formatText('insertunorderedlist');
				return false;
			});
			jQuery('.rte-link', this.tb).click(function(){ 
				var p = prompt("URL:");
				if(p)
					rte.formatText('CreateLink', p);
				return false;
			});
			jQuery('.rte-image', this.tb).click(function(){ 
				var p = prompt("Imagem URL:");
				if(p)
					rte.formatText('InsertImage', p);
				return false;
			});
			jQuery('.rte-disable', this.tb).click(function(){
				// se textarea não visivel, o exibe.
				if (rte.textarea.is(":hidden")) {
					rte.disableDesignMode();
				} else {
					rte.enableDesignMode();
				}
				return false;
			});
			
			this.bindEvents();
		},
		
		bindEvents: function(){
			var rte = this;
			var iframeDoc = jQuery(rte.iframe.contentWindow.document);
			iframeDoc.mouseup(function(){
				var node = rte.getSelectionElement();
				rte.setSelectedFontAndSize(node);
				rte.setSelectedStyle(node);
				rte.updateTextarea();
				return true;
			});
			
			iframeDoc.keyup(function(e){
				var node = rte.getSelectionElement();
				rte.setSelectedFontAndSize(node);
				rte.setSelectedStyle(node);
				rte.updateTextarea();
				return true;
			});
		},
		
		setSelectedStyle: function (node) {
			var styles = jQuery('select.rte-styles', this.tb)[0];
			while(node.parentNode) {
				var nName = node.nodeName.toLowerCase();
				if (selectItem(styles, nName))
					return true;
				node = node.parentNode;
			}
			styles.selectedIndex=0;
			return true;
		},
		
		setSelectedFontAndSize: function (node) {
			var jNode = jQuery(node);
			
			var size = jQuery('select.rte-size', this.tb).get(0);
			var font = jQuery('select.rte-fonts', this.tb).get(0);
			
			var achouFont = false;
			var achouSize = false;
			
			var upSizeFont = function(){
				if (this.size && !achouSize)
					achouSize = selectItem(size, this.size);
				if (this.style.fontFamily && !achouFont)
					achouFont = selectItem(font, this.style.fontFamily.toLowerCase());
				if (this.face && !achouFont)
					achouFont = selectItem(font, this.face.toLowerCase());
			};
			if (jNode.attr('tagName') == 'FONT') {
				jNode.each(upSizeFont);
			}
			if (!achouFont || !achouSize) {
				var fonts = jNode.parents('font');
				if (fonts && fonts.length > 0) {
					fonts.each(upSizeFont);
				}
			}
			
			if (!achouFont) {
				var upFont = function(){
					if (this.style.fontFamily && !achouFont)
						achouFont = selectItem(font, this.style.fontFamily.toLowerCase());
				};
				if (jNode.attr('tagName') == 'SPAN') {
					jNode.each(upFont);
				}
				
				if (!achouFont) {
					var spans = jNode.parents('span');
					if (spans.length > 0) {
						spans.each(upFont);
					}
				}
				
				if (!achouFont) {
					if (jNode.attr('tagName') == 'P') {
						jNode.each(upFont);
					}
					if (!achouFont) {
						var ps = jNode.parents('p');
						if (ps.length > 0) {
							ps.each(upFont);
						}
					}
				}
			}
			
			if (!achouSize)
				size.selectedIndex = 0;
			
			if (!achouFont)
				font.selectedIndex = 0;
			
			return true;
		},
		
		getSelectionElement: function () {
			var node = null;
			if (this.iframe.contentWindow.document.selection) {
				// IE selections
				var selection = this.iframe.contentWindow.document.selection;
				var range = selection.createRange();
				try {
					node = range.parentElement();
				} catch (e) {
					return false;
				}
			} else {
				// Mozilla selections
				var range = null;
				try {
					var selection = this.iframe.contentWindow.getSelection();
					range = selection.getRangeAt(0);
				} catch(e){
					return false;
				}
				node = range.commonAncestorContainer;
			}
			return node;
		}
	};
})(jQuery);
/*
 * SimpleModal 1.1.1 - jQuery Plugin
 * http://www.ericmmartin.com/projects/simplemodal/
 * http://plugins.jquery.com/project/SimpleModal
 * http://code.google.com/p/simplemodal/
 *
 * Copyright (c) 2007 Eric Martin - http://ericmmartin.com
 *
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * Revision: $Id: jquery.simplemodal.js,v 1.4 2008/06/20 18:46:36 p057120 Exp $
 *
 */
(function(jQuery) {
	
	jQuery.modal = function(data, options) {
		var m = new jQuery.modal.JModal(data, options);
		jQuery.modal.current[jQuery.modal.current.length] = m;
		return m;
	};
	
	jQuery.fn.modal = function(options) {
		return jQuery.modal(this, options);
	};
	
	jQuery.modal.defaults = {
		incrementCurrent : true,
		overlay : 50,
		overlayId : 'modalOverlay',
		overlayCss : {
			height : '100%',
			width : '100%',
			position : 'fixed',
			left : 0,
			top : 0,
			zIndex : 5001
		},
		containerId : 'modalContainer',
		containerCss : {
			position : 'fixed',
			zIndex : 5002
		},
		close : true,
		closeTitle : 'Close',
		closeClass : 'modalClose',
		persist : false,
		onOpen : null,
		onShow : null,
		onClose : null,
		iframeCss : {
			opacity : 0,
			position : 'absolute',
			zIndex : 5000,
			width : '100%',
			top : 0,
			left : 0
		}
	};
	
	jQuery.modal.current = new Array();
	
	jQuery.modal.JModal = function(data, options) {
		this.opts = null;
		this.dialog = {};

		this.opts = jQuery.extend( {}, jQuery.modal.defaults, options);
		if (!this.opts.overlayCss)
			this.opts.overlayCss = jQuery.modal.defaults.overlayCss;
		else
			this.opts.overlayCss = jQuery.extend( {},
					jQuery.modal.defaults.overlayCss, this.opts.overlayCss);

		if (!this.opts.containerCss)
			this.opts.containerCss = jQuery.modal.defaults.containerCss;
		else
			this.opts.containerCss = jQuery.extend( {},
					jQuery.modal.defaults.containerCss, this.opts.containerCss);

		if (!this.opts.iframeCss)
			this.opts.iframeCss = jQuery.modal.defaults.iframeCss;
		else
			this.opts.iframeCss = jQuery.extend( {},
					jQuery.modal.defaults.iframeCss, this.opts.iframeCss);

		if (!this.opts.overlayCss.opacity)
			this.opts.overlayCss.opacity = this.opts.overlay / 100;

		if (this.opts.incrementCurrent && jQuery.modal.current.length > 0) {
			var zIndex = jQuery.modal.current[jQuery.modal.current.length - 1].opts.containerCss.zIndex;
			if (this.opts.iframeCss.zIndex < zIndex) {
				zIndex = zIndex + 100;
				this.opts.iframeCss.zIndex = zIndex++;
				this.opts.overlayCss.zIndex = zIndex++;
				this.opts.containerCss.zIndex = zIndex;
			}
		}

		if (typeof data == 'object') {
			data = data instanceof jQuery ? data : jQuery(data);
			if (data.parent().parent().size() > 0) {
				this.dialog.parentNode = data.parent();
				if (!this.opts.persist) {
					this.dialog.original = data.clone(true);
				}
			}
		} else if (typeof data == 'string' || typeof data == 'number') {
			data = jQuery('<div>').html(data);
		} else {
			if (console) {
				console
						.log('SimpleModal Error: Unsupported data type: ' + typeof data);
			}
			return false;
		}
		this.dialog.data = data.addClass('modalData');
		data = null;
		this.create();
		this.open();
		if (jQuery.isFunction(this.opts.onShow)) {
			this.opts.onShow.apply(this, [ this.dialog ]);
		}
	};

	jQuery.modal.JModal.prototype = {
		create : function() {
			this.dialog.overlay = jQuery('<div>').attr('id',
					this.opts.overlayId).addClass('modalOverlay').css(
					this.opts.overlayCss).hide().appendTo('body');
			this.dialog.container = jQuery('<div>')
					.attr('id', this.opts.containerId)
					.addClass('modalContainer')
					.css(this.opts.containerCss)
					.append(
							this.opts.close ? '<div class=\"modalContainerHeader drag\"><h1 class=\"popupCaption drag\"><a class="modalCloseImg '
									+ this.opts.closeClass
									+ '" title="'
									+ this.opts.closeTitle
									+ '" style="z-index: '
									+ this.opts.containerCss.zIndex
									+ '"></a></h1></div>'
									: '').hide().appendTo('body');
			if (jQuery.browser.msie && (jQuery.browser.version < 7)) {
				this.fixIE();
			}
			this.dialog.container.append(this.dialog.data.hide());
		},

		bindEvents : function() {
			var modal = this;
			jQuery('.' + this.opts.closeClass, this.dialog.container).click(
					function(e) {
						e.preventDefault();
						modal.close();
					});
		},

		unbindEvents : function() {
			jQuery('.' + this.opts.closeClass, this.dialog.container).unbind(
					'click');
		},

		fixIE : function() {
			var wHeight = jQuery(document.body).height() + 'px';
			var wWidth = jQuery(document.body).width() + 'px';
			this.dialog.overlay.css( {
				position : 'absolute',
				height : wHeight,
				width : wWidth
			});
			this.dialog.container.css( {
				position : 'absolute'
			});
			if (!this.opts.iframeCss.height)
				this.opts.iframeCss.height = wHeight;
			if (!this.opts.iframeCss.width)
				this.opts.iframeCss.width = wWidth;
			this.dialog.iframe = jQuery('<iframe src="javascript:false;">')
					.css(this.opts.iframeCss).hide().appendTo('body');
		},

		open : function() {
			var wnd = $(window), doc = $(document), pTop = doc.scrollTop(), pLeft = doc
					.scrollLeft();

			if (this.dialog.iframe) {
				this.dialog.iframe.show();
			}
			if (jQuery.isFunction(this.opts.onOpen)) {
				this.opts.onOpen.apply(this, [ this.dialog ]);
			} else {
				this.dialog.overlay.show();
				this.dialog.container.show();
				this.dialog.data.show();
			}

			pLeft += (wnd.width() - this.dialog.container.outerWidth()) / 2;
			pTop += (wnd.height() - this.dialog.container.outerHeight()) / 2;
			if (_iPhone) {
				pTop = _iPhonePopupTop;
			}
			this.dialog.container.css( {
				top : pTop,
				left : pLeft
			});

			this.bindEvents();
			if (this.opts.close) {
				this.dialog.container.resizable( {
					animate : true,
					ghost : true,
					//handles : "all",
					//handles : "e,w",
					transparent : true
				});
				this.dialog.container.draggable( {
					handle : ".drag"
				});
			}
		},

		close : function(external) {
			if (!this.dialog.data) {
				return false;
			}
			if (jQuery.isFunction(this.opts.onClose) && !external) {
				this.opts.onClose.apply(this, [ this.dialog ]);
			} else {
				this.unbindEvents();
				if (this.dialog.parentNode) {
					if (this.opts.persist) {
						this.dialog.data.hide()
								.appendTo(this.dialog.parentNode);
					} else {
						this.dialog.data.remove();
						this.dialog.original.appendTo(this.dialog.parentNode);
					}
				} else {
					this.dialog.data.remove();
				}
				this.dialog.container.remove();
				this.dialog.overlay.remove();
				if (this.dialog.iframe) {
					this.dialog.iframe.remove();
				}
				this.dialog = {};
			}
			vulpe.util.removeArray(jQuery.modal.current,
					jQuery.modal.current.length - 1);
		}
	};
})(jQuery);
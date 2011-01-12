(function($) {

/*
 *
 * Copyright (c) 2007 Tulio Faria (http://www.tuliofaria.net - http://www.iwtech.com.br)
 * Licensed under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 *
 * Version 1.0
 * Demo: http://www.tuliofaria.net/jquery-floatnumber/
 *
 * $LastChangedDate$
 * $Rev$
 */
	$.fn.floatnumber = function(separator,precision) {
		return this.each(function() {
			var input = $(this);
			var valid = false;

			function blur(){
				var re = new RegExp(",", "g");
				s = input.val();
				s = s.replace(re, ".");

				if (s == "")
				    s = "0";

				if (!isNaN(s)){
					n = parseFloat(s);

					s = n.toFixed(precision);

					re2 = new RegExp("\\.", "g");
					s = s.replace(re2, separator);

					input.val(s);
				}
			}
			input.bind("blur", blur);
		});
	};

/**
*
*/
	$.fn.currency = function(max) {
		return this.each(function() {
			var input = $(this);
			if (!max) {
				max = 13;
			}
			function keydown(e) {
				var key = e.charCode ? e.charCode : e.keyCode ? e.keyCode : 0;
				var value = input.val();
				value = value.replace("/", "");
				value = value.replace("/", "");
				value = value.replace(",", "");
				value = value.replace(",", "");
				value = value.replace(".", "");
				value = value.replace(".", "");
				value = value.replace(".", "");
				value = value.replace(".", "");
				//Others replaces
				//value = value.replace("-", "");
				//value = value.replace("+", "");
				//value = value.replace("*", "");
				tam = value.length;

				if (tam < max && key != 8) {
					tam = value.length + 1;
				}

				if (key == 8) {
					tam = tam - 1;
				}

				if (tam <= 2) {
					input.val(value) ;
				}
				if ((tam > 2) && (tam <= 5)) {
					input.val(value.substr(0, tam - 2) + ',' + value.substr(tam - 2, tam)) ;
				}
				if ((tam >= 6) && (tam <= 8)) {
					input.val(value.substr(0, tam - 5) + '.' + value.substr(tam - 5, 3) + ',' + value.substr(tam - 2, tam)) ;
				}
				if ((tam >= 9) && (tam <= 11)) {
					input.val(value.substr(0, tam - 8) + '.' + value.substr(tam - 8, 3) + '.' + value.substr(tam - 5, 3) + ',' + value.substr(tam - 2, tam)) ;
				}
				if ((tam >= 12) && (tam <= 14)) {
					input.val(value.substr(0, tam - 11) + '.' + value.substr(tam - 11, 3) + '.' + value.substr(tam - 8, 3) + '.' + value.substr(tam - 5, 3) + ',' + value.substr(tam - 2, tam)) ;
				}
				if ((tam >= 15) && (tam <= 17)) {
					input.val(value.substr(0, tam - 14) + '.' + value.substr(tam - 14, 3) + '.' + value.substr(tam - 11, 3) + '.' + value.substr(tam - 8, 3) + '.' + value.substr(tam - 5, 3) + ',' + value.substr(tam - 2, tam)) ;
				}
			}
			input.bind("keydown", keydown);
		})
	};


/**
*
*/
	$.fn.dateValidate = function() {
		return this.each(function() {
			var input = $(this);
			function blur(e) {
				var date = input.value;
				var array_data = new Array;
				var ExpReg = new RegExp("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/[12][0-9]{3}");
				//vetor que contem o dia o mes e o ano
				array_data = date.split("/");
				erro = false;
				//Valido se a data esta no formato dd/mm/yyyy e se o dia tem 2 digitos e esta entre 01 e 31
				//se o mes tem d2 digitos e esta entre 01 e 12 e o ano se tem 4 digitos e esta entre 1000 e 2999
				if ( date.search(ExpReg) == -1 )
				    erro = true;
				//Valido os meses que nao tem 31 dias com execao de fevereiro
				else if ( ( ( array_data[1] == 4 ) || ( array_data[1] == 6 ) || ( array_data[1] == 9 ) || ( array_data[1] == 11 ) ) && ( array_data[0] > 30 ) )
				    erro = true;
				//Valido o mes de fevereiro
				else if ( array_data[1] == 2 ) {
				    //Valido ano que nao e bissexto
				    if ( ( array_data[0] > 28 ) && ( ( array_data[2] % 4 ) != 0 ) )
				        erro = true;
				    //Valido ano bissexto
				    if ( ( array_data[0] > 29 ) && ( ( array_data[2] % 4 ) == 0 ) )
				        erro = true;
				}
				if ( erro ) {
				    alert("Data Invalida");
				    input.focus();
				}
			}
			input.bind("blur", blur);
		})
	};


/*
 *
 * Copyright (c) 2006/2007 Sam Collett (http://www.texotela.co.uk)
 * Licensed under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 *
 * Version 1.0
 * Demo: http://www.texotela.co.uk/code/jquery/numeric/
 *
 * $LastChangedDate$
 * $Rev$
 */

/*
 * Allows only valid characters to be entered into input boxes.
 * Note: does not validate that the final text is a valid number
 * (that could be done by another script, or server-side)
 *
 * @name     numeric
 * @param    decimal      Decimal separator (e.g. '.' or ',' - default is '.')
 * @param    callback     A function that runs if the number is not valid (fires onblur)
 * @author   Sam Collett (http://www.texotela.co.uk)
 * @example  $(".numeric").numeric();
 * @example  $(".numeric").numeric(",");
 * @example  $(".numeric").numeric(null, callback);
 *
 */

	$.fn.numeric = function(decimal, callback) {
		decimal = decimal || ".";
		callback = typeof callback == "function" ? callback : function(){};
		this.keypress(
			function(e) {
				var key = e.charCode ? e.charCode : e.keyCode ? e.keyCode : 0;
				// allow enter/return key (only when in an input box)
				if (key == 13 && this.nodeName.toLowerCase() == "input") {
					return true;
				} else if (key == 13) {
					return false;
				}
				var allow = false;
				// allow Ctrl+A
				if ((e.ctrlKey && key == 97 /* firefox */) || (e.ctrlKey && key == 65) /* opera */) return true;
				// allow Ctrl+X (cut)
				if ((e.ctrlKey && key == 120 /* firefox */) || (e.ctrlKey && key == 88) /* opera */) return true;
				// allow Ctrl+C (copy)
				if ((e.ctrlKey && key == 99 /* firefox */) || (e.ctrlKey && key == 67) /* opera */) return true;
				// allow Ctrl+Z (undo)
				if ((e.ctrlKey && key == 122 /* firefox */) || (e.ctrlKey && key == 90) /* opera */) return true;
				// allow or deny Ctrl+V (paste), Shift+Ins
				if ((e.ctrlKey && key == 118 /* firefox */) || (e.ctrlKey && key == 86) /* opera */
				|| (e.shiftKey && key == 45)) return true;
				// if a number was not pressed
				if (key < 48 || key > 57) {
					/* '-' only allowed at start */
					if (key == 45 && this.value.length == 0) { return true };
					/* only one decimal separator allowed */
					if (key == decimal.charCodeAt(0) && this.value.indexOf(decimal) != -1) {
						allow = false;
					}
					// check for other keys that have special purposes
					if (
						key != 8 /* backspace */ &&
						key != 9 /* tab */ &&
						key != 13 /* enter */ &&
						key != 35 /* end */ &&
						key != 36 /* home */ &&
						key != 37 /* left */ &&
						key != 39 /* right */ &&
						key != 46 /* del */
					) {
						allow = false;
					} else {
						// for detecting special keys (listed above)
						// IE does not support 'charCode' and ignores them in keypress anyway
						if (typeof e.charCode != "undefined") {
							// special keys have 'keyCode' and 'which' the same (e.g. backspace)
							if (e.keyCode == e.which && e.which != 0) {
								allow = true;
							}
							// or keyCode != 0 and 'charCode'/'which' = 0
							else if (e.keyCode != 0 && e.charCode == 0 && e.which == 0) {
								allow = true;
							}
						}
					}
					// if key pressed is the decimal and it is not already in the field
					if (key == decimal.charCodeAt(0) && this.value.indexOf(decimal) == -1) {
						allow = true;
					}
				} else {
					allow = true;
				}
				return allow;
			}
		).blur(
			function() {
				var val = jQuery(this).val();
				if (val != "") {
					var re = new RegExp("^\\d+$|\\d*" + decimal + "\\d+");
					if (!re.exec(val)) {
						callback.apply(this);
					}
				}
			}
		);
		return this;
	};

/**
* $HeadURL$ 
* $Date$
* $Author$
* $Revision$
*
* Vinter.validate()
* Version 1.9.5
*
* Vinter.validate is (c) 2008 Lars Huring, Olov Nilzén and Vinter (www.vinterwebb.se) and is released under the MIT License:
* http://www.opensource.org/licenses/mit-license.php
* 
* Changelog:
* 1.1:
* - Added checkbox validation
* - Added checkbox groups validation
* - Added isNumber validation
* - Added onerror callback handler
*
* 1.2:
* - Added selectbox validation
* - Added file-validation
*
* 1.3
* - Added radiobutton validation
* - Added textarea validation
*
* 1.4
* - Added possibility to send html in errmsg
* - Fixed removal of notvalidclass on each validation.
*
* 1.5
* - Added possibility to check against default value (title attribute).
* - Added id to error li:s for possibity to hide em'
* - Updated the examples and added a little more documentation
*
* 1.6
* - Added selectbox default values array
* - Added "defaultval" class to <option>, config: selectboxdefaultclass
* - Added selectbox validation to example
*
* 1.7
* - Added source to SVN repository
* - Changed filenames to exclude version numbers
* - Updated jQuery-version to 1.2.6
*
* 1.8
* Added by Mehdi Cherifi (superyms)
* - Added DATE format (Matches the following formats mm/dd/yy, mm/dd/yyyy, mm-dd-yy, mm-dd-yyyy This covers days with 30 or 31 days but does not handle February, it is allowed 30 days.)
* - Added time format validation
* - Added Url validation
*
* Added by Pitt Phunsanit
* - Added float-number validation 
*
* 1.8.1
* - Changed isNumber to include whitespace as valid character
*
* 1.9
* - Wrapped error messages in <label for="input_id"></label>, now they can be clicked and re-focus input to the error field. Cheers @twitter.com/icaaq for idea.
* - Added errorlabelclass option for error message label to enable better styling (see cursor in demo)
* - Changed $ to jQuery to avoid conflicts
*
* 1.9.1
* - Added oncomplete event
*
* 1.9.5
* - Updated default options to a more jQuery-default syntax.
* - Added configuration possibility for required, checkboxgroup and radiogroup classes.
* - Added configuration possibility for required, checkboxgroup and radiogroup patterns.
* - Added override possibilities for all validation methods
*
* Usage:
* Simple: <input type="submit" value="skicka" onclick="return jQuery.validate();" />
* Advanced: http://labs.vinterwebb.se/jquery.validate/Default.aspx
*
*/
	(function(jQuery) {
		jQuery.validate = function(opt) {
			/**
			* Default options
			*/	
			var options = jQuery.extend({
				fieldset: "",
				messagecontainer: "#validationmsg",
				errormsg: ".errmsg",
				notvalidclass: "notvalid",
				messageheader: "Hittade några problem i formuläret",
				onerror: "",
				oncomplete: "",
				erroridprefix: "validationerror_",
				selectboxdefault: [""],
				selectboxdefaultclass: "defaultval",
				usedefault: false,
				errorlabelclass: "errormsglabel",
				requiredclass: "required",
				radiogroupclass: "radiogroup",
				checkboxgroupclass: "checkboxgroupclass",
				isEmail: isEmail,
				isNumber: isNumber,
				isEmpty: isEmpty,
				isUrl: isUrl,
				isDate: isDate,
				isTime: isTime,
				isFloat: isFloat
			}, opt);
			
			options = jQuery.extend({
				requiredpattern: options.fieldset + " ." + options.requiredclass,
				radiogrouppattern: options.fieldset + " ." + options.radiogroupclass,
				checkboxgrouppattern: options.fieldset + " ." + options.checkboxgroupclass
			}, options);
					
		    var errors = new Array();
			jQuery(options.messagecontainer).empty();
			
			jQuery("." + options.notvalidclass).each(function(i, item) {
				jQuery(item).removeClass(options.notvalidclass);
			});
			
			function isEmail(str) {
			    var regex = /^[a-zA-Z0-9._-]+@([a-zA-Z0-9.-]+\.)+[a-zA-Z0-9.-]{2,4}$/;
			    return regex.test(str);
		    }
		    
		    function isNumber(str) {
			    var regex = /^[0-9-\s]*$/;
			    return regex.test(str);
		    }
		    
		    function isEmpty(item) {
			    if (item.value == "") {
			        return true;
			    }
	            if (options.usedefault && item.value == jQuery(item).attr("title")) {
	                return true;
	            }
	            return false;
		    }
			
			function isUrl(str) {
			    var regex = /^((http|ftp|https):\/\/w{3}[\d]*.|(http|ftp|https):\/\/|w{3}[\d]*.)([\w\d\._\-#\(\)\[\]\\,;:]+@[\w\d\._\-#\(\)\[\]\\,;:])?([a-z0-9]+.)*[a-z\-0-9]+.([a-z]{2,3})?[a-z]{2,6}(:[0-9]+)?(\/[\/a-z0-9\._\-,]+)*[a-z0-9\-_\.\s\%]+(\?[a-z0-9=%&amp;\.\-,#]+)?$/;
			    return regex.test(str);
		    }
			
			function isDate(str) {
			    var regex = /^((0?[13578]|10|12)(-|\/)((0[0-9])|([12])([0-9]?)|(3[01]?))(-|\/)((\d{4})|(\d{2}))|(0?[2469]|11)(-|\/)((0[0-9])|([12])([0-9]?)|(3[0]?))(-|\/)((\d{4}|\d{2})))$/;
			    return regex.test(str);
		    }
			
			function isTime(str) {
			    var regex = /^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$/;
			    return regex.test(str);
		    }
			
			function isFloat(str) {
			    var regex = /^([+-]?(((\d+(\.)?)|(\d*\.\d+))([eE][+-]?\d+)?))$/;
			    return regex.test(str);
		    }
			
			function getMsg(item) {
				return jQuery(item.parentNode).find(options.errormsg).html();
			}
			
			function validateTextBox(item) {
				if (options.isEmpty(item) == true || (jQuery(item).hasClass("email") && options.isEmail(item.value) == false)) {
					errors.push({id: item.id, msg: getMsg(item), type: "text"});
				}
				if (options.isEmpty(item) == false && (jQuery(item).hasClass("date") && options.isDate(item.value) == false)) {
					errors.push({id: item.id, msg: getMsg(item), type: "text"});
				}
				if (options.isEmpty(item) == false && (jQuery(item).hasClass("time") && options.isTime(item.value) == false)) {
					errors.push({id: item.id, msg: getMsg(item), type: "text"});
				}
				if (options.isEmpty(item) == false && jQuery(item).hasClass("float") && options.isFloat(item.value) == false) {
					errors.push({id: item.id, msg: getMsg(item), type: "float"});
				}
				if (options.isEmpty(item) == false && (jQuery(item).hasClass("url") && options.isUrl(item.value) == false)) {
					errors.push({id: item.id, msg: getMsg(item), type: "text"});
				}
				if (options.isEmpty(item) == false && jQuery(item).hasClass("number") && options.isNumber(item.value) == false) {
					errors.push({id: item.id, msg: getMsg(item), type: "number"});
				}
			}
			
			function validateCheckbox(item)	{
				if (item.checked != true) {
					errors.push({id: item.id, msg: getMsg(item), type: "checkbox"});
				}
			}
			
			function validateSelect(item) {	
				if (jQuery.inArray(item.value, options.selectboxdefault) > -1 || item[item.selectedIndex].className == options.selectboxdefaultclass) {
					errors.push({id: item.id, msg: getMsg(item), type: "select-one"});
				}
			}
			// Loop
			jQuery(options.requiredpattern).each(function(i, item) {
				// Checkboxes
				switch (item.type) {
					case "checkbox":
						validateCheckbox(item);
						break;
					case "text":
					case "file":
					case "textarea":
					case "hidden":
					case "password":
						validateTextBox(item);
						break;
					case "select-one":
						validateSelect(item);
				}
			});
			
			/*
			* Validate checkbox groups
			*/
			jQuery(options.checkboxgrouppattern + "," + options.radiogrouppattern).each(function(i, item) {
				var checked = 0;
				var msg = jQuery(item).find(options.errormsg).text();
				jQuery(item).find("input[type=checkbox], input[type=radio]").each(function(i, item) {
					if (item.checked)
						checked++;
				});
				if(checked == 0) {
					errors.push({id: item.id, msg: msg, type: "group"});
				}
			});
			/*
			* Check errors length and output to page.
			*/	
			if (errors.length > 0) {
				// Onerror returns errors array to callback
				if (typeof(options.onerror) == "function") {
					options.onerror(errors);
					return false;
				}
				jQuery(options.messagecontainer).fadeIn("slow");
				jQuery("<h4/>").text(options.messageheader).appendTo(jQuery(options.messagecontainer));
				var ul = jQuery("<ul/>");
				jQuery(errors).each(function(i, item) {
					jQuery("#" + item.id).addClass(options.notvalidclass);
					jQuery("<li />")
						.attr("id", options.erroridprefix + item.id)
						.append("<label for='" + item.id + "' class='" + options.errorlabelclass + "'>" + item.msg + "</label>")
						.appendTo(ul);
				});
				ul.appendTo(jQuery(options.messagecontainer));
				return false;
			}			
			if (typeof(options.oncomplete) == "function") {
				options.oncomplete();
			}
			// Hide if there are no errors
			jQuery(options.messagecontainer).fadeOut();
			return true;
		
		}
	
	})(jQuery);

	/**
	* @Copyright (c) 2008 Aurélio Saraiva (aureliosaraiva@gmail.com)
	* @Page http://inovaideia.com.br/maskInputMoney
	* Permission is hereby granted, free of charge, to any person
	* obtaining a copy of this software and associated documentation
	* files (the "Software"), to deal in the Software without
	* restriction, including without limitation the rights to use,
	* copy, modify, merge, publish, distribute, sublicense, and/or sell
	* copies of the Software, and to permit persons to whom the
	* Software is furnished to do so, subject to the following
	* conditions:
	* The above copyright notice and this permission notice shall be
	* included in all copies or substantial portions of the Software.
	*
	* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
	* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
	* OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
	* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
	* HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
	* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
	* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
	* OTHER DEALINGS IN THE SOFTWARE.
	*/

	/**
	* @Version: 0.2
	* @Release: 2008-07-25
	*/
	$.fn.maskMoney = function(settings) {
		settings = $.extend({
			symbol: "US$",
			decimal: ".",
			precision: 2,
			thousands: ",",
			showSymbol:true
		}, settings);

		settings.symbol = settings.symbol + " ";

		return this.each(function() {
			var input=$(this);
			function money(e) {
				e=e||window.event;
				var k=e.charCode||e.keyCode||e.which;
				if (k == 8) { // tecla backspace
					preventDefault(e);
					var x = input.val().substring(0,input.val().length-1);
					input.val(maskValue(x));
					return false;
				} else if (k == 9) { // tecla tab
					return true;
				}
				if (k < 48 || k > 57) {
					preventDefault(e);
					return true;
				}
				var key = String.fromCharCode(k);  // Valor para o código da Chave
				preventDefault(e);
				input.val(maskValue(input.val()+key));
			}

			function preventDefault(e) {
				if (e.preventDefault) { //standart browsers
					e.preventDefault()
				} else { // internet explorer
					e.returnValue = false
				}
			}

			function maskValue(v) {
				v = v.replace(settings.symbol,"");
				var a = '';
				var strCheck = '0123456789';
				var len = v.length;
				var t = "";
				if (len== 0) {
					t = "0.00";
				}
				for (var i = 0; i < len; i++) {
					if ((v.charAt(i) != '0') && (v.charAt(i) != settings.decimal)) {
						break;
					}
				}

				for (; i < len; i++) {
					if (strCheck.indexOf(v.charAt(i))!=-1) { 
						a+= v.charAt(i) 
					};
				}

				var n = parseFloat(a);
				n = isNaN(n) ? 0 : n/Math.pow(10, settings.precision);
				t = n.toFixed(settings.precision);

				var p, d = (t=t.split("."))[1].substr(0, settings.precision);
				for (p = (t=t[0]).length; (p-=3) >= 1;) {
					t = t.substr(0,p) + settings.thousands + t.substr(p);
				}
				return setSymbol(t+settings.decimal+d+Array(
					(settings.precision+1)-d.length).join(0));
			}

			function focusEvent() {
				if (input.val() == "") {
					input.val(setSymbol(getDefaultMask()));
				} else {
					input.val(setSymbol(input.val()));
				}
			}

			function blurEvent() {
				if (input.val() == setSymbol(getDefaultMask())) {
					input.val("");
				} else {
					input.val(input.val().replace(settings.symbol,""))
				}
			}

			function getDefaultMask() {
				var n = parseFloat("0")/Math.pow(10, settings.precision);
				return (n.toFixed(settings.precision)).replace(new RegExp("\\.", "g"), settings.decimal);
			}

			function setSymbol(v) {
				if (settings.showSymbol) {
					return settings.symbol + v;
				}
				return v;
			}

			input.bind("keypress",money);
			input.bind("blur",blurEvent);
			input.bind("focus",focusEvent);

			input.one("unmaskMoney",function() {
				input.unbind("focus",focusEvent);
				input.unbind("blur",blurEvent);
				input.unbind("keypress",money);
				if ($.browser.msie)
				this.onpaste= null;
				else if ($.browser.mozilla)
				this.removeEventListener('input',blurEvent,false);
			});
		});
	}

	$.fn.unmaskMoney=function() {
		return this.trigger("unmaskMoney");
	};

	$.fn.upperCase = function(settings) {
		var defaults = {
			ln : 'en',
			clear : true
		}, settings = $.extend( {}, defaults, settings);
		this.each(function() {
			var $this = $(this);
			if ($this.is('textarea') || $this.is('input:text')) {
				$this.keypress(function(e) {
					var pressedKey = e.charCode == undefined ? e.keyCode : e.charCode;
					var str = String.fromCharCode(pressedKey);
					if (pressedKey < 97 || (pressedKey > 122 && (pressedKey < 128 && pressedKey > 165))) {
						if (settings.ln == 'en' || !isTRChar(pressedKey))
							return;
					}
					if (settings.ln == 'tr' && pressedKey == 105)
						str = '\u0130';
					if (this.createTextRange) {
						window.event.keyCode = str.toUpperCase().charCodeAt(0);
						return;
					} else {
						var startpos = this.selectionStart;
						var endpos = this.selectionEnd;
						this.value = this.value.substr(0, startpos) + str.toUpperCase()
								+ this.value.substr(endpos);
						this.setSelectionRange(startpos + 1, startpos + 1);
						return false;
					}
				});
				if (settings.clear) {
					$this.blur(function(e) {
						if (settings.ln == 'tr')
							this.value = this.value.replace(/i/g, "\u0130");
						this.value = this.value.replace(/^\s+|\s+$/g, "").replace(/\s{2,}/g, " ")
								.toUpperCase();
					});
				}
			}
		});
	};
	function isTRChar(key) {
		var trchar = [ 231, 246, 252, 287, 305, 351 ];
		for ( var i = 0; i < trchar.length; i++) {
			if (trchar[i] == key)
				return true;
		}
		return false;
	};

	$.fn.lowerCase = function(settings) {
		var defaults = {
			ln : 'en',
			clear : true
		}, settings = $.extend( {}, defaults, settings);
		this.each(function() {
			var $this = $(this);
			if ($this.is('textarea') || $this.is('input:text')) {
				$this.keypress(function(e) {
					var pressedKey = e.charCode == undefined ? e.keyCode : e.charCode;
					var str = String.fromCharCode(pressedKey);
					if (pressedKey < 64 || (pressedKey > 90 && (pressedKey < 128 && pressedKey > 165))) {
						if (settings.ln == 'en' || !isTRChar(pressedKey))
							return;
					}
					if (settings.ln == 'tr' && pressedKey == 105)
						str = '\u0130';
					if (this.createTextRange) {
						window.event.keyCode = str.toLowerCase().charCodeAt(0);
						return;
					} else {
						var startpos = this.selectionStart;
						var endpos = this.selectionEnd;
						this.value = this.value.substr(0, startpos) + str.toLowerCase()
								+ this.value.substr(endpos);
						this.setSelectionRange(startpos + 1, startpos + 1);
						return false;
					}
				});
				if (settings.clear) {
					$this.blur(function(e) {
						if (settings.ln == 'tr') {
							this.value = this.value.replace(/i/g, "\u0130");
						}
						this.value = this.value.replace(/^\s+|\s+$/g, "").replace(/\s{2,}/g, " ")
								.toLowerCase();
					});
				}
			}
		});
	};
})(jQuery);
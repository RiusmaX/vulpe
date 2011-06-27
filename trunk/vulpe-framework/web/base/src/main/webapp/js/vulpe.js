/**
 * Vulpe Framework - Copyright 2010 Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
var vulpe = {
	command: function() {},
	//vulpe.config
	config: {
		authenticator: {
			url: {
				redirect: ''
			}
		},
		autocomplete: false,
		browser: {
			firefox: (BrowserDetect.browser == "Firefox" || BrowserDetect.browser == "Mozilla"),
			ie: (BrowserDetect.browser == "MSIE" || BrowserDetect.browser == "Explorer"),
			ie6: ((BrowserDetect.browser == "MSIE" || BrowserDetect.browser == "Explorer") && BrowserDetect.version == "6"),
			webkit: (BrowserDetect.browser == "Chrome" || BrowserDetect.browser == "Safari")
		},
		buttons: {
			ok: "label.vulpe.button.ok",
			cancel: "label.vulpe.button.cancel"
		},
		contextPath: "",
		css: {
			fieldError: "vulpeFieldError"
		},
		defaultActions: ["create", "createPost", "select", "read", "report", "update", "updatePost", "tabular", "tabularPost", "tabularFilter"],
		elements: new Array(),
		entity: "-entity_",
		formName: "",
		hotKeys: [
		          ["Clear", "Alt+Ctrl+Shift+del", ""],
		          ["Create", "Ctrl+f8", ""],
		          ["CreatePost", "Ctrl+f10,return", "dontFireInText,putSameOnReturnKey"],
		          ["Clone", "Ctrl+f11", ""],
		          ["Prepare", "Ctrl+backspace", ""],
		          ["Read", "Ctrl+f9", "putSameOnReturnKey"],
		          ["Report", "Ctrl+f12", ""],
		          ["UpdatePost", "Ctrl+f10", "dontFireInText,putSameOnReturnKey"],
		          ["TabularFilter", "Ctrl+f7", ""],
		          ["TabularPost", "Ctrl+f10", "putSameOnReturnKey"],
		          ["[addDetail]", "Alt+f8", ""],
		          ["[deleteDetail]", "Alt+del", ""],
		          ["[items]", "Ctrl+Shift+[numbers]", ""],
		          ["[tabs]", "Alt+Shift+left,Alt+Shift+right", ""]
		],
		iPhone: {
			popupTop: 0
		},
		javascript: false,
		layers: {
			alertDialog: "#alertDialog",
			vulpeAlertMessage: "#vulpeAlertMessage",
			confirmationDialog: "#confirmationDialog",
			confirmationMessage: "#confirmationMessage",
			messages: "#messages",
			messagesPopup: "#messagesPopup",
			modalMessages: "#modalMessages"
		},
		layout:{
			showLoading: true,
			showReportInNewWindow: false,
			showIconErrorMessage: true
		},
		lightbox: {
			imageText: "vulpe.lightbox.image.text",
			ofText: "vulpe.lightbox.of.text"
		},
		logic: {
			prepareName: ""
		},
		masterTabId: "#vulpeMainBody",
		messages: {
			error: {
				checkfield: "vulpe.error.validate.checkfield",
				checkfields: "vulpe.error.validate.checkfield",
				fatal: "vulpe.error.fatal",
				validate: {
					date: "vulpe.error.validate.date",
					double: "vulpe.error.validate.double",
					email: "vulpe.error.validate.email",
					float: "vulpe.error.validate.float",
					floatRange: "vulpe.error.validate.float.range",
					integer: "vulpe.error.validate.integer",
					integerRange: "vulpe.error.validate.integer.range",
					long: "vulpe.error.validate.long",
					mask: "vulpe.error.validate.mask",
					maxlength: "vulpe.error.validate.maxlength",
					minlength: "vulpe.error.validate.minlength",
					required: "vulpe.error.validate.required",
					requireOneFilter: "vulpe.error.validate.require.one.filter",
					repeatedCharacters: "vulpe.error.validate.repeated.characters"
				}
			},
			clear: "vulpe.message.confirm.clear",
			charCount: "vulpe.message.charCount",
			deleteThis: "vulpe.message.confirm.delete",
			deleteFile: "vulpe.message.confirm.delete.file",
			fieldRequired: "vulpe.js.error.required",
			keyRequired: "vulpe.js.error.key.required",
			deleteSelected: "vulpe.message.confirm.delete.selected",
			loading: "vulpe.message.loading",
			selectRecordsToDelete: "vulpe.message.select.records.to.delete",
			updatePost: "vulpe.message.confirm.updatePost",
			upload: "vulpe.error.upload",
			close: "vulpe.messages.close"
		},
		messageSlideUp: true,
		messageSlideUpTime: 10000,
		onlyToSee: false,
		os: {
			iPhone: (BrowserDetect.OS == "iPhone/iPod")
		},
		order: new Array(),
		pagingPage: "-paging.page",
		prefix: {
			button: "vulpeButton",
			detail: "#vulpeDetail-",
			detailTab: "#vulpeMainBodyTabs",
			popup: "Popup-",
			selectForm: "vulpeSelectForm-",
			selectTable: "vulpeSelectTable-"
		},
		popup: {
			mobile: false,
			selectRow: false
		},
		sortType: "ALL",
		springSecurityCheck: "j_spring_security_check",
		suffix: {
			iconErrorMessage: "-iconErrorMessage",
			errorMessage: "-errorMessage",
			identifier: "-id",
			loading: "-loading",
			selectPopup: "-selectPopup",
			selectedTab: "-selectedTab"
		},
		redirectToIndex: true,
		requireOneFilter: false,
		tabIndex: 0,
		tabsCount: 0,
		theme: 'default',
		token: {
			fieldIndex: "__",
			dot: "_"
		},
		valid: true,
		accentMap: {}
	},

	// RTE Array
	RTEs: new Array(),

	login: {
		logged: false,

		executeBefore: function() {
			return true;
		},
		executeAfter: function() {
			vulpe.view.hideLoading();
			if (vulpe.login.logged) {
				$(window.location).attr("href", vulpe.config.authenticator.url.redirect);
			}
		}
	},

	// vulpe.util
	util: {
		get: function(id, parent) {
			if (vulpe.util.isEmpty(id)) {
				return null;
			}
			var element = jQuery("#" + id, parent);
			if (!element || element.length <= 0) {
				element = jQuery("*[id='" + id + "']");
			}
			return element;
		},

		getElement: function(id, parent) {
			var element = vulpe.util.get(id, parent);
			if (element && element.length > 0) {
				return element.get(0);
			} else {
				return null;
			}
		},

		normalize: function(term) {
			var ret = "";
			if (typeof term != "undefined") {
				for (var i = 0; i < term.length; i++) {
					ret += vulpe.config.accentMap[term.charAt(i)] || term.charAt(i);
				}
			}
			return ret;
		},

		checkRepeatedCharacters: function(value) {
			var firstChar = value.toUpperCase().charAt(0);
			var equalChars = true;
			for (var i = 0; i < value.length; i++) {
				var char = value.toUpperCase().charAt(i);
				if (char != firstChar) {
					equalChars = false;
				}
			}
			return equalChars;
		},

		checkHotKeys: function(parent) {
			var form = vulpe.util.getForm({parent: parent});
			for (var i = 0; i < vulpe.config.hotKeys.length; i++) {
				var hotkey = vulpe.config.hotKeys[i];
				if (hotkey[0] == "[tabs]") {
					var keys = hotkey[1].split(",");
					vulpe.util.addHotKey({
						hotKey: keys[0],
						command: function () {
							vulpe.util.tabControl(-1);
	    	                return false;
						}
	                });
					vulpe.util.addHotKey({
						hotKey: keys[1],
						command: function () {
							vulpe.util.tabControl(1);
	    	                return false;
						}
	                });
				} else {
					var button = vulpe.util.getButton(hotkey[0], $(form).attr("id"));
					if (button.length > 0) {
						var keys = hotkey[1].split(",");
						var additionals = hotkey[2].split(",");
						var options = {
							button: button,
							override: true
						};
						for (var j = 0; j < additionals.length; j++) {
							if (additionals[j] == "dontFireInText") {
								options.dontFireInText = true;
							} else if (additionals[j] == "returnKeyDontFireInText") {
								options.returnKeyDontFireInText = true;
							} else if (additionals[j] == "putSameOnReturnKey") {
								options.putSameOnReturnKey = true;
							}
						}
						for (var j = 0; j < keys.length; j++) {
							options.hotKey = keys[j];
							vulpe.util.addHotKey(options);
						}
					}
				}
			}
		},

		checkDetailHotKeys: function(parent) {
			parent = "#" + parent;
			var key = "";
			for (var i = 0; i < vulpe.config.hotKeys.length; i++) {
				var hotkey = vulpe.config.hotKeys[i];
				if (hotkey[0] == "[addDetail]") {
					var key = hotkey[1];
					break;
				}
			}
			if (key != "") {
				var buttonName = vulpe.config.prefix.button + "AddDetail-" + parent.replace(vulpe.config.prefix.detail, "") + "-" + vulpe.config.formName;
				vulpe.util.removeHotKey(key);
				if (vulpe.util.get(buttonName).length == 1) {
					vulpe.util.addHotKey({
						hotKey: key,
						command: function () {
							vulpe.util.get(buttonName).trigger('click');
							return false;
						}
					});
				}
			}
		},

		checkHotKeyExists: function(hotKey) {
			var elemData = jQuery.data(jQuery.data(document));
			if (elemData.events) {
				var keydown = elemData.events['keydown'];
				for (var i = 0; i < keydown.length; i++) {
					if (keydown[i].data == hotKey) {
						return i;
					}
				}
			}
			return -1;
		},

		renewHotKeys: function(options) {
			var parent = vulpe.util.get(options.layer);
			var renew = false;
			for (var i = 0; i < vulpe.config.defaultActions.length; i++) {
				var action = vulpe.config.defaultActions[i];
				if (options.url.indexOf(action) != -1) {
					renew = true;
					break;
				}
			}
			if (renew && !vulpe.util.existsVulpePopups()) {
				vulpe.util.removeHotKeys(parent);
				vulpe.util.checkHotKeys(parent);
			}
		},

		/**
		 *
		 * @param options {hotKey, command, override, dontFireInText, returnKeyDontFireInText}
		 */
		addHotKey: function(options) {
			var position = vulpe.util.checkHotKeyExists(options.hotKey);
			if (position != -1 && options.override){
				var elemData = jQuery.data(document);
				if (elemData.events && elemData.events['keydown']) {
					var keydown = elemData.events['keydown'];
					vulpe.util.removeArray(keydown, position);
				}
			}
			if (position == -1 || (position != -1 && options.override)) {
				if (options.button) {
					options.command = function() {$(options.button).trigger('click'); return false;};
				}
				jQuery(document).bind("keydown", options.hotKey, options.command);
				var dontFire = function(hotKey) {
					var dontFireInText = jQuery(document).attr("dontFireInText");
					if (!dontFireInText) {
						dontFireInText = new Array();
					}
					dontFireInText[hotKey] = true;
					jQuery(document).attr("dontFireInText", dontFireInText);
				}
				if (options.putSameOnReturnKey && vulpe.util.checkHotKeyExists("return") == -1) {
					jQuery(document).bind("keydown", "return", options.command);
					if (options.returnKeyDontFireInText) {
						dontFire("return");
					}
				}
				if (options.dontFireInText) {
					dontFire(options.hotKey);
				}
			}
		},

		removeHotKey: function(hotKey) {
			var position = vulpe.util.checkHotKeyExists(hotKey);
			if (position != -1){
				var elemData = jQuery.data(jQuery.data(document));
				if (elemData.events && elemData.events["keydown"]) {
					var keydown = elemData.events["keydown"];
					vulpe.util.removeArray(keydown, position);
				}
			}
			return position;
		},

		removeHotKeys: function() {
			vulpe.util.removeHotKey("return");
			for (var i = 0; i < vulpe.config.hotKeys.length; i++) {
				var hotkey = vulpe.config.hotKeys[i];
				var keys = hotkey[1].split(",");
				for (var j = 0; j < keys.length; j++) {
					vulpe.util.removeHotKey(keys[j]);
				}
			}
		},

		getPrefixId: function(formName) {
			if (formName) {
				vulpe.config.formName = formName;
			}
			return vulpe.config.formName + "-" + (vulpe.config.logic.prepareName == "" ? "" : vulpe.config.logic.prepareName + vulpe.config.token.dot);
		},

		getElementId: function(element) {
			return (typeof element == "string") ? element : (element.id ? element.id : jQuery(element).attr("id"));
		},

		getPrefixIdByElement: function(element) {
			var id = vulpe.util.getElementId(element);
			var prefix = id.indexOf(vulpe.config.token.fieldIndex) != -1 ? id.substring(0, id.lastIndexOf(vulpe.config.token.fieldIndex) + vulpe.config.token.fieldIndex.length -1) : id.substring(0, id.lastIndexOf(vulpe.config.token.dot));
			return prefix + vulpe.config.token.dot;
		},

		getDetailByElementId: function(id) {
			var detail = id.substring(0, id.indexOf(vulpe.config.token.fieldIndex));
			detail = detail.substring(detail.lastIndexOf(vulpe.config.token.dot) + 1, detail.length);
			return detail;
		},

		getIndexOfElement: function(element) {
			var id = element.id.substring(0, element.id.lastIndexOf(vulpe.config.token.fieldIndex));
			var index = id.substring(id.lastIndexOf(vulpe.config.token.fieldIndex) + 1, id.length);
			return index;
		},

		getElementField: function(name, element) {
			name = name.replace(/\./g,"_");
			var prefix = element ? vulpe.util.getPrefixIdByElement(element) : vulpe.util.getPrefixId();
			var field = vulpe.util.get(name.indexOf(prefix) != -1 ? name : prefix + name);
			if (field.length == 0 && vulpe.util.isNotEmpty(element)) {
				prefix = vulpe.util.getElementId(element) + vulpe.config.token.dot;
				field = vulpe.util.get(prefix + name);
				if (field.length == 0) {
					prefix = vulpe.util.getElementId(element) + "-";
					field = vulpe.util.get(prefix + name);
				}
			}
			return field;
		},

		getElementConfig: function(id) {
			return vulpe.config.elements[id];
		},

		getButton: function(name, formName) {
			if (!formName) {
				formName = vulpe.config.formName;
			}
			return vulpe.util.get("vulpeButton" + name + "-" + formName);
		},

		getForm: function(options) {
			if (options.formName) {
				vulpe.config.formName = options.formName;
			} else if (options.parent && $("form", options.parent).length > 0) {
				vulpe.config.formName = $($("form", options.parent)[0]).attr("id");
			} else {
				vulpe.config.formName = $("form").attr("id");
			}
			return vulpe.util.get(vulpe.config.formName);
		},

		completeURL: function(url) {
			if (url.indexOf(vulpe.config.contextPath) == -1) {
				url = vulpe.config.contextPath + (url.indexOf('/') == 0 ? '' : '/') + url;
			}
			return url;
		},

		focusFirst: function(layer) {
			try {
				if (!vulpe.config.onlyToSee) {
					var token = "input.focused,select.focused,textarea.focused";
					var fields = layer ? jQuery(token, layer.indexOf("#") == -1 ? vulpe.util.get(layer) : jQuery(layer)) : jQuery(token);
					var focused = false;
					fields.each(function(index) {
						if ($(this).val() == "" && !focused) {
							$(this).focus();
							if (!$(this).is(":focus")) {
								$(this).focus();
							}
							focused = true;
						}
					});
					if (fields.length > 0) {
						if (!focused) {
							fields[0].focus();
							if (!$(fields[0]).is(":focus")) {
								fields[0].focus();
							}
						}
					}
				}
			} catch(e) {
				//do nothing
			}
		},

		controlMultiselect: function(id, check) {
			var multiselect = vulpe.util.get(id);
			var content = multiselect.val();
			var value = check.value;
			if (check.checked) {
				multiselect.val((content) ? content + "," + value : value);
			} else {
				content = content.replace(value, "");
				content = content.replace(value + ",", "");
				multiselect.val(content);
			}
		},

		selectTab: function(name) {
			var selectedTab = vulpe.util.get(vulpe.config.formName + vulpe.config.suffix.selectedTab);
			selectedTab.val(name);
		},

		decode: function(string) {
			var lsRegExp = /\+/g;
			return unescape(string.replace(lsRegExp, " "));
		},

		/**
		 * list:
		 * fieldNameA=valueA,fieldNameB=valueB,fieldNameC=valueC
		 * key:
		 * fieldNameA
		 */
		getValueMap: function(list, key, delimList, delimValue) {
			delimList = typeof delimList != "undefined" ? delimList : ",";
			delimValue = typeof delimValue != "undefined" ? delimValue : "=";

			return vulpe.util.loopMap(list, function(c, v) {
				if (key == c) {
					return v;
				}
			}, delimList, delimValue);
		},

		/**
		 * list:
		 * fieldNameA=valueA,fieldNameB=valueB,fieldNameC=valueC
		 * function:
		 * command(key, value) {}
		 */
		loopMap: function(list, command, delimList, delimValue) {
			if (typeof list == "undefined" || !list || vulpe.util.trim(list) == '') {
				return false;
			}

			delimList = typeof delimList != "undefined" ? delimList : ",";
			delimValue = typeof delimValue != "undefined" ? delimValue : "=";

			var list = list.split(delimList);
			var i = 0;
			for (var i = 0; list && i < list.length; i++) {
				var value = list[i].split(delimValue);
				var elementId = value[0];
				var element = vulpe.util.getElement(elementId);
				if (element != null && element.className.indexOf(vulpe.config.css.fieldError) != -1) {
					vulpe.exception.hideFieldError({element: element});
				}
				var returnedValue = null;
				if (value.length == 1) {
					returnedValue = command(value[0], value[0]);
				} else {
					returnedValue = command(value[0], value[1]);
				}
				if (typeof returnedValue != "undefined") {
					return returnedValue;
				}
			}
		},

		trim: function(s) {
			if (s) {
				if (typeof s == "function") {
					return s.toString();
				} else if (typeof s == "string") {
					return s.replace(/^\s*/, "").replace(/\s*$/, "");
				}
			} else {
				return "";
			}
		},

		ltrim: function (str, chars) {
			chars = chars || "\\s";
			return str.replace(new RegExp("^[" + chars + "]+", "g"), "");
		},

		rtrim: function (str, chars) {
			chars = chars || "\\s";
			return str.replace(new RegExp("[" + chars + "]+$", "g"), "");
		},

		isNotEmpty: function(v) {
			return (v != null && typeof v != "undefined" && v && vulpe.util.trim(v) != "");
		},

		isEmpty: function(v) {
			return !vulpe.util.isNotEmpty(v);
		},

		replaceAll: function(str, from, to) {
			var pos = str.indexOf(from);
			while (pos > -1) {
				str = str.replace(from, to);
				pos = str.indexOf(from);
			}
			return (str);
		},

		removeArray: function(array, from, to) {
			array.splice(from, !to || 1 + to - from + (!(to < 0 ^ from >= 0) && (to < 0 || -1) * array.length));
		},

		getVulpeValidateForms: function(formName) {
			for (var i = 0; i < vulpe.validate.forms.length; i++) {
				if (vulpe.validate.forms[i].name == formName) {
					return i;
				}
			}
			return vulpe.validate.forms.length;
		},

		getVulpePopup: function(popupName) {
			for (var i = 0; i < vulpe.view.popups.length; i++) {
				if (vulpe.view.popups[i] == popupName) {
					return i;
				}
			}
			return vulpe.view.popups.length;
		},

		setVulpePopup: function(popupName) {
			vulpe.view.popups[vulpe.view.popups.length] = popupName;
		},

		existsVulpePopups: function(popupName) {
			if (vulpe.util.isNotEmpty(popupName)) {
				var vulpePopup = vulpe.view.popups[vulpe.util.getVulpePopup(popupName)];
				if (vulpePopup && vulpePopup != null) {
					return true;
				}
			} else if (vulpe.view.popups.length > 0) {
				return true;
			}
			return false;
		},

		getLastVulpePopup: function() {
			return vulpe.view.popups[vulpe.view.popups.length-1];
		},

		tabControl: function(index) {
			if (index == -1) {
				if (vulpe.config.tabIndex == 0) {
					vulpe.config.tabIndex = vulpe.config.tabsCount;
				} else {
					vulpe.config.tabIndex = vulpe.config.tabIndex - 1;
				}
			} else {
				if (vulpe.config.tabIndex == vulpe.config.tabsCount) {
					vulpe.config.tabIndex = 0;
				} else {
					vulpe.config.tabIndex = vulpe.config.tabIndex + 1;
				}
			}
			jQuery(vulpe.config.prefix.detailTab + vulpe.config.tabIndex).trigger('click');
		}
	},
	// vulpe.validate
	validate: {
		forms: new Array(),

		isEmail: function (str) {
		    var regex = /^[a-zA-Z0-9._-]+@([a-zA-Z0-9.-]+\.)+[a-zA-Z0-9.-]{2,4}$/;
		    return regex.test(str);
	    },

	    isNumber: function (str) {
		    var regex = /^[0-9-\s]*$/;
		    return regex.test(str);
	    },

	    isEmpty: function (item) {
		    if (item.value == "") {
		        return true;
		    }
            if (options.usedefault && item.value == jQuery(item).attr("title")) {
                return true;
            }
            return false;
	    },

		isUrl: function (str) {
		    var regex = /^((http|ftp|https):\/\/w{3}[\d]*.|(http|ftp|https):\/\/|w{3}[\d]*.)([\w\d\._\-#\(\)\[\]\\,;:]+@[\w\d\._\-#\(\)\[\]\\,;:])?([a-z0-9]+.)*[a-z\-0-9]+.([a-z]{2,3})?[a-z]{2,6}(:[0-9]+)?(\/[\/a-z0-9\._\-,]+)*[a-z0-9\-_\.\s\%]+(\?[a-z0-9=%&amp;\.\-,#]+)?$/;
		    return regex.test(str);
	    },

		isDate: function (str) {
		    var regex = /^((0?[13578]|10|12)(-|\/)((0[0-9])|([12])([0-9]?)|(3[01]?))(-|\/)((\d{4})|(\d{2}))|(0?[2469]|11)(-|\/)((0[0-9])|([12])([0-9]?)|(3[0]?))(-|\/)((\d{4}|\d{2})))$/;
		    return regex.test(str);
	    },

		isTime: function (str) {
		    var regex = /^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$/;
		    return regex.test(str);
	    },

		isFloat: function (str) {
		    var regex = /^([+-]?(((\d+(\.)?)|(\d*\.\d+))([eE][+-]?\d+)?))$/;
		    return regex.test(str);
	    },

		isArray: function(obj) {
			if (obj.length) {
				return true;
			} else {
				return false;
			}
		},

		keyNumbers: function(keychar) {
			var reg = /\d/;
			return reg.test(keychar);
		},

		getKeyCode: function(e) {
			if (window.event) {
				return e.keyCode;
			} else if (e.which) {
				return e.which;
			} else {
				return null;
			}
		},

		validInteger: function(evt) {
			var whichCode = vulpe.validate.getKeyCode(evt);
			if (whichCode == 13) { // 'Enter'
				return true;
			}
			if (whichCode == 0 || whichCode == 8 || !whichCode) {// 'Tab'
				return true;
			}
			var keychar = String.fromCharCode(whichCode);
			return vulpe.validate.keyNumbers(keychar);
		},

		matchPattern: function(value, mask) {
			if (typeof mask === "string") {
				return eval(mask).test(value);
			}
			return new RegExp(mask).exec(value);
		},

		isAllDigits: function(argvalue) {
			argvalue = argvalue.toString();
			var validChars = "0123456789.";
			var startFrom = 0;
			if (argvalue.charAt(0) == "-") {
				startFrom = 1;
			}
			var cont = 0;
			for (var n = startFrom; n < argvalue.length; n++) {
				if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) {
					return false;
				}
				if (argvalue.substring(n, n+1) == '.') {
					cont = cont+1;
				}
				if (cont > 1) {
					return false;
				}
			}
			return true;
		},

		isValidDate: function(day, month, year) {
			if (month < 1 || month > 12) {
				return false;
			}
			if (day < 1 || day > 31) {
				return false;
			}
			if ((month == 4 || month == 6 || month == 9 || month == 11) && (day == 31)) {
				return false;
			}
			if (month == 2) {
				var leap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
				if (day>29 || (day == 29 && !leap)) {
					return false;
				}
			}
			return true;
		},

		checkEmail: function(emailStr) {
			if (emailStr.length == 0) {
				return true;
			}
			var emailPat = /^(.+)@(.+)$/;
			var specialChars = "\\(\\)<>@,;:\\\\\\\"\\.\\[\\]";
			var validChars = "\[^\\s" + specialChars + "\]";
			var quotedUser = "(\"[^\"]*\")";
			var ipDomainPat = /^(\d{1,3})[.](\d{1,3})[.](\d{1,3})[.](\d{1,3})$/;
			var atom = validChars + '+';
			var word = "(" + atom + "|" + quotedUser + ")";
			var userPat = new RegExp("^" + word + "(\\." + word + ")*$");
			var domainPat = new RegExp("^" + atom + "(\\." + atom + ")*$");
			var matchArray = emailStr.match(emailPat);
			if (matchArray == null) {
				return false;
			}
			var user = matchArray[1];
			var domain = matchArray[2];
			if (user.match(userPat) == null) {
				return false;
			}
			var IPArray = domain.match(ipDomainPat);
			if (IPArray != null) {
				for (var i = 1; i <= 4; i++) {
					if (IPArray[i] > 255) {
						return false;
					}
				}
				return true;
			}
			var domainArray = domain.match(domainPat);
			if (domainArray == null) {
				return false;
			}
			var atomPat = new RegExp(atom, "g");
			var domArr = domain.match(atomPat);
			var len = domArr.length;
			if ((domArr[domArr.length-1].length < 2) || (domArr[domArr.length-1].length > 3)) {
				return false;
			}
			if (len < 2) {
				return false;
			}
			return true;
		},

		validateRequired: function(config) {
			var isValid = true;
			var message = vulpe.config.messages.error.validate.required;
			if (vulpe.validate.isArray(config.field)) {
				var field = jQuery(config.field[0]);
				var idField = field.attr("id");
				var typeField = field.attr("type");
				if (typeField == 'radio') {
					isValid = false;
					for (var i = 0; i < config.field.length; i++) {
						field = jQuery(config.field[i]);
						if (field.attr("checked")) {
							isValid = true;
							break;
						}
					}
					isValid = false;
				}
			}
			
			var idField = config.field.attr("id");
			var typeField = config.field.attr("type");
			if (typeof typeField == "undefined" && vulpe.util.isEmpty(config.field.val())) {
				isValid = false;
			} else if (typeField == 'text' || typeField == 'textarea' || typeField == 'file' ||
					typeField == 'select-one' || typeField == 'radio' || typeField == 'checkbox' ||
					typeField == 'password') {
				var value = '';
				if (typeField == "select-one") {
					var si = config.field.attr("selectedIndex");
					if (si >= 0) {
						var options = config.field.attr("options");
						value = options[si].value;
					}
				} else if (typeField == "checkbox") {
					isValid = config.field.attr("checked");
				} else {
					var idParent = idField.substring(0, idField.lastIndexOf(vulpe.config.token.dot));
					var idSelectPopup = idParent + vulpe.config.suffix.selectPopup;
					var selectPopup = vulpe.util.get(idSelectPopup);
					if (selectPopup != null && selectPopup.length == 1) {
						value = vulpe.util.get(idParent + "_id").val();
						if (vulpe.util.trim(value).length == 0) {
							vulpe.util.get(idField).val("");
						}
					} else {
						value = config.field.val();
					}
				}
				if (vulpe.util.trim(value).length == 0) {
					isValid = false;
				}
			}
			if (!isValid) {
				vulpe.exception.setupError(idField, message);
			}
			return isValid;
		},

		/**
		 * config = {field = {}, minlength = 1}
		 */
		validateMinLength: function(config) {
			var isValid = true;
			var idField = config.field.attr("id");
			var typeField = config.field.attr("type");
			var message = vulpe.config.messages.error.validate.minlength;
			if (typeField == 'text' || typeField == 'textarea') {
				if (vulpe.util.trim(config.field.val()).length > 0 && vulpe.util.trim(config.field.val()).length < parseInt(config.minlength)) {
					isValid = false;
					vulpe.exception.setupError(idField, message.replace("{0}", config.minlength));
				}
			}
			return isValid;
		},

		validateMaxLength: function(config) {
			var isValid = true;
			var idField = config.field.attr("id");
			var typeField = config.field.attr("type");
			var message = vulpe.config.messages.error.validate.maxlength;
			if (typeField == 'text' || typeField == 'textarea') {
				if (vulpe.util.trim(config.field.val()).length > parseInt(config.maxlength)) {
					isValid = false;
					vulpe.exception.setupError(idField, message.replace("{0}", config.maxlength));
				}
			}
			return isValid;
		},

		validateMask: function(config) {
			var isValid = true;
			var idField = config.field.attr("id");
			var typeField = config.field.attr("type");
			var message = vulpe.config.messages.error.validate.mask;
			if (typeField == 'text' || typeField == 'textarea') {
				if (!vulpe.validate.matchPattern(config.field.val(), config.mask)) {
					isValid = false;
				}
			}
			if (!config.setupErrorOff && !bValid) {
				vulpe.exception.setupError(idField, message);
			}
			return isValid;
		},

		validateInteger: function(config) {
			var bValid = true;
			var idField = config.field.attr("id");
			var typeField = config.field.attr("type");
			var message = vulpe.config.messages.error.validate.integer;
			if (typeField == 'text' || typeField == 'textarea') {
				if (config.field.val().length > 0) {
					var value = vulpe.util.replaceAll(config.field.val(), '.', '');
					value = vulpe.util.replaceAll(value, ',', '.');
					if (!vulpe.validate.isAllDigits(value)) {
						bValid = false;
					} else {
						var iValue = parseInt(value);
						if (isNaN(iValue) || !(iValue >= -2147483648 && iValue <= 2147483647)) {
							bValid = false;
						}
					}
				}
			}
			if (!config.setupErrorOff && !bValid) {
				vulpe.exception.setupError(idField, message);
			}
			return bValid;
		},

		validateLong: function(config) {
			return vulpe.validate.validateInteger(config);
		},

		validateFloat: function(config) {
			var bValid = true;
			var idField = config.field.attr("id");
			var typeField = config.field.attr("type");
			var message = vulpe.config.messages.error.validate.float;
			if (typeField == 'text' || typeField == 'textarea') {
				if (config.field.val().length > 0) {
					var value = vulpe.util.replaceAll(config.field.val(), '.', '');
					value = vulpe.util.replaceAll(value, ',', '.');
					if (!vulpe.validate.isAllDigits(value)) {
						bValid = false;
					} else {
						var iValue = parseFloat(value);
						if (isNaN(iValue)) {
							bValid = false;
						}
					}
				}
			}
			if (!config.setupErrorOff && !bValid) {
				vulpe.exception.setupError(idField, message);
			}
			return bValid;
		},

		validateDouble: function(config) {
			return vulpe.validate.validateFloat(config);
		},

		validateDate: function(config) {
			var bValid = true;
			var idField = config.field.attr("id");
			var typeField = config.field.attr("type");
			var message = vulpe.config.messages.error.validate.date;
			if (typeField == 'text' || typeField == 'textarea') {
				if (config.field.val().length > 0) {
					var MONTH = "MM";
					var DAY = "dd";
					var YEAR = "yyyy";
					var datePattern = config.datePatternStrict;
					var orderMonth = datePattern.indexOf(MONTH);
					var orderDay = datePattern.indexOf(DAY);
					var orderYear = datePattern.indexOf(YEAR);
					var dateRegexp = null;
					var value = config.field.val();
					if ((orderDay < orderYear && orderDay > orderMonth)) {
						var iDelim1 = orderMonth + MONTH.length;
						var iDelim2 = orderDay + DAY.length;
						var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
						var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
						if (iDelim1 == orderDay && iDelim2 == orderYear) {
							dateRegexp = new RegExp("^(\\d{2})(\\d{2})(\\d{4})$");
						} else if (iDelim1 == orderDay) {
							dateRegexp = new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$");
						} else if (iDelim2 == orderYear) {
							dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$");
						} else {
							dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$");
						}
						var matched = dateRegexp.exec(value);
						if (matched != null && !vulpe.validate.isValidDate(matched[2], matched[1], matched[3])) {
							bValid = false;
						} else {
							bValid = false;
						}
					} else if ((orderMonth < orderYear && orderMonth > orderDay)) {
						var iDelim1 = orderDay + DAY.length;
						var iDelim2 = orderMonth + MONTH.length;
						var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
						var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
						if (iDelim1 == orderMonth && iDelim2 == orderYear) {
							dateRegexp = new RegExp("^(\\d{2})(\\d{2})(\\d{4})$");
						} else if (iDelim1 == orderMonth) {
							dateRegexp = new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$");
						} else if (iDelim2 == orderYear) {
							dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$");
						} else {
							dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$");
						}
						var matched = dateRegexp.exec(value);
						if (matched != null) {
							if (!vulpe.validate.isValidDate(matched[1], matched[2], matched[3])) {
								bValid = false;
							}
						} else {
							bValid = false;
						}
					} else if ((orderMonth > orderYear && orderMonth < orderDay)) {
						var iDelim1 = orderYear + YEAR.length;
						var iDelim2 = orderMonth + MONTH.length;
						var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
						var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
						if (iDelim1 == orderMonth && iDelim2 == orderDay) {
							dateRegexp = new RegExp("^(\\d{4})(\\d{2})(\\d{2})$");
						} else if (iDelim1 == orderMonth) {
							dateRegexp = new RegExp("^(\\d{4})(\\d{2})[" + delim2 + "](\\d{2})$");
						} else if (iDelim2 == orderDay) {
							dateRegexp = new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})(\\d{2})$");
						} else {
							dateRegexp = new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{2})$");
						}
						var matched = dateRegexp.exec(value);
						if(matched != null) {
							if (!vulpe.validate.isValidDate(matched[3], matched[2], matched[1])) {
								bValid = false;
							}
						} else {
							bValid = false;
						}
					} else {
						bValid = false;
					}
				}
			}
			if (!config.setupErrorOff && !bValid) {
				vulpe.exception.setupError(idField, message);
			}
			return bValid;
		},

		validateIntRange: function(config) {
			var isValid = true;
			var idField = config.field.attr("id");
			var typeField = config.field.attr("type");
			var message = vulpe.config.messages.error.validate.integerRange;
			if (typeField == 'text' || typeField == 'textarea') {
				var value = parseInt(config.field.val());
				if (!(value >= parseInt(config.min) && value <= parseInt(config.max))) {
					isValid = false;
					vulpe.exception.setupError(idField, message);
				}
			}
			return isValid;
		},

		validateFloatRange: function(config) {
			var isValid = true;
			var idField = config.field.attr("id");
			var typeField = config.field.attr("type");
			var message = vulpe.config.messages.error.validate.floatRange;
			if (typeField == 'text' || typeField == 'textarea') {
				var value = parseFloat(config.field.val());
				if (!(value >= parseFloat(config.min) && value <= parseFloat(config.max))) {
					isValid = false;
					vulpe.exception.setupError(idField, message);
				}
			}
			return isValid;
		},

		validateEmail: function(config) {
			var isValid = true;
			var idField = config.field.attr("id");
			var typeField = config.field.attr("type");
			var message = vulpe.config.messages.error.validate.email;
			if ((typeField == 'text' || typeField == 'textarea') && !vulpe.validate.checkEmail(config.field.val())) {
				isValid = false;
				vulpe.exception.setupError(idField, message);
			}
			return isValid;
		},

		validateAttributes: function(formName) {
			var valid = true;
			var parent = "#" + formName;
			if (vulpe.util.existsVulpePopups()) {
				parent = "#" + vulpe.util.getLastVulpePopup();
			}
			var fields = jQuery("[class*='vulpeRequired']", parent);
			var invalidCount = 0;
			if (fields && fields.length > 0) {
				fields.each(function(index) {
					var field = $(this);
					var idField = field.attr("id");
					if (!vulpe.validate.validateRequired({field: field})) {
						if (invalidCount == 0 && !vulpe.util.existsVulpePopups()) {
							if (idField.indexOf(vulpe.config.entity) != -1 && idField.indexOf(vulpe.config.token.fieldIndex) != -1) {
								var detail = vulpe.config.prefix.detail + vulpe.util.getDetailByElementId(idField);
								$("a[href='" + detail + "']").trigger('click');
							} else if ($("a[href='" + vulpe.config.masterTabId + "']").length == 1) {
								$("a[href='" + vulpe.config.masterTabId + "']").trigger('click');
							}
						}
						valid = false;
						invalidCount++;
					}
					if (valid) {
						if (!vulpe.validate.validateAttribute(field)) {
							valid = false;
						}
					}
				});
			} else if (vulpe.config.requireOneFilter) {
				var empty = true;
				jQuery('input[id*=entitySelect],select[id*=entitySelect],textarea[id*=entitySelect]', parent).each(function(index) {
					var field = $(this);
					var idField = field.attr("id");
					var typeField = field.attr("type");
					if (empty && typeField != "hidden") {
						if (typeField == "checkbox") {
							if (eval(field.attr("checked"))) {
								empty = false;
							}
						} else {
							var idParent = idField.substring(0, idField.lastIndexOf(vulpe.config.token.dot));
							var idSelectPopup = idParent + vulpe.config.suffix.selectPopup;
							var selectPopup = vulpe.util.get(idSelectPopup);
							if (selectPopup != null && selectPopup.length == 1) {
								value = vulpe.util.get(idParent + "_id").val();
								if (vulpe.util.trim(value).length == 0) {
									vulpe.util.get(idField).val("");
								}
							} else {
								value = field.val();
							}
							if (vulpe.util.trim(value).length > 0) {
								empty = false;
							}
						}
					}
				});
				if (empty) {
					vulpe.util.focusFirst("#" + formName);
					vulpe.exception.showMessageError(vulpe.config.messages.error.validate.requireOneFilter);
					return false;
				}
			}
			var invalidFields = 0;
			jQuery(":text,textarea", parent).each(function(index){
				if (!vulpe.validate.validateAttribute($(this))) {
					valid = false;
					invalidFields++;
				}
			});
			if (!vulpe.config.valid && !vulpe.util.existsVulpePopups()) {
				valid = vulpe.config.valid;
			}
			if (!valid) {
				vulpe.exception.focusFirstError(formName);
				var manyFields = (invalidFields > 1 || fields.length > 1);
				vulpe.exception.showMessageError((manyFields ? vulpe.config.messages.error.checkfields : vulpe.config.messages.error.checkfield));
			}
			return valid;
		},

		validateAttribute: function(field) {
			var valid = true;
			var idField = field.attr("id");
			var config = vulpe.util.getElementConfig(idField);
			if (config) {
				if (config.type == "DATE") {
					valid = vulpe.validate.validateDate({
						field: field,
						datePatternStrict: config.datePattern
					});
				} else if (config.type == "INTEGER") {
					valid = vulpe.validate.validateInteger({
						field: field
					});
				} else if (config.type == "LONG") {
					valid = vulpe.validate.validateLong({
						field: field
					});
				} else if (config.type == "DOUBLE") {
					valid = vulpe.validate.validateDouble({
						field: field
					});
				} else if (config.type == "FLOAT") {
					valid = vulpe.validate.validateFloat({
						field: field
					});
				} else if (config.type == "EMAIL") {
					valid = vulpe.validate.validateEmail({
						field: field
					});
				} else if (config.type == "STRING") {
					var value = field.val();
					if (value.length > 1) {
						if (vulpe.util.checkRepeatedCharacters(value)) {
							var message = vulpe.config.messages.error.validate.repeatedCharacters;
							vulpe.exception.setupError(field.attr("id"), message);
							return false;
						}
					}
					if (valid && config.minlength) {
						valid = vulpe.validate.validateMinLength({
							field: field,
							minlength: config.minlength
						});
					}
					if (valid && config.maxlength) {
						valid = vulpe.validate.validateMaxLength({
							field: field,
							maxlength: config.maxlength
						});
					}
					if (valid && config.mask) {
						valid = vulpe.validate.validateMask({
							field: field,
							mask: config.mask
						});
					}
				}
			}
			return valid;
		},

		validateLoginForm: function() {
			var j_username = vulpe.util.getElement("j_username");
			var j_password = vulpe.util.getElement("j_password");
			if (j_username.value != '' && j_password.value != '') {
				return true;
			} else {
				if (j_username.value == '') {
					j_username.focus();
				} else if (j_password.value == '') {
					j_password.focus();
				}
			}
			return false;
		}
	},
	// vulpe.view
	view: {
		
		selectShowContent: function(id) {
			var selectId = id + "_" + vulpe.util.get(id).val();
			var selectShowContentId = id + "_showContent";
			$("div[id*=" + id + "_]").hide();
			vulpe.util.get(selectShowContentId).hide();
			vulpe.util.get(selectId).css('width', vulpe.util.get(id).css('width'));
			vulpe.util.get(selectId).slideDown('slow');
		},
		
		selectHideContent: function(id) {
			var selectId = id + "_" + vulpe.util.get(id).val();
			var selectShowContentId = id + "_showContent";
			vulpe.util.get(selectId).slideUp('slow');
			vulpe.util.get(selectShowContentId).show();
		},
		
		showContent: function(id) {
			var contentId = id + "_content";
			var showContentId = id + "_showContent";
			var valueId = id + "_value";
			vulpe.util.get(showContentId).hide();
			vulpe.util.get(valueId).hide();
			vulpe.util.get(contentId).slideDown('slow');
			vulpe.view.isSelection = true;
		},
		
		hideContent: function(id) {
			var contentId = id + "_content";
			var showContentId = id + "_showContent";
			var valueId = id + "_value";
			vulpe.util.get(contentId).slideUp('slow');
			vulpe.util.get(showContentId).show();
			vulpe.util.get(valueId).show();
		},
		
		setRequired: function(name, enabled) {
			var field = vulpe.util.getElementField(name);
			if (enabled) {
				field.addClass("vulpeRequired");
				vulpe.view.addRequiredField(field);
			} else {
				field.removeClass("vulpeRequired");
				vulpe.view.addRequiredField(field);
				var idField = field.attr("id");
				var idRequiredField = idField + "FieldRequired";
				vulpe.util.get(idRequiredField).hide();
			}
		},

		controlDetailChecked: function(checked, prepareId) {
			vulpe.util.get(prepareId + "selected").val(checked ? "false" : "true");
		},

		addRequiredField: function(field) {
			var idField = field.attr("id");
			var idRequiredField = idField + "FieldRequired";
			if (vulpe.util.get(idRequiredField).length == 0) {
				vulpe.util.get(idField + vulpe.config.suffix.iconErrorMessage).after("<span id='" + idRequiredField + "' class='vulpeFieldRequired'>*</span>");
			}
			vulpe.util.get(idRequiredField).show();
		},

		checkRequiredFields: function(parent) {
			jQuery("[class*='vulpeRequired']", parent).each(function(index) {
				vulpe.view.addRequiredField($(this));
			});
		},

		checkRows: function(parent) {
			$("tr[id*='-row-']", parent).each(function(index) {
				var id = $(this).attr("id");
				if (id.indexOf("header") == -1) {
					$("#" + id).unbind("mouseenter mouseleave");
					$("#" + id).bind("mouseenter mouseleave", function(event){
						$(this).find('td').toggleClass("vulpeSelectedRow");
					});
					var onclick = $(this).attr("onclick");
					if (typeof onclick == "function" || vulpe.util.isNotEmpty(onclick)) {
						var key = "";
						for (var i = 0; i < vulpe.config.hotKeys.length; i++) {
							var hotkey = vulpe.config.hotKeys[i];
							if (hotkey[0] == "[items]") {
								key = hotkey[1];
								break;
							}
						}
						vulpe.util.addHotKey({
							hotKey: key.replace("[numbers]", "") + (index == 10 ? 0 : index),
							command: function () {
								vulpe.util.get(id).trigger('click');
								return false;
							}
						});
					} else {
						$(this).find('td').css("cursor", "default");
					}
				}
			});
		},

		validateSelectedToDelete: function(command) {
			var selected = false;
			jQuery(":checkbox[name$='selected']").each(function(index) {
				if ($(this).attr("checked") && !selected) {
					selected = true;
				}
			});
			vulpe.command = command;
			if (selected) {
				$(vulpe.config.layers.confirmationMessage).html(vulpe.config.messages.deleteSelected);
				$(vulpe.config.layers.confirmationDialog).dialog('open');
			} else {
				vulpe.command();
			}
		},

		confirm: function(type, command) {
			vulpe.command = command;
			var message = "";
			if (type == "clear") {
				message = vulpe.config.messages.clear;
			} else if (type == "delete") {
				message = vulpe.config.messages.deleteThis;
			} else if (type == "deleteFile") {
				message = vulpe.config.messages.deleteFile;
			} else if (type == "deleteSelected") {
				message = vulpe.config.messages.deleteSelected;
			} else if (type == "updatePost") {
				if (!vulpe.validate.validateAttributes(vulpe.config.formName)) {
					return false;
				}
				message = vulpe.config.messages.updatePost;
			}
			$(vulpe.config.layers.confirmationMessage).html(message);
			$(vulpe.config.layers.confirmationDialog).dialog('open');
		},

		prepareRead: function(formName) {
			vulpe.util.setPagingPage('', formName);
			return true;
		},

		clearForm: function(formName) {
			vulpe.util.get(formName).clearForm();
		},

		clearFieldsInLayer: function(layer) {
			jQuery(':input', vulpe.util.get(layer)).clearFields();
		},

		isSelection: false,

		popups: new Array(),

		selectPopupCache: new Array(),

		resetFields: function(formName) {
			vulpe.util.get(formName).resetForm();
			return true;
		},

		showHideElement: function(elementId, fade) {
			var element = jQuery("#" + elementId);
			if (element.css("display") == 'none') {
				if (fade) {
					element.fadeIn();
				} else {
					element.slideDown("slow");
				}
			} else {
				if (fade) {
					element.fadeOut();
				} else {
					element.slideUp("slow");
				}
			}
		},

		sortTable: function(formName, sortPropertyInfo, property) {
			var value = vulpe.util.get(sortPropertyInfo).val();
			if (value == "obj.id") {
				value = "";
			}
			var order = "";
			if (value.indexOf(property + " desc") != -1) {
				order = "";
				var position = value.indexOf(property + " desc")
				if (position == 0) {
					value = value.replace(property + " desc,", "");
				} else {
					value = value.replace("," + property + " desc", "");
				}
				value = value.replace(property + " desc", "");
			} else if (value.indexOf(property) != -1) {
				order = "vulpeOrderDesc";
				value = value.replace(property, property + " desc");
			} else {
				order = "vulpeOrderAsc";
				if (vulpe.config.sortType == "LAST_COLUMN") {
					value = property;
				} else {
					value = (value == "") ? property : value + "," + property;
				}
			}
			vulpe.util.get(sortPropertyInfo).val(value);
			var propertyId = sortPropertyInfo + '-' + property.replace(".", "_");
			vulpe.util.get(propertyId).addClass(order);
			if (vulpe.config.sortType == "LAST_COLUMN") {
				vulpe.config.order = new Array();
			}
			vulpe.config.order[propertyId] = {property: sortPropertyInfo, value: value, css: order};
			var buttonRead = vulpe.util.getButton("Read");
			if (buttonRead) {
				buttonRead.trigger('click');
			}
			if (value == "") {
				value = "obj.id";
			}
		},

		setupSortTable: function(sortPropertyInfoName) {
			jQuery("th[id!='']", "#entities").each(function(index) {
				var column = $(this);
				var order = vulpe.config.order[column.attr("id")];
				if (order) {
					column.addClass(order.css);
				}
			});
		},

		setSelectCheckbox: function(select) {
			vulpe.view.isSelection = select;
		},

		markOnlyOne: function(controller, name, parent) {
			if (!parent) {
				parent = "#body";
			}
			jQuery(":checkbox[name$='" + name + "']", parent).each(function(index) {
				$(this).attr("checked", false);
			});
			controller.checked = true;
		},

		controlMarkUnmarkAll: function(select, name, parent) {
			var selected = false;
			if (select.checked) {
				if (parent.indexOf("#") != 0) {
					parent = "#" + parent;
				}
				var items = jQuery(":checkbox[name$='"+ name +"']", parent).length;
				var count = 0;
				jQuery(":checkbox[name$='"+ name +"']", parent).each(function(index) {
					if (this.checked) {
						++count;
					}
				});
				if (count == items) {
					selected = true;
				}
			}
			vulpe.util.get("selectAll", parent).attr("checked", selected);
		},

		enableMarkUnmarkAll: function(name, parent) {
			disabled = false;
			var items = jQuery(":checkbox[name$='"+ name +"']", parent).length;
			var count = 0;
			jQuery(":checkbox[name$='"+ name +"']", parent).each(function(index) {
				if (eval($(this).attr("disabled"))) {
					++count;
				}
			});
			if (count == items) {
				disabled = true;
			}
			if (disabled) {
				vulpe.util.get("selectAll", parent).attr("disabled", disabled);
			} else {
				vulpe.util.get("selectAll", parent).removeAttr("disabled");
			}
		},

		markUnmarkAll: function(controller, name, parent) {
			if (!parent) {
				parent = "#body";
			}
			jQuery(":checkbox[name$='" + name + "']", parent).each(function(index) {
				if (!$(this).attr("disabled")) {
					$(this).attr("checked", controller.checked);
				}
			});
		},

		onmouseoverRow: function(row) {
			jQuery(row).addClass('vulpeSelectedRow');
		},

		onmouseoutRow: function(row) {
			jQuery(row).removeClass('vulpeSelectedRow');
		},

		/**
		 * values:
		 * fieldNameA=valueA,fieldNameB=valueB,fieldNameC=valueC
		 *
		 * popupProperties:
		 * returnFieldNameA=fieldNameA,returnFieldNameB=fieldNameB,returnFieldNameC=fieldNameC
		 */
		selectRow: function(row, values) {
			vulpe.view.selectPopupCache = new Array();
			vulpe.config.valid = true;
			vulpe.config.popup.selectRow = true;
			var popupName = jQuery(row).parents('div.vulpePopup').attr('id');
			var popup = vulpe.util.get(popupName);
			var popupProperties = popup.attr('properties');
			vulpe.util.loopMap(popupProperties, function(c, v) {
				var value = vulpe.util.getValueMap(values, v);
				value = (typeof value == "undefined") ? '' : webtoolkit.url.decode(value);
				vulpe.util.get(webtoolkit.url.decode(c)).val(value.replace(/\+/g, " "));
				vulpe.util.get(webtoolkit.url.decode(c)).blur();
			});

			var popupExpressions = popup.attr('expressions');
			var layerParent = webtoolkit.url.decode(popup.attr('layerParent'));
			vulpe.util.loopMap(popupExpressions, function(c, v) {
				var value = vulpe.util.getValueMap(values, v);
				value = (typeof value == "undefined") ? '' : webtoolkit.url.decode(value).replace(/\+/g, " ");
				if (vulpe.util.isEmpty(layerParent)) {
					jQuery(webtoolkit.url.decode(c)).val(value);
				} else {
					jQuery(webtoolkit.url.decode(c), vulpe.util.get(layerParent)).val(value);
				}
			});
			vulpe.view.request.invokeSelectRowCallback(popupName);
			vulpe.view.hidePopup(popupName);
		},

		loading: null,

		showLoading: function() {
			var visible = vulpe.util.get('loading').css("display") != "none";
			if (vulpe.config.layout.showLoading && !visible) {
				vulpe.view.loading = jQuery('#loading').modal({
					overlayId: 'overlayLoading',
					containerId: 'containerLoading',
					close: false,
					containerCss: {top: '40%'}
				});
			}
		},

		hideLoading: function() {
			if (vulpe.view.loading) {
				vulpe.view.loading.close();
			}
		},

		showMessages: function() {
			$(vulpe.config.layers.modalMessages).attr("title", function() {
				var popupTitle = jQuery("#messageTitle", "#modalMessages");
				if (popupTitle) {
					popupTitle.hide();
					return popupTitle.html();
				}
				return "";
			});
			var popup = $(vulpe.config.layers.modalMessages).dialog({
					autoOpen: true,
					width: 480,
					resizable: true,
					modal: true
			});
			$(vulpe.config.layers.modalMessages).css("padding", "0px");
			return popup;
		},

		hideMessages: function() {
		},

		/**
		 *
		 * @param options {id, width}
		 */
		showPopup: function(options) {
			$('#' + options.id).attr("title", function() {
				var popupTitle = jQuery("#contentTitle", "#" + options.id);
				if (popupTitle) {
					popupTitle.hide();
					return popupTitle.html();
				}
				return "";
			});
			var popup = $("#" + options.id).dialog({
					autoOpen: true,
					width: options.width,
					modal: true,
					open: function(event, ui) {
						vulpe.util.removeHotKeys();
						vulpe.util.checkHotKeys($("#" + options.id));
					},
					close: function(event, ui) {
						vulpe.util.removeHotKeys($("#" + options.id));
						$(this).remove();
						vulpe.util.checkHotKeys();
						vulpe.util.removeArray(vulpe.view.popups, options.id);
					}
			});
			$('#' + options.id).css("padding", "0px");
			vulpe.util.focusFirst("#" + options.id);
			return popup;
		},

		hidePopup: function(elementId) {
			$("#" + elementId).dialog("close");
			vulpe.util.removeArray(vulpe.view.popups, elementId);
		},

		request: { // vulpe.view.request
			openPopup: function(url, width, height, popupName) {
				var left = (screen.width - width) / 2;
				var top = (screen.height - height) / 2;
				return window.open(url, popupName, 'height=' + height + ', width=' + width + ', top=' + top + ', left=' + left + ', resizable=yes');
			},

			submitReport: function(actionURL, width, height) {
				if (vulpe.config.layout.showReportInNewWindow) {
					var popupName = 'popup' + new Date().getTime();
					return vulpe.view.request.openPopup(actionURL, width, height, popupName);
				} else {
					$("#reportFrame").attr("src", actionURL);
					$("#report").dialog({
						autoOpen: true,
						width: width,
						height: height,
						resizable: true,
						modal: true
					});
				}
			},

			selectRowCallback: new Array(),
			globalsBeforeJs: new Array(),
			globalsAfterJs: new Array(),

			registerFunctions: function(array, callback, key, layerFields) {
				if (typeof key == "undefined") {
					throw vulpe.config.messages.keyRequired;
				}
				if (typeof layerFields == "undefined") {
					layerFields = '';
				}
				var newFunction = {key: key, callback: callback, layerFields: layerFields, getKey: function() {return this.key + this.layerFields;}};
				var exists = false;
				jQuery(array).each(function(i) {
					exists = this.getKey() == newFunction.getKey();
					if (exists) {
						array[i] = newFunction;
						return false;
					}
				});
				if (!exists) {
					array[array.length] = newFunction;
				}
			},

			registerSelectRowCallback: function(callback, key, layerFields) {
				vulpe.view.request.registerFunctions(vulpe.view.request.selectRowCallback, callback, key, layerFields);
			},

			registerGlobalsBeforeJs: function(callback, key, layerFields) {
				vulpe.view.request.registerFunctions(vulpe.view.request.globalsBeforeJs, callback, key, layerFields);
			},

			registerGlobalsAfterJs: function(callback, key, layerFields) {
				vulpe.view.request.registerFunctions(vulpe.view.request.globalsAfterJs, callback, key, layerFields);
			},

			removeFunctions: function(array, key, layerFields) {
				if (typeof key == "undefined") {
					throw vulpe.config.messages.keyRequired;
				}
				if (typeof layerFields == "undefined") {
					layerFields = '';
				}
				var key = key + layerFields;
				var indexs = new Array();
				jQuery(array).each(function(i) {
					if (this.getKey() == key) {
						indexs[indexs.length] = i;
						return false;
					}
				});
				jQuery(indexs).each(function(i) {
					vulpe.util.removeArray(array, this);
				});
			},

			removeSelectRowCallback: function(key, layerFields) {
				vulpe.view.request.removeFunctions(vulpe.view.request.selectRowCallback, key, layerFields);
			},

			removeGlobalsBeforeJs: function(key, layerFields) {
				vulpe.view.request.removeFunctions(vulpe.view.request.globalsBeforeJs, key, layerFields);
			},

			removeGlobalsAfterJs: function(key, layerFields) {
				vulpe.view.request.removeFunctions(vulpe.view.request.globalsAfterJs, key, layerFields);
			},

			invokeFunctions: function(array, layerFields) {
				if (typeof layerFields == "undefined") {
					layerFields = '';
				}
				jQuery(array).each(function(i) {
					if (this.layerFields == layerFields) {
						this.callback();
					}
				});
			},

			invokeSelectRowCallback: function(layerFields) {
				vulpe.view.request.invokeFunctions(vulpe.view.request.selectRowCallback, layerFields);
			},

			invokeGlobalsBeforeJs: function(layerFields) {
				vulpe.view.request.invokeFunctions(vulpe.view.request.globalsBeforeJs, layerFields);
			},

			invokeGlobalsAfterJs: function(layerFields) {
				vulpe.view.request.invokeFunctions(vulpe.view.request.globalsAfterJs, layerFields);
			},

			/**
			 *
			 * @param options {page, url, formName, layerFields, layer, beforeJs, afterJs}
			 */
			submitPaging: function(options) {
				options.validate = false;
				vulpe.view.request.submitAjaxAction(options);
			},

			/**
			 *
			 * @param options {id, url, formName, layerFields, layer, beforeJs, afterJs}
			 */
			submitView: function(options) {
				if (vulpe.view.isSelection) {
					return false;
				}
				options.validate = false;
				vulpe.view.request.submitAjaxAction(options);
			},

			/**
			 *
			 * @param options {id, url, formName, layerFields, layer, beforeJs, afterJs, verify}
			 */
			submitUpdate: function(options) {
				if (options.verify && vulpe.view.isSelection) {
					return false;
				}
				options.validate = false;
				vulpe.view.request.submitAjaxAction(options);
			},

			/**
			 *
			 * @param options {id, url, formName, layerFields, layer, beforeJs, afterJs}
			 */
			submitDelete: function(options) {
				options.validate = false;
				vulpe.view.request.submitAjaxAction(options);
			},

			/**
			 *
			 * @param options {detail, detailIndex, url, formName, layerFields, layer, beforeJs, afterJs}
			 */
			submitDeleteDetail: function(options) {
				options.queryString = "detail=" + (options.detail == "entities" || options.detail.indexOf("entity.") != -1 ? options.detail : "entity." + options.detail) + "&detailIndex=" + options.detailIndex;
				options.validate = false;
				vulpe.view.request.submitAjaxAction(options);
			},

			/**
			 *
			 * @param options {detail, url, formName, layerFields, layer, beforeJs, afterJs}
			 */
			submitDeleteDetailSelected: function(options) {
				var selections = jQuery("*[name$='selected']");
				var selectedIds = new Array();
				var count = 0;
				selections.each(function(index) {
					var checked = $(this).attr("checked")
					if (checked) { count++;	}
					selectedIds[index] = checked ? $(this).val() : "";
				});
				if (count > 0) {
					$(vulpe.config.layers.confirmationMessage).html(vulpe.config.messages.deleteSelected);
					vulpe.command = function() {
						$(this).dialog('close');
						for (var i = 0; i < selectedIds.length; i++) {
							if (selectedIds != "") {
								selections[i].checked;
								selections[i].value = selectedIds[i];
							}
						}
						options.queryString = "detail=" + (options.detail == "entities" || options.detail.indexOf("entity.") != -1 ? options.detail : "entity." + options.detail);
						options.validate = false;
						vulpe.view.request.submitAjaxAction(options);
					}
					$(vulpe.config.layers.confirmationDialog).dialog('open');
				} else {
					$(vulpe.config.layers.vulpeAlertMessage).html(vulpe.config.messages.selectRecordsToDelete);
					$(vulpe.config.layers.alertDialog).dialog('open');
				}
			},

			/**
			 *
			 * @param options {url, formName, layerFields, layer, beforeJs, afterJs}
			 */
			submitDeleteSelected: function(options) {
				var selections = jQuery("*[name$='selected']");
				var selectedIds = new Array();
				var count = 0;
				selections.each(function(index) {
					var checked = $(this).attr("checked")
					if (checked) { count++;	}
					selectedIds[index] = checked ? $(this).val() : "";
				});
				if (count > 0) {
					$(vulpe.config.layers.confirmationMessage).html(vulpe.config.messages.deleteSelected);
					vulpe.command = function() {
						$(this).dialog('close');
						for (var i = 0; i < selectedIds.length; i++) {
							if (selectedIds[i] != "") {
								selections[i].checked;
								selections[i].value = selectedIds[i];
							}
						}
						options.validate = false;
						vulpe.view.request.submitAjaxAction(options);
					}
					$(vulpe.config.layers.confirmationDialog).dialog('open');
				} else {
					$(vulpe.config.layers.vulpeAlertMessage).html(vulpe.config.messages.selectRecordsToDelete);
					$(vulpe.config.layers.alertDialog).dialog('open');
				}
			},

			/**
			 *
			 * @param options {url, formName, layerFields, queryString, layer, validate, beforeJs, afterJs}
			 */
			submitAjaxAction: function(options) {
				if (!options.formName && options.layerFields) {
					options.formName = options.layerFields;
				}
				if (options.validate && !vulpe.validate.validateAttributes(options.formName)) {
					return false;
				}
				if (options.url.indexOf(vulpe.config.contextPath) == -1) {
					options.url = vulpe.config.contextPath + '/' + options.url;
				}
				vulpe.util.getForm(options).attr("action", options.url);
				options.isFile = false;
				vulpe.view.request.submitAjax(options);
			},

			/**
			 *
			 * @param options {formName, layerFields, queryString, layer, validate, beforeJs, afterJs}
			 */
			submitLoginForm: function(options) {
				if (vulpe.validate.validateLoginForm()) {
					if (vulpe.util.existsVulpePopups()) {
						options.layer = vulpe.util.getLastVulpePopup();
					}
					vulpe.util.getForm(options).attr("action", vulpe.config.contextPath + '/' + vulpe.config.springSecurityCheck);
					options.isFile = false;
					vulpe.view.request.submitAjax(options);
				}
			},

			submitBefore: function(beforeJs) {
				if (vulpe.util.isNotEmpty(beforeJs)) {
					try {
						var isValid = eval(webtoolkit.url.decode(beforeJs));
						if (!isValid) {
							return false;
						}
					} catch(e) {
						// do nothing
					}
					try {
						vulpe.view.request.invokeGlobalsBeforeJs();
					} catch(e) {
						return false;
					}
				}
				return true;
			},

			submitLink: function(url, beforeJs, afterJs) {
				jQuery(vulpe.config.layers.messages).hide();
				vulpe.config.order = new Array();
				vulpe.config.redirectToIndex = false;
				return vulpe.view.request.submitPage({
					url: vulpe.config.contextPath + url,
					layer: 'body',
					beforeJs: beforeJs,
					afterJs: afterJs
				});
			},

			/**
			 *
			 * @param options {url, queryString, name, layerParent, paramLayerParent, properties, expressions, paramProperties, paramExpressions, requiredParamProperties, requiredParamExpressions, styleClass, beforeJs, afterJs, width}
			 */
			submitPopup: function(options) {
				vulpe.util.setVulpePopup(options.name);
				vulpe.util.get(options.name).remove();
				var popup = jQuery('<div>');
				popup.attr('id', options.name).attr('layerParent', options.layerParent).attr('properties', options.properties).attr('expressions', options.expressions)
				popup.hide();
				popup.css("height", "100%");
				popup.addClass('vulpePopup');
				if (vulpe.util.isNotEmpty(options.styleClass)) {
					popup.addClass(options.styleClass);
				}
				popup.appendTo('body');
				if (vulpe.util.isNotEmpty(options.afterJs)) {
					options.afterJs = escape("vulpe.view.request.onShowPopup({name: '") + escape(options.name) + escape("', afterJs: '") + options.afterJs + escape("', width: '") + options.width + escape("'})");
				} else {
					options.afterJs = escape("vulpe.view.request.onShowPopup({name: '") + escape(options.name) + escape("', width: '") + options.width + escape("'})");
				}
				try {
					options.queryString = vulpe.view.request.complementQueryString(options);
				} catch(e) {
					popup.remove();
					return false;
				}
				if (!vulpe.view.request.submitPage({
						url: vulpe.config.contextPath + options.url,
						queryString: options.queryString,
						layer: options.name,
						beforeJs: options.beforeJs,
						afterJs: options.afterJs,
						hideLoading: true
				})) {
					popup.remove();
					return false;
				} else {
					return true;
				}
			},
			/**
			 * popupInputField=pageInputFieldWithValue
			 * @param options {queryString, layerParent, paramProperties, paramExpressions, requiredParamProperties, requiredParamExpressions}
			 */
			complementQueryString: function(options) {
				var queryParam = '';
				vulpe.util.loopMap(options.requiredParamProperties, function(c, v) {
					var element = vulpe.util.get(webtoolkit.url.decode(v));
					var value = element.val();
					if (vulpe.util.isEmpty(value)) {
						var name = (element.attr('title') ? element.attr('title') : 'Value');
						element.focus();
						throw name + vulpe.config.messages.fieldRequired;
					}
					queryParam = queryParam + '&' + c + '=' + escape(value);
				});
				vulpe.util.loopMap(options.requiredParamExpressions, function(c, v) {
					var element = null;
					if (vulpe.util.isEmpty(options.layerParent)) {
						element = jQuery(webtoolkit.url.decode(v));
					} else {
						element = jQuery(webtoolkit.url.decode(v), vulpe.util.get(options.layerParent));
					}
					var value = element.val();
					if (vulpe.util.isEmpty(value)) {
						var name = (element.attr('title') ? element.attr('title') : 'Value');
						element.focus();
						throw name + vulpe.config.messages.fieldRequired;
					}
					queryParam = queryParam + '&' + c + '=' + escape(value);
				});
				vulpe.util.loopMap(options.paramProperties, function(c, v) {
					var value = vulpe.util.get(webtoolkit.url.decode(v)).val();
					queryParam = queryParam + '&' + c + '=' + escape(value);
				});
				vulpe.util.loopMap(options.paramExpressions, function(c, v) {
					var value = null;
					if (vulpe.util.isEmpty(options.layerParent)) {
						value = jQuery(webtoolkit.url.decode(v)).val();
					} else {
						value = jQuery(webtoolkit.url.decode(v), vulpe.util.get(options.layerParent)).val();
					}
					queryParam = queryParam + '&' + c + '=' + escape(value);
				});
				if (vulpe.util.isNotEmpty(queryParam)) {
					if (vulpe.util.isEmpty(options.queryString)) {
						return queryParam.substring(1);
					} else {
						return options.queryString + queryParam;
					}
				} else {
					return options.queryString;
				}
			},

			/**
			 *
			 * @param options {name, width, afterJs}
			 */
			onShowPopup: function(options) {
				vulpe.view.showPopup({id: options.name, width: options.width});
				if (vulpe.util.isNotEmpty(options.afterJs)) {
					try {
						eval(webtoolkit.url.decode(options.afterJs));
					} catch(e) {
						// do nothing
					}
				}
			},

			/**
			 *
			 * @param options {url, queryString, layer, beforeJs, afterJs, afterCallback}
			 */
			submitPage: function(options) {
				if (!vulpe.config.autocomplete) {
					vulpe.view.selectPopupCache = new Array();
				}
				jQuery(vulpe.config.layers.messages).hide();
				var messagesPopup = jQuery(vulpe.config.layers.messagesPopup);
				if (messagesPopup.length == 1) {
					messagesPopup.hide();
				}
				if (!vulpe.view.request.submitBefore(options.beforeJs)) {
					return false;
				}
				options.queryString = (vulpe.util.isNotEmpty(options.queryString) ? options.queryString + '&' : '') + 'ajax=true';
				vulpe.view.showLoading();
				jQuery.ajax({
					type: "POST",
					dataType: 'html',
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					url: vulpe.util.completeURL(options.url),
					data: options.queryString,
					success: function (data, status) {
						vulpe.config.layout.showLoading = true;
						var authenticator = options.url.indexOf("/j_spring_security_check") != -1;
						var loginForm = data.indexOf("vulpeLoginForm") != -1;
						var validUrlRedirect = vulpe.config.authenticator.url.redirect.indexOf("/ajax") == -1;
						if (data.indexOf('<!--IS_EXCEPTION-->') != -1) {
							vulpe.exception.handlerError(data, status);
						} else if (!authenticator && loginForm && vulpe.config.redirectToIndex && vulpe.config.authenticator.url.redirect == '') {
							$(window.location).attr("href", vulpe.config.contextPath);
						} else if (data.indexOf('/*[JS]*/') != -1) {
							data = data.replace("/*[JS]*/", "");
							eval(data);
						} else {
							try {
								vulpe.config.redirectToIndex = true;
								if (authenticator && !loginForm && validUrlRedirect) {
									vulpe.login.logged = true;
									vulpe.login.executeAfter();
								} else {
									if (vulpe.util.isEmpty(options.afterJs) && vulpe.util.isEmpty(options.hideLoading)) {
										options.hideLoading = true;
									}
									if (options.hideLoading) {
										vulpe.view.hideLoading();
									}
									if (loginForm) {
										var userAuthenticatedLayer = vulpe.util.get("userAuthenticated");
										if (userAuthenticatedLayer.length == 1) {
											userAuthenticatedLayer.hide();
										}
									}
									var html = "";
									if (vulpe.util.existsVulpePopups(options.layer)) {
										var messagePopup = "<div id=\"messagesPopup\" style=\"display: none;\" class=\"vulpeMessages\"></div>";
										html = messagePopup + data;
									} else {
										html = data;
									}
									html = vulpe.util.trim(html);
									var layerObject = vulpe.util.get(options.layer);
									var layerObjectType = layerObject.attr("type");
									if (layerObjectType && layerObjectType == "text") {
										html = html.replace("/*[PLAINTEXT]*/", "");
										layerObject.val(html);
									} else {
										layerObject.html(html);
										if (vulpe.util.existsVulpePopups(options.layer)) {
											var popupTitle = jQuery("#contentTitle", layerObject);
											if (popupTitle) {
												popupTitle.hide();
											}
										}
										vulpe.view.checkRequiredFields(layerObject);
										if ((vulpe.config.formName && vulpe.config.formName.indexOf("SelectForm") != -1) || (vulpe.util.existsVulpePopups(options.layer))) {
											vulpe.view.checkRows(layerObject)
											vulpe.view.enableMarkUnmarkAll("selected", layerObject);
										}
										vulpe.util.renewHotKeys(options);
										var selectedTab = vulpe.util.get(vulpe.config.formName + vulpe.config.suffix.selectedTab);
										if (selectedTab.val() != "") {
											$(vulpe.config.prefix.detailTab, layerObject).tabs("select", "#" + selectedTab.val());
										}
										if (html.indexOf("vulpeMainBodyTabs") == -1) {
											vulpe.util.focusFirst(options.layer);
										}
									}
									if (typeof options.afterJs == "function") {
										try {
											options.afterJs();
										} catch(e) {
											// do nothing
										}
									} else if (vulpe.util.isNotEmpty(options.afterJs)) {
										try {
											eval(webtoolkit.url.decode(options.afterJs));
											vulpe.view.request.invokeGlobalsAfterJs();
											if (typeof options.afterCallback == "function") {
												options.afterCallback();
											}
										} catch(e) {
											// do nothing
										}
									}
								}
							} catch(e) {
								vulpe.exception.handlerError(data, status, e);
							}
						}
					},
					error: function (data, status, e) {
						vulpe.view.hideLoading();
						vulpe.exception.handlerError(data, status, e);
					}
				});
				return true;
			},

			/**
			 *
			 * @param options {url, autocomplete, value, identifier, description, notFoundMessage}
			 */
			submitAutocompleteIdentifier: function(options) {
				if (vulpe.config.popup.selectRow) {
					vulpe.config.popup.selectRow = false;
					return;
				}
				var identifier = options.identifier;
				var description = options.description;
				vulpe.config.autocomplete = true;
				if (options.value && options.value != "") {
					if (vulpe.util.isEmpty(options.afterJs)) {
						options.hideLoading = true;
					}
					var afterJs = options.afterJs;
					options.afterJs = function() {
						vulpe.config.autocomplete = false;
						if (vulpe.util.get(description).val() == "") {
							vulpe.util.get(identifier).focus();
							vulpe.view.selectPopupCache[identifier][0] = vulpe.util.get(identifier).val();
							if (options.notFoundMessage) {
								var idField = vulpe.util.get(description).attr("id");
								var errorMessage = vulpe.util.get(idField + vulpe.config.suffix.errorMessage);
								errorMessage.html(options.notFoundMessage);
								errorMessage.css("display", "block");
								vulpe.exception.showFieldError({element: vulpe.util.get(description)});
							}
							vulpe.config.valid = false;
							vulpe.view.hideLoading();
							return;
						} else {
							vulpe.view.selectPopupCache[identifier][1] = vulpe.util.get(description).val();
							vulpe.exception.hideFieldError({element: vulpe.util.get(description)});
							vulpe.config.valid = true;
						}
						afterJs();
					};
					var data = vulpe.view.selectPopupCache[identifier];
					var id = data ? data[0] : "";
					if (typeof id == "undefined" || id != options.value || (vulpe.util.get(options.identifier).val() == "" && vulpe.util.get(options.description).val() == "")) {
						vulpe.view.selectPopupCache[options.identifier] = [options.value, ""];
					} else if (id == options.value) {
						vulpe.util.get(description).val(vulpe.view.selectPopupCache[identifier][1]);
						if (vulpe.util.isNotEmpty(vulpe.util.get(description).val())) {
							vulpe.config.valid = true;
						} else {
							options.afterJs();
						}
						return;
					}
					options.queryString = "entitySelect.autocomplete=" + options.autocomplete + "&entitySelect.id=" + options.value;
					options.layer = options.description;
					options.layerFields = options.description;
					vulpe.view.request.submitAjax(options);
				} else {
					vulpe.view.selectPopupCache[identifier] = ["", ""];
					vulpe.util.get(identifier).val("");
					vulpe.util.get(description).val("");
					vulpe.exception.hideFieldError({element: vulpe.util.get(description)});
					vulpe.config.valid = true;
				}
			},

			/**
			 *
			 * @param options {url, uri, formName, layer, layerFields, beforeJs, afterJs, individualLoading, validate, isFile}
			 */
			submitAjax: function(options) {
				// verifier if exists validations before submit
				if (!vulpe.view.request.submitBefore(options.beforeJs)) {
					return false;
				}
				options.beforeJs = ''; // set empty do not validate on submitPage
				for (var i = 0; i < vulpe.RTEs.length; i++) {
					var rteId = vulpe.RTEs[i];
					var rte = vulpe.util.get(rteId).contents();
					var rteContent = vulpe.util.get(rteId + "-content");
					rteContent.val(rte.find('body').html());
				}
				if (options.uri) {
					options.url = (options.uri.indexOf(vulpe.config.contextPath) == -1 ? vulpe.config.contextPath : '') + options.uri;
				}
				if (!options.layer) {
					options.layer = "body";
				}
				if (!options.layerFields && vulpe.config.formName) {
					options.layerFields = vulpe.config.formName;
				}
				if (options.individualLoading) {
					vulpe.config.layout.showLoading = false;
					jQuery("select,input", "#" + options.layer).each(function(index) {
						var elementId = $(this).attr("id");
						var elementType = $(this).attr("type");
						if (elementType != null && elementType != "hidden") {
							var elementLoadingId = elementId + vulpe.config.suffix.loading;
							var elementLoading = vulpe.util.get(elementLoadingId);
							if (elementLoading.length == 1) {
								elementLoading.show();
							} else {
								$(this).append("&nbsp;<img id='" + elementLoadingId + "' class='vulpeImageFieldLoading' src='" + vulpe.config.contextPath + "/themes/" + vulpe.config.theme + "/images/ajax/field-loader.gif' alt='" + vulpe.config.messages.loading + "' />");
								vulpe.util.get(elementLoadingId).show();
							}
						}
					});
				}
				try {
					vulpe.view.request.invokeGlobalsBeforeJs(options.layerFields);
				} catch(e) {
					return false;
				}
				if (!options.formName && options.layerFields) {
					options.formName = 	options.layerFields;
				}
				if (options.validate && (typeof options.isFile == "undefined" || !options.isFile)) {
					var name = options.formName.substring(0, 1).toUpperCase() + options.formName.substring(1);
					try {
						var isValid = eval('validate' + name + '()');
						if (!isValid) {
							return false;
						}
					} catch(e) {
						// do nothing
					}
					jQuery("." + vulpe.config.css.fieldError, vulpe.util.get(options.layerFields)).each(function (i) {
						vulpe.exception.hideError(this);
					});
					var files = jQuery(':file[value]', vulpe.util.get(options.layerFields));
					if (files && files.length && files.length > 0) {
						options.files = files;
						jQuery.uploadFiles(options);
						return;
					}
				}

				// serialize form
				var queryStringForm = jQuery(":input[type!='file']", vulpe.util.get(options.formName)).fieldSerialize();
				if (vulpe.util.isNotEmpty(options.queryString) && vulpe.util.isNotEmpty(queryStringForm)) {
					options.queryString = options.queryString + '&' + queryStringForm;
				} else if (vulpe.util.isNotEmpty(queryStringForm)) {
					options.queryString = queryStringForm;
				}

				// sets values
				jQuery(":input[type!='file']", vulpe.util.get(options.formName)).each(function(i) {
					var input = jQuery(this);
					input.attr('defaultValue', input.val());
				});
				if (!options.url) {
					options.url = vulpe.util.getForm(options).attr("action");
				}
				options.afterCallback = function() {
					vulpe.view.request.invokeGlobalsAfterJs(options.layerFields);
				}
				return vulpe.view.request.submitPage(options);
			}
		}
	},
	// vulpe.exception
	exception: {
		focusFirstError: function(layerFields) {
			var fields = jQuery("." + vulpe.config.css.fieldError, vulpe.util.get(layerFields));
			if (fields && fields.length > 0) {
				var field = jQuery(fields[0]);
				if (field) {
					var idField = field.attr("id");
					var idParent = idField.substring(0, idField.lastIndexOf(vulpe.config.token.dot));
					var idSelectPopup = idParent + vulpe.config.suffix.selectPopup;
					var selectPopup = vulpe.util.get(idSelectPopup);
					if (selectPopup != null && selectPopup.length == 1) {
						vulpe.util.get(idParent + "_id").focus();
					} else {
						field.focus();
					}
				}
			}
		},

		setupError: function(fieldName, message) {
			if (vulpe.config.layout.showIconErrorMessage) {
				var messageSuffix = fieldName + vulpe.config.suffix.iconErrorMessage;
				vulpe.util.get(messageSuffix).show();
			}
			vulpe.util.get(fieldName).addClass(vulpe.config.css.fieldError);
			var errorMessage = vulpe.util.get(fieldName + vulpe.config.suffix.errorMessage);
			errorMessage.html(message);
			errorMessage.css("display", "block");
			var errorController = function () {
				var config = vulpe.util.getElementConfig(fieldName);
				if (!config) {
					if (this.value.length == 0) {
						vulpe.exception.showFieldError({element: this});
					} else {
						errorMessage.hide();
						if (this.value != "__/__/_____") {
							vulpe.exception.hideFieldError({element: this});
						}
					}
				} else {
					var field = vulpe.util.get(fieldName);
					var required = field.attr("class").indexOf("vulpeRequired") != -1;
					var length = field.val().length;
					if (required && length == 0) {
						errorMessage.html(vulpe.config.messages.error.validate.required);
						vulpe.exception.showFieldError({element: this});
					} else {
						if (config.type == "STRING") {
							var value = field.val();
							if (config.minlength) {
								if (vulpe.util.trim(value).length >= config.minlength || length == 0) {
									vulpe.exception.hideFieldError({element: this});
								} else {
									errorMessage.html(vulpe.config.messages.error.validate.minlength.replace("{0}", config.minlength));
									errorMessage.css("display", "block");
									vulpe.exception.showFieldError({element: this});
								}
							} else if (config.maxlength) {
								if (vulpe.util.trim(value).length <= config.maxlength || length == 0) {
									vulpe.exception.hideFieldError({element: this});
								} else {
									errorMessage.html(vulpe.config.messages.error.validate.maxlength.replace("{0}", config.maxlength));
									errorMessage.css("display", "block");
									vulpe.exception.showFieldError({element: this});
								}
							}
							if (value.length > 1) {
								if (vulpe.util.checkRepeatedCharacters(value)) {
									errorMessage.html(vulpe.config.messages.error.validate.repeatedCharacters);
									errorMessage.css("display", "block");
									vulpe.exception.showFieldError({element: this});
								} else {
									vulpe.exception.hideFieldError({element: this});
								}
							}
						} else if (config.type == "DATE") {
							var value = $(this).val();
							if (value.indexOf("_") == -1 && value.length == config.datePattern.length) {
								if (vulpe.validate.validateDate({
									field: $(this),
									datePatternStrict: config.datePattern,
									setupErrorOff: true
								})) {
									vulpe.exception.hideFieldError({element: this});
								} else {
									errorMessage.html(vulpe.config.messages.error.validate.date);
									errorMessage.css("display", "block");
									vulpe.exception.showFieldError({element: this});
								}
							}
						} else {
							if (config.min) {
								if (field.val() >= config.min) {
									vulpe.exception.hideFieldError({element: this});
								} else {
									vulpe.exception.showFieldError({element: this});
								}
							}
							if (config.max) {
								if (field.val() <= config.max) {
									vulpe.exception.hideFieldError({element: this});
								} else {
									vulpe.exception.showFieldError({element: this});
								}
							}
						}
					}
				}
			}
			vulpe.util.get(fieldName).change(errorController);
			vulpe.util.get(fieldName).blur(errorController);
			vulpe.util.get(fieldName).keydown(errorController);
			vulpe.util.get(fieldName).keyup(errorController);
		},

		showFieldError: function(options) {
			if (options.element) {
				if (typeof options.highlight == "undefined") {
					options.highlight = true;
				}
				var idField = jQuery(options.element).attr("id");
				if (options.highlight) {
					var idParent = idField.substring(0, idField.lastIndexOf(vulpe.config.token.dot));
					var idSelectPopup = idParent + vulpe.config.suffix.selectPopup;
					var selectPopup = vulpe.util.get(idSelectPopup);
					if (selectPopup.length == 1) {
						jQuery("#" + idParent + "_id").addClass(vulpe.config.css.fieldError);
					}
					jQuery(options.element).addClass(vulpe.config.css.fieldError);
				}
				if (vulpe.config.layout.showIconErrorMessage) {
					var error = vulpe.util.get(idField + vulpe.config.suffix.iconErrorMessage);
					if (error.length == 1) {
						error.show();
					}
				}
			}
		},

		hideFieldError: function(options) {
			if (options.element) {
				var idField = jQuery(options.element).attr("id");
				var idParent = idField.substring(0, idField.lastIndexOf(vulpe.config.token.dot));
				var idSelectPopup = idParent + vulpe.config.suffix.selectPopup;
				var selectPopup = vulpe.util.get(idSelectPopup);
				if (selectPopup.length == 1) {
					jQuery("#" + idParent + "_id").removeClass(vulpe.config.css.fieldError);
				}
				jQuery(options.element).removeClass(vulpe.config.css.fieldError);
				var error = vulpe.util.get(idField + vulpe.config.suffix.iconErrorMessage);
				if (error.length == 1) {
					error.hide();
					vulpe.util.get(idField + vulpe.config.suffix.errorMessage).hide();
				}
			}
		},

		hideError: function(element) {
			jQuery(element).removeClass(vulpe.config.css.fieldError);
			var error = vulpe.util.get(element.id + vulpe.config.suffix.iconErrorMessage);
			if (error.length == 1) {
				error.hide();
			}
		},

		showMessageError: function(message, complete) {
			var messageLayer = vulpe.config.layers.messages;
			if (vulpe.util.existsVulpePopups()) {
				messageLayer += vulpe.config.prefix.popup + vulpe.util.getLastVulpePopup();
			}
			$(messageLayer).removeClass("vulpeMessageError");
			$(messageLayer).removeClass("vulpeMessageSuccess");
			$(messageLayer).removeClass("vulpeMessageInfo");
			$(messageLayer).addClass("vulpeMessageValidation");
			var messagesClose="<div id=\"closeMessages\"><a href=\"javascript:void(0);\" onclick=\"$('" + messageLayer + "').slideUp('slow')\">" +vulpe.config.messages.close + "</a></div>";
			if (!complete) {
				$(messageLayer).html("<ul><li class='vulpeAlertError'>" + message + "</li></ul>" + messagesClose);
			} else {
				$(messageLayer).html(message + messagesClose);
			}
			$(messageLayer).slideDown("slow");
			if (!vulpe.util.existsVulpePopups()) {
				jQuery(document).bind("keydown", "Esc", function(evt) {
					$('#messages').slideUp('slow', function(){ $(this).html(""); });
					return false;
				});
			}
			if (eval(vulpe.config.messageSlideUp)) {
				setTimeout(function() {
					$(messageLayer).slideUp("slow");
				}, vulpe.config.messageSlideUpTime);
			}
		},

		handlerError: function(data, status, e) {
			vulpe.view.hideLoading();
			if (typeof data != "string" && data.responseText) {
				data = data.responseText;
			}
			if (data.indexOf("\"vulpeAlertError\"") != -1) {
				vulpe.exception.showMessageError(data, true);
			} else {
				var message = data;
				if (message.indexOf("<body>") != -1) {
					message.substring(message.indexOf("<body>"));
					message = message.substring(0, message.indexOf("</body>"));
				}
				jQuery(vulpe.config.layers.modalMessages).html(message);
			}
			if (data.indexOf("\"vulpeAlertError\"") == -1) {
				jQuery(vulpe.config.layers.modalMessages).removeClass("vulpeMessageInfo");
				jQuery(vulpe.config.layers.modalMessages).removeClass("vulpeMessageSuccess");
				jQuery(vulpe.config.layers.modalMessages).removeClass("vulpeMessageValidation");
				jQuery(vulpe.config.layers.modalMessages).addClass("vulpeMessageError");
				var elementId = vulpe.config.layers.modalMessages.substring(1);
				eval("window." + elementId + " = vulpe.view.showMessages();");
			}
		}
	}
};
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
		browser: {
			firefox: (BrowserDetect.browser == "Firefox" || BrowserDetect.browser == "Mozilla"),
			ie: (BrowserDetect.browser == "MSIE" || BrowserDetect.browser == "Explorer"),
			webkit: (BrowserDetect.browser == "Chrome" || BrowserDetect.browser == "Safari")
		},
		contextPath: "",
		css: {
			fieldError: "vulpeFieldError"
		},
		elements: new Array(),
		entity: "-entity.",
		formName: "",
		iPhone: {
			popupTop: 0
		},
		layers: {
			alertDialog: "#alertDialog",
			vulpeAlertMessage: "#vulpeAlertMessage",
			confirmationDialog: "#confirmationDialog",
			confirmationMessage: "#confirmationMessage",
			messages: "#messages",
			modalMessages: "#modalMessages"
		},
		lightbox: {
			imageText: "vulpe.lightbox.image.text",
			ofText: "vulpe.lightbox.of.text"
		},
		logic: {
			prepareName: ""
		},
		masterTabId: "#vulpeCRUDBody",
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
			exclusion: "vulpe.msg.confirm.exclusion",
			fieldRequired: "vulpe.js.error.required",
			keyRequired: "vulpe.js.error.key.required",
			selectedExclusion: "vulpe.msg.confirm.selected.exclusion",
			selectRecordsExclusion: "vulpe.msg.select.records.exclusion",
			upload: "vulpe.error.upload",
			close: "vulpe.messages.close"
		},
		messageSlideUp: true,
		messageSlideUpTime: 10000,
		os: {
			iPhone: (BrowserDetect.OS == "iPhone/iPod")
		},
		order: new Array(),
		pagingPage: "-paging.page",
		prefix: {
			button: "vulpeButton",
			detail: "#vulpeDetail-",
			popup: "Popup-",
			selectForm: "vulpeSelectForm-",
			selectTable: "vulpeSelectTable-"
		},
		popup: {
			mobile: false
		},
		showLoading: true,
		springSecurityCheck: "j_spring_security_check",
		suffix: {
			errorMessage: "-errorMessage",
			identifier: "-id",
			loading: "-loading",
			selectedTab: "-selectedTab"
		},
		redirectToIndex: true,
		requireOneFilter: false,
		theme: 'default',
		token: {
			fieldIndex: "__",
			dot: "_"
		},
		accentMap: {}
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
			for (var i = 0; i < term.length; i++) {
				ret += vulpe.config.accentMap[term.charAt(i)] || term.charAt(i);
			}
			return ret;
		},
		
		checkRepeatedCharacters: function(value) {
			var firstChar = value.charAt(0);
			var equalChars = true;
			for (var i = 0; i < value.length; i++) {
				var char = value.charAt(i);
				if (char != firstChar) {
					equalChars = false;
				}
			}
			return equalChars;
		},

		checkHotKeyExists: function(hotKey) {
			var elemData = jQuery.data(document);
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

		/**
		 *
		 * @param options {hotKey, command, override, dontFireInText}
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
				jQuery(document).bind("keydown", options.hotKey, options.command);
				if (options.putSameOnReturnKey && vulpe.util.checkHotKeyExists("return") == -1) {
					jQuery(document).bind("keydown", "return", options.command);
				}
				if (options.dontFireInText) {
					var dontFireInText = jQuery(document).attr("dontFireInText");
					if (!dontFireInText) {
						dontFireInText = new Array();
					}
					dontFireInText[options.hotKey] = true;
					jQuery(document).attr("dontFireInText", dontFireInText);
				}
			}
		},

		removeHotKey: function(hotKey) {
			var position = vulpe.util.checkHotKeyExists(hotKey);
			if (position != -1){
				var elemData = jQuery.data(document);
				if (elemData.events && elemData.events['keydown']) {
					var keydown = elemData.events['keydown'];
					vulpe.util.removeArray(keydown, position);
				}
			}
			return position;
		},

		removeHotKeys: function(hotKeys) {
			for (var i = 0; i < hotKeys.length; i++) {
				vulpe.util.removeHotKey(hotKeys[i]);
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
			var prefix = element ? vulpe.util.getPrefixIdByElement(element) : vulpe.util.getPrefixId();
			var field = vulpe.util.get(name.indexOf(prefix) != -1 ? name : prefix + name);
			if (field.length == 0){
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

		getButton: function(name) {
			return vulpe.util.get("vulpeButton" + name + "-" + vulpe.config.formName);
		},

		getForm: function(formName) {
			if (formName) {
				vulpe.config.formName = formName;
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
			var token = "input.focused,select.focused,textarea.focused";
			var fields = layer ? jQuery(token, layer.indexOf("#") == -1 ? vulpe.util.get(layer) : jQuery(layer)) : jQuery(token);
			if (fields && fields.length > 0) {
				var focused = false;
				for (var i = 0; i < fields.length; i++) {
					var field = jQuery(fields[i]);
					if (field.val() == "") {
						field.focus();
						focused = true;
						break;
					}
				}
				if (!focused) {
					fields[0].focus();
				}
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

		selectTab: function(formName, name) {
			var selectedTab = vulpe.util.get(formName + vulpe.config.suffix.selectedTab);
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
					vulpe.exception.hideFieldError(element);
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
				return s.replace(/^\s*/, "").replace(/\s*$/, "");
			} else {
				return '';
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
			return (typeof v != "undefined" && v && vulpe.util.trim(v) != '');
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
		}
	},
	// vulpe.validate
	validate: {
		forms: new Array(),

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
					if (!isValid) {
						vulpe.exception.setupError(idField, message);
					}
					return isValid;
				}
			}

			var idField = config.field.attr("id");
			var typeField = config.field.attr("type");
			if (typeField == 'text' || typeField == 'textarea' || typeField == 'file' ||
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
					if (!isValid) {
						vulpe.exception.setupError(idField, message);
					}
					return isValid;
				} else {
					var idParent = idField.substring(0, idField.lastIndexOf(vulpe.config.token.dot));
					var idSelectPopup = idParent + "-selectPopup";
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
					vulpe.exception.setupError(idField, message);
				}
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
					vulpe.exception.setupError(idField, message);
				}
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
						vulpe.exception.setupError(idField, message);
					} else {
						var iValue = parseInt(value);
						if (isNaN(iValue) || !(iValue >= -2147483648 && iValue <= 2147483647)) {
							bValid = false;
							vulpe.exception.setupError(idField, message);
						}
					}
				}
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
						vulpe.exception.setupError(idField, message);
					} else {
						var iValue = parseFloat(value);
						if (isNaN(iValue)) {
							bValid = false;
							vulpe.exception.setupError(idField, message);
						}
					}
				}
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
						if(matched != null) {
							if (!vulpe.validate.isValidDate(matched[2], matched[1], matched[3])) {
								bValid =  false;
								vulpe.exception.setupError(idField, message);
							}
						} else {
							bValid =  false;
							vulpe.exception.setupError(idField, message);
						}
					} else
					if ((orderMonth < orderYear && orderMonth > orderDay)) {
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
						if(matched != null) {
							if (!vulpe.validate.isValidDate(matched[1], matched[2], matched[3])) {
								bValid =  false;
								vulpe.exception.setupError(idField, message);
							}
						} else {
							bValid =  false;
							vulpe.exception.setupError(idField, message);
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
								bValid =  false;
								vulpe.exception.setupError(idField, message);
							}
						} else {
							bValid =  false;
							vulpe.exception.setupError(idField, message);
						}
					} else {
						bValid =  false;
						vulpe.exception.setupError(idField, message);
					}
				}
			}
			if (!bValid) {
				config.field.val("");
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
				for (var i = 0; i < fields.length; i++) {
					var field = jQuery(fields[i]);
					var idField = field.attr("id");
					if (!vulpe.validate.validateRequired({field: field})) {
						if (invalidCount == 0 && !vulpe.util.existsVulpePopups()) {
							if (idField.indexOf(vulpe.config.entity) != -1 && idField.indexOf(vulpe.config.token.fieldIndex) != -1) {
								var detail = vulpe.config.prefix.detail + vulpe.util.getDetailByElementId(idField);
								$("a[href='" + detail + "']").click();
							} else if ($("a[href='" + vulpe.config.masterTabId + "']").length == 1) {
								$("a[href='" + vulpe.config.masterTabId + "']").click();
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
				}
			} else if (vulpe.config.requireOneFilter) {
				var filters = jQuery('input[id*=entitySelect],select[id*=entitySelect],textarea[id*=entitySelect]', parent);
				if (filters && filters.length > 0) {
					var empty = true;
					for (var i = 0; i < filters.length; i++) {
						var field = jQuery(filters[i]);
						var idField = field.attr("id");
						var typeField = field.attr("type");
						if (typeField == "hidden") {
							continue;
						} else if (typeField == "checkbox") {
							if (eval(field.attr("checked"))) {
								empty = false;
								break;
							}
						} else {
							var idParent = idField.substring(0, idField.lastIndexOf(vulpe.config.token.dot));
							var idSelectPopup = idParent + "-selectPopup";
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
								break;
							}
						}
					}
					if (empty) {
						vulpe.util.focusFirst("#" + formName);
						vulpe.exception.showMessageError(vulpe.config.messages.error.validate.requireOneFilter);
						return false;
					}
				}
			}
			var allFields = jQuery("input", parent);
			var invalidFields = 0;
			for (var i = 0; i < allFields.length; i++) {
				var field = jQuery(allFields[i]);
				if (!vulpe.validate.validateAttribute(field)) {
					valid = false;
					invalidFields++;
				}
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
			if (field.val() == "%") {
				return valid;
			}
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
		confirmExclusion: function(command) {
			vulpe.command = command;
			$(vulpe.config.layers.confirmationMessage).html(vulpe.config.messages.exclusion);
			$(vulpe.config.layers.confirmationDialog).dialog('open');
		},

		confirmSelectedExclusion: function() {
			$(vulpe.config.layers.confirmationMessage).html(vulpe.config.messages.selectedExclusion);
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
				value = (value == "") ? property : value + "," + property;
			}
			vulpe.util.get(sortPropertyInfo).val(value);
			var propertyId = sortPropertyInfo + '-' + property.replace(".", "_");
			vulpe.util.get(propertyId).addClass(order);
			vulpe.config.order[propertyId] = {property: sortPropertyInfo, value: value, css: order};
			var buttonRead = vulpe.util.getButton("Read");
			if (buttonRead) {
				buttonRead.click();
			}
			if (value == "") {
				value = "obj.id";
			}
		},

		setupSortTable: function(sortPropertyInfoName) {
			var columns = jQuery("th[id!='']", "#entities");
			for (var i = 0; i < columns.length; i++) {
				var column = columns[i];
				var order = vulpe.config.order[column.id];
				if (order) {
					column.className = order.css;
				}
			}
		},

		setSelectCheckbox: function(select) {
			vulpe.view.isSelection = select;
		},


		markOnlyOne: function(controller, name, parent) {
			if (!parent) {
				parent = "#body";
			}
			var selections = jQuery(":checkbox[name$='" + name + "']", parent);
			for (var i = 0; i < selections.length; i++) {
				selections[i].checked = false;
			}
			controller.checked = true;
		},

		markUnmarkAll: function(controller, name, parent) {
			if (!parent) {
				parent = "#body";
			}
			var selections = jQuery(":checkbox[name$='" + name + "']", parent);
			for (var i = 0; i < selections.length; i++) {
				selections[i].checked = controller.checked;
			}
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
			var popupName = jQuery(row).parents('div.vulpePopup').attr('id');
			var popup = vulpe.util.get(popupName);
			var popupProperties = popup.attr('properties');
			vulpe.util.loopMap(popupProperties, function(c, v) {
				var value = vulpe.util.getValueMap(values, v);
				value = (typeof value == "undefined") ? '' : webtoolkit.url.decode(value);
				vulpe.util.get(webtoolkit.url.decode(c)).val(value.replace(/\+/g, " "));
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

			vulpe.view.hidePopup(popupName);
		},

		loading: null,

		showLoading: function() {
			var visible = vulpe.util.get('loading').css("display") != "none";
			if (vulpe.config.showLoading && !visible) {
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
			var popup = $('#' + options.id).dialog({
					autoOpen: true,
					width: options.width,
					modal: true
			});
			$('#' + options.id).css("padding", "0px");
			vulpe.util.focusFirst("#" + options.id);
			return popup;
		},

		hidePopup: function(elementId) {
			$("#" + elementId).dialog("close");
		},

		request: { // vulpe.view.request
			openPopup: function(url, width, height, popupName) {
				var left = (screen.width - width) / 2;
				var top = (screen.height - height) / 2;
				return window.open(url, popupName, 'height=' + height + ', width=' + width + ', top=' + top + ', left=' + left);
			},

			submitReport: function(actionURL, width, height) {
				var popupName = 'popup' + new Date().getTime();
				return vulpe.view.request.openPopup(actionURL, width, height, popupName);
			},

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
					if (this.layerFields == layerFields)
						this.callback();
				});
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
				options.queryString = 'detail=' + (options.detail == 'entities' ? options.detail : 'entity.' + options.detail) + '&detailIndex=' + options.detailIndex;
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
				for (var i = 0; i < selections.length; i++) {
					if (selections[i].checked) {
						count++;
					}
					selectedIds[i] = selections[i].checked ? selections[i].value : "";
				}
				if (count > 0) {
					$(vulpe.config.layers.confirmationMessage).html(vulpe.config.messages.selectedExclusion);
					$(vulpe.config.layers.confirmationDialog).dialog({
						autoOpen: false,
						resizable: false,
						height:140,
						modal: true,
						overlay: {
							backgroundColor: '#000',
							opacity: 0.5
						},
						buttons: {
							Ok: function() {
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
							},
							Cancel: function() {
								$(this).dialog('close');
							}
						}
					});
					$(vulpe.config.layers.confirmationDialog).dialog('open');
				} else {
					$(vulpe.config.layers.vulpeAlertMessage).html(vulpe.config.messages.selectRecordsExclusion);
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
				for (var i = 0; i < selections.length; i++) {
					if (selections[i].checked) count++;
					selectedIds[i] = selections[i].checked ? selections[i].value : "";
				}
				if (count > 0) {
					$(vulpe.config.layers.confirmationMessage).html(vulpe.config.messages.selectedExclusion);
					$(vulpe.config.layers.confirmationDialog).dialog({
						autoOpen: false,
						resizable: false,
						height:140,
						modal: true,
						overlay: {
							backgroundColor: '#000',
							opacity: 0.5
						},
						buttons: {
							Ok: function() {
								$(this).dialog('close');
								for (var i = 0; i < selectedIds.length; i++) {
									if (selectedIds != "") {
										selections[i].checked;
										selections[i].value = selectedIds[i];
									}
								}
								options.validate = false;
								vulpe.view.request.submitAjaxAction(options);
							},
							Cancel: function() {
								$(this).dialog('close');
							}
						}
					});
					$(vulpe.config.layers.confirmationDialog).dialog('open');
				} else {
					$(vulpe.config.layers.vulpeAlertMessage).html(vulpe.config.messages.selectRecordsExclusion);
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
				vulpe.util.getForm(options.formName).attr("action", options.url);
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
					vulpe.util.getForm(options.formName).attr("action", vulpe.config.contextPath + '/' + vulpe.config.springSecurityCheck);
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
				var popup = jQuery('<div>').attr('id', options.name).attr('layerParent', options.layerParent).attr('properties', options.properties).attr('expressions', options.expressions);
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
						afterJs: options.afterJs
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
				if (!vulpe.view.request.submitBefore(options.beforeJs)) {
					return false;
				}
				options.queryString = (vulpe.util.isNotEmpty(options.queryString) ? options.queryString + '&' : '') + 'ajax=true';
				if (!vulpe.util.existsVulpePopups()) {
					//hotkeys.triggersMap = {};
				}
				vulpe.view.showLoading();
				jQuery.ajax({
					type: "POST",
					dataType: 'html',
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					url: vulpe.util.completeURL(options.url),
					data: options.queryString,
					success: function (data, status) {
						vulpe.view.hideLoading();
						vulpe.config.showLoading = true;
						var authenticator = options.url.indexOf("/j_spring_security_check") != -1;
						var loginError = data.indexOf("vulpeLoginForm") == -1;
						var validUrlRedirect = vulpe.config.authenticator.url.redirect.indexOf("/ajax") == -1;
						if (data.indexOf('<!--IS_EXCEPTION-->') != -1) {
							vulpe.exception.handlerError(data, status);
						} else if (!authenticator && !loginError && vulpe.config.redirectToIndex && vulpe.config.authenticator.url.redirect == '') {
							$(window.location).attr("href", vulpe.config.contextPath);
						} else {
							try {
								vulpe.config.redirectToIndex = true;
								if (authenticator && loginError && validUrlRedirect) {
									$(window.location).attr("href", vulpe.config.authenticator.url.redirect);
								} else {
									var html = "";
									if (vulpe.util.existsVulpePopups(options.layer)) {
										var messagePopup = "<div id=\"messagesPopup_" + options.layer + "\" style=\"display: none;\" class=\"vulpeMessages\"></div>";
										html = messagePopup + data;
									} else {
										html = data;
									}
									vulpe.util.get(options.layer).html(html);
									if (vulpe.util.isNotEmpty(options.afterJs)) {
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
			 * @param options {url, uri, formName, layer, layerFields, beforeJs, afterJs, individualLoading, validate, isFile}
			 */
			submitAjax: function(options) {
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
					vulpe.config.showLoading = false;
					var elements = jQuery("select,input,span", "#" + options.layer);
					for (var i = 0; i < elements.length; i++) {
						var elementId = elements[i].id;
						if (vulpe.util.get(elementId).attr("type") != null && vulpe.util.get(elementId).attr("type") == "hidden") {
							continue;
						}
						var elementLoadingId = elementId + vulpe.config.suffix.loading;
						var elementLoading = vulpe.util.get(elementLoadingId);
						if (elementLoading.length == 1) {
							elementLoading.show();
						} else {
							vulpe.util.get(elementId).append("&nbsp;<img id='" + elementLoadingId + "' class='vulpeImageFieldLoading' src='" + vulpe.config.contextPath + "/themes/" + vulpe.config.theme + "/images/ajax/field-loader.gif' />");
							vulpe.util.get(elementLoadingId).show();
						}
					}
				}
				jQuery(vulpe.config.layers.messages).hide();
				// verifier if exists validations before submit
				if (!vulpe.view.request.submitBefore(options.beforeJs)) {
					return false;
				}
				options.beforeJs = ''; // set empty do not validate on submitPage
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
					options.url = vulpe.util.getForm(options.formName).attr("action");
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
					field.focus();
				}
			}
		},

		setupError: function(fieldName, message) {
			var messageSuffix = fieldName + vulpe.config.suffix.errorMessage;
			vulpe.util.get(messageSuffix).attr("title", message);
			vulpe.util.get(fieldName).addClass(vulpe.config.css.fieldError);
			vulpe.util.get(messageSuffix).tooltip({
				onShow: function() {
					this.getTip().css("zIndex", "100000");
					var title = vulpe.util.get(messageSuffix).attr("title");
					if (title) {
						this.getTip().html(vulpe.util.get(messageSuffix).attr("title"));
						vulpe.util.get(messageSuffix).attr("title", "");
					}
				}
			});
			vulpe.util.get(messageSuffix).show();
			var errorController = function () {
				var config = vulpe.util.getElementConfig(fieldName);
				if (!config) {
					if (this.value.length == 0) {
						vulpe.exception.showFieldError(this);
					} else {
						if (this.value != "__/__/_____") {
							vulpe.exception.hideFieldError(this);
						}
					}
				} else {
					var field = vulpe.util.get(fieldName);
					var required = field.attr("class").indexOf("vulpeRequired") != -1;
					var length = field.val().length;
					if (required && length == 0) {
						vulpe.exception.showFieldError(this);
					} else {
						if (config.type == "STRING") {
							var value = field.val();
							if (value.length > 1) {
								if (vulpe.util.checkRepeatedCharacters(value)) {
									vulpe.exception.showFieldError(this);
								} else {
									vulpe.exception.hideFieldError(this);	
								}
							}
						} else {
							if (config.min) {
								if (field.val() >= config.min) {
									vulpe.exception.hideFieldError(this);
								} else {
									vulpe.exception.showFieldError(this);
								}
							}
							if (config.max) {
								if (field.val() <= config.max) {
									vulpe.exception.hideFieldError(this);
								} else {
									vulpe.exception.showFieldError(this);
								}
							}
							if (config.minlength) {
								if (vulpe.util.trim(field.val()).length >= config.minlength || length == 0) {
									vulpe.exception.hideFieldError(this);
								} else {
									vulpe.exception.showFieldError(this);
								}
							} else if (config.maxlength) {
								if (vulpe.util.trim(field.val()).length <= config.maxlength || length == 0) {
									vulpe.exception.hideFieldError(this);
								} else {
									vulpe.exception.showFieldError(this);
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

		showFieldError: function(element) {
			if (element) {
				jQuery(element).addClass(vulpe.config.css.fieldError);
				var error = vulpe.util.get(element.id + vulpe.config.suffix.errorMessage);
				if (error.length == 1) {
					error.show();
				}
			}
		},

		hideFieldError: function(element) {
			if (element) {
				jQuery(element).removeClass(vulpe.config.css.fieldError);
				var error = vulpe.util.get(element.id + vulpe.config.suffix.errorMessage);
				if (error.length == 1) {
					error.hide();
				}
			}
		},

		hideError: function(element) {
			jQuery(element).removeClass(vulpe.config.css.fieldError);
			var error = vulpe.util.get(element.id + vulpe.config.suffix.errorMessage);
			if (error.length == 1) {
				error.hide();
			}
		},

		showMessageError: function(message) {
			var messageLayer = vulpe.config.layers.messages;
			if (vulpe.util.existsVulpePopups()) {
				messageLayer += vulpe.config.prefix.popup + vulpe.util.getLastVulpePopup();
			}
			$(messageLayer).removeClass("vulpeMessageError");
			$(messageLayer).removeClass("vulpeMessageSuccess");
			$(messageLayer).addClass("vulpeMessageValidation");
			var messagesClose="<div id=\"closeMessages\"><a href=\"javascript:void(0);\" onclick=\"$('" + messageLayer + "').slideUp('slow')\">" +vulpe.config.messages.close + "</a></div>";
			$(messageLayer).html("<ul><li class='vulpeAlertError'>" + message + "</li></ul>" + messagesClose);
			$(messageLayer).slideDown("slow");
			if (!vulpe.util.existsVulpePopups()) {
				jQuery(document).bind("keydown", "Esc", function(evt) {
					$('#messages').slideUp('slow');
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
			if (typeof e == "undefined") {
				if (typeof data != "string" && data.responseText) {
					data = data.responseText;
				}
				if (data.indexOf("\"vulpeAlertError\"") != -1) {
					jQuery(vulpe.config.layers.messages).html(data);
				} else {
					jQuery(vulpe.config.layers.modalMessages).html(data);
				}
			} else {
				jQuery(vulpe.config.layers.messages).html(vulpe.config.messages.error.fatal + e);
			}
			if (data.indexOf("\"vulpeAlertError\"") == -1) {
				jQuery(vulpe.config.layers.modalMessages).removeClass("vulpeMessageSuccess");
				jQuery(vulpe.config.layers.modalMessages).removeClass("vulpeMessageValidation");
				jQuery(vulpe.config.layers.modalMessages).addClass("vulpeMessageError");
				var elementId = vulpe.config.layers.modalMessages.substring(1);
				eval("window." + elementId + " = vulpe.view.showMessages();");
			}
		}
	}
};
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
		browser: {
			firefox: (BrowserDetect.browser == "Firefox" || BrowserDetect.browser == "Mozilla"),
			ie: (BrowserDetect.browser == "MSIE" || BrowserDetect.browser == "Explorer"),
			webkit: (BrowserDetect.browser == "Chrome" || BrowserDetect.browser == "Safari")
		},
		contextPath: '',
		css: {
			fieldError: 'vulpeFieldError'
		},
		elements: new Array(),
		entity: '_entity.',
		identifier: '_id',
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
			imageText: 'vulpe.lightbox.image.text',
			ofText: 'vulpe.lightbox.of.text'
		},
		logic: {
			prepareName: ''
		},
		messages: {
			error: {
				checkfield: 'vulpe.error.validate.checkfield',
				checkfields: 'vulpe.error.validate.checkfield',
				validate: {
					date:'vulpe.error.validate.date',
					double:'vulpe.error.validate.double',
					float:'vulpe.error.validate.float',
					floatRange:'vulpe.error.validate.float.range',
					integer:'vulpe.error.validate.integer',
					integerRange:'vulpe.error.validate.integer.range',
					long:'vulpe.error.validate.long',
					mask:'vulpe.error.validate.mask',
					maxlength:'vulpe.error.validate.maxlength',
					minlength:'vulpe.error.validate.minlength',
					required:'vulpe.error.validate.required'
				}
			},
			exclusion: 'vulpe.msg.confirm.exclusion',
			fieldRequired: 'vulpe.js.error.required',
			keyRequired: 'vulpe.js.error.key.required',
			selectedExclusion: 'vulpe.msg.confirm.selected.exclusion',
			selectRecordsExclusion: 'vulpe.msg.select.records.exclusion',
			upload: 'vulpe.error.upload',
			close: 'vulpe.messages.close'
		},
		messageSlideUp: true,
		messageSlideUpTime: 10000,
		os: {
			iPhone: (BrowserDetect.OS == "iPhone/iPod")
		},
		order: new Array(),
		pagingPage: '_paging.page',
		prefix: {
			popup: "Popup_"
		},
		popup: {
			closeTitle: "Close (Shortcut: Esc)",
			mobile: false
		},
		showLoading: true,
		springSecurityCheck: "j_spring_security_check",
		suffix: {
			action: '.action',
			errorMessage: '_ErrorMessage',
			selectedTab: '_selectedTab'
		},
		theme: 'default'
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

		getPrefixId: function(formName) {
			var prefix = formName + "_" + (vulpe.config.logic.prepareName == "" ? "" : vulpe.config.logic.prepareName + ".");
			return prefix
		},

		getPrefixIdByElement: function(element) {
			var id = element.id;
			var prefix = id.indexOf(":") != -1 ? id.substring(0, id.lastIndexOf(":") + 1) : id.substring(0, id.lastIndexOf(".") + 1);
			return prefix;
		},

		getIndexOfElement: function(element) {
			var id = element.id.substring(0, element.id.lastIndexOf(":"));
			var index = id.substring(id.lastIndexOf(":") + 1, id.length);
			return index;
		},

		getElementConfig: function(id) {
			return vulpe.config.elements[id];
		},

		getURLComplete: function(url) {
			if (url.indexOf(vulpe.config.contextPath) == -1) {
				url = vulpe.config.contextPath + '/' + url;
			}
			return url;
		},

		focusFirst: function(layer) {
			var token = "[class*=focused]";
			var fields = layer ? jQuery(token, layer.indexOf("#") == -1 ? vulpe.util.get(layer) : jQuery(layer)) : jQuery(token);
			if (fields && fields.length > 0) {
				for (var i = 0; i < fields.length; i++) {
					var field = jQuery(fields[i]);
					var type = field.attr("type");
					if ((type == "text" || type == "password" || type == "select-one") && field.val() == "") {
						field.focus();
						break;
					}
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

		selectTab: function(formName, index) {
			var selectedTab = vulpe.util.get(formName + vulpe.config.suffix.selectedTab);
			selectedTab.val(index);
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
			for (i = 0; list && i < list.length; i++) {
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
			for (i = 0; i < vulpe.validate.forms.length; i++) {
				if (vulpe.validate.forms[i].name == formName) {
					return i;
				}
			}
			return vulpe.validate.forms.length;
		},

		getVulpePopup: function(popupName) {
			for (i = 0; i < vulpe.view.popups.length; i++) {
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
					value = config.field.val();
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
			if (typeField == 'text' || typeField == 'textarea') {
				if (!vulpe.validate.checkEmail(config.field.val())) {
					isValid = false;
					vulpe.exception.setupError(idField, message);
				}
			}
			return isValid;
		},

		validateAttributes: function(formName) {
			var valid = true;
			var parent = "#" + formName;
			if (vulpe.util.existsVulpePopups()) {
				parent += "#" + vulpe.util.getLastVulpePopup();
			}
			var fields = jQuery("[class*='vulpeRequired']", parent);
			if (fields && fields.length > 0) {
				for (var i = 0; i < fields.length; i++) {
					var field = jQuery(fields[i]);
					var idField = field.attr("id");
					if (!vulpe.validate.validateRequired({field: field})) {
						valid = false;
					}
					if (valid) {
						if (!vulpe.validate.validateAttribute(field)) {
							valid = false;
						}
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
				var messageLayer = vulpe.config.layers.messages;
				if (vulpe.util.existsVulpePopups()) {
					messageLayer += vulpe.config.prefix.popup + vulpe.util.getLastVulpePopup();
				}
				$(messageLayer).removeClass("vulpeMessageError");
				$(messageLayer).removeClass("vulpeMessageSuccess");
				$(messageLayer).addClass("vulpeMessageValidation");
				var manyFields = (invalidFields > 1 || fields.length > 1);
				var messagesClose="<div id=\"closeMessages\"><a href=\"javascript:void(0);\" onclick=\"$('#messages').slideUp('slow')\">" +vulpe.config.messages.close + "</a></div>";
				$(messageLayer).html("<ul><li class='vulpeAlertError'>" + (manyFields ? vulpe.config.messages.error.checkfields : vulpe.config.messages.error.checkfield) + "</li></ul>" + messagesClose);
				$(messageLayer).slideDown("slow");
				jQuery(document).bind("keydown", "Esc", function(evt) {
					$('#messages').slideUp('slow');
					return false;
				});
				if (eval(vulpe.config.messageSlideUp)) {
					setTimeout(function() {
						$(messageLayer).slideUp("slow");
					}, vulpe.config.messageSlideUpTime);
				}
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

		validateLoginForm: function(formName) {
			var form = vulpe.util.getElement(formName);
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
			vulpe.util.getElement(formName + vulpe.config.pagingPage).value = '';
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

		showHideElement: function(elementId) {
			var element = jQuery("#" + elementId);
			if (element.css("display") == 'none') {
				element.slideDown("slow");
			} else {
				element.slideUp("slow");
			}
		},

		sortTable: function(formName, sortPropertyInfo, property) {
			var columns = vulpe.util.getElement(sortPropertyInfo).value.split(',');
			var oldDesc = false;
			var order = '';
			var value = '';
			for (var i = 0; i < columns.length; i++) {
				if (vulpe.util.trim(columns[i]) == property || vulpe.util.trim(columns[i]) == (property + ' vulpeOrderDesc')) {
					order = 'vulpeOrderDesc';
					if (vulpe.util.trim(columns[i]) == property) {
						if (value == '') {
							value = vulpe.util.trim(columns[i]) + ' desc';
						} else {
							value = value + ',' + vulpe.util.trim(columns[i]) + ' desc';
						}
					} else {
						oldDesc = true;
					}
				} else {
					if (value == '') {
						value = vulpe.util.trim(columns[i]);
					} else {
						value = value + ',' + vulpe.util.trim(columns[i]);
					}
				}
			}
			if (order == '') {
				order = 'vulpeOrderAsc';
				if (value == '') {
					value = property;
				} else {
					value = value + ', ' + property;
				}
			} else if (order == 'vulpeOrderDesc' && oldDesc) {
				order = '';
			}
			vulpe.util.getElement(sortPropertyInfo).value = value;
			vulpe.util.getElement(sortPropertyInfo + '_' + property).className = order;
			vulpe.config.order[sortPropertyInfo + '_' + property] = {property: sortPropertyInfo, value: value, css: order};
			var buttonRead = $("#vulpeButtonRead_" + formName);
			if (buttonRead) {
				buttonRead.click();
			}
		},

		setSelectCheckbox: function(select) {
			vulpe.view.isSelection = select;
		},

		markUnmarkAll: function(controller, parent) {
			var selections = jQuery("*[name$='selected']", parent);
			for (i = 0; i < selections.length; i++) {
				selections[i].checked = controller.checked;
			}
		},

		setupSortTable: function(sortPropertyInfoName) {
			var sortPropertyInfo = vulpe.util.getElement(sortPropertyInfoName);
			if (sortPropertyInfo && vulpe.util.isNotEmpty(sortPropertyInfo.value) != '') {
				var columns = sortPropertyInfo.value.split(',');
				var i = 0;
				for(i = 0; i < columns.length; i++) {
					var order = '';
					if (vulpe.util.trim(columns[i]).indexOf(' vulpeOrderDesc') >= 0) {
						order = 'vulpeOrderDesc';
					} else {
						order = 'vulpeOrderAsc';
					}
					var property = vulpe.util.trim(columns[i]).replace(' vulpeOrderDesc', '');
					var propertyTh = vulpe.util.getElement(sortPropertyInfoName + '_' + property);
					if (propertyTh) {
						propertyTh.className = order;
					}
				}
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
			var popupProperties = popup.attr('popupProperties');

			vulpe.util.loopMap(popupProperties, function(c, v) {
				var value = vulpe.util.getValueMap(values, v);
				if (typeof value == "undefined") {
					value = '';
				} else {
					value = webtoolkit.url.decode(value);
				}
				vulpe.util.get(webtoolkit.url.decode(c)).val(value.replace(/\+/g, " "));
			});

			var popupExpressions = popup.attr('popupExpressions');
			var layerParent = webtoolkit.url.decode(popup.attr('layerParent'));
			vulpe.util.loopMap(popupExpressions, function(c, v) {
				var value = vulpe.util.getValueMap(values, v);
				if (typeof value == "undefined") {
					value = '';
				} else {
					value = webtoolkit.url.decode(value).replace(/\+/g, " ");
				}
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
			if (vulpe.config.showLoading) {
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
			jQuery(vulpe.config.layers.modalMessages).modal({
				title: function() {
					var popupTitle = jQuery("#messageTitle", "#modalMessages");
					if (popupTitle) {
						popupTitle.hide();
						return popupTitle.html();
					}
					return "";
				},
				closeTitle: vulpe.config.popup.closeTitle,
				overlayId: 'overlayMessages',
				containerId: 'containerMessages',
				onClose: function (dialog) {
					this.close(true);
					vulpe.view.hideMessages();
				}
			});
		},

		onhidemessages: null,

		hideMessages: function() {
			if (vulpe.util.isNotEmpty(vulpe.view.onhidemessages)) {
				eval(webtoolkit.url.decode(vulpe.view.onhidemessages));
				vulpe.view.onhidemessages = null;
			}
		},

		showPopup: function(elementId, popupWidth) {
			var popup = vulpe.util.get(elementId).modal({
				title: function() {
					var popupTitle = jQuery("#contentTitle", "#" + elementId);
					if (popupTitle) {
						popupTitle.hide();
						return popupTitle.html();
					}
					return "";
				},
				closeTitle: vulpe.config.popup.closeTitle,
				overlayId: 'overlay' + elementId,
				containerId: 'container' + elementId,
				iframeCss: {zIndex: 2000},
				overlayCss: {zIndex: 2001},
				containerCss: {zIndex: 2002, width: popupWidth},
				onClose: function (dialog) {
					this.close(true);
					eval('window.' + elementId + ' = undefined;');
					vulpe.util.get(elementId).remove();
					vulpe.util.removeArray(vulpe.view.popups, vulpe.util.getVulpePopup(elementId));
				}
			});
			jQuery(document).bind("keydown", "Esc", function(evt) {
				vulpe.view.hidePopup(elementId);
				return false;
			});
			vulpe.util.focusFirst("#" + elementId);
			return popup;
		},

		hidePopup: function(elementId) {
			eval('window.' + elementId + '.close();');
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
				if (typeof layerFields == "undefined")
					layerFields = '';

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

			uploadAll: function(files, formName, layerFields, queryString, layer, validate, beforeJs, afterJs) {
				jQuery.uploadFiles({
					files: files,
					formName: formName,
					layerFields: layerFields,
					queryString: queryString,
					layer: layer,
					validate: validate,
					beforeJs: beforeJs,
					afterJs: afterJs
				});
			},

			submitPaging: function(page, actionURL, formName, layerFields, layer, beforeJs, afterJs) {
				vulpe.util.getElement(formName + vulpe.config.pagingPage).value = page;
				vulpe.view.request.submitFormAction(actionURL, formName, layerFields, '', layer, false, beforeJs, afterJs);
			},

			submitView: function(id, actionURL, formName, layerFields, layer, beforeJs, afterJs) {
				if (vulpe.view.isSelection) {
					return false;
				}
				vulpe.util.getElement(formName + vulpe.config.identifier).value = id;
				paging = vulpe.util.getElement(formName + vulpe.config.pagingPage);
				if (paging) {
					vulpe.util.getElement(formName + vulpe.config.pagingPage).value = 0;
				}
				vulpe.view.request.submitFormAction(actionURL, formName, layerFields, '', layer, false, beforeJs, afterJs);
			},

			submitUpdate: function(id, actionURL, formName, layerFields, layer, beforeJs, afterJs, verify) {
				if (verify && vulpe.view.isSelection) {
					return false;
				}
				vulpe.util.getElement(formName + vulpe.config.identifier).value = id;
				vulpe.view.request.submitFormAction(actionURL, formName, layerFields, '', layer, false, beforeJs, afterJs);
			},

			submitDelete: function(id, actionURL, formName, layerFields, layer, beforeJs, afterJs) {
				vulpe.util.getElement(formName + vulpe.config.identifier).value = id;
				vulpe.view.request.submitFormAction(actionURL, formName, layerFields, '', layer, false, beforeJs, afterJs);
			},

			submitDeleteDetail: function(detail, detailIndex, actionURL, formName, layerFields, layer, beforeJs, afterJs) {
				vulpe.view.request.submitFormAction(actionURL, formName, layerFields, 'detail=' + (detail == 'entities' ? detail : 'entity.' + detail) + '&detailIndex=' + detailIndex, layer, false, beforeJs, afterJs);
			},

			submitDeleteDetailSelected: function(detail, actionURL, formName, layerFields, layer, beforeJs, afterJs) {
				var selections = jQuery("*[name$='selected']");
				var selectedIds = new Array();
				var count = 0;
				for (i = 0; i < selections.length; i++) {
					if (selections[i].checked) count++;
					selectedIds[i] = selections[i].checked ? selections[i].value : "";
				}
				if (count > 0) {
					$(vulpe.config.layers.confirmationMessage).html(vulpe.config.messages.selectedExclusion);
					$(vulpe.config.layers.confirmationDialog).dialog({
						autoOpen: false,
						bgiframe: true,
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
								for (i = 0; i < selectedIds.length; i++) {
									if (selectedIds != "") {
										selections[i].checked;
										selections[i].value = selectedIds[i];
									}
								}
								vulpe.view.request.submitFormAction(actionURL, formName, layerFields, 'detail=' + (detail == 'entities' || detail.indexOf("entity.") != -1 ? detail : 'entity.' + detail), layer, false, beforeJs, afterJs);
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

			submitDeleteSelected: function(actionURL, formName, layerFields, layer, beforeJs, afterJs) {
				var selections = jQuery("*[name$='selected']");
				var selectedIds = new Array();
				var count = 0;
				for (i = 0; i < selections.length; i++) {
					if (selections[i].checked) count++;
					selectedIds[i] = selections[i].checked ? selections[i].value : "";
				}
				if (count > 0) {
					$(vulpe.config.layers.confirmationMessage).html(vulpe.config.messages.selectedExclusion);
					$(vulpe.config.layers.confirmationDialog).dialog({
						autoOpen: false,
						bgiframe: true,
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
								for (i = 0; i < selectedIds.length; i++) {
									if (selectedIds != "") {
										selections[i].checked;
										selections[i].value = selectedIds[i];
									}
								}
								vulpe.view.request.submitFormAction(actionURL, formName, layerFields, '', layer, false, beforeJs, afterJs);
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

			submitFormAction: function(actionURL, formName, layerFields, queryString, layer, validate, beforeJs, afterJs) {
				if (actionURL.indexOf("Post") != -1 || actionURL.indexOf("read") != -1) {
					if (!vulpe.validate.validateAttributes(formName)) {
						return false;
					}
				}
				var form = vulpe.util.getElement(formName);
				if (actionURL.indexOf(vulpe.config.contextPath) == -1) {
					actionURL = vulpe.config.contextPath + '/' + actionURL;
				}
				form.action = actionURL;
				vulpe.view.request.submitForm(formName, layerFields, queryString, layer, validate, beforeJs, afterJs, false);
			},

			submitLoginForm: function(formName, layerFields, queryString, layer, validate, beforeJs, afterJs) {
				var form = vulpe.util.getElement(formName);
				if (vulpe.validate.validateLoginForm(formName)) {
					if (vulpe.util.existsVulpePopups()) {
						layer = vulpe.util.getLastVulpePopup();
					}
					form.action = vulpe.config.contextPath + '/' + vulpe.config.springSecurityCheck;
					vulpe.view.request.submitForm(formName, layerFields, queryString, layer, validate, beforeJs, afterJs, false);
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

			submitForm: function(formName, layerFields, queryString, layer, validate, beforeJs, afterJs, isFile) {
				jQuery(vulpe.config.layers.messages).hide();
				// verifier if exists validations before submit
				if (!vulpe.view.request.submitBefore(beforeJs)) {
					return false;
				}
				beforeJs = ''; // set empty do not validate on submitPage
				try {
					vulpe.view.request.invokeGlobalsBeforeJs(layerFields);
				} catch(e) {
					return false;
				}

				var form = vulpe.util.getElement(formName);
				if (validate && (typeof isFile == "undefined" || !isFile)) {
					var nome = formName.substring(0, 1).toUpperCase() + formName.substring(1);
					try {
						var isValid = eval('validate' + nome + '()');
						if (!isValid) {
							return false;
						}
					} catch(e) {
						// do nothing
					}

					jQuery("." + vulpe.config.css.fieldError, vulpe.util.get(layerFields)).each(function (i) {
						vulpe.exception.hideError(this);
					});

					var files = jQuery(':file[value]', vulpe.util.get(layerFields));
					if (files && files.length && files.length > 0) {
						vulpe.view.request.uploadAll(files, formName, layerFields, queryString, layer, validate, beforeJs, afterJs);
						return;
					}
				}

				// serialize form
				var queryStringForm = jQuery(":input[type!='file']", vulpe.util.get(formName)).fieldSerialize();
				if (vulpe.util.isNotEmpty(queryString) && vulpe.util.isNotEmpty(queryStringForm)) {
					queryString = queryString + '&' + queryStringForm;
				} else if (vulpe.util.isNotEmpty(queryStringForm)) {
					queryString = queryStringForm;
				}

				// sets values
				jQuery(":input[type!='file']", vulpe.util.get(formName)).each(function(i) {
					var input = jQuery(this);
					input.attr('defaultValue', input.val());
				});

				return vulpe.view.request.submitPage(form.action, queryString, layer, beforeJs, afterJs, function() {
					vulpe.view.request.invokeGlobalsAfterJs(layerFields);
				});
			},

			submitMenu: function(url, beforeJs, afterJs) {
				jQuery(vulpe.config.layers.messages).hide();
				vulpe.config.order = new Array();
				return vulpe.view.request.submitPage(vulpe.config.contextPath + url, '', 'body', beforeJs, afterJs);
			},

			submitPopup: function(url, queryString, popupName, popupLayerParent, paramLayerParent, popupProperties, popupExpressions, paramProperties, paramExpressions, requiredParamProperties, requiredParamExpressions, styleClass, beforeJs, afterJs, popupWidth) {
				vulpe.util.setVulpePopup(popupName);
				var popup = jQuery('<div>').attr('id', popupName).attr('layerParent', popupLayerParent).attr('popupProperties', popupProperties).attr('popupExpressions', popupExpressions);
				popup.hide();
				popup.css("height", "100%");
				popup.addClass('vulpePopup');
				if (vulpe.util.isNotEmpty(styleClass)) {
					popup.addClass(styleClass);
				}
				popup.appendTo('body');
				if (vulpe.util.isNotEmpty(afterJs)) {
					afterJs = escape("vulpe.view.request.onShowPopup('") + escape(popupName) + escape("', '") + afterJs + escape("', '") + popupWidth + escape("')");
				} else {
					afterJs = escape("vulpe.view.request.onShowPopup('") + escape(popupName) + escape("', '', '") + popupWidth + escape("')");
				}
				try {
					queryString = vulpe.view.request.complementQueryString(queryString, paramLayerParent, paramProperties, paramExpressions, requiredParamProperties, requiredParamExpressions);
				} catch(e) {
					popup.remove();
					//alert(e);
					return false;
				}
				if (!vulpe.view.request.submitPage(vulpe.config.contextPath + url, queryString, popupName, beforeJs, afterJs)) {
					popup.remove();
					return false;
				} else {
					return true;
				}
			},
			/**
			 * popupInputField=pageInputFieldWithValue
			 */
			complementQueryString: function(queryString, layerParent, paramProperties, paramExpressions, requiredParamProperties, requiredParamExpressions) {
				var queryParam = '';
				vulpe.util.loopMap(requiredParamProperties, function(c, v) {
					var element = vulpe.util.get(webtoolkit.url.decode(v));
					var value = element.val();
					if (vulpe.util.isEmpty(value)) {
						var name = (element.attr('title') ? element.attr('title') : 'Value');
						element.focus();
						throw name + vulpe.config.messages.fieldRequired;
					}
					queryParam = queryParam + '&' + c + '=' + escape(value);
				});
				vulpe.util.loopMap(requiredParamExpressions, function(c, v) {
					var element = null;
					if (vulpe.util.isEmpty(layerParent)) {
						element = jQuery(webtoolkit.url.decode(v));
					} else {
						element = jQuery(webtoolkit.url.decode(v), vulpe.util.get(layerParent));
					}
					var value = element.val();
					if (vulpe.util.isEmpty(value)) {
						var name = (element.attr('title') ? element.attr('title') : 'Value');
						element.focus();
						throw name + vulpe.config.messages.fieldRequired;
					}
					queryParam = queryParam + '&' + c + '=' + escape(value);
				});
				vulpe.util.loopMap(paramProperties, function(c, v) {
					var value = vulpe.util.get(webtoolkit.url.decode(v)).val();
					queryParam = queryParam + '&' + c + '=' + escape(value);
				});
				vulpe.util.loopMap(paramExpressions, function(c, v) {
					var value = null;
					if (vulpe.util.isEmpty(layerParent)) {
						value = jQuery(webtoolkit.url.decode(v)).val();
					} else {
						value = jQuery(webtoolkit.url.decode(v), vulpe.util.get(layerParent)).val();
					}
					queryParam = queryParam + '&' + c + '=' + escape(value);
				});
				if (vulpe.util.isNotEmpty(queryParam)) {
					if (vulpe.util.isEmpty(queryString)) {
						return queryParam.substring(1);
					} else {
						return queryString + queryParam;
					}
				} else {
					return queryString;
				}
			},

			onShowPopup: function(popupName, afterJs, popupWidth) {
				eval('window.' + popupName + ' = vulpe.view.showPopup(popupName, popupWidth);');
				if (vulpe.util.isNotEmpty(afterJs)) {
					try {
						eval(webtoolkit.url.decode(afterJs));
					} catch(e) {
						// do nothing
					}
				}
			},

			submitPage: function(urlStr, queryString, layer, beforeJs, afterJs, afterCallback) {
				if (!vulpe.view.request.submitBefore(beforeJs)) {
					return false;
				}

				if (vulpe.util.isNotEmpty(queryString)) {
					queryString = queryString + '&ajax=true';
				} else {
					queryString = 'ajax=true';
				}

				var popup = jQuery('[id$=Popup]');
				if (!popup || popup.length == 0) {
					hotkeys.triggersMap = {};
				}

				vulpe.view.showLoading();
				jQuery.ajax({
					type: "POST",
					dataType: 'html',
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					url: vulpe.util.getURLComplete(urlStr),
					data: queryString,
					success: function (data, status) {
						vulpe.view.hideLoading();
						vulpe.config.showLoading = true;
						if (data.indexOf('<!--IS_EXCEPTION-->') != -1) {
							vulpe.exception.handlerError(data, status);
						} else {
							try {
								var html = "";
								if (vulpe.util.existsVulpePopups(layer)) {
									var messagePopup = "<div id=\"messagesPopup_" + layer + "\" style=\"display: none;\" class=\"messages\"></div>";
									html = messagePopup + data;
								} else {
									html = data;
								}
								vulpe.util.get(layer).html(html);
								if (vulpe.util.isNotEmpty(afterJs)) {
									try {
										eval(webtoolkit.url.decode(afterJs));
										vulpe.view.request.invokeGlobalsAfterJs();
										if (typeof afterCallback == "function") {
											afterCallback();
										}
									} catch(e) {
										// do nothing
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

			submitAjax: function(formName, uri, id, beforeJs, afterJs, individualLoading) {
				var form = vulpe.util.getElement(formName);
				form.action = vulpe.config.contextPath + uri;
				var layerFields = formName;

				if (individualLoading) {
					vulpe.config.showLoading = false;
					var elements = jQuery("select,input,span", "#" + id);
					for (var i = 0; i < elements.length; i++) {
						var elementId = elements[i].id;
						var elementLoading = vulpe.util.get(elementId + "_loading");
						if (elementLoading) {
							elementLoading.show();
						}
					}
				}
				vulpe.view.request.submitForm(formName, layerFields, '', id, true, beforeJs, afterJs, false);
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
				opacity: 0.9,
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
			vulpe.util.get(fieldName).change(errorController);
			vulpe.util.get(fieldName).blur(errorController);
			vulpe.util.get(fieldName).keydown(errorController);
			vulpe.util.get(fieldName).keyup(errorController);
		},

		showFieldError: function(element) {
			if (element) {
				jQuery(element).addClass(vulpe.config.css.fieldError);
				var error = vulpe.util.get(element.id + vulpe.config.suffix.errorMessage);
				if (error) {
					error.show();
				}
			}
		},

		hideFieldError: function(element) {
			if (element) {
				jQuery(element).removeClass(vulpe.config.css.fieldError);
				var error = vulpe.util.get(element.id + vulpe.config.suffix.errorMessage);
				if (error) {
					error.hide();
				}
			}
		},

		hideError: function(element) {
			jQuery(element).removeClass(vulpe.config.css.fieldError);
			vulpe.util.get(element.id + vulpe.config.suffix.errorMessage).hide();
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
				jQuery(vulpe.config.layers.messages).html("Erro fatal: " + e);
			}
			if (data.indexOf("\"vulpeAlertError\"") == -1) {
				jQuery(vulpe.config.layers.modalMessages).removeClass("vulpeMessageSuccess");
				jQuery(vulpe.config.layers.modalMessages).removeClass("vulpeMessageValidation");
				jQuery(vulpe.config.layers.modalMessages).addClass("vulpeMessageError");
				vulpe.view.showMessages();
			}
		}
	}
};
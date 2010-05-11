var BrowserDetect = {
	init: function () {
		this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
		this.version = this.searchVersion(navigator.userAgent)
			|| this.searchVersion(navigator.appVersion)
			|| "an unknown version";
		this.OS = this.searchString(this.dataOS) || "an unknown OS";
	},
	searchString: function (data) {
		for (var i=0;i<data.length;i++)	{
			var dataString = data[i].string;
			var dataProp = data[i].prop;
			this.versionSearchString = data[i].versionSearch || data[i].identity;
			if (dataString) {
				if (dataString.indexOf(data[i].subString) != -1) {
					return data[i].identity;
				}
			} else if (dataProp) {
				return data[i].identity;
			}
		}
	},
	searchVersion: function (dataString) {
		var index = dataString.indexOf(this.versionSearchString);
		if (index == -1) {
			return;
		}
		return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
	},
	dataBrowser: [
		{
			string: navigator.userAgent,
			subString: "Chrome",
			identity: "Chrome"
		},
		{ 	string: navigator.userAgent,
			subString: "OmniWeb",
			versionSearch: "OmniWeb/",
			identity: "OmniWeb"
		},
		{
			string: navigator.vendor,
			subString: "Apple",
			identity: "Safari",
			versionSearch: "Version"
		},
		{
			prop: window.opera,
			identity: "Opera"
		},
		{
			string: navigator.vendor,
			subString: "iCab",
			identity: "iCab"
		},
		{
			string: navigator.vendor,
			subString: "KDE",
			identity: "Konqueror"
		},
		{
			string: navigator.userAgent,
			subString: "Firefox",
			identity: "Firefox"
		},
		{
			string: navigator.vendor,
			subString: "Camino",
			identity: "Camino"
		},
		{		// for newer Netscapes (6+)
			string: navigator.userAgent,
			subString: "Netscape",
			identity: "Netscape"
		},
		{
			string: navigator.userAgent,
			subString: "MSIE",
			identity: "Explorer",
			versionSearch: "MSIE"
		},
		{
			string: navigator.userAgent,
			subString: "Gecko",
			identity: "Mozilla",
			versionSearch: "rv"
		},
		{ 		// for older Netscapes (4-)
			string: navigator.userAgent,
			subString: "Mozilla",
			identity: "Netscape",
			versionSearch: "Mozilla"
		}
	],
	dataOS : [
		{
			string: navigator.platform,
			subString: "Win",
			identity: "Windows"
		},
		{
			string: navigator.platform,
			subString: "Mac",
			identity: "Mac"
		},
		{
			   string: navigator.userAgent,
			   subString: "iPhone",
			   identity: "iPhone/iPod"
	    },
		{
			string: navigator.platform,
			subString: "Linux",
			identity: "Linux"
		}
	]

};
BrowserDetect.init();

var webtoolkit = {

	/**
	*
	*  Unselectable text
	*  http://www.webtoolkit.info/
	*
	**/
	unselectable: {
		enable : function(e) {
			var e = e ? e : window.event;
			if (e.button != 1) {
				if (e.target) {
					var targer = e.target;
				} else if (e.srcElement) {
					var targer = e.srcElement;
				}

				var targetTag = targer.tagName.toLowerCase();
				if ((targetTag != "input") && (targetTag != "textarea")) {
					return false;
				}
			}
		},

		disable : function () {
			return true;
		}
	}
	/*
	if (typeof(document.onselectstart) != "undefined") {
		document.onselectstart = Unselectable.enable;
	} else {
		document.onmousedown = Unselectable.enable;
		document.onmouseup = Unselectable.disable;
	}
	*/,
	/**
	*
	*  Base64 encode / decode
	*  http://www.webtoolkit.info/
	*
	**/
	base64: {
		// private property
		_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

		// public method for encoding
		encode : function (input) {
			var output = "";
			var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
			var i = 0;

			input = Base64._utf8_encode(input);

			while (i < input.length) {

				chr1 = input.charCodeAt(i++);
				chr2 = input.charCodeAt(i++);
				chr3 = input.charCodeAt(i++);

				enc1 = chr1 >> 2;
				enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
				enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
				enc4 = chr3 & 63;

				if (isNaN(chr2)) {
					enc3 = enc4 = 64;
				} else if (isNaN(chr3)) {
					enc4 = 64;
				}

				output = output +
				this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
				this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

			}

			return output;
		},

		// public method for decoding
		decode : function (input) {
			var output = "";
			var chr1, chr2, chr3;
			var enc1, enc2, enc3, enc4;
			var i = 0;

			input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

			while (i < input.length) {

				enc1 = this._keyStr.indexOf(input.charAt(i++));
				enc2 = this._keyStr.indexOf(input.charAt(i++));
				enc3 = this._keyStr.indexOf(input.charAt(i++));
				enc4 = this._keyStr.indexOf(input.charAt(i++));

				chr1 = (enc1 << 2) | (enc2 >> 4);
				chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
				chr3 = ((enc3 & 3) << 6) | enc4;

				output = output + String.fromCharCode(chr1);

				if (enc3 != 64) {
					output = output + String.fromCharCode(chr2);
				}
				if (enc4 != 64) {
					output = output + String.fromCharCode(chr3);
				}

			}

			output = Base64._utf8_decode(output);

			return output;
		},

		// private method for UTF-8 encoding
		_utf8_encode : function (string) {
			string = string.replace(/\r\n/g,"\n");
			var utftext = "";

			for (var n = 0; n < string.length; n++) {

				var c = string.charCodeAt(n);

				if (c < 128) {
					utftext += String.fromCharCode(c);
				}
				else if((c > 127) && (c < 2048)) {
					utftext += String.fromCharCode((c >> 6) | 192);
					utftext += String.fromCharCode((c & 63) | 128);
				}
				else {
					utftext += String.fromCharCode((c >> 12) | 224);
					utftext += String.fromCharCode(((c >> 6) & 63) | 128);
					utftext += String.fromCharCode((c & 63) | 128);
				}

			}

			return utftext;
		},

		// private method for UTF-8 decoding
		_utf8_decode : function (utftext) {
			var string = "";
			var i = 0;
			var c = c1 = c2 = 0;

			while ( i < utftext.length ) {

				c = utftext.charCodeAt(i);

				if (c < 128) {
					string += String.fromCharCode(c);
					i++;
				}
				else if((c > 191) && (c < 224)) {
					c2 = utftext.charCodeAt(i+1);
					string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
					i += 2;
				}
				else {
					c2 = utftext.charCodeAt(i+1);
					c3 = utftext.charCodeAt(i+2);
					string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
					i += 3;
				}

			}

			return string;
		}

	},
	/**
	*
	*  UTF-8 data encode / decode
	*  http://www.webtoolkit.info/
	*
	**/
	utf8: {
		// public method for url encoding
		encode : function (string) {
			string = string.replace(/\r\n/g,"\n");
			var utftext = "";

			for (var n = 0; n < string.length; n++) {

				var c = string.charCodeAt(n);

				if (c < 128) {
					utftext += String.fromCharCode(c);
				}
				else if((c > 127) && (c < 2048)) {
					utftext += String.fromCharCode((c >> 6) | 192);
					utftext += String.fromCharCode((c & 63) | 128);
				}
				else {
					utftext += String.fromCharCode((c >> 12) | 224);
					utftext += String.fromCharCode(((c >> 6) & 63) | 128);
					utftext += String.fromCharCode((c & 63) | 128);
				}

			}

			return utftext;
		},

		// public method for url decoding
		decode : function (utftext) {
			var string = "";
			var i = 0;
			var c = c1 = c2 = 0;

			while ( i < utftext.length ) {

				c = utftext.charCodeAt(i);

				if (c < 128) {
					string += String.fromCharCode(c);
					i++;
				}
				else if((c > 191) && (c < 224)) {
					c2 = utftext.charCodeAt(i+1);
					string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
					i += 2;
				}
				else {
					c2 = utftext.charCodeAt(i+1);
					c3 = utftext.charCodeAt(i+2);
					string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
					i += 3;
				}

			}

			return string;
		}

	},

	/**
	*
	*  URL encode / decode
	*  http://www.webtoolkit.info/
	*
	**/
	url: {

		// public method for url encoding
		encode : function (string) {
			return escape(this._utf8_encode(string));
		},

		// public method for url decoding
		decode : function (string) {
			return this._utf8_decode(unescape(string));
		},

		// private method for UTF-8 encoding
		_utf8_encode : function (string) {
			string = string.replace(/\r\n/g,"\n");
			var utftext = "";

			for (var n = 0; n < string.length; n++) {

				var c = string.charCodeAt(n);

				if (c < 128) {
					utftext += String.fromCharCode(c);
				} else if((c > 127) && (c < 2048)) {
					utftext += String.fromCharCode((c >> 6) | 192);
					utftext += String.fromCharCode((c & 63) | 128);
				} else {
					utftext += String.fromCharCode((c >> 12) | 224);
					utftext += String.fromCharCode(((c >> 6) & 63) | 128);
					utftext += String.fromCharCode((c & 63) | 128);
				}

			}

			return utftext;
		},

		// private method for UTF-8 decoding
		_utf8_decode : function (utftext) {
			var string = "";
			var i = 0;
			var c = c1 = c2 = 0;

			while ( i < utftext.length ) {

				c = utftext.charCodeAt(i);

				if (c < 128) {
					string += String.fromCharCode(c);
					i++;
				} else if((c > 191) && (c < 224)) {
					c2 = utftext.charCodeAt(i+1);
					string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
					i += 2;
				} else {
					c2 = utftext.charCodeAt(i+1);
					c3 = utftext.charCodeAt(i+2);
					string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
					i += 3;
				}

			}

			return string;
		}
	}
};

var _vulpeContextPath = "";
var _vulpeLightboxImageText = "";
var _vulpeLightboxOfText = "";
var _vulpeViewIsSelection = false;
var _vulpeLogicPrepareName = "";
var _vulpePopups = new Array();
var _vulpePopupMobile = false;
var _vulpeClosePopupTitle = "Close (Shortcut: Esc)";
var _vulpeValidateForms = new Array();
var _vulpeValidateMessages = {};
var _vulpeShowLoading = true;
var _vulpeErrorSufix = "_Error";
var _vulpeErrorMsgSufix = "_ErrorMsg";
var _vulpeModalMessages = "#modalMessages";
var _vulpeMessages = "#messages";
var _vulpeAlertMessage = "#alertMessage";
var _vulpeAlertDialog = "#alertDialog";
var _vulpeConfirmationMessage = "#confirmationMessage";
var _vulpeConfirmationDialog = "#confirmationDialog";
var _vulpeFieldError = "fieldError";
var _vulpeMsgFieldRequired = " is required.";
var _vulpeMsgKeyRequired = "Key is required.";
var _vulpeMsgExclusion = "";
var _vulpeMsgSelectRecordsExclusion = "";
var _vulpeMsgSelectedExclusion = "";
var _vulpeMsgUpload = "";
var _vulpeTheme = "";
var _vulpeActionSufix = ".action";
var _vulpeSpringSecurityCheck = "j_spring_security_check";
var _vulpeIdentifier = "_id";
var _vulpePagingPage = "_paging.page";
var _vulpeEntity = "_entity.";
var _command = function() {};
var _iPhone = (BrowserDetect.OS == "iPhone/iPod");
var _iPhonePopupTop = 0;
var _firefox = (BrowserDetect.browser == "Firefox" || BrowserDetect.browser == "Mozilla");
var _ie = (BrowserDetect.browser == "MSIE" || BrowserDetect.browser == "Explorer");
var _webkit = (BrowserDetect.browser == "Chrome" || BrowserDetect.browser == "Safari");
var vulpe = {
	util: { // vulpe.util
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
			var prefix = formName + "_" + (_vulpeLogicPrepareName == "" ? "" : _vulpeLogicPrepareName + ".");
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

		getURLComplete: function(url) {
			if (url.indexOf(_vulpeContextPath) == -1) {
				url = _vulpeContextPath + '/' + url;
			}
			if (url.indexOf(_vulpeSpringSecurityCheck) == -1 && url.indexOf(_vulpeActionSufix) == -1) {
				url = url + _vulpeActionSufix;
			}
			return url;
		},

		focusFirst: function() {
			var fields = jQuery('input[type!=checkbox][class$=focused]');
			if (fields && fields.length > 0) {
				var index = 0;
				var field = jQuery(fields[index]);
				if (field) {
					var type = field.attr("type");
					if (type == "text" || type == "password" || type == "select-one") {
						field.focus();
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
			var selectedTab = vulpe.util.get(formName + "_selectedTab");
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
				if (element != null && element.className.indexOf(_vulpeFieldError) != -1) {
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
			for (i = 0; i < _vulpeValidateForms.length; i++) {
				if (_vulpeValidateForms[i].name == formName) {
					return i;
				}
			}
			return _vulpeValidateForms.length;
		},

		getVulpePopup: function(popupName) {
			for (i = 0; i < _vulpePopups.length; i++) {
				if (_vulpePopups[i] == popupName) {
					return i;
				}
			}
			return _vulpePopups.length;
		},

		setVulpePopup: function(popupName) {
			_vulpePopups[_vulpePopups.length] = popupName;
		},

		existsVulpePopups: function(popupName) {
			if (vulpe.util.isNotEmpty(popupName)) {
				var vulpePopup = _vulpePopups[vulpe.util.getVulpePopup(popupName)];
				if (vulpePopup && vulpePopup != null) {
					return true;
				}
			} else if (_vulpePopups.length > 0) {
				return true;
			}
			return false;
		},

		getLastVulpePopup: function() {
			return _vulpePopups[_vulpePopups.length-1];
		}
	},

	validate: { // vulpe.validate
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
			return mask.exec(value);
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

			var atomPat = new RegExp(atom,"g");
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
			if (vulpe.validate.isArray(config.field)) {
				if (config.field[0].type == 'radio') {
					isValid = false;
					var i = 0;
					for (i=0; i<config.field.length; i++) {
						if (config.field[i].checked) {
							isValid = true;
							break;
						}
					}
					if (!isValid) {
						vulpe.exception.showError(config);
					}
					return isValid;
				}
			}

			if (config.field.type == 'text' ||
				config.field.type == 'textarea' ||
				config.field.type == 'file' ||
				config.field.type == 'select-one' ||
				config.field.type == 'radio' ||
				config.field.type == 'checkbox' ||
				config.field.type == 'password') {

				var value = '';
				if (config.field.type == "select-one") {
					var si = config.field.selectedIndex;
					if (si >= 0) {
						value = config.field.options[si].value;
					}
				} else if (config.field.type == "checkbox") {
					isValid = config.field.checked;
					if (!isValid) {
						vulpe.exception.showError(config);
					}
					return isValid;
				} else {
					value = config.field.value;
				}

				if (vulpe.util.trim(value).length == 0) {
					isValid = false;
					vulpe.exception.showError(config);
				}
			}
			return isValid;
		},

		validateMinLength: function(config) {
			var isValid = true;
			if (config.field.type == 'text' ||
				config.field.type == 'textarea') {

				if (vulpe.util.trim(config.field.value).length > 0 && vulpe.util.trim(config.field.value).length < parseInt(config.minlength)) {
					isValid = false;
					vulpe.exception.showError(config);
				}
			}
			return isValid;
		},

		validateMaxLength: function(config) {
			var isValid = true;
			if (config.field.type == 'text' ||
				config.field.type == 'textarea') {

				if (vulpe.util.trim(config.field.value).length > parseInt(config.maxlength)) {
					isValid = false;
					vulpe.exception.showError(config);
				}
			}
			return isValid;
		},

		validateMask: function(config) {
			var isValid = true;
			if (config.field.type == 'text' ||
				config.field.type == 'textarea') {

				if (!vulpe.validate.matchPattern(config.field.value, config.mask)) {
					isValid = false;
					vulpe.exception.showError(config);
				}
			}
			return isValid;
		},

		validateInteger: function(config) {
			var bValid = true;
			if (config.field.type == 'text' ||
				config.field.type == 'textarea') {

				if (config.field.value.length > 0) {
					var value = vulpe.util.replaceAll(config.field.value, '.', '');
					value = vulpe.util.replaceAll(value, ',', '.');
					if (!vulpe.validate.isAllDigits(value)) {
						bValid = false;
						vulpe.exception.showError(config);
					} else {
						var iValue = parseInt(value);
						if (isNaN(iValue) || !(iValue >= -2147483648 && iValue <= 2147483647)) {
							bValid = false;
							vulpe.exception.showError(config);
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
			if (config.field.type == 'text' ||
				config.field.type == 'textarea') {

				if (config.field.value.length > 0) {
					var value = vulpe.util.replaceAll(config.field.value, '.', '');
					value = vulpe.util.replaceAll(value, ',', '.');
					if (!vulpe.validate.isAllDigits(value)) {
						bValid = false;
						vulpe.exception.showError(config);
					} else {
						var iValue = parseFloat(value);
						if (isNaN(iValue)) {
							bValid = false;
							vulpe.exception.showError(config);
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
			if (config.field.type == 'text' ||
				config.field.type == 'textarea') {

				if (config.field.value.length > 0) {
					var MONTH = "MM";
					var DAY = "dd";
					var YEAR = "yyyy";
					var datePattern = config.datePatternStrict;
					var orderMonth = datePattern.indexOf(MONTH);
					var orderDay = datePattern.indexOf(DAY);
					var orderYear = datePattern.indexOf(YEAR);
					var dateRegexp = null;
					var value = config.field.value;
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
								vulpe.exception.showError(config);
							}
						} else {
							bValid =  false;
							vulpe.exception.showError(config);
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
								vulpe.exception.showError(config);
							}
						} else {
							bValid =  false;
							vulpe.exception.showError(config);
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
								vulpe.exception.showError(config);
							}
						} else {
							bValid =  false;
							vulpe.exception.showError(config);
						}
					} else {
						bValid =  false;
						vulpe.exception.showError(config);
					}
				}
			}
			return bValid;
		},

		validateIntRange: function(config) {
			var isValid = true;
			if (config.field.type == 'text' ||
				config.field.type == 'textarea') {

				var value = parseInt(config.field.value);
				if (!(value >= parseInt(config.min) && value <= parseInt(config.max))) {
					isValid = false;
					vulpe.exception.showError(config);
				}
			}
			return isValid;
		},

		validateFloatRange: function(config) {
			var isValid = true;
			if (config.field.type == 'text' ||
				config.field.type == 'textarea') {

				var value = parseFloat(config.field.value);
				if (!(value >= parseFloat(config.min) && value <= parseFloat(config.max))) {
					isValid = false;
					vulpe.exception.showError(config);
				}
			}
			return isValid;
		},

		validateEmail: function(config) {
			var isValid = true;
			if (config.field.type == 'text' ||
				config.field.type == 'textarea') {

				if (!vulpe.validate.checkEmail(config.field.value)) {
					isValid = false;
					vulpe.exception.showError(config);
				}
			}
			return isValid;
		},

		validateAttributes: function(formName) {
			var valid = true;
			if (_vulpeValidateForms && _vulpeValidateForms.length > 0) {
				var vulpeValidateAttributes = _vulpeValidateForms[vulpe.util.getVulpeValidateForms(formName)].attributes;
				if (vulpeValidateAttributes && vulpeValidateAttributes.length > 0) {
					for (i = 0; i < vulpeValidateAttributes.length; i++) {
						var attributeName = vulpeValidateAttributes[i].name;
						var attributeLabel = vulpeValidateAttributes[i].label;
						var attributeDescription = vulpeValidateAttributes[i].description;
						var elementId = formName + _vulpeEntity + attributeName;

						var elementIdPk = null;
						var elementIdLookup = null;
						var attributePk = vulpeValidateAttributes[i].identifier;
						var attributeLookup = vulpeValidateAttributes[i].description;

						if (attributePk != null && attributePk != "") {
							elementIdPk = elementId + "." + attributePk;
							if (attributeLookup != null && attributeLookup != "") {
								elementIdLookup = elementId + "." + attributeLookup;
							}
						}

						var element = vulpe.util.getElement((elementIdPk != null ? elementIdPk : elementId));
						if (element) {
							if (element.value == "") {
								var messageRequired = _vulpeValidateMessages.required;
								//vulpe.exception.setupError((elementIdLookup != null ? elementIdLookup : elementId), messageRequired.replace("{0}", (attributeLabel)));
								vulpe.exception.setupError((elementIdLookup != null ? elementIdLookup : elementId), messageRequired);
								valid = false;
							} else {
								if (attributeLookup != null) {
									element = vulpe.util.getElement((elementIdLookup != null ? elementIdLookup : elementId));
								}
								vulpe.exception.hideFieldError(element);
							}
						}
					}
					if (!valid) {
						vulpe.exception.focusFirstError(formName);
						var msgLayer = _vulpeMessages;
						if (vulpe.util.existsVulpePopups()) {
							msgLayer += "Popup_" + vulpe.util.getLastVulpePopup();
						}
						$(msgLayer).removeClass("error");
						$(msgLayer).removeClass("success");
						$(msgLayer).addClass("validation");
						$(msgLayer).html("<ul><li class='alertError'>" + _vulpeValidateMessages.validate + "</li></ul>");
						$(msgLayer).slideDown("slow");
						setTimeout(function() {
						  $(msgLayer).slideUp("slow");
						}, 5000);
					}
				}
			}
			if (valid) {
				var parent = "";
				if (vulpe.util.existsVulpePopups()) {
					parent += "Popup_" + vulpe.util.getLastVulpePopup();
				}
				var fields = jQuery("input[type!=checkbox][class*='required']", parent);
				if (fields && fields.length > 0) {
					for (var i = 0; i < fields.length; i++) {
						var field = jQuery(fields[i]);
						if (field) {
							var type = field.attr("type");
							if (type == "text" || type == "password" || type == "select-one") {
								if (field.val() == "") {
									var messageRequired = _vulpeValidateMessages.required;
									var idField = field.attr("id");
									vulpe.exception.setupError(idField, messageRequired);
									valid = false;
								}
							}
						}
					}
					if (!valid) {
						vulpe.exception.focusFirstError(formName);
						var msgLayer = _vulpeMessages;
						if (vulpe.util.existsVulpePopups()) {
							msgLayer += "Popup_" + vulpe.util.getLastVulpePopup();
						}
						$(msgLayer).removeClass("error");
						$(msgLayer).removeClass("success");
						$(msgLayer).addClass("validation");
						$(msgLayer).html("<ul><li class='alertError'>" + _vulpeValidateMessages.validate + "</li></ul>");
						$(msgLayer).slideDown("slow");
						setTimeout(function() {
						  $(msgLayer).slideUp("slow");
						}, 5000);
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
		},

	},

	view: { // vulpe.view
		redirectLogin: function() {
			window.location = _vulpeContextPath + "/login.action";
		},

		confirmExclusion: function(command) {
			_command = command;
			$(_vulpeConfirmationMessage).html(_vulpeMsgExclusion);
			$(_vulpeConfirmationDialog).dialog('open');
		},

		confirmSelectedExclusion: function() {
			$(_vulpeConfirmationMessage).html(_vulpeMsgSelectedExclusion);
			$(_vulpeConfirmationDialog).dialog('open');
		},

		prepareRead: function(formName) {
			vulpe.util.getElement(formName + _vulpePagingPage).value = '';
			return true;
		},

		clearForm: function(formName) {
			vulpe.util.get(formName).clearForm();
		},

		clearFieldsInLayer: function(layer) {
			jQuery(':input', vulpe.util.get(layer)).clearFields();
		},

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

		sortTable: function(sortPropertyInfo, property) {
			var columns = vulpe.util.getElement(sortPropertyInfo).value.split(',');
			var eraDesc = false;
			var order = '';
			var i=0;
			var value = '';
			for(i=0; i<columns.length; i++) {
				if (vulpe.util.trim(columns[i]) == property || vulpe.util.trim(columns[i]) == (property + ' desc')) {
					order = 'desc';
					if (vulpe.util.trim(columns[i]) == property) {
						if (value == '') {
							value = vulpe.util.trim(columns[i]) + ' desc';
						} else {
							value = value + ',' + vulpe.util.trim(columns[i]) + ' desc';
						}
					} else {
						eraDesc = true;
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
				order = 'asc';
				if (value == '') {
					value = property;
				} else {
					value = value + ', ' + property;
				}
			} else if (order == 'desc' && eraDesc) {
				order = '';
			}
			vulpe.util.getElement(sortPropertyInfo).value = value;
			vulpe.util.getElement(sortPropertyInfo + '_' + property).className = order;
		},

		setSelectCheckbox: function(select) {
			_vulpeViewIsSelection = select;
		},

		markUnmarkAll: function(controller) {
			var selections = jQuery("*[name$='selected']");
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
					if (vulpe.util.trim(columns[i]).indexOf(' desc') >= 0) {
						order = 'desc';
					} else {
						order = 'asc';
					}
					var property = vulpe.util.trim(columns[i]).replace(' desc', '');
					var propertyTh = vulpe.util.getElement(sortPropertyInfoName + '_' + property);
					if (propertyTh) {
						propertyTh.className = order;
					}
				}
			}
		},

		onmouseoverRow: function(row) {
			jQuery(row).addClass('selectedRow');
		},

		onmouseoutRow: function(row) {
			jQuery(row).removeClass('selectedRow');
		},

		/**
		 * values:
		 * fieldNameA=valueA,fieldNameB=valueB,fieldNameC=valueC
		 *
		 * popupProperties:
		 * returnFieldNameA=fieldNameA,returnFieldNameB=fieldNameB,returnFieldNameC=fieldNameC
		 */
		selectRow: function(row, values) {
			var popupName = jQuery(row).parents('div.popup').attr('id');
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
			if (_vulpeShowLoading) {
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
			jQuery(_vulpeModalMessages).modal({
				closeTitle: 'Fechar',
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
				closeTitle: _vulpeClosePopupTitle,
				overlayId: 'overlay' + elementId,
				containerId: 'container' + elementId,
				iframeCss: {zIndex: 2000},
				overlayCss: {zIndex: 2001},
				containerCss: {zIndex: 2002, width: popupWidth},
				onClose: function (dialog) {
					this.close(true);
					eval('window.' + elementId + ' = undefined;');
					vulpe.util.get(elementId).remove();
					vulpe.util.removeArray(_vulpePopups, vulpe.util.getVulpePopup(elementId));
				}
			});
			jQuery(document).bind("keydown", "Esc", function(evt) {
				vulpe.view.hidePopup(elementId); 
				return false; 
			});
			return popup;
		},

		hidePopup: function(elementId) {
			eval('window.' + elementId + '.close();');
		},

		request: { // vulpe.view.request
			openPopup: function(url, width, height, popupName) {
				// define center screen
				var left = (screen.width - width)/2;
				var top = (screen.height - height)/2;
				// open new window
				return window.open(url, popupName, 'height=' + height + ', width=' + width + ', top=' + top + ', left=' + left);
			},

			submitReport: function(actionURL, width, height) {
				var popupName = 'popup' + new Date().getTime();
				return vulpe.view.request.openPopup(actionURL, width, height, popupName);
			},

			globalsBeforeJs: new Array(),
			globalsAfterJs: new Array(),

			registerFunctions: function(v_array, v_callback, v_key, v_layerFields) {
				if (typeof v_key == "undefined") {
					throw _vulpeMsgKeyRequired;
				}
				if (typeof v_layerFields == "undefined") {
					v_layerFields = '';
				}

				var newFunction = {key: v_key, callback: v_callback, layerFields: v_layerFields, getKey: function() {return this.key + this.layerFields;}};

				var exists = false;
				jQuery(v_array).each(function(i) {
					exists = this.getKey() == newFunction.getKey();
					if (exists) {
						v_array[i] = newFunction;
						return false;
					}
				});

				if (!exists) {
					v_array[v_array.length] = newFunction;
				}
			},

			registerGlobalsBeforeJs: function(v_callback, v_key, v_layerFields) {
				vulpe.view.request.registerFunctions(vulpe.view.request.globalsBeforeJs, v_callback, v_key, v_layerFields);
			},

			registerGlobalsAfterJs: function(v_callback, v_key, v_layerFields) {
				vulpe.view.request.registerFunctions(vulpe.view.request.globalsAfterJs, v_callback, v_key, v_layerFields);
			},

			removeFunctions: function(v_array, v_key, v_layerFields) {
				if (typeof v_key == "undefined") {
					throw _vulpeMsgKeyRequired;
				}
				if (typeof v_layerFields == "undefined") {
					v_layerFields = '';
				}

				var key = v_key + v_layerFields;

				var indexs = new Array();
				jQuery(v_array).each(function(i) {
					if (this.getKey() == key) {
						indexs[indexs.length] = i;
						return false;
					}
				});

				jQuery(indexs).each(function(i) {
					vulpe.util.removeArray(v_array, this);
				});
			},

			removeGlobalsBeforeJs: function(v_key, v_layerFields) {
				vulpe.view.request.removeFunctions(vulpe.view.request.globalsBeforeJs, v_key, v_layerFields);
			},

			removeGlobalsAfterJs: function(v_key, v_layerFields) {
				vulpe.view.request.removeFunctions(vulpe.view.request.globalsAfterJs, v_key, v_layerFields);
			},

			invokeFunctions: function(v_array, v_layerFields) {
				if (typeof v_layerFields == "undefined")
					v_layerFields = '';

				jQuery(v_array).each(function(i) {
					if (this.layerFields == v_layerFields)
						this.callback();
				});
			},

			invokeGlobalsBeforeJs: function(v_layerFields) {
				vulpe.view.request.invokeFunctions(vulpe.view.request.globalsBeforeJs, v_layerFields);
			},

			invokeGlobalsAfterJs: function(v_layerFields) {
				vulpe.view.request.invokeFunctions(vulpe.view.request.globalsAfterJs, v_layerFields);
			},

			uploadAll: function(v_files, v_formName, v_layerFields, v_queryString, v_layer, v_validate, v_beforeJs, v_afterJs) {
				jQuery.uploadFiles({
					files: v_files,
					formName: v_formName,
					layerFields: v_layerFields,
					queryString: v_queryString,
					layer: v_layer,
					validate: v_validate,
					beforeJs: v_beforeJs,
					afterJs: v_afterJs
				});
			},

			submitPaging: function(page, actionURL, formName, layerFields, layer, beforeJs, afterJs) {
				vulpe.view.resetFields(formName);
				vulpe.util.getElement(formName + _vulpePagingPage).value = page;
				vulpe.view.request.submitFormAction(actionURL, formName, layerFields, '', layer, false, beforeJs, afterJs);
			},

			submitView: function(id, actionURL, formName, layerFields, layer, beforeJs, afterJs) {
				if (_vulpeViewIsSelection) {
					return false;
				}
				vulpe.util.getElement(formName + _vulpeIdentifier).value = id;
				paging = vulpe.util.getElement(formName + _vulpePagingPage);
				if (paging) {
					vulpe.util.getElement(formName + _vulpePagingPage).value = 0;
				}
				vulpe.view.request.submitFormAction(actionURL, formName, layerFields, '', layer, false, beforeJs, afterJs);
			},

			submitUpdate: function(id, actionURL, formName, layerFields, layer, beforeJs, afterJs) {
				if (_vulpeViewIsSelection) {
					return false;
				}
				vulpe.view.resetFields(formName);
				vulpe.util.getElement(formName + _vulpeIdentifier).value = id;
				vulpe.view.request.submitFormAction(actionURL, formName, layerFields, '', layer, false, beforeJs, afterJs);
			},

			submitDelete: function(id, actionURL, formName, layerFields, layer, beforeJs, afterJs) {
				vulpe.view.resetFields(formName);
				vulpe.util.getElement(formName + _vulpeIdentifier).value = id;
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
					$(_vulpeConfirmationMessage).html(_vulpeMsgSelectedExclusion);
					$(_vulpeConfirmationDialog).dialog({
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
								vulpe.view.request.submitFormAction(actionURL, formName, layerFields, 'detail=' + (detail == 'entities' ? detail : 'entity.' + detail), layer, false, beforeJs, afterJs);
							},
							Cancel: function() {
								$(this).dialog('close');
							}
						}
					});
					$(_vulpeConfirmationDialog).dialog('open');
				} else {
					$(_vulpeAlertMessage).html(_vulpeMsgSelectRecordsExclusion);
					$(_vulpeAlertDialog).dialog('open');
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
					$(_vulpeConfirmationMessage).html(_vulpeMsgSelectedExclusion);
					$(_vulpeConfirmationDialog).dialog({
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
								vulpe.view.resetFields(formName);
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
					$(_vulpeConfirmationDialog).dialog('open');
				} else {
					//alert(_vulpeMsgSelectRecordsExclusion);
					$(_vulpeAlertMessage).html(_vulpeMsgSelectRecordsExclusion);
					$(_vulpeAlertDialog).dialog('open');
				}
			},

			submitFormAction: function(actionURL, formName, layerFields, queryString, layer, validate, beforeJs, afterJs) {
				if (actionURL.indexOf("Post") != -1 || actionURL.indexOf("read") != -1) {
					if (!vulpe.validate.validateAttributes(formName)) {
						return false;
					}
				}
				var form = vulpe.util.getElement(formName);
				if (actionURL.indexOf(_vulpeContextPath) == -1 && actionURL.indexOf(_vulpeActionSufix) == -1) {
					actionURL = _vulpeContextPath + '/' + actionURL + _vulpeActionSufix;
				}
				form.action = actionURL;
				vulpe.view.request.submitForm(formName, layerFields, queryString, layer, validate, beforeJs, afterJs, false);
			},

			submitLoginForm: function(formName, layerFields, queryString, layer, validate, beforeJs, afterJs) {
				var form = vulpe.util.getElement(formName);
				if (vulpe.validate.validateLoginForm(formName)) {
					form.action = _vulpeContextPath + '/' + _vulpeSpringSecurityCheck;
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
				jQuery(_vulpeMessages).hide();
				// verifier if exists validations before submit
				if (!vulpe.view.request.submitBefore(beforeJs))
					return false;
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

					jQuery("." + _vulpeFieldError, vulpe.util.get(layerFields)).each(function (i) {
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
				jQuery(_vulpeMessages).hide();
				return vulpe.view.request.submitPage(_vulpeContextPath + url, '', 'body', beforeJs, afterJs);
			},

			submitPopup: function(url, queryString, popupName, popupLayerParent, paramLayerParent, popupProperties, popupExpressions, paramProperties, paramExpressions, requiredParamProperties, requiredParamExpressions, styleClass, beforeJs, afterJs, popupWidth) {
				vulpe.util.setVulpePopup(popupName);
				var popup = jQuery('<div>').attr('id', popupName).attr('layerParent', popupLayerParent).attr('popupProperties', popupProperties).attr('popupExpressions', popupExpressions);
				popup.hide();
				popup.css("height", "100%");
				popup.addClass('popup');
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
				if (!vulpe.view.request.submitPage(_vulpeContextPath + url, queryString, popupName, beforeJs, afterJs)) {
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
						throw name + _vulpeMsgFieldRequired;
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
						throw name + _vulpeMsgFieldRequired;
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
						_vulpeShowLoading = true;
						if (data.indexOf('<!--IS_EXCEPTION-->') != -1) {
							vulpe.exception.handlerError(data, status);
						//} else if (data.indexOf('j_username') != -1) {
						//	vulpe.view.redirectLogin();
						} else {
							try {
								var html = "";
								if (vulpe.util.existsVulpePopups(layer)) {
									var msgPopup = "<div id=\"messagesPopup_" + layer + "\" style=\"display: none;\" class=\"messages\"></div>";
									html = msgPopup + data;
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
				form.action = _vulpeContextPath + uri;
				var layerFields = formName;

				if (individualLoading) {
					_vulpeShowLoading = false;
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

	exception: { // vulpe.exception
		focusFirstError: function(layerFields) {
			var fields = jQuery("." + _vulpeFieldError, vulpe.util.get(layerFields));
			if (fields && fields.length > 0) {
				for (var i = 0; i < fields.length; i++) {
					var field = jQuery(fields[i]);
					if (field.attr("className").indexOf("hasDatepicker") == -1) {
						field.focus();
						break;
					}
				}
			}
		},

		setupError: function(fieldName, msgs) {
			vulpe.util.get(fieldName + _vulpeErrorSufix).show();
			var msgSuffix = fieldName + _vulpeErrorMsgSufix;
			vulpe.util.get(msgSuffix).attr("title", msgs);
			vulpe.util.get(fieldName).addClass(_vulpeFieldError);
			vulpe.util.get(msgSuffix).tooltip({
				opacity: 0.7,
				effect: 'slide'
			}).dynamic({
				bottom: { 
					direction: 'down', 
					bounce: true 
				}
			});
			//vulpe.util.get(msgSuffix).tooltip({opacity: 0.7});
			/*
			vulpe.util.get(fieldName).attr("title", msgs);
			vulpe.util.get(fieldName).tooltip({
				events: {
				input:"mouseover,mouseout"
				},
				opacity: 0.7
				}
			);
			*/

			var errorController = function () {
				if (this.value.length == 0) {
					vulpe.exception.showFieldError(this);
				} else {
					vulpe.exception.hideFieldError(this);
				}
			}
			vulpe.util.get(fieldName).change(errorController);
			vulpe.util.get(fieldName).blur(errorController);
			vulpe.util.get(fieldName).keydown(errorController);
			vulpe.util.get(fieldName).keyup(errorController);
		},

		showFieldError: function(element) {
			if (element) {
				jQuery(element).addClass(_vulpeFieldError);
				var error = vulpe.util.get(element.id + _vulpeErrorSufix);
				if (error) {
					error.show();
				}
			}
		},

		hideFieldError: function(element) {
			if (element) {
				jQuery(element).removeClass(_vulpeFieldError);
				var error = vulpe.util.get(element.id + _vulpeErrorSufix);
				if (error) {
					error.hide();
				}
			}
		},

		hideError: function(element) {
			jQuery(element).removeClass(_vulpeFieldError);
			vulpe.util.get(element.id + _vulpeErrorSufix).hide();
		},

		handlerError: function(data, status, e) {
			if (typeof e == "undefined") {
				if (typeof data != "string" && data.responseText) {
					data = data.responseText;
				}
				if (data.indexOf("\"alertError\"") != -1) {
					jQuery(_vulpeMessages).html(data);
				} else {
					jQuery(_vulpeModalMessages).html(data);
				}
			} else {
				jQuery(_vulpeMessages).html("Erro fatal: " + e);
			}
			if (data.indexOf("\"alertError\"") == -1) {
				jQuery(_vulpeModalMessages).removeClass("success");
				jQuery(_vulpeModalMessages).removeClass("validation");
				jQuery(_vulpeModalMessages).addClass("error");
				vulpe.view.showMessages();
			}
		},

		showError: function(config) {
			vulpe.util.getElement(config.form + '_' + config.name + _vulpeErrorSufix).style.display = 'inline';
			if (vulpe.util.getElement(config.form + '_' + config.name + _vulpeErrorMsgSufix).innerHTML.length == 9) {
				vulpe.util.getElement(config.form + '_' + config.name + _vulpeErrorMsgSufix).innerHTML = vulpe.util.getElement(config.form + '_' + config.name + _vulpeErrorMsgSufix).innerHTML + config.msg;
			} else {
				vulpe.util.getElement(config.form + '_' + config.name + _vulpeErrorMsgSufix).innerHTML = vulpe.util.getElement(config.form + '_' + config.name + _vulpeErrorMsgSufix).innerHTML + '<br>' + config.msg;
			}
		},

		prepareError: function(form, name) {
			vulpe.util.getElement(form + '_' + name + _vulpeErrorSufix).style.display = 'none';
			vulpe.util.getElement(form + '_' + name + _vulpeErrorMsgSufix).innerHTML = '<em></em>';
		}
	}
};
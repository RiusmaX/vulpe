/**
 * Vulpe Framework - Copyright (c) Active Thread
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
package org.vulpe.commons;

import java.util.Locale;

/**
 * Vulpe Constants
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeConstants {

	public static final String MAPPING_REQUEST = "MAPPING_REQUEST";
	public static final String FORM_REQUEST = "FORM_REQUEST";
	public static final Locale ENGLISH_LOCALE = new Locale("en", "US");
	public static final Locale PORTUGUESE_LOCALE = new Locale("pt", "BR");
	public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";
	public static final String STRING_NULL = "";
	public static final String LITERAL_EMPTY_STRING = "0";
	public static final String LITERAL_POINT = ".";
	public static final String LITERAL_SIMPLE_SPACE = " ";
	public static final String SELECT_OPTION_BLANK = "";
	public static final String IS_EXCEPTION = "IS_EXCEPTION";
	public static final String AJAX = "ajax";
	public static final String VULPE_SHOW_ACTIONS = "vulpeShowActions";
	public static final String VULPE_SHOW_MESSAGES = "vulpeShowMessages";
	public static final String GENERAL_ERROR = "vulpe.error.general";
	public static final String CLEAR_PARAMS = "vulpe.clearParams";
	public static final String PARAMS_SESSION_KEY = "_params";
	public static final String CACHED_CLASSES = "vulpeCachedClasses";
	public static final String CACHED_ENUMS = "vulpeCachedEnums";
	public static final String CACHED_ENUMS_ARRAY = "vulpeCachedEnumsArray";
	public static final String CONTROLLER_METHODS = "vulpeControllerMethods";
	public static final String VULPE = "vulpe";
	public static final String VULPE_SECURITY = "vulpe-security";
	public static final String DOMAINS = "domains";
	public static final String COMMONS = "commons";
	public static final String CONTROLLER = "controller";
	public static final String CONTEXT = "vulpeContext";
	public static final String CURRENT_SERVLET_CONTEXT = "currentServletContext";
	public static final String FILTER_DISPATCHER = "vulpeFilterDispatcher";

	public class Context {
		public static final String APPLICATION_SCOPE = "APPLICATION_SCOPE";
		public static final String PAGE_SCOPE = "PAGE_SCOPE";
		public static final String REQUEST_SCOPE = "REQUEST_SCOPE";
		public static final String SESSION_SCOPE = "SESSION_SCOPE";
		public static final String GLOBAL = "global";
	}

	public class Configuration {
		public class Global {
			public static final String USE_DB4O = "useDB4O";
			public static final String AUDIT_ENABLED = "auditEnabled";
			public static final String SECURITY_ENABLED = "securityEnabled";
			public static final String I18N_MANAGER = "i18nManager";
			public static final String THEME = "theme";
			public static final String FRONTEND_MENU_TYPE = "frontendMenuType";
			public static final String BACKEND_MENU_TYPE = "backendMenuType";
			public static final String BACKEND_CENTERED_LAYOUT = "backendCenteredLayout";
			public static final String FOCUS_FIRST = "focusFirst";
			public static final String FRONTEND_CENTERED_LAYOUT = "frontendCenteredLayout";
			public static final String SHOW_BUTTONS_AS_IMAGE = "showButtonsAsImage";
			public static final String SHOW_ICON_OF_BUTTON = "showIconOfButton";
			public static final String SHOW_TEXT_OF_BUTTON = "showTextOfButton";
			public static final String ICON_WIDTH = "iconWidth";
			public static final String MOBILE_ICON_WIDTH = "mobileIconWidth";
			public static final String ICON_HEIGHT = "iconHeight";
			public static final String MOBILE_ICON_HEIGHT = "mobileIconHeight";
			public static final String MESSAGE_SLIDE_UP = "messageSlideUp";
			public static final String MESSAGE_SLIDE_UP_TIME = "messageSlideUpTime";
			public static final String BREAK_LABEL = "breakLabel";
			public static final String SHOW_BUTTON_DELETE_THIS = "showButtonDeleteThis";
			public static final String SHOW_BUTTON_UPDATE = "showButtonUpdate";
			public static final String SHOW_BUTTONS_DELETE = "showButtonsDelete";
			public static final String SHOW_LINE = "showLine";
			public static final String SHOW_COPYRIGHT = "showCopyright";
			public static final String SHOW_POWERED_BY = "showPoweredBy";
			public static final String SHOW_WARNING_BEFORE_CLEAR = "showWarningBeforeClear";
			public static final String SHOW_WARNING_BEFORE_DELETE = "showWarningBeforeDelete";
			public static final String SHOW_WARNING_BEFORE_UPDATE_POST = "showWarningBeforeUpdatePost";
			public static final String SHOW_MODIFICATION_WARNING = "showModificationWarning";
			public static final String PAGING_STYLE = "pagingStyle";
			public static final String PAGING_BUTTON_STYLE = "pagingButtonStyle";
			public static final String USE_FRONTEND_LAYER = "useFrontendLayer";
			public static final String USE_BACKEND_LAYER = "useBackendLayer";

			public static final String SHOW_AS_MOBILE = "showAsMobile";

			public class Mobile {
				public static final String VIEWPORT_WIDHT = "viewportWidth";
				public static final String VIEWPORT_HEIGHT = "viewportHeight";
				public static final String VIEWPORT_USER_SCALABLE = "viewportUserScalable";
				public static final String VIEWPORT_INITIAL_SCALE = "viewportInitialScale";
				public static final String VIEWPORT_MAXIMUM_SCALE = "viewportMaximumScale";
				public static final String VIEWPORT_MINIMUM_SCALE = "viewportMinimumScale";
			}
		}

		public class Now {
			public static final String SHOW_CONTENT_TITLE = "showContentTitle";
			public static final String CONTENT_TITLE = "contentTitle";
			public static final String SHOW_CONTENT_SUBTITLE = "showContentSubtitle";
			public static final String CONTENT_SUBTITLE = "contentSubtitle";
			public static final String SYSTEM_DATE = "systemDate";
			public static final String CURRENT_YEAR = "currentYear";
			public static final String CURRENT_MONTH = "currentMonth";
			public static final String CURRENT_DAY = "currentDay";
			public static final String CONTROLLER_TYPE = "controllerType";
			public static final String MASTER_TITLE_KEY = "masterTitleKey";
			public static final String TITLE_KEY = "titleKey";
			public static final String FORM_NAME = "formName";
			public static final String FIELD_TO_FOCUS = "fieldToFocus";
			public static final String REQUIRE_ONE_FILTER = "requireOneFilter";
		}

		public class Ever {
			public static final String CURRENT_CONTROLLER_KEY = "currentControllerKey";
			public static final String CURRENT_PAGE = "currentPage";
			public static final String TABULAR_SIZE = "tabularSize";
		}
	}

	public class Error {
		public static final String CONTROLLER = "vulpe.error.controller";
	}

	public class Upload {
		public static final String FILES = "org.vulpe.controller.struts.interceptor.VulpeUploadInterceptor.files";

		public class Image {
			public static final String THUMB = "_thumb";
		}
	}

	public class Controller {
		public static final String ENTITIES = "entities";
		public static final String SELECT_FORM = "_selectForm";
		public static final String SELECT_TABLE = "_selectTable";
		public static final String SELECT_PAGING = "_selectPaging";

		public static final String REPORT_COLLECTION = "reportCollection";
		public static final String REPORT_PARAMETERS = "reportParameters";
		public static final String TABS = "tabs";

		public class Button {
			public static final String CREATE = "create";
			public static final String CREATE_POST = "createPost";
			public static final String DELETE = "delete";
			public static final String UPDATE = "update";
			public static final String UPDATE_POST = "updatePost";
			public static final String PREPARE = "prepare";
			public static final String READ = "read";
			public static final String REPORT = "report";
			public static final String CLEAR = "clear";
			public static final String TABULAR_RELOAD = "tabularReload";
			public static final String TABULAR_POST = "tabularPost";
			public static final String TABULAR_FILTER = "tabularFilter";
			public static final String ADD_DETAIL = "addDetail";
		}

		public class Validate {
			public class Cardinality {
				public static final String ZERO = "0";
				public static final String ZERO_OR_MORE = "0..*";
				public static final String ONE = "1";
				public static final String ONE_OR_MORE = "1..*";
			}
		}

		public class URI {
			public static final String AUTHENTICATOR = "/authenticator";
			public static final String SPRING_SECURITY = "/j_spring_security_check";
			public static final String AJAX = "/ajax";
			public static final String CREATE = "/create";
			public static final String CREATE_AJAX = "/create/ajax";
			public static final String CREATE_POST = "/createPost";
			public static final String UPDATE = "/update";
			public static final String UPDATE_AJAX = "/update/ajax";
			public static final String VIEW = "/view";
			public static final String VIEW_AJAX = "/view/ajax";
			public static final String UPDATE_POST = "/updatePost";
			public static final String UPDATE_POST_AJAX = "/updatePost/ajax";
			public static final String DELETE = "/delete";
			public static final String DELETE_AJAX = "/delete/ajax";
			public static final String READ = "/read";
			public static final String READ_AJAX = "/read/ajax";
			public static final String PAGING = "/paging";
			public static final String PAGING_AJAX = "/paging/ajax";
			public static final String PREPARE = "/prepare";
			public static final String PREPARE_AJAX = "/prepare/ajax";
			public static final String ADD_DETAIL = "/addDetail";
			public static final String TABULAR_POST = "/tabularPost";
			public static final String FIND = "/find";
			public static final String PERSIST = "/persist";
			public static final String UPLOAD = "/upload";
			public static final String DOWNLOAD = "/download";
		}

		public class Forward {
			public static final String SUCCESS = "success";
			public static final String JSON = "json";
			public static final String ACCESS_DENIED = "accessDenied";
			public static final String REPORT = "report";
			public static final String CREATE = "create";
			public static final String UPDATE = "update";
			public static final String READ = "read";
			public static final String BACKEND = "backend";
			public static final String FRONTEND = "frontend";
			public static final String UPLOAD = "upload";
			public static final String DOWNLOAD = "download";

			public static final String ERRORS = "errors";
			public static final String MESSAGES = "messages";
			public static final String REDIRECT = "redirect";
		}

		public class Shortcut {
			public static final String CLEAN = "vulpeShortcutActionClean";
			public static final String CREATE = "vulpeShortcutActionCreate";
			public static final String CREATE_POST = "vulpeShortcutActionCreatePost";
			public static final String PREPARE = "vulpeShortcutActionPrepare";
			public static final String READ = "vulpeShortcutActionRead";
			public static final String REPORT = "vulpeShortcutActionReport";
			public static final String UPDATE = "vulpeShortcutActionUpdate";
			public static final String UPDATE_POST = "vulpeShortcutActionUpdatePost";
		}
	}

	public class View {
		public static final String VULPE_LOCALE = "vulpeLocale";
		public static final String VULPE_I18N_SERVICE = "vulpeI8NService";
		public static final String LAYER_URL_BACK = "layerUrlBack";
		public static final String URL_BACK = "urlBack";
		public static final String LABEL = "label.";
		public static final String MASTER = ".master";
		public static final String ENUM = ".enum.";
		public static final String LAYOUT = "layout.";
		public static final String AUTHENTICATOR = "authenticator";
		public static final String CURRENT_LAYOUT = "vulpeCurrentLayout";

		public class Layout {
			public static final String MAIN = ".main";
			public static final String BACKEND = "backend";
			public static final String FRONTEND = "frontend";
			public static final String PROTECTED_JSP = "/WEB-INF/protected-jsp/";
			public static final String PROTECTED_JSP_COMMONS = PROTECTED_JSP + "commons/";
			public static final String PROTECTED_JSP_FRONTEND = PROTECTED_JSP + "frontend/";
			public static final String PROTECTED_JSP_BACKEND = PROTECTED_JSP + "backend/";
			public static final String IMAGES_CONTEXT = "/images/";
			public static final String JS_CONTEXT = "/js/";
			public static final String CSS_CONTEXT = "/css/";
			public static final String THEMES_CONTEXT = "/themes/";
			public static final String SUFFIX_FORM = "Form";
			public static final String BODY_TWICE = "vulpeBodyTwice";
			public static final String BODY_TWICE_TYPE = "vulpeBodyTwiceType";
			public static final String SUFFIX_JSP = ".jsp";
			public static final String SUFFIX_JSP_TABULAR = "Tabular.jsp";
			public static final String SUFFIX_JSP_OTHER = ".jsp";
			public static final String SUFFIX_JSP_FRONTEND = ".jsp";
			public static final String SUFFIX_JSP_DETAIL = "Detail.jsp";
			public static final String SUFFIX_JSP_SELECT = "Select.jsp";
			public static final String SUFFIX_JSP_SELECT_ITEMS = "SelectItems.jsp";
			public static final String SUFFIX_JSP_REPORT = "Report.jsp";
			public static final String SUFFIX_JSP_REPORT_ITEMS = "ReportItems.jsp";
			public static final String SUFFIX_JSP_MAIN = "Main.jsp";

			public static final String BODY_TWICE_JSP = "bodyTwice.jsp";
			public static final String BODY_JSP = "body.jsp";
			public static final String BODY_MAIN_JSP = "main.jsp";
			public static final String BODY_MAIN_ACTIONS_JSP = "mainActions.jsp";
			public static final String BODY_SELECT_JSP = "select.jsp";
			public static final String BODY_SELECT_ACTIONS_JSP = "selectActions.jsp";
			public static final String BODY_TABULAR_JSP = "tabular.jsp";
			public static final String BODY_TABULAR_ACTIONS_JSP = "tabularActions.jsp";

			public static final String TARGET_CONFIG = "targetConfig";
			public static final String TARGET_CONFIG_PROPERTY_NAME = "targetConfigPropertyName";
			public static final String DETAIL_ITEM = "_item";
		}

		public class Logic {
			public static final String BACKEND = "backend/";
			public static final String FRONTEND = "frontend/";
			public static final String REPORT = "/report";
			public static final String SELECT = "/select";
			public static final String TABULAR = "/tabular";
			public static final String AJAX = "/ajax";
		}

		public class Report {
			public static final String PATH = "/WEB-INF/reports/";
			public static final String JASPER = ".jasper";
		}
	}

	public class Security {
		public static final String USER_AUTHENTICATION = "vulpeUserAuthentication";
		public static final String USER_AUTHENTICATED = "vulpeUserAuthenticated";
		public static final String LOGOUT_EXECUTED = "vulpeLogoutExecuted";
		public static final String ROLE_PREFIX = "ROLE_";
		public static final String SECURITY_CONTEXT = "VulpeSecurityContext";
		public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
		public static final String AFTER_USER_AUTHENTICATION_CALLBACK = "AfterUserAuthenticationCallback";
	}

	public class Model {
		public class Entity {
			public static final String QUERY_CONFIGURATION_NAME = "queryConfigurationName";
			public static final String ONLY_UPDATE_DETAILS = "onlyUpdateDetails";
		}

		public class DAO {
			public class DB4O {
				public static final String DATABASE_NAME = "vulpe.fox";
				public static final String DATABASE_DIRECTORY = "/db4o/data";
				public static final int DATABASE_PORT = 7777;
				public static final String STATUS = "status";
				public static final String SELECTED = "selected";
			}

			public class JPA {
			}
		}

		public class Services {
		}
	}

	public class Code {
		public class Generator {
			public static final String ABSTRACT_PREFIX = "Abstract";
			public static final String ENTITY_PACKAGE = ".model.entity";
			public static final String CONTROLLER_PACKAGE = ".controller";
			public static final String CONTROLLER_SUFFIX = "Controller";
			public static final String DAO_PACKAGE = ".model.dao";
			public static final String DAO_SUFFIX = "DAO";
			public static final String MANAGER_PACKAGE = ".model.manager";
			public static final String MANAGER_SUFFIX = "Manager";
			public static final String SERVICE_PACKAGE = ".model.services";
			public static final String SERVICE_SUFFIX = "Service";
		}
	}

}
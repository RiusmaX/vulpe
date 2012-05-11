/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * 
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.commons;


/**
 * Vulpe Constants
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeConstants {

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
	public static final String SERVLET_CONTEXT = "servletContext";
	public static final String FILTER_DISPATCHER = "vulpeFilterDispatcher";

	public class Context {
		public static final String APPLICATION_SCOPE = "APPLICATION_SCOPE";
		public static final String PAGE_SCOPE = "PAGE_SCOPE";
		public static final String REQUEST_SCOPE = "REQUEST_SCOPE";
		public static final String SESSION_SCOPE = "SESSION_SCOPE";
		public static final String GLOBAL = "global";
	}

	public class Request {
		public static final String NOW = "now";
	}

	public class Session {
		public static final String EVER = "ever";
	}

	public class Configuration {
		public class Global {
			//VulpeApplication
			public static final String APPLICATION_DEBUG = "application-debug";
			public static final String APPLICATION_USE_DB4O = "application-useDB4O";
			public static final String APPLICATION_AUDIT = "application-audit";
			public static final String APPLICATION_SECURITY = "application-security";
			public static final String APPLICATION_I18N_MANAGER = "application-i18nManager";
			public static final String APPLICATION_THEME = "application-theme";
			public static final String APPLICATION_DATE_PATTERN = "application-datePattern";
			public static final String APPLICATION_DATE_TIME_PATTERN = "application-dateTimePattern";
			public static final String APPLICATION_LOCALE_CODE = "application-localeCode";
			public static final String APPLICATION_HOT_KEYS = "application-hotKeys";

			//VulpeView
			public static final String APPLICATION_VIEW_DATE_MASK = "application-view-dateMask";
			public static final String APPLICATION_VIEW_JQUERYUI = "application-view-jQueryUI";
			public static final String APPLICATION_VIEW_FRONTEND_MENU_TYPE = "application-view-frontendMenuType";
			public static final String APPLICATION_VIEW_BACKEND_MENU_TYPE = "application-view-backendMenuType";
			public static final String APPLICATION_VIEW_FOCUS_FIRST = "application-view-focusFirst";
			public static final String APPLICATION_VIEW_SHOW_ICON_ERROR_MESSAGE = "application-view-showIconErrorMessage";
			public static final String APPLICATION_VIEW_SHOW_BUTTONS_AS_IMAGE = "application-view-showButtonsAsImage";
			public static final String APPLICATION_VIEW_SHOW_ICON_OF_BUTTON = "application-view-showIconOfButton";
			public static final String APPLICATION_VIEW_SHOW_TEXT_OF_BUTTON = "application-view-showTextOfButton";
			public static final String APPLICATION_VIEW_ICON_WIDTH = "application-view-iconWidth";
			public static final String APPLICATION_VIEW_ICON_HEIGHT = "application-view-iconHeight";
			public static final String APPLICATION_VIEW_MESSAGE_SLIDE_UP = "application-view-messageSlideUp";
			public static final String APPLICATION_VIEW_MESSAGE_SLIDE_UP_TIME = "application-view-messageSlideUpTime";
			public static final String APPLICATION_VIEW_BREAK_LABEL = "application-view-breakLabel";
			public static final String APPLICATION_VIEW_SHOW_BUTTON_DELETE_THIS = "application-view-showButtonDeleteThis";
			public static final String APPLICATION_VIEW_SHOW_BUTTON_UPDATE = "application-view-showButtonUpdate";
			public static final String APPLICATION_VIEW_SHOW_BUTTONS_DELETE = "application-view-showButtonsDelete";
			public static final String APPLICATION_VIEW_SHOW_ROWS = "application-view-showRows";
			public static final String APPLICATION_VIEW_SHOW_COPYRIGHT = "application-view-showCopyright";
			public static final String APPLICATION_VIEW_SHOW_REPORT_IN_NEW_WINDOW = "application-view-showReportInNewWindow";
			public static final String APPLICATION_VIEW_SHOW_POWERED_BY = "application-view-showPoweredBy";
			public static final String APPLICATION_VIEW_SHOW_WARNING_BEFORE_CLEAR = "application-view-showWarningBeforeClear";
			public static final String APPLICATION_VIEW_SHOW_WARNING_BEFORE_DELETE = "application-view-showWarningBeforeDelete";
			public static final String APPLICATION_VIEW_SHOW_WARNING_BEFORE_UPDATE_POST = "application-view-showWarningBeforeUpdatePost";
			public static final String APPLICATION_VIEW_SHOW_MODIFICATION_WARNING = "application-view-showModificationWarning";
			public static final String APPLICATION_VIEW_LAYOUT_SHOW_SLIDER_PANEL = "application-view-layout-showSliderPanel";
			public static final String APPLICATION_VIEW_LAYOUT_SHOW_SLIDER_PANEL_ONLY_IF_AUTHENTICATED = "application-view-layout-showSliderPanelOnlyIfAuthenticated";
			public static final String APPLICATION_VIEW_LAYOUT_SHOW_LOADING_AS_MODAL = "application-view-layout-showLoadingAsModal";
			public static final String APPLICATION_VIEW_LAYOUT_SHOW_DISPLAY_SPECIFIC_MESSAGES_WHEN_LOADING = "application-view-layout-displaySpecificMessagesWhenLoading";
			public static final String APPLICATION_VIEW_SORT_TYPE = "application-view-sortType";
			public static final String APPLICATION_VIEW_PAGING_STYLE = "application-view-paging-style";
			public static final String APPLICATION_VIEW_PAGING_BUTTON_STYLE = "application-view-paging-buttonStyle";
			public static final String APPLICATION_VIEW_PAGING_SHOW_BUTTON_AS_LINK = "application-view-paging-showButtonAsLink";
			public static final String APPLICATION_VIEW_USE_FRONTEND_LAYER = "application-view-useFrontendLayer";
			public static final String APPLICATION_VIEW_USE_BACKEND_LAYER = "application-view-useBackendLayer";
			public static final String APPLICATION_VIEW_SESSION_IDLE_TIME = "application-view-session-idleTime";
			public static final String APPLICATION_VIEW_SESSION_TIME = "application-view-session-time";
			public static final String APPLICATION_VIEW_SESSION_REDIRECT_AFTER = "application-view-session-redirectAfter";
			public static final String APPLICATION_VIEW_SESSION_REDIRECT_TO = "application-view-session-redirectTo";
			public static final String APPLICATION_VIEW_SESSION_KEEP_ALIVE_URL = "application-view-session-keepAliveURL";

			//VulpeMobile
			public static final String APPLICATION_MOBILE_ENABLED = "application-mobile-enabled";
			public static final String APPLICATION_MOBILE_ICON_WIDTH = "application-mobile-iconWidth";
			public static final String APPLICATION_MOBILE_ICON_HEIGHT = "application-view-iconHeight";
			public static final String APPLICATION_MOBILE_VIEWPORT_WIDHT = "application-mobile-viewportWidth";
			public static final String APPLICATION_MOBILE_VIEWPORT_HEIGHT = "application-mobile-viewportHeight";
			public static final String APPLICATION_MOBILE_VIEWPORT_USER_SCALABLE = "application-mobile-viewportUserScalable";
			public static final String APPLICATION_MOBILE_VIEWPORT_INITIAL_SCALE = "application-mobile-viewportInitialScale";
			public static final String APPLICATION_MOBILE_VIEWPORT_MAXIMUM_SCALE = "application-mobile-viewportMaximumScale";
			public static final String APPLICATION_MOBILE_VIEWPORT_MINIMUM_SCALE = "application-mobile-viewportMinimumScale";
		}

		public class Now {
			//Commons
			public static final String SYSTEM_DATE = "systemDate";
			public static final String CURRENT_YEAR = "currentYear";
			public static final String CURRENT_MONTH = "currentMonth";
			public static final String CURRENT_DAY = "currentDay";
			public static final String CACHED_CLASSES = "cachedClasses";
			public static final String CACHED_ENUMS = "cachedEnums";
			public static final String CACHED_ENUMS_ARRAY = "cachedEnumsArray";

			// View
			public static final String BUTTONS = "buttons";
			public static final String CONTENT_TITLE = "contentTitle";
			public static final String CONTENT_SUBTITLE = "contentSubtitle";
			public static final String FORM_NAME = "formName";
			public static final String FOCUS_TO_FIELD = "focusToField";
			public static final String HOOKS = "hooks";
			public static final String MASTER_TITLE_KEY = "masterTitleKey";
			public static final String NO_CACHE = "noCache";
			public static final String SHOW_CONTENT_TITLE = "showContentTitle";
			public static final String SHOW_CONTENT_SUBTITLE = "showContentSubtitle";
			public static final String REQUIRE_ONE_FILTER = "requireOneFilter";
			public static final String REPORT_TITLE_KEY = "reportTitleKey";
			public static final String TARGET_NAME = "targetName";
			public static final String TARGET_CONFIG = "targetConfig";
			public static final String TARGET_CONFIG_PROPERTY_NAME = "targetConfigPropertyName";
			public static final String TITLE_KEY = "titleKey";

			// Controller
			public static final String CURRENT_METHOD_NAME = "currentMethodName";
			public static final String CONTROLLER_CONFIG = "controllerConfig";
			public static final String CONTROLLER_TYPE = "controllerType";
			public static final String AJAX = "ajax";
			public static final String BACK = "back";
			public static final String CLEANED = "cleaned";
			public static final String DELETED = "deleted";
			public static final String DETAIL = "detail";
			public static final String DETAIL_INDEX = "detailIndex";
			public static final String DETAIL_LAYER = "detailLayer";
			public static final String DOWNLOAD_INFO = "downloadInfo";
			public static final String DOWNLOAD_KEY = "downloadKey";
			public static final String DOWNLOAD_CONTENT_DISPOSITION = "downloadContentDisposition";
			public static final String DOWNLOAD_CONTENT_TYPE = "downloadContentType";
			public static final String EXECUTED = "executed";
			public static final String EXPORTED = "exported";
			public static final String JSON_ROOT = "jsonRoot";
			public static final String ONLY_TO_SEE = "onlyToSee";
			public static final String OPERATION = "operation";
			public static final String POPUP = "popup";
			public static final String POPUP_KEY = "popupKey";
			public static final String PROPERTY_NAME = "propertyName";
			public static final String REPORT_FORMAT = "reportFormat";
			public static final String RESULT_FORWARD = "resultForward";
			public static final String RESULT_NAME = "resultName";
			public static final String SELECTED_TAB = "selectedTab";
			public static final String TABULAR_FILTER = "tabularFilter";
			public static final String UPLOADED = "uploaded";
			public static final String URL_TO_REDIRECT = "urlToRedirect";
		}

		public class Ever {
			public static final String CURRENT_CONTROLLER_KEY = "currentControllerKey";
			public static final String CURRENT_CONTROLLER_NAME = "currentControllerName";
			public static final String CURRENT_HTML_CONTENT = "currentControllerName";
			public static final String EXPORT_CONTENT = "exportContent";
			public static final String CURRENT_PAGE = "currentPage";
			public static final String TABULAR_SIZE = "tabularSize";
			public static final String DEBUG = "debug";
			public static final String MAX_INACTIVE_INTERVAL = "maxInactiveInterval";
		}
	}

	public class Error {
		public static final String CONTROLLER = "vulpe.error.controller";
	}

	public class Upload {
		public static final String FILES = "org.vulpe.controller.struts.interceptor.VulpeUploadInterceptor.files";

		public class File {
			public static final String SUFFIX_CONTENT_TYPE = "ContentType";
			public static final String SUFFIX_FILE_NAME = "FileName";
		}
		
		public class Image {
			public static final String THUMB = "_thumb";
		}
	}

	public class Controller {
		public static final String ENTITY_BEFORE_UPDATE = "entityBeforeUpdate";
		public static final String ENTITIES = "entities";
		public static final String SELECT_FORM = "_selectForm";
		public static final String SELECT_TABLE = "_selectTable";
		public static final String SELECT_PAGING = "_selectPaging";
		public static final String DETAIL_PAGING_LIST = "_pagingList";
		public static final String VIRTUAL_PAGING = "virtualPaging";

		public static final String REPORT_COLLECTION = "reportCollection";
		public static final String REPORT_PARAMETERS = "reportParameters";
		public static final String TABS = "tabs";

		public class Button {
			public static final String CREATE = "create";
			public static final String CREATE_POST = "createPost";
			public static final String CLONE = "clone";
			public static final String DELETE = "delete";
			public static final String UPDATE = "update";
			public static final String UPDATE_POST = "updatePost";
			public static final String BACK = "back";
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

		public class Result {
			public static final String SUCCESS = "success";
			public static final String PLAIN_TEXT = "plaintext";
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
			public static final String EXPORT = "export";

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
			public static final String BODY_TWICE = "bodyTwice";
			public static final String BODY_TWICE_TYPE = "bodyTwiceType";
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
			public static final String PATH = "/reports/";
			public static final String JASPER = ".jasper";
		}
		
		public class Struts {
			public static final String XWORK_CONVERTER = "xworkConverter";
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
			public static final String AUTOCOMPLETE = "autocomplete";
			public static final String AUTOCOMPLETE_TERM = "autocompleteTerm";
			public static final String DELETED_DETAILS = "deletedDetails";
			public static final String FAKE_ID = "fakeId";
			public static final String SELECTED = "selected";
			public static final String USED = "used";
			public static final String CONDITIONAL = "conditional";
			public static final String ROW_NUMBER = "rowNumber";
			public static final String QUERY_CONFIGURATION_NAME = "queryConfigurationName";
			public static final String FIELD_COLUMN_MAP = "fieldColumnMap";
			public static final String ONLY_UPDATE_DETAILS = "onlyUpdateDetails";
			public static final String ORDER_BY = "orderBy";
			public static final String UNPROXYFIED = "unproxyfied";
			public static final String BEFORE_UPDATE = "beforeUpdate";

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
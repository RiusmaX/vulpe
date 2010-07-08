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
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
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
	public static final String JS_REDIRECT_LOGIN = "vulpe.view.redirectLogin()";
	public static final String AJAX = "ajax";
	public static final String ON_HIDE_MESSAGES = "onHideMessages";
	public static final String VULPE_SHOW_ACTIONS = "vulpeShowActions";
	public static final String VULPE_SHOW_MESSAGES = "vulpeShowMessages";
	public static final String ERROR_GERAL = "vulpe.error.general";
	public static final String CLEAR_PARAMS = "vulpe.clearParams";
	public static final String PARAMS_SESSION_KEY = "_params";
	public static final String CACHED_CLASS = "vulpeCachedClass";
	public static final String CACHED_ENUM = "vulpeCachedEnum";
	public static final String CACHED_ENUM_ARRAY = "vulpeCachedEnumArray";
	public static final String FRAMEWORK = "vulpe";
	public static final String DOMAINS = "domains";
	public static final String CONTEXT = "vulpeContext";
	public static final String SECURITY_CONTEXT = "vulpeSecurityContext";

	public class Context {
		public static final String APPLICATION_SCOPE = "APPLICATION_SCOPE";
		public static final String PAGE_SCOPE = "PAGE_SCOPE";
		public static final String REQUEST_SCOPE = "REQUEST_SCOPE";
		public static final String SESSION_SCOPE = "SESSION_SCOPE";
		public static final String THEME = "vulpeTheme";
		public static final String I18N_MANAGER = "vulpeI18nManager";
		public static final String AUDIT_ENABLED = "vulpeAuditEnabled";
		public static final String SECURITY_ENABLED = "vulpeSecurityEnabled";
		public static final String FRONTEND_MENU_TYPE = "vulpeFrontendMenuType";
		public static final String BACKEND_MENU_TYPE = "vulpeBackendMenuType";

		public class View {
			public static final String BACKEND_CENTERED_LAYOUT = "vulpeBackendCenteredLayout";
			public static final String FRONTEND_CENTERED_LAYOUT = "vulpeFrontendCenteredLayout";
			public static final String SHOW_BUTTON_AS_IMAGE = "vulpeShowButtonAsImage";
			public static final String SHOW_BUTTON_ICON = "vulpeShowButtonIcon";
			public static final String SHOW_BUTTON_TEXT = "vulpeShowButtonText";
			public static final String WIDTH_BUTTON_ICON = "vulpeWidthButtonIcon";
			public static final String WIDTH_MOBILE_BUTTON_ICON = "vulpeWidthMobileButtonIcon";
			public static final String HEIGHT_BUTTON_ICON = "vulpeHeightButtonIcon";
			public static final String HEIGHT_MOBILE_BUTTON_ICON = "vulpeHeightMobileButtonIcon";
			public static final String MESSAGE_SLIDE_UP = "vulpeMessageSlideUp";
			public static final String MESSAGE_SLIDE_UP_TIME = "vulpeMessageSlideUpTime";
			public static final String BREAK_LABEL = "vulpeBreakLabel";
		}

		public static final String SHOW_AS_MOBILE = "vulpeShowAsMobile";

		public class Mobile {
			public static final String VIEWPORT_WIDHT = "viewportWidth";
			public static final String VIEWPORT_HEIGHT = "viewportHeight";
			public static final String VIEWPORT_USER_SCALABLE = "viewportUserScalable";
			public static final String VIEWPORT_INITIAL_SCALE = "viewportInitialScale";
			public static final String VIEWPORT_MAXIMUM_SCALE = "viewportMaximumScale";
			public static final String VIEWPORT_MINIMUM_SCALE = "viewportMinimumScale";
		}

		public static final String VULPE_USE_DB4O = "vulpeUseDB4O";
	}

	public class Error {
		public static final String CONTROLLER = "vulpe.error.controller";
	}

	public class Upload {
		public static final String FILES = "org.vulpe.controller.struts.interceptor.UploadInterceptor.files";

		public class Image {
			public static final String THUMB = "_thumb";
		}
	}

	public class Action {
		public static final String CREATE = "create";
		public static final String CREATE_POST = "createPost";
		public static final String UPDATE = "update";
		public static final String VIEW = "view";
		public static final String UPDATE_POST = "updatePost";
		public static final String DELETE = "delete";
		public static final String READ = "read";
		public static final String PAGING = "paging";
		public static final String PREPARE = "prepare";
		public static final String TWICE = "twice";
		public static final String ADD_DETAIL = "addDetail";
		public static final String TABULAR_POST = "tabularPost";
		public static final String FIND = "find";
		public static final String PERSIST = "persist";
		public static final String UPLOAD = "upload";
		public static final String DOWNLOAD = "download";
		public static final String FRONTEND = "frontend";
		public static final String BACKEND = "backend";
		public static final String DEFINE = "define";
		public static final String ENTITIES = "entities";
		public static final String SELECT_FORM = "_selectForm";
		public static final String SELECT_TABLE = "_selectTable";

		public class Button {
			public static final String CREATE = "createShow";
			public static final String CREATE_POST = "createPostShow";
			public static final String DELETE = "deleteShow";
			public static final String UPDATE = "updateShow";
			public static final String UPDATE_POST = "updatePostShow";
			public static final String PREPARE = "prepareShow";
			public static final String READ = "readShow";
			public static final String REPORT = "reportShow";
			public static final String CLEAR = "clearShow";
			public static final String TABULAR_POST = "tabularPostShow";
			public static final String ADD_DETAIL = "addDetailShow";
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
			public static final String SUFFIX_JSP_CRUD = "CRUD.jsp";

			public static final String BODY_TWICE_JSP = "bodyTwice.jsp";
			public static final String BODY_JSP = "body.jsp";
			public static final String BODY_CRUD_JSP = "crud.jsp";
			public static final String BODY_CRUD_ACTIONS_JSP = "crudActions.jsp";
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
			public static final String CRUD = "/crud";
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
		public static final String VULPE_SECURITY_URL_REQUESTED = "vulpeSecurityURLRequested";
		public static final String VULPE_USER_AUTHENTICATION = "vulpeUserAuthentication";
		public static final String VULPE_USER_AUTHENTICATED = "vulpeUserAuthenticated";
		public static final String ROLE_PREFIX = "ROLE_";
	}

	public class Model {
		public class Entity {

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

	public class Modifiers {
		public static final int TRANSIENT = 130;
	}
}
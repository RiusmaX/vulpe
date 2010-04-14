package org.vulpe.common;

import java.util.Locale;

public interface Constants {

	String ACTIVE = "active";
	String AUDIT = "audit";
	String MAPPING_REQUEST = "MAPPING_REQUEST";
	String FORM_REQUEST = "FORM_REQUEST";
	Locale PORTUGUESE_LOCALE = new Locale("pt", "BR");
	String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";
	String STRING_NULL = "";
	String LITERAL_EMPTY_STRING = "0";
	String LITERAL_POINT = ".";
	String LITERAL_SIMPLE_SPACE = " ";
	String SELECT_OPTION_BLANK = "";
	String IS_EXCEPTION = "IS_EXCEPTION";
	String JS_REDIRECT_LOGIN = "vulpe.view.redirectLogin()";
	String AJAX = "ajax";
	String ON_HIDE_MESSAGES = "onHideMessages";
	String PRINT_MSG = "PRINT_MSG";
	String ERROR_GERAL = "vulpe.error.general";
	String CLEAR_PARAMS = "vulpe.clearParams";
	String PARAMS_SESSION_KEY = "_params";
	String CACHED_CLASS = "vulpeCachedClass";
	String CACHED_ENUM = "vulpeCachedEnum";
	String CACHED_ENUM_ARRAY = "vulpeCachedEnumArray";
	String FRAMEWORK = "vulpe";
	String DOMAINS = "domains";

	interface Context {
		String APPLICATION_SCOPE = "APPLICATION_SCOPE";
		String PAGE_SCOPE = "PAGE_SCOPE";
		String REQUEST_SCOPE = "REQUEST_SCOPE";
		String SESSION_SCOPE = "SESSION_SCOPE";
		String THEME = "vulpeTheme";
		String AUDIT_ENABLED = "vulpeAuditEnabled";
		String SECURITY_ENABLED = "vulpeSecurityEnabled";
		String MENU_TYPE = "vulpeMenuType";

		interface View {
			String SHOW_BUTTON_AS_IMAGE = "vulpeShowButtonAsImage";
			String SHOW_BUTTON_ICON = "vulpeShowButtonIcon";
			String SHOW_BUTTON_TEXT = "vulpeShowButtonText";
			String WIDTH_BUTTON_ICON = "vulpeWidthButtonIcon";
			String WIDTH_MOBILE_BUTTON_ICON = "vulpeWidthMobileButtonIcon";
			String HEIGHT_BUTTON_ICON = "vulpeHeightButtonIcon";
			String HEIGHT_MOBILE_BUTTON_ICON = "vulpeHeightMobileButtonIcon";
			String MESSAGE_SLIDE_UP_TIME = "vulpeMessageSlideUpTime";
		}

		String SHOW_AS_MOBILE = "vulpeShowAsMobile";

		interface Mobile {
			String VIEWPORT_WIDHT = "viewportWidth";
			String VIEWPORT_HEIGHT = "viewportHeight";
			String VIEWPORT_USER_SCALABLE = "viewportUserScalable";
			String VIEWPORT_INITIAL_SCALE = "viewportInitialScale";
			String VIEWPORT_MAXIMUM_SCALE = "viewportMaximumScale";
			String VIEWPORT_MINIMUM_SCALE = "viewportMinimumScale";
		}

		String VULPE_USE_DB4O = "vulpeUseDB4O";
	}

	interface Error {
		String CONTROLLER = "vulpe.error.controller";
	}

	interface Upload {
		String FILES = "org.vulpe.controller.struts.interceptor.UploadInterceptor.files";

		interface Image {
			String THUMB = "_thumb";
		}
	}

	interface InitParameter {
		String PROJECT_BUNDLE = "project.bundle";
		String PROJECT_NAME = "project.name";
	}

	interface Action {
		String CREATE = "create";
		String CREATE_POST = "createPost";
		String UPDATE = "update";
		String VIEW = "view";
		String UPDATE_POST = "updatePost";
		String DELETE = "delete";
		String READ = "read";
		String PAGING = "paging";
		String PREPARE = "prepare";
		String ADD_DETAIL = "addDetail";
		String TABULAR_POST = "tabularPost";
		String FIND = "find";
		String PERSIST = "persist";
		String UPLOAD = "upload";
		String DOWNLOAD = "download";

		interface URI {
			String CREATE = "/create";
			String CREATE_AJAX = "/create/ajax";
			String CREATE_POST = "/createPost";
			String UPDATE = "/update";
			String UPDATE_AJAX = "/update/ajax";
			String VIEW = "/view";
			String VIEW_AJAX = "/view/ajax";
			String UPDATE_POST = "/updatePost";
			String UPDATE_POST_AJAX = "/updatePost/ajax";
			String DELETE = "/delete";
			String DELETE_AJAX = "/delete/ajax";
			String READ = "/read";
			String READ_AJAX = "/read/ajax";
			String PAGING = "/paging";
			String PAGING_AJAX = "/paging/ajax";
			String PREPARE = "/prepare";
			String PREPARE_AJAX = "/prepare/ajax";
			String ADD_DETAIL = "/addDetail";
			String TABULAR_POST = "/tabularPost";
			String FIND = "/find";
			String PERSIST = "/persist";
			String UPLOAD = "/upload";
			String DOWNLOAD = "/download";
		}

		interface Forward {
			String SUCCESS = "success";
			String JSON = "json";
			String ACCESS_DENIED = "accessDenied";
			String REPORT = "report";
			String CREATE = "create";
			String UPDATE = "update";
			String READ = "read";
			String FRONTEND = "frontend";
			String UPLOAD = "upload";
			String DOWNLOAD = "download";

			String ERRORS = "errors";
			String MESSAGES = "messages";
		}
	}

	interface View {
		String LAYER_URL_BACK = "layerUrlBack";
		String URL_BACK = "urlBack";
		String LABEL = "label.";
		String ENUM = ".enum.";
		String LAYOUT = "layout.";
		String AUTHENTICATOR = "authenticator";

		interface Layout {
			String UC = ".uc";
			String MAIN = ".main";
			String DETAIL = ".detail";
			String TABULAR = ".tabular";
			String CRUD = ".crud";
			String REPORT = ".report";
			String OTHER = ".other";
			String SELECT = ".select";
			String TABLE = ".table";
			String FORM = ".form";
			String FRONTEND = "frontend";
			String PUBLIC_JSP = "/WEB-INF/protected-jsp/";
			String PUBLIC_JSP_FRONTEND = PUBLIC_JSP + "frontend/";
			String PROTECTED_JSP = "/WEB-INF/protected-jsp/";
			String PROTECTED_JSP_COMMON = PROTECTED_JSP + "common/";
			String PROTECTED_JSP_FRONTEND = PROTECTED_JSP + "frontend/";
			String JSP = ".jsp";
			String SUFIX_JSP_TABULAR = "Tabular.jsp";
			String SUFIX_JSP_OTHER = ".jsp";
			String SUFIX_JSP_FRONTEND = ".jsp";
			String SUFIX_JSP_DETAIL = "Detail.jsp";
			String SUFIX_JSP_SELECT = "Select.jsp";
			String SUFIX_JSP_SELECT_ITEMS = "SelectItems.jsp";
			String SUFIX_JSP_REPORT = "Report.jsp";
			String SUFIX_JSP_REPORT_ITEMS = "ReportItems.jsp";
			String SUFIX_JSP_CRUD = "CRUD.jsp";

			String UC_JSP = "uc.jsp";
			String UC_CRUD_JSP = "crud.jsp";
			String UC_CRUD_ACTIONS_JSP = "crudActions.jsp";
			String UC_SELECT_JSP = "select.jsp";
			String UC_SELECT_ACTIONS_JSP = "selectActions.jsp";
			String UC_TABULAR_JSP = "tabular.jsp";
			String UC_TABULAR_ACTIONS_JSP = "tabularActions.jsp";

			String TARGET_CONFIG = "targetConfig";
			String TARGET_CONFIG_PROPERTY_NAME = "targetConfigPropertyName";
			String DETAIL_ITEM = "_item";

			String ADD_DETAIL_SHOW = "addDetailShow";
			String DELETE_SHOW = "deleteShow";
		}

		interface Logic {
			String FRONTEND = ".frontend";
			String REPORT = ".report";
			String CRUD = ".crud";
			String SELECTION = ".select";
			String TABULAR = ".tabular";
			String AJAX = ".ajax";
		}

		interface Report {
			String PATH = "/WEB-INF/reports/";
			String JASPER = ".jasper";
		}
	}

	interface Model {

		interface DAO {

			interface DB4O {
				String DATABASE_NAME = "vulpe.fox";
				String DATABASE_DIRECTORY = "/db4o/data";
				int DATABASE_PORT = 7777;
				String STATUS = "status";
				String SELECTED = "selected";
			}

			interface JPA {

			}
		}

		interface Services {

		}
	}

	interface Modifiers {
		int TRANSIENT = 130;
	}
}
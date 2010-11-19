package org.vulpe.security.commons;

/**
 * Contains common constants related to application security implementation.
 *
 */
public final class VulpeSecurityConstants {

	/**
	 * A default private constructor to avoid instance creation of
	 * <code>VulpeSecurityConstants</code> class.
	 *
	 */
	private VulpeSecurityConstants() {
	}

	/**
	 * A return code for authentication success.
	 */
	public static final int AUTHENTICATION_SUCCESS = 0;
	/**
	 * A return code for authentication failure.
	 */
	public static final int AUTHENTICATION_FAILED = 1;

	public final class Context {
		public static final String ANONYMOUS_USER = "anonymousUser";
		public static final String ANONYMOUS_ROLE = "anonymousRole";
		public static final String ANONYMOUS_ROLE_DESCRIPTION = "anonymousRoleDescription";

		public static final String ADMINISTRATOR_USER = "administratorUser";
		public static final String ADMINISTRATOR_PASSWORD = "administratorPassword";
		public static final String ADMINISTRATOR_NAME = "administratorName";
		public static final String ADMINISTRATOR_EMAIL = "administratorEmail";
		public static final String ADMINISTRATOR_ROLE = "administratorRole";
		public static final String ADMINISTRATOR_ROLE_DESCRIPTION = "administratorRoleDescription";

		public static final String SECURE_RESOURCES_PATH = "secureResourcesPath";
		public final class ContextDefaults {
			public static final String ANONYMOUS_USER = "anonymous";
			public static final String ANONYMOUS_ROLE = "ROLE_ANONYMOUS";
			public static final String ANONYMOUS_ROLE_DESCRIPTION = "ANONYMOUS";

			public static final String ADMINISTRATOR_USER = "admin";
			public static final String ADMINISTRATOR_PASSWORD = "vulpe";
			public static final String ADMINISTRATOR_NAME = "Administrator";
			public static final String ADMINISTRATOR_EMAIL = "admin@localhost";
			public static final String ADMINISTRATOR_ROLE = "ROLE_ADMINISTRATOR";
			public static final String ADMINISTRATOR_ROLE_DESCRIPTION = "Administrators";

			public static final String SECURE_RESOURCES_PATH = "/audit/**,/security/**";
		}

	}
}

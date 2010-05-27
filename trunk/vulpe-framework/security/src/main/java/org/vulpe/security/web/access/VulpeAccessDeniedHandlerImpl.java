package org.vulpe.security.web.access;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

public class VulpeAccessDeniedHandlerImpl implements AccessDeniedHandler {
	// ~ Static fields/initializers
	// =====================================================================================

	public static final String SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY = "SPRING_SECURITY_403_EXCEPTION";
	protected static final Log logger = LogFactory.getLog(AccessDeniedHandlerImpl.class);
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	// ~ Instance fields
	// ================================================================================================

	private String errorPage;

	// ~ Methods
	// ========================================================================================================

	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (!response.isCommitted()) {
			if (errorPage != null) {
				// Put exception into request scope (perhaps of use to a view)
				request.setAttribute(SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY,
						accessDeniedException);

				// Set the 403 status code.
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);

				// redirect to error page.
				getRedirectStrategy().sendRedirect(request, response, errorPage);
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException
						.getMessage());
			}
		}
	}

	/**
	 * The error page to use. Must begin with a "/" and is interpreted relative
	 * to the current context root.
	 * 
	 * @param errorPage
	 *            the dispatcher path to display
	 * 
	 * @throws IllegalArgumentException
	 *             if the argument doesn't comply with the above limitations
	 */
	public void setErrorPage(String errorPage) {
		if ((errorPage != null) && !errorPage.startsWith("/")) {
			throw new IllegalArgumentException("errorPage must begin with '/'");
		}

		this.errorPage = errorPage;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}

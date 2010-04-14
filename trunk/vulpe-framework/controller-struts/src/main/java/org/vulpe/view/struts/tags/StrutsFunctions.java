package org.vulpe.view.struts.tags;

import java.util.List;

import javax.servlet.jsp.PageContext;

import org.vulpe.view.struts.form.beans.SessionPaging;

@SuppressWarnings("unchecked")
public final class StrutsFunctions {

	private StrutsFunctions() {
		// default constructor
	}

	/**
	 *
	 * @param pageContext
	 * @param pagingName
	 * @param pageSize
	 * @param fullList
	 * @return
	 */
	public static SessionPaging findPaging(final PageContext pageContext,
			final String pagingName, final Long pageSize, final List fullList) {
		SessionPaging paging = (SessionPaging) pageContext.getSession()
				.getAttribute(pagingName);
		if (paging == null && fullList != null) {
			paging = new SessionPaging(pageSize.intValue(), fullList);
			pageContext.getSession().setAttribute(pagingName, paging);
		}
		return paging;
	}
}

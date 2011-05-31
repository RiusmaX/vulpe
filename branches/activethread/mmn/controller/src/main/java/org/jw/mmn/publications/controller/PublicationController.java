package org.jw.mmn.publications.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;

import org.jw.mmn.publications.model.services.PublicationsService;
import org.jw.mmn.controller.ApplicationBaseController;
import org.jw.mmn.publications.model.entity.Publication;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("publications.PublicationController")
@Controller(serviceClass = PublicationsService.class, select = @Select(pageSize = 5, requireOneFilter = true))
public class PublicationController extends ApplicationBaseController<Publication, Long> {

	@Override
	protected List<Publication> autocompleteList() {
		final List<Publication> publications = getCachedClasses().getSelf(
				Publication.class.getSimpleName());
		final List<Publication> filteredPublications = new ArrayList<Publication>();
		for (Publication publication : publications) {
			if (VulpeStringUtil.normalize(publication.getName().toLowerCase()).contains(
					getEntitySelect().getName().toLowerCase())) {
				filteredPublications.add(publication);
			}
		}
		return filteredPublications;
	}
}
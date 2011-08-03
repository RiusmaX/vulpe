package org.jw.mmn.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.jw.mmn.commons.ApplicationConstants.Core;
import org.jw.mmn.controller.ApplicationBaseController;
import org.jw.mmn.core.model.entity.Congregation;
import org.jw.mmn.core.model.entity.CongregationUser;
import org.jw.mmn.core.model.services.CoreService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.annotations.Tabular;

/**
 * Controller implementation of Congregation
 */
@Component("core.CongregationController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, detailsConfig = {
		@DetailConfig(name = "groups", propertyName = "entity.groups", despiseFields = "name", startNewDetails = 3, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE_OR_MORE)),
		@DetailConfig(name = "addresses", propertyName = "entity.addresses", despiseFields = "address", startNewDetails = 1, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE)),
		@DetailConfig(name = "users", propertyName = "entity.users", despiseFields = "user", startNewDetails = 3, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE_OR_MORE)) }, select = @Select(pageSize = 5), tabular = @Tabular(startNewRecords = 1, newRecords = 1))
public class CongregationController extends ApplicationBaseController<Congregation, java.lang.Long> {

	@Override
	protected void createPostAfter() {
		updateData();
	}

	@Override
	protected void updatePostAfter() {
		super.updatePostAfter();
		updateData();
	}

	private void updateData() {
		boolean exists = false;
		List<Congregation> congregations = vulpe.sessionAttribute(Core.CONGREGATIONS_OF_USER);
		if (congregations == null) {
			congregations = new ArrayList<Congregation>();
			congregations.add(entity);
		} else {
			for (Congregation congregation : congregations) {
				if (congregation.getId().equals(entity.getId())) {
					exists = true;
					break;
				}
			}
			if (!exists) {
				final String username = vulpe.userAuthenticated();
				if (entity.getUsers() != null) {
					for (CongregationUser congregationUser : entity.getUsers()) {
						if (congregationUser.getUser().getUsername().equals(username)) {
							congregations.add(entity);
							break;
						}
					}
				}
			}
		}
		vulpe.sessionAttribute(Core.CONGREGATIONS_OF_USER, congregations);
	}

	@Override
	public void manageButtons(Operation operation) {
		super.manageButtons(operation);
		// if (!hasRole("ADMINISTRADOR")) {
		// setOnlyToSee(true);
		// }
	}
}

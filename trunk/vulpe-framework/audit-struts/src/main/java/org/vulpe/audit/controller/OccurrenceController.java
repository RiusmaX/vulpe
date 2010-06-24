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
package org.vulpe.audit.controller;

import static org.vulpe.controller.struts.VulpeStrutsController.BaseActionButtons.CREATE;
import static org.vulpe.controller.struts.VulpeStrutsController.BaseActionButtons.DELETE;
import static org.vulpe.controller.struts.VulpeStrutsController.BaseActionButtons.UPDATE_POST;

import java.util.List;

import org.jfree.util.Log;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.audit.model.services.AuditServices;
import org.vulpe.commons.audit.AuditOccurrenceType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.exception.VulpeApplicationException;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("audit.OccurrenceController")
@Controller(serviceClass = AuditServices.class, pageSize = 5)
@SuppressWarnings("serial")
public class OccurrenceController extends
		VulpeStrutsController<AuditOccurrence, Long> {

	private List<AuditOccurrence> childOccurrences = null;

	@Override
	public String update() {
		final String update = super.update();
		hideButton(new BaseActionButtons[] { UPDATE_POST, DELETE });
		return update;
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		try {
			childOccurrences = getService(AuditServices.class)
					.findByParentAuditOccurrence(
							new AuditOccurrence(getEntity().getId()));
		} catch (VulpeApplicationException e) {
			Log.error(e);
		}
	}

	public List<AuditOccurrence> getChildOccurrences() {
		return childOccurrences;
	}

	public void setChildOccurrences(final List<AuditOccurrence> childOccurrences) {
		this.childOccurrences = childOccurrences;
	}

	@Override
	public String prepare() {
		final String prepare = super.prepare();
		hideButton(CREATE);
		return prepare;
	}

	@Override
	public String read() {
		final String read = super.read();
		hideButton(CREATE);
		return read;
	}

	public AuditOccurrenceType[] getListOccurrenceType() {
		return AuditOccurrenceType.values();
	}
}

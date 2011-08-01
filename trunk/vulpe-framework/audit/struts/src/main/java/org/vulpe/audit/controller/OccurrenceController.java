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

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.audit.model.entity.AuditOccurrenceType;
import org.vulpe.audit.model.services.AuditService;
import org.vulpe.commons.VulpeConstants.Controller.Button;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.exception.VulpeApplicationException;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("audit.OccurrenceController")
@Controller(serviceClass = AuditService.class)
@SuppressWarnings("serial")
public class OccurrenceController extends VulpeStrutsController<AuditOccurrence, Long> {

	private List<AuditOccurrence> childOccurrences = null;

	@Override
	protected void updateAfter() {
		super.updateAfter();
		vulpe.view().onlyToSee(true);
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		try {
			childOccurrences = vulpe.service(AuditService.class).findByParentAuditOccurrence(
					new AuditOccurrence(entity.getId()));
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
	}

	public List<AuditOccurrence> getChildOccurrences() {
		return childOccurrences;
	}

	public void setChildOccurrences(final List<AuditOccurrence> childOccurrences) {
		this.childOccurrences = childOccurrences;
	}

	public AuditOccurrenceType[] getListOccurrenceType() {
		return AuditOccurrenceType.values();
	}

	@Override
	public void manageButtons(Operation operation) {
		super.manageButtons(operation);
		vulpe.view().hideButtons(Button.CREATE);
	}
}

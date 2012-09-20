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
@SuppressWarnings({ "serial", "unchecked" })
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
			LOG.error(e.getMessage());
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

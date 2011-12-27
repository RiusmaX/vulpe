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
package org.vulpe.fox.controller;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DecoratedControllerDetail implements Serializable {

	private String name;
	private int newDetails;
	private int startNewDetails;
	private String despiseFields;
	private String view;
	private String propertyName;
	private String parentDetailName;
	private String next = "";

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDespiseFields() {
		return despiseFields;
	}

	public void setDespiseFields(final String despiseFields) {
		this.despiseFields = despiseFields;
	}

	public String getView() {
		return view;
	}

	public void setView(final String view) {
		this.view = view;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	public String getParentDetailName() {
		return parentDetailName;
	}

	public void setParentDetailName(final String parentDetailName) {
		this.parentDetailName = parentDetailName;
	}

	public void setNext(final String next) {
		this.next = next;
	}

	public String getNext() {
		return next;
	}

	public void setNewDetails(int newDetails) {
		this.newDetails = newDetails;
	}

	public int getNewDetails() {
		return newDetails;
	}

	public void setStartNewDetails(int startNewDetails) {
		this.startNewDetails = startNewDetails;
	}

	public int getStartNewDetails() {
		return startNewDetails;
	}
}
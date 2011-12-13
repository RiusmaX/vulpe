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
package org.vulpe.fox.apt.strategies;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import net.sf.jelly.apt.Context;
import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.strategies.MissingParameterException;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;

/**
 * Strategy for getting the output to a new java source file.
 * 
 */
public class SourceStrategy<B extends TemplateBlock> extends VulpeTemplateOutputRedirectionStrategy<B> {

	private String name;

	private boolean override;

	/**
	 * Get the writer for the specified java source file.
	 * 
	 * @return the writer for the specified java source file.
	 */
	public PrintWriter getWriter() throws MissingParameterException, IOException {
		if (name == null) {
			throw new MissingParameterException("name");
		}

		AnnotationProcessorEnvironment env = getAnnotationProcessorEnvironment();
		if (!override) {
			final String separator = (String) System.getProperties().get("file.separator");
			final String pathname = env.getOptions().get("-s").toString() + separator + name.replaceAll("\\.", "/")
					+ ".java";
			if (new File(pathname).exists()) {
				String message = "Java Source already exists: " + name;
				if (getType().equals("dao-interface")) {
					message = "DAO Interface already exists: " + name;
				} else if (getType().equals("dao-impl")) {
					message = "DAO Implementation already exists: " + name;
				} else if (getType().equals("service-interface")) {
					message = "Service Interface already exists: " + name;
				} else if (getType().equals("service-impl")) {
					message = "Service Implementation already exists: " + name;
				} else if (getType().equals("controller")) {
					message = "Controller already exists: " + name;
				} else if (getType().equals("manager")) {
					message = "Manager already exists: " + name;
				}
				System.out.println(message);
				throw new RuntimeException(message);
			}
		}
		return env.getFiler().createSourceFile(name);
	}

	/**
	 * The current annotation processor environment.
	 * 
	 * @return The current annotation processor environment.
	 */
	protected AnnotationProcessorEnvironment getAnnotationProcessorEnvironment() {
		return Context.getCurrentEnvironment();
	}

	/**
	 * Canonical (fully qualified) name of class whose source is to be written.
	 * 
	 * @return Canonical (fully qualified) name of class whose source is to be
	 *         written.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Canonical (fully qualified) name of class whose source is to be written.
	 * 
	 * @param name
	 *            Canonical (fully qualified) name of class whose source is to
	 *            be written.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public boolean isOverride() {
		return this.override;
	}

}

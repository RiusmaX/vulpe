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

import java.io.IOException;
import java.io.PrintWriter;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.strategies.TemplateBlockStrategy;

/**
 * A strategy for redirecting output.
 */
@SuppressWarnings("rawtypes")
public abstract class VulpeTemplateOutputRedirectionStrategy<B extends TemplateBlock> extends TemplateBlockStrategy<B> {

	private PrintWriter writer;

	private String type;

	/**
	 * @return The writer to which to redirect the output.
	 */
	protected abstract PrintWriter getWriter() throws TemplateException, IOException;

	@Override
	public boolean preProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException,
			TemplateException {
		super.preProcess(block, output, model);
		boolean exists = false;
		try {
			this.writer = getWriter();
			output.redirect(block, writer);
			if (JSPStrategy.class.isAssignableFrom(this.getClass())) {
				System.out.println("Generating JSP: " + ((JSPStrategy) this).getName());
			}
			if (SourceStrategy.class.isAssignableFrom(this.getClass())) {
				final SourceStrategy<B> source = ((SourceStrategy<B>) this);
				if (getType().equals("dao-interface")) {
					System.out.println("Generating DAO Interface: " + source.getName());
				} else if (getType().equals("dao-impl")) {
					System.out.println("Generating DAO Implementation: " + source.getName());
				} else if (getType().equals("service-interface")) {
					System.out.println("Generating Service Interface: " + source.getName());
				} else if (getType().equals("service-impl")) {
					System.out.println("Generating Service Implementation: " + source.getName());
				} else if (getType().equals("controller")) {
					System.out.println("Generating Controller: " + source.getName());
				} else if (getType().equals("manager")) {
					System.out.println("Generating Manager: " + source.getName());
				} else {
					System.out.println("Generating java source: " + source.getName());
				}
			}
		} catch (RuntimeException e) {
			exists = true;
		}
		return !exists;
	}

	@Override
	public void postProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException,
			TemplateException {
		super.postProcess(block, output, model);
		if (this.writer != null) {
			// the writer could be null if there was an error in the
			// pre-process.
			this.writer.close();
		}
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}

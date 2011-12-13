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
import com.sun.mirror.apt.Filer;

/**
 * Strategy for getting the output to a new file <i>relative to the output
 * directory specified for APT</i>.
 */
public class JSPStrategy<B extends TemplateBlock> extends VulpeTemplateOutputRedirectionStrategy<B> {

	private String name;
	private String pkg = "";
	private String charset;
	private boolean override;

	/**
	 * Return the writer to the specified file.
	 * 
	 * @return The writer to the specified file.
	 */
	public PrintWriter getWriter() throws IOException, MissingParameterException {
		if (getName() == null) {
			throw new MissingParameterException("name");
		}

		AnnotationProcessorEnvironment env = getAnnotationProcessorEnvironment();
		if (!override) {
			final String nameWithouExtension = name.substring(0, name.lastIndexOf("."));
			final String extension = name.substring(name.lastIndexOf("."));
			final String pathname = env.getOptions().get("-s").toString() + "/"
					+ nameWithouExtension.replaceAll("\\.", "/") + extension;
			if (new File(pathname).exists()) {
				String message = "JSP already exists: " + name;
				System.out.println(message);
				throw new RuntimeException(message);
			}
		}
		return env.getFiler().createTextFile(Filer.Location.SOURCE_TREE, pkg, new File(getName()), charset);
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
	 * The name of the file.
	 * 
	 * @return The name of the file.
	 */
	public String getName() {
		return name;
	}

	/**
	 * The name of the file.
	 * 
	 * @param name
	 *            The name of the file.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Package relative to which the file should be named, or the empty string
	 * if none.
	 * 
	 * @return Package relative to which the file should be named, or the empty
	 *         string if none.
	 */
	public String getPackage() {
		return pkg;
	}

	/**
	 * Package relative to which the file should be named, or the empty string
	 * if none.
	 * 
	 * @param pkg
	 *            Package relative to which the file should be named, or the
	 *            empty string if none.
	 */
	public void setPackage(String pkg) {
		this.pkg = pkg;
	}

	/**
	 * The name of the charset to use.
	 * 
	 * @return The name of the charset to use.
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * The name of the charset to use.
	 * 
	 * @param charset
	 *            The name of the charset to use.
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public boolean getOverride() {
		return override;
	}

}

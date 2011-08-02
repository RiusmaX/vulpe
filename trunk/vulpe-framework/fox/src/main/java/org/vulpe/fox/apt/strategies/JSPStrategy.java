/*
 * Copyright 2006 Ryan Heaton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * 
 * @author Ryan Heaton
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

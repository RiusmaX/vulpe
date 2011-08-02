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

/**
 * Strategy for getting the output to a new java source file.
 * 
 * @author Ryan Heaton
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

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

import java.io.IOException;
import java.io.PrintWriter;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.strategies.TemplateBlockStrategy;

/**
 * A strategy for redirecting output.
 * 
 * @author Ryan Heaton
 */
@SuppressWarnings("unchecked")
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

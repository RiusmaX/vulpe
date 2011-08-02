/**
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
package org.vulpe.fox.apt.tags;

import net.sf.jelly.apt.APTJellyTag;

import org.vulpe.fox.apt.strategies.JSPStrategy;

/**
 * Tag that pipes its output to a new file <i>relative to the output directory
 * specified for APT</i>.
 * 
 * @author Ryan Heaton
 */
@SuppressWarnings("unchecked")
public class JSPTag extends APTJellyTag<JSPStrategy> {

	public JSPTag() {
		super(new JSPStrategy());
	}

	/**
	 * The name of the file.
	 * 
	 * @param name
	 *            The name of the file.
	 */
	public void setName(String name) {
		strategy.setName(name);
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
		strategy.setPackage(pkg);
	}

	/**
	 * The name of the charset to use.
	 * 
	 * @param charset
	 *            The name of the charset to use.
	 */
	public void setCharset(String charset) {
		strategy.setCharset(charset);
	}

	public void setOverride(boolean override) {
		strategy.setOverride(override);
	}

	public boolean isOverride() {
		return strategy.getOverride();
	}

	public String getType() {
		return strategy.getType();
	}

	public void setType(String type) {
		strategy.setType(type);
	}
}

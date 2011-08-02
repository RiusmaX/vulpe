package org.vulpe.fox.apt.freemarker.transforms;

import net.sf.jelly.apt.freemarker.FreemarkerTransform;

import org.vulpe.fox.apt.strategies.JSPStrategy;

/**
 * Pipes its body to a new file in the APT target directory.
 * 
 */
@SuppressWarnings("unchecked")
public class JSPTransform extends FreemarkerTransform<JSPStrategy> {

	/**
	 * Construct a new transform under the specified namespace.
	 * <code>null</code> or <code>""</code> means the root namespace.
	 * 
	 * @param namespace
	 *            The namespace.
	 */
	public JSPTransform(String namespace) {
		super(namespace);
	}

	// Inherited.
	public JSPStrategy newStrategy() {
		return new JSPStrategy();
	}

}

package org.vulpe.fox.apt.freemarker.transforms;

import net.sf.jelly.apt.freemarker.FreemarkerTransform;

import org.vulpe.fox.apt.strategies.SourceStrategy;

/**
 * Output to a new java source file.
 * 
 */
@SuppressWarnings("unchecked")
public class SourceTransform extends FreemarkerTransform<SourceStrategy> {

	/**
	 * Construct a new transform under the specified namespace.
	 * <code>null</code> or <code>""</code> means the root namespace.
	 * 
	 * @param namespace
	 *            The namespace.
	 */
	public SourceTransform(String namespace) {
		super(namespace);
	}

	// Inherited.
	public SourceStrategy newStrategy() {
		return new SourceStrategy();
	}
}

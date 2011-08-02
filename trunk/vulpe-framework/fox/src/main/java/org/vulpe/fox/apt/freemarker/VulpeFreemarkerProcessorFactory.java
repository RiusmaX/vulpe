package org.vulpe.fox.apt.freemarker;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.sf.jelly.apt.ProcessorFactory;

import com.sun.mirror.apt.AnnotationProcessor;

/**
 * A processor factory for the freemarker engine.
 * 
 * @author Ryan Heaton
 */
public class VulpeFreemarkerProcessorFactory extends ProcessorFactory {

	public static final String FM_LIBRARY_NS_OPTION = "-AAPTJellyFreemarkerLibraryNS";

	public VulpeFreemarkerProcessorFactory() {
	}

	public VulpeFreemarkerProcessorFactory(URL script) {
		super(script);
	}

	@Override
	public Collection<String> supportedOptions() {
		ArrayList<String> options = new ArrayList<String>(super.supportedOptions());
		options.add(FM_LIBRARY_NS_OPTION);
		return Collections.unmodifiableCollection(options);
	}

	/**
	 * Instantiate a new freemarker processor.
	 * 
	 * @param url
	 *            The URL to the template.
	 * @return The processor.
	 */
	protected AnnotationProcessor newProcessor(URL url) {
		return new VulpeFreemarkerProcessor(url);
	}

}

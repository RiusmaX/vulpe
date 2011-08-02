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

package org.vulpe.fox.apt.freemarker;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jelly.apt.Context;
import net.sf.jelly.apt.freemarker.APTJellyObjectWrapper;
import net.sf.jelly.apt.freemarker.FreemarkerModel;
import net.sf.jelly.apt.freemarker.FreemarkerTransform;
import net.sf.jelly.apt.freemarker.FreemarkerVariable;
import net.sf.jelly.apt.freemarker.transforms.AnnotationValueTransform;
import net.sf.jelly.apt.freemarker.transforms.FileTransform;
import net.sf.jelly.apt.freemarker.transforms.ForAllConstructorsTransform;
import net.sf.jelly.apt.freemarker.transforms.ForAllFieldsTransform;
import net.sf.jelly.apt.freemarker.transforms.ForAllImportedTypesTransform;
import net.sf.jelly.apt.freemarker.transforms.ForAllMethodsTransform;
import net.sf.jelly.apt.freemarker.transforms.ForAllNestedTypesTransform;
import net.sf.jelly.apt.freemarker.transforms.ForAllPackagesTransform;
import net.sf.jelly.apt.freemarker.transforms.ForAllParametersTransform;
import net.sf.jelly.apt.freemarker.transforms.ForAllPropertiesTransform;
import net.sf.jelly.apt.freemarker.transforms.ForAllThrownTypesTransform;
import net.sf.jelly.apt.freemarker.transforms.ForAllTypesTransform;
import net.sf.jelly.apt.freemarker.transforms.IfHasAnnotationTransform;
import net.sf.jelly.apt.freemarker.transforms.IfHasDeclarationTransform;
import net.sf.jelly.apt.freemarker.transforms.JavaSourceTransform;
import net.sf.jelly.apt.freemarker.transforms.PrimitiveWrapperTransform;
import net.sf.jelly.apt.freemarker.transforms.UnwrapIfPrimitiveTransform;
import net.sf.jelly.apt.freemarker.transforms.WrapIfPrimitiveTransform;

import org.vulpe.fox.apt.freemarker.transforms.JSPTransform;
import org.vulpe.fox.apt.freemarker.transforms.SourceTransform;

import com.sun.mirror.apt.AnnotationProcessor;

import freemarker.cache.URLTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * The processor for a freemarker template file.
 * 
 * @author Ryan Heaton
 */
@SuppressWarnings("unchecked")
public class VulpeFreemarkerProcessor implements AnnotationProcessor {

	private final URL templateURL;

	public VulpeFreemarkerProcessor(URL templateURL) {
		this.templateURL = templateURL;
	}

	public void process() {
		Configuration configuration = getConfiguration();

		try {
			Template template = configuration.getTemplate(getTemplateURL().toString());
			template.process(getRootModel(), new OutputStreamWriter(System.out));
		} catch (IOException e) {
			process(e);
		} catch (TemplateException e) {
			process(e);
		}
	}

	/**
	 * The template URL.
	 * 
	 * @return The template URL.
	 */
	public URL getTemplateURL() {
		return templateURL;
	}

	/**
	 * Process a TemplateException. Default wraps it in a RuntimeException.
	 * 
	 * @param e
	 *            The exception to process.
	 */
	protected void process(TemplateException e) {
		throw new RuntimeException(e);
	}

	/**
	 * Process an IOException. Default wraps it in a RuntimeException.
	 * 
	 * @param e
	 *            The exception to process.
	 */
	protected void process(IOException e) {
		throw new RuntimeException(e);
	}

	/**
	 * Get the object wrapper for the main model.
	 * 
	 * @return the object wrapper for the main model.
	 */
	protected APTJellyObjectWrapper getObjectWrapper() {
		return new APTJellyObjectWrapper();
	}

	/**
	 * The root data model for the template.
	 * 
	 * @return The root data model for the template.
	 */
	protected FreemarkerModel getRootModel() throws TemplateModelException {
		HashMap<String, Map<String, Object>> sourceMap = new HashMap<String, Map<String, Object>>();

		// set up the variables....
		for (FreemarkerVariable var : getVariables()) {
			String namespace = var.getNamespace();
			if (!sourceMap.containsKey(namespace)) {
				sourceMap.put(namespace, new HashMap<String, Object>());
			}
			sourceMap.get(namespace).put(var.getName(), var.getValue());
		}

		// set up the transforms....
		for (FreemarkerTransform transform : getTransforms()) {
			String namespace = transform.getTransformNamespace();
			if (!sourceMap.containsKey(namespace)) {
				sourceMap.put(namespace, new HashMap<String, Object>());
			}
			sourceMap.get(namespace).put(transform.getTransformName(), transform);
		}

		FreemarkerModel model = newRootModel();
		FreemarkerModel.set(model);
		model.setObjectWrapper(getObjectWrapper());
		if (sourceMap.containsKey(null)) {
			model.putAll(sourceMap.remove(null));
		}
		model.putAll(sourceMap);

		return model;
	}

	/**
	 * Instantiate a new root model.
	 * 
	 * @return The new root model.
	 */
	protected FreemarkerModel newRootModel() {
		return new FreemarkerModel();
	}

	/**
	 * Get the freemarker configuration.
	 * 
	 * @return the freemarker configuration.
	 */
	protected Configuration getConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setTemplateLoader(getTemplateLoader());
		configuration.setLocalizedLookup(false);
		return configuration;
	}

	/**
	 * The collection of transforms to establish in the model before processing.
	 * 
	 * @return The collection of transforms to establish in the model before
	 *         processing.
	 */
	protected Collection<FreemarkerTransform> getTransforms() {
		String namespace = Context.getCurrentEnvironment().getOptions().get(
				VulpeFreemarkerProcessorFactory.FM_LIBRARY_NS_OPTION);
		Collection<FreemarkerTransform> transforms = new ArrayList<FreemarkerTransform>();
		transforms.add(new AnnotationValueTransform(namespace));
		transforms.add(new FileTransform(namespace));
		transforms.add(new ForAllConstructorsTransform(namespace));
		transforms.add(new ForAllFieldsTransform(namespace));
		transforms.add(new ForAllImportedTypesTransform(namespace));
		transforms.add(new ForAllMethodsTransform(namespace));
		transforms.add(new ForAllNestedTypesTransform(namespace));
		transforms.add(new ForAllPackagesTransform(namespace));
		transforms.add(new ForAllParametersTransform(namespace));
		transforms.add(new ForAllPropertiesTransform(namespace));
		transforms.add(new ForAllThrownTypesTransform(namespace));
		transforms.add(new ForAllTypesTransform(namespace));
		transforms.add(new IfHasAnnotationTransform(namespace));
		transforms.add(new IfHasDeclarationTransform(namespace));
		transforms.add(new JavaSourceTransform(namespace));
		transforms.add(new PrimitiveWrapperTransform(namespace));
		transforms.add(new WrapIfPrimitiveTransform(namespace));
		transforms.add(new UnwrapIfPrimitiveTransform(namespace));
		transforms.add(new SourceTransform(namespace));
		transforms.add(new JSPTransform(namespace));
		return transforms;
	}

	/**
	 * The collection of variables to establish in the model before processing.
	 * 
	 * @return The collection of variables to establish in the model before
	 *         processing.
	 */
	protected Collection<FreemarkerVariable> getVariables() {
		Collection<FreemarkerVariable> variables = new ArrayList<FreemarkerVariable>();
		Map<String, String> options = Context.getCurrentEnvironment().getOptions();
		String namespace = options.get(VulpeFreemarkerProcessorFactory.FM_LIBRARY_NS_OPTION);
		variables.add(new FreemarkerVariable(namespace, "aptOptions", options));
		return variables;
	}

	/**
	 * Get the template loader for the freemarker configuration.
	 * 
	 * @return the template loader for the freemarker configuration.
	 */
	protected URLTemplateLoader getTemplateLoader() {
		return new URLTemplateLoader() {
			protected URL getURL(String name) {
				try {
					return new URL(name);
				} catch (MalformedURLException e) {
					return null;
				}
			}
		};
	}

}

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

/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vulpe.commons.xml;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.exception.VulpeSystemException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLReader {

	private static final Logger LOG = Logger.getLogger(XMLReader.class);

	public List<XMLAttribute> reader(final String xml) {
		final List<XMLAttribute> attributeList = new ArrayList<XMLAttribute>();
		if (StringUtils.isNotEmpty(xml)) {
			try {
				final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
						.newInstance();
				final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				final ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes("utf-8"));
				final Document document = docBuilder.parse(bais);
				final Node entity = document.getChildNodes().item(0);
				final NodeList atributos = entity.getChildNodes();
				for (int i = 0; i < atributos.getLength(); i++) {
					final String attribute = atributos.item(i).getNodeName();
					if (!"#text".equals(attribute)) {
						final String value = getChildTagValue((Element) entity, attribute);
						attributeList.add(new XMLAttribute(attribute, value));
					}
				}
			} catch (SAXParseException err) {
				LOG.error("** Parsing error" + ", line " + err.getLineNumber() + ", uri "
						+ err.getSystemId());
				LOG.error(" " + err.getMessage());
			} catch (SAXException e) {
				final Exception exception = e.getException();
				LOG.error(((exception == null) ? e : exception));
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		return attributeList;
	}

	private String getChildTagValue(final Element element, final String tagName)
			throws VulpeSystemException {
		final NodeList children = element.getElementsByTagName(tagName);
		if (children == null) {
			return null;
		}
		final Element child = (Element) children.item(0);
		if (child == null) {
			return null;
		}
		return (child.getFirstChild() == null) ? "" : child.getFirstChild().getNodeValue();
	}

}

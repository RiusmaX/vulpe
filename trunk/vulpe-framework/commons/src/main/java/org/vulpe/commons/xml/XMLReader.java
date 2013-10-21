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
package org.vulpe.commons.xml;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLReader {

	private static final Logger LOG = LoggerFactory.getLogger(XMLReader.class);

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
				LOG.error(((exception == null) ? e.getMessage() : exception.getMessage()));
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
		return attributeList;
	}

	private String getChildTagValue(final Element element, final String tagName) {
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

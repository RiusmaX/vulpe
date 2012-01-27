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
package org.vulpe.fox.apt.strategies;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.decorations.declaration.DecoratedClassDeclaration;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeConstants.Code;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.fox.dao.DecoratedDAO;
import org.vulpe.fox.dao.DecoratedDAOMethod;
import org.vulpe.fox.dao.DecoratedDAOParameter;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.db4o.annotations.SODAQueries;
import org.vulpe.model.db4o.annotations.SODAQuery;
import org.vulpe.model.db4o.annotations.SODAQueryAttribute;
import org.vulpe.model.entity.impl.AbstractVulpeBaseAuditEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseJPAAuditEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseJPAEntity;
import org.vulpe.model.entity.impl.VulpeBaseDB4OAuditEntity;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

import com.sun.mirror.declaration.FieldDeclaration;

public class ForAllDAOTemplateStrategy extends VulpeForAllTemplateStrategy {

	@Override
	public boolean preProcess(final TemplateBlock block, final TemplateOutput<TemplateBlock> output,
			final TemplateModel model) throws IOException, TemplateException {
		if (super.preProcess(block, output, model) && getDeclaration() instanceof DecoratedClassDeclaration) {
			final DecoratedClassDeclaration clazz = (DecoratedClassDeclaration) getDeclaration();
			final CodeGenerator codeGenerator = clazz.getAnnotation(CodeGenerator.class);
			if (getClassName(clazz.getSuperclass()).equals(VulpeBaseSimpleEntity.class.getName())
					|| (codeGenerator != null && !codeGenerator.dao())) {
				return false;
			}
			final DecoratedDAO dao = new DecoratedDAO();
			dao.setOverride(true);
			final String simpleName = clazz.getSimpleName();
			dao.setName(simpleName);
			dao.setDaoName(simpleName + Code.Generator.DAO_SUFFIX);
			final String packageName = clazz.getPackage().toString();
			dao.setPackageName(packageName);
			dao.setDaoPackageName(StringUtils.replace(packageName, Code.Generator.ENTITY_PACKAGE,
					Code.Generator.DAO_PACKAGE));
			if (clazz.getAnnotation(javax.persistence.Inheritance.class) != null
					|| clazz.getAnnotation(org.vulpe.model.db4o.annotations.Inheritance.class) != null) {
				dao.setInheritance(true);
				dao.setDaoSuperclassPackageName(StringUtils.replace(clazz.getSuperclass().getDeclaration().getPackage()
						.toString(), Code.Generator.ENTITY_PACKAGE, Code.Generator.DAO_PACKAGE));
			}
			// if super class isn't Object
			if (clazz.getSuperclass() != null
					&& !getClassName(clazz.getSuperclass()).equals(Object.class.getName())
					&& (!getClassName(clazz.getSuperclass()).equals(AbstractVulpeBaseEntity.class.getName())
							&& (!getClassName(clazz.getSuperclass()).equals(
									AbstractVulpeBaseAuditEntity.class.getName()))
							&& !getClassName(clazz.getSuperclass()).equals(AbstractVulpeBaseJPAEntity.class.getName())
							&& !getClassName(clazz.getSuperclass()).equals(
									AbstractVulpeBaseJPAAuditEntity.class.getName())
							&& !getClassName(clazz.getSuperclass()).equals(VulpeBaseDB4OEntity.class.getName()) && !getClassName(
							clazz.getSuperclass()).equals(VulpeBaseDB4OAuditEntity.class.getName()))) {
				final String superClassName = getClassName(clazz.getSuperclass());
				final String simpleSuperClassName = superClassName.substring(superClassName.lastIndexOf(".") + 1);
				if (!simpleSuperClassName.startsWith(Code.Generator.ABSTRACT_PREFIX)) {
					dao.setSuperclassName(superClassName);
					dao.setDaoSuperclassName(StringUtils.replace(dao.getSuperclassName(),
							Code.Generator.ENTITY_PACKAGE, Code.Generator.DAO_PACKAGE)
							+ Code.Generator.DAO_SUFFIX);
				}
			}

			dao.setIdType(getIDType(clazz.getSuperclass()));
			if (dao.getIdType() == null) {
				final FieldDeclaration field = getField(clazz, "id");
				dao.setIdType(field.getType().toString());
			}

			prepareMethods(clazz, dao);

			model.setVariable(getVar(), dao);
			return true;
		}
		return false;
	}

	protected void prepareMethods(final DecoratedClassDeclaration clazz, final DecoratedDAO dao) {
		if (VulpeConfigHelper.get(VulpeDomains.class).useDB4O()) {
			final SODAQueries sodaQueries = clazz.getAnnotation(SODAQueries.class);
			if (sodaQueries != null) {
				for (SODAQuery sq : sodaQueries.value()) {
					putMethod(dao, sq.name(), sq.unique(), sq.attributes());
				}
			}
			final SODAQuery sodaQuery = clazz.getAnnotation(SODAQuery.class);
			if (sodaQuery != null) {
				putMethod(dao, sodaQuery.name(), sodaQuery.unique(), sodaQuery.attributes());
			}
		} else {
			final NamedQueries namedQueries = clazz.getAnnotation(NamedQueries.class);
			if (namedQueries != null) {
				for (NamedQuery nq : namedQueries.value()) {
					putMethod(clazz, dao, nq.query(), nq.name(), nq.hints());
				}
			}
			final NamedQuery namedQuery = clazz.getAnnotation(NamedQuery.class);
			if (namedQuery != null) {
				putMethod(clazz, dao, namedQuery.query(), namedQuery.name(), namedQuery.hints());
			}
			final NamedNativeQueries namedNativeQueries = clazz.getAnnotation(NamedNativeQueries.class);
			if (namedNativeQueries != null) {
				for (NamedNativeQuery nnq : namedNativeQueries.value()) {
					putMethod(clazz, dao, nnq.query(), nnq.name(), nnq.hints());
				}
			}
			final NamedNativeQuery namedNativeQuery = clazz.getAnnotation(NamedNativeQuery.class);
			if (namedNativeQuery != null) {
				putMethod(clazz, dao, namedNativeQuery.query(), namedNativeQuery.name(), namedNativeQuery.hints());
			}
		}
	}

	private void putMethod(final DecoratedClassDeclaration clazz, final DecoratedDAO dao, final String query,
			final String queryName, final QueryHint[] hints) {
		if (queryName.equals(dao.getName().concat(".read"))) {
			return;
		}

		final DecoratedDAOMethod method = new DecoratedDAOMethod();

		if (StringUtils.indexOf(queryName, dao.getName().concat(".")) > -1) {
			method.setName(StringUtils.substring(queryName, StringUtils.indexOf(queryName, dao.getName().concat("."))
					+ new String(dao.getName().concat(".")).length()));
		} else {
			throw new VulpeSystemException("Name of definition in the query is incorrect. Must be on format: ".concat(
					dao.getName()).concat(".").concat(queryName));
		}

		boolean unique = false;
		String newQuery = StringUtils.replace(query, "\t", " ");
		final char dots = ":".charAt(0);
		final char space = " ".charAt(0);
		while (newQuery.indexOf(dots) > -1) {
			newQuery = newQuery.substring(newQuery.indexOf(dots));
			String param = newQuery.substring(1);
			param = param.replace(")", "").replace("(", "");
			if (newQuery.indexOf(space) > -1) {
				param = newQuery.substring(1, newQuery.indexOf(space));
			}
			newQuery = newQuery.replace(":".concat(param), "");

			// get parameter type in annotations @Params and @QueryParameter
			String type = getType(param, hints);

			if (type == null) {
				final FieldDeclaration field = getField(clazz, param);
				if (field == null) {
					throw new VulpeSystemException("Parameter [".concat(param).concat("] not found on class: ").concat(
							dao.getName()));
				} else {
					if (param.equals("id")) {
						unique = true;
					}
					type = field.getType().toString();
					// verified if is unique column or id
					if (!unique) {
						final Column column = field.getAnnotation(Column.class);
						if (column != null) {
							unique = column.unique();
						}
						if (!unique) {
							final JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
							if (field.getAnnotation(JoinColumn.class) != null) {
								unique = joinColumn.unique();
							}
						}
					}
				}
			}

			final DecoratedDAOParameter parameter = new DecoratedDAOParameter();
			parameter.setName(param);
			parameter.setType(type);
			method.getParameters().add(parameter);
		}

		for (final QueryHint queryHint : hints) {
			if (queryHint.name().equals("limit")) {
				final DecoratedDAOParameter parameter = new DecoratedDAOParameter();
				parameter.setName("limit");
				parameter.setType("java.lang.Integer");
				parameter.setValue(Integer.valueOf(queryHint.value()));
				method.getParameters().add(0, parameter);
			}
		}
		setupReturn(dao, queryName, hints, method, unique);

		dao.getMethods().add(method);
	}

	private void putMethod(final DecoratedDAO dao, final String queryName, final boolean unique,
			final SODAQueryAttribute[] atributes) {
		if (queryName.equals(dao.getName().concat(".read"))) {
			return;
		}

		final DecoratedDAOMethod method = new DecoratedDAOMethod();
		method.setName(queryName);
		for (SODAQueryAttribute queryAttribute : atributes) {
			final DecoratedDAOParameter parameter = new DecoratedDAOParameter();
			parameter.setName(queryAttribute.name());
			parameter.setType(queryAttribute.type());
			method.getParameters().add(parameter);
		}

		setupReturn(dao, queryName, method, unique);

		dao.getMethods().add(method);
	}

	protected void setupReturn(final DecoratedDAO dao, final String queryName, final QueryHint[] hints,
			final DecoratedDAOMethod method, final boolean unique) {
		final String returnType = findQueryHint("return", hints);
		if (StringUtils.isNotEmpty(returnType)) {
			method.setReturnType(returnType);
		} else if (unique || isReturnEntity(hints)) {
			method.setReturnType(dao.getName());
		} else {
			method.setReturnType("java.util.List<".concat(dao.getName()).concat(">"));
		}
	}

	protected void setupReturn(final DecoratedDAO dao, final String queryName, final DecoratedDAOMethod method,
			final boolean unique) {
		if (unique) {
			method.setReturnType(dao.getName());
		} else {
			method.setReturnType("java.util.List<".concat(dao.getName()).concat(">"));
		}
	}

}
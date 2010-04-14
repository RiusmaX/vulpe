package org.vulpe.fox;

import javax.persistence.QueryHint;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.decorations.declaration.DecoratedClassDeclaration;
import net.sf.jelly.apt.strategies.TemplateBlockStrategy;

import org.apache.commons.lang.StringUtils;

import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.FieldDeclaration;
import com.sun.mirror.type.ClassType;
import com.sun.mirror.type.InterfaceType;

public class VulpeForAllTemplateStrategy extends
		TemplateBlockStrategy<TemplateBlock> {
	private String var;
	private Object declaration;

	public String getVar() {
		return var;
	}

	public void setVar(final String var) {
		this.var = var;
	}

	public Object getDeclaration() {
		return declaration;
	}

	public void setDeclaration(final Object declaration) {
		this.declaration = declaration;
	}

	protected String getModuleName(final DecoratedClassDeclaration clazz) {
		final String className = getClassName(clazz);
		final String classSimpleName = clazz.getSimpleName();
		final String moduleName = className.substring(0, className
				.indexOf(".model.entity.".concat(classSimpleName)));
		return moduleName.substring(moduleName.lastIndexOf(".") + 1);
	}

	protected FieldDeclaration getField(final DecoratedClassDeclaration clazz,
			final String param) {
		clazz.getFields();
		for (FieldDeclaration field : clazz.getFields()) {
			if (field.getSimpleName().equals(param)) {
				return field;
			}
		}
		if (clazz.getSuperclass() != null
				&& !clazz.getSuperclass().toString().equals(
						Object.class.getName())
				&& clazz.getSuperclass().getDeclaration() != null) {
			return getField(clazz.getSuperclass().getDeclaration(), param);
		}
		return null;
	}

	protected FieldDeclaration getField(final ClassDeclaration clazz,
			final String param) {
		for (FieldDeclaration field : clazz.getFields()) {
			if (field.getSimpleName().equals(param)) {
				return field;
			}
		}
		if (clazz.getSuperclass() != null
				&& !clazz.getSuperclass().toString().equals(
						Object.class.getName())
				&& clazz.getSuperclass().getDeclaration() != null) {
			return getField(clazz.getSuperclass().getDeclaration(), param);
		}
		return null;
	}

	protected String getType(final String paramName, final QueryHint[] hints) {
		return findQueryHint(":".concat(paramName), hints);
	}

	protected boolean isReturnEntity(final QueryHint[] hints) {
		return "entity".equals(findQueryHint("return", hints));
	}

	protected String findQueryHint(final String name, final QueryHint[] hints) {
		for (int i = 0; i < hints.length; i++) {
			if (hints[i].name().equals(name)) {
				return hints[i].value();
			}
		}
		return null;
	}

	public boolean isInstanceOf(final InterfaceType clazz,
			final String classSuper) {
		if (clazz == null || clazz.toString().equals(Object.class.getName())) {
			return false;
		}

		if (getClassName(clazz).equals(classSuper)) {
			return true;
		} else if (clazz.getSuperinterfaces() != null) {
			for (InterfaceType i : clazz.getSuperinterfaces()) {
				if (isInstanceOf(i, classSuper)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isInstanceOf(final DecoratedClassDeclaration clazz,
			final String classSuper) {
		if (clazz == null
				|| clazz.getSimpleName().equals(Object.class.getName())) {
			return false;
		}

		if (clazz.getSimpleName().equals(classSuper)) {
			return true;
		} else if (clazz.getSuperinterfaces() != null) {
			for (InterfaceType i : clazz.getSuperinterfaces()) {
				if (isInstanceOf(i, classSuper)) {
					return true;
				}
			}
		}

		if (isInstanceOf(clazz.getSuperclass(), classSuper)) {
			return true;
		}

		return false;
	}

	public boolean isInstanceOf(final ClassType clazz, final String classSuper) {
		if (clazz == null || clazz.toString().equals(Object.class.getName())) {
			return false;
		}

		if (getClassName(clazz).equals(classSuper)) {
			return true;
		} else if (clazz.getSuperinterfaces() != null) {
			for (InterfaceType i : clazz.getSuperinterfaces()) {
				if (isInstanceOf(i, classSuper)) {
					return true;
				}
			}
		}

		if (isInstanceOf(clazz.getSuperclass(), classSuper)) {
			return true;
		}

		return false;
	}

	protected String getClassName(final Object clazz) {
		if (StringUtils.indexOf(clazz.toString(), "<") > -1) {
			return StringUtils.substring(clazz.toString(), 0, StringUtils
					.indexOf(clazz.toString(), "<"));
		}
		return clazz.toString();
	}
}
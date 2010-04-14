package org.vulpe.model.services.impl.ws.convert;

import java.util.Collection;

import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.model.entity.AbstractVulpeBaseEntityDelegate;

/**
 * Classe de conversão de lista de entidades
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings("unchecked")
public class ListBeanWSConvert<ENTITY extends VulpeBaseEntity, VODELEGATE extends AbstractVulpeBaseEntityDelegate>
		implements WSConvert<Collection<ENTITY>, Collection<VODELEGATE>> {

	public Collection<ENTITY> toBean(final Collection<VODELEGATE> wsBean) {
		if (wsBean != null) {
			try {
				final Collection<ENTITY> collection = wsBean.getClass()
						.newInstance();
				for (VODELEGATE voDelegate : wsBean) {
					collection.add((ENTITY) voDelegate.getBean());
				}
				return collection;
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
		return null;
	}

	public Collection<VODELEGATE> toWSBean(final Collection<ENTITY> bean) {
		if (bean != null) {
			try {
				final Collection<VODELEGATE> collection = bean.getClass()
						.newInstance();
				for (ENTITY vo : bean) {
					collection.add(getInstanceVO(vo));
				}
				return collection;
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
		return null;
	}

	private VODELEGATE getInstanceVO(final ENTITY bean) {
		if (bean == null) {
			return null;
		}

		final String className = bean.getClass().getName() + "Delegate";
		try {
			final Class classe = Class.forName(className);
			return (VODELEGATE) classe.getConstructor(bean.getClass())
					.newInstance(bean);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}
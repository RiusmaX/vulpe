package org.vulpe.model.services.impl.ws.convert;

import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.model.entity.AbstractVulpeBaseEntityDelegate;

/**
 * Classe de conversão de entidade
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings("unchecked")
public class BeanWSConvert<ENTITY extends VulpeBaseEntity, VODELEGATE extends AbstractVulpeBaseEntityDelegate>
		implements WSConvert<ENTITY, VODELEGATE> {

	public ENTITY toBean(final VODELEGATE wsBean) {
		if (wsBean != null) {
			return (ENTITY) wsBean.getBean();
		}
		return null;
	}

	public VODELEGATE toWSBean(final ENTITY bean) {
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
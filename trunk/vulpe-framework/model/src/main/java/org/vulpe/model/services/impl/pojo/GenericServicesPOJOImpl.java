package org.vulpe.model.services.impl.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.impl.db4o.VulpeBaseCRUDDAODB4OImpl;
import org.vulpe.model.dao.impl.jpa.VulpeBaseCRUDDAOJPAImpl;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;
import org.vulpe.model.services.GenericServices;


/**
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@SuppressWarnings("unchecked")
@Service("GenericServices")
@Transactional
public class GenericServicesPOJOImpl<ENTITY extends AbstractVulpeBaseEntityImpl<ID>, ID extends Serializable & Comparable>
		implements GenericServices {

	private static final Logger LOG = Logger.getLogger(GenericServicesPOJOImpl.class);
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.model.services.GenericServices#getList(
	 * org.vulpe.model.entity.VulpeBaseEntity)
	 */
	public <T extends VulpeBaseEntity<?>> List<T> getList(final T entity) {
		List<T> list = null;
		try {
			if (entity.getClass().isAnnotationPresent(Entity.class)) {
				final VulpeBaseCRUDDAOJPAImpl<ENTITY, ID> dao = new VulpeBaseCRUDDAOJPAImpl<ENTITY, ID>();
				dao.setEntityClass((Class<ENTITY>) entity.getClass());
				list = (List<T>) dao.read((ENTITY) entity);
			} else {
				final VulpeBaseCRUDDAODB4OImpl<ENTITY, ID> dao = new VulpeBaseCRUDDAODB4OImpl<ENTITY, ID>();
				dao.setEntityClass((Class<ENTITY>) entity.getClass());
				list = (List<T>) dao.read((ENTITY) entity);
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return list;
	}

}
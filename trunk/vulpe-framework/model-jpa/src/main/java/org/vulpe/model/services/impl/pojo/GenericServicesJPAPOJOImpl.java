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
package org.vulpe.model.services.impl.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.commons.beans.AbstractVulpeBeanFactory;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.impl.jpa.VulpeBaseCRUDDAOJPAImpl;
import org.vulpe.model.entity.AbstractVulpeBaseEntity;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.model.services.GenericServices;

/**
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@SuppressWarnings( { "unchecked", "rawtypes" })
@Service("GenericServices")
@Transactional
public class GenericServicesJPAPOJOImpl<ENTITY extends AbstractVulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		implements GenericServices {

	private static final Logger LOG = Logger.getLogger(GenericServicesJPAPOJOImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.GenericServices#getList(
	 * org.vulpe.model.entity.VulpeBaseEntity)
	 */
	public <T extends VulpeBaseEntity<?>> List<T> getList(final T entity) {
		List<T> list = null;
		try {
			final VulpeBaseCRUDDAOJPAImpl<ENTITY, ID> dao = new VulpeBaseCRUDDAOJPAImpl<ENTITY, ID>();
			final EntityManagerFactory entityManagerFactory = AbstractVulpeBeanFactory
					.getInstance().getBean("entityManagerFactory");
			dao.setJpaTemplate(new JpaTemplate(entityManagerFactory));
			dao.setEntityManagerFactory(entityManagerFactory);
			dao.setEntityClass((Class<ENTITY>) entity.getClass());
			list = (List<T>) dao.read((ENTITY) entity);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return list;
	}

}
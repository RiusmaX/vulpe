package org.vulpe.security.model.services.manager;

import org.springframework.stereotype.Service;
import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;
import org.vulpe.security.model.dao.SecureResourceDAO;
import org.vulpe.security.model.entity.SecureResource;


@Service
public class SecureResourceManager extends VulpeBaseCRUDManagerImpl<SecureResource, Long, SecureResourceDAO> {

}

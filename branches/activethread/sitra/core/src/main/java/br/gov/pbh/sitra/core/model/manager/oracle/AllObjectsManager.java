package br.gov.pbh.sitra.core.model.manager.oracle;

import org.springframework.stereotype.Service;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

import br.gov.pbh.sitra.core.model.dao.oracle.AllObjectsDAO;
import br.gov.pbh.sitra.core.model.entity.oracle.AllObjects;

/**
 * Manager implementation of AllObjects
 */
@Service
public class AllObjectsManager extends VulpeBaseManager<AllObjects, java.lang.Long, AllObjectsDAO> {

}

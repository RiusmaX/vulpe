package br.gov.pbh.prodabel.transfere.core.model.manager.oracle;

import org.springframework.stereotype.Service;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

import br.gov.pbh.prodabel.transfere.core.model.entity.oracle.AllObjects;
import br.gov.pbh.prodabel.transfere.core.model.dao.oracle.AllObjectsDAO;

/**
 * Manager implementation of AllObjects
 */
@Service
public class AllObjectsManager extends VulpeBaseManager<AllObjects, java.lang.Long, AllObjectsDAO> {

}

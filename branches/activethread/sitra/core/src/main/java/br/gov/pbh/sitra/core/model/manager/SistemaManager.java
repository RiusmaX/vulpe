package br.gov.pbh.sitra.core.model.manager;

import org.springframework.stereotype.Service;

import br.gov.pbh.sitra.core.model.dao.SistemaDAO;
import br.gov.pbh.sitra.core.model.entity.Sistema;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Sistema
 */
@Service
public class SistemaManager extends VulpeBaseManager<Sistema, java.lang.Long, SistemaDAO> {

}


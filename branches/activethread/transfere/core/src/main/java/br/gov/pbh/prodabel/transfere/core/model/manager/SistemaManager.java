package br.gov.pbh.prodabel.transfere.core.model.manager;

import org.springframework.stereotype.Service;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

import br.gov.pbh.prodabel.transfere.core.model.entity.Sistema;
import br.gov.pbh.prodabel.transfere.core.model.dao.SistemaDAO;

/**
 * Manager implementation of Sistema
 */
@Service
public class SistemaManager extends VulpeBaseManager<Sistema, java.lang.Long, SistemaDAO> {

}


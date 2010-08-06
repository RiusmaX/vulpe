package br.gov.pbh.sitra.core.model.manager;

import org.springframework.stereotype.Service;

import br.gov.pbh.sitra.core.model.dao.ObjetoDAO;
import br.gov.pbh.sitra.core.model.entity.Objeto;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Objeto
 */
@Service
public class ObjetoManager extends VulpeBaseManager<Objeto, java.lang.Long, ObjetoDAO> {

}


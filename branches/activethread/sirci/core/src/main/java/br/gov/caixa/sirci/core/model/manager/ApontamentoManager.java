package br.gov.caixa.sirci.core.model.manager;

import org.springframework.stereotype.Service;

import br.gov.caixa.sirci.core.model.dao.ApontamentoDAO;
import br.gov.caixa.sirci.core.model.entity.Apontamento;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Apontamento
 */
@Service
public class ApontamentoManager extends VulpeBaseManager<Apontamento, java.lang.Long, ApontamentoDAO> {

}


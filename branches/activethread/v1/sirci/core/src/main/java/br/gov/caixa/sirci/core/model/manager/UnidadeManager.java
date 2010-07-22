package br.gov.caixa.sirci.core.model.manager;

import org.springframework.stereotype.Service;

import br.gov.caixa.sirci.core.model.dao.UnidadeDAO;
import br.gov.caixa.sirci.core.model.entity.Unidade;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Unidade
 */
@Service
public class UnidadeManager extends VulpeBaseManager<Unidade, java.lang.Long, UnidadeDAO> {

}


package br.gov.caixa.sirci.core.model.manager;

import org.springframework.stereotype.Service;

import br.gov.caixa.sirci.core.model.dao.UnidadeDAO;
import br.gov.caixa.sirci.core.model.entity.Unidade;
import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;

/**
 * Manager implementation of Unidade
 */
@Service
public class UnidadeManager extends VulpeBaseCRUDManagerImpl<Unidade, java.lang.Long, UnidadeDAO> {

}


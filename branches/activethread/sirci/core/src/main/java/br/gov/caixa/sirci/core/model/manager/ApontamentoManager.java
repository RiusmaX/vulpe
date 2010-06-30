package br.gov.caixa.sirci.core.model.manager;

import org.springframework.stereotype.Service;

import br.gov.caixa.sirci.core.model.dao.ApontamentoDAO;
import br.gov.caixa.sirci.core.model.entity.Apontamento;
import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;

/**
 * Manager implementation of Apontamento
 */
@Service
public class ApontamentoManager extends VulpeBaseCRUDManagerImpl<Apontamento, java.lang.Long, ApontamentoDAO> {

}


package br.gov.caixa.sirci.core.model.manager;

import org.springframework.stereotype.Service;

import br.gov.caixa.sirci.core.model.dao.TipoApontamentoDAO;
import br.gov.caixa.sirci.core.model.entity.TipoApontamento;
import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;

/**
 * Manager implementation of TipoApontamento
 */
@Service
public class TipoApontamentoManager extends VulpeBaseCRUDManagerImpl<TipoApontamento, java.lang.Long, TipoApontamentoDAO> {

}


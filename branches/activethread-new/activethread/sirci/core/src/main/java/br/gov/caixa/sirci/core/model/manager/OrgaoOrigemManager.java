package br.gov.caixa.sirci.core.model.manager;

import org.springframework.stereotype.Service;

import br.gov.caixa.sirci.core.model.dao.OrgaoOrigemDAO;
import br.gov.caixa.sirci.core.model.entity.OrgaoOrigem;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of OrgaoOrigem
 */
@Service
public class OrgaoOrigemManager extends VulpeBaseManager<OrgaoOrigem, java.lang.Long, OrgaoOrigemDAO> {

}


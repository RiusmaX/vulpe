package br.gov.caixa.sirci.core.model.manager;

import org.springframework.stereotype.Service;

import br.gov.caixa.sirci.core.model.dao.DocumentoOrigemDAO;
import br.gov.caixa.sirci.core.model.entity.DocumentoOrigem;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of DocumentoOrigem
 */
@Service
public class DocumentoOrigemManager extends VulpeBaseManager<DocumentoOrigem, java.lang.Long, DocumentoOrigemDAO> {

}


package br.com.activethread.gmn.core.model.manager;

import org.springframework.stereotype.Service;

import br.com.activethread.gmn.core.model.dao.CongregacaoDAO;
import br.com.activethread.gmn.core.model.entity.Congregacao;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Congregacao
 */
@Service
public class CongregacaoManager extends VulpeBaseManager<Congregacao, java.lang.Long, CongregacaoDAO> {

}


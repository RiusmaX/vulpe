package br.com.activethread.gmn.publicacoes.model.manager;

import org.springframework.stereotype.Service;

import br.com.activethread.gmn.publicacoes.model.dao.TipoPublicacaoDAO;
import br.com.activethread.gmn.publicacoes.model.entity.TipoPublicacao;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of TipoPublicacao
 */
@Service
public class TipoPublicacaoManager extends VulpeBaseManager<TipoPublicacao, java.lang.Long, TipoPublicacaoDAO> {

}


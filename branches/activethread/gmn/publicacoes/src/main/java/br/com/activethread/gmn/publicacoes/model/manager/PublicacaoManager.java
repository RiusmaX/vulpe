package br.com.activethread.gmn.publicacoes.model.manager;

import org.springframework.stereotype.Service;

import br.com.activethread.gmn.publicacoes.model.dao.PublicacaoDAO;
import br.com.activethread.gmn.publicacoes.model.entity.Publicacao;

import org.vulpe.model.services.manager.impl.VulpeBaseManager;

@Service
public class PublicacaoManager extends VulpeBaseManager<Publicacao, Long, PublicacaoDAO> {

}

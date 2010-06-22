package br.com.activethread.gmn.publicacoes.model.manager;

import org.springframework.stereotype.Service;

import br.com.activethread.gmn.publicacoes.model.dao.PublicacaoDAO;
import br.com.activethread.gmn.publicacoes.model.entity.Publicacao;

import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;

@Service
public class PublicacaoManager extends VulpeBaseCRUDManagerImpl<Publicacao, Long, PublicacaoDAO> {

}

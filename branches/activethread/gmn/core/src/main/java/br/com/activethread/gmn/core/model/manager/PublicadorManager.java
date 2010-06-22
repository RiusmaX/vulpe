package br.com.activethread.gmn.core.model.manager;

import org.springframework.stereotype.Service;

import br.com.activethread.gmn.core.model.dao.PublicadorDAO;
import br.com.activethread.gmn.core.model.entity.Publicador;

import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;

@Service
public class PublicadorManager extends VulpeBaseCRUDManagerImpl<Publicador, Long, PublicadorDAO> {

}

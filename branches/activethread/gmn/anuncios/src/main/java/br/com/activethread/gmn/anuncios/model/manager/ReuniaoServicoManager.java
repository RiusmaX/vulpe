package br.com.activethread.gmn.anuncios.model.manager;

import org.springframework.stereotype.Service;

import br.com.activethread.gmn.anuncios.model.dao.ReuniaoServicoDAO;
import br.com.activethread.gmn.anuncios.model.entity.ReuniaoServico;
import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;

/**
 * Manager implementation of ReuniaoServico
 */
@Service
public class ReuniaoServicoManager extends VulpeBaseCRUDManagerImpl<ReuniaoServico, java.lang.Long, ReuniaoServicoDAO> {

}


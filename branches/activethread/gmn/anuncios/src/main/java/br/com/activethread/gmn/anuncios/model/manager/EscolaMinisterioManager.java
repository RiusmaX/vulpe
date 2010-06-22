package br.com.activethread.gmn.anuncios.model.manager;

import org.springframework.stereotype.Service;

import br.com.activethread.gmn.anuncios.model.dao.EscolaMinisterioDAO;
import br.com.activethread.gmn.anuncios.model.entity.EscolaMinisterio;
import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;

/**
 * Manager implementation of EscolaMinisterio
 */
@Service
public class EscolaMinisterioManager extends VulpeBaseCRUDManagerImpl<EscolaMinisterio, java.lang.Long, EscolaMinisterioDAO> {

}


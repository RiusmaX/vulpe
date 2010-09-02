package br.com.activethread.gmn.anuncios.model.manager;

import org.springframework.stereotype.Service;

import br.com.activethread.gmn.anuncios.model.dao.ReuniaoDAO;
import br.com.activethread.gmn.anuncios.model.entity.Reuniao;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Reuniao
 */
@Service
public class ReuniaoManager extends VulpeBaseManager<Reuniao, java.lang.Long, ReuniaoDAO> {

}


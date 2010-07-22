package br.com.activethread.gmn.core.model.manager;

import org.springframework.stereotype.Service;

import br.com.activethread.gmn.core.model.dao.GrupoDAO;
import br.com.activethread.gmn.core.model.entity.Grupo;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Grupo
 */
@Service
public class GrupoManager extends VulpeBaseManager<Grupo, java.lang.Long, GrupoDAO> {

}


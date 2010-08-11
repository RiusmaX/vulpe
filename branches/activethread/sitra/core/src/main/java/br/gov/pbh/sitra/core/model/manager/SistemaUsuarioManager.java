package br.gov.pbh.sitra.core.model.manager;

import org.springframework.stereotype.Service;

import br.gov.pbh.sitra.core.model.dao.SistemaUsuarioDAO;
import br.gov.pbh.sitra.core.model.entity.SistemaUsuario;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of SistemaUsuario
 */
@Service
public class SistemaUsuarioManager extends VulpeBaseManager<SistemaUsuario, java.lang.Long, SistemaUsuarioDAO> {

}


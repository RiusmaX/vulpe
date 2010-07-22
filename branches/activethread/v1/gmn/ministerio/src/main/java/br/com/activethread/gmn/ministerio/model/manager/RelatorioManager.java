package br.com.activethread.gmn.ministerio.model.manager;

import org.springframework.stereotype.Service;

import br.com.activethread.gmn.ministerio.model.dao.RelatorioDAO;
import br.com.activethread.gmn.ministerio.model.entity.Relatorio;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Relatorio
 */
@Service
public class RelatorioManager extends VulpeBaseManager<Relatorio, java.lang.Long, RelatorioDAO> {

}


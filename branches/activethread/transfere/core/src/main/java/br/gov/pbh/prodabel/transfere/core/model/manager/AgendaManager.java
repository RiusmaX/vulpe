package br.gov.pbh.prodabel.transfere.core.model.manager;

import org.springframework.stereotype.Service;

import br.gov.pbh.prodabel.transfere.core.model.dao.AgendaDAO;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;
import br.gov.pbh.prodabel.transfere.core.model.entity.Agenda;

/**
 * Manager implementation of Agenda
 */
@Service
public class AgendaManager extends VulpeBaseManager<Agenda, java.lang.Long, AgendaDAO> {

}

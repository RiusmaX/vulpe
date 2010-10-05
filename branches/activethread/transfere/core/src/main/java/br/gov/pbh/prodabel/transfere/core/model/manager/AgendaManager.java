package br.gov.pbh.prodabel.transfere.core.model.manager;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.entity.Parameter;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

import br.gov.pbh.prodabel.transfere.core.model.dao.AgendaDAO;
import br.gov.pbh.prodabel.transfere.core.model.entity.Agenda;

/**
 * Manager implementation of Agenda
 */
@Service
public class AgendaManager extends VulpeBaseManager<Agenda, java.lang.Long, AgendaDAO> {

	private static final Logger LOG = Logger.getLogger(AgendaManager.class);

	public Integer autorizar(final Agenda agenda) throws VulpeApplicationException {
		Integer retorno = null;
		final List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(Types.NUMERIC, agenda.getId()));
		parameters.add(new Parameter(Types.NUMERIC, agenda.getUsuario().getId()));
		final CallableStatement cstmt = getDAO().executeFunction("pk_agenda.autoriza_execucao",
				Types.INTEGER, parameters);
		try {
			retorno = cstmt.getInt(1);
			cstmt.close();
		} catch (SQLException e) {
			LOG.error(e);
		}
		return retorno;
	}

	public Integer cancelar(final Agenda agenda) throws VulpeApplicationException {
		Integer retorno = null;
		final List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(Types.NUMERIC, agenda.getId()));
		parameters.add(new Parameter(Types.VARCHAR, "cancelar"));
		parameters.add(new Parameter(Types.NUMERIC, agenda.getUsuario().getId()));
		final CallableStatement cstmt = getDAO().executeFunction(
				"pk_agenda.Status_Execucao_Agenda", Types.INTEGER, parameters);
		try {
			retorno = cstmt.getInt(1);
			cstmt.close();
		} catch (SQLException e) {
			LOG.error(e);
		}
		return retorno;
	}

	public Integer reiniciar(final Agenda agenda) throws VulpeApplicationException {
		Integer retorno = null;
		final List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(Types.NUMERIC, agenda.getId()));
		parameters.add(new Parameter(Types.VARCHAR, "reiniciar"));
		parameters.add(new Parameter(Types.NUMERIC, agenda.getUsuario().getId()));
		final CallableStatement cstmt = getDAO().executeFunction(
				"pk_agenda.Status_Execucao_Agenda", Types.INTEGER, parameters);
		try {
			retorno = cstmt.getInt(1);
			cstmt.close();
		} catch (SQLException e) {
			LOG.error(e);
		}
		return retorno;
	}
}

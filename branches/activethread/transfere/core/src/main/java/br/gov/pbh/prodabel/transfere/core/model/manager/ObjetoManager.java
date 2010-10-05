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

import br.gov.pbh.prodabel.transfere.core.model.dao.ObjetoDAO;
import br.gov.pbh.prodabel.transfere.core.model.entity.Objeto;

/**
 * Manager implementation of Objeto
 */
@Service
public class ObjetoManager extends VulpeBaseManager<Objeto, java.lang.Long, ObjetoDAO> {

	private static final Logger LOG = Logger.getLogger(ObjetoManager.class);

	public String transferir(final Objeto objeto) throws VulpeApplicationException {
		String retorno = "";
		final List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(Types.NUMERIC, objeto.getId()));
		final CallableStatement cstmt = getDAO().executeFunction(
				"pk_transfere_objeto.Gera_Transferencia", Types.VARCHAR, parameters);
		try {
			retorno = cstmt.getString(1);
			cstmt.close();
		} catch (SQLException e) {
			LOG.error(e);
		}
		return retorno;
	}

	public String agendarPublicacao(final Objeto objeto) throws VulpeApplicationException {
		String retorno = "";
		final List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(Types.NUMERIC, objeto.getId()));
		final CallableStatement cstmt = getDAO().executeFunction(
				"pk_transfere_objeto.Gera_Transferencia", Types.VARCHAR, parameters);
		try {
			retorno = cstmt.getString(1);
			cstmt.close();
		} catch (SQLException e) {
			LOG.error(e);
		}
		return retorno;
	}
}

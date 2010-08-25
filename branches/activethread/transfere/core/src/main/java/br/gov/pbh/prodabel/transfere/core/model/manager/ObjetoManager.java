package br.gov.pbh.prodabel.transfere.core.model.manager;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.entity.Parameter;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

import br.gov.pbh.prodabel.transfere.core.model.entity.Objeto;
import br.gov.pbh.prodabel.transfere.core.model.dao.ObjetoDAO;
import br.gov.pbh.prodabel.transfere.core.model.manager.ObjetoManager;

/**
 * Manager implementation of Objeto
 */
@Service
public class ObjetoManager extends VulpeBaseManager<Objeto, java.lang.Long, ObjetoDAO> {

	private static final Logger LOG = Logger.getLogger(ObjetoManager.class);

	public String transferir(final Objeto objeto) throws VulpeApplicationException {
		String retorno = "";
		final List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(Types.INTEGER, 3));
		parameters.add(new Parameter(Types.INTEGER, 3));
		final CallableStatement cstmt = getDAO().executeCallableStatement("soma", parameters);
		try {
			final ResultSet resultSet = cstmt.getResultSet();
			if (resultSet.next()) {
				retorno = resultSet.getString(1);
			}
			resultSet.close();
		} catch (SQLException e) {
			LOG.error(e);
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				LOG.error(e);
			}
		}
		return retorno;
	}

	public String agendarPublicacao(final Objeto objeto) throws VulpeApplicationException {
		String retorno = "";
		final List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(Types.INTEGER, 3));
		parameters.add(new Parameter(Types.INTEGER, 3));
		final CallableStatement cstmt = getDAO().executeCallableStatement("soma", parameters);
		try {
			final ResultSet resultSet = cstmt.getResultSet();
			if (resultSet.next()) {
				retorno = resultSet.getString(1);
			}
			resultSet.close();
		} catch (SQLException e) {
			LOG.error(e);
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				LOG.error(e);
			}
		}
		return retorno;
	}
}

package br.gov.pbh.sitra.core.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Action.Button;
import org.vulpe.commons.VulpeConstants.Action.Forward;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.beans.Tab;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.exception.VulpeApplicationException;

import br.gov.pbh.sitra.commons.ApplicationConstants;
import br.gov.pbh.sitra.core.model.entity.Ambiente;
import br.gov.pbh.sitra.core.model.entity.Objeto;
import br.gov.pbh.sitra.core.model.entity.Sistema;
import br.gov.pbh.sitra.core.model.entity.oracle.AllObjects;
import br.gov.pbh.sitra.core.model.services.CoreService;

/**
 * Controller implementation of Objeto
 */
@Component("core.ObjetoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, detailsConfig = { @DetailConfig(name = "objetoItens", propertyName = "entity.objetoItens", despiseFields = "nomeObjeto", startNewDetails = 5, newDetails = 1) }, select = @Select(pageSize = 5))
public class ObjetoController extends ObjetoBaseController {

	@Override
	public void loadNow() {
		super.loadNow();
		final String keyPrefix = Ambiente.class.getName();
		final List<ValueBean> origem = new ArrayList<ValueBean>();
		origem.add(new ValueBean(Ambiente.H.name(), getText(keyPrefix.concat(".").concat(
				Ambiente.H.name()))));
		origem.add(new ValueBean(Ambiente.P.name(), getText(keyPrefix.concat(".").concat(
				Ambiente.P.name()))));
		now.put("origem", origem);
		final List<ValueBean> destino = new ArrayList<ValueBean>();
		destino.add(new ValueBean(Ambiente.D.name(), getText(keyPrefix.concat(".").concat(
				Ambiente.D.name()))));
		destino.add(new ValueBean(Ambiente.H.name(), getText(keyPrefix.concat(".").concat(
				Ambiente.H.name()))));
		now.put("destino", destino);
	}

	@Override
	public void showButtons(String method) {
		super.showButtons(method);
		if (getControllerType().equals(ControllerType.CRUD)) {
			hideButton(Button.CREATE);
			getTabs().put("master", new Tab("Atualizar de"));
			getTabs().put("objetoItens", new Tab("Objetos a serem Transferidos"));
		}
	}

	@Override
	protected void createPostAfter(Objeto entity) {
		super.createPostAfter(entity);
		getButtons().put("transferir", true);
	}

	@Override
	protected void updateAfter() {
		super.updateAfter();
		getButtons().put("transferir", true);
	}

	protected void atualizarDadosObjeto() {
		super.atualizarDadosObjeto();
		final Objeto objeto = getEntity();
		objeto.setDescricao("ATUALIZAÇÃO AMBIENTE");
		for (Sistema sistema : sistemas) {
			if (sistema.getId().equals(objeto.getSistema().getId())) {
				objeto.setSistema(sistema);
				break;
			}
		}
		if (objeto.getOrigem().equals(Ambiente.H)) {
			objeto.setOwnerOrigem(objeto.getSistema().getOwnerHomologacao());
		} else {
			objeto.setOwnerOrigem(objeto.getSistema().getOwnerProducao());
		}
		if (objeto.getDestino().equals(Ambiente.D)) {
			objeto.setOwnerDestino("LEGADO");
		} else {
			objeto.setOwnerDestino(objeto.getSistema().getOwnerHomologacao());
		}

	}

	public String transferir() {
		// try {
		// String retorno =
		// getService(CoreService.class).transferir(getEntity());
		// addActionMessage("Resultado da Soma: " + retorno);
		// } catch (VulpeApplicationException e) {
		// LOG.error(e);
		// }
		addActionMessage("Transferência realizada com sucesso!");
		getButtons().put("transferir", true);
		return Forward.SUCCESS;
	}

	@Override
	public String read() {
		boolean valido = false;
		if (getEntitySelect() != null
				&& (getEntitySelect().getTipoObjeto() != null || StringUtils
						.isNotEmpty(getEntitySelect().getNomeObjeto()))
				|| getEntitySelect().getDataInicial() != null
				|| getEntitySelect().getDataFinal() != null
				|| StringUtils.isNotEmpty(getEntitySelect().getUsuario())
				|| getEntitySelect().getDestino() != null) {
			valido = true;
		}
		if (!valido) {
			addActionError(getText("sitra.msg.erro.pesquisa.sem.filtro"));
			setResultForward(getControllerConfig().getViewItemsPath());
			return Forward.SUCCESS;
		}
		final Sistema sistema = getSessionAttribute(ApplicationConstants.SISTEMA_SELECIONADO);
		getEntitySelect().setSistema(sistema);
		Calendar calendar = Calendar.getInstance();
		if (getEntitySelect().getDataInicial() != null) {
			calendar.setTime(getEntitySelect().getDataInicial());
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			getEntitySelect().setDataInicial(calendar.getTime());
		}
		if (getEntitySelect().getDataFinal() != null) {
			calendar.setTime(getEntitySelect().getDataFinal());
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 99);
			getEntitySelect().setDataFinal(calendar.getTime());
		}
		return super.read();
	}

	public String objetos() {
		try {
			AllObjects allObjects = new AllObjects();
			allObjects.setType(getEntitySelect().getTipoObjeto().name());
			List<AllObjects> objetosOracle = getService(CoreService.class).readAllObjects(
					allObjects);
			now.put("objetos", objetosOracle);
		} catch (VulpeApplicationException e) {
			e.printStackTrace();
		}
		return Forward.SUCCESS;
	}
}

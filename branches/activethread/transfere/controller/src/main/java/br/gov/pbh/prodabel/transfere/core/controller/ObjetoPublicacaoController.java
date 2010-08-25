package br.gov.pbh.prodabel.transfere.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.commons.beans.Tab;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

import br.gov.pbh.prodabel.transfere.commons.ApplicationConstants.Now;
import br.gov.pbh.prodabel.transfere.commons.ApplicationConstants.Sessao;
import br.gov.pbh.prodabel.transfere.core.model.entity.Ambiente;
import br.gov.pbh.prodabel.transfere.core.model.entity.Objeto;
import br.gov.pbh.prodabel.transfere.core.model.entity.Sistema;
import br.gov.pbh.prodabel.transfere.core.model.entity.SistemaUsuario;
import br.gov.pbh.prodabel.transfere.core.model.entity.Status;
import br.gov.pbh.prodabel.transfere.core.model.services.CoreService;

/**
 * Controller implementation of Objeto
 */
@Component("core.ObjetoPublicacaoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, ownerController = "core/Objeto", viewBaseName = "Objeto", detailsConfig = { @DetailConfig(name = "objetoItens", propertyName = "entity.objetoItens", despiseFields = "nomeObjeto", startNewDetails = 5, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE_OR_MORE)) }, select = @Select(pageSize = 5))
public class ObjetoPublicacaoController extends ObjetoBaseController {

	private static final List<ValueBean> origem = new ArrayList<ValueBean>();
	private static final List<ValueBean> destino = new ArrayList<ValueBean>();

	{
		now.put("publicacao", true);
	}

	@Override
	public void loadNow() {
		super.loadNow();
		final String keyPrefix = Ambiente.class.getName();
		if (origem.isEmpty()) {
			origem.add(new ValueBean(Ambiente.D.name(), getText(keyPrefix.concat(".").concat(
					Ambiente.D.name()))));
			origem.add(new ValueBean(Ambiente.H.name(), getText(keyPrefix.concat(".").concat(
					Ambiente.H.name()))));
		}
		now.put(Now.ORIGEM, origem);
		if (destino.isEmpty()) {
			destino.add(new ValueBean(Ambiente.H.name(), getText(keyPrefix.concat(".").concat(
					Ambiente.H.name()))));
			destino.add(new ValueBean(Ambiente.P.name(), getText(keyPrefix.concat(".").concat(
					Ambiente.P.name()))));
		}
		now.put(Now.DESTINO, destino);
		if (getControllerType().equals(ControllerType.CRUD)) {
			getTabs().put(getControllerConfig().getMasterTitleKey(), new Tab("Publicar em"));
			getTabs().put(getControllerConfig().getDetail("objetoItens").getTitleKey(),
					new Tab("Objetos a serem Publicados"));
		}
	}

	@Override
	public void showButtons(String method) {
		super.showButtons(method);
		if (getControllerType().equals(ControllerType.CRUD)) {
			if (getEntity() != null && getEntity().getId() != null
					&& getEntity().getStatus().equals(Status.N)) {
				final SistemaUsuario sistemaUsuario = getSessionAttribute(Sessao.SISTEMA_USUARIO_SELECIONADO);
				if (sistemaUsuario != null) {
					if ((sistemaUsuario.getPublicaHomologacao() && getEntity().getDestino().equals(
							Ambiente.H))
							|| (sistemaUsuario.getPublicaProducao() && getEntity().getDestino()
									.equals(Ambiente.P))) {
						getButtons().put("publicar", true);
					}
					if (!sistemaUsuario.getAdministrador()
							&& !getEntity().getUsuario().equals(getUserAuthenticated())) {
						setOnlyToSee(true);
					}
				}
			}
		}
	}

	protected void atualizarDadosObjeto() {
		super.atualizarDadosObjeto();
		final Objeto objeto = getEntity();
		for (Sistema sistema : sistemas) {
			if (sistema.getId().equals(objeto.getSistema().getId())) {
				objeto.setSistema(sistema);
				break;
			}
		}
		if (objeto.getOrigem().equals(Ambiente.H)) {
			objeto.setOwnerOrigem(objeto.getSistema().getOwnerHomologacao());
		} else {
			objeto.setOwnerOrigem("LEGADO");
		}
		if (objeto.getDestino().equals(Ambiente.H)) {
			objeto.setOwnerDestino(objeto.getSistema().getOwnerHomologacao());
		} else {
			objeto.setOwnerDestino(objeto.getSistema().getOwnerProducao());
		}
	}

	@Override
	protected void createPostAfter(Objeto entity) {
		super.createPostAfter(entity);
		final SistemaUsuario sistemaUsuario = getSessionAttribute(Sessao.SISTEMA_USUARIO_SELECIONADO);
		if (sistemaUsuario != null) {
			if ((sistemaUsuario.getPublicaHomologacao() && getEntity().getDestino().equals(
					Ambiente.H))
					|| (sistemaUsuario.getPublicaProducao() && getEntity().getDestino().equals(
							Ambiente.P))) {
				getButtons().put("publicar", true);
			}
		}
	}
}

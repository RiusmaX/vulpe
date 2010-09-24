package br.gov.pbh.prodabel.transfere.core.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.Controller.Button;
import org.vulpe.commons.VulpeConstants.Controller.Forward;
import org.vulpe.commons.VulpeConstants.Controller.URI;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.exception.VulpeApplicationException;

import br.gov.pbh.prodabel.transfere.commons.ApplicationConstants.Sessao;
import br.gov.pbh.prodabel.transfere.controller.ApplicationBaseController;
import br.gov.pbh.prodabel.transfere.core.model.entity.Ambiente;
import br.gov.pbh.prodabel.transfere.core.model.entity.Objeto;
import br.gov.pbh.prodabel.transfere.core.model.entity.Sistema;
import br.gov.pbh.prodabel.transfere.core.model.entity.oracle.AllObjects;
import br.gov.pbh.prodabel.transfere.core.model.services.CoreService;

/**
 * Controller Base implementation of Objeto
 */
@SuppressWarnings( { "serial", "unchecked" })
public class ObjetoBaseController extends ApplicationBaseController<Objeto, java.lang.Long> {

	protected final List<Sistema> sistemas = (List<Sistema>) getCachedClass().get(
			Sistema.class.getSimpleName());

	@Override
	public void showButtons(Operation operation) {
		super.showButtons(operation);
		if (getControllerType().equals(ControllerType.SELECT)) {
			hideButton(Button.CREATE);
		}
		if (getControllerType().equals(ControllerType.CRUD)) {
			final List<Objeto> objetos = getSessionAttribute(getSelectTableKey());
			if (objetos == null || objetos.isEmpty()) {
				hideButton(Button.PREPARE);
			}
			hideButton(Button.CREATE);
		}
	}

	@Override
	protected void createPostBefore() {
		super.createPostBefore();
		atualizarDadosObjeto();
	}

	@Override
	protected void updatePostBefore() {
		super.updatePostBefore();
		atualizarDadosObjeto();
	}

	protected void atualizarDadosObjeto() {
		final Objeto objeto = getEntity();
		objeto.setUsuario(getUserAuthenticated());
	}

	@Override
	public String select() {
		final String redirecionar = validarSistemaSelecionado();
		if (StringUtils.isNotEmpty(redirecionar)) {
			return redirecionar;
		}
		return super.select();
	}

	@Override
	public String create() {
		final String redirecionar = validarSistemaSelecionado();
		if (StringUtils.isNotEmpty(redirecionar)) {
			return redirecionar;
		}
		return super.create();
	}

	@Override
	public String update() {
		final String redirecionar = validarSistemaSelecionado();
		if (StringUtils.isNotEmpty(redirecionar)) {
			return redirecionar;
		}
		return super.update();
	}

	private String validarSistemaSelecionado() {
		if (getSessionAttribute(Sessao.SISTEMA_SELECIONADO) == null) {
			if (getRequest().getRequestURI().endsWith(URI.AJAX)) {
				setAjax(true);
			}
			final String currentLayout = getSessionAttribute(View.CURRENT_LAYOUT);
			String url = "FRONTEND".equals(currentLayout) ? "/frontend/Index" : "/backend/Index";
			return redirectTo(url, isAjax());
		}
		return null;
	}

	public Sistema getSistemaSelecionado() {
		return getSessionAttribute(Sessao.SISTEMA_SELECIONADO);
	}

	public String objetos() {
		try {
			returnToPage("objetos");
			final AllObjects allObjects = new AllObjects();
			final String index = getRequest().getParameter("index");
			final String tipo = getRequest().getParameter("tipo");
			allObjects.setType(tipo);
			final Objeto objeto = getEntity();
			if (objeto.getOrigem() == null) {
				StringBuilder mensagem = new StringBuilder(
						"Por favor, selecione um valor no campo 'Origem' dentro da Aba ");
				mensagem.append(now.containsKey("publicacao") ? "'Publicar em'."
						: "'Atualizar de'.");
				now.put("mensagem", mensagem.toString());
				return Forward.SUCCESS;
			}
			if (objeto.getOrigem().equals(Ambiente.H)) {
				allObjects.setOwner(getSistemaSelecionado().getOwnerHomologacao());
			} else {
				allObjects.setOwner(getSistemaSelecionado().getOwnerProducao());
			}
			List<AllObjects> objetosOracle = getService(CoreService.class).readAllObjects(
					allObjects);
			now.put("index", index);
			now.put("objetos", objetosOracle);
		} catch (VulpeApplicationException e) {
			e.printStackTrace();
		}
		return Forward.SUCCESS;
	}
}

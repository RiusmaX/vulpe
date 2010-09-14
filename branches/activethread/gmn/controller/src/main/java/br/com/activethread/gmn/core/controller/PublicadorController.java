package br.com.activethread.gmn.core.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

import br.com.activethread.gmn.commons.ApplicationConstants.Core;
import br.com.activethread.gmn.comuns.model.entity.Sexo;
import br.com.activethread.gmn.comuns.model.entity.TipoMinisterio;
import br.com.activethread.gmn.controller.ApplicationBaseController;
import br.com.activethread.gmn.core.model.entity.Publicador;
import br.com.activethread.gmn.core.model.services.CoreService;

@Component("core.PublicadorController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(controllerType = ControllerType.CRUD, serviceClass = CoreService.class, select = @Select(pageSize = 5, requireOneFilter = true))
public class PublicadorController extends ApplicationBaseController<Publicador, Long> {

	public List<ValueBean> listaTipoMinisterio = new ArrayList<ValueBean>();
	public List<ValueBean> listaTipoMinisterioSimples = new ArrayList<ValueBean>();

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.controller.AbstractVulpeBaseSimpleController#postConstruct()
	 */
	protected void postConstruct() {
		super.postConstruct();
		final String className = TipoMinisterio.class.getName();
		if (listaTipoMinisterio.isEmpty()) {
			listaTipoMinisterio.add(new ValueBean(TipoMinisterio.PUBLICADOR.toString(),
					getText(className + "." + TipoMinisterio.PUBLICADOR.toString())));
			listaTipoMinisterio.add(new ValueBean(TipoMinisterio.PIONEIRO_AUXILIAR.toString(),
					getText(className + "." + TipoMinisterio.PIONEIRO_AUXILIAR.toString())));
			listaTipoMinisterio.add(new ValueBean(TipoMinisterio.PIONEIRO_REGULAR.toString(),
					getText(className + "." + TipoMinisterio.PIONEIRO_REGULAR.toString())));
			listaTipoMinisterio.add(new ValueBean(TipoMinisterio.AUSENTE.toString(),
					getText(className + "." + TipoMinisterio.AUSENTE.toString())));
		}
		if (listaTipoMinisterioSimples.isEmpty()) {
			listaTipoMinisterioSimples.add(new ValueBean(TipoMinisterio.PUBLICADOR.toString(),
					getText(className + "." + TipoMinisterio.PUBLICADOR.toString())));
			listaTipoMinisterioSimples.add(new ValueBean(TipoMinisterio.AUSENTE.toString(),
					getText(className + "." + TipoMinisterio.AUSENTE.toString())));
		}
	}

	@Override
	protected void createPostBefore() {
		super.updatePostBefore();
		limparPrivilegios();
	}

	@Override
	protected void createPostAfter(Publicador entity) {
		super.createPostAfter(entity);
		final List<Publicador> publicadores = getSessionAttribute(Core.PUBLICADORES_CONGREGACAO_SELECIONADA);
		publicadores.add(entity);
		setSessionAttribute(Core.PUBLICADORES_CONGREGACAO_SELECIONADA, publicadores);
	}

	@Override
	protected void updatePostBefore() {
		super.updatePostBefore();
		limparPrivilegios();
	}

	@Override
	protected void updatePostAfter() {
		super.updatePostAfter();
		final List<Publicador> publicadores = getSessionAttribute(Core.PUBLICADORES_CONGREGACAO_SELECIONADA);
		for (Publicador publicador : publicadores) {
			if (publicador.getId().equals(getEntity().getId())) {
				publicador = getEntity();
				break;
			}
		}
		setSessionAttribute(Core.PUBLICADORES_CONGREGACAO_SELECIONADA, publicadores);
	}

	@Override
	protected void deleteAfter() {
		super.deleteAfter();
		final List<Publicador> publicadores = getSessionAttribute(Core.PUBLICADORES_CONGREGACAO_SELECIONADA);
		for (Iterator<Publicador> iterator = publicadores.iterator(); iterator.hasNext();) {
			final Publicador publicador = iterator.next();
			if (publicador.getId().equals(getEntity().getId())) {
				iterator.remove();
				break;
			}
		}
		setSessionAttribute(Core.PUBLICADORES_CONGREGACAO_SELECIONADA, publicadores);
	}

	private void limparPrivilegios() {
		if (!getEntity().getBatizado()) {
			getEntity().setTipoMinisterio(null);
			getEntity().setCargo(null);
			getEntity().setPrivilegiosAdicionais(null);
		} else if (getEntity().getSexo().equals(Sexo.FEMININO)) {
			getEntity().setCargo(null);
			getEntity().setPrivilegiosAdicionais(null);
		}
	}

	@Override
	public List<Publicador> autocompleteList() {
		final List<Publicador> publicadores = getSessionAttribute(Core.PUBLICADORES_CONGREGACAO_SELECIONADA);
		final List<Publicador> publicadoresFiltrados = new ArrayList<Publicador>();
		for (Publicador publicador : publicadores) {
			if (publicador.getNome().toLowerCase().contains(
					getEntitySelect().getNome().toLowerCase())) {
				publicadoresFiltrados.add(publicador);
			}
		}
		return publicadoresFiltrados;
	}

}

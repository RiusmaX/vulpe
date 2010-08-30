package br.com.activethread.gmn.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

import br.com.activethread.gmn.comuns.model.entity.Sexo;
import br.com.activethread.gmn.controller.ApplicationBaseController;
import br.com.activethread.gmn.core.model.entity.Publicador;
import br.com.activethread.gmn.core.model.services.CoreService;

@Component("core.PublicadorController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(controllerType = ControllerType.CRUD, serviceClass = CoreService.class, select = @Select(pageSize = 5, requireOneFilter = true))
public class PublicadorController extends ApplicationBaseController<Publicador, Long> {

	@Override
	protected void createPostBefore() {
		super.updatePostBefore();
		limparPrivilegios();
	}

	@Override
	protected void updatePostBefore() {
		super.updatePostBefore();
		limparPrivilegios();
	}

	private void limparPrivilegios() {
		if (!getEntity().getBatizado()) {
			getEntity().setTipoMinisterio(null);
			getEntity().setCargo(null);
			getEntity().setPrivilegiosAdicionais(null);
//			getEntity().setIndicador(false);
//			getEntity().setLeitor(false);
//			getEntity().setMicrofone(false);
		} else if (getEntity().getSexo().equals(Sexo.FEMININO)) {
			getEntity().setCargo(null);
			getEntity().setPrivilegiosAdicionais(null);
//			getEntity().setIndicador(false);
//			getEntity().setLeitor(false);
//			getEntity().setMicrofone(false);
		}
	}
}

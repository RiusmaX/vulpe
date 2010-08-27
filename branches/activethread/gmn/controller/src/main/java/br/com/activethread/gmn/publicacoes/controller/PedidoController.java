package br.com.activethread.gmn.publicacoes.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Report;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.struts.VulpeStrutsController;

import br.com.activethread.gmn.publicacoes.model.entity.Pedido;
import br.com.activethread.gmn.publicacoes.model.entity.PedidoPublicacao;
import br.com.activethread.gmn.publicacoes.model.services.PublicacoesService;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("publicacoes.PedidoController")
@SuppressWarnings("serial")
@Controller(serviceClass = PublicacoesService.class, select = @Select(pageSize = 5, showReport = true), report = @Report(subReports = "Pedido-publicacoes"), detailsConfig = { @DetailConfig(name = "publicacoes", propertyName = "entity.publicacoes", despiseFields = "publicacao", newDetails = 1, quantity = @Quantity(type = QuantityType.ONE_OR_MORE)) })
public class PedidoController extends VulpeStrutsController<Pedido, Long> {

	@Override
	protected void onCreate() {
		super.onCreate();
		getEntity().setData(new Date());
	}

	@Override
	protected Pedido onCreatePost() {
		getEntity().setDataValidade(gerarDataValidade());
		getEntity().setEntregue(validarEntrega());
		if (getEntity().isEntregue()) {
			getEntity().setDataEntrega(new Date());
		}

		return super.onCreatePost();
	}

	private Date gerarDataValidade() {
		if (getEntity().getData() == null) {
			return null;
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(getEntity().getData());
		calendar.add(Calendar.MONTH, 3);
		return calendar.getTime();
	}

	@Override
	protected boolean onUpdatePost() {
		getEntity().setDataValidade(gerarDataValidade());
		getEntity().setEntregue(validarEntrega());
		if (getEntity().isEntregue()) {
			getEntity().setDataEntrega(new Date());
		} else {
			getEntity().setDataEntrega(null);
		}

		return super.onUpdatePost();
	}

	private boolean validarEntrega() {
		if (VulpeValidationUtil.isEmpty(getEntity().getPublicacoes())) {
			return false;
		}
		int contador = 0;
		for (PedidoPublicacao pedidoPublicacao : getEntity().getPublicacoes()) {
			if (pedidoPublicacao.getQuantidadeEntregue() == null) {
				continue;
			}
			pedidoPublicacao.setEntregue(pedidoPublicacao.getQuantidade().equals(
					pedidoPublicacao.getQuantidadeEntregue()));
			if (pedidoPublicacao.isEntregue()) {
				contador++;
			}
		}
		return contador == getEntity().getPublicacoes().size();
	}
}

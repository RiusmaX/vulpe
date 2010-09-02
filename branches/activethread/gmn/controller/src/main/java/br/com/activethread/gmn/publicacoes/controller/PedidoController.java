package br.com.activethread.gmn.publicacoes.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.util.VulpeDateUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Report;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.struts.VulpeStrutsController;

import br.com.activethread.gmn.comuns.model.entity.ClassificacaoPublicacao;
import br.com.activethread.gmn.publicacoes.model.entity.Pedido;
import br.com.activethread.gmn.publicacoes.model.entity.PedidoPublicacao;
import br.com.activethread.gmn.publicacoes.model.services.PublicacoesService;
import br.com.activethread.gmn.relatorio.model.entity.PedidoSimples;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("publicacoes.PedidoController")
@SuppressWarnings("serial")
@Controller(serviceClass = PublicacoesService.class, select = @Select(pageSize = 5, showReport = true), report = @Report(file = "/WEB-INF/reports/publicacoes/Pedido/Pedidos.jasper", name = "Pedidos", subReports = "Publicacoes"), detailsConfig = { @DetailConfig(name = "publicacoes", propertyName = "entity.publicacoes", despiseFields = "publicacao", newDetails = 1, quantity = @Quantity(type = QuantityType.ONE_OR_MORE)) })
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

	@Override
	protected DownloadInfo doReportLoad() {
		final StringBuilder periodo = new StringBuilder();
		if (getEntitySelect().getDataInicial() != null && getEntitySelect().getDataFinal() != null) {
			final String dataInicial = VulpeDateUtil.getDate(getEntitySelect().getDataInicial(),
					"dd 'de' MMMM 'de' yyyy");
			final String dataFinal = VulpeDateUtil.getDate(getEntitySelect().getDataFinal(),
					"dd 'de' MMMM 'de' yyyy");
			periodo.append(dataInicial).append(" a ").append(dataFinal);
			getReportParameters().put("periodo", periodo.toString());
		} else if (getEntitySelect().getDataInicial() != null) {
			final String dataInicial = VulpeDateUtil.getDate(getEntitySelect().getDataInicial(),
					"dd 'de' MMMM 'de' yyyy");
			periodo.append("A partir de ").append(dataInicial);
			getReportParameters().put("periodo", periodo.toString());
		} else if (getEntitySelect().getDataFinal() != null) {
			final String dataFinal = VulpeDateUtil.getDate(getEntitySelect().getDataFinal(),
					"dd 'de' MMMM 'de' yyyy");
			periodo.append("Antes de ").append(dataFinal);
		} else {
			periodo.append("Total");
		}
		getReportParameters().put("periodo", periodo.toString());
		List<PedidoPublicacao> publicacoes = new ArrayList<PedidoPublicacao>();
		for (Pedido pedido : getEntities()) {
			publicacoes.addAll(pedido.getPublicacoes());
		}
		final List<PedidoSimples> normal = new ArrayList<PedidoSimples>();
		for (PedidoPublicacao pedidoPublicacao : publicacoes) {
			if (pedidoPublicacao.getPublicacao().getClassificacao().equals(
					ClassificacaoPublicacao.NORMAL)) {
				int count = 0;
				for (PedidoPublicacao pedidoPublicacao2 : publicacoes) {
					if (pedidoPublicacao.getPublicacao().getId().equals(
							pedidoPublicacao2.getPublicacao().getId())) {
						count = count + pedidoPublicacao2.getQuantidade();
					}
				}
				boolean existe = false;
				for (PedidoSimples pedidoSimples : normal) {
					if (pedidoSimples.getPublicacao().getId().equals(pedidoPublicacao.getPublicacao().getId())) {
						pedidoSimples.setQuantidade(count);
						existe = true;
					}
				}
				if (!existe) {
					normal.add(new PedidoSimples(pedidoPublicacao.getPublicacao(), count));
				}
			}
		}
		final List<PedidoSimples> ipe = new ArrayList<PedidoSimples>();
		for (PedidoPublicacao pedidoPublicacao : publicacoes) {
			if (pedidoPublicacao.getPublicacao().getClassificacao().equals(
					ClassificacaoPublicacao.IPE)) {
				int count = 0;
				for (PedidoPublicacao pedidoPublicacao2 : publicacoes) {
					if (pedidoPublicacao.getPublicacao().getId().equals(
							pedidoPublicacao2.getPublicacao().getId())) {
						count = count + pedidoPublicacao2.getQuantidade();
					}
				}
				boolean existe = false;
				for (PedidoSimples pedidoSimples : ipe) {
					if (pedidoSimples.getPublicacao().getId().equals(pedidoPublicacao.getPublicacao().getId())) {
						pedidoSimples.setQuantidade(count);
						existe = true;
					}
				}
				if (!existe) {
					ipe.add(new PedidoSimples(pedidoPublicacao.getPublicacao(), count));
				}
			}
		}
		final List<String> collection = new ArrayList<String>();
		collection.add("report");
		setReportCollection(collection);
		getReportParameters().put("publicacoesNormal", normal);
		getReportParameters().put("publicacoesIPE", ipe);
		return super.doReportLoad();
	}
}

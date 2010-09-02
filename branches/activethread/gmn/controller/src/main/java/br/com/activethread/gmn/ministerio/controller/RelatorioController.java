package br.com.activethread.gmn.ministerio.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.util.VulpeDateUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Report;
import org.vulpe.controller.annotations.Select;

import br.com.activethread.gmn.comuns.model.entity.TipoMinisterio;
import br.com.activethread.gmn.controller.ApplicationBaseController;
import br.com.activethread.gmn.ministerio.model.entity.Relatorio;
import br.com.activethread.gmn.ministerio.model.services.MinisterioService;

/**
 * Controller implementation of Relatorio
 */
@Component("ministerio.RelatorioController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = MinisterioService.class, select = @Select(pageSize = 5, showReport = true), report = @Report(subReports = "Relatorios"))
public class RelatorioController extends ApplicationBaseController<Relatorio, java.lang.Long> {

	@Override
	protected void selectAfter() {
		getEntitySelect().setDataInicial(VulpeDateUtil.getFirstDateOfTheMonth());
		getEntitySelect().setDataFinal(VulpeDateUtil.getLastDateOfTheMonth());
		super.selectAfter();
	}

	@Override
	protected void createAfter() {
		super.createAfter();
		getEntity().setData(new Date());
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
		final List<String> collection = new ArrayList<String>();
		collection.add("report");
		setReportCollection(collection);
		final List<Relatorio> publicadores = new ArrayList<Relatorio>();
		for (Relatorio relatorio : getEntities()) {
			if (relatorio.getTipoMinisterio().equals(TipoMinisterio.NORMAL)) {
				publicadores.add(relatorio);
			}
		}
		final List<Relatorio> pioneirosAuxiliares = new ArrayList<Relatorio>();
		for (Relatorio relatorio : getEntities()) {
			if (relatorio.getTipoMinisterio().equals(TipoMinisterio.PIONEIRO_AUXILIAR)) {
				pioneirosAuxiliares.add(relatorio);
			}
		}
		final List<Relatorio> pioneirosRegulares = new ArrayList<Relatorio>();
		for (Relatorio relatorio : getEntities()) {
			if (relatorio.getTipoMinisterio().equals(TipoMinisterio.PIONEIRO_REGULAR)) {
				pioneirosRegulares.add(relatorio);
			}
		}
		final List<Relatorio> pioneirosEspeciais = new ArrayList<Relatorio>();
		for (Relatorio relatorio : getEntities()) {
			if (relatorio.getTipoMinisterio().equals(TipoMinisterio.PIONEIRO_ESPECIAL)) {
				pioneirosEspeciais.add(relatorio);
			}
		}
		getReportParameters().put("publicadores", publicadores.isEmpty() ? null : publicadores);
		getReportParameters().put("pioneirosAuxiliares",
				pioneirosAuxiliares.isEmpty() ? null : pioneirosAuxiliares);
		getReportParameters().put("pioneirosRegulares",
				pioneirosRegulares.isEmpty() ? null : pioneirosRegulares);
		getReportParameters().put("pioneirosEspeciais",
				pioneirosEspeciais.isEmpty() ? null : pioneirosEspeciais);
		return super.doReportLoad();
	}
}

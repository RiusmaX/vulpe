package br.com.activethread.gmn.ministerio.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.util.VulpeDateUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Report;
import org.vulpe.controller.annotations.Select;

import br.com.activethread.gmn.commons.ApplicationConstants.Core;
import br.com.activethread.gmn.comuns.model.entity.TipoMinisterio;
import br.com.activethread.gmn.controller.ApplicationBaseController;
import br.com.activethread.gmn.core.model.entity.Publicador;
import br.com.activethread.gmn.ministerio.model.entity.Mes;
import br.com.activethread.gmn.ministerio.model.entity.Relatorio;
import br.com.activethread.gmn.ministerio.model.services.MinisterioService;

/**
 * Controller implementation of Relatorio
 */
@Component("ministerio.RelatorioController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = MinisterioService.class, select = @Select(pageSize = 5, showReport = true, requireOneFilter = true), report = @Report(subReports = {
		"Relatorios", "RelatoriosPendentes" }))
public class RelatorioController extends ApplicationBaseController<Relatorio, java.lang.Long> {

	@Override
	protected void selectAfter() {
		int mes = Calendar.getInstance().get(Calendar.MONTH);
		getEntitySelect().setMes(Mes.getMesPorValor(mes == 0 ? 11 : mes - 1));
		super.selectAfter();
	}

	@Override
	protected void createAfter() {
		super.createAfter();
		getEntity().setData(new Date());
		int mes = Calendar.getInstance().get(Calendar.MONTH);
		getEntity().setMes(Mes.getMesPorValor(mes == 0 ? 11 : mes - 1));
	}

	@Override
	protected DownloadInfo doReportLoad() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		if (getEntitySelect().getMes() != null) {
			calendar.set(Calendar.MONTH, getEntitySelect().getMes().ordinal());
		}
		final String periodo = VulpeDateUtil.getDate(calendar.getTime(), "MMMM 'de' yyyy");
		getReportParameters().put("periodo", periodo);
		final List<String> collection = new ArrayList<String>();
		collection.add("report");
		setReportCollection(collection);
		final List<Relatorio> publicadores = new ArrayList<Relatorio>();
		final List<Publicador> publicadoresPendentes = new ArrayList<Publicador>();
		final List<Publicador> publicadoresCongregacao = getSessionAttribute(Core.PUBLICADORES_CONGREGACAO_SELECIONADA);
		if (VulpeValidationUtil.isNotEmpty(getEntities())) {
			for (Relatorio relatorio : getEntities()) {
				if (relatorio.getTipoMinisterio().equals(TipoMinisterio.PUBLICADOR)) {
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
			getReportParameters().put("publicadores", publicadores.isEmpty() ? null : publicadores);
			getReportParameters().put("pioneirosAuxiliares",
					pioneirosAuxiliares.isEmpty() ? null : pioneirosAuxiliares);
			getReportParameters().put("pioneirosRegulares",
					pioneirosRegulares.isEmpty() ? null : pioneirosRegulares);
			for (Publicador publicador : publicadoresCongregacao) {
				boolean entregou = false;
				for (Relatorio relatorio : getEntities()) {
					if (relatorio.getPublicador().getId().equals(publicador.getId())) {
						entregou = true;
					}
				}
				if (!entregou) {
					publicadoresPendentes.add(publicador);
				}
			}
		} else {
			publicadoresPendentes.addAll(publicadoresCongregacao);
		}
		Collections.sort(publicadoresPendentes);
		getReportParameters().put("publicadoresPendentes",
				publicadoresPendentes.isEmpty() ? null : publicadoresPendentes);
		return super.doReportLoad();
	}
}

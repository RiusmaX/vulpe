package br.com.activethread.gmn.publicacoes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.struts.VulpeStrutsController;

import br.com.activethread.gmn.publicacoes.model.entity.Publicacao;
import br.com.activethread.gmn.publicacoes.model.services.PublicacoesService;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("publicacoes.PublicacaoController")
@Controller(serviceClass = PublicacoesService.class, select = @Select(pageSize = 5, requireOneFilter = true))
public class PublicacaoController extends VulpeStrutsController<Publicacao, Long> {

	@Override
	protected List<Publicacao> autocompleteList() {
		final List<Publicacao> publicacoes = getCachedClass().getSelf(
				Publicacao.class.getSimpleName());
		final List<Publicacao> publicacoesFiltradas = new ArrayList<Publicacao>();
		for (Publicacao publicacao : publicacoes) {
			if (publicacao.getNome().toLowerCase().contains(
					getEntitySelect().getNome().toLowerCase())) {
				publicacoesFiltradas.add(publicacao);
			}
		}
		return publicacoesFiltradas;
	}
}

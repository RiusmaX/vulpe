package br.com.activethread.gmn.publicacoes.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.annotations.Tabular;

import br.com.activethread.gmn.controller.ApplicationBaseController;
import br.com.activethread.gmn.publicacoes.model.entity.TipoPublicacao;
import br.com.activethread.gmn.publicacoes.model.services.PublicacoesService;

/**
 * Controller implementation of TipoPublicacao
 */
@Component("publicacoes.TipoPublicacaoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = PublicacoesService.class, select = @Select(pageSize = 5), tabular = @Tabular(startNewRecords = 5, newRecords = 1, despiseFields = { "descricao" }))
public class TipoPublicacaoController extends
		ApplicationBaseController<TipoPublicacao, java.lang.Long> {

}

package br.com.activethread.gmn.anuncios.model.entity;

import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;

import org.vulpe.model.entity.VulpeBaseDB4OEntity;
import br.com.activethread.gmn.core.model.entity.Publicador;

@SuppressWarnings("serial")
public class ReuniaoServicoDiscurso extends VulpeBaseDB4OEntity<Long> {

	private ReuniaoServico reuniaoServico;

	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Publicador/select/prepare", popupWidth = 420, argument = true, required = true, autoComplete = true)
	private Publicador orador;

	@VulpeText(required = true, size = 40, maxlength = 100)
	private String tema;

	@VulpeText(mask = "I", required = true)
	private Integer tempo;

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public Publicador getOrador() {
		return orador;
	}

	public void setOrador(Publicador orador) {
		this.orador = orador;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public void setReuniaoServico(ReuniaoServico reuniaoServico) {
		this.reuniaoServico = reuniaoServico;
	}

	public ReuniaoServico getReuniaoServico() {
		return reuniaoServico;
	}

}

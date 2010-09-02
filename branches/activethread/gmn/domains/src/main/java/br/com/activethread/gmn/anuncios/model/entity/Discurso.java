package br.com.activethread.gmn.anuncios.model.entity;

import java.util.Date;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;

import br.com.activethread.gmn.core.model.entity.Publicador;

@SuppressWarnings("serial")
public class Discurso extends VulpeBaseDB4OEntity<Long> {

	private Reuniao reuniao;

	@VulpeDate
	private Date data;

	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Publicador/select", popupWidth = 420, argument = true, required = true, autoComplete = true)
	private Publicador orador;

	@VulpeText(required = true, size = 40, maxlength = 100)
	private String oradorConvidado;

	@VulpeText(required = true, size = 40, maxlength = 100)
	private String tema;

	@VulpeText(mask = "I", required = true)
	private Integer tempo;

	@VulpeText(required = true, size = 40, maxlength = 100)
	private String cena;

	@VulpeText(required = true, size = 40, maxlength = 100)
	private String fonteMateria;

	@VulpeSelect
	private TipoDiscurso tipo;

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

	public void setReuniao(Reuniao reuniao) {
		this.reuniao = reuniao;
	}

	public Reuniao getReuniao() {
		return reuniao;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getData() {
		return data;
	}

	public void setOradorConvidado(String oradorConvidado) {
		this.oradorConvidado = oradorConvidado;
	}

	public String getOradorConvidado() {
		return oradorConvidado;
	}

	public void setCena(String cena) {
		this.cena = cena;
	}

	public String getCena() {
		return cena;
	}

	public void setFonteMateria(String fonteMateria) {
		this.fonteMateria = fonteMateria;
	}

	public String getFonteMateria() {
		return fonteMateria;
	}

	public void setTipo(TipoDiscurso tipo) {
		this.tipo = tipo;
	}

	public TipoDiscurso getTipo() {
		return tipo;
	}

}

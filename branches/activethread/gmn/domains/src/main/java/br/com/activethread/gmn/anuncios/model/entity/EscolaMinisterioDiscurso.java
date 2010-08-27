package br.com.activethread.gmn.anuncios.model.entity;

import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;

import br.com.activethread.gmn.comuns.model.entity.TipoDiscurso;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import br.com.activethread.gmn.core.model.entity.Publicador;

@SuppressWarnings("serial")
public class EscolaMinisterioDiscurso extends VulpeBaseDB4OEntity<Long> {

	private EscolaMinisterio escolaMinisterio;

	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Publicador/select", popupWidth = 420, argument = true, required = true, autoComplete = true)
	private Publicador publicador;

	@VulpeText(required = true, size = 40, maxlength = 100)
	private String tema;

	@VulpeText(required = true, size = 40, maxlength = 60)
	private String materia;

	@VulpeSelect(required = true)
	private TipoDiscurso tipoDiscurso;

	public Publicador getPublicador() {
		return publicador;
	}

	public void setPublicador(Publicador publicador) {
		this.publicador = publicador;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public TipoDiscurso getTipoDiscurso() {
		return tipoDiscurso;
	}

	public void setTipoDiscurso(TipoDiscurso tipoDiscurso) {
		this.tipoDiscurso = tipoDiscurso;
	}

	public void setEscolaMinisterio(EscolaMinisterio escolaMinisterio) {
		this.escolaMinisterio = escolaMinisterio;
	}

	public EscolaMinisterio getEscolaMinisterio() {
		return escolaMinisterio;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getMateria() {
		return materia;
	}

}

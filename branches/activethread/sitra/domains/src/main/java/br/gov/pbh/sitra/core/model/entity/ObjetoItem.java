package br.gov.pbh.sitra.core.model.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.vulpe.model.entity.impl.AbstractVulpeBaseJPAEntity;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;

@Entity
@Table(name = "TRANSFERENCIA_OBJ_ITEM")
@SuppressWarnings("serial")
public class ObjetoItem extends AbstractVulpeBaseJPAEntity<Long> {

	@Id
	@SequenceGenerator(name = "SQ_TRANSFERENCIA_OBJ_ITEM", sequenceName = "SQ_TRANSFERENCIA_OBJ_ITEM")
	@GeneratedValue(generator = "SQ_TRANSFERENCIA_OBJ_ITEM", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_TRANSFERENCIA_OBJ_ITEM")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRANSFERENCIA_OBJ")
	private Objeto objeto;

	@VulpeSelect(showBlank = false)
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_OBJETO")
	private TipoObjeto tipoObjeto;

	@VulpeText(size = 40)
	@Column(name = "NOME_OBJETO")
	private String nomeObjeto;

	@VulpeSelect(showBlank = false)
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private Status status;

	@Column(name = "LOG_PROCESSAMENTO")
	private Blob logProcessamento;

	@Column(name = "TXT_SCRIPT")
	private Blob script;

	@Column(name = "TXT_SCRIPT_ANTERIOR")
	private Blob scriptAntetior;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public TipoObjeto getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(TipoObjeto tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public String getNomeObjeto() {
		return nomeObjeto;
	}

	public void setNomeObjeto(String nomeObjeto) {
		this.nomeObjeto = nomeObjeto;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Blob getLogProcessamento() {
		return logProcessamento;
	}

	public void setLogProcessamento(Blob logProcessamento) {
		this.logProcessamento = logProcessamento;
	}

	public Blob getScript() {
		return script;
	}

	public void setScript(Blob script) {
		this.script = script;
	}

	public Blob getScriptAntetior() {
		return scriptAntetior;
	}

	public void setScriptAntetior(Blob scriptAntetior) {
		this.scriptAntetior = scriptAntetior;
	}

}

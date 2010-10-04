package br.gov.pbh.prodabel.transfere.core.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.AbstractVulpeBaseJPAEntity;
import org.vulpe.security.model.entity.User;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeTextArea;

@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5)), view = @View(viewType = {
		ViewType.CRUD, ViewType.SELECT }))
@Entity
@Table(name = "TRANSFERENCIA_AGENDA")
@SuppressWarnings("serial")
public class Agenda extends AbstractVulpeBaseJPAEntity<Long> {

	@Id
	@SequenceGenerator(name = "SQ_TRANSFERENCIA_AGENDA", sequenceName = "SQ_TRANSFERENCIA_AGENDA")
	@GeneratedValue(generator = "SQ_TRANSFERENCIA_AGENDA", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_TRANSFERENCIA_AGENDA")
	private Long id;

	@VulpeDate(argument = true)
	@Column(name = "DATA_AGENDAMENTO")
	private Date dataAgendamento;

	@VulpeSelect(argument = true)
	@Column(name = "DESTINO")
	private DestinoAgenda destino;

	@VulpeTextArea(cols = 80, rows = 3)
	@Column(name = "TAREFA")
	private String tarefa;

	@VulpeSelect(argument = true)
	@Column(name = "STATUS")
	private StatusAgenda status;

	@Column(name = "DATA_TERMINO")
	private Date dataTermino;

	@VulpeSelectPopup(identifier = "id", description = "name", action = "/security/User/select", popupWidth = 420, argument = true, autocomplete = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
	private User usuario;

	@VulpeSelectPopup(identifier = "id", description = "descricao", action = "/core/Objeto/select", popupWidth = 420, argument = true, autocomplete = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRANSFERENCIA_OBJ")
	private Objeto objeto;

	@Column(name = "NUMERO_JOB")
	private Long numeroJob;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public String getTarefa() {
		return tarefa;
	}

	public void setTarefa(String tarefa) {
		this.tarefa = tarefa;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public Long getNumeroJob() {
		return numeroJob;
	}

	public void setNumeroJob(Long numeroJob) {
		this.numeroJob = numeroJob;
	}

	public void setStatus(StatusAgenda status) {
		this.status = status;
	}

	public StatusAgenda getStatus() {
		return status;
	}

	public void setDestino(DestinoAgenda destino) {
		this.destino = destino;
	}

	public DestinoAgenda getDestino() {
		return destino;
	}

}

package br.gov.pbh.sitra.core.model.entity;

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

import org.vulpe.model.entity.impl.AbstractVulpeBaseJPAEntity;
import org.vulpe.security.model.entity.User;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.output.VulpeColumn;

@Entity
@Table(name = "TRANSFERENCIA_SIST_USUARIO")
@SuppressWarnings("serial")
public class SistemaUsuario extends AbstractVulpeBaseJPAEntity<Long> {

	@Id
	@SequenceGenerator(name = "SQ_TRANSFERENCIA_SIST_USUARIO", sequenceName = "SQ_TRANSFERENCIA_SIST_USUARIO")
	@GeneratedValue(generator = "SQ_TRANSFERENCIA_SIST_USUARIO", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_TRANSFERENCIA_SIST_USUARIO")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRANSFERENCIA_SISTEMA")
	private Sistema sistema;

	@VulpeSelectPopup(identifier = "id", description = "name", action = "/security/User/select/prepare", popupWidth = 420, argument = true, autoComplete = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRANSFERENCIA_USUARIO", referencedColumnName = "ID")
	private User usuario;

	@VulpeColumn(booleanTo = "{Yes}|{No}")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	@Column(name = "IND_PUBLICA_HOMOLOGACAO")
	private Boolean publicaHomologacao;

	@VulpeColumn(booleanTo = "{Yes}|{No}")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	@Column(name = "IND_PUBLICA_PRODUCAO")
	private Boolean publicaProducao;

	public SistemaUsuario() {
	}

	public SistemaUsuario(final Sistema sistema) {
		this.sistema = sistema;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public User getUsuario() {
		return usuario;
	}

	public Boolean getPublicaHomologacao() {
		return publicaHomologacao;
	}

	public void setPublicaHomologacao(Boolean publicaHomologacao) {
		this.publicaHomologacao = publicaHomologacao;
	}

	public Boolean getPublicaProducao() {
		return publicaProducao;
	}

	public void setPublicaProducao(Boolean publicaProducao) {
		this.publicaProducao = publicaProducao;
	}

}

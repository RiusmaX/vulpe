package br.gov.pbh.sitra.core.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.entity.impl.AbstractVulpeBaseJPAEntity;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateScope;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateType;
import org.vulpe.view.annotations.logic.crud.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5), detailsConfig = { @DetailConfig(name = "usuarios", propertyName = "entity.usuarios", despiseFields = "usuario", startNewDetails = 5, newDetails = 1) }), manager = true, view = @View(viewType = {
//		ViewType.SELECT, ViewType.CRUD }))
@CachedClass
@Entity
@Table(name = "TRANSFERENCIA_SISTEMA")
@SuppressWarnings("serial")
public class Sistema extends AbstractVulpeBaseJPAEntity<Long> {

	@Id
	@SequenceGenerator(name = "SQ_TRANSFERENCIA_SISTEMA", sequenceName = "SQ_TRANSFERENCIA_SISTEMA")
	@GeneratedValue(generator = "SQ_TRANSFERENCIA_SISTEMA", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_TRANSFERENCIA_SISTEMA")
	private Long id;

	@Like
	@VulpeColumn
	@VulpeValidate(type = VulpeValidateType.STRING, minlength = 3, maxlength = 200)
	@VulpeText(argument = true, required = true, size = 60, maxlength = 200)
	@Column(name = "NOME_SISTEMA")
	private String nome;

	@VulpeValidate(type = VulpeValidateType.STRING, minlength = 3, maxlength = 20, requiredScope = VulpeValidateScope.CRUD)
	@VulpeColumn
	@VulpeText(argument = true, required = true, size = 20, maxlength = 20)
	@Column(name = "SIGLA")
	private String sigla;

	@VulpeColumn
	@VulpeText(required = true, size = 60, maxlength = 200)
	@Column(name = "GERENCIA")
	private String gerencia;

	@VulpeText(required = true, size = 60, maxlength = 200)
	@Column(name = "ANALISTA_RESPONSAVEL")
	private String analistaResponsavel;

	@VulpeText(required = true, size = 20, maxlength = 20)
	@Column(name = "RAMAL")
	private String ramal;

	@VulpeText(required = true, size = 20, maxlength = 20)
	@Column(name = "OWNER_HOMOLOGACAO")
	private String ownerHomologacao;

	@VulpeText(required = true, size = 50, maxlength = 100)
	@Column(name = "DBLINK_HOMOLOGACAO")
	private String dblinkHomologacao;

	@VulpeText(required = true, size = 20, maxlength = 20)
	@Column(name = "OWNER_PRODUCAO")
	private String ownerProducao;

	@VulpeText(required = true, size = 50, maxlength = 100)
	@Column(name = "DBLINK_PRODUCAO")
	private String dblinkProducao;

	@VulpeText(required = true, size = 50, maxlength = 100)
	@Column(name = "DBLINK_DESENVOLVIMENTO")
	private String dblinkDesenvolvimento;

	@Detail(clazz = SistemaUsuario.class)
	@OneToMany(targetEntity = SistemaUsuario.class, mappedBy = "sistema", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TRANSFERENCIA_SISTEMA")
	private List<SistemaUsuario> usuarios;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getGerencia() {
		return gerencia;
	}

	public void setGerencia(String gerencia) {
		this.gerencia = gerencia;
	}

	public String getAnalistaResponsavel() {
		return analistaResponsavel;
	}

	public void setAnalistaResponsavel(String analistaResponsavel) {
		this.analistaResponsavel = analistaResponsavel;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	public String getOwnerHomologacao() {
		return ownerHomologacao;
	}

	public void setOwnerHomologacao(String ownerHomologacao) {
		this.ownerHomologacao = ownerHomologacao;
	}

	public String getDblinkHomologacao() {
		return dblinkHomologacao;
	}

	public void setDblinkHomologacao(String dblinkHomologacao) {
		this.dblinkHomologacao = dblinkHomologacao;
	}

	public String getOwnerProducao() {
		return ownerProducao;
	}

	public void setOwnerProducao(String ownerProducao) {
		this.ownerProducao = ownerProducao;
	}

	public String getDblinkProducao() {
		return dblinkProducao;
	}

	public void setDblinkProducao(String dblinkProducao) {
		this.dblinkProducao = dblinkProducao;
	}

	public String getDblinkDesenvolvimento() {
		return dblinkDesenvolvimento;
	}

	public void setDblinkDesenvolvimento(String dblinkDesenvolvimento) {
		this.dblinkDesenvolvimento = dblinkDesenvolvimento;
	}

	public void setUsuarios(List<SistemaUsuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<SistemaUsuario> getUsuarios() {
		return usuarios;
	}

}

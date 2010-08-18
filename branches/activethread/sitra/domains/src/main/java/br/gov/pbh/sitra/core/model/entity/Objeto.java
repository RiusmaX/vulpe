package br.gov.pbh.sitra.core.model.entity;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.vulpe.model.annotations.IgnoreAutoFilter;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.Param;
import org.vulpe.model.annotations.Query;
import org.vulpe.model.annotations.QueryComplementation;
import org.vulpe.model.annotations.QueryReplacement;
import org.vulpe.model.annotations.Relationship;
import org.vulpe.model.annotations.Relationships;
import org.vulpe.model.annotations.Param.OperatorType;
import org.vulpe.model.entity.impl.AbstractVulpeBaseJPAEntity;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeTextArea;
import org.vulpe.view.annotations.logic.crud.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

@Query(
	complementation = @QueryComplementation(distinct = true),
	replacement = @QueryReplacement(select = "new Objeto(obj.id, obj.descricao, obj.origem, obj.destino, obj.usuario, obj.data)")
)
@Relationships({
	@Relationship(target = ObjetoItem.class, property = "objetoItens", attributes = { "id", "tipoObjeto", "nomeObjeto", "status" })
})
@Entity
@Table(name = "TRANSFERENCIA_OBJ")
@SuppressWarnings("serial")
public class Objeto extends AbstractVulpeBaseJPAEntity<Long> {

	@Id
	@SequenceGenerator(name = "SQ_TRANSFERENCIA_OBJ", sequenceName = "SQ_TRANSFERENCIA_OBJ")
	@GeneratedValue(generator = "SQ_TRANSFERENCIA_OBJ", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_TRANSFERENCIA_OBJ")
	private Long id;

	@VulpeColumn(sortable = true, attribute = "nome")
	@VulpeSelect(items = "Sistema", itemKey = "id", itemLabel = "nome", required = true, autoLoad = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRANSFERENCIA_SISTEMA")
	private Sistema sistema;

	@IgnoreAutoFilter
	@VulpeColumn
	@Column(name = "DATA_TRANSFERENCIA")
	private Date data = new Date();

	@VulpeSelect(argument = true)
	@Param(alias = "obj.objetoItens", name = "tipoObjeto")
	private transient TipoObjeto tipoObjeto;

	@VulpeText(argument = true, size = 60)
	@Like
	@Param(alias = "obj.objetoItens", name = "nomeObjeto")
	private transient String nomeObjeto;

	@Param(alias = "obj", name = "data", operator = OperatorType.GREATER_OR_EQUAL)
	@VulpeDate(argument = true)
	private transient Date dataInicial;

	@Param(alias = "obj", name = "data", operator = OperatorType.SMALLER_OR_EQUAL)
	@VulpeDate(argument = true)
	private transient Date dataFinal;

	@VulpeCheckbox(fieldValue = "true")
	@Column(name = "IND_COMPILA_INVALIDO")
	private Boolean compilarInvalidos;

	@IgnoreAutoFilter
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", length = 1)
	private Status status = Status.N;

	@Column(name = "OWNER_ORIGEM")
	private String ownerOrigem;

	@Column(name = "OWNER_DESTINO")
	private String ownerDestino;

	@Column(name = "USUARIO")
	private String usuario;

	@VulpeSelect
	@Enumerated(EnumType.STRING)
	@Column(name = "ORIGEM")
	private Ambiente origem;

	@VulpeSelect(argument = true)
	@Enumerated(EnumType.STRING)
	@Column(name = "DESTINO")
	private Ambiente destino;

	@Lob
	@Column(name = "LOG_COMPILA")
	private Blob logCompila;

	@VulpeText(required = true, size = 100)
	@Column(name = "DESCRICAO")
	private String descricao;

	@VulpeTextArea(required = true, cols = 100, rows = 3)
	@Column(name = "JUSTIFICATIVA")
	private String justificativa;

	@VulpeText(required = true, size = 100)
	@Column(name = "DEMANDAS")
	private String demandas;

	@Detail(clazz = ObjetoItem.class)
	@OneToMany(targetEntity = ObjetoItem.class, mappedBy = "objeto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TRANSFERENCIA_OBJ")
	private List<ObjetoItem> objetoItens;

	public Objeto() {
	}

	public Objeto(Long id, String descricao, Ambiente origem, Ambiente destino, String usuario,
			Date data) {
		this.id = id;
		this.descricao = descricao;
		this.origem = origem;
		this.destino = destino;
		this.usuario = usuario;
		this.data = data;
	}

	public Objeto(Long id, String descricao, Ambiente origem, Ambiente destino, String usuario,
			Date data, List<ObjetoItem> objetoItens) {
		this.id = id;
		this.descricao = descricao;
		this.origem = origem;
		this.destino = destino;
		this.usuario = usuario;
		this.data = data;
		this.objetoItens = objetoItens;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Boolean getCompilarInvalidos() {
		return compilarInvalidos;
	}

	public void setCompilarInvalidos(Boolean compilarInvalidos) {
		this.compilarInvalidos = compilarInvalidos;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getOwnerOrigem() {
		return ownerOrigem;
	}

	public void setOwnerOrigem(String ownerOrigem) {
		this.ownerOrigem = ownerOrigem;
	}

	public String getOwnerDestino() {
		return ownerDestino;
	}

	public void setOwnerDestino(String ownerDestino) {
		this.ownerDestino = ownerDestino;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Ambiente getOrigem() {
		return origem;
	}

	public void setOrigem(Ambiente origem) {
		this.origem = origem;
	}

	public Ambiente getDestino() {
		return destino;
	}

	public void setDestino(Ambiente destino) {
		this.destino = destino;
	}

	public Blob getLogCompila() {
		return logCompila;
	}

	public void setLogCompila(Blob logCompila) {
		this.logCompila = logCompila;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getDemandas() {
		return demandas;
	}

	public void setDemandas(String demandas) {
		this.demandas = demandas;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setObjetoItens(List<ObjetoItem> objetoItens) {
		this.objetoItens = objetoItens;
	}

	public List<ObjetoItem> getObjetoItens() {
		return objetoItens;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setTipoObjeto(TipoObjeto tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public TipoObjeto getTipoObjeto() {
		return tipoObjeto;
	}

	public void setNomeObjeto(String nomeObjeto) {
		this.nomeObjeto = nomeObjeto;
	}

	public String getNomeObjeto() {
		return nomeObjeto;
	}

}

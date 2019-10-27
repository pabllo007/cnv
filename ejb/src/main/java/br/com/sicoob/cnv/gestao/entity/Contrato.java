package br.com.sicoob.cnv.gestao.entity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import br.com.bancoob.negocio.entidades.BancoobEntidade;

/**
 * Entidade referente a tabela CNV.CONTRATO do DB2
 *
 * @author Phelipe.Palmeira
 */
@Entity
@Table(name = "CONTRATOARRECADACAO", schema = "CNV")
public class Contrato extends BancoobEntidade {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -683997964555267874L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDCONTRATO")
	private Long id;

	/** The id pessoa empresa. */
	@Column(name = "IDINSTITUICAOEMPRESA")
	private Long idInstituicaoEmpresa;

	/** The id pessoa empresa. */
	@Column(name = "IDPESSOAEMPRESA")
	private Long idPessoaEmpresa;

	/** The numero. */
	@Column(name = "NUMCONTRATO")
	private String numero;

	/** The data assinatura contrato. */
	@Column(name = "DATAASSINATURACONTRATO")
	@Temporal(TemporalType.DATE)
	private Date dataAssinaturaContrato;

	/** The data vigencia. */
	@Column(name = "DATAVIGENCIA")
	@Temporal(TemporalType.DATE)
	private Date dataVigencia;

	/** The bol vigencia indeterminada. */
	@Column(name = "BOLVIGENCIAINDETERMINADA")
	@Type(type = "org.hibernate.type.NumericBooleanType")	
	private Boolean bolVigenciaIndeterminada;

	/** The data renovacao. */
	@Column(name = "DATARENOVACAO")
	@Temporal(TemporalType.DATE)
	private Date dataRenovacao;

	/** The data atualizacao tarifario. */
	@Column(name = "DATAATUALIZACAOTARIFARIO")
	@Temporal(TemporalType.DATE)
	private Date dataAtualizacaoTarifario;

	/** The alerta renovacao. */
	@ManyToOne
	@JoinColumn(name = "CODPERIODICIDADERENOVACAO")
	private PeriodicidadeAlerta alertaRenovacao;

	/** The alerta tarifaria. */
	@ManyToOne
	@JoinColumn(name = "CODPERIODICIDADETARIFARIA")
	private PeriodicidadeAlerta alertaTarifaria;

	/** The data rescisao. */
	@Column(name = "DATARECISAO")
	@Temporal(TemporalType.DATE)
	private Date dataRescisao;

	/** The desc justificativa rescisao. */
	@Column(name = "DESCJUSTIFICATIVARECISAO")
	private String descJustificativaRescisao;

	/** The situacao renovacao. */
	@ManyToOne
	@JoinColumn(name = "CODSITUACAORENOVACAO")
	private SituacaoContrato situacaoRenovacao;

	/** The cod acao operacional renovacao. */
	@Column(name = "BOLACAOOPERACIONALRENOVACAO")
	private Boolean acaoOperacionalRenovacao;

	/** The situacao tarifaria. */
	@ManyToOne
	@JoinColumn(name = "CODSITUACAOTARIFARIA")
	private SituacaoContrato situacaoTarifaria;

	/** The cod acao operacional tarifaria. */
	@Column(name = "BOLACAOOPERACIONALTARIFARIA")
	private Boolean acaoOperacionalTarifaria;

	/** The sigla. */
	@Column(name = "SIGLACONTRATO")
	private String sigla;

	/** The convenios. */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CONTRATOARRECADACAOCONVENIO", joinColumns = @JoinColumn(name = "IDCONTRATO"), inverseJoinColumns = @JoinColumn(name = "IDCONVENIO"))
	private List<Convenio> convenios;

	@Transient
	private String cnpj;

	private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the id pessoa empresa.
	 *
	 * @return the id pessoa empresa
	 */
	public Long getIdPessoaEmpresa() {
		return idPessoaEmpresa;
	}

	/**
	 * Sets the id pessoa empresa.
	 *
	 * @param idPessoaEmpresa the new id pessoa empresa
	 */
	public void setIdPessoaEmpresa(Long idPessoaEmpresa) {
		this.idPessoaEmpresa = idPessoaEmpresa;
	}

	/**
	 * Gets the numero.
	 *
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Sets the numero.
	 *
	 * @param numero the new numero
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * Gets the data assinatura contrato.
	 *
	 * @return the data assinatura contrato
	 */
	public Date getDataAssinaturaContrato() {
		return dataAssinaturaContrato;
	}

	/**
	 * Sets the data assinatura contrato.
	 *
	 * @param dataAssinaturaContrato the new data assinatura contrato
	 */
	public void setDataAssinaturaContrato(Date dataAssinaturaContrato) {
		this.dataAssinaturaContrato = dataAssinaturaContrato;
	}

	/**
	 * Gets the data vigencia.
	 *
	 * @return the data vigencia
	 */
	public Date getDataVigencia() {
		return dataVigencia;
	}

	/**
	 * Sets the data vigencia.
	 *
	 * @param dataVigencia the new data vigencia
	 */
	public void setDataVigencia(Date dataVigencia) {
		this.dataVigencia = dataVigencia;
	}

	/**
	 * Gets the bol vigencia indeterminada.
	 *
	 * @return the bol vigencia indeterminada
	 */
	public Boolean getBolVigenciaIndeterminada() {
		return bolVigenciaIndeterminada;
	}

	/**
	 * Sets the bol vigencia indeterminada.
	 *
	 * @param bolVigenciaIndeterminada the new bol vigencia indeterminada
	 */
	public void setBolVigenciaIndeterminada(Boolean bolVigenciaIndeterminada) {
		this.bolVigenciaIndeterminada = bolVigenciaIndeterminada;
	}

	/**
	 * Gets the data renovacao.
	 *
	 * @return the data renovacao
	 */
	public Date getDataRenovacao() {
		return dataRenovacao;
	}

	/**
	 * Sets the data renovacao.
	 *
	 * @param dataRenovacao the new data renovacao
	 */
	public void setDataRenovacao(Date dataRenovacao) {
		this.dataRenovacao = dataRenovacao;
	}

	/**
	 * Gets the data atualizacao tarifario.
	 *
	 * @return the data atualizacao tarifario
	 */
	public Date getDataAtualizacaoTarifario() {
		return dataAtualizacaoTarifario;
	}

	/**
	 * Sets the data atualizacao tarifario.
	 *
	 * @param dataAtualizacaoTarifario the new data atualizacao tarifario
	 */
	public void setDataAtualizacaoTarifario(Date dataAtualizacaoTarifario) {
		this.dataAtualizacaoTarifario = dataAtualizacaoTarifario;
	}

	/**
	 * Gets the alerta renovacao.
	 *
	 * @return the alerta renovacao
	 */
	public PeriodicidadeAlerta getAlertaRenovacao() {
		return alertaRenovacao;
	}

	/**
	 * Sets the alerta renovacao.
	 *
	 * @param alertaRenovacao the new alerta renovacao
	 */
	public void setAlertaRenovacao(PeriodicidadeAlerta alertaRenovacao) {
		this.alertaRenovacao = alertaRenovacao;
	}

	/**
	 * Gets the alerta tarifaria.
	 *
	 * @return the alerta tarifaria
	 */
	public PeriodicidadeAlerta getAlertaTarifaria() {
		return alertaTarifaria;
	}

	/**
	 * Sets the alerta tarifaria.
	 *
	 * @param alertaTarifaria the new alerta tarifaria
	 */
	public void setAlertaTarifaria(PeriodicidadeAlerta alertaTarifaria) {
		this.alertaTarifaria = alertaTarifaria;
	}

	/**
	 * Gets the data rescisao.
	 *
	 * @return the data rescisao
	 */
	public Date getDataRescisao() {
		return dataRescisao;
	}

	/**
	 * Sets the data rescisao.
	 *
	 * @param dataRescisao the new data rescisao
	 */
	public void setDataRescisao(Date dataRescisao) {
		this.dataRescisao = dataRescisao;
	}

	/**
	 * Gets the desc justificativa rescisao.
	 *
	 * @return the desc justificativa rescisao
	 */
	public String getDescJustificativaRescisao() {
		return descJustificativaRescisao;
	}

	/**
	 * Sets the desc justificativa rescisao.
	 *
	 * @param descJustificativaRescisao the new desc justificativa rescisao
	 */
	public void setDescJustificativaRescisao(String descJustificativaRescisao) {
		this.descJustificativaRescisao = descJustificativaRescisao;
	}

	/**
	 * Gets the situacao renovacao.
	 *
	 * @return the situacao renovacao
	 */
	public SituacaoContrato getSituacaoRenovacao() {
		return situacaoRenovacao;
	}

	/**
	 * Sets the situacao renovacao.
	 *
	 * @param situacaoRenovacao the new situacao renovacao
	 */
	public void setSituacaoRenovacao(SituacaoContrato situacaoRenovacao) {
		this.situacaoRenovacao = situacaoRenovacao;
	}

	/**
	 * @return the acaoOperacionalRenovacao
	 */
	public Boolean getAcaoOperacionalRenovacao() {
		return acaoOperacionalRenovacao;
	}

	/**
	 * Sets the cod acao operacional renovacao.
	 *
	 * @param codAcaoOperacionalRenovacao the new cod acao operacional renovacao
	 */
	public void setAcaoOperacionalRenovacao(Boolean acaoOperacionalRenovacao) {
		this.acaoOperacionalRenovacao = acaoOperacionalRenovacao;
	}

	/**
	 * Gets the situacao tarifaria.
	 *
	 * @return the situacao tarifaria
	 */
	public SituacaoContrato getSituacaoTarifaria() {
		return situacaoTarifaria;
	}

	/**
	 * Sets the situacao tarifaria.
	 *
	 * @param situacaoTarifaria the new situacao tarifaria
	 */
	public void setSituacaoTarifaria(SituacaoContrato situacaoTarifaria) {
		this.situacaoTarifaria = situacaoTarifaria;
	}

	/**
	 * @return the acaoOperacionalTarifaria
	 */
	public Boolean getAcaoOperacionalTarifaria() {
		return acaoOperacionalTarifaria;
	}

	/**
	 * Sets the cod acao operacional tarifaria.
	 *
	 * @param acaoOperacionalTarifaria the new cod acao operacional tarifaria
	 */
	public void setAcaoOperacionalTarifaria(Boolean acaoOperacionalTarifaria) {
		this.acaoOperacionalTarifaria = acaoOperacionalTarifaria;
	}

	/**
	 * Gets the convenios.
	 *
	 * @return the convenios
	 */
	public List<Convenio> getConvenios() {
		return convenios;
	}

	/**
	 * Sets the convenios.
	 *
	 * @param convenios the new convenios
	 */
	public void setConvenios(List<Convenio> convenios) {
		this.convenios = convenios;
	}

	/**
	 * Gets the sigla.
	 *
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * Sets the sigla.
	 *
	 * @param sigla the new sigla
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return the cnpj
	 */
	public String getCnpj() {
		return cnpj;
	}

	/**
	 * @param cnpj the cnpj to set
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * @return the idInstituicaoEmpresa
	 */
	public Long getIdInstituicaoEmpresa() {
		return idInstituicaoEmpresa;
	}

	/**
	 * @param idInstituicaoEmpresa the idInstituicaoEmpresa to set
	 */
	public void setIdInstituicaoEmpresa(Long idInstituicaoEmpresa) {
		this.idInstituicaoEmpresa = idInstituicaoEmpresa;
	}
	/**
	 * Informa o prazo até a data de renovação.
	 *
	 * @return prazo em dias
	 */
	public Long getPrazoRenovacao()
	{
		LocalDate renovacao, hoje = LocalDate.now();
		if (getDataRenovacao() instanceof java.sql.Date)
		{
			renovacao = ((java.sql.Date) getDataRenovacao()).toLocalDate();
		}
		else
		{
			renovacao = getDataRenovacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return ChronoUnit.DAYS.between(hoje, renovacao);
	}
	/**
	 * Informa o prazo até a data de renovação.
	 *
	 * @return prazo em dias
	 */
	public Long getPrazoTarifario()
	{
		LocalDate atualizacao, hoje = LocalDate.now();
		if (getDataAtualizacaoTarifario() instanceof java.sql.Date)
		{
			atualizacao = ((java.sql.Date) getDataAtualizacaoTarifario()).toLocalDate();
		}
		else
		{
			atualizacao = getDataAtualizacaoTarifario().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return ChronoUnit.DAYS.between(hoje, atualizacao);
	}
    /**
     * Retorna a data da assinatura em formato brasileiro encurtado.
     *
     * @return data formatada
     */
    public String getAssinaturaFormatada()
    {
		if (getDataAssinaturaContrato() instanceof java.sql.Date)
		{
			return ((java.sql.Date) getDataAssinaturaContrato()).toLocalDate().format(FORMATTER);
		}
        return getDataAssinaturaContrato().toInstant().atZone(ZoneId.systemDefault()).format(FORMATTER);
    }
    /**
     * Retorna a data da atualização tarifária em formato brasileiro encurtado.
     *
     * @return data formatada
     */
    public String getAtualizacaoFormatada()
    {
		if (getDataAtualizacaoTarifario() instanceof java.sql.Date)
		{
			return ((java.sql.Date) getDataAtualizacaoTarifario()).toLocalDate().format(FORMATTER);
		}
        return getDataAtualizacaoTarifario().toInstant().atZone(ZoneId.systemDefault()).format(FORMATTER);
    }
    /**
     * Retorna a data de recisão em formato brasileiro encurtado.
     *
     * @return data formatada
     */
    public String getRescisaoFormatada()
    {
		if (getDataRescisao() == null)
		{
			return "";
		}
		if (getDataRescisao() instanceof java.sql.Date)
		{
			return ((java.sql.Date) getDataRescisao()).toLocalDate().format(FORMATTER);
		}
        return getDataRescisao().toInstant().atZone(ZoneId.systemDefault()).format(FORMATTER);
    }
    /**
     * Retorna a data de renovação em formato brasileiro encurtado.
     *
     * @return data formatada
     */
    public String getRenovacaoFormatada()
    {
		if (getDataRenovacao() instanceof java.sql.Date)
		{
			return ((java.sql.Date) getDataRenovacao()).toLocalDate().format(FORMATTER);
		}
        return getDataRenovacao().toInstant().atZone(ZoneId.systemDefault()).format(FORMATTER);
    }
    /**
     * Retorna a data de vigência em formato brasileiro encurtado.
     *
     * @return data formatada
     */
    public String getVigenciaFormatada()
    {
		if (getDataVigencia() == null)
		{
			return "";
		}
		if (getDataVigencia() instanceof java.sql.Date)
		{
			return ((java.sql.Date) getDataVigencia()).toLocalDate().format(FORMATTER);
		}
        return getDataVigencia().toInstant().atZone(ZoneId.systemDefault()).format(FORMATTER);
    }
}
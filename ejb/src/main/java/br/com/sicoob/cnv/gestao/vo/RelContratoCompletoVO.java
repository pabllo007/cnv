package br.com.sicoob.cnv.gestao.vo;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

import br.com.bancoob.negocio.entidades.BancoobEntidade;
import br.com.sicoob.cnv.gestao.util.converters.CnpjConverter;


/**
 * @author Phelipe.Palmeira
 *
 */
@Entity
@SqlResultSetMapping
(
    entities = @EntityResult(entityClass = RelContratoCompletoVO.class),
    name = "RelContratoCompletoVO"
)
public class RelContratoCompletoVO extends BancoobEntidade {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	private Long id;
	
	/** The valor situacao. */
	private String situacao;
	
	/** The valor dataRescisaoFormatada. */
	private String dataRescisaoFormatada;
	
	/** The valor numero. */
	private String numero;
	
	/** The valor cnpj. */
	@Convert(converter=CnpjConverter.class)
	private String cnpj;
	
	/** The valor sigla. */
	private String sigla;
	
	/** The valor dataAssinaturaContratoFormatada. */
	private String dataAssinaturaContratoFormatada;
	
	/** The valor dataVigenciaFormatada. */
	private String dataVigenciaFormatada;
	
	/** The valor dataRenovacaoFormatada. */
	private String dataRenovacaoFormatada;
	
	/** The valor descSituacaoRenovacao. */
	private String descSituacaoRenovacao;
	
	/** The valor alertaRenovacaoDias. */
	private String alertaRenovacaoDias;
	
	/** The valor acaoOperacionalRenovacao. */
	private String acaoOperacionalRenovacao;
	
	/** The valor dataAtualizacaoTarifarioFormatada. */
	private String dataAtualizacaoTarifarioFormatada;
	
	/** The valor descSituacaoTarifaria. */
	private String descSituacaoTarifaria;
	
	/** The valor alertaTarifariaDias. */
	private String alertaTarifariaDias;

	/** The valor acaoOperacionalTarifaria. */
	private String acaoOperacionalTarifaria;
	
	/**
	 * Gets the situacao
	 * 
	 * @return situacao
	 */
	public String getSituacao() {
		return situacao;
	}

	/**
	 * Sets the situacao
	 * 
	 * @param situacao
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	/**
	 * Gets the dataRescisaoFormatada
	 * 
	 * @return dataRescisaoFormatada
	 */
	public String getDataRescisaoFormatada() {
		return dataRescisaoFormatada;
	}

	/**
	 * Sets the dataRescisaoFormatada
	 * 
	 * @param dataRescisaoFormatada
	 */
	public void setDataRescisaoFormatada(String dataRescisaoFormatada) {
		this.dataRescisaoFormatada = dataRescisaoFormatada;
	}

	/**
	 * Gets the numero
	 * 
	 * @return numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Sets the numero
	 * 
	 * @param numero
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * Gets the cnpj
	 * 
	 * @return cnpj
	 */
	public String getCnpj() {
		return cnpj;
	}

	/**
	 * Sets the cnpj
	 * 
	 * @param cnpj
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * Gets the sigla
	 * 
	 * @return sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * Sets the situacao
	 * 
	 * @param sigla
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * Gets the dataAssinaturaContratoFormatada
	 * 
	 * @return dataAssinaturaContratoFormatada
	 */
	public String getDataAssinaturaContratoFormatada() {
		return dataAssinaturaContratoFormatada;
	}

	/**
	 * Sets the dataAssinaturaContratoFormatada
	 * 
	 * @param dataAssinaturaContratoFormatada
	 */
	public void setDataAssinaturaContratoFormatada(String dataAssinaturaContratoFormatada) {
		this.dataAssinaturaContratoFormatada = dataAssinaturaContratoFormatada;
	}

	/**
	 * Gets the dataVigenciaFormatada
	 * 
	 * @return dataVigenciaFormatada
	 */
	public String getDataVigenciaFormatada() {
		return dataVigenciaFormatada;
	}

	/**
	 * Sets the dataVigenciaFormatada
	 * 
	 * @param dataVigenciaFormatada
	 */
	public void setDataVigenciaFormatada(String dataVigenciaFormatada) {
		this.dataVigenciaFormatada = dataVigenciaFormatada;
	}

	/**
	 * Gets the dataRenovacaoFormatada
	 * 
	 * @return dataRenovacaoFormatada
	 */
	public String getDataRenovacaoFormatada() {
		return dataRenovacaoFormatada;
	}

	/**
	 * Sets the dataRenovacaoFormatada
	 * 
	 * @param dataRenovacaoFormatada
	 */
	public void setDataRenovacaoFormatada(String dataRenovacaoFormatada) {
		this.dataRenovacaoFormatada = dataRenovacaoFormatada;
	}

	/**
	 * Gets the descSituacaoRenovacao
	 * 
	 * @return descSituacaoRenovacao
	 */
	public String getDescSituacaoRenovacao() {
		return descSituacaoRenovacao;
	}

	/**
	 * Sets the descSituacaoRenovacao
	 * 
	 * @param descSituacaoRenovacao
	 */
	public void setDescSituacaoRenovacao(String descSituacaoRenovacao) {
		this.descSituacaoRenovacao = descSituacaoRenovacao;
	}

	/**
	 * Gets the alertaRenovacaoDias
	 * 
	 * @return alertaRenovacaoDias
	 */
	public String getAlertaRenovacaoDias() {
		return alertaRenovacaoDias;
	}

	/**
	 * Sets the alertaRenovacaoDias
	 * 
	 * @param alertaRenovacaoDias
	 */
	public void setAlertaRenovacaoDias(String alertaRenovacaoDias) {
		this.alertaRenovacaoDias = alertaRenovacaoDias;
	}

	/**
	 * Gets the dataAtualizacaoTarifarioFormatada
	 * 
	 * @return dataAtualizacaoTarifarioFormatada
	 */
	public String getDataAtualizacaoTarifarioFormatada() {
		return dataAtualizacaoTarifarioFormatada;
	}

	/**
	 * Sets the dataAtualizacaoTarifarioFormatada
	 * 
	 * @param dataAtualizacaoTarifarioFormatada
	 */
	public void setDataAtualizacaoTarifarioFormatada(String dataAtualizacaoTarifarioFormatada) {
		this.dataAtualizacaoTarifarioFormatada = dataAtualizacaoTarifarioFormatada;
	}

	/**
	 * Gets the descSituacaoTarifaria
	 * 
	 * @return descSituacaoTarifaria
	 */
	public String getDescSituacaoTarifaria() {
		return descSituacaoTarifaria;
	}

	/**
	 * Sets the descSituacaoTarifaria
	 * 
	 * @param descSituacaoTarifaria
	 */
	public void setDescSituacaoTarifaria(String descSituacaoTarifaria) {
		this.descSituacaoTarifaria = descSituacaoTarifaria;
	}

	/**
	 * Gets the alertaTarifariaDias
	 * 
	 * @return alertaTarifariaDias
	 */
	public String getAlertaTarifariaDias() {
		return alertaTarifariaDias;
	}

	/**
	 * Sets the alertaTarifariaDias
	 * 
	 * @param alertaTarifariaDias
	 */
	public void setAlertaTarifariaDias(String alertaTarifariaDias) {
		this.alertaTarifariaDias = alertaTarifariaDias;
	}

	/**
	 * Gets the acaoOperacionalRenovacao
	 * 
	 * @return acaoOperacionalRenovacao
	 */
	public String getAcaoOperacionalRenovacao() {
		return acaoOperacionalRenovacao;
	}

	/**
	 * Sets the acaoOperacionalRenovacao
	 * 
	 * @param acaoOperacionalRenovacao
	 */
	public void setAcaoOperacionalRenovacao(String acaoOperacionalRenovacao) {
		this.acaoOperacionalRenovacao = acaoOperacionalRenovacao;
	}

	/**
	 * Gets the acaoOperacionalTarifaria
	 * 
	 * @return acaoOperacionalTarifaria
	 */
	public String getAcaoOperacionalTarifaria() {
		return acaoOperacionalTarifaria;
	}

	/**
	 * Sets the acaoOperacionalTarifaria
	 * 
	 * @param acaoOperacionalTarifaria
	 */
	public void setAcaoOperacionalTarifaria(String acaoOperacionalTarifaria) {
		this.acaoOperacionalTarifaria = acaoOperacionalTarifaria;
	}
	
	
}

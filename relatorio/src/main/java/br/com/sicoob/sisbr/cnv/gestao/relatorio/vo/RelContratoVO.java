package br.com.sicoob.sisbr.cnv.gestao.relatorio.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.bancoob.negocio.entidades.BancoobEntidade;

/**
 * The Class RelRepasseFinanceiroVO.
 */
@Entity
public class RelContratoVO extends BancoobEntidade implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@Id
	private Long id;
	
	/** The valor idInstituicaoEmpresa. */
	private String idInstituicaoEmpresa;
	
	/** The valor idPessoaEmpresa */
	private String idPessoaEmpresa;
	
	/** The valor numero. */
	private String numero;
	
	/** The valor dataAssinaturaContratoFormatada. */
	private String dataAssinaturaContratoFormatada;
	
	/** The valor dataVigenciaFormatada. */
	private String dataVigenciaFormatada;
	
	/** The valor bolVigenciaIndeterminada. */
	private String bolVigenciaIndeterminada;
	
	/** The valor dataRenovacaoFormatada. */
	private String dataRenovacaoFormatada;
	
	/** The valor dataAtualizacaoTarifarioFormatada. */
	private String dataAtualizacaoTarifarioFormatada;
	
	/** The valor alertaRenovacaoDias. */
	private String alertaRenovacaoDias;
	
	/** The valor alertaTarifariaDias. */
	private String alertaTarifariaDias;
	
	/** The valor dataRescisaoFormatada. */
	private String dataRescisaoFormatada;
	
	/** The valor descSituacaoRenovacao. */
	private String descSituacaoRenovacao;
	
	/** The valor descSituacaoTarifaria. */
	private String descSituacaoTarifaria;
	
	/** The valor sigla. */
	private String sigla;
	
	/** The valor cnpj. */
	private String cnpj;
	
	/** The valor situacao. */
	private String situacao;
	
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
	 * @param id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the idInstituicaoEmpresa.
	 *
	 * @return the idInstituicaoEmpresa
	 */
	public String getIdInstituicaoEmpresa() {
		return idInstituicaoEmpresa;
	}

	/**
	 * Sets the idInstituicaoEmpresa.
	 *
	 * @param idInstituicaoEmpresa to set
	 */
	public void setIdInstituicaoEmpresa(String idInstituicaoEmpresa) {
		this.idInstituicaoEmpresa = idInstituicaoEmpresa;
	}

	/**
	 * Gets the idPessoaEmpresa.
	 *
	 * @return the idPessoaEmpresa
	 */
	public String getIdPessoaEmpresa() {
		return idPessoaEmpresa;
	}

	/**
	 * Sets the idPessoaEmpresa.
	 *
	 * @param idPessoaEmpresa to set
	 */
	public void setIdPessoaEmpresa(String idPessoaEmpresa) {
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
	 * @param numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * Gets the dataAssinaturaContratoFormatada.
	 *
	 * @return the dataAssinaturaContratoFormatada
	 */
	public String getDataAssinaturaContratoFormatada() {
		return dataAssinaturaContratoFormatada;
	}

	/**
	 * Sets the dataAssinaturaContratoFormatada.
	 *
	 * @param dataAssinaturaContratoFormatada to set
	 */
	public void setDataAssinaturaContratoFormatada(String dataAssinaturaContratoFormatada) {
		this.dataAssinaturaContratoFormatada = dataAssinaturaContratoFormatada;
	}

	/**
	 * Gets the dataVigenciaFormatada.
	 *
	 * @return the dataVigenciaFormatada
	 */
	public String getDataVigenciaFormatada() {
		return dataVigenciaFormatada;
	}

	/**
	 * Sets the dataVigenciaFormatada.
	 *
	 * @param dataVigenciaFormatada to set
	 */
	public void setDataVigenciaFormatada(String dataVigenciaFormatada) {
		this.dataVigenciaFormatada = dataVigenciaFormatada;
	}

	/**
	 * Gets the bolVigenciaIndeterminada.
	 *
	 * @return the bolVigenciaIndeterminada
	 */
	public String getBolVigenciaIndeterminada() {
		return bolVigenciaIndeterminada;
	}

	/**
	 * Sets the bolVigenciaIndeterminada.
	 *
	 * @param bolVigenciaIndeterminada to set
	 */
	public void setBolVigenciaIndeterminada(String bolVigenciaIndeterminada) {
		this.bolVigenciaIndeterminada = bolVigenciaIndeterminada;
	}

	/**
	 * Gets the dataRenovacaoFormatada.
	 *
	 * @return the dataRenovacaoFormatada
	 */
	public String getDataRenovacaoFormatada() {
		return dataRenovacaoFormatada;
	}

	/**
	 * Sets the dataRenovacaoFormatada.
	 *
	 * @param dataRenovacaoFormatada to set
	 */
	public void setDataRenovacaoFormatada(String dataRenovacaoFormatada) {
		this.dataRenovacaoFormatada = dataRenovacaoFormatada;
	}

	/**
	 * Gets the dataAtualizacaoTarifarioFormatada.
	 *
	 * @return the dataAtualizacaoTarifarioFormatada
	 */
	public String getDataAtualizacaoTarifarioFormatada() {
		return dataAtualizacaoTarifarioFormatada;
	}

	/**
	 * Sets the dataAtualizacaoTarifarioFormatada.
	 *
	 * @param dataAtualizacaoTarifarioFormatada to set
	 */
	public void setDataAtualizacaoTarifarioFormatada(String dataAtualizacaoTarifarioFormatada) {
		this.dataAtualizacaoTarifarioFormatada = dataAtualizacaoTarifarioFormatada;
	}

	/**
	 * Gets the alertaRenovacaoDias.
	 *
	 * @return the alertaRenovacaoDias
	 */
	public String getAlertaRenovacaoDias() {
		return alertaRenovacaoDias;
	}

	/**
	 * Sets the alertaRenovacaoDias.
	 *
	 * @param alertaRenovacaoDias to set
	 */
	public void setAlertaRenovacaoDias(String alertaRenovacaoDias) {
		this.alertaRenovacaoDias = alertaRenovacaoDias;
	}

	/**
	 * Gets the alertaTarifariaDias.
	 *
	 * @return the alertaTarifariaDias
	 */
	public String getAlertaTarifariaDias() {
		return alertaTarifariaDias;
	}

	/**
	 * Sets the alertaTarifariaDias.
	 *
	 * @param alertaTarifariaDias to set
	 */
	public void setAlertaTarifariaDias(String alertaTarifariaDias) {
		this.alertaTarifariaDias = alertaTarifariaDias;
	}

	/**
	 * Gets the dataRescisaoFormatada.
	 *
	 * @return the dataRescisaoFormatada
	 */
	public String getDataRescisaoFormatada() {
		return dataRescisaoFormatada;
	}

	/**
	 * Sets the dataRescisaoFormatada.
	 *
	 * @param dataRescisaoFormatada to set
	 */
	public void setDataRescisaoFormatada(String dataRescisaoFormatada) {
		this.dataRescisaoFormatada = dataRescisaoFormatada;
	}

	/**
	 * Gets the descSituacaoRenovacao.
	 *
	 * @return the descSituacaoRenovacao
	 */
	public String getDescSituacaoRenovacao() {
		return descSituacaoRenovacao;
	}

	/**
	 * Sets the descSituacaoRenovacao.
	 *
	 * @param descSituacaoRenovacao to set
	 */
	public void setDescSituacaoRenovacao(String descSituacaoRenovacao) {
		this.descSituacaoRenovacao = descSituacaoRenovacao;
	}

	/**
	 * Gets the descSituacaoTarifaria.
	 *
	 * @return the descSituacaoTarifaria
	 */
	public String getDescSituacaoTarifaria() {
		return descSituacaoTarifaria;
	}

	/**
	 * Sets the descSituacaoTarifaria.
	 *
	 * @param descSituacaoTarifaria to set
	 */
	public void setDescSituacaoTarifaria(String descSituacaoTarifaria) {
		this.descSituacaoTarifaria = descSituacaoTarifaria;
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
	 * @param sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * Gets the cnpj.
	 *
	 * @return the cnpj
	 */
	public String getCnpj() {
		return cnpj;
	}

	/**
	 * Sets the cnpj.
	 *
	 * @param cnpj to set
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * Gets the situacao.
	 *
	 * @return the situacao
	 */
	public String getSituacao() {
		return situacao;
	}

	/**
	 * Sets the situacao.
	 *
	 * @param situacao to set
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	
}

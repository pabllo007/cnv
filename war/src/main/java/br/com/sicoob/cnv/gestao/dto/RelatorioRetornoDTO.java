package br.com.sicoob.cnv.gestao.dto;

import java.io.Serializable;

public class RelatorioRetornoDTO implements Serializable {

	private static final long serialVersionUID = 6878320264097224264L;
	private Long id;
	private Long idInstituicaoEmpresa;
	private Long idPessoaEmpresa;
	private String numero;
	private String dataAssinaturaContratoFormatada;
	private String dataVigenciaFormatada;
	private Boolean bolVigenciaIndeterminada;
	private String dataRenovacaoFormatada;
	private String dataAtualizacaoTarifarioFormatada;
	private Long alertaRenovacaoDias;
	private Long alertaTarifariaDias;
	private String dataRescisaoFormatada;
	private String descJustificativaRescisao;
	private String descSituacaoRenovacao;
	private Boolean acaoOperacionalRenovacao;
	private String descSituacaoTarifaria;
	private Boolean acaoOperacionalTarifaria;
	private String sigla;
	private String cnpj;
	private String situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdInstituicaoEmpresa() {
		return idInstituicaoEmpresa;
	}

	public void setIdInstituicaoEmpresa(Long idInstituicaoEmpresa) {
		this.idInstituicaoEmpresa = idInstituicaoEmpresa;
	}

	public Long getIdPessoaEmpresa() {
		return idPessoaEmpresa;
	}

	public void setIdPessoaEmpresa(Long idPessoaEmpresa) {
		this.idPessoaEmpresa = idPessoaEmpresa;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDataAssinaturaContratoFormatada() {
		return dataAssinaturaContratoFormatada;
	}

	public void setDataAssinaturaContratoFormatada(String dataAssinaturaContratoFormatada) {
		this.dataAssinaturaContratoFormatada = dataAssinaturaContratoFormatada;
	}

	public String getDataVigenciaFormatada() {
		return dataVigenciaFormatada;
	}

	public void setDataVigenciaFormatada(String dataVigenciaFormatada) {
		this.dataVigenciaFormatada = dataVigenciaFormatada;
	}

	public Boolean getBolVigenciaIndeterminada() {
		return bolVigenciaIndeterminada;
	}

	public void setBolVigenciaIndeterminada(Boolean bolVigenciaIndeterminada) {
		this.bolVigenciaIndeterminada = bolVigenciaIndeterminada;
	}

	public String getDataRenovacaoFormatada() {
		return dataRenovacaoFormatada;
	}

	public void setDataRenovacaoFormatada(String dataRenovacaoFormatada) {
		this.dataRenovacaoFormatada = dataRenovacaoFormatada;
	}

	public String getDataAtualizacaoTarifarioFormatada() {
		return dataAtualizacaoTarifarioFormatada;
	}

	public void setDataAtualizacaoTarifarioFormatada(String dataAtualizacaoTarifarioFormatada) {
		this.dataAtualizacaoTarifarioFormatada = dataAtualizacaoTarifarioFormatada;
	}

	public Long getAlertaRenovacaoDias() {
		return alertaRenovacaoDias;
	}

	public void setAlertaRenovacaoDias(Long alertaRenovacaoDias) {
		this.alertaRenovacaoDias = alertaRenovacaoDias;
	}

	public Long getAlertaTarifariaDias() {
		return alertaTarifariaDias;
	}

	public void setAlertaTarifariaDias(Long alertaTarifariaDias) {
		this.alertaTarifariaDias = alertaTarifariaDias;
	}

	public String getDataRescisaoFormatada() {
		return dataRescisaoFormatada;
	}

	public void setDataRescisaoFormatada(String dataRescisaoFormatada) {
		this.dataRescisaoFormatada = dataRescisaoFormatada;
	}

	public String getDescJustificativaRescisao() {
		return descJustificativaRescisao;
	}

	public void setDescJustificativaRescisao(String descJustificativaRescisao) {
		this.descJustificativaRescisao = descJustificativaRescisao;
	}

	public String getDescSituacaoRenovacao() {
		return descSituacaoRenovacao;
	}

	public void setDescSituacaoRenovacao(String descSituacaoRenovacao) {
		this.descSituacaoRenovacao = descSituacaoRenovacao;
	}

	public Boolean getAcaoOperacionalRenovacao() {
		return acaoOperacionalRenovacao;
	}

	public void setAcaoOperacionalRenovacao(Boolean acaoOperacionalRenovacao) {
		this.acaoOperacionalRenovacao = acaoOperacionalRenovacao;
	}

	public String getDescSituacaoTarifaria() {
		return descSituacaoTarifaria;
	}

	public void setDescSituacaoTarifaria(String descSituacaoTarifaria) {
		this.descSituacaoTarifaria = descSituacaoTarifaria;
	}

	public Boolean getAcaoOperacionalTarifaria() {
		return acaoOperacionalTarifaria;
	}

	public void setAcaoOperacionalTarifaria(Boolean acaoOperacionalTarifaria) {
		this.acaoOperacionalTarifaria = acaoOperacionalTarifaria;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getCnpj() {
		if (cnpj.length() == 14) {
			return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/"
					+ cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
		}
		return "";
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSituacao() {
		return dataRescisaoFormatada != null ? "ATIVO" : "ENCERRADO";
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}

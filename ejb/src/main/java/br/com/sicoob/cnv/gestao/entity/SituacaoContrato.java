package br.com.sicoob.cnv.gestao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.bancoob.negocio.entidades.BancoobEntidade;

/**
 * The Class SituacaoContrato.
 *
 * @author Luis.Fernandez
 */
@Entity
@Table(name = "SITUACAOCONTRATO", schema = "CNV")
public class SituacaoContrato extends BancoobEntidade {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8507183014499300222L;
	public final static SituacaoContrato RENOVACAO = new SituacaoContrato(3l), VENCIDO = new SituacaoContrato(2l),
		VIGENTE = new SituacaoContrato(1l);

	/** The id. */
	@Id
	@Column(name = "IDSITUACAOCONTRATO")
	private Long id;

	/** The descricao. */
	@Column(name = "DESCSITUACAOCONTRATO")
	private String descricao;

	/**
	 * Instancia uma situação.
	 */
	public SituacaoContrato() { }
	/**
	 * Instancia uma situação com o ID.
	 *
	 * @param id ID da situação
	 */
	public SituacaoContrato(Long id)
	{
		setId(id);
	}
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
	 * Gets the descricao.
	 *
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Sets the descricao.
	 *
	 * @param descricao the new descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



}

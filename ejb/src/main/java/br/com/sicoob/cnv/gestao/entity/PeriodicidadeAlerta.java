package br.com.sicoob.cnv.gestao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.bancoob.negocio.entidades.BancoobEntidade;

/**
 * The Class PeriodicidadeAlerta.
 *
 * @author Luis.Fernandez
 */
@Entity
@Table(name = "PERIODICIDADEALERTA", schema = "CNV")
public class PeriodicidadeAlerta extends BancoobEntidade {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5450438492544867140L;

	/** The id. */
	@Id
	@Column(name = "IDPERIODICIDADEALERTA")
	private Long id;

	/** The desricao. */
	@Column(name = "DESCPERIODICIDADEALERTA")
	private String descricao;

	/** The dias. */
	@Column(name = "QTDDIA")
	private Integer dias;

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
	 * Gets the dias.
	 *
	 * @return the dias
	 */
	public Integer getDias() {
		return dias;
	}

	/**
	 * Sets the dias.
	 *
	 * @param dias the new dias
	 */
	public void setDias(Integer dias) {
		this.dias = dias;
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

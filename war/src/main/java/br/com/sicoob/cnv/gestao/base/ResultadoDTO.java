package br.com.sicoob.cnv.gestao.base;

import java.io.Serializable;

/**
 * Classe de DTO para padronizacao Open Banking.
 *
 * @param <T> the generic type
 *
 * @author Luis.FernandezS
 */
public class ResultadoDTO<T> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The resultado. */
	private T resultado;

	/**
	 * Instantiates a new resultado DTO.
	 */
	public ResultadoDTO() {
	}

	/**
	 * Instantiates a new resultado DTO.
	 *
	 * @param resultado the resultado
	 */
	public ResultadoDTO(T resultado) {
		this.resultado = resultado;
	}

	/**
	 * Gets the resultado.
	 *
	 * @return the resultado
	 */
	public T getResultado() {
		return resultado;
	}

	/**
	 * Sets the resultado.
	 *
	 * @param resultado the new resultado
	 */
	public void setResultado(T resultado) {
		this.resultado = resultado;
	}

}

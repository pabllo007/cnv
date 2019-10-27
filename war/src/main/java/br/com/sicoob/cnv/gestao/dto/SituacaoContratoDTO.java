package br.com.sicoob.cnv.gestao.dto;

import java.io.Serializable;
import java.util.List;

import br.com.sicoob.cnv.gestao.entity.SituacaoContrato;

/**
 * The Class SituacaoContratoDTO.
 *
 * @author Luis.Fernandez
 */
public class SituacaoContratoDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6016402938800755100L;

	/** The lista. */
	private List<SituacaoContrato> lista;

	/**
	 * Instantiates a new situacao contrato DTO.
	 *
	 * @param lista the lista
	 */
	public SituacaoContratoDTO(List<SituacaoContrato> lista) {
		this.lista = lista;
	}

	/**
	 * Gets the lista.
	 *
	 * @return the lista
	 */
	public List<SituacaoContrato> getLista() {
		return lista;
	}

	/**
	 * Sets the lista.
	 *
	 * @param lista the new lista
	 */
	public void setLista(List<SituacaoContrato> lista) {
		this.lista = lista;
	}

}

package br.com.sicoob.cnv.gestao.dto;

import java.io.Serializable;
import java.util.List;

import br.com.sicoob.cnv.gestao.entity.PeriodicidadeAlerta;

/**
 * The Class PeriodicidadeAlertaDTO.
 *
 * @author Luis.Fernandez
 */
public class PeriodicidadeAlertaDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6674343851233879268L;

	/** The lista. */
	private List<PeriodicidadeAlerta> lista;

	/**
	 * Instantiates a new periodicidade alerta DTO.
	 *
	 * @param lista the lista
	 */
	public PeriodicidadeAlertaDTO(List<PeriodicidadeAlerta> lista) {
		this.lista = lista;
	}

	/**
	 * Gets the lista.
	 *
	 * @return the lista
	 */
	public List<PeriodicidadeAlerta> getLista() {
		return lista;
	}

	/**
	 * Sets the lista.
	 *
	 * @param lista the new lista
	 */
	public void setLista(List<PeriodicidadeAlerta> lista) {
		this.lista = lista;
	}

}

package br.com.sicoob.cnv.gestao.vo;

import java.io.Serializable;


public class RelatorioFiltroContratoVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dataInicioPeriodo;
	private String dataFimPeriodo;
	private Integer numRelatorio;

	/**
	 * @return the dataInicioPeriodo
	 */
	public String getDataInicioPeriodo() {
		return dataInicioPeriodo;
	}
	
	/**
	 * @param dataInicioPeriodo the dataInicioPeriodo to set
	 */
	public void setDataInicioPeriodo(String dataInicioPeriodo) {
		this.dataInicioPeriodo = dataInicioPeriodo;
	}
	
	/**
	 * @return the dataFimPeriodo
	 */
	public String getDataFimPeriodo() {
		return dataFimPeriodo;
	}
	
	/**
	 * @param dataFimPeriodo the dataFimPeriodo to set
	 */
	public void setDataFimPeriodo(String dataFimPeriodo) {
		this.dataFimPeriodo = dataFimPeriodo;
	}
	
	/**
	 * @return the numRelatorio
	 */
	public Integer getNumRelatorio() {
		return numRelatorio;
	}
	
	/**
	 * @param numRelatorio the numRelatorio to set
	 */
	public void setNumRelatorio(Integer numRelatorio) {
		this.numRelatorio = numRelatorio;
	}
	
	
}

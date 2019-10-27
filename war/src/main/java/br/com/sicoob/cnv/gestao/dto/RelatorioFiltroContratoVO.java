package br.com.sicoob.cnv.gestao.dto;

import java.io.Serializable;

import br.com.sicoob.cnv.framework.rest.converter.DateParam;

public class RelatorioFiltroContratoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DateParam dataInicioPeriodo;
	private DateParam dataFimPeriodo;

	public DateParam getDataInicioPeriodo() {
		return dataInicioPeriodo;
	}

	public void setDataInicioPeriodo(DateParam dataInicioPeriodo) {
		this.dataInicioPeriodo = dataInicioPeriodo;
	}

	public DateParam getDataFimPeriodo() {
		return dataFimPeriodo;
	}

	public void setDataFimPeriodo(DateParam dataFimPeriodo) {
		this.dataFimPeriodo = dataFimPeriodo;
	}

}

package br.com.sicoob.sisbr.cnv.gestao.relatorio.util;

import java.util.Map;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.relatorio.api.jasper.SicoobJasperReports;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * Classe responsável por gerar os relatórios do sistema.
 *
 * @author Samuel Correia Guimaraes - CTIS
 */
public class GeradorRelatorios extends SicoobJasperReports {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The data source. */
	private final transient JRDataSource dataSource;

	/**
	 * Construtor que recebe as informações necessárias para gerar o relatório.
	 *
	 * @param caminhoRelatorio            Caminho (URL) do arquivo jasper referente ao relatório a ser
	 *            gerado
	 * @param dataSource            Fonte de dados a ser utilizado pelo relatório
	 * @param parametros            Parâmetros do relatório
	 * @throws BancoobException             Caso ocorra algum erro ao tentar gerar o relatório
	 */
	public GeradorRelatorios(String caminhoRelatorio, JRDataSource dataSource, Map<String, Object> parametros) throws BancoobException {
		super(caminhoRelatorio);
		this.dataSource = dataSource;
		if (parametros != null) {
			getParametros().putAll(parametros);
		}
	}

	/* (non-Javadoc)
	 * @see br.com.sicoob.relatorio.api.jasper.SicoobJasperReports#getDataSource()
	 */
	@Override
	protected JRDataSource getDataSource() {
		return this.dataSource;
	}

}
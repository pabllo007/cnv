package br.com.sicoob.sisbr.cnv.gestao.relatorio.util;

import java.util.Map;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.relatorio.api.jasper.SicoobJasperReports;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * Classe respons�vel por gerar os relat�rios do sistema.
 *
 * @author Samuel Correia Guimaraes - CTIS
 */
public class GeradorRelatorios extends SicoobJasperReports {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The data source. */
	private final transient JRDataSource dataSource;

	/**
	 * Construtor que recebe as informa��es necess�rias para gerar o relat�rio.
	 *
	 * @param caminhoRelatorio            Caminho (URL) do arquivo jasper referente ao relat�rio a ser
	 *            gerado
	 * @param dataSource            Fonte de dados a ser utilizado pelo relat�rio
	 * @param parametros            Par�metros do relat�rio
	 * @throws BancoobException             Caso ocorra algum erro ao tentar gerar o relat�rio
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
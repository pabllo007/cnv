package br.com.sicoob.sisbr.cnv.gestao.relatorio.ejb;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.negocio.servicos.ejb.BancoobServicoEJB;
import br.com.sicoob.relatorio.api.dto.ParametroDTO;
import br.com.sicoob.relatorio.api.dto.RelatorioDadosDTO;
import br.com.sicoob.relatorio.api.dto.RetornoProcessamentoRelatorioDTO;
import br.com.sicoob.relatorio.api.interfaces.IProcessamentoRelatorioServico;
import br.com.sicoob.sisbr.cnv.gestao.relatorio.enums.RelatorioGestaoContratoEnum;
import br.com.sicoob.sisbr.cnv.gestao.relatorio.util.GeradorRelatorios;
import br.com.sicoob.tipos.DateTime;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * The Class RelatorioServicoAbstract.
 *
 * @author Humberto.Ribeiro
 * @param <T> the generic type
 */
public abstract class RelatorioServicoAbstract<T> extends BancoobServicoEJB implements IProcessamentoRelatorioServico {

	/** The Constant URL_LOGO_SICOOB. */
	private static final String URL_LOGO_SICOOB = "urlLogoSicoob";
	
	/** The Constant SICOOB_JPG. */
	private static final String SICOOB_JPG = "sicoob.jpg";

	/** The parametro DTO. */
	private ParametroDTO parametroDTO;
	
	/** The relatorio dados DTO. */
	private RelatorioDadosDTO relatorioDadosDTO;
	
	/** The parametros. */
	private Map<String, Object> parametros;

	/* (non-Javadoc)
	 * @see br.com.sicoob.relatorio.api.interfaces.IProcessamentoRelatorioServico#gerarRelatorio(br.com.sicoob.relatorio.api.dto.ParametroDTO, br.com.sicoob.relatorio.api.dto.RelatorioDadosDTO)
	 */
	@Override
	public RetornoProcessamentoRelatorioDTO gerarRelatorio(ParametroDTO paramDto, RelatorioDadosDTO rDto)
			throws BancoobException {
		this.parametroDTO = paramDto;
		this.relatorioDadosDTO = rDto;
		this.parametros = criarMapaParametrosPadrao();
		String caminhoArquivo = getCaminhoArquivoJasper();
		JRDataSource dataSource = getDataSource();
		GeradorRelatorios geradorRelatorio = new GeradorRelatorios(caminhoArquivo, dataSource, parametros);
		return geradorRelatorio.gerarRelatorio(rDto);
	}

	/**
	 * Retorna os dados a serem enviados ao relatório.
	 *
	 * @return Retorna os dados a serem gerados pelo relatório.
	 * @throws BancoobException             Caso ocorra algum erro ao tentar recuperar os dados a serem
	 *             gerados
	 */
	protected abstract List<T> getDadosRelatorio() throws BancoobException;
	
	/**
	 * Obter relatorio enum.
	 *
	 * @return the relatorios enum
	 */
	protected abstract RelatorioGestaoContratoEnum obterRelatorioEnum();

	/**
	 * Gets the caminho arquivo jasper.
	 *
	 * @return the caminho arquivo jasper
	 */
	protected String getCaminhoArquivoJasper() {
		return obterRelatorioEnum().getCaminhoArquivoJasper();
	}

	/**
	 * Adds the parametro.
	 *
	 * @param chave the chave
	 * @param valor the valor
	 */
	protected void addParametro(String chave, Object valor) {
		parametros.put(chave, valor);
	}

	/**
	 * Retorna o valor do parâmetro com a chave informada.
	 *
	 * @param chave the chave
	 * @return the object
	 */
	protected final Object obterParametro(String chave) {
		return parametroDTO.getDados().get(chave);
	}

	/**
	 * Retorna o valor do parâmetro com a chave informada.
	 *
	 * @param chave the chave
	 * @return the string
	 */
	protected final String obterParametroTexto(String chave) {
		return (String) parametroDTO.getDados().get(chave);
	}

	/**
	 * Retorna o valor do parâmetro com a chave informada.
	 *
	 * @param chave the chave
	 * @return the boolean
	 */
	protected final Boolean obterParametroBoolean(String chave) {
		return (Boolean) parametroDTO.getDados().get(chave);
	}

	/**
	 * Retorna o valor do parâmetro com a chave informada.
	 *
	 * @param chave the chave
	 * @return the number
	 */
	private Number obterParametroNumber(String chave) {
		return (Number) parametroDTO.getDados().get(chave);
	}

	/**
	 * Retorna o valor do parâmetro com a chave informada.
	 *
	 * @param chave the chave
	 * @return the integer
	 */
	protected final Integer obterParametroInt(String chave) {
		Number numero = obterParametroNumber(chave);
		if (numero != null) {
			return obterParametroNumber(chave).intValue();
		}
		return null;
	}

	/**
	 * Retorna o valor do parâmetro com a chave informada.
	 *
	 * @param chave the chave
	 * @return the double
	 */
	protected final Double obterParametroDouble(String chave) {
		return obterParametroNumber(chave).doubleValue();
	}

	/**
	 * Retorna o valor do parâmetro com a chave informada.
	 *
	 * @param chave the chave
	 * @return the big decimal
	 */
	protected final BigDecimal obterParametroBigDecimal(String chave) {
		Number num = obterParametroNumber(chave);
		if (num instanceof Integer) {
			return BigDecimal.valueOf(num.intValue());
		} else {
			return BigDecimal.valueOf(num.doubleValue());
		}
	}

	/**
	 * Retorna o valor do parâmetro com a chave informada.
	 *
	 * @param chave the chave
	 * @return the date time
	 */
	protected final DateTime obterParametroDate(String chave) {
		Date param = (Date) obterParametro(chave);
		DateTime data = null;
		if (param != null) {
			data = new DateTime(param.getTime());
		}
		return data;
	}
	
	/**
	 * Adicionar logo.
	 */
	protected void adicionarLogo(){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if(parametros == null) {
			parametros = new HashMap<>();
		}
		parametros.put(URL_LOGO_SICOOB, classLoader.getResource(SICOOB_JPG));
	}
	
	/**
	 * Cria e retorna a fonte de dados a ser utilizada pelo relatório.
	 *
	 * @return the data source
	 * @throws BancoobException the bancoob exception
	 */
	private JRDataSource getDataSource() throws BancoobException {
		List<T> dadosRelatorio = getDadosRelatorio();
		JRDataSource dataSource;
		if (dadosRelatorio != null && !dadosRelatorio.isEmpty()) {
			dataSource = new JRBeanCollectionDataSource(dadosRelatorio);
		} else {
			dataSource = new JREmptyDataSource();
		}
		return dataSource;
	}
	
	/**
	 * Criar mapa parametros padrao.
	 *
	 * @return the map
	 * @throws BancoobException the bancoob exception
	 */
	private Map<String, Object> criarMapaParametrosPadrao() throws BancoobException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Map<String, Object> parametros = new HashMap<>();
		parametros.put(URL_LOGO_SICOOB, classLoader.getResource(SICOOB_JPG));
		return parametros;
	}

	/**
	 * Gets the relatorio dados DTO.
	 *
	 * @return the relatorioDadosDTO
	 */
	public RelatorioDadosDTO getRelatorioDadosDTO() {
		return relatorioDadosDTO;
	}

}

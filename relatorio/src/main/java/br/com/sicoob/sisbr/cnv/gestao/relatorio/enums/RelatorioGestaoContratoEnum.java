package br.com.sicoob.sisbr.cnv.gestao.relatorio.enums;

/**
 * The Enum RelatoriosEnum.
 */
public enum RelatorioGestaoContratoEnum {
	/** The relatorio repasse financeiro. */
	RELATORIO_TODO("RELATÓRIO - TODOS" , "relatorioTodos.jasper", 0),
	RELATORIO_VIGENTES("RELATÓRIO - VIGENTES" , "relatorioContratosVigentes.jasper", 1),
	RELATORIO_ENCERRADO("RELATÓRIO - ENCERRADOS" , "relatorioContratosEncerrados.jasper",2),
	RELATORIO_RENOVACAO("RELATÓRIO - RENOVAÇÃO" ,	"relatorioDataRenovacao.jasper",3),
	RELATORIO_VENCIDOS("RELATÓRIO - VENCIDOS" , "relatorioDataRenovacao.jasper",4),
	RELATORIO_TARIFARIO("RELATÓRIO - ATUALIZAÇÃO TARIFÁRIA" ,"relatorioAtualizacaoTarifaria.jasper",5);
	
	/** The nome arquivo jasper. */
	private String nomeArquivoJasper;

	/** The caminho arquivo jasper. */
	private String caminhoArquivoJasper;
	
	/** The tipo relatorio jasper. */
	private Integer tipoRelatorio;

	/**
	 * Instantiates a new relatorios enum.
	 *
	 * @param nomeArquivoJasper    the nome arquivo jasper
	 * @param caminhoArquivoJasper the caminho arquivo jasper
	 */
	private RelatorioGestaoContratoEnum(String nomeArquivoJasper, String caminhoArquivoJasper, Integer tipoRelatorio) {
		this.nomeArquivoJasper = nomeArquivoJasper;
		this.caminhoArquivoJasper = caminhoArquivoJasper;
		this.tipoRelatorio = tipoRelatorio;
	}

	/**
	 * Gets the nome arquivo jasper.
	 *
	 * @return the nomeArquivoJasper
	 */
	public String getNomeArquivoJasper() {
		return nomeArquivoJasper;
	}

	/**
	 * Gets the caminho arquivo jasper.
	 *
	 * @return the caminhoArquivoJasper
	 */
	public String getCaminhoArquivoJasper() {
		return caminhoArquivoJasper;
	}

	public static RelatorioGestaoContratoEnum recuperar(Integer tipoRelatorio) {
		RelatorioGestaoContratoEnum retorno = null;
		for (RelatorioGestaoContratoEnum rel : RelatorioGestaoContratoEnum.values()) {
			if(rel.getTipoRelatorio().intValue() == tipoRelatorio.intValue()) {
				retorno = rel;
				break;
			}
		}
		return retorno;
	}

	public Integer getTipoRelatorio() {
		return tipoRelatorio;
	}

}

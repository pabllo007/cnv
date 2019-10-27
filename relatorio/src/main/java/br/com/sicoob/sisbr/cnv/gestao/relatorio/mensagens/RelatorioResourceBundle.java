package br.com.sicoob.sisbr.cnv.gestao.relatorio.mensagens;

import java.util.ResourceBundle;

import br.com.bancoob.infraestrutura.mensagens.BancoobResourceBundle;
import br.com.bancoob.infraestrutura.mensagens.CorporativoResourceBundle;

/**
 * Classe responsavel pela carga das mensagens do sistema Processamento.
 */
public class RelatorioResourceBundle extends BancoobResourceBundle {

	/** Nome do arquivo de mensagens do sistema "Processamento". */
	public static final String RELATORIO_SICOOB_PROPERTIES = "cnv_relatorio_pt_BR.properties";

	/** Resource bundle a ser usada. */
	private static volatile RelatorioResourceBundle bundle;

	/**
	 * Gets the single instance of RelatorioResourceBundle.
	 *
	 * @return o bundle a ser usado.
	 */
	public static RelatorioResourceBundle getInstance() {
		if (bundle == null) {
			synchronized (RelatorioResourceBundle.class) {
				if (bundle == null) {
					bundle = new RelatorioResourceBundle();
				}
			}
		}
		return bundle;
	}

	/**
	 * Inicia o bundle com as suas propriedades padrao.
	 */
	private RelatorioResourceBundle() {
		this(RELATORIO_SICOOB_PROPERTIES);
	}

	/**
	 * Instantiates a new relatorio resource bundle.
	 *
	 * @param arquivoProperties the arquivo properties
	 */
	protected RelatorioResourceBundle(String arquivoProperties) {
		this(arquivoProperties, CorporativoResourceBundle.getInstance());
	}

	/**
	 * Instantiates a new relatorio resource bundle.
	 *
	 * @param arquivoProperties the arquivo properties
	 * @param resourcePai the resource pai
	 */
	protected RelatorioResourceBundle(String arquivoProperties, ResourceBundle resourcePai) {
		super(arquivoProperties, resourcePai);
	}
}

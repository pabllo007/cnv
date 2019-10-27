package br.com.sicoob.cnv.gestao.mensagens;

import java.util.ResourceBundle;

import br.com.bancoob.infraestrutura.mensagens.BancoobResourceBundle;

/**
 * Classe responsavel pela carga das mensagens do sistema
 *
 * @author Luis.Fernandez
 */
public class MensagensResourceBundle extends BancoobResourceBundle {

	/** Nome do arquivo de mensagens do sistema ". */
	public static final String PROPERTIES = "mensagens";

	/** Resource bundle a ser usada. */
	private static final MensagensResourceBundle BUNDLE = new MensagensResourceBundle();

	/**
	 *
	 * @return o bundle a ser usado.
	 */
	public static MensagensResourceBundle getInstance() {
		return BUNDLE;
	}

	/**
	 * Inicia o bundle com as suas propriedades padrao.
	 */
	private MensagensResourceBundle() {
		this(PROPERTIES);
	}

	/**
	 * @param arquivoProperties
	 */
	protected MensagensResourceBundle(String arquivoProperties) {
		this(arquivoProperties, MensagensResourceBundle.getInstance());
	}

	/**
	 * @param arquivoProperties
	 * @param resourcePai
	 */
	protected MensagensResourceBundle(String arquivoProperties, ResourceBundle resourcePai) {
		super(arquivoProperties, resourcePai);
	}
}

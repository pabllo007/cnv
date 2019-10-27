package br.com.sicoob.sisbr.cnv.gestao.relatorio.excecao;

import br.com.bancoob.excecao.BancoobException;

/**
 * The Class RelatorioException.
 *
 * @author marcus.amaro
 * 
 *         Excecao padrao para o sistema Financeiro.
 */
public class RelatorioException extends BancoobException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new relatorio exception.
	 *
	 * @param chave the chave
	 */
	public RelatorioException(String chave) {
		super(chave);
	}

	/**
	 * Instantiates a new relatorio exception.
	 *
	 * @param chave the chave
	 * @param parametros the parametros
	 * @param excecao the excecao
	 */
	public RelatorioException(String chave, Object[] parametros, Throwable excecao) {
		super(chave, parametros, excecao);
	}

	/**
	 * Instantiates a new relatorio exception.
	 *
	 * @param chave the chave
	 * @param excecao the excecao
	 */
	public RelatorioException(String chave, Throwable excecao) {
		super(chave, excecao);
	}

	/**
	 * Instantiates a new relatorio exception.
	 *
	 * @param excecao the excecao
	 */
	public RelatorioException(Throwable excecao) {
		super(excecao);
	}
}

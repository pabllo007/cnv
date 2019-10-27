package br.com.sicoob.sisbr.cnv.gestao.relatorio.excecao;

import javax.ejb.ApplicationException;

import br.com.bancoob.negocio.excecao.NegocioException;

/**
 * The Class RelatorioNegocioException.
 *
 * @author MARCUS.AMARO
 */
@ApplicationException(rollback = true)
public class RelatorioNegocioException extends NegocioException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Cria a excecao definindo a mensagem com parametros através do arquivos de
	 * mensagens padrão do projeto.
	 * 
	 * @param chave - Chave da mensagem.
	 * @param parametros - parametros utilizados para a contrução da mensagem.
	 */
	public RelatorioNegocioException(String chave, Object... parametros) {
		super(chave, parametros);
	}

	/**
	 * Cria a excecao definindo a mensagem com parametros através do arquivos de
	 * mensagens padrão do projeto e atribuindo uma excecao como causa.
	 * 
	 * @param excecao - Throwable que define a causa da excecao
	 * @param chave - Chave da mensagem.
	 * @param parametros - parametros utilizados para a contrução da mensagem.
	 */
	public RelatorioNegocioException(Throwable excecao, String chave, Object... parametros) {
		super(chave, parametros, excecao);
	}

	/**
	 * Cria a excecao definindo a mensagem sem parametros atraves do arquivos de
	 * mensagens padrao do projeto e atribuindo uma excecao como causa.
	 *
	 * @param chave - Chave da mensagem.
	 * @param excecao - Throwable que define a causa da excecao
	 */
	public RelatorioNegocioException(String chave, Throwable excecao) {
		super(chave, excecao);
	}

	/**
	 * Cria a excecao definindo a mensagem sem parametros atraves do arquivos de
	 * mensagens padrao do projeto.
	 * 
	 * @param chave - Chave da mensagem.
	 */
	public RelatorioNegocioException(String chave) {
		super(chave);
	}

	/**
	 * Cria a excecao atribuindo uma excecao como causa.
	 * 
	 * @param excecao - Throwable que define a causa da excecao
	 */
	public RelatorioNegocioException(Throwable excecao) {
		super(excecao);
	}
}
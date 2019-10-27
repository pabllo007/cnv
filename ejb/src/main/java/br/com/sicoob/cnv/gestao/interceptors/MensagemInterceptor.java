package br.com.sicoob.cnv.gestao.interceptors;

import br.com.bancoob.infraestrutura.mensagens.BancoobResourceBundle;
import br.com.bancoob.negocio.servicos.interceptors.InternacionalizacaoInterceptor;
import br.com.sicoob.cnv.gestao.mensagens.MensagensResourceBundle;

/**
 * Classe MensagemInterceptor para mensageria de retorno.
 *
 * @author Luis.Fernandez
 */
public class MensagemInterceptor extends InternacionalizacaoInterceptor {

	/* (non-Javadoc)
	 * @see br.com.bancoob.negocio.servicos.interceptors.InternacionalizacaoInterceptor#getResourceBundle()
	 */
	@Override
	protected BancoobResourceBundle getResourceBundle() {
		return MensagensResourceBundle.getInstance();
	}

}

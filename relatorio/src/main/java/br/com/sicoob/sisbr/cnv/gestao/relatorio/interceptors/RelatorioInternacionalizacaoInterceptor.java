package br.com.sicoob.sisbr.cnv.gestao.relatorio.interceptors;

import br.com.bancoob.infraestrutura.mensagens.BancoobResourceBundle;
import br.com.bancoob.negocio.servicos.interceptors.InternacionalizacaoInterceptor;
import br.com.sicoob.sisbr.cnv.gestao.relatorio.mensagens.RelatorioResourceBundle;

/**
 * The Class RelatorioInternacionalizacaoInterceptor.
 */


public class RelatorioInternacionalizacaoInterceptor extends InternacionalizacaoInterceptor { 
		
	/* (non-Javadoc)
	 * @see br.com.bancoob.negocio.servicos.interceptors.InternacionalizacaoInterceptor#getResourceBundle()
	 */
	@Override
	protected BancoobResourceBundle getResourceBundle() {
		return RelatorioResourceBundle.getInstance();
	}
}

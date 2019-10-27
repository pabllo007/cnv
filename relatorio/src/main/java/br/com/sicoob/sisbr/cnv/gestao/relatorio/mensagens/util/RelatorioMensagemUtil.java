package br.com.sicoob.sisbr.cnv.gestao.relatorio.mensagens.util;

import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.sisbr.cnv.gestao.relatorio.mensagens.RelatorioResourceBundle;


/**
 * @author marcusamaro
 *
 */
public class RelatorioMensagemUtil {

	/**
	 * Returna a mensagem
	 * 
	 * @param chave
	 * @return
	 */
	public static String getString(String chave) {
		return MensagemUtil.getString(chave, RelatorioResourceBundle.getInstance());
	}

	/**
	 * Retorna a mensagem com os parametros
	 * 
	 * @param chave
	 * @param parametros
	 * @return
	 */
	public static String getString(String chave, Object... parametros) {
		return MensagemUtil.getString(chave, RelatorioResourceBundle.getInstance(), parametros);
	}

}
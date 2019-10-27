package br.com.sicoob.cnv.gestao.util;

import com.google.gson.Gson;

/**
 * Classe de utilitário para conversão de objeto para string no formato JSON.
 *
 * @author Luis.Fernandez
 */
public class JsonUtil {

	/**
	 * Converter to string.
	 *
	 * @param objeto the objeto
	 * @param ignores the ignores
	 * @return the string
	 */
	public static String converterToString(Object objeto, String... ignores) {
		return new Gson().toJson(objeto);
	}

}

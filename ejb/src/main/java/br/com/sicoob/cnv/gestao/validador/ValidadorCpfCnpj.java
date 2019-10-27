package br.com.sicoob.cnv.gestao.validador;

import org.apache.commons.lang.StringUtils;

import br.com.bancoob.validacao.ValidacaoCnpj;
import br.com.bancoob.validacao.ValidacaoCpf;

public final class ValidadorCpfCnpj {

	private ValidadorCpfCnpj() throws IllegalAccessException {
		throw new IllegalAccessException("Esta classe é utilitária e não deve ser instânciada");
	}

	/** Tamanho de um CPF não formatado. */
	private static final int TAMANHO_CPF_NAO_FORMATADO = 11;

	/** Tamanho de um CNPJ não formatado. */
	private static final int TAMANHO_CNPJ_NAO_FORMATADO = 14;

	/**
	 * Valida o CPF ou CNPJ
	 * @param valor CPF ou CNPJ a ser validado.
	 * @return se o CPF/CNPJ é valido ou não.
	 */
	public static Boolean validarCpfCnpj(String valor){
		
		if(StringUtils.isEmpty(valor)){
			return false;
		} 
		
		String aux = retirarMascaraCPFCNPJ(valor);
		
		if (aux.length() == TAMANHO_CPF_NAO_FORMATADO) {
			ValidacaoCpf validacao = new ValidacaoCpf(aux, "");
			return validacao.validar();
		} else if (aux.length() == TAMANHO_CNPJ_NAO_FORMATADO) {
			ValidacaoCnpj validacao = new ValidacaoCnpj(aux, "");
			return validacao.validar();			
		} 
		
		return false;
	}
	
	public static String retirarMascaraCPFCNPJ(String valor) {

		StringBuilder buffer = new StringBuilder();

		for (int i = 0; i < valor.length(); i++) {
			char digito = valor.charAt(i);

			if (Character.isDigit(digito)) {
				buffer.append(digito);
			}
		}

		return buffer.toString();
	}
	
}

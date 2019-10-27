package br.com.sicoob.cnv.gestao.mensagens;

/**
 * Enum que contem o retorno da procedure [spu_CNV_ValidarRecebimentoConvenio].
 *
 * @author Luis.Fernandez
 */
public enum EnumMsgProcValidar {

	/** The msge001. */
	MSGE001("MSG_E001", "Convênio não Cadastrado."),

	/** The msge002. */
	MSGE002("MSG_E002", "Código de Barras Inválido."),

	/** The msge006. */
	MSGE006("MSG_E006", "O campo Código de Barras deve ter um comprimento de 44 caracteres."),

	/** The msge007. */
	MSGE007("MSG_E007", "O campo Número do Documento deve ter um valor válido."),

	/** The msge009. */
	MSGE009("MSG_E009", "Convênio não habilitado para recebimento via Código de Barras."),

	/** The msge010. */
	MSGE010("MSG_E010", "Este Convênio não pode ser recebido neste Canal de Atendimento."),

	/** The msge011. */
	MSGE011("MSG_E011", "Código de barras inválido para o convênio 8560117"),

	/** The msge012. */
	MSGE012("MSG_E012", "Código de barras inválido para o convênio 8185701"),

	/** The msge013. */
	MSGE013("MSG_E013", "Arrecadação não permitida para este ano."),

	/** The msge014. */
	MSGE014("MSG_E014", "Ano de Exercício inválido."),

	/** The msge015. */
	MSGE015("MSG_E015", "O valor do DPVAT é inválido."),

	/** The msge016. */
	MSGE016("MSG_E016", "O Ano de Exercício não confere com o Código de Barras."),

	/** The msge017. */
	MSGE017("MSG_E017", "Arrecadação não permitida para este código de receita."),

	/** The msge019. */
	MSGE019("MSG_E019", "Erro ao Validar a Data de Validade/Vencimento."),

	/** The msge020. */
	MSGE020("MSG_E020", "Data Validade/Vencimento Expirada. Pagamento/Agendamento Não Permitido. Data de Pagamento Após Vencimento."),

	/** The msge021. */
	MSGE021("MSG_E021", "O Valor Autenticado deve ter um valor válido."),

	/** The msge022. */
	MSGE022("MSG_E022", "O Valor do Código de Barras igual a ZERO. Não permitido recebimento nesse canal."),

	/** The msge023. */
	MSGE023("MSG_E023", "O Valor do Pagamento deve ser igual ao informado no Código de Barras."),

	/** The msge024. */
	MSGE024("MSG_E024", "Informar ou Desconto ou Multa/Juros/Outros Encargos."),

	/** The msge025. */
	MSGE025("MSG_E025", "Valor do Pagamento diverge do somatório dos Valores Informados."),

	/** The msge026. */
	MSGE027("MSG_E027", "Data Validade/Vencimento Expirada. Recebimento Não Autorizado.");

	/** The codigo. */
	private String codigo;

	/** The retorno proc. */
	private String retornoProc;

	/**
	 * Instantiates a new enum msg proc validar.
	 *
	 * @param codigo the codigo
	 * @param retornoProc the retorno proc
	 */
	private EnumMsgProcValidar(String codigo, String retornoProc) {
		this.codigo = codigo;
		this.retornoProc = retornoProc;
	}

	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Gets the retorno proc.
	 *
	 * @return the retorno proc
	 */
	public String getRetornoProc() {
		return retornoProc;
	}

	/**
	 * Recuperar msg.
	 *
	 * @param procMsg the proc msg
	 * @return the string
	 */
	public static String recuperarMsg(String procMsg) {
		String retorno = "";
		for(final EnumMsgProcValidar msg : values()) {
			if(procMsg.contains(msg.getRetornoProc())) {
				retorno = msg.getCodigo();
				break;
			}
		}
		return retorno;
	}


}



package br.com.sicoob.cnv.gestao.mensagens;

/**
 * Enum que contem o retorno da procedure [spu_CNV_ValidarRecebimentoConvenio].
 *
 * @author Luis.Fernandez
 */
public enum EnumMsgProcValidar {

	/** The msge001. */
	MSGE001("MSG_E001", "Conv�nio n�o Cadastrado."),

	/** The msge002. */
	MSGE002("MSG_E002", "C�digo de Barras Inv�lido."),

	/** The msge006. */
	MSGE006("MSG_E006", "O campo C�digo de Barras deve ter um comprimento de 44 caracteres."),

	/** The msge007. */
	MSGE007("MSG_E007", "O campo N�mero do Documento deve ter um valor v�lido."),

	/** The msge009. */
	MSGE009("MSG_E009", "Conv�nio n�o habilitado para recebimento via C�digo de Barras."),

	/** The msge010. */
	MSGE010("MSG_E010", "Este Conv�nio n�o pode ser recebido neste Canal de Atendimento."),

	/** The msge011. */
	MSGE011("MSG_E011", "C�digo de barras inv�lido para o conv�nio 8560117"),

	/** The msge012. */
	MSGE012("MSG_E012", "C�digo de barras inv�lido para o conv�nio 8185701"),

	/** The msge013. */
	MSGE013("MSG_E013", "Arrecada��o n�o permitida para este ano."),

	/** The msge014. */
	MSGE014("MSG_E014", "Ano de Exerc�cio inv�lido."),

	/** The msge015. */
	MSGE015("MSG_E015", "O valor do DPVAT � inv�lido."),

	/** The msge016. */
	MSGE016("MSG_E016", "O Ano de Exerc�cio n�o confere com o C�digo de Barras."),

	/** The msge017. */
	MSGE017("MSG_E017", "Arrecada��o n�o permitida para este c�digo de receita."),

	/** The msge019. */
	MSGE019("MSG_E019", "Erro ao Validar a Data de Validade/Vencimento."),

	/** The msge020. */
	MSGE020("MSG_E020", "Data Validade/Vencimento Expirada. Pagamento/Agendamento N�o Permitido. Data de Pagamento Ap�s Vencimento."),

	/** The msge021. */
	MSGE021("MSG_E021", "O Valor Autenticado deve ter um valor v�lido."),

	/** The msge022. */
	MSGE022("MSG_E022", "O Valor do C�digo de Barras igual a ZERO. N�o permitido recebimento nesse canal."),

	/** The msge023. */
	MSGE023("MSG_E023", "O Valor do Pagamento deve ser igual ao informado no C�digo de Barras."),

	/** The msge024. */
	MSGE024("MSG_E024", "Informar ou Desconto ou Multa/Juros/Outros Encargos."),

	/** The msge025. */
	MSGE025("MSG_E025", "Valor do Pagamento diverge do somat�rio dos Valores Informados."),

	/** The msge026. */
	MSGE027("MSG_E027", "Data Validade/Vencimento Expirada. Recebimento N�o Autorizado.");

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



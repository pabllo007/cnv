package br.com.sicoob.cnv.gestao.ejb.integracao;

import javax.ejb.Stateless;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.capes.api.negocio.delegates.CAPESApiFabricaDelegates;
import br.com.sicoob.capes.api.negocio.vo.PessoaVO;
import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.gestao.validador.ValidadorCpfCnpj;

/**
 * Classe de integra��o com o CAPES.
 *
 * @author Luis.Fernandez
 */
@Stateless
public class CapesEJB {

	/** The Constant SICOOB. */
	private final static int SICOOB = 1;

	/**
	 * Obter por cpf cnpj.
	 *
	 * @param cpfCnpj the cpf cnpj
	 * @return the list
	 * @throws CnvException the gestao exception
	 */
	public PessoaVO obterPorCpfCnpj(String cpfCnpj) throws CnvException {
		if (!ValidadorCpfCnpj.validarCpfCnpj(cpfCnpj)) {
			throw new CnvException("MSG_E004", "CNPJ");
		}
		try {
			PessoaVO pvo = CAPESApiFabricaDelegates.getInstance().criarPessoaDelegate().obterPorCpfCnpjInstituicao(cpfCnpj, SICOOB);
			if (pvo == null) {
				throw new CnvException("MSG_E005");
			}
			return pvo;
		} catch (BancoobException e) {
			throw new CnvException(getCodigoMensagemException(e));
		}
	}

	/**
	 * Encontra uma pessoa através da instituição e pessoa.
	 *
	 * @param instituicao ID de instituição
	 * @param pessoa      ID de pessoa
	 * @return pessoa correspondente
	 * @throws CnvException the cnv exception
	 */
	public PessoaVO obterPorInstituicaoPessoa(int pessoa) throws CnvException {
		try {
			PessoaVO pvo = CAPESApiFabricaDelegates.getInstance().criarPessoaDelegate().obterPorPessoaInstituicao(pessoa, SICOOB);
			if (pvo == null) {
				throw new CnvException("MSG_E006");
			}
			return pvo;
		} catch (BancoobException e) {
			throw new CnvException(getCodigoMensagemException(e));
		}
	}

	/**
	 * Gets the codigo mensagem exception.
	 *
	 * @param e the e
	 * @return the codigo mensagem exception
	 */
	private String getCodigoMensagemException(BancoobException e) {
		if (!e.getMessage().startsWith("MSG_E")) {
			return "cnv.msg.erro.integracao.capes";
		}
		return e.getMessage();
	}

}

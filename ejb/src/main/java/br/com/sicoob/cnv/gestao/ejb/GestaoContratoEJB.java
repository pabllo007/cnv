package br.com.sicoob.cnv.gestao.ejb;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.cnv.framework.persistencia.ejb.CnvBaseEJB;
import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.gestao.entity.Contrato;
import br.com.sicoob.cnv.gestao.entity.SituacaoContrato;
import br.com.sicoob.cnv.gestao.factory.ConvenioDB2FactoryDAO;
import br.com.sicoob.cnv.gestao.vo.ContratoListagemVO;
import br.com.sicoob.cnv.gestao.vo.ContratoPainelVO;
import br.com.sicoob.cnv.gestao.vo.RelContratoCompletoVO;
import br.com.sicoob.cnv.gestao.vo.RelatorioFiltroContratoVO;

/**
 * EJB de contratos.
 *
 * @author Bruno Santos
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@SuppressWarnings("unchecked")
public class GestaoContratoEJB extends CnvBaseEJB {

	/** DAO de contratos. */
	@Inject
	private ConvenioDB2FactoryDAO<Contrato> contratoDao;
	private final String MSGE002="MSG_E002";
	
	@Schedule
	public void definirSituacoes() {
		try {
			for (final Contrato contrato : contratoDao.buscarTodos(Contrato.class)) {
				final LocalDate hoje = LocalDate.now();
				LocalDate inicioRenovacao, inicioTarifaria, renovacao, tarifaria;
				if (contrato.getDataRenovacao() instanceof java.sql.Date) {
					renovacao = ((java.sql.Date) contrato.getDataRenovacao()).toLocalDate();
				} else {
					renovacao = contrato.getDataRenovacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				}
				if (contrato.getDataAtualizacaoTarifario() instanceof java.sql.Date) {
					tarifaria = ((java.sql.Date) contrato.getDataAtualizacaoTarifario()).toLocalDate();
				} else {
					tarifaria = contrato.getDataAtualizacaoTarifario().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();
				}
				inicioRenovacao = renovacao.minusDays(contrato.getAlertaRenovacao().getDias());
				inicioTarifaria = tarifaria.minusDays(contrato.getAlertaTarifaria().getDias());

				// Renovacao
				if (hoje.isBefore(inicioRenovacao)) {
					contrato.setSituacaoRenovacao(SituacaoContrato.VIGENTE);
				} else if (hoje.isAfter(inicioRenovacao) && hoje.compareTo(renovacao) <= 0) {
					contrato.setSituacaoRenovacao(SituacaoContrato.RENOVACAO);
				} else if (hoje.isAfter(renovacao)) {
					contrato.setSituacaoRenovacao(SituacaoContrato.VENCIDO);
				}

				// Tarifa
				if (hoje.isBefore(inicioTarifaria)) {
					contrato.setSituacaoTarifaria(SituacaoContrato.VIGENTE);
				} else if (hoje.isAfter(inicioTarifaria) && hoje.compareTo(tarifaria) <= 0) {
					contrato.setSituacaoTarifaria(SituacaoContrato.RENOVACAO);
				} else if (hoje.isAfter(tarifaria)) {
					contrato.setSituacaoTarifaria(SituacaoContrato.VENCIDO);
				}
				contratoDao.update(contrato);
			}
		} catch (final CnvException e) {
			Logger.getLogger(GestaoContratoEJB.class.getName()).severe(e.getLocalizedMessage());
		}
	}

	public void definirSituacao(long id) {
		try {
			final Contrato contrato = contratoDao.buscarPorID(id, Contrato.class);
			final LocalDate hoje = LocalDate.now();
			LocalDate inicioRenovacao, inicioTarifaria, renovacao, tarifaria;
			if (contrato.getDataRenovacao() instanceof java.sql.Date) {
				renovacao = ((java.sql.Date) contrato.getDataRenovacao()).toLocalDate();
			} else {
				renovacao = contrato.getDataRenovacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			}
			if (contrato.getDataAtualizacaoTarifario() instanceof java.sql.Date) {
				tarifaria = ((java.sql.Date) contrato.getDataAtualizacaoTarifario()).toLocalDate();
			} else {
				tarifaria = contrato.getDataAtualizacaoTarifario().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
			}
			inicioRenovacao = renovacao.minusDays(contrato.getAlertaRenovacao().getDias());
			inicioTarifaria = tarifaria.minusDays(contrato.getAlertaTarifaria().getDias());

			if (hoje.isBefore(inicioRenovacao)) {
				contrato.setSituacaoRenovacao(SituacaoContrato.VIGENTE);
			} else if (hoje.isAfter(inicioRenovacao) && hoje.compareTo(renovacao) <= 0) {
				contrato.setSituacaoRenovacao(SituacaoContrato.RENOVACAO);
			} else if (hoje.isAfter(renovacao)) {
				contrato.setSituacaoRenovacao(SituacaoContrato.VENCIDO);
			}
			if (hoje.isBefore(inicioTarifaria)) {
				contrato.setSituacaoTarifaria(SituacaoContrato.VIGENTE);
			} else if (hoje.isAfter(inicioTarifaria) && hoje.compareTo(tarifaria) <= 0) {
				contrato.setSituacaoTarifaria(SituacaoContrato.RENOVACAO);
			} else if (hoje.isAfter(tarifaria)) {
				contrato.setSituacaoTarifaria(SituacaoContrato.VENCIDO);
			}
			contratoDao.update(contrato);
		} catch (final CnvException e) {
			Logger.getLogger(GestaoContratoEJB.class.getName()).severe(e.getLocalizedMessage());
		}
	}

	/**
	 * Informa se um contrato existe ou não.
	 *
	 * @param numero número do contrato
	 * @return false case não existam contratos com o número ou true caso existam
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean existe(String numero) throws CnvException {
		return !buscarPorNumero(numero).isEmpty();
	}

	/**
	 * Busca um contrato.
	 *
	 * @param id ID do contrato
	 * @return lista de contratos
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Contrato buscar(long id) throws CnvException {
		try {
			final Contrato contrato = contratoDao.buscarPorID(id, Contrato.class);
			definirSituacao(id);
			if (contrato != null) {
				contrato.getConvenios().size();
				return contrato;
			}
			return null;
		} catch (final CnvException e) {
			throw new CnvException(e);
		}
	}

	/**
	 * Busca todos os contratos.
	 *
	 * @return lista de contratos
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ContratoListagemVO> buscar() throws CnvException {
		try {
			return (List<ContratoListagemVO>) contratoDao.consultarRegistrosVO("CONTRATOS", null,
					ContratoListagemVO.class);
		} catch (final CnvException e) {
			throw new CnvException(e);
		}
	}

	/**
	 * Busca todos os contratos ativos para o painel de monitoração de
	 * renovação.
	 *
	 * @return lista de contratos
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ContratoPainelVO> buscarAtivosRenovacao() throws CnvException {
		try {
			return (List<ContratoPainelVO>) contratoDao.consultarRegistrosVO("CONTRATOS_ATIVOS_RENOVACAO", null,
					ContratoPainelVO.class);
		} catch (final CnvException e) {
			throw new CnvException(e);
		}
	}

	/**
	 * Busca todos os contratos ativos para o painel de monitoração de
	 * atualização tarifária.
	 *
	 * @return lista de contratos
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ContratoPainelVO> buscarAtivosTarifario() throws CnvException {
		try {
			return (List<ContratoPainelVO>) contratoDao.consultarRegistrosVO("CONTRATOS_ATIVOS_TARIFARIO", null,
					ContratoPainelVO.class);
		} catch (final CnvException e) {
			throw new CnvException(e);
		}
	}

	/**
	 * Consulta contratos por instituição e pessoa.
	 *
	 * @param instituicao ID de instituição
	 * @param pessoa      ID de pessoa
	 * @return lista de contratos
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ContratoListagemVO> buscarPorInstituicaoPessoa(int instituicao, int pessoa) throws CnvException {
		try {
			final Map<String, Object> parametros = new HashMap<>();
			parametros.put("instituicao", instituicao);
			parametros.put("pessoa", pessoa);
			return (List<ContratoListagemVO>) contratoDao.consultarRegistrosVO("CONTRATOS_POR_INSTITUICAO_PESSOA",
					parametros, ContratoListagemVO.class);
		} catch (final CnvException e) {
			throw new CnvException(e);
		}
	}
	
	/**
	 * Consulta contratos por número.
	 *
	 * @param numero número do contrato
	 * @return lista de contratos
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ContratoListagemVO> buscarPorNumero(String numero) throws CnvException {
		try {
			final Map<String, Object> parametros = new HashMap<>();
			parametros.put("numero", numero);
			return (List<ContratoListagemVO>) contratoDao.consultarRegistrosVO("CONTRATOS_POR_NUMERO", parametros,
					ContratoListagemVO.class);
		} catch (final CnvException e) {
			throw new CnvException(e);
		}
	}

	/**
	 * Consulta contratos por sigla.
	 *
	 * @param sigla sigla do contrato
	 * @return lista de contratos
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ContratoListagemVO> buscarPorSigla(String sigla) throws CnvException {
		try {
			final Map<String, Object> parametros = new HashMap<>();
			parametros.put("sigla", sigla);
			return (List<ContratoListagemVO>) contratoDao.consultarRegistrosVO("CONTRATOS_POR_SIGLA", parametros,
					ContratoListagemVO.class);
		} catch (final CnvException e) {
			throw new CnvException(e);
		}
	}

	/**
	 * Adiciona um novo contrato.
	 *
	 * @param contrato contrato a adicionar
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void adicionar(Contrato contrato) throws CnvException {
		try {
			validar(contrato);
			contratoDao.incluir(contrato);
		} catch (final CnvException e) {
			throw new CnvException(e.getMessage(), e.getParametros());
		} catch (final BancoobException e) {
			throw new CnvException(e);
		}
	}

	/**
	 * Altera um contrato.
	 *
	 * @param contrato contrato a alterar
	 * @throws CnvException exceÃ§Ã£o de gestÃÂ£o
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void alterar(Contrato contrato) throws CnvException {
		try {
			validar(contrato);
			contratoDao.alterar(contrato);
			definirSituacao(contrato.getId());
		} catch (final CnvException e) {
			throw new CnvException(e.getMessage(), e.getParametros());
		} catch (final BancoobException e) {
			throw new CnvException(e);
		}
	}

	/**
	 * Valida um contrato antes de uma alteração no banco.
	 *
	 * @param contrato contrato a validar
	 * @throws CnvException exceção de validação
	 */
	private void validar(Contrato contrato) throws CnvException {
		if (contrato.getAlertaRenovacao() == null) {
			throw new CnvException(MSGE002, "O alerta para renovação");
		}
		if (contrato.getAlertaTarifaria() == null) {
			throw new CnvException(MSGE002, "O alerta para atualização tarifária");
		}
		if (contrato.getDataAssinaturaContrato() == null) {
			throw new CnvException(MSGE002, "A data de assinatura");
		}
		if (contrato.getDataAtualizacaoTarifario() == null) {
			throw new CnvException(MSGE002, "A data de atualização tarifária");
		}
		if (contrato.getDataRenovacao() == null) {
			throw new CnvException(MSGE002, "A data de renovação");
		}
		if (contrato.getDataRescisao() != null) {
			if (contrato.getDataRescisao().before(contrato.getDataAssinaturaContrato())) {
				throw new CnvException("MSG_E017");
			}
			if (contrato.getDescJustificativaRescisao() == null) {
				throw new CnvException(MSGE002 , "Ao rescindir um contrato, a justificativa");
			}
		}
		if (contrato.getNumero() == null || contrato.getNumero().isEmpty()) {
			throw new CnvException(MSGE002 , "o número do contrato");
		}
		if (contrato.getSigla() == null || contrato.getSigla().isEmpty()) {
			throw new CnvException(MSGE002 , "A sigla do contrato");
		}
		if (!contrato.getBolVigenciaIndeterminada()) {
			if (contrato.getDataVigencia() == null) {
				throw new CnvException(MSGE002 , "A data de vigência");
			}
			if (contrato.getDataAtualizacaoTarifario().after(contrato.getDataVigencia())) {
				throw new CnvException("MSG_E009");
			}
			if (contrato.getDataRenovacao().after(contrato.getDataVigencia())) {
				throw new CnvException("MSG_E016");
			}
			if (!contrato.getDataVigencia().after(contrato.getDataAssinaturaContrato())) {
				throw new CnvException("MSG_E007");
			}
		}
		if (!contrato.getDataAtualizacaoTarifario().after(contrato.getDataAssinaturaContrato())) {
			throw new CnvException("MSG_E013");
		}
		if (!contrato.getDataRenovacao().after(contrato.getDataAssinaturaContrato())) {
			throw new CnvException("MSG_E008");
		}
		if ((contrato.getId() == null
				|| !contrato.getNumero().equals(contratoDao.buscarPorID(contrato.getId(), Contrato.class).getNumero()))
				&& existe(contrato.getNumero())) {
			throw new CnvException("MSG_E019");
		}
	}
}
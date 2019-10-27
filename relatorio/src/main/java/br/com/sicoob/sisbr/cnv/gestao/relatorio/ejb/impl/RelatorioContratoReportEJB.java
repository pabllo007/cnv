package br.com.sicoob.sisbr.cnv.gestao.relatorio.ejb.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.commons.collections.CollectionUtils;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.capes.api.negocio.vo.PessoaVO;
import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.gestao.ejb.RelatorioContratoEJB;
import br.com.sicoob.cnv.gestao.ejb.integracao.CapesEJB;
import br.com.sicoob.cnv.gestao.entity.Contrato;
import br.com.sicoob.cnv.gestao.enums.TipoRelatorioEnum;
import br.com.sicoob.cnv.gestao.util.converters.CnpjConverter;
import br.com.sicoob.cnv.gestao.vo.RelContratoCompletoVO;
import br.com.sicoob.cnv.gestao.vo.RelatorioFiltroContratoVO;
import br.com.sicoob.relatorio.api.interfaces.IProcessamentoRelatorioServico;
import br.com.sicoob.sisbr.cnv.gestao.relatorio.ejb.RelatorioServicoAbstract;
import br.com.sicoob.sisbr.cnv.gestao.relatorio.enums.RelatorioGestaoContratoEnum;
import br.com.sicoob.sisbr.cnv.gestao.relatorio.excecao.RelatorioException;
import br.com.sicoob.sisbr.cnv.sci.api.ejb.impl.InstituicaoIntegracaoServicoEJB;
import br.com.sicoob.sisbr.cnv.sci.api.excecao.SciApiException;
import br.com.sicoob.sisbr.cnv.util.NumberUtilsCNV;
import br.com.sicoob.sisbr.cnv.util.StringUtilsCNV;
import br.com.sicoob.tipos.DateTime;

/**
 * The Class RelatorioRepasseFinanceiroEJB.
 */
@Stateless
@Remote(IProcessamentoRelatorioServico.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RelatorioContratoReportEJB extends RelatorioServicoAbstract<RelContratoCompletoVO>
		implements IProcessamentoRelatorioServico {

	@EJB
	private InstituicaoIntegracaoServicoEJB instituicaoIntegracaoServicoEJB;

	@EJB
	private CapesEJB capesEJB;

	@EJB
	private RelatorioContratoEJB ejb;

	@Inject
	private CnpjConverter cnpjConverter;

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	protected List<RelContratoCompletoVO> getDadosRelatorio() throws BancoobException {

		String nomeRelatorio = obterRelatorioEnum().getNomeArquivoJasper();
		addParametro("nomeRelatorio", nomeRelatorio);
		getLogger().info("PARAMETRO nomeRelatorio: " + nomeRelatorio);
		
		DateTime dataRepasse = obterParametroDate("dataProcessamento");
		addParametro("DATA_PROCESSAMENTO", dataRepasse);
		getLogger().info("PARAMETRO DATA_PROCESSAMENTO: " + dataRepasse);

		String numCooperativa = obterParametroTexto("numCooperativa");
		addParametro("NUM_COOPERATIVA", numCooperativa);
		getLogger().info("PARAMETRO NUM_COOPERATIVA: " + numCooperativa);

		String nomeCooperativa = getNomeCooperativa();
		addParametro("nomeCooperativa", nomeCooperativa);
		getLogger().info("PARAMETRO nomeCooperativa: " + nomeCooperativa);

		String nomeUsuario = getRelatorioDadosDTO().getUsuarioBancoobDTO().getLogin();
		addParametro("nomeUsuario", nomeUsuario);
		getLogger().info("PARAMETRO nomeUsuario: " + nomeUsuario);
		addParametro("REPORT_LOCALE", new Locale("pt", "BR"));
		
		String dataInicio = obterParametroTexto("dataInicio");
		addParametro("dataInicio", dataInicio);
		getLogger().info("PARAMETRO dataInicio: " + dataInicio);

		String dataFim = obterParametroTexto("dataFim");
		addParametro("dataFim", dataFim);
		getLogger().info("PARAMETRO dataFim: " + dataFim);

		RelatorioFiltroContratoVO filtro = new RelatorioFiltroContratoVO();
		filtro.setDataInicioPeriodo(obterParametroTexto("dataInicio"));
		filtro.setDataFimPeriodo(obterParametroTexto("dataFim"));
		filtro.setNumRelatorio(obterParametroInt("numRelatorio"));

		return getListaContratos(filtro);
	}

	@Override
	protected RelatorioGestaoContratoEnum obterRelatorioEnum() {
		return RelatorioGestaoContratoEnum.recuperar(obterParametroInt("numRelatorio"));
	}

	private String getNomeCooperativa() throws RelatorioException {
		String strIdInstituicao = getRelatorioDadosDTO().getUsuarioBancoobDTO().getIdInstituicao();
		if (StringUtilsCNV.isBlank(strIdInstituicao)) {
			return StringUtilsCNV.EMPTY;
		}
		try {
			Integer idInstituicao = NumberUtilsCNV.createInteger(strIdInstituicao);
			return instituicaoIntegracaoServicoEJB.obterNomeInstituicao(idInstituicao);
		} catch (SciApiException e) {
			getLogger().erro(e, e.getMessage());
			throw new RelatorioException(e);
		}
	}

	private List<RelContratoCompletoVO> getListaContratos(RelatorioFiltroContratoVO vo) throws BancoobException {
		try {
			TipoRelatorioEnum tipoRelatorioEnum = TipoRelatorioEnum.getTipoRelatorio(vo.getNumRelatorio());
			List<Contrato> listContrato = ejb.buscaDadosRelatorio(tipoRelatorioEnum,
					vo.getDataInicioPeriodo() != null ? vo.getDataInicioPeriodo().toString() : null,
					vo.getDataFimPeriodo() != null ? vo.getDataFimPeriodo().toString() : null);

			List<RelContratoCompletoVO> listResposta = new ArrayList<RelContratoCompletoVO>();
			if (CollectionUtils.isNotEmpty(listContrato)) {
				for (Contrato relContrato : listContrato) {
					RelContratoCompletoVO contratoTodos = new RelContratoCompletoVO();
					contratoTodos.setSituacao(relContrato.getRescisaoFormatada() != null ? "ATIVO" : "ENCERRADO");
					contratoTodos.setDataRescisaoFormatada(relContrato.getRescisaoFormatada().equals("") ? "-" : relContrato.getRescisaoFormatada());
					contratoTodos.setNumero(relContrato.getNumero());
					contratoTodos.setCnpj(cnpjConverter.convertToEntityAttribute(recuperaCapes(relContrato.getIdPessoaEmpresa())));
					contratoTodos.setSigla(relContrato.getSigla());
					contratoTodos.setDataAssinaturaContratoFormatada(relContrato.getAssinaturaFormatada().equals("") ? "-" : relContrato.getAssinaturaFormatada());
					contratoTodos.setDataVigenciaFormatada(relContrato.getVigenciaFormatada().equals("") ? "-" : relContrato.getVigenciaFormatada());
					contratoTodos.setDataRenovacaoFormatada(relContrato.getRenovacaoFormatada().equals("") ? "-" : relContrato.getRenovacaoFormatada());
					contratoTodos.setDescSituacaoRenovacao(relContrato.getSituacaoRenovacao().getDescricao());
					contratoTodos.setAlertaRenovacaoDias(relContrato.getPrazoRenovacao().toString());
					contratoTodos.setAcaoOperacionalRenovacao(classificarOperacional(relContrato.getAcaoOperacionalRenovacao()));
					
					contratoTodos.setDataAtualizacaoTarifarioFormatada(relContrato.getAtualizacaoFormatada().equals("") == true ? "-" : relContrato.getAtualizacaoFormatada());
					contratoTodos.setDescSituacaoTarifaria(relContrato.getSituacaoTarifaria().getDescricao());
					contratoTodos.setAlertaTarifariaDias(relContrato.getPrazoTarifario().toString());
					contratoTodos.setAcaoOperacionalTarifaria(classificarOperacional(relContrato.getAcaoOperacionalTarifaria()));
					
					listResposta.add(contratoTodos);
				}
			}
			return listResposta;
		} catch (NoResultException e) {
			throw new BancoobException("Registo não encontrado!");
		}

	}

	private String recuperaCapes(Long idPessoaEmpresa) {
		try {
			PessoaVO pessoa = capesEJB.obterPorInstituicaoPessoa(idPessoaEmpresa.intValue());
			return pessoa.getCpfCnpj();
		} catch (CnvException e) {
			return null;
		}
	}
	
	private String classificarOperacional(Boolean operacional) {
		if(operacional == null) {
			return "-";
		}else {
			if(operacional.booleanValue()) {
				return "Iniciado";
			} else {
				return "Pendente";
			}
		}
	}

}

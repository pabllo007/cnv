package br.com.sicoob.cnv.gestao.testes.ejb;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.gestao.ejb.GestaoContratoEJB;
import br.com.sicoob.cnv.gestao.entity.Contrato;
import br.com.sicoob.cnv.gestao.entity.PeriodicidadeAlerta;
import br.com.sicoob.cnv.gestao.entity.SituacaoContrato;
import br.com.sicoob.cnv.gestao.factory.ConvenioDB2FactoryDAO;
import br.com.sicoob.cnv.gestao.vo.ContratoListagemVO;
import br.com.sicoob.cnv.gestao.vo.ContratoPainelVO;

@RunWith(PowerMockRunner.class)
public class GestaoContratoEJBTest {

	@InjectMocks
	private GestaoContratoEJB gestaoContratoEJB;

	@Mock
	private ConvenioDB2FactoryDAO<Contrato> contratoDao;

	@Test(expected = CnvException.class)
	public void testarAtualizacaoAntesIgualAssinatura() throws BancoobException {
		final Contrato contrato = new Contrato();
		final List<Contrato> lista = new ArrayList<>();
		final Map<String, Object> parametros = new HashMap<>();
		contrato.setBolVigenciaIndeterminada(true);
		contrato.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataRenovacao(Date.from(LocalDate.of(2003, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setNumero("104");
		parametros.put("numero", "104");

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_NUMERO", parametros, ContratoListagemVO.class))
				.thenReturn((List) new ArrayList<ContratoListagemVO>());
		Mockito.when(contratoDao.incluir(contrato)).thenAnswer(answer -> {
			lista.add(contrato);
			return Mockito.anyObject();
		});
		gestaoContratoEJB.adicionar(contrato);
	}

	@Test(expected = CnvException.class)
	public void testarAtualizacaoAposVigencia() throws BancoobException {
		final Contrato contrato = new Contrato();
		final List<Contrato> lista = new ArrayList<>();
		final Map<String, Object> parametros = new HashMap<>();
		contrato.setBolVigenciaIndeterminada(false);
		contrato.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2005, 5, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataRenovacao(Date.from(LocalDate.of(2003, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataVigencia(Date.from(LocalDate.of(2004, 4, 4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setNumero("104");
		parametros.put("numero", "104");

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_NUMERO", parametros, ContratoListagemVO.class))
				.thenReturn((List) new ArrayList<ContratoListagemVO>());
		Mockito.when(contratoDao.incluir(contrato)).thenAnswer(answer -> {
			lista.add(contrato);
			return Mockito.anyObject();
		});
		gestaoContratoEJB.adicionar(contrato);
	}

	@Test(expected = CnvException.class)
	public void testarContratoExistente() throws BancoobException {
		final Contrato primeiro = new Contrato(), segundo = new Contrato();
		final List<Contrato> lista = new ArrayList<>();
		final Map<String, Object> parametros = new HashMap<>();
		primeiro.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setDataRenovacao(Date.from(LocalDate.of(2003, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setDataVigencia(Date.from(LocalDate.of(2004, 4, 4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setNumero("104");
		segundo.setBolVigenciaIndeterminada(true);
		segundo.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setDataRenovacao(Date.from(LocalDate.of(2003, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setNumero("104");
		lista.add(primeiro);
		parametros.put("numero", "104");

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_NUMERO", parametros, ContratoListagemVO.class))
				.thenReturn((List) new ArrayList<ContratoListagemVO>());
		Mockito.when(contratoDao.incluir(segundo)).thenAnswer(answer -> {
			lista.add(segundo);
			return Mockito.anyObject();
		});
		gestaoContratoEJB.adicionar(segundo);
	}

	@Test(expected = CnvException.class)
	public void testarRenovacaoAntesIgualAssinatura() throws BancoobException {
		final Contrato contrato = new Contrato();
		final List<Contrato> lista = new ArrayList<>();
		final Map<String, Object> parametros = new HashMap<>();
		contrato.setBolVigenciaIndeterminada(true);
		contrato.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataRenovacao(Date.from(LocalDate.of(2000, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setNumero("104");
		parametros.put("numero", "104");

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_NUMERO", parametros, ContratoListagemVO.class))
				.thenReturn((List) new ArrayList<ContratoListagemVO>());
		Mockito.when(contratoDao.incluir(contrato)).thenAnswer(answer -> {
			lista.add(contrato);
			return Mockito.anyObject();
		});
		gestaoContratoEJB.adicionar(contrato);
	}

	@Test(expected = CnvException.class)
	public void testarRescisaoAntesAssinatura() throws BancoobException {
		final Contrato contrato = new Contrato();
		final List<Contrato> lista = new ArrayList<>();
		final Map<String, Object> parametros = new HashMap<>();
		contrato.setBolVigenciaIndeterminada(true);
		contrato.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataRescisao(Date.from(LocalDate.of(2000, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataRenovacao(Date.from(LocalDate.of(2003, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setNumero("104");
		parametros.put("numero", "104");

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_NUMERO", parametros, ContratoListagemVO.class))
				.thenReturn((List) new ArrayList<ContratoListagemVO>());
		Mockito.when(contratoDao.incluir(contrato)).thenAnswer(answer -> {
			lista.add(contrato);
			return Mockito.anyObject();
		});
		gestaoContratoEJB.adicionar(contrato);
	}

	@Test(expected = CnvException.class)
	public void testarRenovacaoAposVigencia() throws BancoobException {
		final Contrato contrato = new Contrato();
		final List<Contrato> lista = new ArrayList<>();
		final Map<String, Object> parametros = new HashMap<>();
		contrato.setBolVigenciaIndeterminada(false);
		contrato.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataRenovacao(Date.from(LocalDate.of(2005, 5, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataVigencia(Date.from(LocalDate.of(2004, 4, 4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setNumero("104");
		parametros.put("numero", "104");

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_NUMERO", parametros, ContratoListagemVO.class))
				.thenReturn((List) new ArrayList<ContratoListagemVO>());
		Mockito.when(contratoDao.incluir(contrato)).thenAnswer(answer -> {
			lista.add(contrato);
			return Mockito.anyObject();
		});
		gestaoContratoEJB.adicionar(contrato);
	}

	@Test(expected = CnvException.class)
	public void testarVigenciaAntesIgualAssinatura() throws BancoobException {
		final Contrato contrato = new Contrato();
		final List<Contrato> lista = new ArrayList<>();
		final Map<String, Object> parametros = new HashMap<>();
		contrato.setBolVigenciaIndeterminada(false);
		contrato.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataRenovacao(Date.from(LocalDate.of(2000, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataVigencia(Date.from(LocalDate.of(2000, 4, 4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setNumero("104");
		parametros.put("numero", "104");

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_NUMERO", parametros, ContratoListagemVO.class))
				.thenReturn((List) new ArrayList<ContratoListagemVO>());
		Mockito.when(contratoDao.incluir(contrato)).thenAnswer(answer -> {
			lista.add(contrato);
			return Mockito.anyObject();
		});
		gestaoContratoEJB.adicionar(contrato);
	}

	@Test
	public void testarAdicionar() throws BancoobException, CnvException {
		final Contrato contrato = new Contrato();
		final List<Contrato> lista = new ArrayList<>();
		final Map<String, Object> parametros = new HashMap<>();
		contrato.setAcaoOperacionalRenovacao(false);
		contrato.setAcaoOperacionalTarifaria(true);
		contrato.setAlertaRenovacao(new PeriodicidadeAlerta());
		contrato.setAlertaTarifaria(new PeriodicidadeAlerta());
		contrato.setBolVigenciaIndeterminada(false);
		contrato.setConvenios(new ArrayList<>());
		contrato.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataRenovacao(Date.from(LocalDate.of(2003, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataVigencia(Date.from(LocalDate.of(2004, 4, 4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setIdInstituicaoEmpresa(52l);
		contrato.setIdPessoaEmpresa(78l);
		contrato.setNumero("104");
		contrato.setSigla("CNTRT");
		parametros.put("numero", "104");

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_NUMERO", parametros, ContratoListagemVO.class))
				.thenReturn((List) new ArrayList<ContratoListagemVO>());
		Mockito.when(contratoDao.incluir(contrato)).thenAnswer(answer -> {
			lista.add(contrato);
			return Mockito.anyObject();
		});
		gestaoContratoEJB.adicionar(contrato);
		Assert.assertTrue(!lista.isEmpty());
	}

	@Test
	public void testarAlterar() throws BancoobException, CnvException {
		final Contrato primeiro = new Contrato(), segundo = new Contrato();
		final Map<String, Object> parametros = new HashMap<>();
		primeiro.setAcaoOperacionalRenovacao(false);
		primeiro.setAcaoOperacionalTarifaria(true);
		primeiro.setAlertaRenovacao(new PeriodicidadeAlerta());
		primeiro.setAlertaTarifaria(new PeriodicidadeAlerta());
		primeiro.setBolVigenciaIndeterminada(false);
		primeiro.setConvenios(new ArrayList<>());
		primeiro.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setDataRenovacao(Date.from(LocalDate.of(2003, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setDataVigencia(Date.from(LocalDate.of(2004, 4, 4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setIdInstituicaoEmpresa(52l);
		primeiro.setIdPessoaEmpresa(78l);
		primeiro.setNumero("104");
		primeiro.setSigla("CNTRT");

		segundo.setAcaoOperacionalRenovacao(true);
		segundo.setAcaoOperacionalTarifaria(true);
		segundo.setAlertaRenovacao(new PeriodicidadeAlerta());
		segundo.setAlertaTarifaria(new PeriodicidadeAlerta());
		segundo.setBolVigenciaIndeterminada(false);
		segundo.setConvenios(new ArrayList<>());
		segundo.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setDataRenovacao(Date.from(LocalDate.of(2003, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setDataVigencia(Date.from(LocalDate.of(2004, 4, 4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setIdInstituicaoEmpresa(52l);
		segundo.setIdPessoaEmpresa(78l);
		segundo.setNumero("104");
		segundo.setSigla("CNTRT");
		parametros.put("numero", "104");
		primeiro.setId(1L);
		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_NUMERO", parametros, ContratoListagemVO.class))
				.thenReturn((List) new ArrayList<ContratoListagemVO>());
		Mockito.doAnswer(answer -> {
			primeiro.setAcaoOperacionalRenovacao(segundo.getAcaoOperacionalRenovacao());
			return primeiro;
		}).when(contratoDao).alterar(primeiro);

		final Contrato contrato = new Contrato();
		contrato.setAcaoOperacionalRenovacao(false);
		contrato.setAcaoOperacionalTarifaria(true);

		contrato.setBolVigenciaIndeterminada(false);
		contrato.setConvenios(new ArrayList<>());
		contrato.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataRenovacao(Date.from(LocalDate.of(2003, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataVigencia(Date.from(LocalDate.of(2004, 4, 4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setIdInstituicaoEmpresa(52l);
		contrato.setIdPessoaEmpresa(78l);
		contrato.setNumero("104");
		contrato.setSigla("CNTRT");
		final SituacaoContrato situacaoRenovacao = new SituacaoContrato();
		situacaoRenovacao.setId(SituacaoContrato.VENCIDO.getId());
		contrato.setSituacaoRenovacao(situacaoRenovacao);
		contrato.setSituacaoTarifaria(situacaoRenovacao);

		final PeriodicidadeAlerta alertaRenovacao = new PeriodicidadeAlerta();
		alertaRenovacao.setDescricao("Test");
		alertaRenovacao.setDias(2);
		contrato.setAlertaRenovacao(alertaRenovacao);
		contrato.setAlertaTarifaria(alertaRenovacao);
		contrato.setId(1L);

		Mockito.when(contratoDao.buscarPorID(1L, Contrato.class)).thenReturn(contrato);

		gestaoContratoEJB.alterar(primeiro);
		Assert.assertTrue(primeiro.getAcaoOperacionalRenovacao());
	}

	@Test
	public void testarBuscarAtivosRenovacao() throws CnvException {
		final ContratoPainelVO contrato = new ContratoPainelVO();
		final List lista = new ArrayList<>();
		contrato.setAcao(false);
		contrato.setCnpj("26527810413015");
		contrato.setInstituicao(26);
		contrato.setNumero("104");
		contrato.setPessoa(52);
		contrato.setPrazo(78);
		contrato.setSigla("CNTRT");
		contrato.setSituacao("ATIVO");
		contrato.setVigencia(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		lista.add(contrato);

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_ATIVOS_RENOVACAO", null, ContratoPainelVO.class))
				.thenReturn(lista);
		final List<ContratoPainelVO> consulta = gestaoContratoEJB.buscarAtivosRenovacao();
		Assert.assertTrue(!consulta.isEmpty());
	}

	@Test
	public void testarBuscarAtivosTarifario() throws CnvException {
		final ContratoPainelVO contrato = new ContratoPainelVO();
		final List lista = new ArrayList<>();
		contrato.setAcao(false);
		contrato.setCnpj("26527810413015");
		contrato.setInstituicao(26);
		contrato.setNumero("104");
		contrato.setPessoa(52);
		contrato.setPrazo(78);
		contrato.setSigla("CNTRT");
		contrato.setSituacao("ATIVO");
		contrato.setVigencia(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		lista.add(contrato);

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_ATIVOS_TARIFARIO", null, ContratoPainelVO.class))
				.thenReturn(lista);
		final List<ContratoPainelVO> consulta = gestaoContratoEJB.buscarAtivosTarifario();
		Assert.assertTrue(!consulta.isEmpty());
	}

	@Test
	public void testarBuscar() throws CnvException {
		final ContratoListagemVO contrato = new ContratoListagemVO();
		final List lista = new ArrayList<ContratoListagemVO>();
		contrato.setAssinatura(Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setCnpj("26527810413015");
		contrato.setInstituicao(26);
		contrato.setNumero("104");
		contrato.setPessoa(52);
		contrato.setSigla("CNTRT");
		contrato.setSituacao("ATIVO");
		lista.add(contrato);

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS", null, ContratoListagemVO.class)).thenReturn(lista);
		final List<ContratoListagemVO> consulta = gestaoContratoEJB.buscar();
		Assert.assertTrue(!consulta.isEmpty());
	}

	@Test
	public void testarBuscarPorID() throws CnvException {
		final Contrato contrato = new Contrato();
		contrato.setAcaoOperacionalRenovacao(false);
		contrato.setAcaoOperacionalTarifaria(true);

		contrato.setBolVigenciaIndeterminada(false);
		contrato.setConvenios(new ArrayList<>());
		contrato.setDataAssinaturaContrato(
				Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataAtualizacaoTarifario(
				Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataRenovacao(Date.from(LocalDate.of(2003, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setDataVigencia(Date.from(LocalDate.of(2004, 4, 4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		contrato.setIdInstituicaoEmpresa(52l);
		contrato.setIdPessoaEmpresa(78l);
		contrato.setNumero("104");
		contrato.setSigla("CNTRT");
		final SituacaoContrato situacaoRenovacao = new SituacaoContrato();
		situacaoRenovacao.setId(SituacaoContrato.VENCIDO.getId());
		contrato.setSituacaoRenovacao(situacaoRenovacao);
		contrato.setSituacaoTarifaria(situacaoRenovacao);

		final PeriodicidadeAlerta alertaRenovacao = new PeriodicidadeAlerta();
		alertaRenovacao.setDescricao("Test");
		alertaRenovacao.setDias(2);
		contrato.setAlertaRenovacao(alertaRenovacao);
		contrato.setAlertaTarifaria(alertaRenovacao);

		Mockito.when(contratoDao.buscarPorID(26l, Contrato.class)).thenReturn(contrato);
		final Contrato consulta = gestaoContratoEJB.buscar(26l);
		Assert.assertEquals(contrato, consulta);
	}

	@Test
	public void testarBuscarPorInstituicaoPessoa() throws CnvException {
		final ContratoListagemVO primeiro = new ContratoListagemVO(), segundo = new ContratoListagemVO();
		final int instituicao = 26, pessoa = 52;
		final List lista = new ArrayList<ContratoListagemVO>();
		final Map<String, Object> parametros = new HashMap<>();
		primeiro.setAssinatura(Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setCnpj("26527810413015");
		primeiro.setInstituicao(26);
		primeiro.setNumero("104");
		primeiro.setPessoa(52);
		primeiro.setSigla("CNTRT");
		primeiro.setSituacao("ATIVO");
		segundo.setAssinatura(Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setCnpj("61822082342602");
		segundo.setInstituicao(26);
		segundo.setNumero("78");
		segundo.setPessoa(130);
		segundo.setSigla("TRCNTRT");
		segundo.setSituacao("INATIVO");
		lista.add(primeiro);
		lista.add(segundo);
		parametros.put("instituicao", 26);
		parametros.put("pessoa", 52);

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_INSTITUICAO_PESSOA", parametros,
				ContratoListagemVO.class))
				.thenReturn(((List) ((List<ContratoListagemVO>) lista).stream()
						.filter(contrato -> contrato.getInstituicao() == instituicao && contrato.getPessoa() == pessoa)
						.collect(Collectors.toList())));
		final List<ContratoListagemVO> consulta = gestaoContratoEJB.buscarPorInstituicaoPessoa(instituicao, pessoa);
		Assert.assertTrue(consulta.size() == 1);
	}

	@Test
	public void testarBuscarPorNumero() throws CnvException {
		final ContratoListagemVO primeiro = new ContratoListagemVO(), segundo = new ContratoListagemVO();
		final List lista = new ArrayList<ContratoListagemVO>();
		final Map<String, Object> parametros = new HashMap<>();
		final String numero = "104";
		primeiro.setAssinatura(Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setCnpj("26527810413015");
		primeiro.setInstituicao(26);
		primeiro.setNumero("104");
		primeiro.setPessoa(52);
		primeiro.setSigla("CNTRT");
		primeiro.setSituacao("ATIVO");
		segundo.setAssinatura(Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setCnpj("61822082342602");
		segundo.setInstituicao(26);
		segundo.setNumero("78");
		segundo.setPessoa(130);
		segundo.setSigla("TRCNTRT");
		segundo.setSituacao("INATIVO");
		lista.add(primeiro);
		lista.add(segundo);
		parametros.put("numero", numero);

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_NUMERO", parametros, ContratoListagemVO.class))
				.thenReturn(((List) ((List<ContratoListagemVO>) lista).stream()
						.filter(contrato -> contrato.getNumero().equals(numero)).collect(Collectors.toList())));
		final List<ContratoListagemVO> consulta = gestaoContratoEJB.buscarPorNumero(numero);
		Assert.assertTrue(consulta.size() == 1);
	}

	@Test
	public void testarBuscarSigla() throws CnvException {
		final ContratoListagemVO primeiro = new ContratoListagemVO(), segundo = new ContratoListagemVO();
		final List lista = new ArrayList<ContratoListagemVO>();
		final Map<String, Object> parametros = new HashMap<>();
		final String sigla = "CNTRT";
		primeiro.setAssinatura(Date.from(LocalDate.of(2001, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setCnpj("26527810413015");
		primeiro.setInstituicao(26);
		primeiro.setNumero("104");
		primeiro.setPessoa(52);
		primeiro.setSigla("CNTRT");
		primeiro.setSituacao("ATIVO");
		segundo.setAssinatura(Date.from(LocalDate.of(2002, 2, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setCnpj("61822082342602");
		segundo.setInstituicao(26);
		segundo.setNumero("78");
		segundo.setPessoa(130);
		segundo.setSigla("TRCNTRT");
		segundo.setSituacao("INATIVO");
		lista.add(primeiro);
		lista.add(segundo);
		parametros.put("sigla", sigla);

		Mockito.when(contratoDao.consultarRegistrosVO("CONTRATOS_POR_SIGLA", parametros, ContratoListagemVO.class))
				.thenReturn(((List) ((List<ContratoListagemVO>) lista).stream()
						.filter(contrato -> contrato.getSigla().equals(sigla)).collect(Collectors.toList())));
		final List<ContratoListagemVO> consulta = gestaoContratoEJB.buscarPorSigla(sigla);
		Assert.assertTrue(consulta.size() == 1);
	}

	@Test
	public void testarDefinirSituacoes() throws CnvException {
		final Contrato primeiro = new Contrato(), segundo = new Contrato(), terceiro = new Contrato();
		final List<Contrato> lista = new ArrayList<>();
		final PeriodicidadeAlerta primeira = new PeriodicidadeAlerta(), segunda = new PeriodicidadeAlerta();
		primeira.setDias(90);
		segunda.setDias(60);
		primeiro.setAlertaRenovacao(primeira);
		primeiro.setAlertaTarifaria(primeira);
		primeiro.setDataAtualizacaoTarifario(Date.from(
				LocalDate.now().plusDays(primeira.getDias() + 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setDataRenovacao(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		primeiro.setSituacaoRenovacao(SituacaoContrato.VENCIDO);
		primeiro.setSituacaoTarifaria(SituacaoContrato.RENOVACAO);

		segundo.setAlertaRenovacao(segunda);
		segundo.setAlertaTarifaria(segunda);
		segundo.setDataAtualizacaoTarifario(
				Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setDataRenovacao(Date.from(
				LocalDate.now().plusDays(segunda.getDias() + 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		segundo.setSituacaoRenovacao(SituacaoContrato.RENOVACAO);
		segundo.setSituacaoTarifaria(SituacaoContrato.VIGENTE);

		terceiro.setAlertaRenovacao(primeira);
		terceiro.setAlertaTarifaria(segunda);
		terceiro.setDataAtualizacaoTarifario(
				Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		terceiro.setDataRenovacao(
				Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		terceiro.setSituacaoRenovacao(SituacaoContrato.VIGENTE);
		terceiro.setSituacaoTarifaria(SituacaoContrato.VENCIDO);

		lista.add(primeiro);
		lista.add(segundo);
		lista.add(terceiro);

		Mockito.when(contratoDao.buscarTodos(Contrato.class)).thenReturn(lista);
		gestaoContratoEJB.definirSituacoes();

		Assert.assertEquals(primeiro.getSituacaoRenovacao(), SituacaoContrato.RENOVACAO);
		Assert.assertEquals(primeiro.getSituacaoTarifaria(), SituacaoContrato.VIGENTE);

		Assert.assertEquals(segundo.getSituacaoRenovacao(), SituacaoContrato.VIGENTE);
		Assert.assertEquals(segundo.getSituacaoTarifaria(), SituacaoContrato.VENCIDO);

		Assert.assertEquals(terceiro.getSituacaoRenovacao(), SituacaoContrato.VENCIDO);
		Assert.assertEquals(terceiro.getSituacaoTarifaria(), SituacaoContrato.RENOVACAO);
	}
}
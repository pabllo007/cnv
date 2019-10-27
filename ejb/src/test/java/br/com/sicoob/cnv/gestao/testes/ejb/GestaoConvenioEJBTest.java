package br.com.sicoob.cnv.gestao.testes.ejb;

import java.util.ArrayList;
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

import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.gestao.ejb.GestaoConvenioEJB;
import br.com.sicoob.cnv.gestao.entity.Convenio;
import br.com.sicoob.cnv.gestao.factory.ConvenioDB2FactoryDAO;
import br.com.sicoob.cnv.gestao.vo.ConvenioListagemVO;

@RunWith(PowerMockRunner.class)
public class GestaoConvenioEJBTest {

	@InjectMocks
	private GestaoConvenioEJB gestaoConvenioEJB;

	@Mock
	private ConvenioDB2FactoryDAO<Convenio> convenioDao;

	@Test
	public void testarBuscar() throws CnvException
	{
		ConvenioListagemVO convenio = new ConvenioListagemVO();
		List lista = new ArrayList<ConvenioListagemVO>();
		convenio.setCnpj("26527810413015");
		convenio.setFebraban("26");
		convenio.setNumero("52");
		convenio.setSegmento("Segmento");
		convenio.setSigla("CNVN");
		convenio.setSituacao("ATIVO");
		lista.add(convenio);

		Mockito.when(convenioDao.consultarRegistrosVO("CONVENIOS_LIVRES", null, ConvenioListagemVO.class)).thenReturn(lista);
		final List<ConvenioListagemVO> consulta = gestaoConvenioEJB.buscarLivres();
		Assert.assertTrue(!consulta.isEmpty());
	}
	@Test
	public void testarBuscarPorCNPJ() throws CnvException
	{
		ConvenioListagemVO primeiro = new ConvenioListagemVO(), segundo = new ConvenioListagemVO();
		List lista = new ArrayList<ConvenioListagemVO>();
		Map<String, Object> parametros = new HashMap<>();
		String cnpj = "36800319000149";
		primeiro.setCnpj("36800319000149");
		primeiro.setFebraban("26");
		primeiro.setNumero("104");
		primeiro.setSegmento("Segmento");
		primeiro.setSigla("CNVN");
		primeiro.setSituacao("ATIVO");
		segundo.setCnpj("61822082342602");
		segundo.setFebraban("26");
		segundo.setNumero("78");
		segundo.setSegmento("Segmento");
		segundo.setSigla("TRCNVN");
		segundo.setSituacao("INATIVO");
		lista.add(primeiro);
		lista.add(segundo);
		parametros.put("cnpj", cnpj);


		Mockito.when(convenioDao.consultarRegistrosVO("CONVENIOS_LIVRES_POR_CNPJ", parametros, ConvenioListagemVO.class))
			.thenReturn(((List) ((List<ConvenioListagemVO>) lista).stream()
				.filter(convenio -> convenio.getCnpj().equals(cnpj)).collect(Collectors.toList())));
		final List<ConvenioListagemVO> consulta = gestaoConvenioEJB.buscarLivresPorCNPJ(cnpj);
		Assert.assertTrue(consulta.size() == 1);
	}

	@Test
	public void testarBuscarPorNumero() throws CnvException
	{
		ConvenioListagemVO primeiro = new ConvenioListagemVO(), segundo = new ConvenioListagemVO();
		List lista = new ArrayList<ConvenioListagemVO>();
		Map<String, Object> parametros = new HashMap<>();
		String numero = "104";
		primeiro.setCnpj("26527810413015");
		primeiro.setFebraban("26");
		primeiro.setNumero("104");
		primeiro.setSegmento("Segmento");
		primeiro.setSigla("CNVN");
		primeiro.setSituacao("ATIVO");
		segundo.setCnpj("61822082342602");
		segundo.setFebraban("26");
		segundo.setNumero("78");
		segundo.setSegmento("Segmento");
		segundo.setSigla("TRCNVN");
		segundo.setSituacao("INATIVO");
		lista.add(primeiro);
		lista.add(segundo);
		parametros.put("numero", numero);


		Mockito.when(convenioDao.consultarRegistrosVO("CONVENIOS_LIVRES_POR_NUMERO", parametros, ConvenioListagemVO.class))
			.thenReturn(((List) ((List<ConvenioListagemVO>) lista).stream().filter(convenio -> convenio.getNumero().equals(numero))
				.collect(Collectors.toList())));
		final List<ConvenioListagemVO> consulta = gestaoConvenioEJB.buscarLivresPorNumero(numero);
		Assert.assertTrue(consulta.size() == 1);
	}
	@Test
	public void testarBuscarSigla() throws CnvException
	{
		ConvenioListagemVO primeiro = new ConvenioListagemVO(), segundo = new ConvenioListagemVO();
		List lista = new ArrayList<ConvenioListagemVO>();
		Map<String, Object> parametros = new HashMap<>();
		String sigla = "CNVN";
		primeiro.setCnpj("26527810413015");
		primeiro.setFebraban("26");
		primeiro.setNumero("104");
		primeiro.setSegmento("Segmento");
		primeiro.setSigla("CNVN");
		primeiro.setSituacao("ATIVO");
		segundo.setCnpj("61822082342602");
		segundo.setFebraban("26");
		segundo.setNumero("78");
		segundo.setSegmento("Segmento");
		segundo.setSigla("TRCNVN");
		segundo.setSituacao("INATIVO");
		lista.add(primeiro);
		lista.add(segundo);
		parametros.put("sigla", sigla);


		Mockito.when(convenioDao.consultarRegistrosVO("CONVENIOS_LIVRES_POR_SIGLA", parametros, ConvenioListagemVO.class))
			.thenReturn(((List) ((List<ConvenioListagemVO>) lista).stream().filter(convenio -> convenio.getSigla().equals(sigla))
				.collect(Collectors.toList())));
		final List<ConvenioListagemVO> consulta = gestaoConvenioEJB.buscarLivresPorSigla(sigla);
		Assert.assertTrue(consulta.size() == 1);
	}

}

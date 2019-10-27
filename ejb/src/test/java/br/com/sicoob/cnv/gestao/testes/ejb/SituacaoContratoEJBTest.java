package br.com.sicoob.cnv.gestao.testes.ejb;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.gestao.ejb.SituacaoContratoEJB;
import br.com.sicoob.cnv.gestao.entity.SituacaoContrato;
import br.com.sicoob.cnv.gestao.factory.ConvenioDB2FactoryDAO;

@RunWith(PowerMockRunner.class)
public class SituacaoContratoEJBTest {

	@InjectMocks
	private SituacaoContratoEJB situacaoContratoEjb;

	@Mock
	private ConvenioDB2FactoryDAO<SituacaoContrato> situacaoDao;

	@Test
	public void testarConsultar() throws CnvException {
		List<SituacaoContrato> listaRetorno = new ArrayList<>();
		SituacaoContrato sc = new SituacaoContrato();
		sc.setId(1L);
		sc.setDescricao("Desc");
		listaRetorno.add(sc);
		Mockito.when(situacaoDao.buscarTodos(SituacaoContrato.class)).thenReturn(listaRetorno);
		final List<SituacaoContrato> consultar = situacaoContratoEjb.consultar();
		Assert.assertTrue(!consultar.isEmpty());
	}

	@Test(expected = CnvException.class)
	public void testarConsultarException() throws CnvException {
		Mockito.when(situacaoDao.buscarTodos(SituacaoContrato.class)).thenThrow(CnvException.class);
		situacaoContratoEjb.consultar();
	}

}

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
import br.com.sicoob.cnv.gestao.ejb.PeriodicidadeAlertaEJB;
import br.com.sicoob.cnv.gestao.entity.PeriodicidadeAlerta;
import br.com.sicoob.cnv.gestao.factory.ConvenioDB2FactoryDAO;

@RunWith(PowerMockRunner.class)
public class PeriodicidadeAlertaEJBTest {

	@InjectMocks
	private PeriodicidadeAlertaEJB periodicidadeAlertaEJB;

	@Mock
	private ConvenioDB2FactoryDAO<PeriodicidadeAlerta> periodicidadeDao;

	@Test
	public void testarConsultar() throws CnvException {
		List<PeriodicidadeAlerta> listaRetorno = new ArrayList<>();
		PeriodicidadeAlerta pa = new PeriodicidadeAlerta();
		pa.setId(1L);
		pa.setDias(60);
		pa.setDescricao("Desc");
		listaRetorno.add(pa);

		Mockito.when(periodicidadeDao.buscarTodos(PeriodicidadeAlerta.class)).thenReturn(listaRetorno);
		final List<PeriodicidadeAlerta> consultar = periodicidadeAlertaEJB.consultar();
		Assert.assertTrue(!consultar.isEmpty());
	}

	@Test(expected = CnvException.class)
	public void testarConsultarException() throws CnvException {
		Mockito.when(periodicidadeDao.buscarTodos(PeriodicidadeAlerta.class)).thenThrow(CnvException.class);
		periodicidadeAlertaEJB.consultar();
	}

}

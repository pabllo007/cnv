package br.com.sicoob.cnv.gestao.testes.factory;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.bancoob.negocio.entidades.BancoobEntidade;
import br.com.bancoob.persistencia.comandos.ColecaoComandos;
import br.com.sicoob.cnv.gestao.factory.ConvenioDB2FactoryDAO;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EntityManager.class, ColecaoComandos.class })
public class ConvenioDB2FactoryDAOTest {

	@InjectMocks
	private ConvenioDB2FactoryDAO<BancoobEntidade> dao;

	@Mock
	private EntityManager entityManager;

	@Test
	public void buscarPorIDTest() throws Exception {
		Assert.assertNull(dao.getEntityManager());
	}

}

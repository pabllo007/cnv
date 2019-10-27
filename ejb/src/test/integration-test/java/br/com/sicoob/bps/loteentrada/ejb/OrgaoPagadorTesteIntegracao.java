package br.com.sicoob.bps.loteentrada.ejb;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.sicoob.bps.loteentrada.excecao.BPSNegocioException;
import br.com.sicoob.bps.loteentrada.framework.rs.excecao.BPSRSException;
import br.com.sicoob.bps.loteentrada.framework.test.persistencia.TesteIntegracaoBPSLib;
import br.com.sicoob.bps.loteentrada.integracao.exception.BPSIntegracaoNegocioException;

@RunWith(Arquillian.class)
public class OrgaoPagadorTesteIntegracao extends TesteIntegracaoBPSLib {
	
	@EJB OrgaoPagadorArrecadadorEJB ejb;
	
	@Deployment
	public static Archive<EnterpriseArchive> createTestArchive() throws Exception {
		return preparaAmbienteGerarEar(OrgaoPagadorTesteIntegracao.class);
	}

	
	@Test
	public void ejb() throws BPSIntegracaoNegocioException{
		Assert.assertNotNull(null);
	}
	
	@Test
	public void buscarInstituicaoTesteIntegracao()
			throws BPSRSException, InstantiationException, IllegalAccessException, BPSIntegracaoNegocioException, BPSNegocioException {
		Assert.assertFalse(ejb.consultar().isEmpty());
	}

	@Test
	public void buscarCarTesteIntegracao() throws BPSRSException, BPSNegocioException {
		Assert.assertNotNull(ejb.recuperaCar(19L));
	}
	
	
}

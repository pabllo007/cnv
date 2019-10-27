package br.com.sicoob.bps.loteentrada.rs;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.sicoob.bps.loteentrada.framework.rs.BPSRestUtil;
import br.com.sicoob.bps.loteentrada.framework.rs.enums.VerboHTTP;
import br.com.sicoob.bps.loteentrada.framework.rs.excecao.BPSRSException;
import br.com.sicoob.bps.loteentrada.framework.test.persistencia.TesteIntegracaoBPSLibTest;

@RunWith(PowerMockRunner.class)
public class OrgaoPagadorRestTesteIntegracao extends TesteIntegracaoBPSLibTest {

	
	@Test
	public void buscarInstituicaoTesteIntegracao()
			throws BPSRSException, InstantiationException, IllegalAccessException {
		Map<String, String> parametros = new HashMap<>();
		parametros.put("cooperativa", "3027");
		parametros.put("pac", "14");
		String path = "orgaosPagadorasArrecadadoras/instituicao";

		JSONArray resultado = BPSRestUtil.consumirServico(path, VerboHTTP.GET, parametros);
		Assert.assertTrue(resultado.size() > 0);
	}

	@Test
	public void buscarOrgaosTesteIntegracao() throws BPSRSException {
		Map<String, String> parametros = new HashMap<>();
		parametros.put("campo", "1");
		parametros.put("valor", "3027");
		String path = "orgaosPagadorasArrecadadoras";

		JSONArray resultado = BPSRestUtil.consumirServico(path, VerboHTTP.GET, parametros);
		Assert.assertTrue(resultado.size() > 0);
	}

	@Test
	public void buscarOrgaoPagadorTesteIntegracao() throws BPSRSException {
		String path = "orgaosPagadorasArrecadadoras/669649";

		JSONArray resultado = BPSRestUtil.consumirServico(path, VerboHTTP.GET);
		Assert.assertTrue(resultado.size() == 1);
	}

}

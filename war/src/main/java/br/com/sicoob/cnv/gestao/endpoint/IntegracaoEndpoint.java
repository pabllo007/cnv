package br.com.sicoob.cnv.gestao.endpoint;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.sicoob.capes.api.negocio.vo.PessoaVO;
import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.framework.rest.BaseEndpoint;
import br.com.sicoob.cnv.framework.rest.excecao.EndpointException;
import br.com.sicoob.cnv.gestao.base.ResultadoDTO;
import br.com.sicoob.cnv.gestao.dto.PessoaDTO;
import br.com.sicoob.cnv.gestao.ejb.integracao.CapesEJB;

/**
 * The Class IntegracaoEndpoint.
 */
@Path("/integracao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IntegracaoEndpoint extends BaseEndpoint {

	/** The capes EJB. */
	@EJB
	private CapesEJB capesEJB;

	/**
	 * Gets the pessoa por cnpj.
	 *
	 * @param cnpj the cnpj
	 * @return the pessoa por cnpj
	 * @throws EndpointException the endpoint exception
	 */
	@GET
	@Path("/capes/{cnpj}")
	public Response getPessoaPorCnpj(@PathParam("cnpj") String cnpj) throws EndpointException {
		try {
			PessoaVO pessoaVO = capesEJB.obterPorCpfCnpj(cnpj);
			PessoaDTO dto = (PessoaDTO) mapper(pessoaVO, PessoaDTO.class);
			return ok(new ResultadoDTO<>(dto));
		} catch (CnvException e) {
			throw new EndpointException(e.getMessage());
		}
	}

}

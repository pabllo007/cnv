package br.com.sicoob.cnv.gestao.endpoint;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.framework.rest.BaseEndpoint;
import br.com.sicoob.cnv.framework.rest.excecao.EndpointException;
import br.com.sicoob.cnv.gestao.base.ResultadoDTO;
import br.com.sicoob.cnv.gestao.ejb.PeriodicidadeAlertaEJB;
import br.com.sicoob.cnv.gestao.ejb.SituacaoContratoEJB;

/**
 * The Class DominioFixoEndpoint.
 *
 * @author Luis.Fernandez
 */
@Path("/dominio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DominioFixoEndpoint extends BaseEndpoint {

	/** The periodicidade alerta EJB. */
	@EJB
	private PeriodicidadeAlertaEJB periodicidadeAlertaEJB;

	/** The situacao contrato EJB. */
	@EJB
	private SituacaoContratoEJB situacaoContratoEJB;

	/**
	 * Gets the periodicidade.
	 *
	 * @return the periodicidade
	 * @throws EndpointException the endpoint exception
	 */
	@GET
	@Path("/periodicidade")
	public Response getPeriodicidade() throws EndpointException {
		try {
			return ok(new ResultadoDTO<>(periodicidadeAlertaEJB.consultar()));
		} catch (CnvException e) {
			throw new EndpointException(e.getMessage());
		}
	}

	/**
	 * Gets the situacao.
	 *
	 * @return the situacao
	 * @throws EndpointException the endpoint exception
	 */
	@GET
	@Path("/situacao")
	public Response getSituacao() throws EndpointException {
		try {
			return ok(new ResultadoDTO<>(situacaoContratoEJB.consultar()));
		} catch (CnvException e) {
			throw new EndpointException(e.getMessage());
		}
	}

}

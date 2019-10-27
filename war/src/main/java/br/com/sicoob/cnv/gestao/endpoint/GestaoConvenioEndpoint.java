package br.com.sicoob.cnv.gestao.endpoint;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.framework.rest.BaseEndpoint;
import br.com.sicoob.cnv.framework.rest.excecao.EndpointException;
import br.com.sicoob.cnv.gestao.base.ResultadoDTO;
import br.com.sicoob.cnv.gestao.ejb.GestaoConvenioEJB;

/**
 * Endpoint de convênios.
 * @author Bruno Santos
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/convenios")
public class GestaoConvenioEndpoint extends BaseEndpoint
{
    @EJB
    private GestaoConvenioEJB gestaoConvenioEJB;

    @GET
    @Path("/")
    public Response buscar() throws EndpointException
    {
        try
        {
            return ok(new ResultadoDTO<>(gestaoConvenioEJB.buscarLivres()));
        }
        catch (CnvException e)
        {
			throw new EndpointException(e.getMessage());
        }
    }
    @GET
    @Path("/{filtro}/{pesquisa}")
    public Response buscar(@PathParam("filtro") String filtro, @PathParam("pesquisa") String pesquisa) throws EndpointException
    {
        try
        {
            switch (filtro)
            {
                case "cnpj" : return ok(new ResultadoDTO<>(gestaoConvenioEJB.buscarLivresPorCNPJ(pesquisa)));
                case "numero" : return ok(new ResultadoDTO<>(gestaoConvenioEJB.buscarLivresPorNumero(pesquisa)));
                case "sigla" : return ok(new ResultadoDTO<>(gestaoConvenioEJB.buscarLivresPorSigla(pesquisa)));
                default : return Response.status(Status.NOT_FOUND).build();
            }
        }
        catch (CnvException e)
        {
			throw new EndpointException(e.getMessage(), e.getParametros());
        }
    }
    
	@GET
	@Path("/convenios")
	public Response pesquisarBanco( @QueryParam("nomeColuna") String nomeColuna,  @QueryParam("valorColuna") String valorColuna) throws EndpointException { 
		try {
			return ok(new ResultadoDTO<>(gestaoConvenioEJB.listaConveniosSelecaoGeral(nomeColuna, valorColuna)));
		} catch (CnvException e) {
			throw new EndpointException(e.getMessage());
		}
		
	}
    
}
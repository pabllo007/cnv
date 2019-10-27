package br.com.sicoob.cnv.gestao.endpoint;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.sicoob.capes.api.negocio.vo.PessoaVO;
import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.framework.rest.BaseEndpoint;
import br.com.sicoob.cnv.framework.rest.excecao.EndpointException;
import br.com.sicoob.cnv.gestao.base.ResultadoDTO;
import br.com.sicoob.cnv.gestao.ejb.GestaoContratoEJB;
import br.com.sicoob.cnv.gestao.ejb.integracao.CapesEJB;
import br.com.sicoob.cnv.gestao.entity.Contrato;
import br.com.sicoob.cnv.gestao.vo.ContratoListagemVO;
import br.com.sicoob.cnv.gestao.vo.ContratoPainelVO;

/**
 * Endpoint de contrato.
 *
 * @author Bruno Santos
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/contratos")
public class GestaoContratoEndpoint extends BaseEndpoint {

	@EJB
	private CapesEJB capesEJB;
	@EJB
	private GestaoContratoEJB gestaoContratoEJB;

	@GET
	@Path("/{filtro}/{pesquisa}")
	public Response buscar(@PathParam("filtro") String filtro, @PathParam("pesquisa") String pesquisa)
			throws EndpointException {
		try {
			switch (filtro) {
			case "cnpj": {
				PessoaVO pessoa = capesEJB.obterPorCpfCnpj(pesquisa);
				if (pessoa != null) {
					Collection<ContratoListagemVO> contratos = gestaoContratoEJB
							.buscarPorInstituicaoPessoa(pessoa.getIdInstituicao(), pessoa.getIdPessoa());
					contratos.forEach(contrato -> {
						contrato.setCnpj(pesquisa);
						contrato.setFantasia(pessoa.getNomeApelido());
						contrato.setRazaoSocial(pessoa.getNomeCompleto());
					});
					return ok(new ResultadoDTO<>(contratos));
				}
			}
			case "numero": {
				Collection<ContratoListagemVO> contratos = gestaoContratoEJB.buscarPorNumero(pesquisa);
				for (ContratoListagemVO contrato : contratos) {
					PessoaVO pessoa = capesEJB.obterPorInstituicaoPessoa(contrato.getPessoa());
					contrato.setCnpj(pessoa.getCpfCnpj());
					contrato.setFantasia(pessoa.getNomeApelido());
					contrato.setRazaoSocial(pessoa.getNomeCompleto());
				}
				return ok(new ResultadoDTO<>(contratos));
			}
			case "painel": {
				if (pesquisa.equals("renovacao")) {
					Collection<ContratoPainelVO> contratos = gestaoContratoEJB.buscarAtivosRenovacao();
					for (ContratoPainelVO contrato : contratos) {
						contrato.setCnpj(capesEJB.obterPorInstituicaoPessoa(contrato.getPessoa()).getCpfCnpj());
					}
					return ok(new ResultadoDTO<>(contratos));
				} else if (pesquisa.equals("tarifario")) {
					Collection<ContratoPainelVO> contratos = gestaoContratoEJB.buscarAtivosTarifario();
					for (ContratoPainelVO contrato : contratos) {
						contrato.setCnpj(capesEJB.obterPorInstituicaoPessoa(contrato.getPessoa()).getCpfCnpj());
					}
					return ok(new ResultadoDTO<>(contratos));
				}
				return Response.status(Status.NOT_FOUND).build();
			}
			case "sigla": {
				Collection<ContratoListagemVO> contratos = gestaoContratoEJB.buscarPorSigla(pesquisa);
				for (ContratoListagemVO contrato : contratos) {
					PessoaVO pessoa = capesEJB.obterPorInstituicaoPessoa(contrato.getPessoa());
					contrato.setCnpj(pessoa.getCpfCnpj());
					contrato.setFantasia(pessoa.getNomeApelido());
					contrato.setRazaoSocial(pessoa.getNomeCompleto());
				}
				return ok(new ResultadoDTO<>(contratos));
			}
			default:
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (CnvException exception) {
			throw new EndpointException(exception.getMessage(), exception.getParametros());
		}
	}

	@GET
	@Path("/{id}")
	public Response buscar(@PathParam("id") long id) throws EndpointException {
		try {
			return ok(new ResultadoDTO<>(gestaoContratoEJB.buscar(id)));
		} catch (CnvException e) {
			throw new EndpointException(e.getMessage());
		}
	}

	@GET
	@Path("/")
	public Response buscar() throws EndpointException {
		try {
			Collection<ContratoListagemVO> contratos = gestaoContratoEJB.buscar();
			for (ContratoListagemVO contrato : contratos) {
				PessoaVO pessoa = capesEJB.obterPorInstituicaoPessoa(contrato.getPessoa());
				contrato.setCnpj(pessoa.getCpfCnpj());
				contrato.setFantasia(pessoa.getNomeApelido());
				contrato.setRazaoSocial(pessoa.getNomeCompleto());
			}
			return ok(new ResultadoDTO<>(contratos));
		} catch (CnvException e) {
			throw new EndpointException(e.getMessage());
		}
	}

	@POST
	@Path("/")
	public Response adicionar(Contrato contrato) throws EndpointException {
		try {
			PessoaVO pessoa = capesEJB.obterPorCpfCnpj(contrato.getCnpj());
			contrato.setIdInstituicaoEmpresa(pessoa.getIdInstituicao().longValue());
			contrato.setIdPessoaEmpresa(pessoa.getIdPessoa().longValue());
			gestaoContratoEJB.adicionar(contrato);
			return Response.ok().build();
		} catch (CnvException e) {
			throw new EndpointException(e.getMessage(), e.getParametros());
		}
	}

	@PUT
	@Path("/{id}")
	public Response alterar(@PathParam("id") long id, Contrato contrato) throws EndpointException {
		contrato.setId(id);
		return alterar(contrato);
	}

	@PUT
	@Path("/")
	public Response alterar(Contrato contrato) throws EndpointException {
		try {
			if (contrato.getCnpj() != null) {
				PessoaVO pessoa = capesEJB.obterPorCpfCnpj(contrato.getCnpj());
				contrato.setIdInstituicaoEmpresa(pessoa.getIdInstituicao().longValue());
				contrato.setIdPessoaEmpresa(pessoa.getIdPessoa().longValue());
			}
			gestaoContratoEJB.alterar(contrato);
			return Response.ok().build();
		} catch (CnvException e) {
			throw new EndpointException(e.getMessage(), e.getParametros());
		}
	}
}
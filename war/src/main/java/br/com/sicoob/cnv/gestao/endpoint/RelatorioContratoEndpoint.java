package br.com.sicoob.cnv.gestao.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
import br.com.sicoob.cnv.gestao.dto.RelatorioFiltroContratoVO;
import br.com.sicoob.cnv.gestao.dto.RelatorioRetornoDTO;
import br.com.sicoob.cnv.gestao.ejb.RelatorioContratoEJB;
import br.com.sicoob.cnv.gestao.ejb.integracao.CapesEJB;
import br.com.sicoob.cnv.gestao.entity.Contrato;
import br.com.sicoob.cnv.gestao.enums.TipoRelatorioEnum;

/**
 * Endpoint de relatório.
 *
 * @author Phelipe Palmeira
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/relatorio-contrato")
public class RelatorioContratoEndpoint extends BaseEndpoint {

	@EJB
	private RelatorioContratoEJB ejb;

	@EJB
	private CapesEJB capesEJB;

	@POST
	@Path("/{tipoRelatorio}")
	public Response buscar(@PathParam("tipoRelatorio") Integer tipoRelatorio, RelatorioFiltroContratoVO filtro)
			throws EndpointException {
		try {
			List<RelatorioRetornoDTO> listaRetorno = new ArrayList<RelatorioRetornoDTO>();
			TipoRelatorioEnum tipoRelatorioEnum = TipoRelatorioEnum.getTipoRelatorio(tipoRelatorio);
			List<Contrato> lista = ejb.buscaDadosRelatorio(tipoRelatorioEnum,
					filtro.getDataInicioPeriodo() != null ? filtro.getDataInicioPeriodo().getData() : null,
					filtro.getDataFimPeriodo() != null ? filtro.getDataFimPeriodo().getData() : null);

			for (Contrato contrato : lista) {
				RelatorioRetornoDTO dto = (RelatorioRetornoDTO) mapper(contrato, RelatorioRetornoDTO.class);
				dto.setDataAssinaturaContratoFormatada(contrato.getAssinaturaFormatada());
				dto.setDataAtualizacaoTarifarioFormatada(contrato.getAtualizacaoFormatada());
				dto.setDataRenovacaoFormatada(contrato.getRenovacaoFormatada());
				dto.setDataRescisaoFormatada(contrato.getRescisaoFormatada());
				dto.setDataVigenciaFormatada(contrato.getVigenciaFormatada());

				dto.setDescSituacaoRenovacao(
						contrato.getSituacaoRenovacao() != null ? contrato.getSituacaoRenovacao().getDescricao() : "");
				dto.setDescSituacaoTarifaria(
						contrato.getSituacaoTarifaria() != null ? contrato.getSituacaoTarifaria().getDescricao() : "");

				dto.setAlertaRenovacaoDias(
						contrato.getPrazoRenovacao() != null ? contrato.getPrazoRenovacao() : null);
				dto.setAlertaTarifariaDias(
						contrato.getPrazoTarifario() != null ? contrato.getPrazoTarifario() : null);

				PessoaVO pessoa = capesEJB.obterPorInstituicaoPessoa(contrato.getIdPessoaEmpresa().intValue());
				if (pessoa != null) {
					dto.setCnpj(pessoa.getCpfCnpj());
				}

				listaRetorno.add(dto);
			}

			return ok(new ResultadoDTO<List<RelatorioRetornoDTO>>(listaRetorno));
		} catch (CnvException exception) {
			throw new EndpointException(exception.getMessage(), exception.getParametros());
		}
	}

}

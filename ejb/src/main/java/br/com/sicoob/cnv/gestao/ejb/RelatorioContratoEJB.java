package br.com.sicoob.cnv.gestao.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.gestao.entity.Contrato;
import br.com.sicoob.cnv.gestao.enums.TipoRelatorioEnum;
import br.com.sicoob.cnv.gestao.factory.ConvenioDB2FactoryDAO;

/**
 * The Class RelatorioContratoEJB.
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RelatorioContratoEJB {

	@Inject
	private ConvenioDB2FactoryDAO<Contrato> contratoDao; 
	
	
	/**
	 * @param RelatorioFiltroContratoVO
	 * @throws CnvException
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Contrato> buscaDadosRelatorio(TipoRelatorioEnum tipo, String dataInicio, String dataFim)
			throws CnvException {

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", tipo.getCodigo());
		if (dataInicio != null) {
			parametros.put("dataInicio", dataInicio);
		}
		if (dataFim != null) {
			parametros.put("dataFim", dataFim);
		}
		
		try {
			return (ArrayList<Contrato>) contratoDao.consultarRegistrosVO("RELATORIO_BUSCAR", parametros);
		} catch (NoResultException e) {
			return new ArrayList<Contrato> ();
		}
		
	}

}

package br.com.sicoob.cnv.gestao.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import br.com.sicoob.cnv.framework.persistencia.ejb.CnvBaseEJB;
import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.gestao.entity.SituacaoContrato;
import br.com.sicoob.cnv.gestao.factory.ConvenioDB2FactoryDAO;

/**
 * Classe EJB para situacao do contrato
 *
 * @author Luis.Fernandez
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SituacaoContratoEJB extends CnvBaseEJB {

	/** The situacao contrato DAO. */
	@Inject
	private ConvenioDB2FactoryDAO<SituacaoContrato> situacaoContratoDAO;

	/**
	 * Consultar.
	 *
	 * @return the list
	 * @throws CnvException the gestao exception
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SituacaoContrato> consultar() throws CnvException {
		try {
			return situacaoContratoDAO.buscarTodos(SituacaoContrato.class);
		} catch (final CnvException e) {
			throw new CnvException(e);
		}
	}

}

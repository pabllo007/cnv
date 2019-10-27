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
import br.com.sicoob.cnv.gestao.entity.PeriodicidadeAlerta;
import br.com.sicoob.cnv.gestao.factory.ConvenioDB2FactoryDAO;

/**
 * Classe EJB para PeriodicidadeAlerta.
 *
 * @author Luis.Fernandez
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PeriodicidadeAlertaEJB extends CnvBaseEJB {

	/** The periodicidade dao. */
	@Inject
	private ConvenioDB2FactoryDAO<PeriodicidadeAlerta> periodicidadeDao;

	/**
	 * Consultar.
	 *
	 * @return the list
	 * @throws CnvException the gestao exception
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PeriodicidadeAlerta> consultar() throws CnvException {
		try {
			return periodicidadeDao.buscarTodos(PeriodicidadeAlerta.class);
		} catch (final CnvException e) {
			throw new CnvException(e);
		}
	}

}

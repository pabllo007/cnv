package br.com.sicoob.sisbr.cnv.gestao.relatorio.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.bancoob.negocio.entidades.BancoobEntidade;
import br.com.sicoob.cnv.framework.persistencia.CnvDAO;

/**
 * Classe de DAO para o DB2.
 *
 * @author Luis.Fernandez
 * @param <E> the element type
 */
public class RelatorioContratoDB2FactoryDAO<E extends BancoobEntidade> extends CnvDAO<E> {

	/** The entity manager. */
	@PersistenceContext(unitName = "emConvenioRelatorioDs")
	private EntityManager entityManager;

	/**
	 * Instantiates a new convenio DB 2 DAO.
	 *
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public RelatorioContratoDB2FactoryDAO() throws InstantiationException, IllegalAccessException {
		super("jdbc/convenios_cadastroDS", "cnv_relatorio.queries.xml", "comandos-relatorio-helper");
	}

	/* (non-Javadoc)
	 * @see br.com.sicoob.cnv.framework.persistencia.CnvDAO#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}

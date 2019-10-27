package br.com.sicoob.cnv.gestao.factory;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.bancoob.negocio.entidades.BancoobEntidade;
import br.com.bancoob.persistencia.comandos.Comando;
import br.com.bancoob.persistencia.excecao.PersistenciaException;
import br.com.sicoob.cnv.framework.persistencia.CnvDAO;
import br.com.sicoob.cnv.gestao.entity.Convenio;

/**
 * Classe de DAO para o DB2.
 *
 * @param <E> the element type
 *
 * @author Luis.Fernandez
 */
public class ConvenioDB2FactoryDAO<E extends BancoobEntidade> extends CnvDAO<E> {

	/** The entity manager. */
	@PersistenceContext(unitName = "emConvenioDs")
	private EntityManager entityManager;

	/**
	 * Instantiates a new convenio DB 2 DAO.
	 *
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public ConvenioDB2FactoryDAO() throws InstantiationException, IllegalAccessException {
		super("jdbc/convenios_cadastroDS", "db2.queries.xml", "comandos-ejb-helper");
	}

	/* (non-Javadoc)
	 * @see br.com.sicoob.cnv.framework.persistencia.CnvDAO#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
//	TODO: Fazer a implementação de forma correta !!! (verificar a definição dos filtros do relatório)
	public List<Object> listaConveniosSelecaoGeral(String nomeColuna, String valorColuna) {
		List<Object> listaConvenios = new ArrayList<Object>();	
		Comando comando = getComando("LISTA_CONVENIOS_SELECAO_GERAL");
		try {
			comando.configurar();

			StringBuilder condicao = new StringBuilder(" AND ");
			condicao.append(nomeColuna).append(" LIKE '%").append(valorColuna).append("%'");
			String sql = comando.getSqlEmUso() + condicao;
			comando.setSql(sql);

			Query query = getEntityManager().createNativeQuery(comando.getSqlEmUso());
			
			List<Object[]> lista = query.getResultList();
			lista.stream().forEach(objeto ->{
//				Convenio vo = new Convenio();
//				vo.setCodConvenio(converteResultado(objeto[0], String.class));
//				vo.setSiglaConvenio(converteResultado(objeto[1], String.class));				
//				listaConvenios.add(vo);
			});
			return listaConvenios;
		} catch (PersistenceException | PersistenciaException e) {
//			getLogger().erro(e, IntegracaoCadastroMessageUtil.getString("msg.erro.recuperar.convenio.selecao.geral", nomeColuna));
//			throw new IntegracaoCadastroException("msg.erro.recuperar.convenio.selecao.geral", new Object[]{nomeColuna}, e);
		} finally {
			fecharComando(comando);
		}
		return listaConvenios;

	}

}
